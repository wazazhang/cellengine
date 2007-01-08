﻿using System;
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

namespace CellGameEdit.PM
{

    [Serializable]
    public partial class ProjectForm : Form , ISerializable
    {
 
        


        static public String workSpace = "";

        

        TreeNode nodeReses;
        TreeNode nodeLevels;

        //ArrayList formGroup;
        Hashtable formTable;

        //-----------------------------------------------------------------------------------------------------------------------------------
        // new 
        public ProjectForm()
        {
            InitializeComponent();

           // formGroup = new ArrayList();
            formTable = new Hashtable();
            nodeReses = new TreeNode("资源");
            nodeLevels = new TreeNode("场景");

            nodeReses.ContextMenuStrip = this.resMenu;
            nodeLevels.ContextMenuStrip = this.levelMenu;

            treeView1.Nodes.Add(nodeReses);
            treeView1.Nodes.Add(nodeLevels);

            treeView1.ExpandAll();

           
            

        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected ProjectForm(SerializationInfo info, StreamingContext context)
        {
            InitializeComponent();

            

            nodeReses = (TreeNode)info.GetValue("nodeReses", typeof(TreeNode));
            nodeLevels = (TreeNode)info.GetValue("nodeLevels", typeof(TreeNode));
            //formGroup = (ArrayList)info.GetValue("formGroup", typeof(ArrayList));
            formTable = (Hashtable)info.GetValue("formTable", typeof(Hashtable));

            nodeReses.ContextMenuStrip = this.resMenu;
            nodeLevels.ContextMenuStrip = this.levelMenu;


            foreach(TreeNode node in nodeReses.Nodes)
            {
                node.ContextMenuStrip = this.tileMenu;

                foreach(TreeNode subnode in node.Nodes)
                {
                    subnode.ContextMenuStrip = this.subMenu;
                }
            }
            foreach (TreeNode node in nodeLevels.Nodes)
            {
                node.ContextMenuStrip = this.subMenu;
            }
            treeView1.Nodes.Add(nodeReses);
            treeView1.Nodes.Add(nodeLevels);

            treeView1.ExpandAll();
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            RefreshNodeName();

           // info.AddValue("formGroup", formGroup);
            info.AddValue("nodeReses", nodeReses);
            info.AddValue("nodeLevels", nodeLevels);
            info.AddValue("formTable", formTable);
            
          
        }

        public void OutputCustom(String fileName)
        {
            try
            {
                RefreshNodeName();

                if (System.IO.File.Exists(fileName))
                {
                    Encoding encoding = Util.GetEncoding(fileName);

                    StreamReader sr = new StreamReader(fileName, encoding);
                    String script = sr.ReadToEnd();
                    sr.Close();

                    String ret = new string(new char[] { '\r', '\n' });

                    System.IO.StringWriter outRes = new StringWriter();
                    System.IO.StringWriter outLev = new StringWriter();

                    String OutputName = Util.getCommandScript(script, "<OUTPUT>");

                    OutputCustom(nodeReses, script, outRes);
                    script = Util.replaceSubTrunksScript(script, "#<RESOURCE>", "#<END RESOURCE>", new string[] { outRes.ToString() });

                    OutputCustom(nodeLevels, script, outLev);
                    script = Util.replaceSubTrunksScript(script, "#<LEVEL>", "#<END LEVEL>", new string[] { outLev.ToString() });

                    script = script.Insert(0, "/* Email : wazazhang@gmail.com */" + ret);
                    script = script.Insert(0, "/* Cell Game Editor by WAZA Zhang */" + ret);
                    script = script.Insert(0, "/* Encoding : " + encoding.EncodingName + " */" + ret);

                    Console.WriteLine(script);

                    System.IO.File.WriteAllText(
                        workSpace + "\\" + OutputName,
                        script,
                        encoding
                        );
                }
                else
                {
                    Console.WriteLine("Error : " + fileName + " : 不存在!");
                }
            }
            catch (Exception err) { Console.WriteLine(this.Name + " : " + err.Message); }

        }

        public void OutputCustom(TreeNode node, String script, System.IO.StringWriter output)
        {
            if (formTable[node] != null)
            {
                //
                if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                {
                    ((ImagesForm)formTable[node]).OutputCustom(script,output);
                }
                if (formTable[node].GetType().Equals(typeof(MapForm)))
                {
                    ((MapForm)formTable[node]).OutputCustom(script, output);
                }
                if (formTable[node].GetType().Equals(typeof(SpriteForm)))
                {
                    ((SpriteForm)formTable[node]).OutputCustom(script, output);
                }

                //
                if (formTable[node].GetType().Equals(typeof(WorldForm)))
                {
                    ((WorldForm)formTable[node]).OutputCustom(script, output);
                }
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    OutputCustom(sub, script, output); 
                }
            }
        }

        public void Output()
        {
            RefreshNodeName();

            System.IO.StringWriter sw = new System.IO.StringWriter();
            sw.WriteLine("/* Cell Game Editor by WAZA Zhang */");
            sw.WriteLine("/* Email : wazazhang@gmail.com */");

            sw.WriteLine("import com.morefuntek.cell.*;");
            sw.WriteLine("import com.morefuntek.cell.Game.*;");
            sw.WriteLine();

            sw.WriteLine("public class Reses {");
            Output(nodeReses, sw);
            sw.WriteLine("}");

            Output(nodeLevels, sw);

            Console.WriteLine(sw.ToString());

            System.IO.File.WriteAllText(
                ProjectForm.workSpace + "\\Reses.java", 
                sw.ToString());

            //

        }

        public void Output(TreeNode node, System.IO.StringWriter sw)
        {
            if (formTable[node] != null)
            {
                //
                if (formTable[node].GetType().Equals(typeof(ImagesForm)))
                {
                    ((ImagesForm)formTable[node]).Output(sw);
                }
                if (formTable[node].GetType().Equals(typeof(MapForm)))
                {
                    ((MapForm)formTable[node]).Output(sw);
                }
                if (formTable[node].GetType().Equals(typeof(SpriteForm)))
                {
                    ((SpriteForm)formTable[node]).Output(sw);
                }

                //
                if (formTable[node].GetType().Equals(typeof(WorldForm)))
                {
                    ((WorldForm)formTable[node]).Output(sw);
                }
            }

            if (node.Nodes.Count >= 0)
            {
                foreach (TreeNode sub in node.Nodes)
                {
                    Output(sub,sw);
                }
            }
         
        }

        public void RefreshNodeName()
        {
            RefreshNodeName(nodeReses);
            RefreshNodeName(nodeLevels);
        }

        public void RefreshNodeName(TreeNode node)
        {
            if (formTable[node] != null)
            {
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
                        formTable[key].GetType().Equals(typeof(WorldForm)) )
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
        }

        private void treeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            this.treeView1.SelectedNode = e.Node;
        }


        private void treeView1_BeforeLabelEdit(object sender, NodeLabelEditEventArgs e)
        {
            if(e.Node.Parent==null){
                nodeReses.EndEdit(true);
            }
        }

        private void treeView1_AfterLabelEdit(object sender, NodeLabelEditEventArgs e)
        {
           

            if (getForm(treeView1.SelectedNode) != null)
            {
                if (getForm(treeView1.SelectedNode).GetType().Equals(typeof(ImagesForm)))
                { 
                    ((ImagesForm)getForm(treeView1.SelectedNode)).id = e.Label; 
                }
                if (getForm(treeView1.SelectedNode).GetType().Equals(typeof(SpriteForm)))
                { 
                    ((SpriteForm)getForm(treeView1.SelectedNode)).id = e.Label; 
                }
                if (getForm(treeView1.SelectedNode).GetType().Equals(typeof(MapForm)))
                { 
                    ((MapForm)getForm(treeView1.SelectedNode)).id = e.Label; 
                }
                if (getForm(treeView1.SelectedNode).GetType().Equals(typeof(WorldForm)))
                { 
                    ((WorldForm)getForm(treeView1.SelectedNode)).id = e.Label; 
                }

            }

            RefreshNodeName();
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


        //------------------------------------------------------------------------------------------------------------------------------------

#region resMenu
        private void 添加图片组ToolStripMenuItem_Click(object sender, EventArgs e)
        {
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
                formTable.Add(node, form);

                node.ContextMenuStrip = this.tileMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();
                break;
            }

        }
#endregion

#region levelMenu

        private void 添加场景ToolStripMenuItem_Click(object sender, EventArgs e)
        {
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
                formTable.Add(node, form);

                node.ContextMenuStrip = this.subMenu;
                this.treeView1.SelectedNode.Nodes.Add(node);
                this.treeView1.SelectedNode.ExpandAll();
                form.MdiParent = this.MdiParent;
                form.Show();
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

        private void 精灵ToolStripMenuItem_Click(object sender, EventArgs e)
        {
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
                }
                break;
            }
        }

        private void 地图ToolStripMenuItem_Click(object sender, EventArgs e)
        {
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
                    
                }
                break;
            }

        }

        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {

            if (getForm(treeView1.SelectedNode)!=null)
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

        private void 删除ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (getForm(treeView1.SelectedNode) != null)
            {
                getForm(treeView1.SelectedNode).Enabled = false;
                getForm(treeView1.SelectedNode).Dispose();
               
                //getForm(treeView1.SelectedNode).Hide();
                formTable.Remove(treeView1.SelectedNode);
                treeView1.SelectedNode.Parent.Nodes.Remove(treeView1.SelectedNode);

               
            }
        }

#endregion




    }
}