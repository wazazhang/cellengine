package com.g2d.studio.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

@SuppressWarnings("serial")
public abstract class G2DTreeNode<C extends G2DTreeNode<?>> extends DefaultMutableTreeNode implements Transferable
{
	private ImageIcon 		icon_snapshot;
	
	public G2DTreeNode() {
		setAllowsChildren(false);
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	
	public void onDoubleClicked(JTree tree, MouseEvent e){
//		System.out.println("onDoubleClicked : " + getName());
	}
	
	public void onRightClicked(JTree tree, MouseEvent e){
//		System.out.println("onRightClicked : " + getName());
	}

	public void onClicked(JTree tree, MouseEvent e){
//		System.out.println("onClicked : " + getName());
	}
	
	public void onSelected(JTree tree){
//		System.out.println("onSelected : " + getName());
	}
	
//	
//	----------------------------------------------------------------------------------------------------------------------------------

	abstract public		String		getName();

	abstract protected	ImageIcon	createIcon();
	
	public ImageIcon getIcon(boolean update){
		if (icon_snapshot==null || update) {
			icon_snapshot = createIcon();
		}
		return icon_snapshot;
	}

	public void resetIcon() {
		icon_snapshot = null;
	}

//	----------------------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return getName();
	}

//	----------------------------------------------------------------------------------------------------------------------------------

//	----------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void add(MutableTreeNode newChild) {
		if (checkAddChild(newChild)) {
			super.add(newChild);
		}
	}
	
	public void insert(MutableTreeNode child, int index) {
		if (checkAddChild(child)) {
			super.insert(child, index);
		}
	}
	
	protected boolean checkAddChild(MutableTreeNode child) {
//		C pre = getChild(child.getName());
//		if (pre!=null) {
//			throw new IllegalStateException("duplicate element \"" + child.getName() + "\" !");
//		}
		return true;
	}

//	----------------------------------------------------------------------
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		try {
			return new DataFlavor[]{new DataFlavor(getClass().getCanonicalName())};
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return this;
	}
	

}
