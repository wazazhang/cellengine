package com.cell.rpg.quest;

public enum TriggerUnitType {

	PLAYER			("玩家"),
	PET_GROUP		("携带的宠物组"),
	ACTIVE_PET		("活动的宠物"),
	TRIGGERING_NPC	("触发的NPC"),
	;	
	
	final private String text;
	private TriggerUnitType(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
	
	
}
