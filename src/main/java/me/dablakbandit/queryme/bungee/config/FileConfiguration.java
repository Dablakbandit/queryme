/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileConfiguration{
	protected static final ConfigurationProvider	yml	= ConfigurationProvider.getProvider(YamlConfiguration.class);
	private Plugin									plugin;
	private final Configuration						defaultCfg;
	private Configuration							config;
	private File									configFile;
	private String									defaultFile;
	
	public FileConfiguration(Plugin plugin, String path) throws IOException{
		this(plugin, new File(plugin.getDataFolder(), path));
	}
	
	public FileConfiguration(Plugin plugin, File configFile) throws IOException{
		this(plugin, configFile, configFile.getName());
	}
	
	public FileConfiguration(Plugin plugin, File configFile, String defaultFile) throws IOException{
		this.plugin = plugin;
		this.configFile = configFile;
		this.defaultFile = defaultFile;
		InputStream stream = plugin.getResourceAsStream(defaultFile);
		if(stream != null){
			this.defaultCfg = yml.load(new InputStreamReader(stream));
		}else{
			this.defaultCfg = new Configuration();
		}
		loadConfig();
	}
	
	public boolean loadConfig() throws IOException{
		if(this.configFile.exists()){
			this.config = yml.load(this.configFile, this.defaultCfg);
			return true;
		}
		if((this.configFile.getParentFile().exists()) || (this.configFile.getParentFile().mkdirs())){ return createDefaultConfig(); }
		return false;
	}
	
	public boolean saveConfig(){
		try{
			yml.save(this.config, this.configFile);
		}catch(IOException e){
			this.plugin.getLogger().severe("Could not save configuration to " + this.configFile.getAbsolutePath());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean createDefaultConfig() throws IOException{
		if(this.configFile.createNewFile()){
			InputStream stream = this.plugin.getResourceAsStream(this.defaultFile);
			if(stream != null){
				this.config = yml.load(new InputStreamReader(stream), this.defaultCfg);
			}else{
				this.config = new Configuration();
			}
			saveConfig();
			return true;
		}
		return false;
	}
	
	public boolean removeConfig(){
		return this.configFile.delete();
	}
	
	public Configuration getConfiguration(){
		return this.config;
	}
	
	public Configuration getDefaults(){
		return this.defaultCfg;
	}
	
	public boolean isSet(String path){
		return isSet(path, false);
	}
	
	public boolean isSet(String path, boolean ignoreDefaults){
		return (ignoreDefaults ? this.config.get(path, null) : this.config.get(path)) != null;
	}
	
	public void set(String path, Object value){
		this.config.set(path, value);
	}
	
	public boolean getBoolean(String path){
		return this.config.getBoolean(path);
	}
	
	public boolean getBoolean(String path, boolean def){
		return this.config.getBoolean(path, def);
	}
	
	public int getInt(String path){
		return this.config.getInt(path);
	}
	
	public int getInt(String path, int def){
		return this.config.getInt(path, def);
	}
	
	public int getDouble(String path){
		return this.config.getInt(path);
	}
	
	public double getDouble(String path, double def){
		return this.config.getDouble(path, def);
	}
	
	public String getString(String path){
		return this.config.getString(path);
	}
	
	public String getString(String path, String def){
		return this.config.getString(path, def);
	}
	
	public List<String> getStringList(String path){
		return this.config.getStringList(path);
	}
	
	public Configuration getSection(String path){
		return this.config.getSection(path);
	}
	
	public boolean isSection(String path){
		return this.config.get(path) instanceof Configuration;
	}
}
