package us.zingalicio.handlefish.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.Keys;

public class SwitchListener implements Listener
{
	Handlefish plugin;
	
	public SwitchListener(Handlefish plugin)
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
}
