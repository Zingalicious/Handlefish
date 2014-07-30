package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.handlefish.Handlefish;

public final class HandleTime implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleTime(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) 
	{
		boolean success;
		switch(command.getName().toLowerCase())
		{
			case "time":
				success = time(sender, args);
				break;
			case "ptime":
				success = ptime(sender, args);
				break;
			default:
				success = false;
				break;
		}
		return success;
	}
	
	private boolean time(CommandSender sender, String[] args)
	{
		return true;
	}
	
	private boolean ptime(CommandSender sender, String[] args)
	{
		return true;
	}

}
