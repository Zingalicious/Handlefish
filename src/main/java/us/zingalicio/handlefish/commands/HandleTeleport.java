package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.handlefish.Handlefish;

public class HandleTeleport implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleTeleport(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
