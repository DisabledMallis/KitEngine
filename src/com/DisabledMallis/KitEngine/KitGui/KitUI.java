package com.DisabledMallis.KitEngine.KitGui;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitManager.KitStats;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitUI {
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	public static void openKitGUI(Player p) {
		/*
		 * Mathematics by Patrick Hines
		 * Slots to add = 9-size
		 * Determine how many slots we need.
		 */
		int kc = KitStats.allowedKitsAmount(p);
		int x = 9-kc;
		int size = kc + x;
		new Log("size:" + size + " x:" + x + " kc:" + kc);
		Inventory i = Bukkit.createInventory(null, size, new Lang().getText("gui.title"));
		
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		for (File kit : KitsDir.listFiles()) {
			if(p.hasPermission("Kit.Use." + kit.getName())) {
				ItemStack kitStack = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta kitMeta = kitStack.getItemMeta();
				kitMeta.setDisplayName("§a" + kit.getName());
				kitStack.setItemMeta(kitMeta);
				i.addItem(kitStack);
			}
		}
		
		p.openInventory(i);
	}
}
