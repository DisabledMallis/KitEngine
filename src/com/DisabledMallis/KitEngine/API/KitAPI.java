package com.DisabledMallis.KitEngine.API;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.DisabledMallis.KitEngine.Economy.Eco;
import com.DisabledMallis.KitEngine.KitManager.KitData;

public class KitAPI {
	/*
	 * KitEngine API class.
	 * Reference this class to safely work with KitEngine in your own plugins.
	 */
	public KitAPI() {
	}
	
	public KitData getKit(String name) {
		return new KitData(name);
	}
	public KitBuilder createKit() {
		return new KitBuilder();
	}
	
	public void giveKit(KitData kit, Player player) {
		if(kit.getReplace()) {
			player.getInventory().setContents(kit.getContentsArray());
		}
		else {
			for(ItemStack i : kit.getContents()) {
				if(i==null) {}
				else {
					player.getInventory().addItem(i);
				}
			}
		}
	}
	
	public void sellKit(KitData kit, Player player) {
		Eco.chargePlayer(player, kit.getPrice());
		giveKit(kit, player);
	}
}
