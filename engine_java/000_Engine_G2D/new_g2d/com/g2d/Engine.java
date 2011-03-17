package com.g2d;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedString;

import com.cell.gfx.IPalette;
import com.g2d.font.GraphicAttribute;
import com.g2d.geom.AffineTransform;
import com.g2d.text.Instruction;
import com.g2d.text.MultiTextLayout;
import com.g2d.text.TextLayout;

public abstract class Engine 
{
	protected static Engine instance;
	public static Engine getEngine() {
		return instance;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	
	abstract public Font				createFont(String name, int style, int size);
	
	
	abstract public AnimateCursor 		createCursor(Image[] cursor, int spot_x, int spot_y, String name);

	abstract public AnimateCursor 		getCursor(String name);
	
	
	abstract public IPalette			createPalette(InputStream is) throws IOException;
	
	abstract public IPalette			createPalette(byte[] data, short color_count, short transparent_color_index);
	
	abstract public BufferedImage 		createImage(int w, int h);
	
	abstract public BufferedImage 		createImage(InputStream is) throws IOException;
	
	abstract public void 				encodeImage(OutputStream out, Image src, String type) throws Exception;
	

	
	abstract public void 				setClipboardText(String str);
	
	abstract public String 				getClipboardText();
	
	
	
	abstract public TextLayout			createTextLayout(AttributedString text);

	abstract public MultiTextLayout		createMultiTextLayout();
	
}
