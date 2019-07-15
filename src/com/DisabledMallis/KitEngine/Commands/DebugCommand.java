package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DisabledMallis.KitEngine.API.Kit;
import com.DisabledMallis.KitEngine.API.KitAPI;

public class DebugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Kit lit = new KitAPI().getKit("LitKit");
		lit.kitPlayer((Player) sender);
		return true;
	}

}
