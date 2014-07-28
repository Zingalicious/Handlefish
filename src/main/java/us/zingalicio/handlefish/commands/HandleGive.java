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

public final class HandleGive implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleGive(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(command.getName().equalsIgnoreCase("item"))
		{
			if(sender instanceof Player)
			{
				if(args.length == 1)
				{
					if(sender.hasPermission("give"))
					{
						ItemStack item = ItemUtil.getItem(plugin, args[0]);
						if(item != null)
						{
							((Player) sender).getInventory().addItem(item);
							MessageUtil.sendMessage(sender, "You've been given one " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + ".");
						}
						else
						{
							MessageUtil.sendError(sender, "Invalid item!");
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
							MessageUtil.sendMessage(sender, "You've been given " + (Integer.parseInt(args[1]) - extra) + " " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s).");
						}
						else
						{
							MessageUtil.sendError(sender, "Invalid amount!");
						}
					}
					else
					{
						MessageUtil.sendError(sender, "Invalid item!");
					}
				}
			}
		}
		return false;
	}

}
