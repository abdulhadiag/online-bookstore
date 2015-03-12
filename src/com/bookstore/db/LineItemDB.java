package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bookstore.models.Book;
import com.bookstore.models.LineItem;

public class LineItemDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
	
	public LineItemDB() {
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
	 * Creates a database entry for a new line item. 
	 * @param lineItem A fully populated lineItem object (except line_item_id) 
	 * @return Number of rows affected (1 for success. 0 for error)
	 */
	public int insertLineItems(ArrayList<LineItem> lineItems) {	
		Statement stmt = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();

				StringBuilder sb = new StringBuilder("insert into line_item (transaction_id, isbn, quantity) values");
						for (LineItem item : lineItems) {
							sb.append("('" + item.getTransactionId() + "', '");
							sb.append(item.getIsbn() + "', '");
							sb.append(item.getQuantity() + "'),"); 
						}
						sb.replace(sb.length()-1, sb.length(), ";");
				resultNo = stmt.executeUpdate(sb.toString());
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
	 * Retrieve a model for a single line item
	 * @param lineItemId the PK for the line item
	 * @return model of a line item
	 */
	public LineItem selectLineItem(int lineItemId) {
		Statement stmt = null;
		ResultSet rs = null;
		LineItem lineItem = new LineItem();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select transaction_id, isbn, quantity, title "  
						+ "from line_items where line_item_id='" + lineItemId + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					lineItem.setLineItemId(lineItemId);
					lineItem.setTransactionId(rs.getInt(1));
					lineItem.setIsbn(rs.getString(2));
					lineItem.setQuantity(rs.getInt(3));
					lineItem.setTitle(rs.getString(4));
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
		return lineItem;
	}
	
	/**
	 * Retrieve a model for a all line items belonging to a transaction
	 * @param transactionId the FK for the line item
	 * @return List of all line items belonging to transaction
	 */
	public ArrayList<LineItem> selectLineItems(int transactionId) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "select line_item_id, isbn, quantity, title "  
						+ "from line_items where transaction_id='" + transactionId + "'";
				rs = stmt.executeQuery(strQuery);
				if (rs.next()) {
					LineItem lineItem = new LineItem();
					lineItem.setLineItemId(rs.getInt(1));
					lineItem.setTransactionId(transactionId);
					lineItem.setIsbn(rs.getString(2));
					lineItem.setQuantity(rs.getInt(3));
					lineItem.setTitle(rs.getString(4));
					lineItems.add(lineItem);
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
		return lineItems;
	}
	
	/**
	 * Delete a line item from DB
	 * @param lineItemId the PK for the line item to delete
	 * @return number of rows affected
	 */
	public int deleteLineItem(int lineItemId) {
		Statement stmt = null;
		int resultNo = 0;
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "delete from line_item "
						+ "where line_item_id='" + lineItemId + "'";
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
}
