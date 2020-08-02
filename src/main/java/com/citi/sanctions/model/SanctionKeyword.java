package com.citi.sanctions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.Table;
import javax.persistence.Id;


@Entity(name="keyword")
public class SanctionKeyword {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private int Id;
	
	@Column(name="words")
	private String words;
	
	

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	@Override
	public String toString() {
		return words;
	}
	
	

}
