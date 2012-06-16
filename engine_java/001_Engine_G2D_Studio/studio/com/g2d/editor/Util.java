package com.g2d.editor;

import java.util.EnumSet;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import com.cell.CUtil;
import com.cell.reflect.IObjectStringParser;
import com.cell.reflect.Parser;
import com.g2d.Color;
import com.g2d.geom.Rectangle;
import com.g2d.java2d.impl.AwtEngine;

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
	
	public static void initParser()
	{
		Parser.registerObjectStringParser(
				com.g2d.Color.class, 
				new ColorParser());
		Parser.registerObjectStringParser(
				com.g2d.Font.class, 
				new FontParser());
		Parser.registerObjectStringParser(
				com.g2d.geom.Rectangle.class, 
				new RectangleParser());

		Parser.registerObjectStringParser(
				java.awt.Color.class, 
				new AwtColorParser());
		Parser.registerObjectStringParser(
				java.awt.Rectangle.class, 
				new AwtRectangleParser());
	}
	
	public static class ColorParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (return_type.equals(com.g2d.Color.class)) {
				return new Color(str);
			}
			return null;
		}
		
		@Override
		public String toString(Object obj) {
			com.g2d.Color color = (com.g2d.Color) obj;
			return color.toHexString();
		}
	}
	
	
	public static class FontParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (return_type.equals(com.g2d.Font.class)) {
				try {
					String[] split = CUtil.splitString(str, ",", true);
					return AwtEngine.getEngine().createFont(
							split[0], 
							Integer.parseInt(split[2]), 
							Integer.parseInt(split[1]));
				} catch (Exception e) {
					return null;
				}
			}
			return null;
		}
		
		@Override
		public String toString(Object obj) {
			com.g2d.Font f = (com.g2d.Font) obj;
			return f.getName() + "," + f.getSize() + "," + f.getStyle(); 
		}
	}
	
	
	public static class RectangleParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (return_type.equals(Rectangle.class))
			{
				String[] strs = str.split(",");
				int x = Integer.parseInt(strs[0]);
				int y = Integer.parseInt(strs[1]);
				int w = Integer.parseInt(strs[2]);
				int h = Integer.parseInt(strs[3]);
				return new Rectangle(x, y, w, h);
			}
			return null;
		}
		
		@Override
		public String toString(Object obj) {
			Rectangle rect = (Rectangle) obj;
			return rect.x + "," + rect.y + "," + rect.width + "," + rect.height;
		}
	}
	
	public static class AwtColorParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (return_type.equals(java.awt.Color.class)) {
				String[] strs = str.split(",");
				int a = Integer.parseInt(strs[0]);
				int r = Integer.parseInt(strs[1]);
				int g = Integer.parseInt(strs[2]);
				int b = Integer.parseInt(strs[3]);
				return new java.awt.Color(r, g, b, a);
			}
			return null;
		}
		
		@Override
		public String toString(Object obj) {
			java.awt.Color color = (java.awt.Color) obj;
			return color.getAlpha() + "," + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue();
		}
	}
	
	public static class AwtRectangleParser implements IObjectStringParser
	{
		@Override
		public Object parseFrom(String str, Class<?> return_type) {
			if (return_type.equals(java.awt.Rectangle.class))
			{
				String[] strs = str.split(",");
				int x = Integer.parseInt(strs[0]);
				int y = Integer.parseInt(strs[1]);
				int w = Integer.parseInt(strs[2]);
				int h = Integer.parseInt(strs[3]);
				return new java.awt.Rectangle(x, y, w, h);
			}
			return null;
		}
		
		@Override
		public String toString(Object obj) {
			java.awt.Rectangle rect = (java.awt.Rectangle) obj;
			return rect.x + "," + rect.y + "," + rect.width + "," + rect.height;
		}
	}
	
	
//	@SuppressWarnings("unchecked")
//	public static <T> T parseObject(String str, Class<T> cls)
//	{
//		try
//		{
//			if (cls.equals(Byte.class))
//			{
//				return (T)new Byte(str);
//			}
//			else if (cls.equals(Boolean.class))
//			{
//				return (T)new Boolean(str);
//			}
//			else if (cls.equals(Short.class))
//			{
//				return (T)new Short(str);
//			}
//			else if (cls.equals(Character.class))
//			{
//				return (T)new Character(str.charAt(0));
//			}
//			else if (cls.equals(Integer.class))
//			{
//				return (T)new Integer(str);
//			}
//			else if (cls.equals(Long.class))
//			{
//				return (T)new Long(str);
//			}
//			else if (cls.equals(Float.class))
//			{
//				return (T)new Float(str);
//			}
//			else if (cls.equals(Double.class))
//			{
//				return (T)new Double(str);
//			}
//			else if (cls.equals(String.class))
//			{
//				return (T)(str);
//			}
//			/*--------------------------------------------------------------------------------------------*/
//			// 
//			else if (cls.equals(Color.class))
//			{
//				String[] strs = str.split(",");
//				int a = Integer.parseInt(strs[0]);
//				int r = Integer.parseInt(strs[1]);
//				int g = Integer.parseInt(strs[2]);
//				int b = Integer.parseInt(strs[3]);
//				return (T)(new Color(r, g, b, a));
//			}
//			else if (cls.equals(Rectangle.class))
//			{
//				String[] strs = str.split(",");
//				int x = Integer.parseInt(strs[0]);
//				int y = Integer.parseInt(strs[1]);
//				int w = Integer.parseInt(strs[2]);
//				int h = Integer.parseInt(strs[3]);
//				return (T)(new Rectangle(x, y, w, h));
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	public static String fromObject(Object obj)
//	{
//		try
//		{
//			if (obj != null)
//			{
//				Class<?> cls = obj.getClass();
//				
//				if (cls.equals(Color.class))
//				{
//					Color color = ((Color)obj);
//					return color.getAlpha() + "," + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
//				}
//				else if (cls.equals(Rectangle.class))
//				{
//					Rectangle rect = (Rectangle)obj;
//					return rect.x + "," + rect.y + "," +rect.width + "," +rect.height;
//				}
//				else
//				{
//					return obj.toString();
//				}
//			}
//			
//		
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	
}
