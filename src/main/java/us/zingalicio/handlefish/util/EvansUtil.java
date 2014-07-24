package us.zingalicio.handlefish.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EvansUtil 
{
	public static Player getEvans()
	{
		return Bukkit.getServer().getPlayer("masterlink0"); //What a Hitler
	}
	
	public static void shockEvans()
	{
		Player evans = getEvans();
		Location loc = evans.getLocation();
		loc.getWorld().strikeLightning(loc);
		return;
	}
}
