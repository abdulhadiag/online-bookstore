package com.bookstore.models;

import java.io.Serializable;

public class Genre implements Serializable {
	private String genreName;
	
	public Genre() {
		this.genreName = "";
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
}
