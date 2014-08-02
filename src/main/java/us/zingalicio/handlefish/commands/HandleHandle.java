package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.ConfigUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;
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
					MessageUtil.sendMessage(plugin, sender, plugin.getDescription().getVersion());
					return true;
				default: return false;
			}
		}
		else if(command.getName().equalsIgnoreCase("ping"))
		{
			MessageUtil.sendMessage(plugin, sender, "Don't tell anyone, but I heard a rumour that " + NameUtil.getSenderName(sender) + " was gonna be banned...");
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
				ConfigUtil.loadYaml(plugin.getItems(), plugin.getItemFile());
				ConfigUtil.loadYaml(plugin.getMaterials(), plugin.getMaterialFile());
				PluginManager pluginManager = Bukkit.getPluginManager();
				pluginManager.disablePlugin(plugin);
				pluginManager.enablePlugin(plugin);
				MessageUtil.sendMessage(plugin, sender, "Reloaded.");
			}
			return;
		}
		catch(Throwable t)
		{
			MessageUtil.sendError(plugin, sender, "Failed to reload.");
			return;
		}
	}

}
