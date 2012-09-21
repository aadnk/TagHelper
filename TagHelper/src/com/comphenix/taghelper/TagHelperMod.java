package com.comphenix.taghelper;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import com.google.common.collect.Lists;

public class TagHelperMod extends JavaPlugin {
	
	private ProtocolManager manager;
	private Logger logger;
	
	@Override
	public void onEnable() {
		
		logger = getLogger();
		manager = ProtocolLibrary.getProtocolManager();
		
		// Register our handler
		manager.addPacketListener(new TagPacketHandler(this, getServer().getPluginManager(), logger));
	}

	/**
	 * Refreshes the watched player for nearby observers.
	 * @param observer - the observer whose client needs to be refreshed.
	 */
	public void refreshPlayer(Player watched) {
		
		int view = getServer().getViewDistance() * 16;
		List<Player> observers = Lists.newArrayList();
		
		// Get nearby observers
		for (Player observer : getPlayersWithin(watched, view)) {
			if (!observer.equals(watched) && observer.canSee(watched)) {
				observers.add(observer);
			}
		}
		
		// Send a new packet
		refreshPlayer(watched, observers);
	}
		
	/**
	 * Refreshes the tag of the watched player for a list of observers.
	 * @param watched - the watched player.
	 * @param observers - the observers that needs to be refreshed.
	 */
	public void refreshPlayer(Player watched, List<Player> observers) {
		
		try {
			manager.updateEntity(watched, observers);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot update entity.", e);
		}
	}
	
	// Find players within a certain number of blocks
	private List<Player> getPlayersWithin(Player player, int distance) {

		List<Player> res = Lists.newArrayList();
		int d2 = distance * distance;
		
		for (Player p : getServer().getOnlinePlayers()) {
			if (p.getWorld() == player.getWorld() && 
				p.getLocation().distanceSquared(player.getLocation()) <= d2) {
				
				res.add(p);
			}
		}
		return res;
	}
	
	@Override
	public void onDisable() {
		// We don't need to clean up. ProtocolManager does it for us.
	}
}
