package com.g2d.studio.ui.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.Engine;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.ui.edit.gui.EditedCanvas;
import com.g2d.studio.ui.edit.gui.IComponent;

public class UIEdit extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private G2DWindowToolBar tools;
	
	public static UILayout default_ui = new UILayout();
	
	private UIPropertyPanel ui_property_panel;
	
	private UITree tree;
	
	private DisplayObjectPanel stage_view = new DisplayObjectPanel(
			new DisplayObjectPanel.ObjectStage(
					com.g2d.Color.BLACK));
	
	private File workdir;
	
	public UIEdit(File workdir) throws IOException 
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.workdir = workdir;
				
		this.setSize(
				AbstractFrame.getScreenWidth(), 
				AbstractFrame.getScreenHeight());
		
		this.stage_view.start();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stage_view.stop();
			}
		});

		

		IComponent root = createNode(null, EditedCanvas.class, "root");
		root.asComponent().setSize(800, 480);
		
		{
			tools = new G2DWindowToolBar(this, false, true, true, false);
		}
		
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		{
			JSplitPane hplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
			hplit.setResizeWeight(1);
			{
				JPanel right = new JPanel(new BorderLayout());
				right.add(stage_view);
				stage_view.getStage().addChild(root.asComponent());
				stage_view.getCanvas().setFPS(30);
				hplit.setLeftComponent(right);
				
				G2DList<UITemplate> templates = new G2DList<UITemplate>();
				templates.setMinimumSize(new Dimension(200, 200));
				templates.setPreferredSize(new Dimension(200, 200));
				hplit.setRightComponent(new JScrollPane(templates));
			}
			split.setRightComponent(hplit);
		}{
			JPanel left = new JPanel(new BorderLayout());
			
			JSplitPane vsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			{
				tree = new UITree(this, root.getTreeNode());
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


		this.add(tools, BorderLayout.NORTH);
		this.add(split, BorderLayout.CENTER);
		
	}
	
	// tool 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public G2DTree getTree() {
		return tree;
	}
	
	public <T extends IComponent> T createNode(IComponent parent, Class<T> type, String name) 
	{
		try {
			T ic = type.newInstance();
			ic.init(this);
			ic.setName(name);
			ic.asComponent().setCustomLayout(default_ui);
			if (parent != null) {
				Container pc = (Container)parent.asComponent();
				if (pc.addComponent(ic.asComponent())) {
					parent.getTreeNode().add(ic.getTreeNode());
				}
				tree.reload(parent.getTreeNode());
			}
			return ic;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void onSelectTreeNode(UITreeNode node) {
		tree.setSelectionNode(node);
		ui_property_panel.setCompoment(node);
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
					File file = new File(args[0]);
					if (file.isFile()) {
						workdir = file.getParentFile();
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
