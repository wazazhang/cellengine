package com.g2d.cell;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gfx.IPalette;
import com.g2d.Engine;
import com.g2d.Image;

public class CellStreamTiles extends StreamTiles
{
	public CellStreamTiles(ImagesSet img, CellSetResource res) {
		super(img, res);
	}
	
	public boolean isTile() {
		String code = set.getOutput().getPropertiesCode();
		Pattern pattern_tile = Pattern.compile("#<IMAGE TILE>\\s+\\w+");
		Matcher matcher = pattern_tile.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			return group.endsWith("true");
		}
		return false;
	}
	
	public boolean isGroup() {
		String code = set.getOutput().getPropertiesCode();
		Pattern pattern_group = Pattern.compile("#<IMAGE GROUP>\\s+\\w+");
		Matcher matcher = pattern_group.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			return group.endsWith("true");
		}
		return false;
	}
	
	public String getImageExtentions() {
		String code = set.getOutput().getPropertiesCode();
		Pattern pattern_tile = Pattern.compile("#<IMAGE TYPE>\\s+\\w+");
		Matcher matcher = pattern_tile.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			String[] split = group.split("\\s");
			return split[split.length-1];
		}
		return "png";
	}
	
	@Override
	protected void initImages()
	{
		try {
			String image_extentions = getImageExtentions();
			// 导出图片格式为整图
			if (isGroup()) {
				System.out.println("initImages group : " + img.getName());
				byte[] idata = set.getOutput().loadRes(
						img.getName() + "." + image_extentions, null);
				Image src = Engine.getEngine().createImage(new ByteArrayInputStream(idata));
				IPalette palette = this.getPalette();
				if (palette != null) {
					src.setPalette(palette);
				}
				for (int i = 0; i < images.length; i++) {
					if (img.getClipW(i) > 0 && img.getClipH(i) > 0) {
						images[i] = src.subImage(
								img.getClipX(i), 
								img.getClipY(i), 
								img.getClipW(i), 
								img.getClipH(i));
					}
				}
			}
			// 导出图片格式为碎图
			else if (isTile()) {
				System.out.println("initImages tiles : " + img.getName());
				IPalette palette = this.getPalette();
				for (int i = 0; i < images.length; i++) {
					if (img.getClipW(i) > 0 && img.getClipH(i) > 0) {
						byte[] idata = set.getOutput().loadRes(
								img.getName() + "/" + i + "." + image_extentions, null);
						images[i] = Engine.getEngine().createImage(new ByteArrayInputStream(idata));
						if (palette != null) {
							images[i].setPalette(palette);
						}
					}
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}
