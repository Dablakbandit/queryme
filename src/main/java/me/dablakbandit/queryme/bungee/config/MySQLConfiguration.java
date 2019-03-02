/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee.config;

import me.dablakbandit.queryme.universal.database.mysql.MySQL;
import net.md_5.bungee.config.Configuration;

public class MySQLConfiguration{
	
	private String user, password, host, port, database, extra;
	
	public MySQLConfiguration(FileConfiguration file){
		Configuration conf = file.getConfiguration();
		if(!conf.contains("SQL.user")){
			conf.set("SQL.user", "user");
		}
		if(!conf.contains("SQL.password")){
			conf.set("SQL.password", "password");
		}
		if(!conf.contains("SQL.host")){
			conf.set("SQL.host", "localhost");
		}
		if(!conf.contains("SQL.port")){
			conf.set("SQL.port", "3306");
		}
		if(!conf.contains("SQL.database")){
			conf.set("SQL.database", "db");
		}
		if(!conf.contains("SQL.extra")){
			conf.set("SQL.extra", "?useUnicode=true&characterEncoding=utf-8");
		}
		file.saveConfig();
		this.user = conf.getString("SQL.user");
		this.password = conf.getString("SQL.password");
		this.host = conf.getString("SQL.host");
		this.port = conf.getString("SQL.port");
		this.database = conf.getString("SQL.database");
		this.extra = conf.getString("SQL.extra");
	}
	
	public MySQL getMySQL(){
		return new MySQL(this.host, this.port, this.database, this.user, this.password, this.extra);
	}
}
