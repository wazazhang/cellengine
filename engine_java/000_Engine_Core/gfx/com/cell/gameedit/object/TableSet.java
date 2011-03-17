package com.cell.gameedit.object;

import java.util.Vector;

import com.cell.gameedit.SetObject;

public class TableSet implements SetObject
{
	private static final long serialVersionUID = 1L;

	final public int				Index;
	final public String				Name;
	
	public int				TableCount;
	public Vector<Table> 		Tables 			= new Vector<Table>();

	public TableSet(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
	
	public int getIndex() {
		return Index;
	}
	
	public String getName() {
		return Name;
	}

	public static class Table implements SetObject
	{
		private static final long serialVersionUID = 1L;

		final public int Index;
		final public String Name;
		
		public int ColumnCount;
		public int RowCount;
		public String[] Columns;
		public String[][] Rows;
		

		public Table(int index, String name) {
			this.Index = index;
			this.Name = name;
		}
		
		
		public int getIndex() {
			return Index;
		}
		
		public String getName() {
			return Name;
		}

		public String getColumnHeader(int column_index){
			return Columns[column_index];
		}
		
		public String getCell(int column_index, int row_index){
			return Rows[row_index][column_index];
		}
		
		public String[] getRow(int row_index) {
			return Rows[row_index];
		}
		
		@Override
		public String toString() 
		{
			StringBuilder sb = new StringBuilder(getClass().getName()+"\n");
			
			for (int c = 0; c < ColumnCount; c++) {
				sb.append("| " + Columns[c] + "\t");
			}
			sb.append("\n");
			for (int r = 0; r < RowCount; r++) {
				for (int c = 0; c < ColumnCount; c++) {
					sb.append("| " + Rows[r][c] + "\t");
				}
				sb.append("\n");
			}
			
			return sb.toString();
		}
	}
	
}
	
