package com.g2d.studio.old.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Form;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.Version;
import com.g2d.studio.old.ATreeNodeLeaf;
import com.g2d.util.Drawing;


public class FormUIComponentEditView extends FormUIComponentView
{
	private static final long serialVersionUID = Version.VersionGS;

	

	class EditStage extends Stage
	{
		public EditStage() {}
		
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}

		public void update()
		{
			// 选择单位
			if (tool_select.isSelected())
			{
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					try {
						selected_ui = (UIComponent)view_object.getFocus();
						setStateText(view_object.getFocus().toString());
					} catch (Exception e) {
					}
				}
			}
			// 添加单位
			else if (tool_add.isSelected()) 
			{
				FormUIComponentView ui = studio.getSelectedNodeAsUI();
				
				if (ui != null && ui != FormUIComponentEditView.this)
				{
					if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
					{
						if (view_object instanceof Form)
						{
							UIComponent comp = ui.getViewObject();
							
							try {
								comp = (UIComponent)comp.getClass().newInstance();
								comp.setLocation(view_object.getMouseX(), view_object.getMouseY());
								((Form)view_object).addChild(comp);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
				}
				
			}
			// 删除单位
			else if (tool_delete.isSelected()) 
			{

			}
			synchronized (this) {
				if (!this.contains(view_object)) {
					this.addChild(view_object);
				}
			}
		}
		
		public void render(Graphics2D g) {
			g.setColor(Color.WHITE);
			g.fill(local_bounds);
		}
		

		protected void renderAfter(Graphics2D g)
		{
			if (tool_add.isSelected()) 
			{
				FormUIComponentView ui = studio.getSelectedNodeAsUI();
				
				if (ui != null && ui != FormUIComponentEditView.this)
				{
					g.setColor(Color.WHITE);
					Rectangle str_rect = Drawing.drawStringBorder(g, ui.getClass().getName(), 
							getMouseX()+2, getMouseY()+2, 0);
					
					g.setColor(Color.BLACK);
					g.drawRect(getMouseX(), getMouseY(), str_rect.width+4, str_rect.height+4);
				}
			}
		}
		
	}
	
	
	UIComponent selected_ui;
	
	JToggleButton	tool_select	= new JToggleButton("select", true);
	JToggleButton	tool_add	= new JToggleButton("add");
	JToggleButton	tool_delete	= new JToggleButton("delete");
	
	
	
	public FormUIComponentEditView(ATreeNodeLeaf<FormUIComponentView> leaf)
	{
		super(leaf, new Form());
		
		no_save = false;
		
		ButtonGroup 	tool_group 	= new ButtonGroup();
		{
			super.addToolButton(tool_select, "", tool_group);
			super.addToolButton(tool_add, "", tool_group);
			super.addToolButton(tool_delete, "", tool_group);
		}
		
		canvas.getCanvasAdapter().setStage(new EditStage());
		
		this.setSize(view_object.getWidth()*2, view_object.getHeight()*2);
	}
	
	@Override
	public void loadObject(ObjectInputStream is, File file) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveObject(ObjectOutputStream os, File file) throws Exception {
		
	}
	
}
