package com.citi.sanctions.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.citi.sanctions.model.Transaction;
import com.citi.sanctions.model.UserMaster;

public interface FileUploadService {

	//Uploads file to destination folder i.e. archiving the file
	Transaction[] uploadFile(MultipartFile file) throws IllegalStateException, IOException;
	
	// checks if user present in database and password matches
	boolean checkLogin(UserMaster userObject);

}