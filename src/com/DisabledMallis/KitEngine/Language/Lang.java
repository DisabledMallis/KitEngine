package com.DisabledMallis.KitEngine.Language;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.DisabledMallis.KitEngine.Main;

/*
 * Wrapper class for the KitEngine language configuration
 */
public class Lang {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	File fcf;
	FileConfiguration fc;
	
	public Lang() {
		fcf = new File(plugin.getDataFolder() + "/lang.yml");
		fc = YamlConfiguration.loadConfiguration(fcf);
	}
	
	public String getText(String id) {
		fc = YamlConfiguration.loadConfiguration(fcf);
		if(fc.getString(id) != null) {
			if(fc.isSet("prefix")) {
				return fc.getString(id).replace('&', '§').replaceAll("%prefix%", fc.getString("prefix"));
			}
			return fc.getString(id).replace('&', '§');
		}
		else {
			return id;
		}
	}
}
