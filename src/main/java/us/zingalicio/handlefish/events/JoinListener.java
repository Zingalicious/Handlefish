package us.zingalicio.handlefish.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Handlefish;
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
		PermissionUser user = PermissionsEx.getUser(e.getPlayer());
		if(user.getOption("displayname") != null)
		{
			e.getPlayer().setDisplayName(user.getOption("displayname"));
		}
		if(user.getOption("joinmessage") != null)
		{
			e.setJoinMessage(ChatUtil.formatMessage(plugin, e.getPlayer(), user.getOption("displayname")));
		}
	}
}
