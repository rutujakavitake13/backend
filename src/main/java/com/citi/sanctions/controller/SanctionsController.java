package com.citi.sanctions.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.citi.sanctions.SanctionsApplication;
import com.citi.sanctions.model.SanctionKeyword;
import com.citi.sanctions.model.Transaction;
import com.citi.sanctions.model.UserMaster;
import com.citi.sanctions.service.FileUploadService;
import com.citi.sanctions.service.ValidateKeywordService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SanctionsController {
	
	@Autowired
	FileUploadService fileUploadservice;
	
	@Autowired
	ValidateKeywordService validateService;
	private static final Logger logger = LogManager.getLogger(SanctionsApplication.class);
	
	@RequestMapping(value = "/UploadFile", method = RequestMethod.POST, produces = "application/json")
	public Transaction[] UploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		logger.info("Uplaoding File "+file.getOriginalFilename());
		Transaction[] transactions;
		transactions = fileUploadservice.uploadFile(file); 
	return transactions;
	}

	@PostMapping("/screen")
	public Transaction[] Screen(@RequestBody Transaction[] transactionsToScreen) {
		logger.info("Starting to screen applications...");
		return validateService.screenTransactions(transactionsToScreen);
	}
	
	@GetMapping("/getAllKeywords")
	public List<SanctionKeyword> GetAll() {
		logger.info("Fetching all keywords...");
		return validateService.getAllKeywords();
		
	}
	
	@PostMapping("/addKeyword")
	public boolean AddKeyWord(@RequestBody String words){
		logger.info("Adding keyword ", words);
		return validateService.addKeywords(words);
	
	}
	
	@PostMapping("/login")
	public boolean UserLogin(@RequestBody UserMaster userObject ) {
		
		logger.info("Validating login for User ", userObject.getUserId());
		return fileUploadservice.checkLogin(userObject);
		
		
	}
	
	

}