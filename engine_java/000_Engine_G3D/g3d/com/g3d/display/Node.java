package com.g3d.display;

import java.util.Collection;

import com.g3d.G3DGraphics;

public abstract class Node {

	
	abstract public Collection<Node>	getChilds();
	
	abstract public int					getChildCount();

	abstract public void				update(G3DGraphics g);

}
