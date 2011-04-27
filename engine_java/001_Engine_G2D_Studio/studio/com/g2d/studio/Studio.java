package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.rpg.RPGConfig;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.scene.script.SceneScriptManager;
import com.cell.sound.mute_impl.NullSoundManager;
import com.cell.sound.openal_impl.JALSoundManager;
import com.cell.sound.util.StaticSoundPlayer;
import com.cell.sql.SQMTypeManager;
import com.cell.util.concurrent.ThreadPool;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.awt.util.Drawing;
import com.g2d.awt.util.Tools;
import com.g2d.awt.util.Util;
import com.g2d.display.ui.layout.UILayoutManager.SimpleLayoutManager;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.cpj.CPJResourceManager;
import com.g2d.studio.gameedit.ObjectManager;
import com.g2d.studio.gameedit.ObjectManagerTree;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.icon.IconManager;
import com.g2d.studio.instancezone.InstanceZonesManager;
import com.g2d.studio.io.File;
import com.g2d.studio.io.IO;
import com.g2d.studio.io.file.FileIO;
import com.g2d.studio.io.http.FileHttp;
import com.g2d.studio.item.ItemManager;
import com.g2d.studio.quest.QuestManager;
import com.g2d.studio.quest.group.QuestGroupManager;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.SceneManager;
import com.g2d.studio.sound.SoundManager;
import com.g2d.studio.talks.TalkManager;



public class Studio extends AbstractFrame
{
	private static final long 		serialVersionUID = Version.VersionGS;
	
	private static Studio instance;
	
	public static Studio getInstance() {
		return instance;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------
	
	final public ThreadPool			thread_pool = new ThreadPool("studio project");
	
	final private IO				io;
	
	com.cell.sound.SoundManager		sound_system;
	
	private SceneScriptManager		scene_script_manager;
	
//	final private FileOutputStream	project_lock;
	
	final public File 				project_path;
	final public File 				project_file;
	final public File				project_save_path;

	final public File 				root_sound_path;
	final public File 				root_icon_path;
	final public File 				root_talk_path;
	
	final public File				xls_tplayer;
	final public File 				xls_tunit;
	final public File				xls_tpet;
	final public File 				xls_titem;
	final public File 				xls_tshopitem;
	final public File 				xls_tskill;
	
	final public String				talk_example;
//	final public File				plugins_dir;
	
	private CPJResourceManager		frame_cpj_resource_manager;
	private ItemManager				frame_item_manager;
	private ObjectManager			frame_object_manager;
	private SoundManager			frame_sound_manager;
	private IconManager				frame_icon_manager;
	private TalkManager				frame_talk_manager;
	private QuestManager			frame_quest_manager;
	private QuestGroupManager		frame_quest_group_manager;
	private InstanceZonesManager	frame_instance_zone_manager;
	
	private SceneManager			scene_manager;
	
	private StaticSoundPlayer		g2d_sound;
	
	private Studio(File g2d_file, IO io) throws Throwable
	{
		instance 			= this;
		this.io				= io;
		
		System.out.println("IO system : " + io.getClass().getName());
		
		project_file 		= g2d_file;
		project_path 		= io.createFile(project_file.getParent());
		project_save_path	= io.createFile(project_file.getParentFile(), Config.G2D_SAVE_NAME);
		
		RPGConfig.IS_EDIT_MODE = true;
		RPGObjectMap.setPersistanceManagerDriver	(Config.PERSISTANCE_MANAGER);
		SQMTypeManager.setTypeComparer				(Config.PERSISTANCE_SQL_TYPE);
		Builder.setBuilder							(Config.BUILDER_CLASS, project_path.getPath());

		try {
			scene_script_manager = (SceneScriptManager)Class.forName(
					Config.DYNAMIC_SCENE_SCRIPT_MANAGER_CLASS).newInstance();
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
		
		root_icon_path		= getFile				(Config.RES_ICON_ROOT);
		root_sound_path		= getFile				(Config.RES_SOUND_ROOT);
		root_talk_path		= getFile				(Config.RES_TALK_ROOT);
		
		xls_tplayer			= getFile				(Config.XLS_TPLAYER);
		xls_tunit			= getFile				(Config.XLS_TUNIT);
		xls_tpet			= getFile				(Config.XLS_TPET);
		xls_titem			= getFile				(Config.XLS_TITEM);
		xls_tshopitem		= getFile				(Config.XLS_TSHOPITEM);
		xls_tskill			= getFile				(Config.XLS_TSKILL);
		
		File talk_example_file = getFile			(Config.TALK_EXAMPLE);
		if (talk_example_file.exists()) {
			talk_example = CIO.stringDecode(talk_example_file.readBytes(), CObject.ENCODING);
		} else {
			talk_example = "// talk example";
		}
		
		try {
			UILayoutManager.setInstance(new UILayoutManager());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception err) {
			err.printStackTrace();
		}
		try {
			Font font = new Font(Config.DEFAULT_FONT, Font.PLAIN, Config.DEFAULT_FONT_SIZE);
			this.setFont(font);
//			FontUIResource fontRes = new FontUIResource(font);
//		    for(Enumeration<?> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
//		        Object key = keys.nextElement();
//		        Object value = UIManager.get(key);
//		        if(value instanceof FontUIResource) {
//		            UIManager.put(key, fontRes);
//		        }
//		    }
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		try{
			sound_system = JALSoundManager.getInstance();
			com.cell.sound.SoundManager.setSoundManager(sound_system);
			g2d_sound = new StaticSoundPlayer("/com/g2d/studio/res/openning.wav");
			g2d_sound.play(false);
		}catch(Throwable tr) {
			tr.printStackTrace();
			sound_system = new NullSoundManager();		
			com.cell.sound.SoundManager.setSoundManager(sound_system);
		}			
		
		System.out.println(System.setProperty("user.dir", project_path.getPath()));
		System.out.println(System.getProperty("user.dir"));
		
		this.setTitle(Config.TITLE);
		this.setIconImage(Res.icon_edit);
		this.setSize(300, AbstractFrame.getScreenHeight()-60);
		this.setLocation(0, 0);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		
		new LoadTask().start();
	}

	private class LoadTask extends Thread
	{
		@Override
		public void run()
		{
			// sysetm init
			ProgressForm progress_form = new ProgressForm();
			progress_form.setVisible(true);

			try
			{
				initToolBar(progress_form);
				initStateBar(progress_form);
				
				Studio.this.scene_manager = new SceneManager(Studio.this, progress_form);
				
				Studio.this.frame_instance_zone_manager = scene_manager.getInstanceZonesManager();
				
				Studio.this.add(scene_manager, BorderLayout.CENTER);
				
				Studio.this.addWindowListener(new StudioWindowListener());
				
				progress_form.setVisible(false);
				progress_form.dispose();
				
				Studio.this.setVisible(true);
			}
			catch (Throwable e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	
	// init tool bar
	private void initToolBar(ProgressForm progress)
	{
		JToolBar tool_bar_1 	= new JToolBar();
		JToolBar tool_bar_2 	= new JToolBar();
		tool_bar_1.setFloatable(false);
		tool_bar_2.setFloatable(false);
		
		// icon manager
		{
			frame_icon_manager = new IconManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_icon_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_icon_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_icon_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// sound manager
		{
			frame_sound_manager = new SoundManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_sound_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_sound_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_sound_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// talk manager
		{
			frame_talk_manager = new TalkManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_talk_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_talk_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_talk_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// res manager
		{
			frame_cpj_resource_manager = new CPJResourceManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_cpj_resource_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_cpj_resource_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_cpj_resource_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// item manager
		{
			frame_item_manager = new ItemManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_item_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_item_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_item_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// objects manager
		{
			frame_object_manager = new ObjectManager(this);
			frame_object_manager.loadAll(this, progress);
			for (ObjectManagerTree<?, ?> page : frame_object_manager.getIconPages()) {
				JButton btn = new JButton();
				btn.setToolTipText(page.getTitle());
				btn.setIcon(Tools.createIcon(page.getIconImage()));
				final ObjectManagerTree<?, ?> frame = page;
				btn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						frame.setVisible(true);
					}
				});
				tool_bar_2.add(btn);
			}
		}
		// quest group manager
		{
			frame_quest_group_manager = new QuestGroupManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_quest_group_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_quest_group_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_quest_group_manager.setVisible(true);
				}
			});
//			tool_bar.add(btn);
		}
		// quest manager
		{
			frame_quest_manager = new QuestManager(this, progress);
			JButton btn = new JButton();
			btn.setToolTipText(frame_quest_manager.getTitle());
			btn.setIcon(Tools.createIcon(frame_quest_manager.getIconImage()));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame_quest_manager.setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		// about
		{
			JButton btn = new JButton();
			btn.setToolTipText("About");
			btn.setIcon(Tools.createIcon(Res.icon_edit));
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					new VersionFrame().setVisible(true);
				}
			});
			tool_bar_1.add(btn);
		}
		JPanel tool_bar = new JPanel(new BorderLayout());
		tool_bar.add(tool_bar_1, BorderLayout.NORTH);
		tool_bar.add(tool_bar_2, BorderLayout.SOUTH);
		this.add(tool_bar, BorderLayout.NORTH);
	}
	
	// init state bar
	private void initStateBar(ProgressForm progress)
	{
		JToolBar state_bar = new JToolBar();
		
		progress.startReadBlock("初始化工具...");
		progress.setMaximum("", 1);
		// heap state
		{
			final JLabel mem_state = new JLabel("  "+Util.getHeapString()+"  ") {
				private static final long serialVersionUID = 1L;
				public void paint(Graphics g) {
					super.paint(g);
					super.setText("  "+Util.getHeapString()+"  ");
					Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth() - 2, getHeight() - 2, false);
				}
			};
			thread_pool.scheduleAtFixedRate(new Runnable() {
				public void run() {
					mem_state.repaint();
				}
			}, 2000, 2000);
			state_bar.add(mem_state);
		}

		// gc
		{
			JButton gc = new JButton("gc");
			gc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.gc();
					System.runFinalization();
				}
			});
			state_bar.add(gc);
		}

		this.add(state_bar, BorderLayout.SOUTH);
		progress.increment();
	}
	
	public IO getIO() {
		return io;
	}
	
	/**
	 * 得到工作空间跟目录下的文件
	 * @param path
	 * @return
	 */
	public File getFile(String path)
	{
		return io.createFile(project_path, path);
	}

//-----------------------------------------------------------------------------------------------------------
	public com.cell.sound.SoundManager getSoundSystem() {
		return sound_system;
	}		
	

	public CPJResourceManager getCPJResourceManager() {
		return frame_cpj_resource_manager;
	}

	public SceneScriptManager getSceneScriptManager() {
		return scene_script_manager;
	}
	
	public ObjectManager getObjectManager() {
		return frame_object_manager;
	}

	public SoundManager getSoundManager() {
		return frame_sound_manager;
	}
	
	public IconManager getIconManager() {
		return frame_icon_manager;
	}
	
	public TalkManager getTalkManager() {
		return frame_talk_manager;
	}
	
	public QuestManager getQuestManager() {
		return frame_quest_manager;
	}
	
	public QuestGroupManager getQuestGroupManager() {
		return frame_quest_group_manager;
	}
	
	public ItemManager getItemManager() {
		return frame_item_manager;
	}
	
	public InstanceZonesManager	getInstanceZoneManager() {
		return frame_instance_zone_manager;
	}
	
	public SceneManager getSceneManager() {
		return scene_manager;
	}
	
//	-----------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------
	
	public void saveAll() 
	{		
		SaveProgressForm progress = new SaveProgressForm(false);
		
		try {
			frame_sound_manager.saveAll(progress);
			frame_icon_manager.saveAll(progress);
			frame_cpj_resource_manager.saveAll(progress);
			frame_talk_manager.saveAll(progress);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		try {
			frame_item_manager.saveAll(progress);	
			frame_object_manager.saveAll(progress);
			frame_quest_manager.saveAll(progress);
			frame_quest_group_manager.saveAll(progress);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		scene_manager.saveAll(progress);
	
		try {
			frame_instance_zone_manager.saveAll(progress);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

//	----------------------------------------------------------------------------------------------------------------
	
//	class ShutdownHook extends Thread
//	{
//		@Override
//		public void run() {
////			try {
////				project_lock.close();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
//		}
//		
//	}
	

//	----------------------------------------------------------------------------------------------------------------
//	ui action
	
	class StudioWindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(Studio.this, "保存并退出?");
			if (result == JOptionPane.OK_OPTION) {
				saveAll();
				System.out.println("save and exit");
				System.exit(1);
			} else if (result == JOptionPane.NO_OPTION) {
				System.out.println("exit");
				System.exit(1);
			} else {
				new Thread() {
					public void run() {
						Studio.this.setVisible(true);
					}
				}.start();
			}
		}
	}
	
	public static class ProgressForm extends JFrame implements IProgress
	{
		private static final long serialVersionUID = 1L;
		private JProgressBar progress = new JProgressBar();

		JLabel lbl_title;
		JLabel back;
		private String prefix;
		
		public ProgressForm()
		{
			this.setTitle(Config.TITLE);
			this.setSize(Res.img_splash.getWidth(), Res.img_splash.getHeight()+40);
			this.setResizable(false);

//			this.setAlwaysOnTop(true);
			AbstractFrame.setCenter(this);
			
			setIconImage(Res.icon_edit);
			
			progress.setStringPainted(true);
			
			lbl_title 	= new JLabel("初始化中...");
			BufferedImage back_image = Tools.cloneImage(Res.img_splash);
			try {
				Graphics2D g2d = back_image.createGraphics();
				Drawing.drawStringBorder(g2d, Version.getFullVersion() + "", 10, 10, getWidth()-2, getHeight()-2, 0);
				g2d.dispose();
			} catch (Throwable ex) {}
			back = new JLabel(Tools.createIcon(back_image));
			 
			this.add(lbl_title, BorderLayout.NORTH);
			this.add(back, BorderLayout.CENTER);
			this.add(progress, BorderLayout.SOUTH);	
			
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(1);
				}
			});
		}
		
		public void startReadBlock(String title) {
			lbl_title.setText(title);
		}
		
		@Override
		public void setMaximum(String prefix, int total) 
		{
			this.prefix = prefix;
			progress.setMaximum(total);			
			progress.setValue(0);
			progress.setString(prefix + " " + (progress.getValue())+"/"+progress.getMaximum());
		}
		
		@Override
		public void setValue(String prefix, int n) 
		{
			this.prefix = prefix;
			progress.setValue(n);
			progress.setString(prefix + " " + (progress.getValue()+1)+"/"+progress.getMaximum());
		}
		
		@Override
		public int getMaximum()
		{
			return progress.getMaximum();
		}
		
		@Override
		public int getValue()
		{
			return progress.getValue();
		}
		
		@Override
		public void increment() {
			increment(1);
		}
		
		@Override
		public void increment(int count) {
			progress.setValue(progress.getValue()+count);
			progress.setString(prefix + " " + (progress.getValue())+"/"+progress.getMaximum());
		}
//		public void increment() {
//			progress.setValue(progress.getValue()+1);
//		}
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	resource manager
	public class UILayoutManager extends SimpleLayoutManager
	{
		public UILayoutManager() {
			super();
			initLayout();
		}
	}
//	public class SetResourceManager extends CellSetResourceManager
//	{
//		public SetResourceManager() {}
//		@Override
//		public StudioResource createSet(String setPath) throws Exception {
//			return new StudioResource(project_path.getPath() + "/" + setPath, setPath);
//		}
//		
//	}
	
//	----------------------------------------------------------------------------------------------------------------
//	main entry

	
	static public void main(final String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	CAppBridge.init();
            	start_studio(args);
            }
        });
	}
	
	static public void start_studio(String[] args) 
	{
		try
		{
			if (args == null || args.length == 0) 
			{
				System.err.println("usage : g2dstudio.jar [g2d file path] <IO class name>");
				return;
			} 
			else
			{
				CObject.initSystem(
					new CStorage("g2d_studio"), 
					new CAppBridge(
					Studio.class.getClassLoader(), 
					Studio.class));
				Config.load(args[0]);
				new AwtEngine();
				
				IO io = null;
				if (args.length > 1 && args[0].startsWith("http")) {
					io = new FileHttp(args);
				}
				else if (io == null) {
					io = new FileIO(args);
				}
				
				System.out.println("Open: " + args[0]);
				File g2d_file = io.createFile(args[0]);
				new Studio(g2d_file, io);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			String message = "Open workspace error ! \n" + e.getClass().getName() + " : " + e.getMessage() + "\n";
			for (StackTraceElement stack : e.getStackTrace()) {
				message += "\t"+stack.toString()+"\n";
			}
			JOptionPane.showMessageDialog(null, message);
			System.exit(1);
		}
	}
}

