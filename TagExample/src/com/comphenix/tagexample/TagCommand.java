package com.comphenix.tagexample;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comphenix.taghelper.TagHelperMod;

public class TagCommand implements CommandExecutor {

	public static String COMMAND_REFRESH = "refresh";
	
	private Server server;
	private TagHelperMod tagHelper;
	
	public TagCommand(TagHelperMod tagHelper, Server server) {
		this.tagHelper = tagHelper;
		this.server = server;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (label.equalsIgnoreCase(COMMAND_REFRESH)) {
			if (args.length == 1) {
				
				Player candidate = server.getPlayer(args[0]);
				
				// Did we get a player?
				if (candidate != null) {
					
					tagHelper.refreshPlayer(candidate);
					sender.sendMessage("Refreshing " + args[0] + " for nearby players.");
					return true;
					
				} else {
					List<Player> matches = server.matchPlayer(args[0]);
					
					// Find close matches
					sender.sendMessage("Could not find '" + args[0] + "'");
					
					if (matches.size() > 0)
						sender.sendMessage("Did you mean '" + matches.get(0).getDisplayName() + "'");
				}
				
			} else {
				sender.sendMessage("Error: Insufficient parameters.");
			}
		}
		
		// Didn't work
		return false;
	}

}
