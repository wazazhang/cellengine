package com.g2d.studio.ui.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.awt.util.Tools;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UERoot;

public class UIEdit extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	final public File workdir;
	final public File resdir;
	
	private UILayoutManager manager;

	private JToggleButton tool_grid = new JToggleButton(Tools.createIcon(Res.icon_grid));
	private JSpinner tool_grid_size = new JSpinner(new SpinnerNumberModel(8, 2, 100, 1));
	private G2DWindowToolBar tools;
	private JToolBar bar_status;
	private JProgressBar bar_save_progress = new JProgressBar();
	
	private UIStage g2d_stage;
	private UIPropertyPanel ui_property_panel;
	private UITreeNode tree_root;
	private UITree tree;
	private UITemplateList templates;
	private DisplayObjectPanel stage_view;
	private File last_saved_file;
		
	public UIEdit(File cfg) throws IOException 
	{
		UIEditConfig.load(cfg.getPath());
		this.workdir = cfg.getParentFile();
		this.resdir	 = new File(workdir, "res");
		this.manager = new UILayoutManager(this);
		
		this.setTitle(workdir.getPath());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setSize(
				AbstractFrame.getScreenWidth() - 100, 
				AbstractFrame.getScreenHeight() - 100);
		this.setCenter();
		
		this.g2d_stage = new UIStage(this);
		this.stage_view = new DisplayObjectPanel(g2d_stage);
		this.stage_view.start();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stage_view.stop();
			}
		});

		tree_root = new UITreeNode(this, UERoot.class, "root");
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		{
			JSplitPane hplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
			hplit.setResizeWeight(1);
			{
				JPanel right = new JPanel(new BorderLayout());
				right.add(stage_view);
				stage_view.getStage().addChild(tree_root.display);
				stage_view.getCanvas().setFPS(30);
				DropTarget dt = new DropTarget(
						stage_view.getSimpleCanvas(), 
						DnDConstants.ACTION_REFERENCE,
						g2d_stage, true);
				stage_view.getSimpleCanvas().setDropTarget(dt);
				hplit.setLeftComponent(right);

				ui_property_panel = new UIPropertyPanel(300, 300);
				hplit.setRightComponent(ui_property_panel);
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
				
				JToolBar temptoolbar = new JToolBar();
				temptoolbar.add(new JLabel("标准控件"));
				templates = new UITemplateList(this);
				templates.setMinimumSize(new Dimension(200, 200));
				templates.setPreferredSize(new Dimension(200, 200));
				vsplit.setBottomComponent(new JScrollPane(templates));
			}
			left.add(vsplit, BorderLayout.CENTER);
			split.setLeftComponent(left);
		}

		{
			tools = new G2DWindowToolBar(this, false, true, true, true);
			tools.add(tool_grid);
			tools.add(tool_grid_size);
			tools.save_s.setToolTipText("另存为");
			tool_grid_size.setValue(8);
			tool_grid_size.setPreferredSize(new Dimension(50, 25));
			
			this.tools.save.setToolTipText("保存 Ctrl + S");
			this.tools.save.registerKeyboardAction(
					this, 
					KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK), 
					JComponent.WHEN_IN_FOCUSED_WINDOW);  
		}
		{
			bar_status = new JToolBar();
			bar_status.setFloatable(false);
			bar_status.add(bar_save_progress);
			
		}
		
		
		
		this.add(tools, 		BorderLayout.NORTH);
		this.add(split, 		BorderLayout.CENTER);
		this.add(bar_status, 	BorderLayout.SOUTH);
		
	}
	
	public UILayoutManager getLayoutManager() {
		return manager;
	}
	
	public UITemplateList getTemplates() {
		return templates;
	}
	
	public UITemplate getSelectedTemplate() {
		if (templates.getSelectedNode() instanceof UITemplate) {
			UITemplate ut = (UITemplate)templates.getSelectedNode();
			if (ut != null) {
				return ut;
			}
		}
		return null;
	}
	
	public UITreeNode getSelectedUINode() {
		Object value = tree.getSelectedNode();
		if (value instanceof UITreeNode){
			return (UITreeNode)value;
		}
		return null;
	}
	
	public UITree getTree() {
		return tree;
	}
	

	
	public InputStream getStream(String sub_file) {
		return CIO.getInputStream(workdir.getPath() + "/" + sub_file);
	}
	
	public File getSubFile(String sub_file) {
		return new File(workdir, sub_file);
	}
	
	public void onSelectTreeNode(UITreeNode node) {
		tree.setSelectionNode(node);
		ui_property_panel.setCompoment(node);
	}

	public boolean isGridEnable() {
		return tool_grid.isSelected();
	}

	public int getGridSize() {
		return ((Number)tool_grid_size.getValue()).intValue();
	}
	
	// tool 
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == tools.save) {
			saveFile(false);
		}
		else if (e.getSource() == tools.save_s) {
			saveFile(true);
		}
		else if (e.getSource() == tools.load) {
			loadFile();
		}
	}
	
	private void saveFile(boolean saveAs)
	{
		try {
			bar_save_progress.setValue(0);
			bar_save_progress.setMaximum(tree.getAllNodes().size());
			if (last_saved_file == null || saveAs) {
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
					last_saved_file = file.getCanonicalFile();
				} else {
					return;
				}
			}
			if (last_saved_file != null) {
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				tree_root.toXMLStream(this, fos);
				fos.flush();
				CFile.writeData(last_saved_file, fos.toByteArray());
				this.setTitle(last_saved_file.getPath());
			}
			bar_save_progress.setValue(bar_save_progress.getMaximum());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void loadFile()
	{
		try {
			java.awt.FileDialog fd = new FileDialog(this, 
					"打开文件",
					FileDialog.LOAD);
			fd.setLocation(getLocation());
			fd.setDirectory(workdir.getPath());
			fd.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.toLowerCase().endsWith(".gui.xml")) {
						return true;
					}
					return false;
				}
			});
			fd.setVisible(true);
			if (fd.getFile() != null) {
				try {
					String file = fd.getDirectory() + fd.getFile();
					File xmlfile = new File(file).getCanonicalFile();
					FileInputStream fis = new FileInputStream(xmlfile);
					tree_root.fromXMLStream(this, fis);
					fis.close();
					last_saved_file = xmlfile;
					this.setTitle(last_saved_file.getPath());
					tree.reload();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, 
							"非法的格式!\n" + 
							e.getMessage());
				}
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
				try {
					if (args.length > 0) {
						File cfg = new File(args[0]).getCanonicalFile();
						if (cfg.exists()) {
							new AwtEngine();
							UIEdit frm = new UIEdit(cfg);
							frm.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									System.exit(1);
								}
							});
							frm.setVisible(true);
							return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "工作空间配置不正确!");
				System.exit(1);
            }
        });
	}

	
	
}
