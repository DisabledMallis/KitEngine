package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.API.KitAPI;
import com.DisabledMallis.KitEngine.KitManager.KitData;

public class DebugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		KitAPI api = new KitAPI();
		KitData lit = api.getKit("LitKit");
		api.giveKit(lit, (Player) sender);
		return true;
	}

}
