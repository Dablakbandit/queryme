/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit;

import me.dablakbandit.core.configuration.Configuration;
import me.dablakbandit.core.metrics.Metrics;
import me.dablakbandit.core.plugin.CoreHandler;
import me.dablakbandit.core.updater.PluginUpdater;
import me.dablakbandit.queryme.bukkit.config.MySQLConfiguration;
import me.dablakbandit.queryme.bukkit.listener.HandshakeListener;
import me.dablakbandit.queryme.data.QueryMeDatabase;
import me.dablakbandit.queryme.universal.database.Database;
import me.dablakbandit.queryme.universal.database.mysql.MySQLDatabase;

public class QueryMeCoreHandler extends CoreHandler{
	
	private static QueryMeCoreHandler main = new QueryMeCoreHandler();
	
	public static QueryMeCoreHandler getInstance(){
		return main;
	}
	
	private QueryMeBukkit	plugin;
	
	private Database		db;
	
	public void onLoad(){
		plugin = QueryMeBukkit.getInstance();
		PluginUpdater.getInstance().checkUpdate(plugin, "65317");
		QueryMeBukkitConfiguration.load();
		HandshakeListener.getInstance();
		try{
			MySQLConfiguration mysql = new MySQLConfiguration(new Configuration(plugin, "mysql.yml"));
			db = new MySQLDatabase(mysql.getMySQL(), true);
			db.addListener(QueryMeDatabase.getInstance());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onEnable(){
		new Metrics(plugin, "QueryMe");
		PluginUpdater.getInstance().checkUpdate(plugin, "65317");
	}
	
	public void onDisable(){
		
	}
	
}
