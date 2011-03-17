package com.g2d.studio.gameedit;

import javax.swing.JScrollPane;

import com.g2d.studio.gameedit.template.XLSSkill;

public class SkillViewer extends XLSObjectViewer<XLSSkill>
{
	private static final long serialVersionUID = 1L;

	public SkillViewer(XLSSkill skill) {
		super(skill);
		table.insertTab("每等级属性", null, new JScrollPane(new SkillPropertiesEditor(skill)), "", 1);
	}
	
}
