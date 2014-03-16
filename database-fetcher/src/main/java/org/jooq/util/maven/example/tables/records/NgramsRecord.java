/**
 * This class is generated by jOOQ
 */
package org.jooq.util.maven.example.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NgramsRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.util.maven.example.tables.records.NgramsRecord> implements org.jooq.Record6<java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.Integer, byte[]> {

	private static final long serialVersionUID = 2095900513;

	/**
	 * Setter for <code>sejmngram.ngrams.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>sejmngram.ngrams.datefrom</code>.
	 */
	public void setDatefrom(java.sql.Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.datefrom</code>.
	 */
	public java.sql.Timestamp getDatefrom() {
		return (java.sql.Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>sejmngram.ngrams.dateto</code>.
	 */
	public void setDateto(java.sql.Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.dateto</code>.
	 */
	public java.sql.Timestamp getDateto() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>sejmngram.ngrams.ngram</code>.
	 */
	public void setNgram(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.ngram</code>.
	 */
	public java.lang.String getNgram() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>sejmngram.ngrams.nrOccurences</code>.
	 */
	public void setNroccurences(java.lang.Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.nrOccurences</code>.
	 */
	public java.lang.Integer getNroccurences() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>sejmngram.ngrams.content</code>.
	 */
	public void setContent(byte[] value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>sejmngram.ngrams.content</code>.
	 */
	public byte[] getContent() {
		return (byte[]) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.Integer, byte[]> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.Integer, byte[]> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field2() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.DATEFROM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.DATETO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.NGRAM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.NROCCURENCES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<byte[]> field6() {
		return org.jooq.util.maven.example.tables.Ngrams.NGRAMS.CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value2() {
		return getDatefrom();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getDateto();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getNgram();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getNroccurences();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value6() {
		return getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value2(java.sql.Timestamp value) {
		setDatefrom(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value3(java.sql.Timestamp value) {
		setDateto(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value4(java.lang.String value) {
		setNgram(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value5(java.lang.Integer value) {
		setNroccurences(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord value6(byte[] value) {
		setContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NgramsRecord values(java.lang.Integer value1, java.sql.Timestamp value2, java.sql.Timestamp value3, java.lang.String value4, java.lang.Integer value5, byte[] value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached NgramsRecord
	 */
	public NgramsRecord() {
		super(org.jooq.util.maven.example.tables.Ngrams.NGRAMS);
	}

	/**
	 * Create a detached, initialised NgramsRecord
	 */
	public NgramsRecord(java.lang.Integer id, java.sql.Timestamp datefrom, java.sql.Timestamp dateto, java.lang.String ngram, java.lang.Integer nroccurences, byte[] content) {
		super(org.jooq.util.maven.example.tables.Ngrams.NGRAMS);

		setValue(0, id);
		setValue(1, datefrom);
		setValue(2, dateto);
		setValue(3, ngram);
		setValue(4, nroccurences);
		setValue(5, content);
	}
}
