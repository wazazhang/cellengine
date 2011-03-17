package com.cell.gfx.gui;

import com.cell.CUtil;
import com.cell.gfx.AScreen;
import com.cell.location.IStringCovert;
import com.cell.util.MarkedHashtable;

public abstract class Control extends AScreen 
{

	public boolean IsDebug			= false;
	
	static public int FreezeEventDelay = 10;
	static public int FreezeEventSpeed = 1;

	/** golbal string covert */
	static public IStringCovert LocalCovert = new IStringCovert.DefaultCovert();
	/** golbal Texts */
	static public Strings Text = new Strings('\b', '%', 100);
	static private String[][] ReplaceTable;
	
	static public String processReplaceTable(String text) 
	{
		if (Text!=null) 
		{
			if (ReplaceTable == null) {
				ReplaceTable = Text.getReplaceTable();
			}
			if (text!=null) {
				for (int i = 0; i < ReplaceTable.length; i++) {
					text = CUtil.replaceString(text, ReplaceTable[i][0],ReplaceTable[i][1]);
				}
			}
		}
		return text;
	}
	
	static public boolean isEventKeyHold(int key)
	{
		if(isKeyDown(key)){
			return true;
		}else{
			if(HoldEventTime>FreezeEventDelay && HoldEventTime%FreezeEventSpeed==0){
				return isKeyHold(key);
			}else{
				return false;
			}
		}
	}
	
	static public boolean isEventPointerHold()
	{
		if(isPointerDown()){
			return true;
		}else{
			if(HoldEventTime>FreezeEventDelay && HoldEventTime%FreezeEventSpeed==0){
				return isPointerHold();
			}else{
				return false;
			}
		}
	}
	
	static public boolean isEventKeyHoldState(int key)
	{
		if(isKeyDownState(key)){
			return true;
		}else{
			if(HoldEventTime>FreezeEventDelay && HoldEventTime%FreezeEventSpeed==0){
				return isKeyHoldState(key);
			}else{
				return false;
			}
		}
	}
	
	static public boolean isEventPointerHoldState()
	{
		if(isPointerDownState()){
			return true;
		}else{
			if(HoldEventTime>FreezeEventDelay && HoldEventTime%FreezeEventSpeed==0){
				return isPointerHoldState();
			}else{
				return false;
			}
		}
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/** append extra data */
	public int Tag = 0;
	
	/** append extra data */
	public Object Data = null;
	
	public<T> void setData(T data) {
		Data = data;
	}
	
	@SuppressWarnings("unchecked")
	public<T> T getData() {
		return (T)Data;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/** attributes */
	private MarkedHashtable Attributes;
	
	private boolean hasPropertise = false;
	private boolean hasAttributes = false;
	
	public MarkedHashtable getAttributes() {
		return Attributes;
	}

	// attributes
	@SuppressWarnings("unchecked")
	public<T> T getAttribute(String key) {
		if (Attributes==null) return null;
		return (T)Attributes.get("attribute_"+key);
	}
	
	public Object setAttribute(String key, Object value) {
		if (value==null) {
			Attributes.remove("attribute_"+key);
			if (Attributes.isEmpty()) {
				hasAttributes = false;
			}
			return null;
		}
		if (Attributes==null) {
			Attributes = new MarkedHashtable();
		}
		hasAttributes = true;
		return Attributes.put("attribute_"+key, value);
	}

	// properties
	@SuppressWarnings("unchecked")
	public<T> T getProperty(String key) {
		if (Attributes==null) return null;
		return (T)Attributes.get("property_"+key);
	}
	
	public Object setProperty(String key, Object value) {
		if (value==null) {
			Attributes.remove("property_"+key);
			if (Attributes.isEmpty()) {
				hasPropertise = false;
			}
			return null;
		}
		if (Attributes==null) {
			Attributes = new MarkedHashtable();
		}
		hasPropertise = true;
		return Attributes.put("property_"+key, value);
	}
	
	
	//
	public boolean hasProperties() {
		return hasPropertise;
	}
	
	public boolean hasAttributes() {
		return hasAttributes;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public ControlEventListener EventListener;
	
	
	
	
	
	
	
	
	
}
