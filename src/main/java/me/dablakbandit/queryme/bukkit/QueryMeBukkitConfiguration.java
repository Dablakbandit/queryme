/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit;

import java.util.Arrays;

import org.bukkit.plugin.Plugin;

import me.dablakbandit.core.configuration.PluginConfiguration;

public class QueryMeBukkitConfiguration extends PluginConfiguration{
	
	private static QueryMeBukkitConfiguration	configuration	= new QueryMeBukkitConfiguration(QueryMeBukkit.getInstance());
	
	public static StringListPath				BLACKLIST		= new StringListPath("Blacklist", Arrays.asList(new String[]{ "0.0.0.-1" }));
	
	private QueryMeBukkitConfiguration(Plugin plugin){
		super(plugin);
	}
	
	public static void load(){
		configuration.loadPaths();
	}
	
	public static void reload(){
		load();
	}
	
}
