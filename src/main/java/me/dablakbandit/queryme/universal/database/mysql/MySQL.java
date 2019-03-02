/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.universal.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL{
	private final String	user;
	private final String	database;
	private final String	password;
	private final String	port;
	private final String	hostname;
	private final String	extra;
	private Connection		connection;
	
	public MySQL(String hostname, String port, String database, String username, String password, String extra){
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.extra = extra;
	}
	
	public String getDatabase(){
		return database;
	}
	
	public Connection openConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database + extra, this.user, this.password);
		}catch(SQLException e){
			System.out.print(e.getMessage());
			closeConnection();
		}catch(ClassNotFoundException e){
			System.out.print("JDBC Driver not found!");
		}
		return this.connection;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void closeConnection(){
		if(this.connection != null){
			try{
				this.connection.close();
			}catch(SQLException e){
				System.out.print("Error closing the MySQL Connection!");
				e.printStackTrace();
			}
		}
	}
}
