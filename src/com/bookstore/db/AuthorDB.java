package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.Author;

public class AuthorDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public AuthorDB() {
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
	 * Retrieve a model for a single Author
	 * @param username case-sensitive String containing username
	 * @param isbn 
	 * @return model of an author
	 */
	public Author selectAuthor(String name) {
		Statement stmt = null;
		ResultSet rs = null;
		Author author = new Author();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select author_id, author_name"
						+ " from author where author_name = '" + name + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					author.setAuthorId(rs.getInt(1));
					author.setAuthorName(rs.getString(2));		
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
		return author;
	}
	
	/**
	 * Creates a database entry for a new author.
	 * @param author A fully populated Author object 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int registerAuthor(Author author) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into author (author_name"
						+ ") values('"
				        + author.getAuthorName() + "')";
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
	 * Creates a database entry for a new author.
	 * @param author A fully populated Author object 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int registerBookAuthor(Author author, String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into book_author (isbn, author_id"
						+ ") values('" + isbn + "','"
				        + author.getAuthorId() + "')";
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
	 * Update an existing author. To update a author, retrieve their author object
	 * using selectAuthor() or selectAuthors(), edit the author's fields, and then
	 * send it back to this method.
	 * @param author An updated author object
	 * @return The number of affected rows. (1 for success. 0 for error)
	 */
	public int updateAuthor(Author author) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "update author set "
				+ "author_name='" + author.getAuthorName()  
				+ "' where author_id='" + author.getAuthorId() + "'";
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
	 * Deletes a author from the database. 
	 * @param username case-sensitive String representation of the username
	 * @param isbn case-sensitive String representation of the isbn
	 * @return The number of affected rows. (1 for success. 0 for error) 
	 */
	public int deleteAuthor(String name) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "delete from author where author_name = '" + name + "'";
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
	public ArrayList<Author> selectAuthors() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Author> authors = new ArrayList<Author>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select author_id, author_name "
						+ "from author";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Author r = new Author();
					r.setAuthorName(rs.getString(1));
					authors.add(r);
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
			
		return authors;
	}

	public int checkAndAdd(ArrayList<Author> authors, String isbn) {
		Author author = new Author();
		String name = "";
		int newResult = 0;
		int regResult = 0;
		for (int i = 0; i < authors.size(); i++){
			name = authors.get(i).getAuthorName();
			System.out.println(authors.get(i).getAuthorName());
			if (!name.equals("")){
				author = selectAuthor(name);
				System.out.println(author.getAuthorName());
				System.out.println(author.getAuthorId());
				if(author.getAuthorId() == 0){
					author.setAuthorName(name);
					newResult += registerAuthor(author);
				}
				author = selectAuthor(name);
				System.out.println(author.getAuthorName());
				System.out.println(author.getAuthorId());
				regResult += registerBookAuthor(author, isbn);
			}
		}
		
		return newResult + regResult;
	}

	public ArrayList<Author> searchForAuthors(String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Author> authors = new ArrayList<Author>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				System.out.println(isbn);
				String strQuery = "select author_name from author join " 
				+ "book_author on author.author_id = book_author.author_id "
				+ "where book_author.isbn = '" + isbn + "'";
				rs = stmt.executeQuery(strQuery);
				System.out.println(strQuery);
				while(rs.next()) {
					Author r = new Author();
					r.setAuthorName(rs.getString(1));
					System.out.println(rs.getString(1));
					authors.add(r);
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
		return authors;
	}
}
