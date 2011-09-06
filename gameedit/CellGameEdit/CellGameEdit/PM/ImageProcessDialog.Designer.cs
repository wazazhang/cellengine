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
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.checkFlip = new System.Windows.Forms.CheckBox();
            this.SuspendLayout();
            // 
            // buttonOK
            // 
            this.buttonOK.Location = new System.Drawing.Point(453, 198);
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
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(102, 10);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(137, 21);
            this.textBox1.TabIndex = 2;
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
            // ImageProcessDialog
            // 
            this.AcceptButton = this.buttonOK;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(539, 226);
            this.Controls.Add(this.checkFlip);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.checkSetKeyColor);
            this.Controls.Add(this.buttonOK);
            this.Name = "ImageProcessDialog";
            this.Text = "图片批处理";
            this.Load += new System.EventHandler(this.ImageProcessDialog_Load);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.ImageProcessDialog_FormClosing);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonOK;
        private System.Windows.Forms.CheckBox checkSetKeyColor;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.CheckBox checkFlip;
    }
}