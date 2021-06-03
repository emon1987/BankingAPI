package com.project.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
	private static Connection connection;
	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
//				FileInputStream fis = new FileInputStream("connection.properties");
//				Properties prop = new Properties();
//				prop.load(fis);
				
				//url=jdbc:postgresql://lallah.db.elephantsql.com:5432/wualvcfo
					//username=wualvcfo
					// password=mvHFDJEOaCBCbG1Q180ItPOmyxLL-r3J
					connection = DriverManager.getConnection("jdbc:postgresql://lallah.db.elephantsql.com:5432/wualvcfo",
							"wualvcfo", "mvHFDJEOaCBCbG1Q180ItPOmyxLL-r3J");

//				connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
//						prop.getProperty("password"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}