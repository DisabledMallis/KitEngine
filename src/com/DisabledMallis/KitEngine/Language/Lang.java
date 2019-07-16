package com.DisabledMallis.KitEngine.Language;

import java.io.File;
import java.io.IOException;

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
		if(!fcf.exists()) {
			try {
				fcf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fc.set("error.disable", "if the plugin was disabled due to an error, please report it here: https://github.com/DisabledMallis/KitEngine/issues");
			fc.set("error.permission", "§cYou do not have permission to use this command!");
		}
	}
	
	public String getText(String id) {
		fc = YamlConfiguration.loadConfiguration(fcf);
		if(fc.getString(id) != null) {
			return fc.getString(id);
		}
		else {
			return id;
		}
	}
}
