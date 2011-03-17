package com.g2d.editor;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.EnumSet;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class Util 
{
	
	public static<E extends Enum<E>, B extends AbstractButton> ButtonGroup createButtonsFromEnum(Class<E> enumClass, Class<B> buttonClass) throws Exception
	{
		ButtonGroup group = new ButtonGroup();
		
		for (Enum<?> e : EnumSet.allOf(enumClass))
		{
			AbstractButton btn = (AbstractButton)buttonClass.newInstance();
			btn.setText(e.toString());
			group.add(btn);
		}
		
		return group;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T parseObject(String str, Class<T> cls)
	{
		try
		{
			if (cls.equals(Byte.class))
			{
				return (T)new Byte(str);
			}
			else if (cls.equals(Boolean.class))
			{
				return (T)new Boolean(str);
			}
			else if (cls.equals(Short.class))
			{
				return (T)new Short(str);
			}
			else if (cls.equals(Character.class))
			{
				return (T)new Character(str.charAt(0));
			}
			else if (cls.equals(Integer.class))
			{
				return (T)new Integer(str);
			}
			else if (cls.equals(Long.class))
			{
				return (T)new Long(str);
			}
			else if (cls.equals(Float.class))
			{
				return (T)new Float(str);
			}
			else if (cls.equals(Double.class))
			{
				return (T)new Double(str);
			}
			else if (cls.equals(String.class))
			{
				return (T)(str);
			}
			/*--------------------------------------------------------------------------------------------*/
			// 
			else if (cls.equals(Color.class))
			{
				String[] strs = str.split(",");
				int a = Integer.parseInt(strs[0]);
				int r = Integer.parseInt(strs[1]);
				int g = Integer.parseInt(strs[2]);
				int b = Integer.parseInt(strs[3]);
				return (T)(new Color(r, g, b, a));
			}
			else if (cls.equals(Rectangle.class))
			{
				String[] strs = str.split(",");
				int x = Integer.parseInt(strs[0]);
				int y = Integer.parseInt(strs[1]);
				int w = Integer.parseInt(strs[2]);
				int h = Integer.parseInt(strs[3]);
				return (T)(new Rectangle(x, y, w, h));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String fromObject(Object obj)
	{
		try
		{
			if (obj != null)
			{
				Class<?> cls = obj.getClass();
				
				if (cls.equals(Color.class))
				{
					Color color = ((Color)obj);
					return color.getAlpha() + "," + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
				}
				else if (cls.equals(Rectangle.class))
				{
					Rectangle rect = (Rectangle)obj;
					return rect.x + "," + rect.y + "," +rect.width + "," +rect.height;
				}
				else
				{
					return obj.toString();
				}
			}
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
