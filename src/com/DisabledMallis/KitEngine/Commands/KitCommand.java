package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.API.KitAPI;
import com.DisabledMallis.KitEngine.KitGui.KitUI;
import com.DisabledMallis.KitEngine.KitManager.KitData;
import com.DisabledMallis.KitEngine.Language.Lang;

public class KitCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		KitAPI api = new KitAPI();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("kit.use")) {
				if(args.length == 0) {
					KitUI.openKitGUI(p);
				}
				else if(args.length == 1) {
					String kitName = args[0];
					if(p.hasPermission("Kit.Use." + kitName)) {
						KitData kd = new KitData(kitName);
						api.giveKit(kd, p);
					}
					else {
						p.sendMessage(new Lang().getText("error.permission"));
					}
				}
				else {
					new Lang().getText("error.usage.kit");
				}
			}
			else {
				p.sendMessage(new Lang().getText("error.permission"));
			}
		}
		return true;
	}
}
