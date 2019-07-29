package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.HybridLib.Hybrid;
import com.DisabledMallis.HybridLib.Hybrid.HBL;
import com.DisabledMallis.HybridLib.Hybrid.LavaVersion;
import com.DisabledMallis.KitEngine.KitGui.CustomKitUI;
import com.DisabledMallis.KitEngine.Language.Lang;

public class EditKitCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("kit.editgui")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				HBL lib = new Hybrid().new HBL();
				if(lib.isLava(LavaVersion.MC1_12_2)) {
					p.sendMessage(new Lang().getText("error.lavagui"));
				}
				else {
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
