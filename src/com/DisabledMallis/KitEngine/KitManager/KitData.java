package com.DisabledMallis.KitEngine.KitManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	Material icon = Material.DIAMOND_BLOCK;
	Boolean addToInventory = false;
	
	File fcf;
	FileConfiguration fc;
	
	public KitData(String kitName) {
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
		
		this.icon = Material.valueOf(fc.getString(this.kitName + ".Icon"));
		this.addToInventory = fc.getBoolean(this.kitName + ".addToInventory");
		
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
	public void saveContents(ArrayList<ItemStack> acontents) {
		ItemStack[] contents = new ItemStack[40];
		acontents.toArray(contents);
		fc.set(kitName + ".Contents", contents);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveContents(ItemStack[] contents) {
		fc.set(kitName + ".Contents", contents);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get the contents from a file
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ItemStack> getContents() {
		return (ArrayList<ItemStack>) fc.get(kitName + ".Contents");
	}
	public ItemStack[] getContentsArray() {
		ItemStack[] arr = new ItemStack[40];
		List<?> list = fc.getList(kitName + ".Contents");
		arr = list.toArray(arr);
		return arr;
	}
	
	/*
	 * Check if the file exists
	 */
	public boolean exists() {
		return fcf.exists();
	}
	
	public void setIcon(Material icon) {
		this.icon = icon;
		fc.set(this.kitName + ".Icon", icon.name());
	}
	
	public void setReplace(Boolean replace) {
		this.addToInventory = !replace;
		fc.set(this.kitName + ".addToInventory", !replace);
	}
	
	public Boolean getReplace() {
		return !this.addToInventory;
	}

	public Material getIcon() {
		return icon;
	}
	
	public boolean delete() {
		return fcf.delete();
	}
}
