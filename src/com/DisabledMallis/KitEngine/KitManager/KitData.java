package com.DisabledMallis.KitEngine.KitManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitData {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	boolean valid = true;
	
	String kitName;
	File fcf;
	FileConfiguration fc;
	
	public KitData(String kitName, Player p) {
		this.kitName = kitName;
		
		fcf = new File(plugin.getDataFolder() + "/Kits/" + kitName);
		
		/*
		 * Create the file
		 */
		try {
			fcf.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Ensure it exists
		 */
		if(exists()) {
			fc = YamlConfiguration.loadConfiguration(fcf);
		}
		else {
			valid = false;
		}
	}
	
	/*
	 * Save inventory contents (Including blank slots!)
	 */
	public void saveContents(Player p) {
		ItemStack[] contents = p.getInventory().getContents();
		fc.set(kitName + ".Contents", contents);
		try {
			fc.save(fcf);
			p.sendMessage(new Lang().getText("kit.saved"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Load contents (Including blanks!)
	 */
	public void loadContents(Player p) {
		try {
			if(valid) {
				ItemStack[] contents = new ItemStack[getContents().size()];
				getContents().toArray(contents);
				p.getInventory().setContents(contents);
				p.sendMessage(new Lang().getText("kit.loaded"));
			}
		}
		catch (NullPointerException e) {
			p.sendMessage(new Lang().getText("kit.missing"));
		}
	}
	
	/*
	 * Get the contents from a file
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ItemStack> getContents() {
		return (ArrayList<ItemStack>) fc.get(kitName + ".Contents");
	}
	
	/*
	 * Check if the file exists
	 */
	public boolean exists() {
		return fcf.exists();
	}
}
