// TD Script v0.0.0
// <OUTPUT>     		.\output\SetOutput.java
// <IMAGE OUTPUT>		.\output\
// <IMAGE TYPE>			png
// <IMAGE TILE>			false
// <IMAGE GROUP>		false

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Hashtable;
import java.util.Vector;



public class SetOutput {

	static public String Path = "set";

	static public ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
	
#<RESOURCE>

	public static void putTileClip(ByteBuffer bb, short x, short y, short w, short h)
	{
			bb.putShort(x);	// <-- s16 * count
			bb.putShort(y);	// <-- s16 * count
			bb.putShort(w);	// <-- s16 * count
			bb.putShort(h);	// <-- s16 * count
	}

//	########################################################################################################################
	
	#<IMAGES> 
	// Images : <NAME> 
	public static void saveTileSet_<NAME>()
	{
		FileOutputStream fos = null;
		ByteBuffer bb = null;
		
		try {
			fos = new FileOutputStream(Path+"/<NAME>.ts");
			bb = ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
			
		{
			//-->
//			--------------------------------------------------------------------------------------------------------------
//			clips 
			bb.putInt((int)<COUNT>);	// <-- u16
			#<CLIP> 
			putTileClip(bb,(short)<X>,(short)<Y>,(short)<W>,(short)<H>);
			#<END CLIP>
			//<--
		}
			
		try 
		{
			bb.flip();
			byte buf[] = new byte[bb.limit()];
			bb.get(buf);
			bb.clear();
			fos.write(buf);
			fos.close();
			
			System.out.println("Save TileSet : <NAME>");
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		
		bb = null;
		fos = null;
		
		
	}
	#<END IMAGES>

	
//	########################################################################################################################
	
	#<MAP>
	// Map : <NAME> //<IMAGES NAME>
	public static void saveMapSet_<NAME>()
	{
		FileOutputStream fos = null;
		ByteBuffer bb = null;
		
		try {
			fos = new FileOutputStream(Path+"/<NAME>.ms");
			bb = ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
			
		{
			//-->
//			--------------------------------------------------------------------------------------------------------------
//			property
			short[] data ;
			byte type = 0;
			bb.putInt((int)<X COUNT>);			// <-- u16
			bb.putInt((int)<Y COUNT>);			// <-- u16
			bb.putInt((int)<CELL W>);			// <-- u16
			bb.putInt((int)<CELL H>);			// <-- u16
//			--------------------------------------------------------------------------------------------------------------
//			parts
			bb.putInt((int)<SCENE PART COUNT>);		// <-- u16
			#<SCENE PART> 
			bb.putShort((short)<TILE>);			// <-- s16 * count
			bb.put((byte)<TRANS>);				// <-- s8  * count
			#<END SCENE PART>
//			--------------------------------------------------------------------------------------------------------------
//			frames
			bb.putInt((int)<SCENE FRAME COUNT>);		// <-- u16
			#<SCENE FRAME>
			data = new short[]{<DATA>};
			bb.putInt((int)data.length);				// <-- u16 * count
			for(int i=0;i<data.length;i++)
				bb.putShort((short)data[i]);			// <-- s16 * length * count
			#<END SCENE FRAME>
//			--------------------------------------------------------------------------------------------------------------
//			tile matrix
			short[][] tileMatrix = new short[][]{
					<TILE MATRIX>
			};
			for(int y=0; y<<Y COUNT>; y++){
				for(int x=0; x<<X COUNT>; x++){
					bb.putShort((short)tileMatrix[y][x]);// <-- s16 * xcount * ycount
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			cds
			bb.putInt((int)<CD PART COUNT>);		// <-- u16
		    #<CD PART>
		    if("<TYPE>"=="rect") type = 0;
		    if("<TYPE>"=="line") type = 1;
		    bb.put((byte)type);				// <-- s8  * count
		    bb.putInt((int)<MASK>);			// <-- s32 * count
		    bb.putShort((short)<X1>);				// <-- s16 * count
		    bb.putShort((short)<Y1>);				// <-- s16 * count
		    bb.putShort((short)<X2>);				// <-- s16 * count
		    bb.putShort((short)<Y2>);				// <-- s16 * count
		    bb.putShort((short)<W>);				// <-- s16 * count
		    bb.putShort((short)<H>);				// <-- s16 * count
			#<END CD PART>
//			--------------------------------------------------------------------------------------------------------------
//			cd matrix
			short[][] flagMatrix = new short[][]{
					<FLAG MATRIX>
			};
			for(int y=0; y<<Y COUNT>; y++){
				for(int x=0; x<<X COUNT>; x++){
					bb.putShort((short)flagMatrix[y][x]);// <-- s16 * xcount * ycount
				}
			}
			//<--
		}
		
		try {
			bb.flip();
			byte buf[] = new byte[bb.limit()];
			bb.get(buf);
			bb.clear();
			fos.write(buf);
			fos.close();

			System.out.println("Save MapSet : <NAME>");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		bb = null;
		fos = null;
		
	    
	}
	#<END MAP>
	
//	########################################################################################################################
	
	public static void putSprPart(ByteBuffer bb, short x, short y, short tile, byte trans)
	{
		bb.putShort(x);				// <-- s16 * count
		bb.putShort(y);				// <-- s16 * count
		bb.putShort(tile);				// <-- s16 * count
		bb.put(trans);				// <-- s8  * count
	}
	
	public static void putSprCD(ByteBuffer bb, int mask, short x, short y, short w, short h)
	{
		 bb.putInt(mask);					// <-- s32 * count
		 bb.putShort(x);				// <-- s16 * count
		 bb.putShort(y);				// <-- s16 * count
		 bb.putShort(w);				// <-- s16 * count
		 bb.putShort(h);				// <-- s16 * count
	}
	
	public static void putSprFrame(ByteBuffer bb, short[] data)
	{
		bb.putInt((int)data.length);				// <-- u16 * count
		for(int i=0;i<data.length;i++)
			bb.putShort((short)data[i]);			// <-- s16 * length * count
	}
	
	public static void putSprAnimate(ByteBuffer bb, 
			short[][] frameAnimate,
			short[][] frameCDMap,
			short[][] frameCDAtk,
			short[][] frameCDDef,
			short[][] frameCDExt,
			short[] frameCounts
			)
	{
			bb.putInt((int)frameCounts.length);					// <-- u16
			
			for(int i=0;i<frameCounts.length;i++){
				bb.putInt((int)frameCounts[i]);				// <-- u16 * animateCount
				for(int f=0;f<frameCounts[i];f++){
					bb.putShort((short)frameAnimate[i][f]);		// <-- s16 * animateCount * frameCount
					bb.putShort((short)frameCDMap[i][f]);		// <-- s16 * animateCount * frameCount
					bb.putShort((short)frameCDAtk[i][f]);		// <-- s16 * animateCount * frameCount
					bb.putShort((short)frameCDDef[i][f]);		// <-- s16 * animateCount * frameCount
					bb.putShort((short)frameCDExt[i][f]);		// <-- s16 * animateCount * frameCount
				}
			}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	#<SPRITE>
	// Sprite : <NAME> //<IMAGES NAME>
	public static void saveSprSet_<NAME>()
	{
		FileOutputStream fos = null;
		ByteBuffer bb = null;
		
		try
		{
			fos = new FileOutputStream(Path+"/<NAME>.ss");
			bb = ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		{
			//-->
//			--------------------------------------------------------------------------------------------------------------
//			property
			short[] data;
//			--------------------------------------------------------------------------------------------------------------
//			scene parts
			bb.putInt((int)<SCENE PART COUNT>);	// <-- u16
			class Parts_<NAME>
			{
				public void put(ByteBuffer buff)
				{
					#<SCENE PART> 
					putSprPart(buff, (short)<X>, (short)<Y>, (short)<TILE>, (byte)<TRANS>);
					#<END SCENE PART>
				}
			}
			(new Parts_<NAME>()).put(bb);
			
//			--------------------------------------------------------------------------------------------------------------
//			scene frames
			bb.putInt((int)<SCENE FRAME COUNT>);		// <-- u16
			#<SCENE FRAME>
			putSprFrame(bb, new short[]{<DATA>});
			#<END SCENE FRAME>
			
//			--------------------------------------------------------------------------------------------------------------
//			cd parts
			bb.putInt((int)<CD PART COUNT>);		// <-- u16
		    #<CD PART>
		    putSprCD(bb, (int)<MASK>, (short)<X1>, (short)<Y1>, (short)<W>, (short)<H>);
			#<END CD PART>
			
//			--------------------------------------------------------------------------------------------------------------
//			cd frames
			bb.putInt((int)<CD FRAME COUNT>);			// <-- u16
			#<CD FRAME>
			putSprFrame(bb, new short[]{<DATA>});
			#<END CD FRAME>
			
//			--------------------------------------------------------------------------------------------------------------
//			animates
			putSprAnimate(bb, 
					new short[][]{<FRAME ANIMATE>},
					new short[][]{<FRAME CD MAP>},
					new short[][]{<FRAME CD ATK>},
					new short[][]{<FRAME CD DEF>},
					new short[][]{<FRAME CD EXT>},
					new short[]{<FRAME COUNTS>}
					);
			//<--
		}
		
		try
		{
			bb.flip();
			byte buf[] = new byte[bb.limit()];
			bb.get(buf);
			bb.clear();
			fos.write(buf);
			fos.close();

			System.out.println("Save SprSet : <NAME>");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		bb = null;
		fos = null;
		
	    
	}
	#<END SPRITE>
	
//	########################################################################################################################

	public static void saveResource(){
		
		#<IMAGES>
		try{
			saveTileSet_<NAME>(); 
		}catch(Exception err){err.printStackTrace();}
		#<END IMAGES>
		
		#<MAP>
		try{
			saveMapSet_<NAME>();
		}catch(Exception err){err.printStackTrace();}
		#<END MAP>
		
		#<SPRITE>
		try{
			saveSprSet_<NAME>();
		}catch(Exception err){err.printStackTrace();}
		#<END SPRITE>
	}
	
#<END RESOURCE>

//################################################################################################################################

#<LEVEL>

	#<WORLD>
	public static void saveWorldWayPointSet_<NAME>()
	{
		FileOutputStream fos = null;
		ByteBuffer bb = null;
		
		try 
		{
			fos = new FileOutputStream(Path+"/<NAME>.wps");
			bb = ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		{
			//-->
//			--------------------------------------------------------------------------------------------------------------
//			waypoints
			bb.putInt((int)<WAYPOINT COUNT>);	// <-- u16
			#<WAYPOINT> 
			bb.putInt((int)<X>);				// <-- s32 * count
			bb.putInt((int)<Y>);				// <-- s32 * count
			#<END WAYPOINT>
//			--------------------------------------------------------------------------------------------------------------
//			links
			#<WAYPOINT LINK> 
			bb.putShort((short)<START>);			// <-- s16 * count
			bb.putShort((short)<END>);				// <-- s16 * count
			#<END WAYPOINT LINK>
			bb.putShort((short)-1);					// <-- s16 end flag 
			//<--
		}
		
		try {
			bb.flip();
			byte buf[] = new byte[bb.limit()];
			bb.get(buf);
			bb.clear();
			fos.write(buf);
			fos.close();

			System.out.println("Save WaypointSet : <NAME>");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		bb = null;
		fos = null;
		
	}
	#<END WORLD>
	
	

	#<WORLD>
	public static void saveWorldRegionSet_<NAME>()
	{
		FileOutputStream fos = null;
		ByteBuffer bb = null;
		
		try {
			fos = new FileOutputStream(Path+"/<NAME>.wrs");
			bb = ByteBuffer.allocate(1024*1024);
			bb.order(byteOrder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
			
		{
			//-->
	//		--------------------------------------------------------------------------------------------------------------
	//		regions
			bb.putInt((int)<REGION COUNT>);	// <-- u16
	        #<REGION>
	        bb.putShort((short)<X>);	// <-- s16
	        bb.putShort((short)<Y>);	// <-- s16
	        bb.putShort((short)<W>);	// <-- s16
	        bb.putShort((short)<H>);	// <-- s16
	        #<END REGION>
			//<--
		}
		
		try {
			bb.flip();
			byte buf[] = new byte[bb.limit()];
			bb.get(buf);
			bb.clear();
			fos.write(buf);
			fos.close();
	
			System.out.println("Save RegionSet : <NAME>");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
		bb = null;
		fos = null;
		
	}
	#<END WORLD>

	
	public static void saveWorld(){
		#<WORLD>
		
		try{
			saveWorldWayPointSet_<NAME>();
		}catch(Exception err){err.printStackTrace();}
		
		try{
			saveWorldRegionSet_<NAME>();
		}catch(Exception err){err.printStackTrace();}
		
		#<END WORLD>
	}
	
#<END LEVEL>


#<COMMAND>
#<END COMMAND>


	public static void saveAll(){
		saveResource();
		saveWorld();
	}

	public static void main(String[] args) {
		saveAll();
		
	}


}



