package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.activation.FileDataSource;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.reflect.Parser;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.item.ItemPropertyTypes;
import com.cell.rpg.quest.QuestGenerator.Festival;
import com.cell.rpg.quest.QuestGenerator.Festival.FestivalDate;
import com.cell.rpg.struct.InstanceZoneScriptCode;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression;
import com.g2d.editor.DialogCronExpressionEdit;
import com.g2d.editor.DialogTimeObjectEdit;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.ObjectSelectDialog;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.instancezone.InstanceZoneNode;
import com.g2d.studio.instancezone.InstanceZoneSelectDialog;
import com.g2d.studio.instancezone.InstanceZoneScriptCodeEditor;
import com.g2d.studio.item.property.ItemPropertySavedTypeSelectDialog;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.quest.QuestSelectCellEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.editor.SceneSelectDialog;
import com.g2d.studio.scene.entity.SceneNode;

public class PropertyAdapters
{
	public static class UnitTypeAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,
				Field field) {
			return false;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field) {
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			try{
				if (adapter != null) {
					switch (adapter.value()) {
					case UNIT_ID: {
						ObjectSelectCellEditInteger<XLSUnit> ud = new ObjectSelectCellEditInteger<XLSUnit>(
								owner.getComponent(), XLSUnit.class, fieldValue);
						ud.showDialog();
						return ud;
					}
					case ITEM_ID: {
						ObjectSelectCellEditInteger<XLSItem> id = new ObjectSelectCellEditInteger<XLSItem>(
								owner.getComponent(), XLSItem.class, fieldValue);
						id.showDialog();
						return id;
					}
					case ITEM_LIST_ID:{
						ObjectSelectCellEditInteger<DItemList> ild = new ObjectSelectCellEditInteger<DItemList>(
								owner.getComponent(), DItemList.class, fieldValue);
						ild.showDialog();
						return ild;
					}
					case QUEST_ID:{
						QuestSelectCellEdit dialog = new QuestSelectCellEdit(
								owner.getComponent(), false,
								(Integer)fieldValue);
						dialog.showDialog();
						return dialog;
					}
					case SKILL_ID:{
						ObjectSelectCellEditInteger<XLSSkill> skd = new ObjectSelectCellEditInteger<XLSSkill>(
								owner.getComponent(), XLSSkill.class, fieldValue);
						skd.showDialog();
						return skd;
					}
					case SCENE_ID:{
						SceneSelectDialog dialog4 = new SceneSelectDialog(
								owner.getComponent(), Parser.castNumber(fieldValue, Integer.class));
						dialog4.showDialog();
						return dialog4;
					}
					case AVATAR_ID:{
						ObjectSelectCellEditInteger<DAvatar> ad = new ObjectSelectCellEditInteger<DAvatar>(
								owner.getComponent(), DAvatar.class, fieldValue);
						ad.showDialog();
						return ad;
					}
					case ITEM_PROPERTY_SAVED_TYPE:{
						ItemPropertySavedTypeSelectDialog dialog2 = new ItemPropertySavedTypeSelectDialog(
								owner.getComponent(), 
								(Integer)fieldValue);
						dialog2.showDialog();
						return dialog2;
					}
					case INSTANCE_ZONE_ID:{
						InstanceZoneSelectDialog dialog3 = new InstanceZoneSelectDialog(
								owner.getComponent(), 
								(Integer)fieldValue);
						dialog3.showDialog();
						return dialog3;
					}
					case TIME_OBJECT:{
						DialogTimeObjectEdit tedit = new DialogTimeObjectEdit(
								owner.getComponent(),
								(TimeObject)fieldValue);
						tedit.showDialog();
						return tedit;
					}
					case TIME_TASK: {
						DialogCronExpressionEdit<CronExpression> edit = new DialogCronExpressionEdit<CronExpression>(
								owner.getComponent(),
								(CronExpression)fieldValue, 
								CronExpression.class);
						edit.setVisible(true);
						return edit;
					}
					}
				}
			}catch(Exception err){
				err.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) 
		{
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			try {
				if (adapter != null) {
					switch (adapter.value()) {
					case UNIT_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSUnit.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case ITEM_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSItem.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case SKILL_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSSkill.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case ITEM_LIST_ID: {
						DItemList node = Studio.getInstance().getObjectManager().getObject(
								DItemList.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case QUEST_ID: {
						QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case SCENE_ID: {
						SceneNode node = Studio.getInstance().getSceneManager().getSceneNode((Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case AVATAR_ID: {
						DynamicNode<?> node = Studio.getInstance().getObjectManager().getObject(
								DAvatar.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case ITEM_PROPERTY_SAVED_TYPE: {
						Class<?> type = ItemPropertyTypes.getItemPropertyType((Integer) fieldValue);
						if (type != null) {
							src.setText(AbstractAbility.getEditName(type));
							src.setIcon(null);
						}
						break;
					}
					case INSTANCE_ZONE_ID: {
						InstanceZoneNode node = Studio.getInstance().getInstanceZoneManager().getNode((Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case TIME_OBJECT: {
					
						break;
					}
					case TIME_TASK: {
						
						break;
					}
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}
	
	public static class CronExpressionAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (CronExpression.class.isAssignableFrom(field.getType())) {
				DialogCronExpressionEdit<CronExpression> edit = new DialogCronExpressionEdit<CronExpression>(
						owner.getComponent(),
						(CronExpression)fieldValue, 
						CronExpression.class);
				edit.setVisible(true);
				return edit;
			}
			return null;
		}
		
		
	}
	
	public static class TimeObjectAdapter extends AbilityCellEditAdapter<AbstractAbility>
	{
		@Override
		public Class<AbstractAbility> getType() {
			return AbstractAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
			Object editObject, Object fieldValue, Field field) {
			if (TimeObject.class.isAssignableFrom(field.getType())) {
				DialogTimeObjectEdit edit = new DialogTimeObjectEdit(owner.getComponent(), (TimeObject)fieldValue);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
	}
	
	public static class InstanceZoneScriptCodeAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}
		
		@Override
		public boolean fieldChanged(
				Object editObject,
				Object fieldValue,
				Field field) {
			return false;
		}

		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field) {
			if (InstanceZoneScriptCode.class.isAssignableFrom(field.getType())) {
				InstanceZoneScriptCodeEditor editor = new InstanceZoneScriptCodeEditor(
						owner.getComponent(),
						(InstanceZoneScriptCode)fieldValue);
				editor.showDialog();
				return editor;
			}
			return null;
		}

		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) {
			if (InstanceZoneScriptCode.class.isAssignableFrom(field.getType())) {
				String title = InstanceZoneScriptCodeEditor.getTitle((InstanceZoneScriptCode)fieldValue);
				src.setText(title);
				return src;
			}
			return null;
		}

		@Override
		public String getCellText(Object editObject, Field field,
				Object fieldSrcValue) {
			return null;
		}

		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}

		
	}
}
