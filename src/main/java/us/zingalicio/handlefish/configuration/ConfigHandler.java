package us.zingalicio.handlefish.configuration;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler 
{
	public static void loadYaml(YamlConfiguration yaml, File file)
	{
		try
		{
			yaml.load(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveYaml(YamlConfiguration yaml, File file)
	{
		try
		{
			yaml.save(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
