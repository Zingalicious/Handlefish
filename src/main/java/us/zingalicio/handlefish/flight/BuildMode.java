package us.zingalicio.handlefish.flight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Handlefish;

public class BuildMode extends BukkitRunnable
{
	Player player;
	Handlefish plugin;
	BukkitScheduler scheduler;
	Location oldPos;
	Boolean oldDirection = null;
	byte oldSneaking;
	Boolean startup = true;
	int id;
	public BuildMode(Player player, Handlefish plugin)
	{
		this.player = player;
		this.plugin = plugin;
		this.scheduler = Bukkit.getServer().getScheduler();
		if(startup)
		{
			this.oldPos = player.getLocation();
			this.oldSneaking = 0;
		}
	}
	
	public void setId(int i)
	{
		id = i;
	}
	
	@Override
	public void run() 
	{	
		PermissionUser user = PermissionsEx.getUser(player);
		World world = player.getWorld();
		Location newPos = player.getLocation();
		if(user.getOptionBoolean("buildmode", world.getName(), false))
		{
			if(player.isFlying())
			{
				Vector v = player.getVelocity();
				if(player.isSneaking())
				{
					if(oldSneaking < 5)
					{
						v = v.setY(.153F);
						player.setVelocity(v);
						oldSneaking += 1;
					}
				}
				else
				{
					oldSneaking = 0;
				}
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(id);
		}
		oldPos = newPos;
	}
}
