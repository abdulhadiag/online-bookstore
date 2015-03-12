package com.bookstore.util;

import java.security.MessageDigest;

public class Hasher {

	public static String getHash(String plainTextIn) {
		// Get plaintext password, and set hash algorithm
		String password = plainTextIn;
		String algorithm = "SHA-256";

		byte[] plainText = password.getBytes();  // Convert to bytes
		MessageDigest md = null;

		try { 
		    md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		md.reset(); 
		md.update(plainText);                   // Add passwd bytes to digester
		byte[] encodedPassword = md.digest();   // Hash the passwd
		
		// Iterate over the result and build a string representation
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < encodedPassword.length; i++) {
		    if ((encodedPassword[i] & 0xff) < 0x10) {
		        sb.append("0");
		    }
		
		    sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		
		return sb.toString();
	}
}
