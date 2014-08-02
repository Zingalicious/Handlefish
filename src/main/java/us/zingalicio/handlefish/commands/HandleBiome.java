package us.zingalicio.handlefish.commands;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.EvansUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

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
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_CHECK_BIOME, false))
			{
				return true;
			}
			
			Location loc = ((Player) sender).getLocation();
			Biome b = ((Player) sender).getWorld().getBiome(loc.getBlockX(), loc.getBlockZ());
			MessageUtil.sendMessage(plugin, sender, "You are in a " + NameUtil.format(b.name()) + " biome.");
			Player evans = EvansUtil.getEvans();
			if(evans != null)
			{
				MessageUtil.sendMessage(plugin, evans, "yo evans someone just checked which biome they are in and you suck.");
			}
			
		}
		else
		{
			MessageUtil.sendError(plugin, sender, "Hitler coded nothing wrong!"); //Hitler also coded nothing right.  Hitler didn't code.
		}                                                                   //That's what YOU think, you sheep!
		return true;
	}

}