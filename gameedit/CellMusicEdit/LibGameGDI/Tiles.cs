using System;
using System.Drawing;

namespace Cell.LibGame
{
	/// <summary>
	/// 
	/// </summary>
	public class Tiles
	{

		/*
		 * 翻转参数
		 */
		/**不翻转*/
		public static byte TRANS_NONE	= 0 ; 
		/**水平翻转*/
		public static byte TRANS_H 	= 2 ; 
		/**垂直翻转*/
		public static byte TRANS_V	= 1 ; 
		/**180度翻转*/
		public static byte TRANS_HV	= 3 ;
		/**逆时针90度翻转*/
		public static byte TRANS_90	= 6 ; 
		/**逆时针270度翻转*/
		public static byte TRANS_270 	= 5 ; 
		/**先逆时针90度翻转，然后在水平翻转*/
		public static byte TRANS_H90	= 4 ; 
		/**先逆时针90度翻转，然后在垂直翻转*/
		public static byte TRANS_V90	= 7 ; 
		/**180度翻转*/
		public static byte TRANS_180	= 3 ; // 180 = HV

		private int count;
		private int index;
		/**TILE块的个数*/
		private Image[] tiles;
	
		private RotateFlipType[] TransTable = 
		{
			RotateFlipType.RotateNoneFlipNone,
			RotateFlipType.RotateNoneFlipY,
			RotateFlipType.RotateNoneFlipX,
			RotateFlipType.RotateNoneFlipXY,
			RotateFlipType.Rotate90FlipX,
			RotateFlipType.Rotate270FlipNone,
			RotateFlipType.Rotate90FlipNone,
			RotateFlipType.Rotate90FlipY,
		};
	

		//--------------------------------------------------------------------------------------------------------

		/**
		 * 生成一个TileGroup
		 * @param NewTileImage 原图片
		 * @param NewTilecount 切出的图的个数
		 */
		public Tiles(int NewTilecount) 
		{
			count = NewTilecount ;
			index = 0 ;
			tiles = new Image[count];
		}


		/**
		 * 得到子图的宽度
		 * @param Index 子图序号
		 * @return 宽度
		 */
		public int GetWidth(int i) 
		{
			return tiles[i].Width;
		}
		/**
		 * 得到子图的高度
		 * @param Index 子图序号
		 * @return 高度
		 */
		public int GetHeight(int i) 
		{
			return tiles[i].Height;
		}
		/**
		 * 得到容量
		 * @return 容量
		 */
		public int getCount()
		{
			return count;
		}

		/**
		 * 把原图整张的添加到组里。
		 * @return 是否未装满
		 */
		public bool addTile(string str) 
		{
			if (index < count) 
			{
				try
				{
					//装入图片
					Image image = System.Drawing.Image.FromFile(str);
					tiles[index] = image ;
				}
				catch(System.Exception e)
				{
                    throw (e);
					//System.Windows.Forms.MessageBox.Show(e.Message.ToString());	
				}
				index++;
			}
			if (index >= count) 
			{
				return false;
			} 
			else 
			{
				return true;
			}
		}

		/**
		 * 把原图整张的添加到组里。
		 * @return 是否未装满
		 */
		public bool addTile(Image image) 
		{
			if (index < count) 
			{
				tiles[index] = image ;
				index++;
			}
			if (index >= count) 
			{
				return false;
			} 
			else 
			{
				return true;
			}
		}

	
	
		/**
		 * 把原图的一个区域切成小图，放进组里。
		 * @param TileX		切原图的x起点
		 * @param TileY		切原图的y起点
		 * @param TileWidth		切原图的宽
		 * @param TileHeight	切原图的高
		 * @return false 是否未装满
		 */
		public bool addTile(
			Image image,
			int TileX, int TileY, 
			int TileWidth, int TileHeight) 
		{
			if (index < count) 
			{
				if (TileWidth <= 0 || TileHeight <= 0) 
				{
					//System.Windows.Forms.MessageBox.Show("图块大小错误");	//
				} 
				else 
				{
                    Rectangle srcRect = new Rectangle(TileX,TileY,TileWidth,TileHeight);
                    GraphicsUnit units = GraphicsUnit.Pixel;
					tiles[index] =  new System.Drawing.Bitmap(TileWidth, TileHeight);
					Graphics g = Graphics.FromImage(tiles[index]);
                    g.DrawImage(image, 0, 0, srcRect, units);
				}
				index++;
			}
			if (index >= count) 
			{
				return false;
			} 
			else 
			{
				return true;
			}
		}

	
	
		/**
		 * 把原图按照网格切成若干小图，放进组里。
		 * @param ClipX		切原图的x起点
		 * @param ClipY		切原图的y起点
		 * @param ClipWidth		切原图的整个宽
		 * @param ClipHeight	切原图的整个高
		 * @param TileWidth		每块的宽
		 * @param TileHeight	每块的高
		 */
		public void addTile(
			Image image,
			int ClipX, int ClipY, 
			int ClipWidth, int ClipHeight,
			int TileWidth, int TileHeight) 
		{
			if (index < count) 
			{
				for (int j = 0; j < ClipHeight / TileHeight; j++) 
				{
					for (int i = 0; i < ClipWidth / TileWidth; i++) 
					{
						if (!addTile(image,ClipX + TileWidth * i, ClipY + TileHeight * j,
							TileWidth, TileHeight)) 
						{
							return;
						}
					}
				}
			}
		}
	
		/**
		 * 绘制到指定的Graphics上。
		 * @param g		指定的graphics
		 * @param Index	序号
		 * @param PosX	X坐标
		 * @param PosY	Y坐标
		 */
		public void render(System.Drawing.Graphics g,int i, int PosX, int PosY) 
		{//	不可选择对齐方式,不可旋转
			g.DrawImage(tiles[i],PosX,PosY,GetWidth(i),GetHeight(i));
		}

		/**
		 * 绘制到指定的Graphics上。
		 * @param g		指定的graphics
		 * @param Index	序号
		 * @param PosX	X坐标
		 * @param PosY	Y坐标
		 * @param Style 翻转方式，参考 CSSprite 里的翻转参数。
		 */
		public void render(System.Drawing.Graphics g,int i, int PosX, int PosY, int Style) 
		{
			Image buf = (Image)tiles[i].Clone();
			buf.RotateFlip(TransTable[Style]);
			g.DrawImage(buf,PosX,PosY,GetWidth(i),GetHeight(i));
		}


	

	}
}
