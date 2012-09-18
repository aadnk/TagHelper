package com.comphenix.tagexample;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.taghelper.TagHelperMod;

public class TagExampleMod extends JavaPlugin {
	@Override
	public void onEnable() {
		
		Server server = getServer();
		PluginManager manager = server.getPluginManager();
		
		Logger logger = getLogger();
		
		// Get the tag helper
		TagHelperMod tagHelper = (TagHelperMod) manager.getPlugin("TagHelper");
		
		if (tagHelper != null) {
			
			// Register commands
			getCommand(TagCommand.COMMAND_REFRESH).setExecutor(new TagCommand(tagHelper, server));
			
			// Register events
			manager.registerEvents(new RandomColorListener(), this);
			
		} else {
			logger.log(Level.SEVERE, "Cannot find TagHelper!");
		}
	}
}
