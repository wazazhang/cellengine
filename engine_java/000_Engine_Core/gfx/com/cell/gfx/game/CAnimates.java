package com.cell.gfx.game;


import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;


/**
 * Animates contain some frame, it have a coordinate system with all part. </br>
 * every frame contian some part </br> 
 * every part is Images's index value </br>
 * every part has itself 2d coordinate x y </br>>
 * every part has itself Flip attribute </br>
 * @author yifeizhang
 * @since 2006-11-29 
 * @version 1.0
 */
public class CAnimates extends CGroup
{
	private static final long serialVersionUID = 1L;
	
	protected IImages images ;
	
	protected short[] SX;
	protected short[] SY;
	protected short[] SW;
	protected short[] SH;
	protected short[] STileID;
	protected byte[] SFlip;
	
	/**
	 * Construct Animates
	 * @param partCount size of part </br>
	 * @param images reference </br>
	 */
	public CAnimates(int partCount,IImages images){
		this.images = images;
		SubCount = (short) partCount;
		SubIndex = 0;
		
		STileID = new short[partCount];
		SFlip = new byte[partCount];
		
		SW = new short[partCount];
		SH = new short[partCount];
		SX = new short[partCount];
		SY = new short[partCount];
		
		Frames = new short[partCount][];
	}
	
	protected CAnimates()
	{
		
	}
	
	public CAnimates clone()
	{
		CAnimates ret = new CAnimates();
		
		ret.images = this.images;
		
		ret.SX = this.SX.clone();
		ret.SY = this.SY.clone();
		ret.SW = this.SW.clone();
		ret.SH = this.SH.clone();
		ret.STileID = this.STileID.clone();
		ret.SFlip = this.SFlip.clone();	
		
		// CGroup
		ret.Frames = this.Frames.clone();
		
		ret.SubIndex = this.SubIndex;
		ret.SubCount = this.SubCount;
		
		ret.w_left = this.w_left;
		ret.w_top = this.w_top;
		ret.w_bottom = this.w_bottom;
		ret.w_right = this.w_right;
		ret.w_width = this.w_width;
		ret.w_height = this.w_height;		
		
		return ret;
	}
	
	/**
	 * Add all image part form construct images reference</br>
	 * @param center image is center </br>�1�7
	 */
	public void addPart(boolean center) {
		for (int i = 0; i < images.getCount(); i++) {
			if (SubIndex >= SubCount) {
				System.err.println("Out Of Animate Max Count !");
				return;
			}
			if (center){
				addPart(-images.getWidth(i) / 2, 
						-images.getHeight(i) / 2,
						i, IImage.TRANS_NONE);
			}else{
				addPart(0, 0,
						i, IImage.TRANS_NONE);	
			}
		}
	}

	/**
	 * Add an image part form construct images reference</br>�1�7
	 * @param px part's x coordinate </br>�1�7
	 * @param py part's y coordinate </br>�1�7
	 * @param tileid part's images index value </br>
	 * @param trans part's flip rotate paramenter
	 * 	 */
	public void addPart(int px, int py, int tileid, int trans) {
		if (SubIndex >= SubCount) {
			System.err.println("Out Of Animate Max Count !");
			return;
		}
		if (SubIndex < SubCount) {
			STileID[SubIndex] = (short) tileid;
			SW[SubIndex] = (short) images.getWidth(tileid);
			SH[SubIndex] = (short) images.getHeight(tileid);
			SX[SubIndex] = (short) px;
			SY[SubIndex] = (short) py;
			SFlip[SubIndex] = (byte) trans;
			switch(trans){
			case IImage.TRANS_NONE:
			case IImage.TRANS_H:
			case IImage.TRANS_V:
			case IImage.TRANS_HV:
				SW[SubIndex] = (short) images.getWidth(tileid);
				SH[SubIndex] = (short) images.getHeight(tileid);
				break;
			case IImage.TRANS_90:
			case IImage.TRANS_270:
			case IImage.TRANS_H90:
			case IImage.TRANS_V90:
				SW[SubIndex] = (short) images.getHeight(tileid);
				SH[SubIndex] = (short) images.getWidth(tileid);
				break;
			}
			
			fixArea(SX[SubIndex], SY[SubIndex], 
					SX[SubIndex] + SW[SubIndex],
					SY[SubIndex] + SH[SubIndex]);
			
			Frames[SubIndex] = new short[]{(short)SubIndex};
			
			SubIndex++;
		}
	}

	//---------------------------------------------------------------------------------------------------
	public IImages getImages(){
		return images;
	}
	
	public IImages setImages(IImages images) {
		IImages ori = this.images;
		this.images = images;		
		return ori;
	}
	
	/**
	 * Get image form construct images reference</br>
	 * @param index index of construct images 
	 * @return image
	 */
	public IImage getImage(int index){
		return images.getImage(index);
	}
	
	/**
	 * Get image form specify frame id and part id</br>
	 * @param frame frame id within animates</br>
	 * @param sub part id within frame</br>
	 * @return image
	 */
	public IImage getFrameImage(int frame,int sub){
		return images.getImage(STileID[Frames[frame][sub]]);
	}
	
	public int getFrameX(int frame,int sub){
		return SX[Frames[frame][sub]];
	}
	public int getFrameY(int frame,int sub){
		return SY[Frames[frame][sub]];
	}
	
	public int getFrameW(int frame,int sub) {
		return SW[Frames[frame][sub]];
	}
	
	public int getFrameH(int frame,int sub) {
		return SH[Frames[frame][sub]];
	}

	public byte getFrameTransform(int frame,int sub){
		return SFlip[Frames[frame][sub]];
	}
	
	public int[] getFrameBounds(int frame)
	{
		int left	= Integer.MAX_VALUE;
		int right	= Integer.MIN_VALUE; 
		int top		= Integer.MAX_VALUE;
		int bottom	= Integer.MIN_VALUE;
		
		for (int i=0; i<SX.length; i++)
		{
			left	= Math.min(getFrameX(frame, i), left);
			right	= Math.max(getFrameX(frame, i) + getFrameW(frame, i), right);
			top		= Math.min(getFrameY(frame, i), top);
			bottom	= Math.max(getFrameY(frame, i) + getFrameH(frame, i), bottom);
		}
		
		return new int[]{left, top, right - left, bottom - top};
	}
	
	/**
	 * Draw one frame with specify frame id</br>
	 * @param g	graphics surface 
	 * @param index frame id </br>
	 * @param x x on graphics surface</br>
	 * @param y y on graphics surface</br>
	 */
	public void render(IGraphics g,int index,int x,int y){
		for(int i=Frames[index].length-1;i>=0;i--){
			int idx = Frames[index][i];
			images.render(g,
					STileID[idx], 
					x + SX[idx], //
					y + SY[idx], //
					SFlip[idx]);
		}
	}	
		
	/**
	 * Draw one part with specify frame id and part id</br>�1�7
	 * @param g graphics surface 
	 * @param index frame id </br>
	 * @param part part id </br>
	 * @param x x on graphics surface </br>�1�7
	 * @param y y on graphics surface </br>
	 */
	public void renderSub(IGraphics g,int index,int part,int x,int y){
		int idx = Frames[index][part];
		images.render(g,
				STileID[idx], 
				x + SX[idx], //
				y + SY[idx], //
				SFlip[idx]);
	}	
	
	/**
	 * Draw one frame with specify frame id ignore part's coordinate, 
	 * all part based zero point.</br>
	 * @param g graphics surface
	 * @param index frame id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	public void renderSingle(IGraphics g,int index,int x,int y){
		for(int i=Frames[index].length-1;i>=0;i--){
			int idx = Frames[index][i];
			images.render(g,
					STileID[idx], 
					x , //
					y , //
					SFlip[idx]);
		}
	}
	
	/**
	 * Draw one part with specify frame id and part id ignore part's coordinate, 
	 * part based zero point.</br>
	 * @param g graphics surface
	 * @param index frame id
	 * @param part part id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	public void renderSingleSub(IGraphics g,int index,int part,int x,int y){
		int idx = Frames[index][part];
		images.render(g,
				STileID[idx], 
				x , //
				y , //
				SFlip[idx]);

	}
}


