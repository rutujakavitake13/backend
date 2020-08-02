package com.citi.sanctions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.citi.sanctions.model.SanctionKeyword;

public interface keywordRepository extends JpaRepository<SanctionKeyword, Integer>{
	
	List<SanctionKeyword> findAll();

}
