package com.cell.rpg.quest;

public enum QuestState {

	NA,
	
	ACCEPTED,
	COMPLETED,
	ACCEPTED_OR_COMPLETED,

	NOT_ACCEPTED,
	NOT_COMPLETED,
	NOT_ACCEPTED_OR_NOT_COMPLETED,
	;
	
	public String toString() {
		return super.toString();
	}
	
	public String toTinyString() {
		switch (this) {
		case NA:
			return "NA";
		case ACCEPTED:
			return "ACC";
		case COMPLETED:
			return "COM";
		case ACCEPTED_OR_COMPLETED:
			return "A_C";
		case NOT_ACCEPTED:
			return "nACC";
		case NOT_COMPLETED:
			return "nCOM";
		case NOT_ACCEPTED_OR_NOT_COMPLETED:
			return "nA_nC";
		}
		return "null";
	}
}
