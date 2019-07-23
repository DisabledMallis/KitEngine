package com.DisabledMallis.KitEngine.Configuration;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.DisabledMallis.KitEngine.Main;

public class ConfigHandler {
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	File configFile = new File(plugin.getDataFolder() + "/config.yml");
	FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	public ConfigHandler() {
	}
	
	public int getGuiSize() {
		return config.getInt("KitsGuiSize");
	}
}
