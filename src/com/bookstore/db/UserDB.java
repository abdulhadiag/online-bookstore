package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.User;

public class UserDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public UserDB() {
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
	 * Retrieve a model for a single user
	 * @param username case-sensitive String containing username
	 * @return model of a user
	 */
	public User selectUser(String username) {
		Statement stmt = null;
		ResultSet rs = null;
		User user = new User();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select username, f_name, l_name, email, phone, "
						+ "passwd, signup_date, last_login, is_staff "  
						+ "from users where username = '" + username + "'";
				rs = stmt.executeQuery(strQuery);
				int count = 0;
				if (rs.next()) {
					count++;
					user.setUsername(rs.getString(1));
					user.setFirstName(rs.getString(2));
					user.setLastName(rs.getString(3));
					user.setEmail(rs.getString(4));
					user.setPhoneNumber(rs.getString(5));
					user.setPasswd(rs.getString(6));
					user.setSignDate(rs.getString(7));
					user.setLastDate(rs.getString(8));
					user.setStaff(rs.getBoolean(9));
				}
				if (count == 0) {
					user = null;
				}
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
			user = null;
		} catch (Exception et) {
			et.printStackTrace();
			user = null;
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
		return user;
	}
	
	/**
	 * Creates a database entry for a new user. 
	 * @param user A fully populated User object 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int registerUser(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "insert into users (username, f_name, l_name, email, "
						+ "phone, passwd, signup_date, last_login, is_staff) values('"
				        + user.getUsername() + "', '" 
				        + user.getFirstName() + "', '"
				        + user.getLastName() + "', '" 
						+ user.getEmail() + "', '"
						+ user.getPhoneNumber() + "', '"
						+ user.getPasswd() + "', "
						+ "now(), now(),'"
						+ (user.isStaff() ? 1 : 0) + "')";
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
	 * Update an existing user. To update a user, retrieve their user object
	 * using selectUser() or selectUsers(), edit the user's fields, and then
	 * send it back to this method.
	 * @param user An updated user object
	 * @return The number of affected rows. (1 for success. 0 for error)
	 */
	public int updateUser(User user) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "update users set "
				+ "f_name='" + user.getFirstName() 
				+ "', l_name='" + user.getLastName() 
				+ "', email='" + user.getEmail() 
				+ "', phone='" + user.getPhoneNumber()
				+ "', email='" + user.getEmail() 
				+ "', passwd='" + user.getPasswd()
				+ "', signup_date='" + user.getSignDate() 
				+ "', last_login='" + user.getLastDate()
				+ "' where username='" + user.getUsername() + "'";
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
	 * Deletes a user from the database. 
	 * @param username case-sensitive String representation of the username
	 * @return The number of affected rows. (1 for success. 0 for error) 
	 */
	public int deleteUser(String username) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "delete from users where username = '" + username + "'";
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
	 * Returns a list of all users in the system. May be useful for running reports
	 * of for user administration by staff
	 * @return A list of all users in the system.
	 */
	public ArrayList<User> selectUsers() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<User> users = new ArrayList<User>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select username, f_name, l_name, email, phone, "
						+ "passwd, signup_date, last_login, is_staff from users";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					User u = new User();
					u.setUsername(rs.getString(1));
					u.setFirstName(rs.getString(2));
					u.setLastName(rs.getString(3));
					u.setEmail(rs.getString(4));
					u.setPhoneNumber(rs.getString(5));
					u.setPasswd(rs.getString(6));
					u.setSignDate(rs.getString(7));
					u.setLastDate(rs.getString(8));
					u.setStaff(rs.getBoolean(9));
					users.add(u);
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
			
		return users;
	}
}
