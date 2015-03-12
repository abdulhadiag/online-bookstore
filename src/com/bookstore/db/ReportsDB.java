package com.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportsDB {
	final private static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final private static String db_username = "cssProjHandler";
	final private static String db_passwd = "AbdulAaronTim$490";
	final private static String[] weeks = {"201509", "201507", "201505", "201503", "201501", "201453",
		"201451", "201449", "201447", "201445", "201443", "201441", "201439"};
	
	final private static String[] yearMonths = {"20153", "20152", "20151", "201412", "201411", "201410", "20149", "20148", "20147",};
	
	DBConnectionPool connPool = null;
	
	public ReportsDB() {
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
	
	public ArrayList<ArrayList<String>> getWeeklySales() {
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "SELECT CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)) as weeks, sum(total_price) "
						+ "FROM transactions "
						+ "GROUP BY CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)) "
						+ "ORDER BY weeks ASC";
				rs = stmt.executeQuery(strQuery);
				System.out.println(strQuery);
				double temp = 0;
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				while (rs.next()) {
					System.out.println("one row from weekly report");
					ArrayList<String> row = new ArrayList<String>();
					row.add(rs.getString(1));
					row.add(nf.format(rs.getDouble(2)));
					row.add(nf.format(rs.getDouble(2) - temp));
					temp = rs.getDouble(2);
					rows.add(row);
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
		return rows;
	}
	
	public ArrayList<ArrayList<String>> getMonthlySales() {
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				String strQuery = "SELECT CONCAT(YEAR(purchase_date), '/', MONTH(purchase_date)) as months, sum(total_price) "
						+ "FROM transactions "
						+ "GROUP BY CONCAT(YEAR(purchase_date), '/', MONTH(purchase_date)) "
						+ "ORDER BY months ASC";
				rs = stmt.executeQuery(strQuery);
				System.out.println(strQuery);
				double temp = 0;
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				while (rs.next()) {
					System.out.println("one row from monthly report");
					ArrayList<String> row = new ArrayList<String>();
					row.add(rs.getString(1));
					row.add(nf.format(rs.getDouble(2)));
					row.add(nf.format(rs.getDouble(2) - temp));
					temp = rs.getDouble(2);
					rows.add(row);
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
		return rows;
	}
	
	public HashMap<String, ArrayList<ArrayList<String>>> getWeeklyTopTen() {
		HashMap<String, ArrayList<ArrayList<String>>> topTen = new HashMap<String, ArrayList<ArrayList<String>>>();
		// Get the weeks
		String weeksQuery = "SELECT YEARWEEK(purchase_date) as weeks "
				+ "from line_item as l "
				+ "inner join books as b on l.isbn = b.isbn "
				+ "inner join transactions as t on l.transaction_id = t.transaction_id "
				+ "group by CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)) "
				+ "order by weeks DESC";
		ArrayList<String> weeks = getListOfWeeks(weeksQuery);
		
		// Build data
		for (String week : weeks) {
			String topTenQuery = "SELECT count(l.isbn) as num, b.title "
					+ "from line_item as l "
					+ "inner join books as b on l.isbn = b.isbn "
					+ "inner join transactions as t on l.transaction_id = t.transaction_id "
					+ "where YEARWEEK(purchase_date) = '" + week + "' "
					+ "group by CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)), l.isbn "
					+ "order by num LIMIT 10";
			Statement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = connPool.getConnection();
				
				if(conn != null) {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(topTenQuery);
					ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
					while (rs.next()) {
						ArrayList<String> row = new ArrayList<String>();
						row.add(rs.getString(1));
						row.add(rs.getString(2));
						rows.add(row);
					}
					if (!rows.isEmpty()) {
						topTen.put(week, rows);
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
		}
		
		return topTen;
	}
	
	public HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>> getWeeklyTopFiveByGenre() {
		HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>> topTen = new HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>>();
		// Get the weeks
		String weeksQuery = "SELECT YEARWEEK(purchase_date) as weeks "
				+ "from line_item as l "
				+ "inner join books as b on l.isbn = b.isbn "
				+ "inner join transactions as t on l.transaction_id = t.transaction_id "
				+ "group by CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)) "
				+ "order by weeks DESC";
		ArrayList<String> weeks = getListOfWeeks(weeksQuery);
		
		String genresQuery = "select genre_name from genres";
		ArrayList<String> genres = getListOfWeeks(genresQuery);
		
		// Build data
		for (String week : weeks) {
			HashMap<String, ArrayList<ArrayList<String>>> oneGenre = new HashMap<String, ArrayList<ArrayList<String>>>();
			for (String genre : genres) {
				 
				String topInGenreQuery = "SELECT count(l.isbn) as num, b.title "
						+ "from line_item as l "
						+ "inner join books as b on l.isbn = b.isbn "
						+ "inner join transactions as t on l.transaction_id = t.transaction_id "
						+ "inner join genre_book as gb on b.isbn=gb.isbn "
						+ "where YEARWEEK(purchase_date) = '" + week + "' and gb.genre_name='" + genre + "' "
						+ "group by CONCAT(YEAR(purchase_date), '/', WEEK(purchase_date)), l.isbn "
						+ "order by num LIMIT 5";
				Statement stmt = null;
				ResultSet rs = null;
				Connection conn = null;
				try {
					conn = connPool.getConnection();
					
					if(conn != null) {
						stmt = conn.createStatement();
						rs = stmt.executeQuery(topInGenreQuery);
						ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
						while (rs.next()) {
							ArrayList<String> row = new ArrayList<String>();
							row.add(rs.getString(1));
							row.add(rs.getString(2));
							rows.add(row);
						}
						if (!rows.isEmpty()) {
							oneGenre.put(genre, rows);
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
				if (!oneGenre.isEmpty()) {
					topTen.put(week, oneGenre);
				}
			}
			
		}
		
		return topTen;
	}
	
	public HashMap<String, ArrayList<ArrayList<String>>> getBiweeklyTopRated() {
		HashMap<String, ArrayList<ArrayList<String>>> topReviews = new HashMap<String, ArrayList<ArrayList<String>>>();
		// Build the data
		for (String week : weeks) {
			String ratingQuery = "SELECT b.title, avg(rating) as rating_avg "
					+ "from rating as r "
					+ "join books as b on b.isbn=r.isbn "
					+ "where yearweek(review_date, 0) = '" 
					+ week + "' or yearweek(review_date, 0) = '" + week + "' - 1 "
					+ "group by r.isbn order by rating_avg desc limit 10;";
			Statement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = connPool.getConnection();
				
				if(conn != null) {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(ratingQuery);
					ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
					while (rs.next()) {
						ArrayList<String> row = new ArrayList<String>();
						row.add(rs.getString(1));
						row.add(rs.getString(2));
						rows.add(row);
					}
					if (!rows.isEmpty()) {
						topReviews.put(week, rows);
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
		}
		return topReviews;
	}
	
	public HashMap<String, ArrayList<ArrayList<String>>> getDirectMarketingReport() {
		HashMap<String, ArrayList<ArrayList<String>>> monthlyBuyers = new HashMap<String, ArrayList<ArrayList<String>>>();

		// Build data
		for (String yearMonth : yearMonths) {
			String topTenQuery = "SELECT t.username, genre_name, count(g.genre_name) as purchase_count, t.purchase_date "
					+ "from line_item as l "
					+ "inner join books as b on l.isbn = b.isbn "
					+ "inner join transactions as t on l.transaction_id = t.transaction_id "
					+ "inner join genre_book as g on g.isbn = b.isbn "
					+ "group by t.username, g.genre_name "
					+ "having count(g.genre_name) > 1 and "
					+ "concat(year(t.purchase_date), month(t.purchase_date)) = '" + yearMonth + "' "
					+ "order by purchase_count desc;";
			Statement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = connPool.getConnection();
				
				if(conn != null) {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(topTenQuery);
					ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
					while (rs.next()) {
						ArrayList<String> row = new ArrayList<String>();
						row.add(rs.getString(1));
						row.add(rs.getString(2));
						row.add(rs.getString(3));
						rows.add(row);
					}
					if (!rows.isEmpty()) {
						monthlyBuyers.put(yearMonth, rows);
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
		}
		return monthlyBuyers;
	}
	
	public ArrayList<String> getListOfWeeks(String strQuery) {
		ArrayList<String> rows = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = connPool.getConnection();
			
			if(conn != null) {
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(strQuery);
				System.out.println(strQuery);
				while (rs.next()) {
					rows.add(rs.getString(1));
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
		return rows;
	}
	
}
