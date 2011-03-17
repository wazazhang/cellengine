package com.g2d.studio.rpg;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class AbilityTreeView extends JSplitPane
{
	AbilityTrees	tree;
	
	JPanel 			right;
	
	public AbilityTreeView(Abilities abilities, CellEditAdapter<?> ... adapters) 
	{
		super(HORIZONTAL_SPLIT);
		
		this.tree 	= new AbilityTrees(abilities, adapters);
		this.right	= new JPanel();
		
		this.setLeftComponent(this.tree);
		this.setRightComponent(this.right);
		
	}
	
	
	
	
	class AbilityTrees extends G2DTree
	{
		AbilityTrees(Abilities abilities, CellEditAdapter<?> ... adapters) {
			super(new DefaultMutableTreeNode(abilities));
			this.setDragEnabled(false);
			for (abilities.getAbilities()) {
				
			}
			;
			
		}
		
	}
	
	class NodeAbility extends G2DTreeNode<G2DTreeNode<?>>
	{
		public NodeAbility(AbstractAbility ability) {

		}
		
		@Override
		public String getName() {
			return "";
		}
		
		@Override
		protected ImageIcon createIcon() {
			return null;
		}
	}
	
	class NodeAbilities extends G2DTreeNodeGroup<G2DTreeNode<?>>
	{
		public NodeAbilities(Abilities abilities) {
			super(abilities.toString());
		}
		
		@Override
		protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
			return null;
		}
		
		@Override
		protected boolean pathAddLeafNode(String name, int index, int length) {
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			new NodeAbilitiesMenu(this).show(tree, e.getX(), e.getY());
		}
		
		class NodeAbilitiesMenu extends G2DTreeNodeGroup.GroupMenu
		{
			 public NodeAbilitiesMenu(NodeAbilities root) {
				super(root, tree, tree);
			}
		}
		
		
	}
	
	
	
}
