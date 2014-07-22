package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.handlefish.Handlefish;

public class HandleBiome implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleBiome(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String arg2,
			String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}