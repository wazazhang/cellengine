package com.cell.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.TreeNode;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.util.StringFilters;
import com.cell.util.Pair;


/**
 * MD5的算法在RFC1321 中定义
 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 * 
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 * 
 */
public class MD5
 {

	final public static int COVERT_TYPE_MD5 = 5;
	
	final public static int COVERT_TYPE_MD6 = 6;
	
	
	public static final String getMD5cyc(String str) {
		return getMD6(str);
	}
	
	public static final String getMD5cyc(byte[] data) {
		return getMD6(data);
	}
	
	public static final String getMDX(byte[] data, int type) {
		if (type==COVERT_TYPE_MD5) {
			return getMD5(data);
		}else if (type==COVERT_TYPE_MD6) {
			return getMD5cyc(data);
		}else{
			return null;
		}
	}
	
	public static final String getMDX(String data, int type) {
		if (type==COVERT_TYPE_MD5) {
			return getMD5(data);
		}else if (type==COVERT_TYPE_MD6) {
			return getMD5cyc(data);
		}else{
			return null;
		}
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------

	// 用来将字节转换成 16 进制表示的字符
	final static char hexDigits[] = { 
			'0', '1', '2', '3', 
			'4', '5', '6', '7',
			'8', '9', 'a', 'b', 
			'c', 'd', 'e', 'f' };
	
	private static String md5(byte[] source)
	{
		String s = null;

		try
		{
			java.security.MessageDigest md = java.security.MessageDigest .getInstance("MD5");
			
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	
	}
	
	public static String getMD5(byte[] source)
	{
		return md5(source);
	}
	
    public static String getMD5(String str) {
    	return md5(str.getBytes());
    }
    
    public static String getMD5(String str, String encode) {
    	try {
			return md5(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
    private static String md6(byte[] source)
    {
    	String str = md5(source);
    	
    	if (str!=null) 
    	{
    		String str_ret = "";
    		int count = str.length();
    		if (count == 32) {
    			for (int i = 0; i < count; i += 2) {
    				str_ret += str.charAt(i);
    			}
    			for (int i = 1; i < count; i += 2) {
    				str_ret += str.charAt(i);
    			}
    		} 
    		return str_ret;
    	}
    	
    	return str;
    }
    
	public static String getMD6(byte[] source){
		return md6(source);
	}
	
    public static String getMD6(String str) {
    	return md6(str.getBytes());
    }
    
    public static String getMD6(String str, String encode) {
    	try {
			return md6(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    
    }
  
//	------------------------------------------------------------------------------------------------------------------------------

//	------------------------------------------------------------------------------------------------------------------------------
 
//    private static int processed_files = 0;
//    private static int ignored_files = 0;
//    
//    private static boolean processFilter(
//    		File	file,
//    		ArrayList<Pattern> filters_add, 
//    		ArrayList<Pattern> filters_dec) 
//    {
//    	String fname = file.getPath().replaceAll("\\\\", "/");
//		if (filters_dec != null) {
//    		// 判断所有需要排除的
//    		for (Pattern ft : filters_dec) {
//    			if (ft.matcher(fname).find()) {
////    				System.out.println("ignore: " + file.getPath());
//    				ignored_files ++;
//        			return false;
//    			}
//    		}
//		}
//		if (filters_add != null && !filters_add.isEmpty()) {
//			// 判断所有需要包含的
//    		for (Pattern ft : filters_add) {
//    			if (ft.matcher(fname).find()) {
//					return true;
//				}
//    		}
////			System.out.println("ignore: " + file.getPath());    		
//    		ignored_files ++;
//    		return false;
//		}
//    	return true;
//    }

    private static String processVerbos(int verbos, int size, long date, String name) {
    	String dst = "";
    	if ((verbos & VERBOS_FLAG_FILE_SIZE) != 0) {
			dst += " : " + CUtil.snapStringRightSize(size+"", 10, ' ');
		}
		if ((verbos & VERBOS_FLAG_FILE_DATE) != 0) {
			dst += " : " + CUtil.snapStringRightSize(date+"", 13, ' ');
		}
		if ((verbos & VERBOS_FLAG_FILE_NAME) != 0) {
			dst += " : " + name + "\t";
		}
		return dst;
    }
    
    private static void processSrcText(
    		String srcText, 
    		File dstFile, 
    		int CoverType,
    		int verbos,
    		StringBuilder output) throws Exception
    {
    	String dst = getMDX(srcText, CoverType) + ((verbos&VERBOS_FLAG_FILE_NAME)!=0 ? " : " + srcText : "");
		System.out.println(dst);
		output.append(dst + "\n");
    }
    
    private static void processSrcFile(
    		File srcFile, 
    		File dstFile, 
    		int CoverType,
    		int verbos,
    		StringFilters filters, 
    		Pair<AtomicInteger, AtomicInteger> ret, 
    		StringBuilder output) throws Exception
    {
		if (!srcFile.exists())
		{
			System.err.println("src file is not exist!");
		}
		else if (srcFile.isHidden()) 
		{
			System.err.println("src file is hidden!");
    	}
		else if (srcFile.isDirectory()) 
		{
			File[] files = srcFile.listFiles();
			
			for (int l=0; l<files.length; l++)
			{
				if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(dstFile)){
					continue;
				}
				else {
					if (filters != null && !filters.accept(files[l])) {
						ret.getValue().incrementAndGet();
						continue;
					}
					FileInputStream fis = new FileInputStream(files[l]);
					byte[] data = CIO.readStream(fis);
					String dst = MD5.getMDX(data, CoverType);
					dst += processVerbos(verbos, data.length, files[l].lastModified(), files[l].getPath());
					System.out.println(dst);
					output.append(dst + "\n");
				}
			}
		}
		else
		{
			FileInputStream fis = new FileInputStream(srcFile);
			byte[] data = CIO.readStream(fis);
			String dst = getMDX(data, CoverType);// + (IsOutputSrc ? " : " + srcFile.getPath() : "");
			dst += processVerbos(verbos, data.length, srcFile.lastModified(), srcFile.getPath());
			System.out.println(dst);
			output.append(dst + "\n");
		    ret.getKey().incrementAndGet();
		}

    }
    
    private static void processSrcDir(
    		File srcDir, 
    		File dstFile, 
    		int CoverType,
    		int verbos,
    		StringFilters filters, 
    		Pair<AtomicInteger, AtomicInteger> ret, 
    		StringBuilder output) throws Exception
    {
		if (!srcDir.exists())
		{
			System.err.println("src dir is not exist!");
		}	
		else if (srcDir.isHidden()) {
    		
    	}
		else if (srcDir.isDirectory()) 
		{
			File[] files = srcDir.listFiles();
			
			for (int l=0; l<files.length; l++)
			{
				if (files[l].isHidden()){
					continue;
				}
				if (files[l].isDirectory()) {
					processSrcDir(files[l], dstFile, CoverType, verbos, filters, ret, output);
					continue;
				} else {
					if (filters != null && !filters.accept(files[l])) {
						ret.getValue().incrementAndGet();
						continue;
					}
					FileInputStream fis = new FileInputStream(files[l]);
					byte[] data = CIO.readStream(fis);
					String dst = MD5.getMDX(data, CoverType);
					dst += processVerbos(verbos, data.length, files[l].lastModified(), files[l].getPath());
					System.out.println(dst);
					output.append(dst + "\n");
					ret.getKey().incrementAndGet();
				}
			}
		}
    }
    
    private static void processSrcTextFile(
    		File srcTextFile, 
    		File dstFile, 
    		int CoverType,
    		int verbos,
    		StringBuilder output, 
    		String srcTextFileEncoding, 
    		Pair<AtomicInteger, AtomicInteger> ret) throws Exception
    {
		if (!srcTextFile.exists()) 
		{
			System.err.println("src textfile is not exist!");
		}
		else if (srcTextFile.isHidden()) 
		{
			System.err.println("src textfile is hidden!");
    	}
		else if (srcTextFile.isDirectory())
		{
			System.err.println("src textfile can not be a directory!!");
		}
		else 
		{
			FileInputStream fis = new FileInputStream(srcTextFile);
			byte[] data = CIO.readStream(fis);
			String text = null;
			if (srcTextFileEncoding!=null) {
				text = new String(data, srcTextFileEncoding);
			}else{
				text = new String(data);
			}
			String[] lines = text.split("\n");
			
			for (int l=0; l<lines.length; l++)
			{
				String src = lines[l].trim();
				String dst = getMDX(src, CoverType);
				dst += processVerbos(verbos&VERBOS_FLAG_FILE_NAME, 0, 0, src);
				System.out.println(dst);
				output.append(dst + "\n");
				 ret.getKey().incrementAndGet();
			}
		}
	
    }
    
    private static void processDstFile(
    		File dstFile, 
    		String dstEncoding, 
    		boolean dstAppend,
    		StringBuilder output) throws Exception
    {

		if (dstFile.exists()==false) {
			dstFile.createNewFile();
			dstAppend = false;
		}
		
		FileOutputStream fos = new FileOutputStream(dstFile, dstAppend);
		
		try {
			if (dstEncoding == null) {
				fos.write(output.toString().getBytes());
			} else {
				fos.write(output.toString().getBytes(dstEncoding));
			}
			fos.flush();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			fos.close();
		}
    }
    
	public static void main(String args[]) 
    {
		String usage = 
			"***********************************************************************\n" +
			"* Old functions                                                       *\n" +
			"***********************************************************************\n" +
			"-md5(-md6)(:v)\n" +
			" output type md5 or md6.\n" +
			" :v 是否在最后显示原,例如:\n" +
			" 输出的字符为\"103a813a4961ceaeebe1af7866a2d124 : ./reg2.png\"那么\".reg2.png\"为原\n" +
			" 如果不加:v,那么输出格式为\"103a813a4961ceaeebe1af7866a2d124\"\n" +
			"\n" + 
			"-md5(-md6)(:v) -txt:<字符串>\n" +
			" 输入字符串,输出该字符串的md5(md6)值\n" +
			"\n" +
			"-md5(-md6)(:v) -f:<文件名>\n" +
			" 输入二进制文件,输出该文件的md5(md6)值\n" +
			"\n" +
			"-md5(-md6)(:v) -fn:<文本文件名> (-enc:<可选,文本编码,默认为ASCII>) (-fout:<可选,输出到文件,默认到控制台>)\n" +
			" 输入文本文件,输出文本文件的每行的md5(md6)值\n" +
			"\n" + 
			"-md5(-md6)(:v) -dir:<文件夹名> (-fout:<可选,输出到文件,默认到控制台>)\n" +
			" 输入一个文件夹,输出该文件夹内所有文件的md5(md6)值\n" +
			" 忽略文件夹和输出文件和隐藏文件\n" +
			"\n" +
			"-md5(-md6)(:v) -cmp:<文件(文件夹)名> (-fout:<可选,输出到文件,默认到控制台>)\n" +
			" 输入一个文件(文件夹),输出一个md5(md6)值,一般用于比较2个文件(文件夹)内容是否相等\n" +
			"\n" + 
		
			"***********************************************************************\n" +
			"* New functions  所有方法将全局忽略输出文件和隐藏文件                          *\n" +
			"***********************************************************************\n" +
			"--md5(--md6)\n" +
			"	使用md5或md6方式\n\n" +
			
			"-verbos[:s][:d]\n" +
			"	是否连同原一起输出\n" +
			"	假如源是一个文件名，可选[:s]文件尺寸，可选[:d]文件日期；比如-verbos:sd\n\n" +
			
			"-srcText:<字符串>\n" +
			"	输入字符串\n\n" +
			
			"-srcTextFile:<文件名>\n" +
			"	输入文本文件,计算每行的md5值\n\n" +
			
			"-srcTextFileEnc:<编码方式>\n" +
			"	输入文件的编码方式\n\n" +
			
			"-srcFile:<文件名>\n" +
			"	输入文件,如果为目录的话,将计算该目录下每个文件的md5值\n\n" +
			
			"-srcDir:<文件夹>\n" +
			"	输入文件夹,并递归。\n\n" +
			
			"-dstFile:<输出文件名>\n" +
			"	输出文件名,将输出文本文件\n\n" +
			
			"-dstAppend\n" +
			"	是否附加到输出文件的末尾\n\n" +
			
			"-dstEnc:<编码方式>\n" +
			"	输出文件的编码方式\n\n" +
			
			"-filter:<过滤器，+(-)正则表达式>\n" +
			"	+代表包含(默认)，-代表不包含。比如:-\\*+.svn(排除所有.svn目录)\n" +
			"	多项时用 ; 分隔，比如:+.png;+.jpg\n" +
			"	(该项只有在递归或枚举文件夹时有效)\n\n" +
			"";
		
		
		try {
			if (args != null) {
				if (args.length > 1 && args[0].startsWith("--")) {
					Pair<AtomicInteger, AtomicInteger> files = mainNew(args);
					System.out.println(
							CUtil.snapStringRL(
									new Object[]{files.getKey().get(),  files.getValue().get()}, 
									new Object[]{"processed ",    "ignored "}, 
									' ', " : "));
					return;
				} else if (args.length > 1 && args[0].length() >= "-md5".length()) {
					mainOld(args);
					return;
				}
			}
			System.out.println(usage);
		} catch (Throwable err) {
			err.printStackTrace();
			System.out.println(usage);
		}
	}

	static final public int VERBOS_FLAG_FILE_NAME = 1;
	static final public int VERBOS_FLAG_FILE_SIZE = 2;
	static final public int VERBOS_FLAG_FILE_DATE = 4;
	
	private static Pair<AtomicInteger, AtomicInteger> mainNew(String args[]) throws Exception
	{
		Pair<AtomicInteger, AtomicInteger> ret = new Pair<AtomicInteger, AtomicInteger>(
				new AtomicInteger(0), new AtomicInteger(0));
		
		int 	CoverType			= 0;
		int 	verbos				= 0;
		
		String	srcText				= null;
		File	srcFile				= null;
		File	srcDir				= null;
		File	srcTextFile			= null;
		String	srcTextFileEncoding	= null;
		
		File 	dstFile				= null;
		boolean	dstAppend			= false;
		String	dstEncoding			= null;
		
		StringFilters filters			= null;
		
//		ArrayList<Pattern> filters_add = null;
//		ArrayList<Pattern> filters_dec = null;
		
		for (int i=0; i<args.length; i++) 
		{
			if (args[i].toLowerCase().startsWith("--md5")){
				CoverType = COVERT_TYPE_MD5;
			}
			else if (args[i].toLowerCase().startsWith("--md6")) {
				CoverType = COVERT_TYPE_MD6;
			}
			else if (args[i].toLowerCase().startsWith("-verbos")){
				verbos = VERBOS_FLAG_FILE_NAME;
				String append = args[i].substring("-verbos".length());
				if (append.contains("s")) {
					verbos |= VERBOS_FLAG_FILE_SIZE;
				}
				if (append.contains("d")) {
					verbos |= VERBOS_FLAG_FILE_DATE;
				}
			}
			
			else if (args[i].toLowerCase().startsWith("-srctext:")){
				srcText = args[i].substring("-srctext:".length());
			}
			else if (args[i].toLowerCase().startsWith("-srcfile:")){
				srcFile = new File(args[i].substring("-srcfile:".length()));
			}
			else if (args[i].toLowerCase().startsWith("-srcdir:")){
				srcDir = new File(args[i].substring("-srcdir:".length()));
			}
			
			
			else if (args[i].toLowerCase().startsWith("-srctextfile:")){
				srcTextFile = new File(args[i].substring("-srctextfile:".length()));
			}
			else if (args[i].toLowerCase().startsWith("-srctextfileenc:")){
				srcTextFileEncoding = args[i].substring("-srctextfileenc:".length());
			}
			
			else if (args[i].toLowerCase().startsWith("-dstfile:")){
				dstFile = new File(args[i].substring("-dstfile:".length()));
			}
			else if (args[i].toLowerCase().startsWith("-dstappend")){
				dstAppend = true;
			}
			else if (args[i].toLowerCase().startsWith("-dstenc:")){
				dstEncoding = args[i].substring("-dstenc:".length());
			}
			else if (args[i].toLowerCase().startsWith("-filter:")){
				String fts = args[i].substring("-filter:".length()).trim();
				filters = new StringFilters(fts);
			}
			
		}
		
		// process
		{
			StringBuilder output = new StringBuilder();
			
			if (srcText!=null) 
			{
				processSrcText(srcText, dstFile, CoverType, verbos, output);
			}
			
			if (srcFile!=null) 
			{
				processSrcFile(srcFile, dstFile, CoverType, verbos, filters, ret, output);
			}
			
			if (srcDir != null) 
			{
				processSrcDir(srcDir, dstFile, CoverType, verbos, filters, ret, output);
			}
			
			if (srcTextFile!=null) 
			{
				processSrcTextFile(srcTextFile, dstFile, CoverType, verbos, output, srcTextFileEncoding, ret);
			}
			
			if (dstFile!=null) 
			{
				processDstFile(dstFile, dstEncoding, dstAppend, output);
			}
		}
		
		return ret;
	
	}
	
	private static void mainOld(String[] args) throws Exception
	{

		boolean isShowSrc = false;
		if (args[0].length()>"-md5:".length()) {
			String p = args[0].substring("-md5:".length());
			if (p.startsWith("v")) isShowSrc = true;
		}
		
		if (args[1].startsWith("-txt:")) 
		{
			String src = args[1].substring("-txt:".length());
			
			if (args[0].startsWith("-md5")) {
				System.out.println(MD5.getMD5(src)
						+(isShowSrc ? " : " + src : "")
						);
			} else {
				System.out.println(MD5.getMD5cyc(src)
						+(isShowSrc ? " : " + src : "")
						);
			}
			return;
		} 
		else if (args[1].startsWith("-f:")) 
		{
			File file = new File(args[1].substring("-f:".length()));
			FileInputStream fs = new FileInputStream(file);
			byte[] data = CIO.readStream(fs);

			if (args[0].startsWith("-md5")) {
				System.out.println(MD5.getMD5(data) + (isShowSrc ? " : " + file.getPath() : ""));
			} else {
				System.out.println(MD5.getMD5cyc(data) + (isShowSrc ? " : " + file.getPath() : ""));
			}
			return;
		} 
		else if (args[1].startsWith("-fn:"))
		{
			File file = new File(args[1].substring("-fn:".length()));
			FileInputStream fs = new FileInputStream(file);
			byte[] data = CIO.readStream(fs);
			
			String str = "";
			String enc = null;
			
			if (args.length > 2 && args[2].startsWith("-enc:")){
				enc = args[2].substring("-enc:".length());
				str = new String(data, enc);
			}else{
				str = new String(data);
			}
			
			String[] lines = str.split("\n");
			
			String outputs = "";
			
			for (int l=0; l<lines.length; l++)
			{
				String src = lines[l].trim();
				String dst = "";
				if (args[0].startsWith("-md5")) {
					dst = MD5.getMD5(src)+(isShowSrc ? " : " + src : "");
				} else {
					dst = MD5.getMD5cyc(src)+(isShowSrc ? " : " + src : "");
				}
				System.out.println(dst);
				outputs += dst + "\n";
			}
			
			String outfile = null;
			if ((enc!=null && args.length > 3 && args[3].startsWith("-fout:"))){
				outfile = args[3].substring("-fout:".length());
			}
			else if ((enc==null && args.length > 2 && args[2].startsWith("-fout:"))){
				outfile = args[2].substring("-fout:".length());
			}
			if (outfile!=null) {
				File fout = new File(outfile);
				fout.createNewFile();
				FileOutputStream fos = new FileOutputStream(fout);
				if (enc==null){
					fos.write(outputs.getBytes());
				}else{
					fos.write(outputs.getBytes(enc));
				}
				fos.flush();
				fos.close();
			}
			
			return;
		}
		else if (args[1].startsWith("-dir:"))
		{
			File dir = new File(args[1].substring("-dir:".length()));
			File[] files = dir.listFiles();
			
			File fout = null;
			if (args.length > 2 && args[2].startsWith("-fout:")){
				fout = new File(args[2].substring("-fout:".length()));
			}
			
			String outputs = "";
			
			for (int l=0; l<files.length; l++)
			{
				if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(fout)){
					continue;
				}
				
				FileInputStream fs = new FileInputStream(files[l]);
				byte[] data = CIO.readStream(fs);
				
				String dst = "";
				if (args[0].startsWith("-md5")) {
					dst = MD5.getMD5(data)+(isShowSrc ? " : " + files[l].getPath() : "");
				} else {
					dst = MD5.getMD5cyc(data)+(isShowSrc ? " : " + files[l].getPath() : "");
				}
				System.out.println(dst);
				outputs += dst + "\n";
			}
			
			if (fout!=null) {
				fout.createNewFile();
				FileOutputStream fos = new FileOutputStream(fout);
				fos.write(outputs.getBytes());
				fos.flush();
				fos.close();
			}
			
			return;
			
		}
		
		else if (args[1].startsWith("-cmp:"))
		{
			File cmp = new File(args[1].substring("-cmp:".length()));
			
			File fout = null;
			if (args.length > 2 && args[2].startsWith("-fout:")){
				fout = new File(args[2].substring("-fout:".length()));
			}
			
			String outputs = "";
			
			if (cmp.isDirectory())
			{
				File[] files = cmp.listFiles();
				
				for (int l=0; l<files.length; l++)
				{
					if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(fout)){
						continue;
					}
					
					FileInputStream fs = new FileInputStream(files[l]);
					byte[] data = CIO.readStream(fs);
					
					if (args[0].startsWith("-md5")) {
						outputs += MD5.getMD5(data);
					} else {
						outputs += MD5.getMD5cyc(data);
					}
				}
				
				if (args[0].startsWith("-md5")) {
					outputs = MD5.getMD5(outputs);
				} else {
					outputs = MD5.getMD5cyc(outputs);
				}
			}
			else 
			{
				FileInputStream fs = new FileInputStream(cmp);
				byte[] data = CIO.readStream(fs);

				if (args[0].startsWith("-md5")) {
					outputs += MD5.getMD5(data);
				} else {
					outputs += MD5.getMD5cyc(data);
				}
			}
			
			outputs += (isShowSrc ? " : " + cmp.getPath() : "");
			
			System.out.println(outputs);
			
			if (fout!=null) {
				fout.createNewFile();
				FileOutputStream fos = new FileOutputStream(fout);
				fos.write(outputs.getBytes());
				fos.flush();
				fos.close();
			}
			
			return;
			
		}
	
	}
	
}

