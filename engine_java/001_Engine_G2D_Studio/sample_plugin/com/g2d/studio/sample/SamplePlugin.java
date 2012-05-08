package com.g2d.studio.sample;

import java.io.File;
import java.io.IOException;

import com.cell.mysql.MySQLDriver;
import com.cell.rpg.item.ItemPropertyManager;
import com.cell.rpg.scene.SceneAbilityManager;
import com.cell.xstream.XStreamAdapter;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.StudioPlugin;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.io.file.FileIO;

public class SamplePlugin implements StudioPlugin
{

	@Override
	public ItemPropertyManager createItemPropertyManager() {
		return null;
	}

	@Override
	public SceneAbilityManager createSceneAbilityManager() {
		return null;
	}

	@Override
	public Class<?> getPlayerClass() {
		return null;
	}

	@Override
	public Class<?> getPetClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getNpcClass() {
		// TODO Auto-generated method stub
		return null;
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
	public Builder createResourceBuilder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
