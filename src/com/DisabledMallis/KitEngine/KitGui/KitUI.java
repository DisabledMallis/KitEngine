package com.DisabledMallis.KitEngine.KitGui;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitUI {
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	public static void openKitGUI(Player p) {
		/*
		 * DEPRECATED
		 * This equation is no longer used, however, the credit will remain.
		 * Mathematics by Patrick Hines
		 * Slots to add = 9-size
		 * Determine how many slots we need.
		 */
		int size = 9*6;
		Inventory i = Bukkit.createInventory(null, size, new Lang().getText("gui.title"));
		
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		for (File kit : KitsDir.listFiles()) {
			if(p.hasPermission("Kit.Use." + kit.getName())) {
				KitData kd = new KitData(kit.getName());
				ItemStack kitStack = new ItemStack(kd.getIcon());
				ItemMeta kitMeta = kitStack.getItemMeta();
				kitMeta.setDisplayName("§a" + kit.getName());
				if(Eco.validVault()) {
					if(kd.hasPrice()) {
						ArrayList<String> priceLore = new ArrayList<String>();
						priceLore.add(new Lang().getText("eco.price") + kd.getPrice());
						kitMeta.setLore(priceLore);
					}
				}
				kitStack.setItemMeta(kitMeta);
				i.addItem(kitStack);
			}
		}
		
		p.openInventory(i);
	}
}
