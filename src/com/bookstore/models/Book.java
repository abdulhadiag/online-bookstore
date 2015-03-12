package com.bookstore.models;

import java.io.Serializable;
import java.util.ArrayList;

import com.bookstore.db.AuthorDB;
import com.bookstore.db.GenreDB;
import com.bookstore.db.ReviewDB;

public class Book implements Serializable {
	private String isbn;
	private String title;
	private String publisher;
	private double price;
	private String description;
	private String publishDate;
	private String coverImageFile;
	private int inventory;
	
	public Book(String isbn, String title, String publisher, double price,
			String description, String publishDate, String coverImageFile,
			int inventory) {
		this.isbn = isbn;
		this.title = title;
		this.publisher = publisher;
		this.price = price;
		this.description = description;
		this.publishDate = publishDate;
		this.coverImageFile = coverImageFile;
		this.inventory = inventory;
	}
	
	public Book() {
		this.isbn = "";
		this.title = "";
		this.publisher = "";
		this.price = 0.0;
		this.description = "";
		this.publishDate = "";
		this.coverImageFile = "";
		this.inventory = 0;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCoverImageFile() {
		return coverImageFile;
	}

	public void setCoverImageFile(String coverImageFile) {
		this.coverImageFile = coverImageFile;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public String getAuthors() {
		String authorNames = "";

		ArrayList<Author> authors = new AuthorDB().searchForAuthors(this.isbn);
		authorNames = authors.get(0).getAuthorName();
		for (int i = 1; i < authors.size(); i++){
			authorNames += "; " + authors.get(i).getAuthorName();
		}
		return authorNames;
	}
	public String getGenres() {
		String genreNames = "";

		ArrayList<Genre> genres = new GenreDB().searchForGenres(this.isbn);
		genreNames = genres.get(0).getGenreName();
		for (int i = 1; i < genres.size(); i++){
			genreNames += "; " + genres.get(i).getGenreName();
		}
		return genreNames;
	}
	
	public float getRating() {
		float score = 0;
		score = new ReviewDB().selectReviewScore(this.isbn);
		
		return score;
	}
}
