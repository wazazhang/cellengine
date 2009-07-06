using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters;
using System.Security.Permissions;

using javax.microedition.lcdui;

namespace CellGameEdit.PM
{

    [Serializable]
    public partial class MapForm : Form , ISerializable ,IEditForm
    {

        public BufferdMiniMap MiniView;

        public String id;
        public String superName;
        public ImagesForm super;

        public int CellW;
        public int CellH;

        int KeyX = 0;
        int KeyY = 0;


        int[][] MatrixTile;
        int[][] MatrixAnim;
        int[][] MatrixFlip;
        int[][] MatrixTag;

        //Hashtable AnimTable;
        Hashtable AnimIndexTable;
        Hashtable AnimFlipTable;
        ArrayList Tiles;
        ArrayList TileKeys;

        public int XCount;
        public int YCount;

        #region fliptable
        static public System.Drawing.RotateFlipType[] flipTable = new System.Drawing.RotateFlipType[]
        {
            System.Drawing.RotateFlipType.RotateNoneFlipNone,
            System.Drawing.RotateFlipType.Rotate90FlipNone,//
            System.Drawing.RotateFlipType.Rotate180FlipNone,
            System.Drawing.RotateFlipType.Rotate270FlipNone,//

            System.Drawing.RotateFlipType.RotateNoneFlipX,
            System.Drawing.RotateFlipType.Rotate270FlipX,//
            System.Drawing.RotateFlipType.Rotate180FlipX,
            System.Drawing.RotateFlipType.Rotate90FlipX,//
        };

        static public int[] flipTableJ2me = new int[]{
           Cell.Game.CImages.TRANS_NONE,
            Cell.Game.CImages.TRANS_270,
            Cell.Game.CImages.TRANS_180,
            Cell.Game.CImages.TRANS_90,

            Cell.Game.CImages.TRANS_H,
            Cell.Game.CImages.TRANS_V90,
            Cell.Game.CImages.TRANS_V,
            Cell.Game.CImages.TRANS_H90,

        };
        #endregion

        //---------------------------------------------------------
        int flipIndex = 0;
        int tagIndex = 0;
        int srcIndex = 0;
        int srcIndexR = 0;

        System.Drawing.Rectangle srcRect;
        System.Drawing.Rectangle srcRectR;
        //----------------------------------------------------------
        int srcPX = 0;
        int srcPY = 0;
        int srcQX = 0;
        int srcQY = 0;
        System.Drawing.Rectangle srcRects;
        ArrayList srcTilesIndexBrush;
        ArrayList srcTilesOXBrush;
        ArrayList srcTilesOYBrush;

        //----------------------------------------------------------

        System.Drawing.Rectangle MapLoc = new System.Drawing.Rectangle(0,0,100,100);

        Boolean dstIsDown = false;
        int dstPX = 0;
        int dstPY = 0;
        int dstQX = 0;
        int dstQY = 0;
        int dstBX = 0;
        int dstBY = 0;
        System.Drawing.Rectangle dstRect;

        public static ImagesForm clipSuper;
        public static int[][] dstClipTile;
        public static int[][] dstClipTag;
        public static int[][] dstClipAnim;
        public static int[][] dstClipFlip;

        int animCount = 0;
        int curAnimate = -1;
        int curTime =0;
        int curFrame = -1;

        public MapForm(String name,int cellw,int cellh,ImagesForm images)
        {
            InitializeComponent();
            mapTerrains1.init(this) ;

            id = name; this.Text = id;
            super = images;
            CellW = cellw;
            CellH = cellh;
            numericUpDown4.Value = CellW;
            numericUpDown5.Value = CellH;

            srcRectR = new System.Drawing.Rectangle(0, 0, 0, 0);
            srcRect = new System.Drawing.Rectangle(0, 0, 0, 0);
            dstRect = new System.Drawing.Rectangle(0, 0, 0, 0);

            Tiles = super.dstImages;
            TileKeys = super.dstDataKeys;

            for (int i = 0; i < getTileCount(); i++)
            {
                if (getTileImage(i)!=null)
                {
                    srcIndex = i;
                    //srcImage = getTileImage(i);
                    srcRect.X = getTileImage(i).x;
                    srcRect.Y = getTileImage(i).y;
                    srcRect.Width = getTileImage(i).getWidth();
                    srcRect.Height = getTileImage(i).getHeight();

                    srcIndexR = i;
                    srcRectR.X = getTileImage(i).x;
                    srcRectR.Y = getTileImage(i).y;
                    srcRectR.Width = getTileImage(i).getWidth();
                    srcRectR.Height = getTileImage(i).getHeight();
                    break;
                }
            }

            XCount = 20;
            YCount = 20;
            MatrixTile = new int[YCount][];
            MatrixTag = new int[YCount][];
            MatrixAnim = new int[YCount][];
            MatrixFlip = new int[YCount][];
            for (int i = 0; i < YCount;i++ )
            {
                MatrixTile[i] = new int[XCount];
                MatrixTag[i] = new int[XCount];
                MatrixAnim[i] = new int[XCount];
                MatrixFlip[i] = new int[XCount];
                //for (int j = 0; j < XCount; j++)
                //{
                //    MatrixAnim[i][j] = 0;
                //}
            }
            MapLoc.Width = CellW * XCount;
            MapLoc.Height = CellH * YCount;
            numericUpDown2.Value = XCount;
            numericUpDown3.Value = YCount;
            

            dstClipTile = new int[1][];
            dstClipTag = new int[1][];
            dstClipAnim = new int[1][];
            dstClipFlip = new int[1][];

            dstClipTile[0] = new int[1];
            dstClipTag[0] = new int[1];
            dstClipAnim[0] = new int[1];
            dstClipFlip[0] = new int[1];

            //AnimTable = new Hashtable();
            //Animates = new ArrayList();
            AnimIndexTable = new Hashtable();
            AnimFlipTable = new Hashtable();
           
            ArrayList frames = new ArrayList();
            frames.Add(0);
            ArrayList flips = new ArrayList();
            flips.Add(0);
            ListViewItem item = new ListViewItem(new String[] { animCount.ToString("(空)"), "1"});
            listView1.Items.Add(item);
            AnimIndexTable.Add(item, frames);
            AnimFlipTable.Add(item, flips);
           
            animCount++;

            pictureBox3.Width = CellW;
            pictureBox3.Height = CellH;

            pictureBox4.Left = pictureBox3.Location.X + 3 + CellW;
            pictureBox4.Width = CellW;
            pictureBox4.Height = CellH;

            trackBar1.Maximum = 0;

           
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected MapForm(SerializationInfo info, StreamingContext context)
        {
            try
            {
                InitializeComponent();
                mapTerrains1.init(this);

                id = (String)info.GetValue("id", typeof(String));
                this.Text = id;

                if (!ProjectForm.IsCopy)
                {
                    super = (ImagesForm)info.GetValue("super", typeof(ImagesForm));
                }

                if (super == null)
                {
                    superName = (String)info.GetValue("SuperName", typeof(String));
                    super = ProjectForm.getInstance().getImagesFormByName(superName);
                }
                
                CellW = (int)info.GetValue("CellW", typeof(int));
                CellH = (int)info.GetValue("CellH", typeof(int));
                numericUpDown4.Value = CellW;
                numericUpDown5.Value = CellH;

                srcRectR = new System.Drawing.Rectangle(0, 0, 0, 0);
                srcRect = new System.Drawing.Rectangle(0, 0, 0, 0);
                dstRect = new System.Drawing.Rectangle(0, 0, 0, 0);

                Tiles = super.dstImages;
                TileKeys = super.dstDataKeys;
                for (int i = 0; i < getTileCount(); i++)
                {
                    if (getTileImage(i) != null)
                    {
                        srcIndex = i;
                        //srcImage = getTileImage(i);
                        srcRect.X = getTileImage(i).x;
                        srcRect.Y = getTileImage(i).y;
                        srcRect.Width = getTileImage(i).getWidth();
                        srcRect.Height = getTileImage(i).getHeight();

                        srcIndexR = i;
                        srcRectR.X = getTileImage(i).x;
                        srcRectR.Y = getTileImage(i).y;
                        srcRectR.Width = getTileImage(i).getWidth();
                        srcRectR.Height = getTileImage(i).getHeight();

                        break;
                    }
                }

                pictureBox3.Width = CellW;
                pictureBox3.Height = CellH;

                pictureBox4.Left = pictureBox3.Location.X + 3 + CellW;

                pictureBox4.Width = CellW;
                pictureBox4.Height = CellH;
               

                MatrixTile = (int[][])info.GetValue("MatrixTile", typeof(int[][]));
                MatrixTag = (int[][])info.GetValue("MatrixTag", typeof(int[][]));
                MatrixAnim = (int[][])info.GetValue("MatrixAnim", typeof(int[][]));
                MatrixFlip = (int[][])info.GetValue("MatrixFlip", typeof(int[][]));
                XCount = MatrixTile[0].Length;
                YCount = MatrixTile.Length;

                MapLoc.Width = CellW * XCount;
                MapLoc.Height = CellH * YCount;
                numericUpDown2.Value = XCount;
                numericUpDown3.Value = YCount;


                dstClipTile = new int[1][];
                dstClipTag = new int[1][];
                dstClipAnim = new int[1][];
                dstClipFlip = new int[1][];

                dstClipTile[0] = new int[1];
                dstClipTag[0] = new int[1];
                dstClipAnim[0] = new int[1];
                dstClipFlip[0] = new int[1];

                

                AnimIndexTable = new Hashtable();
                AnimFlipTable = new Hashtable();
          
                ArrayList animK = (ArrayList)info.GetValue("animK", typeof(ArrayList));
                ArrayList animV = (ArrayList)info.GetValue("animV", typeof(ArrayList));
                ArrayList animF = (ArrayList)info.GetValue("animF", typeof(ArrayList));
               
                //Console.WriteLine("KCount=" + animK.Count);

                for (int i = 0; i < animK.Count; i++)
                {
                    ArrayList v = (ArrayList)animV[i];
                    ArrayList f = (ArrayList)animF[i];
                    ListViewItem k = new ListViewItem(new String[] { animK[i].ToString(), v.Count.ToString()});
                    listView1.Items.Add(k);
                    AnimIndexTable.Add(k, v);
                    AnimFlipTable.Add(k, f);
                   
                }

                animCount = (int)info.GetValue("animCount", typeof(int));

                trackBar1.Maximum = 0;



                try
                {
                    this.IsTerrain.Checked = (Boolean)info.GetValue("IsTerrain", typeof(Boolean));
                }
                catch (Exception err) { }


                
            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace + "  at  " +err.Message);
            }
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            Console.WriteLine("Serializ Map : " + id);

            try{
                info.AddValue("id",id);

                if (!ProjectForm.IsCopy)
                {
                    info.AddValue("super", super);
                }

                info.AddValue("SuperName", super.id);

                info.AddValue("CellW",CellW);
                info.AddValue("CellH",CellH);
                

                info.AddValue("MatrixTile",MatrixTile);
                info.AddValue("MatrixTag", MatrixTag);
                info.AddValue("MatrixAnim", MatrixAnim);
                info.AddValue("MatrixFlip", MatrixFlip);

                //info.AddValue("AnimTable", AnimTable);
                ArrayList animK = new ArrayList();
                ArrayList animV = new ArrayList();
                ArrayList animF = new ArrayList();
                
                for (int i = 0; i < listView1.Items.Count; i++)
                {
                    animK.Add(listView1.Items[i].Text);
                    animV.Add(getFrame(i));
                    animF.Add(getFrameFlip(i));
                }
                info.AddValue("animK", animK);
                info.AddValue("animV", animV);
                info.AddValue("animF", animF);
                //Console.WriteLine("NCount=" + AnimTable.Count);

                info.AddValue("animCount", animCount);

                info.AddValue("IsTerrain", IsTerrain.Checked);


            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace + "  at  " +err.Message);
            }
        }

        public String getID()
        {
            return id;
        }

        public Form getForm()
        {
            return this;
        }

        public void ChangeSuper(ArrayList images)
        {
            Hashtable imagesHT = new Hashtable();

            for (int i = 0; i < images.Count; i++)
            {
                imagesHT.Add(((ImagesForm)images[i]).id, images[i]);
            }

            if (imagesHT.ContainsKey(superName))
            {
                ChangeSuper((ImagesForm)imagesHT[superName]);
                Console.WriteLine("Map ChangeImages : " + superName);
            }
        }

        public void ChangeSuper(ImagesForm super)
        {
            this.super = super;
            this.Tiles = super.dstImages;

            srcRectR = new System.Drawing.Rectangle(0, 0, 0, 0);
            srcRect = new System.Drawing.Rectangle(0, 0, 0, 0);
            dstRect = new System.Drawing.Rectangle(0, 0, 0, 0);

            Tiles = super.dstImages;
            for (int i = 0; i < getTileCount(); i++)
            {
                if (getTileImage(i) != null)
                {
                    srcIndex = i;
                    //srcImage = getTileImage(i);
                    srcRect.X = getTileImage(i).x;
                    srcRect.Y = getTileImage(i).y;
                    srcRect.Width = getTileImage(i).getWidth();
                    srcRect.Height = getTileImage(i).getHeight();

                    srcIndexR = i;
                    srcRectR.X = getTileImage(i).x;
                    srcRectR.Y = getTileImage(i).y;
                    srcRectR.Width = getTileImage(i).getWidth();
                    srcRectR.Height = getTileImage(i).getHeight();

                    break;
                }
            }

            pictureBox3.Width = CellW;
            pictureBox3.Height = CellH;

            pictureBox4.Left = pictureBox3.Location.X + 3 + CellW;

            pictureBox4.Width = CellW;
            pictureBox4.Height = CellH;

            XCount = MatrixTile[0].Length;
            YCount = MatrixTile.Length;

            MapLoc.Width = CellW * XCount;
            MapLoc.Height = CellH * YCount;
            numericUpDown2.Value = XCount;
            numericUpDown3.Value = YCount;


        }


        int[][] OutputTileMatrix;
        int[][] OutputFlagMatrix;

        Animates OutputAnimates;

        public void initOutput(){
            //init
            OutputTileMatrix = new int[YCount][];
            OutputFlagMatrix = new int[YCount][];
            for (int y = 0; y < YCount; y++)
            {
                OutputTileMatrix[y] = new int[XCount];
                OutputFlagMatrix[y] = new int[XCount];
                for (int x = 0; x < XCount; x++)
                {
                    OutputTileMatrix[y][x] = 0;
                    OutputFlagMatrix[y][x] = (int)MatrixTag[y][x];
                }

            }

            // subs
            OutputAnimates = new Animates();

            for (int y = 0; y < YCount; y++)
            {
                for (int x = 0; x < XCount; x++)
                {
                    int indexSub = OutputAnimates.subIndexOf(MatrixTile[y][x], MatrixFlip[y][x]);
                    if (indexSub < 0)
                    {
                        OutputAnimates.subAdd(MatrixTile[y][x], MatrixFlip[y][x]);
                        indexSub = OutputAnimates.subGetCount() - 1;
                    }

                    ArrayList frame = new ArrayList();
                    frame.Add(indexSub);

                    int indexFrame = OutputAnimates.frameIndexOf(frame);
                    if (indexFrame < 0)
                    {
                        OutputAnimates.frameAdd(frame);
                        indexFrame = OutputAnimates.frameGetCount() - 1;
                    }

                    OutputTileMatrix[y][x] = (int)indexFrame;

                }
            }
            for (int y = 0; y < YCount; y++)
            {
                for (int x = 0; x < XCount; x++)
                {
                    int anim = Math.Abs(MatrixAnim[y][x]);

                    if (anim > 0 && getFrame(anim) != null && getFrame(anim).Count > 0)
                    {
                        ArrayList frame = new ArrayList();

                        for (int i = 0; i < getFrame(anim).Count; i++)
                        {
                            int indexSub = OutputAnimates.subIndexOf((int)(getFrame(anim)[i]), (int)(getFrameFlip(anim)[i]));
                            if (indexSub < 0)
                            {
                                OutputAnimates.subAdd((int)(getFrame(anim)[i]), (int)(getFrameFlip(anim)[i]));
                                indexSub = OutputAnimates.subGetCount() - 1;
                            }
                            frame.Add(indexSub);
                        }

                        int indexFrame = OutputAnimates.frameIndexOf(frame);
                        if (indexFrame < 0)
                        {
                            OutputAnimates.frameAdd(frame);
                            indexFrame = OutputAnimates.frameGetCount() - 1;
                        }


                        // 和JAVA端相反
                        if (MatrixAnim[y][x] < 0)//多层
                        {
                            OutputTileMatrix[y][x] = indexFrame;
                        }
                        else
                        {
                            OutputTileMatrix[y][x] = -indexFrame;
                        }

                        //Console.WriteLine(" OutputTileMatrix = " + OutputTileMatrix[y][x].ToString("d"));

                    }
                }
            }

        }

        //public void Output(System.IO.StringWriter sw)
        //{

        //    initOutput();

        //    sw.WriteLine();
        //    sw.WriteLine("final static public CMap createMap_" + super.id + "_" + this.id + "(IImages tiles,boolean isAnimate,boolean isCyc)");
        //    sw.WriteLine("{");
        //    // OutputAnimates
        //    sw.WriteLine("    CAnimates OutputAnimates = new CAnimates("+OutputAnimates.subGetCount()+",tiles);");
        //for (int i = 0; i < OutputAnimates.subGetCount(); i++)  
        //    sw.WriteLine("    OutputAnimates.addPart(0,0," + OutputAnimates.SubPart[i] + "," + flipTableJ2me[(int)(OutputAnimates.SubFlip[i])] + ");");
        //    sw.WriteLine("    OutputAnimates.setFrame(new int[" + OutputAnimates.frameGetCount() + "][]);");
        //for (int i = 0; i < OutputAnimates.frameGetCount(); i++)
        //    sw.WriteLine("    OutputAnimates.setComboFrame(new int[]{"+Util.toTextArray((int[])(OutputAnimates.frameGetFrame(i).ToArray(typeof(int))))+"},"+i+");");
        //    sw.WriteLine();
        //    // collides
        //    sw.WriteLine("    CCollides collides = new CCollides(8);");
        //    sw.WriteLine("    collides.addCDRect(0x00000000, 0, 0, " + CellW + "," + CellH + ");");
        //    sw.WriteLine("    collides.addCDRect(0x00000001, " + 0 + ", " + 0 + ", " + CellW + "," + CellH + ");");
        //    sw.WriteLine("    collides.addCDRect(0x00000002, " + 0 + ", " + 0 + ", " + CellW + "," + 1 + ");");
        //    sw.WriteLine("    collides.addCDRect(0x00000004, " + 0 + ", " + (CellH - 1) + ", " + CellW + "," + 1 + ");");
        //    sw.WriteLine("    collides.addCDRect(0x00000008, " + 0 + ", " + 0 + ", " + 1 + "," + CellH + ");");
        //    sw.WriteLine("    collides.addCDRect(0x00000010, " + (CellW - 1) + ", " + 0 + ", " + 1 + "," + CellH + ");");
        //    sw.WriteLine("    collides.addCDLine(0x00000020, " + 0 + ", " + 0 + ", " + (CellW-1) + "," + (CellH-1) + ");");
        //    sw.WriteLine("    collides.addCDLine(0x00000040, " + (CellW-1) + ", " + 0 + ", " + 0 + "," + (CellH-1) + ");");
        //    sw.WriteLine();
        //    // map matrix
        //    sw.WriteLine("    short[][] OutputTileMatrix = new short[][]{");
        //for (int i = 0; i < YCount; i++)
        //    sw.WriteLine("        {"+ Util.toTextArray(OutputTileMatrix[i]) +"},");
        //    sw.WriteLine("    };");
        //    sw.WriteLine("    short[][] OutputFlagMatrix = new short[][]{");
        //for (int i = 0; i < YCount; i++)
        //    sw.WriteLine("        {" + Util.toTextArray(OutputFlagMatrix[i]) + "},");
        //    sw.WriteLine("    };");
        //    sw.WriteLine();
        //    // map new
        //    sw.WriteLine("    CMap ret = new CMap(");
        //    sw.WriteLine("            OutputAnimates, ");
        //    sw.WriteLine("            collides, ");
        //    sw.WriteLine("            " + CellW + ", " + CellH + ", ");
        //    sw.WriteLine("            OutputTileMatrix, ");
        //    sw.WriteLine("            OutputFlagMatrix, ");
        //    sw.WriteLine("            isAnimate,isCyc ");
        //    sw.WriteLine("            );");
        //    sw.WriteLine();
        //    sw.WriteLine("    return ret;");
        //    sw.WriteLine();
        //    sw.WriteLine("}");

        //}

        public void OutputCustom(int index, String script, System.IO.StringWriter output)
        {
            lock (this)
            {
                try
                {
                    initOutput();

                    String map = Util.getFullTrunkScript(script, "#<MAP>", "#<END MAP>");

                    bool fix = false;

                    // OutputAnimates part
                    do
                    {
                        String[] senceParts = new string[OutputAnimates.subGetCount()];
                        for (int i = 0; i < OutputAnimates.subGetCount(); i++)
                        {
                            string TILE = OutputAnimates.SubPart[i].ToString();
                            string TRANS = flipTableJ2me[(int)(OutputAnimates.SubFlip[i])].ToString();

                            senceParts[i] = Util.replaceKeywordsScript(map, "#<SCENE PART>", "#<END SCENE PART>",
                                new string[] { "<INDEX>", "<TILE>", "<TRANS>" },
                                new string[] { i.ToString(), TILE, TRANS }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(map, "#<SCENE PART>", "#<END SCENE PART>", senceParts);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            map = temp;
                        }
                    } while (fix);


                    // OutputAnimates frame
                    do
                    {
                        String[] senceFrames = new string[OutputAnimates.frameGetCount()];
                        for (int i = 0; i < OutputAnimates.frameGetCount(); i++)
                        {
                            //string DATA = Util.toTextArray((int[])(OutputAnimates.frameGetFrame(i).ToArray(typeof(int))));
                            int[] frames = ((int[])(OutputAnimates.frameGetFrame(i).ToArray(typeof(int))));
                            string DATA = Util.toNumberArray1D<int>(ref frames);

                            senceFrames[i] = Util.replaceKeywordsScript(map, "#<SCENE FRAME>", "#<END SCENE FRAME>",
                                new string[] { "<INDEX>", "<DATA SIZE>", "<DATA>" },
                                new string[] { i.ToString(), OutputAnimates.frameGetFrame(i).Count.ToString(), DATA }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(map, "#<SCENE FRAME>", "#<END SCENE FRAME>", senceFrames);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            map = temp;
                        }
                    } while (fix);




                    // cd parts
                    int[][] cds = new int[][]{
                     new int[]{1,0x00000000, 0, 0, CellW, CellH, CellW, CellH},//null
                     new int[]{1,0x00000001, 0, 0, CellW, CellH, CellW, CellH},//full

                     new int[]{1,0x00000002, 0,       0,       CellW,   1,       CellW ,   CellH},//
                     new int[]{1,0x00000004, 0,       CellH-1, CellW,   1,       CellW ,   CellH},//
                     new int[]{1,0x00000008, 0,       0,       1,       CellH,   CellW ,   CellH},//
                     new int[]{1,0x00000010, CellW-1, 0,       1,       CellH,   CellW ,   CellH},//

                     new int[]{2,0x00000020, 0,       0,       CellW,   CellH,   CellW-1 , CellH-1},//
                     new int[]{2,0x00000040, CellW-1, 0,       CellW,   CellH,   0 ,       CellH-1},//
                };

                    do
                    {
                        String[] cdParts = new string[8];
                        for (int i = 0; i < 8; i++)
                        {
                            string TYPE = cds[i][0] == 1 ? "rect" : "line";
                            string MASK = cds[i][1].ToString();
                            string X1 = cds[i][2].ToString();
                            string Y1 = cds[i][3].ToString();
                            string W = cds[i][4].ToString();
                            string H = cds[i][5].ToString();
                            string X2 = cds[i][6].ToString();
                            string Y2 = cds[i][7].ToString();
                            cdParts[i] = Util.replaceKeywordsScript(map, "#<CD PART>", "#<END CD PART>",
                                new string[] { "<INDEX>", "<TYPE>", "<MASK>", "<X1>", "<Y1>", "<W>", "<H>", "<X2>", "<Y2>" },
                                new string[] { i.ToString(), TYPE, MASK, X1, Y1, W, H, X2, Y2 }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(map, "#<CD PART>", "#<END CD PART>", cdParts);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            map = temp;
                        }
                    } while (fix);


                    // tile matrix
                    //String senceMatrix = "";
                    //for (int i = 0; i < YCount; i++)
                    //    senceMatrix += "{" + Util.toTextArray(OutputTileMatrix[i]) + "},\r\n";
                    String senceMatrix = Util.toNumberArray2D<int>(ref OutputTileMatrix);

                    // cd matrix
                    //String cdMatrix = "";
                    //for (int i = 0; i < YCount; i++)
                    //    cdMatrix += "{" + Util.toTextArray(OutputFlagMatrix[i]) + "},\r\n";

                    String cdMatrix = Util.toNumberArray2D<int>(ref OutputFlagMatrix);

                    map = Util.replaceKeywordsScript(map, "#<MAP>", "#<END MAP>",
                        new string[] { 
                    "<NAME>", 
                    "<MAP INDEX>",
                    "<IMAGES NAME>",
                    "<CELL W>",
                    "<CELL H>" , 
                    "<X COUNT>",
                    "<Y COUNT>" , 
                    "<TILE MATRIX>" , 
                    "<FLAG MATRIX>" , 
                    "<SCENE PART COUNT>" ,
                    "<SCENE FRAME COUNT>" ,
                    "<CD PART COUNT>"
                },
                        new string[] { 
                    this.id, 
                    index.ToString(),
                    super.id,
                    CellW.ToString(),
                    CellH.ToString(),
                    XCount.ToString(),
                    YCount.ToString(),
                    senceMatrix,
                    cdMatrix,
                    OutputAnimates.subGetCount().ToString(),
                    OutputAnimates.frameGetCount().ToString(),
                    "8"}
                        );

                    output.WriteLine(map);
                    //Console.WriteLine(map);
                }
                catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }

            }
        }

        private void MapForm_Load(object sender, EventArgs e)
        {
            timer1.Start();
            refreshAnimateList();
        }
        private void MapForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }
        private void MapForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }
        private void MapForm_TextChanged(object sender, EventArgs e)
        {
            this.id = this.Text;
        }


        public Boolean isTerrain() { return this.IsTerrain.Checked; }

        public Image getTileImage(int x, int y) {
            return (((Image)(Tiles[MatrixTile[y][x]])));
        }
        public Image getTileImage(int index)
        {
            return (((Image)(Tiles[index])));
        }
        public String getTileKey(int x, int y)
        {
            return (((String)(TileKeys[MatrixTile[y][x]])));
        }
        public String getTileKey(int index)
        {
            return (((String)(TileKeys[index])));
        }

        public int getTileCount()
        {
            return Tiles.Count;
        }

        public int getWidth()
        {
            return this.XCount * this.CellW;
        }
        public int getHeight()
        {
            return this.YCount * this.CellH;
        }

        

        public int getTileID(int x,int y)
        {
            try {
                return MatrixTile[y][x];
            }
            catch (Exception err) {
                return -1;
            }
        }
        public int getTileFlip(int x, int y)
        {
            try
            {
                return MatrixFlip[y][x];
            }
            catch (Exception err)
            {
                return -1;
            }
        }
        public int getTagID(int x, int y)
        {
            try
            {
                return MatrixTag[y][x];
            }
            catch (Exception err)
            {
                return -1;
            }
        }
        public int getAnimateID(int x, int y)
        {
            try
            {
                return MatrixAnim[y][x];
            }
            catch (Exception err)
            {
                return 0;
            }
        }
        

        private void addAnimate()
        {
            int pos = listView1.Items.Count;
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0 )
            {
                pos = listView1.Items.IndexOf(listView1.SelectedItems[0]);
            }
            if (pos == 0 && listView1.Items.Count>1)
            {
                pos = 1;
            }

            ArrayList frames = new ArrayList();
            frames.Add(srcIndex);
            ArrayList flips = new ArrayList();
            flips.Add(flipIndex);
          
            ListViewItem item = new ListViewItem(new String[] { animCount.ToString("d4"), "1" });
            
            //Animates.Add(frames);
            listView1.Items.Insert(pos,item);
            AnimIndexTable.Add(item, frames);
            AnimFlipTable.Add(item,flips);
        

            exchangeAnimate(pos,pos+1);

            item.Selected = true;

            animCount++;
            //curAnimate = listView1.Items.IndexOf(item);
           
        }
        private void delAnimate()
        {
            if (listView1.Items.Count > 1 &&
                listView1.SelectedItems != null &&
                listView1.SelectedItems.Count > 0 && 
                listView1.Items.IndexOf(listView1.SelectedItems[0])>0)
            {
                //Animates.Remove(AnimTable[listView1.SelectedItems[0]]);

                AnimIndexTable.Remove(listView1.SelectedItems[0]);
                AnimFlipTable.Remove(listView1.SelectedItems[0]);
                listView1.Items.Remove(listView1.SelectedItems[0]);


            }
          
        }
        private void exchangeAnimate(int src, int dst)
        {
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0)
            {
                if (listView1.Items.Count > 1 )
                {
                    ListViewItem tempsrc = listView1.Items[src];
                    ListViewItem tempdst = listView1.Items[dst];
                    listView1.Items.Remove(tempdst);
                    listView1.Items.Remove(tempsrc);
                    if (src < dst)
                    {
                        listView1.Items.Insert(src, tempdst);
                        listView1.Items.Insert(dst, tempsrc);
                    }
                    else
                    {
                        listView1.Items.Insert(dst, tempsrc);
                        listView1.Items.Insert(src, tempdst);
                    }
                    tempsrc.Focused = true;
                    
                }
            }
        }

       
        private void addFrame(int pos, int index, int flip)
        {
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0)
            {
                ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]]).Insert(pos,index);
                ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]]).Insert(pos, flip);
                listView1.SelectedItems[0].SubItems[1].Text = (((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])).Count.ToString();
                exchangeFrame(pos,pos+1);
            }

        }
        private void delFrame(int pos)
        {
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0)
            {
                if (((ArrayList)AnimIndexTable[listView1.SelectedItems[0]]).Count > 1)
                {
                    ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]]).RemoveAt(pos);
                    ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]]).RemoveAt(pos);
                    listView1.SelectedItems[0].SubItems[1].Text = (((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])).Count.ToString();
                }
            }
        }
        private void exchangeFrame(int src, int dst)
        {
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0)
            {
                if (((ArrayList)AnimIndexTable[listView1.SelectedItems[0]]).Count > 1)
                {
                    Object obj1 = ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])[src];
                    ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])[src] = ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])[dst];
                    ((ArrayList)AnimIndexTable[listView1.SelectedItems[0]])[dst] = obj1;

                    Object obj2 = ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]])[src];
                    ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]])[src] = ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]])[dst];
                    ((ArrayList)AnimFlipTable[listView1.SelectedItems[0]])[dst] = obj2;
                }
            }
        }

        private ArrayList getFrame(int animate)
        {
            if (animate>=0 && animate < listView1.Items.Count)
            {
                return ((ArrayList)AnimIndexTable[listView1.Items[animate]]);
            }
            return null;
        }
        private ArrayList getFrameFlip(int animate)
        {
            if (animate >= 0 && animate < listView1.Items.Count)
            {
                return ((ArrayList)AnimFlipTable[listView1.Items[animate]]);
            }
            return null;
        }

        private void fillAnimate(int index)
        {
            for (int y = dstRect.Y; y < dstRect.Y + dstRect.Height;y+=CellH )
            {
                for (int x = dstRect.X; x < dstRect.X + dstRect.Width; x+=CellW)
                {
                    putAnimate(index,x/CellW,y/CellH,IsMultiLayer.Checked);
                }
            }
        }
        private void fillTile(int index, int flip)
        {
            for (int y = dstRect.Y; y < dstRect.Y + dstRect.Height;y+=CellH )
            {
                for (int x = dstRect.X; x < dstRect.X + dstRect.Width; x+=CellW)
                {
                    putTile(index,x/CellW,y/CellH);
                    putFlip(flip, x / CellW, y / CellH);
                }
            }
        }
        private void fillTag(int index)
        {
            for (int y = dstRect.Y; y < dstRect.Y + dstRect.Height; y += CellH)
            {
                for (int x = dstRect.X; x < dstRect.X + dstRect.Width; x += CellW)
                {
                    putTag(index, x / CellW, y / CellH);
                }
            }
        }
        private void copyDst()
        {
            int sbx = dstRect.X / CellW;
            int sby = dstRect.Y / CellH;
            int xcount = dstRect.Width / CellW;
            int ycount = dstRect.Height / CellH;

            if (xcount <= 0 || ycount <= 0) return;

            clipSuper = this.super;
            dstClipTile = new int[ycount][];
            dstClipTag = new int[ycount][];
            dstClipAnim = new int[ycount][];
            dstClipFlip = new int[ycount][];
            for (int by = 0; by < ycount; by++)
            {
                dstClipTile[by] = new int[xcount];
                dstClipTag[by] = new int[xcount];
                dstClipAnim[by] = new int[xcount];
                dstClipFlip[by] = new int[xcount];
                for (int bx = 0; bx < xcount; bx++)
                {
                    if (sby + by < MatrixTile.Length && sbx + bx < MatrixTile[by].Length)
                    {
                        dstClipTile[by][bx] = MatrixTile[sby + by][sbx + bx];
                        dstClipTag[by][bx] = MatrixTag[sby + by][sbx + bx];
                        dstClipAnim[by][bx] = MatrixAnim[sby + by][sbx + bx];
                        dstClipFlip[by][bx] = MatrixFlip[sby + by][sbx + bx];
                    }
                }
            }
            
        }
        private void clipDst()
        {
            int sbx = dstRect.X / CellW;
            int sby = dstRect.Y / CellH;
            int xcount = dstRect.Width / CellW;
            int ycount = dstRect.Height / CellH;

            if (xcount <= 0 || ycount <= 0) return;

            clipSuper = this.super;
            dstClipTile = new int[ycount][];
            dstClipTag = new int[ycount][];
            dstClipAnim = new int[ycount][];
            dstClipFlip = new int[ycount][];
            for (int by = 0; by < ycount; by++)
            {
                dstClipTile[by] = new int[xcount];
                dstClipTag[by] = new int[xcount];
                dstClipAnim[by] = new int[xcount];
                dstClipFlip[by] = new int[xcount];
                for (int bx = 0; bx < xcount; bx++)
                {
                    if (sby + by < MatrixTile.Length && sbx + bx < MatrixTile[by].Length)
                    {
                        dstClipTile[by][bx] = MatrixTile[sby + by][sbx + bx];
                        dstClipTag[by][bx] = MatrixTag[sby + by][sbx + bx];
                        dstClipAnim[by][bx] = MatrixAnim[sby + by][sbx + bx];
                        dstClipFlip[by][bx] = MatrixFlip[sby + by][sbx + bx];

                        putTile(srcIndexR, sbx + bx, sby + by);
                        putFlip(flipIndex, sbx + bx, sby + by);
                        putTag(0, sbx + bx, sby + by);
                        putAnimate(0, sbx + bx, sby + by, false);
                    }
                }
            }

        }
        private void paseDst()
        {
            if (clipSuper != this.super) return;

            
            int sbx = dstRect.X / CellW;
            int sby = dstRect.Y / CellH;
            //int xcount = dstRect.Width / CellW;
            //int ycount = dstRect.Height / CellH;
            int xcount = dstRect.Width / CellW;
            int ycount = dstRect.Height / CellH;

            if (dstClipTile!=null)
            {
                xcount = Math.Max(xcount, dstClipTile[0].Length);
                ycount = Math.Max(ycount, dstClipTile.Length);
            }

            if (xcount <= 0 || ycount <= 0) return;

            for (int by = 0; by < ycount; by++)
            {
                for (int bx = 0; bx < xcount; bx++)
                {
                    putTile(
                        dstClipTile[by % dstClipTile.Length][bx % dstClipTile[by % dstClipTile.Length].Length], 
                        sbx + bx, sby + by);
                    putFlip(
                        dstClipFlip[by % dstClipFlip.Length][bx % dstClipFlip[by % dstClipFlip.Length].Length],
                        sbx + bx, sby + by);
                    putTag(
                        dstClipTag[by % dstClipTag.Length][bx % dstClipTag[by % dstClipTag.Length].Length], 
                        sbx + bx, sby + by);

                    int anim =  dstClipAnim[by % dstClipAnim.Length][bx % dstClipAnim[by % dstClipAnim.Length].Length];
                    putAnimate(
                        anim, 
                        sbx + bx, sby + by,
                        anim<0);
                }
            }
        }
        private void clearDst()
        {
            int sbx = dstRect.X / CellW;
            int sby = dstRect.Y / CellH;
            int xcount = dstRect.Width / CellW;
            int ycount = dstRect.Height / CellH;

            if (xcount <= 0 || ycount <= 0) return;

            for (int by = 0; by < ycount; by++)
            {
                for (int bx = 0; bx < xcount; bx++)
                {
                    if (sby + by < MatrixTile.Length && sbx + bx < MatrixTile[by].Length)
                    {
                        putTile(srcIndexR, sbx + bx, sby + by);
                        putFlip(flipIndex, sbx + bx, sby + by);
                        putTag(0, sbx + bx, sby + by);
                        putAnimate(0, sbx + bx, sby + by, false);
                    }
                }
            }

        }


        public void putTile(int index, int bx, int by)
        {
            if (index < getTileCount() && by < MatrixTile.Length && bx < MatrixTile[by].Length)
            {
                MatrixTile[by][bx] = index;

                if (MiniView != null)
                {
                    MiniView.rebuff(bx,by,getTileImage(bx,by));
                }
            }
        }
        public void putTag(int tag, int bx, int by)
        {
            if (by < MatrixTag.Length && bx < MatrixTag[by].Length)
            {
                MatrixTag[by][bx] = tag;
            }
        }
        public void putAnimate(int index, int bx, int by, Boolean multiLayer)
        {   
            if (by < MatrixAnim.Length && bx < MatrixAnim[by].Length)
            {
                index = Math.Abs(index);

                if (index > 0 && index < listView1.Items.Count)
                {
                    if (multiLayer)//duo ceng
                    {
                        MatrixAnim[by][bx] = -index;
                    }
                    else
                    {
                        MatrixAnim[by][bx] = index;
                    }
                }
                else
                {
                    MatrixAnim[by][bx] = 0;
                }
                
            }
        }
        public void putFlip(int flip, int bx, int by)
        {
            if (flip < 8 && by < MatrixFlip.Length && bx < MatrixFlip[by].Length)
            {
                MatrixFlip[by][bx] = flip;
            }
        }

        private void fillSrcTiles(int bx, int by)
        {
            if (srcRects != null && srcTilesIndexBrush != null)
            {
                for (int i = srcTilesIndexBrush.Count - 1; i >= 0; --i)
                {
                    int index = (int)srcTilesIndexBrush[i];
                    int ox = (int)srcTilesOXBrush[i];
                    int oy = (int)srcTilesOYBrush[i];

                    putTile(index, bx + ox, by + oy);
                }
            }
        }

        private void renderSrcTile(Graphics g, int index, int flip, int x, int y)
        {
            Image img = getTileImage(index);
            if (img != null && flip < flipTable.Length)
            {
                g.drawImage(img, x, y, flipTable[flip], 0);
            }
            
        }
        private void renderDstTile(Graphics g, int index, int flip, int x, int y)
        {
            Image img = getTileImage(index);

            if (img != null && flip < flipTable.Length)
            {
                if (D45.Checked)
                {
                    g.drawImage(img, x, y, flipTable[flip], 0);
                }
                else
                {
                    g.drawImage(img, x, y, KeyX, KeyY, CellW, CellH, flipTable[flip], 0);
                }
            }
        }
        private void renderDstKeyAndTile(Graphics g, bool key, bool tile, int index, int x, int y)
        {
            if (key)
            {
                String str = getTileKey(index);
                if (str != null)
                {
                    g.setColor(0xff, 0, 0, 0);
                    g.drawString(str, x + 1, y + 1, 0);
                    g.setColor(ImagesForm.ColorKey.ToArgb());
                    g.drawString(str, x, y, 0);
                }
            }
            if (tile)
            {
                y = y + CellH / 2;
                String str = index.ToString();
                g.setColor(0xff, 0, 0, 0);
                g.drawString(str, x + 1, y + 1, 0);
                g.setColor(ImagesForm.ColorTileID.ToArgb());
                g.drawString(str, x, y, 0);
            }
        }

        private void renderSrcTiles(Graphics g, int x, int y)
        {
            if (srcRects != null && srcTilesIndexBrush != null)
            {
                for (int i = srcTilesIndexBrush.Count - 1; i >= 0; --i)
                {
                    int index = (int)srcTilesIndexBrush[i];
                    int ox = (int)srcTilesOXBrush[i] * CellW;
                    int oy = (int)srcTilesOYBrush[i] * CellH;

                    renderSrcTile(g, index, 0, x + ox, y + oy);
                }
            }
        }

        private void renderTag(Graphics g, int type, int x, int y)
        {
            g.setColor(0xff, 0, 0, 0);
            switch (type)
            {
                case 0:
                    break;
                case 1:
                    g.drawRect(1 + x, 1 + y, CellW - 3, CellH - 3);
                    break;
                case 2:
                    g.drawLine(x, 1 + y, x + CellW - 1, 1 + y);
                    break;
                case 3:
                    g.drawLine(x, y + CellH - 1 - 1, x + CellW - 1, y + CellH - 1 - 1);
                    break;
                case 4:
                    g.drawLine(x + 1, y, x + 1, y + CellH - 1);
                    break;
                case 5:
                    g.drawLine(x + CellW - 1 - 1, y, x + CellW - 1 - 1, y + CellH - 1);
                    break;
                case 6:
                    g.drawLine(x, 1 + y, x + CellW - 1, 1 + y + CellH - 1);
                    break;
                case 7:
                    g.drawLine(x + CellW - 1, 1 + y, x, 1 + y + CellH - 1);
                    break;
            }
            g.setColor(0xff, 0xff, 0, 0);
            switch (type)
            {
                case 0:
                    break;
                case 1:
                    g.drawRect(x, y, CellW - 1, CellH - 1);
                    break;
                case 2:
                    g.drawLine(x, y, x + CellW - 1, y);
                    break;
                case 3:
                    g.drawLine(x, y + CellH - 1, x + CellW - 1, y + CellH - 1);
                    break;
                case 4:
                    g.drawLine(x, y, x, y + CellH - 1);
                    break;
                case 5:
                    g.drawLine(x + CellW - 1, y, x + CellW - 1, y + CellH - 1);
                    break;
                case 6:
                    g.drawLine(x, y, x + CellW - 1, y + CellH - 1);
                    break;
                case 7:
                    g.drawLine(x + CellW - 1, y, x, y + CellH - 1);
                    break;
            }

        }
        private void renderAnimate(Graphics g, int anim, int frame, int x, int y, Boolean tag)
        {
            try
            {
                if (listView1.Items.Count <= 0) return;

                if (anim >= 0 && anim < listView1.Items.Count)
                {
                    ArrayList animate = (ArrayList)(AnimIndexTable[listView1.Items[anim]]);
                    ArrayList flips = (ArrayList)(AnimFlipTable[listView1.Items[anim]]);
                    if (animate.Count <= 0) return;
                    if (flips.Count <= 0) return;

                    int tid = (int)(animate[frame % animate.Count]);
                    int tif = (int)(flips[frame % flips.Count]);

                    renderDstTile(g,
                        tid,
                        tif,
                        x, y);

                    
                    renderDstKeyAndTile(g, isShowKey.Checked, isShowTileID.Checked ,tid, x, y);
                    

                    if (tag)
                    {
                        g.setColor(0x00, 0x00, 0xff);
                        g.drawRect(x, y, CellW - 1, CellH - 1);
                    }
                }
            }
            catch (Exception err)
            {
            }
               
        }
        private void renderCombo(Graphics g, int anim, int x, int y, Boolean tag)
        {
            if (listView1.Items.Count <= 0) return;

            ArrayList animate = (ArrayList)(AnimIndexTable[listView1.Items[anim]]);
            ArrayList flips = (ArrayList)(AnimFlipTable[listView1.Items[anim]]);
            if (animate.Count <= 0) return;
            if (flips.Count <= 0) return;
            for (int frame = animate.Count - 1; frame >= 0; frame--)
            {
                int tid = (int)(animate[frame % animate.Count]);
                int tif = (int)(flips[frame % flips.Count]);
                renderDstTile(g,
                  tid,
                  tif,
                  x, y);
                if (frame==0){
                    renderDstKeyAndTile(g, isShowKey.Checked, isShowTileID.Checked, tid, x, y);
                }
            }
            if (tag)
            {
                g.setColor(0x00, 0xff, 0xff);
                g.drawRect(x, y, CellW - 1, CellH - 1);
            }
        }


        private System.Drawing.Image getAnimateImage(int anim, int frame)
        {
            try
            {
                if (listView1.Items.Count <= 0) return null;

                if (anim >= 0 && anim < listView1.Items.Count)
                {
                    ArrayList animate = (ArrayList)(AnimIndexTable[listView1.Items[anim]]);
                    if (animate.Count <= 0) return null;
                    return getTileImage((int)(animate[frame % animate.Count])).dimg;
                }
            }
            catch (Exception err)
            {
            }
            return null;
        }

        private System.Drawing.RotateFlipType getAnimateFlip(int anim, int frame)
        {
            try
            {
                if (listView1.Items.Count <= 0) return 0;

                if (anim >= 0 && anim < listView1.Items.Count)
                {
                    ArrayList flips = (ArrayList)(AnimFlipTable[listView1.Items[anim]]);
                    if (flips.Count <= 0) return 0;
                    return flipTable[(int)(flips[frame % flips.Count])];
                }
            }
            catch (Exception err)
            {
            }
            return 0;
        }

        private void refreshAnimateList()
        {
            try
            {
                //listView1.TileSize = new System.Drawing.Size(CellW + 1, CellH + 1);
               

                imageList1.ImageSize = new System.Drawing.Size(CellW, CellH);
                imageList1.Images.Clear();
               
                foreach (ListViewItem item in listView1.Items)
                {
                    item.Text = item.Index.ToString();

                    System.Drawing.Image icon = getAnimateImage(item.Index, 0);

                    icon = new System.Drawing.Bitmap(icon);
                    icon.RotateFlip(getAnimateFlip(item.Index, 0));

                    if (icon != null)
                    {
                        imageList1.Images.Add(icon);
                        item.ImageIndex = imageList1.Images.Count - 1;
                    }

                    switch (listView1.View)
                    {
                        case View.Tile:
                            break;
                    }

                }
            }
            catch (Exception err) { }
        }


        private void renderSrc(Graphics g, int x, int y, System.Drawing.Rectangle screen)
        {
            for (int i = 0; i < getTileCount(); i++)
            {
                if (getTileImage(i) != null &&
                    screen.IntersectsWith(new System.Drawing.Rectangle(
                        x + getTileImage(i).x, 
                        y + getTileImage(i).y,
                        getTileImage(i).getWidth()+1,
                        getTileImage(i).getHeight()+1)
                    ))
                {
                    renderSrcTile(g,i,0, x + getTileImage(i).x, y + getTileImage(i).y);
                }
            }
            if (srcIndex < getTileCount())
            {
                if (getTileImage(srcIndex) != null)
                {
                    renderSrcTile(g, srcIndex, flipIndex, x + getTileImage(srcIndex).x, y + getTileImage(srcIndex).y);
                }
            }
            if (srcIndexR < getTileCount())
            {
                if (getTileImage(srcIndexR) != null)
                {
                    renderSrcTile(g, srcIndexR, flipIndex, x + getTileImage(srcIndexR).x, y + getTileImage(srcIndexR).y);
                }
            }
        }
        private void renderDst(Graphics g, int x, int y, System.Drawing.Rectangle screen, 
            Boolean grid, 
            Boolean tag, 
            Boolean tagAnim,
            Boolean anim,
            int timer)
        {
            int sx = (screen.X >= 0) ? (screen.X / CellW) : 0;
            int sy = (screen.Y >= 0) ? (screen.Y / CellH) : 0;
            int sw = (screen.Width / CellW + 2 );
            int sh = (screen.Height / CellH + 2 );

            //Console.WriteLine(" sx="+sx+" sy"+sy+" sw"+sw+" sh"+sh);

            for (int by = sy ; by < sy + sh && by < YCount; by++)
            {
                for (int bx = sx; bx < sx + sw && bx < XCount; bx++)
                {
                    int drawx = x + bx * CellW;
                    int drawy = y + by * CellH;

                    if (MatrixAnim[by][bx] > 0 && MatrixAnim[by][bx] < listView1.Items.Count)
                    {
                        renderAnimate(g, MatrixAnim[by][bx], anim ? timer : 0, drawx, drawy, tagAnim);
                    }
                    else if (MatrixAnim[by][bx] < 0 && Math.Abs(MatrixAnim[by][bx]) < listView1.Items.Count)
                    {
                        renderCombo(g, Math.Abs(MatrixAnim[by][bx]), drawx, drawy, tagAnim);
                    }
                    else if (MatrixTile[by][bx] >= 0 && MatrixTile[by][bx] < getTileCount())
                    {
                        renderDstTile(g,MatrixTile[by][bx],MatrixFlip[by][bx],drawx, drawy);
                        renderDstKeyAndTile(g, isShowKey.Checked, isShowTileID.Checked, MatrixTile[by][bx], drawx, drawy);
                    }

                    if (tag && MatrixAnim[by][bx] != 0)
                    {
                        g.setColor(0, 0, 0xff);
                        g.drawRect(drawx, drawy, CellW, CellH);
                    }

                    if (tag && MatrixTag[by][bx] != 0)
                    {
                        renderTag(g, MatrixTag[by][bx], drawx, drawy);
                    }
                }
            }
       
            if (grid)
            {
                g.setColor(0x80, 0xff, 0xff, 0xff);
               
                for (int bx = sx; bx < sx + sw; bx++)
                {
                    g.drawLine(
                        x + bx * CellW, 
                        y + sy * CellH, 
                        x + bx * CellW, 
                        y + sy * CellH + sh * CellH);
                }
                for (int by = sy; by < sy + sh; by++)
                {
                    g.drawLine(
                        x + sx * CellW, 
                        y + by * CellH, 
                        x + sx * CellW + sw * CellW, 
                        y + by * CellH);
                }
            }
        }

        public void Render(Graphics g, int x, int y, System.Drawing.Rectangle screen, Boolean grid, Boolean tag, Boolean anim,int timer)
        {
            renderDst(g, x, y, screen, grid, tag, false, anim, timer);
        }

        // src
        #region src tiles

        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            for (int i = getTileCount() - 1; i >= 0; i--)
            {
                if (getTileImage(i)!=null)
                {
                    pictureBox1.Width = Math.Max(
                        pictureBox1.Width,
                        getTileImage(i).x + getTileImage(i).getWidth()
                        );
                    pictureBox1.Height = Math.Max(
                        pictureBox1.Height,
                        getTileImage(i).y + getTileImage(i).getHeight()
                        );
                    //break;
                }
            }
                

            Graphics g = new Graphics(e.Graphics);


            renderSrc(g, 0, 0 ,
                new System.Drawing.Rectangle(
                    - pictureBox1.Location.X,
                    - pictureBox1.Location.Y,
                    panel3.Width,
                    panel3.Height
                )
            );



            if (toolTilesBrush.Checked)
            {
                System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0, 0, 0));
                System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;
                e.Graphics.FillRectangle(brush, srcRects);
                e.Graphics.DrawRectangle(pen, srcRects.X, srcRects.Y, srcRects.Width, srcRects.Height);
            }
            else if (toolTileBrush.Checked || toolAnimBrush.Checked || toolSelecter.Checked)
            {

                System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0, 0, 0));
                System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;
                e.Graphics.FillRectangle(brush, srcRect);
                e.Graphics.DrawRectangle(pen, srcRect.X, srcRect.Y, srcRect.Width, srcRect.Height);

                System.Drawing.Pen penR = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0xff, 0xff, 0xff));
                System.Drawing.Brush brushR = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0x80, 0x80, 0x80)).Brush;
                e.Graphics.FillRectangle(brushR, srcRectR);
                e.Graphics.DrawRectangle(penR, srcRectR.X, srcRectR.Y, srcRectR.Width, srcRectR.Height);
            }

        }
        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            if (toolTilesBrush.Checked)
            {
                if (e.Button == MouseButtons.Left)
                {
                    srcPX = e.X / CellW * CellW;
                    srcPY = e.Y / CellH * CellW;

                    srcRects = new System.Drawing.Rectangle(srcPX, srcPY, CellW, CellH);
                }
                pictureBox1.Refresh();
            }
            else if (toolTileBrush.Checked || toolAnimBrush.Checked || toolSelecter.Checked) 
            {
                System.Drawing.Rectangle dst = new System.Drawing.Rectangle(0, 0, 1, 1);
                for (int i = 0; i < getTileCount(); i++)
                {
                    if (getTileImage(i) != null && getTileImage(i).killed == false)
                    {
                        dst.X = getTileImage(i).x;
                        dst.Y = getTileImage(i).y;
                        dst.Width = getTileImage(i).getWidth();
                        dst.Height = getTileImage(i).getHeight();

                        if (dst.Contains(e.X, e.Y))
                        {
                            if (e.Button == MouseButtons.Left)
                            {
                                srcRect.X = dst.X;
                                srcRect.Y = dst.Y;
                                srcRect.Width = dst.Width;
                                srcRect.Height = dst.Height;

                                srcIndex = i;
                                //srcImage = getTileImage(i);

                                toolStripLabel1.Text =
                                    "第" + i + "号" +
                                    " 宽：" + getTileImage(i).getWidth() +
                                    " 高：" + getTileImage(i).getHeight();

                                pictureBox1.Refresh();
                                break;
                            }
                            if (e.Button == MouseButtons.Right)
                            {
                                srcRectR.X = dst.X;
                                srcRectR.Y = dst.Y;
                                srcRectR.Width = dst.Width;
                                srcRectR.Height = dst.Height;

                                srcIndexR = i;
                                //srcImage = getTileImage(i);

                                toolStripLabel1.Text =
                                    "第" + i + "号" +
                                    " 宽：" + getTileImage(i).getWidth() +
                                    " 高：" + getTileImage(i).getHeight();

                                pictureBox1.Refresh();
                                break;
                            }

                        }
                    }
                }
                textBox1.Focus();
            }
        }
       
        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            if (toolTilesBrush.Checked)
            {
                if (srcRects!=null && e.Button==MouseButtons.Left)
                {
                    srcQX = e.X / CellW * CellW;
                    srcQY = e.Y / CellH * CellW;

                    srcRects.X = Math.Min(srcPX, srcQX);
                    srcRects.Y = Math.Min(srcPY, srcQY);
                    srcRects.Width  = Math.Abs(srcPX - srcQX) + CellW;
                    srcRects.Height = Math.Abs(srcPY - srcQY) + CellW;

                    pictureBox1.Refresh();
                }
            }
        }
        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {
            if (toolTilesBrush.Checked)
            {
                if (srcRects != null && e.Button == MouseButtons.Left)
                {
                    srcTilesIndexBrush = new ArrayList();
                    srcTilesOXBrush = new ArrayList();
                    srcTilesOYBrush = new ArrayList();

                    for (int i = 0; i < getTileCount(); i++)
                    {
                        Image tile = getTileImage(i);

                        if (tile != null && tile.killed == false)
                        {
                            if (srcRects.IntersectsWith(new System.Drawing.Rectangle(tile.x, tile.y, tile.getWidth(), tile.getHeight())))
                            {
                                int ox = (tile.x - srcRects.X) / CellW;
                                int oy = (tile.y - srcRects.Y) / CellH;

                                srcTilesIndexBrush.Add(i);
                                srcTilesOXBrush.Add(ox);
                                srcTilesOYBrush.Add(oy);
                            }
                        }
                    }

                    pictureBox1.Refresh();
                }
            }
        }

        private void toolStripMenuItem10_Click(object sender, EventArgs e)
        {
            try
            {
                if (!sender.Equals(toolStripMenuItem10)) toolStripMenuItem10.Checked = false;
                if (!sender.Equals(toolStripMenuItem11)) toolStripMenuItem11.Checked = false;
                if (!sender.Equals(toolStripMenuItem12)) toolStripMenuItem12.Checked = false;
                if (!sender.Equals(toolStripMenuItem13)) toolStripMenuItem13.Checked = false;
                if (!sender.Equals(toolStripMenuItem14)) toolStripMenuItem14.Checked = false;
                if (!sender.Equals(toolStripMenuItem15)) toolStripMenuItem15.Checked = false;
                if (!sender.Equals(toolStripMenuItem16)) toolStripMenuItem16.Checked = false;
                if (!sender.Equals(toolStripMenuItem17)) toolStripMenuItem17.Checked = false;

                ((ToolStripMenuItem)sender).Checked = true;
                toolStripDropDownButton3.Image = ((ToolStripMenuItem)sender).Image;

                //
                if (sender.Equals(toolStripMenuItem10)) flipIndex = 0;
                if (sender.Equals(toolStripMenuItem11)) flipIndex = 1;
                if (sender.Equals(toolStripMenuItem12)) flipIndex = 2;
                if (sender.Equals(toolStripMenuItem13)) flipIndex = 3;
                if (sender.Equals(toolStripMenuItem14)) flipIndex = 4;
                if (sender.Equals(toolStripMenuItem15)) flipIndex = 5;
                if (sender.Equals(toolStripMenuItem16)) flipIndex = 6;
                if (sender.Equals(toolStripMenuItem17)) flipIndex = 7;

                pictureBox1.Refresh();
            }
            catch (Exception err)
            {
            }
        }

        #endregion

        //property
        private void dstChangeMapSize(int xcount, int ycount)
        {
            XCount = xcount;
            YCount = ycount;

            if (YCount != MatrixTile.Length || XCount != MatrixTile[0].Length)
            {
                int[][] matrixTile = new int[YCount][];
                int[][] matrixTag = new int[YCount][];
                int[][] matrixAnim = new int[YCount][];
                int[][] matrixFlip = new int[YCount][];
                for (int y = 0; y < YCount; y++)
                {
                    matrixTile[y] = new int[XCount];
                    matrixTag[y] = new int[XCount];
                    matrixAnim[y] = new int[XCount];
                    matrixFlip[y] = new int[XCount];
                    for (int x = 0; x < XCount; x++)
                    {
                        if (y < MatrixTile.Length && x < MatrixTile[y].Length)
                        {
                            matrixTile[y][x] = MatrixTile[y][x];
                            matrixTag[y][x] = MatrixTag[y][x];
                            matrixAnim[y][x] = MatrixAnim[y][x];
                            matrixFlip[y][x] = MatrixFlip[y][x];
                        }
                    }
                }

                MatrixTile = matrixTile;
                MatrixTag = matrixTag;
                MatrixAnim = matrixAnim;
                MatrixFlip = matrixFlip;

                MapLoc.Width = CellW * XCount;
                MapLoc.Height = CellH * YCount;
            }

            refreshMap();
        }
        private void numericUpDown2_Enter_1(object sender, EventArgs e)
        {
            numericUpDown2.Value = XCount;
        }
        private void numericUpDown3_Enter_1(object sender, EventArgs e)
        {
            numericUpDown3.Value = YCount;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            dstChangeMapSize((int)numericUpDown2.Value, (int)numericUpDown3.Value);
        }

        private void dstChageCellSize(int cellw,int cellh)
        {
            CellW = cellw;
            CellH = cellh;

            pictureBox3.Width = CellW;
            pictureBox3.Height = CellH;

            pictureBox4.Left = pictureBox3.Location.X + 3 + CellW;

            pictureBox4.Width = CellW;
            pictureBox4.Height = CellH;

            MapLoc.Width = CellW * XCount;
            MapLoc.Height = CellH * YCount;

            refreshMap();
        }
        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            dstChageCellSize((int)numericUpDown4.Value, CellH);
        }
        private void numericUpDown5_ValueChanged(object sender, EventArgs e)
        {
            dstChageCellSize(CellW, (int)numericUpDown5.Value);
        }

        // dst
        private void toolStriptoolStripButton_Clicked(object sender, EventArgs e)
        {
            try
            {
                dstRect.X = dstPX / CellW * CellW;
                dstRect.Y = dstPY / CellH * CellH;
                dstRect.Width = 0;
                dstRect.Height = 0;

                if (!sender.Equals(toolSelecter)) toolSelecter.Checked = false;
                if (!sender.Equals(toolTileBrush)) toolTileBrush.Checked = false;
                if (!sender.Equals(toolTilesBrush)) toolTilesBrush.Checked = false;
                if (!sender.Equals(toolCDBrush)) toolCDBrush.Checked = false;
                if (!sender.Equals(toolAnimBrush)) toolAnimBrush.Checked = false;
                ((ToolStripButton)sender).Checked = true;

                refreshMap();
            }
            catch (Exception err)
            {
            }

        }
        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            try
            {
                if (!sender.Equals(toolStripMenuItem2)) toolStripMenuItem2.Checked = false;
                if (!sender.Equals(toolStripMenuItem3)) toolStripMenuItem3.Checked = false;
                if (!sender.Equals(toolStripMenuItem4)) toolStripMenuItem4.Checked = false;
                if (!sender.Equals(toolStripMenuItem5)) toolStripMenuItem5.Checked = false;
                if (!sender.Equals(toolStripMenuItem6)) toolStripMenuItem6.Checked = false;
                if (!sender.Equals(toolStripMenuItem7)) toolStripMenuItem7.Checked = false;
                if (!sender.Equals(toolStripMenuItem8)) toolStripMenuItem8.Checked = false;
                if (!sender.Equals(toolStripMenuItem9)) toolStripMenuItem9.Checked = false;

                ((ToolStripMenuItem)sender).Checked = true;
                toolStripDropDownButton1.Image = ((ToolStripMenuItem)sender).Image;

                //
                if (sender.Equals(toolStripMenuItem2)) tagIndex = 0;
                if (sender.Equals(toolStripMenuItem3)) tagIndex = 1;
                if (sender.Equals(toolStripMenuItem4)) tagIndex = 2;
                if (sender.Equals(toolStripMenuItem5)) tagIndex = 3;
                if (sender.Equals(toolStripMenuItem6)) tagIndex = 4;
                if (sender.Equals(toolStripMenuItem7)) tagIndex = 5;
                if (sender.Equals(toolStripMenuItem8)) tagIndex = 6;
                if (sender.Equals(toolStripMenuItem9)) tagIndex = 7;
            }
            catch (Exception err)
            {
            }

            
        }
        private void toolStripButton11_Click(object sender, EventArgs e)
        {
            refreshMap();
        }

        private void 填充ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            fillTile(srcIndex,flipIndex);
            
        }
        private void 填充当前动画ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            fillAnimate(curAnimate);
        }
        private void 填充当前地形ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            fillTag(tagIndex);
        }
        private void 复制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            copyDst();
        }
        private void 粘贴ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            paseDst();
        }
  //minimap
        private void toolStripButton23_Click(object sender, EventArgs e)
        {
            try
            {
                System.Drawing.Image image = new System.Drawing.Bitmap(getWidth(), getHeight());
                System.Drawing.Graphics dg = System.Drawing.Graphics.FromImage(image);
                Render(new Graphics(dg), 0, 0, new System.Drawing.Rectangle(0, 0, getWidth(), getHeight()), false, false, false, 0);

                MapMini mini = new MapMini(image);
                mini.Show();
            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace);
            }
        }

        //animate
        private void toolStripButton15_Click(object sender, EventArgs e)
        {
            addAnimate();
            refreshAnimateList(); 
        }
        private void toolStripButton16_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("删除后，动画顺序将重新调整！", "确定要删除？", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                delAnimate();
                refreshAnimateList();
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (!this.Visible) return;

            if (toolStripButton5.Checked)
            {
                if (trackBar1.Value < trackBar1.Maximum)
                {
                    trackBar1.Value++;
                }
                else
                {
                    trackBar1.Value = trackBar1.Minimum;
                }
                
                pictureBox4.Width = trackBar1.Maximum * CellW + CellW;

                timer1.Interval = (int)numericUpDown1.Value;

            }

            if (toolStripButton14.Checked)
            {
                refreshMap();

                timer1.Interval = (int)numericUpDown1.Value;

            }


            curTime++;
        }

        private void listView1_ItemActivate(object sender, EventArgs e)
        {

        }
        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listView1.SelectedItems != null && listView1.SelectedItems.Count > 0)
            {
                curAnimate = listView1.Items.IndexOf(listView1.SelectedItems[0]);
                toolStripLabel2.Text = "动画："+curAnimate.ToString();
                curFrame = 0;

                trackBar1.Maximum = getFrame(curAnimate).Count - 1;
                trackBar1.Value = 0;

                pictureBox4.Width = getFrame(curAnimate).Count * CellW;
                trackBar1_ValueChanged(sender, e);
            }

        }
        private void listView1_DrawItem(object sender, DrawListViewItemEventArgs e)
        {

        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            try
            {
                if (getFrame(curAnimate) != null)
                {
                    addFrame(curFrame, srcIndex, flipIndex);

                    curFrame = Math.Max(curFrame + 1, 0);

                    trackBar1.Maximum = getFrame(curAnimate).Count - 1;
                    trackBar1.Value = curFrame;

                    pictureBox4.Width = getFrame(curAnimate).Count * CellW;
                   
                }
            }
            catch (Exception err) { }
        }
        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            try
            {
                if (getFrame(curAnimate) != null)
                {
                    delFrame(curFrame);

                    curFrame = Math.Min(curFrame, getFrame(curAnimate).Count - 1);

                    trackBar1.Maximum = getFrame(curAnimate).Count - 1;
                    trackBar1.Value = curFrame;

                    pictureBox4.Width = getFrame(curAnimate).Count * CellW;
                 
                }
            }
            catch (Exception err) { }
        }
        
        private void pictureBox3_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = new Graphics(e.Graphics);
            if (IsMultiLayer.Checked)
            {
                if (curAnimate >= 0)
                {
                    renderCombo(g, curAnimate, 0, 0, false);
                }
            }
            else
            {
                if (curAnimate >= 0 && curFrame >= 0)
                {
                    renderAnimate(g, curAnimate, curFrame, 0, 0, false);
                }
            }

        }


        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            if (getFrame(curAnimate) != null)
            {
                trackBar1.Value = trackBar1.Minimum;
            }
            
        }
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            if (getFrame(curAnimate) != null)
            {
                if (trackBar1.Value > trackBar1.Minimum)
                {
                    trackBar1.Value -= 1;
                }
                else
                {
                    trackBar1.Value = trackBar1.Maximum;
                }
                
            }
        }
        private void toolStripButton5_Click(object sender, EventArgs e)
        {
            //if (toolStripButton5.Checked)
            //{
            //    timer1.Start();
            //}
            //else
            //{
            //    timer1.Stop();
            //}
        }
        private void toolStripButton6_Click(object sender, EventArgs e)
        {
            if (getFrame(curAnimate) != null)
            {
                if (trackBar1.Value < trackBar1.Maximum)
                {
                    trackBar1.Value += 1;
                }
                else
                {
                    trackBar1.Value = trackBar1.Minimum;
                }
            }
        }
        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            if (getFrame(curAnimate) != null)
            {
                trackBar1.Value = trackBar1.Maximum;
            }
            
        }

        private void toolStripButton19_Click(object sender, EventArgs e)
        {
            if (trackBar1.Value > trackBar1.Minimum)
            {
                exchangeFrame(trackBar1.Value, trackBar1.Value - 1);
                trackBar1.Value = trackBar1.Value - 1;
            }
        }
        private void toolStripButton18_Click(object sender, EventArgs e)
        {
            if (trackBar1.Value < trackBar1.Maximum)
            {
                exchangeFrame(trackBar1.Value, trackBar1.Value + 1);
                trackBar1.Value = trackBar1.Value + 1;
            }
        }

        private void toolStripButton20_Click(object sender, EventArgs e)
        {
            if (curAnimate > 1)
            {
                exchangeAnimate(curAnimate, curAnimate-1);
            }
            refreshAnimateList(); 
        }
        private void toolStripButton21_Click(object sender, EventArgs e)
        {
            if (curAnimate < listView1.Items.Count - 1 && curAnimate > 0)
            {
                exchangeAnimate(curAnimate, curAnimate + 1);
            }
            refreshAnimateList(); 
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            curFrame = trackBar1.Value;
        }
        private void trackBar1_ValueChanged(object sender, EventArgs e)
        {
            curFrame = trackBar1.Value;

            pictureBox4.Refresh();
            pictureBox3.Refresh();

            toolStripStatusLabel2.Text =
                " 帧：" + (trackBar1.Value ) + "/" + (trackBar1.Maximum + 1 ) +
                " FPS=" + (((float)1000) / ((float)timer1.Interval));


        }

        private void pictureBox4_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = new Graphics(e.Graphics);

            for (int i = trackBar1.Minimum; i <= trackBar1.Maximum; i++)
            {
                renderAnimate(g, curAnimate, i, CellW * i, 0, false);

                if (trackBar1.Value == i)
                {
                    g.setColor(0x20, 0xff, 0xff, 0xff);
                    g.fillRect(CellW * i, 0, CellW - 1, CellW - 1);
                    g.setColor(0xff, 0, 0, 0);
                    g.drawRect(CellH * i, 0, CellH - 1, CellH - 1);
                }
            }
            
        }
        private void pictureBox4_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                if (getFrame(curAnimate) != null)
                {
                    trackBar1.Value = e.X / CellW;
                }
            }
        }

        private void toolStripButton22_Click(object sender, EventArgs e)
        {

        }


        //adjust flip
        private void textBox1_KeyDown(object sender, KeyEventArgs e)
        {

            switch (e.KeyCode)
            {
                case Keys.PageUp:
                    if (toolStripMenuItem10.Checked) toolStripMenuItem10_Click(toolStripMenuItem17, null);
                    else if (toolStripMenuItem11.Checked) toolStripMenuItem10_Click(toolStripMenuItem10, null);
                    else if (toolStripMenuItem12.Checked) toolStripMenuItem10_Click(toolStripMenuItem11, null);
                    else if (toolStripMenuItem13.Checked) toolStripMenuItem10_Click(toolStripMenuItem12, null);
                    else if (toolStripMenuItem14.Checked) toolStripMenuItem10_Click(toolStripMenuItem13, null);
                    else if (toolStripMenuItem15.Checked) toolStripMenuItem10_Click(toolStripMenuItem14, null);
                    else if (toolStripMenuItem16.Checked) toolStripMenuItem10_Click(toolStripMenuItem15, null);
                    else if (toolStripMenuItem17.Checked) toolStripMenuItem10_Click(toolStripMenuItem16, null);
                    break;

                case Keys.PageDown: 
                    if (toolStripMenuItem10.Checked) toolStripMenuItem10_Click(toolStripMenuItem11, null);
                    else if (toolStripMenuItem11.Checked) toolStripMenuItem10_Click(toolStripMenuItem12, null);
                    else if (toolStripMenuItem12.Checked) toolStripMenuItem10_Click(toolStripMenuItem13, null);
                    else if (toolStripMenuItem13.Checked) toolStripMenuItem10_Click(toolStripMenuItem14, null);
                    else if (toolStripMenuItem14.Checked) toolStripMenuItem10_Click(toolStripMenuItem15, null);
                    else if (toolStripMenuItem15.Checked) toolStripMenuItem10_Click(toolStripMenuItem16, null);
                    else if (toolStripMenuItem16.Checked) toolStripMenuItem10_Click(toolStripMenuItem17, null);
                    else if (toolStripMenuItem17.Checked) toolStripMenuItem10_Click(toolStripMenuItem10, null);
                    break;
            }
            switch (e.KeyCode)
            {
                case Keys.PageUp:
                case Keys.PageDown:
                    refreshMap();
                    break;
            }


            if (e.Control && e.KeyCode == Keys.A)
            {
                dstPX = 0;
                dstPY = 0;
                dstQX = getWidth();
                dstQY = getHeight();
            
                dstBX = 0;
                dstBY = 0;


                dstRect.X = 0;
                dstRect.Y = 0;
                dstRect.Width = getWidth();
                dstRect.Height = getHeight();

                refreshMap();
            }
            else if (e.Control && e.KeyCode == Keys.X) 
            {
                clipDst(); 
                refreshMap();
            }
            else if (e.Control && e.KeyCode == Keys.C)
            {
                copyDst(); 
                refreshMap();
            }
            else if (e.Control && e.KeyCode == Keys.V)
            {
                paseDst(); 
                refreshMap();
            }
            else if (e.KeyCode == Keys.Delete)
            {
                clearDst();
                refreshMap();
            }
        }

        private void CellKeyPixelX_ValueChanged(object sender, EventArgs e)
        {
            KeyX = (int)CellKeyPixelX.Value;

            refreshMap();
        }

        private void CellKeyPixelY_ValueChanged(object sender, EventArgs e)
        {
            KeyY = (int)CellKeyPixelY.Value;

            refreshMap();
        }

        private void 统计相同的地块ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                int dy = dstRect.Y / CellW;
                int dx = dstRect.X / CellH;

                int curTile = getTileID(dx, dy);
                int curFlip = getTileFlip(dx, dy);
                int curFlag = getTagID(dx, dy);
                int curAnim = getAnimateID(dx, dy);

                long tileCount = 0;
                long flagCount = 0;
                long animCount = 0;

                for (int x = 0; x < XCount; x++)
                {
                    for (int y = 0; y < YCount; y++)
                    {
                        if (getTileID(x, y) == curTile && getTileFlip(x, y) == curFlip)
                        {
                            tileCount++;
                        }

                        if (getTagID(x, y) == curFlag)
                        {
                            flagCount++;
                        }

                        if (getAnimateID(x, y) == curAnim)
                        {
                            animCount++;
                        }

                    }
                }

                MessageBox.Show(
                    "相同的 地块 : " + tileCount + "\n" +
                    "相同的 判定 : " + flagCount + "\n" +
                    "相同的 动画 : " + animCount + "\n"
                    );


            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace);
            }




        }

        private void 详细ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1.View = View.Details;
            listViewAnim.Text = "详细";
            refreshAnimateList();
        }

        private void 列表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1.View = View.List;
            listViewAnim.Text = "列表";
            refreshAnimateList();
        }

        private void 大图标ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1.View = View.LargeIcon;
            listViewAnim.Text = "大图标";
            refreshAnimateList();
        }

        private void 小图标ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1.View = View.SmallIcon;
            listViewAnim.Text = "小图标";
            refreshAnimateList();
        }

        private void 平铺ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            listView1.View = View.Tile;
            listViewAnim.Text = "平铺";
            refreshAnimateList();
            //listView1.TileSize = new System.Drawing.Size(CellW, CellH);
        }

        private void toolStripButton3_Click_1(object sender, EventArgs e)
        {
            refreshAnimateList();
        }

        private void toolStripButton4_Click_1(object sender, EventArgs e)
        {
            try {
                BufferdMiniMap bf = new BufferdMiniMap(this);
                bf.Show();
            }
            catch (Exception err) { }
        }


        private void btnSaveMiniMap_Click(object sender, EventArgs e)
        {
            try
            {
                System.Drawing.Bitmap minimap = new System.Drawing.Bitmap(
                    XCount, YCount//,
                    //System.Drawing.Imaging.PixelFormat.Format16bppArgb1555
                    );
                System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(minimap);

                for (int x = 0; x < XCount; x++)
                {
                    for (int y = 0; y < YCount; y++)
                    {
                        javax.microedition.lcdui.Image img = getTileImage(x, y);

                        if (img != null)
                        {
                            g.FillRectangle(img.getColorKeyBrush(), x, y, 1, 1);
                        }
                    }
                }
                
                SaveFileDialog sfd = new SaveFileDialog();
                sfd.InitialDirectory = ProjectForm.workSpace;
                sfd.Filter = "bmp图片(*.bmp)|*.bmp";
                sfd.FileName = this.id + ".bmp";
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    minimap.Save(sfd.FileName, System.Drawing.Imaging.ImageFormat.Bmp);
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }


        //public Panel getViewPanel() { return panel2; }

        public System.Drawing.Size getMapSize()
        {
            return MapLoc.Size;
        }

        public System.Drawing.Rectangle getMapViewRectangle()
        {
            return new System.Drawing.Rectangle(
                hScrollBar1.Value,
                vScrollBar1.Value, 
                panel2.Width, 
                panel2.Height);
        }


        public void setMapViewLoc(int x, int y)
        {
            hScrollBar1.Value = x;
            vScrollBar1.Value = y;
            MapLoc.X = -x;
            MapLoc.Y = -y;
        }

        private void panel2_Scroll(object sender, ScrollEventArgs e)
        {
            if(MiniView!=null){
                MiniView.relocation(this);
            }
        }
        private void hScrollBar1_Scroll(object sender, ScrollEventArgs e)
        {

        }
        private void vScrollBar1_Scroll(object sender, ScrollEventArgs e)
        {

        }
        private void hScrollBar1_ValueChanged(object sender, EventArgs e)
        {
            MapLoc.X = -hScrollBar1.Value;
            refreshMap();
        }
        private void vScrollBar1_ValueChanged(object sender, EventArgs e)
        {
            MapLoc.Y = -vScrollBar1.Value;
            refreshMap();
        }


        private void panel2_Paint(object sender, PaintEventArgs e)
        {
            hScrollBar1.Maximum = MapLoc.Width;
            hScrollBar1.Minimum = 0;
            hScrollBar1.LargeChange = panel2.Width;

            vScrollBar1.Maximum = MapLoc.Height;
            vScrollBar1.Minimum = 0;
            vScrollBar1.LargeChange = panel2.Height;
        }
        private void panel2_Resize(object sender, EventArgs e)
        {
            hScrollBar1.Maximum = MapLoc.Width;
            hScrollBar1.Minimum = 0;
            hScrollBar1.LargeChange = panel2.Width;

            vScrollBar1.Maximum = MapLoc.Height;
            vScrollBar1.Minimum = 0;
            vScrollBar1.LargeChange = panel2.Height;

        }



        private void isShowKey_Click(object sender, EventArgs e)
        {
            refreshMap();
        }

        private void isShowTileID_Click(object sender, EventArgs e)
        {
            refreshMap();
        }

        #region Dest Map 
        
        public void refreshMap()
        {
            hScrollBar1.Maximum = MapLoc.Width;
            hScrollBar1.Minimum = 0;
            hScrollBar1.LargeChange = panel2.Width;

            vScrollBar1.Maximum = MapLoc.Height;
            vScrollBar1.Minimum = 0;
            vScrollBar1.LargeChange = panel2.Height;

            /*MapRegion.Location = new System.Drawing.Point(
                hScrollBar1.Value,
                vScrollBar1.Value
                );*/

            /*MapRegion.Location = new System.Drawing.Point(
                -MapLoc.Location.X,
                -MapLoc.Location.Y
                );*/

            MapRegion.Size = new System.Drawing.Size(
                panel2.Width,
                panel2.Height
                );
            MapRegion.Refresh();

        }

        private void MapLoc_Paint(object sender, PaintEventArgs e)
        {
            refreshMap();
        }

        private void renderMapSelecter(Graphics g, PaintEventArgs e, System.Drawing.Rectangle viewRect)
        {

            System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0, 0, 0));
            System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x40, 0xff, 0xff, 0xff)).Brush;

            int px = -viewRect.X;
            int py = -viewRect.Y;

            int bx = dstQX / CellW * CellW + px;
            int by = dstQY / CellH * CellH + py;
            int bw = CellW - 1;
            int bh = CellH - 1;

            if (tabControl1.SelectedTab == tabPageTerrain)
            {
                mapTerrains1.renderSelectRegion(e.Graphics, bx, by, dstBX, dstBY, this);

                e.Graphics.DrawRectangle(System.Drawing.Pens.White, bx, by, bw, bh);

            }
            else if (tabControl1.SelectedTab == tabPageTile)
            {
                if (toolSelecter.Checked)
                {
                    e.Graphics.FillRectangle(
                        brush,
                        px + dstRect.X,
                        py + dstRect.Y,
                        dstRect.Width - 1,
                        dstRect.Height - 1
                        );
                    e.Graphics.DrawRectangle(
                        pen,
                        px + dstRect.X,
                        py + dstRect.Y,
                        dstRect.Width - 1,
                        dstRect.Height - 1
                        );
                }
                else if (toolTileBrush.Checked)
                {
                    System.Drawing.Pen lpen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0, 0, 0));

                    renderDstTile(g, srcIndex, flipIndex, bx, by);

                    if (D45.Checked)
                    {
                        int x = dstQX / CellW * CellW;
                        int y = dstQY / CellH * CellH;
                        int w = CellW * 2;
                        int h = CellH * 2;

                        e.Graphics.DrawLine(lpen, px + x + 0x000, py + y + CellH, px + x + CellW, py + y + 0x000);//         /
                        e.Graphics.DrawLine(lpen, px + x + CellW, py + y + 0x000, px + x + w + 0, py + y + CellH);//       \
                        e.Graphics.DrawLine(lpen, px + x + 0x000, py + y + CellH, px + x + CellW, py + y + h + 0);//     \
                        e.Graphics.DrawLine(lpen, px + x + CellW, py + y + h + 0, px + x + w + 0, py + y + CellH);//   /
                    }
                    else
                    {
                        e.Graphics.DrawRectangle(lpen, bx, by, bw, bh);
                    }
                }
                else if (toolTilesBrush.Checked)
                {
                    renderSrcTiles(g, bx, by);
                }
                else if (toolCDBrush.Checked)
                {
                    System.Drawing.Pen lpen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0xff, 0, 0));
                    e.Graphics.DrawRectangle(lpen, bx, by, bw, bh);
                }
                else if (toolAnimBrush.Checked)
                {
                    System.Drawing.Pen lpen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xff, 0, 0, 0xff));

                    int x = dstQX / CellW * CellW;
                    int y = dstQY / CellH * CellH;
                    int w = CellW * 2;
                    int h = CellH * 2;
                    if (D45.Checked)
                    {
                        e.Graphics.DrawLine(lpen, px + x + 0x000, py + y + CellH, px + x + CellW, py + y + 0x000);//         /
                        e.Graphics.DrawLine(lpen, px + x + CellW, py + y + 0x000, px + x + w + 0, py + y + CellH);//       \
                        e.Graphics.DrawLine(lpen, px + x + 0x000, py + y + CellH, px + x + CellW, py + y + h + 0);//     \
                        e.Graphics.DrawLine(lpen, px + x + CellW, py + y + h + 0, px + x + w + 0, py + y + CellH);//   /
                    }
                    else
                    {
                        e.Graphics.DrawRectangle(lpen, bx, by, bw, bh);
                    }
                }
            }

            

        }

        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = new Graphics(e.Graphics);

            try
            {
                System.Drawing.Rectangle viewRect = getMapViewRectangle();

                renderDst(g,
                    -viewRect.X,
                    -viewRect.Y,
                    viewRect,
                    toolStripButton11.Checked,
                    toolStripButton12.Checked || toolCDBrush.Checked,
                    toolAnimBrush.Checked,
                    toolStripButton14.Checked,
                    curTime
                    );

                renderMapSelecter(g, e, viewRect);
            }
            catch (Exception err)
            {
            }


            // Console.WriteLine(""+pictureBox2.Location.Y);
        }
        private void pictureBox2_MouseLeave(object sender, EventArgs e)
        {
            toolStripStatusLabel1.Text = "地图";
        }
        private void pictureBox2_MouseMove(object sender, MouseEventArgs ed)
        {
            System.Drawing.Rectangle viewRect = getMapViewRectangle();

            int wx = ed.X + viewRect.X;
            int wy = ed.Y + viewRect.Y;


            dstQX = wx < 0 ? 0 : (wx > MapLoc.Width - 1 ? MapLoc.Width - 1 : wx);
            dstQY = wy < 0 ? 0 : (wy > MapLoc.Height - 1 ? MapLoc.Height - 1 : wy);

            dstBX = dstQX / CellW;
            dstBY = dstQY / CellH;


            toolStripStatusLabel1.Text = "格:" + dstBX + "," + dstBY;

         
            if (tabControl1.SelectedTab == tabPageTerrain)
            {
                if (ed.Button == MouseButtons.Left)
                {
                    mapTerrains1.putSelectRegion(this, dstBX, dstBY);
                } 
                else if (ed.Button == MouseButtons.Right)
                {
                    //mapTerrains1.clearSelectRegion(this, srcIndexR, flipIndex, dstBX, dstBY);
                    putTile(srcIndexR, dstBX, dstBY);
                    putFlip(flipIndex, dstBX, dstBY);
                    putTag(0, dstBX, dstBY);
                    putAnimate(0, dstBX, dstBY, IsMultiLayer.Checked);
                }
            }
            else
            {
                if (toolSelecter.Checked)
                {
                    if (dstIsDown)
                    {
                        int l = Math.Min(dstPX, dstQX) / CellW;
                        int r = Math.Max(dstPX, dstQX) / CellW;
                        int t = Math.Min(dstPY, dstQY) / CellH;
                        int b = Math.Max(dstPY, dstQY) / CellH;

                        dstRect.X = l * CellW;
                        dstRect.Y = t * CellH;
                        dstRect.Width = (r - l + 1) * CellW;
                        dstRect.Height = (b - t + 1) * CellH;

                        toolStripStatusLabel1.Text += " 选择:" + (r - l + 1) + "x" + (b - t + 1) + "格";
                    }
                }
                else if (ed.Button == MouseButtons.Left)
                {

                    if (toolTileBrush.Checked)
                    {
                        putTile(srcIndex, dstBX, dstBY);
                        putFlip(flipIndex, dstBX, dstBY);
                    }
                    else if (toolTilesBrush.Checked)
                    {
                        fillSrcTiles(dstBX, dstBY);
                    }
                    else if (toolCDBrush.Checked)
                    {
                        putTag(tagIndex, dstBX, dstBY);
                    }
                    else if (toolAnimBrush.Checked)
                    {
                        putAnimate(curAnimate, dstBX, dstBY, IsMultiLayer.Checked);
                    }
                    

                }
                else if (ed.Button == MouseButtons.Right)
                {
                    if (toolTileBrush.Checked)
                    {
                        putTile(srcIndexR, dstBX, dstBY);
                        putFlip(flipIndex, dstBX, dstBY);
                    }
                    else if (toolCDBrush.Checked)
                    {
                        putTag(0, dstBX, dstBY);
                    }
                    else if (toolAnimBrush.Checked)
                    {
                        putAnimate(0, dstBX, dstBY, IsMultiLayer.Checked);
                    }
                }

            }

            refreshMap();
        }
        private void pictureBox2_MouseDown(object sender, MouseEventArgs ed)
        {
            System.Drawing.Rectangle viewRect = getMapViewRectangle();

            int wx = ed.X + viewRect.X;
            int wy = ed.Y + viewRect.Y;

           

            if (ed.Button == MouseButtons.Left || ed.Button == MouseButtons.Right)
            {
                dstPX = wx;
                dstPY = wy;
                dstQX = wx;
                dstQY = wy;
                dstIsDown = true;

                dstBX = dstQX / CellW;
                dstBY = dstQY / CellH;
            }

            if (tabControl1.SelectedTab == tabPageTerrain)
            {
                if (ed.Button == MouseButtons.Left)
                {
                    mapTerrains1.putSelectRegion(this, dstBX, dstBY);
                }
                else if (ed.Button == MouseButtons.Right)
                {
                    //mapTerrains1.clearSelectRegion(this, srcIndexR, flipIndex, dstBX, dstBY);
                    putTile(srcIndexR, dstBX, dstBY);
                    putFlip(flipIndex, dstBX, dstBY);
                    putTag(0, dstBX, dstBY);
                    putAnimate(0, dstBX, dstBY, IsMultiLayer.Checked);
                }
            }
            else
            {
                if (ed.Button == MouseButtons.Left)
                {
                    if (toolSelecter.Checked)
                    {
                        dstRect.X = dstPX / CellW * CellW;
                        dstRect.Y = dstPY / CellH * CellH;
                        dstRect.Width = 0;
                        dstRect.Height = 0;
                    }
                    else if (toolTileBrush.Checked)
                    {
                        putTile(srcIndex, dstBX, dstBY);
                        putFlip(flipIndex, dstBX, dstBY);
                    }
                    else if (toolTilesBrush.Checked)
                    {
                        fillSrcTiles(dstBX, dstBY);
                    }
                    else if (toolCDBrush.Checked)
                    {
                        putTag(tagIndex, dstBX, dstBY);
                    }
                    else if (toolAnimBrush.Checked)
                    {
                        putAnimate(curAnimate, dstBX, dstBY, IsMultiLayer.Checked);
                    }
                }
                else if (ed.Button == MouseButtons.Right)
                {
                    if (toolSelecter.Checked)
                    {
                        if (dstRect.Contains(wx, wy))
                        {
                            try
                            {
                                contextMenuStrip1.Opacity = 0.5;
                                contextMenuStrip1.Show(MapRegion, ed.Location);
                            }
                            catch (Exception err)
                            {
                                Console.WriteLine(err.StackTrace + "  at  " + err.Message);
                            }
                        }
                    }
                    else if (toolTileBrush.Checked)
                    {
                        putTile(srcIndexR, dstBX, dstBY);
                        putFlip(flipIndex, dstBX, dstBY);
                    }
                    else if (toolCDBrush.Checked)
                    {
                        putTag(0, dstBX, dstBY);
                    }
                    else if (toolAnimBrush.Checked)
                    {
                        putAnimate(0, dstBX, dstBY, IsMultiLayer.Checked);
                    }

                }
            }
            refreshMap();

            textBox1.Focus();
        }
        private void pictureBox2_MouseUp(object sender, MouseEventArgs ed)
        {
            System.Drawing.Rectangle viewRect = getMapViewRectangle();


            int wx = ed.X + viewRect.X;
            int wy = ed.Y + viewRect.Y;

            if (dstIsDown)
            {
                dstQX = wx;
                dstQY = wy;
                dstIsDown = false;
            }
            refreshMap();
        }

        #endregion



        #region Script

        //

        abstract class IScriptOP
        {
            public class MapBlock
            {
                public int X;
                public int Y;
                public int TileID;
                public int AnimID;

                public MapBlock(int x, int y, int tileID, int animID)
                {
                    X = x;
                    Y = y;
                    TileID = tileID;
                    AnimID = animID;
                }
            }

            //
            public const int sc_TypeTile = 0;
            public const int sc_TypeAnim = 1;
            public const int sc_TypeLayer = 2;

            static public int sc_getTileType(String str)
            {
                str = str.Trim().ToLower();
                if (str.Equals("tile")) return sc_TypeTile;
                if (str.Equals("anim")) return sc_TypeAnim;
                if (str.Equals("layer")) return sc_TypeLayer;
                return -1;
            }

            static public String sc_putTileType(int dstType)
            {
                if (dstType == sc_TypeTile) return "tile";
                if (dstType == sc_TypeAnim) return "anim";
                if (dstType == sc_TypeLayer) return "layer";
                return "";
            }

            static public String[] sc_getArgs(String str)
            {
                int args = str.IndexOf("(") + 1;
                int arge = str.LastIndexOf(")") - 1;
                int argl = arge - args + 1;
                String arg = str.Substring(args, argl);
                String[] ret = sc_split(arg);
                for (int i = 0; i < ret.Length; i++ )
                {
                    ret[i] = ret[i].Trim();
                }
                return ret;
            }

            static String[] sc_split(String str)
            {
                String[] ret;

                // split group
                while (true) 
                {
                    int gi = str.IndexOf("{");

                    if (gi<0)
                    {
                        ret = str.Split(new char[] { ',' });
                        break;
                    }
                    else
                    {
                        int ge = str.IndexOf("}", gi);
                        int gn = ge - gi + 1;
                        String sub = str.Substring(gi, gn);
                        String newsub = sub.Replace(',', '?');
                        newsub = newsub.Replace('{', ' ');
                        newsub = newsub.Replace('}', ' ');
                        str = str.Replace(sub, newsub);
                    }
                }

                for (int i = 0; i < ret.Length; i++ )
                {
                    ret[i] = ret[i].Replace('?', ',');
                }

                return ret;
            }

            static public int[] sc_getGroupArg(String str)
            {
                int[] SrcGroup;

                if (str.Contains("~"))
                {
                    String[] ss = str.Split(new char[] { '~' });
                    int min = int.Parse(ss[0].Trim());
                    int max = int.Parse(ss[1].Trim());
                    min = Math.Min(min, max);
                    max = Math.Max(min, max);
                    int len = max - min + 1;

                    SrcGroup = new int[len];
                    for (int i = 0; i < len; i++)
                    {
                        SrcGroup[i] = min + i;
                    }
                }
                else
                {
                    String[] ss = str.Split(new char[] { ',' });
                    SrcGroup = new int[ss.Length];
                    for (int i = 0; i < ss.Length; i++)
                    {
                        SrcGroup[i] = int.Parse(ss[i].Trim());
                    }
                }

                return SrcGroup;
            }

            static public String sc_putGroupArg(int[] group)
            {
                String ret = "{";
                for (int i = 0; i < group.Length; i++ )
                {
                    if (i == group.Length - 1)
                    {
                        ret += group[i];
                    }
                    else
                    {
                        ret += group[i] + ",";
                    }
                }
                ret += "}";

                return ret;
            }


            static public int sc_indexOfGroup(int[] group, int value) 
            {
                for (int i = 0; i < group.Length; i++ )
                {
                    if (group[i] == value)
                    {
                        return i;
                    }
                }

                return -1;
            }

            static public MapBlock[] sc_randomMapBlocks(ArrayList list)
            {
                MapBlock[] ret = new MapBlock[list.Count];
                list.CopyTo(ret);

                for (int i = 0; i < ret.Length; i++)
                {
                    int dst = Math.Abs(sc_Random.Next() % ret.Length);

                    MapBlock temp = ret[i];
                    ret[i] = ret[dst];
                    ret[dst] = temp;
                }

                return ret;
            }


            static public MapBlock sc_getFillableMapBlock(IScriptOP op, int srcType, int[] srcGroup, int x, int y )
            {
                int srcIndex = -1;

                switch (srcType)
                {
                    case sc_TypeTile:
                        srcIndex = sc_indexOfGroup(srcGroup, op.CurMap.getTileID(x, y));
                        if (srcIndex >= 0 && op.CurMap.getAnimateID(x, y) == 0)
                        {
                            MapBlock block = new MapBlock(x, y, srcGroup[srcIndex], -1);
                            return block;
                        }
                        break;
                    case sc_TypeAnim:
                        srcIndex = sc_indexOfGroup(srcGroup, op.CurMap.getAnimateID(x, y));
                        if (srcIndex >= 0)
                        {
                            MapBlock block = new MapBlock(x, y, -1, srcGroup[srcIndex]);
                            return block;
                        }
                        break;
                    case sc_TypeLayer:
                        srcIndex = sc_indexOfGroup(srcGroup, -op.CurMap.getAnimateID(x, y));
                        if (srcIndex >= 0)
                        {
                            MapBlock block = new MapBlock(x, y, -1, srcGroup[srcIndex]);
                            return block;
                        }
                        break;
                }

                return null;
            }


            static public void sc_FillDstBlocks(IScriptOP op, MapBlock[] blocks, int dstType, int[] dstGroup, int dstCount)
            {
                for (int i = 0; i < dstCount && i < blocks.Length; i++)
                {
                    int dst = dstGroup[i % dstGroup.Length];

                    switch (dstType)
                    { 
                        case sc_TypeTile:
                            op.CurMap.putTile(dst, blocks[i].X, blocks[i].Y);
                            break;
                        case sc_TypeAnim:
                            op.CurMap.putAnimate(dst, blocks[i].X, blocks[i].Y, false);
                            break;
                        case sc_TypeLayer:
                            op.CurMap.putAnimate(dst, blocks[i].X, blocks[i].Y, true);
                            break;
                    }
                }

            }

            // gobal


            static public Random sc_Random = new Random();

            static public int sc_RegionX = 0, sc_RegionY = 0, sc_RegionW, sc_RegionH;
            static public String sc_RegionShape = "rect";

            //


            //

            protected MapForm CurMap;

            public IScriptOP(MapForm mapform)
            {
                CurMap = mapform;
            }

            //


            abstract public String getFuncKey();

            abstract public void doScript();

            abstract protected String[] getArgs();

            abstract protected void setArgs(String[] args);

            abstract public String getCommet();

            //
           
            public String createScript()
            {
                String ret = getCommet() + "\n" + getFuncKey() + "(";
                String[] args = getArgs();
                for (int i = 0; i < args.Length; i++ )
                {
                    if (i == args.Length - 1)
                    {
                        ret += args[i];
                    }
                    else
                    {
                        ret += args[i] + ", ";
                    }
                }
                ret += ");\n";
                return ret;
            }

            public Boolean tryDoScript(String line) 
            { 
                line = line.Trim();

                int func = line.IndexOf(getFuncKey());

                if (func>=0)
                {
                    line = line.Substring(func + getFuncKey().Length).Trim();

                    if (line.StartsWith("("))
                    {
                        if (line.EndsWith(")"))
                        {
                            String[] sargs = sc_getArgs(line);
                            setArgs(sargs);
                            String[] dargs = getArgs();

                            if (sargs.Length == dargs.Length)
                            {
                                doScript();
                                return true;
                            }
                            else
                            {
                                throw new Exception("function\"" + getFuncKey() + "\" not support " + sargs.Length + " args!");
                            }
                        }
                        else
                        {
                            throw new Exception("function ( ) not Match!");
                        }
                    }
                }
                return false;
            }


        }

        //
        //--------------------------------------------------------------------------------------------------------

        class ScriptSetRandomSeed : IScriptOP
        {
            //
            private int seed;

            public ScriptSetRandomSeed(MapForm map, int s)
                : base(map)
            {
                seed = s;
            }
            public ScriptSetRandomSeed(MapForm map)
                : base(map)
            { 
            }

            override public String getCommet()
            {
                return "/// 设置当前随机数种子";
            }
            override public String getFuncKey()
            {
                return "SetRandomSeed";
            }


            override public void doScript() 
            {
                sc_Random = new Random(seed);
            }

            override protected String[] getArgs()
            {
                return new String[] { seed.ToString() };
            }

            override protected void setArgs(String[] args)
            {
                seed = int.Parse(args[0].Trim());
            }
        }


        class ScriptSetRegion : IScriptOP
        {
           

            //
            private int x,y,w,h;
            private String shape;

            public ScriptSetRegion(MapForm map, int x, int y, int w, int h, String shape)
                : base(map)
            {
                CurMap = map;
                this.x = x;
                this.y = y;
                this.w = w;
                this.h = h;
                this.shape = shape;
            }
            public ScriptSetRegion(MapForm map)
                : base(map)
            { 
            }

            override public String getCommet()
            {
                return "/// 设置当前脚本影响到的区域，即脚本的有效范围";
            }
            override public String getFuncKey()
            {
                return "SetRegion";
            }

            override public void doScript()
            {
                sc_RegionX = x;
                sc_RegionY = y;
                sc_RegionW = w;
                sc_RegionH = h;
                sc_RegionShape = shape;
            }

            override protected String[] getArgs()
            {
                return new String[] { x.ToString(), y.ToString(), w.ToString(), h.ToString(), shape };
            }

            override protected void setArgs(String[] args)
            {
                x = int.Parse(args[0].Trim());
                y = int.Parse(args[1].Trim());
                w = int.Parse(args[2].Trim());
                h = int.Parse(args[3].Trim());
                shape = args[4].Trim();
            }
        }


        class ScriptReplace : IScriptOP
        {
            int SrcType;
            int Src; 
            int DstType; 
            int Dst;

            public ScriptReplace(MapForm map, int srcType, int src, int dstType, int dst)
                : base(map)
            {
                this.SrcType = srcType;
                this.Src = src;
                this.DstType = dstType;
                this.Dst = dst;
            }
            public ScriptReplace(MapForm map)
                : base(map)
            { 
            }

            override public String getCommet()
            {
                return
                    "/// 替换指定的tile或anim成为新的tile或anim \n" +
                    "/// SrcType - 被替换的类型(tile或anim或layer); ps: (anim和layer编号是一样的，只是渲染方式不同，anim为正，layer为负)\n" +
                    "/// Src     - 被替换的tile编号或anim编号\n" +
                    "/// DstType - 要替换的类型\n" +
                    "/// Dst     - 要替换的tile编号或anim编号";
            }
            override public String getFuncKey()
            {
                return "Replace";
            }

            override public void doScript()
            {
                Boolean isSrcTile = SrcType == sc_TypeTile;
                Boolean isSrcAnim = SrcType == sc_TypeAnim || SrcType == sc_TypeLayer;

                Boolean isDstTile = DstType == sc_TypeTile;
                Boolean isDstAnim = DstType == sc_TypeAnim;
                Boolean isDstLayer = DstType == sc_TypeLayer;

                int sw = Math.Min(sc_RegionX + sc_RegionW, CurMap.XCount);
                int sh = Math.Min(sc_RegionY + sc_RegionH, CurMap.YCount);
                for (int x = sc_RegionX; x < sw; x++)
                {
                    for (int y = sc_RegionY; y < sh; y++)
                    {
                        if ((isSrcTile && CurMap.getTileID(x, y) == Src) ||
                            (isSrcAnim && CurMap.getAnimateID(x, y) == Src))
                        {
                            if (isDstTile)
                            {
                                CurMap.putTile(Dst, x, y);
                            }
                            else if (isDstAnim)
                            {
                                CurMap.putAnimate(Dst, x, y, false);
                            }
                            else if (isDstLayer)
                            {
                                CurMap.putAnimate(Dst, x, y, true);
                            }
                        }
                    }
                }
            }

            override protected String[] getArgs()
            {
                return new String[] { 
                    sc_putTileType(SrcType), 
                    Src.ToString(), 
                    sc_putTileType(DstType),
                    Dst.ToString()
                };
            }

            override protected void setArgs(String[] args)
            {
                SrcType = sc_getTileType(args[0]);
                Src = int.Parse(args[1].Trim());
                DstType = sc_getTileType(args[2]);
                Dst = int.Parse(args[3].Trim());
            }
        }


        class ScriptFill : IScriptOP
        {

            int SrcType;
            int[] SrcGroup;
            int DstType;
            int[] DstGroup;
            int DstCount;

            public ScriptFill(MapForm map, int srcType, int[] srcGroup, int dstType, int[] dstGroup, int dstCount)
                : base(map)
            {
                SrcType = srcType;
                SrcGroup = srcGroup;
                DstType = dstType;
                DstGroup = dstGroup;
                DstCount = dstCount;
            }
            public ScriptFill(MapForm map)
                : base(map)
            {
            }
            override public String getCommet()
            {
                return 
                    "/// 随机填充指定的tile或anim，为新的tile或anim \n" +
                    "/// SrcType  - 原地图中可被填充的类型(tile或anim或layer)\n" +
                    "/// SrcGroup - 原地图中可被填充的tile编号或anim编号组合{n,n...,n}\n" +
                    "/// DstType  - 要填充的类型\n" +
                    "/// DstGroup - 要填充的tile编号或anim编号组合\n" +
                    "/// DstCount - 要最大填充多少个" 
                    ;
            }
            override public String getFuncKey()
            {
                return "Fill";
            }

            override public void doScript()
            {
                ArrayList srcBlocks = new ArrayList();
                {
                    int sw = Math.Min(sc_RegionX + sc_RegionW, CurMap.XCount);
                    int sh = Math.Min(sc_RegionY + sc_RegionH, CurMap.YCount);
                    for (int x = sc_RegionX; x < sw; x++)
                    {
                        for (int y = sc_RegionY; y < sh; y++)
                        {
                            MapBlock block = sc_getFillableMapBlock(this, SrcType, SrcGroup, x, y);
                            if (block != null) srcBlocks.Add(block);
                        }
                    }
                }

                MapBlock[] blocks = sc_randomMapBlocks(srcBlocks);

                sc_FillDstBlocks(this, blocks, DstType, DstGroup, DstCount);



            }

            override protected String[] getArgs()
            {
                return new String[] { 
                    sc_putTileType(SrcType),
                    sc_putGroupArg(SrcGroup),
                    sc_putTileType(DstType),
                    sc_putGroupArg(DstGroup),
                    DstCount.ToString()
                };
            }

            override protected void setArgs(String[] args)
            {
                SrcType = sc_getTileType(args[0]);
                SrcGroup = sc_getGroupArg(args[1]);              
                DstType = sc_getTileType(args[2]);
                DstGroup = sc_getGroupArg(args[3]);
                DstCount = int.Parse(args[4].Trim());
            }
        }



        class ScriptFillGrid : IScriptOP
        {
            int GridW;
            int GridH;

            int SubX;
            int SubY;
            int SubW;
            int SubH;

            int SrcType;
            int[] SrcGroup;
            int DstType;
            int[] DstGroup;
            int DstCount;

            public ScriptFillGrid(MapForm map, 
                int gridW, int gridH, 
                int subX,int subY, int subW, int subH,
                int srcType, int[] srcGroup, 
                int dstType, int[] dstGroup, 
                int dstCount)
                : base(map)
            {
                GridW = gridW;
                GridH = gridH;

                SubX = subX;
                SubY = subY;
                SubW = subW;
                SubH = subH;

                SrcType = srcType;
                SrcGroup = srcGroup;
                DstType = dstType;
                DstGroup = dstGroup;
                DstCount = dstCount;
            }
            public ScriptFillGrid(MapForm map)
                : base(map)
            {
            }
            override public String getCommet()
            {
                return
                    "/// 在指定的网格范围内，随机填充指定范围内的tile或anim，为新的tile或anim \n" +
                    "/// GridW    - 原网格尺寸\n" +
                    "/// GridH    - \n" +
                    "/// SubX     - 在该网格尺寸中的矩形范围\n" +
                    "/// SubY     - \n" +
                    "/// SubW     - \n" +
                    "/// SubH     - \n" +
                    "/// SrcType  - 原地图中可被填充的类型(tile或anim或layer)\n" +
                    "/// SrcGroup - 原地图中可被填充的tile编号或anim编号组合{n,n...,n}\n" +
                    "/// DstType  - 要填充的类型\n" +
                    "/// DstGroup - 要填充的tile编号或anim编号组合\n" +
                    "/// DstCount - 要最大填充多少个"
                ;
            }
            override public String getFuncKey()
            {
                return "FillGrid";
            }

            override public void doScript()
            {
                System.Drawing.Rectangle gridRegion = new System.Drawing.Rectangle(
                       SubX, SubY, SubW, SubH
                       );

                int rw = (sc_RegionW) / GridW;
                int rh = (sc_RegionH) / GridH;
                for (int rx = 0; rx < rw; rx++)
                {
                    for (int ry = 0; ry < rh; ry++)
                    {
                        ArrayList srcBlocks = new ArrayList();
                        {
                            int sx = sc_RegionX + rx * GridW + SubX;
                            int sy = sc_RegionY + ry * GridH + SubY;
                            int sw = Math.Min(sx + SubW, CurMap.XCount);
                            int sh = Math.Min(sy + SubH, CurMap.YCount);

                            for (int x = sx; x < sw; x++)
                            {
                                for (int y = sy; y < sh; y++)
                                {
                                   // if (gridRegion.Contains(x % GridW, y % GridH))
                                    {
                                        MapBlock block = sc_getFillableMapBlock(this, SrcType, SrcGroup, x, y);
                                        if (block != null) srcBlocks.Add(block);
                                    }
                                }
                            }
                        }

                        MapBlock[] blocks = sc_randomMapBlocks(srcBlocks);

                        sc_FillDstBlocks(this, blocks, DstType, DstGroup, DstCount);

                    }
                }
            }

            override protected String[] getArgs()
            {
                return new String[] { 
                    GridW.ToString(),
                    GridH.ToString(),
                    SubX.ToString(),
                    SubY.ToString(),
                    SubW.ToString(),
                    SubH.ToString(),
                    sc_putTileType(SrcType),
                    sc_putGroupArg(SrcGroup),
                    sc_putTileType(DstType),
                    sc_putGroupArg(DstGroup),
                    DstCount.ToString()
                };
            }

            override protected void setArgs(String[] args)
            {
                GridW = int.Parse(args[0].Trim());
                GridH = int.Parse(args[1].Trim());
                SubX = int.Parse(args[2].Trim());
                SubY = int.Parse(args[3].Trim());
                SubW = int.Parse(args[4].Trim());
                SubH = int.Parse(args[5].Trim());
                SrcType = sc_getTileType(args[6]);
                SrcGroup = sc_getGroupArg(args[7]);
                DstType = sc_getTileType(args[8]);
                DstGroup = sc_getGroupArg(args[9]);
                DstCount = int.Parse(args[10].Trim());
            }
        }



        class ScriptSpawn : IScriptOP
        {
            int KeyType;
            int[] KeyGroup;
            int X;
            int Y;
            int W;
            int H;
            String Shape = "rect";

            int SrcType;
            int[] SrcGroup;
            int DstType;
            int[] DstGroup;
            int DstCount;

            public ScriptSpawn(MapForm map,
                int keyType, int[] keyGroup,
                int x, int y, int w, int h, String shape,
                int srcType, int[] srcGroup,
                int dstType, int[] dstGroup,
                int dstCount)
                : base(map)
            {
                KeyType = keyType;
                KeyGroup = keyGroup;
                X = x;
                Y = y;
                W = w;
                H = h;
                Shape = shape;

                SrcType = srcType;
                SrcGroup = srcGroup;
                DstType = dstType;
                DstGroup = dstGroup;
                DstCount = dstCount;
            }
            public ScriptSpawn(MapForm map)
                : base(map)
            {

            }

            override public String getCommet()
            {
                return
                    "/// 在指定的tile或anim周围随机生成若干个tile或anim \n" +
                    "/// KeyType  - 周围生成的参考点类型(tile或anim或layer)\n" +
                    "/// KeyGroup - 周围生成的参考点的tile编号或anim编号组合{n,n...,n}\n" +
                    "/// X        - 基于参考点的范围 eg:(-2,-2,4,4)即围绕该点周围2个半径范围内生成\n" +
                    "/// Y        - \n" +
                    "/// W        - \n" +
                    "/// H        - \n" + 
                    "/// Shape    - 当前只能指定 rect 范围\n" +
                    "/// SrcType  - 原地图中可被填充的类型(tile或anim或layer)\n" +
                    "/// SrcGroup - 原地图中可被填充的tile编号或anim编号组合{n,n...,n}\n" +
                    "/// DstType  - 要填充的类型\n" +
                    "/// DstGroup - 要填充的tile编号或anim编号组合\n" +
                    "/// DstCount - 要最大填充多少个"
                    ;
            }
            override public String getFuncKey()
            {
                return "Spawn";
            }

            override public void doScript()
            {

                ArrayList keyBlocks = new ArrayList();

                {
                    int sw = Math.Min(sc_RegionX + sc_RegionW, CurMap.XCount);
                    int sh = Math.Min(sc_RegionY + sc_RegionH, CurMap.YCount);
                    for (int x = sc_RegionX; x < sw; x++)
                    {
                        for (int y = sc_RegionY; y < sh; y++)
                        {
                            MapBlock block = sc_getFillableMapBlock(this, KeyType, KeyGroup, x, y);
                            if (block != null) keyBlocks.Add(block);
                        }
                    }
                }

                foreach (MapBlock block in keyBlocks)
                {
                    ArrayList srcBlocks = new ArrayList(DstCount);

                    int sx = Math.Max(block.X + X, 0);
                    int sy = Math.Max(block.Y + Y, 0);
                    int sw = Math.Min(sx + W, CurMap.XCount);
                    int sh = Math.Min(sy + H, CurMap.YCount);
                    for (int x = sx; x < sw; x++)
                    {
                        for (int y = sy; y < sh; y++)
                        {
                            MapBlock b = sc_getFillableMapBlock(this, SrcType, SrcGroup, x, y);
                            if (b != null) srcBlocks.Add(b);
                        }
                    }

                    MapBlock[] blocks = sc_randomMapBlocks(srcBlocks);

                    sc_FillDstBlocks(this, blocks, DstType, DstGroup, DstCount);

                }
            }

            override protected String[] getArgs()
            {
                return new String[] { 
                    sc_putTileType(KeyType),
                    sc_putGroupArg(KeyGroup),
                    X.ToString(),
                    Y.ToString(),
                    W.ToString(),
                    H.ToString(),
                    Shape,
                    sc_putTileType(SrcType),
                    sc_putGroupArg(SrcGroup),
                    sc_putTileType(DstType),
                    sc_putGroupArg(DstGroup),
                    DstCount.ToString()
                };
            }

            override protected void setArgs(String[] args)
            {
                KeyType = sc_getTileType(args[0]);
                KeyGroup = sc_getGroupArg(args[1]);
                X = int.Parse(args[2].Trim());
                Y = int.Parse(args[3].Trim());
                W = int.Parse(args[4].Trim());
                H = int.Parse(args[5].Trim());
                Shape = args[6];
                SrcType = sc_getTileType(args[7]);
                SrcGroup = sc_getGroupArg(args[8]);
                DstType = sc_getTileType(args[9]);
                DstGroup = sc_getGroupArg(args[10]);
                DstCount = int.Parse(args[11].Trim());
            }
        }



        class ScriptCopy : IScriptOP
        {
            int SX, SY, SW, SH;
            int DX, DY, DW, DH;

            int SrcType;
            int[] SrcGroup;

            public ScriptCopy(MapForm map, 
                int sx, int sy, int sw, int sh,
                int dx, int dy, int dw, int dh,
                int srcType, int[] srcGroup)
                : base(map)
            {
                SX = sx;
                SY = sy;
                SW = sw;
                SH = sh;

                DX = dx;
                DY = dy;
                DW = dw;
                DH = dh;

                SrcType = srcType;
                SrcGroup = srcGroup;
            }
            public ScriptCopy(MapForm map)
                : base(map)
            {
            }
            override public String getCommet()
            {
                return 
                    "/// 将制定范围的地图数据复制到另一范围\n" +
                    "/// SX, SY, SW, SH - 被复制的范围\n" +
                    "/// DX, DY, DW, DH - 复制到的范围\n" +
                    "/// SrcType  - 原地图中可被填充的类型(tile或anim或layer)\n" +
                    "/// SrcGroup - 原地图中可被填充的tile编号或anim编号组合{n,n...,n}"
                    ;
            }
            override public String getFuncKey()
            {
                return "Copy";
            }

            override public void doScript()
            {
                Boolean isSrcTile = SrcType == sc_TypeTile;
                Boolean isSrcAnim = SrcType == sc_TypeAnim || SrcType == sc_TypeLayer;

                {
                    int sw = Math.Min(SX + SW, CurMap.XCount);
                    int sh = Math.Min(SY + SH, CurMap.YCount);

                    int[][] clipTile = new int[SW][];
                    int[][] clipFlip = new int[SW][];
                    int[][] clipAnim = new int[SW][];

                    for (int x = SX, fx = 0; x < sw; x++, fx++)
                    {
                        clipTile[fx] = new int[SH];
                        clipFlip[fx] = new int[SH];
                        clipAnim[fx] = new int[SH];

                        for (int y = SY, fy=0; y < sh; y++, fy++)
                        { 
                            clipTile[fx][fy] = CurMap.getTileID(x, y);
                            clipFlip[fx][fy] = CurMap.getTileFlip(x, y);
                            clipAnim[fx][fy] = CurMap.getAnimateID(x, y);
                        }
                    }

                    int dw = Math.Min(DX + DW, CurMap.XCount);
                    int dh = Math.Min(DY + DH, CurMap.YCount);

                    for (int x = DX, fx = 0; x < dw; x++, fx++)
                    {
                        for (int y = DY, fy = 0; y < dh; y++, fy++)
                        {
                            int srcIndex = -1;

                            if (isSrcTile)
                            {
                                srcIndex = sc_indexOfGroup(SrcGroup, CurMap.getTileID(x, y));
                            }
                            else if (isSrcAnim)
                            {
                                srcIndex = sc_indexOfGroup(SrcGroup, CurMap.getAnimateID(x, y));
                            }

                            if (srcIndex >= 0)
                            {
                                fx = fx % clipTile.Length;
                                fy = fy % clipTile[fx].Length;

                                CurMap.putTile(clipTile[fx][fy], x, y);
                                CurMap.putFlip(clipFlip[fx][fy], x, y);
                                CurMap.putAnimate(clipAnim[fx][fy], x, y, clipAnim[fx][fy] < 0);
                            }

                        }
                    }

                }
            }

            override protected String[] getArgs()
            {
                return new String[] { 
                    SX.ToString(),
                    SY.ToString(),
                    SW.ToString(),
                    SH.ToString(),
                    DX.ToString(),
                    DY.ToString(),
                    DW.ToString(),
                    DH.ToString(),
                    sc_putTileType(SrcType),
                    sc_putGroupArg(SrcGroup),
                };
            }

            override protected void setArgs(String[] args)
            {
                SX = int.Parse(args[0]);
                SY = int.Parse(args[1]);
                SW = int.Parse(args[2]);
                SH = int.Parse(args[3]);
                DX = int.Parse(args[4]);
                DY = int.Parse(args[5]);
                DW = int.Parse(args[6]);
                DH = int.Parse(args[7]);
                SrcType = sc_getTileType(args[8]);
                SrcGroup = sc_getGroupArg(args[9]);
            }
        }



    //--------------------------------------------------------------------------------------------------------
  

        /// <summary>
        /// 
        /// </summary>
        /// <param name="script"></param>
        /// <returns></returns>
        public String scriptRun(String script)
        {
            // remove rem
            while (true)
            {
                int gi = script.IndexOf("//");

                if (gi >= 0)
                {
                    int ge = script.IndexOf("\n", gi);
                    int gn = ge - gi + 1;
                    script = script.Remove(gi, gn);
                }
                else { break; }
            }


            String res = "";
            String line = "";
            int i = 0;

            try
            {
                String[] lines = script.Split(new char[] { ';' });

                for (i = 0; i < lines.Length; i++)
                {
                    line = lines[i];

                    if (new ScriptSetRandomSeed(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptSetRegion(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptReplace(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptFill(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptFillGrid(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptSpawn(this).tryDoScript(lines[i]))
                    { }
                    else if (new ScriptCopy(this).tryDoScript(lines[i]))
                    { }
                }

                res = "Run script succeed !";
            }
            catch (Exception err) 
            { 
                res = 
                    "Error at func:" + i + "\n" + 
                    line + "\n\n" +
                    err.Message + "\n" + err.StackTrace; 
            }
           
            refreshMap();

            return res;
        }

        #endregion


        private void BtnFunc_Click(object sender, EventArgs e)
        {
                MapFormFunc mf = new MapFormFunc(this);
                mf.Show(this);
        }

        private void 替换ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (toolSelecter.Checked)
            {
                int sbx = dstRect.X / CellW;
                int sby = dstRect.Y / CellH;
                int xcount = dstRect.Width / CellW;
                int ycount = dstRect.Height / CellH;

                MapFormFunc mf = new MapFormFunc(this,
                    new ScriptSetRegion(this, sbx, sby, xcount, ycount, "rect").createScript() +
                    new ScriptReplace(this, 0, 0, 0, 0).createScript()
                    );
                mf.Show(this);
            }

           
        }

        private void 填充ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (toolSelecter.Checked)
            {
                int sbx = dstRect.X / CellW;
                int sby = dstRect.Y / CellH;
                int xcount = dstRect.Width / CellW;
                int ycount = dstRect.Height / CellH;

                MapFormFunc mf = new MapFormFunc(this,
                    new ScriptSetRandomSeed(this, IScriptOP.sc_Random.Next()).createScript() +
                    new ScriptSetRegion(this, sbx, sby, xcount, ycount, "rect").createScript() +
                    new ScriptFill(this, 0, new int[] { 0, }, 0, new int[] { 0 }, 0).createScript()
                    );
                mf.Show(this);
            }
        }

        private void 生成ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (toolSelecter.Checked)
            {
                int sbx = dstRect.X / CellW;
                int sby = dstRect.Y / CellH;
                int xcount = dstRect.Width / CellW;
                int ycount = dstRect.Height / CellH;

                MapFormFunc mf = new MapFormFunc(this,
                    new ScriptSetRandomSeed(this, IScriptOP.sc_Random.Next()).createScript() +
                    new ScriptSetRegion(this, sbx, sby, xcount, ycount, "rect").createScript() +
                    new ScriptSpawn(this,
                    0, new int[] { 0, },
                    -2, -2, 4, 4, "rect",
                    0, new int[] { 0, },
                    0, new int[] { 0 }, 0).createScript()
                    );
                mf.Show(this);
            }
        }

        private void 网格填充ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (toolSelecter.Checked)
            {
                int sbx = dstRect.X / CellW;
                int sby = dstRect.Y / CellH;
                int xcount = dstRect.Width / CellW;
                int ycount = dstRect.Height / CellH;

                MapFormFunc mf = new MapFormFunc(this,
                   new ScriptSetRandomSeed(this, IScriptOP.sc_Random.Next()).createScript() +
                   new ScriptSetRegion(this, sbx, sby, xcount, ycount, "rect").createScript() +
                   new ScriptFillGrid(this, 
                   5, 5, 
                   1, 1, 4, 4, 
                   0, new int[] { 0, }, 
                   0, new int[] { 0 }, 0).createScript()
                   );
                mf.Show(this);
            }
        }

        private void 复制填充ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (toolSelecter.Checked)
            {
                int sbx = dstRect.X / CellW;
                int sby = dstRect.Y / CellH;
                int xcount = dstRect.Width / CellW;
                int ycount = dstRect.Height / CellH;

                MapFormFunc mf = new MapFormFunc(this,
                   new ScriptCopy(this, 
                   sbx, sby, xcount, ycount, 
                   0, 0, XCount, YCount,
                   0, new int[] { 0 }).createScript()
                 );
                mf.Show(this);
            }
        }

      












    }

    class Animates 
    {

        ArrayList Frames = new ArrayList();

        public ArrayList SubPart = new ArrayList();
        public ArrayList SubFlip = new ArrayList();

        public Animates()
        {
           
        }

        public void frameAdd(ArrayList frame)
        {
            Frames.Add(frame);
        }

        public int frameGetCount()
        {
            return Frames.Count;
        }

        public int frameIndexOf(ArrayList frame)
        {
            for (int i = 0; i < Frames.Count;i++ )
            {
                if (Frames[i].Equals(frame))return i;

                if (((ArrayList)Frames[i]).Count != frame.Count) continue;

                Boolean ok = true;

                for (int j = 0; j < frame.Count;j++ )   
                {
                    if (((int)((ArrayList)Frames[i])[j]) != ((int)frame[j]))
                    {
                        ok = false;
                        break;
                    }
                }

                if (ok) return i;
            }

            return -1;
        }

        public ArrayList frameGetFrame(int index)
        {
            return (ArrayList)(Frames[index]);
        }

        public void subAdd(int part, int flip)
        {
            SubPart.Add(part);
            SubFlip.Add(flip);
        }

        public int subGetCount()
        {
            return SubPart.Count;
        }

        public int subIndexOf(int part, int flip)
        {
            if (!SubPart.Contains(part)) return -1;
            if (!SubFlip.Contains(flip)) return -1;

            for(int i=0;i<subGetCount();i++)
            {
                int p1 = SubPart.IndexOf(part, i, 1);
                int p2 = SubFlip.IndexOf(flip, i, 1);
                if (p1>=0 && p2>=0 && p1==p2)
                {
                    return i;
                }
            }
            return -1;
        }

    }
}