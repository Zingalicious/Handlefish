package us.zingalicio.handlefish;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ZingPlugin extends JavaPlugin
{
	private final YamlConfiguration materials;
	private final YamlConfiguration config;
	
	private File materialFile;
	private File configFile;
	
	public ZingPlugin()
	{
		materials = new YamlConfiguration();
		config = new YamlConfiguration();
	}

	public YamlConfiguration getMaterials()
	{
		return materials;
	}
	public YamlConfiguration getConfig()
	{
		return config;
	}
	public File getMaterialFile()
	{
		return materialFile;
	}
	public File getConfigFile()
	{
		return configFile;
	}
}
