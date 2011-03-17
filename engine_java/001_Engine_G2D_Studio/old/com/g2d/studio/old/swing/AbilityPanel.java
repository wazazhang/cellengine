package com.g2d.studio.old.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbilitySceneNPC;
import com.cell.rpg.ability.AbilitySceneNPCPathPoint;
import com.cell.rpg.ability.AbilitySceneNPCSpawn;
import com.cell.rpg.ability.AbilitySceneNPCTeamNode;
import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.ability.AbilityXLS;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Config;
import com.g2d.studio.old.Studio;
import com.g2d.studio.old.scene.FormSceneViewer;
import com.g2d.studio.old.scene.SceneActor;
import com.g2d.studio.old.scene.ScenePoint;
import com.g2d.studio.old.scene.SceneUnitTag;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的面板
 */
public class AbilityPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final Abilities			abilities;
	
	final SceneUnitTag<?>	scene_unit;
	
	JList list_cur_ability = new JList();
	
	JButton btn_add_ability = new JButton("添加能力");
	JButton btn_del_ability = new JButton("删除能力");
	
	public AbilityPanel(SceneUnitTag<?> scene_unit, Abilities abilities)
	{
		this.scene_unit = scene_unit;
		this.abilities = abilities;
		
		this.setLayout(new BorderLayout());
		
		{
			this.list_cur_ability.setListData(abilities.getAbilities());
			this.list_cur_ability.addMouseListener(new MouseAdapter(){
				 public void mouseClicked(MouseEvent e) {
			         if (e.getClickCount() == 2) {
			             int index = list_cur_ability.locationToIndex(e.getPoint());
			             AbstractAbility data = (AbstractAbility)list_cur_ability.getSelectedValue();
			             new AbilityPropertyForm(data).setVisible(true);
	//		             System.out.println("Double clicked on Item " + index + " " + data);
			          }
			     }
			});
			this.list_cur_ability.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						deleteSeletedAbility();
					}
				}
			});
			this.add(new JScrollPane(list_cur_ability), BorderLayout.CENTER);
		}
		{
			JPanel bpan = new JPanel();
			bpan.setLayout(new FlowLayout());
			
			this.btn_add_ability.setActionCommand("btn_add_ability");
			this.btn_add_ability.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddAbilityForm().setVisible(true);
				}
			});
			bpan.add(btn_add_ability);
			
			
			this.btn_del_ability.setActionCommand("btn_del_ability");
			this.btn_del_ability.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteSeletedAbility();
				}
			});
			bpan.add(btn_del_ability);
			
			this.add(bpan, BorderLayout.SOUTH);
		}
	}
	
	public void deleteSeletedAbility() {
		try{
			AbstractAbility data = (AbstractAbility)list_cur_ability.getSelectedValue();
			this.abilities.removeAbility(data);
			this.list_cur_ability.setListData(abilities.getAbilities());
		}catch (Exception err) {}
	}
	
	public void resetAbility() {
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	public void addAbility(AbstractAbility ability) {
		this.abilities.addAbility(ability);
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	@Override
	public String toString(){
		return "Ability";
	}
	
	
	
	/**
	 * 添加能力时弹出的框
	 * @author WAZA
	 */
	final class AddAbilityForm extends AbstractDialog
	{
		private static final long serialVersionUID = 1L;
		
		JComboBox combo_abilities;
		
		JButton btn_add = new JButton("添加");
		
		JPanel	pan_property = new JPanel();
		
		AbstractAbility current_ability;
		
		public AddAbilityForm()
		{
			super.setLayout(new BorderLayout());
			
			{
				combo_abilities = new JComboBox(abilities.getSubAbilityTypes());
				combo_abilities.setRenderer(new ListCellRenderer(){
					@SuppressWarnings("unchecked")
					public Component getListCellRendererComponent(JList list,
							Object value, int index, boolean isSelected,
							boolean cellHasFocus) {
						Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)value;
						return new JLabel(AbstractAbility.getName(ability_cls));
					}
				});
				combo_abilities.addActionListener(new ActionListener() {
					@SuppressWarnings("unchecked")
					public void actionPerformed(ActionEvent e) {
						Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)combo_abilities.getSelectedItem();
						setAbilityClass(ability_cls);
					}
				});
				this.add(combo_abilities, BorderLayout.NORTH);
			}
			{
				pan_property.setLayout(new BorderLayout());
				this.add(pan_property, BorderLayout.CENTER);
			}
			{
				btn_add.setActionCommand("btn_add");
				btn_add.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try{
							AbilityPanel.this.addAbility(current_ability);
							AddAbilityForm.this.setVisible(false);
							AddAbilityForm.this.dispose();
						}catch (Exception err) {
							err.printStackTrace();
						}
					}
				});
				this.add(btn_add, BorderLayout.SOUTH);
			}
			

			combo_abilities.setSelectedIndex(0);
		}
		
		public void setAbilityClass(Class<? extends AbstractAbility> cls) 
		{
			current_ability = AbstractAbility.createAbility(cls);
			AbilityPropertyPanel obj_pan = new AbilityPropertyPanel(current_ability);
			pan_property.removeAll();
			pan_property.add(obj_pan, BorderLayout.CENTER);
			pan_property.updateUI();
			
			// 如果该 ability 不允许多个实例
			if (!current_ability.isMultiField() && abilities.getAbility(current_ability.getClass())!=null) 
			{
				btn_add.setForeground(Color.RED);
				btn_add.setEnabled(false);
				btn_add.setText(AbstractAbility.getName(current_ability.getClass())+" (已存在)");
			}
			else 
			{
				btn_add.setForeground(Color.BLACK);
				btn_add.setEnabled(true);
				btn_add.setText("添加");
			}
		}
		
	}
	
	

	/**
	 * @author WAZA
	 * 编辑能力的窗口
	 */
	final class AbilityPropertyForm extends AbstractDialog implements WindowListener
	{
		private static final long serialVersionUID = 1L;
		
		final AbstractAbility ability;
		
		AbilityPropertyPanel panel_ability;
		
		JButton btn_add = new JButton("确定");
		
		public AbilityPropertyForm(AbstractAbility src) 
		{
			super.setTitle(src.toString());
			super.setLayout(new BorderLayout());
			
			this.ability = src;
			{
				this.panel_ability = new AbilityPropertyPanel(ability);
				this.add(panel_ability, BorderLayout.CENTER);
			}
			{
				btn_add.setActionCommand("btn_add");
				btn_add.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try{
							resetAbility();
							AbilityPropertyForm.this.setVisible(false);
							AbilityPropertyForm.this.dispose();
						}catch (Exception err) {
							err.printStackTrace();
						}
					}
				});
				this.add(btn_add, BorderLayout.SOUTH);
			}
			this.addWindowListener(this);
		}
		
		public void windowClosing(WindowEvent e) {
			super.setTitle(ability.toString());
			resetAbility();
		}
		public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
		
	}

	/**
	 * @author WAZA
	 * 能力属性编辑器
	 */
	public class AbilityPropertyPanel extends ObjectPropertyPanel
	{
		private static final long serialVersionUID = 1L;
		
		final public AbstractAbility ability;
		
		public AbilityPropertyPanel(AbstractAbility ability)
		{
			super(ability);
			this.ability = ability;
		}

		@Override
		protected PropertyCellEdit<?> getPropertyCellEdit(Object object, Field field, Object value) {
			PropertyCellEdit<?> ret = getAbilityCellEdit(object, field, value);
			if (ret != null) {
				return ret;
			}
			return super.getPropertyCellEdit(object, field, value);
		}
		
		@Override
		protected void onFieldChanged(Object object, Field field) {
			fieldChanged(object, field);
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		
		void fieldChanged(Object object, Field field)
		{
			if (object instanceof AbilitySceneTransport)
			{
				// 场景改变了，清除场景单位的值
				AbilitySceneTransport tp = (AbilitySceneTransport)object;
				if (field.getName().equals("destination_scene_name")){
					tp.destination_scene_object_name = null;
				}
			}
			if (object instanceof AbilityXLS)
			{
				// xls配置改变了，清除xls行信息
				AbilityXLS xls = (AbilityXLS)object;
				if (field.getName().equals("xls_file_name")){
					xls.xls_primary_key = null;
				}
			}
			
			if (object instanceof AbilitySceneNPCSpawn) 
			{
				// 设置默认的team给Spawn
				AbilitySceneNPCSpawn spawn = (AbilitySceneNPCSpawn)object;
				if (field.getName().equals("cpj_actor_name")){
					System.out.println("set default team from actor !");
					try{
						AbilitySceneNPC template_npc = Studio.getInstance().getActor(
								spawn.getActorCPJName(), 
								spawn.getActorCPJSpriteName()).
								createRPGActor().
								getAbility(AbilitySceneNPC.class);
						spawn.team 				= template_npc.team;
						spawn.xls_file_name		= template_npc.xls_file_name;
						spawn.xls_primary_key	= template_npc.xls_primary_key;
					}catch (Exception e) {}
				}
			}
			
			if (object instanceof AbilitySceneNPCTeamNode) 
			{
				// 设置默认的team给node
				AbilitySceneNPCTeamNode node = (AbilitySceneNPCTeamNode)object;
				if (field.getName().equals("cpj_actor_name")){
					System.out.println("set default team from actor !");
					try{
						AbilitySceneNPC template_npc = Studio.getInstance().getActor(
								node.getActorCPJName(), 
								node.getActorCPJSpriteName()).
								createRPGActor().
								getAbility(AbilitySceneNPC.class);
						node.xls_file_name		= template_npc.xls_file_name;
						node.xls_primary_key	= template_npc.xls_primary_key;
					}catch (Exception e) {}
				}
			}
				
		}
		
		/**
		 * 得到Ability相应的编辑器
		 * @param object
		 * @param field
		 * @param value
		 * @return
		 */
		PropertyCellEdit<?> getAbilityCellEdit(Object object, Field field, Object value) 
		{
			// 测试是否是集合
			try
			{
				field.getType().asSubclass(Abilities.class);
				System.out.println("field is Abilities");
				if (value == null) {
					value = field.getType().newInstance();
				}
				return new AbilityForm(scene_unit, (Abilities) value);
			}catch (Exception e) {}
			
			// 如果是传送点，则遍历Scene或者SceneUnit
			if (object instanceof AbilitySceneTransport)
			{
				AbilitySceneTransport tp = (AbilitySceneTransport)object;
				
				if (field.getName().equals("destination_scene_name")){
					return new SceneListComboBox();
				}
				
				if (field.getName().equals("destination_scene_object_name")){
					if (tp.destination_scene_name!=null) {
						try{
							FormSceneViewer sv = Studio.getInstance().getScene(tp.destination_scene_name);
							return new SceneUnitListComboBox(sv, SceneActor.class);
						}catch (Exception e) {}
					}
				}
			}
			
			// NPC行动开始点
			if (object instanceof AbilitySceneNPCPathPoint) 
			{
				AbilitySceneNPCPathPoint path = (AbilitySceneNPCPathPoint)object;
				if (field.getName().equals("point_name") && scene_unit!=null){
					try{
//						System.out.println(object.getClass().getName());
						return new SceneUnitListComboBox(scene_unit.getViewer(), ScenePoint.class);
					}catch (Exception e) {}
				}
			}
			
			// 如果是XLS数据绑定
			if (object instanceof AbilityXLS)
			{
				AbilityXLS xls = (AbilityXLS)object;
				
				if (field.getName().equals("xls_file_name")){
					return new XLSFileListComboBox();
				}
				
				if (field.getName().equals("xls_primary_key")){
					try{
						return new XLSRowListComboBox(new XLSFile(Studio.getInstance().getFile(
								Config.XLS_ROOT + "/" + xls.xls_file_name)));
					}catch (Exception e) {}
				}
				
				if (object instanceof AbilitySceneNPCSpawn)
				{
					if (field.getName().equals("cpj_actor_name")){
						return new ActorListComboBox();
					}
				}
				
				if (object instanceof AbilitySceneNPCTeamNode) {
					if (field.getName().equals("cpj_actor_name")){
						return new ActorListComboBox();
					}
				}
			}
			
			// 如果是其他能力
			
			return null;
		}
	}
	
	
//	---------------------------------------------------------------------------------------------------------
	
	
	
}
