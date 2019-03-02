/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bungee.listener;

import me.dablakbandit.queryme.bungee.QueryMeBungee;
import me.dablakbandit.queryme.bungee.QueryMeBungeeConfigration;
import me.dablakbandit.queryme.data.QueryMeDatabase;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.Handshake;

public class HandshakeListener implements Listener{
	
	private static HandshakeListener listener = new HandshakeListener();
	
	public static HandshakeListener getInstance(){
		return listener;
	}
	
	private HandshakeListener(){
		QueryMeBungee mb = QueryMeBungee.getInstance();
		mb.getProxy().getPluginManager().registerListener(mb, this);
	}
	
	@EventHandler
	public void onPlayerHandshake(PlayerHandshakeEvent event){
		PendingConnection pc = event.getConnection();
		Handshake hs = event.getHandshake();
		String ip = pc.getAddress().getHostString();
		if(QueryMeBungeeConfigration.getInstance().getBlacklist().contains(ip)){ return; }
		String host = hs.getHost() + ":" + hs.getPort();
		int protocol = hs.getProtocolVersion();
		QueryMeDatabase.getInstance().add(ip, host, protocol);
	}
	
}
