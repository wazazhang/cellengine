package com.g2d.studio.old;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.Version;
import com.thoughtworks.xstream.XStream;

public abstract class AFormDisplayObjectViewer<D extends DisplayObject> extends DisplayObjectViewer<D> 
{
	private static final long serialVersionUID = Version.VersionGS;
	
	final public ATreeNodeLeaf<? extends AFormDisplayObjectViewer<D>> leaf_node;
	
	final public Studio studio;
	
	//
	protected boolean	no_save			= false;
	//
	protected boolean	is_changed		= false;
	
//	工具栏
	protected JToolBar	bar_tools		= new JToolBar();
	JToggleButton		tool_always_top	= new JToggleButton("总在最前");
	JToggleButton		tool_debug		= new JToggleButton("DEBUG");
	JButton				tool_save		= new JButton("保存");
	
//	状态拦
	protected JToolBar	bar_state		= new JToolBar();
	JLabel 				state_label 	= new JLabel("单位状态"); 
	
	
//	缩略图标
	protected ImageIcon	snapshot		;
	
	
	public AFormDisplayObjectViewer(ATreeNodeLeaf<? extends AFormDisplayObjectViewer<D>> leaf, D object) 
	{
		super(object);
		
		leaf_node	= leaf;
		studio		= leaf_node.getParent().getParent().studio;
		
		leaf_node.setUserObject(this);
		
		this.setLocation(
				studio.getX() + studio.getWidth(), 
				studio.getY());
		
		this.setLayout(new BorderLayout());
		
		// tool bar
		
		{
			JPanel tool_pan = new JPanel();
			tool_pan.setLayout(new BorderLayout());
			tool_pan.add(bar_tools);
			this.add(tool_pan, BorderLayout.NORTH);
			
			
			//
			tool_always_top.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					AFormDisplayObjectViewer.this.setAlwaysOnTop(tool_always_top.isSelected());
				}
			});
			addToolButton(tool_always_top, "always on top", null);
			
			//
			tool_debug.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					view_object.setDebug(tool_debug.isSelected());
				}
			});
			addToolButton(tool_debug, "", null);
			
			//
			tool_save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					AFormDisplayObjectViewer.this.save();
				}
			});
			addToolButton(tool_save, "save", null);
			
			//
			bar_tools.addSeparator();

		}
		
		
		// state bar
		{
			JPanel tool_pan = new JPanel();
			tool_pan.setLayout(new BorderLayout());
			tool_pan.add(bar_state);
			this.add(tool_pan, BorderLayout.SOUTH);
			
			
			//
			bar_state.add(state_label);
			
		}
		
		// scene view
		{
			if (view_object!=null) {
				canvas.getCanvasAdapter().setStageSize(view_object.getWidth(), view_object.getHeight());
			}
			
			canvas.addComponentListener(new ComponentListener(){
				public void componentResized(ComponentEvent e) {
					canvas.getCanvasAdapter().setStageSize(e.getComponent().getWidth(), e.getComponent().getHeight());
				}
				public void componentHidden(ComponentEvent e) {}
				public void componentMoved(ComponentEvent e) {}
				public void componentShown(ComponentEvent e) {}
			});
			
			this.add(canvas, BorderLayout.CENTER);
		}
		
		setMinimumSize(new Dimension(200, 200));
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			is_changed = true;
		}
	}
	
	public String getCpjName()
	{
		return leaf_node.getParent().getID();
	}
	
	public String getCpjObjectID()
	{
		return leaf_node.getID();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------

	
	protected void addToolButton(AbstractButton tool, String tip, ButtonGroup group)
	{
		tool.setToolTipText(tip);
	
		bar_tools.add(tool);

		if (group!=null) {
			group.add(tool);
		}
	}
	
	protected void setStateText(String text) {
		state_label.setText(text);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	public ImageIcon getSnapshot(boolean update)
	{
		if (is_changed && view_object!=null) {
			if (snapshot==null || update){
				try {
					snapshot = Tools.createIcon(view_object.getSnapshot(32, 32));
				} catch (Exception e) {
				}
			}
		}
		return snapshot;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	abstract public void saveObject(ObjectOutputStream os, File file) throws Exception;
	
	abstract public void loadObject(ObjectInputStream is, File file) throws Exception;
	
	public void load()
	{
		if (no_save) return;

		File file = null;
			
		try 
		{ 
			file = new File(studio.project_path.getPath() + "/" + leaf_node.path + ".xml");
			
			if (file.exists())
			{
				byte[] data = com.cell.io.File.readData(file);
				if (data!=null) {
					String text_data = new String(data, "UTF-8");
//					System.out.println(text_data);
					Reader reader = new StringReader(text_data);
					try{
						XStream xstream = new XStream();
						ObjectInputStream in = xstream.createObjectInputStream(reader);
						try{
							loadObject(in, file);
						}finally{
							in.close();
						}
					}finally{
						reader.close();
					}
//					System.out.println("load obj : " + file.getPath());
				}
			}
		} 
		catch (Exception e) {
			System.err.println("load error : " + leaf_node.getID() + " : " + file);
			e.printStackTrace();
			is_changed = true;
		}
		
	}
	
	public void save()
	{
		if (no_save) return;

		File file = null ;
		
		try 
		{	
			file = studio.getFile(leaf_node.path + ".xml") ;
			
			if (!file.exists()) {
				file.createNewFile();
			} else if (!is_changed) {
				return;
			}
			
			Writer writer = new StringWriter(1024);
			
			try{
				XStream xstream = new XStream();
				ObjectOutputStream out = xstream.createObjectOutputStream(writer);
				try{
					saveObject(out, file);
				}finally{
					out.close();
				}
				writer.flush();
				String text_data = writer.toString();
//				System.out.println(text_data);
				com.cell.io.File.wirteData(file, text_data.getBytes("UTF-8"));
			}finally{
				writer.close();
			}
			
//			System.out.println("save obj : " + file.getPath());
		} 
		catch (Exception e) {
			System.err.println("save error : " + leaf_node.getID() + " : " + file);
			e.printStackTrace();
			is_changed = true;
		}
		
	}
	
	

//	-------------------------------------------------------------------------------------------------------------------------
	

	@Override
	public String toString() {
		return getCpjName() + "/" + getCpjObjectID();
	}
	

}
