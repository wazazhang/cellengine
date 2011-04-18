package com.cell.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;


public abstract class Config
{
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Config config, String file)
	{
		return load(config, config.getClass(), file, true);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param is
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Config config, InputStream is)
	{
		return load(config, config.getClass(), is, true);
	}

	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Config config, java.util.Properties cfg)
	{
		return load(config, config.getClass(), new Properties(cfg), true);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, String file)
	{
		return load(config_class, file, true);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param is
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, InputStream is)
	{
		return load(null, config_class, is, true);
	}

	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, java.util.Properties cfg)
	{
		return load(null, config_class, new Properties(cfg), true);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, String file, boolean verbos)
	{
		return load(null, config_class, file, verbos);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, InputStream is, boolean verbos)
	{
		return load((Config)null, config_class, is, verbos);
	}
	

	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, java.util.Properties cfg, boolean verbos)
	{
		return load((Config)null, config_class, new Properties(cfg), verbos);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	protected static Map<Field, Object> load(Config instance, Class<? extends Config> config_class, String file, boolean verbos)
	{
		return load(instance, config_class, CIO.getInputStream(file), verbos);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param is
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	protected static Map<Field, Object> load(Config instance, Class<? extends Config> config_class, InputStream is, boolean verbos)
	{
		Properties cfg = new Properties();
		cfg.load(is);
		return load(instance, config_class, cfg, verbos);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param is
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	protected static Map<Field, Object> load(Config instance, Class<? extends Config> config_class, Properties cfg, boolean verbos)
	{
		HashMap<Field, Object> map = new HashMap<Field, Object>();
		
		StringBuilder sb = new StringBuilder();
		toCommetLine(sb, "#", 100);
		sb.append("### " + config_class.getName() + "\n");
		ConfigType class_sign = config_class.getAnnotation(ConfigType.class);
		if (class_sign != null) {
			toCommet(sb, class_sign.value());
		}
		toCommetLine(sb, "#", 100);
		System.out.print(sb);
		System.out.println("loading config \"" + config_class.getName() + "\"");
		
		
		try
		{			
			Field[] fields = config_class.getFields();
			
			for (Field field : fields)
			{
				try
				{
					String v = cfg.get(field.getName());
					Object value = null;
					
					if (v != null) {
						value = Parser.stringToObject(v.trim(), field.getType());
						if (value != null) {
							try {
								field.set(instance, value);
								map.put(field, value);
							} catch (Exception e) {
								value = null;
							}
						}
					}
					if (verbos)
					{
						ConfigField field_sign = field.getAnnotation(ConfigField.class);

						if (field_sign != null) 
						{
							for (String fs : field_sign.value()) {
								String[] splits = CUtil.splitString(fs, "\n");
								for (String s : splits) {
									System.out.println("\t### " + s.trim());
								}
							}
						}
						
						if (v == null)
						{
								System.out.println("\t"+ //
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " + // 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   " + //
										" (default)");// 
						}
						else if (value == null)
						{
								System.err.println("\t"+// 
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " +// 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   " +// 
										" (bad exchange \"" + v + "\", set default)");// 
						}
						else
						{
								System.out.println("\t"+// 
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " + // 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   ");// 
						}
					}
					
					
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("load config completed !");
		return map;
	}
	
//	----------------------------------------------------------------------------------------------------------------
	static private void toCommetLine(StringBuilder sb, String s, int size) {
		sb.append("###");
		for (int i=0; i<size; i++) {
			sb.append(s);
		}
		sb.append('\n');
	}
	
	static private void toCommet(StringBuilder sb, String ... src)
	{
		for (String v : src) {
			String[] splits = CUtil.splitString(v, "\n");
			for (String s : splits) {
				sb.append("### " + s + "\n");
			}
		}
	}
	
	/**
	 * 保存文档
	 * @param config_class
	 * @return
	 */
	static public String toProperties(Class<? extends Config> config_class) {
		return toProperties(null, config_class, 32);
	}
	
	/**
	 * 保存文档
	 * @param instance
	 * @param config_class
	 * @param name_width
	 * @return
	 */
	static public String toProperties(Config instance, Class<? extends Config> config_class, int name_width) 
	{
		StringBuilder sb = new StringBuilder();
		toCommetLine(sb, "#", name_width << 1);
		sb.append("### " + config_class.getName() + "\n");
		ConfigType class_sign = config_class.getAnnotation(ConfigType.class);
		if (class_sign != null) {
			toCommet(sb, class_sign.value());
		}
		toCommetLine(sb, "#", name_width << 1);
		sb.append("\n\n");
		
		try
		{
			Field[] fields = config_class.getFields();
			for (Field field : fields)
			{
				try
				{
					ConfigSeparator separator = field.getAnnotation(ConfigSeparator.class);
					if (separator != null) {
						sb.append("\n");
						toCommetLine(sb, separator.fill(), name_width << 1);
						toCommet(sb, separator.value());
						toCommetLine(sb, separator.fill(), name_width << 1);
						sb.append("\n");
					}
					
					ConfigField field_sign = field.getAnnotation(ConfigField.class);
					if (field_sign != null || (class_sign!=null && class_sign.is_all_field())) {
						if (field_sign != null) {
							toCommet(sb, field_sign.value());
						}
						Object value = field.get(instance);
						if (value != null) {
							value = CUtil.replaceString(value.toString(), "\n", "+\n");
						}
						if (value != null) {
							sb.append(
									CUtil.snapStringRightSize(field.getName(),  name_width, ' ') + 
									" = " +
									CUtil.snapStringRightSize(value.toString(),	name_width, ' ') + 
									"\n");
						} else {
							sb.append("# " +
									CUtil.snapStringRightSize(field.getName(),  name_width, ' ') + 
									" = (null) <-- fill value here \n");
						}
						sb.append("\n");
					}
				} catch (Exception e) {
					sb.append("### " + e.getMessage() + "\n");
				}
			}
		} catch (Exception e) {
			sb.append("### " + e.getMessage() + "\n");
		}
		return sb.toString();
	}
	
//	----------------------------------------------------------------------------------------------------------------

}
