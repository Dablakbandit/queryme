/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dablakbandit.queryme.bungee.config.FileConfiguration;

public class QueryMeBungeeConfiguration{
	
	private static QueryMeBungeeConfiguration qconfig = new QueryMeBungeeConfiguration();
	
	public static QueryMeBungeeConfiguration getInstance(){
		return qconfig;
	}
	
	private List<String>		blacklist	= new ArrayList<String>();
	
	private FileConfiguration	config;
	
	private QueryMeBungeeConfiguration(){
		try{
			config = new FileConfiguration(QueryMeBungee.getInstance(), "config.yml");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(config.isSet("Blacklist")){
			blacklist = config.getStringList("Blacklist");
		}else{
			config.set("Blacklist", Arrays.asList(new String[]{ "0.0.0.-1" }));
			config.saveConfig();
		}
	}
	
	public List<String> getBlacklist(){
		return blacklist;
	}
}
