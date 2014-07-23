package us.zingalicio.handlefish.util;

import net.minecraft.server.v1_7_R1.NBTTagCompound;

import org.bukkit.craftbukkit.v1_7_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil 
{
	public static CraftItemStack addNBTBoolean(ItemStack item, String tag, Boolean value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setBoolean(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTInt(ItemStack item, String tag, Integer value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setInt(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTString(ItemStack item, String tag, String value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setString(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTLong(ItemStack item, String tag, Long value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setLong(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTFloat(ItemStack item, String tag, Float value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setFloat(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTDouble(ItemStack item, String tag, Double value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setDouble(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTByte(ItemStack item, String tag, Byte value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setByte(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTShort(ItemStack item, String tag, Short value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setShort(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTByteArray(ItemStack item, String tag, byte[] value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setByteArray(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
	public static CraftItemStack addNBTIntArray(ItemStack item, String tag, int[] value)
	{
		net.minecraft.server.v1_7_R1.ItemStack netItem = CraftItemStack.asNMSCopy(item);
		
		if(netItem.getTag() == null)
		{
			netItem.tag = new NBTTagCompound();
		}
		
		netItem.getTag().setIntArray(tag, value);
		
		return CraftItemStack.asCraftMirror(netItem);
	}
}
