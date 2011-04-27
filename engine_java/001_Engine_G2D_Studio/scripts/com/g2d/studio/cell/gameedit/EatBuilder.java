package com.g2d.studio.cell.gameedit;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gameedit.Output;
import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;
import com.cell.gameedit.output.OutputProperties;
import com.cell.gameedit.output.OutputPropertiesDir;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.IPalette;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.script.js.JSManager;
import com.cell.util.Pair;
import com.cell.util.PropertyGroup;
import com.cell.util.zip.ZipUtil;
import com.g2d.Engine;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.cell.CellStreamTiles;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.io.file.FileIO;


public class EatBuilder extends Builder
{
//	final HashMap<String, String> script_map_sprite  = new HashMap<String, String>(5);
//	final HashMap<String, String> script_map_scene   = new HashMap<String, String>(5);
	
	final HashMap<String, String> script_map   = new HashMap<String, String>(5);
	
	java.io.File g2d_project_root;
	
	JSManager external_script_manager = new JSManager();
	
	public EatBuilder(String g2d_project_root) throws IOException
	{
		this.g2d_project_root = new java.io.File(g2d_project_root).getCanonicalFile();
//		script_map.put("output.properties", 		CIO.readAllText("/com/g2d/studio/cell/gameedit/output.properties"));
//		script_map.put("scene_jpg.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
//		script_map.put("scene_jpg_thumb.script",	CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg_thumb.script"));
//		script_map.put("scene_png.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
//		for (String config : Config.CELL_BUILD_SPRITE_OUTPUT.split("\\s")) {
//			script_map_sprite.put(config, CFile.readText(new java.io.File(root, config)));
//		}
//		for (String config : Config.CELL_BUILD_SCENE_OUTPUT.split("\\s")) {
//			script_map_scene.put(config, CFile.readText(new java.io.File(root, config)));
//		}
	}
	
	private java.io.File getLocalFile(com.g2d.studio.io.File cpj_file) {
		try {
			return new File(cpj_file.getPath()).getCanonicalFile();
		} catch (Exception err) {
			err.printStackTrace();
			return new File(cpj_file.getPath());
		}
	}

	public void openCellGameEdit(com.g2d.studio.io.File cpj_file) 
	{
		CellGameEditWrap.openCellGameEdit(
				Config.CELL_GAME_EDIT_CMD, 
				getLocalFile(cpj_file)
				);
	}
	
	public void buildSprite(com.g2d.studio.io.File cpj_file, boolean ignore_on_exist)
	{
		File cpj_file_name = getLocalFile(cpj_file);
		PreBuilder pb = new PreBuilder(cpj_file_name);
		if (ignore_on_exist && pb.checkOutputExists()) {
			System.out.println("ignore : " + cpj_file);
			return;
		}
		pb.build();
	}
	
	public void buildScene(com.g2d.studio.io.File cpj_file, boolean ignore_on_exist)
	{
		File cpj_file_name = getLocalFile(cpj_file);
		PreBuilder pb = new PreBuilder(cpj_file_name);
		if (ignore_on_exist && pb.checkOutputExists()) {
			System.out.println("ignore : " + cpj_file);
			return;
		}
		pb.build();
	}
	
	class PreBuilder
	{
		BuildExternalScript script;
		File dir;
		BuildProcess bp;
		java.io.File cpj_file;
		
		public PreBuilder(java.io.File cpj_file) {
			try {
				this.cpj_file = cpj_file.getCanonicalFile();
				File scfile = new File(Config.getRoot() + "/" + 
						Config.CELL_BUILD_OUTPUT_SCRIPT_FILE).getCanonicalFile();
				if (scfile.exists()) {
					script = external_script_manager.getInterface(
							scfile.getCanonicalPath(), BuildExternalScript.class);
					if (script != null) {
						dir = cpj_file.getParentFile().getCanonicalFile();
						bp = new BuildProcess(dir, g2d_project_root);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean checkOutputExists() {
			if (bp != null) {
				try {
					return script.checkOutputExists(bp, dir, cpj_file);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		public void build()
		{
			try {
				System.out.print("build " + cpj_file.getName() + " : " + cpj_file.getPath());
				String[] output_properties = this.selectOuputProperties();
				Process process = CellGameEditWrap.openCellGameEdit(
						Config.CELL_GAME_OUTPUT_CMD, 
						cpj_file, 
						output_properties
						);	
				WaitProcessTask task = new WaitProcessTask(process, Config.CELL_BUILD_WAIT_TIMEOUT_MS);
				task.start();
				try {
					process.waitFor();
					Thread.yield();
				} finally {
					synchronized (task) {
						task.notifyAll();
					}
				}
				this.runCustomOutput();
				System.out.println(" : done !");
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				this.saveBuildBat();
			}
		}
		
		private String[] selectOuputProperties() throws IOException
		{
			if (bp != null) {
				try {
					ArrayList<String> output_files = new ArrayList<String>();
					script.selectOuputProperties(bp, dir, cpj_file, output_files);
					if (!output_files.isEmpty()) {
						File dst_dir = new File(cpj_file.getParent(), "_script");
						if (!dst_dir.exists()) {
							dst_dir.mkdirs();
						}
						String[] ret = new String[output_files.size()];
						int i = 0;
						for (String file : output_files) {
							File dst_file = new File(dst_dir, file);
//							String script_code = script_map.get(file);
//							if (script_code == null) {
//								script_code = CFile.readText(new File(g2d_project_root, file), "UTF-8");
//								script_map.put(file, script_code);
//							}
							String script_code = CFile.readText(new File(g2d_project_root, file), "UTF-8");
							CFile.writeText(dst_file, script_code, "UTF-8");
							ret[i] = dst_file.getCanonicalPath();
							i++;
						}
						return ret;
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
			return new String[]{};
		}


		private void runCustomOutput() 
		{
			if (bp != null) {
				try {
					script.output(bp, dir, cpj_file);
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}

		
		private void saveBuildBat() {
			if (bp != null) {
				try {
					script.saveBuildBat(bp, dir, cpj_file);
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
		
		
	}
	
	protected void buildCMD(String sc_file_path)
	{
		try {
			File sc_file = new File(sc_file_path).getCanonicalFile();
			if (sc_file.exists()) {
				BuildScript script = external_script_manager.getInterface(
						sc_file.getCanonicalPath(), 
						BuildScript.class);
				if (script != null) {
					File dir = new File(System.getProperty("user.dir")).getCanonicalFile();
					BuildProcess bp = new BuildProcess(dir, g2d_project_root);
					script.build(bp, dir);
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	
	

//	private void saveBuildSpriteBat(File cpj_file_name) {
//		String cmd = CUtil.replaceString(Config.CELL_BUILD_SPRITE_CMD, "{file}", cpj_file_name.getName());
//		cmd = CUtil.replaceString(cmd, "\\n", "\n");
//		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), cmd, "UTF-8");
//	}
//
//	private void saveBuildSceneBat(File cpj_file_name) {
//		String cmd = CUtil.replaceString(Config.CELL_BUILD_SCENE_CMD, "{file}", cpj_file_name.getName());
//		cmd = CUtil.replaceString(cmd, "\\n", "\n");
//		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_scene.bat"), cmd, "UTF-8");
//	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------
//
//	---------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------

	@Override
	public com.g2d.studio.io.File getCPJFile(
			com.g2d.studio.io.File file,
			CPJResourceType resType) 
	{
		switch (resType) {
		case ACTOR:
			if (file.getName().startsWith("actor_")) {
				return file.getChildFile("actor.cpj");
			}
			break;
		case AVATAR:
			if (file.getName().startsWith("item_")) {
				return file.getChildFile("item.cpj");
			}
			break;
		case EFFECT:
			if (file.getName().startsWith("effect_")) {
				return file.getChildFile("effect.cpj");
			}
			break;
		case WORLD:
			if (file.getName().startsWith("scene_")) {
				return file.getChildFile("scene.cpj");
			}
			break;
		}
		return null;
	}
	
	@Override
	public StudioResource createResource(com.g2d.studio.io.File cpj_file) {
		try {
			Output out = getOutputFile(cpj_file);
			if (out != null) {
				EatResource ret = new EatResource(out, cpj_file.getPath());
//				System.out.println("create " + ret);
				return ret;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------

	private static Output getOutputFile(com.g2d.studio.io.File cpj_file)
	{
		try{
			com.g2d.studio.io.File pak = cpj_file.getParentFile().getParentFile().getChildFile(
					cpj_file.getParentFile().getName() + ".pak");
//			System.out.println("check : " + pak.getPath());
			if (pak.exists()) {
				return new OutputPack(pak);
			}
		} catch(Exception err){}
		try {
			if (cpj_file.getParentFile().getChildFile("output").exists()) {
				String outname = cpj_file.getName().toLowerCase()
						.replace("cpj", "properties");
				com.g2d.studio.io.File o = cpj_file.getParentFile().getChildFile("output").getChildFile(outname);
//				System.out.println("check : " + o.getPath());
				return new OutputPropertiesDir(o.getPath());
			}
		} catch (Exception err) {
		}
		return null;
	}
	
	static class EatResource extends StudioResource
	{
		public EatResource(Output output, String path) throws Exception {
			super(output, path);
		}
		
		@Override
		protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
			StreamTiles tiles = new OutputDirTiles(img, this);
			return tiles;
		}
		
		@Override
		public String toString() {
			return "EatResource : " + getPath();
		}
	}
	
	static class OutputPack extends OutputProperties
	{
		String conf_code;
		PropertyGroup config;
		HashMap<String, byte[]> resources;
		com.g2d.studio.io.File 	pak_file;
		
		public OutputPack(com.g2d.studio.io.File pak_file) throws Exception
		{
			super(pak_file.getPath());
			this.pak_file = pak_file;
		
			InputStream fis = pak_file.getInputStream();
			try {
				ZipInputStream zip_in = new ZipInputStream(fis);
				ZipEntry e = zip_in.getNextEntry();
				// 读入基础属性
				byte[] conf_data = ZipUtil.readBytes(zip_in);
				if (conf_data == null) {
					throw new FileNotFoundException(path);
				} else {
					e.getName();
//					System.out.println("unpak : " + e.getName());
				}
				conf_code = new String(conf_data, CIO.ENCODING);
				config = new PropertyGroup(conf_code, "=");
				super.init(config);
			} finally {
				fis.close();
			}
		}
		
		@Override
		public String getPropertiesCode() {
			return conf_code;
		}
		
		@Override
		public PropertyGroup getProperties() {
			return config;
		}
		
		@Override
		public void dispose() {
			resources = null;
		}
		
		@Override
		public byte[] loadRes(String name, AtomicReference<Float> percent) {
			synchronized (this) {
				if (resources == null) {
					resources = initPakFiles();
				}
			}
			name = name.replaceAll("\\\\", "/");
			return resources.get(name);
		}
		
		private HashMap<String, byte[]> initPakFiles()
		{
			HashMap<String, byte[]> resources = new HashMap<String, byte[]>();
			try {
				InputStream fis = pak_file.getInputStream();
				try {
					ZipInputStream zip_in = new ZipInputStream(fis);
					zip_in.getNextEntry();
					try {
						for (ZipEntry e = zip_in.getNextEntry(); e != null; e = zip_in.getNextEntry()) {
							resources.put(e.getName(), ZipUtil.readBytes(zip_in));
						}
					} finally {
						zip_in.close();
					}
				} finally {
					fis.close();
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
			return resources;
		}
	}


	/**
	 * 根据图片组名字确定读入jpg或png
	 * @author WAZA
	 */
	static class OutputDirTiles extends CellStreamTiles
	{
		public OutputDirTiles(ImagesSet img, StudioResource resource) {
			super(img, resource);
		}
		
		@Override
		protected void initImages() throws Throwable 
		{
			System.out.println("initImages : " + img.getName() + " : " + set.toString());
			
			// 根据tile的类型来判断读取何种图片
			if (img.Name.equals("png") || img.Name.equals("jpg")) {
				if (set.getOutput() instanceof OutputPack) {
					if (loadPakImages()) {
						return;
					}
				} else {
					if (loadZipImages()) {
						return;
					}
				}
			}
			super.initImages();
		}
		
		@Override
		public void unloadAllImages() {
			super.unloadAllImages();
			System.out.println("unloadAllImages : " + img.getName() + " : " + set.toString());
		}
		
		@Override
		protected void drawLoading(IGraphics g, int x, int y, int w, int h) {}
		
		protected boolean loadPakImages() {
			boolean is_png_mask = img.Name.startsWith("png");
			for (int i = 0; i < images.length; i++) {
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					try {
						if (is_png_mask) {
							byte[] idata = set.getOutput().loadRes(img.Name+"/"+i+".png", null);
//							if (idata != null) {
								images[i] = com.g2d.Tools.decodeImageMask(idata, 0);
//							}
						} else {
							byte[] idata = set.getOutput().loadRes(img.Name+"/"+i+"."+img.Name, null);
//							if (idata != null) {
								images[i] = Engine.getEngine().createImage(new ByteArrayInputStream(idata));
//							}
						}
						IPalette palette = this.getPalette();
						if (palette != null && images[i] != null) {
							images[i].setPalette(palette);		
						}
					} catch (Exception err) {
						System.err.println("loadPakImages \""+img.Name+"\" tile error : " + i);
						err.printStackTrace();
					}
				}
			}
			return true;
		}
		
		protected boolean loadZipImages() {
			boolean is_png_mask = img.Name.startsWith("png");
			byte[] zipdata = set.getOutput().loadRes(img.Name+".zip", null);
			if (zipdata != null) {
				Map<String, ByteArrayInputStream> files = ZipUtil.unPackFile(new ByteArrayInputStream(zipdata));
				for (int i = 0; i < images.length; i++) {
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						ByteArrayInputStream idata = files.get(i+"."+img.Name);
						try { 
							if (idata != null) {
								if (is_png_mask) {
									images[i] = com.g2d.Tools.decodeImageMask(CIO.readStream(idata), 0);
								} else {
									images[i] = Engine.getEngine().createImage(idata);
								}
							}
							IPalette palette = this.getPalette();
							if (palette != null && images[i] != null) {
								images[i].setPalette(palette);		
							}
						} catch (Exception err) {
							System.err.println("loadZipImages \""+img.Name+"\" tile error : " + i);
							err.printStackTrace();
						}
					}
				}
				return true;
			}
			return false;
		}
	}
	


	
	
//	-----------------------------------------------------------------------------------------------------------

	
	public static void main(String[] args) throws IOException
	{
//		System.err.close();
//		System.out.close();
		
		String usage = "usage: 有2种方式运行导出脚本\n" +
		
				"1. 导出CPJ资源文件，必须在CPJ工程目录下执行，需要3个参数\n" +
				"EatBuilder <cpj file> <cpj type> <g2d project file>\n" +
				"	cpj file          : CPJ工程文件\n" +
				"	cpj type          : scene 或 sprite\n" +
				"	g2d project file  : G2D工程文件\n" +
				"\n" +
				
				"2. 运行任意脚本，在任意的目录\n" +
				"EatBuilder <js process script>\n" +
				"	js process script : 要运行的JS脚本，必须实现\n" +
				"	                    void build(BuildProcess p, File dir);\n" +
				"	g2d project file  : G2D工程文件\n" +
				"\n";
		
		System.out.println(usage);
//		System.out.println(System.getProperty("user.dir"));
		
		if (args.length > 0)
		{
			if (args.length == 2) 
			{
				String arg_0 = args[0].toLowerCase().trim();
				String arg_1 = args[1].toLowerCase().trim();
				Config.load(arg_1);
				EatBuilder builder = new EatBuilder(new File(arg_1).getParent());
				Builder.setBuilder(builder);
				
				builder.buildCMD(arg_0);
			}
			else if (args.length == 3) 
			{
				String arg_0 = args[0].toLowerCase().trim();
				String arg_1 = args[1].toLowerCase().trim();
				String arg_2 = args[2].toLowerCase().trim();
				Config.load(arg_2);
				EatBuilder builder = new EatBuilder(new File(arg_2).getParent());
				Builder.setBuilder(builder);
				
				FileIO io = new FileIO(new String[]{});
				if (arg_1.equals("scene")) {
					builder.buildScene(io.createFile(new File(arg_0).getCanonicalPath()), false);
				} else {
					builder.buildSprite(io.createFile(new File(arg_0).getCanonicalPath()), false);
				}
			}
		}
		
		System.exit(0);
	}
}
