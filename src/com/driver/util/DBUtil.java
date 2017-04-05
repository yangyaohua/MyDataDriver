package com.driver.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/mydriver";
	private static final String user = "root";
	private static final String password = "";
	
	private DBUtil(){
		
	}
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	

	public static void closeConnection(Connection conn)
	{
		if(conn != null)
		{
			try
			{
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
