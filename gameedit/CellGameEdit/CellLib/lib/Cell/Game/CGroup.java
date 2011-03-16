package Cell.Game;

import Cell.CObject;

abstract public class CGroup extends CObject {

	protected int Frames[][];
	
	protected int SubIndex; //当前子精灵标号
	protected int SubCount; //组合精灵的子精灵数
	
	protected short w_left = 0;
	protected short w_top = 0;
	protected short w_botton = 0;
	protected short w_right = 0;
	
	
	protected void fixArea(int left, int top, int right, int botton) {
		if (left < w_left)
			w_left = (short) left;
		if (top < w_top)
			w_top = (short) top;
		if (right > w_right)
			w_right = (short) right;
		if (botton > w_botton)
			w_botton = (short) botton;
	}
	
	public void setFrame(int[][] frames){
		Frames = frames;
	}
	
	public void setComboFrame(int[] frame,int index){
		Frames[index] = frame;
	}
	
	public int getCount(){
		return Frames.length;
	}
	
}
