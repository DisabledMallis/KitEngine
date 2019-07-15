package com.DisabledMallis.KitEngine.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitManager.KitData;

public class Kit {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	boolean valid = true;
	
	String kitName;
	File fcf;
	FileConfiguration fc;
	
	public Kit(String kitName) {
		this.kitName = kitName;
		
		fcf = new File(plugin.getDataFolder() + "/Kits/" + kitName);
		fc = YamlConfiguration.loadConfiguration(fcf);
	}
	
	/*
	 * Give player a kit
	 */
	public void kitPlayer(Player p) {
		new KitData(kitName, p).loadContents(p);
	}
	
	/*
	 * Save inventory contents (Including blank slots!)
	 */
	public void saveContents(ArrayList<ItemStack> contents) {
		ItemStack[] icontents = new ItemStack[40];
		icontents = contents.toArray(icontents);
		fc.set(kitName + ".Contents", contents);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
