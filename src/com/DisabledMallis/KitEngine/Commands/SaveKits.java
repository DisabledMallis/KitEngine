package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.Main;
import com.DisabledMallis.KitEngine.KitManager.KitData;

public class SaveKits implements CommandExecutor{
	
	Main plugin = (Main) Bukkit.getPluginManager().getPlugin("KitEngine");
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("Kit.Save")) {
				if(args.length == 1) {
					String kitName = args[0];
					
					KitData kd = new KitData(kitName, p);
					kd.saveContents(p);
					
				}
				else {
					p.sendMessage("§cUsage: /saveKit <name>");
				}
			}
			else {
				p.sendMessage("§cNo permission!");
			}
		}
		return true;
	}
}
