package us.zingalicio.handlefish.persistence;

import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="handlefish_players")
public class PlayerData
{
	@Id
	private int id;
	
	@NotNull
	private String player;
	
	private String displayName;

	@NotNull
	private byte time = 0;

	@NotNull
	private byte god = 0;
	
	private String joinMessage = "&6%player &6has entered the fringe.";
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setPlayer(String player)
	{
		this.player = player;
	}
	
	public String getPlayer()
	{
		return player;
	}	
	
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}

	public void setTime(byte time)
	{
		this.time = time;
	}
	
	public byte getTime()
	{
		return time;
	}
	
	public void setGod(byte god)
	{
		this.god = god;
	}
	
	public byte getGod()
	{
		return god;
	}
	
	public void setJoinMessage(String message)
	{
		this.joinMessage = message;
	}
	
	public String getJoinMessage()
	{
		return joinMessage;
	}
	
}
