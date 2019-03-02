/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.queryme.bukkit.listener;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import me.dablakbandit.core.players.CorePlayerManager;
import me.dablakbandit.core.server.packet.ServerHandler;
import me.dablakbandit.core.server.packet.ServerPacketListener;
import me.dablakbandit.core.server.packet.ServerPacketManager;
import me.dablakbandit.core.utils.PacketUtils;

public class HandshakeListener extends ServerPacketListener{
	
	private static HandshakeListener listener = new HandshakeListener();
	
	public static HandshakeListener getInstance(){
		return listener;
	}
	
	private HandshakeListener(){
		CorePlayerManager.getInstance().enablePacketListener();
		ServerPacketManager.getInstance().addListener(this);
	}
	
	@Override
	public boolean read(ServerHandler handler, Object packet){
		if(PacketUtils.HandshakingInSetProtocol.classPacketHandshakingInSetProtocol.equals(packet.getClass())){
			try{
				InetSocketAddress socketAddress = (InetSocketAddress)handler.getChannel().remoteAddress();
				InetAddress inetAddress = socketAddress.getAddress();
				
				inetAddress.getHostAddress();
				String host = PacketUtils.HandshakingInSetProtocol.fieldPacketHandshakingInSetProtocolHostname.get(packet) + ":" + PacketUtils.HandshakingInSetProtocol.fieldPacketHandshakingInSetProtocolPort.get(packet);
				int protocol = (int)PacketUtils.HandshakingInSetProtocol.fieldPacketHandshakingInSetProtocolProtocol.get(packet);
				System.out.println(host);
				System.out.println(protocol);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	public boolean write(ServerHandler handler, Object packet){
		return true;
	}
}
