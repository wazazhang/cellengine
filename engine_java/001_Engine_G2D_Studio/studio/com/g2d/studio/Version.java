package com.g2d.studio;

import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cell.CIO;
import com.cell.CUtil;

public class Version 
{
	final static public long VersionGS = 1L;
	

	final public static String	VERSION = "1.0";
	
	
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	private static Integer		REVERSION;
	private static String		REVERSION_TIME;

	public static Integer getReversion() {
		return REVERSION;
	}
	
	public static String getReversionTime() {
		return REVERSION_TIME;
	}
	
	public static String getFullVersion() {
		return VERSION + "." + REVERSION;
	}
	
	static 
	{
		initReversion("/deployed/.svn/entries");
		System.out.println("************************************************************************************");
		System.out.println("* rev : " + getFullVersion() + " @ " + getReversionTime());
		System.out.println("************************************************************************************");
	}
	
	
	protected static void initReversion(String path)
	{
		try 
		{
			String[] lines = readAllLines(path);
			
			if (lines != null) 
			{
				for (int i = 0; i < lines.length; i++) 
				{
					String line = lines[i];
					if (line.equals("dir") && REVERSION == null)
					{
						try{
							REVERSION = Integer.parseInt(lines[i+1]);
							System.out.println("found svn entries : " + REVERSION);
						}catch(Exception err){}
					} 
					else if (REVERSION_TIME == null) 
					{
						Pattern pattern = Pattern.compile("" +
								"[0-9]{4}-[0-9]{2}-[0-9]{2}" +
								"T[0-9]{2}:[0-9]{2}:[0-9]{2}");//正则表达式
						Matcher matcher_name = pattern.matcher(line);
						if(matcher_name.find()) {
							int ti = 0;
							String YYYY = line.substring(ti, ti+4); ti += 5;
							String MM   = line.substring(ti, ti+2); ti += 3;
							String DD   = line.substring(ti, ti+2); ti += 3;
							
							String hh   = line.substring(ti, ti+2); ti += 3;
							String mm   = line.substring(ti, ti+2); ti += 3;
							String ss   = line.substring(ti, ti+2); ti += 3;
							
							Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
							cal.set(Integer.parseInt(YYYY), Integer.parseInt(MM)-1, Integer.parseInt(DD), 
									Integer.parseInt(hh),   Integer.parseInt(mm),   Integer.parseInt(ss));

							REVERSION_TIME	= cal.getTime().toLocaleString();

							System.out.println(
									"found svn entries : " + 
									YYYY + "-" + MM + "-" + DD + " " + hh + ":" + mm + ":" + ss +
									" -> " + REVERSION_TIME);
						}
					}
				}
			} else {
				System.err.println("reversion not found : " + path);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	
	}
	
	protected static String[] readAllLines(String path)
	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (is == null) {
			path = path.substring(1);
		}
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (is != null) {
			byte[] data = CIO.readStream(is);
			String text = new String(data);
			return CUtil.splitString(text, "\n");
		}
		return null;
	}

	
	
}
