package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
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
					if(!PermissionsUtil.checkPermission(sender, "handlefish.movement.buildmode", false))
					{
						return true;
					}
					PermissionUser user = PermissionsEx.getUser((Player) sender);
					if(user.getOptionBoolean("buildmode.enabled", ((Player) sender).getWorld().getName(), false))
					{
						setBuildMode(plugin, sender, ((Player) sender), false);
					}
					else
					{
						setBuildMode(plugin, sender, ((Player) sender), true);
					}
				}
				else
				{
					MessageUtil.sendError(plugin, sender, "U R FOOL");
					return true;
				}
			}
			else
			{
				Player player = Bukkit.getPlayer(args[0]);
				if(player == null)
				{
					MessageUtil.sendError(plugin, sender, "Ain't nobody with a name like that!");
					return true;
				}
			}
		}
		return false;
	}

	public static void setFlight(Handlefish plugin, CommandSender sender, Player player, boolean b)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			user.setOption("flight", b + "", player.getWorld().getName());
			player.setAllowFlight(b);
			return;
		}
		else
		{
			if(PermissionsUtil.checkPermission(sender, "handlefish.movement.flight", false))
			{
				user.setOption("flight", b + "", player.getWorld().getName());
				player.setAllowFlight(b);
				if(sender == player)
				{
					MessageUtil.sendMessage(plugin, sender, "Flight toggled " + b + ".");
				}
				else
				{
					MessageUtil.sendMessage(plugin, sender, "Flight of " + player.getDisplayName() + " toggled " + b + ".");
					MessageUtil.sendMessage(plugin, player, "Flight toggled " + b + " by " + sender.getName() + ".");
				}
				return;
			}
		}
	}
	
	public static void setWalkSpeed(Handlefish plugin, CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(PermissionsUtil.checkPermission(sender, "handlefish.movement.walkspeed", false))
		{
			if(-1 <= speed && speed <= 1)
			{
				user.setOption("walkspeed", Float.toString(speed), player.getWorld().getName());
				player.setWalkSpeed(speed);
				if(sender == player)
				{
					MessageUtil.sendMessage(plugin, sender, "Walk speed set to " + speed + ".");
				}
				else
				{
					MessageUtil.sendMessage(plugin, sender, "Set the walk speed of " + player.getName() + " to " + speed + ".");
					MessageUtil.sendMessage(plugin, player, "Walk speed set to " + speed + " by " + sender.getName() + ".");
				}
				return;
			}
			else
			{
				MessageUtil.sendError(plugin, sender, "Invalid value.  Valid values are -1 to 1.");
				return;
			}
		}
		return;
	}
	
	public static void resetWalkSpeed(Handlefish plugin, CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			user.setOption("walkspeed", null, player.getWorld().getName());
			player.setWalkSpeed(DEFAULT_WALK_SPEED);
			return;
		}
		if(PermissionsUtil.checkPermission(sender, "handlefish.movement.walkspeed", false))
		{
			if(sender == player)
			{
				MessageUtil.sendMessage(plugin, sender, "Walk speed reset.");
			}
			else
			{
				MessageUtil.sendMessage(plugin, sender, "Reset the walk speed of " + player.getName() + ".");
				MessageUtil.sendMessage(plugin, player, "Walk speed reset by " + sender.getName() + ".");
			}
		}
	}
	
	public static void setFlySpeed(Handlefish plugin, CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(PermissionsUtil.checkPermission(sender, "handlefish.movement.flyspeed", false))
		{
			if(-1 <= speed && speed <= 1)
			{
				user.setOption("flyspeed", Float.toString(speed), player.getWorld().getName());
				player.setFlySpeed(speed);
				if(sender == player)
				{
					MessageUtil.sendMessage(plugin, sender, "Flight speed set to " + speed + ".");
				}
				else
				{
					MessageUtil.sendMessage(plugin, sender, "Set the flight speed of " + player.getName() + " to " + speed + ".");
					MessageUtil.sendMessage(plugin, player, "Flight speed set to " + speed + " by " + sender.getName() + ".");
				}
				return;
			}
			else
			{
				MessageUtil.sendError(plugin, sender, "Invalid value.  Valid values are -1 to 1.");
				return;
			}
		}
		return;
	}
	
	public static void resetFlySpeed(Handlefish plugin, CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			user.setOption("flyspeed", null, player.getWorld().getName());
			player.setFlySpeed(DEFAULT_FLY_SPEED);
			return;
		}
		if(PermissionsUtil.checkPermission(sender, "handlefish.movement.flyspeed", false))
		{
			user.setOption("flyspeed", null, player.getWorld().getName());
			player.setFlySpeed(DEFAULT_FLY_SPEED);
			if(sender == player)
			{
				MessageUtil.sendMessage(plugin, sender, "Flight speed reset.");
			}
			else
			{
				MessageUtil.sendMessage(plugin, sender, "Reset the flight speed of " + player.getName() + ".");
				MessageUtil.sendMessage(plugin, player, "Flight speed reset by " + sender.getName() + ".");
			}
		}
	}
	
	public static void setBuildMode(Handlefish plugin, CommandSender sender, Player player, boolean b)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			if(b)
			{
				user.setOption("buildmode.enabled", true + "", player.getWorld().getName());
				BuildMode buildMode = new BuildMode((Player) sender, plugin);
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				int task = scheduler.scheduleSyncRepeatingTask(plugin, buildMode, 0, 1L);
				buildMode.setId(task);
				return;
			}
			else
			{
				user.setOption("buildmode.enabled", "false", player.getWorld().getName());
			}
		}
		else
		{
			user.setOption("buildmode.enabled", "true", ((Player) sender).getWorld().getName());
			BuildMode buildMode = new BuildMode((Player) sender, plugin);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			int task = scheduler.scheduleSyncRepeatingTask(plugin, buildMode, 0, 1L);
			buildMode.setId(task);
			if(sender == player)
			{
				if(!PermissionsUtil.checkPermission(sender, "handlefish.movement.buildmode", false))
				{
					return;
				}
				MessageUtil.sendMessage(plugin, sender, "Buildmode toggled " + b + ".");
			}
			else
			{
				if(!PermissionsUtil.checkPermission(sender, "handlefish.others.movement.buildmode", false))
				{
					return;
				}
				MessageUtil.sendMessage(plugin, sender, "Buildmode of " + player.getName() + " toggled " + b + ".");
				MessageUtil.sendMessage(plugin, player, "Buildmode toggled " + b + " by " + sender.getName() + ".");
			}
			return;
		}
	}
}
