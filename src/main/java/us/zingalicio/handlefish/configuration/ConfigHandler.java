package us.zingalicio.handlefish.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	public static void saveDefault(JavaPlugin plugin, File file)
	{
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			copy(plugin.getResource(file.getName()), file);
		}
	}
	
	private static void copy(InputStream in, File file)
	{
		try
		{
			OutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while((length = in.read(buffer))>0)
			{
				out.write(buffer, 0, length);
			}
			out.close();
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
