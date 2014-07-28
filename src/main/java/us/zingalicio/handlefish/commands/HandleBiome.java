package us.zingalicio.handlefish.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.EvansUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;

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
			Location loc = ((Player) sender).getLocation();
			World w = ((Player) sender).getWorld();
			Biome b = w.getBiome(loc.getBlockX(), loc.getBlockZ());
			MessageUtil.sendMessage(sender, "You are in a " + NameUtil.format(b.name()) + " biome.");
			Player evans = EvansUtil.getEvans();
			if(evans != null)
			{
				MessageUtil.sendMessage(evans, "Evans someone just checked which Biome they are in and you suck.");
			}
			
		}
		else
		{
			MessageUtil.sendMessage(sender, "Hitler coded nothing wrong!"); //Hitler also coded nothing right.  Hitler didn't code.
		}                                                                   //That's what YOU think, you sheep!
		return true;
	}

}