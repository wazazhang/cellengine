package com.cell.util.pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.util.Pair;
import com.cell.util.zip.ZipUtil;

public class Packer 
{

	
	
	
	
	
//	
//	
//	public static byte[] pakFiles(File dir)
//	{
//		try {
//
//
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//	
//	
	public static void main(String[] args)
	{
		try 
		{
			File	src_dir		= new File(args[0]);
			File	dst_file	= new File(src_dir, src_dir.getName() + ".zip");
			Pattern	macher		= java.util.regex.Pattern.compile(args[1]);
		
			LinkedHashMap<String, Pair<Integer, Integer>> log = new LinkedHashMap<String, Pair<Integer,Integer>>();
			
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(baos);
				zos.setComment("");
				zos.setLevel(9);
				zos.setMethod(ZipOutputStream.DEFLATED);
				for (File file : src_dir.listFiles())
				{
					if (macher.matcher(file.getName()).find()) {
						byte[] data = CFile.readData(file);
						ZipEntry e = new ZipEntry(file.getName());
						e.setTime(0);
						zos.putNextEntry(e);
						zos.write(data);
						log.put(file.getName(), new Pair<Integer, Integer>(data.length, 0));
					}
				}
				zos.finish();
				zos.close();
				CFile.writeData(dst_file, baos.toByteArray());
			
				System.out.println("save to : " + dst_file);
				
				
			} catch (Exception err) {
				err.printStackTrace();
			}
		
			try {
				byte[] fdata = CFile.readData(dst_file);
				
//				fdata = Arrays.copyOfRange(fdata, 0, fdata.length/2);

				ByteArrayInputStream bais = new ByteArrayInputStream(fdata);
				
				ZipInputStream zis = new ZipInputStream(bais);
				
				for (ZipEntry e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) 
				{
					Pair<Integer, Integer> p = log.get(e.getName());
					p.setValue(ZipUtil.readBytes(zis).length);
					System.out.println(
							"check | " + 
							CUtil.snapStringRightSize(e.getName(), 32, ' ') + " | " + 
							CUtil.snapStringRightSize(p.getKey(),  16, ' ') + " | " + 
							p.getValue());
				}
				zis.close();
			} catch (Exception err) {
				err.printStackTrace();
			}

		} catch (Exception err) {
			System.out.println("useage: Packer <src dir> <regx filter>");
			err.printStackTrace();
		}
	}
	
}
