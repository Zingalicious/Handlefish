package us.zingalicio.handlefish.persistence;

import com.avaje.ebean.validation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="handlefish_warp")
public class WarpData
{
	@Id
	private int id;
	
	@NotNull
	private String warp;

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
	
	public void setWarp(String warp)
	{
		this.warp = warp;
	}
	
	public String getWarp()
	{
		return warp;
	}	
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getX()
	{
		return x;
	}	
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getY()
	{
		return y;
	}	
	
	public void setZ(double z)
	{
		this.z = z;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public void setWorld(String world)
	{
		this.world = world;
	}
	
	public String getWorld()
	{
		return world;
	}
	
	public void setLocation(double x, double y, double z, String world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}	
}
