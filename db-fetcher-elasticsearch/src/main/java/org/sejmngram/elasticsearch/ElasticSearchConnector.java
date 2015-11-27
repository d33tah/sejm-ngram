package org.sejmngram.elasticsearch;

import org.apache.commons.lang.NotImplementedException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.sejmngram.database.fetcher.connection.NgramDbConnector;
import org.sejmngram.database.fetcher.json.datamodel.NgramResponse;
import org.sejmngram.database.fetcher.json.datamodel.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.sejmngram.database.fetcher.json.datamodel.ResponseBuilder.emptyResponse;

public class ElasticSearchConnector extends AbstractElasticSearchConnector implements NgramDbConnector {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConnector.class);

    private static final int RESULT_SIZE_LIMIT = 1000 * 1000;

    private final String index;
    private Map<String, String> partiesIdMap;
    private Set<String> dates = new HashSet<String>();

    public ElasticSearchConnector(Client client, String index) {
        super(client);
        this.index = index;
        this.partiesIdMap = new HashMap<String, String>();
    }

    public ElasticSearchConnector(Client client, String index,
                                  Set<String> providedDates, Map<String, String> partiesIdMap) {
        this(client, index);
        this.partiesIdMap = partiesIdMap;
        this.dates = Collections.unmodifiableSet(providedDates);
    }

    @Override
    public NgramResponse retrieve(String ngram) {

        // TODO sanitize input
        ngram = normalizeNgram(ngram);
        LOG.trace("Received ngram request: " + ngram);
        LOG.trace("Querying ElasticSearch...");

        SearchResponse esSearchResponse = queryElasticSearch(ngram);

        NgramResponse ngramResponse;
        if (isContainingAnyResults(esSearchResponse)) {
            logResponse(esSearchResponse);

            ngramResponse = createResponseFor1Word(ngram, esSearchResponse, ngram.split(" ").length > 1);
        } else {
            LOG.trace("Does not contain any results, returning empty response");
            ngramResponse = emptyResponse(ngram, dates);
        }
        LOG.trace("Finished processing.");
        return ngramResponse;
    }

    private void logResponse(SearchResponse esSearchResponse) {
        LOG.trace("There are " + esSearchResponse.getHits().getTotalHits() + " search hits");
        LOG.trace("Here is first 5:");

        //logging out first 5 SearchHits
        long showNrHits = 5;
        long totalHits = esSearchResponse.getHits().getTotalHits();
        if (totalHits < showNrHits) {
            showNrHits = totalHits;
        }

        for (int i = 0; i < showNrHits; i++) {
            SearchHit sH = esSearchResponse.getHits().getAt(i);
            String fieldString = "";
            for (String field : sH.fields().keySet()) {
                fieldString += field + " : " + sH.fields().get(field).getValue() + "\n";
            }
            LOG.trace(fieldString);
        }
    }

    private NgramResponse createResponseFor1Word(String ngram, SearchResponse esSearchResponse, boolean isMoreThan1Word) {
        ResponseBuilder responseBuilder = new ResponseBuilder(ngram, dates);
        // TODO histogram result size? https://dropwizard.github.io/metrics/3.1.0/manual/core/#histograms
        if (esSearchResponse.getHits().getTotalHits() >= RESULT_SIZE_LIMIT) {
            LOG.warn("Maximum number of elasticsearch search hits reached: " + RESULT_SIZE_LIMIT
                    + ". Possibly not receiving all available results from elasticsearch.");
        }
        for (SearchHit searchHit : esSearchResponse.getHits()) {
            int termCount = isMoreThan1Word ? calculatePhraseCount(ngram, (String) searchHit.field(TEXT_FIELD).getValue()) : (int) searchHit.field(TERM_COUNT).getValue();
            String partyName = lookupPartyName(searchHit);
            String date = searchHit.field(DATE_FIELD).getValue();
            responseBuilder.addOccurances(partyName, date, termCount);
        }
        return responseBuilder.generateResponse();
    }

    private int calculatePhraseCount(String ngram, String source) {
        int i = 0;
        Pattern p = Pattern.compile(ngram, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        while (m.find()) {
            i++;
        }

        return i;
    }

    private String lookupPartyName(SearchHit searchHit) {
        SearchHitField field = searchHit.field(PARTY_FIELD);
        if (field == null) {
            return "no party";
        }

        String partyIdString = field.getValue();
        String partyName = partiesIdMap.get(partyIdString);
        if (partyName == null)
            return partyIdString;
        else return partyName;
    }

    private String normalizeNgram(String ngram) {
        return ngram != null ? ngram.toLowerCase() : null;
    }

    private boolean isContainingAnyResults(SearchResponse searchResponse) {
        return searchResponse != null
                && searchResponse.getHits() != null
                && searchResponse.getHits().getHits() != null
                && searchResponse.getHits().getHits().length > 0;
    }

    private String createCountScript(String ngram) {
        return "_index['" + TEXT_FIELD + "']['" + ngram + "'].tf()";
    }

    @Override
    public NgramResponse retrieveByParty(String ngram, int partyId) {
        throw new NotImplementedException();
    }

    @Override
    public NgramResponse retrieveByPosel(String ngram, int poselId) {
        throw new NotImplementedException();
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {
        client.close();
    }

    @Override
    protected SearchRequestBuilder buildQuery(SearchRequestBuilder searchRequestBuilder, String phrase) {
        SearchRequestBuilder updatedSearchRequestBuilder = searchRequestBuilder
                .setSize(RESULT_SIZE_LIMIT)
                .addField(DATE_FIELD)
                .addField(PARTY_FIELD)
                .addField(TEXT_FIELD)
                .addField(ID_FIELD);

        if (phrase.split(" ").length == 1) {
            updatedSearchRequestBuilder.addScriptField(TERM_COUNT, createCountScript(phrase));
        }

        return updatedSearchRequestBuilder;
    }

    @Override
    protected String getIndex() {
        return index;
    }

}
