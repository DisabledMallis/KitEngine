package com.DisabledMallis.KitEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

		File lang = new File(getDataFolder() + "/lang.yml");
		File kitDir = new File(getDataFolder() + "/Kits/");
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		if(!kitDir.exists()) {
			kitDir.mkdir();
		}
		if(!lang.exists()) {
			new Log("lang.yml...");
			InputStream stream = getResource("lang.yml");
			copy(stream, lang.getPath());
			new Log("lang.yml!");
		}
		
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
	
	
	/**
     * Copy a file from source to destination.
     *
     * @param source
     *        the source
     * @param destination
     *        the destination
     * @return True if succeeded , False if not
     * 
     * from https://stackoverflow.com/questions/10308221/how-to-copy-file-inside-jar-to-outside-the-jar
     * by https://stackoverflow.com/users/4970079/goxr3plus
     * 
     * thx homie
     */
	public static boolean copy(InputStream source , String destination) {
        boolean succeess = true;

        new Log("Copying ->" + source + "\n\tto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
            succeess = false;
        }

        return succeess;

    }
}
