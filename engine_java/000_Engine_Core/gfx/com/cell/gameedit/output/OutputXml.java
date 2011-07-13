package com.cell.gameedit.output;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gameedit.Output;
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
abstract public class OutputXml extends BaseOutput
{		
	private String	image_type;
	private boolean	image_tile;
	private boolean	image_group;
	
	final protected void init(Document doc) throws Exception
	{
		Element element = doc.getDocumentElement();
		element.normalize();
		
		NodeList list = element.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) {
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("IMAGE_TYPE")) {
					image_type = e.getTextContent().trim();
				}
				else if (e.getNodeName().equals("IMAGE_TILE")) {
					image_tile = Boolean.parseBoolean(e.getTextContent().trim());
				}
				else if (e.getNodeName().equals("IMAGE_GROUP")) {
					image_group = Boolean.parseBoolean(e.getTextContent().trim());
				}
				else if (e.getNodeName().equals("level")) {
					initLevel(e);
				}
				else if (e.getNodeName().equals("resource")) {
					initResource(e);
				}
			}
		}
	}
	
	private void initResource(Element resource) throws IOException 
	{
//		Integer.parseInt(resource.getAttribute("images_count"));
//		Integer.parseInt(resource.getAttribute("map_count"));
//		Integer.parseInt(resource.getAttribute("sprite_count"));

		NodeList list = resource.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) {
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("images")) {
					initImages(e);
				}
				else if (e.getNodeName().equals("map")) {
					initMap(e);
				}
				else if (e.getNodeName().equals("sprite")) {
					initSprite(e);
				}
			}
		}
	}
	
	private void initImages(Element images)  throws IOException 
	{
		ImagesSet set = new ImagesSet(
				Integer.parseInt(images.getAttribute("index")), 
				images.getAttribute("name"));
		
		set.Count = Integer.parseInt(images.getAttribute("size"));
		set.ClipsX = new int[set.Count];
		set.ClipsY = new int[set.Count];
		set.ClipsW = new int[set.Count];
		set.ClipsH = new int[set.Count];
		set.ClipsKey = new String[set.Count];
		
		NodeList list = images.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) {
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("clip")) {
					int index = Integer.parseInt(e.getAttribute("index"));
					set.ClipsX[index] 	= Integer.parseInt(e.getAttribute("x"));
					set.ClipsY[index] 	= Integer.parseInt(e.getAttribute("y"));
					set.ClipsW[index] 	= Integer.parseInt(e.getAttribute("width"));
					set.ClipsH[index] 	= Integer.parseInt(e.getAttribute("height"));
					set.ClipsKey[index] = e.getAttribute("data");
				}
			}
		}
	}

	private void initMap(Element map)  throws IOException 
	{
		MapSet set = new MapSet(
				Integer.parseInt(map.getAttribute("index")), 
				map.getAttribute("name"));
		
		set.ImagesName 		= map.getAttribute("images_name");
		set.XCount 			= Integer.parseInt(map.getAttribute("xcount"));
		set.YCount 			= Integer.parseInt(map.getAttribute("ycount"));
		set.CellW 			= Integer.parseInt(map.getAttribute("cellw"));
		set.CellH 			= Integer.parseInt(map.getAttribute("cellh"));

		int scenePartCount 	= Integer.parseInt(map.getAttribute("scene_part_count"));
		int animateCount 	= Integer.parseInt(map.getAttribute("scene_frame_count"));
		int cdCount 		= Integer.parseInt(map.getAttribute("cd_part_count"));

		set.TileID 			= new int[scenePartCount];
		set.TileTrans 		= new int[scenePartCount];
		set.Animates 		= new int[animateCount][];
		set.BlocksType 		= new int[cdCount];
		set.BlocksMask 		= new int[cdCount];
		set.BlocksX1 		= new int[cdCount];
		set.BlocksY1 		= new int[cdCount];
		set.BlocksX2 		= new int[cdCount];
		set.BlocksY2 		= new int[cdCount];
		set.BlocksW 		= new int[cdCount];
		set.BlocksH 		= new int[cdCount];
		
		set.TerrainScene2D	= new int[set.YCount][set.XCount];
		set.TerrainBlock2D	= new int[set.YCount][set.XCount];

		NodeList list = map.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("scene_part")) 
				{
					int index 			= Integer.parseInt(e.getAttribute("index"));
					set.TileID[index]	= Integer.parseInt(e.getAttribute("tile"));
					set.TileTrans[index]= Integer.parseInt(e.getAttribute("trans"));
				}
				else if (e.getNodeName().equals("scene_frame")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					int frameCount = Integer.parseInt(e.getAttribute("data_size"));
					set.Animates[index] = new int[frameCount];
					if (frameCount > 0) {
						String[] data = CUtil.splitString(e.getAttribute("data"), ",");
						for (int f = 0; f < frameCount; f++) {
							set.Animates[index][f] = Integer.parseInt(data[f]);
						}
					}
				}
				else if (e.getNodeName().equals("cd_part")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					set.BlocksType[index]	= "rect".equals(e.getAttribute("type")) ?
							CCD.CD_TYPE_RECT : CCD.CD_TYPE_LINE;
					set.BlocksMask[index]	= Integer.parseInt(e.getAttribute("mask"));
					set.BlocksX1[index] 	= Integer.parseInt(e.getAttribute("x1"));
					set.BlocksY1[index] 	= Integer.parseInt(e.getAttribute("y1"));
					set.BlocksX2[index] 	= Integer.parseInt(e.getAttribute("x2"));
					set.BlocksY2[index] 	= Integer.parseInt(e.getAttribute("y2"));
					set.BlocksW[index] 		= Integer.parseInt(e.getAttribute("width"));
					set.BlocksH[index] 		= Integer.parseInt(e.getAttribute("height"));
				}
				else if (e.getNodeName().equals("matrix")) 
				{
					String tile_matrix[]	= getArray2D(e.getAttribute("tile_matrix"));
					String cd_matrix[] 		= getArray2D(e.getAttribute("flag_matrix"));
					for (int y = 0; y < set.YCount; y++) {
						String[] tline = CUtil.splitString(tile_matrix[y], ",");
						String[] cline = CUtil.splitString(cd_matrix[y],   ",");
						for (int x = 0; x < set.XCount; x++) {
							set.TerrainScene2D[y][x] = Integer.parseInt(tline[x]);
							set.TerrainBlock2D[y][x] = Integer.parseInt(cline[x]);
						}
					}
				}
			}
		}
	
	}

	private void initSprite(Element sprite) throws IOException 
	{
		SpriteSet set = new SpriteSet(
				Integer.parseInt(sprite.getAttribute("index")), 
				sprite.getAttribute("name"));
		
		set.ImagesName = sprite.getAttribute("images_name");

		int scenePartCount 	= Integer.parseInt(sprite.getAttribute("scene_part_count"));
		int sceneFrameCount = Integer.parseInt(sprite.getAttribute("scene_frame_count"));
		int cdCount 		= Integer.parseInt(sprite.getAttribute("cd_part_count"));
		int collidesCount 	= Integer.parseInt(sprite.getAttribute("cd_frame_count"));
		int animateCount 	= Integer.parseInt(sprite.getAttribute("animate_count"));

		set.PartX 			= new short[scenePartCount];
		set.PartY 			= new short[scenePartCount];
		set.PartTileID 		= new short[scenePartCount];
		set.PartTileTrans 	= new byte[scenePartCount];
		set.Parts 			= new short[sceneFrameCount][];
		set.BlocksMask 		= new int[cdCount];
		set.BlocksX1 		= new short[cdCount];
		set.BlocksY1 		= new short[cdCount];
		set.BlocksW 		= new short[cdCount];
		set.BlocksH 		= new short[cdCount];
		set.Blocks 			= new short[collidesCount][];
		set.AnimateCount 	= animateCount;
		set.AnimateNames 	= new String[animateCount];
		set.FrameAnimate 	= new short[animateCount][];
		set.FrameCDMap 		= new short[animateCount][];
		set.FrameCDAtk 		= new short[animateCount][];
		set.FrameCDDef 		= new short[animateCount][];
		set.FrameCDExt 		= new short[animateCount][];
		
		NodeList list = sprite.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("scene_part")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					set.PartTileID[index] 		= Short.parseShort(e.getAttribute("tile"));
					set.PartX[index] 			= Short.parseShort(e.getAttribute("x"));
					set.PartY[index] 			= Short.parseShort(e.getAttribute("y"));
					set.PartTileTrans[index]	= Byte.parseByte(e.getAttribute("trans"));
				}
				else if (e.getNodeName().equals("scene_frame")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					int frameCount = Integer.parseInt(e.getAttribute("data_size"));
					set.Parts[index] = new short[frameCount];
					if (frameCount > 0) {
						String[] data = CUtil.splitString(e.getAttribute("data"), ",");
						for (int f = 0; f < frameCount; f++) {
							set.Parts[index][f] = Short.parseShort(data[f]);
						}
					}
				}
				else if (e.getNodeName().equals("cd_part")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					set.BlocksMask[index]	= Integer.parseInt(e.getAttribute("mask"));
					set.BlocksX1[index] 	= Short.parseShort(e.getAttribute("x1"));
					set.BlocksY1[index] 	= Short.parseShort(e.getAttribute("y1"));
					set.BlocksW[index] 		= Short.parseShort(e.getAttribute("width"));
					set.BlocksH[index] 		= Short.parseShort(e.getAttribute("height"));
				}
				else if (e.getNodeName().equals("cd_frame")) 
				{
					int index = Integer.parseInt(e.getAttribute("index"));
					int frameCount = Integer.parseInt(e.getAttribute("data_size"));
					set.Blocks[index] = new short[frameCount];
					if (frameCount > 0) {
						String[] data = CUtil.splitString(e.getAttribute("data"), ",");
						for (int f = 0; f < frameCount; f++) {
							set.Blocks[index][f] = Short.parseShort(data[f]);
						}
					}
				}
				else if (e.getNodeName().equals("frames"))
				{
					StringReader AnimateNamesReader = new StringReader(e.getAttribute("names"));
					String frame_counts[] 	= CUtil.splitString(e.getAttribute("counts"), ",");
					String frame_animate[] 	= getArray2D(e.getAttribute("animates"));
					String frame_cd_map[] 	= getArray2D(e.getAttribute("cd_map"));
					String frame_cd_atk[] 	= getArray2D(e.getAttribute("cd_atk"));
					String frame_cd_def[] 	= getArray2D(e.getAttribute("cd_def"));
					String frame_cd_ext[] 	= getArray2D(e.getAttribute("cd_ext"));
					
					for (int i = 0; i < animateCount; i++) {
						set.AnimateNames[i] = TextDeserialize.getString(AnimateNamesReader);
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
				}
			}
		}
	}
	
	private void initLevel(Element level)  throws IOException 
	{
//		Integer.parseInt(level.getAttribute("world_count"));

		NodeList list = level.getChildNodes();
		
		for (int i = list.getLength() - 1; i >= 0; --i) {
			Node node = list.item(i);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("world")) {
					initWorld(e);
				}
			}
		}
	}
	

	private void initWorld(Element world) throws IOException 
	{
		WorldSet set = new WorldSet(
				Integer.parseInt(world.getAttribute("index")), 
				world.getAttribute("name"));
		
		set.GridXCount	= Integer.parseInt(world.getAttribute("grid_x_count"));
		set.GridYCount	= Integer.parseInt(world.getAttribute("grid_y_count"));
		set.GridW		= Integer.parseInt(world.getAttribute("grid_w"));
		set.GridH		= Integer.parseInt(world.getAttribute("grid_h"));
		set.Width		= Integer.parseInt(world.getAttribute("width"));
		set.Height		= Integer.parseInt(world.getAttribute("height"));
		
//		int maps_count	= Integer.parseInt(world.getAttribute("unit_count_map"));
//		int sprs_count	= Integer.parseInt(world.getAttribute("unit_count_sprite"));
//		int wpss_count	= Integer.parseInt(world.getAttribute("waypoint_count"));
//		int wrss_count	= Integer.parseInt(world.getAttribute("region_count"));

		set.Data		= world.getAttribute("data");
		
		int terrains_count = set.GridXCount * set.GridYCount;
		set.Terrian = new int[set.GridXCount][set.GridYCount];
		String terrains[] = CUtil.splitString(world.getAttribute("terrain"), ",");
		for (int i = 0; i < terrains_count; i++) {
			int x = i / set.GridYCount;
			int y = i % set.GridYCount;
			set.Terrian[x][y] = Integer.parseInt(terrains[i]);
		}

		NodeList list = world.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("unit_map")) 
				{
					WorldSet.MapObject map = new WorldSet.MapObject();
					map.Index 		= Integer.parseInt(e.getAttribute("index"));
					map.UnitName 	= e.getAttribute("map_name");
					map.MapID 		= e.getAttribute("id");
					map.X 			= Integer.parseInt(e.getAttribute("x"));
					map.Y 			= Integer.parseInt(e.getAttribute("y"));
					map.ImagesID 	= e.getAttribute("images");
					map.Data		= e.getAttribute("data");
					set.Maps.put(map.Index, map);
				}
				else if (e.getNodeName().equals("unit_sprite"))
				{
					WorldSet.SpriteObject spr = new WorldSet.SpriteObject();
					spr.Index 		= Integer.parseInt(e.getAttribute("index"));
					spr.UnitName 	= e.getAttribute("spr_name");
					spr.SprID 		= e.getAttribute("id");
					spr.Anim		= Integer.parseInt(e.getAttribute("animate_id"));
					spr.Frame		= Integer.parseInt(e.getAttribute("frame_id"));
					spr.X 			= Integer.parseInt(e.getAttribute("x"));
					spr.Y 			= Integer.parseInt(e.getAttribute("y"));
					spr.ImagesID 	= e.getAttribute("images");
					spr.Data		= e.getAttribute("data");
					set.Sprs.put(spr.Index, spr);
				}
				else if (e.getNodeName().equals("waypoint"))
				{
					WorldSet.WaypointObject wp = new WorldSet.WaypointObject();
					wp.Index 		= Integer.parseInt(e.getAttribute("index"));
					wp.X 			= Integer.parseInt(e.getAttribute("x"));
					wp.Y 			= Integer.parseInt(e.getAttribute("y"));
					wp.Data			= e.getAttribute("data");
					set.WayPoints.put(wp.Index, wp);
				}
				else if (e.getNodeName().equals("region"))
				{
					WorldSet.RegionObject wr = new WorldSet.RegionObject();
					wr.Index 		= Integer.parseInt(e.getAttribute("index"));
					wr.X 			= Integer.parseInt(e.getAttribute("x"));
					wr.Y 			= Integer.parseInt(e.getAttribute("y"));
					wr.W 			= Integer.parseInt(e.getAttribute("width"));
					wr.H 			= Integer.parseInt(e.getAttribute("height"));
					wr.Data			= e.getAttribute("data");
					set.Regions.put(wr.Index, wr);
				}
			}
		}
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("waypoint_link")) {
					int start	= Integer.parseInt(e.getAttribute("start"));
					int end 	= Integer.parseInt(e.getAttribute("end"));
					set.WayPoints.get(start).Nexts.add(set.WayPoints.get(end));
				}
			}
		}
	}
	
//	------------------------------------------------------------------------------------------------
	
	@Override
	public String getImageExtentions() {
		return image_type;
	}
	
	@Override
	public boolean isGroup() {
		return image_group;
	}
	
	@Override
	public boolean isTile() {
		return image_tile;
	}
	
	@Override
	public void dispose() 
	{}

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



