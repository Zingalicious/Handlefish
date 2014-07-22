package us.zingalicio.handlefish.persistence;

import java.util.List;

import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="common_item")
public class ItemData
{
	@Id
	private int id;
	
	private String displayName;
	
	private String lore;
	
	private int enchantment;

	private int enchantLevel;
	
	@NotNull
	private int mat;
	
	@NotNull
	private byte data;
	
	@ManyToMany(mappedBy="items")
	private List<KitData> kits;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}
	
	public String getLore()
	{
		return lore;
	}
	
	public void setLore(String lore)
	{
		this.lore = lore;
	}
	
	public int getEnchantment()
	{
		return enchantment;
	}
	
	public void setEnchantment(int enchantment)
	{
		this.enchantment = enchantment;
	}	
	public int getEnchantLevel()
	{
		return enchantLevel;
	}
	
	public void setEnchantLevel(int enchantLevel)
	{
		this.enchantLevel = enchantLevel;
	}	
	public int getMat()
	{
		return mat;
	}
	
	public void setMat(int mat)
	{
		this.mat = mat;
	}
	
	public byte getData()
	{
		return data;
	}
	
	public void setData(byte data)
	{
		this.data = data;
	}
	
	public List<KitData> getKits()
	{
		return kits;
	}
	
	public void setKits(List<KitData> kits)
	{
		this.kits = kits;
	}
}
