package com.citi.sanctions.service;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.sanctions.SanctionsApplication;
import com.citi.sanctions.model.SanctionKeyword;
import com.citi.sanctions.model.Status;
import com.citi.sanctions.model.Transaction;
import com.citi.sanctions.repository.keywordRepository;


@Service
public class ValidateKeywordServiceImpl implements ValidateKeywordService {
	
	@Autowired
	keywordRepository keywordrepository;
	
	private static final Logger logger = LogManager.getLogger(SanctionsApplication.class);

	
	/* (non-Javadoc)
	 * @see com.citi.sanctions.service.ValidateKeywordService#Readcsv(java.lang.String)
	 */
	@Override
	public Transaction[] Readcsv(String FileName) throws IOException 
	{
		final String Name = FileName;
		logger.info("Uploaded File at location "+FileName+" is been read");
		logger.info("Validation check starting for file at location "+FileName);
		
		try
		{
			
			FileInputStream file = new FileInputStream(new File(Name)); 
			
			Workbook workbook = new XSSFWorkbook(file); 				
			
			DataFormatter dataformatter = new DataFormatter();
			
			Iterator<Sheet> sheets = workbook.sheetIterator();			
			
			int rowTotal = workbook.getSheetAt(workbook.getActiveSheetIndex()).getLastRowNum(); 
			
			Transaction[] transactions = new Transaction[rowTotal+1];		
			
			
			while(sheets.hasNext())											
			{
				Sheet sh =  sheets.next();
				int rowCount = -1;
				int sheet_number = workbook.getActiveSheetIndex();
				Iterator<Row> iterator = sh.iterator();	
				
				logger.info("Sheet "+workbook.getSheetName(sheet_number)+" is been read");
				
				while(iterator.hasNext()) 								
				{
					Status status1 = Status.VALIDATE_PASS;
					String status = status1.toString();
					String comments= " ";
					int count = 0;										
					rowCount++ ;
					boolean flag = true;	
					
					Row row = iterator.next();
					transactions[rowCount] = new Transaction();
					boolean row_check = true;
					Iterator<Cell> cellIterator = row.iterator();		
					
					while(cellIterator.hasNext()) 
					{
						
						count += 1;									
						
						Cell cell = cellIterator.next();
						
						String cellValue = dataformatter.formatCellValue(cell);
						
						if(cellValue == null) {
							status1 = Status.VALIDATE_FAIL;
							status = status1.toString();
							transactions[rowCount].setStatus(status);
							logger.warn("Value read is null; Status changed to Validate Fail");
							continue;
						}
						
						if(status1 == Status.VALIDATE_FAIL) {
							if(count == 2) {
								transactions[rowCount].setDate(cellValue);
							}
							if(count == 3) {
								transactions[rowCount].setPayerName(cellValue);
							}
							if(count == 4) {
								transactions[rowCount].setPayerAcc(cellValue);
							}
							if(count == 5) {
								transactions[rowCount].setPayeeName(cellValue);
							}
							if(count == 6) {
								transactions[rowCount].setPayeeAcc(cellValue);
							}
							if(count == 7) {
								transactions[rowCount].setAmount(cellValue);
							}
							if(row_check == true) 
							{
							logger.warn("Status already Validate Fail: Entry skipped at value "+cellValue);
							row_check=false;
							}
							continue;
						}
						if(count == 1)
						{
							transactions[rowCount].setTransRef(cellValue);
							
							if(isAlphanumeric(cellValue) == false)
							{
								flag = false;
								comments = comments +" Transaction Ref# not alphanumeric";
								
							}
							
						}
						
						if(count == 2)
						{
							transactions[rowCount].setDate(cellValue);
							
							if(isSameDay(cellValue) == false) 
							{
								flag = false;
								comments = comments +", date is not current";
								
							}
						}
						if(count == 3)
						{
							transactions[rowCount].setPayerName(cellValue);
							
							if(isAlphanumeric(cellValue) == false)
							{
								flag = false;
								comments = comments +", Payer Name is not alphanumeric";
								
							}
						}
						if(count == 4)
						{
							transactions[rowCount].setPayerAcc(cellValue);
							if(isAlphanumeric(cellValue) == false) 
							{
								flag = false;
								comments = comments +", Payer Account# is not alphanumeric";
							}
						}
						if(count == 5)
						{
							transactions[rowCount].setPayeeName(cellValue);
							
							if(isAlphanumeric(cellValue) == false) 
							{
								flag = false;
								comments = comments +", Payee Name is not alphanumeric";
							}
						}
						if(count == 6)
						{
							transactions[rowCount].setPayeeAcc(cellValue);
							
							if(isAlphanumeric(cellValue) == false) 
							{
								flag = false;
								comments = comments +", Payee Account# is not alphanumeric";
								
							}
						}
						if(count == 7)
						{
							transactions[rowCount].setAmount(cellValue);
							
							if(isRight(cellValue.toString()) == false) {
								flag = false;
								comments = comments +", amount is not in valid";
							}
						}
						
						if(flag == false)
						{
							status1 = Status.VALIDATE_FAIL;
							status = status1.toString();
							logger.warn("Validate Failed: "+cellValue+" --> "+comments);
						}
						transactions[rowCount].setStatus(status);	
						
					}	
				}
			}
			return transactions;	
		}
		
		catch(Exception e)
		{
				e.printStackTrace();
				return null;
		}
		
	}
	
	/* (non-Javadoc)
	 * Screens transactions (Payer Name, Payee name for sanctions
	 * Fetches list of keywords
	 */
	@Override
	public Transaction[] screenTransactions(Transaction[] transactionsToScreen) throws NoSuchElementException{
		
		logger.info("Sending transactions for screening.");
		
		int indx = 0;
		List<SanctionKeyword> list_keyword = keywordrepository.findAll();
		String[] key_words = new String[list_keyword.size()];
		
		while(indx<=(list_keyword.size()-1)) 
		{
			key_words[indx] = list_keyword.get(indx).getWords();
			indx++;				
		}
		
		logger.info("Retrieving Sanctioned Words");
		
		return checkScreening(key_words, transactionsToScreen);
		
	}
	
	/* (non-Javadoc)
	 * 
	 * Screens transactions against a set of keywords
	 */
	@Override
	public Transaction[] checkScreening(String[] key_words, Transaction[] transactions) 
	{
		logger.info("Checking and Uploading Transaction Screening Status");
		
		for(int j=0;j<transactions.length;j++) 
		{
			
			String newStatus =  Status.SCREEN_PASS.toString();
			
			for(int i=0; i<key_words.length;i++) 	
			{
				
				String word = key_words[i];
				if(transactions[j].status.equals(Status.VALIDATE_PASS.toString())) 
				{
					String trans_check = transactions[j].getPayerName()+transactions[j].getPayeeName();
					if(trans_check.contains(word)) {
						newStatus = Status.SCREEN_FAIL.toString();
						break;
					}	
				}	
			}
			transactions[j].setStatus(newStatus);
		}
		logger.info("Trasanctions Screened and Status updated");
		
	return transactions;
	}
	
	@Override
	public List<SanctionKeyword> getAllKeywords() {
		return keywordrepository.findAll();
	}

	@Override
	public boolean addKeywords(String word) {
		
		SanctionKeyword keyword = new SanctionKeyword();
		keyword.setWords(word);
		SanctionKeyword s = keywordrepository.saveAndFlush(keyword);
		logger.info("Added word ", s.getWords()+" to list of sanctioned words");
		return s == null ? false : true;
	}
	
	
	//Transaction Reference number, Payer Name, Payer Account Number, Payee Name, Payee Account Number must be alphanumeric string
	boolean isAlphanumeric(String s)
	{
		boolean ans = s.matches("[A-Za-z0-9]+");
		return ans;
	}
	
	
	// date of transactions should be current day
	public static boolean isSameDay(String date) throws IllegalArgumentException, ParseException 
	{
	    LocalDateTime ldt = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String fdt = ldt.format(formatter);
	    return (date).equals(fdt);
	}
	
	// amount should either be an integer or a floating point with upto 2 decimal places and 10 integer bits
	public static boolean isRight(String text) 
	{
	    if(text.contains(".")) 
	    {
	    	String[] amountData = text.split("\\.");
	    	return amountData[0].length()<=10 && amountData[1].length() <= 2 ? true : false;
	    } 
	    else { return text.length() <= 10 ? true : false; }	
	}

}