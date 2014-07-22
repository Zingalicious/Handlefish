package us.zingalicio.handlefish.persistence;

import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="handlefish_home")
public class HomeData
{
	@Id
	private int id;
	
	@NotNull
	private String player;

	@NotNull
	private String world;
	
	@NotNull
	private double x;

	@NotNull
	private double y;
	
	@NotNull
	private double z;
	
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
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getX()
	{
		return this.x;
	}	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getY()
	{
		return this.y;
	}	
	public void setZ(double z)
	{
		this.z = z;
	}
	
	public double getZ()
	{
		return this.z;
	}
	
	public void setWorld(String world)
	{
		this.world = world;
	}
	
	public String getWorld()
	{
		return world;
	}
	
	public void setLocation(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}	
}
