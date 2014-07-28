package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.ConfigUtil;
import us.zingalicio.zinglib.util.MessageUtil;
import us.zingalicio.zinglib.util.NameUtil;

public final class HandleHandle implements CommandExecutor
{
	Handlefish plugin;
	
	public HandleHandle(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(command.getName().equalsIgnoreCase("handlefish"))
		{
			if(args[0].equalsIgnoreCase("reload"))
			{
				ConfigUtil.loadYaml(plugin.getHelp(), plugin.getHelpFile());
				ConfigUtil.loadYaml(plugin.getConfig(), plugin.getConfigFile());
				ConfigUtil.loadYaml(plugin.getMaterials(), plugin.getMaterialFile());
				PluginManager pluginManager = Bukkit.getPluginManager();
				pluginManager.disablePlugin(plugin);
				pluginManager.enablePlugin(plugin);
				MessageUtil.sendMessage(plugin, sender, "Reloaded.");
				return true;
			}
			else if(args[0].equalsIgnoreCase("version"))
			{
				MessageUtil.sendMessage(plugin, sender, plugin.getDescription().getVersion());
				return true;
			}
			else if(args[0].equalsIgnoreCase("debug"))
			{
				if(sender instanceof Player)
				{
					Block b = ((Player) sender).getTargetBlock(null, 100);
					MaterialData data = b.getState().getData();
					String dataName = NameUtil.getMaterialName(plugin, data);
					if(args.length > 1)
					{
						b.setTypeId(Integer.parseInt(args[1]));
						b.setData(Byte.parseByte(args[2]));
					}
					MessageUtil.sendMessage(plugin, sender, 
							NameUtil.getName(plugin, b.getType()) + 
							(dataName != null ? ":" + dataName : ""));
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else if(command.getName().equalsIgnoreCase("ping"))
		{
			MessageUtil.sendMessage(plugin, sender, "Pong!");
			return true;
		}
		return false;
	}

}
