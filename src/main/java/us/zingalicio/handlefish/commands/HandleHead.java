package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.cordstone.ZingPlugin;
import us.zingalicio.handlefish.Handlefish;

public final class HandleHead implements CommandExecutor
{
	ZingPlugin plugin;
	
	public HandleHead(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
