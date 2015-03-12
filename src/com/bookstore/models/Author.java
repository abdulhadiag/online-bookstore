package com.bookstore.models;

import java.io.Serializable;

public class Author implements Serializable {
	private int authorId;
	private String authorName;
	
	public Author() {
		this.authorId = 0;
		this.authorName = "";
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}	
}
