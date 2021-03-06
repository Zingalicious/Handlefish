package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.zingalicio.handlefish.Keys;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.cordstone.StoredMessages;
import us.zingalicio.cordstone.ZingPlugin;
import us.zingalicio.cordstone.util.EvansUtil;
import us.zingalicio.cordstone.util.MessageUtil;
import us.zingalicio.cordstone.util.NameUtil;
import us.zingalicio.cordstone.util.PermissionsUtil;

public class HandleBiome implements CommandExecutor
{
	ZingPlugin plugin;
	
	public HandleBiome(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String arg2,
			String[] args) 
	{
		if(!PermissionsUtil.checkPermission(sender, Keys.PERMISSION_CHECK_BIOME, false))
		{
			return true;
		}
		else if(args.length == 0)
		{
			if(sender instanceof Player)
			{
				
				Location loc = ((Player) sender).getLocation();
				Biome b = ((Player) sender).getWorld().getBiome(loc.getBlockX(), loc.getBlockZ());
				String biomeMessage = StoredMessages.CHECKED_BIOME.selfMessage()
						.replace("%biome", ChatColor.WHITE + NameUtil.format(b.name()) + ChatColor.YELLOW);
				MessageUtil.sendMessage(plugin, sender, biomeMessage);
				{
									Player evans = EvansUtil.getEvans();
									if(evans != null)
									{
										MessageUtil.sendMessage(plugin, evans, "yo evans someone just checked which biome they are in and you suck.");
									}
				}
				return true;
			}
			else
			{
				MessageUtil.sendError(plugin, sender, "Hitler coded nothing wrong!"); //Hitler also coded nothing right.  Hitler didn't code.
				return true;															//That's what you think, you sheep!
			}     
		}
		else
		{
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null)
			{
				Biome b = target.getWorld().getBiome(target.getLocation().getBlockX(), target.getLocation().getBlockZ());
				String biomeMessage = StoredMessages.CHECKED_BIOME.toMessage(target)
						.replace("%biome", ChatColor.WHITE + NameUtil.format(b.name()) + ChatColor.YELLOW);
				MessageUtil.sendMessage(plugin, sender, biomeMessage);
				return true;
			}
			else
			{
				MessageUtil.sendMessage(plugin, sender, StoredMessages.NO_PLAYER.selfMessage());
				return true;
			}
		}
	}

}