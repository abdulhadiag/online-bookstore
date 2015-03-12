package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.Review;

public class ReviewDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public ReviewDB() {
		this.connPool = setDBConnection();
	}
	
	/**
	 * Sets this instances DBConnector pool using internal credentials.
	 * @return the DBConnection pool upon successful connection to db.
	 */
	public DBConnectionPool setDBConnection() {
		try {
			connPool = new DBConnectionPool(new String(db_url), new String(db_username), new String(db_passwd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connPool;
	}
	
	/**
	 * Retrieve a model for a single Review
	 * @param username case-sensitive String containing username
	 * @param isbn 
	 * @return model of a review
	 */
	public Review selectReview(String username, String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		Review review = new Review();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select username, isbn, rating, review_text "
						+ "from rating where username = '" + username + "' and isbn = '" + isbn + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					review.setUserName(rs.getString(1));
					review.setISBN(rs.getString(2));
					review.setRating(rs.getInt(3));
					review.setReviewText(rs.getString(4));
				}
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return review;
	}
	
	/**
	 * Creates a database entry for a new review.
	 * @param review A fully populated Review object 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int registerReview(Review review) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into rating (username, isbn, rating, review_text"
						+ ") values('"
				        + review.getUserName() + "', '" 
				        + review.getISBN() + "', '"
				        + review.getRating() + "', '" 
						+ review.getReviewText() + "')";
				System.out.println(strQuery);
				resultNo = stmt.executeUpdate(strQuery);
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return resultNo;
	}
	
	/**
	 * Update an existing review. To update a review, retrieve their review object
	 * using selectReview() or selectReviews(), edit the review's fields, and then
	 * send it back to this method.
	 * @param review An updated review object
	 * @return The number of affected rows. (1 for success. 0 for error)
	 */
	public int updateReview(Review review) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "update rating set "
				+ "rating='" + review.getRating() 
				+ "', review_text='" + review.getReviewText() 
				+ "' where username='" + review.getUserName() + "' and isbn='" + review.getISBN() + "'";
				resultNo = stmt.executeUpdate(strQuery);
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return resultNo;
	}
	
	/**
	 * Deletes a review from the database. 
	 * @param username case-sensitive String representation of the username
	 * @param isbn case-sensitive String representation of the isbn
	 * @return The number of affected rows. (1 for success. 0 for error) 
	 */
	public int deleteReview(String username, String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "delete from rating where username = '" + username + "' and isbn='" + isbn + "'";
				resultNo = stmt.executeUpdate(strQuery);
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return resultNo;
	}
	
	/**
	 * Returns a list of all ratings in the system. May be useful for running reports
	 * of for user administration by staff
	 * @return A list of all ratings in the system.
	 */
	public ArrayList<Review> selectReviews() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select username, isbn, rating, review_text, "
						+ "from rating";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Review r = new Review();
					r.setUserName(rs.getString(1));
					r.setISBN(rs.getString(2));
					r.setRating(rs.getInt(3));
					r.setReviewText(rs.getString(4));
					reviews.add(r);
				}
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return reviews;
	}
	
	/**
	 * Returns a list of all ratings in the system. May be useful for running reports
	 * of for user administration by staff
	 * @return A list of all ratings in the system.
	 */
	public ArrayList<Review> selectReviewsFromUser(String username) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select username, isbn, rating, review_text "
						+ "from rating where username='" + username + "'";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Review r = new Review();
					r.setUserName(rs.getString(1));
					r.setISBN(rs.getString(2));
					r.setRating(rs.getInt(3));
					r.setReviewText(rs.getString(4));
					reviews.add(r);
				}
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return reviews;
	}
	
	/**
	 * Returns a list of all ratings for a book. 
	 * @return A list of all ratings in the system for a book.
	 */
	public ArrayList<Review> selectBookReviews(String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Review> reviews = new ArrayList<Review>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select username, rating, review_text, "
						+ "from rating where isbn = '" + isbn + "'";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Review r = new Review();
					r.setUserName(rs.getString(1));
					r.setRating(rs.getInt(2));
					r.setReviewText(rs.getString(3));
					reviews.add(r);
				}
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					connPool.returnConnection(conn);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
			
		return reviews;
	}
	
	public float selectReviewScore(String isbn) {
	Statement stmt = null;
	ResultSet rs = null;
	float num = 0;
	float sum = 0;
	float avg = 0;
	Connection conn = null;
	try {
		conn = connPool.getConnection();
		
		if(conn != null) {
			stmt = conn.createStatement();
			
			String strQuery = "select count(rating) as count, sum(rating) as sum from rating where isbn = '" + isbn + "'";
			rs = stmt.executeQuery(strQuery);
			if (rs.next()) {
				num = rs.getInt(1);
				System.out.println(num);
				sum = rs.getInt(2);
				System.out.println(sum);
			}
		}
	} catch (SQLException e) {
		for(Throwable t: e) {
			t.printStackTrace();
		}
	} catch (Exception et) {
		et.printStackTrace();
	} finally {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				connPool.returnConnection(conn);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	avg = sum / num; 
	System.out.println(avg);
	return avg;
	}
}
