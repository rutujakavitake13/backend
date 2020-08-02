package com.citi.sanctions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citi.sanctions.model.UserMaster;


public interface UserMasterRepository extends JpaRepository<UserMaster, String>{

	UserMaster findUserMasterByUserId(String userId);

}
