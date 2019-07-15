package com.DisabledMallis.KitEngine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.DisabledMallis.KitEngine.Commands.DebugCommand;
import com.DisabledMallis.KitEngine.Commands.KitCommand;
import com.DisabledMallis.KitEngine.Commands.KitsCommand;
import com.DisabledMallis.KitEngine.Commands.SaveKits;
import com.DisabledMallis.KitEngine.KitGui.kitGuiClick;

public class Main extends JavaPlugin{
	public void onEnable() {
		new Log("Starting KitEngine...");
		
		getCommand("savekit").setExecutor(new SaveKits());
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("kits").setExecutor(new KitsCommand());
		getCommand("debug").setExecutor(new DebugCommand());
		
		Bukkit.getPluginManager().registerEvents(new kitGuiClick(), this);
		
		new Log("KitEngine Loaded & Running!");
	}
	public void onDisable() {
		new Log("if the plugin was disabled due to an error, please report it here: [REPO URL]");
	}
}
