using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Collections;

namespace CellGameEdit.PM
{
    public partial class MapTerrains : UserControl
    {
        // 深度检测节点
        public class DeepNode
        {
            public int DX;
            public int DY;

            public double Len;

            public DeepNode(int dx, int dy)
            {
                DX = dx;
                DY = dy;
                
                Len = (float)dx * (float)dx + (float)dy * (float)dy;
                Len = Math.Sqrt(Len);
            }
        };

        // 冲突修补节点
        public class FixNode
        {
            public int Tile;
            public int Flip;

            public FixNode(int tile, int flip)
            {
                Tile = tile;
                Flip = flip;
            }
        };


        MapForm CurMap;

        MapForm MapClip;

        Dictionary<String, DeepNode> LengthTable = new Dictionary<String, DeepNode>();

        Dictionary<String, FixNode> FixTable = new Dictionary<String, FixNode>();
       

        public MapTerrains()
        {
            InitializeComponent();
        }

        public void init(MapForm map) 
        {
            CurMap = map;
        }

        private void ListTerrains_DropDownOpening(object sender, EventArgs e)
        {
            ListTerrains.DropDown.Items.Clear();

            ArrayList maps = ProjectForm.getInstance().getMaps(CurMap.super);

            foreach (MapForm map in maps)
            {
                if (map.XCount % 2 == 1 && map.YCount / map.XCount == 3)
                {
                    if (map.isTerrain())
                    {
                        ToolStripMenuItem droup = new ToolStripMenuItem(
                            map.getID()
                            );
                        droup.Tag = map;

                        ListTerrains.DropDown.Items.Add(droup);
                    }
                }
                
            }


        }

        private void ListTerrains_DropDownItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            if (e.ClickedItem.Tag.GetType().Equals(typeof(MapForm)))
            {
                MapForm map = (MapForm)e.ClickedItem.Tag;

                if (map.XCount % 2 == 1 && map.YCount / map.XCount == 3)
                {
                    MapClip = map;

                    initDeepNodes();
                    initFixNodes();

                    refreshRegion();
                }
            }
        }

        
        
        public void initDeepNodes()
        {
            if (MapClip != null)
            {
                LengthTable.Clear();

                int px = MapClip.XCount / 2;
                int py = MapClip.XCount / 2;

                for (int x = 0; x < MapClip.XCount; x++)
                {
                    for (int y = 0; y < MapClip.XCount; y++)
                    {
                        int srcTile = MapClip.getTileID(x, y);
                        int srcFlip = MapClip.getTileFlip(x, y);

                        int d1Tile = MapClip.getTileID(x, y + MapClip.XCount);
                        int d1Flip = MapClip.getTileFlip(x, y + MapClip.XCount);

                        int d2Tile = MapClip.getTileID(x, y + MapClip.XCount * 2);
                        int d2Flip = MapClip.getTileFlip(x, y + MapClip.XCount * 2);

                        int dx = x - px;
                        int dy = y - py;

                        putTileDeep(dx, dy, srcTile, srcFlip);
                        
                        if (dx != 0 && dy != 0)
                        {
                            DeepNode n1 = putTileDeep(dx, dy, d1Tile, d1Flip);
                            n1.Len = 0.8f;
                            DeepNode n2 = putTileDeep(dx, dy, d2Tile, d2Flip);
                            n2.Len = 0.9f;
                        }

                        //Console.WriteLine(key + " : " + value);
                    }
                }
            }
        }
        public DeepNode putTileDeep(int dx, int dy, int srcTile, int srcFlip)
        {
            String key = srcTile + "_" + srcFlip;
            DeepNode value = new DeepNode(dx, dy);

            if (!LengthTable.ContainsKey(key))
            {
                LengthTable.Add(key, value);
            }
            else
            {
                DeepNode v = LengthTable[key];
                if (value.Len > v.Len) { LengthTable[key] = value; }
            }

            return value;
        }
        public DeepNode getTileDeep(int tile, int flip) 
        {
            try
            {
                String key = tile + "_" + flip;
                return LengthTable[key];
            }
            catch (Exception err) { return null; }
        }



        public void initFixNodes()
        {
            if (MapClip != null)
            {
                FixTable.Clear();

                int px = MapClip.XCount / 2;
                int py = MapClip.XCount / 2;

                for (int x = 0; x < MapClip.XCount; x++)
                {
                    for (int y = 0; y < MapClip.XCount; y++)
                    {
                        int dx = x - px;
                        int dy = y - py;

                        // 双通
                        int d2Tile = MapClip.getTileID(x, y + MapClip.XCount * 2);
                        int d2Flip = MapClip.getTileFlip(x, y + MapClip.XCount * 2);

                        // 单拐
                        int d1Tile = MapClip.getTileID(x, y + MapClip.XCount);
                        int d1Flip = MapClip.getTileFlip(x, y + MapClip.XCount);

                        if (dx != 0 && dy != 0)
                        {
                            //
                            putFixNode(dx, dy, -dx, -dy, d2Tile, d2Flip);

                            
                            //putFixNode(0, dy, dx, 0, d1Tile, d1Flip);
                            putFixNode(dx, 0, 0, dy, d1Tile, d1Flip);
                            putFixNode(0, dy, dx, 0, d1Tile, d1Flip);

                            putFixNode(dx, dy, 0, dy, d1Tile, d1Flip);
                            putFixNode(dx, dy, dx, 0, d1Tile, d1Flip);

                            putFixNode(dx, 0, dx, dy, d1Tile, d1Flip);
                            putFixNode(0, dy, dx, dy, d1Tile, d1Flip);

                            putFixNode(dx, dy, 0, 0, d1Tile, d1Flip);

                        }
                        if (dx == 0 && dy == 0)
                        {
                            putFixNode(0, 0, 0, 0, d1Tile, d1Flip);
                        }
                    }
                }
            }
        }
        public FixNode putFixNode(int sx, int sy, int dx, int dy, int tile, int flip)
        {
            String key = sx + "_" + sy + "_" + dx + "_" + dy;
            FixNode value = new FixNode(tile, flip);

            if (!FixTable.ContainsKey(key))
            {
                FixTable.Add(key, value);
            }
            return value;

        }
        public FixNode getFixNode(int sx, int sy, int dx, int dy)
        {
            try
            {
                String key = sx + "_" + sy + "_" + dx + "_" + dy;
                return FixTable[key];
            }
            catch (Exception err) { return null; }
        }


        public void putSelectRegion(
            MapForm map,
            int bx, 
            int by)
        {

            if (MapClip != null)
            {
                int px = MapClip.XCount / 2;
                int py = MapClip.XCount / 2;

                bx -= px;
                by -= py;

                for (int x = 0; x < MapClip.XCount; x++)
                {
                    for (int y = 0; y < MapClip.XCount; y++)
                    {
                        int lenx = Math.Abs(x - px);
                        int leny = Math.Abs(y - py);

                        int dbx = bx + x;
                        int dby = by + y;

                        if (dbx >= 0 && dbx < map.XCount &&
                            dby >= 0 && dby < map.YCount)
                        {
                            int srcTile = MapClip.getTileID(x, y);
                            int srcFlip = MapClip.getTileFlip(x, y);
                            DeepNode srcDeep = getTileDeep(srcTile, srcFlip);

                            int dstTile = map.getTileID(dbx, dby);
                            int dstFlip = map.getTileFlip(dbx, dby);
                            DeepNode dstDeep = getTileDeep(dstTile, dstFlip);

                            //if (dstDeep == null)
                            {
                                srcTile = MapClip.getTileID(x, y);
                                srcFlip = MapClip.getTileFlip(x, y);
                                map.putTile(srcTile, dbx, dby);
                                map.putFlip(srcFlip, dbx, dby);
                            }
                            //else
                            if (false)
                            {
                                if (dstDeep.Len < srcDeep.Len)//联合
                                {
                                    if (srcDeep.DX != 0 && srcDeep.DY != 0)
                                    {
                                        if (srcDeep.DX == -dstDeep.DX || 
                                            srcDeep.DY == -dstDeep.DY)
                                        {
                                            FixNode fix = null;

                                            if (srcDeep.DX == -dstDeep.DX)
                                            {
                                                fix = getFixNode(-srcDeep.DX, srcDeep.DY, dstDeep.DX, dstDeep.DY);
                                            }
                                            else if (srcDeep.DY == -dstDeep.DY)
                                            {
                                                fix = getFixNode(srcDeep.DX, -srcDeep.DY, dstDeep.DX, dstDeep.DY);
                                            }

                                            if (fix != null)
                                            {
                                                map.putTile(fix.Tile, dbx, dby);
                                                map.putFlip(fix.Flip, dbx, dby);
                                            }
                                        }
                                    }
                                }
                                else if (dstDeep.Len > srcDeep.Len)//覆盖
                                {
                                    FixNode fix = null;

                                    if (dstDeep.DX != 0 && dstDeep.DY != 0)
                                    {
                                        if (srcDeep.DX == -dstDeep.DX)
                                        {
                                            fix = getFixNode(srcDeep.DX, 0, -dstDeep.DX, dstDeep.DY);
                                        }
                                        else if (srcDeep.DY == -dstDeep.DY)
                                        {
                                            fix = getFixNode(0, srcDeep.DY, dstDeep.DX, -dstDeep.DY);
                                        }
                                    }

                                    if (fix != null)
                                    {
                                        map.putTile(fix.Tile, dbx, dby);
                                        map.putFlip(fix.Flip, dbx, dby);
                                    }
                                    else
                                    {
                                        map.putTile(srcTile, dbx, dby);
                                        map.putFlip(srcFlip, dbx, dby);
                                    }

                                }
                                else if (srcDeep.Len == dstDeep.Len)
                                {
                                    if (srcTile != dstTile || srcFlip != dstFlip)//冲突
                                    {
                                        if (srcDeep.Len == 0 || dstDeep.Len == 0)
                                        {
                                            map.putTile(srcTile, dbx, dby);
                                            map.putFlip(srcFlip, dbx, dby);
                                        }
                                        else
                                        {
                                            FixNode fix = getFixNode(srcDeep.DX, srcDeep.DY, dstDeep.DX, dstDeep.DY);

                                            if (fix != null)
                                            {
                                                map.putTile(fix.Tile, dbx, dby);
                                                map.putFlip(fix.Flip, dbx, dby);
                                            }
                                            //if (srcDeep.DX != 0 && srcDeep.DX == -dstDeep.DX &&
                                            //    srcDeep.DY != 0 && srcDeep.DY == -dstDeep.DY)
                                            //{
                                               
                                            //}
                                            else
                                            {
                                                int sdx = x;
                                                int sdy = y;

                                                if (srcDeep.DX != dstDeep.DX && srcDeep.DY == dstDeep.DY)//水平扩展
                                                {
                                                    sdx -= Util.getDirect(x - px);
                                                }
                                                else if (srcDeep.DX == dstDeep.DX && srcDeep.DY != dstDeep.DY)//垂直扩展
                                                {
                                                    sdy -= Util.getDirect(y - py);
                                                }
                                                else // 缩回
                                                {
                                                    sdx += Util.getDirect(px - x);
                                                    sdy += Util.getDirect(py - y);
                                                }

                                                if (sdx >= 0 && sdx < MapClip.XCount && sdy >= 0 && sdy < MapClip.YCount)
                                                {
                                                    srcTile = MapClip.getTileID(sdx, sdy);
                                                    srcFlip = MapClip.getTileFlip(sdx, sdy);
                                                    map.putTile(srcTile, dbx, dby);
                                                    map.putFlip(srcFlip, dbx, dby);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }


        public void refreshRegion()
        {
            if (MapClip != null)
            {
                try
                {
                    if (MapRegion.Image == null ||
                        MapRegion.Image.Size.Width != MapClip.getWidth() ||
                        MapRegion.Image.Size.Height != MapClip.getHeight()
                        )
                    {
                        MapRegion.Image = new Bitmap(MapClip.getWidth(), MapClip.getHeight());
                        MapRegion.Size = MapRegion.Image.Size;
                    }

                    javax.microedition.lcdui.Graphics g = new javax.microedition.lcdui.Graphics(Graphics.FromImage(MapRegion.Image));

                    MapClip.Render(g,
                        0,
                        0,
                        new System.Drawing.Rectangle(
                            0,
                            0,
                            MapRegion.Width,
                            MapRegion.Height
                        ),
                        false, false, false, 0
                        );

                }
                catch (Exception err)
                {
                }
            }
        }


        public void renderSelectRegion(Graphics g, int rx, int ry, int bx, int by, MapForm map)
        {
            if (MapClip != null)
            {
                bx -= MapClip.XCount / 2;
                by -= MapClip.XCount / 2;

                int px = -MapClip.XCount / 2 * MapClip.CellW;
                int py = -MapClip.XCount / 2 * MapClip.CellH;

                g.DrawRectangle(Pens.GreenYellow,
                    rx + px, ry + py,
                    MapClip.getWidth(),
                    MapClip.getWidth()
                    );
                /*
                for (int x = 0; x < MapClip.XCount; x++)
                {
                    for (int y = 0; y < MapClip.XCount; y++)
                    {
                        int srcTile = MapClip.getTileID(x, y);
                        int srcFlip = MapClip.getTileFlip(x, y);
                        DeepNode srcDeep = getTileDeep(srcTile, srcFlip);

                        g.DrawString(
                            srcDeep.Len.ToString("f2"),
                            javax.microedition.lcdui.Graphics.font,
                            Brushes.Red,
                            rx + px + x * MapClip.CellW,
                            ry + py + y * MapClip.CellH
                            );

                        int dbx = bx + x;
                        int dby = by + y;

                        if (dbx >= 0 && dbx < map.XCount &&
                            dby >= 0 && dby < map.YCount)
                        {
                            int dstTile = map.getTileID(dbx, dby);
                            int dstFlip = map.getTileFlip(dbx, dby);
                            
                            DeepNode dstDeep = getTileDeep(dstTile, dstFlip);
                           
                            if (dstDeep != null)
                            {
                                g.DrawString(
                                    dstDeep.Len.ToString("f2"),
                                    javax.microedition.lcdui.Graphics.font,
                                    Brushes.Blue,
                                    rx + px + x * MapClip.CellW,
                                    ry + py + y * MapClip.CellH + 16
                                    );
                            }


                        }
                    }
                }
                */


                //g.DrawImage(MapRegion.Image, x + px, y + py);
            }
        }


        public void clearSelectRegion(
           MapForm map, int tile, int flip,
           int bx,
           int by)
        {

            if (MapClip != null)
            {
                int px = MapClip.XCount / 2;
                int py = MapClip.XCount / 2;

                bx -= px;
                by -= py;

                for (int x = 0; x < MapClip.XCount; x++)
                {
                    for (int y = 0; y < MapClip.XCount; y++)
                    {
                        int lenx = Math.Abs(x - px);
                        int leny = Math.Abs(y - py);

                        int dbx = bx + x;
                        int dby = by + y;

                        if (dbx >= 0 && dbx < map.XCount &&
                            dby >= 0 && dby < map.YCount)
                        {
                            map.putTile(tile, dbx, dby);
                            map.putFlip(flip, dbx, dby);
                        }
                    }
                }

            }
        }
    }

    

}
