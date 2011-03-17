package com.cell.classloader.jcl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * 提供java动态编译的方法，在程序运行时，可将java文件动态编译成类，装载进程序。
 * @author WAZA
 */
public class JavaCompiler extends ClassLoader
{
	/**
	 * 编译并装载指定的java文件，注意，该方法并不装载内部类
	 * @param name		定义的类名
	 * @param javaFile	文件名
	 * @return 编译并装载完成的类
	 * @throws Exception
	 */
	public static Class<?> compileAndLoadClass(String name, File javaFile) throws Throwable
	{
		ArrayList<Class<?>> ret = new ArrayList<Class<?>>();
		Class<?> simple_clazz = instance_compiler.loadClass(name, javaFile, ret);
		return simple_clazz;
	}
	
	/**
	 * 编译并装载指定的java文件，包含内部类
	 * @param name		定义的主类名
	 * @param javaFile	文件名
	 * @return 编译出的所有类
	 * @throws Throwable
	 */
	public static ArrayList<Class<?>> compileAndLoadAllClass(String name, File javaFile) throws Throwable
	{
		ArrayList<Class<?>> ret = new ArrayList<Class<?>>();
		instance_compiler.loadClass(name, javaFile, ret);
		return ret;
	}
	
	/**
	 * 调用java编译命令
	 * @param javaFile	文件名
	 * @return classFile 编译完的class文件
	 * @throws IOException
	 */
	public static File compile(String javaFile) throws Throwable
	{
//		com.sun.tools.javac.Main.compile(new String[]{"-help"});
//		com.sun.tools.javac.Main.compile(new String[]{javaFile});
		Class<?> cls = Class.forName("com.sun.tools.javac.Main");
		System.out.println("Compiling java file : \"" + javaFile + "\" : " + 
				callMethod(cls, "compile", new String[]{"-encoding", "UTF-8", javaFile}));
		File classFile = new File(javaFile.substring(0, javaFile.lastIndexOf(".java"))+".class");
		if (!classFile.exists()) {
			throw new ClassNotFoundException("build java class not found : " + classFile.getPath());
		} 
		return classFile;
	}

	/**
	 * 调用该class内的静态方法
	 * @param clz
	 * @param methodName
	 * @param args
	 */
	final static public Object callMethod(Class<?> clz, String methodName, String[] args)
	{
		Class<?>[] arg = new Class<?>[1];
		arg[0] = args.getClass();
		try {
			Method method = clz.getMethod(methodName, arg);
			Object[] inArg = new Object[1];
			inArg[0] = args;
			return method.invoke(clz, inArg);
		} catch (Exception e) {
			e.printStackTrace();
			return e;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------

	static JavaCompiler instance_compiler = new JavaCompiler();

	final static public JavaCompiler getInstance() {
		return instance_compiler;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------

	private byte[] getBytes(File file) throws IOException 
	{
		long len = file.length();
		byte raw[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		try{
			int r = fin.read(raw);
			if (r != len)
				throw new IOException("Can't read all, " + r + " != " + len);
			return raw;
		}finally{
			fin.close();
		}
	}

	synchronized private Class<?> loadClass(String name, File javaFile, Collection<Class<?>> inner_class) throws Throwable 
	{
		Class<?> ret_class = null;
		
		if (javaFile.exists())
		{
			String javaFilename = javaFile.getPath();

			try 
			{
				File	simple_file 	= compile(javaFilename);
				String	simple_name 	= simple_file.getName();
						simple_name 	= simple_name.substring(0, simple_name.lastIndexOf(".class"));
				String	package_name	= name.contains(".") ? name.substring(0, name.lastIndexOf('.')+1) : "";
			
				{
					byte raw[] = getBytes(simple_file);
					ret_class = defineClass(name, raw, 0, raw.length);
//					System.out.println("define main class : " + ret_class.getName());
					resolveClass(ret_class);
					if (inner_class != null) {
						inner_class.add(ret_class);
					}
					simple_file.delete();
				}
				
				ArrayList<File> builded_class_files = new ArrayList<File>();
			
				try{
					
					for (File class_file : simple_file.getParentFile().listFiles()) 
					{
						String class_name = class_file.getName();
						
						if (class_name.endsWith(".class") && class_name.startsWith(simple_name)) 
						{
							builded_class_files.add(class_file);
							
							class_name = package_name + class_name.substring(0, class_name.lastIndexOf(".class"));
						
							try{
								byte raw[] = getBytes(class_file);
								Class<?> clazz = defineClass(class_name, raw, 0, raw.length);
								if (clazz != null) {
//									System.out.println("\tdefine class : " + clazz.getName());
									resolveClass(clazz);
									if (inner_class != null) {
										inner_class.add(clazz);
									}
								}
							}catch(Throwable err){
								System.err.println("\tdefine class error : " + class_name);
							}
						}
					}
				}
				finally{
					for (File class_file : builded_class_files) {
						class_file.delete();
					}
				}
				
			} catch (Throwable ie) {
				throw ie;
			}
		}
		
		return ret_class;
	}

	
	
	
}
