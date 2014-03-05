package com.example.helloworld.factory;

import java.util.ArrayList;
import java.util.List;

import org.sejmngram.database.fetcher.connection.DbConnector;
import org.sejmngram.database.fetcher.json.datamodel.ListDate;
import org.sejmngram.database.fetcher.json.datamodel.NgramResponse;
import org.sejmngram.database.fetcher.json.datamodel.PartiesNgrams;

public class NgramFactory {

	private DbConnector db; 
	
	public NgramFactory(DbConnector dbConnector) {
		this.db = dbConnector;
		this.db.connect();
	}
	
	public NgramResponse generateDefaultNgramResponse() {
		List<ListDate> listDates = new ArrayList<ListDate>();
		String ngramName = "domyślnowyraz";
		
		listDates.add(new ListDate("2011-02-08", 21));
		listDates.add(new ListDate("2011-02-09", 123));
		listDates.add(new ListDate("2011-02-08", 56));
		
		List<PartiesNgrams> partiesNgrams = new ArrayList<PartiesNgrams>();
		partiesNgrams.add(new PartiesNgrams("Paaartiaa", listDates));
		return new NgramResponse(ngramName, partiesNgrams);
	}

	public NgramResponse generateNgramResponse(String ngramName) {
		int partyId = 10;
		return db.retrieve(ngramName, null, null, partyId);
	}
}
