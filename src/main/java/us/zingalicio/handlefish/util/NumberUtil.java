package us.zingalicio.handlefish.util;

public class NumberUtil 
{
	public static Float getFloat(String s)
	{
		float a;
		try
		{
			a = Float.valueOf(s);
		}
		catch(NumberFormatException ex)
		{
			return null;
		}
		return a;
	}
	
	public static Integer getInt(String s)
	{
		int a;
		try
		{
			a = Integer.valueOf(s);
		}
		catch(NumberFormatException ex)
		{
			return null;
		}
		return a;
	}
}
