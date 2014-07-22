package us.zingalicio.handlefish.persistence;

import java.util.List;

import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="handlefish_kit")
public class KitData
{
	@Id
	private int id;
	
	@NotNull
	private String name;
	
	@ManyToMany
	@JoinTable(name="handlefish_kits")
	private List<ItemData> items;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<ItemData> getItems()
	{
		return items;
	}
	
	public void setItems(List<ItemData> items)
	{
		this.items = items;
	}
}
