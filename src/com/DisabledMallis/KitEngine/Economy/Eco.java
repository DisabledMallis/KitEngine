package com.DisabledMallis.KitEngine.Economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.DisabledMallis.KitEngine.Log;
import com.DisabledMallis.KitEngine.Language.Lang;

import net.milkbowl.vault.economy.Economy;

public class Eco {
	
	public static Economy getEconomy() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
            new Log(new Lang().getText("error.noeco"));
        }
		return rsp.getProvider();
	}
	
	public static void chargePlayer(OfflinePlayer p, double price) {
		getEconomy().withdrawPlayer(p, price);
		p.getPlayer().sendMessage(new Lang().getText("eco.charged") + price);
	} public static void chargePlayer(Player p, double price) {
		getEconomy().withdrawPlayer(p, price);
		p.sendMessage(new Lang().getText("eco.charged") + price);
	}
	
	public static boolean validVault() {
		return Bukkit.getPluginManager().isPluginEnabled("Vault");
	}
}
