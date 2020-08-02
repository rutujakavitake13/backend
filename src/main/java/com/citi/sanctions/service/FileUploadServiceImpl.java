package com.citi.sanctions.service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.citi.sanctions.SanctionsApplication;
import com.citi.sanctions.model.Transaction;
import com.citi.sanctions.model.UserMaster;
import com.citi.sanctions.repository.UserMasterRepository;


@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	ValidateKeywordService validateService;
	
	@Autowired
	UserMasterRepository userMasterRepository;
	
	private static final Logger logger = LogManager.getLogger(SanctionsApplication.class);
	
	/* (non-Javadoc)
	 * Function to upload file
	 */
	@Override
	public Transaction[] uploadFile(MultipartFile file) throws IllegalStateException, IOException {
		
		long fileName = new Date().getTime();
		String FileName= "C:\\Users\\Rutuja Kavitake\\git\\backend\\backend\\ArchivedFile\\"+fileName+"_"+file.getOriginalFilename();
		logger.info("File Uploaded to "+FileName);
		file.transferTo(new File(FileName));
		return validateService.Readcsv(FileName);
	}

	@Override
	public boolean checkLogin(UserMaster userObject) {
		
		String password = decodeString(userObject.getPassword());
		UserMaster temp = userMasterRepository.findUserMasterByUserId(userObject.getUserId());
		
		if(temp != null) {
			logger.info("User found in database");
		}
		else {
			logger.info("User not found in databae" );
		}
		
		if(decodeString(temp.getPassword()).equals(password)) {
			logger.info("User Login Successful");
			return true;
		}
		logger.info("User Login UnsSuccessful");
		return false;
	}

	private String decodeString(String encodedPassword) {
		
		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password = password.reverse();
		return password.toString();
	}
	
}
