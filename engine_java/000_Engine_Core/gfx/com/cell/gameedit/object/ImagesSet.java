package com.cell.gameedit.object;

import com.cell.gameedit.SetObject;

public class ImagesSet implements SetObject
{
	private static final long serialVersionUID = 1L;

	final public int Index;
	final public String Name;
	
	public int Count;
	public int ClipsX[];
	public int ClipsY[];
	public int ClipsW[];
	public int ClipsH[];
	public String ClipsKey[];

	public ImagesSet(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
	
	public int getIndex() {
		return Index;
	}

	public String getName() {
		return Name;
	}

	public int getCount() {
		return Count;
	}

	public int getClipX(int i) {
		return ClipsX[i];
	}

	public int getClipY(int i) {
		return ClipsY[i];
	}

	public int getClipW(int i) {
		return ClipsW[i];
	}

	public int getClipH(int i) {
		return ClipsH[i];
	}

	public String getClipKey(int i) {
		return ClipsKey[i];
	}
}
