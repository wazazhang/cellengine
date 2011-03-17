package com.cell.gfx.gui;

import java.util.Hashtable;
import java.util.Vector;

import com.cell.gfx.IImage;

public class Command
{
	final static public int DISABLED= -2;
	final static public int CANCEL 	= -1;
	final static public int OK 		= 1;
	final static public int ITEM	= 0;

	public int Type;

	
	public String Lable;
	public String LongLable;
	public IImage Icon;

	/** append extra data */
	public int Tag = 0;
	/** append extra data */
	public Object Data = null;
	
	public Vector<Command> SubCommands;
	
	private Hashtable<String, Object> Attributes;
	
	public Command(Command other){
		this.set(other);
//		this.SubCommands	= other.SubCommands;
	}
	public Command(String lable){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
	}
	public Command(String lable, int type){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
		Type 		= type;
	}
	public Command(String lable,String longLable){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
		LongLable 	= longLable;
	}
	public Command(String lable,String longLable,int type){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
		LongLable 	= longLable;
		Type 		= type;
	}
	public Command(String lable,String longLable,IImage icon){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
		LongLable 	= longLable;
		Icon 		= icon;
	}
	public Command(String lable,String longLable,IImage icon,int type){
		lable = Control.processReplaceTable(lable);
		lable = Control.LocalCovert.covert(lable);
		Lable 		= lable;
		LongLable 	= longLable;
		Icon 		= icon;
		Type 		= type;
	}

	public String getLable()
	{
		return Lable;
	}
	
	public String getLongLable()
	{
		return LongLable;
	}

	public IImage getIcon()
	{
		return Icon;
	}
	
	public IImage setIcon(IImage icon)
	{
		IImage prevIcon = this.Icon;
		
		this.Icon = icon;
		
		return prevIcon;
	}
	
	
	public void set(Command other){
		this.Lable			= other.Lable;
		this.LongLable		= other.LongLable;
		this.Icon			= other.Icon;
		this.Tag			= other.Tag;
		this.Data			= other.Data;
		this.Type			= other.Type;
//		this.SubCommands	= other.SubCommands;
	}
	
	
	public Command get(int index){
		if(SubCommands!=null){
			return (Command)SubCommands.elementAt(index);
		}
		return null;
	}
	public int size(){
		if(SubCommands==null){
			return 0;
		}
		return SubCommands.size();
	}
	public void add(Command cmd){
		if(SubCommands==null){
			SubCommands = new Vector<Command>();
		}
		if (cmd!=null)
		SubCommands.addElement(cmd);
	}
	public void add(Command[] cmds){
		if(SubCommands==null){
			SubCommands = new Vector<Command>();
		}
		if (cmds != null) {
			for (int i = 0; i < cmds.length; i++) {
				SubCommands.addElement(cmds[i]);
			}
		}
	}
	public void add(Vector<Command> cmds){
		if(SubCommands==null){
			SubCommands = new Vector<Command>();
		}
		if (cmds != null) {
			for (int i = 0; i < cmds.size(); i++) {
				SubCommands.addElement((Command) cmds.elementAt(i));
			}
		}
	}
	public void remove(Command cmd){
		if(SubCommands!=null){
			SubCommands.removeElement(cmd);
		}
	}
	public void remove(int index){
		if(SubCommands!=null && SubCommands.size()>index){
			SubCommands.removeElementAt(index);
		}
	}
	public void clear(){
		if(SubCommands!=null){
			SubCommands.removeAllElements();
		}
	}
	

	public Object getAttribute(String key) {
		if (Attributes==null) return null;
		return Attributes.get(key);
	}
	
	public Object setAttribute(String key, Object value) {
		if (Attributes==null) {
			Attributes = new Hashtable<String, Object>();
		}
		return Attributes.put(key, value);
	}
	
}
