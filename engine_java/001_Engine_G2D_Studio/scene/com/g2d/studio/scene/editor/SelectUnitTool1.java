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

import com.g2d.awt.util.Tools;
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
public class SelectUnitTool1 extends SelectUnitTool
{
	public SelectUnitTool1() {
		super();
	}

	@Override
	protected com.g2d.studio.scene.editor.SelectUnitTool.ObjectPage<CPJSprite> createCPJSpritePage() {
		return new CPJSpritePanel();
	}
	
	@Override
	protected com.g2d.studio.scene.editor.SelectUnitTool.ObjectPage<DEffect> createDEffectPage() {
		return new DEffectPanel();
	}

	@Override
	protected com.g2d.studio.scene.editor.SelectUnitTool.ObjectPage<XLSUnit> createXLSUnitPage() {
		return new XLSUnitPanel();
	}
	
	
//	----------------------------------------------------------------------------------------------------------------

	class XLSUnitPanel extends ObjectPanel<XLSUnit>
	{
		public XLSUnitPanel() {
			super(XLSUnit.class, "单位");
		}
		
		@Override
		public void onRefresh() 
		{
			refresh.setEnabled(false);
			
			new Thread()
			{
				@Override
				public void run() 
				{
					this.setPriority(MIN_PRIORITY);
					try{
						HashMap<JToggleButton , XLSUnit> map = new HashMap<JToggleButton, XLSUnit>();
						JPanel 		panel 			= new JPanel(null);
						ButtonGroup button_group	= new ButtonGroup();
			
						Vector<XLSUnit> tunits 		= Studio.getInstance().getObjectManager().getObjects(
								XLSUnit.class);
						
						progress.setMaximum(tunits.size());
						
						int mw = 0;
						int mh = 0;
						
						int w = icon_size;
						int h = icon_size;
						int wc = icon_column_count;
						
						int i=0;
						for (XLSUnit tunit : tunits)
						{
							JToggleButton btn = new JToggleButton();
							btn.setToolTipText(tunit.getName());
							btn.setLocation(i%wc * w, i/wc * h);
							btn.setSize(w, h);
							if (tunit.getCPJSprite()!=null) {
								btn.setIcon(Tools.createIcon(
										Tools.combianImage(w-4, h-4, tunit.getCPJSprite().getSnapShoot())));
								btn.addActionListener(XLSUnitPanel.this);
								map.put(btn, tunit);
							}
							button_group.add(btn);
							panel.add(btn);
							mw = Math.max(mw, btn.getX() + btn.getWidth());
							mh = Math.max(mh, btn.getY() + btn.getHeight());
							i++;
							progress.setValue(i);
							progress.setString(tunit.getName() + 
									"    " + i +"/" + tunits.size());
						}
		
						panel.setSize(mw, mh);
						panel.setPreferredSize(new Dimension(mw, mh));
						panel.setMinimumSize(new Dimension(mw, mh));
						
						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + tunits.size());
						
						System.out.println("refresh units");
					} 
					finally {
						refresh.setEnabled(true);
					}
				}
			}.start();
		}
		
		@Override
		public void onSelected(XLSUnit spr) {
			spr.getCPJSprite();
//			spr.getCPJSprite().getDisplayObject();
		}
	}
	
	class CPJSpritePanel extends ObjectPanel<CPJSprite>
	{
		public CPJSpritePanel() {
			super(CPJSprite.class, "精灵");
		}
		
		
		@Override
		public void onRefresh() 
		{
			refresh.setEnabled(false);
			
			new Thread()
			{
				@Override
				public void run() 
				{				
					this.setPriority(MIN_PRIORITY);
					try{
						Hashtable<JToggleButton , CPJSprite> map = new Hashtable<JToggleButton, CPJSprite>();
						JPanel 		panel 			= new JPanel(null);
						ButtonGroup button_group	= new ButtonGroup();
						
						Vector<CPJSprite> actors 	= Studio.getInstance().getCPJResourceManager().getNodes(
								CPJResourceType.ACTOR, 
								CPJSprite.class);
		
						progress.setMaximum(actors.size());
						
						int mw = 0;
						int mh = 0;
						
						int w = icon_size;
						int h = icon_size;
						int wc = icon_column_count;
						
						int i=0;
						for (CPJSprite actor : actors)
						{
							try{
								JToggleButton btn = new JToggleButton();
								btn.setToolTipText(actor.getName());
								btn.setLocation(i%wc * w, i/wc * h);
								btn.setSize(w, h);
								btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, actor.getSnapShoot())));
								btn.addActionListener(CPJSpritePanel.this);
								map.put(btn, actor);
								button_group.add(btn);
								panel.add(btn);
								mw = Math.max(mw, btn.getX() + btn.getWidth());
								mh = Math.max(mh, btn.getY() + btn.getHeight());
							} catch (Exception er){
								er.printStackTrace();
							}
							i++;
							progress.setValue(i);
							progress.setString(actor.getName() + 
									"    " + i +"/" + actors.size());
							
						}
		
						panel.setSize(mw, mh);
						panel.setPreferredSize(new Dimension(mw, mh));
						panel.setMinimumSize(new Dimension(mw, mh));
		
						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + actors.size());

						System.out.println("refresh resource");
					}
					finally{
						refresh.setEnabled(true);
					}
				}
			}.start();
			
		
		}
		
		@Override
		public void onSelected(CPJSprite spr) {
//			spr.getDisplayObject();
		}
	}
	
	class DEffectPanel extends ObjectPanel<DEffect>
	{
		public DEffectPanel() {
			super(DEffect.class, "特效");
		}
		
		@Override
		public void onRefresh() {

			refresh.setEnabled(false);
			
			new Thread()
			{
				@Override
				public void run() 
				{
					this.setPriority(MIN_PRIORITY);
					try{
						HashMap<JToggleButton , DEffect> map = new HashMap<JToggleButton, DEffect>();
						JPanel 		panel 			= new JPanel(new GridLayout(0, 1));
						ButtonGroup button_group	= new ButtonGroup();
						Vector<DEffect> effects 	= Studio.getInstance().getObjectManager().getObjects(DEffect.class);
						
						progress.setMaximum(effects.size());
						
						int i=0;
						for (DEffect effect : effects)
						{
							JToggleButton btn = new JToggleButton(effect.getName());
							btn.setToolTipText(effect.getName());
							btn.setIcon(effect.getIcon(false));
							btn.addActionListener(DEffectPanel.this);
							map.put(btn, effect);
							
							button_group.add(btn);
							panel.add(btn);
							
							i++;
							progress.setValue(i);
							progress.setString(effect.getName() + 
									"    " + i +"/" + effects.size());
						}

						selected_object = null;
						res_map.clear();
						res_map.putAll(map);
						scroll_pan.setViewportView(panel);
						progress.setString(i +"/" + effects.size());
						
						System.out.println("refresh effects");
					} 
					finally {
						refresh.setEnabled(true);
					}
				}
			}.start();
		
		}
		
		@Override
		public void onSelected(DEffect spr) {
			
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------
	
	abstract static class ObjectPanel<T extends G2DTreeNode<?>> extends JPanel implements ActionListener, ObjectPage<T>
	{	
		final static int icon_size			= 32;
		final static int icon_column_count	= 5;
	
		String			title			;
		
		JScrollPane		scroll_pan 		= new JScrollPane();
		JLabel 			label 			= new JLabel("单位:");
		JButton 		refresh 		= new JButton(" 刷新 ");	
		JProgressBar	progress		= new JProgressBar();
		
		Hashtable<JToggleButton , T> res_map = new Hashtable<JToggleButton, T>();
		T selected_object;
		
		ObjectPanel(Class<T> type, String title)
		{
			super(new BorderLayout());
			
			this.title = title;
			
			label.setText(title+":");
			
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(refresh);
			tool_bar.addSeparator();
			tool_bar.add(label);
			refresh.addActionListener(this);
			progress.setStringPainted(true);
			scroll_pan.getVerticalScrollBar().setUnitIncrement(icon_size);
			
			this.add(tool_bar, BorderLayout.NORTH);
			this.add(scroll_pan, BorderLayout.CENTER);
			this.add(progress, BorderLayout.SOUTH);		
		}
		
		public T getSelected() {
			return selected_object;
		}

		@Override
		public JPanel asPanel() {
			return this;
		}

		abstract void onSelected(T spr);
		
		@Override
		final public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refresh) {
				onRefresh();
			} 
			else if (res_map.containsKey(e.getSource())) {
				selected_object = res_map.get(e.getSource());
				if (selected_object != null) {
					label.setText(title + ":" + selected_object.getName());
					onSelected(selected_object);
					this.repaint(100);
				}
			}
		}
		
	}
	
	
	
	
}
