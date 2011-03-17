package com.cell.rpg.client.j3d;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

import com.cell.game.edit.SetInput;
import com.cell.rpg.client.Actor;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;



public class Util 
{
	protected class GridMap extends Shape3D
	{
	    private float[] verts;
	    private float[] colors;
	    private float[] texturecords;
	    
	    final public int MapWidth;
	    final public int MapHeight;
	    final public float CellWidth;
	    final public float CellHeight;
	    
	    final public float Width;
	    final public float Height;
	    
		public GridMap(int mapw, int maph, float cellw, float cellh) 
		{
			MapWidth = mapw;
			MapHeight = maph;
			CellWidth = cellw;
			CellHeight = cellh;

		    Width = mapw * cellw;
		    Height = maph * cellh;
		    
		    {
				int count = MapWidth * MapHeight * 12;
				verts = new float[count];
				colors = new float[count];
				texturecords = new float[MapWidth * MapHeight * 8];
				
				for (int w=0; w<MapWidth; ++w) {
					for (int h=0; h<MapHeight; ++h) {
						int i = (h * MapWidth + w) * 12;
						float mx = w * cellw;
						float my = h * cellh;
						System.arraycopy(new float[]{
								 mx        , 0, my + cellh,
								 mx + cellw, 0, my + cellh,
								 mx + cellw, 0, my,
								 mx        , 0, my,
						}, 0, verts, i, 12);
						System.arraycopy(new float[]{
								 1f, 0, 0,
								 0, 1f, 0,
								 0, 0, 1f,
								 1f, 1f, 1f,
						}, 0, colors, i, 12);
						
						int ti = (h * MapWidth + w) * 8;
						System.arraycopy(new float[]{
								0, 0,
								1, 0,
								1, 1,
								0, 1,
						}, 0, texturecords, ti, 8);
						
					}
				}
	

		    	Appearance appearance = new Appearance();
		    	
				try {
					BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/tiles.png"));
					TextureLoader texLoader = new TextureLoader(img, TextureLoader.GENERATE_MIPMAP);
					appearance.setTexture(texLoader.getTexture());
					TextureAttributes texAttr = new TextureAttributes();
					texAttr.setTextureMode(TextureAttributes.MODULATE);
					appearance.setTextureAttributes(texAttr);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			    Color3f write = new Color3f(1f, 1f, 1f);
			    Material wMat= new Material(write, write, write, write, 25.0f);
			    wMat.setLightingEnable(true);
			    //appearance.setMaterial(wMat);
			    
			    
			    // use GeometryInfo to compute normals
				GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
				geom.setCoordinates(verts);
				geom.setTextureCoordinateParams(1, 2); // set up tex coords
				geom.setTextureCoordinates(0, texturecords);
				NormalGenerator norms = new NormalGenerator();
				norms.generateNormals(geom);
				setGeometry(geom.getGeometryArray()); // back to geo array

				this.setAppearance(appearance);
				
		    }
		    

		}


	}
	
	
	public static Actor createActor() 
	{
		String imageFile = "/set/000_duanzhangbing.png";
		String tsFile = "/set/000_duanzhangbing.ts";
		String ssFile = "/set/000_duanzhangbing.ss";
		
		try 
		{
			BufferedImage img = ImageIO.read(imageFile.getClass().getResourceAsStream(imageFile));
			SetInput.TImages ts = new SetInput.TImages(imageFile, tsFile.getClass().getResourceAsStream(tsFile));
			SetInput.TSprite ss = new SetInput.TSprite(ssFile.getClass().getResourceAsStream(ssFile));
			
			BufferedImage images[] = new BufferedImage[ts.ClipsX.length];
			for (int i=0; i<images.length; i++) {
				if (ts.ClipsW[i]!=0 && ts.ClipsH[i]!=0) {
					images[i] = img.getSubimage(ts.ClipsX[i], ts.ClipsY[i], ts.ClipsW[i], ts.ClipsH[i]);
				}
			}
			
			// test
			images = new BufferedImage[]{
	    			ImageIO.read(imageFile.getClass().getResourceAsStream("/anim/1.png")),
	    			ImageIO.read(imageFile.getClass().getResourceAsStream("/anim/2.png")),
	    			ImageIO.read(imageFile.getClass().getResourceAsStream("/anim/3.png")),
	    	};
			final Texture2D[] textures = new Texture2D[images.length];
			for (int i=0; i<images.length; i++)
			{
				TextureLoader texLoader = new TextureLoader(images[i], 
		    			TextureLoader.GENERATE_MIPMAP | 
		    			TextureLoader.BY_REFERENCE |
		    			TextureLoader.Y_UP
		    			);
		    	Texture2D texture = (Texture2D)texLoader.getTexture();
	    	    ImageComponent2D imageComp = (ImageComponent2D)texture.getImage(0);
	    	    BufferedImage bImage = imageComp.getImage();
	    	    bImage = convertImage(bImage, BufferedImage.TYPE_4BYTE_ABGR);
	    	    flipImage(bImage);
	    	    imageComp.set(bImage);
	    	    texture.setBoundaryModeS(Texture.CLAMP);
	    	    texture.setBoundaryModeT(Texture.CLAMP);
	    	    texture.setBoundaryColor(1.0f, 1.0f, 1.0f, 1.0f);
	    	    texture.setImage(0, imageComp);
	    	    
		    	textures[i] = texture;
			}
			// test
			
			final Shape3D shape = new Shape3D();
			shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
			{	
			    float[] verts = new float[]{
			    		0, 0, 0,
			    		1, 0, 0,
			    		1, 1, -1,
			    		0, 1, -1, 
				};

			    float[] texturecords = new float[]{
			    		0, 0, 
			    		1, 0, 
			    		1, 1, 
			    		0, 1, 
				};
				
		    	Appearance appearance = new Appearance();
		    	
		    	{
		    		// enable the TEXTURE_WRITE so we can modify it at runtime
			        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		    	    appearance.setTexture(textures[0]);
					
					TextureAttributes texAttr = new TextureAttributes();
					texAttr.setTextureMode(TextureAttributes.MODULATE);
					appearance.setTextureAttributes(texAttr);	
		    	}
		    	
				{
					 Color3f write = new Color3f(1f, 1f, 1f);
					 Material wMat= new Material(write, write, write, write, 25.0f);
					 wMat.setLightingEnable(true);
					 //appearance.setMaterial(wMat);
				}
				
				{
					TransparencyAttributes ta = new TransparencyAttributes( );
					ta.setTransparencyMode( TransparencyAttributes.BLENDED );
					ta.setTransparency(0f);	// 1.0f is totally transparent
					appearance.setTransparencyAttributes( ta );
				} 
				{
					PolygonAttributes pa = new PolygonAttributes( );
					pa.setCullFace( PolygonAttributes.CULL_NONE );
					pa.setPolygonMode( PolygonAttributes.POLYGON_LINE );
					//appearance.setPolygonAttributes( pa );
				}
				
			    // use GeometryInfo to compute normals
				GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
				geom.setCoordinates(verts);
				geom.setTextureCoordinateParams(1, 2); // set up tex coords
				geom.setTextureCoordinates(0, texturecords);
				NormalGenerator norms = new NormalGenerator();
				norms.generateNormals(geom);
				shape.setGeometry(geom.getGeometryArray()); // back to geo array
				
				shape.setAppearance(appearance);

			}
			
			ActorImpl actor = new ActorImpl("000_duanzhangbing", shape){
				int anim = 0;
				public void update() {
					super.update();
					shape.getAppearance().setTexture(textures[anim]);
					anim += 1;
					anim %= 3;
				}
			};
			
			return actor;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

	  // flip the image
	public static void flipImage(BufferedImage bImage) {
		int width = bImage.getWidth();
		int height = bImage.getHeight();
		int[] rgbArray = new int[width * height];
		bImage.getRGB(0, 0, width, height, rgbArray, 0, width);
		int[] tempArray = new int[width * height];
		int y2 = 0;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				tempArray[y2 * width + x] = rgbArray[y * width + x];
			}
			y2++;
		}
		bImage.setRGB(0, 0, width, height, tempArray, 0, width);
	}

	// convert the image to a specified BufferedImage type and return it
	public static BufferedImage convertImage(BufferedImage bImage, int type) {
		int width = bImage.getWidth();
		int height = bImage.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, type);
		int[] rgbArray = new int[width * height];
		bImage.getRGB(0, 0, width, height, rgbArray, 0, width);
		newImage.setRGB(0, 0, width, height, rgbArray, 0, width);
		return newImage;
	}

	// print out some of the types of BufferedImages
	static void printType(BufferedImage bImage) {
		int type = bImage.getType();
		if (type == BufferedImage.TYPE_4BYTE_ABGR) {
			System.out.println("TYPE_4BYTE_ABGR");
		} else if (type == BufferedImage.TYPE_INT_ARGB) {
			System.out.println("TYPE_INT_ARGB");
		} else if (type == BufferedImage.TYPE_3BYTE_BGR) {
			System.out.println("TYPE_3BYTE_BGR");
		} else if (type == BufferedImage.TYPE_CUSTOM) {
			System.out.println("TYPE_CUSTOM");
		} else
			System.out.println(type);
	}

	public static BufferedImage convertToCustomRGBA(BufferedImage bImage) {
		if (bImage.getType() != BufferedImage.TYPE_INT_ARGB) {
			convertImage(bImage, BufferedImage.TYPE_INT_ARGB);
		}

		int width = bImage.getWidth();
		int height = bImage.getHeight();

		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		int[] nBits = { 8, 8, 8, 8 };
		ColorModel cm = new ComponentColorModel(cs, nBits, true, false,
				Transparency.OPAQUE, 0);
		int[] bandOffset = { 0, 1, 2, 3 };

		WritableRaster newRaster = Raster.createInterleavedRaster(
				DataBuffer.TYPE_BYTE, width, height, width * 4, 4, bandOffset,
				null);
		byte[] byteData = ((DataBufferByte) newRaster.getDataBuffer())
				.getData();
		Raster origRaster = bImage.getData();
		int[] pixel = new int[4];
		int k = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				pixel = origRaster.getPixel(i, j, pixel);
				byteData[k++] = (byte) (pixel[0]);
				byteData[k++] = (byte) (pixel[1]);
				byteData[k++] = (byte) (pixel[2]);
				byteData[k++] = (byte) (pixel[3]);
			}
		}
		BufferedImage newImage = new BufferedImage(cm, newRaster, false, null);
		//	     if (newImage.getType() == BufferedImage.TYPE_CUSTOM) {
		//	       System.out.println("Type is custom");
		//	     }
		return newImage;
	}

	public static BufferedImage convertToCustomRGB(BufferedImage bImage) {
		if (bImage.getType() != BufferedImage.TYPE_INT_ARGB) {
			convertImage(bImage, BufferedImage.TYPE_INT_ARGB);
		}

		int width = bImage.getWidth();
		int height = bImage.getHeight();

		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		int[] nBits = { 8, 8, 8 };
		ColorModel cm = new ComponentColorModel(cs, nBits, false, false,
				Transparency.OPAQUE, 0);
		int[] bandOffset = { 0, 1, 2 };

		WritableRaster newRaster = Raster.createInterleavedRaster(
				DataBuffer.TYPE_BYTE, width, height, width * 3, 3, bandOffset,
				null);
		byte[] byteData = ((DataBufferByte) newRaster.getDataBuffer())
				.getData();
		Raster origRaster = bImage.getData();
		int[] pixel = new int[4];
		int k = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				pixel = origRaster.getPixel(i, j, pixel);
				byteData[k++] = (byte) (pixel[0]);
				byteData[k++] = (byte) (pixel[1]);
				byteData[k++] = (byte) (pixel[2]);
			}
		}
		BufferedImage newImage = new BufferedImage(cm, newRaster, false, null);
		//	     if (newImage.getType() == BufferedImage.TYPE_CUSTOM) {
		//	       System.out.println("Type is custom");
		//	     }
		return newImage;
	}
	


	
}
