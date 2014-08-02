package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import us.zingalicio.handlefish.Constants;
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
		
		Player recipient = null;
		ItemStack item = null;
		Integer amt = 1;
		switch(command.getName().toLowerCase())
		{
			case "item":
				//Gotta be a Player
				if(sender instanceof Player)
				{
					recipient = ((Player) sender);
					if(args.length > 0)
					{
						item = ItemUtil.getItem(plugin, args[0]);
					}
					if(args.length > 1)
					{
						amt = NumberUtil.getInt(args[1]);
					}
				}
				else
				{
					MessageUtil.sendError(plugin, sender, "You foolish console peasant!");
					return true;
				}
				break;
			case "give":
				//Take first arg and pass as a Player, then pass all other args
				if(args.length == 0)
				{
					return false;
				}
				if(args.length > 0)
				{
					recipient = Bukkit.getPlayer(args[0]);
				}
				if(args.length > 1)
				{
					item = ItemUtil.getItem(plugin, args[1]);
				}
				if(args.length > 2)
				{
					if(args[2].equalsIgnoreCase("i") || args[2].equalsIgnoreCase("inf") || args[2].equalsIgnoreCase("infinite"))
					{
						amt = -1;
					}
					amt = NumberUtil.getInt(args[2]);
				}
				break;
			case "more":
				if(args.length == 0)
				{
					if(sender instanceof Player)
					{
						recipient = ((Player) sender);
					}
					else
					{
						MessageUtil.sendError(plugin, sender, "You foolish console peasant!");
						return true;
					}
				}
				else
				{
					recipient = Bukkit.getPlayer(args[0]);
				}
				item = recipient.getItemInHand();
				amt = item.getMaxStackSize() - item.getAmount();
				item.setAmount(1);
				break;
			default:
				return false;
		}
		if(recipient == null)
		{
			MessageUtil.sendError(plugin, sender, "Ain't nobody with a name like that!");
			return true;
		}
		if(item == null)
		{
			MessageUtil.sendError(plugin, sender, "Invalid item.");
			return true;
		}
		if(amt == null || amt < -1 || amt == 0)
		{
			MessageUtil.sendError(plugin, sender, "Invalid amount.");
			return true;
		}
		
		give(sender, recipient, item, amt);
		return true;
		
	}

	private void give(CommandSender sender, Player recipient, ItemStack item, int amt)
	{		
		//Check if giving to self, then check appropriate permissions.
		boolean self;
		if(recipient == sender)
		{
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_GIVE, false))
			{
				return;
			}
			self = true;
		}
		else
		{
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_GIVE_OTHERS, false))
			{
				return;
			}
			self = false;
		}
		
		//Give one
		if(amt == 1)
		{	
			recipient.getInventory().addItem(item);
			
			//Different messages if giving to self/others.
			if(self)
			{
				MessageUtil.sendMessage(plugin, recipient, "You've been given one " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + ".");
				return;
			}
			else
			{
				MessageUtil.sendMessage(plugin, recipient, NameUtil.getSenderName(sender) + " has given you one " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + ".");
				MessageUtil.sendMessage(plugin, sender, "You've given one " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + " to " + recipient.getDisplayName() + ".");
				return;
			}
		}
		
		//Give infinite
		else if(amt == -1)
		{
			//Check infinite give permissions
			if(self)
			{
				if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_GIVE_INFINITE, false))
				{
					return;
				}
			}
			else
			{
				if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_GIVE_OTHERS_INFINITE, false))
				{
					return;
				}
			}
			
			item.setAmount(-1);
			recipient.getInventory().addItem(item);
			
			//Different messages if giving to self/others.
			if(self)
			{
				MessageUtil.sendMessage(plugin, recipient, "You've been given infinite " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + ".");
				return;
			}
			else
			{
				MessageUtil.sendMessage(plugin, recipient, NameUtil.getSenderName(sender) + " has given you infinite " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s).");
				MessageUtil.sendMessage(plugin, sender, "You've given infinite " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s) to " + recipient.getDisplayName() + ".");
				return;
			}
		}
		
		//Give many
		else if(amt > 0)
		{			
			int extra = ItemUtil.giveMany(item, recipient, amt);
			
			//Different messages for blah blah
			if(self)
			{

				MessageUtil.sendMessage(plugin, recipient, "You've been given " + (amt - extra) + " " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s).");
				return;
			}
			else
			{
				MessageUtil.sendMessage(plugin, recipient, NameUtil.getSenderName(sender) + " has given you " + (amt - extra) + " " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s).");
				MessageUtil.sendMessage(plugin, sender, "You've given " + (amt - extra) + " " + NameUtil.getFullName(plugin, item.getType(), item.getData()) + "(s) to " + recipient.getDisplayName() + ".");
				return;
			}
		}
		else
		{
			MessageUtil.sendError(plugin, sender, "Invalid amount!");
			return;
		}
	}
}
