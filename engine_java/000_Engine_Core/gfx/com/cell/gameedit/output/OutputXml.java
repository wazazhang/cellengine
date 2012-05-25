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
import com.cell.gameedit.OutputLoader;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;
import com.cell.gfx.IGraphics.ImageAnchor;
import com.cell.gfx.IGraphics.ImageTrans;
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
	

	protected void init(InputStream is) throws Exception 
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			this.init(doc);
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());
			throw err;
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw e;
		}
	}
	
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
					ImagesSet is = initImages(e);
					super.ImgTable.put(is.Name, is);
				}
				else if (e.getNodeName().equals("map")) {
					MapSet ms = initMap(e);
					super.MapTable.put(ms.Name, ms);
				}
				else if (e.getNodeName().equals("sprite")) {
					SpriteSet ss = initSprite(e);
					super.SprTable.put(ss.Name, ss);
				}
			}
		}
	}
	
	private ImagesSet initImages(Element images)  throws IOException 
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
		
		return set;
	}

	private MapSet initMap(Element map)  throws IOException 
	{
		MapSet set = new MapSet(
				Integer.parseInt(map.getAttribute("index")), 
				map.getAttribute("name"));
		
		set.ImagesName 		= map.getAttribute("images_name");
		set.XCount 			= Integer.parseInt(map.getAttribute("xcount"));
		set.YCount 			= Integer.parseInt(map.getAttribute("ycount"));
		set.CellW 			= Integer.parseInt(map.getAttribute("cellw"));
		set.CellH 			= Integer.parseInt(map.getAttribute("cellh"));
		set.LayerCount		= Integer.parseInt(map.getAttribute("layer_count"));
		int cdCount 		= Integer.parseInt(map.getAttribute("cd_part_count"));

		set.BlocksType 		= new int[cdCount];
		set.BlocksMask 		= new int[cdCount];
		set.BlocksX1 		= new int[cdCount];
		set.BlocksY1 		= new int[cdCount];
		set.BlocksX2 		= new int[cdCount];
		set.BlocksY2 		= new int[cdCount];
		set.BlocksW 		= new int[cdCount];
		set.BlocksH 		= new int[cdCount];
		
		set.TerrainTile		= new int[set.LayerCount][set.YCount][set.XCount];
		set.TerrainFlip		= new int[set.LayerCount][set.YCount][set.XCount];
		set.TerrainFlag		= new int[set.LayerCount][set.YCount][set.XCount];

		NodeList list = map.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("cd_part")) 
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
				else if (e.getNodeName().equals("layer")) 
				{
					int layerIndex			= Integer.parseInt(e.getAttribute("index"));
					String tile_matrix[]	= getArray2D(e.getAttribute("tile_matrix"));
					String flip_matrix[]	= getArray2D(e.getAttribute("flip_matrix"));
					String flag_matrix[] 	= getArray2D(e.getAttribute("flag_matrix"));
					for (int y = 0; y < set.YCount; y++) {
						String[] tline = CUtil.splitString(tile_matrix[y], ",");
						String[] fline = CUtil.splitString(flip_matrix[y], ",");
						String[] cline = CUtil.splitString(flag_matrix[y], ",");
						for (int x = 0; x < set.XCount; x++) {
							set.TerrainTile[layerIndex][y][x] = Integer.parseInt(tline[x]);
							set.TerrainFlip[layerIndex][y][x] = Integer.parseInt(fline[x]);
							set.TerrainFlag[layerIndex][y][x] = Integer.parseInt(cline[x]);
						}
					}
				}
			}
		}
	
		return set;
	}

	private SpriteSet initSprite(Element sprite) throws IOException 
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
		set.PartZ 			= new short[scenePartCount];
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
		set.FrameDatas		= new String[animateCount][];
		
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
					set.PartZ[index] 			= Short.parseShort(e.getAttribute("z"));
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
					String frame_datas[]	= getArray2D(e.getAttribute("fdata"));
					
					for (int i = 0; i < animateCount; i++) 
					{
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
						
						StringReader frameDataReader = new StringReader(frame_datas[i]);
						set.FrameDatas[i] = new String[frameCount];
						
						for (int f = 0; f < frameCount; f++) 
						{
							set.FrameAnimate[i][f] = Short.parseShort(animate[f]);
							set.FrameCDMap[i][f] = Short.parseShort(cd_map[f]);
							set.FrameCDAtk[i][f] = Short.parseShort(cd_atk[f]);
							set.FrameCDDef[i][f] = Short.parseShort(cd_def[f]);
							set.FrameCDExt[i][f] = Short.parseShort(cd_ext[f]);
							
							set.FrameDatas[i][f] = TextDeserialize.getString(frameDataReader);
							
						}
					}
				}
			}
		}
		
		return set;
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
					WorldSet ws = initWorld(e);
					super.WorldTable.put(ws.Name, ws);
				}
			}
		}
	}
	

//	<world index="0" name="scene000000" 
//		grid_x_count="20"
//		grid_y_count="20"
//		grid_w="32"
//		grid_h="32"
//		width="640"
//		height="640"
//		unit_count_map="1"
//		unit_count_sprite="3"
//		unit_count_image="3"
//		waypoint_count="3"
//		region_count="2"
//		event_count="4"
//		data="0,,"
//		terrain="0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,">
//		
//		<unit_map index="0" map_name="M000_map000000" id="map000000" x="0" y="0" images="mapTile" map_data="0,," priority="0" />
//		
//		<unit_sprite index="0" spr_name="S001_unamed_Sprite" id="unamed_Sprite" animate_id="0" frame_id="0" x="197" y="86" images="unamed_Tile" spr_data="0,," priority="0" />
//		<unit_sprite index="1" spr_name="S002_unamed_Sprite" id="unamed_Sprite" animate_id="0" frame_id="0" x="422" y="222" images="unamed_Tile" spr_data="0,," priority="0" />
//		<unit_sprite index="2" spr_name="S003_unamed_Sprite" id="unamed_Sprite" animate_id="2" frame_id="0" x="142" y="281" images="unamed_Tile" spr_data="0,," priority="0" />
//		
//		<unit_image index="0" img_name="T004_mapObjTile" id="mapObjTile" tile_id="0" anchor="C_C" x="240" y="172" trans="MR_90" img_data="0,," priority="0" />
//		<unit_image index="1" img_name="T006_mapObjTile" id="mapObjTile" tile_id="2" anchor="L_T" x="243" y="244" trans="R_90" img_data="0,," priority="0" />
//		<unit_image index="2" img_name="T005_mapObjTile" id="mapObjTile" tile_id="1" anchor="C_C" x="205" y="366" trans="NONE" img_data="0,," priority="0" />
//		
//		<waypoint index="0" x="220" y="301" path_data="0,," />
//		<waypoint index="1" x="495" y="395" path_data="0,," />
//		<waypoint index="2" x="312" y="414" path_data="0,," />
//		
//		<waypoint_link start="0" end="2" />
//		<waypoint_link start="2" end="0" />
//		
//		<region index="0" x="367" y="342" width="238" height="125" region_data="0,," />
//		<region index="1" x="30" y="359" width="101" height="120" region_data="0,," />
//		
//		<event index="0" id="0" event_name="��xxx" event_file="EventTemplate.txt" x="111" y="146" event_data="0,," />
//		<event index="1" id="1" event_name="��xxx" event_file="EventTemplate.txt" x="435" y="115" event_data="0,," />
//		<event index="2" id="2" event_name="��xxx" event_file="EventTemplate.txt" x="537" y="154" event_data="0,," />
//		<event index="3" id="3" event_name="��ͷ��" event_file="EventTemplate.txt" x="348" y="321" event_data="0,," />
//	</world>
	private WorldSet initWorld(Element world) throws IOException 
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

		set.Data		= getArray1DLines(world.getAttribute("data"));
		
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
					map.Data		= getArray1DLines(e.getAttribute("map_data"));
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
					spr.Data		= getArray1DLines(e.getAttribute("spr_data"));
					set.Sprs.put(spr.Index, spr);
				}
				else if (e.getNodeName().equals("unit_image")) 
				{
					WorldSet.ImageObject img = new WorldSet.ImageObject();
					img.Index 		= Integer.parseInt(e.getAttribute("index"));
					img.UnitName 	= e.getAttribute("img_name");
					img.ImagesID 	= e.getAttribute("id");
					img.TileID		= Integer.parseInt(e.getAttribute("tile_id"));
					img.Anchor		= ImageAnchor.valueOf(e.getAttribute("anchor"));
					img.Trans		= ImageTrans.valueOf(e.getAttribute("trans"));
					img.X 			= Integer.parseInt(e.getAttribute("x"));
					img.Y 			= Integer.parseInt(e.getAttribute("y"));
					img.Data		= getArray1DLines(e.getAttribute("img_data"));
					set.Imgs.put(img.Index, img);
				}
				else if (e.getNodeName().equals("waypoint"))
				{
					WorldSet.WaypointObject wp = new WorldSet.WaypointObject();
					wp.Index 		= Integer.parseInt(e.getAttribute("index"));
					wp.X 			= Integer.parseInt(e.getAttribute("x"));
					wp.Y 			= Integer.parseInt(e.getAttribute("y"));
					wp.Data			= getArray1DLines(e.getAttribute("path_data"));
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
					wr.Data			= getArray1DLines(e.getAttribute("region_data"));
					set.Regions.put(wr.Index, wr);
				}
				else if (e.getNodeName().equals("event"))
				{
					WorldSet.EventObject ev = new WorldSet.EventObject();
					ev.Index 		= Integer.parseInt(e.getAttribute("index"));
					ev.ID			= Integer.parseInt(e.getAttribute("id"));
					ev.X 			= Integer.parseInt(e.getAttribute("x"));
					ev.Y 			= Integer.parseInt(e.getAttribute("y"));
					ev.EventName 	= e.getAttribute("event_name");
					ev.EventFile 	= e.getAttribute("event_file");
					ev.Data		 	= e.getAttribute("event_data");
					set.Events.put(ev.Index, ev);
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
		
		return set;
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




}



