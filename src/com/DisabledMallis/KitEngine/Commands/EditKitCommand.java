package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.KitGui.CustomKitUI;
import com.DisabledMallis.KitEngine.Language.Lang;

public class EditKitCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("kit.editgui")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				CustomKitUI cku = new CustomKitUI(p);
				if(args.length < 1) {
					try {
						p.openInventory(cku.loadGui());
					}
					catch(NullPointerException e) {
						p.performCommand("editkitgui new");
					}
				}
				else {
					if(args[0].equalsIgnoreCase("new")) {
						p.openInventory(cku.newGui());
					}
					if(args[0].equalsIgnoreCase("delete")) {
						cku.delete();
					}
				}
			}
			else {
				sender.sendMessage(new Lang().getText("error.notaplayer"));
			}
		}
		else {
			sender.sendMessage(new Lang().getText("error.permission"));
		}
		return true;
	}
}
