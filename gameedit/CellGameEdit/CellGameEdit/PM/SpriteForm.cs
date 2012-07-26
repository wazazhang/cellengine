using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

using System.Text;
using System.Windows.Forms;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters;
using System.Security.Permissions;
using System.Collections;

using javax.microedition.lcdui;

namespace CellGameEdit.PM
{

    [Serializable]
    public partial class SpriteForm : Form, ISerializable ,IEditForm
    {

        //static public const String VAR_FILE_NAME = "<VAR FILE NAME>";
        //static public const String VAR_FILE_NAME = "<VAR FILE NAME>";



        public String id;

        public String superName;
        public ImagesForm super;

        //Hashtable Frames;
        Hashtable AnimTable;
        int animCount;

        //[NoneSerializable]
        ArrayList srcTiles;
      //  int srcIndex = 0;
        Image srcImage;
        System.Drawing.Rectangle srcRect;

        int dstPanelSize = 2048;
        float curMasterScale = 1;
		int srcScale = 1;
		public static javax.microedition.lcdui.Image imgScaleP =
			new javax.microedition.lcdui.Image(Resource1.touch_marker);
        /*
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
        */

        //ArrayList curFrames;
        //Frame curFrame;

		private string append_data = "";

        public SpriteForm(String name,ImagesForm images)
        {
            InitializeComponent();

            id = name; this.Text = id;
            super = images;

            srcRect = new System.Drawing.Rectangle(0, 0, 0, 0);
            //dstRect = new System.Drawing.Rectangle(0, 0, 0, 0);

            srcTiles = super.dstImages;
            for (int i = 0; i < srcGetCount(); i++)
            {
				Image img = srcGetImage(i);
				if (img != null)
				{
					//srcIndex = i;
					srcImage = img;
					srcRect.X = img.x;
					srcRect.Y = img.y;
					srcRect.Width = img.getWidth();
					srcRect.Height = img.getHeight();
					break;
				}
            }

            AnimTable = new Hashtable();
            animCount = 0;

            
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected SpriteForm(SerializationInfo info, StreamingContext context)
        {
            InitializeComponent();

            try
            {
                id = (String)info.GetValue("id", typeof(String));
                this.Text = id;
				/*
                if (!ProjectForm.IsCopy)
                {
                    super = (ImagesForm)info.GetValue("super", typeof(ImagesForm));
                    ChangeSuper(super);
                }
				*/
                //if (super == null)
                {
                    superName = (String)info.GetValue("SuperName", typeof(String));
                    //super = ProjectForm.getInstance().getImagesFormByName(superName);
                }
                
                animCount = (int)info.GetValue("animCount", typeof(int));
                ArrayList Animates = (ArrayList)info.GetValue("Animates", typeof(ArrayList));
                ArrayList AniNames = (ArrayList)info.GetValue("AniNames", typeof(ArrayList));

                AnimTable = new Hashtable();
               
                for (int i = 0; i < Animates.Count; i++)
                {
                    try
                    {
                        ArrayList frames = (ArrayList)Animates[i];
                        String name = (String)AniNames[i];
                        ListViewItem item = new ListViewItem(new String[] { name, frames.Count.ToString("d") });
                        listView2.Items.Add(item);
                        AnimTable.Add(item, frames);
                       
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message);
                    }
                   
                }


				try
				{
					this.append_data = info.GetString("AppendData");
				}catch(Exception err){
					this.append_data = "";
				}

				try {
					checkComplexMode.Checked = info.GetBoolean("checkComplexMode");
				}
				catch (Exception err) { checkComplexMode.Checked = false; }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace + "  at  " +err.Message);
            }
           

        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            Console.WriteLine("Serializ Sprite : " + id);
            try
            {
                info.AddValue("id",id);

				/*
                if (!ProjectForm.IsCopy)
                {
                    info.AddValue("super", super);
                }
              */
                info.AddValue("SuperName",super.id);
                


                info.AddValue("animCount", animCount);

                ArrayList Animates = new ArrayList();
                ArrayList AniNames = new ArrayList();
                foreach (ListViewItem item in listView2.Items)
                {
                    if (item != null)
                    {
                        try
                        {
                            Animates.Add((ArrayList)AnimTable[item]);
                            AniNames.Add(item.Text);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message);
                        }
                    }
                }
                info.AddValue("Animates", Animates);
                info.AddValue("AniNames", AniNames);

				info.AddValue("AppendData", append_data);
				info.AddValue("checkComplexMode", checkComplexMode.Checked);
				
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
                Console.WriteLine("Sprite ChangeImages : " + superName);
            }
        }

        public void ChangeSuper(ImagesForm super)
        {
            this.super = super;
            this.srcTiles = super.dstImages;

            srcRect = new System.Drawing.Rectangle(0, 0, 0, 0);
            //dstRect = new System.Drawing.Rectangle(0, 0, 0, 0);

            srcTiles = super.dstImages;
            for (int i = 0; i < srcGetCount(); i++)
            {
				Image img = srcGetImage(i);
				if (img != null)
                {
                   // srcIndex = i;
					srcImage = img;
					srcRect.X = img.x;
					srcRect.Y = img.y;
					srcRect.Width = img.getWidth();
					srcRect.Height = img.getHeight();
                    break;
                }
            }
        }

       


        private void initOutput(
			Frame AllFrame,
			Group animates,
			Group collides,

			String[] frameName,
			int[][] frameAnimate,

			float[][] frameAlpha,
			float[][] frameRotate,
			float[][] frameScaleX,
			float[][] frameScaleY,
			float[][] frameShearX,
			float[][] frameShearY,

			int[][] frameCDMap,
			int[][] frameCDAtk,
			int[][] frameCDDef,
            int[][] frameCDExt, 

            string[][] frameAdata)
        {
            for (int animID = 0; animID < listView2.Items.Count; animID++)//anim
            {
                int FrameCount = ((ArrayList)AnimTable[listView2.Items[animID]]).Count;

                frameName[animID] = listView2.Items[animID].Text;
                frameAnimate[animID] = new int[FrameCount];

				frameAlpha[animID] = new float[FrameCount];
				frameRotate[animID] = new float[FrameCount];
				frameScaleX[animID] = new float[FrameCount];
				frameScaleY[animID] = new float[FrameCount];
				frameShearX[animID] = new float[FrameCount];
				frameShearY[animID] = new float[FrameCount];

                frameCDMap[animID] = new int[FrameCount];
                frameCDAtk[animID] = new int[FrameCount];
                frameCDDef[animID] = new int[FrameCount];
                frameCDExt[animID] = new int[FrameCount];

                frameAdata[animID] = new string[FrameCount];

                for (int frameID = 0; frameID < FrameCount; frameID++)// frame
                {
                    Frame frame = (Frame)(
                        ((ArrayList)(
                            AnimTable[listView2.Items[animID]]
                            ))
                            [frameID]
                        );

                    frameAdata[animID][frameID] = frame.append_data.Replace("\n", "");

					frameAlpha[animID][frameID] = frame.alpha;
					frameRotate[animID][frameID] = frame.rotate;
					frameScaleX[animID][frameID] = frame.scalex;
					frameScaleY[animID][frameID] = frame.scaley;
					frameShearX[animID][frameID] = frame.shearx;
					frameShearY[animID][frameID] = frame.sheary;

                    // sub
                    ArrayList fAnimate = new ArrayList();

                    for (int subID = 0; subID < frame.getSubCount(); subID++)
                    {
                        int indexSub = AllFrame.indexOfSub(
                            (int)frame.SubIndex[subID],
                            (int)frame.SubX[subID],
                            (int)frame.SubY[subID],
                            (int)frame.SubZ[subID],
                            (int)frame.SubW[subID],
                            (int)frame.SubH[subID],
							(int)frame.SubFlip[subID],
							(float)frame.SubTRotate[subID],
							(float)frame.SubTScaleX[subID],
							(float)frame.SubTScaleY[subID],
							(float)frame.SubTAlpha[subID],
							(float)frame.SubTShearX[subID],
							(float)frame.SubTShearY[subID]
                            );

                        if (indexSub < 0)
                        {
                            AllFrame.addFSub(
                                (int)frame.SubIndex[subID],
                                (int)frame.SubX[subID],
                                (int)frame.SubY[subID],
                                (int)frame.SubZ[subID],
                                (int)frame.SubW[subID],
                                (int)frame.SubH[subID],
								(int)frame.SubFlip[subID],
							(float)frame.SubTRotate[subID],
							(float)frame.SubTScaleX[subID],
							(float)frame.SubTScaleY[subID],
							(float)frame.SubTAlpha[subID],
							(float)frame.SubTShearX[subID],
							(float)frame.SubTShearY[subID]
							);
                           
                            indexSub = AllFrame.getSubCount() - 1;

                           
                        }
                        fAnimate.Add(indexSub);
                    }

                    int indexAnimate = animates.frameIndexOf(fAnimate);
                    if (indexAnimate < 0)
                    {
                        animates.frameAdd(fAnimate);
                        indexAnimate = animates.frameGetCount() - 1;
                    }

                    frameAnimate[animID][frameID] = indexAnimate;

                    // cd
                    ArrayList fCDMap = new ArrayList();
                    ArrayList fCDAtk = new ArrayList();
                    ArrayList fCDDef = new ArrayList();
                    ArrayList fCDExt = new ArrayList();

                    for (int cdID = 0; cdID < frame.getCDCount(); cdID++)
                    {
                        int indexCD = AllFrame.indexOfCD(
                            (int)frame.CDMask[cdID],
                            (int)frame.CDX[cdID],
                            (int)frame.CDY[cdID],
                            (int)frame.CDW[cdID],
                            (int)frame.CDH[cdID]);
                        if (indexCD < 0)
                        {
                            AllFrame.addFCD(
                                (int)frame.CDMask[cdID],
                                (int)frame.CDX[cdID],
                                (int)frame.CDY[cdID],
                                (int)frame.CDW[cdID],
                                (int)frame.CDH[cdID],
                                (int)frame.CDType[cdID]);
                            indexCD = AllFrame.getCDCount() - 1;
                        }
                        switch ((int)frame.CDType[cdID])
                        {
                            case Frame.CD_TYPE_MAP: fCDMap.Add(indexCD);
                                break;
                            case Frame.CD_TYPE_ATK: fCDAtk.Add(indexCD);
                                break;
                            case Frame.CD_TYPE_DEF: fCDDef.Add(indexCD);
                                break;
                            case Frame.CD_TYPE_EXT: fCDExt.Add(indexCD);
                                break;
                        }
                    }
                    // map
                    int indexCDMap = collides.frameIndexOf(fCDMap);
                    if (indexCDMap < 0)
                    {
                        collides.frameAdd(fCDMap);
                        indexCDMap = collides.frameGetCount() - 1;
                    }
                    // atk
                    int indexCDAtk = collides.frameIndexOf(fCDAtk);
                    if (indexCDAtk < 0)
                    {
                        collides.frameAdd(fCDAtk);
                        indexCDAtk = collides.frameGetCount() - 1;
                    }
                    // def
                    int indexCDDef = collides.frameIndexOf(fCDDef);
                    if (indexCDDef < 0)
                    {
                        collides.frameAdd(fCDDef);
                        indexCDDef = collides.frameGetCount() - 1;
                    }
                    // ext
                    int indexCDExt = collides.frameIndexOf(fCDExt);
                    if (indexCDExt < 0)
                    {
                        collides.frameAdd(fCDExt);
                        indexCDExt = collides.frameGetCount() - 1;
                    }

                    frameCDMap[animID][frameID] = indexCDMap;
                    frameCDAtk[animID][frameID] = indexCDAtk;
                    frameCDDef[animID][frameID] = indexCDDef;
                    frameCDExt[animID][frameID] = indexCDExt;
                }
            }
        }


        public void OutputCustom(int index, String script, System.IO.StringWriter output)
        {
            lock (this)
			{
				Frame AllFrame = new Frame();
				Group animates = new Group();
				Group collides = new Group();

				String[] frameName = new string[listView2.Items.Count];
				int[][] frameAnimate = new int[listView2.Items.Count][];

				float[][] frameAlpha = new float[listView2.Items.Count][];
				float[][] frameRotate = new float[listView2.Items.Count][];
				float[][] frameScaleX = new float[listView2.Items.Count][];
				float[][] frameScaleY = new float[listView2.Items.Count][];
				float[][] frameShearX = new float[listView2.Items.Count][];
				float[][] frameShearY = new float[listView2.Items.Count][];

				int[][] frameCDMap = new int[listView2.Items.Count][];
				int[][] frameCDAtk = new int[listView2.Items.Count][];
				int[][] frameCDDef = new int[listView2.Items.Count][];
                int[][] frameCDExt = new int[listView2.Items.Count][];

                string[][] frameAdata = new string[listView2.Items.Count][];

				
                try
                {
					initOutput(
						AllFrame,
						 animates,
						 collides,

						frameName,
						 frameAnimate,

						 frameAlpha,
						 frameRotate,
						 frameScaleX,
						 frameScaleY,
						 frameShearX,
						 frameShearY,

						 frameCDMap,
						 frameCDAtk,
						 frameCDDef,
						 frameCDExt,
                         frameAdata);

                    String sprite = Util.getFullTrunkScript(script, "<SPRITE>", "</SPRITE>");

                    bool fix = false;

                    // OutputAnimates part
                    do
                    {
                        String[] senceParts = new string[AllFrame.getSubCount()];
                        for (int i = 0; i < senceParts.Length; i++)
                        {
                            string X = ((int)AllFrame.SubX[i]).ToString();
                            string Y = ((int)AllFrame.SubY[i]).ToString();
                            string Z = ((int)AllFrame.SubZ[i]).ToString();
                            string TILE = ((int)AllFrame.SubIndex[i]).ToString();
                            string TRANS = AllFrame.SubFlip[i].ToString();

							string ALPHA = AllFrame.SubTAlpha[i].ToString();
							string ROTATE = AllFrame.SubTAlpha[i].ToString();
							string SCALE_X = AllFrame.SubTScaleX[i].ToString();
							string SCALE_Y = AllFrame.SubTScaleY[i].ToString();
							string SHEAR_X = AllFrame.SubTShearX[i].ToString();
							string SHEAR_Y = AllFrame.SubTShearY[i].ToString();

                            senceParts[i] = Util.replaceKeywordsScript(sprite, "<SCENE_PART>", "</SCENE_PART>",
                                new string[] { "<INDEX>", "<X>", "<Y>", "<Z>", "<TILE>", "<TRANS>", 
									"<ALPHA>", "<ROTATE>", "<SCALE_X>", "<SCALE_Y>", "<SHEAR_X>", "<SHEAR_Y>" },
                                new string[] { i.ToString(), X, Y, Z, TILE, TRANS ,
									ALPHA, ROTATE, SCALE_X, SCALE_Y, SHEAR_X, SHEAR_Y
								}
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(sprite, "<SCENE_PART>", "</SCENE_PART>", senceParts);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            sprite = temp;
                        }
                    } while (fix);


                    // OutputAnimates frame
                    do
                    {
                        String[] senceFrames = new string[animates.frameGetCount()];
                        for (int i = 0; i < senceFrames.Length; i++)
                        {

                            int[] frames = (int[])(animates.frameGetFrame(i).ToArray(typeof(int)));
                            string DATA = Util.toNumberArray1D<int>(ref frames);


                            senceFrames[i] = Util.replaceKeywordsScript(sprite, "<SCENE_FRAME>", "</SCENE_FRAME>",
                                new string[] { "<INDEX>", "<DATA_SIZE>", "<DATA>" },
                                new string[] { i.ToString(), animates.frameGetFrame(i).Count.ToString(), DATA }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(sprite, "<SCENE_FRAME>", "</SCENE_FRAME>", senceFrames);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            sprite = temp;
                        }
                    } while (fix);

                    // cd part
                    do
                    {
                        String[] cdParts = new string[AllFrame.getCDCount()];
                        for (int i = 0; i < cdParts.Length; i++)
                        {
                            string TYPE = "rect";
                            string MASK = ((int)AllFrame.CDMask[i]).ToString();
                            string X1 = ((int)AllFrame.CDX[i]).ToString();
                            string Y1 = ((int)AllFrame.CDY[i]).ToString();
                            string W = ((int)AllFrame.CDW[i]).ToString();
                            string H = ((int)AllFrame.CDH[i]).ToString();
                            string X2 = ((int)(((int)AllFrame.CDX[i]) + ((int)AllFrame.CDW[i]))).ToString();
                            string Y2 = ((int)(((int)AllFrame.CDY[i]) + ((int)AllFrame.CDH[i]))).ToString();

                            cdParts[i] = Util.replaceKeywordsScript(sprite, "<CD_PART>", "</CD_PART>",
                                new string[] { "<INDEX>", "<TYPE>", "<MASK>", "<X1>", "<Y1>", "<W>", "<H>", "<X2>", "<Y2>" },
                                new string[] { i.ToString(), TYPE, MASK, X1, Y1, W, H, X2, Y2 }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(sprite, "<CD_PART>", "</CD_PART>", cdParts);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            sprite = temp;
                        }
                    } while (fix);


                    // cd frame
                    do
                    {
                        String[] cdFrames = new string[collides.frameGetCount()];
                        for (int i = 0; i < cdFrames.Length; i++)
                        {
                            int[] frame = (int[])(collides.frameGetFrame(i).ToArray(typeof(int)));
                            string DATA = Util.toNumberArray1D<int>(ref frame);

                            cdFrames[i] = Util.replaceKeywordsScript(sprite, "<CD_FRAME>", "</CD_FRAME>",
                                new string[] { "<INDEX>", "<DATA_SIZE>", "<DATA>" },
                                new string[] { i.ToString(), collides.frameGetFrame(i).Count.ToString(), DATA }
                                );
                        }
                        string temp = Util.replaceSubTrunksScript(sprite, "<CD_FRAME>", "</CD_FRAME>", cdFrames);
                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            sprite = temp;
                        }
                    } while (fix);

                    /*
                    // sprframes
                    String outFrameName = "";
                    String outFrameAnimate = "";
                    String outFrameCDMap = "";
                    String outFrameCDAtk = "";
                    String outFrameCDDef = "";
                    String outFrameCDExt = "";

                    for (int i = 0; i < frameName.Length; i++)
                        outFrameName += "\"" + frameName[i] + "\",\r\n"; 

                    for (int i = 0; i < frameAnimate.Length; i++)
                        outFrameAnimate += "{" + Util.toTextArray(frameAnimate[i]) + "},\r\n";
                    for (int i = 0; i < frameCDMap.Length; i++)
                        outFrameCDMap += "{" + Util.toTextArray(frameCDMap[i]) + "},\r\n";
                    for (int i = 0; i < frameCDAtk.Length; i++)
                        outFrameCDAtk += "{" + Util.toTextArray(frameCDAtk[i]) + "},\r\n";
                    for (int i = 0; i < frameCDDef.Length; i++)
                        outFrameCDDef += "{" + Util.toTextArray(frameCDDef[i]) + "},\r\n";
                    for (int i = 0; i < frameCDExt.Length; i++)
                        outFrameCDExt += "{" + Util.toTextArray(frameCDExt[i]) + "},\r\n";

                    int[] frameCounts = new int[frameName.Length];
                    for (int i = 0; i < frameAnimate.Length; i++)
                        frameCounts[i] = frameAnimate[i].Length;
                    String outFrameCounts = Util.toTextArray(frameCounts);
                    */

                    // sprframes

                    String outFrameAnimate = Util.toNumberArray2D<int>(ref frameAnimate);
                    String outFrameCDMap = Util.toNumberArray2D<int>(ref frameCDMap);
                    String outFrameCDAtk = Util.toNumberArray2D<int>(ref frameCDAtk);
                    String outFrameCDDef = Util.toNumberArray2D<int>(ref frameCDDef);
                    String outFrameCDExt = Util.toNumberArray2D<int>(ref frameCDExt);

					String outFrameAlpha = Util.toNumberArray2D<float>(ref frameAlpha);
					String outFrameRotate = Util.toNumberArray2D<float>(ref frameRotate);
					String outFrameScaleX = Util.toNumberArray2D<float>(ref frameScaleX);
					String outFrameScaleY = Util.toNumberArray2D<float>(ref frameScaleY);
					String outFrameShearX = Util.toNumberArray2D<float>(ref frameShearX);
					String outFrameShearY = Util.toNumberArray2D<float>(ref frameShearY);

                    String outFrameAdata = Util.toStringArray2D(ref frameAdata);

                    int[] frameCounts = new int[frameName.Length];
                    for (int i = 0; i < frameAnimate.Length; i++)
                        frameCounts[i] = frameAnimate[i].Length;
                    String outFrameCounts = Util.toNumberArray1D<int>(ref frameCounts);

                    String outFrameName = Util.toStringArray1D(ref frameName);


					string[] adata = Util.toStringMultiLine(append_data);
					string APPEND_DATA = Util.toStringArray1D(ref adata);

                    sprite = Util.replaceKeywordsScript(sprite, "<SPRITE>", "</SPRITE>",
                    new string[] { 
						"<NAME>", 
						"<SPR_INDEX>",
						"<IMAGES_NAME>",
						"<SCENE_PART_COUNT>" ,
						"<SCENE_FRAME_COUNT>" ,
						"<CD_PART_COUNT>",
						"<CD_FRAME_COUNT>",
						"<ANIMATE_COUNT>",
						"<FRAME_COUNTS>",
						"<FRAME_NAME>",
						"<FRAME_ANIMATE>",

						"<COMPLEX_MODE>",
						"<FRAME_ALPHA>",
						"<FRAME_ROTATE>",
						"<FRAME_SCALE_X>",
						"<FRAME_SCALE_Y>",
						"<FRAME_SHEAR_X>",
						"<FRAME_SHEAR_Y>",

						"<FRAME_CD_MAP>",
						"<FRAME_CD_ATK>",
						"<FRAME_CD_DEF>",
						"<FRAME_CD_EXT>",
                        "<FRAME_APPEND_DATA>",
						"<APPEND_DATA>",
                    },
                        new string[] { 
						this.id,
						index.ToString(),
						super.id,
						AllFrame.getSubCount().ToString(),
						animates.frameGetCount().ToString(),
						AllFrame.getCDCount().ToString(),
						collides.frameGetCount().ToString(),
						frameAnimate.Length.ToString(),
						outFrameCounts,
						outFrameName,
						outFrameAnimate,

						checkComplexMode.Checked.ToString(),
						outFrameAlpha,
						outFrameRotate,
						outFrameScaleX,
						outFrameScaleY,
						outFrameShearX,
						outFrameShearY,

						outFrameCDMap,
						outFrameCDAtk,
						outFrameCDDef,
						outFrameCDExt,
                        outFrameAdata,

						APPEND_DATA}
                        );

                    output.WriteLine(sprite);
                    //Console.WriteLine(sprite);
                }
                catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }

            }
        }

        private void SpriteForm_Load(object sender, EventArgs e)
        {
            splitContainer6.Panel1.Enabled = false; 
            pictureBox1.Width = 1;
            pictureBox1.Height = 1;
            pictureBoxMain.Width = dstPanelSize;
            pictureBoxMain.Height = dstPanelSize;
            pictureBoxMain.Image = new System.Drawing.Bitmap(dstPanelSize, dstPanelSize);
            try
            {
                panel2.HorizontalScroll.Value = panel2.HorizontalScroll.Maximum / 2 - panel2.Width / 2;
                panel2.VerticalScroll.Value = panel2.VerticalScroll.Maximum / 2 - panel2.Height / 2;

            }
            catch (Exception err) { }
            dstRefersh();
            //timer1.Start();

        }
        private void SpriteForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }
        private void SpriteForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

        public System.Drawing.Rectangle getVisibleBounds(int anim, int frame) 
        {
            try
            {

                Frame curFrame = (Frame)((ArrayList)AnimTable[listView2.Items[anim]])[frame % GetFrameCount(anim)];

                if (curFrame != null)
                {
                    return curFrame.getVisibleBounds();
                }

            }
            catch (Exception err)
            {
            }

            return new System.Drawing.Rectangle();
        }

		public void Render(javax.microedition.lcdui.Graphics g, float x, float y, Boolean tag, int anim, int frame)
        {

            try
            {

                Frame curFrame = (Frame)((ArrayList)AnimTable[listView2.Items[anim]])[frame % GetFrameCount(anim)];

                if (curFrame != null)
                {
                    curFrame.render(g, srcTiles, x, y, false, tag, checkComplexMode.Checked);
                }

            }catch(Exception err){
            }

        }

		public Frame getFrame(int anim, int frame)
		{
			try
			{
				return (Frame)((ArrayList)AnimTable[listView2.Items[anim]])[frame];
			}
			catch (Exception err)
			{
			} return null;
		}

        public int GetFrameCount(int animID)
        {
            try
            {
                return ((ArrayList)AnimTable[listView2.Items[animID]]).Count;
            }
            catch (Exception err)
            {
                return -1;
            }
        }
        public int GetAnimateCount()
        {
            try
            {
                return listView2.Items.Count;
            }
            catch (Exception err)
            {
                return -1;
            }
        }
// src tile
        private Image srcGetImage(int index)
        {
            return (((Image)(srcTiles[index])));
        }
        private int srcGetCount()
        {
            return srcTiles.Count;
        }
        private void srcRender(Graphics g, int index, int flip, int x, int y, Boolean showimageborder)
        {
            Image img = srcGetImage(index);
            if (img != null && !img.killed && flip < Graphics.FlipTable.Length)
            {
                g.drawImageScale(img, x, y, Graphics.FlipTable[flip], srcScale);

                if (showimageborder)
                {
                    g.setColor(0x80, 0xff, 0xff, 0xff);
                    g.drawRect(x, y, img.getWidth(), img.getHeight());
                }

            }

        }
        private void srcRenderAll(Graphics g, int x, int y, System.Drawing.Rectangle screen, Boolean showimageborder)
        {
            for (int i = 0; i < srcGetCount(); i++)
            {
                if (srcGetImage(i) != null &&
                    screen.IntersectsWith(new System.Drawing.Rectangle(
                        x + srcGetImage(i).x * srcScale,
                        y + srcGetImage(i).y * srcScale,
                        srcGetImage(i).getWidth() * srcScale,
                        srcGetImage(i).getHeight() * srcScale)
                    ))
                {
                    srcRender(g, i, 0, x + srcGetImage(i).x * srcScale, y + srcGetImage(i).y * srcScale, showimageborder);
                }
            }
            
        }

        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                System.Drawing.Rectangle dst = new System.Drawing.Rectangle(0, 0, 1, 1);
                for (int i = 0; i < srcGetCount(); i++)
                {
                    if (srcGetImage(i) != null && srcGetImage(i).killed == false)
                    {
                        dst.X = srcGetImage(i).x * srcScale;
                        dst.Y = srcGetImage(i).y * srcScale;
                        dst.Width = srcGetImage(i).getWidth() * srcScale;
                        dst.Height = srcGetImage(i).getHeight() * srcScale;

                        if (dst.Contains(e.X, e.Y))
                        {

                            srcRect = dst;
                            //srcIndex = i;
                            srcImage = srcGetImage(i);

                            toolStripLabel1.Text =
                                "第" + i + "号" +
                                " 宽：" + srcGetImage(i).getWidth() +
                                " 高：" + srcGetImage(i).getHeight();

                           
                            pictureBox1.Refresh();
                            break;
                        }
                    }
                }
            }
        }
        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            for (int i = srcGetCount() - 1; i >= 0; i--)
            {
                if (srcGetImage(i) != null)
                {
                    pictureBox1.Width = Math.Max(
                        pictureBox1.Width,
                        (srcGetImage(i).x + srcGetImage(i).getWidth()) * srcScale
                        );
                    pictureBox1.Height = Math.Max(
                        pictureBox1.Height,
                        (srcGetImage(i).y + srcGetImage(i).getHeight()) * srcScale
                        );
                    //break;
                }
            }


            Graphics g = new Graphics(e.Graphics);


            srcRenderAll(g, 0, 0,
                new System.Drawing.Rectangle(
                    -pictureBox1.Location.X,
                    -pictureBox1.Location.Y,
                    (int)(panel1.Width * srcScale),
                    (int)(panel1.Height * srcScale)
                ),
                chkShowImageBorder.Checked
            );

            System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xFF, 0xFF, 0xFF, 0xFF));
            System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;

            e.Graphics.FillRectangle(brush, srcRect);
            e.Graphics.DrawRectangle(pen, srcRect.X, srcRect.Y, srcRect.Width - 1, srcRect.Height );
        }

        // +
        private void toolStripButton31_Click(object sender, EventArgs e)
        {
            srcScale += 1;
            if (srcScale > 8) srcScale = 8;
            pictureBox1.Refresh();
        }
        // -
        private void toolStripButton32_Click(object sender, EventArgs e)
        {
            srcScale -= 1;
            if (srcScale < 1) srcScale = 1;
            pictureBox1.Refresh();
        }



// dst part

        Boolean dstRefreshEnable = true;
        private void dstRefersh()
        {
            if (!dstRefreshEnable) return ;

            dstSelectSub();
            dstSelectCD();

            try
            {
                framesGetCurFrame().updatePartListView(listView3);
                framesGetCurFrame().updateCDListView(listView4);

                pictureBoxMain.Refresh();
                pictureBox3.Refresh();
            }
            catch (Exception err)
            {
            }
        }

        private void dstSelectSub()
        {
            for (int i = 0;framesGetCurFrame()!=null &&  i < framesGetCurFrame().SubSelected.Count; i++)
            {
                framesGetCurFrame().SubSelected[i] = false;
            }

            dstSubSelected.Clear();

            if (listView3.SelectedItems != null &&
                listView3.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null 
                )
            {
               
                foreach(ListViewItem item in listView3.SelectedItems)
                {
                    if (framesGetCurFrame().SubTable.Contains(item))
                    {
                        framesGetCurFrame().SubSelected[framesGetCurFrame().SubTable.IndexOf(item)] = true;
                        dstSubSelected.Add(framesGetCurFrame().SubTable.IndexOf(item));
                    }
                }
            }
           
        }
        private void dstSelectCD()
        {
            for (int i = 0;framesGetCurFrame()!=null && i < framesGetCurFrame().CDSelected.Count; i++)
            {
                framesGetCurFrame().CDSelected[i] = false;
            }

            dstCDSelected.Clear();

            if (listView4.SelectedItems != null &&
                listView4.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null )
            {
                foreach (ListViewItem item in listView4.SelectedItems)
                {
                    if (framesGetCurFrame().CDTable.Contains(item))
                    {
                        framesGetCurFrame().CDSelected[framesGetCurFrame().CDTable.IndexOf(item)] = true;
                        dstCDSelected.Add(framesGetCurFrame().CDTable.IndexOf(item));
                    }
                }
            }
           
        }

        ArrayList dstSubSelected = new ArrayList();
        ArrayList dstCDSelected = new ArrayList();
        private int[] dstGetCurSubIndexes()
        {
            return (int[])dstSubSelected.ToArray(typeof(int));
        }
        private int[] dstGetCurCDIndexes()
        {
            return (int[])dstCDSelected.ToArray(typeof(int));
        }


        private void dstDelSelectSub()
        {
            if (listView3.SelectedItems != null &&
                listView3.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null)
            {
                foreach (ListViewItem item in listView3.SelectedItems)
                {
                    if (framesGetCurFrame().SubTable.Contains(item))
                    {
                        framesGetCurFrame().delFSub(framesGetCurFrame().SubTable.IndexOf(item));
                        listView3.Items.Remove(item);
                    }
                }
            }
        }
        private void dstDelSelectCD()
        {
            if (listView4.SelectedItems != null &&
                listView4.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null)
            {
                foreach (ListViewItem item in listView4.SelectedItems)
                {
                    if (framesGetCurFrame().CDTable.Contains(item))
                    {
                        framesGetCurFrame().delFCD(framesGetCurFrame().CDTable.IndexOf(item));
                        listView4.Items.Remove(item);
                    }
                }
            }
        }

        static Frame clipFrame = null ;

        private void dstCopySelectSub()
        {
            if (listView3.SelectedItems != null &&
                listView3.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null)
            {
                clipFrame = new Frame();

                foreach (ListViewItem item in listView3.SelectedItems)
                {
                    int index = framesGetCurFrame().SubTable.IndexOf(item);
                    if (index>=0)
                    {
                       clipFrame.addFSub(
                           (int)framesGetCurFrame().SubIndex[index],
                           (int)framesGetCurFrame().SubX[index],
                           (int)framesGetCurFrame().SubY[index],
                           (int)framesGetCurFrame().SubZ[index],
                           (int)framesGetCurFrame().SubW[index],
                           (int)framesGetCurFrame().SubH[index],
                           (int)framesGetCurFrame().SubFlip[index],

							(float)framesGetCurFrame().SubTRotate[index],
							(float)framesGetCurFrame().SubTScaleX[index],
							(float)framesGetCurFrame().SubTScaleY[index],
							(float)framesGetCurFrame().SubTAlpha[index],
							(float)framesGetCurFrame().SubTShearX[index],
							(float)framesGetCurFrame().SubTShearY[index]

                           );
                    }
                }
            }
        }
        private void dstCopySelectCD()
        {
            if (listView4.SelectedItems != null &&
                listView4.SelectedItems.Count > 0 &&
                framesGetCurFrame() != null)
            {
                clipFrame = new Frame();

                foreach (ListViewItem item in listView4.SelectedItems)
                {
                    int index = framesGetCurFrame().CDTable.IndexOf(item);
                    if (index >= 0)
                    {
                        clipFrame.addFCD(
                            (int)framesGetCurFrame().CDMask[index],
                            (int)framesGetCurFrame().CDX[index],
                            (int)framesGetCurFrame().CDY[index],
                            (int)framesGetCurFrame().CDW[index],
                            (int)framesGetCurFrame().CDH[index],
                            (int)framesGetCurFrame().CDType[index]
                            );
                    }
                }
            }
        }
        private void dstPasteSelectSub()
        {
            try
            {
                if (listView3.SelectedItems != null &&
                    listView3.SelectedItems.Count > 0 &&
                    framesGetCurFrame() != null)
                {
                    foreach (ListViewItem item in listView3.SelectedItems)
                    {
                        item.Selected = false;
                        item.Focused = false;
                    }
                }

                if (framesGetCurFrame() != null && clipFrame != null)
                {
                    for (int i = 0; i < clipFrame.getSubCount(); i++)
                    {
						ListViewItem item = framesGetCurFrame().insertFSub(0,
                            (int)clipFrame.SubIndex[i],
                            (int)clipFrame.SubX[i],
                            (int)clipFrame.SubY[i],
                            (int)clipFrame.SubZ[i],
                            (int)clipFrame.SubW[i],
                            (int)clipFrame.SubH[i],
							(int)clipFrame.SubFlip[i],
							(float)clipFrame.SubTRotate[i],
							(float)clipFrame.SubTScaleX[i],
							(float)clipFrame.SubTScaleY[i],
							(float)clipFrame.SubTAlpha[i],
							(float)clipFrame.SubTShearX[i],
							(float)clipFrame.SubTShearY[i]
							);
						item.Checked = true;
                        item.Focused = true;
						item.Selected = true; 
						listView3.Items.Insert(0, item);
                    }
                }
            }
            catch (Exception err)
            { }
        }
        private void dstPasteSelectCD()
        {
            try
            {
                if (listView4.SelectedItems != null &&
                    listView4.SelectedItems.Count > 0 &&
                    framesGetCurFrame() != null)
                {
                    foreach (ListViewItem item in listView4.SelectedItems)
                    {
                        item.Selected = false;
                        item.Focused = false;
                    }
                }

                if (framesGetCurFrame() != null && clipFrame != null)
                {
                    for (int i = 0; i < clipFrame.getCDCount(); i++)
                    {
						ListViewItem item = framesGetCurFrame().insertFCD(
                            0,
                            (int)clipFrame.CDMask[i],
                            (int)clipFrame.CDX[i],
                            (int)clipFrame.CDY[i],
                            (int)clipFrame.CDW[i],
                            (int)clipFrame.CDH[i],
                            (int)clipFrame.CDType[i]
                            );
                        item.Focused = true;
						item.Selected = true;
						item.Checked = true;
						listView4.Items.Insert(0, item);
                    }
                }
            }
            catch (Exception err) { }
        }
        private void dstPasteSelectSubAll()
        {
            try
            {
                if (listView3.SelectedItems != null &&
                    listView3.SelectedItems.Count > 0 &&
                    framesGetCurFrame() != null)
                {
                    foreach (ListViewItem item in listView3.SelectedItems)
                    {
                        item.Selected = false;
                        item.Focused = false;
                    }
                }

                for (int f = 0; f < animGetCurFrames().Count; f++)
                {
                    Frame curFrame = (Frame)animGetCurFrames()[f];

                    // if (curFrame == framesGetCurFrame()) continue;

                    if (curFrame != null && clipFrame != null)
                    {
                        for (int i = 0; i < clipFrame.getSubCount(); i++)
                        {
							ListViewItem item = curFrame.insertFSub(0,
                                (int)clipFrame.SubIndex[i],
                                (int)clipFrame.SubX[i],
                                (int)clipFrame.SubY[i],
                                (int)clipFrame.SubZ[i],
                                (int)clipFrame.SubW[i],
                                (int)clipFrame.SubH[i],
								(int)clipFrame.SubFlip[i],
							(float)clipFrame.SubTRotate[i],
							(float)clipFrame.SubTScaleX[i],
							(float)clipFrame.SubTScaleY[i],
							(float)clipFrame.SubTAlpha[i],
							(float)clipFrame.SubTShearX[i],
							(float)clipFrame.SubTShearY[i]
								); 
							item.Checked = true;
                            item.Focused = true;
                            item.Selected = true;
                        }
                    }
                }
            }
            catch (Exception err) { }
        }
        private void dstPasteSelectCDAll()
        {
            try
            {
                if (listView4.SelectedItems != null &&
                     listView4.SelectedItems.Count > 0 &&
                     framesGetCurFrame() != null)
                {
                    foreach (ListViewItem item in listView4.SelectedItems)
                    {
                        item.Selected = false;
                        item.Focused = false;
                    }
                }

                for (int f = 0; f < animGetCurFrames().Count; f++)
                {
                    Frame curFrame = (Frame)animGetCurFrames()[f];

                    // if (curFrame == framesGetCurFrame()) continue;

                    if (curFrame != null && clipFrame != null)
                    {
                        for (int i = 0; i < clipFrame.getCDCount(); i++)
                        {
							ListViewItem item = curFrame.insertFCD(0,
                                (int)clipFrame.CDMask[i],
                                (int)clipFrame.CDX[i],
                                (int)clipFrame.CDY[i],
                                (int)clipFrame.CDW[i],
                                (int)clipFrame.CDH[i],
                                (int)clipFrame.CDType[i]
								);
							item.Checked = true;
                            item.Focused = true;
                            item.Selected = true;
                        }
                    }
                }
            }
            catch (Exception err) { }
        }


        private void dstAddPart()
        {
			dstAddPart(srcImage);
        }

		private void dstAddPart(Image img)
		{
			Frame curf = framesGetCurFrame();
			if (curf != null)
			{
				ListViewItem item = curf.insertFSub(
						0,
						img.indexOfImages,
						-img.getWidth() / 2,
						-img.getHeight() / 2,
                        0,
						img.getWidth(),
						img.getHeight(),
						0, 0, 1, 1, 1, 0, 0
						);
				item.Focused = true;
				item.Selected = true;
				item.Checked = true;
				//listView3.Items.Add(item);
				listView3.Items.Insert(0, item);
			}
		}

        private void dstAddCD()
        {
            if (framesGetCurFrame() != null)
            {
				ListViewItem item = framesGetCurFrame().addFCD(
                    (int)(0x0000ffff),
                    -srcImage.getWidth() / 2,
                    -srcImage.getHeight() / 2,
                    srcImage.getWidth(),
                    srcImage.getHeight(),
                    Frame.CD_TYPE_MAP
                    );
                item.Focused = true;
				item.Selected = true; 
				item.Checked = true;
				listView4.Items.Add(item);
            }
        }
        
        // main editor
        private void toolStripButton10_Click(object sender, EventArgs e)
        {
            dstAddPart();
            dstRefersh();
        }
        private void toolStripButton11_Click(object sender, EventArgs e)
        {
            dstAddCD();
            dstRefersh();
        }
        private void toolStripButton20_Click(object sender, EventArgs e)
        {
            try
            {
                if (dstGetCurSubIndexes().Length == 1)
                {
                    int flip = (int)framesGetCurFrame().SubFlip[dstGetCurSubIndexes()[0]];

                    framesGetCurFrame().SubIndex[dstGetCurSubIndexes()[0]] = srcImage.indexOfImages;

                    switch (Graphics.FlipTable[flip])
                    {
                        case System.Drawing.RotateFlipType.RotateNoneFlipNone:
                        case System.Drawing.RotateFlipType.Rotate180FlipNone:
                        case System.Drawing.RotateFlipType.Rotate180FlipX:
                        case System.Drawing.RotateFlipType.RotateNoneFlipX:
							framesGetCurFrame().SubW[dstGetCurSubIndexes()[0]] = srcImage.getWidth();
							framesGetCurFrame().SubH[dstGetCurSubIndexes()[0]] = srcImage.getHeight();
                            break;
                        case System.Drawing.RotateFlipType.Rotate90FlipNone:
                        case System.Drawing.RotateFlipType.Rotate270FlipNone:
                        case System.Drawing.RotateFlipType.Rotate270FlipX:
                        case System.Drawing.RotateFlipType.Rotate90FlipX:
							framesGetCurFrame().SubW[dstGetCurSubIndexes()[0]] = srcImage.getHeight();
							framesGetCurFrame().SubH[dstGetCurSubIndexes()[0]] = srcImage.getWidth();
                            break;
                    }

                    dstRefersh();
                }
            }
            catch (Exception err) { }
            dstRefersh();
        }

        private void toolStripButton15_Click(object sender, EventArgs e)
        {
            if (tabControl1.SelectedIndex == 0)
            {
                dstCopySelectSub();
                //Console.WriteLine("Copy Sub !");
            }
            if (tabControl1.SelectedIndex == 1)
            {
                dstCopySelectCD();
            }
            //framesRefersh();
            dstRefersh();
        }
        private void toolStripButton19_Click(object sender, EventArgs e)
        {
            if (tabControl1.SelectedIndex == 0)
            {
                dstPasteSelectSub();
                //Console.WriteLine("Paste Sub !");
            }
            if (tabControl1.SelectedIndex == 1)
            {
                dstPasteSelectCD();
            }
            //framesRefersh();
            dstRefersh();
        }
        private void toolStripButton21_Click(object sender, EventArgs e)
        {
            //all
            if (tabControl1.SelectedIndex == 0)
            {
                dstPasteSelectSubAll();
                //Console.WriteLine("Paste Sub !");
            }
            if (tabControl1.SelectedIndex == 1)
            {
                dstPasteSelectCDAll();
            }
            framesRefersh();
            dstRefersh();
        }
        private void toolStripButton16_Click(object sender, EventArgs e)
        {
            if (tabControl1.SelectedIndex == 0)
            {
                dstDelSelectSub();
                //Console.WriteLine("Del Sub !");
            }
            if (tabControl1.SelectedIndex == 1)
            {
                dstDelSelectCD();
            }
            //framesRefersh();
            dstRefersh();
        }

        private void 复制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton15_Click(null, null);
        }
        private void 粘贴ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton19_Click(null, null);
        }
        private void 粘贴到所有帧ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton21_Click(null, null);
        }
        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton16_Click(null, null);
        }

        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            framesRefersh();
        }
        
        
        //scale
        private void toolStripButton27_Click(object sender, EventArgs e)
        {
			curMasterScale += 1;
			if (curMasterScale > 8) curMasterScale = 8;
            dstGridChange();
            dstRefersh();

        }
        private void toolStripButton28_Click(object sender, EventArgs e)
        {
			curMasterScale -= 1;
			if (curMasterScale < 1) curMasterScale = 1;
            dstGridChange();
            dstRefersh();
        }

        //sub pri
        private void button1_Click(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1 &&
               dstGetCurSubIndexes()[0] - 1 < framesGetCurFrame().getSubCount() &&
               dstGetCurSubIndexes()[0] - 1 >= 0)
            {
                framesGetCurFrame().exchangeSub(dstGetCurSubIndexes()[0], dstGetCurSubIndexes()[0] - 1);
            }
            dstRefersh();

        }
        private void button2_Click(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1 &&
                dstGetCurSubIndexes()[0] + 1 < framesGetCurFrame().getSubCount() &&
                dstGetCurSubIndexes()[0] + 1 >= 0)
            {
                framesGetCurFrame().exchangeSub(dstGetCurSubIndexes()[0], dstGetCurSubIndexes()[0] + 1);
            }
            dstRefersh();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                framesGetCurFrame().exchangeSub(dstGetCurSubIndexes()[0], 0);
            }
            catch (Exception err)
            {
            }
            dstRefersh();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            try
            {
                framesGetCurFrame().exchangeSub(dstGetCurSubIndexes()[0], framesGetCurFrame().getSubCount() - 1);
            }
            catch (Exception err)
            {
            }
            dstRefersh();
        }

        //cd pri

        private void cdUP_Click(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1 &&
                dstGetCurCDIndexes()[0] - 1 < framesGetCurFrame().getCDCount() &&
                dstGetCurCDIndexes()[0] - 1 >= 0)
            {
                framesGetCurFrame().exchangeCD(dstGetCurCDIndexes()[0], dstGetCurCDIndexes()[0] - 1);
            }
            dstRefersh();
        }

        private void cdDown_Click(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1 &&
                dstGetCurCDIndexes()[0] + 1 < framesGetCurFrame().getCDCount() &&
                dstGetCurCDIndexes()[0] + 1 >= 0)
            {
                framesGetCurFrame().exchangeCD(dstGetCurCDIndexes()[0], dstGetCurCDIndexes()[0] + 1);
            }
            dstRefersh();
        }

        //

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1)
            {
                framesGetCurFrame().SubX[dstGetCurSubIndexes()[0]] = (int)numericUpDown1.Value;
            } 
            dstRefersh();
            
        }
        private void numericUpDown3_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1)
            {
                framesGetCurFrame().SubY[dstGetCurSubIndexes()[0]] = (int)numericUpDown3.Value;
            }
            dstRefersh();
        }
        private void numericUpDown9_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1)
            {
                framesGetCurFrame().SubZ[dstGetCurSubIndexes()[0]] = (int)numericUpDown9.Value;
            }
            dstRefersh();
        }

		private void numPartAlpha_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTAlpha[dstGetCurSubIndexes()[0]] = (float)numPartAlpha.Value;
			}
			dstRefersh();
		}

		private void numPartRotate_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTRotate[dstGetCurSubIndexes()[0]] = (float)numPartRotate.Value;
			}
			dstRefersh();
		}

		private void numPartScaleX_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTScaleX[dstGetCurSubIndexes()[0]] = (float)numPartScaleX.Value;
			}
			dstRefersh();
		}

		private void numPartScaleY_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTScaleY[dstGetCurSubIndexes()[0]] = (float)numPartScaleY.Value;
			}
			dstRefersh();
		}

		private void numPartShearX_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTShearX[dstGetCurSubIndexes()[0]] = (float)numPartShearX.Value;
			}
			dstRefersh();
		}

		private void numPartShearY_ValueChanged(object sender, EventArgs e)
		{
			if (dstGetCurSubIndexes().Length == 1)
			{
				framesGetCurFrame().SubTShearY[dstGetCurSubIndexes()[0]] = (float)numPartShearY.Value;
			}
			dstRefersh();
		}


        /*
        private void toolStripMenuItem10_Click(object sender, EventArgs e)
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

            if (dstGetCurSubIndexes().Length == 1)
            {
                int flip = 0;
                if (sender.Equals(toolStripMenuItem10)) flip = 0;
                if (sender.Equals(toolStripMenuItem11)) flip = 1;
                if (sender.Equals(toolStripMenuItem12)) flip = 2;
                if (sender.Equals(toolStripMenuItem13)) flip = 3;
                if (sender.Equals(toolStripMenuItem14)) flip = 4;
                if (sender.Equals(toolStripMenuItem15)) flip = 5;
                if (sender.Equals(toolStripMenuItem16)) flip = 6;
                if (sender.Equals(toolStripMenuItem17)) flip = 7;

                framesGetCurFrame().flipSub(dstGetCurSubIndexes()[0], flip);
            }
            
            dstRefersh();
        }*/
        private void refreshCurPartEditBox()
        {
            Frame frame = framesGetCurFrame();
            if (frame != null)
            {
                int[] subIndexes = dstGetCurSubIndexes();
                if (subIndexes!=null && subIndexes.Length>0)
                {
                    int index = subIndexes[0];

                    numericUpDown1.Value = (Decimal)((int)frame.SubX[index]);
                    numericUpDown3.Value = (Decimal)((int)frame.SubY[index]);
                    numericUpDown9.Value = (Decimal)((int)frame.SubZ[index]);
                    PartW.Text = "" + frame.SubW[index];
                    PartH.Text = "" + frame.SubH[index];
                    numPartAlpha.Value = (Decimal)((float)frame.SubTAlpha[index]);
                    numPartRotate.Value = (Decimal)((float)frame.SubTRotate[index]);
                    numPartScaleX.Value = (Decimal)((float)frame.SubTScaleX[index]);
                    numPartScaleY.Value = (Decimal)((float)frame.SubTScaleY[index]);
                    numPartShearX.Value = (Decimal)((float)frame.SubTShearX[index]);
                    numPartShearY.Value = (Decimal)((float)frame.SubTShearY[index]);


                    imageFlipToolStripButton1.setFlipIndex((int)frame.SubFlip[index]);
                }
            }
        }

        private void listView3_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
            dstRefreshEnable = false;
            dstSelectSub();

            if (dstGetCurSubIndexes().Length == 1)
            {
                splitContainer6.Panel1.Enabled = true;

                refreshCurPartEditBox();
            } 
            else
            {
                splitContainer6.Panel1.Enabled = false;
            }

            PartState.Text = "" + e.ItemIndex;

         

            pictureBoxMain.Refresh();
            dstRefreshEnable = true;
            
        }
        //cd
        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1)
            {
                framesGetCurFrame().CDX[dstGetCurCDIndexes()[0]] = (int)numericUpDown4.Value;
            }
            dstRefersh();
        }
        private void numericUpDown5_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1)
            {
                framesGetCurFrame().CDY[dstGetCurCDIndexes()[0]] = (int)numericUpDown5.Value;
            }
            dstRefersh();
        }
        private void numericUpDown6_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1)
            {
                framesGetCurFrame().CDW[dstGetCurCDIndexes()[0]] = (int)numericUpDown6.Value;
            }
            dstRefersh();
        }
        private void numericUpDown7_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1)
            {
                framesGetCurFrame().CDH[dstGetCurCDIndexes()[0]] = (int)numericUpDown7.Value;
            }
            dstRefersh();
        }
        private void numericUpDown8_ValueChanged(object sender, EventArgs e)
        {
            if (dstGetCurCDIndexes().Length == 1)
            {
                framesGetCurFrame().CDMask[dstGetCurCDIndexes()[0]] = (int)numericUpDown8.Value;
            }
            dstRefersh();
        }
        private void ToolStripMenuItemSprCD_Click(object sender, EventArgs e)
        {

            if (!sender.Equals(地图ToolStripMenuItem)) 地图ToolStripMenuItem.Checked = false;
            if (!sender.Equals(攻击ToolStripMenuItem)) 攻击ToolStripMenuItem.Checked = false;
            if (!sender.Equals(防御ToolStripMenuItem)) 防御ToolStripMenuItem.Checked = false;
            if (!sender.Equals(其他ToolStripMenuItem)) 其他ToolStripMenuItem.Checked = false;

            ((ToolStripMenuItem)sender).Checked = true;
            toolStripDropDownButton1.Image = ((ToolStripMenuItem)sender).Image;

            if (dstGetCurCDIndexes().Length == 1)
            {
                int type = 0;
                if (sender.Equals(地图ToolStripMenuItem)) type = Frame.CD_TYPE_MAP;
                if (sender.Equals(攻击ToolStripMenuItem)) type = Frame.CD_TYPE_ATK;
                if (sender.Equals(防御ToolStripMenuItem)) type = Frame.CD_TYPE_DEF;
                if (sender.Equals(其他ToolStripMenuItem)) type = Frame.CD_TYPE_EXT;

                framesGetCurFrame().CDType[dstGetCurCDIndexes()[0]] = type;
            }

            dstRefersh();
           
        }
        private void listView4_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
            dstRefreshEnable = false;
            dstSelectCD();

            if (dstGetCurCDIndexes().Length == 1)
            {
                numericUpDown4.Value = (Decimal)((int)framesGetCurFrame().CDX[dstGetCurCDIndexes()[0]]);
                numericUpDown5.Value = (Decimal)((int)framesGetCurFrame().CDY[dstGetCurCDIndexes()[0]]);
                numericUpDown6.Value = (Decimal)((int)framesGetCurFrame().CDW[dstGetCurCDIndexes()[0]]);
                numericUpDown7.Value = (Decimal)((int)framesGetCurFrame().CDH[dstGetCurCDIndexes()[0]]);
                numericUpDown8.Value = (Decimal)((int)framesGetCurFrame().CDMask[dstGetCurCDIndexes()[0]]);
                switch ((int)framesGetCurFrame().CDType[dstGetCurCDIndexes()[0]])
                {
                    case Frame.CD_TYPE_MAP: ToolStripMenuItemSprCD_Click(地图ToolStripMenuItem, null); break;
                    case Frame.CD_TYPE_ATK: ToolStripMenuItemSprCD_Click(攻击ToolStripMenuItem, null); break;
                    case Frame.CD_TYPE_DEF: ToolStripMenuItemSprCD_Click(防御ToolStripMenuItem, null); break;
                    case Frame.CD_TYPE_EXT: ToolStripMenuItemSprCD_Click(其他ToolStripMenuItem, null); break;
                }
            }

            CDState.Text = "" + e.ItemIndex;

            pictureBoxMain.Refresh();
            dstRefreshEnable = true;

        }

		System.Drawing.Pen cross_pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0, 0, 0));
		System.Drawing.Pen rule_pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff));


        // dst box
        private void pictureBoxMain_Paint(object sender, PaintEventArgs e)
		{
			try
			{
				// pictureBox2.SizeMode = PictureBoxSizeMode.
				

				int tx = pictureBoxMain.Width / 2;
				int ty = pictureBoxMain.Height / 2;

				if (this.显示十字ToolStripMenuItem.Checked)
				{
					// System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;
					e.Graphics.DrawLine(cross_pen, tx, 0, tx, pictureBoxMain.Height);
					e.Graphics.DrawLine(cross_pen, 0, ty, pictureBoxMain.Width, ty);
				}

				

				{
					Graphics g = new Graphics(e.Graphics);
					g.pushState();
					Frame frame = framesGetCurFrame();
					if (frame != null)
					{
						g.translate(tx, ty);
						g.scale(curMasterScale, curMasterScale);

						if (showClipToolStripMenuItem.Checked)
						{
							g.pushState();
							try
							{
								ClipShow.render(e.Graphics, 0, 0, getCurrentAnimate(), getCurrentFrame());
							}
							catch (Exception err) { }
							g.popState();
						}


						frame.render(
						  g,
						  srcTiles,
						  0,
						  0,
							chkShowImageBorder.Checked,
							chkShowCD.Checked,
							checkComplexMode.Checked);

						if (keyCtrl)
						{
							g.drawImage(imgScaleP, 
								edtMouseCurX - imgScaleP.getWidth() / 2,
								edtMouseCurY - imgScaleP.getHeight() / 2);
							g.drawImage(imgScaleP,
								-edtMouseCurX - imgScaleP.getWidth() / 2,
								-edtMouseCurY - imgScaleP.getHeight() / 2);
						}
					}
					g.popState();
				}
				if (this.显示尺子ToolStripMenuItem.Checked)
				{
					e.Graphics.DrawLine(rule_pen, rx, 0, rx, pictureBoxMain.Height);
					e.Graphics.DrawLine(rule_pen, 0, ry, pictureBoxMain.Width, ry);
				}
				//e.Graphics.DrawImage(pictureBox2.Image,0,0);
			}
			catch (Exception err) {
				Console.WriteLine(err.StackTrace);
			}

		}
        private void 显示网格ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            dstGridChange();
            pictureBoxMain.Refresh();
        }
        private void 显示十字ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            pictureBoxMain.Refresh();
        }
        private void 显示尺子ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            pictureBoxMain.Refresh();
        }
        private void 显示图片框ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            pictureBoxMain.Refresh();
            pictureBox1.Refresh();
        }


		private Boolean isFrameEditPart()
		{
			return tabControl1.SelectedIndex == 0;
		}
		private Boolean isFrameEditCD()
		{
			return tabControl1.SelectedIndex == 1;
		}
		private Boolean isFrameEdit()
		{
			return tabControl1.SelectedIndex == 3;
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////

        int rx;
        int ry;

		float edtMouseCurX;
		float edtMouseCurY;

        float edtMouseDownPX;
        float edtMouseDownPY;
		float edtMouseDownX;
		float edtMouseDownY;
		float edtMouseDownRX;
		float edtMouseDownRY;

		float edtMouseDownRotate;
		float edtMouseDownScaleX;
		float edtMouseDownScaleY;
		System.Drawing.RectangleF edtMouseDownTileRect;
		
        bool isEdtDown =false;
        bool isSub = false;

        private void pictureBox2_MouseDown(object sender, MouseEventArgs e)
        {
            isSub = false;
            isEdtDown = true;
			float x = (e.X - pictureBoxMain.Width / 2) / curMasterScale;
			float y = (e.Y - pictureBoxMain.Height / 2) / curMasterScale;
			edtMouseDownX = x;
			edtMouseDownY = y;

			if (isFrameEditPart())
			{
                listView3.SelectedItems.Clear();
                foreach (ListViewItem item in listView3.Items)
                {
                    if (item.Checked == false) continue;

					Frame frame = framesGetCurFrame();

					int i = frame.SubTable.IndexOf(item);

					if (i >= 0) 
					{
						Image tile = srcGetImage((int)frame.SubIndex[i]);

						edtMouseDownTileRect = new System.Drawing.RectangleF(
							   ((int)frame.SubX[i]),
							   ((int)frame.SubY[i]),
							   tile.getWidth(),
							   tile.getHeight()
							   );
						string flipText = Graphics.FlipTextTable[(int)frame.SubFlip[i]];

						if (flipText == Graphics.FlipTextTable[1] ||
							flipText == Graphics.FlipTextTable[3] ||
							flipText == Graphics.FlipTextTable[5] ||
							flipText == Graphics.FlipTextTable[7])
						{
							float temp = edtMouseDownTileRect.Width;
							edtMouseDownTileRect.Width = edtMouseDownTileRect.Height;
							edtMouseDownTileRect.Height = temp;
						}

						if (edtMouseDownTileRect.Contains(x, y))
						{
							edtMouseDownPX = x - edtMouseDownTileRect.X;
							edtMouseDownPY = y - edtMouseDownTileRect.Y;
							edtMouseDownRX = x - (edtMouseDownTileRect.X + edtMouseDownTileRect.Width / 2);
							edtMouseDownRY = y - (edtMouseDownTileRect.Y + edtMouseDownTileRect.Height / 2);
							edtMouseDownRotate = (float)frame.SubTRotate[i];
							edtMouseDownScaleX = (float)frame.SubTScaleX[i];
							edtMouseDownScaleY = (float)frame.SubTScaleY[i];
							item.Selected = true;
							break;
						}
					}
					
                }
            }
			if (isFrameEditCD())
            {
                listView4.SelectedItems.Clear();
                foreach (ListViewItem item in listView4.Items)
                {
                    if (item.Checked == false) continue;

					Frame frame = framesGetCurFrame();

					int i = frame.CDTable.IndexOf(item);

					if (i >= 0)
					{
						System.Drawing.RectangleF rect = new System.Drawing.RectangleF(
							((int)frame.CDX[i]),
							((int)frame.CDY[i]),
							((int)frame.CDW[i]),
							((int)frame.CDH[i])
							);
						if (rect.Contains(x, y))
						{
							edtMouseDownPX = x - rect.X;
							edtMouseDownPY = y - rect.Y;
							item.Selected = true;

							System.Drawing.RectangleF sub = new System.Drawing.RectangleF(
								rect.X + rect.Width - Frame.CDSubW,
								rect.Y + rect.Height - Frame.CDSubH,
								Frame.CDSubW,
								Frame.CDSubH
								);
							if (sub.Contains(x, y))
							{
								isSub = true;
							}
							break;
						}
					}
                }
            }

            dstBoxFocus();
        }

        private void pictureBox2_MouseUp(object sender, MouseEventArgs e)
        {
            isEdtDown = false;
            isSub = false; 
            refreshCurPartEditBox();
        }

        private void pictureBox2_MouseMove(object sender, MouseEventArgs e)
		{
			float x = (e.X - pictureBoxMain.Width / 2) / curMasterScale;
			float y = (e.Y - pictureBoxMain.Height / 2) / curMasterScale;
            edtMouseCurX = x;
			edtMouseCurY = y;

			if (isEdtDown)
			{
                try
				{
					Frame curFrame = framesGetCurFrame();
					if (isFrameEditPart())
                    {
						int dstI = dstGetCurSubIndexes()[0];

						if (keyCtrl)
						{
							float rx = x - (edtMouseDownTileRect.X + edtMouseDownTileRect.Width / 2);
							float ry = y - (edtMouseDownTileRect.Y + edtMouseDownTileRect.Height / 2);
							double edtSrcDegree = Math.Atan2(edtMouseDownRY, edtMouseDownRX);
							double edtCurRotate = Math.Atan2(ry, rx);
							curFrame.SubTRotate[dstI] = (float)Util.defaultAngle(
								edtMouseDownRotate + Util.toAngle(edtCurRotate - edtSrcDegree)
								);
						}
						else
						{
							curFrame.SubX[dstI] = (int)((x - edtMouseDownPX));
							curFrame.SubY[dstI] = (int)((y - edtMouseDownPY));
						}
                    }
					if (isFrameEditCD())
					{
						int dstI = dstGetCurCDIndexes()[0];
                        if (!isSub)
                        {
							curFrame.CDX[dstI] = (int)((x - edtMouseDownPX));
							curFrame.CDY[dstI] = (int)((y - edtMouseDownPY));
                        }
                        else
                        {
							curFrame.CDW[dstI] = (int)((x)) - (int)curFrame.CDX[dstI];
							curFrame.CDH[dstI] = (int)((y)) - (int)curFrame.CDY[dstI];

							if ((int)curFrame.CDW[dstI] <= 0) curFrame.CDW[dstI] = 1;
							if ((int)curFrame.CDH[dstI] <= 0) curFrame.CDH[dstI] = 1;
                        }
                     }
                }
                catch (Exception err) { }
                
            }

			if (this.显示尺子ToolStripMenuItem.Checked)
			{
				int mScale = (int)curMasterScale;
				rx = e.X - e.X % mScale;
				ry = e.Y - e.Y % mScale;
				this.RulerLabel2.Text =
					" X=" + (rx - pictureBoxMain.Width / 2) / mScale + 
					" Y=" + (ry - pictureBoxMain.Height / 2) / mScale;
				
			}
			
			dstRefersh();
			
        }

		private bool keyCtrl = false;

        //adjust frame
        private void textBox1_KeyDown(object sender, KeyEventArgs e)
        {
			keyCtrl = e.Control;

            if (e.Shift)
            {
                switch (e.KeyCode)
                {
                    case Keys.Up: framesMoveAll(0, -1); textBox1.Text += "UP"; break;
                    case Keys.Down: framesMoveAll(0,  1); textBox1.Text += "DOWN"; break;
                    case Keys.Left: framesMoveAll(-1, 0); textBox1.Text += "LEFT"; break;
                    case Keys.Right: framesMoveAll(1, 0); textBox1.Text += "RIGHT"; break;
                }
                framesRefersh();
            }
            else
            {
                int eX = 0;
                int eY = 0;
                textBox1.Text = "";
                switch (e.KeyCode)
                {
                    case Keys.Up: eY = -1; textBox1.Text += "UP"; break;
                    case Keys.Down: eY = 1; textBox1.Text += "DOWN"; break;
                    case Keys.Left: eX = -1; textBox1.Text += "LEFT"; break;
                    case Keys.Right: eX = 1; textBox1.Text += "RIGHT"; break;

                    case Keys.PageUp:
                       imageFlipToolStripButton1.prewFlipIndex();
                        break;
                    case Keys.PageDown:
                        imageFlipToolStripButton1.nextFlipIndex();
                        break;
                }

                switch (e.KeyCode)
                {
                    case Keys.PageUp:
                    case Keys.PageDown:
                        if (dstGetCurSubIndexes().Length == 1)
                        {
                            int flip = imageFlipToolStripButton1.getFlipIndex();
                            framesGetCurFrame().flipSub(dstGetCurSubIndexes()[0], flip);
                        }
                        break;
                }
              


                try
                {
					if (isFrameEditPart())
                    {
                        framesGetCurFrame().SubX[dstGetCurSubIndexes()[0]] = (int)framesGetCurFrame().SubX[dstGetCurSubIndexes()[0]] + eX;
                        framesGetCurFrame().SubY[dstGetCurSubIndexes()[0]] = (int)framesGetCurFrame().SubY[dstGetCurSubIndexes()[0]] + eY;
                    }
                    if (isFrameEditCD())
                    {
                        framesGetCurFrame().CDX[dstGetCurCDIndexes()[0]] = (int)framesGetCurFrame().CDX[dstGetCurCDIndexes()[0]] + eX;
                        framesGetCurFrame().CDY[dstGetCurCDIndexes()[0]] = (int)framesGetCurFrame().CDY[dstGetCurCDIndexes()[0]] + eY;
                    }
                }
                catch (Exception err) { }
			}
			dstRefersh();
        }

		private void textBox1_KeyUp(object sender, KeyEventArgs e)
		{
			keyCtrl = false;
			dstRefersh();
		}



        private void pictureBox2_PreviewKeyDown(object sender, PreviewKeyDownEventArgs e)
        {

        }

       

        private void toolStripButton12_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = false;
            MyDialog.ShowHelp = true;
            MyDialog.Color = pictureBoxMain.BackColor;
            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                pictureBoxMain.BackColor = MyDialog.Color;

				cross_pen.Color = System.Drawing.Color.FromArgb(0xFF,
					pictureBoxMain.BackColor.R ^ 0xFF,
					pictureBoxMain.BackColor.G ^ 0xFF,
					pictureBoxMain.BackColor.B ^ 0xFF);

				rule_pen.Color = System.Drawing.Color.FromArgb(0x80, 
					cross_pen.Color.R,
					cross_pen.Color.G,
					cross_pen.Color.B);

				pictureBoxMain.Refresh();

            }
        }
        private void toolStripButton26_Click(object sender, EventArgs e)
        {
            dstRefersh();
        }

        private void dstGridChange()
        {
			int masterScale = (int)curMasterScale;

            pictureBoxMain.Width = pictureBoxMain.Height = Math.Min(dstPanelSize * masterScale, 1024 * 4);

                try
                {
                    panel2.HorizontalScroll.Value = panel2.HorizontalScroll.Maximum / 2 - panel2.Width / 2;
                    panel2.VerticalScroll.Value = panel2.VerticalScroll.Maximum / 2 - panel2.Height / 2;
                }
                catch (Exception err) { }

                if (this.显示网格ToolStripMenuItem.Checked && masterScale > 1)
                {
                    System.Drawing.Image bg = new System.Drawing.Bitmap(masterScale, masterScale);
                    System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(
						0xff, 0x80, 0x80, 0x80));
                    System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(bg);
                    g.DrawLine(pen, 0, 0, masterScale, 0);
                    g.DrawLine(pen, 0, 0, 0, masterScale);

                    pictureBoxMain.BackgroundImage = bg;
                    pictureBoxMain.Refresh();
                }
                else
                {
                    pictureBoxMain.BackgroundImage = null;
                    pictureBoxMain.Refresh();
                }
          
        }
        private void dstBoxFocus()
        {
            textBox1.Focus();
            //if (this.panel2.Focused == false)
            //{
            //    this.panel2.Focus();

            //    //try
            //    //{
            //    //    panel2.HorizontalScroll.Value   = panel2.HorizontalScroll.Maximum / 2 - panel2.Width / 2;
            //    //    panel2.VerticalScroll.Value     = panel2.VerticalScroll.Maximum / 2 - panel2.Height / 2;

            //    //    panel2.Refresh();
            //    //}
            //    //catch (Exception err) { }

            //}
        }
        

// frames
        private int ViewW = 64;
        private int ViewH = 64;

        private void framesRefersh()
        {
            splitContainer6.Panel1.Enabled = false; 
			Frame curFrame = framesGetCurFrame();
            listView3.Items.Clear();
            listView4.Items.Clear();
			if (curFrame != null)
            {
				for (int i = 0; i < curFrame.getSubCount(); i++)
                {
					((ListViewItem)curFrame.SubTable[i]).Selected = false;
					listView3.Items.Add((ListViewItem)curFrame.SubTable[i]);
                }
				for (int i = 0; i < curFrame.getCDCount(); i++)
                {
					((ListViewItem)curFrame.CDTable[i]).Selected = false;
					listView4.Items.Add((ListViewItem)curFrame.CDTable[i]);
                }
				numFrameAlpha.Value = new Decimal(curFrame.alpha);
				numFrameRotate.Value = new Decimal(curFrame.rotate);
				numScaleX.Value = new Decimal(curFrame.scalex);
				numScaleY.Value = new Decimal(curFrame.scaley);
				
            }
            dstRefersh();
			ArrayList frameAnims = animGetCurFrames();
			if (frameAnims != null)
            {
				pictureBox3.Width = ViewW * frameAnims.Count;
                pictureBox3.Height = ViewH;
            }
            pictureBox3.Refresh();
        }

        private void framesAdd()
        {
            if (animGetCurFrames() != null)
            {

                Frame frame = new Frame();
                
                if (animGetCurFrames().Count > 0)
                {
                    trackBar1.Maximum++;
                    animGetCurFrames().Insert(trackBar1.Value + 1, frame);
                    trackBar1.Value ++;
                }
                else
                {
                    trackBar1.Maximum=0;
                    animGetCurFrames().Add(frame);
                    trackBar1.Value=0;
                }

                //PictureBox pictureBox = new PictureBox();
                //pictureBox.Location = new System.Drawing.Point(3, 3);
                //pictureBox.Name = "framePicture";
                //pictureBox.Size = new System.Drawing.Size(100, 100);
                //pictureBox.TabIndex = 0;
                //pictureBox.TabStop = false;
                //pictureBox.BackColor = 0xffff00ff;
                //panel3.Controls.Add(pictureBox);

                
                listView2.SelectedItems[0].SubItems[1].Text = animGetCurFrames().Count.ToString();

               
            }
        }
        private void framesDel()
        {
            if (framesGetCurFrame() != null)
            {
                animGetCurFrames().Remove(framesGetCurFrame());
                trackBar1.Maximum = Math.Max(animGetCurFrames().Count - 1, 0);

                listView2.SelectedItems[0].SubItems[1].Text = animGetCurFrames().Count.ToString();
            }
        }
        private void framesCopy()
        {
            if (framesGetCurFrame()!=null)
            {
                Frame frame = new Frame(framesGetCurFrame());

                trackBar1.Maximum++;
                animGetCurFrames().Insert(trackBar1.Value + 1, frame);
                trackBar1.Value++;
          
                listView2.SelectedItems[0].SubItems[1].Text = animGetCurFrames().Count.ToString();


            }
        }

        private Frame framesGetCurFrame()
        {
            if (animGetCurFrames() != null && trackBar1.Value < animGetCurFrames().Count)
            {
                return (Frame)(animGetCurFrames()[trackBar1.Value]);
            }
            else
            {
                return null;
            }
        }

		private int getCurrentFrame() {
			return trackBar1.Value;
		}

        private void renderFrameSnaps(Graphics g, int ww, int wh)
        {
            ArrayList frames = animGetCurFrames();

            if (frames != null)
            {
                float cx = ww / 2;
                float cy = wh / 2;

                for (int i = 0; i < frames.Count; i++)
                {
                    g.setClip(0, 0, ww, wh);

                    Frame frame = (Frame)(frames[i]);
                    System.Drawing.Rectangle bounds = frame.getVisibleBounds();
                    g.translate(cx, cy);
                    if (bounds.Width > ww*2) 
                    {
                        float scale = (float)ww / bounds.Width;
                        g.pushState();
                        g.scale(scale, scale);
                        frame.render(g, srcTiles, 0, 0, false,
                        chkShowCD.Checked, checkComplexMode.Checked);
                        g.popState();
                    }
                    else
                    {
                        frame.render(g, srcTiles, 0, 0, false,
                        chkShowCD.Checked, checkComplexMode.Checked);
                    }
                    g.translate(-cx, -cy);

                    if (trackBar1.Value == i)
                    {
                        g.setColor(0x20, 0, 0, 0);
                        g.fillRect(0, 0, ww, wh);
                        g.setColor(0xff, 0, 0, 0);
                        g.drawRect(1, 1, ww - 2, wh - 2);
                    }
                    if (frame.append_data.Length > 0) 
                    {
                        g.setColor(0x80, 0, 0, 0);
                        g.fillRect(0, 0, ww, g.getFont().GetHeight());
                        g.setColor(0xff, 0xFF, 0xff, 0xff);
                        g.drawString(frame.append_data, 0, 0, Graphics.LEFT);

                    }

                    g.translate(ww, 0);
                }
            }
        }

        private void pictureBox3_Paint(object sender, PaintEventArgs e)
        {
            if (toolBtnPlayLoop.Checked == false)
            {
                Graphics g = new Graphics(e.Graphics);
                renderFrameSnaps(g, ViewW, ViewH);
            }
            
        }
        private void pictureBox3_MouseDown(object sender, MouseEventArgs e)
        {
            if(e.Button == MouseButtons.Left)
            {
                if (animGetCurFrames() != null)
                {
                    trackBar1.Value = e.X/ViewW;
                }
            }
            framesRefersh();
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            framesAdd();
            framesRefersh();
        }

		private void btnAddFrameAndSub_Click(object sender, EventArgs e)
		{
			framesAdd();
			dstAddPart();
			framesRefersh();

		}
	
		private void btnAddSequence_Click(object sender, EventArgs e)
		{
			ArrayList added = super.addDirImages();
			foreach (Image img in added)
			{
				framesAdd();
				dstAddPart(img);
			}
			framesRefersh();
		}

        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            framesDel();
            framesRefersh();
        }
        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            framesCopy();
            framesRefersh();
        }
        private void toolStripButton18_Click(object sender, EventArgs e)
        {
            if (trackBar1.Value >= trackBar1.Minimum + 1)
            {
                animGetCurFrames().Reverse(trackBar1.Value - 1, 2);
                trackBar1.Value = trackBar1.Value - 1;
            }
            framesRefersh();

          
        }
        private void toolStripButton17_Click(object sender, EventArgs e)
        {
            if (trackBar1.Value <= trackBar1.Maximum - 1)
            {
                animGetCurFrames().Reverse(trackBar1.Value, 2);
                trackBar1.Value = trackBar1.Value + 1;
            }
            framesRefersh();
          
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            framesRefersh();
        }
        private void trackBar1_ValueChanged(object sender, EventArgs e)
        {

            splitContainer6.Panel1.Enabled = false; 
            toolStripStatusLabel1.Text = "";

            if (animGetCurFrames() != null && framesGetCurFrame() != null)
            {
                toolStripStatusLabel1.Text += " 帧：" + (trackBar1.Value) + "/" + (trackBar1.Maximum + 1);
            }
            toolStripStatusLabel1.Text += " FPS=" + (((float)1000) / ((float)timer1.Interval));
        }
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            trackBar1.Value = trackBar1.Minimum;
            framesRefersh();
        }
        //private void toolStripButton5_Click(object sender, EventArgs e)
        //{
        //    if (trackBar1.Value > trackBar1.Minimum)
        //    {
        //        trackBar1.Value -= trackBar1.SmallChange;
        //    }
        //    else
        //    {
        //        trackBar1.Value = trackBar1.Maximum;
        //    }
        //    framesRefersh();
        //}
        private void toolStripButton6_Click(object sender, EventArgs e)
        {
        
            if (toolBtnPlayLoop.Checked == true)
            {
                tabControl1.Enabled = false;
                panel2.Enabled = false;
            }
            else
            {
                tabControl1.Enabled = true;
                panel2.Enabled = true;
            }
            if (toolBtnPlayLoop.Checked)
            {
                timer1.Start();
            }
            else
            {
                timer1.Stop();
            }
            framesRefersh();
            
        }
        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            //if (toolStripButton7.Checked == true)
            //{
            //    //foreach (Control control in splitContainer5.Panel2.Controls)
            //    //{
            //    //    control.Enabled = false;
            //    //}
            //}
            //else
            //{
            //    //foreach (Control control in splitContainer5.Panel2.Controls)
            //    //{
            //    //    control.Enabled = true;
            //    //}
			//}
			trackBar1.Value = trackBar1.Minimum;
            timer1.Start();
            framesRefersh();
        }
        private void toolStripButton14_Click(object sender, EventArgs e)
        {
            trackBar1.Value = trackBar1.Maximum;
            framesRefersh();
        }



        // all
        private void framesMoveAll(int px,int py)
        {
            foreach (Frame frame in animGetCurFrames())
            {
                for (int i = 0; i < frame.getSubCount(); i++)
                {
                    frame.SubX[i] = ((int)(frame.SubX[i])) + px;
                    frame.SubY[i] = ((int)(frame.SubY[i])) + py;
                }

                for (int i = 0; i < frame.getCDCount(); i++)
                {
                    frame.CDX[i] = ((int)(frame.CDX[i])) + px;
                    frame.CDY[i] = ((int)(frame.CDY[i])) + py;
                }
            }
        }
        //left
        private void toolStripButton22_Click(object sender, EventArgs e)
        {
            framesMoveAll(-1, 0);
            framesRefersh();
        }
        //right
        private void toolStripButton23_Click(object sender, EventArgs e)
        {
            framesMoveAll( 1, 0);
            framesRefersh();
        }
        //up
        private void toolStripButton24_Click(object sender, EventArgs e)
        {
            framesMoveAll(0, -1);
            framesRefersh();
        }
        //down
        private void toolStripButton25_Click(object sender, EventArgs e)
        {
            framesMoveAll(0,  1);
            framesRefersh();
        }
 // animate
        public static String clipAnimateName;
        public static ArrayList clipAnimate;
        public static ImagesForm clipSuperForm;

        private void animAdd()
        {
            ArrayList frames = new ArrayList();

            ListViewItem item = new ListViewItem(new String[] { animCount.ToString("d4"), "0" });

            //Animates.Add(frames);
            listView2.Items.Add(item);
            AnimTable.Add(item, frames);

            item.Focused = true;
            item.Selected = true;

            animCount++;

        }
        private void animDel()
        {
            if (animGetCurFrames() != null)
            {
                AnimTable.Remove(listView2.SelectedItems[0]);
                listView2.Items.Remove(listView2.SelectedItems[0]);
            }
        }
        private void animCopy()
        {
            try
            {
                if (animGetCurFrames() != null)
                {
                    clipAnimateName = listView2.SelectedItems[0].Text;
                    clipAnimate = new ArrayList();
                    foreach (Frame obj in animGetCurFrames())
                    {
                        Frame frame = new Frame(obj);
                        clipAnimate.Add(frame);
                    }
                    clipSuperForm = this.super;
                }
            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message); }
        }
        private void animPaste()
        {
            try
            {
                if (clipAnimate != null && clipSuperForm != null && clipAnimateName!=null)
                {
                    if (clipSuperForm == this.super)
                    {
                        ListViewItem item = new ListViewItem(new String[] { clipAnimateName, clipAnimate.Count.ToString() });

                        listView2.Items.Add(item);

                        ArrayList frames = new ArrayList();
                        foreach (Frame obj in clipAnimate)
                        {
                            Frame frame = new Frame(obj);
                            frames.Add(frame);
                        }

                        AnimTable.Add(item, frames);

                        item.Focused = true;
                        item.Selected = true;

                        animCount++;
                    }
                }
            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message); }
        }

        public string getAnimateName(int index)
        {
            if (index >= 0 &&
                index < listView2.Items.Count)
            {
                return listView2.Items[index].Text;
            }
            return "(null)";
        }

        private ArrayList animGetCurFrames()
        {
            if (listView2.Items.Count > 0 &&
                listView2.SelectedItems != null &&
                listView2.SelectedItems.Count > 0)
            {
                return (ArrayList)AnimTable[listView2.SelectedItems[0]];
            }
            else
            {
                return null;
            }
        }

		 private int getCurrentAnimate()
		 {
			 return listView2.Items.IndexOf(listView2.SelectedItems[0]);
		 }

        private void toolStripButton8_Click(object sender, EventArgs e)
        {
            animAdd();
            framesRefersh();
        }
        private void toolStripButton9_Click(object sender, EventArgs e)
        {
            animDel();
            framesRefersh();
        }
        private void toolStripButton13_Click(object sender, EventArgs e)
        {
            animCopy();
            framesRefersh();
        }
        private void toolStripButton33_Click(object sender, EventArgs e)
        {
            animPaste();
            framesRefersh();
        }
        private void listView2_ItemSelectionChanged(object sender, ListViewItemSelectionChangedEventArgs e)
        {
			if (listView2.SelectedItems != null && listView2.SelectedItems.Count > 0)
			{
				toolStrip4.Enabled = true;
				tabControl1.Enabled = true;
				if (animGetCurFrames() != null)
				{
					trackBar1.Maximum = Math.Max(animGetCurFrames().Count - 1, 0);
					trackBar1.Value = 0;
				}

				AnimState.Text = "" + e.ItemIndex;
			}
			else
			{
				toolStrip4.Enabled = false; 
				tabControl1.Enabled = false;
			}
            framesRefersh();
        }

        // up
        private void toolStripButton29_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView2.Items.Count > 0 &&
                   listView2.SelectedItems != null &&
                   listView2.SelectedItems.Count > 0)
                {
                    int index = listView2.Items.IndexOf(listView2.SelectedItems[0]);
                    if (index > 0)
                    {
                        ListViewItem src = listView2.Items[index - 1];
                        ListViewItem dst = listView2.Items[index];

                        listView2.Items.RemoveAt(index - 1);
                        listView2.Items.RemoveAt(index - 1);

                        listView2.Items.Insert(index - 1, src);
                        listView2.Items.Insert(index - 1, dst);

                        dst.Selected = true;
                        dst.Focused = true;
                    }

                }
                framesRefersh();
            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message); }
            
        }
        // down
        private void toolStripButton30_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView2.Items.Count > 0 &&
                   listView2.SelectedItems != null &&
                   listView2.SelectedItems.Count > 0)
                {
                    int index = listView2.Items.IndexOf(listView2.SelectedItems[0]);
                    if (index >= 0 && index < listView2.Items.Count - 1)
                    {
                        ListViewItem src = listView2.Items[index + 1];
                        ListViewItem dst = listView2.Items[index];

                        listView2.Items.RemoveAt(index);
                        listView2.Items.RemoveAt(index);

                        listView2.Items.Insert(index, dst);
                        listView2.Items.Insert(index, src);

                        dst.Selected = true;
                        dst.Focused = true;
                    }

                }
                framesRefersh();
            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message); }
        }
// timer
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (!this.Visible) return;

            timer1.Interval = (int)numericUpDown2.Value;
   

            if (toolBtnPlayLoop.Checked)
            {
                if (trackBar1.Value < trackBar1.Maximum)
                {
                    trackBar1.Value += trackBar1.SmallChange;
                }
                else
                {
                    trackBar1.Value = trackBar1.Minimum;
                }
                pictureBoxMain.Refresh();
            }

            if (toolBtnPlayOnce.Checked)
            {
                if (trackBar1.Value < trackBar1.Maximum)
                {
                    trackBar1.Value += trackBar1.SmallChange;
                }
                else
                {
                    //trackBar1.Value = trackBar1.Minimum;
                    toolBtnPlayOnce.Checked = false;
                    toolStripButton7_Click(toolBtnPlayOnce,null);
                    timer1.Stop();
                }
                pictureBoxMain.Refresh();
            }
        
        }

        private void SpriteForm_TextChanged(object sender, EventArgs e)
        {
            this.id = this.Text;
        }
        private void imageFlipToolStripButton1_DropDownClosed(object sender, EventArgs e)
        {
            if (dstGetCurSubIndexes().Length == 1)
            {
                int flip = imageFlipToolStripButton1.getFlipIndex();
                /*
                if (sender.Equals(toolStripMenuItem10)) flip = 0;
                if (sender.Equals(toolStripMenuItem11)) flip = 1;
                if (sender.Equals(toolStripMenuItem12)) flip = 2;
                if (sender.Equals(toolStripMenuItem13)) flip = 3;
                if (sender.Equals(toolStripMenuItem14)) flip = 4;
                if (sender.Equals(toolStripMenuItem15)) flip = 5;
                if (sender.Equals(toolStripMenuItem16)) flip = 6;
                if (sender.Equals(toolStripMenuItem17)) flip = 7;
                */
                framesGetCurFrame().flipSub(dstGetCurSubIndexes()[0], flip);
            }

            dstRefersh();
        }




		private void imageFlipToolStripButton1_DropDownItemClicked(object sender, ToolStripItemClickedEventArgs e)
		{

		}

		private void btnAppendData_Click(object sender, EventArgs e)
		{
			StringBuilder sb = new StringBuilder(this.append_data);

			DataEdit dataedit = new DataEdit(sb);

			dataedit.ShowDialog(this);

			this.append_data = sb.ToString();
		}


		private void numFrameAlpha_ValueChanged(object sender, EventArgs e)
		{
			Frame frame = framesGetCurFrame();
			if (frame != null)
			{
				frame.alpha = (float)numFrameAlpha.Value;
				dstRefersh();
			}
		}

		private void numFrameRotate_ValueChanged(object sender, EventArgs e)
		{
			Frame frame = framesGetCurFrame();
			if (frame != null)
			{
				frame.rotate = (float)numFrameRotate.Value;
				dstRefersh();
			}
		}

		private void numScaleX_ValueChanged(object sender, EventArgs e)
		{
			Frame frame = framesGetCurFrame();
			if (frame != null)
			{
				frame.scalex = (float)numScaleX.Value;
				dstRefersh();
			}
		}

		private void numScaleY_ValueChanged(object sender, EventArgs e)
		{
			Frame frame = framesGetCurFrame();
			if (frame != null)
			{
				frame.scaley = (float)numScaleY.Value;
				dstRefersh();
			}
		}

		private void checkComplexMode_CheckedChanged(object sender, EventArgs e)
		{
			groupBox1.Enabled = checkComplexMode.Checked;
			groupBoxPartComplex.Enabled = checkComplexMode.Checked;
		}

		




		public void onProcessImageSizeChanged(ImagesForm src, ArrayList events)
		{
			try
			{
				foreach (ArrayList animFrames in AnimTable.Values)
				{
					foreach (Frame frame in animFrames)
					{
						frame.onImagePartsChange(srcTiles, events);
					}
				}
			}
			catch (Exception err)
			{
			}
		}

        private void btnCurFrameData_Click(object sender, EventArgs e)
        {
           Frame fr = framesGetCurFrame();
            if (fr != null) {
                StringBuilder sb = new StringBuilder(fr.append_data);

                DataEdit dataedit = new DataEdit(sb);

                dataedit.ShowDialog(this);

                fr.append_data = sb.ToString();
            }
           
        }


		//////////////////////////////////////////////////////////////////////////////////////////
		private void showClipToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (showClipToolStripMenuItem.Checked)
			{
				ClipShow.load();
			}
			pictureBoxMain.Refresh();
		}

		private void setClipShowToolStripMenuItem_Click(object sender, EventArgs e)
		{
			ClipShow.save(this);
		}
		//////////////////////////////////////////////////////////////////////////////////////////

 







      

        





    }

	public partial class ClipShow
	{
		private static Hashtable animates;

		public static void save(SpriteForm sf)
		{
			try
			{
				animates = new Hashtable();
				String dir = Application.StartupPath + "\\clipshow";
				if (System.IO.Directory.Exists(dir))
				{
					System.IO.Directory.Delete(dir, true);
				}
				System.IO.Directory.CreateDirectory(dir);

				for (int anim = 0; anim < sf.GetAnimateCount(); anim++)
				{
					for (int frame = 0; frame < sf.GetFrameCount(anim); frame++)
					{
						try
						{
							Frame curFrame = sf.getFrame(anim, frame);

							if (curFrame != null)
							{
								System.Drawing.Rectangle vb = curFrame.getVisibleBounds();
								int x1 = vb.X;
								int x2 = vb.X + vb.Width;
								int y1 = vb.Y;
								int y2 = vb.Y + vb.Height;
								int maxw = Math.Max(Math.Abs(x1), Math.Abs(x2));
								int maxh = Math.Max(Math.Abs(y1), Math.Abs(y2));
								if (maxw > 0 && maxh > 0)
								{
									int maxs = Math.Max(maxw * 2, maxh * 2);
									javax.microedition.lcdui.Image img = javax.microedition.lcdui.Image.createImage(maxs, maxs);
									javax.microedition.lcdui.Graphics g = img.getGraphics();
									sf.Render(g, maxs / 2, maxs / 2, false, anim, frame);

									String key = anim + "_" + frame + ".png";
									img.getDImage().Save(
										dir + "\\" + key,
										System.Drawing.Imaging.ImageFormat.Png);
									animates[key] = img.getDImage();
								}
							}
						}
						catch (Exception err)
						{
							MessageBox.Show(err.Message);
						}
					}
				}
			}
			catch (Exception err)
			{
				MessageBox.Show(err.Message);
			} 
		}

		public static void load()
		{
			try {
				animates = new Hashtable();
				String dir = Application.StartupPath + "\\clipshow";
				string[] files = System.IO.Directory.GetFiles(dir);
				foreach (string file in files) {
					String key = System.IO.Path.GetFileName(file);
					System.Drawing.Image bm = System.Drawing.Bitmap.FromFile(file);
					animates[key] = bm;
				}
			}
			catch (Exception err)
			{
				MessageBox.Show(err.Message);
			}
		}

		public static void render(System.Drawing.Graphics dg, int x, int y, int anim, int frame)
		{
			System.Drawing.Image img = (System.Drawing.Image)animates[anim+"_"+frame + ".png"];
			if (img != null)
			{
				dg.DrawImage(img, x - img.Width / 2, y - img.Height / 2);
			}
		}

	}

    [Serializable]
    public partial class Frame : ISerializable
    {


        #region const
        public static int CDSubW = 4;
        public static int CDSubH = 4;

        public const int CD_TYPE_MAP = 0;
        public const int CD_TYPE_ATK = 1;
        public const int CD_TYPE_DEF = 2;
        public const int CD_TYPE_EXT = 3;

        
     
        static public String[] CDtypeTextTable = new String[]
        {
            "地图",
            "攻击",
            "防御",
            "其他",
        };
        #endregion
        //ArrayList Animates;
       public ArrayList SubTable = new ArrayList();
        

        public ArrayList SubIndex = new ArrayList();
        public ArrayList SubX = new ArrayList();
        public ArrayList SubY = new ArrayList();
        public ArrayList SubZ = new ArrayList();
        public ArrayList SubW = new ArrayList();
        public ArrayList SubH = new ArrayList();
        public ArrayList SubFlip = new ArrayList();

		public ArrayList SubTRotate = new ArrayList();
		public ArrayList SubTScaleX	= new ArrayList();
		public ArrayList SubTScaleY	= new ArrayList();
		public ArrayList SubTAlpha	= new ArrayList();
		public ArrayList SubTShearX = new ArrayList();
		public ArrayList SubTShearY = new ArrayList();

       public ArrayList SubSelected = new ArrayList();

        //ArrayList Collides;
       public ArrayList CDTable = new ArrayList();

        public ArrayList CDMask = new ArrayList();
        public ArrayList CDX = new ArrayList();
        public ArrayList CDY = new ArrayList();
        public ArrayList CDW = new ArrayList();
        public ArrayList CDH = new ArrayList();
        public ArrayList CDType = new ArrayList();

       public ArrayList CDSelected = new ArrayList();

       public string append_data = "";
		public float rotate = 0;
		public float scalex = 1;
		public float scaley = 1;
		public float alpha = 1;
		public float shearx = 0;
		public float sheary = 0;

        public Frame()
        {
            
        }
        public Frame(Frame obj)
        {                    
            //ArrayList Animates;
            //SubTable = (ArrayList)obj.SubTable.Clone();

            SubIndex = (ArrayList)obj.SubIndex.Clone();
            SubX = (ArrayList)obj.SubX.Clone();
            SubY = (ArrayList)obj.SubY.Clone();
            SubZ = (ArrayList)obj.SubZ.Clone();
            SubW = (ArrayList)obj.SubW.Clone();
            SubH = (ArrayList)obj.SubH.Clone();
            SubFlip = (ArrayList)obj.SubFlip.Clone();

			SubTRotate = (ArrayList)obj.SubTRotate.Clone();
			SubTScaleX = (ArrayList)obj.SubTScaleX.Clone();
			SubTScaleY = (ArrayList)obj.SubTScaleY.Clone();
			SubTAlpha = (ArrayList)obj.SubTAlpha.Clone();
			SubTShearX = (ArrayList)obj.SubTShearX.Clone();
			SubTShearY = (ArrayList)obj.SubTShearY.Clone();

            SubSelected = (ArrayList)obj.SubSelected.Clone();

            //ArrayList Collides;
            //CDTable = (ArrayList)obj.CDTable.Clone();

            CDMask = (ArrayList)obj.CDMask.Clone();
            CDX = (ArrayList)obj.CDX.Clone();
            CDY = (ArrayList)obj.CDY.Clone();
            CDW = (ArrayList)obj.CDW.Clone();
            CDH = (ArrayList)obj.CDH.Clone();
            CDType = (ArrayList)obj.CDType.Clone();

            CDSelected = (ArrayList)obj.CDSelected.Clone();


            SubTable = new ArrayList();
            for (int i = 0; i < SubIndex.Count; i++)
            {
                ListViewItem item = new ListViewItem(new string[] {
                    ((int)SubIndex[i]).ToString("d"),
                    ((int)SubX[i]).ToString("d"),
                    ((int)SubY[i]).ToString("d"),
                    ((int)SubZ[i]).ToString("d"),
                     Graphics.FlipTextTable[(int)SubFlip[i]] ,
					 ((float)SubTRotate[i])+"", 
					 ((float)SubTScaleX[i])+"", 
					 ((float)SubTScaleY[i])+"",
					 ((float)SubTAlpha[i])+"",
					 ((float)SubTShearX[i])+"",
					 ((float)SubTShearY[i])+""
				});
                item.Checked = true;
                SubTable.Add(item);
                SubSelected.Add(false);
            }

            CDTable = new ArrayList();
            for (int i = 0; i < CDMask.Count; i++)
            {
                ListViewItem item = new ListViewItem(new string[] {
                    ((int)CDMask[i]).ToString("x"),
                    ((int)CDX[i]).ToString("d"),
                    ((int)CDY[i]).ToString("d"),
                    ((int)CDW[i]).ToString("d"),
                    ((int)CDH[i]).ToString("d"),
                     Frame.CDtypeTextTable[(int)CDType[i]]}
                    );
                item.Checked = true;
                CDTable.Add(item);
                CDSelected.Add(false);
            }

			this.rotate = obj.rotate;
			this.scalex = obj.scalex;
			this.scaley = obj.scaley;
			this.alpha = obj.alpha;
			this.shearx = obj.shearx;
			this.sheary = obj.sheary;
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected Frame(SerializationInfo info, StreamingContext context)
        {
			try
			{
				rotate = (float)info.GetValue("rotate", typeof(float));
				scalex = (float)info.GetValue("scalex", typeof(float));
				scaley = (float)info.GetValue("scaley", typeof(float));
				alpha = (float)info.GetValue("alpha", typeof(float));
				try {
					shearx = (float)info.GetValue("shearx", typeof(float));
					sheary = (float)info.GetValue("sheary", typeof(float));
				}
				catch (Exception err)
				{
				}
			}
			catch (Exception err)
			{
			}
            append_data = (string)Util.GetValue(info, "append_data", typeof(string), "");

            SubIndex = (ArrayList)info.GetValue("SubIndex", typeof(ArrayList));
            SubX = (ArrayList)info.GetValue("SubX", typeof(ArrayList));
            SubY = (ArrayList)info.GetValue("SubY", typeof(ArrayList));
            SubW = (ArrayList)info.GetValue("SubW", typeof(ArrayList));
            SubH = (ArrayList)info.GetValue("SubH", typeof(ArrayList));
            SubFlip = (ArrayList)info.GetValue("SubFlip", typeof(ArrayList));
            try
			{
                SubZ = (ArrayList)info.GetValue("SubZ", typeof(ArrayList));
            }
            catch (Exception err)
            {
                SubZ = new ArrayList();
                for (int i = 0; i < SubX.Count;i++ )
                {
                    SubZ.Add(0);
                }
            }
			try
			{
				SubTRotate = (ArrayList)info.GetValue("SubTRotate", typeof(ArrayList));
				SubTScaleX = (ArrayList)info.GetValue("SubTScaleX", typeof(ArrayList));
				SubTScaleY = (ArrayList)info.GetValue("SubTScaleY", typeof(ArrayList));
				SubTAlpha = (ArrayList)info.GetValue("SubTAlpha", typeof(ArrayList));
				SubTShearX = (ArrayList)info.GetValue("SubTShearX", typeof(ArrayList));
				SubTShearY = (ArrayList)info.GetValue("SubTShearY", typeof(ArrayList));
			} 
			catch (Exception err) {
			
			}
			if (SubTShearY.Count != SubIndex.Count)
			{
				SubTRotate = new ArrayList();
				SubTScaleX = new ArrayList();
				SubTScaleY = new ArrayList();
				SubTAlpha = new ArrayList();
				SubTShearX = new ArrayList();
				SubTShearY = new ArrayList();
				for (int i = 0; i < SubIndex.Count; i++ )
				{
					SubTRotate.Add(0.0f);
					SubTScaleX.Add(1.0f);
					SubTScaleY.Add(1.0f);
					SubTAlpha.Add(1.0f); 
					SubTShearX.Add(0.0f);
					SubTShearY.Add(0.0f);
				}
			}

            CDMask = (ArrayList)info.GetValue("CDMask", typeof(ArrayList));
            CDX = (ArrayList)info.GetValue("CDX", typeof(ArrayList));
            CDY = (ArrayList)info.GetValue("CDY", typeof(ArrayList));
            CDW = (ArrayList)info.GetValue("CDW", typeof(ArrayList));
            CDH = (ArrayList)info.GetValue("CDH", typeof(ArrayList));
            CDType = (ArrayList)info.GetValue("CDType", typeof(ArrayList));

            SubTable = new ArrayList();
            for (int i = 0; i < SubIndex.Count; i++)
            {
                ListViewItem item = new ListViewItem(new string[] {
                    ((int)SubIndex[i]).ToString("d"),
                    ((int)SubX[i]).ToString("d"),
                    ((int)SubY[i]).ToString("d"),
                    ((int)SubZ[i]).ToString("d"),
                     Graphics.FlipTextTable[(int)SubFlip[i]] ,
					 ((float)SubTRotate[i])+"", 
					 ((float)SubTScaleX[i])+"", 
					 ((float)SubTScaleY[i])+"",
					 ((float)SubTAlpha[i])+"",
					 ((float)SubTShearX[i])+"",
					 ((float)SubTShearY[i])+""
				}
                    );
                item.Checked = true;
                SubTable.Add(item);
                SubSelected.Add(false);
            }

            CDTable = new ArrayList();
            for (int i = 0; i < CDMask.Count; i++)
            {
                ListViewItem item = new ListViewItem(new string[] {
                    ((int)CDMask[i]).ToString("x"),
                    ((int)CDX[i]).ToString("d"),
                    ((int)CDY[i]).ToString("d"),
                    ((int)CDW[i]).ToString("d"),
                    ((int)CDH[i]).ToString("d"),
                     Frame.CDtypeTextTable[(int)CDType[i]]}
                    );
                item.Checked = true;
                CDTable.Add(item);
                CDSelected.Add(false);
            }
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            info.AddValue("append_data", append_data);

			info.AddValue("rotate", rotate);
			info.AddValue("scalex", scalex);
			info.AddValue("scaley", scaley);
			info.AddValue("alpha", alpha);
			info.AddValue("shearx", shearx);
			info.AddValue("sheary", sheary);

            info.AddValue("SubIndex", SubIndex);
            info.AddValue("SubX", SubX);
            info.AddValue("SubY", SubY);
            info.AddValue("SubZ", SubZ);
            info.AddValue("SubW", SubW);
            info.AddValue("SubH", SubH);
            info.AddValue("SubFlip", SubFlip);
			info.AddValue("SubTRotate", SubTRotate);
			info.AddValue("SubTScaleX", SubTScaleX);
			info.AddValue("SubTScaleY", SubTScaleY);
			info.AddValue("SubTAlpha", SubTAlpha);
			info.AddValue("SubTShearX", SubTShearX);
			info.AddValue("SubTShearY", SubTShearY);

            info.AddValue("CDMask", CDMask);
            info.AddValue("CDX", CDX);
            info.AddValue("CDY", CDY);
            info.AddValue("CDW", CDW);
            info.AddValue("CDH", CDH);
            info.AddValue("CDType", CDType);
        }

		public string[] toPartListViewItem(int i)
		{
			return new string[] {
                    ((int)SubIndex[i]).ToString("d"),
                    ((int)SubX[i]).ToString("d"),
                    ((int)SubY[i]).ToString("d"),
                    ((int)SubZ[i]).ToString("d"),
                     Graphics.FlipTextTable[(int)SubFlip[i]] ,
					 ((float)SubTRotate[i])+"", 
					 ((float)SubTScaleX[i])+"", 
					 ((float)SubTScaleY[i])+"",
					 ((float)SubTAlpha[i])+"",
					 ((float)SubTShearX[i])+"",
					 ((float)SubTShearY[i])+""
				};
                    
		}
		public string[] toCDListViewItem(int i)
		{
			return new string[] {
                    ((int)CDMask[i]).ToString("x"),
                    ((int)CDX[i]).ToString("d"),
                    ((int)CDY[i]).ToString("d"),
                    ((int)CDW[i]).ToString("d"),
                    ((int)CDH[i]).ToString("d"),
                     Frame.CDtypeTextTable[(int)CDType[i]]}
					   ;
		}

        public void updatePartListView(ListView view)
        {
            foreach (ListViewItem item in view.Items)
            {
                int index = (int)SubTable.IndexOf(item);
                item.SubItems[0].Text = ((int)SubIndex[index]).ToString("d");
                item.SubItems[1].Text = ((int)SubX[index]).ToString("d");
                item.SubItems[2].Text = ((int)SubY[index]).ToString("d");
                item.SubItems[3].Text = ((int)SubZ[index]).ToString("d");
                item.SubItems[4].Text = Graphics.FlipTextTable[(int)SubFlip[index]];
                item.SubItems[5].Text = SubTAlpha[index].ToString();
                item.SubItems[6].Text = SubTRotate[index].ToString();
                item.SubItems[7].Text = SubTScaleX[index].ToString();
                item.SubItems[8].Text = SubTScaleY[index].ToString();
                item.SubItems[9].Text = SubTShearX[index].ToString();
                item.SubItems[10].Text = SubTShearY[index].ToString();
            }
        }

        public void updateCDListView(ListView view)
        {
            foreach (ListViewItem item in view.Items)
            {
                int index = (int)CDTable.IndexOf(item);
                item.SubItems[0].Text = ((int)CDMask[index]).ToString("x");
                item.SubItems[1].Text = ((int)CDX[index]).ToString("d");
                item.SubItems[2].Text = ((int)CDY[index]).ToString("d");
                item.SubItems[3].Text = ((int)CDW[index]).ToString("d");
                item.SubItems[4].Text = ((int)CDH[index]).ToString("d");
                item.SubItems[5].Text = Frame.CDtypeTextTable[(int)CDType[index]];
            }
        }

        public int indexOfSub(int index,int x,int y, int z, int w,int h,int flip, 
			float rot, float scalex, float scaley, float alpha , float shearx, float sheary)
        {
            for (int i = 0; i < SubIndex.Count;i++ )
            {
                if (((int)SubIndex[i]) == index &&
                    ((int)SubX[i]) == x &&
                    ((int)SubY[i]) == y &&
                    ((int)SubZ[i]) == z &&
                    ((int)SubW[i]) == w &&
                    ((int)SubH[i]) == h &&
                    ((int)SubFlip[i]) == flip &&
					((float)SubTRotate[i]) == rot &&
                    ((float)SubTScaleX[i]) == scalex &&
                    ((float)SubTScaleY[i]) == scaley &&
                    ((float)SubTAlpha[i]) == alpha &&
					((float)SubTShearX[i]) == shearx &&
					((float)SubTShearY[i]) == sheary
					)
                {
                    return i;
                }
            }
            return -1;
        }

        public int indexOfCD(int mask,int x,int y,int w,int h)
        {
            for (int i = 0; i < CDMask.Count; i++)
            {
                if (((int)CDMask[i]) == mask &&
                    ((int)CDX[i]) == x &&
                    ((int)CDY[i]) == y &&
                    ((int)CDW[i]) == w &&
                    ((int)CDH[i]) == h  )
                {
                    return i;
                }
            }
            return -1;
        }

        public int indexOfKey(ListViewItem key)
        {
            return SubTable.IndexOf(key);
        }

        public int getSubCount()
        {
            return SubIndex.Count;
        }
		public ListViewItem addFSub(int part, int x, int y, int z, int w, int h, int flip,
			float rot, float scalex, float scaley, float alpha, float shearx, float sheary)
        {
            SubIndex.Add(part);
            SubX.Add(x);
            SubY.Add(y);
            SubZ.Add(z);
            SubW.Add(w);
            SubH.Add(h);
            SubFlip.Add(flip);

			SubTRotate.Add(rot);
			SubTScaleX.Add(scalex);
			SubTScaleY.Add(scaley);
			SubTAlpha.Add(alpha);
			SubTShearX.Add(shearx);
			SubTShearY.Add(sheary);

			ListViewItem key = new ListViewItem(toPartListViewItem(SubIndex.Count - 1));
			key.Checked = true;
			SubTable.Add(key);
            SubSelected.Add(true);
			return key;
        }
		public ListViewItem insertFSub(int index, int part, int x, int y, int z, int w, int h, int flip,
			float rot, float scalex, float scaley, float alpha, float shearx, float sheary)
        {

            SubIndex.Insert(index, part);
            SubX.Insert(index, x);
            SubY.Insert(index, y);
            SubZ.Insert(index, z);
            SubW.Insert(index, w);
            SubH.Insert(index, h);
            SubFlip.Insert(index, flip);


			SubTRotate.Insert(index, rot);
			SubTScaleX.Insert(index, scalex);
			SubTScaleY.Insert(index, scaley);
			SubTAlpha.Insert(index, alpha);
			SubTShearX.Insert(index, shearx);
			SubTShearY.Insert(index, sheary);

			ListViewItem key = new ListViewItem(toPartListViewItem(index));
			key.Checked = true;
			SubTable.Insert(index, key);
			SubSelected.Insert(index, true);
			return key;

        }
		public ListViewItem delFSub(int index)
        {
			ListViewItem key = (ListViewItem)SubTable[index];

            SubIndex.RemoveAt(index);
            SubX.RemoveAt(index);
            SubY.RemoveAt(index);
            SubZ.RemoveAt(index);
            SubW.RemoveAt(index);
            SubH.RemoveAt(index);
            SubFlip.RemoveAt(index);

			SubTRotate.RemoveAt(index);
			SubTScaleX.RemoveAt(index);
			SubTScaleY.RemoveAt(index);
			SubTAlpha.RemoveAt(index);

			SubTShearX.RemoveAt(index);
			SubTShearY.RemoveAt(index);

			SubTable.RemoveAt(index);
            SubSelected.RemoveAt(index);
			return key;
        }
        public void exchangeSub(int src, int dst)
        {
            int index = (int)SubIndex[src];
            int x = (int)SubX[src];
            int y = (int)SubY[src];
            int z = (int)SubZ[src];
            int w = (int)SubW[src];
            int h = (int)SubH[src];
            int flip = (int)SubFlip[src];

			float rot = (float)SubTRotate[src];
			float scalex = (float)SubTScaleX[src];
			float scaley = (float)SubTScaleY[src];
			float alpha = (float)SubTAlpha[src];
			float shearx = (float)SubTShearX[src];
			float sheary = (float)SubTShearY[src];

            Boolean selected = (Boolean)SubSelected[src];
            //ListViewItem item = (ListViewItem)SubTable[src];

            SubIndex[src] = SubIndex[dst];
            SubX[src] = SubX[dst];
            SubY[src] = SubY[dst];
            SubZ[src] = SubZ[dst];
            SubW[src] = SubW[dst];
            SubH[src] = SubH[dst];
            SubFlip[src] = SubFlip[dst];

			SubTRotate[src] = SubTRotate[dst];
			SubTScaleX[src] = SubTScaleX[dst];
			SubTScaleY[src] = SubTScaleY[dst];
			SubTAlpha[src] = SubTAlpha[dst];

			SubTShearX[src] = SubTShearX[dst];
			SubTShearY[src] = SubTShearY[dst];

			//SubTable[src] = SubTable[dst];
            SubSelected[src] = (Boolean)SubSelected[dst];



            SubIndex[dst] = index;
            SubX[dst] = x;
            SubY[dst] = y;
            SubZ[dst] = z;
            SubW[dst] = w;
            SubH[dst] = h;
            SubFlip[dst] = flip;
			SubTRotate[dst] = rot;
			SubTScaleX[dst] = scalex;
			SubTScaleY[dst] = scaley;
			SubTAlpha[dst] = alpha;

			SubTShearX[dst] = shearx;
			SubTShearY[dst] = sheary;

            //SubTable[dst] = item;
            SubSelected[dst] = selected;

            ((ListViewItem)SubTable[src]).Selected = false;
            ((ListViewItem)SubTable[src]).Focused = false;
            ((ListViewItem)SubTable[dst]).Selected = true;
            ((ListViewItem)SubTable[dst]).Focused = true;

            

        }
        public void flipSub(int index,int flip)
        {
            if (flip % 2 != ((int)SubFlip[index]) % 2)
            {
                int w = (int)SubW[index];
                int h = (int)SubH[index];
                SubW[index] = h;
                SubH[index] = w;
            }
            SubFlip[index] = flip;
        }

       


        public int getCDCount()
        {
            return CDMask.Count;
        }
		public ListViewItem addFCD(int mask, int x, int y, int w, int h, int type)
        {

            CDMask.Add(mask);
            CDX.Add(x);
            CDY.Add(y);
            CDW.Add(w);
            CDH.Add(h);
            CDType.Add(type);

			ListViewItem key = new ListViewItem(toCDListViewItem(CDMask.Count - 1));
			key.Checked = true;
            CDTable.Add(key);
            CDSelected.Add(true);
			return key;
        }
		public ListViewItem insertFCD(int index, int mask, int x, int y, int w, int h, int type)
        {

            CDMask.Insert(index, mask);
            CDX.Insert(index, x);
            CDY.Insert(index, y);
            CDW.Insert(index, w);
            CDH.Insert(index, h);
            CDType.Insert(index, type);

			ListViewItem key = new ListViewItem(toCDListViewItem(index));
			key.Checked = true;
			CDTable.Insert(index, key);
			CDSelected.Insert(index, true);
			return key;
        }
		public ListViewItem delFCD(int index)
        {
			ListViewItem key = (ListViewItem)CDTable[index];

            CDMask.RemoveAt(index);
            CDX.RemoveAt(index);
            CDY.RemoveAt(index);
            CDW.RemoveAt(index);
            CDH.RemoveAt(index);
            CDType.RemoveAt(index);


            CDTable.RemoveAt(index);
            CDSelected.RemoveAt(index);

			return key;
        }
        public void exchangeCD(int src, int dst)
        {
            int mask = (int)CDMask[src];
            int x = (int)CDX[src];
            int y = (int)CDY[src];
            int w = (int)CDW[src];
            int h = (int)CDH[src];
            int type = (int)CDType[src];

            Boolean selected = (Boolean)CDSelected[src];
            //ListViewItem item = (ListViewItem)SubTable[src];

            CDMask[src] = CDMask[dst];
            CDX[src] = CDX[dst];
            CDY[src] = CDY[dst];
            CDW[src] = CDW[dst];
            CDH[src] = CDH[dst];
            CDType[src] = CDType[dst];
            //SubTable[src] = SubTable[dst];
            CDSelected[src] = (Boolean)CDSelected[dst];

            CDMask[dst] = mask;
            CDX[dst] = x;
            CDY[dst] = y;
            CDW[dst] = w;
            CDH[dst] = h;
            CDType[dst] = type;
            //SubTable[dst] = item;
            CDSelected[dst] = selected;

            ((ListViewItem)CDTable[src]).Selected = false;
            ((ListViewItem)CDTable[src]).Focused = false;
            ((ListViewItem)CDTable[dst]).Selected = true;
            ((ListViewItem)CDTable[dst]).Focused = true;

        }

        public float Scale = 1;

        public System.Drawing.Rectangle getVisibleBounds() 
        {
            System.Drawing.Rectangle rect = new System.Drawing.Rectangle();
            if (SubIndex.Count > 0)
            {
                int sx = int.MaxValue;
                int sy = int.MaxValue;
                int dx = int.MinValue;
                int dy = int.MinValue;

                for (int i = SubIndex.Count - 1; i >= 0; i--)
                {
                    sx = Math.Min(sx, (int)SubX[i]);
                    dx = Math.Max(dx, (int)SubX[i] + (int)SubW[i]);
                    sy = Math.Min(sy, (int)SubY[i]);
                    dy = Math.Max(dy, (int)SubY[i] + (int)SubH[i]);
                }
                rect.X = sx;
                rect.Y = sy;
                rect.Width = dx - sx;
                rect.Height = dy - sy;
            }
            else
            {
            }
            return rect;
        }

		public void onImagePartsChange(ArrayList tile, ArrayList events)
		{
			foreach (ImageChange ic in events) 
			{
				for (int i = SubIndex.Count - 1; i >= 0; --i)
				{
					Image srcimg = ((Image)tile[(int)SubIndex[i]]);
					Image dstimg = ic.dstImage;
					if (srcimg == dstimg) {
						SubX[i] = (int)SubX[i] + ic.dstRect.X;
						SubY[i] = (int)SubY[i] + ic.dstRect.Y;
					}
				}
			}
		}

		private void renderPart(Graphics g, ArrayList tile, int i, bool showimageborder, bool complex_mode)
		{
			Image timg = ((Image)tile[(int)SubIndex[i]]);
			if (timg == null)
			{
				return;
			}

			g.pushState();
			try
			{
				switch (Graphics.FlipTable[(int)SubFlip[i]])
				{
					case System.Drawing.RotateFlipType.RotateNoneFlipNone:
					case System.Drawing.RotateFlipType.Rotate180FlipNone:
					case System.Drawing.RotateFlipType.Rotate180FlipX:
					case System.Drawing.RotateFlipType.RotateNoneFlipX:
						SubW[i] = ((Image)tile[(int)SubIndex[i]]).getWidth();
						SubH[i] = ((Image)tile[(int)SubIndex[i]]).getHeight();
						break;
					case System.Drawing.RotateFlipType.Rotate90FlipNone:
					case System.Drawing.RotateFlipType.Rotate270FlipNone:
					case System.Drawing.RotateFlipType.Rotate270FlipX:
					case System.Drawing.RotateFlipType.Rotate90FlipX:
						SubH[i] = ((Image)tile[(int)SubIndex[i]]).getWidth();
						SubW[i] = ((Image)tile[(int)SubIndex[i]]).getHeight();
						break;
				}
				

				if (complex_mode)
				{
					float cx = ((int)SubW[i]) / 2.0f;
					float cy = ((int)SubH[i]) / 2.0f;

					g.multiplyAlpha((float)SubTAlpha[i]);
					g.translate((int)SubX[i] + cx, (int)SubY[i] + cy);
					g.rotate((float)SubTRotate[i]);
					g.scale((float)SubTScaleX[i], (float)SubTScaleY[i]);
					g.shear((float)SubTShearX[i], (float)SubTShearY[i]);
					
					g.drawImageTrans(timg, -cx, -cy, (int)SubFlip[i]);
				}
				else
				{
					g.drawImageTrans(timg, (int)SubX[i], (int)SubY[i], (int)SubFlip[i]);
				}
			}
			finally {
				g.popState();
			}

			if (showimageborder)
			{
				g.setColor(0xff, 0xff, 0xff, 0xff);
				g.drawRect(
					(int)SubX[i], (int)SubY[i],
					(((int)SubW[i])),
					(((int)SubH[i]))
					);
			}
			if ((Boolean)(SubSelected[i]))
			{
				g.setColor(0xff, 0xff, 0xff, 0xff);
				g.drawRect(
					(int)SubX[i], (int)SubY[i],
					(((int)SubW[i])),
					(((int)SubH[i]))
					);
				g.setColor(0x20, 0xff, 0xff, 0xff);
				g.fillRect(
					(int)SubX[i], (int)SubY[i],
					(((int)SubW[i])),
					(((int)SubH[i]))
					);
			}
		}

		private void renderCD(Graphics g, int i)
		{
				
					switch ((int)CDType[i])
					{
						case CD_TYPE_MAP:
							g.setColor(0xff, 0x00, 0xff, 0x00);
							break;
						case CD_TYPE_ATK:
							g.setColor(0xff, 0xff, 0x00, 0x00);
							break;
						case CD_TYPE_DEF:
							g.setColor(0xff, 0x00, 0x00, 0xff);
							break;
						case CD_TYPE_EXT:
							g.setColor(0xff, 0xff, 0xff, 0x00);
							break;
					}
					g.drawRect(
						((int)CDX[i]),
						((int)CDY[i]),
						((int)CDW[i]) - 1,
						((int)CDH[i]) - 1);
					g.drawRect(
					   ((int)CDX[i]) + ((int)CDW[i]) - Frame.CDSubW,
					   ((int)CDY[i]) + ((int)CDH[i]) - Frame.CDSubH,
					   Frame.CDSubW,
					   Frame.CDSubH);

					if ((Boolean)(CDSelected[i]))
					{
						g.setColor(0x80, 0xff, 0xff, 0xff);
						g.fillRect(
							((int)CDX[i]),
							((int)CDY[i]),
							((int)CDW[i]),
							((int)CDH[i])
							);
						g.fillRect(
						   ((int)CDX[i]) + ((int)CDW[i]) - Frame.CDSubW,
						   ((int)CDY[i]) + ((int)CDH[i]) - Frame.CDSubH,
						   Frame.CDSubW,
						   Frame.CDSubH);
					}

		} 
        
 

        public void render(Graphics g, 
			ArrayList tile,
			float bx, float by,
			bool showimageborder,
			bool showCD,
			bool complex_mode)
		{
			try
			{
				g.pushState();
				g.translate(bx, by);

				if (complex_mode)
				{
					g.multiplyAlpha(alpha);
					g.rotate(rotate);
					g.scale(scalex, scaley);
					g.shear(shearx, sheary);
				}


				for (int i = SubIndex.Count - 1; i >= 0; i--)
				{
					renderPart(g, tile, i, showimageborder, complex_mode);
				}
				if (showCD)
				{
					for (int i = 0; i < CDMask.Count; i++)
					{
						renderCD(g, i);
					}
				}
				g.popState();
			}
			catch (Exception err) 
			{
				Console.WriteLine(err.Message);
				Console.WriteLine(err.StackTrace);

			}

        }
       


    }

    class Group
    {
        ArrayList Frames = new ArrayList();

        public Group()
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
            for (int i = 0; i < Frames.Count; i++)
            {
                if (Frames[i].Equals(frame)) return i;

                if (((ArrayList)Frames[i]).Count != frame.Count) continue;

                Boolean ok = true;

                for (int j = 0; j < frame.Count; j++)
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
    }
}