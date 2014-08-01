package us.zingalicio.handlefish;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import us.zingalicio.handlefish.commands.HandleBan;
import us.zingalicio.handlefish.commands.HandleBiome;
import us.zingalicio.handlefish.commands.HandleMovement;
import us.zingalicio.handlefish.commands.HandleGive;
import us.zingalicio.handlefish.commands.HandleHandle;
import us.zingalicio.handlefish.commands.HandleHead;
import us.zingalicio.handlefish.commands.HandleHelp;
import us.zingalicio.handlefish.commands.HandleHome;
import us.zingalicio.handlefish.commands.HandleKit;
import us.zingalicio.handlefish.commands.HandleMob;
import us.zingalicio.handlefish.commands.HandlePlayer;
import us.zingalicio.handlefish.commands.HandlePunish;
import us.zingalicio.handlefish.commands.HandleSpawn;
import us.zingalicio.handlefish.commands.HandleTeleport;
import us.zingalicio.handlefish.commands.HandleTime;
import us.zingalicio.handlefish.commands.HandleWarp;
import us.zingalicio.handlefish.commands.HandleWeather;
import us.zingalicio.handlefish.events.BuildModeListener;
import us.zingalicio.handlefish.events.JoinListener;
import us.zingalicio.handlefish.persistence.HomeData;
import us.zingalicio.handlefish.persistence.WarpData;
import us.zingalicio.zinglib.plugin.ZingPlugin;
import us.zingalicio.zinglib.util.ConfigUtil;

public final class Handlefish extends ZingPlugin
{	
	private HandleBan handleBan;
	private HandleBiome handleBiome;
	private HandleGive handleGive;
	private HandleHandle handleHandle;
	private HandleHead handleHead;
	private HandleHelp handleHelp;
	private HandleHome handleHome;
	private HandleKit handleKit;
	private HandleMob handleMob;
	private HandleMovement handleMovement;
	private HandlePlayer handlePlayer;
	private HandlePunish handlePunish;
	private HandleSpawn handleSpawn;
	private HandleTeleport handleTeleport;
	private HandleTime handleTime;
	private HandleWarp handleWarp;
	private HandleWeather handleWeather;
	private File helpFile;
	private final YamlConfiguration help;
	private JoinListener joinListener;
	private BuildModeListener buildModeListener;
	
	public Handlefish()
	{
		super();
		
		helpFile = new File(getDataFolder(), "help.yml");
		help = new YamlConfiguration();
		
		ConfigUtil.saveDefault(this, helpFile);
		ConfigUtil.loadYaml(help, helpFile);
	}
	
	@Override
	public void onEnable()
	{
		registerModules();
		
		registerCommands();
		registerListeners();
		
		setupDatabase();
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		ConfigUtil.saveYaml(help, helpFile);
	}
	
	private void registerModules()
	{
		this.handleBan = new HandleBan(this);
		this.handleBiome = new HandleBiome(this);
		this.handleMovement = new HandleMovement(this);
		this.handleGive = new HandleGive(this);
		this.handleHandle = new HandleHandle(this);
		this.handleHead = new HandleHead(this);
		this.handleHelp = new HandleHelp(this);
		this.handleHome = new HandleHome(this);
		this.handleKit = new HandleKit(this);
		this.handleMob = new HandleMob(this);
		this.handlePlayer = new HandlePlayer(this);
		this.handlePunish = new HandlePunish(this);
		this.handleSpawn = new HandleSpawn(this);
		this.handleTeleport = new HandleTeleport(this);
		this.handleTime = new HandleTime(this);
		this.handleWarp = new HandleWarp(this);
	}
	
	private void registerCommands()
	{
		getCommand("ban").setExecutor(handleBan);
		getCommand("unban").setExecutor(handleBan);
		getCommand("biome").setExecutor(handleBiome);
		getCommand("item").setExecutor(handleGive);
		getCommand("give").setExecutor(handleGive);
		getCommand("more").setExecutor(handleGive);
		getCommand("handlefish").setExecutor(handleHandle);
		getCommand("ping").setExecutor(handleHandle);
		getCommand("head").setExecutor(handleHead);
		getCommand("help").setExecutor(handleHelp);
		getCommand("home").setExecutor(handleHome);
		getCommand("sethome").setExecutor(handleHome);
		getCommand("kit").setExecutor(handleKit);
		getCommand("spawnmob").setExecutor(handleMob);
		getCommand("butcher").setExecutor(handleMob);
		getCommand("buildmode").setExecutor(handleMovement);
		getCommand("fly").setExecutor(handleMovement);
		getCommand("speed").setExecutor(handleMovement);
		getCommand("god").setExecutor(handlePlayer);
		getCommand("ungod").setExecutor(handlePlayer);
		getCommand("heal").setExecutor(handlePlayer);
		getCommand("list").setExecutor(handlePlayer);
		getCommand("clear").setExecutor(handlePlayer);
		getCommand("self").setExecutor(handlePlayer);
		getCommand("other").setExecutor(handlePlayer);
		getCommand("kick").setExecutor(handlePunish);
		getCommand("rocket").setExecutor(handlePunish);
		getCommand("freeze").setExecutor(handlePunish);
		getCommand("unfreeze").setExecutor(handlePunish);
		getCommand("slap").setExecutor(handlePunish);
		getCommand("unslap").setExecutor(handlePunish);
		getCommand("blind").setExecutor(handlePunish);
		getCommand("unblind").setExecutor(handlePunish);
		getCommand("mute").setExecutor(handlePunish);
		getCommand("unmute").setExecutor(handlePunish);
		getCommand("peffect").setExecutor(handlePunish);
		getCommand("ceffect").setExecutor(handlePunish);
		getCommand("kill").setExecutor(handlePunish);
		getCommand("shock").setExecutor(handlePunish);
		getCommand("drunk").setExecutor(handlePunish);
		getCommand("undrunk").setExecutor(handlePunish);
		getCommand("spawn").setExecutor(handleSpawn);
		getCommand("setspawn").setExecutor(handleSpawn);
		getCommand("teleport").setExecutor(handleTeleport);
		getCommand("tpa").setExecutor(handleTeleport);
		getCommand("bring").setExecutor(handleTeleport);
		getCommand("call").setExecutor(handleTeleport);
		getCommand("return").setExecutor(handleTeleport);
		getCommand("teleprivacy").setExecutor(handleTeleport);
		getCommand("put").setExecutor(handleTeleport);
		getCommand("time").setExecutor(handleTime);
		getCommand("ptime").setExecutor(handleTime);
		getCommand("warp").setExecutor(handleWarp);
		getCommand("setwarp").setExecutor(handleWarp);
		getCommand("removewarp").setExecutor(handleWarp);
		getCommand("weather").setExecutor(handleWeather);
	}
	
	private void registerListeners()
	{
		joinListener = new JoinListener(this);
		buildModeListener = new BuildModeListener(this);
		getServer().getPluginManager().registerEvents(joinListener, this);
		getServer().getPluginManager().registerEvents(buildModeListener, this);
	}
	
	private void setupDatabase()
	{
		try
		{
			getDatabase().find(HomeData.class).findRowCount();
			getDatabase().find(WarpData.class).findRowCount();
		}
		catch (PersistenceException ex)
		{
			Bukkit.getLogger().log(Level.INFO, "Installing database for Handlefish due to first time usage.");
			installDDL();
		}
	}
	
	public List<Class<?>> getDatabaseClasses()
	{
		List<Class<?>> classes = new LinkedList<Class<?>>();
		
		classes.add(HomeData.class);
		classes.add(WarpData.class);
		
		return classes;
		
	}	
	
	public YamlConfiguration getHelp()
	{
		return help;
	}
	
	public File getHelpFile()
	{
		return helpFile;
	}
	
	public YamlConfiguration getConfig()
	{
		return config;
	}
	
	public File getConfigFile()
	{
		return configFile;
	}
	
	public YamlConfiguration getMaterials()
	{
		return materials;
	}
	
	public File getMaterialFile()
	{
		return materialFile;
	}
}
