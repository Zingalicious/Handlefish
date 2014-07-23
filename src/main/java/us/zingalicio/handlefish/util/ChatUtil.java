package us.zingalicio.handlefish.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatUtil 
{
	public static String getFormattedName(Player player)
	{
		String name = player.getDisplayName();
		name = name.replace("&0", ChatColor.BLACK.toString());
		name = name.replace("&1", ChatColor.DARK_BLUE.toString());
		name = name.replace("&2", ChatColor.DARK_GREEN.toString());
		name = name.replace("&3", ChatColor.DARK_AQUA.toString());
		name = name.replace("&4", ChatColor.DARK_RED.toString());
		name = name.replace("&5", ChatColor.DARK_PURPLE.toString());
		name = name.replace("&6", ChatColor.GOLD.toString());
		name = name.replace("&7", ChatColor.GRAY.toString());
		name = name.replace("&8", ChatColor.DARK_GRAY.toString());
		name = name.replace("&9", ChatColor.BLUE.toString());
		name = name.replace("&a", ChatColor.GREEN.toString());
		name = name.replace("&b", ChatColor.AQUA.toString());
		name = name.replace("&c", ChatColor.RED.toString());
		name = name.replace("&d", ChatColor.LIGHT_PURPLE.toString());
		name = name.replace("&e", ChatColor.YELLOW.toString());
		name = name.replace("&f", ChatColor.WHITE.toString());
		name = name.replace("&k", ChatColor.MAGIC.toString());
		name = name.replace("&l", ChatColor.BOLD.toString());
		name = name.replace("&m", ChatColor.STRIKETHROUGH.toString());
		name = name.replace("&n", ChatColor.UNDERLINE.toString());
		name = name.replace("&o", ChatColor.ITALIC.toString());
		name = name.replace("&r", ChatColor.RESET.toString());
		return name;
	}
	
	public static String getPrefix(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(user.getPrefix() != null)
		{
			return user.getPrefix();
		}
		else
		{
			return "";
		}
	}
	
	public static String getSuffix(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		if(user.getSuffix() != null)
		{
			return user.getSuffix();
		}
		else
		{
			return "";
		}
	}
	
	public static String formatMessage(Player player, String message)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		message = message.replace("%prefix", user.getPrefix());
		message = message.replace("%suffix", user.getSuffix());
		message = message.replace("&0", ChatColor.BLACK.toString());
		message = message.replace("&1", ChatColor.DARK_BLUE.toString());
		message = message.replace("&2", ChatColor.DARK_GREEN.toString());
		message = message.replace("&3", ChatColor.DARK_AQUA.toString());
		message = message.replace("&4", ChatColor.DARK_RED.toString());
		message = message.replace("&5", ChatColor.DARK_PURPLE.toString());
		message = message.replace("&6", ChatColor.GOLD.toString());
		message = message.replace("&7", ChatColor.GRAY.toString());
		message = message.replace("&8", ChatColor.DARK_GRAY.toString());
		message = message.replace("&9", ChatColor.BLUE.toString());
		message = message.replace("&a", ChatColor.GREEN.toString());
		message = message.replace("&b", ChatColor.AQUA.toString());
		message = message.replace("&c", ChatColor.RED.toString());
		message = message.replace("&d", ChatColor.LIGHT_PURPLE.toString());
		message = message.replace("&e", ChatColor.YELLOW.toString());
		message = message.replace("&f", ChatColor.WHITE.toString());
		message = message.replace("&k", ChatColor.MAGIC.toString());
		message = message.replace("&l", ChatColor.BOLD.toString());
		message = message.replace("&m", ChatColor.STRIKETHROUGH.toString());
		message = message.replace("&n", ChatColor.UNDERLINE.toString());
		message = message.replace("&o", ChatColor.ITALIC.toString());
		message = message.replace("&r", ChatColor.RESET.toString());
		message = message.replace("%player", getFormattedName(player));
		
		return message;
	}
	
	public static String permFormatMessage(Player player, String message)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		message = message.replace("%prefix", user.getPrefix());
		message = message.replace("%suffix", user.getSuffix());
		if(PermissionsUtil.silentCheckPermission(player, "chat.color"))
		{
			message = message.replace("&0", ChatColor.BLACK.toString());
			message = message.replace("&1", ChatColor.DARK_BLUE.toString());
			message = message.replace("&2", ChatColor.DARK_GREEN.toString());
			message = message.replace("&3", ChatColor.DARK_AQUA.toString());
			message = message.replace("&4", ChatColor.DARK_RED.toString());
			message = message.replace("&5", ChatColor.DARK_PURPLE.toString());
			message = message.replace("&6", ChatColor.GOLD.toString());
			message = message.replace("&7", ChatColor.GRAY.toString());
			message = message.replace("&8", ChatColor.DARK_GRAY.toString());
			message = message.replace("&9", ChatColor.BLUE.toString());
			message = message.replace("&a", ChatColor.GREEN.toString());
			message = message.replace("&b", ChatColor.AQUA.toString());
			message = message.replace("&c", ChatColor.RED.toString());
			message = message.replace("&d", ChatColor.LIGHT_PURPLE.toString());
			message = message.replace("&e", ChatColor.YELLOW.toString());
			message = message.replace("&f", ChatColor.WHITE.toString());
		}
		else
		{
			message = message.replace("&0", "");
			message = message.replace("&1", "");
			message = message.replace("&2", "");
			message = message.replace("&3", "");
			message = message.replace("&4", "");
			message = message.replace("&5", "");
			message = message.replace("&6", "");
			message = message.replace("&7", "");
			message = message.replace("&8", "");
			message = message.replace("&9", "");
			message = message.replace("&a", "");
			message = message.replace("&b", "");
			message = message.replace("&c", "");
			message = message.replace("&d", "");
			message = message.replace("&e", "");
			message = message.replace("&f", "");
		}
		if(PermissionsUtil.silentCheckPermission(player, "chat.magic"))
		{
			message = message.replace("&k", ChatColor.MAGIC.toString());
		}
		else
		{
			message = message.replace("&k", "");
		}
		if(PermissionsUtil.silentCheckPermission(player, "chat.bold"))
		{
			message = message.replace("&l", ChatColor.BOLD.toString());
		}
		else
		{
			message = message.replace("&l", "");
		}
		if(PermissionsUtil.silentCheckPermission(player, "chat.lines"))
		{
			message = message.replace("&m", ChatColor.STRIKETHROUGH.toString());
			message = message.replace("&n", ChatColor.UNDERLINE.toString());
		}
		else
		{
			message = message.replace("&m", "");
			message = message.replace("&n", "");
		}
		if(PermissionsUtil.silentCheckPermission(player, "chat.italic"))
		{
			message = message.replace("&o", ChatColor.ITALIC.toString());
		}
		else
		{
			message = message.replace("&o", "");
		}
		message = message.replace("&r", ChatColor.RESET.toString());
		message = message.replace("%player", getFormattedName(player));
		
		return message;
	}
}
