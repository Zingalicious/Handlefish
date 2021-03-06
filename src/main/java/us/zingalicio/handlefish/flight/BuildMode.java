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
import us.zingalicio.cordstone.ZingPlugin;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.Keys;

public class BuildMode extends BukkitRunnable
{
	Player player;
	ZingPlugin plugin;
	BukkitScheduler scheduler;
	Location oldPos;
	Boolean oldDirection = null;
	int downDelay;
	short sneakTicks;
	Boolean startup = true;
	int id;
	public BuildMode(Player player)
	{
		this.player = player;
		this.plugin = Handlefish.getInstance();
		this.scheduler = Bukkit.getServer().getScheduler();
		if(startup)
		{
			this.oldPos = player.getLocation();
			this.sneakTicks = 0;
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
		if(user.getOptionBoolean(Keys.OPTION_BUILD_MODE, world.getName(), false))
		{
			this.downDelay = user.getOptionInteger(Keys.OPTION_BUILD_MODE_DELAY, world.getName(), 10);
			if(player.isFlying())
			{
				Vector v = player.getVelocity();
				if(player.isSneaking())
				{
					if(sneakTicks < downDelay)
					{
						v = v.setY(.153F);
						player.setVelocity(v);
						sneakTicks += 1;
					}
				}
				else
				{
					sneakTicks = 0;
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
