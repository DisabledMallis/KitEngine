package com.DisabledMallis.KitEngine.KitGui;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Configuration.ConfigHandler;
import com.DisabledMallis.KitEngine.Cooldown.CooldownStorage;
import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitUI implements Listener{
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	static ArrayList<Player> pwog = new ArrayList<>();
	public static void openKitGUI(Player p) {
		/*
		 * DEPRECATED
		 * This equation is no longer used, however, the credit will remain.
		 * Mathematics by Patrick Hines
		 * Slots to add = 9-size
		 * Determine how many slots we need.
		 */
		ConfigHandler ch = new ConfigHandler();
		int size = 9*ch.getGuiSize();
		Inventory i = Bukkit.createInventory(null, size, new Lang().getText("gui.title"));
		updateGui(p, i);
		p.openInventory(i);
	}
	
	public static void guiUpdater() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				for(Player p : pwog) {
					if(p.getOpenInventory().getTitle().compareTo(new Lang().getText("gui.title")) == 0) {
						updateGui(p, p.getOpenInventory().getTopInventory());
					}
				}
			}
		}, 0, 20);
	}
	static void updateGui(Player p, Inventory i) {
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		for(ItemStack item : i.getContents()) {
			if(item != null) {
				i.removeItem(item);
			}
		}
		for (File kit : KitsDir.listFiles()) {
			if(p.hasPermission("Kit.Use." + kit.getName())) {
				KitData kd = new KitData(kit.getName());
				if(kd.isSafe()) {
					ItemStack kitStack = new ItemStack(kd.getIcon());
					ItemMeta kitMeta = kitStack.getItemMeta();
					kitMeta.setDisplayName("§a" + kit.getName());
					ArrayList<String> lore = new ArrayList<String>();
					if(Eco.validVault()) {
						if(kd.hasPrice()) {
							lore.add(new Lang().getText("eco.price") + kd.getPrice());
						}
					}
					lore.add(new Lang().getText("kit.cooldown") + kd.getCooldown());
					CooldownStorage cs = new CooldownStorage(p);
					if(!(cs.getCooldown(kit.getName()) < 1)) {
						lore.add(new Lang().getText("kit.cooldownRemaining") + cs.getCooldown(kit.getName()));
					}
					kitMeta.setLore(lore);
					kitStack.setItemMeta(kitMeta);
					i.addItem(kitStack);
				}
				else {
					ItemStack corruptedStack = new ItemStack(Material.BARRIER);
					ItemMeta corruptedMeta = corruptedStack.getItemMeta();
					corruptedMeta.setDisplayName(new Lang().getText("error.corrupted"));
					corruptedStack.setItemMeta(corruptedMeta);
					i.addItem(corruptedStack);
				}
			}
		}
	}
	
	@EventHandler
	public void openGui(InventoryOpenEvent e) {
		pwog.add((Player) e.getPlayer());
	}
	@EventHandler
	public void openGui(InventoryCloseEvent e) {
		pwog.remove((Player) e.getPlayer());
	}
}
