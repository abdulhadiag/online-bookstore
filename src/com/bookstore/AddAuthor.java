package com.bookstore;

import java.util.ArrayList;
import java.util.HashMap;

import com.bookstore.models.Author;

public class AddAuthor {
	
	public static ArrayList<Author> populateAuthor(HashMap<String, String> map) {
		ArrayList<Author> authors = new ArrayList<Author>();
		Author author1 = new Author();
		Author author2 = new Author();
		Author author3 = new Author();
		
		String a1 = map.get("author1");
		String a2 = map.get("author2");
		String a3 = map.get("author3");
		System.out.println("author1: " + a1);
		System.out.println("author2: " + a2);
		System.out.println("author3: " + a3);
		
		if (!a1.equals("")){
			author1.setAuthorName(a1);
			authors.add(author1);
			System.out.println(author1.getAuthorName());
			System.out.println("adding a1");


		}
		if (!a2.equals("")){
			author2.setAuthorName(a2);
			authors.add(author2);
			System.out.println(author2.getAuthorName());
			System.out.println("adding a2");
		}
		if (!a3.equals("")){
			author3.setAuthorName(a3);
			authors.add(author3);
			System.out.println("adding a3");
		}
		return authors;
	}
}
