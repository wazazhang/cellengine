package com.g2d.studio.sample.builder;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.cell.CIO;
import com.cell.gameedit.OutputLoader;
import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.output.OutputXmlDir;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IPalette;
import com.cell.io.CFile;
import com.cell.util.PropertyGroup;
import com.cell.util.zip.ZipUtil;
import com.g2d.Engine;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.cell.CellStreamTiles;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.cell.gameedit.JSBuildOutputScript;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.file.FileIO;

public class SampleBuilder extends Builder
{
	public SampleBuilder(String g2d_project_root) throws IOException
	{
		super(g2d_project_root);
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
//
//	---------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------

	@Override
	public com.g2d.studio.io.File findCPJFile(
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
	public BuilderTask preBuild(File cpj_file_name) throws Exception
	{	
		//File scfile = new File(g2d_project_root, "buildscript/build_output_script.js");
		//JSBuildOutputScript js = createOutputScript(scfile);
		return new BuilderTask(null, cpj_file_name, 60000);
	}
	
	@Override
	public StudioResource createResource(com.g2d.studio.io.File cpj_file) {
		try {
			OutputLoader out = getOutputFile(cpj_file);
			if (out != null) {
				SampleResource ret = new SampleResource(out);
//				System.out.println("create " + ret);
				return ret;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------

	private static OutputLoader getOutputFile(com.g2d.studio.io.File cpj_file)
	{
		try {
			if (cpj_file.getParentFile().getChildFile("output").exists()) {
				String outname = cpj_file.getName().toLowerCase()
						.replace("cpj", "xml");
				com.g2d.studio.io.File o = cpj_file.getParentFile().
				getChildFile("output").getChildFile(outname);
				return new OutputXmlDir(o.getPath());
			}
		} catch (Exception err) {
		}
		return null;
	}
	
	static class SampleResource extends StudioResource
	{
		public SampleResource(OutputLoader output) throws Exception {
			super(output);
		}
		
		@Override
		public String toString() {
			return "SampleResource : " + getPath();
		}
	}
	
}
