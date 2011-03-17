package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.Triggers;
import com.cell.rpg.scene.TriggerGenerator.TriggerInstance;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.editor.property.ComboTextListEdit;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.TextCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;

@SuppressWarnings("serial")
public class TriggerGeneratorPanel extends AbilityPanel
{
	Triggers 					triggers;
	TriggerGenerator 			instances;
	Class<? extends Scriptable>	trigger_object_type;
	
	public TriggerGeneratorPanel(
			TriggerGenerator instances,
			Triggers lib,
			Class<? extends Scriptable> trigger_object_type) 
	{
		super(instances, new TriggerGeneratorAdapter(lib, instances, trigger_object_type));
		this.triggers				= lib;
		this.instances				= instances;
		this.trigger_object_type	= trigger_object_type;
	}

	@Override
	protected String getListAbilityText(AbstractAbility ability) {
		TriggerInstance ins = (TriggerInstance)ability;
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("<p>");
		if (ins.enable) {
			sb.append(ins.toString());
		} else {
			sb.append("<font color=ff0000>"+ins.toString()+"</font>");
		}
		sb.append("</p>");
		sb.append("</body></html>");
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "绑定的触发器";
	}
	
	static class TriggerGeneratorAdapter extends AbilityCellEditAdapter<TriggerInstance>
	{
		Triggers 					triggers;
		TriggerGenerator 			instances;
		Class<? extends Scriptable>	trigger_object_type;
		
		public TriggerGeneratorAdapter(
				Triggers triggers, 
				TriggerGenerator instances,
				Class<? extends Scriptable> trigger_object_type) {
			this.triggers				= triggers;
			this.instances				= instances;
			this.trigger_object_type	= trigger_object_type;
		}

		@Override
		public Class<TriggerInstance> getType() {
			return TriggerInstance.class;
		}

		@Override
		public boolean fieldChanged(
				Object editObject,
				Object fieldValue,
				Field field) {
			if (field.getName().equals("trigger_name")) {
				
			}
			return super.fieldChanged(editObject, fieldValue, field);
		}
		
		@Override
		public String getCellText(Object editObject, Field field,
				Object fieldSrcValue) {
			// TODO Auto-generated method stub
			return super.getCellText(editObject, field, fieldSrcValue);
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyEdit owner,
				Object editObject, 
				Object fieldValue, 
				Field field) 
		{
			if (field.getName().equals("trigger_name")) {
				if (fieldValue != null && triggers.getTrigger(fieldValue.toString())!=null) {
					TextCellEdit edit = new TextCellEdit();
					edit.setText(fieldValue.toString());
					edit.setEnabled(false);
					return edit;
				} else {
					Vector<String> texts = new Vector<String>();
					for (SceneTrigger st : triggers.getTriggers()) {
						if (instances.getTriggerInstance(st.getName()) == null) {
							texts.add(st.getName());
						}
					}
					ComboTextListEdit edit = new ComboTextListEdit(texts);
					return edit;
				}
			}
			return super.getCellEdit(owner, editObject, fieldValue, field);
		}
		
		@Override
		public Component getCellRender(
				ObjectPropertyEdit owner,
				Object editObject, 
				Object fieldValue,
				Field field,
				DefaultTableCellRenderer src) 
		{
			return super.getCellRender(owner, editObject, fieldValue, field, src);
		}
		
	}
	
}
