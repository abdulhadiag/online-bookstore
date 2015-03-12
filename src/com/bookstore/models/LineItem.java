package com.bookstore.models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bookstore.db.BookDB;

public class LineItem implements Serializable {
	private int lineItemId;
	private int transactionId;
	private String isbn;
	private int quantity;
	private String title;
	
	
	
	public LineItem(int lineItemId, int transactionId, String isbn, int quantity, String title) {
		this.lineItemId = lineItemId;
		this.transactionId = transactionId;
		this.isbn = isbn;
		this.quantity = quantity;
		this.title = title;
	}

	public LineItem() {
		this.lineItemId = 0;
		this.transactionId = 0;
		this.isbn = "";
		this.quantity = 0;
		this.title = "";
	}

	public int getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPrice() {
		double price = new BookDB().selectBook(isbn).getPrice();
		NumberFormat dm = NumberFormat.getCurrencyInstance();
		return dm.format(price);
	}
	
	public String getTotalPrice() {
		double price = this.quantity * new BookDB().selectBook(isbn).getPrice();
		NumberFormat dm = NumberFormat.getCurrencyInstance();
		return dm.format(price);
	}
	
	public double getTotalPriceDouble() {
		return this.quantity * new BookDB().selectBook(isbn).getPrice();
	}
	
	public boolean enoughInventory() {
		boolean retVal = true;
		if (quantity > new BookDB().selectBook(isbn).getInventory()) {
			retVal = false;
		}
		return retVal;
	}
	
	public void commitInventoryDecrement() {
		BookDB bookDb = new BookDB();
		Book book = bookDb.selectBook(isbn);
		book.setInventory(book.getInventory() - quantity);
		bookDb.updateBook(book);
	}
}