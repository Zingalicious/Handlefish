package us.zingalicio.handlefish.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import us.zingalicio.handlefish.Keys;
import us.zingalicio.handlefish.Handlefish;
import us.zingalicio.cordstone.StoredMessages;
import us.zingalicio.cordstone.util.MessageUtil;
import us.zingalicio.cordstone.util.PermissionsUtil;

public final class HandleBan implements CommandExecutor
{
	Handlefish plugin;
	YamlConfiguration bans;
	
	public HandleBan(Handlefish plugin)
	{
		this.plugin = plugin;
		this.bans = plugin.getBans();
	}
	
	@SuppressWarnings("deprecation")
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
			if(args.length == 1)
			{
				Player p = Bukkit.getPlayer(args[0]);
				if(p == null)
				{
					MessageUtil.sendError(plugin, sender, StoredMessages.NO_PLAYER.selfMessage());
					return true;
				}
				String kickMessage = StoredMessages.KICKED.toMessage(p)
						.replace("%reason", "for no apparent reason");
				MessageUtil.sendMessage(plugin, sender, kickMessage);
				p.kickPlayer("You been kicked, m8.");
				return true;
			}
			else if(args.length > 1)
			{
				Player p = Bukkit.getPlayer(args[0]);
				if(p == null)
				{
					MessageUtil.sendError(plugin,sender, StoredMessages.NO_PLAYER.selfMessage());
					return true;
				}
				String reason = "";
				for(String s : Arrays.copyOfRange(args, 1, args.length))
				{
					reason = reason.concat(s).concat(" ");
				}
				reason = reason.trim();

				String kickMessage = StoredMessages.KICKED.toMessage(p)
						.replace("%reason", reason);
				MessageUtil.sendMessage(plugin, sender, kickMessage);
				p.kickPlayer("You been kicked " + reason + ".");
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void ban(CommandSender sender, String victim, String duration, String reason, boolean exact)
	{
		if(!PermissionsUtil.checkPermission(sender, Keys.PERMISSION_BAN, false))
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
		
		if(PermissionsEx.getUser(victim).getOptionBoolean(Keys.OPTION_BAN_OVERRIDE, null, false))
		{
			if(!PermissionsUtil.checkPermission(sender, Keys.PERMISSION_BAN_OVERRIDE, false))
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
