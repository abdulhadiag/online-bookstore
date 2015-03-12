package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.Transaction;

public class TransactionDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public TransactionDB() {
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
	 * Retrieve a model for a single Transaction
	 * @param TransactionId int containing Transaction ID
	 * @return model of a user
	 */
	public Transaction selectTransaction(int transactionId) {
		Statement stmt = null;
		ResultSet rs = null;
		Transaction transaction = new Transaction();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select username, purchase_date, total_price, is_processed "
						+ "from transactions where transaction_id = '" + transactionId + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					transaction.setTransactionId(transactionId);
					transaction.setUserName(rs.getString(1));
					transaction.setPurchaseDate(rs.getDate(2).toString());
					transaction.setTotalPrice(rs.getDouble(3));
					transaction.setIsProcessed(rs.getInt(4));
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
		return transaction;
	}
	
	/**
	 * Creates a database entry for a new transaction. 
	 * @param transaction A fully populated Genre object 
	 * @return transaction_id on success, -1 on error
	 */
	public int insertTransaction(Transaction transaction) {
		Statement stmt = null;
		int resultNo = 0;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "insert into transactions (username, purchase_date, total_price, is_processed) "
							+ "values('"
					        + transaction.getUserName() + "', NOW(), '"
							+ transaction.getTotalPrice() + "', '"
							+ transaction.getIsProcessed() + "')";
				
				stmt.executeUpdate(strQuery, Statement.RETURN_GENERATED_KEYS);
				rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					resultNo = rs.getInt(1);
				}
				System.out.println("TransID: " + resultNo);
			}
		} catch (SQLException e) {
			for(Throwable t: e) {
				t.printStackTrace();
			}
		} catch (Exception et) {
			et.printStackTrace();
		} finally {
			try {
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
	 * Update an existing transaction. To update a transaction, retrieve their transaction object
	 * using selectTransaction() or selectTransactions(), edit the transaction's fields, and then
	 * send it back to this method.
	 * @param transaction An updated transaction object
	 * @return The number of affected rows. (1 for success. 0 for error)
	 */
	public int updateTransaction(Transaction transaction) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
	
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "update transactions set "
				+ "username='" + transaction.getUserName() 
				+ "' where transaction_id='" + transaction.getTransactionId();
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
	 * Deletes a transaction from the database. 
	 * @param transaction_id unique int representation of the transaction
	 * @return The number of affected rows. (1 for success. 0 for error) 
	 */
	public int deleteTransaction(int transaction_id) {
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "delete from transactions where transaction_id = '" + transaction_id + "'";
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
	 * Returns a list of all transactions in the system. May be useful for running reports
	 * of for user administration by staff
	 * @return A list of all transactions in the system.
	 */
	public ArrayList<Transaction> selectTransactions() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				String strQuery = "select transaction_id, username, purchase_date, total_price, is_processed from transactions";
				rs = stmt.executeQuery(strQuery);
				while(rs.next()) {
					Transaction t = new Transaction();
					t.setTransactionId(rs.getInt(1));
					t.setUserName(rs.getString(2));
					t.setPurchaseDate(rs.getDate(3).toString());
					t.setTotalPrice(rs.getDouble(4));
					t.setIsProcessed(rs.getInt(5));
					transactions.add(t);
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
			
		return transactions;
	}
}