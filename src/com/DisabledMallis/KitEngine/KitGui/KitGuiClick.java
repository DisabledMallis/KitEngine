package com.DisabledMallis.KitEngine.KitGui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitGuiClick implements Listener{
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equals(new Lang().getText("gui.title"))) {
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
