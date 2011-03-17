package com.cell.classloader.jcl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import javax.crypto.*;

public class CC 
{
	private Cipher e, d;

	public CC(String strKey) throws Exception 
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = gg(strKey.getBytes());
		e = Cipher.getInstance("DES");
		e.init(Cipher.ENCRYPT_MODE, key);
		d = Cipher.getInstance("DES");
		d.init(Cipher.DECRYPT_MODE, key);
	}

	Key gg(byte[] at) throws Exception {
		byte[] ab = new byte[8];
		for (int i = 0; i < at.length && i < ab.length; i++) {
			ab[i] = at[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(ab, "DES");
		return key;
	}

	public byte[] ee(byte[] ab) throws Exception {
		return e.doFinal(ab);
	}

	public byte[] dd(byte[] ab) throws Exception {
		return d.doFinal(ab);
	}

	public static String pp(byte[] test) {
		StringBuffer sb = new StringBuffer();
		for (byte b : test) {
			sb.append(b+",");
		}
		return sb.toString();
	}

	public static String b2h(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] h2b(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
//	public static void main(String[] args) {
//		try {
//			String dst = b2h(new CC("gametiler").ee("gametiler".getBytes()));
//			String src = new String(new CC("gametiler").dd(h2b(dst)));
//			System.out.println(src);
//			System.out.println(dst);
//		} catch (Exception e) {
//			System.err.println(e.getClass().getName() + " : " + e.getMessage());
//		}			
//		System.exit(1);
//	}
	
//*
	public static void main(String[] args) {
		try {
			FileInputStream src = new FileInputStream(new File(args[0]));
			byte[] src_data = new byte[src.available()];
			src.read(src_data);
			src.close();
			FileOutputStream dst = new FileOutputStream(new File(args[1]));
			byte[] dst_data = new CC(args[2]).ee(src_data);
			dst.write(dst_data);
			dst.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
		}			
		System.exit(1);
	}
//*/
	
/*
	public static void main(String[] args) 
	{
		try {
			FileInputStream fis = new FileInputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system.jar"));
			byte[] data = new byte[fis.available()];
			fis.read(data);
			fis.close();
			
			CC des = new CC("");
			
			System.out.println("read " + data.length + " bytes");
			{
				long start_time = System.currentTimeMillis();
				data = des.ee(data);
				System.out.println("enc to " + data.length + " bytes");
				long end_time = System.currentTimeMillis();
				System.out.println("use " + (end_time - start_time) + " ms");
		
				FileOutputStream fos = new FileOutputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system_enc.jar"));
				fos.write(data);
				fos.close();
			}
			{
				long start_time = System.currentTimeMillis();
				data = des.dd(data);
				System.out.println("dec to " + data.length + " bytes");
				long end_time = System.currentTimeMillis();
				System.out.println("use " + (end_time - start_time) + " ms");
				
				FileOutputStream fos = new FileOutputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system_dec.jar"));
				fos.write(data);
				fos.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}			
		System.exit(1);
	}
//*/
	
}
