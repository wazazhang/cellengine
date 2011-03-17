package com.cell.classloader.jcl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import sun.plugin2.applet.Plugin2ClassLoader;


/**
 * 提供java动态加载的方法，在程序运行时，可将jar包动态加载进程序。
 * @author WAZA
 */
public class JarClassLoader extends ClassLoader
{
	final static String NATIVE_SUFFIX_WINDOWS	= ".dll";
	final static String NATIVE_SUFFIX_MAC_OS	= ".jnilib";
	final static String NATIVE_SUFFIX_LINUX		= ".so";
	final static String NATIVE_SUFFIX;
	static
	{
		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("windows")) {
			NATIVE_SUFFIX = NATIVE_SUFFIX_WINDOWS;
		} else if (os.startsWith("mac")) {
			NATIVE_SUFFIX = NATIVE_SUFFIX_MAC_OS;
		} else {
			NATIVE_SUFFIX = NATIVE_SUFFIX_LINUX;
		}
		System.out.println("current os : " + os);
		System.out.println("current native suffix : " + NATIVE_SUFFIX);
	}
	
//	final static public void loadClasses(
//			ClassLoader		root_class_loader,
//			Vector<byte[]> 	resources,
//			String 			key,
//			boolean 		is_sign_class)
//	{
//		try {
//			resources = dds(resources, key);
//			if (root_class_loader instanceof sun.plugin2.applet.JNLP2ClassLoader) {
//				sun.plugin2.applet.JNLP2ClassLoader jnl = (sun.plugin2.applet.JNLP2ClassLoader)root_class_loader;
//
//			}
//		} catch (Throwable err) {
//			err.printStackTrace();
//		}
//	}

	
	/**
	 * 创造一个JarClassLoader
	 * @param root_class_loader
	 * @param resources
	 * @param key
	 * @param is_sign_class
	 * @return
	 */
	final static public JarClassLoader createJarClassLoader(
			ClassLoader		root_class_loader,
			Vector<byte[]> 	resources,
			String 			key,
			boolean 		is_sign_class,
			boolean			decode)
	{
		try{
			JarClassLoader classloader = new JarClassLoader(
					root_class_loader,
					resources, 
					key, 
					is_sign_class, decode);
			return classloader;
		} catch(Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将所有的字节流解码
	 * @param resources
	 * @param key
	 * @return
	 * @throws Exception
	 */
	final static private Vector<byte[]> dds(Vector<byte[]> resources, String key) throws Exception
	{
		if (key != null) {
			Vector<byte[]> resources_enc = new Vector<byte[]>(resources.size());
			for (byte[] resource : resources) {
				CC enc = new CC(key);
				resources_enc.add(enc.dd(resource));
				System.out.println("decode jar : " + resource.length + " bytes");
			}
			resources = resources_enc;
		}
		return resources;
	}
	
	/**
	 * 得到一个字符串的前缀
	 * @param name
	 * @param suffix
	 * @return
	 */
	final static private String getPrefix(String name, String suffix) {
		if (name.endsWith(suffix)) {
			return name.substring(0, name.length() - suffix.length());
		}
		return name;
	}

	/**
	 * 完全读入一个jar包的内容
	 * @param jar
	 * @return
	 * @throws IOException
	 */
	final static private byte[] getResourceData(JarInputStream jar) throws IOException 
	{
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int size;
		while (jar.available() > 0) {
			size = jar.read(buffer);
			if (size > 0) {
				data.write(buffer, 0, size);
			}
		}
		return data.toByteArray();
	}

	/**
	 * 调用Class里的静态方法
	 * @param clz
	 * @param methodName
	 * @param args
	 */
	final static public void callMain(Class<?> clz, String[] args)
	{
		try {
			Method method = clz.getMethod("main", String[].class);
			Object[] inArg = new Object[1];
			inArg[0] = args;
			method.invoke(clz, inArg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
//	final static public void loadNatives(JarClassLoader loader, ArrayList<String> native_libs)
//	{
//		loader.initAllNatives();
//		for (String native_lib : native_libs) {
//			try{
//				if (native_lib.length()>0) {
//					String path = loader.findLibrary(native_lib);
//					System.loadLibrary(native_lib);
//					System.out.println("load native : " + native_lib + " : " + path);
//				}
//			}catch(Throwable err){
//				err.printStackTrace();
//			}
//		}
//	}
//	----------------------------------------------------------------------------------------------------------------------------------
	// 
	private boolean		is_set_ProtectionDomain	= true;
	
	private ClassLoader	root_class_loader;
	
	//资源缓存
	private HashMap<String, byte[]> Resources	= new HashMap<String, byte[]>();
	private HashMap<String, byte[]> Classes		= new HashMap<String, byte[]>();
	
	HashMap<String, String>	native_paths		= new HashMap<String, String>();
	
	//class资源及实体缓存
	//private ArrayList<String> classNames = new ArrayList<String>();
//	----------------------------------------------------------------------------------------------------------------------------------

	JarClassLoader(
			ClassLoader		class_loader,
			Vector<byte[]> 	resources,
			String 			key,
			boolean 		is_sign_class, 
			boolean			decode) throws Throwable
	{
//		super(class_loader);
		if (decode) {
			resources = dds(resources, key);
		}

		HashMap<String, byte[]> natives		= new HashMap<String, byte[]>();
		
		this.is_set_ProtectionDomain	= is_sign_class;
		this.root_class_loader 			= class_loader;

		for (byte[] resource : resources)
		{
			// 将byte[]转为JarInputStream
			JarInputStream jar = new JarInputStream(new ByteArrayInputStream(resource));
			
			// 依次获得对应JAR文件中封装的各个被压缩文件的JarEntry
			JarEntry entry;
			while ((entry = jar.getNextJarEntry()) != null)
			{
				String name	= entry.getName();
				String ex	= name.toLowerCase();
				
				// put classes
				if (ex.endsWith(".class")) 
				{
					String class_name = getPrefix(name, ".class").replace('/', '.');
					byte[] data = getResourceData(jar);
					this.Classes.put(class_name, data);
				}
				// put native
				else if (ex.endsWith(NATIVE_SUFFIX)) 
				{
					String native_name = getPrefix(name, NATIVE_SUFFIX);
					byte[] data = getResourceData(jar);
					natives.put(native_name, data);
					System.out.println("get native lib : " + name);
				}
				// other resources
				else
				{
					byte[] data = getResourceData(jar);
					if (name.charAt(0) != '/') {
						name = "/" + name;
					}
					this.Resources.put(name, data);
				}
			}
		}
		
		try 
		{
			// 将本地库缓存到jre系统目录或者用户目录
			String libpath		= System.getProperty("java.library.path");
			String user_home	= System.getProperty("user.home");
			File native_file_dir = null;
			StringTokenizer st	= new StringTokenizer(libpath, File.pathSeparator);
			if (st.hasMoreElements()) {
				// 首先尝试存入jre系统目录
				native_file_dir = new File(st.nextToken());
			} else {
				// 然后尝试存入用户目录
				native_file_dir	= new File(user_home+File.separatorChar+"jni_natives_cache");
				native_file_dir.mkdirs();
			}
			
			for (String libname : natives.keySet()) {
				File file = new File(native_file_dir, libname + NATIVE_SUFFIX);
				try{
					byte[] native_data = natives.get(libname);
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(native_data);
					fos.flush();
					fos.close();
				}catch(Throwable err){
					System.err.println("error save native cache ! " + err.getMessage());
				}
				native_paths.put(libname, file.getPath());
				System.out.println("cache library : " + libname + " : " + file);
			}
			natives.clear();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
//	----------------------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public Class<?> findClass(String className) throws ClassNotFoundException 
	{
		try{
			Class clazz = this.findLoadedClass(className);
			if (null == clazz) {
				byte[] bytes = Classes.get(className);
				if (bytes != null) {
					if (is_set_ProtectionDomain) {
						clazz = defineClass(className, bytes, 0, bytes.length, 
								this.getClass().getProtectionDomain());
					}else{
						clazz = defineClass(className, bytes, 0, bytes.length);
					}
				}
			}
			if (null == clazz) {
				clazz = root_class_loader.loadClass(className);
			}
			if (null == clazz) {
				clazz = Class.forName(className, true, root_class_loader);
				this.resolveClass(clazz);
			}
			return clazz;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("JarClassLoader : " + e.getMessage());
		}
		return super.findClass(className);
	}

	@Override
	public String findLibrary(String libname)
	{
		String path = native_paths.get(libname);
		System.out.println("JarClassLoader.findLibrary : " + libname + " : " + path);
		return path;
	}
	
	public InputStream getResourceAsStream(String path) {
		String name = path;
		if (name.charAt(0) != '/') {
			name = "/" + name;
		}
		byte[] data = Resources.get(name);
		if (data != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			return bais;
		}
		InputStream is = root_class_loader.getResourceAsStream(path);
		if (is != null) {
			return is;
		}
		return super.getResourceAsStream(path);
	}
	

//	----------------------------------------------------------------------------------------------------------------------------------

}
