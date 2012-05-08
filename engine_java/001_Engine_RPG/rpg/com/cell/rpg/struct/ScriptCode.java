package com.cell.rpg.struct;

import java.io.Serializable;


public class ScriptCode implements Serializable
{
	private static final long serialVersionUID = 1L;

	public String	script = "";

	@Override
	public String toString() {
		return script + "";
	}
}
