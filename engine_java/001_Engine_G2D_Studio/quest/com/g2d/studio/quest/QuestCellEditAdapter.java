package com.g2d.studio.quest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.reflect.Parser;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.quest.QuestGenerator.Festival;
import com.cell.rpg.quest.QuestGenerator.Festival.FestivalDate;
import com.cell.rpg.quest.QuestItem.AwardBattle;
import com.cell.rpg.quest.QuestItem.AwardItem;
import com.cell.rpg.quest.QuestItem.AwardTeleport;
import com.cell.rpg.quest.QuestItem.DropItem;
import com.cell.rpg.quest.QuestItem.TagItem;
import com.cell.rpg.quest.QuestItem.TagQuest;
import com.cell.rpg.quest.QuestItem.TagQuestItem;
import com.cell.rpg.quest.QuestItem.TagQuestStateKillMonsterComparison;
import com.cell.rpg.quest.ability.QuestTrigger;
import com.cell.rpg.scene.SceneUnit;
import com.cell.util.EnumManager;
import com.cell.util.DateUtil.DayOfWeek;
import com.cell.util.DateUtil.MonthOfYear;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression;
import com.cell.util.task.CronExpression.DateType;
import com.g2d.editor.DialogCronExpressionEdit;
import com.g2d.editor.DialogTimeObjectEdit;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.quest.items.QuestItemNode;
import com.g2d.studio.quest.items.QuestItemSelectCellEdit;
import com.g2d.studio.rpg.FormulaEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.editor.SceneSelectDialogString;
import com.g2d.studio.scene.editor.SceneUnitListCellEdit;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.awt.util.*;

public class QuestCellEditAdapter {

//	-------------------------------------------------------------------------------------------------------------------------
	
	
	
	public static class QuestTriggerAdapter extends AbilityCellEditAdapter<QuestTrigger>
	{
		@Override
		public Class<QuestTrigger> getType() {
			return QuestTrigger.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(
						AbstractDialog.getTopWindow(owner.getComponent()), 
						false,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
	
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id") && fieldValue!=null) {
				Integer quest_id = (Integer)fieldValue;
				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
				if (node != null) {
					src.setText(node.toString());
					src.setIcon(node.getIcon(false));
					return src;
				}
			}
			return null;
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
	
	
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 依赖已完成的任务
	 * @author WAZA
	 *
	 */
	public static class QuestItemTagQuest extends AbilityCellEditAdapter<TagQuest>
	{

		@Override
		public Class<TagQuest> getType() {
			return TagQuest.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(
						owner.getComponent(), 
						true,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field,
			DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id")) {
				try{
					QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}

	/**
	 * 依赖已完成的任务
	 * @author WAZA
	 *
	 */
	public static class QuestItemTagQuestStateKillMonsterComparison extends AbilityCellEditAdapter<TagQuestStateKillMonsterComparison>
	{

		@Override
		public Class<TagQuestStateKillMonsterComparison> getType() {
			return TagQuestStateKillMonsterComparison.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("kill_unit_id")) {
				ObjectSelectCellEditInteger<XLSUnit> edit = new ObjectSelectCellEditInteger<XLSUnit>(
						owner.getComponent(), XLSUnit.class, fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field,
			DefaultTableCellRenderer src) {
			if (field.getName().equals("kill_unit_id")) {
				try{
					XLSUnit unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, (Integer)fieldValue);
					src.setText(unit.getName());
					src.setIcon(unit.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	
	/**
	 * 任务条件，道具
	 * @author WAZA
	 */
	public static class QuestItemTagItem extends AbilityCellEditAdapter<TagItem>
	{
		@Override
		public Class<TagItem> getType() {
			return TagItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(
						owner.getComponent(), XLSItem.class, fieldValue);
				item_edit.showDialog();
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务条件，依赖的任务条件
	 * @author WAZA
	 */
	public static class QuestItemTagQuestItem extends AbilityCellEditAdapter<TagQuestItem>
	{	
		@Override
		public Class<TagQuestItem> getType() {
			return TagQuestItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(owner.getComponent(), true,
						(Integer)fieldValue);
				edit.showDialog();
				return edit;
			}
			else if (field.getName().equals("quest_item_index")) {
				try{
					TagQuestItem tag = (TagQuestItem)editObject;
					QuestItemSelectCellEdit quest_item_edit = new QuestItemSelectCellEdit(
							AbstractDialog.getTopWindow(owner.getComponent()),
							tag.quest_id
							);
					quest_item_edit.showDialog();
					return quest_item_edit;
				}catch(Exception err){}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id")) {
				try{
					QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			else if (field.getName().equals("quest_item_index")) {
				try{
					TagQuestItem 	tag 	= (TagQuestItem)editObject;
					QuestNode 		qnode 	= Studio.getInstance().getQuestManager().getQuest(tag.quest_id);
					QuestItemNode 	node 	= qnode.getQuestItemManager().get((Integer)fieldValue);
					src.setText(node.getName());
					src.setIcon(node.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
//	/**
//	 * 任务条件，依赖的任务条件
//	 * @author WAZA
//	 */
//	public static class QuestItemTagQuestStateComparison extends AbilityCellEditAdapter<TagQuestStateComparison>
//	{	
//		@Override
//		public Class<TagQuestStateComparison> getType() {
//			return TagQuestStateComparison.class;
//		}
//		
//		@Override
//		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
//			Object editObject, Object fieldValue, Field field) {
//			if (field.getName().equals("quest_id")) {
//				QuestSelectCellEdit edit = new QuestSelectCellEdit(AbstractDialog.getTopWindow(owner));
//				edit.showDialog();
//				return edit;
//			}
//			return null;
//		}
//		
//		@Override
//		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
//			Object fieldValue, Field field, DefaultTableCellRenderer src) {
//			if (field.getName().equals("quest_id") && fieldValue!=null) {
//				Integer quest_id = (Integer)fieldValue;
//				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
//				if (node != null) {
//					src.setText(node.toString());
//					src.setIcon(node.getIcon(false));
//					return src;
//				}
//			}
//			return null;
//		}
//	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	

	/**
	 * 任务奖励，道具
	 * @author WAZA
	 */
	public static class QuestItemAwardItem extends AbilityCellEditAdapter<AwardItem>
	{
		@Override
		public Class<AwardItem> getType() {
			return AwardItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(
						owner.getComponent(), XLSItem.class, fieldValue);
				item_edit.showDialog();
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}

	/**
	 * 任务奖励，道具
	 * @author WAZA
	 */
	public static class QuestItemDropItem extends AbilityCellEditAdapter<DropItem>
	{
		@Override
		public Class<DropItem> getType() {
			return DropItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(
						owner.getComponent(), XLSItem.class, fieldValue);
				item_edit.showDialog();
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务奖励，传送
	 * @author WAZA
	 */
	public static class QuestItemAwardTeleport extends AbilityCellEditAdapter<AwardTeleport>
	{
		@Override
		public Class<AwardTeleport> getType() {
			return AwardTeleport.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("scene_id")){
				SceneSelectDialogString dialog = new SceneSelectDialogString(owner.getComponent(), (String)fieldValue);
				dialog.showDialog();
				return dialog;
			}
			else if (field.getName().equals("scene_object_id")){
				AwardTeleport tp = (AwardTeleport)editObject;
				if (tp.scene_id!=null) {
					SceneNode scene = Studio.getInstance().getSceneManager().getSceneNode(tp.scene_id+"");
					return new SceneUnitListCellEdit(scene.getData(), SceneUnit.class);
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("scene_id")){
				try{
					SceneNode node = Studio.getInstance().getSceneManager().getSceneNode(fieldValue + "");
					if (fieldValue != null && node != null) {
						src.setText(node.getName() + "(" + node.getID() + ")");
					} else {
						src.setText("null");
					}
				}catch(Exception err){}
			}
			return null;
		}
		
	}
	
	/**
	 * 任务奖励，传送
	 * @author WAZA
	 */
	public static class QuestItemAwardBattle extends AbilityCellEditAdapter<AwardBattle>
	{
		@Override
		public Class<AwardBattle> getType() {
			return AwardBattle.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("unit_id")) {
				ObjectSelectCellEditInteger<XLSUnit> edit = new ObjectSelectCellEditInteger<XLSUnit>(
						owner.getComponent(), XLSUnit.class, fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("unit_id")) {
				try{
					XLSUnit unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, (Integer)fieldValue);
					src.setText(unit.getName());
					src.setIcon(unit.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	

	

//	-------------------------------------------------------------------------------------------------------------------------
	
	public static class AbstractValueAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (AbstractValue.class.equals(field.getType())) {
				FormulaEdit edit = new FormulaEdit(
						owner.getComponent(), 
						(AbstractValue)fieldValue);
				edit.showDialog();
				return edit;
			}
			if (AbstractValue.class.isAssignableFrom(field.getType())) {
				FormulaEdit edit = new FormulaEdit(
						owner.getComponent(), 
						new Class<?>[]{field.getType()}, 
						(AbstractValue)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
	
	public static class QuestFestivalAdapter extends AbilityCellEditAdapter<Festival>
	{
		@Override
		public Class<Festival> getType() {
			return Festival.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("date_time")) {
				DialogCronExpressionEdit<FestivalDate> edit = new DialogCronExpressionEdit<FestivalDate>(
						owner.getComponent(),
						(FestivalDate)fieldValue, 
						FestivalDate.class);
				edit.setVisible(true);
				return edit;
			}
			return super.getCellEdit(owner, editObject, fieldValue, field);
		}
		
		
	}
//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
}
