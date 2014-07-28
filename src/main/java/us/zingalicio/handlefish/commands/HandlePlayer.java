package us.zingalicio.handlefish.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.flight.BuildMode;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NumberUtil;

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
					options.add(ChatColor.YELLOW + "Flight: " + ChatColor.WHITE + user.getOption("flight", world.getName()));
					options.add(ChatColor.YELLOW + "Display Name: " + ChatColor.WHITE + user.getOption("displayname"));
					options.add(ChatColor.YELLOW + "Prefix: " + ChatColor.WHITE + user.getPrefix());
					options.add(ChatColor.YELLOW + "Suffix: " + ChatColor.WHITE + user.getSuffix());
					options.add(ChatColor.YELLOW + "Join Message: " + ChatColor.WHITE + user.getOption("joinmessage"));
					options.add(ChatColor.YELLOW + "Flight Speed: " + ChatColor.WHITE + user.getOption("flyspeed", world.getName()));
					options.add(ChatColor.YELLOW + "Walk Speed: " + ChatColor.WHITE + user.getOption("walkspeed", world.getName()));
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
						MessageUtil.sendMessage(plugin, sender, "Suffix successfully set to '" + ChatColor.RESET + suffix + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String suffix = user.getSuffix();
						MessageUtil.sendMessage(plugin, sender, "Your suffix is currently '" + ChatColor.RESET + suffix + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("clearsuffix"))
				{
					user.setSuffix(null, null);
					MessageUtil.sendMessage(plugin, sender, "Suffix cleared.");
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
						MessageUtil.sendMessage(plugin, sender, "Prefix successfully set to '" + ChatColor.RESET + prefix + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String prefix = user.getPrefix();
						MessageUtil.sendMessage(plugin, sender, "Your prefix is currently '" + ChatColor.RESET + prefix + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("clearprefix"))
				{
					user.setPrefix(null, null);
					MessageUtil.sendMessage(plugin, sender, "Prefix cleared.");
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
						MessageUtil.sendMessage(plugin, sender, "Display name successully set to '" + ChatColor.RESET + displayName + ChatColor.YELLOW + "'.");
						return true;
					}
					else
					{
						String displayName = ((Player) sender).getDisplayName();
						MessageUtil.sendMessage(plugin, sender, "Your display name is currently '" + ChatColor.RESET + displayName + ChatColor.YELLOW + "'.");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("cleardisplayname"))
				{
					user.setOption("displayname", null);
					MessageUtil.sendMessage(plugin, sender, "Display name cleared.");
					return true;
				}
				else if(args[0].equalsIgnoreCase("flyspeed"))
				{
					if(args.length > 1)
					{
						if(args[1].equalsIgnoreCase("reset") || args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("default"))
						{
							HandleMovement.resetFlySpeed(plugin, sender, ((Player) sender));
							return true;
						}
						Float speed = NumberUtil.getFloat(args[1]);
						if(speed != null)
						{
							HandleMovement.setFlySpeed(plugin, sender, ((Player) sender), speed);
							return true;
						}
					}
					MessageUtil.sendMessage(plugin, sender, "Your flight speed is " + Float.toString(((Player) sender).getFlySpeed()) + ".");
				}
				else if(args[0].equalsIgnoreCase("walkspeed"))
				{
					if(args.length > 1)
					{
						if(args[1].equalsIgnoreCase("reset") || args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("default"))
						{
							HandleMovement.resetWalkSpeed(plugin, sender, ((Player) sender));
							return true;
						}
						Float speed = NumberUtil.getFloat(args[1]);
						if(speed != null)
						{
							HandleMovement.setWalkSpeed(plugin, sender, ((Player) sender), speed);
							return true;
						}
					}
					MessageUtil.sendMessage(plugin, sender, "Your walk speed is " + Float.toString(((Player) sender).getWalkSpeed()) + ".");
				}
				else if(args[0].equalsIgnoreCase("flight"))
				{
					if(((Player) sender).getAllowFlight())
					{
						HandleMovement.setFlight(plugin, sender, ((Player) sender), false);
					}
					else
					{
						HandleMovement.setFlight(plugin, sender, ((Player) sender), true);
					}
				}
				else if(args[0].equalsIgnoreCase("buildmode"))
				{
					if(user.getOptionBoolean("buildmode.enabled", world.getName(), false))
					{
						user.setOption("buildmode.enabled", "false", ((Player) sender).getWorld().getName());
					}
					else
					{
						user.setOption("buildmode.enabled", "true", ((Player) sender).getWorld().getName());
						BuildMode build = new BuildMode((Player) sender, plugin);
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						int task = scheduler.scheduleSyncRepeatingTask(plugin, build, 0, 1L);
						build.setId(task);
					}
				}
			}
		}
		return false;
	}

}
