package us.zingalicio.handlefish.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.util.ChatUtil;

public final class ChatListener implements Listener
{
	Handlefish plugin;
	
	public ChatListener(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		String message = ChatUtil.permFormatMessage(plugin, e.getPlayer(), e.getMessage());
		e.setMessage(message);
		String format = ChatUtil.formatMessage(plugin, e.getPlayer(), plugin.config.getString("chat.format"));
		String[] splitFormat = format.split("%message");
		message = message.replace("%", "%%");
		if(splitFormat.length > 1)
		{
			splitFormat[0] = splitFormat[0].replace("%", "%%");
			splitFormat[1] = splitFormat[1].replace("%", "%%");
			e.setFormat(splitFormat[0] + message + splitFormat[1]);
		}
		else
		{
			splitFormat[0] = splitFormat[0].replace("%", "%%");
			e.setFormat(splitFormat[0] + message);
		}
	}
}
