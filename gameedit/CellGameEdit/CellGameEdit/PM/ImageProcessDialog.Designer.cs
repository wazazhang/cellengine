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
            this.toolStrip2 = new System.Windows.Forms.ToolStrip();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.textColor = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel2 = new System.Windows.Forms.ToolStripLabel();
            this.imageFlipToolStripButton1 = new CellGameEdit.PM.ImageFlipToolStripButton();
            this.toolStrip1.SuspendLayout();
            this.toolStrip2.SuspendLayout();
            this.SuspendLayout();
            // 
            // buttonOK
            // 
            this.buttonOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonOK.Location = new System.Drawing.Point(334, 160);
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
            this.checkSetKeyColor.Location = new System.Drawing.Point(12, 12);
            this.checkSetKeyColor.Name = "checkSetKeyColor";
            this.checkSetKeyColor.Size = new System.Drawing.Size(84, 16);
            this.checkSetKeyColor.TabIndex = 1;
            this.checkSetKeyColor.Text = "清理透明色";
            this.checkSetKeyColor.UseVisualStyleBackColor = true;
            // 
            // checkFlip
            // 
            this.checkFlip.AutoSize = true;
            this.checkFlip.Location = new System.Drawing.Point(12, 65);
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
            this.toolStrip1.Location = new System.Drawing.Point(12, 84);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(105, 25);
            this.toolStrip1.TabIndex = 4;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStrip2
            // 
            this.toolStrip2.Dock = System.Windows.Forms.DockStyle.None;
            this.toolStrip2.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripLabel1,
            this.textColor});
            this.toolStrip2.Location = new System.Drawing.Point(12, 31);
            this.toolStrip2.Name = "toolStrip2";
            this.toolStrip2.Size = new System.Drawing.Size(173, 25);
            this.toolStrip2.TabIndex = 5;
            this.toolStrip2.Text = "toolStrip2";
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(68, 22);
            this.toolStripLabel1.Text = "输入颜色值";
            // 
            // textColor
            // 
            this.textColor.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.textColor.Name = "textColor";
            this.textColor.Size = new System.Drawing.Size(100, 25);
            // 
            // toolStripLabel2
            // 
            this.toolStripLabel2.Name = "toolStripLabel2";
            this.toolStripLabel2.Size = new System.Drawing.Size(80, 22);
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
            // ImageProcessDialog
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(421, 195);
            this.Controls.Add(this.toolStrip2);
            this.Controls.Add(this.toolStrip1);
            this.Controls.Add(this.checkFlip);
            this.Controls.Add(this.checkSetKeyColor);
            this.Controls.Add(this.buttonOK);
            this.Name = "ImageProcessDialog";
            this.Text = "图片批处理";
            this.Load += new System.EventHandler(this.ImageProcessDialog_Load);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.ImageProcessDialog_FormClosing);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.toolStrip2.ResumeLayout(false);
            this.toolStrip2.PerformLayout();
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
        private System.Windows.Forms.ToolStripTextBox textColor;
    }
}