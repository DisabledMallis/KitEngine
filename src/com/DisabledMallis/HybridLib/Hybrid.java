package com.DisabledMallis.HybridLib;

import org.bukkit.Bukkit;

/*
 * Hybrid detection class made by DisabledMallis
 * To use in your plugin, simply copy the class.
 * Please include this comment if your source will be public.
 * 
 * Example usage:
   HBL lib = new Hybrid().new HBL();
   if(lib.isKettle()) {
       //Kettle compatible code
   }
 */
public class Hybrid {
	static boolean Kettle;
	static boolean Lava14;
	static boolean Lava;
	
	public static void enableLib() {
		new Log("Loading ForgeBukkitLib...");
		new Log("Server version: " + Bukkit.getVersion());
		new Log("Bukkit version: " + Bukkit.getBukkitVersion());
		try {
			Class.forName("net.minecraftforge.common.ForgeVersion");
			new Log("Found Forge!");
			new Log("Checking for Kettle...");
			try {
				Class.forName("kettlefoundation.kettle.KettleVersionCommand");
				new Log("Kettle found!");
				Kettle = true;
			}
			catch(ClassNotFoundException ex) {
				new Log("Couldn't find Kettle.");
				Kettle = false;
			}
			new Log("Checking for LavaPowered 1.14.4...");
			try {
				Class.forName("org.lavapowered.lava.LavaInternal");
				new Log("LavaPowered 1.14.4 found!");
				Lava14 = true;
			}
			catch(ClassNotFoundException ex) {
				new Log("Couldn't find LavaPowered 1.14.4.");
				Lava14 = false;
			}
			new Log("Checking for Lava...");
			if(Bukkit.getVersion().contains("Lava")) {
				new Log("LavaPowered found!");
				Lava = true;
			} else {
				new Log("Couldn't find LavaPowered.");
				Lava = false;
			}
			if(Kettle == false && Lava14 == false && Lava == false) {
				new Log("Unknown Forge Bukkit hybrid.");
			}
		}
		catch(ClassNotFoundException ex) {
			new Log("Couldn't find Forge.");
		}
	}
	
	public Hybrid() {
	}
	
	public class HBL {
		public HBL() {
		}
		public boolean isKettle() {
			return Kettle;
		}
		public boolean isLava(LavaVersion version) {
			if(version == LavaVersion.MC1_12_2) {
				return Lava;
			} else if (version == LavaVersion.MC1_14_4) {
				return Lava14;
			}
			return false;
		}
	}
	
	public static class Log {
		public Log(Object o) {
			System.out.println("[§bHybridLib§r] " + o);
		}
	}
	public enum LavaVersion {
		MC1_14_4,
		MC1_12_2
	}
}
