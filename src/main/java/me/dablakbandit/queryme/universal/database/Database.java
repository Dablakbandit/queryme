/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.universal.database;

import java.sql.Connection;

import me.dablakbandit.queryme.universal.database.listener.SQLListener;

public abstract class Database{
	
	public abstract Connection openConnection();
	
	public abstract Connection getConnection();
	
	public abstract boolean isConnected();
	
	public abstract void closeConnection();
	
	public abstract void addListener(SQLListener listener);
}
