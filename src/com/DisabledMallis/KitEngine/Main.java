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
import com.DisabledMallis.KitEngine.Commands.EditKitCommand;
import com.DisabledMallis.KitEngine.Commands.KitCommand;
import com.DisabledMallis.KitEngine.Commands.KitsCommand;
import com.DisabledMallis.KitEngine.Commands.SaveKits;
import com.DisabledMallis.KitEngine.Cooldown.CooldownUpdate;
import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.Kettle.KettleCompat;
import com.DisabledMallis.KitEngine.KitGui.CustomKitUI;
import com.DisabledMallis.KitEngine.KitGui.KitUI;
import com.DisabledMallis.KitEngine.KitGui.SaveKitUI;
import com.DisabledMallis.KitEngine.Language.Lang;
import com.DisabledMallis.KitEngine.Stats.MetricsLite;

public class Main extends JavaPlugin{
	
	KettleCompat kc;
	
	public void onEnable() {
		new Log(new Lang().getText("plugin.loading"));
		
		new MetricsLite(this);
		
		File lang = new File(getDataFolder() + "/lang.yml");
		File config = new File(getDataFolder() + "/config.yml");
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
		if(!config.exists()) {
			new Log("config.yml...");
			InputStream stream = getResource("config.yml");
			copy(stream, config.getPath());
			new Log("config.yml!");
		}
		
		getCommand("savekit").setExecutor(new SaveKits());
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("kits").setExecutor(new KitsCommand());
		getCommand("debug").setExecutor(new DebugCommand());
		getCommand("editkitgui").setExecutor(new EditKitCommand());
		
		Bukkit.getPluginManager().registerEvents(new KitUI.KitGuiClick(), this);
		Bukkit.getPluginManager().registerEvents(new SaveKitUI(), this);
		Bukkit.getPluginManager().registerEvents(new KitUI(), this);
		Bukkit.getPluginManager().registerEvents(new SaveKitUI.IconSelect(), this);
		Bukkit.getPluginManager().registerEvents(new CustomKitUI.Events(), this);
		
		KitUI.guiUpdater();
		
		if(Eco.validVault()) {
			new Log("Vault found!");
		} else {
			new Log("Vault not found, Vault features disabled.");
		}
		
		new CooldownUpdate().runTaskTimer(this, 0, 20);
		
		new Log("Checking for Forge...");
		try {
			Class.forName("net.minecraftforge.common.ForgeVersion");
			new Log("Forge found! Assuming server is a Kettle server. Enabling Kettle compatability.");
			kc = new KettleCompat();
		}
		catch(ClassNotFoundException ex) {
			new Log("Server doesn't have Forge, compatability is not necessary.");
		}
		
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
