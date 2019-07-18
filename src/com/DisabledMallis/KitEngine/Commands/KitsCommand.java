package com.DisabledMallis.KitEngine.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitGui.KitUI;

public class KitsCommand implements CommandExecutor{
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("kits.use")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				KitUI.openKitGUI(p);
			}
			else {
				sender.sendMessage("§aAvailable kits:");
				File KitsDir = new File(plugin.getDataFolder() + "/Kits/");
				for (File kit : KitsDir.listFiles()) {
					if(sender.hasPermission("Kit.Use." + kit.getName())) {
						sender.sendMessage("§e - §b" + kit.getName());
					}
				}
			}
		}
		return true;
	}
}
