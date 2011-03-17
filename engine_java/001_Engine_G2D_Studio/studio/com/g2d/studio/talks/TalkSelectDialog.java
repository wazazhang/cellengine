package com.g2d.studio.talks;

import java.awt.Component;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;

public class TalkSelectDialog extends G2DListSelectDialog<TalkFile>
{
	private static final long serialVersionUID = 1L;
	
	public TalkSelectDialog(TalkFile dv)
	{
		super(Studio.getInstance().getTalkManager(), new TalkList(), dv);
		super.setTitle("选择一个对话");
	}
	
	public TalkSelectDialog(Component owner, TalkFile dv) {
		super(owner, new TalkList(), dv);
		super.setTitle("选择一个对话");
	}
}
