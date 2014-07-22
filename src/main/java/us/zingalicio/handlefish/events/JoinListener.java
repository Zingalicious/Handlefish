package us.zingalicio.handlefish.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.commands.HandleFlight;
import us.zingalicio.handlefish.util.ChatUtil;

public class JoinListener implements Listener
{
	Handlefish plugin;
	
	public JoinListener(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		PermissionUser user = PermissionsEx.getUser(player);
		World world = e.getPlayer().getWorld();
		if(!user.getOption("displayname").equals(""))
		{
			player.setDisplayName(user.getOption("displayname"));
		}
		if(!user.getOption("joinmessage").equals(""))
		{
			e.setJoinMessage(ChatUtil.formatMessage(plugin, e.getPlayer(), user.getOption("displayname")));
		}
		else
		{
			user.setOption("joinmessage", "&6%player &6has entered the fringe.");
		}
		if(user.getOptionBoolean("flight", world.getName(), false))
		{
			HandleFlight.setFlight(null, player, true);
		}
		if(user.getOptionBoolean("god", world.getName(), false))
		{
			//TODO Implement godmode
		}
		if(!user.getOption("walkspeed", world.getName()).equals(""))
		{
			try
			{
				player.setFlySpeed(Float.parseFloat(user.getOption("walkspeed", world.getName())));
			}
			catch(IllegalArgumentException ex)
			{}
		}
		else
		{
			HandleFlight.resetWalkSpeed(null, player);
		}
		if(!user.getOption("flyspeed", world.getName()).equals(""))
		{
			player.sendMessage("yes");
			try
			{
				player.setFlySpeed(Float.parseFloat(user.getOption("flyspeed", world.getName())));
			}
			catch(IllegalArgumentException ex)
			{}
		}
		else
		{
			HandleFlight.resetFlySpeed(null, player);
		}
	}
}
