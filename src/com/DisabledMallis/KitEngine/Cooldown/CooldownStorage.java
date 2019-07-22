package com.DisabledMallis.KitEngine.Cooldown;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.Main;

public class CooldownStorage {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	File fcf;
	FileConfiguration fc;
	
	public CooldownStorage(Player p) {
		fcf = new File(plugin.getDataFolder() + "/Cooldowns/" + p.getUniqueId() + ".yml");
		fc = YamlConfiguration.loadConfiguration(fcf);
		fc.set("Player_Name", p.getName());
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getCooldown(String kitName) {
		if(fc.isSet(kitName)) {
			return fc.getInt(kitName);
		}
		return 0;
	}
	
	public void setCooldown(String kitName, int cooldown) {
		fc.set(kitName, cooldown);
		try {
			fc.save(fcf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
