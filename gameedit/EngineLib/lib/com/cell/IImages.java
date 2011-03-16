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
package com.cell;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * 最底层的对图片的处理。 里面包含了一组图片的集合。 
 * 可以把一个大Image作为原图切成若干块小Image，也可以使用若干Image.
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public interface IImages {
	
	/** 不翻转 */
	final public static byte TRANS_NONE = 0;
	/** 水平翻转 */
	final public static byte TRANS_H = 2;
	/** 垂直翻转 */
	final public static byte TRANS_V = 1;
	/** 180度翻转 */
	final public static byte TRANS_HV = 3;
	/** 逆时针90度翻转 */
	final public static byte TRANS_90 = 6;
	/** 逆时针270度翻转 */
	final public static byte TRANS_270 = 5;
	/** 先逆时针90度翻转，然后在水平翻转 */
	final public static byte TRANS_H90 = 4;
	/** 先逆时针90度翻转，然后在垂直翻转 */
	final public static byte TRANS_V90 = 7;
	/** 180度翻转 */
	final public static byte TRANS_180 = 3; // 180 = HV


	//--------------------------------------------------------------------------------------------------------

	
	/**
	 * 重新创建图片组
	 * @param srcImage 原图片
	 * @param count 数量
	 * @return 
	 */
	public void buildImages(Image srcImage,int count);

//	/**
//	 * 按照序号得到图片对象
//	 * @param index
//	 * @return 
//	 */
//	public Image getSrcImage();
//	
//	/**
//	 * 得到子图片
//	 * @param index
//	 * @return
//	 */
//	public Image getImage(int index);
	
	/**
	 * @param index
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPixel(int index, int x,int y);
	
	/**
	 * 得到子图的宽度
	 * @param Index 子图序号
	 * @return 宽度
	 */
	public int getWidth(int Index);

	/**
	 * 得到子图的高度
	 * @param Index 子图序号
	 * @return 高度
	 */
	public int getHeight(int Index);

	/**
	 * 得到图片组图片数量
	 * @return 
	 */
	public int getCount();

	/**
	 * 把原图整张的添加到组里。
	 * @return 是否未装满
	 */
	public boolean addTile();

	/**
	 * 把原图的一个区域切成小图，放进组里。
	 * @param TileX 切原图的x起点
	 * @param TileY 切原图的y起点
	 * @param TileWidth 切原图的宽
	 * @param TileHeight  切原图的高
	 * @return false 是否未装满
	 */
	public boolean addTile(int TileX, int TileY, int TileWidth, int TileHeight);

	/**
	 * 把原图按照网格切成若干小图，放进组里。
	 * @param ClipX 切原图的x起点
	 * @param ClipY 切原图的y起点
	 * @param ClipWidth 切原图的整个宽
	 * @param ClipHeight 切原图的整个高
	 * @param TileWidth 每块的宽
	 * @param TileHeight 每块的高
	 */
	public void addTile(int ClipX, int ClipY, int ClipWidth, int ClipHeight, int TileWidth, int TileHeight) ;

	/**
	 * 绘制到指定的Graphics上。
	 * @param g 指定的graphics
	 * @param Index 序号
	 * @param PosX X坐标
	 * @param PosY Y坐标
	 */
	public void render(Graphics g, int Index, int PosX, int PosY);

	/**
	 * 绘制到指定的Graphics上。
	 * @param g 指定的graphics
	 * @param Index 序号
	 * @param PosX X坐标
	 * @param PosY Y坐标
	 * @param Style 翻转方式 
	 */
	public void render(Graphics g, int Index, int PosX, int PosY, int Style) ;

}
