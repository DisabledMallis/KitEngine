package com.DisabledMallis.KitEngine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.DisabledMallis.KitEngine.Commands.DebugCommand;
import com.DisabledMallis.KitEngine.Commands.KitCommand;
import com.DisabledMallis.KitEngine.Commands.KitsCommand;
import com.DisabledMallis.KitEngine.Commands.SaveKits;
import com.DisabledMallis.KitEngine.KitGui.KitGuiClick;
import com.DisabledMallis.KitEngine.KitGui.SaveKitUI;
import com.DisabledMallis.KitEngine.Language.Lang;

public class Main extends JavaPlugin{
	public void onEnable() {
		new Log(new Lang().getText("plugin.loading"));
		
		getCommand("savekit").setExecutor(new SaveKits());
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("kits").setExecutor(new KitsCommand());
		getCommand("debug").setExecutor(new DebugCommand());
		
		Bukkit.getPluginManager().registerEvents(new KitGuiClick(), this);		
		Bukkit.getPluginManager().registerEvents(new SaveKitUI(), this);
		
		new Log(new Lang().getText("plugin.loaded"));
	}
	public void onDisable() {
		new Log(new Lang().getText("error.disable"));
	}
}
