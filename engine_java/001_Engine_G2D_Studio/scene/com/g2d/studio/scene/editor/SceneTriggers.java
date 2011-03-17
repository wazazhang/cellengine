package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;

import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.script.TriggersEditor;

@SuppressWarnings("serial")
public class SceneTriggers extends AbstractDialog
{
	TriggersEditor triggers;
	
	public SceneTriggers(SceneEditor root) {
		super(root);
		super.setSize(800, 600);
		super.setIconImage(Res.icon_action);
		super.setCenter();
		super.setLayout(new BorderLayout());
		
		this.triggers = new TriggersEditor(
				this,
				root.getSceneNode().getData());
		this.add(triggers, BorderLayout.CENTER);
	}
}
