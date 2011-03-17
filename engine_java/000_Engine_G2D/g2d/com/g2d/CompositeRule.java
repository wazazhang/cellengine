package com.g2d;

import java.awt.AlphaComposite;
import java.util.EnumSet;

import com.cell.util.EnumManager.ValueEnum;

public enum CompositeRule implements ValueEnum<Integer>
{
	CLEAR		(AlphaComposite.CLEAR	), 
	SRC			(AlphaComposite.SRC		),
	DST			(AlphaComposite.DST		),
	SRC_OVER	(AlphaComposite.SRC_OVER),
	DST_OVER	(AlphaComposite.DST_OVER),
	SRC_IN		(AlphaComposite.SRC_IN	),
	DST_IN		(AlphaComposite.DST_IN	),
	SRC_OUT		(AlphaComposite.SRC_OUT	),
	DST_OUT		(AlphaComposite.DST_OUT	),
	SRC_ATOP	(AlphaComposite.SRC_ATOP),
	DST_ATOP	(AlphaComposite.DST_ATOP),
	XOR			(AlphaComposite.XOR		),
	;
	
	final public int rule;
	
	private CompositeRule(int rule) {
		this.rule = rule;
	}

	@Override
	public Integer getValue() {
		return rule;
	}
	
	public static String[] getEnumNames() {
		EnumSet<CompositeRule> set = EnumSet.allOf(CompositeRule.class);
		String[] ret = new String[set.size()];
		int i=0;
		for (CompositeRule r : set) {
			ret[i] = r.name();
			i++;
		}
		return ret;
	}
	public static CompositeRule forName(String name) {
		return Enum.valueOf(CompositeRule.class, name);
	}
}