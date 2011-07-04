 namespace Cell.Game{

using java.util.Vector;

using javax.microedition.lcdui.Graphics;

using Cell.CObject;


public class CWorld : CObject {

	private Vector ZBuffer;
	
	protected CMap Map;
	protected CCamera Camera;
	protected CSprite[] Sprs ;
	
	public CWorld(CMap map,CCamera camera,CSprite[] sprs) {
		Map = map;
		Camera = camera;
		Sprs = sprs;
		
		Map.world = this;
		Camera.world = this;
		for(int i=0;i<Sprs.length;i++){
			Sprs[i].world = this;
			if(Sprs[i].mapcd==null){
				Sprs[i].mapcd = CCD.createCDRect(0, -4, -4, 8, 8);
			}
		}
//		ZBuffer = new Vector(sprs.length);
	}


	public CSprite getSprite(int index){
		if(index<Sprs.length)
		return Sprs[index];
		else return null;
	}
	
	public CMap getMap(){
		return Map;
	}
	
	public CCamera getCamera(){
		return Camera;
	}
	
	public void render(Graphics g){
		int cx = g.getClipX();
		int cy = g.getClipY();
		int cw = g.getClipWidth();
		int ch = g.getClipHeight();
    	g.setClip(Camera.WindowX,Camera.WindowY,Camera.getWidth(),Camera.getHeight());
    	
		Camera.render(g);
		
		for(int i=0;i<Sprs.length;i++){
			if(Sprs[i].Visible && CCD.cdRect(
					Sprs[i].X + Sprs[i].animates.w_left, 
					Sprs[i].Y + Sprs[i].animates.w_top, 
					Sprs[i].animates.w_right  - Sprs[i].animates.w_left, 
					Sprs[i].animates.w_botton - Sprs[i].animates.w_top, 
					Camera.X, 
					Camera.Y, 
					Camera.getWidth(), 
					Camera.getHeight())){
				Sprs[i].OnScreen = true;
				Sprs[i].render(g,
						Sprs[i].X-Camera.X+Camera.WindowX,
						Sprs[i].Y-Camera.Y+Camera.WindowY);
			} else {
				Sprs[i].OnScreen = false;
			}
		}
		g.setClip(cx,cy,cw,ch);
	}
	
	
	
	

}
 }