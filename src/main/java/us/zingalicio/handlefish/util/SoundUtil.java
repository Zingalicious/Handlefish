package us.zingalicio.handlefish.util;

import org.bukkit.Material;
import org.bukkit.Sound;

import us.zingalicio.handlefish.ZingPlugin;

public class SoundUtil
{
	public static Sound getSound(Material material, ZingPlugin plugin)
	{
		String soundString;
		if((soundString = plugin.getMaterials().getString("blocks." + material.name() + ".sound")) != null)
		{
			return Sound.valueOf(soundString);
		}
		return Sound.DIG_STONE;
	}
}