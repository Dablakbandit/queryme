/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.plugin.Plugin;

import me.dablakbandit.core.configuration.PluginConfiguration;

public class QueryMeBukkitConfiguration extends PluginConfiguration{
	
	private static QueryMeBukkitConfiguration	configuration;
	
	public static StringListPath				BLACKLIST	= new StringListPath("Blacklist", Arrays.asList(new String[]{ "0.0.0.-1" }));
	
	private QueryMeBukkitConfiguration(Plugin plugin){
		super(plugin);
	}
	
	public static void setup(Plugin plugin){
		configuration = new QueryMeBukkitConfiguration(plugin);
		load();
	}
	
	public static void load(){
		configuration.plugin.reloadConfig();
		try{
			boolean save = false;
			for(Field f : QueryMeBukkitConfiguration.class.getDeclaredFields()){
				if(Path.class.isAssignableFrom(f.getType())){
					Path p = (Path)f.get(null);
					if(!save){
						save = p.retrieve(configuration.plugin.getConfig());
					}else{
						p.retrieve(configuration.plugin.getConfig());
					}
				}
			}
			if(save){
				configuration.plugin.saveConfig();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void reload(){
		load();
	}
	
}
