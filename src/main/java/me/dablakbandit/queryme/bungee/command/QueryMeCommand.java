/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class QueryMeCommand extends Command{
	
	public QueryMeCommand(){
		super("queryme");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args){
		sender.sendMessage(new ComponentBuilder("").create());
	}
	
}
