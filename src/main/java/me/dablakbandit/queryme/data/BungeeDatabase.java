/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import me.dablakbandit.queryme.universal.database.listener.SQLListener;

public class BungeeDatabase extends SQLListener{
	
	private static BungeeDatabase database = new BungeeDatabase();
	
	public static BungeeDatabase getInstance(){
		return database;
	}
	
	private BungeeDatabase(){
		
	}
	
	private static PreparedStatement add, get_all, get_unique, get_total;
	
	@Override
	public void setup(Connection con){
		try{
			con.prepareStatement("CREATE TABLE IF NOT EXISTS `queryme_ip` (`id` INT NOT NULL AUTO_INCREMENT, `ip` INT UNSIGNED NOT NULL, `host` TINYTEXT NOT NULL, `protocol` INT NOT NULL, `time` DATETIME NOT NULL,  PRIMARY KEY(`id`));").execute();
			// SELECT DISTINCT INET_NTOA(`ip`) FROM `queryme_ip`
			add = con.prepareStatement("INSERT INTO `queryme_ip` (`ip`, `host`, `protocol`, `time`) VALUES (INET_ATON(?), ?, ?, ?);");
			get_all = con.prepareStatement("SELECT INET_NTOA(`ip`) AS `ip`, `host`, `protocol`, UNIX_TIMESTAMP(`time`) AS `time` FROM `queryme_ip`;");
			get_unique = con.prepareStatement("SELECT COUNT (DISTINCT INET_NTOA(`ip`)) FROM `queryme_ip` WHERE `time` > ?;");
			get_total = con.prepareStatement("SELECT COUNT (INET_NTOA(`ip`)) FROM `queryme_ip` WHERE `time` > ?;");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void add(String ip, String host, int protocol){
		try{
			ensureConnection();
			long time = System.currentTimeMillis();
			Timestamp t = new Timestamp(time);
			add.setString(1, ip);
			add.setString(2, host);
			add.setInt(3, protocol);
			add.setTimestamp(4, t);
			add.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getUnique(long since){
		try{
			ensureConnection();
			get_unique.setTimestamp(1, new Timestamp(since));
			ResultSet rs = get_unique.executeQuery();
			if(rs.next()){ return rs.getInt(1); }
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getTotal(long since){
		try{
			ensureConnection();
			get_total.setTimestamp(1, new Timestamp(since));
			ResultSet rs = get_total.executeQuery();
			if(rs.next()){ return rs.getInt(1); }
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public void close(Connection con){
		closeStatements();
	}
}
