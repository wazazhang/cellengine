package com.cell.gameedit.output;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.gameedit.OutputLoader;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CAnimates;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CCollides;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.TextDeserialize;
import com.cell.util.PropertyGroup;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
abstract public class OutputProperties extends BaseOutput
{
	final protected void init(PropertyGroup Config) throws Exception
	{
		// 解吸所有对象
		int ImagesCount 	= Config.getInteger("ImagesCount", 0);
		int SpriteCount 	= Config.getInteger("SpriteCount", 0);
		int MapCount 		= Config.getInteger("MapCount", 0);
		int WorldCount 		= Config.getInteger("WorldCount", 0);
		int TableGroupCount	= Config.getInteger("TableGroupCount", 0);

		for (int i=0; i<ImagesCount; i++){
			ImagesSet img = createImageSet(
					Config.getString("Images_" + i), 
					Config.getString("Images_" + i + "_tiles"));
			ImgTable.put(img.Name, img);
		}

		for (int i = 0; i < SpriteCount; i++) {
			SpriteSet spr = createSpriteSet(
					Config.getString("Sprite_" + i),
					Config.getString("Sprite_" + i + "_parts"),
					Config.getString("Sprite_" + i + "_frames"),
					Config.getString("Sprite_" + i + "_cds"),
					Config.getString("Sprite_" + i + "_cd_frames"),
					Config.getString("Sprite_" + i + "_frame_counts"),
					Config.getString("Sprite_" + i + "_frame_name"),
					Config.getString("Sprite_" + i + "_frame_animate"),
					Config.getString("Sprite_" + i + "_frame_cd_map"),
					Config.getString("Sprite_" + i + "_frame_cd_atk"),
					Config.getString("Sprite_" + i + "_frame_cd_def"),
					Config.getString("Sprite_" + i + "_frame_cd_ext")
			);
			SprTable.put(spr.Name, spr);
		}
		
		
		for (int i = 0; i < MapCount; i++) {
			MapSet map = createMapSet(
					Config.getString("Map_" + i),
					Config.getString("Map_" + i + "_parts"),
					Config.getString("Map_" + i + "_frames"),
					Config.getString("Map_" + i + "_cds"),
					Config.getString("Map_" + i + "_tile_matrix"),
					Config.getString("Map_" + i + "_cd_matrix")
			);
			MapTable.put(map.Name, map);
		}
		
		for (int i = 0; i < WorldCount; i++) {
			WorldSet world = createWorldSet(
					Config.getString("World_" + i),
					Config.getString("World_" + i + "_maps"),
					Config.getString("World_" + i + "_sprs"),
					Config.getString("World_" + i + "_waypoints"),
					Config.getString("World_" + i + "_waypoint_link"),
					Config.getString("World_" + i + "_regions"),
					Config.getString("World_" + i + "_data"),
					Config.getString("World_" + i + "_terrain")
			);
			WorldTable.put(world.Name, world);
		}
		
		for (int i = 0; i < TableGroupCount; i++) {
			TableSet tg = createTableSet(
					Config.getString("TG_" + i), Config);
			TableGroups.put(tg.Name, tg);
		}
	
	}
	
//	------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
//	Images_<IMAGES INDEX>		=<IMAGES INDEX>,<NAME>,<COUNT>
//	Images_<IMAGES INDEX>_tiles	=#<CLIP>{<INDEX>,<X>,<Y>,<W>,<H>,<DATA>,},#<END CLIP>
	final ImagesSet createImageSet(String images, String tiles) 
	{
		String[] args0 = CUtil.splitString(images, ",");
		
		ImagesSet set = new ImagesSet(Integer.parseInt(args0[0]), args0[1]);
		
		set.Count = Integer.parseInt(args0[2]);

		String[] clips = getArray2D(tiles);
		set.ClipsX = new int[set.Count];
		set.ClipsY = new int[set.Count];
		set.ClipsW = new int[set.Count];
		set.ClipsH = new int[set.Count];
		set.ClipsKey = new String[set.Count];
		for (int i = 0; i < set.Count; i++) {
			String[] clip = CUtil.splitString(clips[i], ",");
			set.ClipsX[i] = Integer.parseInt(clip[1]);
			set.ClipsY[i] = Integer.parseInt(clip[2]);
			set.ClipsW[i] = Integer.parseInt(clip[3]);
			set.ClipsH[i] = Integer.parseInt(clip[4]);
			set.ClipsKey[i] = clip[5];
		}
		return set;
	}

//	-------------------------------------------------------------------------------------
//	Sprite_<SPR INDEX>					=<SPR INDEX>,<NAME>,<IMAGES NAME>,<SCENE PART COUNT>,<SCENE FRAME COUNT>,<CD PART COUNT>,<CD FRAME COUNT>,<ANIMATE COUNT>
//	Sprite_<SPR INDEX>_parts			=#<SCENE PART>{<INDEX>,<TILE>,<X>,<Y>,<TRANS>},#<END SCENE PART>
//	Sprite_<SPR INDEX>_frames			=#<SCENE FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END SCENE FRAME>
//	Sprite_<SPR INDEX>_cds				=#<CD PART>{<INDEX>,<TYPE>,<MASK>,<X1>,<Y1>,<X2>,<Y2>,<W>,<H>},#<END CD PART>
//	Sprite_<SPR INDEX>_cd_frames		=#<CD FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END CD FRAME>
//	Sprite_<SPR INDEX>_frame_counts		=<FRAME COUNTS>
//	Sprite_<SPR INDEX>_frame_name		=<FRAME NAME>
//	Sprite_<SPR INDEX>_frame_animate	=<FRAME ANIMATE>
//	Sprite_<SPR INDEX>_frame_cd_map		=<FRAME CD MAP>
//	Sprite_<SPR INDEX>_frame_cd_atk		=<FRAME CD ATK>
//	Sprite_<SPR INDEX>_frame_cd_def		=<FRAME CD DEF>
//	Sprite_<SPR INDEX>_frame_cd_ext		=<FRAME CD EXT>
	final SpriteSet createSpriteSet(String spr, 
			String _parts, 
			String _frames,
			String _cds, 
			String _cd_frames, 
			String _frame_counts,
			String _frame_name, 
			String _frame_animate, 
			String _frame_cd_map,
			String _frame_cd_atk, 
			String _frame_cd_def,
			String _frame_cd_ext) throws IOException 
	{
		String[] args = CUtil.splitString(spr, ",");

		SpriteSet set = new SpriteSet(Integer.parseInt(args[0]), args[1]);
		
		set.ImagesName = args[2];

		int scenePartCount = Integer.parseInt(args[3]);
		int sceneFrameCount = Integer.parseInt(args[4]);
		int cdCount = Integer.parseInt(args[5]);
		int collidesCount = Integer.parseInt(args[6]);
		int animateCount = Integer.parseInt(args[7]);

		// --------------------------------------------------------------------------------------------------------------
		// scene parts
		String parts[] = getArray2D(_parts);
		set.PartX = new short[scenePartCount];
		set.PartY = new short[scenePartCount];
		set.PartTileID = new short[scenePartCount];
		set.PartTileTrans = new byte[scenePartCount];
		for (int i = 0; i < scenePartCount; i++) {
			String[] tile = CUtil.splitString(parts[i], ",");
			set.PartTileID[i] = Short.parseShort(tile[1]);
			set.PartX[i] = Short.parseShort(tile[2]);
			set.PartY[i] = Short.parseShort(tile[3]);
			set.PartTileTrans[i] = Byte.parseByte(tile[4]);
		}
		// --------------------------------------------------------------------------------------------------------------
		// scene frames
		String frames[] = getArray2D(_frames);
		set.Parts = new short[sceneFrameCount][];
		for (int i = 0; i < sceneFrameCount; i++) {
			String[] frame = frames[i].split(",", 3);
			int frameCount = Integer.parseInt(frame[1]);
			set.Parts[i] = new short[frameCount];
			if (frameCount > 0) {
				String[] data = CUtil.splitString(frame[2], ",");
				for (int f = 0; f < frameCount; f++) {
					set.Parts[i][f] = Short.parseShort(data[f]);
				}
			}
		}
		// --------------------------------------------------------------------------------------------------------------
		// cd parts
		String cds[] = getArray2D(_cds);
		set.BlocksMask = new int[cdCount];
		set.BlocksX1 = new short[cdCount];
		set.BlocksY1 = new short[cdCount];
		set.BlocksW = new short[cdCount];
		set.BlocksH = new short[cdCount];
		for (int i = 0; i < cdCount; i++) {
			String[] cd = CUtil.splitString(cds[i], ",");
			set.BlocksMask[i] = Integer.parseInt(cd[2]);
			set.BlocksX1[i] = Short.parseShort(cd[3]);
			set.BlocksY1[i] = Short.parseShort(cd[4]);
			set.BlocksW[i] = Short.parseShort(cd[7]);
			set.BlocksH[i] = Short.parseShort(cd[8]);
		}
		// --------------------------------------------------------------------------------------------------------------
		// cd frames
		String cd_frames[] = getArray2D(_cd_frames);
		set.Blocks = new short[collidesCount][];
		for (int i = 0; i < collidesCount; i++) {
			String[] frame = cd_frames[i].split(",", 3);
			int frameCount = Integer.parseInt(frame[1]);
			set.Blocks[i] = new short[frameCount];
			if (frameCount > 0) {
				String[] data = CUtil.splitString(frame[2], ",");
				for (int f = 0; f < frameCount; f++) {
					set.Blocks[i][f] = Short.parseShort(data[f]);
				}
			}
		}
		// --------------------------------------------------------------------------------------------------------------
		// animates
		set.AnimateCount = animateCount;
		set.AnimateNames = new String[animateCount];
		StringReader AnimateNamesReader = new StringReader(_frame_name);
		for (int i = 0; i < animateCount; i++) {
			set.AnimateNames[i] = TextDeserialize
					.getBytesString(AnimateNamesReader);
		}

		String frame_counts[] = CUtil.splitString(_frame_counts, ",");
		String frame_animate[] = getArray2D(_frame_animate);
		String frame_cd_map[] = getArray2D(_frame_cd_map);
		String frame_cd_atk[] = getArray2D(_frame_cd_atk);
		String frame_cd_def[] = getArray2D(_frame_cd_def);
		String frame_cd_ext[] = getArray2D(_frame_cd_ext);
		set.FrameAnimate = new short[animateCount][];
		set.FrameCDMap = new short[animateCount][];
		set.FrameCDAtk = new short[animateCount][];
		set.FrameCDDef = new short[animateCount][];
		set.FrameCDExt = new short[animateCount][];
		for (int i = 0; i < animateCount; i++) {
			int frameCount = Integer.parseInt(frame_counts[i]);
			String[] animate = CUtil.splitString(frame_animate[i], ",");
			String[] cd_map = CUtil.splitString(frame_cd_map[i], ",");
			String[] cd_atk = CUtil.splitString(frame_cd_atk[i], ",");
			String[] cd_def = CUtil.splitString(frame_cd_def[i], ",");
			String[] cd_ext = CUtil.splitString(frame_cd_ext[i], ",");
			set.FrameAnimate[i] = new short[frameCount];
			set.FrameCDMap[i] = new short[frameCount];
			set.FrameCDAtk[i] = new short[frameCount];
			set.FrameCDDef[i] = new short[frameCount];
			set.FrameCDExt[i] = new short[frameCount];
			for (int f = 0; f < frameCount; f++) {
				set.FrameAnimate[i][f] = Short.parseShort(animate[f]);
				set.FrameCDMap[i][f] = Short.parseShort(cd_map[f]);
				set.FrameCDAtk[i][f] = Short.parseShort(cd_atk[f]);
				set.FrameCDDef[i][f] = Short.parseShort(cd_def[f]);
				set.FrameCDExt[i][f] = Short.parseShort(cd_ext[f]);
			}
		}
		return set;
	}

//	-------------------------------------------------------------------------------------
//	Map_<MAP INDEX>             =<MAP INDEX>,<NAME>,<IMAGES NAME>,<X COUNT>,<Y COUNT>,<CELL W>,<CELL H>,<SCENE PART COUNT>,<SCENE FRAME COUNT>,<CD PART COUNT>
//	Map_<MAP INDEX>_parts		=#<SCENE PART>{<INDEX>,<TILE>,<TRANS>},#<END SCENE PART>
//	Map_<MAP INDEX>_frames		=#<SCENE FRAME>{<INDEX>,<DATA SIZE>,<DATA>},#<END SCENE FRAME>
//	Map_<MAP INDEX>_cds			=#<CD PART>{<INDEX>,<TYPE>,<MASK>,<X1>,<Y1>,<X2>,<Y2>,<W>,<H>},#<END CD PART>
//	Map_<MAP INDEX>_tile_matrix	=<TILE MATRIX>
//	Map_<MAP INDEX>_cd_matrix	=<FLAG MATRIX>
	final MapSet createMapSet(
			String map, 
			String _parts,
			String _frames,
			String _cds, 
			String _tile_matrix,
			String _cd_matrix)
	{
		String[] args = CUtil.splitString(map, ",");

		MapSet set = new MapSet(Integer.parseInt(args[0]), args[1]);
		
		set.ImagesName = args[2];

		set.XCount = Integer.parseInt(args[3]);
		set.YCount = Integer.parseInt(args[4]);
		set.CellW = Integer.parseInt(args[5]);
		set.CellH = Integer.parseInt(args[6]);

		int scenePartCount = Integer.parseInt(args[7]);
		int animateCount = Integer.parseInt(args[8]);
		int cdCount = Integer.parseInt(args[9]);

		// --------------------------------------------------------------------------------------------------------------
		// parts
		String parts[] = getArray2D(_parts);
		set.TileID = new int[scenePartCount];
		set.TileTrans = new int[scenePartCount];
		for (int i = 0; i < scenePartCount; i++) {
			String[] tile = CUtil.splitString(parts[i], ",");
			set.TileID[i] = Integer.parseInt(tile[1]);
			set.TileTrans[i] = Integer.parseInt(tile[2]);
		}
		// --------------------------------------------------------------------------------------------------------------
		// frames
		String frames[] = getArray2D(_frames);
		set.Animates = new int[animateCount][];
		for (int i = 0; i < animateCount; i++) {
			String[] frame = frames[i].split(",", 3);
			int frameCount = Integer.parseInt(frame[1]);
			set.Animates[i] = new int[frameCount];
			if (frameCount > 0) {
				String[] data = CUtil.splitString(frame[2], ",");
				for (int f = 0; f < frameCount; f++) {
					set.Animates[i][f] = Integer.parseInt(data[f]);
				}
			}
		}
		// --------------------------------------------------------------------------------------------------------------
		// cds
		String cds[] = getArray2D(_cds);
		set.BlocksType = new int[cdCount];
		set.BlocksMask = new int[cdCount];
		set.BlocksX1 = new int[cdCount];
		set.BlocksY1 = new int[cdCount];
		set.BlocksX2 = new int[cdCount];
		set.BlocksY2 = new int[cdCount];
		set.BlocksW = new int[cdCount];
		set.BlocksH = new int[cdCount];
		for (int i = 0; i < cdCount; i++) {
			String[] cd = CUtil.splitString(cds[i], ",");
			set.BlocksType[i] = "rect".equals(cd[1]) ? CCD.CD_TYPE_RECT : CCD.CD_TYPE_LINE;
			set.BlocksMask[i] = Integer.parseInt(cd[2]);
			set.BlocksX1[i] = Integer.parseInt(cd[3]);
			set.BlocksY1[i] = Integer.parseInt(cd[4]);
			set.BlocksX2[i] = Integer.parseInt(cd[5]);
			set.BlocksY2[i] = Integer.parseInt(cd[6]);
			set.BlocksW[i] = Integer.parseInt(cd[7]);
			set.BlocksH[i] = Integer.parseInt(cd[8]);
		}
		// --------------------------------------------------------------------------------------------------------------
		// tile matrix
		String tile_matrix[] = getArray2D(_tile_matrix);
		set.TerrainScene2D = new int[set.YCount][set.XCount];
		for (int y = 0; y < set.YCount; y++) {
			String[] hline = CUtil.splitString(tile_matrix[y], ",");
			for (int x = 0; x < set.XCount; x++) {
				set.TerrainScene2D[y][x] = Integer.parseInt(hline[x]);
			}
		}
		// --------------------------------------------------------------------------------------------------------------
		// cd matrix
		String cd_matrix[] = getArray2D(_cd_matrix);
		set.TerrainBlock2D = new int[set.YCount][set.XCount];
		for (int y = 0; y < set.YCount; y++) {
			String[] hline = CUtil.splitString(cd_matrix[y], ",");
			for (int x = 0; x < set.XCount; x++) {
				set.TerrainBlock2D[y][x] = Integer.parseInt(hline[x]);
			}
		}
		
		return set;
	}



//	--------------------------------------------------------------------------------------------------------------
//	World_<WORLD INDEX>					=<WORLD INDEX>,<NAME>,<GRID X COUNT>,<GRID Y COUNT>,<GRID W>,<GRID H>,<WIDTH>,<HEIGHT>,<UNIT MAP COUNT>,<UNIT SPRITE COUNT>,<WAYPOINT COUNT>,<REGION COUNT>
//	World_<WORLD INDEX>_maps			=#<UNIT MAP>{<INDEX>,<MAP NAME>,<IDENTIFY>,<X>,<Y>,<SUPER>,<MAP DATA>},#<END UNIT MAP>
//	World_<WORLD INDEX>_sprs			=#<UNIT SPRITE>{<INDEX>,<SPR NAME>,<IDENTIFY>,<ANIMATE ID>,<FRAME ID>,<X>,<Y>,<SUPER>,<SPR DATA>},#<END UNIT SPRITE>
//	World_<WORLD INDEX>_waypoints		=#<WAYPOINT>{<INDEX>,<X>,<Y>,<PATH DATA>},#<END WAYPOINT>
//	World_<WORLD INDEX>_waypoint_link	=#<WAYPOINT LINK>{<START>,<END>},#<END WAYPOINT LINK>
//	World_<WORLD INDEX>_regions			=#<REGION>{<INDEX>,<X>,<Y>,<W>,<H>,<REGION DATA>},#<END REGION>
//	World_<WORLD INDEX>_data			=<DATA>
//	World_<WORLD INDEX>_terrain			=<TERRAIN>

	final WorldSet createWorldSet(String world, 
				String _maps,
				String _sprs, 
				String _waypoints, 
				String _waypoint_link, 
				String _regions, 
				String _data, 
				String _terrain) throws IOException
	{
		String[] args = CUtil.splitString(world, ",");
		
		WorldSet set = new WorldSet(Integer.parseInt(args[0]), args[1]);
		
		set.GridXCount	= Integer.parseInt(args[2]);
		set.GridYCount	= Integer.parseInt(args[3]);
		set.GridW		= Integer.parseInt(args[4]);
		set.GridH		= Integer.parseInt(args[5]);
		set.Width		= Integer.parseInt(args[6]);
		set.Height		= Integer.parseInt(args[7]);
		
		//------------------------------------------------------------------------
		// units
		int maps_count	= Integer.parseInt(args[8]);
		int sprs_count	= Integer.parseInt(args[9]);
		int wpss_count	= Integer.parseInt(args[10]);
		int wrss_count	= Integer.parseInt(args[11]);
		
		String[] maps	= getArray2D(_maps);
		String[] sprs	= getArray2D(_sprs);
		String[] wpss	= getArray2D(_waypoints);
		String[] wpsl	= getArray2D(_waypoint_link);
		String[] wrss	= getArray2D(_regions);
		
		for (int i=0; i<maps_count; i++){
			// <INDEX>,<MAP NAME>,<IDENTIFY>,<X>,<Y>,<SUPER>,<MAP DATA>
			WorldSet.MapObject map = new WorldSet.MapObject();
			String[] _args = maps[i].split(",", 7);
			map.Index 		= Integer.parseInt(_args[0]);
			map.UnitName 	= _args[1];
			map.MapID 		= _args[2];
			map.X 			= Integer.parseInt(_args[3]);
			map.Y 			= Integer.parseInt(_args[4]);
			map.ImagesID 	= _args[5];
			map.Data		= getArray1DLines(_args[6]);
			set.Maps.put(map.Index, map);
		}
		for (int i=0; i<sprs_count; i++) {
			// <INDEX>,<SPR NAME>,<IDENTIFY>,<ANIMATE ID>,<FRAME ID>,<X>,<Y>,<SUPER>,<SPR DATA>
			WorldSet.SpriteObject spr = new WorldSet.SpriteObject();
			String[] _args = sprs[i].split(",", 9);
			spr.Index 		= Integer.parseInt(_args[0]);
			spr.UnitName 	= _args[1];
			spr.SprID 		= _args[2];
			spr.Anim		= Integer.parseInt(_args[3]);
			spr.Frame		= Integer.parseInt(_args[4]);
			spr.X 			= Integer.parseInt(_args[5]);
			spr.Y 			= Integer.parseInt(_args[6]);
			spr.ImagesID 	= _args[7];
			spr.Data		= getArray1DLines(_args[8]);
			set.Sprs.put(spr.Index, spr);
		}
		for (int i=0; i<wpss_count; i++) {
			WorldSet.WaypointObject wp = new WorldSet.WaypointObject();
			String[] _args = wpss[i].split(",", 4);
			wp.Index 		= Integer.parseInt(_args[0]);
			wp.X 			= Integer.parseInt(_args[1]);
			wp.Y 			= Integer.parseInt(_args[2]);
			wp.Data			= getArray1DLines(_args[3]);
			set.WayPoints.put(wp.Index, wp);
		}
		for (int i=0; i<wrss_count; i++) {
			WorldSet.RegionObject wr = new WorldSet.RegionObject();
			String[] _args = wrss[i].split(",", 6);
			wr.Index 		= Integer.parseInt(_args[0]);
			wr.X 			= Integer.parseInt(_args[1]);
			wr.Y 			= Integer.parseInt(_args[2]);
			wr.W 			= Integer.parseInt(_args[3]);
			wr.H 			= Integer.parseInt(_args[4]);
			wr.Data			= getArray1DLines(_args[5]);
			set.Regions.put(wr.Index, wr);
		}
		
		for (int i = 0; i < wpsl.length; i++) {
			String[] link = CUtil.splitString(wpsl[i], ",");
			if (link.length >= 2) {
				int start = Integer.parseInt(link[0]);
				int end = Integer.parseInt(link[1]);
				set.WayPoints.get(start).Nexts.add(set.WayPoints.get(end));
			}
		}
		
		//------------------------------------------------------------------------
		// data
		set.Data		= getArray1DLines(_data);

		int terrains_count = set.GridXCount * set.GridYCount;
		set.Terrian = new int[set.GridXCount][set.GridYCount];
		try {
			String terrains[] = CUtil.splitString(_terrain, ",");
			for (int i = 0; i < terrains_count; i++) {
				int x = i / set.GridYCount;
				int y = i % set.GridYCount;
				set.Terrian[x][y] = Integer.parseInt(terrains[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return set;
	}







//-------------------------------------------------------------------------------------
//	#<COMMAND>
//	TableGroupCount=<COMMAND TABLE GROUP COUNT>
//	#<TABLE GROUP>
//	TG_<TABLE GROUP INDEX>=<TABLE GROUP INDEX>,<TABLE COUNT>,<TABLE GROUP NAME>
//	#<TABLE>
//	T_<TABLE GROUP INDEX>_<TABLE INDEX>	=<TABLE INDEX>,<COLUMN COUNT>,<ROW COUNT>,<TABLE NAME>
//	T_<TABLE GROUP INDEX>_<TABLE INDEX>_C	=#<COLUMN HEAD>{<INDEX>,<TEXT>},#<END COLUMN HEAD>
//	T_<TABLE GROUP INDEX>_<TABLE INDEX>_R	=#<ROWS>{<INDEX>,<ARRAY STR>},#<END ROWS>//	#<END TABLE>
//	#<END TABLE GROUP>
//	#<END COMMAND>

	final TableSet createTableSet(String table_group, PropertyGroup cfg) throws IOException
	{
		String[] args = CUtil.splitString(table_group, ",", 4);
		TableSet set = new TableSet(Integer.parseInt(args[0]), args[2]);
		
		set.TableCount = Integer.parseInt(args[1]);

		for (int i = 0; i < set.TableCount; i++)
		{
			String table	= cfg.getString("T_" + set.Index + "_" + i);
			String _columns	= cfg.getString("T_" + set.Index + "_" + i + "_C");
			String _rows	= cfg.getString("T_" + set.Index + "_" + i + "_R");
			
			String[] _args = CUtil.splitString(table, ",", 5);
			TableSet.Table tb = new TableSet.Table(Integer.parseInt(_args[0]), _args[3]);
			{
				tb.ColumnCount	= Integer.parseInt(_args[1]);
				tb.RowCount		= Integer.parseInt(_args[2]);
				tb.Columns		= getArray2D(_columns);
				tb.Rows			= new String[tb.RowCount][tb.ColumnCount];
				
				String[] rows	= getArray2D(_rows);
				for (int r = 0; r < tb.RowCount; r++) {
					StringReader reader = new StringReader(rows[r]);
					for (int c = 0; c < tb.ColumnCount; c++) {
						tb.Rows[r][c] = TextDeserialize.getBytesString(reader);
					}
				}
			}
			
			set.Tables.add(tb);
		}
		return set;
	}





}



