package com.DisabledMallis.KitEngine.KitGui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.Configuration.ConfigHandler;
import com.DisabledMallis.KitEngine.Cooldown.CooldownStorage;
import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class CustomKitUI {
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	private File fcf;
	private YamlConfiguration fc;
	private Player p;
	private String name = "GUI";
	
	public CustomKitUI(Player p) {
		fcf = new File(plugin.getDataFolder() + "/GUI",name + ".yml");
		fc = YamlConfiguration.loadConfiguration(fcf);
		this.p = p;
	}
	public CustomKitUI() {
		fcf = new File(plugin.getDataFolder() + "/GUI",name + ".yml");
		fc = YamlConfiguration.loadConfiguration(fcf);
	};

	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public void saveGui(Inventory i) throws IOException {
		ArrayList<ItemStack> stacksNoKit = new ArrayList<>();
		ItemStack[] stacks = i.getContents();
		int slot = 0;
		for(ItemStack stack : stacks) {
			slot++;
			if(stack != null) {
				if(stack.hasItemMeta()) {
					String name;
					try {
						name = stack.getItemMeta().getDisplayName().substring(2);
					}
					catch (StringIndexOutOfBoundsException ex) {
						name = stack.getItemMeta().getDisplayName();
					}
					KitData kd = new KitData(name);
					if(!kd.isSafe()) {
						stacksNoKit.add(stack);
					}
					else {
						fc.set("KitData." + name + ".slot", slot-1);
						stacksNoKit.add(null);
					}
				}
				else {
					stacksNoKit.add(stack);
				}
			}
			else {
				stacksNoKit.add(stack);
			}
		}
		fc.set("GUI.Inventory", stacksNoKit);
		fc.save(fcf);
	}
	public Inventory loadGui() {
		ConfigHandler ch = new ConfigHandler();
		int size = 9*ch.getGuiSize();
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) fc.get("GUI.Inventory");
		ItemStack[] arrstacks = new ItemStack[size];
		arrstacks = stacks.toArray(arrstacks);
		Inventory i = Bukkit.createInventory(null, size, new Lang().getText("gui.edittitle"));
		i.setContents(arrstacks);
		File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
		for (File kit : KitsDir.listFiles()) {
			i.setItem(getKitSlot(kit.getName()), getKitItem(kit.getName(), p));
		}
		return i;
	}
	
	public boolean guiExists() {
		return fcf.exists();
	}
	public boolean delete() {
		File f = new File(plugin.getDataFolder() + "/GUI");
		fcf.delete();
		return f.delete();
	}
	
	public ItemStack getKitItem(String name, Player p) {
		KitData kd = new KitData(name);
		ItemStack kitStack = new ItemStack(kd.getIcon());
		ItemMeta kitMeta = kitStack.getItemMeta();
		kitMeta.setDisplayName("§a" + name);
		ArrayList<String> lore = new ArrayList<String>();
		if(Eco.validVault()) {
			if(kd.hasPrice()) {
				lore.add(new Lang().getText("eco.price") + kd.getPrice());
			}
		}
		lore.add(new Lang().getText("kit.cooldown") + kd.getCooldown());
		if(p == null) {
			ItemStack corruptedStack = new ItemStack(Material.BARRIER);
			ItemMeta corruptedMeta = corruptedStack.getItemMeta();
			corruptedMeta.setDisplayName(new Lang().getText("error.corrupted"));
			corruptedStack.setItemMeta(corruptedMeta);
			return corruptedStack;
		}
		CooldownStorage cs = new CooldownStorage(p);
		if(!(cs.getCooldown(name) < 1)) {
			lore.add(new Lang().getText("kit.cooldownRemaining") + cs.getCooldown(name));
		}
		kitMeta.setLore(lore);
		kitStack.setItemMeta(kitMeta);
		return kitStack;
	}
	public int getKitSlot(String name) {
		return fc.getInt("KitData." + name + ".slot");
	}
	
	public Inventory newGui() {
		ConfigHandler ch = new ConfigHandler();
		int size = 9*ch.getGuiSize();
		Inventory i = Bukkit.createInventory(null, size, new Lang().getText("gui.edittitle"));
		KitUI.legacyUpdateGui(p, i);
		return i;
	}
	
	public static class Events implements Listener{
		@EventHandler
		public void onClose(InventoryCloseEvent e) throws IOException {
			Player p = (Player) e.getPlayer();
			
			if(e.getView().getTitle().compareTo(new Lang().getText("gui.edittitle")) == 0) {
				Inventory i = e.getView().getTopInventory();
				CustomKitUI cku = new CustomKitUI(p);
				cku.saveGui(i);
				p.sendMessage(new Lang().getText("gui.saved"));
			}
		}
		@EventHandler
		public void onClick(InventoryClickEvent e) throws IOException {
			Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
			File fcf = new File(plugin.getDataFolder() + "/GUI","GUI.yml");
			YamlConfiguration fc = YamlConfiguration.loadConfiguration(fcf);
			
			if(e.getView().getTitle().compareTo(new Lang().getText("gui.edittitle")) == 0) {
				if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
					return;
				}
				if(e.getAction() == InventoryAction.PLACE_SOME) {
					
				}
				if(e.getCurrentItem().hasItemMeta()) {
					String name;
					try {
						name = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
					}
					catch (StringIndexOutOfBoundsException ex) {
						name = e.getCurrentItem().getItemMeta().getDisplayName();
					}
					KitData kd = new KitData(name);
					if(kd.isSafe()) {
						fc.set("KitData." + name + ".slot", e.getRawSlot()-1);
						fc.save(fcf);
					}
				}
			}
		}
	}
}
