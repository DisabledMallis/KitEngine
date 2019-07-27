package com.DisabledMallis.KitEngine.KitGui;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Configuration.ConfigHandler;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitUI implements Listener{
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	static ArrayList<Player> pwog = new ArrayList<>();
	static CustomKitUI cku;
	public static void openKitGUI(Player p) {
		/*
		 * DEPRECATED
		 * This equation is no longer used, however, the credit will remain.
		 * Mathematics by Patrick Hines
		 * Slots to add = 9-size
		 * Determine how many slots we need.
		 */
		ConfigHandler ch = new ConfigHandler();
		cku = new CustomKitUI(p);
		Inventory i;
		if(cku.guiExists()) {
			int size = 9*ch.getGuiSize();
			i = Bukkit.createInventory(null, size, new Lang().getText("gui.title"));
			updateGui(p, i);
		}
		else {
			int size = 9*ch.getGuiSize();
			i = Bukkit.createInventory(null, size, new Lang().getText("gui.title"));
			legacyUpdateGui(p, i);
		}
		p.openInventory(i);
	}
	
	public static void guiUpdater() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				for(Player p : pwog) {
					if(p.getOpenInventory().getTitle().compareTo(new Lang().getText("gui.title")) == 0) {
						if(cku.guiExists()) {
							updateGui(p, p.getOpenInventory().getTopInventory());
						}
						else {
							legacyUpdateGui(p, p.getOpenInventory().getTopInventory());
						}
					}
				}
			}
		}, 0, 20);
	}
	static void legacyUpdateGui(Player p, Inventory i) {
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
					i.addItem(cku.getKitItem(kd.getName(), p));
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
	static void updateGui(Player p, Inventory i) {
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		CustomKitUI cku = new CustomKitUI();
		Inventory c = cku.loadGui();
		i.setContents(c.getContents());
		for (File kit : KitsDir.listFiles()) {
			if(p.hasPermission("Kit.Use." + kit.getName())) {
				i.setItem(cku.getKitSlot(kit.getName()), cku.getKitItem(kit.getName(), p));
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
	
	public static class KitGuiClick implements Listener{
		@EventHandler
		public void onClick(InventoryClickEvent e) {
			if(e.getView().getTitle().equals(new Lang().getText("gui.title"))) {
				e.setCancelled(true);
				if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
					return;
				}
				else {
					Player p = (Player) e.getWhoClicked();
					String name;
					try {
						name = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
					}
					catch (StringIndexOutOfBoundsException ex) {
						name = e.getCurrentItem().getItemMeta().getDisplayName();
					}
					p.performCommand("kit " + name);
				}
			}
		}
	}
}
