namespace CellGameEdit.PM
{
    partial class ImageProcessDialog
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
            this.buttonOK = new System.Windows.Forms.Button();
            this.checkSetKeyColor = new System.Windows.Forms.CheckBox();
            this.checkFlip = new System.Windows.Forms.CheckBox();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripLabel2 = new System.Windows.Forms.ToolStripLabel();
            this.imageFlipToolStripButton1 = new CellGameEdit.PM.ImageFlipToolStripButton();
            this.toolStrip2 = new System.Windows.Forms.ToolStrip();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.textColor = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel3 = new System.Windows.Forms.ToolStripLabel();
            this.textDstColor = new System.Windows.Forms.ToolStripTextBox();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.labelSelectedCount = new System.Windows.Forms.ToolStripStatusLabel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.chkOptImageSize = new System.Windows.Forms.CheckBox();
            this.toolStrip1.SuspendLayout();
            this.toolStrip2.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // buttonOK
            // 
            this.buttonOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOK.Location = new System.Drawing.Point(342, 228);
            this.buttonOK.Name = "buttonOK";
            this.buttonOK.Size = new System.Drawing.Size(75, 23);
            this.buttonOK.TabIndex = 0;
            this.buttonOK.Text = "OK";
            this.buttonOK.UseVisualStyleBackColor = true;
            this.buttonOK.Click += new System.EventHandler(this.buttonOK_Click);
            // 
            // checkSetKeyColor
            // 
            this.checkSetKeyColor.AutoSize = true;
            this.checkSetKeyColor.Location = new System.Drawing.Point(15, 12);
            this.checkSetKeyColor.Name = "checkSetKeyColor";
            this.checkSetKeyColor.Size = new System.Drawing.Size(72, 16);
            this.checkSetKeyColor.TabIndex = 1;
            this.checkSetKeyColor.Text = "替换颜色";
            this.checkSetKeyColor.UseVisualStyleBackColor = true;
            // 
            // checkFlip
            // 
            this.checkFlip.AutoSize = true;
            this.checkFlip.Location = new System.Drawing.Point(15, 83);
            this.checkFlip.Name = "checkFlip";
            this.checkFlip.Size = new System.Drawing.Size(48, 16);
            this.checkFlip.TabIndex = 3;
            this.checkFlip.Text = "翻转";
            this.checkFlip.UseVisualStyleBackColor = true;
            // 
            // toolStrip1
            // 
            this.toolStrip1.Dock = System.Windows.Forms.DockStyle.None;
            this.toolStrip1.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripLabel2,
            this.imageFlipToolStripButton1});
            this.toolStrip1.Location = new System.Drawing.Point(15, 102);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(102, 25);
            this.toolStrip1.TabIndex = 4;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripLabel2
            // 
            this.toolStripLabel2.Name = "toolStripLabel2";
            this.toolStripLabel2.Size = new System.Drawing.Size(77, 22);
            this.toolStripLabel2.Text = "翻转所选图片";
            // 
            // imageFlipToolStripButton1
            // 
            this.imageFlipToolStripButton1.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.imageFlipToolStripButton1.Image = global::CellGameEdit.Resource1.Image36;
            this.imageFlipToolStripButton1.ImageScaling = System.Windows.Forms.ToolStripItemImageScaling.None;
            this.imageFlipToolStripButton1.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.imageFlipToolStripButton1.Name = "imageFlipToolStripButton1";
            this.imageFlipToolStripButton1.Size = new System.Drawing.Size(22, 22);
            this.imageFlipToolStripButton1.Text = "翻转方式";
            // 
            // toolStrip2
            // 
            this.toolStrip2.Dock = System.Windows.Forms.DockStyle.None;
            this.toolStrip2.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripLabel1,
            this.textColor,
            this.toolStripLabel3,
            this.textDstColor});
            this.toolStrip2.Location = new System.Drawing.Point(15, 31);
            this.toolStrip2.Name = "toolStrip2";
            this.toolStrip2.Size = new System.Drawing.Size(325, 25);
            this.toolStrip2.TabIndex = 5;
            this.toolStrip2.Text = "toolStrip2";
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(65, 22);
            this.toolStripLabel1.Text = "输入颜色值";
            // 
            // textColor
            // 
            this.textColor.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.textColor.Name = "textColor";
            this.textColor.Size = new System.Drawing.Size(100, 25);
            this.textColor.Text = "输入十六位颜色值";
            this.textColor.TextChanged += new System.EventHandler(this.textColor_TextChanged);
            // 
            // toolStripLabel3
            // 
            this.toolStripLabel3.Name = "toolStripLabel3";
            this.toolStripLabel3.Size = new System.Drawing.Size(53, 22);
            this.toolStripLabel3.Text = "目标颜色";
            // 
            // textDstColor
            // 
            this.textDstColor.Name = "textDstColor";
            this.textDstColor.Size = new System.Drawing.Size(100, 25);
            this.textDstColor.Text = "0";
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.labelSelectedCount});
            this.statusStrip1.Location = new System.Drawing.Point(0, 264);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(429, 22);
            this.statusStrip1.TabIndex = 6;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // labelSelectedCount
            // 
            this.labelSelectedCount.Name = "labelSelectedCount";
            this.labelSelectedCount.Size = new System.Drawing.Size(131, 17);
            this.labelSelectedCount.Text = "toolStripStatusLabel1";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.chkOptImageSize);
            this.panel1.Controls.Add(this.checkSetKeyColor);
            this.panel1.Controls.Add(this.buttonOK);
            this.panel1.Controls.Add(this.toolStrip2);
            this.panel1.Controls.Add(this.checkFlip);
            this.panel1.Controls.Add(this.toolStrip1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(429, 264);
            this.panel1.TabIndex = 7;
            // 
            // chkOptImageSize
            // 
            this.chkOptImageSize.AutoSize = true;
            this.chkOptImageSize.Location = new System.Drawing.Point(15, 148);
            this.chkOptImageSize.Name = "chkOptImageSize";
            this.chkOptImageSize.Size = new System.Drawing.Size(144, 16);
            this.chkOptImageSize.TabIndex = 6;
            this.chkOptImageSize.Text = "去除多余透明像素区域";
            this.chkOptImageSize.UseVisualStyleBackColor = true;
            // 
            // ImageProcessDialog
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(429, 286);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.statusStrip1);
            this.Name = "ImageProcessDialog";
            this.Text = "图片批处理";
            this.Load += new System.EventHandler(this.ImageProcessDialog_Load);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.ImageProcessDialog_FormClosing);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.toolStrip2.ResumeLayout(false);
            this.toolStrip2.PerformLayout();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonOK;
        private System.Windows.Forms.CheckBox checkSetKeyColor;
        private System.Windows.Forms.CheckBox checkFlip;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private ImageFlipToolStripButton imageFlipToolStripButton1;
        private System.Windows.Forms.ToolStripLabel toolStripLabel2;
        private System.Windows.Forms.ToolStrip toolStrip2;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel labelSelectedCount;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ToolStripTextBox textColor;
        private System.Windows.Forms.ToolStripLabel toolStripLabel3;
        private System.Windows.Forms.ToolStripTextBox textDstColor;
		private System.Windows.Forms.CheckBox chkOptImageSize;
    }
}