package us.zingalicio.handlefish.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.StoredMessages;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NumberUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

public class HandlePlayer implements CommandExecutor
{
	private Handlefish plugin;
	private static final String CHAT_PLUGIN_PREFIX = "songlantern";
	private static final String SET_PREFIX = ".set.prefix";
	private static final String SET_SUFFIX = ".set.suffix";
	private static final String SET_DISPLAY_NAME = ".set.displayname";
	
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
				
				String[] argz = (args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null);
				
				switch(args[0].toLowerCase())
				{
				case "suffix":
					suffix((Player) sender, argz, user);
					break;
				case "prefix":
					prefix((Player) sender, argz, user);
					break;
				case "clearsuffix":	
					clearSuffix((Player) sender, user);
					break;
				case "clearprefix":
					clearPrefix((Player) sender, user);
					break;
				case "displayname": case "dispname":
					displayName((Player) sender, argz, user);
					break;
				case "cleardisplayname":
					clearDisplayName((Player) sender, user);
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
	
	private void suffix(Player sender, String[] args, PermissionUser user)
	{
		if(args != null)
		{
			if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_SUFFIX, false))
			{
				return;
			}
			String suffix = "";
			for(String a : args)
			{
				suffix = suffix.concat(a + " ");
			}
			suffix = suffix.trim();
			user.setSuffix(suffix, null);
			String message = StoredMessages.SET_SUFFIX.selfMessage(plugin).
					replace("%suffix", ChatColor.RESET + suffix + ChatColor.YELLOW);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
		else
		{
			String suffix = user.getSuffix();
			String message = StoredMessages.SET_SUFFIX.selfMessage(plugin).
					replace("%suffix", ChatColor.RESET + suffix + ChatColor.YELLOW);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
	}
	
	private void prefix(Player sender, String[] args, PermissionUser user)
	{
		if(args != null)
		{
			if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_PREFIX, false))
			{
				return;
			}
			String prefix = "";
			for(String a : args)
			{
				prefix = prefix.concat(a + " ");
			}
			prefix = prefix.trim();
			user.setSuffix(prefix, null);
			String message = StoredMessages.SET_PREFIX.selfMessage(plugin).
					replace("%prefix", ChatColor.RESET + prefix + ChatColor.YELLOW);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
		else
		{
			String prefix = user.getPrefix();
			String message = StoredMessages.SET_PREFIX.selfMessage(plugin).
					replace("%prefix", ChatColor.RESET + prefix + ChatColor.YELLOW);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
	}
	
	private void clearSuffix(Player sender, PermissionUser user)
	{
		if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_SUFFIX, false))
		{
			return;
		}
		user.setSuffix(null, null);
		MessageUtil.sendMessage(plugin, sender, StoredMessages.CLEAR_SUFFIX.selfMessage(plugin));
		return;
	}
	
	private void clearPrefix(Player sender, PermissionUser user)
	{
		if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_PREFIX, false))
		{
			return;
		}
		user.setPrefix(null, null);
		MessageUtil.sendMessage(plugin, sender, StoredMessages.CLEAR_PREFIX.selfMessage(plugin));
		return;
	}

	private void displayName(Player sender, String[] args, PermissionUser user)
	{
		if(args != null)
		{
			if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_DISPLAY_NAME, false))
			{
				return;
			}
			String displayName = "";
			for(String a : args)
			{
				displayName = displayName.concat(a);
			}
			displayName = displayName.trim();
			user.setOption(Constants.OPTION_DISPLAY_NAME, displayName);
			String message = StoredMessages.SET_DISPLAY_NAME.selfMessage(plugin).replace("%displayname", displayName);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
		else
		{
			String displayName = ((Player) sender).getDisplayName();
			String message = StoredMessages.SET_DISPLAY_NAME.selfMessage(plugin).replace("%displayname", displayName);
			MessageUtil.sendMessage(plugin, sender, message);
			return;
		}
	}

	private void clearDisplayName(Player sender, PermissionUser user)
	{
		if(!PermissionsUtil.checkPermission(sender, CHAT_PLUGIN_PREFIX + SET_DISPLAY_NAME, false))
		{
			return;
		}
		user.setOption(Constants.OPTION_DISPLAY_NAME, null);
		MessageUtil.sendMessage(plugin, sender, StoredMessages.CLEAR_DISPLAY_NAME.selfMessage(plugin));
		return;
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
			if(user.getOptionBoolean(Constants.OPTION_BUILD_MODE, sender.getWorld().getName(), false))
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
			String message = StoredMessages.SET_FLIGHT_SPEED.selfMessage(plugin).
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
			String message = StoredMessages.SET_WALK_SPEED.selfMessage(plugin).
					replace("%speed", Float.toString(sender.getFlySpeed()));
			MessageUtil.sendMessage(plugin, sender, message);
		}
	}
}
