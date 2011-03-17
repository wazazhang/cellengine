package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

/**
 * @author WAZA覆盖地块的区域
 */
@Property("[区域能力] 覆盖地块的区域")
public class RegionMapBlock extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	@Property("此值将覆盖地表的地块信息")
	public int block_value = 0xff0000;

	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + Integer.toString(block_value, 16);
	}
}
