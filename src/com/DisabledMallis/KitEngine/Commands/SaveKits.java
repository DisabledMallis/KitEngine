package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitGui.SaveKitUI;
import com.DisabledMallis.KitEngine.Language.Lang;

public class SaveKits implements CommandExecutor{
	
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("Kit.Save")) {
				SaveKitUI.openSaveKitGUI(p);
			}
			else {
				p.sendMessage(new Lang().getText("error.permission"));
			}
		}
		return true;
	}
}
