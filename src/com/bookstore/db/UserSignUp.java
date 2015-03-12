package com.bookstore.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserSignUp
 */
public class UserSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static String db_url = "jdbc:mysql://css-490-db.cs2vzd2bk0ai.us-west-2.rds.amazonaws.com:3306/css490pr";
	final static String db_username = "cssProjHandler";
	final static String db_passwd = "AbdulAaronTim$490";
	
	DBConnectionPool connPool = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fName = request.getParameter("fName");
		String insertQuery = "insert into TestTable (nameD) values ('" + fName + "')";
		Statement stmt = null;
		ResultSet rs = null;
		int resultNo = 0;
		Connection conn = null;
		
		try {
			connPool = new DBConnectionPool(db_url, db_username, db_passwd); 
			
			try {
				conn = connPool.getConnection();
				
				if(conn != null) {
					stmt = conn.createStatement();
					resultNo = stmt.executeUpdate(insertQuery);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("./DbTest");
	}

}
