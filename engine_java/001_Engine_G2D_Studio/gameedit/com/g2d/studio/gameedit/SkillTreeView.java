package com.g2d.studio.gameedit;


import com.cell.rpg.template.TSkill;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DTree;

@SuppressWarnings("serial")
public class SkillTreeView extends ObjectTreeViewTemplateXLS<XLSSkill, TSkill>
{
	
	public SkillTreeView(
			String		title, 
			com.g2d.studio.io.File		objects_dir,
			File		xls_file,
			ProgressForm progress) {
		super(title, XLSSkill.class, TSkill.class, objects_dir, xls_file, progress);
	}

	@Override
	public G2DTree createTree(ObjectGroup<XLSSkill, TSkill> treeRoot) {
		return new G2DTree(treeRoot){
			@Override
			public String convertValueToText(Object value, boolean selected,
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
				if (value instanceof XLSSkill) {
					XLSSkill node = (XLSSkill)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(node.getName());
					sb.append("<font color=0000ff> - Level:" + node.getData().getMaxLevel() + "</font>");
					sb.append("</p>");
					sb.append("</body></html>");
					return sb.toString();
				}
				return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
			}
		};
	}
}
