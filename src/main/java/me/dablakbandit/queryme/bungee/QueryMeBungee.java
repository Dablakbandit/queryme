/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee;

import me.dablakbandit.queryme.bungee.config.FileConfiguration;
import me.dablakbandit.queryme.bungee.config.MySQLConfiguration;
import me.dablakbandit.queryme.bungee.listener.HandshakeListener;
import me.dablakbandit.queryme.data.QueryMeDatabase;
import me.dablakbandit.queryme.universal.database.Database;
import me.dablakbandit.queryme.universal.database.mysql.MySQLDatabase;
import net.md_5.bungee.api.plugin.Plugin;

public class QueryMeBungee extends Plugin{
	
	public static QueryMeBungee main;
	
	public static QueryMeBungee getInstance(){
		return main;
	}
	
	private Database db;
	
	public void onLoad(){
		main = this;
	}
	
	public void onEnable(){
		try{
			MySQLConfiguration mysql = new MySQLConfiguration(new FileConfiguration(this, "mysql.yml"));
			db = new MySQLDatabase(mysql.getMySQL(), true);
			db.addListener(QueryMeDatabase.getInstance());
		}catch(Exception e){
			e.printStackTrace();
		}
		HandshakeListener.getInstance();
		// getProxy().getPluginManager().registerCommand(this, new QueryMeCommand());
		QueryMeBungeeConfigration.getInstance();
	}
	
	public void onDisable(){
		db.closeConnection();
	}
	
	public Database getDatabase(){
		return db;
	}
}
