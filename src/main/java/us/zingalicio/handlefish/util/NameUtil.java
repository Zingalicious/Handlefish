package us.zingalicio.handlefish.util;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.material.MaterialData;

import us.zingalicio.handlefish.Handlefish;

public class NameUtil
{	
	@SuppressWarnings("deprecation")
	public static MaterialData getMaterialData(Handlefish plugin, Material material, String name)
	{
		YamlConfiguration file = plugin.names;
		if(file.contains("blocks." + material.name() + ".names." + name))
		{
			MaterialData data = new MaterialData(material, Byte.parseByte(file.getString("blocks." + material.name() + ".names." + name)));
			return data;
		}
		else
		{
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getMaterialName(Handlefish plugin, MaterialData data)
	{
		Material material = data.getItemType();
		YamlConfiguration file = plugin.names;
		if(file.contains("blocks." + material.name() + ".data." + data.getData()))
		{
			String name = file.getString("blocks." + material.name() + ".data." + data.getData());
			return name;
		}
		else
		{
			return null;
		}
	}
	
	public static Material getMaterial(Handlefish plugin, String name)
	{
		YamlConfiguration file = plugin.names;
		if(file.contains("lookup." + name))
		{
			return Material.getMaterial(file.getString("blocknames." + name));
		}
		else
		{
			try
			{
				return Material.getMaterial(name);
			}
			catch(Exception ex)
			{
				return null;
			}
		}
	}

	public static String getName(Handlefish plugin, Material material)
	{
		YamlConfiguration file = plugin.names;
		if(file.contains("blocks." + material.name() + ".name"))
		{
			return file.getString("blocks." + material.name() + ".name");
		}
		else
		{
			return material.name();
		}
	}
}
