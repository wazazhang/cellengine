package com.g2d.studio.cpj;

import java.awt.Component;
import com.g2d.awt.util.*;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.swing.G2DListSelectDialog;

public class CPJResourceSelectDialog<T extends CPJObject<?>> extends G2DListSelectDialog<T>
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	public CPJResourceSelectDialog(Component window, CPJResourceType type) {
		super(window, 
				(CPJResourceList<T>)Studio.getInstance().getCPJResourceManager().createObjectsPanel(type),
				null
		);
		super.setTitle("选择一个资源");
	}
}
