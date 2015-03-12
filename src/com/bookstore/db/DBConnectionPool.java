package com.bookstore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	final String driver = "org.gjt.mm.mysql.Driver";
	String url;
	String username;
	String passwd; 
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String url, String username, String passwd) throws Exception{
		this.url = url;
		this.username = username;
		this.passwd = passwd; 
		
		Class.forName(driver);
	}
	
	public Connection getConnection() throws Exception{
		if(connList.size() > 0){
			Connection conn = connList.get(0);
			if(conn.isValid(10)){
				return conn;
			}
		}
		
		return DriverManager.getConnection(url, username, passwd);
	}
	
	public void returnConnection(Connection conn) throws Exception{
		connList.add(conn);
	}
	
	public void closeAll(){
		for(Connection conn: connList){
			try{
				conn.close();
			}catch(Exception e){
			}
		}
	}
}
