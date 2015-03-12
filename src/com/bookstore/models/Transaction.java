package com.bookstore.models;

import java.io.Serializable;

public class Transaction implements Serializable {
	private int transactionId;
	private String username;
	private String purchaseDate;
	private double totalPrice;
	private int isProcessed;
	
	public Transaction(int transactionId, String username, String purchaseDate, double totalPrice,
			int isProcessed) {
		this.transactionId = transactionId;
		this.username = username;
		this.purchaseDate = purchaseDate;
		this.totalPrice = totalPrice;
		this.isProcessed = isProcessed;
	}
	
	public Transaction() {
		this.transactionId = 0;
		this.username = "";
		this.purchaseDate = "";
		this.totalPrice = 0.0;
	}

	// SET funcs ------------------------------------------------------//
	public void setTransactionId(int transaction_id) {
		this.transactionId = transaction_id;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public void setPurchaseDate(String purchase_date) {
		this.purchaseDate = purchase_date;
	}

	public void setTotalPrice(double total_price) {
		this.totalPrice = total_price;
	}
	
	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}

	// GET funcs ------------------------------------------------------//
	public int getTransactionId() {
		return transactionId;
	}

	public String getUserName() {
		return username;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public int getIsProcessed() {
		return isProcessed;
	}
}