package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.handlefish.Handlefish;

public final class HandleGive implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleGive(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(command.getName().equalsIgnoreCase("item"))
		{
		}
		return false;
	}

}
