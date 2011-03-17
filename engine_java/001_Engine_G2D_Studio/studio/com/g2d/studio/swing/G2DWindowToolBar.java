package com.g2d.studio.swing;

import java.awt.event.ActionListener;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.g2d.awt.util.*;

import com.g2d.studio.res.Res;


public class G2DWindowToolBar extends JToolBar implements Externalizable
{
	private static final long serialVersionUID = 1L;
	
	final public JButton save 	= new JButton(Tools.createIcon(Res.icons_tool_bar[2]));
	final public JButton load 	= new JButton(Tools.createIcon(Res.icons_tool_bar[1]));
	final public JButton new_ 	= new JButton(Tools.createIcon(Res.icons_tool_bar[0]));
	final public JButton save_s	= new JButton(Tools.createIcon(Tools.createAlphaImage(Res.icons_tool_bar[2], 0.5f)));

	public G2DWindowToolBar(ActionListener listener) {
		this(listener, false, false, true, false);
	}
	
	public G2DWindowToolBar(ActionListener listener, 
			boolean enable_new, 
			boolean enable_open, 
			boolean enable_save,
			boolean enable_save_single) 
	{
		new_.setToolTipText("新建");
		new_.addActionListener(listener);
		
		load.setToolTipText("打开");
		load.addActionListener(listener);
		
		save.setToolTipText("保存");
		save.addActionListener(listener);
		
		save_s.setToolTipText("保存所选");
		save_s.addActionListener(listener);
		
		if (enable_new) super.add(new_);
		if (enable_open) super.add(load);
		if (enable_save) super.add(save);
		if (enable_save_single) super.add(save_s);
		super.addSeparator();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {}
	
}
