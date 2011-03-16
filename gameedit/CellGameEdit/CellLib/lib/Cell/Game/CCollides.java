package Cell.Game;

import javax.microedition.lcdui.Graphics;
import Cell.CObject;

public class CCollides extends CGroup{
	protected CCD cds[];

	public CCollides(int cdCount){
		
		SubIndex = 0;
		SubCount = (short)cdCount;
		cds = new CCD[cdCount];
		
		Frames = new int[cdCount][];
	}
	
	
	
	public void addCDRect(int mask, int x, int y, int w, int h) {
		addCD(CCD.createCDRect(mask, x, y, w, h));
	}
	
	public void addCD(CCD cd) {
		if (SubIndex >= SubCount) {
			println("Out of Max CD Count !");
			return;
		}
		cds[SubIndex] = cd;
		if(cd!=null){
			fixArea(cd.X, cd.Y, 
					cd.X + cd.W,
					cd.Y + cd.H);
		}
		
		Frames[SubIndex] = new int[]{SubIndex};
		SubIndex++;
	}

	public CCD getCD(int index){
		return cds[index];
	}
	
	public void render(Graphics g,int index,int x,int y,int color){
		for(int i=Frames[index].length-1;i>=0;i--){
			cds[Frames[index][i]].render(g, x, y, color);
		}
		
	}
}
