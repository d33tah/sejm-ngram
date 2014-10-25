package org.sejmngram.server;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.sejmngram.server.cache.RedisCacheProvider;
import org.sejmngram.server.cache.RedisHitCounter;
import org.sejmngram.server.health.DatabaseHealthCheck;
import org.sejmngram.server.resources.NgramFTSResource;
import org.sejmngram.server.resources.NgramHitCountResource;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.jdbi.bundles.DBIExceptionsBundle;

public class RestApiService extends Service<RestApiConfiguration> {

	private static final Logger LOG = LoggerFactory.getLogger(RestApiService.class);

    public static void main(String[] args) throws Exception {
        new RestApiService().run(args);
    }

    @Override
    public void initialize(Bootstrap<RestApiConfiguration> bootstrap) {
        bootstrap.setName("sejm-ngram");
        
        // static assets
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        
        // unwrap any thrown SQLException or DBIException instances
        // necessary for getting full stack trace in logs
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(RestApiConfiguration config,
                    Environment environment) throws ClassNotFoundException {
        DBI jdbi = new DBIFactory().build(environment,
        		config.getDatabaseConfiguration(), "mysql");

        int dbHealthCheckTimeout = 15;
        environment.addHealthCheck(new DatabaseHealthCheck(jdbi, dbHealthCheckTimeout));
//        environment.addHealthCheck(new RedisHealthCheck(redisHostname));
        
        JedisPool jedisPool = createJedisPool(config);
        RedisHitCounter redisHitCounter = createRedisCounter(jedisPool);
        RedisCacheProvider redisCache = createRedisCacheProvider(jedisPool);
        
        environment.addResource(new NgramFTSResource(
				jdbi,
				redisHitCounter,
				redisCache,
				config.getPartiaIdFilename(),
				config.getPoselIdFilename()));

        environment.addResource(new NgramHitCountResource(redisHitCounter));

        //add filters for cors
        environment.addFilter(CrossOriginFilter.class, "/*")
                .setInitParam("allowedOrigins", "*")
                .setInitParam("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
                .setInitParam("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    }

	private JedisPool createJedisPool(RestApiConfiguration config) {
		String redisAddress = config.getRedisAddress();
		if (redisAddress == null) {
			return null;
		}
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), redisAddress);
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.configSet("maxmemory", "1000000000"); // in bytes for redis 2.8.13
			jedis.configSet("maxmemory-policy", "volatile-lru");
			jedis.configSet("maxmemory-samples", "5");
		}
		LOG.info("Successfully created jedis pool for redis access.");
		return jedisPool;
	}

	private RedisCacheProvider createRedisCacheProvider(JedisPool jedisPool) {
		if (jedisPool != null) {
			return new RedisCacheProvider(jedisPool);
		} else {
			return null;
		}
	}

	private RedisHitCounter createRedisCounter(JedisPool jedisPool) {
		if (jedisPool != null) {
			return new RedisHitCounter(jedisPool);
		} else {
			return null;
		}
	}
}
