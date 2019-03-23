/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import me.dablakbandit.core.plugin.downloader.CorePluginDownloader;

public class QueryMeBukkit extends JavaPlugin{
	
	private static QueryMeBukkit main;
	
	public static QueryMeBukkit getInstance(){
		return main;
	}
	
	private QueryMeCoreHandler handler;
	
	public void onLoad(){
		main = this;
		if(CorePluginDownloader.ensureCorePlugin()){
			saveDefaultConfig();
			handler = QueryMeCoreHandler.getInstance();
			handler.onLoad();
		}
	}
	
	public void onEnable(){
		if(handler != null){
			handler.onEnable();
		}
	}
	
	public void onDisable(){
		if(handler != null){
			handler.onDisable();
		}
	}
	
}
