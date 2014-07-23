package us.zingalicio.handlefish;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ZingPlugin extends JavaPlugin
{
	public File materialFile;
	public YamlConfiguration materials;
}
