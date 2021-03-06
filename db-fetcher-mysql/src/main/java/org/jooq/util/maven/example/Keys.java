/**
 * This class is generated by jOOQ
 */
package org.jooq.util.maven.example;

/**
 * This class is generated by jOOQ.
 *
 * A class modelling foreign key relationships between tables of the <code>sejmngram</code> 
 * schema
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<org.jooq.util.maven.example.tables.records.NgramsRecord, java.lang.Integer> IDENTITY_NGRAMS = Identities0.IDENTITY_NGRAMS;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<org.jooq.util.maven.example.tables.records.NgramsRecord> KEY_NGRAMS_PRIMARY = UniqueKeys0.KEY_NGRAMS_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<org.jooq.util.maven.example.tables.records.NgramsRecord, java.lang.Integer> IDENTITY_NGRAMS = createIdentity(org.jooq.util.maven.example.tables.Ngrams.NGRAMS, org.jooq.util.maven.example.tables.Ngrams.NGRAMS.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<org.jooq.util.maven.example.tables.records.NgramsRecord> KEY_NGRAMS_PRIMARY = createUniqueKey(org.jooq.util.maven.example.tables.Ngrams.NGRAMS, org.jooq.util.maven.example.tables.Ngrams.NGRAMS.ID);
	}
}
