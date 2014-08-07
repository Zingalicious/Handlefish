package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.flight.BuildMode;
import us.zingalicio.zinglib.StoredMessages;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

public final class HandleMovement implements CommandExecutor
{
	Handlefish plugin;
	private static float DEFAULT_FLY_SPEED = 0.1F;
	private static float DEFAULT_WALK_SPEED = 0.2F;
	
	public HandleMovement(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) 
	{
		if(command.getName().equalsIgnoreCase("buildmode"))
		{
			if(args.length == 0)
			{
				if(sender instanceof Player)
				{
					PermissionUser user = PermissionsEx.getUser((Player) sender);
					if(user.getOptionBoolean(Constants.OPTION_BUILD_MODE, ((Player) sender).getWorld().getName(), false))
					{
						setBuildMode(plugin, sender, ((Player) sender), false);
						return true;
					}
					else
					{
						setBuildMode(plugin, sender, ((Player) sender), true);
						return true;
					}
				}
				else
				{
					MessageUtil.sendError(plugin, sender, StoredMessages.NO_CONSOLE.selfMessage(plugin));
					return true;
				}
			}
			else
			{
				Player player = Bukkit.getPlayer(args[0]);
				if(player == null)
				{
					MessageUtil.sendError(plugin, sender, StoredMessages.NO_PLAYER.selfMessage(plugin));
					return true;
				}
				PermissionUser user = PermissionsEx.getUser(player);
				if(user.getOptionBoolean(Constants.OPTION_BUILD_MODE, player.getWorld().getName(), false))
				{
					setBuildMode(plugin, sender, player, false);
					return true;
				}
				else
				{
					setBuildMode(plugin, sender, player, true);
					return true;
				}
			}
		}
		if(command.getName().equalsIgnoreCase("fly"))
		{
			Player player;
			Boolean b = null;
			if(args.length == 0)
			{
				if(sender instanceof Player)
				{
					player = (Player) sender;
				}
				else
				{
					MessageUtil.sendError(plugin, sender, StoredMessages.NO_CONSOLE.selfMessage(plugin));
					return true;
				}
			}
			else
			{
				player = Bukkit.getPlayer(args[0]);
			}
			
			if(args.length > 1 && (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("on")))
			{
				b = true;
			}
			else if(args.length > 1 && (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("off")))
			{
				b = false;
			}
			
			if(b != null)
			{
				setFlight(plugin, sender, player, b);
				return true;
			}
			
			PermissionUser user = PermissionsEx.getUser((Player) sender);
			if(user.getOptionBoolean(Constants.OPTION_FLIGHT, player.getWorld().getName(), false))
			{
				setFlight(plugin, sender, player, false);
				return true;
			}
			else
			{
				setFlight(plugin, sender, player, true);
				return true;
			}
		}
		return false;
	}

	public static void setFlight(Handlefish plugin, CommandSender sender, Player player, boolean b)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == player)
		{
			if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT, false))
			{
				return;
			}
			String message;
			if(b)
			{
				message = StoredMessages.SET_FLIGHT_ON.selfMessage(plugin);
			}
			else
			{
				message = StoredMessages.SET_FLIGHT_OFF.selfMessage(plugin);
			}
			MessageUtil.sendMessage(plugin, sender, message);
		}
		else if(sender != null)
		{
			if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT_OTHERS, false))
			{
				return;
			}
			String toMessage;
			String fromMessage;
			if(b)
			{
				toMessage = StoredMessages.SET_FLIGHT_ON.toMessage(plugin).replace("%target", player.getDisplayName());
				fromMessage = StoredMessages.SET_FLIGHT_ON.fromMessage(plugin).replace("%sender", NameUtil.getSenderName(sender));
			}
			else
			{
				toMessage = StoredMessages.SET_FLIGHT_OFF.toMessage(plugin).replace("%target", player.getDisplayName());
				fromMessage = StoredMessages.SET_FLIGHT_OFF.fromMessage(plugin).replace("%sender", NameUtil.getSenderName(sender));
			}
			MessageUtil.sendMessage(plugin, sender, toMessage);
			MessageUtil.sendMessage(plugin, player, fromMessage);
		}
		user.setOption(Constants.OPTION_FLIGHT, b + "", player.getWorld().getName());
		player.setAllowFlight(b);
		return;
	}
	
	public static void setWalkSpeed(Handlefish plugin, CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(-1 <= speed && speed <= 1)
		{
			if(sender == player)
			{
				if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_WALK_SPEED, false))
				{
					return;
				}
				String message = StoredMessages.SET_WALK_SPEED.selfMessage(plugin).
						replace("%speed", speed.toString());
				MessageUtil.sendMessage(plugin, sender, message);
			}
			else if(sender != null)
			{
				if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_WALK_SPEED_OTHERS, false))
				{
					return;
				}
				String toMessage = StoredMessages.SET_WALK_SPEED.toMessage(plugin).
						replace("%speed", speed.toString()).
						replace("%target", player.getDisplayName());
				String fromMessage = StoredMessages.SET_WALK_SPEED.fromMessage(plugin).
						replace("%speed", speed.toString()).
						replace("%sender", NameUtil.getSenderName(sender));
				MessageUtil.sendMessage(plugin, sender, toMessage);
				MessageUtil.sendMessage(plugin, player, fromMessage);
			}
			user.setOption(Constants.OPTION_WALK_SPEED, Float.toString(speed), player.getWorld().getName());
			player.setWalkSpeed(speed);
			return;
		}
		else
		{
			MessageUtil.sendError(plugin, sender, "Invalid value.  Valid values are -1 to 1.");
			return;
		}
	}
	
	public static void resetWalkSpeed(Handlefish plugin, CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == player)
		{
			String message = StoredMessages.SET_WALK_SPEED.selfMessage(plugin).
					replace("%speed", "default");
			MessageUtil.sendMessage(plugin, sender, message);
		}
		else if(sender != null)
		{
			String toMessage = StoredMessages.SET_WALK_SPEED.toMessage(plugin).
					replace("%speed", "default").
					replace("%target", player.getDisplayName());
			String fromMessage = StoredMessages.SET_WALK_SPEED.fromMessage(plugin).
					replace("%speed", "default").
					replace("%sender", NameUtil.getSenderName(sender));
			MessageUtil.sendMessage(plugin, sender, toMessage);
			MessageUtil.sendMessage(plugin, player, fromMessage);
		}
		user.setOption(Constants.OPTION_WALK_SPEED, null, player.getWorld().getName());
		player.setWalkSpeed(DEFAULT_WALK_SPEED);
	}
	
	public static void setFlySpeed(Handlefish plugin, CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		
		if(-1 <= speed && speed <= 1)
		{
			if(sender == player)
			{
				if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT_SPEED, false))
				{
					return;
				}
				String message = StoredMessages.SET_FLIGHT_SPEED.selfMessage(plugin).
						replace("%speed", speed.toString());
				MessageUtil.sendMessage(plugin, sender, message);
			}
			else if(sender != null)
			{
				if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT_SPEED_OTHERS, false))
				{
					return;
				}
				String toMessage = StoredMessages.SET_FLIGHT_SPEED.toMessage(plugin).
						replace("%speed", speed.toString()).
						replace("%target", player.getDisplayName());
				String fromMessage = StoredMessages.SET_FLIGHT_SPEED.fromMessage(plugin).
						replace("%speed", speed.toString()).
						replace("%sender", NameUtil.getSenderName(sender));
				MessageUtil.sendMessage(plugin, sender, toMessage);
				MessageUtil.sendMessage(plugin, player, fromMessage);
			}
			user.setOption(Constants.OPTION_FLIGHT_SPEED, Float.toString(speed), player.getWorld().getName());
			player.setFlySpeed(speed);
			return;
		}
		else
		{
			MessageUtil.sendError(plugin, sender, "Invalid value.  Valid values are -1 to 1.");
			return;
		}
	}
	
	public static void resetFlySpeed(Handlefish plugin, CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == player)
		{
			if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT_SPEED, false))
			{
				return;
			}
			String message = StoredMessages.SET_FLIGHT_SPEED.selfMessage(plugin).
					replace("%speed", "default");
			MessageUtil.sendMessage(plugin, sender, message);
		}
		else if(sender != null)
		{
			if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_FLIGHT_SPEED_OTHERS, false))
			{
				return;
			}
			String toMessage = StoredMessages.SET_FLIGHT_SPEED.toMessage(plugin).
					replace("%speed", "default").
					replace("%target", player.getDisplayName());
			String fromMessage = StoredMessages.SET_FLIGHT_SPEED.fromMessage(plugin).
					replace("%speed", "default").
					replace("%sender", NameUtil.getSenderName(sender));
			MessageUtil.sendMessage(plugin, sender, toMessage);
			MessageUtil.sendMessage(plugin, player, fromMessage);
		}
		user.setOption(Constants.OPTION_FLIGHT_SPEED, null, player.getWorld().getName());
		player.setFlySpeed(DEFAULT_FLY_SPEED);
	}
	
	public static void setBuildMode(Handlefish plugin, CommandSender sender, Player player, boolean b)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == player)
		{
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_BUILD_MODE, false))
			{
				return;
			}
			MessageUtil.sendMessage(plugin, sender, StoredMessages.SET_BUILD_MODE.selfMessage(plugin).replace("%state", b + ""));
		}
		else if(sender != null)
		{
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_BUILD_MODE_OTHERS, false))
			{
				return;
			}
			String toMessage = StoredMessages.SET_BUILD_MODE.toMessage(plugin).
					replace("%target", player.getDisplayName()).
					replace("%state", b + "");
			String fromMessage = StoredMessages.SET_BUILD_MODE.toMessage(plugin).
					replace("%sender", NameUtil.getSenderName(sender)).
					replace("%state", b + "");
			MessageUtil.sendMessage(plugin, sender, toMessage);
			MessageUtil.sendMessage(plugin, player, fromMessage);
		}
		
		if(b)
		{
			user.setOption(Constants.OPTION_BUILD_MODE, "true", ((Player) sender).getWorld().getName());
			BuildMode buildMode = new BuildMode((Player) sender, plugin);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			buildMode.setId(scheduler.scheduleSyncRepeatingTask(plugin, buildMode, 0, 1L));
			return;
		}
		else
		{
			user.setOption(Constants.OPTION_BUILD_MODE, "false", player.getWorld().getName());
			return;
		}
	}
}
