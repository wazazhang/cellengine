package com.g2d.studio.talks;

import java.util.Vector;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;

public class TalkList extends G2DList<TalkFile>
{
	private static final long serialVersionUID = 1L;

	TalkList(Vector<TalkFile> icons) {
		super(icons);
	}
	
	TalkList() {
		super(Studio.getInstance().getTalkManager().getTalks());
	}
}