package com.g2d.studio.gameedit;

import com.g2d.studio.gameedit.entity.ObjectNode;


public interface ObjectSelectFilter<T extends ObjectNode<?>>
{
	public boolean accept(T node);
}
