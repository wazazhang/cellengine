// TD Script v0.0.0
// <OUTPUT>     		.\output\SetOutputTxt.java
// <IMAGE OUTPUT>		.\output\
// <IMAGE TYPE>			png
// <IMAGE TILE>			false
// <IMAGE GROUP>		false
//#<RESOURCE>
//#<END RESOURCE>
//#<LEVEL>
//#<END LEVEL>
//#<COMMAND>
//#<END COMMAND>

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;


public class SetOutputTxt 
{
	final public static ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
	
	final public static String TYPE_IMAGES 	= "IMAGES";
	final public static String TYPE_MAP 	= "MAP";
	final public static String TYPE_SPRITE 	= "SPRITE";
	final public static String TYPE_WORLD 	= "WORLD";

	public static ArrayList<TextRegion> findRegions(String reader)
	{
		ArrayList<TextRegion> ret = new ArrayList<TextRegion>();
		
		int length = reader.length();
		for (int p = 0; p < length; p++) {
			// 找到类型
			String type = null;
			if (reader.startsWith(TYPE_IMAGES, p)) {
				type = TYPE_IMAGES;
			}
			else if (reader.startsWith(TYPE_MAP, p)) {
				type = TYPE_MAP;
			}
			else if (reader.startsWith(TYPE_SPRITE, p)) {
				type = TYPE_SPRITE;
			}
			else if (reader.startsWith(TYPE_WORLD, p)) {
				type = TYPE_WORLD;
			}
			//
			if (type != null && (p += type.length()) < length) {
				int e_index = reader.indexOf('=', p);
				if (e_index > 0) {
					p = e_index;
					int left_index  = reader.indexOf('{', p);
					int right_index = reader.indexOf('}', p);
					if (left_index > 0 && right_index > 0 && left_index < length - 1) {
						TextRegion region = new TextRegion(
								type, 
								reader.substring(left_index+1, right_index));
						ret.add(region);
						p = right_index;
						continue;
					}
				}
			}
		}
		
		return ret;
	}

	public static void saveAll(File file, File output, String encode) throws Exception
	{
		String all_text = TS.readText(file, encode);
		ArrayList<TextRegion> regions = findRegions(all_text);
		for (TextRegion r : regions) {
			r.save(output);
		}
	}
	
	public static void usage()
	{
		System.out.println("usage: SetOutputTxt src_file output_dir [encoding]");
		System.out.println(" src_file   : input file as a SetOutputTxt.txt");
		System.out.println(" output_dir : output set files dirctory");
		System.out.println(" encoding   : (option) input file encoding, as UTF-8 or ANSI ...");
	}
	
	public static void main(String[] args)
	{
		final File		file;
		final File		output;
		final String	encode;
		
		if (args.length >= 2) {
			try{
				file	= new File(args[0].trim());
				output	= new File(args[1].trim());
				if (args.length >= 3) {
					encode	= args[2].trim();
				} else {
					encode	= "UTF-8";
				}
				saveAll(file, output, encode);
			}catch (Exception e) {
				e.printStackTrace();
				usage();
			}
		} else {
			usage();
			// TODO test 
			{
				file	= new File("D:\\Projects\\Lordol_2usa\\010_Lord_J2se_USA\\editor\\battle\\output\\SetOutputTxt.txt");
				output	= new File("D:\\Projects\\Lordol_2usa\\010_Lord_J2se_USA\\editor\\battle\\output\\set");
				encode	= "UTF-8";
				try{
					saveAll(file, output, encode);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	

//	------------------------------------------------------------------------------------------------------------------
	
	public static class TextRegion
	{
		final public String	type;
		final public String	text;
		public String name;
		
		public TextRegion(String type, String text) {
			this.type	= type.trim();
			this.text	= text.trim();
		}
		
		public void save(File out_dir) 
		{	
			try {
				if (type.startsWith(TYPE_IMAGES)) {
					saveImagesSet(out_dir);
				} else if (type.startsWith(TYPE_MAP)) {
					saveMapSet(out_dir);
				} else if (type.startsWith(TYPE_SPRITE)) {
					saveSpriteSet(out_dir);
				} else if (type.startsWith(TYPE_WORLD)) {
					saveWorldSet(out_dir);
				} else {
					System.err.println("unknow data : " + type);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		private void saveImagesSet(File out_dir) throws Exception
		{
			Reader		in	= new StringReader(text);
			ByteBuffer	bb	= ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
			try{
				this.name = TS.getNext(in);
//				----------------------------------------------------------------
//				clips
				int tile_count = TS.getInt(in);
				bb.putInt(tile_count);
				for (int i=0; i<tile_count; i++) {
					bb.putShort(TS.getShort(in));
					bb.putShort(TS.getShort(in));
					bb.putShort(TS.getShort(in));
					bb.putShort(TS.getShort(in));
				}
//				----------------------------------------------------------------
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			File 				out_file	= new File(out_dir, name + ".ts");
			FileOutputStream	fos			= new FileOutputStream(out_file);
			try{
				bb.flip();
				byte buf[] = new byte[bb.limit()];
				bb.get(buf);
				fos.write(buf);
				System.out.println("save : " + out_file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fos.close();
			}
		}
		
		private void saveMapSet(File out_dir) throws Exception
		{
			Reader		in	= new StringReader(text);
			ByteBuffer	bb	= ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
			try 
			{
				this.name = TS.getNext(in);
//				----------------------------------------------------------------
//				property
				int xcount	= TS.getInt(in);
				int ycount	= TS.getInt(in);
				int cellw	= TS.getInt(in);
				int cellh	= TS.getInt(in);
				bb.putInt(xcount);
				bb.putInt(ycount);
				bb.putInt(cellw);
				bb.putInt(cellh);
//				----------------------------------------------------------------
//				parts
				int scene_part_count = TS.getInt(in);
				bb.putInt(scene_part_count);
				for (int i=0; i<scene_part_count; i++) {
					bb.putShort(TS.getShort(in));
					bb.put(TS.getByte(in));
				}
//				----------------------------------------------------------------
//				frames
				int frame_count = TS.getInt(in);
				bb.putInt(frame_count);
				for (int i=0; i<frame_count; i++) {
					short[] data = TS.getShorts(in);
					bb.putInt(data.length);
					for (int d = 0; d < data.length; d++)
						bb.putShort(data[d]);
					TS.getNext(in);
				}
//				----------------------------------------------------------------
//				tile matrix
				for(int y=0; y<ycount; y++){
					for(int x=0; x<xcount; x++){
						bb.putShort(TS.getShort(in));
					}
					TS.getNext(in);
				}
				TS.getNext(in);
//				--------------------------------------------------------------------------------------------------------------
//				cds
				int cd_count = TS.getInt(in);
				bb.putInt(cd_count);
				for (int i=0; i<cd_count; i++) {
					String type = TS.getNext(in);
					if (type.equals("rect")) {
						bb.put((byte) 0);
					} else if (type.equals("line")) {
						bb.put((byte) 1);
					} else {
						bb.put((byte) 0);
					}
				    bb.putInt(TS.getInt(in));
				    bb.putShort(TS.getShort(in));
				    bb.putShort(TS.getShort(in));
				    bb.putShort(TS.getShort(in));
				    bb.putShort(TS.getShort(in));
				    bb.putShort(TS.getShort(in));
				    bb.putShort(TS.getShort(in));
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd matrix
				for (int y = 0; y < ycount; y++) {
					for (int x = 0; x < xcount; x++) {
						bb.putShort(TS.getShort(in));
					}
					TS.getNext(in);
				}
				TS.getNext(in);
//				--------------------------------------------------------------------------------------------------------------
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			File 				out_file	= new File(out_dir, name + ".ms");
			FileOutputStream	fos			= new FileOutputStream(out_file);
			try{
				bb.flip();
				byte buf[] = new byte[bb.limit()];
				bb.get(buf);
				fos.write(buf);
				System.out.println("save : " + out_file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fos.close();
			}
		}
		
		private void saveSpriteSet(File out_dir) throws Exception
		{
			Reader		in	= new StringReader(text);
			ByteBuffer	bb	= ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
			try 
			{
				this.name = TS.getNext(in);
				String tile_name = TS.getNext(in);
//				----------------------------------------------------------------
//				scene parts
				int scene_part_count = TS.getInt(in);
				bb.putInt(scene_part_count);
				for (int i=0; i<scene_part_count; i++) {
					bb.putShort(TS.getShort(in));
					bb.putShort(TS.getShort(in));
					bb.putShort(TS.getShort(in));
					bb.put(TS.getByte(in));
				}
//				----------------------------------------------------------------
//				scene frames
				int scene_frame_count = TS.getInt(in);
				bb.putInt(scene_frame_count);
				for (int i=0; i<scene_frame_count; i++) {
					short[] data = TS.getShorts(in);
					bb.putInt(data.length);
					for (int d = 0; d < data.length; d++)
						bb.putShort(data[d]);
					TS.getNext(in);
				}
//				----------------------------------------------------------------
//				cd parts
				int cd_part_count = TS.getInt(in);
				bb.putInt(cd_part_count);
				for (int i=0; i<cd_part_count; i++) {
					 bb.putInt(TS.getInt(in));
					 bb.putShort(TS.getShort(in));
					 bb.putShort(TS.getShort(in));
					 bb.putShort(TS.getShort(in));
					 bb.putShort(TS.getShort(in));
				}
//				----------------------------------------------------------------
//				cd frames
				int cd_frame_count = TS.getInt(in);
				bb.putInt(cd_frame_count);
				for (int i=0; i<cd_frame_count; i++) {
					short[] data = TS.getShorts(in);
					bb.putInt(data.length);
					for (int d = 0; d < data.length; d++)
						bb.putShort(data[d]);
					TS.getNext(in);
				}
//				----------------------------------------------------------------
//				animates
				int[] frameCounts = TS.getInts(in);TS.getNext(in);
				bb.putInt(frameCounts.length);
				short[][] frameAnimate	= TS.getSprFrames(frameCounts, in); TS.getNext(in);
				short[][] frameCDMap	= TS.getSprFrames(frameCounts, in); TS.getNext(in);
				short[][] frameCDAtk	= TS.getSprFrames(frameCounts, in); TS.getNext(in);
				short[][] frameCDDef	= TS.getSprFrames(frameCounts, in); TS.getNext(in);
				short[][] frameCDExt	= TS.getSprFrames(frameCounts, in); TS.getNext(in);
				for(int i=0;i<frameCounts.length;i++){
					bb.putInt((int)frameCounts[i]);
					for(int f=0;f<frameCounts[i];f++){
						bb.putShort((short)frameAnimate[i][f]);
						bb.putShort((short)frameCDMap[i][f]);
						bb.putShort((short)frameCDAtk[i][f]);
						bb.putShort((short)frameCDDef[i][f]);
						bb.putShort((short)frameCDExt[i][f]);
					}
				}
//				----------------------------------------------------------------
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			File 				out_file	= new File(out_dir, name + ".ss");
			FileOutputStream	fos			= new FileOutputStream(out_file);
			try{
				bb.flip();
				byte buf[] = new byte[bb.limit()];
				bb.get(buf);
				fos.write(buf);
				System.out.println("save : " + out_file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fos.close();
			}
		}
		

		private void saveWorldSet(File out_dir) throws Exception
		{
			Reader		in		= new StringReader(text);
			ByteBuffer	bb_wps	= ByteBuffer.allocate(1024*1024);
			ByteBuffer	bb_wrs	= ByteBuffer.allocate(1024*1024);
			bb_wps.order(byteOrder);
			bb_wrs.order(byteOrder);
			try{
				this.name = TS.getNext(in);
//				----------------------------------------------------------------
//				waypoints
				int waypoint_count = TS.getInt(in);
				bb_wps.putInt(waypoint_count);
				for (int i=0; i<waypoint_count; i++) {
					bb_wps.putInt(TS.getInt(in));
					bb_wps.putInt(TS.getInt(in));
				}
//				----------------------------------------------------------------
//				regions
				int region_count = TS.getInt(in);
				bb_wrs.putInt(region_count);
				for (int i=0; i<region_count; i++) {
					bb_wrs.putShort(TS.getShort(in));
					bb_wrs.putShort(TS.getShort(in));
					bb_wrs.putShort(TS.getShort(in));
					bb_wrs.putShort(TS.getShort(in));
				}
//				----------------------------------------------------------------
//				links
				while (true) {
					short begin	= TS.getShort(in);
					if (begin < 0) {
						break;
					}
					short end	= TS.getShort(in);
					bb_wps.putShort(begin);
					bb_wps.putShort(end);
				}
				bb_wps.putShort((short)-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			{
				ByteBuffer			bb			= bb_wps;
				File 				out_file	= new File(out_dir, name + ".wps");
				FileOutputStream	fos			= new FileOutputStream(out_file);
				try{
					bb.flip();
					byte buf[] = new byte[bb.limit()];
					bb.get(buf);
					fos.write(buf);
					System.out.println("save : " + out_file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.close();
				}
			}
			
			{
				ByteBuffer			bb			= bb_wrs;
				File 				out_file	= new File(out_dir, name + ".wrs");
				FileOutputStream	fos			= new FileOutputStream(out_file);
				try{
					bb.flip();
					byte buf[] = new byte[bb.limit()];
					bb.get(buf);
					fos.write(buf);
					System.out.println("save : " + out_file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.close();
				}
			}
		}
		
		
	}

	static class TS
	{
		private static String readText(java.io.File file, String encoding) throws Exception
		{
			String ret = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] data = new byte[fis.available()];
				fis.read(data);
				ret = new String(data, encoding);
			} finally {
				try {
					fis.close();
				} catch (Exception e2) {}
			}
			return ret;
		}

		private static String getNext(Reader in) throws IOException {
			String ret = "";
			while (true) {
				int r = in.read();
				if (r == -1)
					break;
				if (r == ',')
					break;
				ret = (ret + ((char) r)).trim();
			}
			return ret;
		}

		private static byte getByte(Reader in) throws IOException {
			return Byte.parseByte(getNext(in));
		}

		private static short getShort(Reader in) throws IOException {
			return Short.parseShort(getNext(in));
		}

		private static int getInt(Reader in) throws IOException {
			return Integer.parseInt(getNext(in));
		}

		private static long getUnsignedInt(Reader in) throws IOException {
			return Long.parseLong(getNext(in));
		}

		private static short[] getShorts(Reader in) throws IOException {
			int count = (int) getUnsignedInt(in);
			return getShorts(count, in);
		}

		private static int[] getInts(Reader in) throws IOException {
			int count = (int) getUnsignedInt(in);
			return getInts(count, in);
		}

		private static short[] getShorts(int count, Reader in)
				throws IOException {
			short[] values = new short[count];
			for (int i = 0; i < count; ++i)
				values[i] = getShort(in);
			return values;
		}

		private static int[] getInts(int count, Reader in) throws IOException {
			int[] values = new int[count];
			for (int i = 0; i < count; ++i)
				values[i] = getInt(in);
			return values;
		}
		
		private static short[][] getSprFrames(int[] frameCounts, Reader in) throws IOException {
			short[][] values = new short[frameCounts.length][];
			for (int i = 0; i < frameCounts.length; i++) {
				values[i] = getShorts(frameCounts[i], in);
				getNext(in);
			}
			return values;
		}
	}

}



