package com.g2d.studio.ui.edit;

import com.g2d.studio.swing.G2DList;

public class UITemplateList extends G2DList<UITemplate>
{
	public UITemplateList(UIEdit edit) {
		super(edit.getLayoutManager().getTemplates());
	}
}
