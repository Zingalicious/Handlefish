package us.zingalicio.handlefish.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.zingalicio.handlefish.Handlefish;

public class HandleChat implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleChat(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
