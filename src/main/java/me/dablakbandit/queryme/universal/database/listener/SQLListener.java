/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.universal.database.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import me.dablakbandit.queryme.universal.database.Database;

public abstract class SQLListener{
	
	protected Database database;
	
	public void setDatabase(Database database){
		this.database = database;
	}
	
	public Database getDatabase(){
		return database;
	}
	
	public void ensureConnection(){
		if(database != null){
			database.getConnection();
		}
	}
	
	public abstract void setup(Connection con);
	
	public abstract void close(Connection con);
	
	public void closeStatements(){
		try{
			for(Field f : getFields(this.getClass())){
				if(f.getType().equals(PreparedStatement.class)){
					PreparedStatement ps = (PreparedStatement)f.get(this);
					if(ps != null){
						ps.close();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static List<Field> getFields(Class<?> clazz) throws Exception{
		List<Field> f = new ArrayList<Field>();
		
		for(Field field : clazz.getDeclaredFields()){
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			int modifiers = modifiersField.getInt(field);
			modifiers &= ~Modifier.FINAL;
			modifiersField.setInt(field, modifiers);
			f.add(field);
		}
		return f;
	}
	
}
