package com.DisabledMallis.KitEngine.KitGui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.API.KitBuilder;
import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.IconGUI.Page;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;
import com.google.common.primitives.Doubles;

public class SaveKitUI implements Listener{
	
	static Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	static HashMap<Player, KitBuilder> sessions = new HashMap<>(); //store sessions as a kitbuilder
	static HashMap<Player, Purpose> textInput = new HashMap<>(); //if and which player is checking for input text
	public static boolean isKettle = false;
	
	public static void openSaveKitGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 9, new Lang().getText("gui.savetitle"));
		
		/*
		 * 3, 4, 5 are the slots we will use for data
		 */
		
		ItemStack setNameStack;
		ItemStack setIconStack;
		ItemStack setReplaceStack;
		ItemStack setCooldownStack;
		ItemStack setPriceStack = null;
		
		if(sessions.containsKey(p)) {
			KitBuilder kb = sessions.get(p);
			//set name
			setNameStack = new ItemStack(Material.SIGN);
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
				setIconStack = new ItemStack(Material.SIGN);
			}
			ItemMeta setIconMeta = setIconStack.getItemMeta();
			setIconMeta.setDisplayName(new Lang().getText("gui.setting.setIcon"));
			setIconStack.setItemMeta(setIconMeta);
			
			//set add or replace inventory
			setReplaceStack = new ItemStack(Material.SIGN);
			ItemMeta setReplaceMeta = setReplaceStack.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(new Lang().getText("plugin.approve"));
			setReplaceMeta.setLore(lore);
			setReplaceMeta.setDisplayName(new Lang().getText("gui.setting.setReplace"));
			setReplaceStack.setItemMeta(setReplaceMeta);
			
			if(Eco.validVault()) {
				//set price
				setPriceStack = new ItemStack(Material.SIGN);
				ItemMeta setPriceMeta = setPriceStack.getItemMeta();
				ArrayList<String> priceLore = new ArrayList<String>();
				priceLore.add(new Lang().getText("eco.price") + kb.getPrice());
				setPriceMeta.setLore(priceLore);
				setPriceMeta.setDisplayName(new Lang().getText("gui.setting.setPrice"));
				setPriceStack.setItemMeta(setPriceMeta);
			}
			
			//set cooldown
			setCooldownStack = new ItemStack(Material.SIGN);
			ItemMeta setCooldownMeta = setCooldownStack.getItemMeta();
			ArrayList<String> coolLore = new ArrayList<String>();
			coolLore.add(new Lang().getText("kit.cooldown") + kb.getCooldown());
			setCooldownMeta.setLore(coolLore);
			setCooldownMeta.setDisplayName(new Lang().getText("gui.setting.setCooldown"));
			setCooldownStack.setItemMeta(setCooldownMeta);
		}
		else {
			//set name
			setNameStack = new ItemStack(Material.SIGN);
			ItemMeta setNameMeta = setNameStack.getItemMeta();
			setNameMeta.setDisplayName(new Lang().getText("gui.setting.setName"));
			setNameStack.setItemMeta(setNameMeta);
			
			//set icon
			setIconStack = new ItemStack(Material.SIGN);
			ItemMeta setIconMeta = setIconStack.getItemMeta();
			setIconMeta.setDisplayName(new Lang().getText("gui.setting.setIcon"));
			setIconStack.setItemMeta(setIconMeta);
			
			//set add or replace inventory
			setReplaceStack = new ItemStack(Material.SIGN);
			ItemMeta setReplaceMeta = setReplaceStack.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(new Lang().getText("plugin.approve"));
			setReplaceMeta.setLore(lore);
			setReplaceMeta.setDisplayName(new Lang().getText("gui.setting.setReplace"));
			setReplaceStack.setItemMeta(setReplaceMeta);
			
			if(Eco.validVault()) {
				//set price
				setPriceStack = new ItemStack(Material.SIGN);
				ItemMeta setPriceMeta = setPriceStack.getItemMeta();
				ArrayList<String> priceLore = new ArrayList<String>();
				priceLore.add(new Lang().getText("eco.price") + "0");
				setPriceMeta.setLore(priceLore);
				setPriceMeta.setDisplayName(new Lang().getText("gui.setting.setPrice"));
				setPriceStack.setItemMeta(setPriceMeta);
			}
			
			//set cooldown
			setCooldownStack = new ItemStack(Material.SIGN);
			ItemMeta setCooldownMeta = setCooldownStack.getItemMeta();
			ArrayList<String> coolLore = new ArrayList<String>();
			coolLore.add(new Lang().getText("kit.cooldown") + "0");
			setCooldownMeta.setLore(coolLore);
			setCooldownMeta.setDisplayName(new Lang().getText("gui.setting.setCooldown"));
			setCooldownStack.setItemMeta(setCooldownMeta);
			
			sessions.put(p, new KitBuilder());
		}
		
		//finish
		ItemStack finishStack = new ItemStack(Material.SIGN);
		ItemMeta finishMeta = finishStack.getItemMeta();
		finishMeta.setDisplayName(new Lang().getText("gui.setting.finish"));
		finishStack.setItemMeta(finishMeta);
		
		//cancel
		ItemStack cancelStack = new ItemStack(Material.BARRIER);
		ItemMeta cencelMeta = cancelStack.getItemMeta();
		cencelMeta.setDisplayName(new Lang().getText("gui.setting.cancel"));
		cancelStack.setItemMeta(cencelMeta);
		
		if(Eco.validVault()) {
			i.setItem(2, setNameStack);
			i.setItem(3, setIconStack);
			i.setItem(4, setCooldownStack);
			i.setItem(5, setReplaceStack);
			i.setItem(6, setPriceStack);
		}
		else {
			i.setItem(2, setNameStack);
			i.setItem(3, setIconStack);
			i.setItem(5, setReplaceStack);
			i.setItem(6, setCooldownStack);
		}
		
		i.setItem(8, finishStack);
		i.setItem(0, cancelStack);
		
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
				if(!isKettle) {
					IconSelect.openGui(p);
				}
				else {
					e.getWhoClicked().sendMessage(new Lang().getText("gui.setting.inputIcon"));
					textInput.put(p, Purpose.ICON);
					p.closeInventory();
				}
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setReplace")) == 0) {
				if(kb.replace) {
					kb.replace = false;
					ItemMeta setReplaceMeta = e.getCurrentItem().getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(new Lang().getText("plugin.deny"));
					setReplaceMeta.setLore(lore);
					e.getCurrentItem().setItemMeta(setReplaceMeta);
				}else {
					kb.replace = true;
					ItemMeta setReplaceMeta = e.getCurrentItem().getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(new Lang().getText("plugin.approve"));
					setReplaceMeta.setLore(lore);
					e.getCurrentItem().setItemMeta(setReplaceMeta);
				}
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setPrice")) == 0) {
				e.getWhoClicked().sendMessage(new Lang().getText("gui.setting.inputPrice"));
				textInput.put(p, Purpose.PRICE);
				p.closeInventory();
			}
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.setCooldown")) == 0) {
				e.getWhoClicked().sendMessage(new Lang().getText("gui.setting.inputCooldown"));
				textInput.put(p, Purpose.COOLDOWN);
				p.closeInventory();
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
			else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.setting.cancel")) == 0) {
				sessions.get(p).cancelBuild();
				sessions.remove(p);
				p.sendMessage(new Lang().getText("kit.canceled"));
				p.closeInventory();
			}
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
				try {
					Material mat = Material.valueOf(e.getMessage().toUpperCase());
					if(mat == Material.AIR) {
						throw new NullPointerException();
					}
					kb.setIcon(Material.valueOf(e.getMessage().toUpperCase()));
				}
				catch (IllegalArgumentException | NullPointerException e1) {
					p.sendMessage(new Lang().getText("error.itemmissing"));
				}
				e.setCancelled(true);
				new BukkitRunnable() {
					@Override
					public void run() {
						openSaveKitGUI(p);
					}
				}.runTaskLater(plugin, 1);
				textInput.remove(p);
			}
			else if(textInput.get(p) == Purpose.PRICE) {
				e.setCancelled(true);
				double price = Doubles.tryParse(e.getMessage());
				kb.setPrice(price);
				new BukkitRunnable() {
					@Override
					public void run() {
						openSaveKitGUI(p);
					}
				}.runTaskLater(plugin, 1);
				textInput.remove(p);
			}
			else if(textInput.get(p) == Purpose.COOLDOWN) {
				e.setCancelled(true);
				int cooldown = Integer.parseInt(e.getMessage());
				kb.setCooldown(cooldown);
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
		ICON,
		COOLDOWN,
		PRICE
	}
	
	public static class IconSelect implements Listener{
		
		static ArrayList<Page> pages = new ArrayList<>();
		static HashMap<Player, Integer> currentPage = new HashMap<>();
		
		public static void openGui(Player p) {
			int slot = 0;
			int page = 1;
			Inventory i = Bukkit.createInventory(null, 9*6, new Lang().getText("gui.saveIcon") + " - " + page);
			for(Material m : Material.values()) {
				if(slot > 45) {
					slot++;
					pages.add(new Page(i, page));
					i = Bukkit.createInventory(null, 9*6, new Lang().getText("gui.saveIcon") + " - " + (page + 1));
					slot = 0;
					page++;
				}
				else {
					if(m != null) {
						slot++;
						i.addItem(new ItemStack(m));
					}
				}
			}
			currentPage.put(p, 0);
			p.openInventory(pages.get(0).getInventory());
		}
		
		@EventHandler
		public void onOpen(InventoryOpenEvent e) {
			Player p = (Player) e.getPlayer();
			Inventory i = e.getView().getTopInventory();
			if(e.getView().getTitle().startsWith(new Lang().getText("gui.saveIcon"))) {
				ItemStack nextPageStack = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta nextPageMeta = nextPageStack.getItemMeta();
				nextPageMeta.setDisplayName(new Lang().getText("gui.nextPage"));
				nextPageStack.setItemMeta(nextPageMeta);
				
				ItemStack prevPageStack = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta prevPageMeta = prevPageStack.getItemMeta();
				prevPageMeta.setDisplayName(new Lang().getText("gui.prevPage"));
				prevPageStack.setItemMeta(prevPageMeta);
				
				if(currentPage.get(p) < pages.size() - 1) {
					i.setItem(51, nextPageStack);
				}
				if(currentPage.get(p) > 0) {
					i.setItem(47, prevPageStack);
				}
			}
		}
		@EventHandler
		public void onClick(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if(e.getView().getTitle().startsWith(new Lang().getText("gui.saveIcon"))) {
				e.setCancelled(true);
				if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
					return;
				}
				else if(e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.nextPage")) == 0) {
						currentPage.put(p, currentPage.get((Player) e.getWhoClicked()) + 1);
						p.openInventory(pages.get(currentPage.get(p)).getInventory());
					}
					else if(e.getCurrentItem().getItemMeta().getDisplayName().compareTo(new Lang().getText("gui.prevPage")) == 0) {
						currentPage.put(p, currentPage.get(p) - 1);
						p.openInventory(pages.get(currentPage.get(p)).getInventory());
					}
				}
				else {
					sessions.get(p).setIcon(e.getCurrentItem().getType());
					new BukkitRunnable() {
						@Override
						public void run() {
							openSaveKitGUI(p);
						}
					}.runTaskLater(plugin, 1);
				}
			}
		}
	}
}
