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
using System.Diagnostics;
using System.Text.RegularExpressions;

namespace CellGameEdit.PM
{
    [Serializable]
    public partial class ImagesForm : Form, ISerializable , IEditForm
    {
        //public static System.Drawing.Color ColorKey = System.Drawing.Color.FromArgb(0xff,0xff,0xff,0);
        //public static System.Drawing.Color ColorTileID = System.Drawing.Color.FromArgb(0xff, 0, 0xff, 0);

        public String id;

        int CellW = 16;
        int CellH = 16;
        public ArrayList dstImages;
        public ArrayList dstDataKeys;

        //[NoneSerializable]
        javax.microedition.lcdui.Image srcImage;
        System.Drawing.Rectangle srcRect;
        Boolean srcRectIsDown = false;
        int srcPX;
        int srcPY;
        int srcQX;
        int srcQY;
        int srcSize = 1;

        System.Drawing.Color backColor = System.Drawing.Color.Magenta;
        System.Drawing.Color keyColor = System.Drawing.Color.MediumBlue;

        private Boolean is_change_image = false;
        ArrayList outStreamLen = null;

		private string append_data = "";

        private string image_convert_script_file = "";
        private string[] image_convert_script = null;

        public ImagesForm(String name)
        {
            InitializeComponent();

            id = name; this.Text = id;
            CellW = 16;
            CellH = 16;
            dstImages = new ArrayList();
            dstDataKeys = new ArrayList();
            toolStripTextBox1.Text = CellW.ToString();
            toolStripTextBox2.Text = CellH.ToString();

            // src
            pictureBox1.Width = 1;
            pictureBox1.Height = 1;
            pictureBox1.Image = new System.Drawing.Bitmap(1,1);
            srcImage = Image.createImage(pictureBox1.Width, pictureBox1.Height);
            

            srcRectIsDown = false;
            srcRect = new System.Drawing.Rectangle(0, 0, 1, 1);

           
            //
            pictureBox2.Width = 1;
            pictureBox2.Height = 1;
            
            dstSelected = null;
            
            dstRect = new System.Drawing.Rectangle(0, 0, 1, 1);

            is_change_image = true;

            pictureBox1.BackColor = toolStripButton10.BackColor;
            pictureBox2.BackColor = toolStripButton10.BackColor;

        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected ImagesForm(SerializationInfo info, StreamingContext context)
        {
            try
            {
                InitializeComponent();
                pictureBox2.Width = 1;
                pictureBox2.Height = 1;
                // load start
                id = (String)info.GetValue("id", typeof(String));
                this.Text = id;
                CellW = (int)info.GetValue("CellW", typeof(int));
                CellH = (int)info.GetValue("CellH", typeof(int));
                ArrayList output = (ArrayList)info.GetValue("output", typeof(ArrayList));
                ArrayList outX = (ArrayList)info.GetValue("outX", typeof(ArrayList));
                ArrayList outY = (ArrayList)info.GetValue("outY", typeof(ArrayList));
                ArrayList outK;

				try
				{
					this.append_data = info.GetString("AppendData");
				}
				catch (Exception err)
				{
					this.append_data = "";
				}
                try
                {
                    chk_custom_output.Checked = (Boolean)info.GetValue("custom_output", typeof(Boolean));
                    chk_output_group.Checked =
                        (Boolean)info.GetValue("output_group", typeof(Boolean));
                    chk_output_tile.Checked =
                        (Boolean)info.GetValue("output_tile", typeof(Boolean));
                }
                catch (Exception err) { }
                finally
                {
                    chk_output_group.Enabled = !chk_custom_output.Checked;
                    chk_output_tile.Enabled = !chk_custom_output.Checked;
                }
                
                try
                {
                    chk_custom_filetype.Checked = (Boolean)info.GetValue("custom_filetype", typeof(Boolean));
                    chk_output_file_bmp.Checked =
                        (Boolean)info.GetValue("output_file_bmp", typeof(Boolean));
                    chk_output_file_jpg.Checked =
                        (Boolean)info.GetValue("output_file_jpg", typeof(Boolean));
                    chk_output_file_png.Checked =
                        (Boolean)info.GetValue("output_file_png", typeof(Boolean));
                }
                catch (Exception err) { }
                finally
                {
                    chk_output_file_bmp.Enabled = !chk_custom_filetype.Checked;
                    chk_output_file_jpg.Enabled = !chk_custom_filetype.Checked;
                    chk_output_file_png.Enabled = !chk_custom_filetype.Checked;
                }

                try
                {
                    outK = (ArrayList)info.GetValue("outK", typeof(ArrayList));
                }
                catch (Exception err)
                {
                    outK = new ArrayList();
                    for (int i = 0; i < output.Count; i++)
                    {
                        outK.Add(false);
                    }
                }

                try
                {
                    dstDataKeys = (ArrayList)info.GetValue("dstDataKeys", typeof(ArrayList));
                }
                catch (Exception err)
                {
                    dstDataKeys = new ArrayList();
                    for (int i = 0; i < output.Count; i++)
                    {
                        dstDataKeys.Add("");
                    }
                }

                image_convert_script_file = (string)Util.GetValue(info, "image_convert_script", typeof(string), "");
                if (image_convert_script_file != null && image_convert_script_file.Length > 0)
                {
                    comboImageConvert.Text = image_convert_script_file;
                }

                try
                {
                    outStreamLen = (ArrayList)info.GetValue("outStreamLen", typeof(ArrayList));
                }
                catch (Exception err) { }


                try
                {
                    toolStripButton10.BackColor = 
                        (System.Drawing.Color)info.GetValue("BackColor", typeof(System.Drawing.Color));
                    BtnSelectKeyColor.BackColor = 
                        (System.Drawing.Color)info.GetValue("TileKeyColor", typeof(System.Drawing.Color));
                    BtnSelectTileIDColor.BackColor = 
                        (System.Drawing.Color)info.GetValue("TileIDColor", typeof(System.Drawing.Color));
                }
                catch (Exception err) {
                } finally {
                    pictureBox1.BackColor = toolStripButton10.BackColor;
                    pictureBox2.BackColor = toolStripButton10.BackColor;
                }

              


                System.IO.FileStream images_fs = null;

                if (outStreamLen != null)
                {
                    String dir1 = ProjectForm.workSpace + "\\tiles\\" + this.id + ".tiles";
                    String dir2 = ProjectForm.workSpace + "\\" + this.id + ".tiles";

                    if (System.IO.File.Exists(dir2))
                    {
                        images_fs = new System.IO.FileStream(dir2, System.IO.FileMode.Open);
                    }
                    else if (System.IO.File.Exists(dir1))
                    {
                        images_fs = new System.IO.FileStream(dir1, System.IO.FileMode.Open);
                    }
                }


                dstImages = new ArrayList();

                for (int i = 0; i < output.Count; i++)
                {
                    try
                    {
                        String name = (String)output[i];
                        int x = (int)outX[i];
                        int y = (int)outY[i];
                        Boolean kill = (Boolean)outK[i];
                        int len = -1;
                        if (outStreamLen != null)
                        {
                            len = (int)outStreamLen[i];
                        }


                        Image img;

                        System.IO.MemoryStream ms = null;
                        if (len < 0)
                        {
                            ms = new System.IO.MemoryStream(System.IO.File.ReadAllBytes(ProjectForm.workSpace + name));
                        }
                        else
                        {
                            byte[] data = new byte[len];
                            images_fs.Read(data, 0, len);
                            ms = new System.IO.MemoryStream(data);
                        }
                        System.Drawing.Image dimg = System.Drawing.Image.FromStream(ms);
                        ms.Close();

                        img = new Image(dimg);
                        img.x = x;
                        img.y = y;
                        img.killed = kill;
						img.indexOfImages = i;
                        if (!img.killed)
                        {
                            pictureBox2.Width = Math.Max(pictureBox2.Width, img.x + img.getWidth());
                            pictureBox2.Height = Math.Max(pictureBox2.Height, img.y + img.getHeight());
                        }

                        dstImages.Add(img);
                    }
                    catch (Exception err)
                    {
                        dstImages.Add(null);
                        Console.WriteLine(this.id + " : Tile[" + i + "] : " + err.StackTrace + "  at  " + err.Message);
                    }



                }

                if (images_fs != null)
                {
                    images_fs.Close();
                }
                // load end

                toolStripTextBox1.Text = CellW.ToString();
                toolStripTextBox2.Text = CellH.ToString();

                // src
                pictureBox1.Width = 1;
                pictureBox1.Height = 1;
                pictureBox1.Image = new System.Drawing.Bitmap(1, 1);
                srcImage = Image.createImage(pictureBox1.Width, pictureBox1.Height);

                srcRectIsDown = false;
                srcRect = new System.Drawing.Rectangle(0, 0, 1, 1);


                //
                //pictureBox2.Width = 1;
                //pictureBox2.Height = 1;

                dstSelected = null;

                dstRect = new System.Drawing.Rectangle(0, 0, 1, 1);
            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace + "  at  " +err.Message);
            }

            is_change_image = false;
        }
        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            Console.WriteLine("Serializ Images : " + id);

            try
            {
                info.AddValue("id", id);
                info.AddValue("CellW", CellW);
                info.AddValue("CellH", CellH);


				info.AddValue("AppendData", append_data);

                info.AddValue("image_convert_script", image_convert_script_file);

                ArrayList output = new ArrayList();
                ArrayList outX = new ArrayList();
                ArrayList outY = new ArrayList();
                ArrayList outK = new ArrayList();


                //String dir =  "\\tiles\\" + this.id;
                //System.IO.Directory.CreateDirectory(ProjectForm.workSpace + "\\tiles");
                //System.IO.Directory.CreateDirectory(ProjectForm.workSpace + dir);
                String tiles_file_name = ProjectForm.workSpace + "\\" + this.id + ".tiles";
                if (!System.IO.File.Exists(tiles_file_name))
                {
                    is_change_image = true;
                }

                // first put all image to stream
                if (is_change_image)
                {
                    outStreamLen = new ArrayList();

                    System.IO.FileStream fs = new System.IO.FileStream(
                               tiles_file_name,
                                 System.IO.FileMode.Create);

                    for (int i = 0; i < dstImages.Count; i++)
                    {
                        try
                        {
                            // String name = dir + "\\" + i.ToString() + ".tile";

                            Image img = getDstImage(i);

                            if (img != null)
                            {
                                System.IO.MemoryStream ms = new System.IO.MemoryStream();

                                System.Drawing.Image dimage = img.getDImage();
                                dimage.Save(ms, System.Drawing.Imaging.ImageFormat.Png);

                                byte[] data = ms.ToArray();
                                ms.Close();
                                fs.Write(data, 0, data.Length);
                                fs.Flush();

                                outStreamLen.Add(data.Length);
                            }
                            else
                            {
                                outStreamLen.Add(-1);
                            }
                        }
                        catch (Exception err)
                        {
                            outStreamLen.Add(-1);

                            Console.WriteLine(this.id + " : " + i + " : " + err.StackTrace + "  at  " + err.Message);
                        }
                    }

                    fs.Close();

                }

                // put image property
                for (int i = 0; i < dstImages.Count; i++)
                {
                    try
                    {
                        Image img = getDstImage(i);

                        String name = i.ToString() + ".tile";

                        if (img != null)
                        {
                            output.Add(name);
                            outX.Add(img.x);
                            outY.Add(img.y);
                            outK.Add(img.killed);
                        }
                        else
                        {
                            output.Add("");
                            outX.Add(0);
                            outY.Add(0);
                            outK.Add(false);
                        }
                    }
                    catch (Exception err)
                    {
                        output.Add("");
                        outX.Add(0);
                        outY.Add(0);
                        outK.Add(false);

                        Console.WriteLine(this.id + " : " + i + " : " + err.StackTrace + "  at  " + err.Message);
                    }
                }

                info.AddValue("output", output);
                info.AddValue("outX", outX);
                info.AddValue("outY", outY);
                info.AddValue("outK", outK);
                {
                    info.AddValue("custom_output", chk_custom_output.Checked);
                    info.AddValue("output_tile", chk_output_tile.Checked);
                    info.AddValue("output_group", chk_output_group.Checked);
                }{
                    info.AddValue("custom_filetype", chk_custom_filetype.Checked);
                    info.AddValue("output_file_bmp", chk_output_file_bmp.Checked);
                    info.AddValue("output_file_jpg", chk_output_file_jpg.Checked);
                    info.AddValue("output_file_png", chk_output_file_png.Checked);
                }
                info.AddValue("outStreamLen", outStreamLen);

                info.AddValue("dstDataKeys", dstDataKeys);

                info.AddValue("BackColor", toolStripButton10.BackColor);
                info.AddValue("TileKeyColor", BtnSelectKeyColor.BackColor);
                info.AddValue("TileIDColor", BtnSelectTileIDColor.BackColor);

                is_change_image = false;
            }
            catch (Exception err)
            {
                MessageBox.Show(err.StackTrace + "  at  " +err.Message);
            }
        }

        public void changeImage()
        {
            is_change_image = true;
        }

        public void DelAllImages()
        {
           
        }

       

        private void outputAllImages(String dir,String type,Boolean tile,Boolean group)
        {
            try
            {
                image_convert_script = null;

                if (image_convert_script_file != null && image_convert_script_file.Length > 0)
                {
                    image_convert_script = System.IO.File.ReadAllLines(
                         Application.StartupPath + "\\converter\\" + image_convert_script_file);
                }
                string default_image_convert_script = Form1.getGobalImageConvertScriptFile();
                if (image_convert_script == null &&
                    default_image_convert_script != null && default_image_convert_script.Length > 0)
                {

                    image_convert_script = System.IO.File.ReadAllLines(
                        Application.StartupPath + "\\converter\\" + default_image_convert_script);
                }
            }
            catch (Exception err)
            {
                image_convert_script = null;
                MessageBox.Show(this.id + " : " + err.Message + "\n" + err.StackTrace);
            }

            try
            {
                System.Drawing.Imaging.EncoderParameters param = null;
                System.Drawing.Imaging.ImageCodecInfo info = null;
                System.Drawing.Imaging.ImageFormat format = null;
                //
                if (type.Equals("png", StringComparison.CurrentCultureIgnoreCase))
                    format = System.Drawing.Imaging.ImageFormat.Png;
                if (type.Equals("bmp", StringComparison.CurrentCultureIgnoreCase))
                    format = System.Drawing.Imaging.ImageFormat.Bmp;
                if (type.Equals("jpg", StringComparison.CurrentCultureIgnoreCase))
                    format = System.Drawing.Imaging.ImageFormat.Jpeg;
                if (type.Equals("gif", StringComparison.CurrentCultureIgnoreCase))
                    format = System.Drawing.Imaging.ImageFormat.Gif;

                //info.

                if (format == null) return;

                if (group)
                {
                    saveAllAsGrop(dir + "\\" + this.id + "." + type, format);
                }
                if (tile)
                {
                    saveAllTiles(dir + "\\" + this.id + "\\", format);
                }
            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }

        }

        private void saveAllTiles(string tileDir, System.Drawing.Imaging.ImageFormat format)
		{
			if (!System.IO.Directory.Exists(tileDir))
			{
				System.IO.Directory.CreateDirectory(tileDir);
			}
			for (int i = 0; i < getDstImageCount(); i++)
			{
				if (getDstImage(i) == null || getDstImage(i).killed) continue;
				try
				{
                    string filepath = tileDir + i + "." + format.ToString().ToLower();
                    getDstImage(i).getDImage().Save(filepath, format);

                    runImageConvertScript(image_convert_script, filepath);
				}
				catch (Exception err) { Console.WriteLine(this.id + " : save tile : " + err.StackTrace + "  at  " + err.Message); }
			}
		}

        private void saveAllAsGrop(string filepath, System.Drawing.Imaging.ImageFormat format)
		{
			try
			{
				
				
					int outW = 0;
					int outH = 0;
					for (int i = 0; i < getDstImageCount(); i++)
					{
						if (getDstImage(i) != null && !getDstImage(i).killed)
						{
							outW = Math.Max(outW, getDstImage(i).x + getDstImage(i).getWidth());
							outH = Math.Max(outH, getDstImage(i).y + getDstImage(i).getHeight());
						}
					}

					Image outputImage = Image.createImage(outW, outH);
					Graphics g = outputImage.getGraphics();
					for (int i = 0; i < getDstImageCount(); i++)
					{
						if (getDstImage(i) == null || getDstImage(i).killed) continue;
						g.drawImage(getDstImage(i), getDstImage(i).x, getDstImage(i).y);

					}
					//
					outputImage.getDImage().Save(filepath, format);

                    runImageConvertScript(image_convert_script, filepath);
				
			}
			catch (Exception err)
			{
				Console.WriteLine(this.id + " : save group : " + err.StackTrace + "  at  " + err.Message);
			}
		}

        private void runImageConvertScript(string[] script, string input)
        {
            if (script == null || script.Length == 0)
            {
                return;
            } 
            //append\ImageMagick\convertimg.exe -resize 50% <INPUT> png8:<OUTPUT>
            try
            {
                string workdir = System.IO.Path.GetDirectoryName(input);

                string inputfile = System.IO.Path.GetFileName(input);

                foreach (string cmd in script) 
                {
                    string cmdline = cmd.Trim();

                    if (cmdline.Length > 0)
                    {
                        runImageConvertExt(cmdline, workdir, inputfile);
                    }
                }
            }
            catch (System.Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }

        private void runImageConvertExt(string cmd, string workdir, string input)
        {
            string[] exeargs = Regex.Split(cmd, "\\s+");
            string exec = System.IO.Path.GetFullPath(Application.StartupPath + exeargs[0]);
            string args = Util.stringLink(exeargs, 1, exeargs.Length - 1, " ");
            args = args.Replace("<INPUT>", input);
            Console.WriteLine(exec + " " + args);
            //實例一個Process類，啟動一個獨立進程
            Process p = new Process();
            //Process類有一個StartInfo屬性，這個是ProcessStartInfo類，
            //包括了一些屬性和方法，下面我們用到了他的幾個屬性：
            p.StartInfo.FileName = exec;  //設定程序名
            p.StartInfo.Arguments = args;    //設定程式執行參數
            p.StartInfo.WorkingDirectory = workdir;
            p.StartInfo.UseShellExecute = false;        //關閉Shell的使用
            p.StartInfo.RedirectStandardInput = false;   //重定向標準輸入
            p.StartInfo.RedirectStandardOutput = false;  //重定向標準輸出
            p.StartInfo.RedirectStandardError = false;   //重定向錯誤輸出
            p.StartInfo.CreateNoWindow = false;          //設置不顯示窗口
            //啟動
            if (p.Start())
            {
            }
            p.WaitForExit();
            //String result = p.StandardOutput.ReadToEnd();        //從輸出流取得命令執行結果
            //Console.WriteLine(result);
            //p.StandardInput.WriteLine(command);       //也可以用這種方式輸入要執行的命令
            //p.StandardInput.WriteLine("exit");        //不過要記得加上Exit要不然下一行程式執行的時候會當機
        }

        public System.Drawing.Color getCurrentClickColor()
        {
            return toolStripColor.BackColor;
        }

		public void onProcessImageSizeChanged(ArrayList events)
		{
			ArrayList childs = ProjectForm.getInstance().getImangesFormChilds(this);

			foreach (Object ef in childs)
			{
				if (ef.GetType() == typeof(SpriteForm)) 
				{
					((SpriteForm)ef).onProcessImageSizeChanged(this, events);
				}
			}
		}

//        public void Output(System.IO.StringWriter sw)
//        {
//            String head = "/" + this.id+"/tile_";

//            sw.WriteLine("final static public void buildImages_" + this.id + "(IImages stuff)");
//            sw.WriteLine("{");
//            sw.WriteLine("    stuff.buildImages(null, " + this.getDstImageCount() + ");");
//            sw.WriteLine("    for(int i=0;i<" + this.getDstImageCount() + ";i++){");
//            sw.WriteLine("        stuff.setTileImage(CIO.loadImage(\"" + head + "\"+i+\".png" + "\"));");
//            sw.WriteLine("        stuff.addTile();");
//            sw.WriteLine("    }");
//            sw.WriteLine("    stuff.gc();");
//            sw.WriteLine("}");

//            sw.WriteLine("final static public void buildClipImages_" + this.id + "(IImages stuff)");
//            sw.WriteLine("{");
//            sw.WriteLine("    stuff.buildImages(CIO.loadImage(\"/" + this.id + ".png\"), " + this.getDstImageCount() + ");");
//for (int i = 0; i < getDstImageCount(); i++){if (getDstImage(i) != null){//
//            sw.WriteLine("    stuff.addTile(" + getDstImage(i).x + "," + getDstImage(i).y + "," + getDstImage(i).getWidth() + "," + getDstImage(i).getHeight() + ");");
//}}//
//            sw.WriteLine("    stuff.gc();");
//            sw.WriteLine("}");

//            SaveAllImages(ProjectForm.workSpace,"png",false,true);

//        }

        public void OutputCustom(int index, String script, System.IO.StringWriter output, String outDir,
            String imageType, bool imageTile, bool imageTileData, bool imageGroup, bool imageGroupData)
        {
            lock (this)
            {
                try
                {
                    String images = Util.getFullTrunkScript(script, SC._IMAGES, SC._END_IMAGES);

                    Boolean isIgnoreNullTile = false;
                    try
                    {
                        isIgnoreNullTile = Util.getCommandScript(images, SC.IGNORE_NULL_CLIP).Equals("true", StringComparison.CurrentCultureIgnoreCase);
                    }
                    catch (Exception err) { }

                    bool fix = false;
                    do
                    {
                        String[] clips = new string[getDstImageCount()];
                        for (int i = 0; i < getDstImageCount(); i++)
                        {
                            if (getDstImage(i) != null && getDstImage(i).killed==false)
                            {
                                string X = getDstImage(i).killed ? "0" : getDstImage(i).x.ToString();
                                string Y = getDstImage(i).killed ? "0" : getDstImage(i).y.ToString();
                                string W = getDstImage(i).killed ? "0" : getDstImage(i).getWidth().ToString();
                                string H = getDstImage(i).killed ? "0" : getDstImage(i).getHeight().ToString();
                                string DATA = getDstImage(i).killed ? "" : (String)dstDataKeys[i];
                                clips[i] = Util.replaceKeywordsScript(images, SC._CLIP, SC._END_CLIP,
                                    new string[] { SC.INDEX, SC.X, SC.Y, SC.W, SC.H ,SC.DATA},
                                    new string[] { i.ToString(), X, Y, W, H ,DATA}
                                    );
                            }
                            else 
                            {
                                if (isIgnoreNullTile == false)
                                {
                                    clips[i] = Util.replaceKeywordsScript(images, SC._CLIP, SC._END_CLIP,
                                          new string[] { SC.INDEX, SC.X, SC.Y, SC.W, SC.H, SC.DATA },
                                          new string[] { i.ToString(), "0", "0", "0", "0", "" }
                                          );
                                }
                                else
                                {
                                    clips[i] = "";
                                    Console.WriteLine("Ignore null clip : " + i);
                                }
                                
                            }
                        }
                        string temp = Util.replaceSubTrunksScript(images, SC._CLIP, SC._END_CLIP, clips);

                        if (temp == null)
                        {
                            fix = false;
                        }
                        else
                        {
                            fix = true;
                            images = temp;
                        }

                    } while (fix);

					String ofiletype = imageType;
					Boolean ogroup = imageGroup;
					Boolean otile = imageTile;

                    String custom_output_image_type = "";
                    if (!chk_custom_output.Checked)
                    {
                        custom_output_image_type =
                            (chk_output_group.Checked ? "group" : "") + "," +
                            (chk_output_tile.Checked ? "tile" : "");

						if (chk_output_group.Checked)
						{
							ogroup = true;
						}
						if (chk_output_tile.Checked)
						{
							otile = true;
						}
                    }

                    String custom_output_image_file = "";
                    if (!chk_custom_filetype.Checked)
                    {
                        if (chk_output_file_bmp.Checked)
                        {
							custom_output_image_file = "bmp";
							ofiletype = "bmp";
                        }
                        else if (chk_output_file_jpg.Checked)
                        {
							custom_output_image_file = "jpg";
							ofiletype = "jpg";
                        }
                        else if (chk_output_file_png.Checked)
                        {
							custom_output_image_file = "png";
							ofiletype = "png";
                        }
                    }



					string[] adata = Util.toStringMultiLine(append_data);
					string APPEND_DATA = Util.toStringArray1D(ref adata);
                    images = Util.replaceKeywordsScript(images, SC._IMAGES, SC._END_IMAGES,
                        new string[] {
                            SC.NAME,
                            SC.IMAGES_INDEX,
                            SC.COUNT, 
                            SC.OUTPUT_IMAGE_TYPE,
                            SC.OUTPUT_IMAGE_FILE,
							SC.APPEND_DATA,
                            SC.IMAGE_INFO
                        },
                        new string[] { 
                            this.id,
                            index.ToString(),
                            this.getDstImageCount().ToString(),
                            custom_output_image_type,
                            custom_output_image_file,
							APPEND_DATA,
                            image_convert_script_file
                        }
                        );

                    output.WriteLine(images);
                    //Console.WriteLine(images);

                    outputAllImages(outDir, ofiletype, otile, ogroup);

                }
                catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }
            }
        }


        private void ImagesForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if(e.CloseReason == CloseReason.UserClosing)
	        {
                e.Cancel = true;
                this.Hide();
	        }
        }
        private void ImagesForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

        public String getID()
        {
            return id;
        }

        public Form getForm()
        {
            return this;
        }

        public void setSrcImage()
        {
            OpenFileDialog openFileDialog1 = new OpenFileDialog();
            openFileDialog1.Filter = "PNG files (*.png)|*.png|All files (*.*)|*.*";
			try
            {
	            openFileDialog1.InitialDirectory = System.IO.Path.GetDirectoryName(Config.Default.LastImageOpenDir);
            }
            catch (System.Exception e){}
             if (openFileDialog1.ShowDialog() == DialogResult.OK)
			{
				Config.Default.LastImageOpenDir = System.IO.Path.GetDirectoryName(openFileDialog1.FileName);
				Config.Default.Save();
                Image buf = Image.createImage(openFileDialog1.FileName);

                srcImage = Image.createImage(buf.getWidth(), buf.getHeight());
                srcImage.getGraphics().drawImage(buf,0,0);

                buf.dimg.Dispose();
                buf = null;

                pictureBox1.Width = srcImage.getWidth() * srcSize;
                pictureBox1.Height = srcImage.getHeight() * srcSize;

                toolStripButton2.Checked = false;
                toolStripButton3.Checked = false;

                toolStripStatusLabel3.Text =
                  " : 宽=" + srcImage.getWidth() +
                  " : 高=" + srcImage.getHeight()
                ;
            }
         
        }


        public void addDst(Image img)
        {
            is_change_image = true;

            for (int i = 0; i < dstImages.Count; i++)
            {
                if (dstImages[i] == null || getDstImage(i).killed)
                {
					img.indexOfImages = i;
                    dstImages[i] = img;
//                     try
//                     {
//                         // save to src pic
//                         //String name = "\\tiles\\" + this.id + "\\" + i.ToString() + ".tile";
//                        // if (System.IO.File.Exists(ProjectForm.workSpace + name))
//                         //{
//                         //    System.IO.File.Delete(ProjectForm.workSpace + name);
//                        // }
//                         //getDstImage(i).dimg.Save(ProjectForm.workSpace + name, System.Drawing.Imaging.ImageFormat.Png);
//                     }
//                     catch (Exception err) { }
                    return;
                }
            }
			img.indexOfImages = dstImages.Count;
            dstImages.Add(img);
            dstDataKeys.Add("");

        }

        public ArrayList addDirImages()
        {
			ArrayList ret = new ArrayList();

            OpenFileDialog openFileDialog1 = new OpenFileDialog();
            openFileDialog1.Multiselect = true;
            openFileDialog1.Filter = "PNG files (*.png)|*.png|All files (*.*)|*.*";
			try
			{
				openFileDialog1.InitialDirectory = System.IO.Path.GetDirectoryName(Config.Default.LastImageOpenDir);
			}
			catch (System.Exception e)
			{
				
			}
		
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
				Config.Default.LastImageOpenDir = System.IO.Path.GetDirectoryName(openFileDialog1.FileName);
				Config.Default.Save();
				
				int maxW = pictureBox2.Width / dstScale;
				int maxH = pictureBox2.Height / dstScale;
				int curX = 0;
				int curY = maxH;

                for (int i = 0; i < openFileDialog1.FileNames.Length; i++ )
                {
					try
					{
						Image img = Image.createImage(openFileDialog1.FileNames[i]);
						if (img.getWidth() > maxW)
						{
							maxW = img.getWidth();
						}
						if (curX + img.getWidth() > maxW)
						{
							curX = 0;
							curY = maxH;
							img.x = curX;
							img.y = curY;
							curX += img.getWidth();
						}
						else
						{
							img.x = curX;
							img.y = curY;
							curX += img.getWidth();
						}
						addDst(img);
						ret.Add(img);
						maxW = Math.Max(maxW, img.x + img.getWidth());
						maxH = Math.Max(maxH, img.y + img.getHeight());
					}
					catch (Exception err)
					{
						Console.WriteLine(err.StackTrace + "  at  " + err.Message);
					}
				}
				pictureBox2.Width = maxW * dstScale;
				pictureBox2.Height = maxH * dstScale;
            }

			return ret;
        }


        public void addDstImage()
        {
            if (srcImage == null)
            {
                System.Windows.Forms.MessageBox.Show("设置原始图片先");
            }
            else
            {
                if (srcRect.Width != 0 && srcRect.Height != 0)
                {
                    Image img = Image.createImage(
                        srcImage,
                        srcRect.X,
                        srcRect.Y,
                        srcRect.Width,
                        srcRect.Height);
                    img.x = 0;
                    img.y = pictureBox2.Height / dstScale;
                    addDst(img);

                    pictureBox2.Width = Math.Max(pictureBox2.Width, img.getWidth() * dstScale);
                    pictureBox2.Height += (img.getHeight() * dstScale);
                }
            }
        }
        
        public void addDstImages()
        {
            if (srcImage == null)
            {
                System.Windows.Forms.MessageBox.Show("设置原始图片先");
            }
            else
            {
                if (srcRect.Width != 0 && srcRect.Height != 0)
                {
                    for (int y = 0; y < srcRect.Height / CellH; y++)
                    {
                        for (int x = 0; x < srcRect.Width / CellW; x++)
                        {

                            if (CellW != 0 && CellH != 0)
                            {
                                Image img = Image.createImage(
                                    srcImage,
                                    srcRect.X + x * CellW,
                                    srcRect.Y + y * CellH,
                                    CellW,
                                    CellH);
                                img.x = x * (CellW);
                                img.y = y * (CellH) + pictureBox2.Height / dstScale;
                                addDst(img);
                            }

                        }
                    }
                    pictureBox2.Width = Math.Max(pictureBox2.Width, (srcRect.Width / CellW * CellW) ) * dstScale;
                    pictureBox2.Height += ((srcRect.Height / CellH * CellH)) * dstScale;

                }
            }

        }
       
        public Image getSrcImage()
        {
            return srcImage;
        }
       
        public Image getDstImage(int index)
        {
            if (index >= dstImages.Count || index < 0) return null;
            return (((Image)(dstImages[index])));
        }

        public Image getSelectedImage()
        {
            return dstSelected;
        }

		public int getAvaliableImageIndex()
		{
			Image img = getDstImage(dstSelectIndex);
			if (img == null)
			{
				int count = getDstImageCount();
				for (int i = 0; i < count; i++)
				{
					img = getDstImage(i);
					if (img != null) {
						return i;
					}
				}
			}
			return dstSelectIndex;
		}

        public int getDstImageCount()
        {
            return dstImages.Count;
        }

      
        public bool moveDstImage(int index,int dx,int dy)
        {
            Image img_i = getDstImage(index);

            if (img_i == null) return false;
           
            img_i.x += dx;
            img_i.y += dy;

            if (pictureBox2.Width < img_i.x * dstScale + img_i.getWidth() * dstScale) { 
                pictureBox2.Width += dx * dstScale; }
            if (pictureBox2.Height < img_i.y * dstScale + img_i.getHeight() * dstScale) {
                pictureBox2.Height += dy * dstScale; }

            System.Drawing.Rectangle src = new System.Drawing.Rectangle(
                img_i.x,
                img_i.y,
                img_i.getWidth(),
                img_i.getHeight()
                );

            System.Drawing.Rectangle scope = new System.Drawing.Rectangle(
                0, 0,
                pictureBox2.Width / dstScale,
                pictureBox2.Height / dstScale
                );

            if (scope.Contains(src) == false)
            {
                img_i.x -= dx;
                img_i.y -= dy;
                return false;
            }
         

            for (int i = 0; i < getDstImageCount(); i++)
            {
                Image img_d = getDstImage(i);

                if (img_d != null && img_d.killed == false && i != index)
                {
                    System.Drawing.Rectangle dst = new System.Drawing.Rectangle(
                       img_d.x,
                       img_d.y,
                       img_d.getWidth(),
                       img_d.getHeight()
                       );

                    if (src.IntersectsWith(dst))
                    {
                        img_i.x -= dx;
                        img_i.y -= dy;
                        return false;
                    }
                    
                }
            }

            return true;
        }

        public bool moveDstImages(int dx, int dy)
        {
            if (dx == 0 && dy == 0) return true;

            System.Drawing.Rectangle src = new System.Drawing.Rectangle();
            System.Drawing.Rectangle dst = new System.Drawing.Rectangle();

            for (int i = 0; i < getDstImageCount(); i++)
            {
                Image img_i = getDstImage(i);

                if (img_i != null && img_i.killed == false && img_i.selected)
                {
                    src.X = img_i.x + dx;
                    src.Y = img_i.y + dy;
                    src.Width = img_i.getWidth();
                    src.Height = img_i.getHeight();

                    if (src.X < 0 || src.Y < 0)
                    {
                        return false;
                    }
                    for (int j = 0; j < getDstImageCount(); j++)
                    {
                        Image img_j = getDstImage(j);

                        if (i != j && img_j != null && img_j.killed == false && img_j.selected == false)
                        {
                            dst.X = img_j.x;
                            dst.Y = img_j.y;
                            dst.Width = img_j.getWidth();
                            dst.Height = img_j.getHeight();

                            if (src.IntersectsWith(dst))
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < getDstImageCount(); i++)
            {
                Image img_i = getDstImage(i);

                if (img_i != null && img_i.killed == false && img_i.selected)
                {
                    img_i.x += dx;
                    img_i.y += dy;

                    if (pictureBox2.Width < img_i.x * dstScale + img_i.getWidth() * dstScale) { 
                        pictureBox2.Width += dx * dstScale; 
                    }
                    if (pictureBox2.Height < img_i.y * dstScale + img_i.getHeight() * dstScale) {
                        pictureBox2.Height += dy * dstScale; 
                    }

                }
            }

            return true;
        }

        public void changeDstImage(int index, Image changed)
        {
            Image srcImage = getDstImage(index);
            int oy = srcImage.y;
            int ox = srcImage.x;

            // test size
            if (changed.getWidth() <= srcImage.getWidth() &&
                changed.getHeight() <= srcImage.getHeight())
            {
                changed.x = ox;
                changed.y = oy;
            }
            else
            {
                bool isSameSize = true;
                System.Drawing.Rectangle srcRect = new System.Drawing.Rectangle(
                    ox,
                    oy,
                    changed.getWidth(),
                    changed.getHeight()
                    );
                for (int i = 0; i < getDstImageCount(); i++)
                {
                    Image dstImage = getDstImage(i);

                    if (dstImage != null && !dstImage.killed && i != index)
                    {
                        System.Drawing.Rectangle dstRect = new System.Drawing.Rectangle(
                           dstImage.x,
                           dstImage.y,
                           dstImage.getWidth(),
                           dstImage.getHeight()
                           );

                        if (srcRect.IntersectsWith(dstRect))
                        {
                            isSameSize = false;
                            break;
                        }

                    }
                }
                if (isSameSize)
                {
                    changed.x = ox;
                    changed.y = oy;
                }
                else
                {
                    changed.x = pictureBox2.Width / dstScale;
                    changed.y = oy;
                }
            }

            dstImages[index] = changed;

            pictureBox2.Width += changed.getWidth() * dstScale;
            pictureBox2.Height = Math.Max(pictureBox2.Height, changed.getHeight() * dstScale);
        }

        public void changeDstImageFromDir(int index)
        {
            
            if (getDstImage(index) == null) return ;

            is_change_image = true;

            try
            {
                OpenFileDialog openFileDialog1 = new OpenFileDialog();
                openFileDialog1.Filter = "PNG files (*.png)|*.png|All files (*.*)|*.*";
				try
				{
					openFileDialog1.InitialDirectory = System.IO.Path.GetDirectoryName(Config.Default.LastImageOpenDir);
				}
				catch (System.Exception e)
				{
					
				}
                if (openFileDialog1.ShowDialog() == DialogResult.OK)
				{
					Config.Default.LastImageOpenDir = System.IO.Path.GetDirectoryName(openFileDialog1.FileName);
					Config.Default.Save();

                    Image changed = Image.createImage(openFileDialog1.FileName);
                    changeDstImage(index, changed);
                }
            }
            catch (Exception err) {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message);
            }
       

        }

        public void changeDstImageFormSrc(int index)
        {
            if (getDstImage(index) == null) return;
            if (srcImage == null) return;

            is_change_image = true;

            try
            {
                if (srcPX != srcQX && srcPY != srcQY)
                if (srcRect.Width > 0 && srcRect.Height > 0 && srcImage.getWidth()>0 && srcImage.getHeight()>0)
                {
                    Image changed = Image.createImage(
                                   srcImage,
                                   srcRect.X,
                                   srcRect.Y,
                                   srcRect.Width,
                                   srcRect.Height);

                    changeDstImage(index, changed);
                    
                    //changed.dimg.Save(ProjectForm.workSpace + name, System.Drawing.Imaging.ImageFormat.Png);
                }
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " +err.Message);
            }


        }

        public void delDstImage(int index)
        {
            getDstImage(index).killed = true;
        }

        public void renderSrcImage(Graphics g, int x, int y)
        {
            if (srcImage == null) return;
            g.drawImageScale(srcImage, x, y, 0, srcSize);
        }

        public void renderDstImage(Graphics g, int x, int y)
        {

            for (int i = 0; i < getDstImageCount(); i++)
            {
                if (getDstImage(i) != null && getDstImage(i).killed == false)
                {
                    g.drawImageScale(getDstImage(i), 
						x + getDstImage(i).x * dstScale, 
						y + getDstImage(i).y * dstScale,
						0, dstScale);
                    if (toolStripButton13.Checked)
                    {
                        g.setColor(0x7fffffff);
                        g.drawRect(x + getDstImage(i).x * dstScale, y + getDstImage(i).y * dstScale, getDstImage(i).getWidth() * dstScale, getDstImage(i).getHeight() * dstScale);
                    }
                    if (chkIsShowkey.Checked)
                    {
                        int tx = x + getDstImage(i).x * dstScale;
                        int ty = y + getDstImage(i).y * dstScale;
                        g.setColor(0xff, 0, 0, 0);
                        g.drawString((String)dstDataKeys[i], tx + 1, ty + 1, 0);
                        g.setColor(getColorKey().ToArgb());
                        g.drawString((String)dstDataKeys[i], tx, ty, 0);
                    }
                    if (chkIsShowTileID.Checked)
                    {
                        int tx = x + getDstImage(i).x * dstScale;
                        int ty = y + (getDstImage(i).y + getDstImage(i).getHeight() - (int)Graphics.font.GetHeight()) * dstScale;
                        g.setColor(0xff, 0, 0, 0);
                        g.drawString(i.ToString(), tx + 1, ty + 1, 0);
                        g.setColor(getColorTileID().ToArgb());
                        g.drawString(i.ToString(), tx, ty, 0);
                    }
                    if (multiSelect.Checked)
                    {
                        if (getDstImage(i).selected)
                        {
                            g.setColor(0x40ffffff);
                            g.fillRect(x + getDstImage(i).x * dstScale, y + getDstImage(i).y * dstScale, getDstImage(i).getWidth() * dstScale, getDstImage(i).getHeight() * dstScale);
                            g.setColor(0x7fffffff);
                            g.drawRect(x + getDstImage(i).x * dstScale, y + getDstImage(i).y * dstScale, getDstImage(i).getWidth() * dstScale, getDstImage(i).getHeight() * dstScale);
                        }
                    }
                }
            }
        }

        public System.Drawing.Color getColorKey() 
        {
            return BtnSelectKeyColor.BackColor;
        }

        public System.Drawing.Color getColorTileID()
        {
            return BtnSelectTileIDColor.BackColor;
        }

		public System.Drawing.Rectangle getMinSize()
		{
			int outW = 0;
			int outH = 0;
			for (int i = 0; i < getDstImageCount(); i++)
			{
				if (getDstImage(i) != null && !getDstImage(i).killed)
				{
					outW = Math.Max(outW, getDstImage(i).x + getDstImage(i).getWidth());
					outH = Math.Max(outH, getDstImage(i).y + getDstImage(i).getHeight());
				}
			}
			return new System.Drawing.Rectangle(0, 0, outW, outH);
		}


        // src edit
        private void toolStripTextBox1_Leave(object sender, EventArgs e)
        {
            if (Cell.Util.stringIsDigit(toolStripTextBox1.Text, 0, toolStripTextBox1.Text.Length) >= toolStripTextBox1.Text.Length &&
                Cell.Util.stringDigitToInt(toolStripTextBox1.Text, 0, toolStripTextBox1.Text.Length) >= 1)
            {
                CellW = Cell.Util.stringDigitToInt(toolStripTextBox1.Text, 0, toolStripTextBox1.Text.Length);
            }
            else
            {
                MessageBox.Show("只能输入大于0的数字！");
                toolStripTextBox1.Focus();
            }
        }
        private void toolStripTextBox2_Leave(object sender, EventArgs e)
        {
            if (Cell.Util.stringIsDigit(toolStripTextBox2.Text, 0, toolStripTextBox2.Text.Length) >= toolStripTextBox2.Text.Length &&
              Cell.Util.stringDigitToInt(toolStripTextBox2.Text, 0, toolStripTextBox2.Text.Length) >= 1)
            {
                CellH = Cell.Util.stringDigitToInt(toolStripTextBox2.Text, 0, toolStripTextBox2.Text.Length);
            }
            else
            {
                MessageBox.Show("只能输入大于0的数字！");
                toolStripTextBox2.Focus();
            }

        }
        
        private void toolStripButton1_Click(object sender, EventArgs e)
        {
           setSrcImage();
        }
        private void toolStripButton6_Click(object sender, EventArgs e)
        {
            ArrayList added = addDirImages();

			ImageProcessDialog ipd = new ImageProcessDialog(this, added, true);

			if (ipd.ShowDialog() == DialogResult.OK)
			{
				changeImage();

				pictureBox2.Refresh();
			}
        }
        private void toolStripButton2_CheckedChanged(object sender, EventArgs e)
        {
            if (toolStripButton2.Checked)
            {
                toolStripButton3.Checked = false; 
                toolStripRightSize.Checked = false;
            }

            refreshSrcRect();
            srcRectIsDown = false;
            pictureBox1.Refresh();
        }
        private void toolStripButton3_CheckedChanged(object sender, EventArgs e)
        {
            if (toolStripButton3.Checked)
            {
                toolStripButton2.Checked = false;
                toolStripRightSize.Checked = false;
            }
            refreshSrcRect();
            srcRectIsDown = false;
            pictureBox1.Refresh();
        }

        private void toolStripRightSize_CheckedChanged(object sender, EventArgs e)
        {
            if (toolStripRightSize.Checked)
            {
                toolStripButton2.Checked = false;
                toolStripButton3.Checked = false;
            }
            refreshSrcRect();
            srcRectIsDown = false;
            pictureBox1.Refresh();
        }

        private void toolStripRightSize_Click(object sender, EventArgs e)
        {

        }

        private void toolStripButton2_Click(object sender, EventArgs e)
        {

        }

        private void toolStripButton3_Click(object sender, EventArgs e)
        {

        }

        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            if (toolStripButton2.Checked || toolStripRightSize.Checked)
            {
                addDstImage();
            }
            else if (toolStripButton3.Checked)
            {
                addDstImages();
            }
            //pictureBox2.Refresh();
        }

        private void btnSrcSelectAll_Click(object sender, EventArgs e)
        {
            srcPX = 0;
            srcPY = 0;
            srcQX = srcImage.getWidth();
            srcQY = srcImage.getHeight();
            
            refreshSrcRect();
            srcRectIsDown = false;
            pictureBox1.Refresh();
        }

        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            //MapRegion.Image = new System.Drawing.Bitmap(MapRegion.Width, MapRegion.Height);
            //System.Drawing.Graphics dg = System.Drawing.Graphics.FromImage(MapRegion.Image);
            System.Drawing.Graphics dg = e.Graphics;
            Graphics g = new Graphics(dg);
            renderSrcImage(g, 0, 0);


            //System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff));
           // System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0x80, 0x80, 0x80)).Brush;
          
            g.setColor(0xFF, 0, 0, 0);
            if (btnIsShowGrid.Checked)
            {
                g.setColor(0x7f, 0xff, 0xff, 0xff);
                for (int x = 0; x < pictureBox1.Width; x += CellW * srcSize)
                {
                    g.drawLine(x , 0, x , pictureBox1.Height);
                }
                for (int y = 0; y < pictureBox1.Height; y += CellH * srcSize)
                {
                    g.drawLine(0, y , pictureBox1.Width, y );
                }
            }

            if (toolStripButton2.Checked || toolStripRightSize.Checked)
            {
                g.setColor(0x20, 0xff, 0xff, 0xff);
                dg.FillRectangle(g.pen.Brush, 
                    srcRect.X * srcSize, srcRect.Y * srcSize, (srcRect.Width) * srcSize, (srcRect.Height) * srcSize);
            }
            else if (toolStripButton3.Checked)
            {
                g.setColor(0x20, 0xff, 0xff, 0xff);
                dg.FillRectangle(g.pen.Brush, 
                    srcRect.X * srcSize, srcRect.Y * srcSize, (srcRect.Width) * srcSize, (srcRect.Height) * srcSize);
                for (int x = srcRect.X; x < srcRect.X + srcRect.Width; x += CellW)
                {
                    dg.DrawLine(System.Drawing.Pens.White, 
                        x * srcSize, srcRect.Y * srcSize, x * srcSize, srcRect.Y * srcSize + (srcRect.Height) * srcSize );
                }
                for (int y = srcRect.Y; y < srcRect.Y + srcRect.Height; y += CellH)
                {
                    dg.DrawLine(System.Drawing.Pens.White, 
                        srcRect.X * srcSize, y * srcSize, srcRect.X * srcSize + (srcRect.Width ) * srcSize, y * srcSize);
                }
            }

            g.setColor(0xff, 0x80, 0xff, 0x80);
            g.drawLine(srcPX * srcSize, 0, srcPX * srcSize, pictureBox1.Height);
            g.drawLine(0, srcPY * srcSize, pictureBox1.Width, srcPY * srcSize);

            g.setColor(0xff, 0x80, 0x80, 0xFF);
            g.drawLine(srcQX * srcSize, 0, srcQX * srcSize, pictureBox1.Height);
            g.drawLine(0, srcQY * srcSize, pictureBox1.Width, srcQY * srcSize);

        }

        
        private void pictureBox1_MouseLeave(object sender, EventArgs e)
        {
        }

        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            toolStripStatusLabel3.Text =
                        " : X=" + e.X / srcSize +
                        " : Y=" + e.Y / srcSize +
                        " : 宽=" + pictureBox1.Width / srcSize +
                        " : 高=" + pictureBox1.Height / srcSize 
                    ;


            if (toolStripButton2.Checked || toolStripButton3.Checked || toolStripRightSize.Checked)
            {
                if (srcRectIsDown)
                {
                    srcQX = (e.X < 0 ? 0 : (e.X > pictureBox1.Width ? pictureBox1.Width : e.X)) / srcSize;
                    srcQY = (e.Y < 0 ? 0 : (e.Y > pictureBox1.Height ? pictureBox1.Height : e.Y)) / srcSize;
                }
                refreshSrcRect();
            }

            pictureBox1.Refresh();
        }
        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            textFocus.Focus();

            srcPX = e.X / srcSize;
            srcPY = e.Y / srcSize;
            srcQX = e.X / srcSize;
            srcQY = e.Y / srcSize;

            if (toolStripButton2.Checked || toolStripButton3.Checked || toolStripRightSize.Checked)
            {
                srcRect.X = srcPX;
                srcRect.Width = 0;
                srcRect.Y = srcPY;
                srcRect.Height = 0;

                refreshSrcRect();
            }


            srcRectIsDown = true;
            pictureBox1.Refresh();


        }
        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {

            srcQX = e.X / srcSize;
            srcQY = e.Y / srcSize;

            refreshSrcRect();

            srcRectIsDown = false;
            pictureBox1.Refresh();
        }


        private void textFocus_KeyDown(object sender, KeyEventArgs e)
        {
            if (srcRect.Width != 0 && srcRect.Height != 0)
            {
                if (e.Shift)
                {
                    switch (e.KeyCode)
                    {
                        case Keys.Up:
                            srcQY -= 1; break;
                        case Keys.Down:
                            srcQY += 1; break;
                        case Keys.Left:
                            srcQX -= 1; break;
                        case Keys.Right:
                            srcQX += 1; break;
                    }
                } 
                else 
                {
                    switch (e.KeyCode)
                    {
                        case Keys.Up:
                            srcPY -= 1; break;
                        case Keys.Down:
                            srcPY += 1; break;
                        case Keys.Left:
                            srcPX -= 1; break;
                        case Keys.Right:
                            srcPX += 1; break;
                    }
                }
               
                refreshSrcRect();
                pictureBox1.Refresh();
            }
        }

        private void statusStrip1_KeyDown(object sender, KeyEventArgs e)
        {
           

        }

        private void refreshSrcRect()
        {
            srcPY = Math.Max(0, srcPY);
            srcPX = Math.Max(0, srcPX);
            srcPY = Math.Min(pictureBox1.Width, srcPY);
            srcPX = Math.Min(pictureBox1.Height , srcPX);

            Image dstImage = getSelectedImage();
            if (toolStripRightSize.Checked && dstImage != null)
            {
                srcQX = srcPX + dstImage.getWidth();
                srcQY = srcPY + dstImage.getHeight();
            }
            else
            {
                srcQY = Math.Max(0, srcQY);
                srcQX = Math.Max(0, srcQX);
                srcQY = Math.Min(pictureBox1.Width, srcQY);
                srcQX = Math.Min(pictureBox1.Height, srcQX);
            }

            srcRect.X = Math.Min(srcPX, srcQX);
            srcRect.Width = Math.Abs(srcPX - srcQX);
            srcRect.Y = Math.Min(srcPY, srcQY);
            srcRect.Height = Math.Abs(srcPY - srcQY);

            toolStripStatusLabel2.Text =
                " : 选择宽=" + srcRect.Width +
                " : 选择高=" + srcRect.Height;

            if (toolStripButton3.Checked)
            {
                toolStripStatusLabel2.Text +=
                " : 横向数量=" + srcRect.Width / CellW +
                " : 纵向数量=" + srcRect.Height / CellH
                ;
            }
        }

        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            srcSize += 1;
            if (srcSize > 8) srcSize = 8;

            try
            {
                if (getSrcImage() != null)
                {
                    pictureBox1.Width = getSrcImage().getWidth() * srcSize;
                    pictureBox1.Height = getSrcImage().getHeight() * srcSize;
                    pictureBox1.Refresh();
                }
            }catch(Exception err){}

        }
        private void toolStripButton8_Click(object sender, EventArgs e)
        {

            srcSize -= 1;
            if (srcSize < 1) srcSize = 1;

            try
            {
                if (getSrcImage() != null)
                {
                    pictureBox1.Width = getSrcImage().getWidth() * srcSize;
                    pictureBox1.Height = getSrcImage().getHeight() * srcSize;
                    pictureBox1.Refresh();
                }
            }
            catch (Exception err) { }
        }
        // dst edit

        Image dstSelected = null;
        int dstSelectIndex = -1;
        System.Drawing.Rectangle dstRect = new System.Drawing.Rectangle();

        bool dstDown = false;

        int dstPX;
        int dstPY;
        int dstSX;
        int dstSY;

        int dstScale = 1;



        System.Drawing.Rectangle ScopeRect = new System.Drawing.Rectangle();
        Boolean IsScopeSelected = false;
        int ScopePX;
        int ScopePY;

        // scope
        private void toolStripButton5_Click(object sender, EventArgs e)
        {
            dstScale += 1;
            if (dstScale > 8) dstScale = 8;

            try
            {
                int outW = 0;
                int outH = 0;
                for (int i = 0; i < getDstImageCount(); i++)
                {
                    if (getDstImage(i) != null && !getDstImage(i).killed)
                    {
                        outW = Math.Max(outW, getDstImage(i).x + getDstImage(i).getWidth());
                        outH = Math.Max(outH, getDstImage(i).y + getDstImage(i).getHeight());
                    }
                }

                pictureBox2.Width = outW * dstScale;
                pictureBox2.Height = outH * dstScale;
                pictureBox2.Refresh();
            }
            catch (Exception err)
            {
            }

        }

        private void toolStripButton11_Click(object sender, EventArgs e)
        {

            dstScale -= 1;
            if (dstScale < 1) dstScale = 1;

            try
            {
                System.Drawing.Rectangle ms = getMinSize();

				pictureBox2.Width = ms.Width * dstScale;
				pictureBox2.Height = ms.Height * dstScale;
                pictureBox2.Refresh();
            }
            catch (Exception err)
            {
            }
        }
        // change image
        private void toolStripButton9_Click(object sender, EventArgs e)
        {
            try
            {
                changeDstImageFromDir(dstSelectIndex);
                dstRect.X = getDstImage(dstSelectIndex).x;
                dstRect.Y = getDstImage(dstSelectIndex).y;
                dstRect.Width = getDstImage(dstSelectIndex).getWidth();
                dstRect.Height = getDstImage(dstSelectIndex).getHeight();
                pictureBox2.Refresh();
            }
            catch (Exception err) { }
        }
        private void toolStripButton14_Click(object sender, EventArgs e)
        {
            try
            {
                changeDstImageFormSrc(dstSelectIndex);
                dstRect.X = getDstImage(dstSelectIndex).x;
                dstRect.Y = getDstImage(dstSelectIndex).y;
                dstRect.Width = getDstImage(dstSelectIndex).getWidth();
                dstRect.Height = getDstImage(dstSelectIndex).getHeight();
                pictureBox2.Refresh();
            }
            catch (Exception err) { }
        }

        //select image
        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = new Graphics(e.Graphics);

            renderDstImage(g, 0, 0);

           

            System.Drawing.Pen pen = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0xFF, 0xFF, 0xFF, 0xFF));
            System.Drawing.Brush brush = new System.Drawing.Pen(System.Drawing.Color.FromArgb(0x80, 0xff, 0xff, 0xff)).Brush;

            if (dstRect != null)
            {
                e.Graphics.FillRectangle(brush, dstRect.X * dstScale, dstRect.Y * dstScale, (dstRect.Width) * dstScale, (dstRect.Height) * dstScale);
                e.Graphics.DrawRectangle(pen, dstRect.X * dstScale, dstRect.Y * dstScale, (dstRect.Width) * dstScale, (dstRect.Height) * dstScale);
            }

            if (ScopeRect != null)
            {
                pen.Color = System.Drawing.Color.FromArgb(0xff, 0, 0xff, 0);
                e.Graphics.DrawRectangle(pen, ScopeRect.X * dstScale, ScopeRect.Y * dstScale, (ScopeRect.Width) * dstScale, (ScopeRect.Height) * dstScale);
            }
            
        }


        private void pictureBox2_MouseDown(object sender, MouseEventArgs e)
        {
            int mouseX = e.X;
            int mouseY = e.Y;

            dstDown = true;

            if (multiSelect.Checked)
            {
                if (e.Button == MouseButtons.Left)
                {
                    dstRect.X = -1;
                    dstRect.Y = -1;
                    dstRect.Width = 1;
                    dstRect.Height = 1;

                    dstSelected = null;
                    dstSelectIndex = -1;

                    if (Control.ModifierKeys == Keys.Shift || Control.ModifierKeys == Keys.Control)
                    {
                        dstPX = mouseX / dstScale;
                        dstPY = mouseY / dstScale;
                    }
                    else 
                    {
                        dstSX = mouseX / dstScale;
                        dstSY = mouseY / dstScale;
                        dstPX = dstSX;
                        dstPY = dstSY;

                        if (ScopeRect.Contains(dstSX, dstSY))
                        {
                            ScopePX = dstSX - ScopeRect.X;
                            ScopePY = dstSY - ScopeRect.Y;

                            IsScopeSelected = true;
                        }
                        else
                        {
                            IsScopeSelected = false;

                            ScopeRect.X = dstSX;
                            ScopeRect.Y = dstSY;
                            ScopeRect.Width = 1;
                            ScopeRect.Height = 1;

                            System.Drawing.Rectangle dst = new System.Drawing.Rectangle(0, 0, 1, 1);

                            for (int i = 0; i < getDstImageCount(); i++)
                            {
                                if (getDstImage(i) != null)
                                {
                                    dst.X = getDstImage(i).x;
                                    dst.Y = getDstImage(i).y;
                                    dst.Width = getDstImage(i).getWidth();
                                    dst.Height = getDstImage(i).getHeight();

                                    getDstImage(i).selected = ScopeRect.IntersectsWith(dst);

                                }
                            }
                        }
                    }
                   
                }
            }
            else
            {

                ScopeRect.X = -1;
                ScopeRect.Y = -1;
                ScopeRect.Width = 1;
                ScopeRect.Height = 1;

                dstSelected = null;
                dstSelectIndex = -1;

                dstRect.X = -1;
                dstRect.Y = -1;
                dstRect.Width = 1;
                dstRect.Height = 1;

                dstPX = mouseX / dstScale;
                dstPY = mouseY / dstScale;

                System.Drawing.Rectangle dst = new System.Drawing.Rectangle();
                for (int i = 0; i < getDstImageCount(); i++)
                {
                    Image dstimg = getDstImage(i);
                    if (dstimg != null && dstimg.killed == false)
                    {
                        dst.X = dstimg.x;
                        dst.Y = dstimg.y;
                        dst.Width = dstimg.getWidth();
                        dst.Height = dstimg.getHeight();

                        if (dst.Contains(dstPX, dstPY))
                        {
                            dstDown = true;
                            dstSelected = getDstImage(i);
                            dstSelectIndex = i;
                            dstRect = dst;

                            System.Drawing.Color color = dstimg.getPixel(
                                dstPX - dstimg.x, 
                                dstPY - dstimg.y);

                            toolStripStatusLabel1.Text =
                                "目标Tile：[" + dstSelectIndex + "]" +
                                " X=" + dstSelected.x +
                                " Y=" + dstSelected.y +
                                " W=" + dstSelected.getWidth() +
                                " H=" + dstSelected.getHeight() +
								" Key=\"" + ((String)dstDataKeys[dstSelectIndex]) + "\"" +
									   " CW=" + pictureBox2.Width +
									   " CH=" + pictureBox2.Height
                                ;
                            toolStripColor.BackColor = color;
                            toolStripColor.Text ="当前像素颜色=\"" + color + "\"";

                            if (e.Button == MouseButtons.Left)
                            {
                            }
                            else if (e.Button == MouseButtons.Right)
                            {
                                clipMenu.Show(pictureBox2, e.X, e.Y);
                            }

                            break;
                        }
                    }
                }
            }

            pictureBox2.Refresh();
            
            
        }
        
        private void pictureBox2_MouseUp(object sender, MouseEventArgs e)
        {
           
            dstDown = false;
            dstPX = e.X / dstScale;
            dstPY = e.Y / dstScale;

            pictureBox2.Refresh();
        }

        private void pictureBox2_MouseMove(object sender, MouseEventArgs e)
        {
            if (dstDown)
            {
                if (multiSelect.Checked)
                {
                    if (IsScopeSelected)
                    {
                        int px = (e.X / dstScale - dstPX);
                        int py = (e.Y / dstScale - dstPY);
                        dstPX = e.X / dstScale;
                        dstPY = e.Y / dstScale;

                        //Console.WriteLine(" px="+px+" py="+py);

                        if (moveDstImages(px, 0))
                        {
                            ScopeRect.X += px;
                        }
                        if (moveDstImages(0, py))
                        {
                            ScopeRect.Y += py;
                        }
                    }
                    else
                    {
                        dstPX = e.X / dstScale;
                        dstPY = e.Y / dstScale;

                        ScopeRect.X = Math.Min(dstSX, dstPX);
                        ScopeRect.Y = Math.Min(dstSY, dstPY);
                        ScopeRect.Width = (dstSX - dstPX == 0) ? 1 : Math.Abs(dstSX - dstPX);
                        ScopeRect.Height = (dstSY - dstPY == 0) ? 1 : Math.Abs(dstSY - dstPY);

                        System.Drawing.Rectangle dst = new System.Drawing.Rectangle(0, 0, 1, 1);

                        for (int i = 0; i < getDstImageCount(); i++)
                        {
                            if (getDstImage(i) != null)
                            {
                                dst.X = getDstImage(i).x;
                                dst.Y = getDstImage(i).y;
                                dst.Width = getDstImage(i).getWidth();
                                dst.Height = getDstImage(i).getHeight();

                                getDstImage(i).selected = ScopeRect.IntersectsWith(dst);
                            }
                        }
                    }
                 
                }
                else
                {
                    if (dstSelectIndex >= 0)
                    {
                        int px = (e.X / dstScale - dstPX);
                        int py = (e.Y / dstScale - dstPY);
                        dstPX = e.X / dstScale;
                        dstPY = e.Y / dstScale;

                        if (moveDstImage(dstSelectIndex, px, 0))
                        {
                            dstRect.X += px;
                        }
                        if (moveDstImage(dstSelectIndex, 0, py))
                        {
                            dstRect.Y += py;
                        }
                      
                        toolStripStatusLabel1.Text =
                                       "目标Tile：[" + dstSelectIndex + "]" +
                                       " X=" + dstSelected.x +
                                       " Y=" + dstSelected.y +
                                       " W=" + dstSelected.getWidth() +
                                       " H=" + dstSelected.getHeight() +
                                       " Key=\"" + ((String)dstDataKeys[dstSelectIndex]) + "\"" +
                                       " CW=" + pictureBox2.Width + 
                                       " CH=" + pictureBox2.Height
                                       ;
                    }
                }
                pictureBox2.Refresh();
            }
            
        }

        // del image
        private void toolStripButton12_Click(object sender, EventArgs e)
        {
            try
            {
                if (MessageBox.Show("警告", "确认删除？", MessageBoxButtons.OKCancel)== DialogResult.OK)
                {
                    if (multiSelect.Checked)
                    {
                        for (int i = 0; i < getDstImageCount(); i++)
                        {
                            if (getDstImage(i) != null && getDstImage(i).selected)
                            {
                                delDstImage(i);
                            }
                        }
                    }
                    else
                    {
                        delDstImage(dstSelectIndex);
                    }
                    dstRect.X = -1;
                    dstRect.Y = -1;
                    dstRect.Width = 1;
                    dstRect.Height = 1;
                    dstSelectIndex = -1;
                    pictureBox2.Refresh();
                }
            }
            catch (Exception err) { }
        }

		private void 全选ToolStripMenuItem_Click(object sender, EventArgs e)
		{
			ScopeRect.X = 0;
			ScopeRect.Y = 0;
			ScopeRect.Width = pictureBox2.Width;
			ScopeRect.Height = pictureBox2.Height;

			for (int i = 0; i < getDstImageCount(); i++)
			{
				if (getDstImage(i) != null)
				{
					getDstImage(i).selected = true;
				}
			}

			pictureBox2.Refresh();
		}

        private void 从目录替换ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton9_Click(null,null);
        }

        private void 从左边替换ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton14_Click(null,null);
        }

        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            toolStripButton12_Click(null,null);
        }

        private void 编辑ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                String name = (String)dstDataKeys[dstSelectIndex];
                TextDialog nameDialog = new TextDialog(name);
                if (nameDialog.ShowDialog() == DialogResult.OK)
                {
                    dstDataKeys[dstSelectIndex] = nameDialog.getText();
                    pictureBox2.Refresh();
                }
            }
            catch (Exception err) { }
        }

        // 清理透明色
        private void 清理透明色ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ImageProcessDialog ipd = new ImageProcessDialog(this, dstImages, false);

            if (ipd.ShowDialog() == DialogResult.OK)
            {
                changeImage();

                pictureBox2.Refresh();
            }
        }



        //-------------------------------------------------------------------------------------------------------------------------------------
        
        public MapForm createMapForm(String name)
        {
            if (dstImages.Count < 1)
            {
                MessageBox.Show("Tile容量不能为0！");
                return null;
            }
           
            //for (int i = 0; i < dstImages.Count; i++)
            //{
            //    if (((Image)dstImages[i]) == null || ((Image)dstImages[i]).killed) continue;
            //    if (((Image)dstImages[i]).getWidth() != CellW ||
            //        ((Image)dstImages[i]).getHeight() != CellH)
            //    {
            //        MessageBox.Show("地图的Tile大小必须相等！");
            //        return null;
            //    }
            //}

            MapForm ret = new MapForm(name,CellW, CellH, this);
            return ret;
        }

        public SpriteForm createSpriteForm(String name)
        {
//             if (dstImages.Count < 1)
//             {
//                 MessageBox.Show("Tile容量不能为0！");
//                 return null;
//             }
            for (int i = 0; i < dstImages.Count; i++)
            {
                if (((Image)dstImages[i]) == null) continue;
                //if (((Image)dstImages[i]).getWidth() != CellW ||
                //    ((Image)dstImages[i]).getHeight() != CellH)
                //{
                //    MessageBox.Show("地图的Tile大小必须相等！");
                //    return null;
                //}
            }

            SpriteForm ret = new SpriteForm(name,this);
            return ret;
        }

        //change color
        private void toolStripButton10_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = true;
            MyDialog.ShowHelp = true;
            MyDialog.Color = pictureBox1.BackColor;
            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                pictureBox2.BackColor = MyDialog.Color;
                pictureBox1.BackColor = MyDialog.Color;
                toolStripButton10.BackColor = MyDialog.Color;
            }
        }
        private void BtnSelectKeyColor_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = true;
            MyDialog.ShowHelp = true;
            MyDialog.Color = BtnSelectKeyColor.BackColor;
            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                BtnSelectKeyColor.BackColor = MyDialog.Color;
            }
        }
        private void BtnSelectTileIDColor_Click(object sender, EventArgs e)
        {
            ColorDialog MyDialog = new ColorDialog();
            MyDialog.AllowFullOpen = true;
            MyDialog.ShowHelp = true;
            MyDialog.Color = BtnSelectTileIDColor.BackColor;
            if (MyDialog.ShowDialog() == DialogResult.OK)
            {
                BtnSelectTileIDColor.BackColor = MyDialog.Color;
            }
        }

        //
        private void toolStripButton13_Click(object sender, EventArgs e)
        {
            pictureBox2.Refresh();
        }

        private void ImagesForm_TextChanged(object sender, EventArgs e)
        {
            this.id = this.Text;
        }

        private void toolStripButton15_Click(object sender, EventArgs e)
        {
            pictureBox2.Refresh();
        }
        private void toolStripButton15_Click_1(object sender, EventArgs e)
        {
            pictureBox2.Refresh();
        }
        private void 添加切片ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (toolStripButton2.Checked)
            {
                addDstImage();
            }
            if (toolStripButton3.Checked)
            {
                addDstImages();
            }
        }

        private void 添加多张图片ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            addDirImages();
        }

        private void toolStripButton16_Click(object sender, EventArgs e)
        {
            pictureBox1.Refresh();
        }

        private void 导出图片ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
               // String name = (String)dstDataKeys[dstSelectIndex];
                Image image = (Image)dstImages[dstSelectIndex];
                //TextDialog nameDialog = new TextDialog(name);
                //if (nameDialog.ShowDialog() == DialogResult.OK)
                //{
                //    dstDataKeys[dstSelectIndex] = nameDialog.getText();
                //    pictureBox2.Refresh();
                //}
                SaveFileDialog sfd = new SaveFileDialog();
                sfd.DefaultExt = ".png";
                sfd.AddExtension = true;
                sfd.Filter = "PNG file (*.png)|*.png";
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    image.getDImage().Save(sfd.FileName, System.Drawing.Imaging.ImageFormat.Png);
                }
            }
            catch (Exception err) { }
        }


        private void panel5_Paint(object sender, PaintEventArgs e)
        {

        }

        private Boolean moveImage(Image srcImage, System.Drawing.Rectangle region, int dx, int dy)
        {
            System.Drawing.Rectangle srcRect = new System.Drawing.Rectangle(
                srcImage.x+dx, srcImage.y+dy, srcImage.getWidth(), srcImage.getHeight()
                );
            System.Drawing.Rectangle dstRect = new System.Drawing.Rectangle();

            if (!region.Contains(srcRect)) 
            {
                return false;
            }

            for (int i = dstImages.Count-1; i >=0; --i)
            {
                int di = Util.cycNum(i, srcImage.indexOfImages, dstImages.Count);
                Image dstimg = (Image)dstImages[di];
                if (dstimg != null)
                {
                    if (!dstimg.killed && dstimg != srcImage)
                    {
                        dstRect.X = dstimg.x;
                        dstRect.Y = dstimg.y; 
                        dstRect.Width = dstimg.getWidth(); 
                        dstRect.Height = dstimg.getHeight();

                        if (srcRect.IntersectsWith(dstRect))
                        {
                            return false;
                        }
                    }
                }
            }

            srcImage.x += dx;
            srcImage.y += dy;

            return true;
        }

        private void moveAllImages(int dx, int dy)
        {
            if (dx == 0 && dy == 0) return;

            System.Drawing.Rectangle region = new System.Drawing.Rectangle(
                0, 
                0, 
                pictureBox2.Width, 
                pictureBox2.Height);

            while (true)
            {
                Boolean ismoved = false;

                for (int i = 0; i < dstImages.Count; i++)
                {
                    Image img = (Image)dstImages[i];
                    if (img != null && !img.killed)
                    {
                        if (moveImage(img, region, dx * img.getWidth(), dy * img.getHeight()))
                        {
                            ismoved = true;
                        }
                        else if (moveImage(img, region, dx, dy))
                        {
                            ismoved = true;
                        }
                    }
                }
                if (!ismoved)
                {
                    break;
                }
            }
        }

        private void btnUpAllImage_Click(object sender, EventArgs e)
        {
            moveAllImages(0, -1);

            pictureBox2.Refresh();
        }

        private void btnLeftAllImage_Click(object sender, EventArgs e)
        {
            moveAllImages(-1, 0);

            pictureBox2.Refresh();

        }


        //------------------------------------------------------------------------------------------------
        // custom output menu

        private void nAToolStripMenuItem_Click(object sender, EventArgs e)
        {
            chk_output_group.Enabled = !chk_custom_output.Checked;
            chk_output_tile.Enabled = !chk_custom_output.Checked;
        }

        private void output_type_click(object sender, EventArgs e)
        {
        }

        private void output_type_changed(object sender, EventArgs e)
        {

        }

        private void chk_custom_filetype_Click(object sender, EventArgs e)
        {

            chk_output_file_bmp.Enabled = !chk_custom_filetype.Checked;
            chk_output_file_jpg.Enabled = !chk_custom_filetype.Checked;
            chk_output_file_png.Enabled = !chk_custom_filetype.Checked;
        }

        private void chk_output_file_png_Click(object sender, EventArgs e)
        {
            chk_output_file_bmp.Checked = sender == chk_output_file_bmp;
            chk_output_file_jpg.Checked = sender == chk_output_file_jpg;
            chk_output_file_png.Checked = sender == chk_output_file_png;
        }

        //------------------------------------------------------------------------------------------------

        class ImagesCompareY : IComparer
        {

            public int Compare(Object a, Object b)
            { 
                if (a!=null && b!=null)
                {
                    return ((Image)a).y - ((Image)b).y;
                }
                return -1;
            }
        }

		private void 附加数据ToolStripMenuItem_Click(object sender, EventArgs e)
		{
			StringBuilder sb = new StringBuilder(this.append_data);

			DataEdit dataedit = new DataEdit(sb);

			dataedit.ShowDialog(this);

			this.append_data = sb.ToString();
		}

		private void btnChangeDstToolStripMenuItem_Click(object sender, EventArgs e)
		{

			try
			{
				OpenFileDialog openFileDialog1 = new OpenFileDialog();
				openFileDialog1.Filter = "PNG files (*.png)|*.png|All files (*.*)|*.*";
				openFileDialog1.InitialDirectory = System.IO.Path.GetDirectoryName(Config.Default.LastImageOpenDir);
				
				if (openFileDialog1.ShowDialog() == DialogResult.OK)
				{
					is_change_image = true;
					
					Image changed = Image.createImage(openFileDialog1.FileName);
					System.Drawing.Rectangle ms = getMinSize();
					if (changed.getWidth() < ms.Width || changed.getHeight() < ms.Height)
					{
						MessageBox.Show("图片太小，不能覆盖所有! ");
						return;
					}

					for (int i = 0; i < dstImages.Count; i++)
					{
						Image img = (Image)dstImages[i];
						if (!img.killed)
						{
							Image clip = changed.subImage(img.x, img.y, img.getWidth(), img.getHeight());
							if (clip != null)
							{
								img.changeDimg(clip.dimg);
							}
						}
					}
				}
			}
			catch (Exception err)
			{
				Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
				MessageBox.Show(err.Message);
			}


		}

		private void btnOutputDstToolStripMenuItem_Click(object sender, EventArgs e)
		{
			try
			{
				SaveFileDialog sfd = new SaveFileDialog();
				sfd.DefaultExt = ".png";
				sfd.AddExtension = true;
				sfd.Filter = "PNG file (*.png)|*.png";
				if (sfd.ShowDialog() == DialogResult.OK)
				{
					saveAllAsGrop(sfd.FileName, System.Drawing.Imaging.ImageFormat.Png);
				}
			}
			catch (Exception err) { }
        }

        private void toolImageComvert_Click(object sender, EventArgs e)
        {
            StringBuilder sb = new StringBuilder(this.image_convert_script_file);

            DataEdit dataedit = new DataEdit(sb);
            dataedit.Text = "图片转换脚本";
            dataedit.ShowDialog(this);

            this.image_convert_script_file = sb.ToString();
        }

        private void comboImageConvert_DropDown(object sender, EventArgs e)
        {
            comboImageConvert.Items.Clear();
            String[] scriptFiles = Form1.getImageConvertScriptList();
            comboImageConvert.Items.AddRange(scriptFiles);
        }

        private void comboImageConvert_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {
                image_convert_script_file = comboImageConvert.Text;
            }
            catch (Exception err)
            {
            }
        }

        private void comboImageConvert_TextUpdate(object sender, EventArgs e)
        {
            try
            {
                image_convert_script_file = comboImageConvert.Text;
            }
            catch (Exception err)
            {
            }
        }


  



    

       





    }
}