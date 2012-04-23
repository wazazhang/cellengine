namespace CellGameEdit.PM
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
            this.toolComboEventFiles = new System.Windows.Forms.ToolStripComboBox();
            this.btnRefreshFiles = new System.Windows.Forms.ToolStripButton();
            this.listView1 = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // toolStrip1
            // 
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolComboEventFiles,
            this.btnRefreshFiles});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(193, 25);
            this.toolStrip1.TabIndex = 0;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolComboEventFiles
            // 
            this.toolComboEventFiles.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.toolComboEventFiles.Name = "toolComboEventFiles";
            this.toolComboEventFiles.Size = new System.Drawing.Size(121, 25);
            this.toolComboEventFiles.SelectedIndexChanged += new System.EventHandler(this.toolComboEventFiles_SelectedIndexChanged);
            this.toolComboEventFiles.DropDown += new System.EventHandler(this.toolComboEventFiles_DropDown);
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
            // listView1
            // 
            this.listView1.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3,
            this.columnHeader4});
            this.listView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listView1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.listView1.FullRowSelect = true;
            this.listView1.GridLines = true;
            this.listView1.HideSelection = false;
            this.listView1.LargeImageList = this.imageList1;
            this.listView1.Location = new System.Drawing.Point(0, 25);
            this.listView1.MultiSelect = false;
            this.listView1.Name = "listView1";
            this.listView1.Size = new System.Drawing.Size(193, 402);
            this.listView1.SmallImageList = this.imageList1;
            this.listView1.TabIndex = 1;
            this.listView1.UseCompatibleStateImageBehavior = false;
            this.listView1.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "Type";
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Name";
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Icon";
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "Other";
            // 
            // imageList1
            // 
            this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
            this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            // 
            // FormEventTemplate
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(193, 427);
            this.Controls.Add(this.listView1);
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
        private System.Windows.Forms.ToolStripComboBox toolComboEventFiles;
        private System.Windows.Forms.ListView listView1;
        private System.Windows.Forms.ToolStripButton btnRefreshFiles;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ImageList imageList1;
    }
}