package com.DisabledMallis.KitEngine.Cooldown;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Language.Lang;

public class CooldownUpdate extends BukkitRunnable{
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	File fcf = new File(plugin.getDataFolder() + "/Cooldowns/");
	File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
	@Override
	public void run() {
		if(fcf.isDirectory()) {
			for(File f : fcf.listFiles()) {
				FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
				for(File kit : KitsDir.listFiles()) {
					if(fc.isSet(kit.getName())) {
						int time = fc.getInt(kit.getName());
						time -= 1;
						if(!(time < 0)) {
							fc.set(kit.getName(), time);
							try {
								fc.save(f);
								new Log(time);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		else {
			new Log(new Lang().getText("error.cooldownDir"));
			fcf.mkdir();
		}
	}
}
