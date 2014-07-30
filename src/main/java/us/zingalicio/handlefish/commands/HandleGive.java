package us.zingalicio.handlefish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.ItemUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;
import us.zingalicio.zinglib.util.NumberUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

public final class HandleGive implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleGive(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		boolean success;
		switch(command.getName().toLowerCase())
		{
			case "item":
				success = item(sender, args);
				break;
			default:
				success = false;
				break;
		}
		return success;
	}
	
	private boolean item(CommandSender sender, String[] args)
	{	
		if(args.length == 0)
		{
			return false;
		}
		if(sender instanceof Player)
		{
			if(args.length == 1)
			{
				if(PermissionsUtil.checkPermission(sender, "handlefish.give", false))
				{
					ItemStack item = ItemUtil.getItem(plugin, args[0]);
					if(item != null)
					{
						((Player) sender).getInventory().addItem(item);
						MessageUtil.sendMessage(plugin, sender, "You've been given one " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + ".");
					}
					else
					{
						MessageUtil.sendError(plugin, sender, "Invalid item!");
					}
				}
				return true;
			}
			if(args.length == 2)
			{
				ItemStack item = ItemUtil.getItem(plugin, args[0]);
				if(item != null)
				{
					if(NumberUtil.getInt(args[1]) != null)
					{
						int extra = ItemUtil.giveMany(item, (Player) sender, NumberUtil.getInt(args[1]));
						MessageUtil.sendMessage(plugin, sender, "You've been given " + (Integer.parseInt(args[1]) - extra) + " " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s).");
						return true;
					}
					else
					{
						MessageUtil.sendError(plugin, sender, "Invalid amount!");
						return true;
					}
				}
				else
				{
					MessageUtil.sendError(plugin, sender, "Invalid item!");
					return true;
				}
			}
		}
		if(args.length == 1)
		{
			return false;
		}
		return false;
	}

}
