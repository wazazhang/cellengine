package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

/**
 * 提供单位场景传送点的能力
 * @author WAZA
 *
 */
@Property("[不可破坏能力] 场景传送点")
public class ActorTransport extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
	
	@Property("传送到目标场景名字")
	public String next_scene_id;
	
	@Property("传送到目标场景的特定单位名字")
	public String next_scene_object_id;
	
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : scene=" + next_scene_id + " : unit=" + next_scene_object_id;
	}
}
