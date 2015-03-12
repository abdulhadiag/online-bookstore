package com.bookstore;

import java.util.ArrayList;
import java.util.HashMap;

import com.bookstore.models.Genre;

public class AddGenre {
	
	public static ArrayList<Genre> populateGenre(HashMap<String, String> map) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		Genre genre1 = new Genre();
		Genre genre2 = new Genre();
		
		String g1 = map.get("genre1");
		String g2 = map.get("genre2");
		System.out.println("g1: " + g1);
		System.out.println("g2: " + g2);
		
		if (!g1.equals("")){
			genre1.setGenreName(g1);
			genres.add(genre1);
			System.out.println(genre1.getGenreName());
			System.out.println("adding g1");
		}
		if (!g2.equals("")){
			genre2.setGenreName(g2);
			genres.add(genre2);
			System.out.println(genre2.getGenreName());
			System.out.println("adding g2");
		}
		return genres;
	}
}
