package com.cell.gameedit.object;

import com.cell.gameedit.SetObject;

public class SpriteSet implements SetObject
{
	private static final long serialVersionUID = 1L;
	
	final public int Index;
	final public String Name;
	
	public String ImagesName;

	public short[] PartX;
	public short[] PartY;
	public short[] PartTileID;
	public byte[] PartTileTrans;
	public short[][] Parts;

	public int[] BlocksMask;
	public short[] BlocksX1;
	public short[] BlocksY1;
	public short[] BlocksW;
	public short[] BlocksH;
	public short[][] Blocks;

	public int AnimateCount;
	public String[] AnimateNames;
	public short[][] FrameAnimate;
	public short[][] FrameCDMap;
	public short[][] FrameCDAtk;
	public short[][] FrameCDDef;
	public short[][] FrameCDExt;

	public SpriteSet(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
	
	
	public int getIndex() {
		return Index;
	}

	public String getName() {
		return Name;
	}

	// images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
	public int getPartImageIndex(int anim, int frame, int subpart) {
		return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	public int getPartTrans(int anim, int frame, int subpart) {
		return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	public int getPartX(int anim, int frame, int subpart) {
		return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	public int getPartY(int anim, int frame, int subpart) {
		return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
	}

}
