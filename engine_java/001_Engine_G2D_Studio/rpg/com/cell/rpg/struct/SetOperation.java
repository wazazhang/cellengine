package com.cell.rpg.struct;

public enum SetOperation
{
	SET("="), 
	ASET("+="), 
	DSET("-="),
	;
	final private String text;
	private SetOperation(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}
