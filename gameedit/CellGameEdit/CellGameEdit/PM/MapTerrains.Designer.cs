namespace CellGameEdit.PM
{
    partial class MapTerrains
    {
        /// <summary> 
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region 组件设计器生成的代码

        /// <summary> 
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MapTerrains));
            this.panel1 = new System.Windows.Forms.Panel();
            this.MapRegion = new System.Windows.Forms.PictureBox();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.ListTerrains = new System.Windows.Forms.ToolStripDropDownButton();
            this.asdfToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.MapRegion)).BeginInit();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.MapRegion);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 25);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(269, 439);
            this.panel1.TabIndex = 0;
            // 
            // MapRegion
            // 
            this.MapRegion.Location = new System.Drawing.Point(3, 3);
            this.MapRegion.Name = "MapRegion";
            this.MapRegion.Size = new System.Drawing.Size(263, 433);
            this.MapRegion.TabIndex = 0;
            this.MapRegion.TabStop = false;
            // 
            // toolStrip1
            // 
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ListTerrains});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(269, 25);
            this.toolStrip1.TabIndex = 0;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // ListTerrains
            // 
            this.ListTerrains.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.asdfToolStripMenuItem});
            this.ListTerrains.Image = ((System.Drawing.Image)(resources.GetObject("ListTerrains.Image")));
            this.ListTerrains.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.ListTerrains.Name = "ListTerrains";
            this.ListTerrains.Size = new System.Drawing.Size(84, 22);
            this.ListTerrains.Text = "地形列表";
            this.ListTerrains.DropDownOpening += new System.EventHandler(this.ListTerrains_DropDownOpening);
            this.ListTerrains.DropDownItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.ListTerrains_DropDownItemClicked);
            // 
            // asdfToolStripMenuItem
            // 
            this.asdfToolStripMenuItem.Name = "asdfToolStripMenuItem";
            this.asdfToolStripMenuItem.Size = new System.Drawing.Size(95, 22);
            this.asdfToolStripMenuItem.Text = "asdf";
            // 
            // MapTerrains
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.toolStrip1);
            this.Name = "MapTerrains";
            this.Size = new System.Drawing.Size(269, 464);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.MapRegion)).EndInit();
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripDropDownButton ListTerrains;
        private System.Windows.Forms.ToolStripMenuItem asdfToolStripMenuItem;
        private System.Windows.Forms.PictureBox MapRegion;
    }
}
