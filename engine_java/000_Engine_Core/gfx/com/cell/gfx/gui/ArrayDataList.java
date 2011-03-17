package com.cell.gfx.gui;

import java.util.Vector;

public abstract class ArrayDataList<T> extends DataList
{
	protected T[] SrcArray;
	
	public ArrayDataList(Form form, String title, T[] array, int x, int y, int w, int h, int headh, int cellh, boolean isBeginEnd) {
		super(form, title, x, y, w, h, headh, cellh, isBeginEnd);
		this.setMaxDataCount(SrcArray.length);
	}
	
	public ArrayDataList(Form form, String title, Vector<T> array, int x, int y, int w, int h, int headh, int cellh, boolean isBeginEnd) {
		super(form, title, x, y, w, h, headh, cellh, isBeginEnd);
		SrcArray = (T[]) array.toArray(new Object[array.size()]);
		array.copyInto(SrcArray);
		this.setMaxDataCount(SrcArray.length);
	}


	final protected void updateGridRow(Item[] row, int r) {
		try{
			int si = getPageIndex() * getRowCount() + r;
			if (si < SrcArray.length && SrcArray[si]!=null){
				for (int i=row.length-1; i>=0; --i){
					row[i].setEnable(true, OwnerForm);
				}
				updateGridRow(row, SrcArray[si]);
			}else{
				for (int i=row.length-1; i>=0; --i){
					row[i].setEnable(false, OwnerForm);
				}
			}
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	abstract protected void updateGridRow(Item[] row, T data);
	
}
