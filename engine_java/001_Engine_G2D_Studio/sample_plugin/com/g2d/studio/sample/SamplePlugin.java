package com.g2d.studio.sample;


import java.io.IOException;

import com.cell.mysql.MySQLDriver;
import com.cell.rpg.item.ItemPropertyManager;
import com.cell.rpg.scene.SceneAbilityManager;
import com.cell.xstream.XStreamAdapter;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.StudioPlugin;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.io.File;
import com.g2d.studio.io.file.FileIO;
import com.g2d.studio.sample.builder.SampleBuilder;
import com.g2d.studio.sample.entity.TPlayer;
import com.g2d.studio.sample.item.EatItemPropertyManager;
import com.g2d.studio.sample.scene.EatSceneAbilityManager;

public class SamplePlugin implements StudioPlugin
{

	@Override
	public ItemPropertyManager createItemPropertyManager() {
		return new EatItemPropertyManager();
	}

	@Override
	public SceneAbilityManager createSceneAbilityManager() {
		return new EatSceneAbilityManager();
	}

	@Override
	public Class<?> getPlayerClass() {
		return TPlayer.class;
	}

	@Override
	public Class<?> getPetClass() {
		return TPlayer.class;
	}

	@Override
	public Class<?> getNpcClass() {
		return TPlayer.class;
	}

	@Override
	public String getPersistanceManager() {
		return XStreamAdapter.class.getCanonicalName();
	}

	@Override
	public String getSQLDriver() {
		return MySQLDriver.class.getCanonicalName();
	}

	@Override
	public Builder createResourceBuilder(File g2d_root) throws IOException {
		return new SampleBuilder(g2d_root.getPath());
	}
	
	
}
