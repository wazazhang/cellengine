using System;
using System.Drawing;
using System.Collections;

namespace Cell.LibGame
{
	/// <summary>
	/// 
	/// </summary>
	public class Sprite
	{
        public int HPos;
        public int VPos;


        Tiles Tile;

        ArrayList sx = new ArrayList();
        ArrayList sy = new ArrayList();

        //ArrayList Frame = new ArrayList();
        ArrayList Animate = new ArrayList();

       // int CurFrame = 0 ;
        int CurAnimate = 0;

		public Sprite()
		{

		}

        public void addUnit(Tiles tile,Boolean center)
        {
            Tile = tile;
            for (int i = 0; i < Tile.getCount();i++ )
            {
                if (center)
                {
                    sx.Add(-Tile.GetWidth(i)/2);
                    sy.Add(-Tile.GetHeight(i) / 2);
                }
                else
                {
                    sx.Add(0);
                    sy.Add(0);
                }
                Animate.Add(i);
            }
        }

        //public void setFrame()
       // {
       // }

        public void setAnimate(int index)
        {
            if(index<Animate.Count)CurAnimate = index;
        }

        public void render(Graphics g)
        {
            Tile.render(g, (int)Animate[CurAnimate],
                HPos + (int)(sx[(int)Animate[CurAnimate]]),
                VPos + (int)(sy[(int)Animate[CurAnimate]]));

        }


        static public Boolean cdRect(
                 int sx, int sy, int sw, int sh,
                 int dx, int dy, int dw, int dh)
        {
            if (Math.Abs(((sx << 1) + sw) - ((dx << 1) + dw)) < (sw + dw) &&
                Math.Abs(((sy << 1) + sh) - ((dy << 1) + dh)) < (sh + dh))
            {
                return true;
            }
            return false;
        }
	}
}
