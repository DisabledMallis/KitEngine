package com.DisabledMallis.KitEngine.KitGui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.DisabledMallis.KitEngine.Log;

public class kitGuiClick implements Listener{
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equals("§aKits")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
				return;
			}
			else {
				Player p = (Player) e.getWhoClicked();
				String name = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
				new Log(name);
				p.performCommand("kit " + name);
			}
		}
	}
}
