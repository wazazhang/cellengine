package com.g2d.studio.old.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.cell.rpg.ability.Abilities;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.old.scene.SceneUnitTag;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的窗口
 */
public class AbilityForm extends AbstractDialog implements PropertyCellEdit<Abilities>
{
	private static final long serialVersionUID = 1L;
	
	ObjectPropertyPanel property_panel;
	JButton button;
	
	final AbilityPanel ability_panel;
	final Abilities abilities;
	
	public AbilityForm(SceneUnitTag<?> scene_unit, Abilities abilities) 
	{
		this.abilities 		= abilities;
		this.ability_panel 	= new AbilityPanel(scene_unit, abilities);
		this.add(ability_panel);
		this.setTitle(abilities.toString());
		
		this.setSize(500, 400);
		this.setCenter();
		
		button = new JButton(abilities.toString());
		button.setActionCommand("ok");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityForm.this.setVisible(true);
			}
		});
	}
	
	public void beginEdit(ObjectPropertyPanel panel) {
		property_panel = panel;
	}
	
	public Component getComponent() {	
		button.setText(abilities.toString());
		return button;
	}
	
	public Abilities getValue() {
		return ability_panel.abilities;
	}
	
	
	
	
}
