package us.zingalicio.handlefish.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import us.zingalicio.handlefish.ZingPlugin;

public class ItemUtil 
{	
	@SuppressWarnings("deprecation")
	public static ItemStack getItem(ZingPlugin plugin, String name)
	{
		ItemStack item;
		YamlConfiguration materials = plugin.getMaterials();
		Material mat = null;
		String dataString = "0";
		Short data = 0;
		List<String> enchantments = new ArrayList<String>();
		
		if(name.contains("|"))
		{
			String[] parts = name.split("\\|");
			name = parts[0];
						
			boolean i = false;
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
		else if(materials.contains("lookup." + name.toLowerCase().replace("_", "")))
		{
			mat = Material.valueOf(materials.getString("lookup." + name.toLowerCase().replace("_", "")));
		}
		else
		{
			return null;
		}
		
		if(NumberUtil.getInt(dataString) != null)
		{
			data = Short.parseShort(dataString);
		}
		else if(mat.getId() < 256)
		{
			if(materials.contains("blocks." + mat.name() + ".names." + dataString.toLowerCase().replace("_", "")))
			{
				data = (short) materials.getInt("blocks." + mat.name() + ".names." + dataString.toLowerCase().replace("_", ""));
			}
			else
			{
				return null;
			}
		}
		else
		{
			if(materials.contains("items." + mat.name() + ".names." + dataString.toLowerCase().replace("_", "")))
			{
				data = (short) materials.getInt("items." + mat.name() + ".names." + dataString.toLowerCase().replace("_", ""));
			}
			else 
			{
				return null;
			}
		}
		
		item = new ItemStack(mat, 1, data);
		
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
				else if(materials.contains("enchantments." + enchantParts[0].toLowerCase()))
				{
					enchantment = Enchantment.getById(materials.getInt("enchantments." + enchantParts[0].toLowerCase()));
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
				enchantment = Enchantment.getById(NumberUtil.getInt(e));
			}
			else if(materials.contains("enchantments." + e.toLowerCase()))
			{
				enchantment = Enchantment.getById(materials.getInt("enchantments." + e.toLowerCase()));
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
	
	public static int giveMany(ItemStack item, Player player, int amount)
	{
		int stackSize = item.getMaxStackSize();
		item.setAmount(stackSize);
		int extra = 0;
		while(amount > stackSize)
		{
			HashMap<Integer, ItemStack> i = player.getInventory().addItem(item);
			if(i.containsKey(0))
			{
				extra = extra + i.get(0).getAmount();
			}
			amount -= stackSize;
		}
		item.setAmount(amount);
		
		HashMap<Integer, ItemStack> i = player.getInventory().addItem(item);
		if(i.containsKey(0))
		{
			extra = extra + i.get(0).getAmount();
		}
		
		return extra;
	}
}
