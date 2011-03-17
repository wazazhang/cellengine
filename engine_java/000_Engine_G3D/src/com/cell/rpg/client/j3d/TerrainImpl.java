package com.cell.rpg.client.j3d;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


import com.cell.rpg.client.World;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class TerrainImpl extends com.cell.rpg.client.Terrain  implements J3dNode
{
	protected BranchGroup Root;
	
	protected TransformGroup Group;
	
	private Vector3d pos = new Vector3d();
	private Transform3D t1 = new Transform3D();
	
	protected TerrainImpl(String name) {
		super(name);
	}
	
	public TerrainImpl(String name, boolean def) {
		super(name);
		init(new GridQuad(10, 10, 1, 1));
	}
	
	public TerrainImpl(String name, Shape3D shape) 
	{
		super(name);
		init(shape);
	}
	
	protected void init(Shape3D shape)
	{
		Group = new TransformGroup();
		Group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Group.addChild(shape);

		Root = new BranchGroup();
		Root.setCapability(BranchGroup.ALLOW_DETACH);
		Root.addChild(Group);
		Root.compile();
	}
	
	public WorldImpl getParent() {
		return (WorldImpl)Parent;
	}
	
	public void added(World world) {
		getParent().SceneRoot.addChild(Root);
	}
	
	public void removed(World world) {
		getParent().SceneRoot.removeChild(Root);
	}
	
	public void update() {}

	
	
	public double getWidth() {
		return 0;
	}
	
	public double getHeight() {
		return 0;
	}

	
	public double getX(){
		return pos.x;
	}
	
	public double getY(){
		return pos.z;
	}
	
	public void setPos(double x, double y){
		pos.z = y;
		pos.x = x;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	public void move(float dx, float dy){
		pos.z += dy;
		pos.x += dx;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	public void moveTo(float speed, float degree){
		pos.z += Math.sin(degree) * speed;
		pos.x += Math.cos(degree) * speed;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected class GridQuad extends Shape3D
	{
	    private float[] verts;
	    private Color3f[] colors;
	    
	    final public int MapWidth;
	    final public int MapHeight;
	    final public float CellWidth;
	    final public float CellHeight;
	    
	    final public float Width;
	    final public float Height;
	    
		public GridQuad(int mapw, int maph, float cellw, float cellh) 
		{
			MapWidth = mapw;
			MapHeight = maph;
			CellWidth = cellw;
			CellHeight = cellh;

		    Width = mapw * cellw;
		    Height = maph * cellh;
		    
		    {
				verts = new float[MapWidth * MapHeight * 12];
				colors = new Color3f[MapWidth * MapHeight * 4];
				
				for (int w=0; w<MapWidth; ++w) 
				{
					for (int h=0; h<MapHeight; ++h) 
					{
						float mx = w * cellw;
						float my = h * cellh;
						
						System.arraycopy(new float[]{
								 mx        , 0, my + cellh,
								 mx + cellw, 0, my + cellh,
								 mx + cellw, 0, my,
								 mx        , 0, my,
						}, 0, verts, (h * MapWidth + w) * 12, 12);
						
						Color3f color = new Color3f(0,  0,  0 );
						if (w%2==h%2){
							color = new Color3f(1,  1,  1 );
						}
						System.arraycopy(new Color3f[]{
								color,
								color,
								color,
								color,
						}, 0, colors, (h * MapWidth + w) * 4, 4);
						
					}
				}
	

				Appearance appearance = new Appearance();
		    	
				{
					PolygonAttributes pa = new PolygonAttributes( );
					pa.setCullFace( PolygonAttributes.CULL_NONE );
					pa.setPolygonMode( PolygonAttributes.POLYGON_LINE );
					//appearance.setPolygonAttributes( pa );
				}
				
				GeometryInfo geom = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
				geom.setCoordinates(verts);
				geom.setColors(colors);
				NormalGenerator norms = new NormalGenerator();
				norms.generateNormals(geom);
				this.setGeometry(geom.getGeometryArray()); // back to geo array
				
				this.setAppearance(appearance);
				
		    }
		    

		}


	}
	
	
	
}
