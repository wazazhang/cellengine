package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.cell.rpg.particle.ParticleAppearanceType;
import com.cell.rpg.particle.ParticleAppearanceType.DisplayNodeImage;
import com.cell.rpg.particle.ParticleAppearanceType.DisplayNodeSprite;
import com.cell.rpg.template.TEffect;
import com.g2d.annotation.Property;
import com.g2d.awt.util.Tools;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.OriginShape;
import com.g2d.display.particle.ParticleAffect;
import com.g2d.display.particle.ParticleAppearance;
import com.g2d.display.particle.Layer.TimeNode;
import com.g2d.display.particle.affects.Gravity;
import com.g2d.display.particle.affects.Vortex;
import com.g2d.display.particle.affects.Wander;
import com.g2d.display.particle.affects.Wind;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyDialog;
import com.g2d.editor.property.ObjectPropertyForm;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.CPJEffectImageSelectDialog.TileImage;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.particles.ParticleViewer;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DListSelectDialog;


@SuppressWarnings("serial")
public class EffectEditor extends JSplitPane implements ActionListener, ListSelectionListener
{
	final TEffect effect;
	
	Vector<LayerEdit>	layer_list	= new Vector<LayerEdit>();
	
	G2DList<LayerEdit> 	layers		= new G2DList<LayerEdit>();
	
	LayerEdit			last_layer;
	
	JButton layer_add	= new JButton("添加层");
	JButton layer_copy	= new JButton("复制层");
	JButton layer_del	= new JButton("删除层");
	
	JButton layer_up	= new JButton("向上");
	JButton layer_down	= new JButton("向下");
	
	JButton refresh		= new JButton("刷新");
	JButton show		= new JButton("查看");
	
	
	public EffectEditor(TEffect e) 
	{
		this.effect		= e;
		
		JPanel left = new JPanel(new BorderLayout());
		setLeftComponent(left);
		setRightComponent(new JPanel());
		
		// init left
		{
			JToolBar bar = new JToolBar();
			
			bar.add	(layer_add);
			bar.add	(layer_copy);
			bar.add	(layer_del);
			bar.addSeparator();
			bar.add	(layer_up);
			bar.add	(layer_down);
			bar.addSeparator();
			bar.add	(refresh);
			bar.add	(show);
			
			layer_add	.addActionListener(this);
			layer_copy	.addActionListener(this);
			layer_del	.addActionListener(this);
			
			refresh		.addActionListener(this);
			show		.addActionListener(this);
			
			layer_up	.addActionListener(this);
			layer_down	.addActionListener(this);
			
			layers.addListSelectionListener(this);
			layers.setMinimumSize(new Dimension(400, 400));
			layers.setPreferredSize(new Dimension(400, 400));
			left.add(bar, BorderLayout.NORTH);
			left.add(new JScrollPane(layers), BorderLayout.CENTER);
		}
		
		for (Layer layer : e.particles) {
			LayerEdit edit = new LayerEdit(layer);
			layer_list.add(edit);
		}
		
		layers.setListData(layer_list);
	}

	public TEffect getData() {
		try{
			for (LayerEdit edit : layer_list) {
				edit.getData();
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return effect;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object obj = layers.getSelectedValue();
		if (obj instanceof LayerEdit) {
			LayerEdit right = (LayerEdit)obj;
			if (last_layer != null) {
				right.setSelectedIndex(last_layer.getSelectedIndex());
			}
			last_layer = right;
			setRightComponent(right);
		} else {
			setRightComponent(new JPanel());
		}
		layers.repaint();
	}
		
	private void swapLayer(int direct) {
		Object obj = layers.getSelectedValue();
		if (obj instanceof LayerEdit) {
			LayerEdit right = (LayerEdit)obj;
			int si = layer_list.indexOf(right);
			if (si >= 0) {
				if (direct < 0 && si > 0) {
					Collections.swap(layer_list, si, si-1);
					effect.particles.swap(si, si-1);
					layers.setSelectedIndex(si-1);
				} else if (direct > 0 && si < layer_list.size() - 1) {
					Collections.swap(layer_list, si, si+1);
					effect.particles.swap(si, si+1);
					layers.setSelectedIndex(si+1);
				}
			}
		}
		layers.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == layer_add) 
		{
			Layer layer = new Layer();
			effect.particles.addLayer(layer);
			LayerEdit edit = new LayerEdit(layer);
			layer_list.add(edit);
			layers.setListData(layer_list);
			layers.setSelectedValue(edit, true);
		}
		else if (e.getSource() == layer_copy)
		{
			Object obj = layers.getSelectedValue();
			if (obj instanceof LayerEdit) {
				LayerEdit right = (LayerEdit)obj;
				Layer layer = CIO.cloneObject(right.layer);
				layer.alias = layer + " Copy";
				effect.particles.addLayer(layer);
				LayerEdit edit = new LayerEdit(layer);
				layer_list.add(edit);
				layers.setListData(layer_list);
				layers.setSelectedValue(edit, true);
			}
		}
		else if (e.getSource() == layer_del) 
		{
			Object obj = layers.getSelectedValue();
			if (obj instanceof LayerEdit) {
				LayerEdit right = (LayerEdit)obj;
				effect.particles.removeLayer(right.layer);
				layer_list.remove(right);
				setRightComponent(new JPanel());
				layers.setListData(layer_list);
			}
		}
		else if (e.getSource() == refresh) 
		{
			layers.setListData(layer_list);
		}
		else if (e.getSource() == show)
		{
			ParticleViewer viewer = new ParticleViewer(this);
			viewer.setVisible(true);
		}
		else if (e.getSource() == layer_up)
		{
			swapLayer(-1);
		}	
		else if (e.getSource() == layer_down)
		{
			swapLayer(1);
		}
		layers.repaint();
		
	}
		


//	--------------------------------------------------------------------------------------------------------------------

	static class LayerEdit extends JTabbedPane implements G2DListItem
	{
		final Layer layer;
		
		PageAppearance	page_appearance	;
		PageScene		page_scene		;
		PageOrigin 		page_origin		;
		PageTimeLine	page_timeline	;
		PageInfluences	page_influences	;
		
		public LayerEdit(Layer layer) 
		{
			this.layer = layer;
			this.page_appearance	= new PageAppearance();
			this.page_scene			= new PageScene();
			this.page_origin		= new PageOrigin();
			this.page_timeline		= new PageTimeLine();
			this.page_influences	= new PageInfluences();
			addTab("场景", 		page_scene);
			addTab("粒子外观", 	page_appearance);
			addTab("发射", 		page_origin);
			addTab("粒子变化",	page_timeline);
			addTab("影响", 		page_influences);
			setData(layer);
		}
		
		void setData(Layer layer) {
			page_scene			.setData(layer);
			page_appearance		.setData(layer);
			page_origin			.setData(layer);
			page_timeline		.setData(layer);
			page_influences		.setData(layer);
		}
		
		void getData() {
			page_scene			.getData(layer);
			page_appearance		.getData(layer);
			page_origin			.getData(layer);
			page_timeline		.getData(layer);
			page_influences		.getData(layer);
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		@Override
		public ImageIcon getListIcon(boolean update) {
			return new ImageIcon(Res.icon_layer);
		}
		@Override
		public String getListName() {
			return toString();
		}
		@Override
		public String toString() {
			return layer + "";
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		abstract class PropertyPage extends JPanel
		{
			public PropertyPage() {
				super(null);
			}
			
			abstract void setData(Layer layer);
			abstract void getData(Layer layer);
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		class PageAppearance extends PropertyPage implements ActionListener
		{
			ListEnumEdit<ParticleAppearanceType> 
			type_list = new ListEnumEdit<ParticleAppearanceType>(ParticleAppearanceType.class);
			
			Hashtable<ParticleAppearanceType, PropertyPage> 
			panels = new Hashtable<ParticleAppearanceType, PropertyPage>();
			
			JScrollPane center = new JScrollPane(new JPanel());
			
			public PageAppearance() 
			{
				super.setLayout(new BorderLayout());				
				
				panels.put(ParticleAppearanceType.IMAGE, new AppearanceImage());
				panels.put(ParticleAppearanceType.SPRITE, new AppearanceSprite());

				JPanel top = new JPanel(new BorderLayout());
				top.add(new JLabel("选择外观类型"), BorderLayout.WEST);
				top.add(type_list, BorderLayout.CENTER);

				type_list.addActionListener(this);
				
				if (layer.appearance instanceof ParticleAppearanceType.DisplayNodeImage) {
					type_list.setValue(ParticleAppearanceType.IMAGE);
				}
				if (layer.appearance instanceof ParticleAppearanceType.DisplayNodeSprite) {
					type_list.setValue(ParticleAppearanceType.SPRITE);
				}
				
				this.add(top, BorderLayout.NORTH);
				this.add(center, BorderLayout.CENTER);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyPage page = panels.get(type_list.getValue());
				if (page != null) {
					center.setViewportView(page);
				}
			}
			
			@Override
			void getData(Layer layer) {
				PropertyPage page = panels.get(type_list.getValue());
				if (page != null) {
					page.getData(layer);
				}
			}
			
			@Override
			void setData(Layer layer) {
				PropertyPage page = panels.get(type_list.getValue());
				if (page != null) {
					page.setData(layer);
				}
			}
			
			abstract class AppearancePage extends PropertyPage implements ActionListener
			{
				JButton btn_property = new JButton("属性");
				JToolBar tools = new JToolBar();
				
				public AppearancePage() 
				{
					super.setLayout(new BorderLayout());
					super.add(tools, BorderLayout.NORTH);
					
					btn_property.addActionListener(this);
					tools.add(btn_property);
					tools.addSeparator();
					tools.setFloatable(false);
				}
				
				abstract ParticleAppearance getAppearance() ;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btn_property) {
						if (getAppearance() != null) {
							ObjectPropertyDialog d = new ObjectPropertyDialog(this, 
									new ObjectPropertyPanel(getAppearance()));
							d.setVisible(true);
						}
					}
				}
			}
			
			class AppearanceImage extends AppearancePage
			{
				DisplayNodeImage	appearance;				
				JButton				image_brwoser_btn	= new JButton("浏览图片");
				JLabel 				image_view			= new JLabel();
				
				public AppearanceImage() 
				{
					tools.add(image_brwoser_btn);
					
					add(image_view, BorderLayout.CENTER);
					image_brwoser_btn.addActionListener(this);			
					
				}
				
				@Override
				ParticleAppearance getAppearance() {
					return appearance;
				}
				
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					super.actionPerformed(e);
					
					if (e.getSource() == image_brwoser_btn) {
						TileImage ret = new CPJEffectImageSelectDialog(this).showDialog();
						if (ret != null) {
							if (appearance == null) {
								appearance = new DisplayNodeImage();
							}
							appearance.cpj_image_id		= ret.index;
							appearance.cpj_project_name	= ret.parent_name;
							appearance.cpj_sprite_name	= ret.sprite_name;
							BufferedImage buff			= ret.getEffectImage();
							appearance.setImage(Tools.wrap_g2d(buff));
							layer.appearance = appearance;
							if (appearance.getImage() != null) {
								image_view.setIcon(Tools.createIcon(buff));
							}
						}
					}
				}

				void getData(Layer layer) {
					if (appearance != null) {
						layer.appearance = appearance;
					}
				}

				void setData(Layer layer) {
					if (layer.appearance instanceof DisplayNodeImage) {
						appearance = (DisplayNodeImage) layer.appearance;
						TileImage ret = new TileImage(
								appearance.cpj_project_name,
								appearance.cpj_sprite_name,
								appearance.cpj_image_id);
						BufferedImage buff = ret.getEffectImage();
						appearance.setImage(Tools.wrap_g2d(buff));
						if (buff != null) {
							image_view.setIcon(Tools.createIcon(buff));
						}
					}
				}
				
				
				
			}
			
			
			class AppearanceSprite extends AppearancePage
			{
				DisplayNodeSprite	appearance;
				
				JButton				image_brwoser_btn	= new JButton("浏览精灵");
				JLabel 				image_view			= new JLabel();
				
				JLabel				sprite_anim		= new JLabel("动画号");
				JSpinner			sprite_anim_v 	= new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));
				JLabel				sprite_anim_max	= new JLabel("");
				
				public AppearanceSprite() 
				{
					tools.add(image_brwoser_btn);
					
					add(image_view, BorderLayout.CENTER);
					JPanel south = new JPanel(new BorderLayout());
					south.add(sprite_anim, BorderLayout.WEST);
					south.add(sprite_anim_v, BorderLayout.CENTER);
					south.add(sprite_anim_max, BorderLayout.EAST);
					add(south, BorderLayout.SOUTH);
					
					image_brwoser_btn.addActionListener(this);				
				}

				@Override
				ParticleAppearance getAppearance() {
					return appearance;
				}
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					super.actionPerformed(e);
					
					if (e.getSource() == image_brwoser_btn) {
						CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(this, CPJResourceType.EFFECT).showDialog();
						if (spr != null) {
							if (appearance == null) {
								appearance = new DisplayNodeSprite();
							}
							appearance.cpj_project_name	= spr.parent.name;
							appearance.cpj_sprite_name	= spr.name;
							appearance.setSprite(spr.createCSprite());
							layer.appearance = appearance;
							
							sprite_anim_v.setModel(new SpinnerNumberModel(0, 0, appearance.getSprite().getAnimateCount()-1, 1));
							sprite_anim_v.setValue(0);
							sprite_anim_max.setText("最大"+(appearance.getSprite().getAnimateCount()-1)+" ");
							image_view.setIcon(Tools.createIcon(spr.getSnapShoot()));
						}
					}
				}

				void getData(Layer layer) {
					if (appearance != null) {
						appearance.sprite_anim = (Integer)(sprite_anim_v.getValue());
						layer.appearance = appearance;
					}
				}

				void setData(Layer layer) {
					if (layer.appearance instanceof DisplayNodeSprite) {
						appearance = (DisplayNodeSprite) layer.appearance;
						CPJIndex<CPJSprite> index = Studio.getInstance().getCPJResourceManager().getNode(
								CPJResourceType.EFFECT, 
								appearance.cpj_project_name, 
								appearance.cpj_sprite_name);
						if (index != null) {
							CPJSprite spr = Studio.getInstance().getCPJResourceManager().getNode(index);
							if (spr != null) {
								appearance.setSprite(spr.createCSprite());
								sprite_anim_v.setModel(new SpinnerNumberModel(0, 0, appearance.getSprite().getAnimateCount()-1, 1));
								sprite_anim_v.setValue(appearance.sprite_anim);
								sprite_anim_max.setText(
										" 动画数:"+   (appearance.getSprite().getAnimateCount()-1)+
										" 总共帧数:" + appearance.getSprite().getFrameCount(appearance.sprite_anim));
								image_view.setIcon(Tools.createIcon(spr.getSnapShoot()));
							}
						}
					}
				}
				
				
				
			}
		}
//		-------------------------------------------------------------------------------------------------------------------------------

		class PageScene extends PropertyPage implements KeyListener
		{
			JLabel		name					= new JLabel("名字");
			JTextField	name_v 					= new JTextField();
			
			JLabel		particles_capacity		= new JLabel("粒子容量");
			JSpinner	particles_capacity_v 	= new JSpinner(new SpinnerNumberModel(300, 0, Integer.MAX_VALUE, 1));
			JLabel		particle_age			= new JLabel("粒子生命周期时间范围(帧)");
			JSpinner	particle_min_age_v		= new JSpinner(new SpinnerNumberModel(30, 1, Integer.MAX_VALUE, 1)), 
						particle_max_age_v		= new JSpinner(new SpinnerNumberModel(60, 1, Integer.MAX_VALUE, 1));
			JLabel		particles_per_frame		= new JLabel("粒子每帧释放多少个");
			JSpinner	particles_per_frame_v 	= new JSpinner(new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 1));
			JCheckBox	particles_cointinued_v 	= new JCheckBox("粒子持续释放");
			JCheckBox	is_local_coordinate_v 	= new JCheckBox("粒子是否释放到本身坐标系统");
			
			public PageScene() 
			{
				int sx = 20, sy = 20;
				{
					name					.setBounds(sx, sy, 200, 24); sy += 25;
					name_v					.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(name);
					super.add(name_v);
					
					particles_capacity		.setBounds(sx, sy, 200, 24); sy += 25;
					particles_capacity_v	.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(particles_capacity);
					super.add(particles_capacity_v);
					
					particle_age			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					particle_min_age_v		.setBounds(sx + 0,   sy, 99,  24);
					particle_max_age_v		.setBounds(sx + 101, sy, 99,  24); sy += 25;
					super.add(particle_age);
					super.add(particle_min_age_v);
					super.add(particle_max_age_v);
					
					particles_per_frame		.setBounds(sx, sy, 200, 24); sy += 25;
					particles_per_frame_v	.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(particles_per_frame);
					super.add(particles_per_frame_v);
					
					particles_cointinued_v	.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(particles_cointinued_v);
					
					is_local_coordinate_v	.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(is_local_coordinate_v);
					
					name_v.addKeyListener(this);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				layer.alias = name_v.getText();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				layer.alias = name_v.getText();
			}
			public void keyTyped(KeyEvent e) {
				layer.alias = name_v.getText();
			}
			
			void setData(Layer layer) {
				name_v					.setText(layer.toString());
				particles_capacity_v	.setValue(layer.particles_capacity);
				particle_min_age_v		.setValue(layer.particle_min_age);
				particle_max_age_v		.setValue(layer.particle_max_age);
				particles_per_frame_v	.setValue(layer.particles_per_frame);
				particles_cointinued_v	.setSelected(layer.particles_continued);
				is_local_coordinate_v	.setSelected(layer.is_local_coordinate);
			}
			
			void getData(Layer layer) {
				layer.alias					= name_v.getText();
				layer.particles_capacity	= Parser.castNumber(particles_capacity_v.getValue(), Integer.class);
				layer.particle_min_age		= Parser.castNumber(particle_min_age_v.getValue(), Integer.class);
				layer.particle_max_age		= Parser.castNumber(particle_max_age_v.getValue(), Integer.class);
				layer.particles_per_frame	= Parser.castNumber(particles_per_frame_v.getValue(), Integer.class);
				layer.particles_continued	= particles_cointinued_v.isSelected();
				layer.is_local_coordinate	= is_local_coordinate_v.isSelected();
			}
		}
//		-------------------------------------------------------------------------------------------------------------------------------

		class PageOrigin extends PropertyPage implements ActionListener
		{
			JLabel		origin_pos				= new JLabel("发射基地位置");
			JSpinner	origin_x_v 				= new JSpinner(new SpinnerNumberModel(0, -Float.MAX_VALUE, Float.MAX_VALUE, 1f)),
						origin_y_v				= new JSpinner(new SpinnerNumberModel(0, -Float.MAX_VALUE, Float.MAX_VALUE, 1f));
			JLabel		origin_rotation_angle	= new JLabel("发射基地变换角度");
			JSpinner	origin_rotation_angle_v	= new JSpinner(new SpinnerNumberModel(-90f, -360d, 360d, 1f));
			JLabel		origin_scale			= new JLabel("发射基地拉伸");
			JSpinner	origin_scale_x_v		= new JSpinner(new SpinnerNumberModel(1f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f)),
						origin_scale_y_v		= new JSpinner(new SpinnerNumberModel(1f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
			
			JLabel		origin_shape			= new JLabel("发射基地几何造型");
			OriginShapeType	origin_shape_rect	= new Rectangle();
			OriginShapeType origin_shape_ring	= new Ring();
			JComboBox	origin_shape_list		= new JComboBox(new OriginShapeType[]{
					origin_shape_rect,
					origin_shape_ring,
			});
			JScrollPane	orgin_shape_pane		= new JScrollPane(new JPanel());
			
			JCheckBox	spawn_orgin_angle		= new JCheckBox("以原点发射");
		
			JLabel		spawn_angle				= new JLabel("发射角度");
			JSpinner	spawn_angle_v			= new JSpinner(new SpinnerNumberModel(-90f, -360d, 360d, 1f));
			JLabel		spawn_angle_range		= new JLabel("发射角度随机±范围");
			JSpinner	spawn_angle_range_v		= new JSpinner(new SpinnerNumberModel(-90f, -360d, 360d, 1f));
			
			JLabel		spawn_velocity			= new JLabel("发射速度");
			JSpinner	spawn_velocity_v		= new JSpinner(new SpinnerNumberModel(4.0f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
			JLabel		spawn_velocity_range	= new JLabel("发射速度随机±范围");
			JSpinner	spawn_velocity_range_v	= new JSpinner(new SpinnerNumberModel(2.0f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
			
			JLabel		spawn_acc				= new JLabel("发射加速度");
			JSpinner	spawn_acc_v				= new JSpinner(new SpinnerNumberModel(1.0f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.01f));
			JLabel		spawn_acc_range			= new JLabel("发射加速度随机±范围");
			JSpinner	spawn_acc_range_v		= new JSpinner(new SpinnerNumberModel(0.0f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.01f));
			
			JLabel		spawn_damp				= new JLabel("速度阻尼");
			JSpinner	spawn_damp_v			= new JSpinner(new SpinnerNumberModel(1.0f, 0f, Float.MAX_VALUE, 0.01f));
			JLabel		spawn_damp_range		= new JLabel("速度阻尼随机±范围");
			JSpinner	spawn_damp_range_v		= new JSpinner(new SpinnerNumberModel(0.0f, -Float.MAX_VALUE, Float.MAX_VALUE, 0.01f));
			
			
			public PageOrigin() 
			{
				int sx = 20, sy = 20;
				{
					origin_pos.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					origin_x_v.setBounds(sx + 0,   sy, 99,  24);
					origin_y_v.setBounds(sx + 101, sy, 99,  24); sy += 25;
					super.add(origin_pos);
					super.add(origin_x_v);
					super.add(origin_y_v);
				
					origin_rotation_angle	.setBounds(sx, sy, 200, 24); sy += 25;
					origin_rotation_angle_v	.setBounds(sx, sy, 200, 24); sy += 25;
					super.add(origin_rotation_angle);
					super.add(origin_rotation_angle_v);
				
					origin_scale	.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					origin_scale_x_v.setBounds(sx + 0,   sy, 99,  24);
					origin_scale_y_v.setBounds(sx + 101, sy, 99,  24); sy += 25;
					super.add(origin_scale);
					super.add(origin_scale_x_v);
					super.add(origin_scale_y_v);
				
					origin_shape		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					origin_shape_list	.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					orgin_shape_pane	.setBounds(sx + 0,   sy, 200, 200); sy += 200;
					super.add(origin_shape);
					super.add(origin_shape_list);
					super.add(orgin_shape_pane);
					
					origin_shape_list.addActionListener(this);
					origin_shape_list.setSelectedIndex(0);
				}
				
				sy = 20; sx += 250;
				{
					spawn_orgin_angle		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_angle				.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_angle_v			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_angle_range		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_angle_range_v		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					super.add(spawn_orgin_angle);
					super.add(spawn_angle);
					super.add(spawn_angle_v);
					super.add(spawn_angle_range);
					super.add(spawn_angle_range_v);
					
					spawn_orgin_angle.addActionListener(this);
					
					spawn_velocity			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_velocity_v		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_velocity_range	.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_velocity_range_v	.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					super.add(spawn_velocity);
					super.add(spawn_velocity_v);
					super.add(spawn_velocity_range);
					super.add(spawn_velocity_range_v);
					
					spawn_acc				.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_acc_v				.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_acc_range			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_acc_range_v		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					super.add(spawn_acc);
					super.add(spawn_acc_v);
					super.add(spawn_acc_range);
					super.add(spawn_acc_range_v);
					
					spawn_damp				.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_damp_v			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_damp_range		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					spawn_damp_range_v		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
					super.add(spawn_damp);
					super.add(spawn_damp_v);
					super.add(spawn_damp_range);
					super.add(spawn_damp_range_v);
				}
			}
			
			void setData(Layer layer)
			{
				origin_x_v				.setValue(layer.origin_x);
				origin_y_v				.setValue(layer.origin_y);
				origin_rotation_angle_v	.setValue(Math.toDegrees(layer.origin_rotation_angle));
				origin_scale_x_v		.setValue(layer.origin_scale_x);
				origin_scale_y_v		.setValue(layer.origin_scale_y);
				
				if (layer.origin_shape instanceof OriginShape.Rectangle) {
					origin_shape_rect.setShape(layer.origin_shape);
					origin_shape_list.setSelectedItem(origin_shape_rect);
				} 
				else if (layer.origin_shape instanceof OriginShape.Ring) {
					origin_shape_ring.setShape(layer.origin_shape);
					origin_shape_list.setSelectedItem(origin_shape_ring);
				}
				
				spawn_orgin_angle		.setSelected(layer.spawn_orgin_angle);
				
				spawn_angle_v			.setValue(Math.toDegrees(layer.spawn_angle));
				spawn_angle_range_v		.setValue(Math.toDegrees(layer.spawn_angle_range));
				
				spawn_velocity_v		.setValue(layer.spawn_velocity);
				spawn_velocity_range_v	.setValue(layer.spawn_velocity_range);
			
				spawn_acc_v				.setValue(layer.spawn_acc);
				spawn_acc_range_v		.setValue(layer.spawn_acc_range);

				spawn_damp_v			.setValue(layer.spawn_damp);
				spawn_damp_range_v		.setValue(layer.spawn_damp_range);
				
				spawn_angle_v			.setEnabled(!spawn_orgin_angle.isSelected());
				spawn_angle_range_v		.setEnabled(!spawn_orgin_angle.isSelected());
				
			}
			
			void getData(Layer layer) 
			{
				layer.origin_x				= Parser.castNumber(origin_x_v.getValue(), Float.class);
				layer.origin_y				= Parser.castNumber(origin_y_v.getValue(), Float.class);
				layer.origin_rotation_angle	= (float)Math.toRadians((Double)(origin_rotation_angle_v.getValue()));
				layer.origin_scale_x		= Parser.castNumber(origin_scale_x_v.getValue(), Float.class);
				layer.origin_scale_y		= Parser.castNumber(origin_scale_y_v.getValue(), Float.class);
				
				if (origin_shape_list.getSelectedItem() instanceof OriginShapeType) {
					OriginShapeType type	= (OriginShapeType)origin_shape_list.getSelectedItem();
					layer.origin_shape		= type.getShape();
				}
				
				layer.spawn_orgin_angle		= spawn_orgin_angle.isSelected();
				
				layer.spawn_angle			= (float)Math.toRadians((Double)(spawn_angle_v.getValue()));
				layer.spawn_angle_range		= (float)Math.toRadians((Double)(spawn_angle_range_v.getValue()));
				
				layer.spawn_velocity		= Parser.castNumber(spawn_velocity_v.getValue(), Float.class);
				layer.spawn_velocity_range	= Parser.castNumber(spawn_velocity_range_v.getValue(), Float.class);
				
				layer.spawn_acc				= Parser.castNumber(spawn_acc_v.getValue(), Float.class);
				layer.spawn_acc_range		= Parser.castNumber(spawn_acc_range_v.getValue(), Float.class);
				
				layer.spawn_damp			= Parser.castNumber(spawn_damp_v.getValue(), Float.class);
				layer.spawn_damp_range		= Parser.castNumber(spawn_damp_range_v.getValue(), Float.class);
				
				spawn_angle_v			.setEnabled(!spawn_orgin_angle.isSelected());
				spawn_angle_range_v		.setEnabled(!spawn_orgin_angle.isSelected());
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == origin_shape_list) {
					Object obj = origin_shape_list.getSelectedItem();
					if (obj instanceof OriginShapeType) {
						orgin_shape_pane.setViewportView((OriginShapeType)obj);
					}
				}
				spawn_angle_v			.setEnabled(!spawn_orgin_angle.isSelected());
				spawn_angle_range_v		.setEnabled(!spawn_orgin_angle.isSelected());
			}
			
			
			abstract class OriginShapeType extends JPanel
			{
				final String 	name;
				final Class<?> 	type;
				public OriginShapeType(String name, Class<?> cls) {
					this.name = name;
					this.type = cls;
				}
				public String toString() {
					return name;
				}
				
				abstract OriginShape getShape() ;
				
				abstract void setShape(OriginShape shape);
			}
			
			class Rectangle extends OriginShapeType
			{
				JLabel lbl_x = new JLabel("X");
				JLabel lbl_y = new JLabel("Y");
				JLabel lbl_w = new JLabel("宽");
				JLabel lbl_h = new JLabel("高");
				JSpinner x = new JSpinner(new SpinnerNumberModel(-100, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
				JSpinner y = new JSpinner(new SpinnerNumberModel(-100, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
				JSpinner w = new JSpinner(new SpinnerNumberModel(200, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
				JSpinner h = new JSpinner(new SpinnerNumberModel(200, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
				
				public Rectangle()
				{
					super("矩形", OriginShape.Rectangle.class);
					super.setLayout(null);
					
					lbl_x.setHorizontalAlignment(SwingConstants.RIGHT);
					lbl_y.setHorizontalAlignment(SwingConstants.RIGHT);
					lbl_w.setHorizontalAlignment(SwingConstants.RIGHT);
					lbl_h.setHorizontalAlignment(SwingConstants.RIGHT);
					
					lbl_x.setBounds(0,   0, 80, 24); x.setBounds(80,   0, 100, 24); 
					lbl_y.setBounds(0,  25, 80, 24); y.setBounds(80,  25, 100, 24); 
					lbl_w.setBounds(0,  50, 80, 24); w.setBounds(80,  50, 100, 24); 
					lbl_h.setBounds(0,  75, 80, 24); h.setBounds(80,  75, 100, 24); 
					
					super.add(lbl_x); super.add(x);
					super.add(lbl_y); super.add(y);
					super.add(lbl_w); super.add(w);
					super.add(lbl_h); super.add(h);
					
				}
				
				@Override
				public void setShape(OriginShape shape) {
					if (shape instanceof OriginShape.Rectangle) {
						OriginShape.Rectangle s = (OriginShape.Rectangle)shape;
						x.setValue(s.x);
						y.setValue(s.y);
						w.setValue(s.w);
						h.setValue(s.h);
					}
				}
				
				@Override
				public OriginShape getShape() {
					OriginShape.Rectangle ret = new OriginShape.Rectangle();
					ret.x = Parser.castNumber(x.getValue(), Float.class);
					ret.y = Parser.castNumber(y.getValue(), Float.class);
					ret.w = Parser.castNumber(w.getValue(), Float.class);
					ret.h = Parser.castNumber(h.getValue(), Float.class);
					return ret;
				}
			}
			
			class Ring extends OriginShapeType
			{
				JLabel lbl_r1 = new JLabel("内圆半径");
				JLabel lbl_r2 = new JLabel("外圆半径");
				JSpinner r1 = new JSpinner(new SpinnerNumberModel(100, 0, Float.MAX_VALUE, 0.1f));
				JSpinner r2 = new JSpinner(new SpinnerNumberModel(200, 0, Float.MAX_VALUE, 0.1f));
				
				public Ring() {
					super("圆环", OriginShape.Ring.class);
					super.setLayout(null);
					
					lbl_r1.setHorizontalAlignment(SwingConstants.RIGHT);
					lbl_r2.setHorizontalAlignment(SwingConstants.RIGHT);
					
					lbl_r1.setBounds(0,   0, 80, 24); r1.setBounds(80,   0, 100, 24); 
					lbl_r2.setBounds(0,  25, 80, 24); r2.setBounds(80,  25, 100, 24); 
					
					super.add(lbl_r1); super.add(r1);
					super.add(lbl_r2); super.add(r2);
				}
				
				@Override
				public void setShape(OriginShape shape) {
					if (shape instanceof OriginShape.Ring) {
						OriginShape.Ring s = (OriginShape.Ring)shape;
						r1.setValue(s.radius1);
						r2.setValue(s.radius2);
					}
				}
				
				@Override
				public OriginShape getShape() {
					OriginShape.Ring ret = new OriginShape.Ring();
					ret.radius1 = Parser.castNumber(r1.getValue(), Float.class);
					ret.radius2 = Parser.castNumber(r2.getValue(), Float.class);
					return ret;
				}
			}
			
		}
//		-------------------------------------------------------------------------------------------------------------------------------

		class PageTimeLine extends PropertyPage implements ActionListener, ListSelectionListener
		{
			JSplitPane 		split		= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			G2DList<LineEdit>
							timeline	= new G2DList<LineEdit>();
			
			JScrollPane 	bottom		= new JScrollPane();

			JButton			line_ref	= new JButton("刷新");
			JButton			line_add	= new JButton("添加");
			JButton			line_del	= new JButton("删除");
			
			Vector<LineEdit> timeline_data = new Vector<LineEdit>();
			
			public PageTimeLine()
			{
				super.setLayout(new BorderLayout());
				{
					JPanel toppan = new JPanel(new BorderLayout());
					JScrollPane top = new JScrollPane(timeline);
					top.setMinimumSize(new Dimension(200, 200));
					toppan.add(top, BorderLayout.CENTER);
					JToolBar bar = new JToolBar();
					bar.add(line_ref);
					bar.addSeparator();
					bar.add(line_add);
					bar.add(line_del);
					toppan.add(bar, BorderLayout.NORTH);
					split.setTopComponent(toppan);
					
					line_ref.addActionListener(this);
					line_add.addActionListener(this);
					line_del.addActionListener(this);
				}
				
				split.setBottomComponent(bottom);
				
				this.add(split, BorderLayout.CENTER);
				
				timeline.addListSelectionListener(this);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == line_ref) {
					Collections.sort(timeline_data);
					timeline.setListData(timeline_data);
				}
				else if (e.getSource() == line_add) {
					TimeNode node = new TimeNode(0.5f);
					if (layer.timeline.addTimeNode(node)) {
						LineEdit edit = new LineEdit(node, timeline);
						timeline_data.add(edit);
						Collections.sort(timeline_data);
						timeline.setListData(timeline_data);
						timeline.setSelectedValue(edit, true);
					}
				} 
				else if (e.getSource() == line_del) {
					LineEdit item = timeline.getSelectedItem();
					if (layer.timeline.removeTimeNode(item.time_node)) {
						timeline_data.remove(item);
						Collections.sort(timeline_data);
						timeline.setListData(timeline_data);
					}
				}
				validateItem();
			}
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource() == timeline) {
					LineEdit item = timeline.getSelectedItem();
					bottom.setViewportView(item);
				}
				validateItem();
			}
			
			@Override
			void getData(Layer layer) {
				for (LineEdit e : timeline_data) {
					e.getData();
				}
			}
			
			@Override
			void setData(Layer layer) 
			{
				boolean change = false;
				for (TimeNode node : layer.timeline) {
					LineEdit edit = getTimeNodeEdit(node);
					if (edit == null) {
						edit = new LineEdit(node, timeline);
						timeline_data.add(edit);
						change = true;
					}
				}
				if (change) {
					Collections.sort(timeline_data);
					timeline.setListData(timeline_data);
					timeline.repaint();
				}

				validateItem();
			}
			
			private void validateItem() {
				if (timeline.getSelectedItem()!= null) {
					if (timeline.getSelectedItem().time_node == layer.timeline.getStart() ||
						timeline.getSelectedItem().time_node == layer.timeline.getEnd()) {
						line_del.setVisible(false);
					} else {
						line_del.setVisible(true);
					}
				} else {
					line_del.setVisible(false);
				}
				for (LineEdit e : timeline_data) {
					e.getData();
				}
			}
			
			private LineEdit getTimeNodeEdit(TimeNode node) {
				for (LineEdit e : timeline_data) {
					if (e.time_node == node) {
						return e;
					}
				}
				return null;
			}
		}

//		-------------------------------------------------------------------------------------------------------------------------------

		class PageInfluences extends PropertyPage implements ActionListener, ListSelectionListener
		{
			JSplitPane 		split		= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			
			G2DList<AffectEdit>	affects			= new G2DList<AffectEdit>();
			Vector<AffectEdit>	affects_data	= new Vector<AffectEdit>();
			
			JScrollPane 	right		= new JScrollPane();

			JButton			btn_ref		= new JButton("刷新");
			JButton			btn_add		= new JButton("添加");
			JButton			btn_del		= new JButton("删除");
			
			public PageInfluences()
			{
				super.setLayout(new BorderLayout());
				{
					JPanel toppan = new JPanel(new BorderLayout());
					JScrollPane top = new JScrollPane(affects);
					top.setMinimumSize(new Dimension(200, 200));
					toppan.add(top, BorderLayout.CENTER);
					JToolBar bar = new JToolBar();
					bar.add(btn_ref);
					bar.addSeparator();
					bar.add(btn_add);
					bar.add(btn_del);
					toppan.add(bar, BorderLayout.NORTH);
					split.setLeftComponent(toppan);
					
					btn_ref.addActionListener(this);
					btn_add.addActionListener(this);
					btn_del.addActionListener(this);
				}
				
				split.setRightComponent(right);
				
				this.add(split, BorderLayout.CENTER);
				
				affects.addListSelectionListener(this);
			}
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn_ref) {
					affects.setListData(affects_data);
					affects.repaint();
				}
				else if (e.getSource() == btn_add) {
					DialogAddAffect dialog = new DialogAddAffect(this, null);
					AffectType type = dialog.showDialog();
					if (type != null) {
						try{
							ParticleAffect af = (ParticleAffect)type.type.newInstance();
							AffectEdit edit = new AffectEdit(af);
							layer.affects.add(af);
							affects_data.add(edit);
							affects.setListData(affects_data);
							affects.setSelectedValue(edit, true);
						}catch(Exception er){
							er.printStackTrace();
						}
					}
				} 
				else if (e.getSource() == btn_del) {
					AffectEdit edit = affects.getSelectedItem();
					if (edit != null) {
						layer.affects.remove(edit.affect);
						affects_data.remove(edit);
						affects.setListData(affects_data);
					}
				}
			}
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource() == affects) {
					AffectEdit item = affects.getSelectedItem();
					right.setViewportView(item);
				}
			}
			
			@Override
			void getData(Layer layer) {
				for (AffectEdit e : affects_data) {
					e.getData();
				}
			}
			
			@Override
			void setData(Layer layer) 
			{
				boolean change = false;
				for (ParticleAffect node : layer.affects) {
					AffectEdit edit = getNodeEdit(node);
					if (edit == null) {
						edit = new AffectEdit(node);
						affects_data.add(edit);
						change = true;
					}
				}
				if (change) {
					affects.setListData(affects_data);
					affects.repaint();
				}
			}
			
			AffectEdit getNodeEdit(ParticleAffect affect) {
				for (AffectEdit e : affects_data) {
					if (e.affect == affect) {
						return e;
					}
				}
				return null;
			}
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	static class LineEdit extends JPanel implements G2DListItem, ActionListener, Comparable<LineEdit>
	{
		final TimeNode 				time_node;
		final G2DList<LineEdit>		list;
		
		JLabel 		position		= new JLabel("播放位置");
		JSpinner 	position_v		= new JSpinner(new SpinnerNumberModel(0d, 0d, 100d, 1f));
		
//		JCheckBox 	enable_color	= new JCheckBox("颜色变化");
//		JSpinner 	color_v			= new JSpinner(new SpinnerNumberModel(0d, 0d, 100d, 1f));

		JCheckBox 	enable_size		= new JCheckBox("缩放变化");
		JSpinner 	size_v			= new JSpinner(new SpinnerNumberModel(1d, -Float.MAX_VALUE, Float.MAX_VALUE, 0.1f));
		
		JCheckBox 	enable_spin		= new JCheckBox("旋转变化");
		JSpinner 	spin_v			= new JSpinner(new SpinnerNumberModel(0, -Float.MAX_VALUE, Float.MAX_VALUE, 1d));

		JCheckBox 	enable_alpha	= new JCheckBox("Alpha变化");
		JSpinner 	alpha_v			= new JSpinner(new SpinnerNumberModel(1d, 0d, 1d, 0.1d));
		
		public LineEdit(TimeNode time_node, G2DList<LineEdit> list) 
		{
			super(null);
			this.list		= list;
			this.time_node	= time_node;
			
			int sx = 20, sy = 20;
			{
				position		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				position_v		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				super.add(position);
				super.add(position_v);
			
				enable_size		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				size_v			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				super.add(enable_size);
				super.add(size_v);
			
				enable_spin		.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				spin_v			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				super.add(enable_spin);
				super.add(spin_v);
				
				enable_alpha	.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				alpha_v			.setBounds(sx + 0,   sy, 200, 24); sy += 25;
				super.add(enable_alpha);
				super.add(alpha_v);
			}
			
			enable_size.addActionListener(this);
			enable_spin.addActionListener(this);
			enable_alpha.addActionListener(this);
			
			setData();
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			size_v		.setEnabled(enable_size.isSelected());
			spin_v		.setEnabled(enable_spin.isSelected());
			alpha_v		.setEnabled(enable_alpha.isSelected());
			
			getData();
			
			list.repaint();
		}
		
		@Override
		public int compareTo(LineEdit o) {
			return time_node.compareTo(o.time_node);
		}
		
		void setData()
		{
			position_v	.setValue(time_node.position * 100);
			size_v		.setValue(time_node.size);
			spin_v		.setValue(Math.toDegrees(time_node.spin));
			alpha_v		.setValue(time_node.alpha);
			
			enable_size.setSelected(time_node.enable_size);
			enable_spin.setSelected(time_node.enable_spin);
			enable_alpha.setSelected(time_node.enable_alpha);
		}
		
		void getData()
		{
			time_node.position	= Parser.castNumber(position_v.getValue(), Float.class) / 100;
			time_node.size		= Parser.castNumber(size_v.getValue(), Float.class);
			time_node.spin		= (float)Math.toRadians((Double)spin_v.getValue());
			time_node.alpha		= Parser.castNumber(alpha_v.getValue(), Float.class);

			time_node.enable_size	= enable_size.isSelected();
			time_node.enable_spin	= enable_spin.isSelected();
			time_node.enable_alpha	= enable_alpha.isSelected();
			
			size_v		.setEnabled(enable_size.isSelected());
			spin_v		.setEnabled(enable_spin.isSelected());
			alpha_v		.setEnabled(enable_alpha.isSelected());
		}
		
		@Override
		public Component getListComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		@Override
		public ImageIcon getListIcon(boolean update) {
			return new ImageIcon(Res.icon_camera);
		}
		@Override
		public String getListName()
		{
			String ret = "[位置=" + (time_node.position * 100) + "%" + "]";
			
			if (time_node.enable_size) {
				ret += "[缩放=" + time_node.size + "]";
			}
			if (time_node.enable_spin) {
				ret += "[旋转=" + Math.toDegrees(time_node.spin) + "]";
			}
			if (time_node.enable_alpha) {
				ret += "[透明度=" + time_node.alpha + "]";
			}

			return ret;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LineEdit) {
				return ((LineEdit) obj).time_node == this.time_node;
			}
			return false;
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	static class AffectEdit extends ObjectPropertyPanel implements G2DListItem
	{
		final ParticleAffect affect;
		
		public AffectEdit(ParticleAffect affect) {
			super(affect);
			this.affect = affect;
		}
		
		
		void setData() {
		}

		void getData() {
		}
		
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		@Override
		public ImageIcon getListIcon(boolean update) {
			return new ImageIcon(Res.icon_affect);
		}
		@Override
		public String getListName() {
			return affect + "";
		}
	}

//	--------------------------------------------------------------------------------------------------------------------
	
	static class AffectType implements G2DListItem
	{
		final Class<?> type;
		
		public AffectType(Class<?> type) {
			this.type = type;
		}
		
		@Override
		public Component getListComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		public ImageIcon getListIcon(boolean update) {
			return new ImageIcon(Res.icon_affect);
		}
		public String getListName() {
			Property property = type.getAnnotation(Property.class);
			if (property!=null) {
				return CUtil.arrayToString(property.value(), "");
			} else {
				return type.getName();
			}
		}
		
		static ArrayList<AffectType> getTypes()
		{
			ArrayList<Class<?>> affect_types = new ArrayList<Class<?>>();
			affect_types.add(Wind.class);
			affect_types.add(Gravity.class);
			affect_types.add(Vortex.class);
			affect_types.add(Wander.class);
			
			// add others
			
			ArrayList<AffectType> ret = new ArrayList<AffectType>(affect_types.size());
			for (Class<?> type : affect_types) {
				ret.add(new AffectType(type));
			}
			return ret;
		}
	}
	
	static class DialogAddAffect extends G2DListSelectDialog<AffectType>
	{
		public DialogAddAffect(Component owner, AffectType dv) {
			super(owner, new G2DList<AffectType>(AffectType.getTypes()), dv);
			super.getList().setLayoutOrientation(G2DList.VERTICAL_WRAP);
		}
	}
}

