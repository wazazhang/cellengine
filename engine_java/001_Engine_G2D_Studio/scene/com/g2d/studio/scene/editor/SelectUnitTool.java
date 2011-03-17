package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.g2d.Tools;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;

@SuppressWarnings("serial")
public abstract class SelectUnitTool extends AbstractFrame
{
	private static final long serialVersionUID = 1L;
	
	private static SelectUnitTool instance;
	
	public static SelectUnitTool getUnitTool()
	{
		if (instance == null) {
			instance = new SelectUnitTool2();
		}
		return instance;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------

	JTabbedPane table = new JTabbedPane();

	ObjectPage<XLSUnit>	page_xls_unit_panel;
	ObjectPage<CPJSprite>	page_cpj_sprite_panel;
	ObjectPage<DEffect>	page_deffect_panel;
	
	protected SelectUnitTool() 
	{
		this.setIconImage(Res.icon_edit);
		this.setSize(200, 400);
		this.setAlwaysOnTop(true);
		this.setTitle("单位面板");
		this.setLocation(
				Studio.getInstance().getX() + Studio.getInstance().getWidth(), 
				Studio.getInstance().getY()
				);

		this.page_xls_unit_panel = createXLSUnitPage();
		this.table.addTab("单位", page_xls_unit_panel.asPanel());
		this.page_cpj_sprite_panel = createCPJSpritePage();
		this.table.addTab("不可破坏", page_cpj_sprite_panel.asPanel());
		this.page_deffect_panel = createDEffectPage();
		this.table.addTab("特效", page_deffect_panel.asPanel());
		
		this.add(table, BorderLayout.CENTER);
		
		this.page_xls_unit_panel.onRefresh();
		this.page_cpj_sprite_panel.onRefresh();
		this.page_deffect_panel.onRefresh();
	}

	abstract protected ObjectPage<XLSUnit>		createXLSUnitPage();
	
	abstract protected ObjectPage<CPJSprite>	createCPJSpritePage();
	
	abstract protected ObjectPage<DEffect>		createDEffectPage();
	
	final public XLSUnit getSelectedUnit() {
		if (table.getSelectedIndex() == 0) {
			return this.page_xls_unit_panel.getSelected();
		}
		return null;
	}
	
	final public CPJSprite getSelectedSpr() {
		if (table.getSelectedIndex() == 1) {
			return this.page_cpj_sprite_panel.getSelected();
		}
		return null;
	}

	final public DEffect getSelectedEffect() {
		if (table.getSelectedIndex() == 2) {
			return this.page_deffect_panel.getSelected();
		}
		return null;
	}
	
	@Override
	final public void setVisible(boolean b) {
		if (b) {
			if (super.isVisible()) {
				return;
			} else {
				this.page_xls_unit_panel.onRefresh();
				this.page_cpj_sprite_panel.onRefresh();
				this.page_deffect_panel.onRefresh();
			}
		}
		super.setVisible(b);		
	}
	
	
//	----------------------------------------------------------------------------------------------------------------

	abstract protected static interface ObjectPage<T extends G2DTreeNode<?>>
	{	
		abstract public T 		getSelected();
		
		abstract public void	onRefresh();
		
		abstract public JPanel 	asPanel();
	}
	
	
	
	
	
}
