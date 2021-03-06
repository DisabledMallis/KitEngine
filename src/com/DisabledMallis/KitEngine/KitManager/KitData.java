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

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitData {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	boolean valid = true;
	
	String kitName;
	Material icon = Material.DIAMOND_BLOCK;
	Boolean addToInventory = false;
	double Price = 0;
	int cooldown;
	
	File fcf;
	FileConfiguration fc;
	
	public KitData(String kitName) {
		this.kitName = kitName;
		
		fcf = new File(plugin.getDataFolder() + "/Kits/" + kitName);
		
		/*
		 * Ensure it exists
		 */
		if(fcf.exists()) {
			fc = YamlConfiguration.loadConfiguration(fcf);
			if(fc.isSet(kitName)) {
				try {
					this.icon = Material.valueOf(fc.getString(this.kitName + ".Icon"));
				}
				catch (IllegalArgumentException | NullPointerException e ) {
					valid = false;
				}
				this.addToInventory = fc.getBoolean(this.kitName + ".addToInventory");
			}
			else {
				new Log(new Lang().getText("error.namechanged"));
				valid = false;
			}
			if(fc.isSet(kitName + ".Cooldown")) {
				this.cooldown = fc.getInt(kitName + ".Cooldown");
			}
			else {
				fc.set(kitName + ".Cooldown", cooldown);
			}
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
		if(isSafe()) {
			ItemStack[] arr;
			if(Bukkit.getBukkitVersion().contains("1.8")) {
				arr = new ItemStack[36];
			} else {
				arr = new ItemStack[40];
			}
			List<?> list = fc.getList(kitName + ".Contents");
			arr = list.toArray(arr);
			return arr;
		}
		else {
			return null;
		}
	}
	
	public void setIcon(Material icon) {
		this.icon = icon;
		fc.set(this.kitName + ".Icon", icon.name());
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setReplace(Boolean replace) {
		this.addToInventory = !replace;
		fc.set(this.kitName + ".addToInventory", !replace);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean getReplace() {
		return !this.addToInventory;
	}

	public Material getIcon() {
		return icon;
	}
	
	/*
	 * Price
	 */
	public Boolean hasPrice() {
		return fc.isSet(kitName + ".Price");
	}
	public void setPrice(double price) {
		this.Price = price;
		fc.set(this.kitName + ".Price", this.Price);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public double getPrice() {
		this.Price = fc.getDouble(kitName + ".Price");
		fc.set(this.kitName + ".Price", this.Price);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.Price;
	}
	
	/*
	 * Cooldown
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
		fc.set(this.kitName + ".Cooldown", this.cooldown);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getCooldown() {
		this.cooldown = fc.getInt(kitName + ".Cooldown");
		fc.set(kitName + ".Cooldown", this.cooldown);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.cooldown;
	}
	
	/*
	 * Delete
	 */
	public boolean delete() {
		return fcf.delete();
	}
	public Boolean isSafe() {
		return valid;
	}

	public String getName() {
		return kitName;
	}
}
