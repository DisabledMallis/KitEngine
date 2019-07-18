package com.DisabledMallis.KitEngine.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitManager.KitData;

/*
 * Kit builder
 * Make kits programatically & safely
 */
public class KitBuilder {
	
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	File fcf;
	FileConfiguration fc;
	
	ArrayList<ItemStack> ali = new ArrayList<>();
	String name;
	public Boolean replace = true;
	Material icon = Material.DIAMOND;
	double Price = 0;
	
	public KitBuilder() {
	}
	public KitBuilder(String name) {
		setName(name);
	}
	public KitBuilder setName(String name) {
		this.name = name;
		fcf = new File(plugin.getDataFolder() + "/Kits/" + name);
		try {
			fcf.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fc = YamlConfiguration.loadConfiguration(fcf);
		return this;
	}
	public KitBuilder setIcon(Material icon) {
		this.icon = icon;
		return this;
	}
	public KitBuilder replaceInventory(Boolean replace) {
		this.replace = replace;
		return this;
	}
	public KitBuilder add(ItemStack i) {
		ali.add(i);
		return this;
	}
	public KitBuilder setReplacing(Boolean i) {
		replace = i;
		return this;
	}
	public KitBuilder addAll(ArrayList<ItemStack> i) {
		ali.addAll(i);
		return this;
	}
	public KitData build() {
		try {
			fc.load(fcf);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		fc.set(name + ".Icon", icon.name());
		fc.set(name + ".addToInventory", !replace);
		fc.set(name + ".Price", Price);
		
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		KitData r = new KitData(this.name);
		return r;
	}

	public boolean hasName() {
		return name != null;
	}
	public String getName() {
		return name;
	}
	
	public boolean cancelBuild() {
		if(fcf != null) {
			if(fcf.exists()) {
				return fcf.delete();
			}
			return true;
		}
		return true;
	}
	
	public boolean hasIcon() {
		return icon != Material.DIAMOND;
	}
	public Material getIcon() {
		return icon;
	}
	
	public KitBuilder setPrice(double price) {
		this.Price = price;
		return this;
	}
	public double getPrice() {
		return this.Price;
	}
}
