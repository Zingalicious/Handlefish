package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.StoredMessages;
import us.zingalicio.zinglib.util.ConfigUtil;
import us.zingalicio.zinglib.util.ItemUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.PermissionsUtil;

public final class HandleHandle implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleHandle(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(command.getName().equalsIgnoreCase("handlefish"))
		{
			if(args.length == 0)
			{
				return false;
			}
			switch(args[0])
			{
				case "reload":
					 reload(sender);
					return true;
				case "version":
					String message = StoredMessages.VERSION.selfMessage(plugin).
							replace("%version", plugin.getDescription().getVersion());
					MessageUtil.sendMessage(plugin, sender, message);
					return true;
				case "debug":
					ItemUtil.saveItem(plugin, ((Player) sender).getItemInHand(), args[1]);
				default: return false;
			}
		}
		else if(command.getName().equalsIgnoreCase("ping"))
		{
			MessageUtil.sendMessage(plugin, sender, StoredMessages.PING.selfMessage(plugin));
			return true;
		}
		return false;
	}
	
	public void reload(CommandSender sender)
	{
		try
		{
			if(PermissionsUtil.checkPermission(sender, Constants.PERMISSION_RELOAD, false))
			{
				ConfigUtil.loadYaml(plugin.getHelp(), plugin.getHelpFile());
				ConfigUtil.loadYaml(plugin.getConfig(), plugin.getConfigFile());
				PluginManager pluginManager = Bukkit.getPluginManager();
				pluginManager.disablePlugin(plugin);
				pluginManager.enablePlugin(plugin);
				MessageUtil.sendMessage(plugin, sender, StoredMessages.RELOADED.selfMessage(plugin));
			}
			return;
		}
		catch(Throwable t)
		{
			MessageUtil.sendError(plugin, sender, StoredMessages.GENERAL_FAILURE.selfMessage(plugin));
			return;
		}
	}

}
