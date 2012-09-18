package com.comphenix.tagexample;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.comphenix.taghelper.ReceiveNameTagEvent;

public class RandomColorListener implements Listener {

	private Random rnd = new Random();
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onReceiveNameTagEvent(ReceiveNameTagEvent event) {
		// TESTING CODE
		event.setTag(getRandomColor() + event.getTag());
	}
	
	private ChatColor getRandomColor() {
		ChatColor[] list = ChatColor.values();
		return list[rnd.nextInt(list.length)];
	}
}
