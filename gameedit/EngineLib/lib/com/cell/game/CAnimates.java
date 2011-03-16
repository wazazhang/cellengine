/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.cell.IImages;


/**
 * Animates contain some frame, it have a coordinate system with all part. </br>
 * every frame contian some part </br> 
 * every part is Images's index value </br>
 * every part has itself 2d coordinate x y </br>>
 * every part has itself Flip attribute </br>
 * 组合动画管理类</br>
 * 一个动画包含N帧，一帧包含N个部件，
 * 每个部件都有自己的坐标存在于这个动画的坐标系统中，
 * 每个部件的图片都是CImages图片组里的一个索引，
 * 每个部件都有自己的翻转属性</br>
 * 
 * @author yifeizhang
 * @since 2006-11-29 
 * @version 1.0
 */
public class CAnimates extends CGroup{
	protected IImages images ;
	
	protected short[] SX; //子精灵和坐标的X偏移
	protected short[] SY; //子精灵和坐标的Y偏移
	protected short[] SW; //子精灵宽
	protected short[] SH; //子精灵高
	protected short[] STileID; //子精灵TILE号
	protected byte[] SFlip; //子精灵的翻转参数

	/**
	 * Construct Animates
	 * @param partCount size of part </br> 动画的部件数量
	 * @param images reference </br> 使用的CImages图片组
	 */
	public CAnimates(int partCount,IImages images){
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
	
	/**
	 * Add all image part form construct images reference</br>
	 * 添加当前images里的所有图片到动画里，每张图片代表一帧
	 * @param center image is center </br> 是否居中
	 */
	public void addPart(boolean center) {
		for (int i = 0; i < images.getCount(); i++) {
			if (SubIndex >= SubCount) {
				println("Out Of Animate Max Count !");
				return;
			}
			if (center){
				addPart(-images.getWidth(i) / 2, 
						-images.getHeight(i) / 2,
						i, IImages.TRANS_NONE);
			}else{
				addPart(0, 0,
						i, IImages.TRANS_NONE);	
			}
		}
	}

	/**
	 * Add an image part form construct images reference</br>
	 * 添加当前images里的一个图片到一个部件并指定坐标
	 * @param px part's x coordinate </br>当前部件的x子坐标
	 * @param py part's y coordinate </br>当前部件的y子坐标
	 * @param tileid part's images index value </br>当前部件的图片编号
	 * @param trans part's flip rotate paramenter </br>当前部件的翻转属性
	 */
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
			switch(trans){
			case IImages.TRANS_NONE:
			case IImages.TRANS_H:
			case IImages.TRANS_V:
			case IImages.TRANS_HV:
				SW[SubIndex] = (short) images.getWidth(tileid);
				SH[SubIndex] = (short) images.getHeight(tileid);
				break;
			case IImages.TRANS_90:
			case IImages.TRANS_270:
			case IImages.TRANS_H90:
			case IImages.TRANS_V90:
				SW[SubIndex] = (short) images.getHeight(tileid);
				SH[SubIndex] = (short) images.getWidth(tileid);
				break;
			}
			
			fixArea(SX[SubIndex], SY[SubIndex], 
					SX[SubIndex] + SW[SubIndex],
					SY[SubIndex] + SH[SubIndex]);
			
			Frames[SubIndex] = new int[]{SubIndex};
			
			SubIndex++;
		}
	}

	//---------------------------------------------------------------------------------------------------
	public IImages getImages(){
		return images;
	}
//	/**
//	 * Get image form construct images reference</br>
//	 * 从当前的images里得到image
//	 * @param index index of construct images 
//	 * @return image
//	 */
//	public Image getImage(int index){
//		return images.getImage(index);
//	}
//	
//	/**
//	 * Get image form specify frame id and part id</br>
//	 * 指定帧号和该帧中的部件号得到图片
//	 * @param frame frame id within animates</br> 帧号
//	 * @param sub part id within frame</br>部件号
//	 * @return image
//	 */
//	public Image getFrameImage(int frame,int sub){
//		return images.getImage(STileID[Frames[frame][sub]]);
//	}
	
	/**
	 * Draw one frame with specify frame id</br>
	 * 渲染一帧
	 * @param g	graphics surface 
	 * @param index frame id </br>帧号
	 * @param x x on graphics surface</br>x坐标
	 * @param y y on graphics surface</br>y坐标
	 */
	public void render(Graphics g,int index,int x,int y){
		for(int i=Frames[index].length-1;i>=0;i--){
			images.render(g,
					STileID[Frames[index][i]], 
					x + SX[Frames[index][i]], //
					y + SY[Frames[index][i]], //
					SFlip[Frames[index][i]]);
		}
	}
	
	/**
	 * Draw one part with specify frame id and part id</br>
	 * 渲染一帧中的一个部件
	 * @param g graphics surface 
	 * @param index frame id </br> 帧号
	 * @param part part id </br> 部件号
	 * @param x x on graphics surface </br> x坐标
	 * @param y y on graphics surface </br> y坐标
	 */
	public void renderSub(Graphics g,int index,int part,int x,int y){
		images.render(g,
				STileID[Frames[index][part]], 
				x + SX[Frames[index][part]], //
				y + SY[Frames[index][part]], //
				SFlip[Frames[index][part]]);

	}
	
	/**
	 * Draw one frame with specify frame id ignore part's coordinate, 
	 * all part based zero point.</br>
	 * 渲染一帧，忽略该帧图片的坐标
	 * 
	 * @param g graphics surface
	 * @param index frame id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	public void renderSingle(Graphics g,int index,int x,int y){
		for(int i=Frames[index].length-1;i>=0;i--){
			images.render(g,
					STileID[Frames[index][i]], 
					x , //
					y , //
					SFlip[Frames[index][i]]);
		}
	}
	
	/**
	 * Draw one part with specify frame id and part id ignore part's coordinate, 
	 * part based zero point.</br>
	 * 渲染一帧中的一个部件，忽略该帧的图片坐标
	 * 
	 * @param g graphics surface
	 * @param index frame id
	 * @param part part id
	 * @param x x on graphics surface
	 * @param y y on graphics surface
	 */
	public void renderSingleSub(Graphics g,int index,int part,int x,int y){
		images.render(g,
				STileID[Frames[index][part]], 
				x , //
				y , //
				SFlip[Frames[index][part]]);

	}
}
