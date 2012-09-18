package com.comphenix.taghelper;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.google.common.base.Objects;

/**
 * Invoked when a player's name tag is being sent to a client.
 * 
 * @author Kristian
 */
public class ReceiveNameTagEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player observer;
	private Player watched;
	
	private String tag;
	private String originalTag;
	
	public ReceiveNameTagEvent(Player observer, Player watched, String tag) {
		super();
		this.observer = observer;
		this.watched = watched;
		this.tag = tag;
		this.originalTag = tag;
	}

	/**
	 * Retrieve the player who is receiving the name tag.
	 * @return The observer whose receiving the name tag.
	 */
	public Player getObserver() {
		return observer;
	}

	/**
	 * Retrieve the player whose name tag is being sent.
	 * @return The observed player.
	 */
	public Player getWatched() {
		return watched;
	}

	/**
	 * Retrieve the name tag that will actually be sent.
	 * @return The name tag that will end up being sent.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets the name tag to send.
	 * @param tag - the new name tag.
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Retrieve the original, unaltered name tag that was going to be sent.
	 * @return The original name tag.
	 */
	public String getOriginalTag() {
		return originalTag;
	}
	
	/**
	 * Retrieve the name tag as it actually will be received by the client. 
	 * The tag is trimmed to 16 characters.
	 * @return The final result of the modified name tag.
	 */
	public String getTrimmedTag() {
		if (tag != null && tag.length() > 16)
			return tag.substring(0, 16);
		else
			return tag;
	}

	/**
	 * Determine whether or not the name tag has been modified.
	 * @return TRUE if it has been modified by a plugin, FALSE otherwis.e
	 */
	public boolean isModified() {
		return !Objects.equal(originalTag, tag);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
