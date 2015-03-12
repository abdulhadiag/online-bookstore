package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.Author;
import com.bookstore.models.Genre;

public class GenreDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public GenreDB() {
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
	 * Retrieve a model for a single genre
	 * @param genreName case-sensitive String containing genreName
	 * @return model of a user
	 */
	public Genre selectGenre(String genreName) {
		Statement stmt = null;
		ResultSet rs = null;
		Genre genre = new Genre();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select genre_name "
						+ "from genres where genre_name = '" + genreName + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					genre.setGenreName(rs.getString(1));
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
		return genre;
	}
	
	/**
	 * Creates a database entry for a new genre. 
	 * @param genre A fully populated Genre object 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int registerGenre(Genre genre) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into genres (genre_name) values('"
				        + genre.getGenreName() + "')";
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
	 * Update an existing genre. To update a genre, retrieve their genre object
	 * using selectGenre() or selectGenres(), edit the genre's fields, and then
	 * send it back to this method.
	 * @param genre An updated genre object
	 * @return The number of affected rows. (1 for success. 0 for error)
	 */
	public int updateGenre(Genre genre) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "update genres set "
				+ "genre_name='" + genre.getGenreName() + "'";
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
	 * Deletes a genre from the database. 
	 * @param genreName case-sensitive String representation of the genreName
	 * @return The number of affected rows. (1 for success. 0 for error) 
	 */
	public int deleteGenre(String genreName) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "delete from genres where genre_name = '" + genreName + "'";
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
	 * Returns a list of all genres in the system. May be useful for running reports
	 * of for user administration by staff
	 * @return A list of all genres in the system.
	 */
	public ArrayList<Genre> selectGenres() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Genre> genres = new ArrayList<Genre>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select genre_name from genres";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Genre g = new Genre();
					g.setGenreName(rs.getString(1));
					genres.add(g);
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
			
		return genres;
	}
	public int checkAndAdd(ArrayList<Genre> genres, String isbn) {
		Genre genre = new Genre();
		String name = "";
		int newResult = 0;
		int regResult = 0;
		for (int i = 0; i < genres.size(); i++){
			name = genres.get(i).getGenreName();
			System.out.println(genres.get(i).getGenreName());
			if (!name.equals("")){
				genre = selectGenre(name);
				System.out.println(genre.getGenreName());
				if(genre.getGenreName().equals("")){
					genre.setGenreName(name);
					newResult += registerGenre(genre);
				}
				genre = selectGenre(name);
				System.out.println(genre.getGenreName());
				regResult += registerBookGenre(genre, isbn);
			}
		}
		
		return newResult + regResult;
	}
	

	public int registerBookGenre(Genre genre, String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into genre_book (isbn, genre_name"
						+ ") values('" + isbn + "','"
				        + genre.getGenreName() + "')";
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
	public ArrayList<Genre> searchForGenres(String isbn) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Genre> genres = new ArrayList<Genre>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				System.out.println(isbn);
				String strQuery = "select genre_book.genre_name from genres join " 
				+ "genre_book on genres.genre_name = genre_book.genre_name "
				+ "where genre_book.isbn = '" + isbn + "'";
				rs = stmt.executeQuery(strQuery);
				System.out.println(strQuery);
				while(rs.next()) {
					Genre r = new Genre();
					r.setGenreName(rs.getString(1));
					System.out.println(rs.getString(1));
					genres.add(r);
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
		return genres;
	}
}
