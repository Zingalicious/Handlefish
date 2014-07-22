package us.zingalicio.handlefish.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.util.MessageUtil;
import us.zingalicio.handlefish.util.NumberUtil;

public class HandlePlayer implements CommandExecutor
{
	Handlefish plugin;
	
	public HandlePlayer(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) 
	{
		if(command.getName().equalsIgnoreCase("self"))
		{
			if(sender instanceof Player)
			{
				PermissionUser user = PermissionsEx.getUser((Player) sender);
				World world = ((Player) sender).getWorld();
				if(args.length == 0 || NumberUtil.getInt(args[0]) != null)
				{
					sender.sendMessage(ChatColor.GOLD + "-------{ " + ChatColor.WHITE + "All About You!" + ChatColor.GOLD + " }-------");
					List<String> options = new ArrayList<>();
					options.add(ChatColor.YELLOW + "Godmode: " + ChatColor.WHITE + (user.getOptionBoolean("god", world.getName(), false) ? "true" : "false"));
					options.add(ChatColor.YELLOW + "Time Format: " + ChatColor.WHITE + (user.getOptionBoolean("time", null, false) ? "24 Hour" : "12 Hour"));
					options.add(ChatColor.YELLOW + "Display Name: " + ChatColor.WHITE + user.getOption("displayname"));
					options.add(ChatColor.YELLOW + "Prefix: " + ChatColor.WHITE + user.getPrefix());
					options.add(ChatColor.YELLOW + "Suffix: " + ChatColor.WHITE + user.getSuffix());
					if(args.length > 0)
					{
						MessageUtil.paginate(sender, options, Integer.parseInt(args[0]));
						return true;
					}
					else
					{
						MessageUtil.paginate(sender, options, 1);
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("suffix"))
				{
					if(args.length >= 2)
					{
						int i = 0;
						String suffix = "";
						for(String a : args)
						{
							if(i != 0)
							{
								suffix = suffix.concat(a + " ");
							}
							else
							{
								i = 1;
							}
						}
						suffix = suffix.trim();
						user.setSuffix(suffix, null);
						MessageUtil.sendMessage(sender, "Suffix successfully set to '" + ChatColor.RESET + suffix + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String suffix = user.getSuffix();
						MessageUtil.sendMessage(sender, "Your suffix is currently '" + ChatColor.RESET + suffix + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("clearsuffix"))
				{
					user.setSuffix(null, null);
					MessageUtil.sendMessage(sender, "Suffix cleared.");
					return true;
				}
				else if(args[0].equalsIgnoreCase("prefix"))
				{
					if(args.length >= 2)
					{
						int i = 0;
						String prefix = "";
						for(String a : args)
						{
							if(i != 0)
							{
								prefix = prefix.concat(a + " ");
							}
							else
							{
								i = 1;
							}
						}
						prefix = prefix.trim();
						user.setPrefix(prefix, null);
						MessageUtil.sendMessage(sender, "Prefix successfully set to '" + ChatColor.RESET + prefix + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String prefix = user.getPrefix();
						MessageUtil.sendMessage(sender, "Your prefix is currently '" + ChatColor.RESET + prefix + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("clearprefix"))
				{
					user.setPrefix(null, null);
					MessageUtil.sendMessage(sender, "Prefix cleared.");
					return true;
				}
				else if(args[0].equalsIgnoreCase("displayname"))
				{
					if(args.length >= 2)
					{
						int i = 0;
						String displayName = "";
						for(String a : args)
						{
							if(i != 0)
							{
								displayName = displayName.concat(a + " ");
							}
							else
							{
								i = 1;
							}
						}
						displayName = displayName.trim();
						user.setOption("displayname", displayName);
						((Player) sender).setDisplayName(displayName);
						MessageUtil.sendMessage(sender, "Display name successully set to '" + ChatColor.RESET + displayName + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String displayName = ((Player) sender).getDisplayName();
						MessageUtil.sendMessage(sender, "Your display name is currently '" + ChatColor.RESET + displayName + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("cleardisplayname"))
				{
					user.setOption("displayname", null);
					MessageUtil.sendMessage(sender, "Display name cleared.");
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "no");
				}
			}
		}
		return false;
	}

}
