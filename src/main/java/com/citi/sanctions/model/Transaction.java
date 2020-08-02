package com.citi.sanctions.model;


public class Transaction {
	
	public String transRef;

	public String date;

	public String payerName;

	public String payerAcc;

	public String payeeName;

	public String payeeAcc;

	public String amount;

	public String status;
	
	

	public String getTransRef() {
		return transRef;
	}



	public void setTransRef(String transRef) {
		this.transRef = transRef;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getPayerName() {
		return payerName;
	}



	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}



	public String getPayerAcc() {
		return payerAcc;
	}



	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}



	public String getPayeeName() {
		return payeeName;
	}



	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}



	public String getPayeeAcc() {
		return payeeAcc;
	}



	public void setPayeeAcc(String payeeAcc) {
		this.payeeAcc = payeeAcc;
	}



	public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "Transaction transRef=" + transRef + ", date=" + date + ", payerName=" + payerName
				+ ", payerAcc=" + payerAcc + ", payeeName=" + payeeName + ", payeeAcc=" + payeeAcc + ", amount="
				+ amount + ", status=" + status + "]";
	}
		
	
	

}
