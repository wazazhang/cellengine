package com.cell.rpg.scene;

public enum EUnitType 
{
	ACTOR,
	IMMUTABLE,
	EFFECT,
	POINT,
	REGION,
	;
	
	public String toString() {
		switch(this) {
		case ACTOR:		return "单位";
		case IMMUTABLE:	return "不可破坏";
		case EFFECT:	return "特效";
		case POINT:		return "路点";
		case REGION:	return "区域";
		}
		return super.toString();
	}
}
