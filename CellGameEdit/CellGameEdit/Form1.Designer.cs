namespace CellGameEdit
{
    partial class Form1
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

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.文件ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.新建ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.打开ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
            this.清理ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.保存ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.另存为toolStripMenuItem8 = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem3 = new System.Windows.Forms.ToolStripSeparator();
            this.关闭ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
            this.退出ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.导入脚本ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.脚本ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.删除ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripComboBox1 = new System.Windows.Forms.ToolStripComboBox();
            this.toolStripMenuItem4 = new System.Windows.Forms.ToolStripMenuItem();
            this.自定义脚本ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.当前工程脚本ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem6 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripMenuItem5 = new System.Windows.Forms.ToolStripSeparator();
            this.javaToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.帮助ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.关于ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem7 = new System.Windows.Forms.ToolStripMenuItem();
            this.显示输出ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.项目ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.选项ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.CfgOutputEncoding = new System.Windows.Forms.ToolStripMenuItem();
            this.枚举查看器ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.改变渲染字体ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.文件ToolStripMenuItem,
            this.导入脚本ToolStripMenuItem,
            this.toolStripComboBox1,
            this.toolStripMenuItem4,
            this.帮助ToolStripMenuItem,
            this.项目ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(706, 25);
            this.menuStrip1.TabIndex = 1;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // 文件ToolStripMenuItem
            // 
            this.文件ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.新建ToolStripMenuItem,
            this.打开ToolStripMenuItem,
            this.toolStripMenuItem2,
            this.清理ToolStripMenuItem,
            this.保存ToolStripMenuItem,
            this.另存为toolStripMenuItem8,
            this.toolStripMenuItem3,
            this.关闭ToolStripMenuItem,
            this.toolStripMenuItem1,
            this.退出ToolStripMenuItem});
            this.文件ToolStripMenuItem.Name = "文件ToolStripMenuItem";
            this.文件ToolStripMenuItem.Size = new System.Drawing.Size(43, 21);
            this.文件ToolStripMenuItem.Text = "文件";
            this.文件ToolStripMenuItem.DropDownOpening += new System.EventHandler(this.文件ToolStripMenuItem_DropDownOpening);
            // 
            // 新建ToolStripMenuItem
            // 
            this.新建ToolStripMenuItem.Name = "新建ToolStripMenuItem";
            this.新建ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.新建ToolStripMenuItem.Text = "新建";
            this.新建ToolStripMenuItem.Click += new System.EventHandler(this.新建ToolStripMenuItem_Click);
            // 
            // 打开ToolStripMenuItem
            // 
            this.打开ToolStripMenuItem.Name = "打开ToolStripMenuItem";
            this.打开ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.打开ToolStripMenuItem.Text = "打开";
            this.打开ToolStripMenuItem.Click += new System.EventHandler(this.打开ToolStripMenuItem_Click);
            // 
            // toolStripMenuItem2
            // 
            this.toolStripMenuItem2.Name = "toolStripMenuItem2";
            this.toolStripMenuItem2.Size = new System.Drawing.Size(199, 6);
            // 
            // 清理ToolStripMenuItem
            // 
            this.清理ToolStripMenuItem.Name = "清理ToolStripMenuItem";
            this.清理ToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Shift)
                        | System.Windows.Forms.Keys.S)));
            this.清理ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.清理ToolStripMenuItem.Text = "保存并清理";
            this.清理ToolStripMenuItem.Click += new System.EventHandler(this.清理ToolStripMenuItem_Click);
            // 
            // 保存ToolStripMenuItem
            // 
            this.保存ToolStripMenuItem.Name = "保存ToolStripMenuItem";
            this.保存ToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.S)));
            this.保存ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.保存ToolStripMenuItem.Text = "保存";
            this.保存ToolStripMenuItem.Click += new System.EventHandler(this.保存ToolStripMenuItem_Click);
            // 
            // 另存为toolStripMenuItem8
            // 
            this.另存为toolStripMenuItem8.Name = "另存为toolStripMenuItem8";
            this.另存为toolStripMenuItem8.Size = new System.Drawing.Size(202, 22);
            this.另存为toolStripMenuItem8.Text = "另存为";
            this.另存为toolStripMenuItem8.Click += new System.EventHandler(this.另存为toolStripMenuItem8_Click);
            // 
            // toolStripMenuItem3
            // 
            this.toolStripMenuItem3.Name = "toolStripMenuItem3";
            this.toolStripMenuItem3.Size = new System.Drawing.Size(199, 6);
            // 
            // 关闭ToolStripMenuItem
            // 
            this.关闭ToolStripMenuItem.Name = "关闭ToolStripMenuItem";
            this.关闭ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.关闭ToolStripMenuItem.Text = "关闭";
            this.关闭ToolStripMenuItem.Click += new System.EventHandler(this.关闭ToolStripMenuItem_Click);
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(199, 6);
            // 
            // 退出ToolStripMenuItem
            // 
            this.退出ToolStripMenuItem.Name = "退出ToolStripMenuItem";
            this.退出ToolStripMenuItem.Size = new System.Drawing.Size(202, 22);
            this.退出ToolStripMenuItem.Text = "退出";
            this.退出ToolStripMenuItem.Click += new System.EventHandler(this.退出ToolStripMenuItem_Click);
            // 
            // 导入脚本ToolStripMenuItem
            // 
            this.导入脚本ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.脚本ToolStripMenuItem,
            this.删除ToolStripMenuItem});
            this.导入脚本ToolStripMenuItem.Name = "导入脚本ToolStripMenuItem";
            this.导入脚本ToolStripMenuItem.Size = new System.Drawing.Size(67, 21);
            this.导入脚本ToolStripMenuItem.Text = "公共脚本";
            // 
            // 脚本ToolStripMenuItem
            // 
            this.脚本ToolStripMenuItem.Name = "脚本ToolStripMenuItem";
            this.脚本ToolStripMenuItem.Size = new System.Drawing.Size(98, 22);
            this.脚本ToolStripMenuItem.Text = "添加";
            this.脚本ToolStripMenuItem.Click += new System.EventHandler(this.脚本ToolStripMenuItem_Click);
            // 
            // 删除ToolStripMenuItem
            // 
            this.删除ToolStripMenuItem.Name = "删除ToolStripMenuItem";
            this.删除ToolStripMenuItem.Size = new System.Drawing.Size(98, 22);
            this.删除ToolStripMenuItem.Text = "删除";
            this.删除ToolStripMenuItem.Click += new System.EventHandler(this.删除ToolStripMenuItem_Click);
            // 
            // toolStripComboBox1
            // 
            this.toolStripComboBox1.Name = "toolStripComboBox1";
            this.toolStripComboBox1.Size = new System.Drawing.Size(121, 21);
            this.toolStripComboBox1.Text = "选择公共脚本...";
            this.toolStripComboBox1.DropDown += new System.EventHandler(this.toolStripComboBox1_DropDown);
            // 
            // toolStripMenuItem4
            // 
            this.toolStripMenuItem4.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.自定义脚本ToolStripMenuItem,
            this.当前工程脚本ToolStripMenuItem,
            this.toolStripMenuItem5,
            this.javaToolStripMenuItem});
            this.toolStripMenuItem4.Name = "toolStripMenuItem4";
            this.toolStripMenuItem4.Size = new System.Drawing.Size(43, 21);
            this.toolStripMenuItem4.Text = "导出";
            this.toolStripMenuItem4.DropDownOpening += new System.EventHandler(this.当前工程脚本ToolStripMenuItem_DropDownOpening);
            // 
            // 自定义脚本ToolStripMenuItem
            // 
            this.自定义脚本ToolStripMenuItem.Name = "自定义脚本ToolStripMenuItem";
            this.自定义脚本ToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.自定义脚本ToolStripMenuItem.Text = "当前公共脚本";
            this.自定义脚本ToolStripMenuItem.Click += new System.EventHandler(this.自定义脚本ToolStripMenuItem_Click);
            // 
            // 当前工程脚本ToolStripMenuItem
            // 
            this.当前工程脚本ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripMenuItem6});
            this.当前工程脚本ToolStripMenuItem.Name = "当前工程脚本ToolStripMenuItem";
            this.当前工程脚本ToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.当前工程脚本ToolStripMenuItem.Text = "当前工程脚本";
            // 
            // toolStripMenuItem6
            // 
            this.toolStripMenuItem6.Name = "toolStripMenuItem6";
            this.toolStripMenuItem6.Size = new System.Drawing.Size(57, 6);
            // 
            // toolStripMenuItem5
            // 
            this.toolStripMenuItem5.Name = "toolStripMenuItem5";
            this.toolStripMenuItem5.Size = new System.Drawing.Size(143, 6);
            // 
            // javaToolStripMenuItem
            // 
            this.javaToolStripMenuItem.Enabled = false;
            this.javaToolStripMenuItem.Name = "javaToolStripMenuItem";
            this.javaToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.javaToolStripMenuItem.Text = "Java代码";
            this.javaToolStripMenuItem.Visible = false;
            this.javaToolStripMenuItem.Click += new System.EventHandler(this.javaToolStripMenuItem_Click);
            // 
            // 帮助ToolStripMenuItem
            // 
            this.帮助ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.关于ToolStripMenuItem,
            this.toolStripMenuItem7,
            this.显示输出ToolStripMenuItem});
            this.帮助ToolStripMenuItem.Name = "帮助ToolStripMenuItem";
            this.帮助ToolStripMenuItem.Size = new System.Drawing.Size(43, 21);
            this.帮助ToolStripMenuItem.Text = "帮助";
            // 
            // 关于ToolStripMenuItem
            // 
            this.关于ToolStripMenuItem.Name = "关于ToolStripMenuItem";
            this.关于ToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.关于ToolStripMenuItem.Text = "关于";
            this.关于ToolStripMenuItem.Click += new System.EventHandler(this.关于ToolStripMenuItem_Click);
            // 
            // toolStripMenuItem7
            // 
            this.toolStripMenuItem7.Name = "toolStripMenuItem7";
            this.toolStripMenuItem7.Size = new System.Drawing.Size(152, 22);
            this.toolStripMenuItem7.Text = "使用说明";
            this.toolStripMenuItem7.Click += new System.EventHandler(this.toolStripMenuItem7_Click);
            // 
            // 显示输出ToolStripMenuItem
            // 
            this.显示输出ToolStripMenuItem.Name = "显示输出ToolStripMenuItem";
            this.显示输出ToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.显示输出ToolStripMenuItem.Text = "显示输出";
            this.显示输出ToolStripMenuItem.Click += new System.EventHandler(this.显示输出ToolStripMenuItem_Click);
            // 
            // 项目ToolStripMenuItem
            // 
            this.项目ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.选项ToolStripMenuItem,
            this.枚举查看器ToolStripMenuItem,
            this.改变渲染字体ToolStripMenuItem});
            this.项目ToolStripMenuItem.Name = "项目ToolStripMenuItem";
            this.项目ToolStripMenuItem.Size = new System.Drawing.Size(43, 21);
            this.项目ToolStripMenuItem.Text = "工具";
            // 
            // 选项ToolStripMenuItem
            // 
            this.选项ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.CfgOutputEncoding});
            this.选项ToolStripMenuItem.Name = "选项ToolStripMenuItem";
            this.选项ToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.选项ToolStripMenuItem.Text = "输出";
            // 
            // CfgOutputEncoding
            // 
            this.CfgOutputEncoding.Checked = true;
            this.CfgOutputEncoding.CheckOnClick = true;
            this.CfgOutputEncoding.CheckState = System.Windows.Forms.CheckState.Checked;
            this.CfgOutputEncoding.Name = "CfgOutputEncoding";
            this.CfgOutputEncoding.Size = new System.Drawing.Size(170, 22);
            this.CfgOutputEncoding.Text = "输出编码字符信息";
            this.CfgOutputEncoding.CheckedChanged += new System.EventHandler(this.CfgOutputEncoding_CheckedChanged);
            // 
            // 枚举查看器ToolStripMenuItem
            // 
            this.枚举查看器ToolStripMenuItem.Name = "枚举查看器ToolStripMenuItem";
            this.枚举查看器ToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.枚举查看器ToolStripMenuItem.Text = "枚举查看器";
            this.枚举查看器ToolStripMenuItem.Click += new System.EventHandler(this.枚举查看器ToolStripMenuItem_Click);
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // progressBar1
            // 
            this.progressBar1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.progressBar1.Location = new System.Drawing.Point(0, 421);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(706, 23);
            this.progressBar1.Step = 1;
            this.progressBar1.TabIndex = 3;
            // 
            // 改变渲染字体ToolStripMenuItem
            // 
            this.改变渲染字体ToolStripMenuItem.Name = "改变渲染字体ToolStripMenuItem";
            this.改变渲染字体ToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.改变渲染字体ToolStripMenuItem.Text = "改变渲染字体";
            this.改变渲染字体ToolStripMenuItem.Click += new System.EventHandler(this.改变渲染字体ToolStripMenuItem_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(706, 444);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.menuStrip1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.IsMdiContainer = true;
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Cell Game Editor";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form1_FormClosed);
            this.Shown += new System.EventHandler(this.Form1_Shown);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 文件ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 打开ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 保存ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 关闭ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 帮助ToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem 退出ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 新建ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 关于ToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem3;
        private System.Windows.Forms.ToolStripMenuItem toolStripMenuItem4;
        private System.Windows.Forms.ToolStripMenuItem javaToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 自定义脚本ToolStripMenuItem;
        private System.Windows.Forms.ToolStripComboBox toolStripComboBox1;
        private System.Windows.Forms.ToolStripMenuItem 显示输出ToolStripMenuItem;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.ToolStripMenuItem 导入脚本ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 脚本ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 删除ToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem5;
        private System.Windows.Forms.ToolStripMenuItem 当前工程脚本ToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem6;
        private System.Windows.Forms.ToolStripMenuItem toolStripMenuItem7;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.ToolStripMenuItem 另存为toolStripMenuItem8;
        private System.Windows.Forms.ToolStripMenuItem 清理ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 项目ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 选项ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem CfgOutputEncoding;
        private System.Windows.Forms.ToolStripMenuItem 枚举查看器ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 改变渲染字体ToolStripMenuItem;
    }
}

