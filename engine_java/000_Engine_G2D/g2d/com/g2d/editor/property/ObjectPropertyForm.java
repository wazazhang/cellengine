package com.g2d.editor.property;

import java.awt.BorderLayout;

import com.g2d.annotation.Property;
import com.g2d.util.AbstractFrame;


/**
 * 该编辑器可将一个对象内所有的 {@link Property}标注的字段，显示在该控件内，并可以编辑。<br>
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 *
 */
public class ObjectPropertyForm extends AbstractFrame
{
	private static final long serialVersionUID = 1L;

	public ObjectPropertyForm(BaseObjectPropertyPanel panel) {
		super.setLayout(new BorderLayout());
		super.add(panel);
	}
}
