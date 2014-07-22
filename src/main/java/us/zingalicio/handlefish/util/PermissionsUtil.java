package us.zingalicio.handlefish.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PermissionsUtil 
{
	public static boolean checkPermission(CommandSender sender, String permission)
	{
		if(sender.hasPermission("handlefish." + permission) | sender.isOp())
		{
			return true;
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
			return false;
		}
	}
	
	public static boolean silentCheckPermission(CommandSender sender, String permission)
	{
		if(sender.hasPermission("handlefish." + permission) | sender.isOp())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
