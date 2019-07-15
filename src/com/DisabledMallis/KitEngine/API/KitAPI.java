package com.DisabledMallis.KitEngine.API;

import org.bukkit.Bukkit;

import com.DisabledMallis.KitEngine.Main;

public class KitAPI {
	
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	/*
	 * KitEngine API class.
	 * Reference this class to safely work with KitEngine in your own plugins.
	 */
	public KitAPI() {
	}
	
	public Kit getKit(String name) {
		return new Kit(name);
	}
}
