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
import us.zingalicio.handlefish.util.MessageUtil;
import us.zingalicio.handlefish.util.PermissionsUtil;

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
					if(user.getOptionBoolean("buildmode.enabled", ((Player) sender).getWorld().getName(), false))
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
				else
				{
					return false;
				}
			}
		}
		return false;
	}

	public static void setFlight(CommandSender sender, Player player, boolean b)
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
			if(PermissionsUtil.checkPermission(sender, "movement.flight"))
			{
				user.setOption("flight", b + "", player.getWorld().getName());
				player.setAllowFlight(b);
				if(sender == player)
				{
					MessageUtil.sendMessage(sender, "Flight toggled " + b + ".");
				}
				else
				{
					MessageUtil.sendMessage(sender, "Flight of " + player.getName() + " toggled " + b + ".");
					MessageUtil.sendMessage(sender, "Flight toggled " + b + " by " + sender.getName() + ".");
				}
				return;
			}
		}
	}
	
	public static void setWalkSpeed(CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(PermissionsUtil.checkPermission(sender, "movement.walkspeed"))
		{
			if(-1 <= speed && speed <= 1)
			{
				user.setOption("walkspeed", Float.toString(speed), player.getWorld().getName());
				player.setWalkSpeed(speed);
				if(sender == player)
				{
					MessageUtil.sendMessage(sender, "Walk speed set to " + speed + ".");
				}
				else
				{
					MessageUtil.sendMessage(sender, "Set the walk speed of " + player.getName() + " to " + speed + ".");
					MessageUtil.sendMessage(sender, "Walk speed set to " + speed + " by " + sender.getName() + ".");
				}
				return;
			}
			else
			{
				MessageUtil.sendError(sender, "Invalid value.  Valid values are -1 to 1.");
				return;
			}
		}
		return;
	}
	
	public static void resetWalkSpeed(CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			user.setOption("walkspeed", null, player.getWorld().getName());
			player.setWalkSpeed(DEFAULT_WALK_SPEED);
			return;
		}
		if(PermissionsUtil.checkPermission(sender, "movement.walkspeed"))
		{
			if(sender == player)
			{
				MessageUtil.sendMessage(sender, "Walk speed reset.");
			}
			else
			{
				MessageUtil.sendMessage(sender, "Reset the walk speed of " + player.getName() + ".");
				MessageUtil.sendMessage(sender, "Walk speed reset by " + sender.getName() + ".");
			}
		}
	}
	
	public static void setFlySpeed(CommandSender sender, Player player, Float speed)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(PermissionsUtil.checkPermission(sender, "movement.flyspeed"))
		{
			if(-1 <= speed && speed <= 1)
			{
				user.setOption("flyspeed", Float.toString(speed), player.getWorld().getName());
				player.setFlySpeed(speed);
				if(sender == player)
				{
					MessageUtil.sendMessage(sender, "Flight speed set to " + speed + ".");
				}
				else
				{
					MessageUtil.sendMessage(sender, "Set the flight speed of " + player.getName() + " to " + speed + ".");
					MessageUtil.sendMessage(sender, "Flight speed set to " + speed + " by " + sender.getName() + ".");
				}
				return;
			}
			else
			{
				MessageUtil.sendError(sender, "Invalid value.  Valid values are -1 to 1.");
				return;
			}
		}
		return;
	}
	
	public static void resetFlySpeed(CommandSender sender, Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(sender == null)
		{
			user.setOption("flyspeed", null, player.getWorld().getName());
			player.setFlySpeed(DEFAULT_FLY_SPEED);
			return;
		}
		if(PermissionsUtil.checkPermission(sender, "movement.flyspeed"))
		{
			user.setOption("flyspeed", null, player.getWorld().getName());
			player.setFlySpeed(DEFAULT_FLY_SPEED);
			if(sender == player)
			{
				MessageUtil.sendMessage(sender, "Flight speed reset.");
			}
			else
			{
				MessageUtil.sendMessage(sender, "Reset the flight speed of " + player.getName() + ".");
				MessageUtil.sendMessage(sender, "Flight speed reset by " + sender.getName() + ".");
			}
		}
	}
}
