using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Runtime.Serialization;
using System.Security.Permissions;
using System.Runtime.Serialization.Formatters;
using System.IO;

namespace CellGameEdit.PM
{
    [Serializable]
    public partial class CommandForm : Form, ISerializable ,IEditForm, IFormatProvider
    {
        public string id;
        
        ArrayList Tables = new ArrayList();
        ArrayList Pages = new ArrayList();

        Hashtable tablemap = new Hashtable();
   


//-----------------------------------------------------------------------------------------------------------------------------------

        public CommandForm(String name)
        {
            InitializeComponent();

            id = name;
            this.Text = id;
            
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        protected CommandForm(SerializationInfo info, StreamingContext context)
        {
            InitializeComponent();
           
            
            try
            {
                id = (String)info.GetValue("id", typeof(String));
                this.Text = id;

                int TableCount = (int)info.GetValue("TableCount", typeof(int));
                ArrayList TableNames = (ArrayList)info.GetValue("TableNames", typeof(ArrayList));

                for (int i = 0; i < TableCount; i++)
                {
                    String name = (String)TableNames[i];

                    appendTable(name);

                    ArrayList heads = (ArrayList)info.GetValue("heads" + i, typeof(ArrayList));
                    ArrayList types = null;
                    ArrayList colors = null;
                    try{
                        types = (ArrayList)info.GetValue("types" + i, typeof(ArrayList));
                        colors = (ArrayList)info.GetValue("colors" + i, typeof(ArrayList));
                    }catch(Exception err){}

                    ArrayList cells         = (ArrayList)info.GetValue("cells" + i, typeof(ArrayList));
                    int ColumnsCount        = (int)info.GetValue("ColumnsCount" + i, typeof(int));
                    int RowsCount           = (int)info.GetValue("RowsCount" + i, typeof(int));
                  
                    for (int c = 0; c < ColumnsCount; c++)
                    {
                        string head = (String)heads[c];
                        getTable(i).Columns.Add(c.ToString(), head);

                        if(types!=null)
                        {
                            Type type = Type.GetType((String)types[c]);
                            if (type != null)
                            {
                                getTable(i).Columns[getTable(i).Columns.Count - 1].ValueType = type;
                            }
                        }

                        if (colors != null)
                        {
                            Color color = (Color)colors[c];
                            getTable(i).Columns[getTable(i).Columns.Count - 1].DefaultCellStyle.BackColor = color;
                        }
                        
                    }

                    for (int r = 0; r < RowsCount; r++)
                    {
                        object[] row = new object[ColumnsCount];

                        for (int c = 0; c < ColumnsCount; c++)
                        {
                            int ci = r * ColumnsCount + c;
                            row[c] = cells[ci];
                        }

                       getTable(i).Rows.Add(row);
                    }


                    try
                    {
                        for (int c = 0; c < ColumnsCount; c++)
                        {
                            ArrayList c_array = (ArrayList)info.GetValue("c_array" + i + "_" + c, typeof(ArrayList));
                            ArrayList c_array_color = null;
                            try {
                                c_array_color = (ArrayList)info.GetValue("c_array_color" + i + "_" + c, typeof(ArrayList));
                            }
                            catch (Exception err) { }

                            Type type = getTable(i).Columns[c].ValueType;
                            
                            for (int r = 0; r < RowsCount; r++)
                            {
                                DataGridViewCell cell = getCell(i, r, c);
                                cell.Value = c_array[r];
                                if (c_array_color != null) {
                                    cell.Style.BackColor = (Color)c_array_color[r];
                                }
                                resetCellType(cell, type);
                            }
                        }
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine(err.Message + "\n" + err.StackTrace);
                    }


                }
              
                

            }catch(Exception err){
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
            }
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public virtual void GetObjectData(SerializationInfo info, StreamingContext context)
        {
           
           info.AddValue("id", id);

           info.AddValue("TableCount",getTableCount());

           ArrayList TableNames = new ArrayList();

           for (int i = 0; i < getTableCount(); i++)
           {
               DataGridView table = getTable(i);

               TableNames.Add(getTableName(i));

               ArrayList heads = new ArrayList();
               ArrayList types = new ArrayList();
               ArrayList colors = new ArrayList();

               ArrayList cells  = new ArrayList();
               int ColumnsCount = table.Columns.Count;
               int RowsCount    = table.Rows.Count - 1;

               for (int c = 0; c < ColumnsCount; c++)
               {
                   string head = getHeadText(i, c);
                   heads.Add(head);

                   Type type = getColumn(i, c).ValueType;
                   types.Add(type!=null?type.FullName:"");

                   Color color = getColumn(i, c).DefaultCellStyle.BackColor;
                   colors.Add(color);
               }

               for (int c = 0; c < table.Columns.Count; c++)
               {
                   Type type = getColumn(i, c).ValueType;
                   ArrayList c_array = new ArrayList();
                   ArrayList c_array_color = new ArrayList();

                   for (int r = 0; r < RowsCount; r++)
                   {
                       DataGridViewCell rc = getCell(i, r, c);
                       rc.ValueType = type;
                       c_array.Add(rc.Value);
                       c_array_color.Add(rc.Style.BackColor);
                   }

                   info.AddValue("c_array" + i + "_" + c, c_array);
                   info.AddValue("c_array_color" + i + "_" + c, c_array_color);
               }

               for (int r = 0; r < RowsCount; r++)
               {
                   for (int c = 0; c < getTable(i).Columns.Count; c++)
                   {
                       int ci = r * getTable(i).Columns.Count + c;
                       string cell = getCellText(i, r, c);
                       cells.Add(cell);
                   }
               }

               info.AddValue("heads" + i, heads);
               info.AddValue("types" + i, types);
               info.AddValue("colors" + i, colors);

               info.AddValue("cells" + i, cells);
               info.AddValue("ColumnsCount" + i, ColumnsCount);
               info.AddValue("RowsCount" + i, RowsCount);

           }

           info.AddValue("TableNames", TableNames);
           
        }


        #region IFormatProvider 成员

        object IFormatProvider.GetFormat(Type formatType)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        #endregion


        public void OutputCustom(int index, String src, System.IO.StringWriter output)
        {
            lock (this)
            {
                String tableGroup = src.Substring(0, src.Length);
                try
                {
                    string command = Util.getTopTrunk(tableGroup, "<TABLE_GROUP>", "</TABLE_GROUP>");
                    if (command != null)
                    {
                        bool fix = false;
                        do
                        {
                            fix = false;
                            string table = fillScriptSub(command, "<TABLE>", "</TABLE>");
                            if (table != null) { command = table; fix = true; }

                        } while (fix);

                        command = Util.replaceKeywordsScript(command, "<TABLE_GROUP>", "</TABLE_GROUP>",
                             new string[] { 
                            "<TABLE_GROUP_NAME>", 
                            "<TABLE_GROUP_INDEX>",
                            "<TABLE_COUNT>",
                            },
                            new string[] { 
                                this.id, 
                                index.ToString(),
                                getTableCount().ToString(),
                            }
                            );
                        tableGroup = Util.replaceSubTrunksScript(tableGroup, "<TABLE_GROUP>", "</TABLE_GROUP>", new string[] { command });

                        output.WriteLine(tableGroup);
                    }
                }
                catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }

            }
        }

        private string fillScriptSub(string src, string start, string end)
        {
            string script = src.Substring(0, src.Length);
            string sub = Util.getTopTrunk(script, start, end);
            if (sub == null) return null;

            ArrayList scripts = new ArrayList();
            try
            {
                for (int i = 0; i < getTableCount(); i++)
                {
                    String ignoreKey = null;
                    if (Util.testIgnore("<IGNORE_TABLE>", sub, getTableName(i), ref ignoreKey) == true)
                    {
                        continue;
                    }

                    String keepKey = null;
                    if (Util.testKeep("<KEEP_TABLE>", sub, getTableName(i), ref keepKey) == false)
                    {
                        continue;
                    }

                    String node = outputSubTable(i,sub);
                    scripts.Add(node);
                }
            }
            catch (Exception err) { MessageBox.Show(err.StackTrace + "  at  " + err.Message); }

            script = Util.replaceSubTrunksScript(script, start, end, (string[])scripts.ToArray(typeof(string)));

            return script;
        }

        private String outputSubTable(int index, String script)
        {

            int ColumnsCount = getTable(index).Columns.Count;
            int RowsCount = getTable(index).Rows.Count - 1;

            //
            String[][] cell_matrix_c_r = new string[ColumnsCount][];
            Type[][] type_matrix_c_r = new Type[ColumnsCount][];
            {
                for (int c = 0; c < ColumnsCount; c++)
                {
                    cell_matrix_c_r[c] = new string[RowsCount];
                    type_matrix_c_r[c] = new Type[RowsCount];

                    Type ctype = getColumn(index, c).ValueType;

                    for (int r = 0; r < RowsCount; r++)
                    {
                        getCell(index, r, c).ValueType = ctype;

                        cell_matrix_c_r[c][r] = getCellText(index, r, c);
                        type_matrix_c_r[c][r] = getCell(index, r, c).ValueType;
                    }
                }
            }

            //
            String[][] cell_matrix_r_c = new string[RowsCount][];
            Type[][] type_matrix_r_c = new Type[RowsCount][];
            {
                for (int r = 0; r < RowsCount; r++)
                {
                    cell_matrix_r_c[r] = new string[ColumnsCount];
                    type_matrix_r_c[r] = new Type[ColumnsCount];

                    for (int c = 0; c < ColumnsCount; c++)
                    {
                        cell_matrix_r_c[r][c] = getCellText(index, r, c);
                        type_matrix_r_c[r][c] = getCell(index, r, c).ValueType;
                    }
                }

            }



            try
            {
                String table = Util.getFullTrunkScript(script, "<TABLE>", "</TABLE>");

                bool fix = false;

                int keep_c = -1;
                int keep_r = -1;
                int ignore_c = -1;
                int ignore_r = -1;

                try
                {
                    keep_c = int.Parse(Util.getCommandScript(table, "<KEEP_COLUMN>"));
                    //Console.WriteLine("Keep Table Column   : " + this.id + " : " + keep_c);
                }
                catch (Exception err) { }

                try
                {
                    keep_r = int.Parse(Util.getCommandScript(table, "<KEEP_ROW>"));
                    //Console.WriteLine("Keep Table Row      : " + this.id + " : " + keep_c);
                }
                catch (Exception err) { }

                try
                {
                    ignore_c = int.Parse(Util.getCommandScript(table, "<IGNORE_COLUMN>"));
                    //Console.WriteLine("Ignore Table Column : " + this.id + " : " + keep_c);
                }
                catch (Exception err) { }

                try
                {
                    ignore_r = int.Parse(Util.getCommandScript(table, "<IGNORE_ROW>"));
                    //Console.WriteLine("Ignore Table Row    : " + this.id + " : " + keep_c);
                }
                catch (Exception err) { }


                // column heads
                do
                {

                    String[] heads = new string[ColumnsCount];

                    for (int c = 0; c < heads.Length; c++)
                    {
                        if (keep_c >= 0 && c != keep_c)
                        {
                            heads[c] = ""; continue;
                        }
                        if (ignore_c >= 0 && c == ignore_c)
                        {
                            heads[c] = ""; continue;
                        }

                        string TEXT = getHeadText(index, c);

                        heads[c] = Util.replaceKeywordsScript(table, "<COLUMN_HEAD>", "</COLUMN HEAD>",
                            new string[] { "<INDEX>", "<TEXT>" },
                            new string[] { c.ToString(), TEXT }
                            );
                    }
                    string temp = Util.replaceSubTrunksScript(table, "<COLUMN_HEAD>", "</COLUMN_HEAD>", heads);
                    if (temp == null)
                    {
                        fix = false;
                    }
                    else
                    {
                        fix = true;
                        table = temp;
                    }
                } while (fix);



                // cells
                do
                {
                    String[] cells = new string[ColumnsCount * RowsCount];

                    for (int r = 0; r < RowsCount; r++)
                    {
                        for (int c = 0; c < ColumnsCount; c++)
                        {
                            int i = r * ColumnsCount + c;

                            if (keep_c >= 0 && c != keep_c)
                            {
                                cells[i] = ""; continue;
                            }
                            if (keep_r >= 0 && r != keep_r)
                            {
                                cells[i] = ""; continue;
                            }

                            if (ignore_c >= 0 && c == ignore_c)
                            {
                                cells[i] = ""; continue;
                            }
                            if (ignore_r >= 0 && r == ignore_r)
                            {
                                cells[i] = ""; continue;
                            }


                            string TEXT = getCellText(index, r, c);
                            if (TEXT == null) TEXT = "";
                            cells[i] = Util.replaceKeywordsScript(table, "<CELL>", "</CELL>",
                                new string[] { "<COLUMN_INDEX>", "<ROW_INDEX>", "<TEXT>" },
                                new string[] { c.ToString(), r.ToString(), TEXT }
                                );
                        }
                    }
                    string temp = Util.replaceSubTrunksScript(table, "<CELL>", "</CELL>", cells);
                    if (temp == null)
                    {
                        fix = false;
                    }
                    else
                    {
                        fix = true;
                        table = temp;
                    }
                } while (fix);



                // rows
                //#<ROWS>                /* 表行数据 开始 横向输出 */
                //    <INDEX>                /* (int)行 号码 */
                //    <ARRAY>                /* (obj)[]行 数据*/
                //    <ARRAY STR>            /* (str)[]行 数据*/
                //    <ARRAY NUM>            /* (int)[]行 数据*/
                //    <ARRAY SMART>          /* (ato)[]行 数据*/
                //#<END ROWS>            /* 表行数据 结束*/
                do
                {
                    String[] rows = new string[RowsCount];
                    for (int r = 0; r < RowsCount; r++)
                    {
                        if (keep_r >= 0 && r != keep_r)
                        {
                            rows[r] = ""; continue;
                        }
                        if (ignore_r >= 0 && r == ignore_r)
                        {
                            rows[r] = ""; continue;
                        }

                        string ARRAY = Util.toArray1D(ref cell_matrix_r_c[r]);
                        string ARRAY_NUM = Util.toNumberArray1D(ref cell_matrix_r_c[r]);
                        string ARRAY_STR = Util.toStringArray1D(ref cell_matrix_r_c[r]);
                        string ARRAY_SMART = Util.toSmartArray1D(ref cell_matrix_r_c[r], type_matrix_r_c[r]);

                        rows[r] = Util.replaceKeywordsScript(table, "<ROWS>", "</ROWS>",
                            new string[] { "<INDEX>", "<ARRAY>", "<ARRAY_STR>", "<ARRAY_NUM>", "<ARRAY_SMART>" },
                            new string[] { r.ToString(), ARRAY, ARRAY_STR, ARRAY_NUM, ARRAY_SMART }
                            );
                    }
                    string temp = Util.replaceSubTrunksScript(table, "<ROWS>", "</ROWS>", rows);
                    if (temp == null)
                    {
                        fix = false;
                    }
                    else
                    {
                        fix = true;
                        table = temp;
                    }
                } while (fix);


                // columns
                //#<COLUMNS>             /* 表列数据 开始 纵向输出 */
                //    <INDEX>                /* (int)列 号码 */
                //    <ARRAY>                /* (obj)[]列 数据*/
                //    <ARRAY STR>            /* (str)[]列 数据*/
                //    <ARRAY NUM>            /* (int)[]列 数据*/
                //    <ARRAY SMART>          /* (ato)[]列 数据*/
                //#<END COLUMNS>         /* 表列数据 结束*/
                do
                {
                    String[] columns = new string[ColumnsCount];
                    for (int c = 0; c < ColumnsCount; c++)
                    {
                        if (keep_c >= 0 && c != keep_c)
                        {
                            columns[c] = ""; continue;
                        }
                        if (ignore_c >= 0 && c == ignore_c)
                        {
                            columns[c] = ""; continue;
                        }

                        string ARRAY = Util.toArray1D(ref cell_matrix_c_r[c]);
                        string ARRAY_NUM = Util.toNumberArray1D(ref cell_matrix_c_r[c]);
                        string ARRAY_STR = Util.toStringArray1D(ref cell_matrix_c_r[c]);
                        string ARRAY_SMART = Util.toSmartArray1D(ref cell_matrix_c_r[c], type_matrix_c_r[c]);

                        columns[c] = Util.replaceKeywordsScript(table, "<COLUMNS>", "</COLUMNS>",
                            new string[] { "<INDEX>", "<ARRAY>", "<ARRAY_STR>", "<ARRAY_NUM>", "<ARRAY_SMART>" },
                            new string[] { c.ToString(), ARRAY, ARRAY_STR, ARRAY_NUM, ARRAY_SMART }
                            );
                    }
                    string temp = Util.replaceSubTrunksScript(table, "<COLUMNS>", "</COLUMNS>", columns);
                    if (temp == null)
                    {
                        fix = false;
                    }
                    else
                    {
                        fix = true;
                        table = temp;
                    }
                } while (fix);




                //matrix
                string matrix = Util.toArray2D(ref cell_matrix_r_c);
                string strMatrix = Util.toStringArray2D(ref cell_matrix_r_c);
                string numMatrix = Util.toNumberArray2D(ref cell_matrix_r_c);
                string smartMatrix = Util.toSmartArray2D(ref cell_matrix_r_c, type_matrix_r_c);

                table = Util.replaceKeywordsScript(table, "<TABLE>", "</TABLE>",
                    new string[] { 
                            "<TABLE_NAME>", 
                            "<TABLE_INDEX>",
                            "<COLUMN_COUNT>",
                            "<ROW_COUNT>",
                            "<TABLE_MATRIX> ",
                            "<TABLE_MATRIX_STR>",         /*(str)[][] 表文字二维数组 */
                            "<TABLE_MATRIX_NUM>",         /*(str)[][] 表数字二维数组 */
                            "<TABLE_MATRIX_SMART>",       /*(str)[][] 表自动二维数组 */
                        },
                    new string[] { 
                            getTableName(index), 
                            index.ToString(),
                            ColumnsCount.ToString(),
                            RowsCount.ToString(),
                            matrix,
                            strMatrix,
                            numMatrix,
                            smartMatrix,
                        }
                 );

                table = outputTableFunctions(table, index);

                return table;

            }
            catch (Exception err) { Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message); }

            return "";

        }

       
        //
        class CFCallFunctionParser : Util.CallFunctionParser
        {
            CommandForm command_form;
            int table_index;

            public CFCallFunctionParser(CommandForm cf, int table)
            {
                this.command_form = cf;
                this.table_index = table;
            }

            public string parse(string function_name, string[] args)
            {
                try
                {
                    if (function_name.Equals("GET CELL"))
                    {
                        if (args.Length == 2)
                        {
                            return command_form.getCellText(table_index, int.Parse(args[1]), int.Parse(args[0]));
                        }
                    }
                }
                catch (Exception err) { }
              
                return "unknow_parser";
            }
        }

        private string outputTableFunctions(string src, int table_index)
        {
            //<CALL GET CELL><COLUMN,ROW>

            src = Util.callFunction(src, "GET CELL", new CFCallFunctionParser(this, table_index));

            return src;
        }



        private void CommandForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.Hide();
            }
        }

        public String getID()
        {
            return id;
        }

        public Form getForm()
        {
            return this;
        }

//-----------------------------------------------------------------------------------------------------------------------------------
        private int getTableCount()
        {
            try
            {
                return Pages.Count;
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
                return -1;
            }
        }

        private DataGridView getTable(TabPage head)
        {
            try
            {
                DataGridView table = (DataGridView)tablemap[head];
                return table;
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
                return null;
            }
        }

        private DataGridView getTable(int index)
        {
            try
            {
                DataGridView table = (DataGridView)tablemap[tabControl1.TabPages[index]];
                return table;
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
                return null;
            }
        }


        private String getTableName(int index)
        {
            try
            {
                return tabControl1.TabPages[index].Text;
            }
            catch (Exception err)
            {
                Console.WriteLine(this.id + " : " + err.StackTrace + "  at  " + err.Message);
                return null;
            }
        }

        private DataGridView getCurTable()
        {
            return getTable(tabControl1.SelectedTab);
        }

        private TabPage getCurPage()
        {
            return tabControl1.SelectedTab;
        }

        private int indexOfTable(DataGridView table)
        {
            for(int i=0;i<tabControl1.TabPages.Count;i++)
            {
                if (table == (DataGridView)tablemap[tabControl1.TabPages[i]])
                {
                    return i;
                }
            }
            return -1;
        }
        
        
        private void addTable(String name)
        {
            if (getTableCount() > 0)
            {
                appendTable(name);

                DataGridViewColumnCollection SrcColumns = getTable(getTableCount() - 2).Columns;
                DataGridViewColumnCollection DstColumns = getTable(getTableCount() - 1).Columns;

                for (int c = 0; c < SrcColumns.Count; c++)
                {
                    string chead = SrcColumns[c].HeaderText;
                    string cname = SrcColumns[c].Name;

                    DstColumns.Add(cname, chead);

                    DstColumns[c].ValueType = SrcColumns[c].ValueType;
                    DstColumns[c].DefaultCellStyle.BackColor = SrcColumns[c].DefaultCellStyle.BackColor;
                  
                }

                getTable(getTableCount() - 1).AutoSizeColumnsMode = getTable(getTableCount() - 2).AutoSizeColumnsMode;

                //for (int r = 0; r < getTable(getTableCount() - 2).Rows.Count-1; r++)
                //{
                //    string[] row = new string[getTable(getTableCount() - 2).Columns.Count];

                //    for (int c = 0; c < getTable(getTableCount() - 2).Columns.Count; c++)
                //    {
                //        int ci = r * getTable(getTableCount() - 2).Columns.Count + c;
                //        row[c] = getCellText(getTableCount() - 2, r, c);
                //    }

                //    getTable(getTableCount() - 1).Rows.Add(row);
                //}
                

            }
            else
            {
                appendTable(name);
            }
        }

        private void copyTable(String name, DataGridView srcTable)
        {
            int src = indexOfTable(srcTable);

            if (getTableCount() > 0 && src>=0)
            {
                appendTable(name);

                DataGridView dstTable = getTable(getTableCount() - 1);

                //for (int c = 0; c < srcTable.Columns.Count; c++)
                //{
                //    string chead = getTable(getTableCount() - 2).Columns[c].HeaderText;
                //    string cname = getTable(getTableCount() - 2).Columns[c].Name;
                //    getTable(getTableCount() - 1).Columns.Add(cname, chead);
                //}
               
                DataGridViewColumnCollection SrcColumns = srcTable.Columns;
                DataGridViewColumnCollection DstColumns = dstTable.Columns;

                for (int c = 0; c < SrcColumns.Count; c++)
                {
                    string chead = SrcColumns[c].HeaderText;
                    string cname = SrcColumns[c].Name;

                    DstColumns.Add(cname, chead);

                    DstColumns[c].ValueType = SrcColumns[c].ValueType;
                    DstColumns[c].DefaultCellStyle.BackColor = SrcColumns[c].DefaultCellStyle.BackColor;

                }

                dstTable.AutoSizeColumnsMode = srcTable.AutoSizeColumnsMode;



                for (int r = 0; r < srcTable.Rows.Count - 1; r++)
                {
                    string[] row = new string[srcTable.Columns.Count];

                    for (int c = 0; c < srcTable.Columns.Count; c++)
                    {
                        int ci = r * srcTable.Columns.Count + c;
                        row[c] = getCellText(src, r, c);
                    }

                    dstTable.Rows.Add(row);
                }
            }
            else
            {
                return;
            }

        }



        private void appendTable(String name)
        {
            //datagridview
            DataGridView table = new System.Windows.Forms.DataGridView();
            //table.AllowUserToOrderColumns = true;
            table.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.None;
            table.ClipboardCopyMode = System.Windows.Forms.DataGridViewClipboardCopyMode.EnableAlwaysIncludeHeaderText;
            table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            table.Dock = System.Windows.Forms.DockStyle.Fill;
            table.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnKeystrokeOrF2;
            table.Location = new System.Drawing.Point(3, 3);
            table.Name = name;
            table.RowTemplate.Height = 23;
            table.Size = new System.Drawing.Size(632, 350);
            table.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            table.ColumnHeaderMouseClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dataGridView1_ColumnHeaderMouseClick);
            table.RowHeaderMouseClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dataGridView1_RowHeaderMouseClick);
            //table.ColumnDisplayIndexChanged += new System.Windows.Forms.DataGridViewColumnEventHandler(this.dataGridView1_ColumnDisplayIndexChanged);
            table.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);

            table.DataError += new DataGridViewDataErrorEventHandler(this.dataGridView1_DataError);

            //table.CellEndEdit += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellEndEdit);
            //table.CellValueChanged += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellValueChanged);

            table.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dataGridView1_RowsAdded);

            table.MouseClick += new MouseEventHandler(this.dataGridView1_MouseClick);
            table.CellClick += new DataGridViewCellEventHandler(this.dataGridView1_CellClick_1);
            table.CellMouseClick += new DataGridViewCellMouseEventHandler(this.dataGridView1_CellMouseClick);
            table.ColumnDisplayIndexChanged += new DataGridViewColumnEventHandler(this.dataGridView1_ColumnDisplayIndexChanged_1);
            

            // tabpage
            TabPage tabPage = new System.Windows.Forms.TabPage();
            tabPage.Location = new System.Drawing.Point(4, 22);
            tabPage.Name = name;
            tabPage.Padding = new System.Windows.Forms.Padding(3);
            tabPage.Size = new System.Drawing.Size(638, 356);
            //tabPage.TabIndex = 0;
            tabPage.Text = tabPage.Name;
            //tabPage.UseVisualStyleBackColor = true;
            //tabPage.ContextMenuStrip = this.tableMenu;

            // data set
            Tables.Add(table);
            Pages.Add(tabPage);
            tablemap.Add(tabPage, table);

            tabPage.Controls.Add(table);
            tabControl1.Controls.Add(tabPage);
        }




//-----------------------------------------------------------------------------------------------------------------------------------


        private string getCellText(int index,int r,int c)
        {   
            string text = "";
            try
            {
                int dc = c;

                if (getTable(index).Columns[c].DisplayIndex != c)
                {
                    foreach (DataGridViewColumn column in getTable(index).Columns)
                    {
                        if (column.DisplayIndex == c)
                        {
                            dc = getTable(index).Columns.IndexOf(column);
                            break;
                        }
                    }
                }

                DataGridViewColumn dcolumn = getTable(index).Columns[dc];
                DataGridViewCell cell = getTable(index)[dc, r];//.Rows[r].Cells[dc];

                resetCellType(cell, dcolumn.ValueType);

                text = cell.Value.ToString();
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message + "\n getCellText");
                //Console.WriteLine(err.Message + "\n getCellText");
                text = "";
            }
            return text;
        }

        private DataGridViewCell getCell(int index, int r, int c)
        {
            try
            {
                int dc = c;
                if (getTable(index).Columns[c].DisplayIndex != c)
                {
                    foreach (DataGridViewColumn column in getTable(index).Columns)
                    {
                        if (column.DisplayIndex == c)
                        {
                            dc = getTable(index).Columns.IndexOf(column);
                            break;
                        }
                    }
                }

                return getTable(index)[dc, r];//.Rows[r].Cells[dc];
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message + "\n getCellText");
                //Console.WriteLine(err.Message + "\n getCell");
            }

            return null;
        }



        private string getHeadText(int index, int c)
        {
            string text = "";
            try
            {
                int dc = c;
                if (getTable(index).Columns[c].DisplayIndex != c)
                {
                    foreach (DataGridViewColumn column in getTable(index).Columns)
                    {
                        if (column.DisplayIndex == c)
                        {
                            dc = getTable(index).Columns.IndexOf(column);
                            break;
                        }
                    }
                }
                text = getTable(index).Columns[dc].HeaderText;
            }
            catch (Exception err)
            {
                text = "";
            }
            return text;
        }

        private DataGridViewColumn getColumn(int index, int c)
        {
            try
            {
                int dc = c;
                if (getTable(index).Columns[c].DisplayIndex != c)
                {
                    foreach (DataGridViewColumn column in getTable(index).Columns)
                    {
                        if (column.DisplayIndex == c)
                        {
                            return column;
                        }
                    }
                }
                else
                {
                    return getTable(index).Columns[c];
                }
            }
            catch (Exception err)
            {
            }
            return null;
        }




        private void resetCellType(DataGridViewCell cell, Type type)
        {
            if (cell != null && type!=null)
            {
                {
                    try
                    {
                        cell.ValueType = type;
                        cell.Value = Convert.ChangeType(cell.Value, type);
                    }
                    catch (Exception err)
                    {
                        if (type == typeof(Decimal))
                        {
                            try
                            {
                                cell.Value = Decimal.Parse(cell.ToString().Trim());
                            }
                            catch (Exception err2)
                            {
                                cell.Value = new Decimal(0);
                            }
                        }

                        Console.WriteLine(err.Message + "\n" + err.StackTrace);
                    }
                }
            }

            
        }

        private void setCell(int i, int r, int c, object data)
        {
            DataGridViewCell cell = getCell(i, r, c);

            if (cell != null)
            {
                if (cell.ValueType != null)
                {
                    try
                    {
                        cell.Value = Convert.ChangeType(data, cell.ValueType);
                    }
                    catch (Exception err)
                    {
                        Console.WriteLine("setCell("+i+","+r+","+c+","+data+") : " + err.Message);
                    }
                }



            }

        }


//----------------------------------------------------------------------------------------------------------------------------------------------------------


        // add column
        private void 列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String name = "ColumnName";
            TextDialog nameDialog = new TextDialog(name);
            if (nameDialog.ShowDialog() == DialogResult.OK)
            {
                for (int i = 0; i < getTableCount(); i++)
                {
                    getTable(i).Columns.Add(getTable(i).ColumnCount.ToString(), nameDialog.getText());
                }
            }

        }

        private void 文本列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String name = "ColumnName";
            TextDialog nameDialog = new TextDialog(name);
            if (nameDialog.ShowDialog() == DialogResult.OK)
            {
                for (int i = 0; i < getTableCount(); i++)
                {
                    getTable(i).Columns.Add(getTable(i).ColumnCount.ToString() , nameDialog.getText());

                    getTable(i).Columns[getTable(i).Columns.Count - 1].ValueType = typeof(String);
                    getTable(i).Columns[getTable(i).Columns.Count - 1].DefaultCellStyle.BackColor = Color.FromArgb(0xc0, 0xc0, 0xff);

                }
            }

        }

        private void 数字列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String name = "ColumnName";
            TextDialog nameDialog = new TextDialog(name);
            if (nameDialog.ShowDialog() == DialogResult.OK)
            {
                for (int i = 0; i < getTableCount(); i++)
                {
                    getTable(i).Columns.Add(getTable(i).ColumnCount.ToString(), nameDialog.getText());

                    getTable(i).Columns[getTable(i).Columns.Count - 1].ValueType = typeof(Decimal);
                    getTable(i).Columns[getTable(i).Columns.Count - 1].DefaultCellStyle.BackColor = Color.FromArgb(0xff, 0xc0, 0xc0);

                }
            }

        }



        // delete column
        int PopedColumnIndex = -1;
        private void resetAllCell(DataGridViewColumn column)
        {
            try
            {

                for (int r = 0; r < getCurTable().Rows.Count - 1; r++)
                {
                    DataGridViewCell cell = getCurTable()[column.Index, r];

                    resetCellType(cell, column.ValueType);
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }

        }
        private void dataGridView1_ColumnHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            try
            {
                DataGridViewColumn column = getCurTable().Columns[e.ColumnIndex];

                for (int r = 0; r < getCurTable().Rows.Count-1; r++)
                {
                    DataGridViewCell cell = getCurTable()[e.ColumnIndex, r];
                   
                    if (cell.ValueType != column.ValueType) cell.ValueType = column.ValueType;
                   
                    resetCellType(cell, column.ValueType);
                }
              

                if (e.Button == MouseButtons.Right)
                {
                    PopedColumnIndex = e.ColumnIndex;

                    columnMenu.Show(
                        getCurTable(),
                        e.Location.X + getCurTable().GetColumnDisplayRectangle(e.ColumnIndex, false).Location.X,
                        e.Location.Y + getCurTable().GetColumnDisplayRectangle(e.ColumnIndex, false).Location.Y
                        );
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }

        }
        private void dataGridView1_ColumnDisplayIndexChanged(object sender, DataGridViewColumnEventArgs e)
        {
            try
            {

                //int dst = e.Column.DisplayIndex;
                //int src = Int32.Parse(e.Column.Name);
                //e.Column.Name = dst.ToString();

                //Console.WriteLine(" column=" + dst);

                //for (int i = 0; i < getTableCount(); i++)
                //{
                //    if (i != Tables.IndexOf(e.Column.DataGridView))
                //    {
                //        getColumn(i, src).DisplayIndex = dst;
                //    }
                //}

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }


        }


        private void 删除ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                for (int i = 0; i < getTableCount(); i++)
                {
                    getTable(i).Columns.RemoveAt(PopedColumnIndex);
                }
                
            }
            catch (Exception err) { }
        }

        private void 重命名列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                String name = getCurTable().Columns[PopedColumnIndex].HeaderText;
                TextDialog nameDialog = new TextDialog(name);
                if (nameDialog.ShowDialog() == DialogResult.OK)
                {
                    for (int i = 0; i < getTableCount(); i++)
                    {
                        getTable(i).Columns[PopedColumnIndex].HeaderText = nameDialog.getText();
                    }
                }
                
            }
            catch (Exception err) { }
        }

        private void 填充该列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DataGridView table = getCurTable();

            TextDialog dialog = new TextDialog("");

            if (dialog.ShowDialog() == DialogResult.OK)
            {
                string value = dialog.getText();

                for (int r = 0; r < table.Rows.Count - 1; r++)
                {
                    //getCell(indexOfTable(table), r, PopedColumnIndex).Value = value;

                    try
                    {
                        DataGridViewCell cell = getCell(indexOfTable(table), r, PopedColumnIndex);

                        cell.Value = Convert.ChangeType(value, cell.ValueType);

                    }
                    catch (Exception err) { Console.WriteLine(err.Message + "\n" + err.StackTrace); }
                }
            }

            table.Refresh();
   
        }

        private void 粘贴填充ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try {
                if (Clipboard.ContainsText())
                {
                    String[] lines = Clipboard.GetText().Split(new char[] { '\n' });

                    DataGridView table = getCurTable();

                    for (int r = 0; r < table.Rows.Count - 1; r++)
                    {
                        if (r < lines.Length)
                        {
                            try
                            {
                                DataGridViewCell cell = getCell(indexOfTable(table), r, PopedColumnIndex);

                                cell.Value = Convert.ChangeType(lines[r], cell.ValueType);

                            }
                            catch (Exception err) { Console.WriteLine(err.Message + "\n" + err.StackTrace); }
                        }
                    }
                    
                    table.Refresh();
                }
            }
            catch (Exception err) { }
        }

        //find
        //search
        private void 替换ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            TextDialog d = new TextDialog(getCurTable(), PopedColumnIndex);
            d.showDialog();
        }


        // delete row
        int PopedRowIndex = -1;
        private void dataGridView1_RowHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {

            try
            {
                if (e.Button == MouseButtons.Right)
                {
                    PopedRowIndex = e.RowIndex;
                    rowMenu.Show(
                        getCurTable(),
                        e.Location.X + getCurTable().GetRowDisplayRectangle(e.RowIndex, false).Location.X,
                        e.Location.Y + getCurTable().GetRowDisplayRectangle(e.RowIndex, false).Location.Y
                        );
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }



        }

        private void 删除行ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                //for (int i = 0; i < getTableCount(); i++)
                //{
                    getCurTable().Rows.RemoveAt(PopedRowIndex);
                //}
                
                
            }
            catch (Exception err) { }
        }
        private void 删除选择的ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                //foreach (DataGridViewRow r in getCurTable().Rows)
                //{

                //}

                foreach (DataGridViewRow sr in getCurTable().SelectedRows)
                {
                    getCurTable().Rows.Remove(sr);
                }
             
                

            }
            catch (Exception err) { }
        }
        private void 插入行ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                DataGridView table = getCurTable();


                getCurTable().Rows.InsertCopy(PopedRowIndex, PopedRowIndex + 1);
                

            }
            catch (Exception err) { }
        }
        private void 粘贴所选ToolStripMenuItem2_Click(object sender, EventArgs e)
        {
            parseAllCell();
        }
        private void 复制所选ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            copyAllCell();
        }
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
 


        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }


        private void dataGridView1_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            try
            {

                MessageBox.Show(e.Exception.Message);
                e.ThrowException = false;
                e.Cancel = false;

               // getCurTable()[e.ColumnIndex, e.RowIndex].Value = "";
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

       

        private void dataGridView1_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            try
            {

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

        private void dataGridView1_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            try
            {

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
                getCurTable()[e.ColumnIndex,e.RowIndex].Value = "";
            }
        }
        

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            dataLable.Text = "表=" + tabControl1.TabPages.IndexOf(getCurPage());
            dataLable.Text += " 行=" + e.RowIndex;
            try
            {
                dataLable.Text += " 列=" + getCurTable().Columns[e.ColumnIndex].DisplayIndex;
            }
            catch (Exception err)
            {
            }
        }

        private void dataGridView1_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            
        }

        

        private void dataGridView1_MouseClick(object sender, MouseEventArgs e)
        {
          

        }

        private void dataGridView1_CellClick_1(object sender, DataGridViewCellEventArgs e)
        {
            
        }

        private void dataGridView1_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            try
            {
                DataGridView table = getCurTable();

                if (e.Button == MouseButtons.Right)
                {
                    PopedRowIndex = e.RowIndex;
                    PopedColumnIndex = e.ColumnIndex;

                    cellMenu.Show(
                        table,
                        e.Location.X + table.GetCellDisplayRectangle(e.ColumnIndex, e.RowIndex, false).Location.X,
                        e.Location.Y + table.GetCellDisplayRectangle(e.ColumnIndex, e.RowIndex, false).Location.Y
                        );
                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

        private void dataGridView1_ColumnDisplayIndexChanged_1(object sender, DataGridViewColumnEventArgs e)
        {
            try
            {
                if (e.Column.Index != e.Column.DisplayIndex)
                {

                    for (int t = 0; t < getTableCount(); t++)
                    {
                        DataGridView table = getTable(t);

                        foreach (DataGridViewColumn column in getTable(t).Columns)
                        {
                            if (column.Index != column.DisplayIndex) 
                            {
                                Console.WriteLine("DisplayIndex not equal Index !!!");
                            }
                        }
                    }
                }



            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }


//----------------------------------------------------------------------------------------------------------------------------------------------------------
        private void parseAllCell()
        {
            try
            {
                if (Clipboard.ContainsText())
                {
                    String text = Clipboard.GetText();
                    String[] lines = text.Split(new String[] { "\n" }, StringSplitOptions.None);
                    String[][] cells = new String[lines.Length][];
                    for (int r = 0; r < cells.Length; r++)
                    {
                        cells[r] = lines[r].Split(new String[] { "\t" }, StringSplitOptions.None);
                        for (int c = 0; c < cells[r].Length; c++)
                        {
                            cells[r][c] = cells[r][c].Trim();
                        }
                    }

                    DataGridView table = getCurTable();
                    int tableIndex = indexOfTable(table);

                    if (table.SelectedCells.Count > 0)
                    {
                        int prmax = int.MinValue;
                        int pcmax = int.MinValue;
                        int prmin = int.MaxValue;
                        int pcmin = int.MaxValue;
                        foreach (DataGridViewCell cell in table.SelectedCells)
                        {
                            prmax = Math.Max(prmax, cell.RowIndex);
                            pcmax = Math.Max(pcmax, cell.ColumnIndex);
                            prmin = Math.Min(prmin, cell.RowIndex);
                            pcmin = Math.Min(pcmin, cell.ColumnIndex);
                        }

                        Console.WriteLine("SRow:" + prmin + " SColumn:" + pcmin + " DRow:" + prmax + " DColumn:" + pcmax);

                        for (int r = 0, cellr = prmin; r < cells.Length && cellr <= prmax; r++, cellr++)
                        {
                            for (int c = 0, cellc = pcmin; c < cells[r].Length && cellc <= pcmax; c++, cellc++)
                            {
                                if (cellr < table.Rows.Count && cellc < table.Columns.Count)
                                {
                                    setCell(tableIndex, cellr, cellc, cells[r][c]);
                                }
                            }
                        }
                        
                    }

                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }
        private void copyAllCell()
        {
            try
            {
               
                DataGridView table = getCurTable();
                int tableIndex = indexOfTable(table);

                if (table.SelectedCells.Count > 0)
                {
                     String text = "";

                    int prmax = int.MinValue;
                    int pcmax = int.MinValue;
                    int prmin = int.MaxValue;
                    int pcmin = int.MaxValue;
                    foreach (DataGridViewCell cell in table.SelectedCells)
                    {
                        prmax = Math.Max(prmax, cell.RowIndex);
                        pcmax = Math.Max(pcmax, cell.ColumnIndex);
                        prmin = Math.Min(prmin, cell.RowIndex);
                        pcmin = Math.Min(pcmin, cell.ColumnIndex);
                    }

                    Console.WriteLine("SRow:" + prmin + " SColumn:" + pcmin + " DRow:" + prmax + " DColumn:" + pcmax);

                    for (int cellr = prmin; cellr <= prmax; cellr++)
                    {
                        for (int cellc = pcmin; cellc <= pcmax; cellc++)
                        {
                            if (cellr < table.Rows.Count && cellc < table.Columns.Count)
                            {
                                text += getCellText(tableIndex, cellr, cellc) + "\t";
                            }
                        }
                        text += "\n";
                    }

                    Clipboard.SetText(text);

                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }
        private void setAllCellColor(Color color)
        {
            try
            {

                DataGridView table = getCurTable();
                int tableIndex = indexOfTable(table);

                if (table.SelectedCells.Count > 0)
                {
                    int prmax = int.MinValue;
                    int pcmax = int.MinValue;
                    int prmin = int.MaxValue;
                    int pcmin = int.MaxValue;
                    foreach (DataGridViewCell cell in table.SelectedCells)
                    {
                        prmax = Math.Max(prmax, cell.RowIndex);
                        pcmax = Math.Max(pcmax, cell.ColumnIndex);
                        prmin = Math.Min(prmin, cell.RowIndex);
                        pcmin = Math.Min(pcmin, cell.ColumnIndex);
                    }

                    Console.WriteLine("SRow:" + prmin + " SColumn:" + pcmin + " DRow:" + prmax + " DColumn:" + pcmax);

                    for (int cellr = prmin; cellr <= prmax; cellr++)
                    {
                        for (int cellc = pcmin; cellc <= pcmax; cellc++)
                        {
                            if (cellr < table.Rows.Count && cellc < table.Columns.Count)
                            {
                                DataGridViewCell cell = getCell(tableIndex, cellr, cellc);
                                cell.Style.BackColor = color;
                            }
                        }

                    }
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }
        private void clearAllCellColor()
        {
            try
            {

                DataGridView table = getCurTable();
                int tableIndex = indexOfTable(table);

                if (table.SelectedCells.Count > 0)
                {
                    int prmax = int.MinValue;
                    int pcmax = int.MinValue;
                    int prmin = int.MaxValue;
                    int pcmin = int.MaxValue;
                    foreach (DataGridViewCell cell in table.SelectedCells)
                    {
                        prmax = Math.Max(prmax, cell.RowIndex);
                        pcmax = Math.Max(pcmax, cell.ColumnIndex);
                        prmin = Math.Min(prmin, cell.RowIndex);
                        pcmin = Math.Min(pcmin, cell.ColumnIndex);
                    }

                    Console.WriteLine("SRow:" + prmin + " SColumn:" + pcmin + " DRow:" + prmax + " DColumn:" + pcmax);

                    for (int cellr = prmin; cellr <= prmax; cellr++)
                    {
                        for (int cellc = pcmin; cellc <= pcmax; cellc++)
                        {
                           DataGridViewColumn colum = getColumn(tableIndex, cellc);

                            if (cellr < table.Rows.Count && cellc < table.Columns.Count)
                            {
                                DataGridViewCell cell = getCell(tableIndex, cellr, cellc);
                                cell.Style.BackColor = colum.DefaultCellStyle.BackColor;
                            }
                        }

                    }
                }

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

        private void 粘贴所选ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            parseAllCell();
        }
        private void 复制所选ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            copyAllCell();
        }
//----------------------------------------------------------------------------------------------------------------------------------------------------------
 
        private void 表头样式_DropDownOpening(object sender, EventArgs e)
        {
            try
            {
                 DataGridViewAutoSizeColumnMode[] ColumnModes = new DataGridViewAutoSizeColumnMode[]
                {
                    DataGridViewAutoSizeColumnMode.None,
                    DataGridViewAutoSizeColumnMode.AllCells,
                    DataGridViewAutoSizeColumnMode.AllCellsExceptHeader,
                    DataGridViewAutoSizeColumnMode.ColumnHeader,
                    DataGridViewAutoSizeColumnMode.DisplayedCells,
                    DataGridViewAutoSizeColumnMode.DisplayedCellsExceptHeader,
                    DataGridViewAutoSizeColumnMode.Fill,
                };

                表头样式.DropDownItems.Clear();

                for (int i = 0; i < ColumnModes.Length; i++)
                {
                    ToolStripMenuItem item = new ToolStripMenuItem();
                    item.Text = ColumnModes[i].ToString();
                    item.AutoSize = true;
                    item.Click += new EventHandler(表头样式Item_Click);
                    表头样式.DropDownItems.Add(item);
                    item.Tag = ColumnModes[i];
                }
             
                
            }
            catch (Exception err)
            {
            }

        }
        private void 表头样式Item_Click(object sender, EventArgs e)
        {
            try{
                ToolStripMenuItem item = (ToolStripMenuItem)sender;
                for (int i = 0; i < getTableCount(); i++)
                {
                    getTable(i).AutoSizeColumnsMode = (DataGridViewAutoSizeColumnsMode)item.Tag;
                }

            }catch(Exception err){
            }
        }
        private void 添加表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
            String name = "TableName";
            TextDialog nameDialog = new TextDialog(name);
            if (nameDialog.ShowDialog() == DialogResult.OK)
            {
                addTable(nameDialog.getText());
            }
           
        }
        private void 重命名当前表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                TabPage page = getCurPage();
                String name = page.Text;
                TextDialog nameDialog = new TextDialog(name);
                if (nameDialog.ShowDialog() == DialogResult.OK)
                {
                    page.Text = nameDialog.getText();
                }
            }
            catch (Exception err) { }
        }
        private void 复制当前表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String name = getCurPage().Text+"_Copy";

            copyTable(name, getCurTable());


            
        }
        private void 删除当前表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                TabPage page = getCurPage();
                DataGridView table = getTable(page);

                Pages.Remove(page);
                tablemap.Remove(page);
                Tables.Remove(table);

                try
                {
                    tabControl1.SelectedIndex = tabControl1.TabPages.IndexOf(page) - 1;
                }
                catch (Exception err) { }

                tabControl1.TabPages.Remove(page);

                
                
            }
            catch (Exception err) { }
        }

        //----------------------------------------------------------------------------------------------------------------------------------------------------------
 
        private void exchageTable(int src,int dst)
        {
            tabControl1.SuspendLayout();

            TabPage page1 = tabControl1.TabPages[src];
            TabPage page2 = tabControl1.TabPages[dst];

            tabControl1.TabPages[src] = page2;
            tabControl1.TabPages[dst] = page1;

            tabControl1.ResumeLayout();
        }

        //tablle move left
        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            int src = tabControl1.TabPages.IndexOf(tabControl1.SelectedTab);
            int dst = src - 1;
            if (src > 0)
            {
                exchageTable(src, dst);

                tabControl1.SelectedTab = tabControl1.TabPages[dst];
            }

            //tabControl1.SelectedTab.
        }
        //table move right
        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            int src = tabControl1.TabPages.IndexOf(tabControl1.SelectedTab);
            int dst = src + 1;
            if (dst < tabControl1.TabCount)
            {
                exchageTable(src, dst);

                tabControl1.SelectedTab = tabControl1.TabPages[dst];
            }
        }
        private void exchageColumn(int src, int dst)
        {

            for (int i = 0; i < getTableCount(); i++)
            {
                DataGridView table = getTable(i);
                table.SuspendLayout();
                {

                    DataGridViewColumn scolumn = getColumn(i, src);
                    DataGridViewColumn dcolumn = getColumn(i, dst);

                    //scolumn.DisplayIndex = dst;
                    //dcolumn.DisplayIndex = src;

                    DataGridViewColumnHeaderCell shc = scolumn.HeaderCell;
                    DataGridViewColumnHeaderCell dhc = dcolumn.HeaderCell;
                    scolumn.HeaderCell = dhc;
                    dcolumn.HeaderCell = shc;
                    /*
                    String shead = scolumn.HeaderText;
                    String dhead = dcolumn.HeaderText;
                    scolumn.HeaderText = dhead;
                    dcolumn.HeaderText = shead;
                    */

                    Type stype = scolumn.ValueType;
                    Type dtype = dcolumn.ValueType;
                    scolumn.ValueType = dtype;
                    dcolumn.ValueType = stype;

                    Color scolor = scolumn.DefaultCellStyle.BackColor;
                    Color dcolor = dcolumn.DefaultCellStyle.BackColor;
                    scolumn.DefaultCellStyle.BackColor = dcolor;
                    dcolumn.DefaultCellStyle.BackColor = scolor;


                    foreach (DataGridViewRow row in table.Rows)
                    {
                        DataGridViewCell sc = row.Cells[scolumn.Index];
                        DataGridViewCell dc = row.Cells[dcolumn.Index];

                        stype = sc.ValueType;
                        dtype = dc.ValueType;
                        sc.ValueType = dtype;
                        dc.ValueType = stype;

                        Object sv = sc.Value;
                        Object dv = dc.Value;
                        sc.Value = dv;
                        dc.Value = sv;

                        Boolean ssel = sc.Selected;
                        Boolean dsel = dc.Selected;
                        sc.Selected = dsel;
                        dc.Selected = ssel;

                        try
                        {
                            if (sc.ValueType != null && sc.Value != null) sc.Value = Convert.ChangeType(sc.Value, sc.ValueType);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(err.Message);
                        }

                        try
                        {
                            if (dc.ValueType != null && dc.Value != null) dc.Value = Convert.ChangeType(dc.Value, dc.ValueType);
                        }
                        catch (Exception err)
                        {
                            Console.WriteLine(err.Message);
                        }

                    }
                }
                table.ResumeLayout();
            }
            
        }
        //column move left
        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            int src = getCurTable().Columns[getCurTable().SelectedCells[0].ColumnIndex].DisplayIndex;
            int dst = src - 1;
            if (src > 0)
            {
                exchageColumn(src, dst);
            }
        }
        //column move right
        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            int src = getCurTable().Columns[getCurTable().SelectedCells[0].ColumnIndex].DisplayIndex;
            int dst = src + 1;
            if (dst < getCurTable().ColumnCount)
            {
                exchageColumn(src, dst);
            }

        }
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
 
        private void 文本ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            for (int t = 0; t < getTableCount(); t++)
            {
                DataGridView table = getTable(t);

                table.Columns[PopedColumnIndex].ValueType = typeof(String);
                table.Columns[PopedColumnIndex].DefaultCellStyle.BackColor = Color.FromArgb(0xc0, 0xc0, 0xff);

                //for (int r = 0; r < table.Rows.Count - 1; r++)
                //{
                //    getCell(indexOfTable(table), r, PopedColumnIndex).ValueType = typeof(String);
                //    getCell(indexOfTable(table), r, PopedColumnIndex).Style.BackColor = Color.FromArgb(0xc0, 0xc0, 0xff);
                //}
                resetAllCell(table.Columns[PopedColumnIndex]);
                table.Refresh();
            }
        }

        private void 数字ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            for (int t = 0; t < getTableCount(); t++)
            {
                DataGridView table = getTable(t);

                table.Columns[PopedColumnIndex].ValueType = typeof(Decimal);
                table.Columns[PopedColumnIndex].DefaultCellStyle.BackColor = Color.FromArgb(0xff, 0xc0, 0xc0);

                //for (int r = 0; r < table.Rows.Count - 1; r++)
                //{
                //    getCell(indexOfTable(table), r, PopedColumnIndex).ValueType = typeof(Decimal);
                //    getCell(indexOfTable(table), r, PopedColumnIndex).Style.BackColor = Color.FromArgb(0xff, 0xc0, 0xc0);
                //}
                resetAllCell(table.Columns[PopedColumnIndex]);
                table.Refresh();
            }
        }

        private void 默认的ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            for (int t = 0; t < getTableCount(); t++)
            {
                DataGridView table = getTable(t);

                table.Columns[PopedColumnIndex].ValueType = typeof(Object);
                table.Columns[PopedColumnIndex].DefaultCellStyle.BackColor = Color.FromArgb(0xff, 0xff, 0xff);

                //for (int r = 0; r < table.Rows.Count - 1; r++)
                //{
                //    getCell(indexOfTable(table), r, PopedColumnIndex).ValueType = typeof(Object);
                //    getCell(indexOfTable(table), r, PopedColumnIndex).Style.BackColor = Color.FromArgb(0xff, 0xff, 0xff);
                //}
                resetAllCell(table.Columns[PopedColumnIndex]);
                table.Refresh();
            }
        }

        private void 复制所选ToolStripMenuItem2_Click(object sender, EventArgs e)
        {
            copyAllCell();
        }
        private void 粘贴所选ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            parseAllCell();
        }

        private void toolStripButton5_Click(object sender, EventArgs e)
        {

        }

        private void toolStripButton6_Click(object sender, EventArgs e)
        {

        }

        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void 全选ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (DataGridViewColumn column in getCurTable().Columns)
                {
                    column.Selected = false;
                }
                getColumn(indexOfTable(getCurTable()), PopedColumnIndex).Selected = true;
            }
            catch (Exception err)
            {
            }
        }

        private void 插入列ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                DataGridViewColumn column = new DataGridViewColumn();
               
                getCurTable().Columns.Insert(PopedColumnIndex, column);
            }
            catch (Exception err) {
            }
        }

        private void btnSetCellColor_Click(object sender, EventArgs e)
        {
            ColorDialog cd = new ColorDialog();
            cd.Color = btnSetCellColor.BackColor;
            if (cd.ShowDialog() == DialogResult.OK) 
            {
                setAllCellColor(cd.Color);
                btnSetCellColor.BackColor = cd.Color;
            }
        }

        private void btnSetCellColor_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                setAllCellColor(btnSetCellColor.BackColor);
            }
        }

        private void btnClearCellColor_Click(object sender, EventArgs e)
        {
            clearAllCellColor();
        }

     

   
     

  

    

   


    }
}