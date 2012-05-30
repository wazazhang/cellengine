package com.g2d.studio.ui.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UERoot;

public class UIEdit extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private UILayoutManager manager;
	
	private G2DWindowToolBar tools;
	
	private UIPropertyPanel ui_property_panel;
	
	private UITreeNode tree_root;
	
	private UITree tree;
	
	private UITemplateList templates;
	
	
	private DisplayObjectPanel stage_view = new DisplayObjectPanel(
			new DisplayObjectPanel.ObjectStage(
					com.g2d.Color.BLACK));
	
	private File workdir;
		
	public UIEdit(File workdir) throws IOException 
	{
		this.workdir = workdir;
		this.manager = new UILayoutManager();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setSize(
				AbstractFrame.getScreenWidth() - 100, 
				AbstractFrame.getScreenHeight() - 100);
		this.setCenter();
		
		this.stage_view.start();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stage_view.stop();
			}
		});

		tree_root = createNode(null, UERoot.class, "root");
		{
			
		}
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		{
			JSplitPane hplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
			hplit.setResizeWeight(1);
			{
				JPanel right = new JPanel(new BorderLayout());
				right.add(stage_view);
				stage_view.getStage().addChild(tree_root.display);
				stage_view.getCanvas().setFPS(30);
				hplit.setLeftComponent(right);
				
				templates = new UITemplateList(this);
				templates.setMinimumSize(new Dimension(200, 200));
				templates.setPreferredSize(new Dimension(200, 200));
				hplit.setRightComponent(new JScrollPane(templates));
			}
			split.setRightComponent(hplit);
		}{
			JPanel left = new JPanel(new BorderLayout());
			
			JSplitPane vsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			{
				tree = new UITree(this, tree_root);
				tree.setMinimumSize(new Dimension(300, 200));
				JScrollPane treescroll = new JScrollPane(tree);
				treescroll.setPreferredSize(new Dimension(300, 300));
				vsplit.setTopComponent(treescroll);
				
				ui_property_panel = new UIPropertyPanel(300, 300);
				vsplit.setBottomComponent(ui_property_panel);
			}
			left.add(vsplit, BorderLayout.CENTER);
			split.setLeftComponent(left);
		}


		tools = new G2DWindowToolBar(this, false, true, true, false);
		this.add(tools, BorderLayout.NORTH);
		this.add(split, BorderLayout.CENTER);
		
	}
	
	public UILayoutManager getLayoutManager() {
		return manager;
	}
	
	public UITemplateList getTemplates() {
		return templates;
	}
	
	public Class<?> getSelectedTemplate() {
		UITemplate ut = templates.getSelectedItem();
		if (ut != null) {
			return ut.uiType;
		}
		return UECanvas.class;
	}
	
	public G2DTree getTree() {
		return tree;
	}
	
	public UITreeNode createNode(UITreeNode parent, Class<?> type, String name) 
	{
		try {
			UITreeNode node = new UITreeNode(this, type, name);
			if (parent != null) {
				Container pc = (Container)parent.display;
				if (pc.addComponent(node.display)) {
					parent.add(node);
				}
				tree.reload(parent);
			}
			return node;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void onSelectTreeNode(UITreeNode node) {
		tree.setSelectionNode(node);
		ui_property_panel.setCompoment(node);
	}


	// tool 
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == tools.save) {
			saveFile();
		}
	}
	
	private void saveFile()
	{
		try {
			String name = JOptionPane.showInputDialog(this, "输入名字！", "");
			if (name != null && name.length() > 0) {
				File file = new File(workdir, name + ".gui.xml");
				if (file.exists()) {
					int result = JOptionPane.showConfirmDialog(this,
							file.getName() + " 文件已存在，是否覆盖？", "确认？",
							JOptionPane.OK_CANCEL_OPTION);
					if (result != JOptionPane.OK_OPTION) {
						return;
					}
				} else {
					file.createNewFile();
				}
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				((UERoot)tree_root.display).toXMLStream(fos);
				fos.flush();
				CFile.writeData(file, fos.toByteArray());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	static public void main(final String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	CObject.initSystem(
    					new CStorage("ui_edit"), 
    					new CAppBridge(
    					Studio.class.getClassLoader(), 
    					Studio.class));
				try {
					UIManager.setLookAndFeel(
							"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception err) {
					err.printStackTrace();
				}
				File workdir = new File(".");
				if (args.length > 0) {
					File sfile = new File(args[0]);
					if (sfile.exists() && sfile.isDirectory()) {
						workdir = sfile;
					}
				}
				new AwtEngine();
				try {
					UIEdit frm = new UIEdit(workdir);
					frm.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							System.exit(1);
						}
					});
					frm.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
            }
        });
	}

	
	
}
