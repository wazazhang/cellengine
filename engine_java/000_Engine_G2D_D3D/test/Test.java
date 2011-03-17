

import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.swing.JApplet;
import javax.vecmath.Point3d;

import com.cell.game.edit.SetInput;
import com.cell.rpg.client.j3d.*;
import com.cell.rpg.client.j3d.Set2D.SetMap2D;
import com.cell.rpg.client.j3d.Set2D.SetSprite2D;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Test extends JApplet
 {
	private SimpleUniverse univ = null;

	private Canvas3D createUniverse()
	{
		// Get the preferred graphics configuration for the default screen
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		// Create a Canvas3D using the preferred configuration
		Canvas3D c = new Canvas3D(config);

		// Create simple universe with view branch
		univ = new SimpleUniverse(c);

		// create rpg world
		WorldImpl world = new WorldImpl(
				univ, 
				new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0),
				30);
		// setup background
		world.setBackground(new WorldBackground("/bg.jpg", new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0)));
		
		// setup map
		
			//TerrainImpl map = new TerrainImpl("map", true);
			//world.add(map);
			
			try {
				SetMap2D smap = new SetMap2D("smap", 
						new SetInput.TImages("/set/BattleMapTile.png", getClass().getResourceAsStream("/set/BattleMapTile.ts")),
						new SetInput.TMap(getClass().getResourceAsStream("/set/MAP_AMBUSH.ms"))
						);
				smap.setPos(0, -10);
				world.add(smap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		
		// setup camera
		
			CameraImpl cam = new CameraImpl("cam");
			world.add(cam);
		
		
		// setup actors
		{
			ActorImpl actor = new ActorImpl("actor", true){
				public void update() {
					super.update();
					float speed = 0.1f;
					if (Parent.isKeyHold(KeyEvent.VK_UP))
						move(0, -speed);
					if (Parent.isKeyHold(KeyEvent.VK_DOWN))
						move(0, +speed);
					if (Parent.isKeyHold(KeyEvent.VK_LEFT))
						move(-speed, 0);
					if (Parent.isKeyHold(KeyEvent.VK_RIGHT))
						move(+speed, 0);
				}
			};
			actor.setPos(2, 2);
			world.add(actor);
			world.remove(actor);
			world.add(actor);
			//cam.lock(actor);
			
			ActorImpl actor2 = new ActorImpl("actor2", true);
			actor2.setPos(10, 10);
			world.add(actor2);
			
			try {
				SetSprite2D spr = new SetSprite2D("spr", 
						new SetInput.TImages("/set/000_duanzhangbing.png", getClass().getResourceAsStream("/set/000_duanzhangbing.ts")),
						new SetInput.TSprite(getClass().getResourceAsStream("/set/000_duanzhangbing.ss"))
						);
				spr.setPos(-3, -3);
				world.add(spr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			world.add(Util.createActor());
		}

		
		
		
		//cam.setPos(actor.getX(), actor.getY());
		
		
		return c;
	}


	public void init() {
		add(createUniverse());
	}
	
}
