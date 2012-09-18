package com.comphenix.taghelper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.reflect.StructureModifier;

public class TagPacketHandler extends PacketAdapter {
	
	private Logger logger;
	private PluginManager pluginManager;
	
	/**
	 * Construct a packet handler with the given parent plugin.
	 * @param plugin - parent plugin.
	 */
	public TagPacketHandler(Plugin plugin, PluginManager pluginManager, Logger logger) {
		super(plugin, ConnectionSide.SERVER_SIDE, Packets.Server.NAMED_ENTITY_SPAWN);
		this.pluginManager = pluginManager;
		this.logger = logger;
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
		
		if (!event.isCancelled() && event.getPacketID() == Packets.Server.NAMED_ENTITY_SPAWN) {
			
			PacketContainer packet = event.getPacket();
			StructureModifier<String> text = packet.getSpecificModifier(String.class);
			
			try {
				String tag = text.read(0);
				Player observer = event.getPlayer();
				Entity watched = packet.getEntityModifier(observer.getWorld()).read(0);
				
				if (watched instanceof Player) {
					ReceiveNameTagEvent nameTagEvent = new ReceiveNameTagEvent(event.getPlayer(), (Player) watched, tag);
					pluginManager.callEvent(nameTagEvent);

					if (nameTagEvent.isModified()) {
						// Trim excess
						tag = nameTagEvent.getTrimmedTag();
						
						// Uh, ok.
						if (tag == null)
							tag = "";
						text.write(0, tag);
					}
				
				} else {
					// Might as well notify about this
					logger.log(Level.WARNING, "Cannot find entity id " + 
							packet.getSpecificModifier(int.class).read(0));
				}
				
			} catch (FieldAccessException e) {
				logger.log(Level.SEVERE, "Cannot read field.", e);
			}	
		}
	}
}
