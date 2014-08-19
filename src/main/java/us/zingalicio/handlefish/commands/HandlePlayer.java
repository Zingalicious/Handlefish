package us.zingalicio.handlefish.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.StoredMessages;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NumberUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

import static us.zingalicio.handlefish.Keys.*;

public class HandlePlayer implements CommandExecutor
{
	private Handlefish plugin;
	private static final String CHAT_PLUGIN_PREFIX = "songlantern";
	private static final String SET_PREFIX = ".set.prefix";
	private static final String SET_SUFFIX = ".set.suffix";
	private static final String SET_DISPLAY_NAME = ".set.displayname";
	private static final String SET_JOIN_MESSAGE = ".set.joinmessage";
	
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
						MessageUtil.paginate(sender, options, NumberUtil.getInt(args[0]));
						return true;
					}
					else
					{
						MessageUtil.paginate(sender, options, 1);
						return true;
					}
				}
				
				String[] argz;
				if(args.length > 1)
				{
					argz = Arrays.copyOfRange(args, 1, args.length);
				}
				else
				{
					argz = null;
				}
				switch(args[0].toLowerCase())
				{
				case "suffix":
					if(argz == null)
					{
						setOption((Player) sender, null, user, CHAT_PLUGIN_PREFIX + SET_SUFFIX, "suffix", StoredMessages.CHECKED_SUFFIX);
					}
					else
					{
						setOption((Player) sender, argz, user, CHAT_PLUGIN_PREFIX + SET_SUFFIX, "suffix", StoredMessages.SET_SUFFIX);
					}
					break;
				case "prefix":
					if(argz == null)
					{
						setOption((Player) sender, null, user, CHAT_PLUGIN_PREFIX + SET_PREFIX, "prefix", StoredMessages.CHECKED_PREFIX);
					}
					else
					{
						setOption((Player) sender, argz, user, CHAT_PLUGIN_PREFIX + SET_PREFIX, "prefix", StoredMessages.SET_PREFIX);
					}
					break;
				case "displayname": case "dispname":
					if(argz == null)
					{
						setOption((Player) sender, null, user, CHAT_PLUGIN_PREFIX + SET_DISPLAY_NAME, "OPTION_DISPLAY_NAME", StoredMessages.CHECKED_DISPLAY_NAME);
					}
					else
					{
						setOption((Player) sender, argz, user, CHAT_PLUGIN_PREFIX + SET_DISPLAY_NAME, OPTION_DISPLAY_NAME, StoredMessages.SET_DISPLAY_NAME);
					}
					break;
				case "joinmessage":
					if(argz == null)
					{
						setOption((Player) sender, null, user, CHAT_PLUGIN_PREFIX + SET_JOIN_MESSAGE, "OPTION_JOIN_MESSAGE", StoredMessages.CHECKED_JOIN_MESSAGE);
					}
					else
					{
						setOption((Player) sender, argz, user, CHAT_PLUGIN_PREFIX + SET_JOIN_MESSAGE, OPTION_JOIN_MESSAGE, StoredMessages.SET_JOIN_MESSAGE);
					}
					break;
				case "clearsuffix":	
					args[0] = null;
					setOption((Player) sender, args, user, CHAT_PLUGIN_PREFIX + SET_SUFFIX, "suffix", StoredMessages.CLEARED_SUFFIX);
					break;
				case "clearprefix":
					args[0] = null;
					setOption((Player) sender, args, user, CHAT_PLUGIN_PREFIX + SET_PREFIX, "prefix", StoredMessages.CLEARED_PREFIX);
					break;
				case "cleardisplayname":
					args[0] = null;
					setOption((Player) sender, args, user, CHAT_PLUGIN_PREFIX + SET_DISPLAY_NAME, OPTION_DISPLAY_NAME, StoredMessages.CLEARED_DISPLAY_NAME);
					break;
				case "clearjoinmessage":
					args[0] = null;
					setOption((Player) sender, args, user, CHAT_PLUGIN_PREFIX + SET_JOIN_MESSAGE, OPTION_JOIN_MESSAGE, StoredMessages.CLEARED_JOIN_MESSAGE);
					break;
				case "flight": case "fly":
					flight((Player) sender, argz, user);
					break;
				case "buildmode": case "bm": case "build":
					buildMode((Player) sender, argz, user);
					break;
				case "flightspeed": case "flyspeed": case "flyingspeed":
					flySpeed((Player) sender, argz, user);
					break;
				case "walkspeed": case "walkingspeed": case "runspeed":
					walkSpeed((Player) sender, argz, user);
					break;
				default:
					return false;
				}
				return true;
			}
			MessageUtil.sendError(plugin, sender, StoredMessages.NO_CONSOLE.selfMessage(plugin));
			return false;
		}
		else if(command.getName().equalsIgnoreCase("yeah"))
		{
			//Other commands
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void setOption(Player sender, String[] args, PermissionUser user, String permission, String option, StoredMessages message)
	{
		if(args != null)
		{
			Bukkit.getLogger().log(Level.INFO, args.toString() + "" + args[0]);
			if(!PermissionsUtil.checkPermission(sender, permission, false))
			{
				return;
			}
			String s = "";
			if(args[0] == null)
			{
				s = null;
			}
			else
			{
				for(String a : args)
				{
					s = s.concat(a + " ");
				}
				s = s.trim();
			}
			if(option.equalsIgnoreCase("prefix"))
			{
				user.setPrefix(s, null);
			}
			else if(option.equalsIgnoreCase("suffix"))
			{
				user.setSuffix(s, null);
			}
			else
			{
				user.setOption(option, s);
			}
			String m = message.selfMessage(plugin).replace("%new", s);
			MessageUtil.sendMessage(plugin, sender, m);
			return;
		}
		else
		{
			String q;
			if(option.equalsIgnoreCase("prefix"))
			{
				q = user.getPrefix();
			}
			else if(option.equalsIgnoreCase("suffix"))
			{
				q = user.getSuffix();
			}
			else
			{
				q = user.getOption(option, sender.getWorld().getName());
			}
			String m = message.selfMessage(plugin).replace("%current", q);
			MessageUtil.sendMessage(plugin, sender, m);
			return;
		}
	}
	
	private void flight(Player sender, String[] args, PermissionUser user)
	{
		if(args == null)
		{
			if(sender.getAllowFlight())
			{
				HandleMovement.setFlight(plugin, sender, sender, false);
				return;
			}
			else
			{
				HandleMovement.setFlight(plugin, sender, sender, true);
				return;
			}
		}
		else if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("t"))
		{
			HandleMovement.setFlight(plugin, sender, sender, true);
		}
		else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("f"))
		{
		HandleMovement.setFlight(plugin, sender, sender, false);
		}
	}
	
	private void buildMode(Player sender, String[] args, PermissionUser user)
	{
		if(args == null)
		{
			if(user.getOptionBoolean(OPTION_BUILD_MODE, sender.getWorld().getName(), false))
			{
				HandleMovement.setBuildMode(plugin, sender, sender, false);
				return;
			}
			else
			{
				HandleMovement.setBuildMode(plugin, sender, sender, true);
				return;
			}
		}
		else if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("t"))
		{
			HandleMovement.setBuildMode(plugin, sender, sender, true);
		}
		else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("f"))
		{
		HandleMovement.setBuildMode(plugin, sender, sender, false);
		}
	}
	
	private void flySpeed(Player sender, String[] args, PermissionUser user)
	{
		if(args != null)
		{
			if(args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("default"))
			{
				HandleMovement.resetFlySpeed(plugin, sender, sender);
				return;
			}
			Float speed = NumberUtil.getFloat(args[0]);
			if(speed != null)
			{
				HandleMovement.setFlySpeed(plugin, sender, sender, speed);
				return;
			}
			else
			{
				MessageUtil.sendError(plugin, sender, "Not a valid value.  Use '" + ChatColor.WHITE + "reset" + ChatColor.RED + "' or a number between -1 and 1.");
			}
		}
		else
		{
			String message = StoredMessages.CHECKED_FLIGHT_SPEED.selfMessage(plugin).
					replace("%speed", Float.toString(sender.getFlySpeed()));
			MessageUtil.sendMessage(plugin, sender, message);
		}
	}
	
	private void walkSpeed(Player sender, String[] args, PermissionUser user)
	{
		if(args != null)
		{
			if(args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("default"))
			{
				HandleMovement.resetWalkSpeed(plugin, sender, sender);
				return;
			}
			Float speed = NumberUtil.getFloat(args[0]);
			if(speed != null)
			{
				HandleMovement.setWalkSpeed(plugin, sender, sender, speed);
				return;
			}
			else
			{
				MessageUtil.sendError(plugin, sender, "Not a valid value.  Use '" + ChatColor.WHITE + "reset" + ChatColor.RED + "' or a number between -1 and 1.");
			}
		}
		else
		{
			String message = StoredMessages.CHECKED_WALK_SPEED.selfMessage(plugin).
					replace("%speed", Float.toString(sender.getFlySpeed()));
			MessageUtil.sendMessage(plugin, sender, message);
		}
	}
}
