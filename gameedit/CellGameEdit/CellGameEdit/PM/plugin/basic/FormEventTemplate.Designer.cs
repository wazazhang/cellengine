namespace CellGameEdit.PM.plugin.basic
{
    partial class FormEventTemplate
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
			this.components = new System.ComponentModel.Container();
			this.toolStrip1 = new System.Windows.Forms.ToolStrip();
			this.btnRefreshFiles = new System.Windows.Forms.ToolStripButton();
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.treeView1 = new System.Windows.Forms.TreeView();
			this.toolStrip1.SuspendLayout();
			this.SuspendLayout();
			// 
			// toolStrip1
			// 
			this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnRefreshFiles});
			this.toolStrip1.Location = new System.Drawing.Point(0, 0);
			this.toolStrip1.Name = "toolStrip1";
			this.toolStrip1.Size = new System.Drawing.Size(193, 25);
			this.toolStrip1.TabIndex = 0;
			this.toolStrip1.Text = "toolStrip1";
			// 
			// btnRefreshFiles
			// 
			this.btnRefreshFiles.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
			this.btnRefreshFiles.Image = global::CellGameEdit.Resource1.ajax_refresh_icon;
			this.btnRefreshFiles.ImageScaling = System.Windows.Forms.ToolStripItemImageScaling.None;
			this.btnRefreshFiles.ImageTransparentColor = System.Drawing.Color.Magenta;
			this.btnRefreshFiles.Name = "btnRefreshFiles";
			this.btnRefreshFiles.Size = new System.Drawing.Size(23, 22);
			this.btnRefreshFiles.Text = "刷新";
			this.btnRefreshFiles.Click += new System.EventHandler(this.btnRefreshFiles_Click);
			// 
			// imageList1
			// 
			this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// treeView1
			// 
			this.treeView1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.treeView1.Location = new System.Drawing.Point(0, 25);
			this.treeView1.Name = "treeView1";
			this.treeView1.Size = new System.Drawing.Size(193, 402);
			this.treeView1.TabIndex = 1;
			// 
			// FormEventTemplate
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(193, 427);
			this.Controls.Add(this.treeView1);
			this.Controls.Add(this.toolStrip1);
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "FormEventTemplate";
			this.ShowIcon = false;
			this.ShowInTaskbar = false;
			this.Text = "FormEventTemplate";
			this.TopMost = true;
			this.Load += new System.EventHandler(this.FormEventTemplate_Load);
			this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormEventTemplate_FormClosing);
			this.toolStrip1.ResumeLayout(false);
			this.toolStrip1.PerformLayout();
			this.ResumeLayout(false);
			this.PerformLayout();

        }

        #endregion

		private System.Windows.Forms.ToolStrip toolStrip1;
		private System.Windows.Forms.ToolStripButton btnRefreshFiles;
        private System.Windows.Forms.ImageList imageList1;
		private System.Windows.Forms.TreeView treeView1;
    }
}