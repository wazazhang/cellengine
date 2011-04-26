package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.cell.rpg.template.TAvatar;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class AvatarTreeView extends ObjectTreeViewTemplateDynamic<DAvatar, TAvatar>
{
	private static final long serialVersionUID = 1L;

	public AvatarTreeView(String title, String objects_dir, ProgressForm progress) 
	{
		super(title, DAvatar.class, TAvatar.class, objects_dir, progress);		
	}

	@Override
	protected AvatarGroup createTreeRoot(String title) {
		return new AvatarGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class AvatarGroup extends ObjectGroup<DAvatar, TAvatar>
	{
		private static final long serialVersionUID = 1L;

		public AvatarGroup(String name) {
			super(name, 
					AvatarTreeView.this.list_file,
					AvatarTreeView.this.node_type, 
					AvatarTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
//			System.out.println("create avatar group node : " + name);
			return new AvatarGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, TAvatar data) {
			try {
//				System.out.println("create avatar tree node : " + getName() + " : "+ key);
				addNode(this, new DAvatar(data));
				return true;
			} catch (Exception err) {
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new AvatarRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	


//	-------------------------------------------------------------------------------------------------------------------------------
	class AvatarRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		AvatarGroup root;
		JMenuItem add_avatar = new JMenuItem("添加AVATAR");
		
		public AvatarRootMenu(AvatarGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_avatar.addActionListener(this);
			add(add_avatar);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add_avatar) {
				AvatarAddDialog dialog = new AvatarAddDialog();
				CPJSprite spr = dialog.showDialog();
				if (spr!=null) {
					DAvatar avatar = new DAvatar(
							AvatarTreeView.this, 
							dialog.getAvatarName(),
							Studio.getInstance().getCPJResourceManager().getNodeIndex(spr));
					if (avatar!=null) {
						addNode(root, avatar);
						getTree().reload(root);
					}
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------
	class AvatarAddDialog extends CPJResourceSelectDialog<CPJSprite>
	{
		private static final long serialVersionUID = 1L;
		
		TextField text = new TextField();
		
		public AvatarAddDialog() {
			super(AbstractDialog.getTopWindow(AvatarTreeView.this), CPJResourceType.ACTOR);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(" 输入AVATAR名字 "), BorderLayout.WEST);
			panel.add(text, BorderLayout.CENTER);
			super.add(panel, BorderLayout.NORTH);
		}
		
		public String getAvatarName() {
			return text.getText();
		}
		
		@Override
		protected boolean checkOK() {
			if (text.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "AVATAR名字不能为空！");
				return false;
			}
			if (getList().getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "还未选择AVATAR主角身体！");
				return false;
			}
			return true;
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	
}
