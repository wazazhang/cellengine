package com.cell.bms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.cell.bms.BMSFile.HeadInfo;
import com.cell.bms.BMSFile.Note;
import com.cell.bms.BMSFile.NoteDefine;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;

public interface BMSPlayerListener
{
	public void onBeat(BMSPlayer player, int beat_count);

	public void onDropNote(BMSPlayer player, Note note);
}
