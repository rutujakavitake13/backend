package com.citi.sanctions.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.citi.sanctions.model.SanctionKeyword;
import com.citi.sanctions.model.Transaction;

public interface ValidateKeywordService {

	//Validating uploaded file
	Transaction[] Readcsv(String FileName) throws IOException;

	//Fetching keywords and sending transactions for screening
	Transaction[] screenTransactions(Transaction[] transactionsToScreen) throws NoSuchElementException;

	//Screening Transactions and updating status
	Transaction[] checkScreening(String[] key_words, Transaction[] transactions);
	
	//Fetching all keywords from database
	List<SanctionKeyword> getAllKeywords();
	
	//Adding keyword to database
	boolean addKeywords(String word);

}