using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Xml;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Runtime.Serialization.Formatters;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization.Formatters.Soap;


namespace CellGameEdit.PM
{

    [Serializable]
    public partial class ProjectForm : Form , ISerializable
    {
        private static ProjectForm curInstance;
        static public ProjectForm getInstance()
        {
            return curInstance;
        }
        static public Boolean is_console = false;

        static public String workSpace = "";
        static public String workName = "";

        static public String getEnumsDir()
        {
            return workSpace + @"\enums";
        }

        static public Boolean IsCopy = false;/*此状态不序列化子对象*/

        static public Boolean IsOutEncodingInfo = true;

        TreeNode nodeReses;
        TreeNode nodeLevels;
        TreeNode nodeObjects;
        TreeNode nodeCommands;

        //ArrayList formGroup;
        Hashtable formTable;

        private FormEventTemplate form_event_templates;

        //-----------------------------------------------------------------------------------------------------------------------------------
        // new 
        public ProjectForm()
        {
            curInstance = this;

            InitializeComponent();

           // formGroup = new ArrayList();
            formTable       = new Hashtable();

            nodeReses       = new TreeNode("资源");
            nodeObjects     = new TreeNode("对象");
            nodeLevels      = new TreeNode("场景");
            nodeCommands    = new TreeNode("命令");

            nodeReses.ContextMenuStrip = this.resMenu;
            nodeObjects.ContextMenuStrip = this.objMenu;
            nodeLevels.ContextMenuStrip = this.levelMenu;
            nodeCommands.ContextMenuStrip = this.commandMenu;

            treeView1.Nodes.Add(nodeReses);
            treeView1.Nodes.Add(nodeObjects);
            treeView1.Nodes.Add(nodeLevels);
            treeView1.Nodes.Add(nodeCommands);

            //treeView1.ExpandAll();

            //Util.checkWildcard("1234", "1234");
            //Util.checkWildcard("123", "1234");
            //Util.checkWildcard("1234", "123");
            //Util.checkWildcard("4321", "1234");
            //Util.checkWildcard("A?56", "A456");
            //Util.checkWildcard("A?5?0", "A4530");
            //Util.checkWildcard("A*", "A456");
            //Util.checkWildcard("A*5", "A456");
            //Util.checkWildcard("A*56", "A456");
            //Util.checkWildcard("A*5?7", "A4567");
            //Util.checkWildcard("A*5*", "A42543");
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected ProjectForm(SerializationInfo info, StreamingContext context)
        {
            curInstance = this;

            InitializeComponent();

            try
            {
                nodeReses = (TreeNode)info.GetValue("nodeReses", typeof(TreeNode));
                nodeLevels = (TreeNode)info.GetValue("nodeLevels", typeof(TreeNode));

                try{
					nodeObjects = (TreeNode)info.GetValue("nodeObjects", typeof(TreeNode));}
                catch (Exception err){nodeObjects = new TreeNode("对象");}

                try{
					nodeCommands = (TreeNode)info.GetValue("nodeCommands", typeof(TreeNode));}
                catch (Exception err){nodeCommands = new TreeNode("命令");}

                //formGroup = (ArrayList)info.GetValue("formGroup", typeof(ArrayList));

                formTable = (Hashtable)info.GetValue("formTable", typeof(Hashtable));

                nodeReses.ContextMenuStrip = this.resMenu;
                nodeObjects.ContextMenuStrip = this.objMenu;
                nodeLevels.ContextMenuStrip = this.levelMenu;
                nodeCommands.ContextMenuStrip = this.commandMenu;

                foreach (TreeNode node in nodeReses.Nodes)
                {
                    node.ContextMenuStrip = this.tileMenu;

                    foreach (TreeNode subnode in node.Nodes)
                    {
                        subnode.ContextMenuStrip = this.subMenu;
                    }
                }
                foreach (TreeNode node in nodeLevels.Nodes)
                {
                    node.ContextMenuStrip = this.subMenu;
                }
                foreach (TreeNode node in nodeObjects.Nodes)
                {
                    node.ContextMenuStrip = this.subMenu;
                } 
                foreach (TreeNode node in nodeCommands.Nodes)
                {
                    node.ContextMenuStrip = this.subMenu;
                }

                treeView1.Nodes.Add(nodeReses);
                treeView1.Nodes.Add(nodeObjects);
                treeView1.Nodes.Add(nodeLevels);
                treeView1.Nodes.Add(nodeCommands);

				//loadOver();
                //treeView1.ExpandAll();

            }catch(Exception err){
                MessageBox.Show("构造工程出错 !\n" + err.Message +"\n"+err.StackTrace + "  at  " +err.Message);
            }
        }

		private void loadOver()
		{
			ArrayList form_maps = new ArrayList();
            ArrayList form_sprites = new ArrayList();
            ArrayList form_images = new ArrayList();

			foreach (TreeNode node in nodeReses.Nodes)
			{
                Form imgf = getForm(node);

                if (imgf != null && imgf.GetType() == typeof(ImagesForm))
                {
                    form_images.Add(imgf);
                }

				foreach (TreeNode subnode in node.Nodes)
				{
					Form sf = getForm(subnode);
					if (sf.GetType() == typeof(MapForm))
					{
						form_maps.Add(sf);
						MapForm subf = ((MapForm)sf);
						subf.ChangeSuper(getImagesFormByName(subf.superName));
					}
					if (sf.GetType() == typeof(SpriteForm))
					{
						form_sprites.Add(sf);
						SpriteForm subf = ((SpriteForm)sf);
						subf.ChangeSuper(getImagesFormByName(subf.superName));
					}
				}
			}


			foreach (TreeNode node in nodeLevels.Nodes)
			{
				WorldForm wf = (WorldForm)getForm(node);
                wf.ChangeAllUnits(form_maps, form_sprites, form_images);
			}

            getEventTemplateForm();

            foreach (TreeNode node in nodeLevels.Nodes)
            {
                WorldForm wf = (WorldForm)getForm(node);
                wf.loadOver();
            }
		}

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            RefreshNodeName();

                // info.AddValue("formGroup", formGroup);
                info.AddValue("nodeReses", nodeReses);
                info.AddValue("nodeObjects", nodeObjects);
                info.AddValue("nodeLevels", nodeLevels);
                info.AddValue("nodeCommands", nodeCommands);

                info.AddValue("formTable", formTable);

        }
        
        private void ProjectForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            foreach (Form f in Application.OpenForms)
            {
                f.TopMost = false;
            }

            if (!is_console) 
            {
                if (MessageBox.Show(
                    "是否要关闭工程？",
                    "警告",
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Question,
                    MessageBoxDefaultButton.Button2
                    ) != DialogResult.OK)
                {
                    e.Cancel = true;
                }
            }

        }

        private void ProjectForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            foreach (TreeNode key in formTable.Keys)
            {

                if (getForm(key) != null)
                {
                    getForm(key).Close();
                    getForm(key).Dispose();
                }
            }

        }
        private void ProjectForm_Shown(object sender, EventArgs e)
        {
			sortTreeView(); 
			loadOver();
        }

        //public void Output()
        //{
        //    RefreshNodeName();

        //    System.IO.StringWriter sw = new System.IO.StringWriter();
        //    sw.WriteLine("/* Cell Game Editor by WAZA Zhang */");
        //    sw.WriteLine("/* Email : wazazhang@gmail.com */");

        //    sw.WriteLine("import com.morefuntek.cell.*;");
        //    sw.WriteLine("import com.morefuntek.cell.Game.*;");
        //    sw.WriteLine();

        //    sw.WriteLine("public class Reses {");
        //    Output(nodeReses, sw);
        //    sw.WriteLine("}");

        //    Output(nodeLevels, sw);

        //    Console.WriteLine(sw.ToString());

        //    System.IO.File.WriteAllText(
        //        ProjectForm.workSpace + "\\Reses.java",
        //        sw.ToString());

        //    //

        //}

        //public void Output(TreeNode node, System.IO.StringWriter sw)
        //{
        //    if (formTable[node] != null)
        //    {
        //        //
        //        if (formTable[node].GetType().Equals(typeof(ImagesForm)))
        //        {
        //            ((ImagesForm)formTable[node]).Output(sw);
        //        }
        //        if (formTable[node].GetType().Equals(typeof(MapForm)))
        //        {
        //            ((MapForm)formTable[node]).Output(sw);
        //        }
        //        if (formTable[node].GetType().Equals(typeof(SpriteForm)))
        //        {
        //            ((SpriteForm)formTable[node]).Output(sw);
        //        }

        //        //
        //        if (formTable[node].GetType().Equals(typeof(WorldForm)))
        //        {
        //            ((WorldForm)formTable[node]).Output(sw);
        //        }
        //    }

        //    if (node.Nodes.Count >= 0)
        //    {
        //        foreach (TreeNode sub in node.Nodes)
        //        {
        //            Output(sub, sw);
        //        }
        //    }

        //}

        
        string OutputName ;
        string OutputDir ;
        string OutputDirImage;

        string ImageType;			/* 输出图片格式 默认(*.png) */

        Boolean ImageTile;
        Boolean ImageTileData;

        Boolean ImageGroup;
        Boolean ImageGroupData;

        
        public void OutputCustom(String fileName)
        {
            lock (this)
            {
                try
                {

                    initForms();


                    if (System.IO.File.Exists(fileName))
                    {
                        Encoding encoding = Util.GetEncoding(fileName);

                        Util.CurEncoding = encoding;

                        Console.WriteLine("Encoding : " + encoding.EncodingName);

                        StreamReader sr = new StreamReader(fileName, encoding);
                        string script = sr.ReadToEnd();
                        sr.Close();


                        string ret = new string(new char[] { '\r', '\n' });

                        // var
                        script = Util.fillVar(script,
                            new String[] { 
                                SC.VAR_FILE_NAME,
                                SC.VAR_PATH_NAME,
                            },
                            new String[] { 
                                Path.GetFileNameWithoutExtension(workName),
                                Path.GetDirectoryName(workName),
                            }
                            );
                        



                        // build command
                        OutputName = Util.getCommandScript(script, SC.OUTPUT);
                        //OutputName = Path.GetFullPath(OutputName);
                        try
                        {
                            if (System.IO.Path.IsPathRooted(OutputName))
                            {
                                OutputDir = System.IO.Path.GetDirectoryName(OutputName);
                            }
                            else
                            {
                                OutputDir = workSpace + "\\" + System.IO.Path.GetDirectoryName(OutputName);
                            }
                        }
                        catch (Exception err)
                        {
                            OutputDir = System.IO.Path.GetDirectoryName(workSpace);
                        }
                        if (!System.IO.Directory.Exists(OutputDir))
                        {
                            System.IO.Directory.CreateDirectory(OutputDir);
                        }
                        OutputName = OutputDir + "\\" + System.IO.Path.GetFileName(OutputName);
                        OutputName = Path.GetFullPath(OutputName);

                        Console.WriteLine("OutputName : " + OutputName);

                        // out image
                        OutputDirImage = Util.getCommandScript(script, SC.IMAGE_OUTPUT);
                        if (OutputDirImage == "") OutputDirImage = null;
                        try
                        {
                            if (System.IO.Path.IsPathRooted(OutputDirImage))
                            {
                                OutputDirImage = System.IO.Path.GetDirectoryName(OutputDirImage);
                            }
                            else
                            {
                                OutputDirImage = workSpace + "\\" + System.IO.Path.GetDirectoryName(OutputDirImage);
                            }
                            if (!System.IO.Directory.Exists(OutputDirImage))
                            {
                                System.IO.Directory.CreateDirectory(OutputDirImage);
                            }
                            ImageType = Util.getCommandScript(script, SC.IMAGE_TYPE);
                            ImageTile = Util.getCommandScript(script, SC.IMAGE_TILE).Equals("true", StringComparison.CurrentCultureIgnoreCase);
                            ImageTileData = Util.getCommandScript(script, SC.IMAGE_TILE_DATA).Equals("true", StringComparison.CurrentCultureIgnoreCase);
                            ImageGroup = Util.getCommandScript(script, SC.IMAGE_GROUP).Equals("true", StringComparison.CurrentCultureIgnoreCase);
                            ImageGroupData = Util.getCommandScript(script, SC.IMAGE_GROUP_DATA).Equals("true", StringComparison.CurrentCultureIgnoreCase);
                        }
                        catch (Exception err)
                        {
                            OutputDirImage = System.IO.Path.GetDirectoryName(workSpace);
                            ImageType = "";
                            ImageTile = false;
                            ImageTileData = false;
                            ImageGroup = false;
                            ImageGroupData = false;
                        }

                        // build format
                        Util.setFormatNumberArray1D(Util.getCommandScript(script, SC.FORMAT_NUMBER_ARRAY_1D), "<>");
                        Util.setFormatStringArray1D(Util.getCommandScript(script, SC.FORMAT_STRING_ARRAY_1D), "<>");

                        Util.setFormatArray1D(Util.getCommandScript(script, SC.FORMAT_ARRAY_1D), "<>");
                        Util.setFormatArray2D(Util.getCommandScript(script, SC.FORMAT_ARRAY_2D), "<>");

                        Util.setFixedStringArray(Util.getCommandScript(script, SC.FIXED_STRING_ARRAY));
                        
                        ///////////////////////////////////////////////////////////////////
                        script = fillScriptNode(script);
                        ///////////////////////////////////////////////////////////////////

                        script = Util.replaceFuncScript(script);

                        // complete
                        if (IsOutEncodingInfo)
                        {
                            script = script.Insert(0, "/* Email : wazazhang@gmail.com */" + ret);
                            script = script.Insert(0, "/* Cell Game Editor by WAZA Zhang */" + ret);
                            script = script.Insert(0, "/* Encoding : " + encoding.EncodingName + " */" + ret);
                        }
                       

                        Console.WriteLine("");
                        Console.WriteLine(script);

                        System.IO.File.WriteAllText(
                            OutputName,
                            script,
                            encoding
                            );

                        Console.WriteLine(ret + "Output --> " + OutputName + " --> " + script.Length + "(Chars)");
                        Console.WriteLine("");
                    }
                    else
                    {
                        Console.WriteLine("Error : " + fileName + " : 不存在!");
                    }
                }
                catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " + err.Message); }
            }
        }

        ArrayList FormsImages = new ArrayList();
        ArrayList FormsMap = new ArrayList();
        ArrayList FormsSprite = new ArrayList();
        ArrayList FormsWorld = new ArrayList();
        ArrayList FormsObjects = new ArrayList();
        ArrayList FormsCommands = new ArrayList();

        public string fillScriptSub(string src, string start, string end, ArrayList forms)
        {
            string script = src.Substring(0, src.Length);
            string sub = Util.getTopTrunk(script, start, end);
            if (sub == null) return null;

            ArrayList scripts = new ArrayList();
            try
            {
                for (int i = 0; i < forms.Count; i++)
                {

                    IEditForm form = ((IEditForm)forms[i]);

                    String ignoreKey = null;
                    if (Util.testIgnore("<IGNORE>", sub, form.getID(), ref ignoreKey) == true)
                    {
                        continue;
                    }

                    String keepKey = null;
                    if (Util.testKeep("<KEEP>", sub, form.getID(), ref keepKey) == false)
                    {
                        continue;
                    }
                   


                    StringWriter output = new StringWriter();
                    //
                    if (forms[i].GetType().Equals(typeof(ImagesForm)))
                    {
                        ((ImagesForm)forms[i]).OutputCustom(i, sub, output, OutputDirImage, ImageType,ImageTile,ImageTileData,ImageGroup,ImageGroupData);
                        //Console.WriteLine("Output Images : " + ((ImagesForm)forms[i]).id + " -> " + output.ToString().Length + "(Chars)");
                    }
                    if (forms[i].GetType().Equals(typeof(MapForm)))
                    {
                        ((MapForm)forms[i]).OutputCustom(i, sub, output);
                        //Console.WriteLine("Output Map : " + ((MapForm)forms[i]).id + " -> " + output.ToString().Length + "(Chars)");
                    }
                    if (forms[i].GetType().Equals(typeof(SpriteForm)))
                    {
                        ((SpriteForm)forms[i]).OutputCustom(i, sub, output);
                        //Console.WriteLine("Output Sprite : " + ((SpriteForm)forms[i]).id + " -> " + output.ToString().Length + "(Chars)");
                    }
                    if (forms[i].GetType().Equals(typeof(WorldForm)))
                    {
                        ((WorldForm)forms[i]).OutputCustom(i, sub, output);
                        //Console.WriteLine("Output World : " + ((WorldForm)forms[i]).id + " -> " + output.ToString().Length + "(Chars)");
                    }
                    //
                    if (forms[i].GetType().Equals(typeof(CommandForm)))
                    {
                        ((CommandForm)forms[i]).OutputCustom(i, sub, output);
                        //Console.WriteLine("Output Command : " + ((CommandForm)forms[i]).id + " -> " + output.ToString().Length + "(Chars)");
                    }

                    scripts.Add(output.ToString());

                    Console.WriteLine("Output : " + form.GetType().ToString() + " : " + form.getID() + " -> " + output.ToString().Length + "(Chars)");
                }
            }
            catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " +err.Message); }

            script = Util.replaceSubTrunksScript(script, start, end, (string[])scripts.ToArray(typeof(string)));
                
            return script;
        }

        public string fillScriptNode(string src)
        {
            string script = src.Substring(0, src.Length);

#region build resource trunk
            Console.WriteLine("build resource trunk");
            try
            {
                string resource = Util.getTopTrunk(script, SC._RESOURCE, SC._END_RESOURCE);
                if (resource != null)
                {
                    bool fix = false;
                    do
                    {
                        fix = false;
                        string images = fillScriptSub(resource, SC._IMAGES, SC._END_IMAGES, FormsImages);
                        if (images != null) { resource = images; fix = true; }

                        string map = fillScriptSub(resource, SC._MAP, SC._END_MAP, FormsMap);
                        if (map != null) { resource = map; fix = true; }

                        string sprite = fillScriptSub(resource, SC._SPRITE, SC._END_SPRITE, FormsSprite);
                        if (sprite != null) { resource = sprite; fix = true; }

                    } while (fix);

                    resource = Util.replaceKeywordsScript(resource,
                        SC._RESOURCE, 
                        SC._END_RESOURCE,
                     new string[] { SC.RES_IMAGES_COUNT, SC.RES_MAP_COUNT, SC.RES_SPRITE_COUNT },
                     new string[] { FormsImages.Count.ToString(), FormsMap.Count.ToString(), FormsSprite.Count.ToString() });
                    script = Util.replaceSubTrunksScript(script, SC._RESOURCE, SC._END_RESOURCE, 
                        new string[] { resource });
            } 
            }
            catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " +err.Message); }
#endregion

#region build world trunk
            Console.WriteLine("build world trunk");
            try
            {
                string level = Util.getTopTrunk(script, SC._LEVEL, SC._END_LEVEL);
                if (level != null)
                {
                    bool fix = false;
                    do
                    {
                        fix = false;
                        string world = fillScriptSub(level, SC._WORLD, SC._END_WORLD, FormsWorld);
                        if (world != null) { level = world; fix = true; }

                    } while (fix);

                    level = Util.replaceKeywordsScript(level, SC._LEVEL, SC._END_LEVEL,
                    new string[] { SC.LEVEL_WORLD_COUNT },
                    new string[] { FormsWorld.Count.ToString() });
                    script = Util.replaceSubTrunksScript(script, SC._LEVEL, SC._END_LEVEL, new string[] { level });
            }
            }
            catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " +err.Message); }
#endregion

#region build command trunk
            Console.WriteLine("build command trunk");
            try
            {
                string command = Util.getTopTrunk(script, SC._COMMAND, SC._END_COMMAND);
                if (command != null)
                {
                    bool fix = false;
                    do
                    {
                        fix = false;
                        string table = fillScriptSub(command, SC._TABLE_GROUP, SC._END_TABLE_GROUP, FormsCommands);
                        if (table != null) { command = table; fix = true; }

                    } while (fix);

                    command = Util.replaceKeywordsScript(command, SC._COMMAND, SC._END_COMMAND,
                      new string[] { SC.COMMAND_TABLE_GROUP_COUNT },
                      new string[] { FormsCommands.Count.ToString() });
                    script = Util.replaceSubTrunksScript(script, SC._COMMAND, SC._END_COMMAND, new string[] { command });
             }
            }
            catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " + err.Message); }
#endregion

            return script;
        }

        public void lockForms()
        {
            
            lockForms(nodeReses);
            lockForms(nodeLevels);
            lockForms(nodeObjects);
            lockForms(nodeCommands);

            //this.Hide();
            this.Enabled = false;
            
        }
        private void lockForms(TreeNode node)
        {
            if (getForm(node)!=null)
            {
                getForm(node).Hide();
                getForm(node).Enabled = false;
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    lockForms(sub);
                }
            }
        }
        public void unlockForms()
        {

            unlockForms(nodeReses);
            unlockForms(nodeLevels);
            unlockForms(nodeObjects);
            unlockForms(nodeCommands);
            //this.Show();
            this.Enabled = true;
            
        }
        private void unlockForms(TreeNode node)
        {
            if (getForm(node) != null)
            {
                //getForm(node).Show();
                getForm(node).Enabled = true;
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    unlockForms(sub);
                }
            }
        }



        public void initForms()
        {
            FormsImages.Clear();
            FormsMap.Clear();
            FormsSprite.Clear();
            FormsWorld.Clear();
            FormsObjects.Clear();
            FormsCommands.Clear();

            initForms(nodeReses);
            initForms(nodeLevels);
            initForms(nodeObjects);
            initForms(nodeCommands);
        }
        public void initForms(TreeNode node)
        {
            if (formTable[node] != null)
            {
                //
                if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                {
                    FormsImages.Add(((ImagesForm)formTable[node]));
                }
                if (formTable[node].GetType().Equals(typeof(MapForm)))
                {
                    FormsMap.Add(((MapForm)formTable[node]));
                }
                if (formTable[node].GetType().Equals(typeof(SpriteForm)))
                {
                    FormsSprite.Add(((SpriteForm)formTable[node]));
                }

                //
                if (formTable[node].GetType().Equals(typeof(WorldForm)))
                {
                    FormsWorld.Add(((WorldForm)formTable[node]));
                }
                //
                if (formTable[node].GetType().Equals(typeof(ObjectForm)))
                {
                    FormsObjects.Add(((ObjectForm)formTable[node]));
                }
                //
                if (formTable[node].GetType().Equals(typeof(CommandForm)))
                {
                    FormsCommands.Add(((CommandForm)formTable[node]));
                }
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    initForms(sub);
                }
            }

        }


        public void RefreshNodeName()
        {
            RefreshNodeName(nodeReses);
            RefreshNodeName(nodeLevels);
            RefreshNodeName(nodeObjects);
            RefreshNodeName(nodeCommands);
        }
        public void RefreshNodeName(TreeNode node)
        {
            if (formTable[node] != null)
            {
                node.Name = node.Text;

                //
                if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                {
                    ((ImagesForm)formTable[node]).Text = node.Text;
                    ((ImagesForm)formTable[node]).id = node.Text;
                }
                if (formTable[node].GetType().Equals(typeof(MapForm)))
                {
                    ((MapForm)formTable[node]).Text = node.Text;
                    ((MapForm)formTable[node]).id = node.Text;
                }
                if (formTable[node].GetType().Equals(typeof(SpriteForm)))
                {
                    ((SpriteForm)formTable[node]).Text = node.Text;
                    ((SpriteForm)formTable[node]).id = node.Text;
                }

                //
                if (formTable[node].GetType().Equals(typeof(WorldForm)))
                {
                    ((WorldForm)formTable[node]).Text = node.Text;
                    ((WorldForm)formTable[node]).id = node.Text;
                }

                //
                if (formTable[node].GetType().Equals(typeof(ObjectForm)))
                {
                    ((ObjectForm)formTable[node]).Text = node.Text;
                    ((ObjectForm)formTable[node]).id = node.Text;
                }

                //
                if (formTable[node].GetType().Equals(typeof(CommandForm)))
                {
                    ((CommandForm)formTable[node]).Text = node.Text;
                    ((CommandForm)formTable[node]).id = node.Text;
                }
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    RefreshNodeName(sub);
                }
            }
         
        }

        private Form getForm(TreeNode key)
        {
            try
            {
                if (formTable[key] != null)
                {
                    if (formTable[key].GetType().Equals(typeof(ImagesForm)) ||
                        formTable[key].GetType().Equals(typeof(SpriteForm)) ||
                        formTable[key].GetType().Equals(typeof(MapForm))    ||
                        formTable[key].GetType().Equals(typeof(WorldForm))  ||
                        formTable[key].GetType().Equals(typeof(ObjectForm)) ||
                        formTable[key].GetType().Equals(typeof(CommandForm))
                        )
                    {
                        return (Form)formTable[key];
                    }
                }
            }
            catch (Exception err)
            {
            }
            return null;
        }


        public ArrayList getMaps(ImagesForm images) 
        {
            ArrayList list = new ArrayList();

            foreach (TreeNode node in nodeReses.Nodes)
            {
                foreach (TreeNode subnode in node.Nodes)
                {
                    Form frm = getForm(subnode);

                    if (frm.GetType().Equals(typeof(MapForm))) 
                    {
                        MapForm map = (MapForm)frm;

                        if (map.super == images)
                        {
                            list.Add(map);
                        }
                    }
                }
            }

            return list;
        }

        private void treeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            this.treeView1.SelectedNode = e.Node;
        }


        private void treeView1_BeforeLabelEdit(object sender, NodeLabelEditEventArgs e)
        {
            if(e.Node.Parent==null){
                e.CancelEdit = true;
                nodeReses.EndEdit(true);
            }
        }

        private void treeView1_AfterLabelEdit(object sender, NodeLabelEditEventArgs e)
        {
           
            if (getForm(e.Node) != null && e.Label != null && e.Label.Length > 0)
            {
                e.CancelEdit = true;

                String name = e.Label;
                

                while(true)
                {
                    int index = e.Node.Parent.Nodes.IndexOfKey(name);
                    if (index >= 0 && index != e.Node.Parent.Nodes.IndexOf(e.Node))
                    {
                        TextDialog nameDialog = new TextDialog(name);
                        nameDialog.Text = "已经有" + name + "这个名字了";
                        if (nameDialog.showDialog() == DialogResult.OK)
                        {
                            name = nameDialog.getText();
                            continue;
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        if (getForm(e.Node).GetType().Equals(typeof(ImagesForm)))
                        {
                            ((ImagesForm)getForm(e.Node)).id = name;
                            ((ImagesForm)getForm(e.Node)).changeImage();
                            Console.WriteLine("change image : " + ((ImagesForm)getForm(e.Node)).id);
                        }
                        if (getForm(e.Node).GetType().Equals(typeof(SpriteForm)))
                        {
                            ((SpriteForm)getForm(e.Node)).id = name;
                        }
                        if (getForm(e.Node).GetType().Equals(typeof(MapForm)))
                        {
                            ((MapForm)getForm(e.Node)).id = name;
                        }
                        if (getForm(e.Node).GetType().Equals(typeof(WorldForm)))
                        {
                            ((WorldForm)getForm(e.Node)).id = name;
                        }
                        if (getForm(e.Node).GetType().Equals(typeof(ObjectForm)))
                        {
                            ((ObjectForm)getForm(e.Node)).id = name;
                        }
                        if (getForm(e.Node).GetType().Equals(typeof(CommandForm)))
                        {
                            ((CommandForm)getForm(e.Node)).id = name;
                        }

                        e.Node.Name = name;
                        e.Node.Text = name;

                        RefreshNodeName();
                        sortTreeView();

                        Console.WriteLine("New Name = " + e.Node.Name);
                        break;
                    }
                }
            }
        }

        private void treeView1_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (getForm(treeView1.SelectedNode) != null)
            {
                getForm(treeView1.SelectedNode).MdiParent = this.MdiParent;
                getForm(treeView1.SelectedNode).Show();
                getForm(treeView1.SelectedNode).Select();
            }
           
            
        }

        private void treeView1_ItemDrag(object sender, ItemDragEventArgs e)
        {
            if (getForm((TreeNode)e.Item) != null)
            {

                if (getForm((TreeNode)e.Item).GetType().Equals(typeof(SpriteForm)))
                {
                    DoDragDrop((SpriteForm)getForm((TreeNode)e.Item), DragDropEffects.Link);
                    //Console.WriteLine("Spr drag");
                }
                if (getForm((TreeNode)e.Item).GetType().Equals(typeof(MapForm)))
                {
                    DoDragDrop((MapForm)getForm((TreeNode)e.Item), DragDropEffects.Link);
                    //Console.WriteLine("map drag");
                }

            }
           
        }


        private void 刷新ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            sortTreeView();
        }


        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            sortTreeView();
        }

        private void treeView1_AfterCollapse(object sender, TreeViewEventArgs e)
        {
        }

        private void treeView1_AfterExpand(object sender, TreeViewEventArgs e)
        {
        }
        
        //------------------------------------------------------------------------------------------------------------------------------------

        class NodesSorter : IComparer
        {
            StringComparer comparer = StringComparer.OrdinalIgnoreCase;

            int IComparer.Compare(Object x, Object y)
            {
                return comparer.Compare(((TreeNode)x).Name, ((TreeNode)y).Name);
            }

        }

        public void sortTreeView()
        {
            treeView1.SelectedNode = null;
            treeView1.TreeViewNodeSorter = new NodesSorter();
            treeView1.Sort();
            treeView1.SelectedNode = null;
        }

        public FormEventTemplate getEventTemplateForm()
        {
            if (form_event_templates == null) { 
                form_event_templates = new FormEventTemplate(); 
            }
            return form_event_templates;
        }

        //------------------------------------------------------------------------------------------------------------------------------------

#region resMenu
        private void 添加图片组ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Tile";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }
                
                ImagesForm form = new ImagesForm(name);
                TreeNode node = new TreeNode(name);
                node.Name = name;
                node.Text = name;
                formTable.Add(node, form);

                node.ContextMenuStrip = this.tileMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();

                try
                {
                    String dir = workSpace + "\\" + form.id;
                    if (System.IO.Directory.Exists(dir))
                    {
                        System.IO.Directory.Delete(dir,true);
                    }
                }
                catch (Exception err)
                {
                    MessageBox.Show(err.TargetSite+":"+err.StackTrace + "  at  " +err.Message);
                }
                sortTreeView();
                break;
            }

        }
      
     
#endregion

#region levelMenu

        private void 添加场景ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Level";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }

                WorldForm form = new WorldForm(name);
                TreeNode node = new TreeNode(name);
                node.Name = name;
                node.Text = name;
                formTable.Add(node, form);

                node.ContextMenuStrip = this.subMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();
                sortTreeView();
                break;
            }

        }
#endregion

#region objMenu
        private void 添加对象ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Object";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }

                ObjectForm form = new ObjectForm(name);
                TreeNode node = new TreeNode(name);
                node.Name = name;
                node.Text = name;
                formTable.Add(node, form);

                node.ContextMenuStrip = this.subMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();
                sortTreeView();
                break;
            }
        }

#endregion

#region commandMenu
        private void 添加属性列表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Command";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }

                CommandForm form = new CommandForm(name);
                TreeNode node = new TreeNode(name);
                node.Name = name;
                node.Text = name;
                formTable.Add(node, form);

                node.ContextMenuStrip = this.subMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();
                sortTreeView();
                break;
            }
        }
#endregion
        //------------------------------------------------------------------------------------------------------------------------------------
        
#region tileMenu

        private void 打开ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (getForm(treeView1.SelectedNode) != null)
            {
                getForm(treeView1.SelectedNode).MdiParent = this.MdiParent;
                getForm(treeView1.SelectedNode).Show();
                getForm(treeView1.SelectedNode).Select();
            }
           
        }
        private void 复制ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            IsCopy = true;
            copySub(treeView1.SelectedNode, null);
            sortTreeView();
            IsCopy = false;
        }
        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {

            if (getForm(treeView1.SelectedNode) != null)
            {
                while (treeView1.SelectedNode.Nodes.Count > 0)
                {
                    getForm(treeView1.SelectedNode.Nodes[0]).Enabled = false;
                    getForm(treeView1.SelectedNode.Nodes[0]).Dispose();
                    //getForm(treeView1.SelectedNode.Nodes[0]).Hide();
                    formTable.Remove(treeView1.SelectedNode.Nodes[0]);
                    treeView1.SelectedNode.Nodes.RemoveAt(0);
                }

                getForm(treeView1.SelectedNode).Enabled = false;
                getForm(treeView1.SelectedNode).Dispose();
                //getForm(treeView1.SelectedNode).Hide();
                formTable.Remove(treeView1.SelectedNode);
                treeView1.SelectedNode.Parent.Nodes.Remove(treeView1.SelectedNode);

                sortTreeView();
            }

        }

        private void 精灵ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Sprite";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }
                SpriteForm form = ((ImagesForm)getForm(treeView1.SelectedNode)).createSpriteForm(name);
                if (form != null)
                {
                    TreeNode node = new TreeNode(name);
                    node.Name = name;
                    formTable.Add(node, form);
                    node.ContextMenuStrip = this.subMenu;
                    this.treeView1.SelectedNode.Nodes.Add(node);
                    this.treeView1.SelectedNode.ExpandAll();
                    form.MdiParent = this.MdiParent;
                    form.Show();
                    sortTreeView();
                }
                break;
            }
        }

        private void 地图ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RefreshNodeName();
            String name = "unamed_Map";
            TextDialog nameDialog = new TextDialog(name);
            while (nameDialog.ShowDialog() == DialogResult.OK)
            {
                name = nameDialog.getText();
                if (treeView1.SelectedNode.Nodes.ContainsKey(name))
                {
                    MessageBox.Show("已经有　" + name + " 这个名字了");
                    continue;
                }
                MapForm form = ((ImagesForm)getForm(treeView1.SelectedNode)).createMapForm(name);
                if (form != null)
                {
                    TreeNode node = new TreeNode(name);
                    node.Name = name;
                    formTable.Add(node, form);
                    node.ContextMenuStrip = this.subMenu;
                    this.treeView1.SelectedNode.Nodes.Add(node);
                    this.treeView1.SelectedNode.ExpandAll();
                    form.MdiParent = this.MdiParent;
                    form.Show();
                    sortTreeView();
                }
                break;
            }

        }

        

#endregion

        //------------------------------------------------------------------------------------------------------------------------------------

#region subMenu

        private void 打开ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (getForm(treeView1.SelectedNode) != null)
            {
                getForm(treeView1.SelectedNode).MdiParent = this.MdiParent;
                getForm(treeView1.SelectedNode).Show();
                getForm(treeView1.SelectedNode).Select();
            }
            
        }
        private void 复制ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            IsCopy = true;
            copySub(treeView1.SelectedNode, null);
            sortTreeView();
            IsCopy = false;
        }
        private void 删除ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (getForm(treeView1.SelectedNode) != null)
            {

                getForm(treeView1.SelectedNode).Enabled = false;
                getForm(treeView1.SelectedNode).Dispose();
               
                //getForm(treeView1.SelectedNode).Hide();
                formTable.Remove(treeView1.SelectedNode);
                treeView1.SelectedNode.Parent.Nodes.Remove(treeView1.SelectedNode);

                sortTreeView();
            }
        }

#endregion
        public ImagesForm getImagesFormByName(string name)
        {
            foreach (TreeNode node in formTable.Keys)
            {
                if (node.Text.Equals(name))
                {
                    if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                    {
                        return (ImagesForm)formTable[node];
                    }
                }
            }
            return null;
        }

        private void copySub(TreeNode super,TreeNode superCopy)
        {
            if (formTable[super] != null)
            {
                RefreshNodeName();
                //
                if (formTable[super].GetType().Equals(typeof(ImagesForm)) ||
                    formTable[super].GetType().Equals(typeof(MapForm)) ||
                    formTable[super].GetType().Equals(typeof(SpriteForm)) ||
                    formTable[super].GetType().Equals(typeof(WorldForm)) ||
                    formTable[super].GetType().Equals(typeof(CommandForm))
                    )
                {
                    try
                    {
                        // clone form
                       
                        SoapFormatter formatter = new SoapFormatter();
                        
                        MemoryStream stream = new MemoryStream();
                        stream.Seek(0, SeekOrigin.Begin);
                        formatter.Serialize(stream, formTable[super]);
                        
                        stream.Seek(0, SeekOrigin.Begin);
                        Form form = (Form)formatter.Deserialize(stream);
                        stream.Close();

                        if (formTable[super].GetType().Equals(typeof(ImagesForm)))
                        {
                            ((ImagesForm)form).changeImage();
                            Console.WriteLine("change image : " + ((ImagesForm)form).id);
                        }
                        if (formTable[super].GetType().Equals(typeof(MapForm)))
                        {
                            //((MapForm)form).id = ((MapForm)form).id ;

                            if (superCopy != null && formTable[superCopy] != null && formTable[superCopy].GetType().Equals(typeof(ImagesForm)))
                            {
                                ((MapForm)form).ChangeSuper((ImagesForm)formTable[superCopy]);
                                Console.WriteLine("change super : " + ((ImagesForm)formTable[superCopy]).id);
                            }
                            else if (super.Parent != null && formTable[super.Parent] != null && formTable[super.Parent].GetType().Equals(typeof(ImagesForm)))
                            {
                                ((MapForm)form).ChangeSuper((ImagesForm)formTable[super.Parent]);
                                Console.WriteLine("change super : " + ((ImagesForm)formTable[super.Parent]).id);
                            }
                        }
                        if (formTable[super].GetType().Equals(typeof(SpriteForm)))
                        {
                            //initForms();
                            //((SpriteForm)form).ChangeSuper(FormsImages);
                            //((SpriteForm)form).id = ((SpriteForm)form).id ;

                            if (superCopy != null && formTable[superCopy] != null && formTable[superCopy].GetType().Equals(typeof(ImagesForm)))
                            {
                                ((SpriteForm)form).ChangeSuper((ImagesForm)formTable[superCopy]);
                                Console.WriteLine("change super : " + ((ImagesForm)formTable[superCopy]).id);
                            }
                            else if (super.Parent != null && formTable[super.Parent] != null && formTable[super.Parent].GetType().Equals(typeof(ImagesForm)))
                            {
                                ((SpriteForm)form).ChangeSuper((ImagesForm)formTable[super.Parent]);
                                Console.WriteLine("change super : " + ((ImagesForm)formTable[super.Parent]).id);
                            }
                        }
                        if (formTable[super].GetType().Equals(typeof(WorldForm)))
                        {
                            initForms();
                            ((WorldForm)form).ChangeAllUnits(FormsMap,FormsSprite,FormsImages);
                        }
                        if (formTable[super].GetType().Equals(typeof(CommandForm)))
                        {
                        }

                        form.MdiParent = this.MdiParent;
                        
                        

                        // clone node
                        TreeNode copy = new TreeNode(super.Name + "_Copy");
                        copy.Name = super.Name + "_Copy";
                        copy.ContextMenuStrip = super.ContextMenuStrip;

                        // add 
                        formTable.Add(copy, form);
                        if (superCopy != null)
                        {
                            // adjust
                            while (true)
                            {
                                if (superCopy.Nodes.ContainsKey(copy.Name))
                                {
                                    copy.Name = copy.Name + "_Copy";
                                    copy.Text = copy.Name;
                                    continue;
                                }
                                else
                                {
                                    superCopy.Nodes.Add(copy);
                                    break;
                                }
                            }
                        }
                        else
                        {
                            // adjust
                            while (true)
                            {
                                if (super.Parent.Nodes.ContainsKey(copy.Name))
                                {
                                    copy.Name = copy.Name + "_Copy";
                                    copy.Text = copy.Name;
                                    continue;
                                }
                                else
                                {
                                    super.Parent.Nodes.Add(copy);
                                    break;
                                }
                            }
                        }

                        

                        // sub nodes
                        if (super.Nodes.Count >= 0)
                        {
                            foreach (TreeNode sub in super.Nodes)
                            {
                                if (form.GetType().Equals(typeof(ImagesForm)))
                                {
                                    copySub(sub, copy);
                                }
                                else
                                {
                                    copySub(sub, null);
                                }
                                
                            }
                        }
                    }
                    catch (Exception err) {
                        Console.WriteLine(err.HelpLink); 
                        Console.WriteLine(err.Source); 
                        Console.WriteLine(err.Message); 
                        Console.WriteLine(err.StackTrace + "  at  " +err.Message); 
                    }
                }
                RefreshNodeName();
            }

        

        }


	

		public ArrayList getImangesFormChilds(ImagesForm src)
		{
			ArrayList ret = new ArrayList();

			foreach (TreeNode node in formTable.Keys)
            {
                if (node.Text.Equals(src.getID()))
                {
                    if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                    {
						foreach (TreeNode subNode in node.Nodes)
						{
							ret.Add(formTable[subNode]);
						}
						return ret;
                    }
                }
            }

			return null;
		}


     

    



    }
}