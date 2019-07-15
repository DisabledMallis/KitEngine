package com.DisabledMallis.KitEngine.KitManager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.Main;

public class KitStats {
	
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	/*
	 * Count all the kits
	 */
	public static int allKitsAmount() {
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		int i = 0;
		for (@SuppressWarnings("unused") File kit : KitsDir.listFiles()) {
			i++;
		}
		return i;
	}
	
	/*
	 *Count all the kits a player has access to 
	 */
	public static int allowedKitsAmount(Player p) {
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		int i = 0;
		for (File kit : KitsDir.listFiles()) {
			if(p.hasPermission("Kit.Use." + kit.getName())) {
				i++;
			}
		}
		return i;
	}
}
