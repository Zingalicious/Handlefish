package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.util.MessageUtil;

public class HandleBiome implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleBiome(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String arg2,
			String[] args) 
	{
		if(sender instanceof Player)
		{
		}
		else{MessageUtil.sendMessage(sender, "Hitler did coded nothing wrong");} return true;
	}

}