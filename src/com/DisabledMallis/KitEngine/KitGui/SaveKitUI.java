package com.DisabledMallis.KitEngine.KitGui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.API.KitBuilder;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class SaveKitUI implements Listener{
	
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	static HashMap<Player, KitBuilder> sessions = new HashMap<>(); //store sessions as a kitbuilder
	static HashMap<Player, Purpose> textInput = new HashMap<>(); //if and which player is checking for input text
	
	public static void openSaveKitGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 9, new Lang().getText("gui.savetitle"));
		
		/*
		 * 3, 4, 5 are the slots we will use for data
		 */
		
		ItemStack setNameStack;
		ItemStack setIconStack;
		ItemStack setReplaceStack;
		
		if(sessions.containsKey(p)) {
			KitBuilder kb = sessions.get(p);
			//set name
			setNameStack = new ItemStack(Material.OAK_SIGN);
			ItemMeta setNameMeta = setNameStack.getItemMeta();
			setNameMeta.setDisplayName(new Lang().getText("gui.setting.setName"));
			if(kb.hasName()) {
				ArrayList<String> lore = new ArrayList<>();
				lore.add(kb.getName());
				setNameMeta.setLore(lore);
			}
			setNameStack.setItemMeta(setNameMeta);
			
			//set icon
			if(kb.hasIcon()) {
				setIconStack = new ItemStack(kb.getIcon());
			}
			else {
				setIconStack = new ItemStack(Material.OAK_SIGN);
			}
			ItemMeta setIconMeta = setIconStack.getItemMeta();
			setIconMeta.setDisplayName(new Lang().getText("gui.setting.setIcon"));
			setIconStack.setItemMeta(setIconMeta);
			
			//set add or replace inventory
			setReplaceStack = new ItemStack(Material.OAK_SIGN);
			ItemMeta setReplaceMeta = setReplaceStack.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(new Lang().getText("plugin.yes"));
			setReplaceMeta.setLore(lore);
			setReplaceMeta.setDisplayName(new Lang().getText("gui.setting.setReplace"));
			setReplaceStack.setItemMeta(setReplaceMeta);
		}
		else {
			//set name
			setNameStack = new ItemStack(Material.OAK_SIGN);
			ItemMeta setNameMeta = setNameStack.getItemMeta();
			setNameMeta.setDisplayName(new Lang().getText("gui.setting.setName"));
			setNameStack.setItemMeta(setNameMeta);
			
			//set icon
			setIconStack = new ItemStack(Material.OAK_SIGN);
			ItemMeta setIconMeta = setIconStack.getItemMeta();
			setIconMeta.setDisplayName(new Lang().getText("gui.setting.setIcon"));
			setIconStack.setItemMeta(setIconMeta);
			
			//set add or replace inventory
			setReplaceStack = new ItemStack(Material.OAK_SIGN);
			ItemMeta setReplaceMeta = setReplaceStack.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(new Lang().getText("plugin.yes"));
			setReplaceMeta.setLore(lore);
			setReplaceMeta.setDisplayName(new Lang().getText("gui.setting.setReplace"));
			setReplaceStack.setItemMeta(setReplaceMeta);
			sessions.put(p, new KitBuilder());
		}
		
		//finish
		ItemStack finishStack = new ItemStack(Material.OAK_SIGN);
		ItemMeta finishMeta = finishStack.getItemMeta();
		finishMeta.setDisplayName(new Lang().getText("gui.setting.finish"));
		finishStack.setItemMeta(finishMeta);
		
		i.setItem(3, setNameStack);
		i.setItem(4, setIconStack);
		i.setItem(5, setReplaceStack);
		i.setItem(8, finishStack);
		
		p.openInventory(i);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getView().getTitle().equals(new Lang().getText("gui.savetitle"))) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			KitBuilder kb = sessions.get(p);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
				return;
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setName")) == 0) {
				e.getWhoClicked().sendMessage(new Lang().getText("gui.setting.inputName"));
				textInput.put(p, Purpose.NAME);
				p.closeInventory();
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setIcon")) == 0) {
				e.getWhoClicked().sendMessage(new Lang().getText("gui.setting.inputIcon"));
				textInput.put(p, Purpose.ICON);
				p.closeInventory();
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setReplace")) == 0) {
				if(kb.replace) {
					kb.replace = false;
					ItemMeta setReplaceMeta = e.getCurrentItem().getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(new Lang().getText("plugin.no"));
					setReplaceMeta.setLore(lore);
					e.getCurrentItem().setItemMeta(setReplaceMeta);
				}else {
					kb.replace = true;
					ItemMeta setReplaceMeta = e.getCurrentItem().getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(new Lang().getText("plugin.yes"));
					setReplaceMeta.setLore(lore);
					e.getCurrentItem().setItemMeta(setReplaceMeta);
				}
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.finish")) == 0) {
				if(kb.hasName()) {
					KitData k = kb.build();
					k.saveContents(p);
					sessions.remove(p);
				}
				else {
					p.sendMessage(new Lang().getText("kit.unfinished"));
				}
			}
			new Log(e.getCurrentItem().getItemMeta().getDisplayName());
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		KitBuilder kb = sessions.get(p);
		if(textInput.containsKey(p)) {
			if(textInput.get(p) == Purpose.NAME) {
				kb.setName(e.getMessage());
				e.setCancelled(true);
				new BukkitRunnable() {
					@Override
					public void run() {
						openSaveKitGUI(p);
					}
				}.runTaskLater(plugin, 1);
				textInput.remove(p);
			}
			else if(textInput.get(p) == Purpose.ICON) {
				kb.setIcon(Material.valueOf(e.getMessage().toUpperCase()));
				e.setCancelled(true);
				new BukkitRunnable() {
					@Override
					public void run() {
						openSaveKitGUI(p);
					}
				}.runTaskLater(plugin, 1);
				textInput.remove(p);
			}
		}
	}
	
	public enum Purpose {
		NAME,
		ICON
	}
}
