package us.zingalicio.handlefish;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import us.zingalicio.handlefish.commands.HandleBan;
import us.zingalicio.handlefish.commands.HandleBiome;
import us.zingalicio.handlefish.commands.HandleChat;
import us.zingalicio.handlefish.commands.HandleFlight;
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
import us.zingalicio.handlefish.configuration.ConfigHandler;
import us.zingalicio.handlefish.events.ChatListener;
import us.zingalicio.handlefish.events.JoinListener;
import us.zingalicio.handlefish.persistence.HomeData;
import us.zingalicio.handlefish.persistence.WarpData;
import us.zingalicio.handlefish.util.ItemUtil;

public class Handlefish extends JavaPlugin implements Listener
{	
	private HandleBan handleBan;
	private HandleBiome handleBiome;
	private HandleChat handleChat;
	private HandleFlight handleFlight;
	private HandleGive handleGive;
	private HandleHandle handleHandle;
	private HandleHead handleHead;
	private HandleHelp handleHelp;
	private HandleHome handleHome;
	private HandleKit handleKit;
	private HandleMob handleMob;
	private HandlePlayer handlePlayer;
	private HandlePunish handlePunish;
	private HandleSpawn handleSpawn;
	private HandleTeleport handleTeleport;
	private HandleTime handleTime;
	private HandleWarp handleWarp;
	private HandleWeather handleWeather;
	public ItemUtil itemUtil;
	public File configFile;
	public YamlConfiguration config;
	public File helpFile;
	public YamlConfiguration help;
	public File namesFile;
	public YamlConfiguration names;
	private ChatListener chatListener;
	private JoinListener joinListener;
	
	@Override
	public void onEnable()
	{		
		helpFile = new File(getDataFolder(), "help.yml");
		configFile = new File(getDataFolder(), "config.yml");
		namesFile = new File("plugins/common", "names.yml");
		saveDefault(helpFile);
		saveDefault(configFile);
		saveDefault(namesFile);
		
		help = new YamlConfiguration();
		config = new YamlConfiguration();
		names = new YamlConfiguration();
		ConfigHandler.loadYaml(help, helpFile);
		ConfigHandler.loadYaml(config, configFile);
		ConfigHandler.loadYaml(names, namesFile);
		
		registerModules();
		
		registerCommands();
		registerListeners();
		
		setupDatabase();
	}
	
	@Override
	public void onDisable()
	{
		ConfigHandler.saveYaml(help, helpFile);
	}
	
	private void registerModules()
	{
		this.handleBan = new HandleBan(this);
		this.handleBiome = new HandleBiome(this);
		this.handleChat = new HandleChat(this);
		this.handleFlight = new HandleFlight(this);
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
		this.itemUtil = new ItemUtil(this);
	}
	
	private void registerCommands()
	{
		getCommand("ban").setExecutor(handleBan);
		getCommand("unban").setExecutor(handleBan);
		getCommand("ipban").setExecutor(handleBan);
		getCommand("ipunban").setExecutor(handleBan);
		getCommand("biome").setExecutor(handleBiome);
		getCommand("message").setExecutor(handleChat);
		getCommand("reply").setExecutor(handleChat);
		getCommand("broadcast").setExecutor(handleChat);
		getCommand("say").setExecutor(handleChat);
		getCommand("sayas").setExecutor(handleChat);
		getCommand("fly").setExecutor(handleFlight);
		getCommand("speed").setExecutor(handleFlight);
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
		chatListener = new ChatListener(this);
		joinListener = new JoinListener(this);
		getServer().getPluginManager().registerEvents(chatListener, this);
		getServer().getPluginManager().registerEvents(joinListener, this);
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
	
	private void saveDefault(File file)
	{
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			copy(getResource(file.getName()), file);
		}
	}
	
	private void copy(InputStream in, File file)
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
