package us.zingalicio.handlefish.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.handlefish.util.MessageUtil;
import us.zingalicio.handlefish.util.NumberUtil;
import us.zingalicio.handlefish.util.PermissionsUtil;

public class HandleHelp implements CommandExecutor
{
	Handlefish plugin;
	YamlConfiguration help;
	
	public HandleHelp(Handlefish plugin)
	{
		this.plugin = plugin;
		this.help = plugin.help;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(args.length == 0)
		{
			List<String> topics = new ArrayList<>();
			for(String key : help.getConfigurationSection("listed").getKeys(false))
			{
				for(String s: help.getConfigurationSection("listed." + key).getKeys(false))
				{
					if(PermissionsUtil.silentCheckPermission(sender, "help." + s))
					{
						topics.add("/help " + s + ": " + ChatColor.WHITE + help.getString("listed." + key + "." + s));
					}
				}
			}
			MessageUtil.paginate(sender, topics, 1);
		}
		else if(NumberUtil.getInt(args[0]) != null)
		{
			int pageN = NumberUtil.getInt(args[0]);
			List<String> topics = new ArrayList<>();
			for(String key : help.getConfigurationSection("listed").getKeys(false))
			{
				for(String s: help.getConfigurationSection("listed." + key).getKeys(false))
				{
					if(PermissionsUtil.silentCheckPermission(sender, "help." + s))
					{
						topics.add("/help " + s + ": " + ChatColor.WHITE + help.getString("listed." + key + "." + s));
					}
				}
			}
			MessageUtil.paginate(sender, topics, pageN);
		}
		else if(help.contains("topics." + args[0]))
		{
			int pageN = 1;
			if(args.length > 1 && NumberUtil.getInt(args[1]) != null)
			{
				pageN = NumberUtil.getInt(args[1]);
			}
			MessageUtil.paginate(sender, help.getStringList("topics." + args[0]), pageN);
			return true;
		}
		else
		{
			MessageUtil.sendMessage(sender, "No such topic, friend.");
		}
		return false;
	}

}
