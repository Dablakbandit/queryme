/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import me.dablakbandit.core.configuration.Configuration;
import me.dablakbandit.core.metrics.Metrics;
import me.dablakbandit.queryme.bukkit.config.MySQLConfiguration;
import me.dablakbandit.queryme.bukkit.listener.HandshakeListener;
import me.dablakbandit.queryme.data.QueryMeDatabase;
import me.dablakbandit.queryme.universal.database.Database;
import me.dablakbandit.queryme.universal.database.mysql.MySQLDatabase;

public class QueryMeBukkit extends JavaPlugin{
	
	private static QueryMeBukkit main;
	
	public static QueryMeBukkit getInstance(){
		return main;
	}
	
	private Database db;
	
	public void onLoad(){
		main = this;
		QueryMeBukkitConfiguration.setup(this);
		HandshakeListener.getInstance();
		try{
			MySQLConfiguration mysql = new MySQLConfiguration(new Configuration(this, "mysql.yml"));
			db = new MySQLDatabase(mysql.getMySQL(), true);
			db.addListener(QueryMeDatabase.getInstance());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onEnable(){
		new Metrics(this, "QueryMe");
	}
	
	public void onDisable(){
		
	}
	
}
