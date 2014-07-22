package us.zingalicio.handlefish.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import us.zingalicio.handlefish.Handlefish;

public class ItemUtil 
{
	Handlefish plugin;
	
	public ItemUtil(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getItem(String name)
	{
		ItemStack item;
		Material mat = null;
		String dataString = "0";
		Short data = 0;
		List<String> enchantments = new ArrayList<String>();
		
		if(name.contains("|"))
		{
			String[] parts = name.split("\\|");
			name = parts[0];
						
			boolean i;
			i = false;
			for(String e : parts)
			{
				if(i)
				{
					enchantments.add(e);
				}
				else
				{
					i = true;
				}
			}
		}
		
		if(name.contains(":"))
		{
			String[] parts = name.split(":");
			name = parts[0];
			dataString = parts[1];
		}
		
		if(NumberUtil.getInt(name) != null)
		{
			mat = Material.getMaterial(Integer.parseInt(name));
		}
		else if(this.plugin.config.contains("names." + name.toLowerCase()))
		{
			mat = Material.getMaterial(this.plugin.config.getInt("names." + name.toLowerCase()));
		}
		else
		{
			return null;
		}
		
		if(NumberUtil.getInt(dataString) != null)
		{
			data = Short.parseShort(dataString);
		}
		else if(this.plugin.config.contains("data." + mat.getId() + "." + dataString.toLowerCase()))
		{
			data = (short) this.plugin.config.getInt("data." + mat.getId() + "." + dataString.toLowerCase());
		}
		else
		{
			return null;
		}
		
		if(mat != null)
		{
			item = new ItemStack(mat, 1, data);
		}
		else
		{
			item = null;
			return null;
		}
		
		for(String e : enchantments)
		{
			Enchantment enchantment;
			int enchantLevel = 1;
			if(e.contains(":"))
			{
				String[] enchantParts = e.split(":");
				if(NumberUtil.getInt(enchantParts[0]) != null)
				{
					enchantment = Enchantment.getById(Integer.parseInt(enchantParts[0]));
				}
				else if(this.plugin.config.contains("enchantments." + enchantParts[0].toLowerCase()))
				{
					enchantment = Enchantment.getById(this.plugin.config.getInt("enchantments." + enchantParts[0].toLowerCase()));
				}
				else
				{
					enchantment = Enchantment.getByName(enchantParts[0]);
				}
				try
				{
					enchantLevel = Integer.parseInt(enchantParts[1]);
				}
				catch(NumberFormatException ex)
				{
					return null;
				}
			}
			else if(NumberUtil.getInt(e) != null)
			{
			enchantment = Enchantment.getById(Integer.parseInt(e));
			}
			else if(this.plugin.config.contains("enchantments." + e.toLowerCase()))
			{
				enchantment = Enchantment.getById(this.plugin.config.getInt("enchantments." + e.toLowerCase()));
			}
			else
			{
				enchantment = Enchantment.getByName(e);
			}
			
			if(enchantment != null)
			{
				item.addUnsafeEnchantment(enchantment, enchantLevel);
			}
		}
		
		return item;
	}
}
