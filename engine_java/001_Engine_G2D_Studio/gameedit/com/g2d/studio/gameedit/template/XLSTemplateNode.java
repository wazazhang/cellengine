package com.g2d.studio.gameedit.template;

import com.cell.rpg.template.TemplateNode;
import com.cell.xls.XLSFile;
import com.cell.xls.XLSFullRow;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.icon.IconFile;

public abstract class XLSTemplateNode<T extends TemplateNode> extends ObjectNode<T>
{
	final protected T	template_data;
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;

	transient protected IconFile	icon_file;
	
	@SuppressWarnings("unchecked")
	XLSTemplateNode(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) 
	{
		this.xls_file		= xls_file;
		this.xls_fullrow	= xls_row;
		this.template_data	= (data == null) ? newData(xls_file, xls_row) : (T)data;
		this.template_data.name = xls_row.desc;
//		System.out.println("read a xls row : " + xls_file.xls_file + " : " + xls_fullrow.id + " : " + xls_fullrow.desc);

	}
	
	/**
	 * 根据xls创建新的数据对象，当构造函数无法得到数据对象时自动创建
	 * @param xls_file
	 * @param xls_row
	 * @return
	 */
	abstract protected T newData(XLSFile xls_file, XLSFullRow xls_row) ;
	
	/**
	 * 获得当前实时数据对象
	 * @return
	 */
	public T getData() {
		return template_data;
	}
	
	final public XLSFile getXLSFile() {
		return xls_file;
	}
	
	final public XLSFullRow getXLSRow() {
		return xls_fullrow;
	}
	
	@Override
	final public String getID() {
		return getXLSRow().id;
	}
	
	final public int getIntID() {
		return Integer.parseInt(getXLSRow().id);
	}

	@Override
	public String getName() {
		return getXLSRow().desc + "(" + getXLSRow().id + ")";
	}

//	----------------------------------------------------------------------------------------------------------------------
	
	
}
