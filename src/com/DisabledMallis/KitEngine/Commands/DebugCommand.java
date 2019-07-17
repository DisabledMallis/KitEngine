package com.DisabledMallis.KitEngine.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.DisabledMallis.KitEngine.Language.Lang;

public class DebugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(new Lang().getText(args[1]));
		return true;
	}

}
