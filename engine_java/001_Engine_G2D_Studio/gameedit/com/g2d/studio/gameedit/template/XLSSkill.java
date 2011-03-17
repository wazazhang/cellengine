package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.awt.util.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.SkillViewer;
import com.g2d.studio.res.Res;

final public class XLSSkill extends XLSTemplateNode<TSkill>
{
	public XLSSkill(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) {
		super(xls_file, xls_row, data);		
		if (template_data.icon_index!=null) {
			icon_file = Studio.getInstance().getIconManager().getIcon(template_data.icon_index);
		}
	}
	
	@Override
	protected TSkill newData(XLSFile xlsFile, XLSFullRow xlsRow) {
		return new TSkill(getIntID(), xlsRow.desc);
	}

	public ObjectViewer<?> getEditComponent(){
		onOpenEdit();
		if (edit_component==null) {
//			edit_component = new SkillObjectViewer();
			edit_component = new SkillViewer(this);
		}
		return edit_component;
	}
	

	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_file==null || !icon_file.icon_file_name.equals(getData().icon_index)) {
			resetIcon();
		} 
		return super.getIcon(update);
	}
	
	@Override
	protected ImageIcon createIcon() {
		icon_file = Studio.getInstance().getIconManager().getIcon(getData().icon_index);
		if (icon_file!=null) {
			return new ImageIcon(Tools.combianImage(20, 20, icon_file.getImage()));
		}
		return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------
	
//	class SkillObjectViewer extends XLSObjectViewer<XLSSkill>
//	{
//		private static final long serialVersionUID = 1L;
//		
//		JButton set_binding = new JButton("设置图标");
//		
//		public SkillObjectViewer() 
//		{
//			super(XLSSkill.this);
//
//			if (getIconFile()!=null) {
//				set_binding.setIcon(getIconFile().getListIcon(false));
//			}
//			set_binding.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					IconFile icon = new IconSelectDialog().showDialog();
//					if (icon != null) {
//						XLSSkill.this.setIcon(icon);
//						XLSSkill.this.getIcon(true);
//						set_binding.setIcon(getIconFile().getListIcon(false));
//						Studio.getInstance().getObjectManager().repaint();
//					}
//				}
//			});
//			page_properties.add(set_binding, BorderLayout.SOUTH);
//		}
//		
//		
//		
//	}
	
	
	
}
