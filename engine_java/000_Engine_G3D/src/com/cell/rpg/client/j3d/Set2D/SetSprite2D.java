package com.cell.rpg.client.j3d.Set2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

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
import com.cell.rpg.client.j3d.ActorImpl;
import com.cell.rpg.client.j3d.Util;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;

public class SetSprite2D extends ActorImpl
{
	
	protected Shape3D Shape;
	protected Texture2D[] Textures;
	protected int AnimIndex;
	
	public SetSprite2D(String name, SetInput.TImages ts, SetInput.TSprite ss) throws IOException
	{
		super(name);
		
		Shape3D shape = new Shape3D();
		{
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream(ts.FileName));
			
			BufferedImage images[] = new BufferedImage[ts.ClipsX.length];
			for (int i=0; i<images.length; i++) {
				if (ts.ClipsW[i]!=0 && ts.ClipsH[i]!=0) {
					images[i] = img.getSubimage(ts.ClipsX[i], ts.ClipsY[i], ts.ClipsW[i], ts.ClipsH[i]);
				}
			}
			
			Texture2D[] textures = new Texture2D[images.length];
			for (int i=0; i<images.length; i++)
			{
				if (images[i]!=null)
				{
					TextureLoader texLoader = new TextureLoader(images[i], 
			    			TextureLoader.GENERATE_MIPMAP | 
			    			TextureLoader.BY_REFERENCE
			    			);
			    	Texture2D texture = (Texture2D)texLoader.getTexture();
		    	    ImageComponent2D imageComp = (ImageComponent2D)texture.getImage(0);
		    	    BufferedImage bImage = imageComp.getImage();
		    	    bImage = Util.convertImage(bImage, BufferedImage.TYPE_4BYTE_ABGR);
		    	    Util.flipImage(bImage);
		    	    imageComp.set(bImage);
		    	    //texture.setBoundaryModeS(Texture.CLAMP);
		    	    //texture.setBoundaryModeT(Texture.CLAMP);
		    	    //texture.setBoundaryColor(1.0f, 1.0f, 1.0f, 1.0f);
		    	    texture.setImage(0, imageComp);
		    	    
			    	textures[i] = texture;
				}
			}
		
			shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
			{
			    float[] verts = new float[] {
			    		0, 0, 0,
			    		1, 0, 0,
			    		1, 1, -1,
			    		0, 1, -1, 
				};
	
			    float[] texturecords = new float[]{
			    		0, 1, 
			    		1, 1, 
			    		1, 0, 
			    		0, 0, 
				};
				
			    GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
				geom.setCoordinates(verts);
				
		    	Appearance appearance = new Appearance();
		    	
		    	{
			        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		    	    appearance.setTexture(textures[0]);
					
					TextureAttributes texAttr = new TextureAttributes();
					texAttr.setTextureMode(TextureAttributes.MODULATE);
					appearance.setTextureAttributes(texAttr);	
					geom.setTextureCoordinateParams(1, 2); // set up tex coords
					geom.setTextureCoordinates(0, texturecords);
		    	}
		    	
		    	{
					TransparencyAttributes ta = new TransparencyAttributes( );
					ta.setTransparencyMode( TransparencyAttributes.BLENDED );
					ta.setTransparency(0f);	// 1.0f is totally transparent
					appearance.setTransparencyAttributes( ta );
				} 
		    	
				{
					 Color3f write = new Color3f(1f, 1f, 1f);
					 Material wMat= new Material(write, write, write, write, 25.0f);
					 wMat.setLightingEnable(true);
					 //appearance.setMaterial(wMat);
				}
				
				{
					PolygonAttributes pa = new PolygonAttributes( );
					pa.setCullFace( PolygonAttributes.CULL_NONE );
					pa.setPolygonMode( PolygonAttributes.POLYGON_LINE );
					//appearance.setPolygonAttributes( pa );
				}
				
			    // use GeometryInfo to compute normals
				
				
				NormalGenerator norms = new NormalGenerator();
				norms.generateNormals(geom);
				shape.setGeometry(geom.getGeometryArray()); // back to geo array
				shape.setAppearance(appearance);
	
			}
			
			
			Shape = shape;
			Textures = textures;
		}
		
		init(shape);
	
		
		
	}
	
	public void update() 
	{
		super.update();
		
		Shape.getAppearance().setTexture(Textures[AnimIndex]);
		AnimIndex += 1;
		AnimIndex %= Textures.length;
	}
	
	
	
}
