package us.zingalicio.handlefish.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Keys;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.commands.HandleMovement;
import us.zingalicio.handlefish.flight.BuildMode;

public final class JoinListener implements Listener
{
	Handlefish plugin;
	
	public JoinListener(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onSwitch(PlayerGameModeChangeEvent e)
	{
		final Player p = e.getPlayer();
		final PermissionUser user = PermissionsEx.getUser(p);
		BukkitRunnable task = (new BukkitRunnable()
		{
			@Override
			public void run()
			{
				p.setAllowFlight(user.getOptionBoolean(Keys.OPTION_FLIGHT, p.getWorld().getName(), false));
			}
		});
		task.runTaskLater(this.plugin, 1);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		final Player player = e.getPlayer();
		final PermissionUser user = PermissionsEx.getUser(player);
		final World world = e.getPlayer().getWorld();
		if(user.getOptionBoolean(Keys.OPTION_FLIGHT, world.getName(), false))
		{
			HandleMovement.setFlight(plugin, null, player, true);
		}
		if(user.getOptionBoolean(Keys.OPTION_GOD, world.getName(), false))
		{
			//TODO Implement godmode
		}
		if(!user.getOption(Keys.OPTION_WALK_SPEED, world.getName()).equals(""))
		{
			try
			{
				player.setFlySpeed(Float.parseFloat(user.getOption(Keys.OPTION_WALK_SPEED, world.getName())));
			}
			catch(IllegalArgumentException ex)
			{}
		}
		else
		{
			HandleMovement.resetWalkSpeed(plugin, null, player);
		}
		if(!user.getOption(Keys.OPTION_FLIGHT_SPEED, world.getName()).equals(""))
		{
			try
			{
				player.setFlySpeed(Float.parseFloat(user.getOption(Keys.OPTION_FLIGHT_SPEED, world.getName())));
			}
			catch(IllegalArgumentException ex)
			{}
		}
		else
		{
			HandleMovement.resetFlySpeed(plugin,  null, player);
		}
		if(user.getOptionBoolean(Keys.OPTION_BUILD_MODE, world.getName(), false))
		{
			BuildMode build = new BuildMode(player, plugin);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			int task = scheduler.scheduleSyncRepeatingTask(plugin, build, 0, 1L);
			build.setId(task);
		}
	}
}
