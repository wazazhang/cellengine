package Cell.Game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import Cell.CObject;

public class CAnimate extends CGroup{
	protected CImages images ;
	
	protected short[] SW; //子精灵宽
	protected short[] SH; //子精灵高
	protected short[] SX; //子精灵和坐标的X偏移
	protected short[] SY; //子精灵和坐标的Y偏移
	protected short[] STileID; //子精灵TILE号
	protected byte[] SFlip; //子精灵的翻转参数

	public CAnimate(int partCount,CImages images){
		this.images = images;
		//子精灵
		SubCount = (short) partCount;
		SubIndex = 0;
		STileID = new short[partCount];
		SFlip = new byte[partCount];
		SW = new short[partCount];
		SH = new short[partCount];
		SX = new short[partCount];
		SY = new short[partCount];
		
		Frames = new int[partCount][];
	}
	
	public void addPart(boolean center) {
		for (int i = 0; i < images.getCount(); i++) {
			if (SubIndex >= SubCount) {
				println("Out Of Animate Max Count !");
				return;
			}
			if (center){
				addPart(-images.getWidth(i) / 2, 
						-images.getHeight(i) / 2,
						i, CImages.TRANS_NONE);
			}else{
				addPart(0, 0,
						i, CImages.TRANS_NONE);	
			}
		}
	}

	public void addPart(int px, int py, int tileid, int trans) {
		if (SubIndex >= SubCount) {
			println("Out Of Animate Max Count !");
			return;
		}
		if (SubIndex < SubCount) {
			STileID[SubIndex] = (short) tileid;
			SW[SubIndex] = (short) images.getWidth(tileid);
			SH[SubIndex] = (short) images.getHeight(tileid);
			SX[SubIndex] = (short) px;
			SY[SubIndex] = (short) py;
			SFlip[SubIndex] = (byte) trans;
			fixArea(SX[SubIndex], SY[SubIndex], 
					SX[SubIndex] + SW[SubIndex],
					SY[SubIndex] + SH[SubIndex]);
			
			Frames[SubIndex] = new int[]{SubIndex};
			
			SubIndex++;
		}
	}

	public Image getImage(int index){
		return images.getImage(index);
	}
	

	
	public void render(Graphics g,int index,int x,int y){
		for(int i=Frames[index].length-1;i>=0;i--){
			images.render(g,
					STileID[Frames[index][i]], 
					x + SX[Frames[index][i]], //
					y + SY[Frames[index][i]], //
					SFlip[Frames[index][i]]);
		}
	}
	
	public void render(Graphics g,int index,int part,int x,int y){
		images.render(g,
				STileID[Frames[index][part]], 
				x + SX[Frames[index][part]], //
				y + SY[Frames[index][part]], //
				SFlip[Frames[index][part]]);

	}
	
}
