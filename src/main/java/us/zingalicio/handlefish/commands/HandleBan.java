package us.zingalicio.handlefish.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Constants;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.zinglib.util.PermissionsUtil;

public final class HandleBan implements CommandExecutor
{
	Handlefish plugin;
	YamlConfiguration bans;
	
	public HandleBan(Handlefish plugin)
	{
		this.plugin = plugin;
		this.bans = plugin.getBans();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) 
	{
		
		if(command.getName().equalsIgnoreCase("ban"))
		{
			String victim = null;
			String duration = "";
			String reason = "";
			boolean exact = false;
			if(args.length == 0)
			{
				return false;
			}
			boolean flag = false;
			byte index = 0;
			for(String s : args)
			{
				if(!flag)
				{
					if(s.equalsIgnoreCase("-e"))
					{
						exact = true;
					}
					else if(s.equalsIgnoreCase("-t"))
					{
						flag = true;
					}
					else
					{
						break;
					}
					index ++;
				}
				//Time
				else
				{
					duration = s;
					flag = false;
					index ++;
				}
			}
			
			if(args.length > index)
			{
				victim = args[index];
				index ++;
			}
			else
			{
				return false;
			}
			
			if(args.length > index)
			{
				while(index != args.length)
				{
					reason = reason.concat(args[index] + " ");
					index ++;
				}
				reason = reason.trim();
			}
			
			ban(sender, victim, duration, reason, exact);
			return true;
		}
		else if(command.getName().equalsIgnoreCase("unban"))
		{
			unban(sender, label, label);
		}
		else if(command.getName().equalsIgnoreCase("kick"))
		{
			Bukkit.getPlayer(args[0]).kickPlayer("test");
		}
		return false;
	}
	
	private void ban(CommandSender sender, String victim, String duration, String reason, boolean exact)
	{
		if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_BAN, false))
		{
			return;
		}
		
		Player victimPlayer = Bukkit.getPlayer(victim);
		
		if(!exact)
		{
			if(victimPlayer != null)
			{
				victim = victimPlayer.getName();
			}
		}
		else
		{
			if(!victimPlayer.getName().equalsIgnoreCase(victim))
			{
				victimPlayer = null;
			}
		}
		
		if(PermissionsEx.getUser(victim).getOptionBoolean(Constants.OPTION_BAN_OVERRIDE, null, false))
		{
			if(!PermissionsUtil.checkPermission(sender, Constants.PERMISSION_BAN_OVERRIDE, false))
			{
				return;
			}
		}
		
		if(victimPlayer != null)
		{
			victimPlayer.kickPlayer(reason);
		}
		
	}

	private void unban(CommandSender sender, String target, String reason)
	{
		
	}
}
