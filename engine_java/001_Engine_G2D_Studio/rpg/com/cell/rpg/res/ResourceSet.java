package com.cell.rpg.res;

import java.io.Serializable;

import com.cell.gameedit.SetObject;
import com.g2d.cell.CellSetResource;


public abstract class ResourceSet<T extends SetObject> implements Serializable
{
	private static final long serialVersionUID = 1L;

//	--------------------------------------------------------------------------------------------------------------------
	
	final public String		cpj_name;
	final public String		set_name;
	final public String		resource_path; // avatar/item_xxxx/output/xxx.properties
	
	private T				set_object;

//	--------------------------------------------------------------------------------------------------------------------
	
	ResourceSet(
			String cpj_name, 
			String set_name,
			String res_path) throws Exception
	{
		this.cpj_name		= cpj_name;
		this.set_name		= set_name;
		this.resource_path	= res_path;
	}
	
	final public T getSetObject(ResourceManager manager) throws Exception {
		if (set_object == null) {
			set_object = loadSetObject(manager);
		}
		return set_object;
	}
	
	final public CellSetResource getSetResource(ResourceManager manager) throws Exception {
		return manager.getSet(manager.res_root + "/" + resource_path);
	}

	final public String getID() {
		return toID(cpj_name, set_name);
	}
	
	abstract protected T loadSetObject(ResourceManager manager) throws Exception;
	
	
//	--------------------------------------------------------------------------------------------------------------------
	
	static public String toID(String cpj_name, String obj_name) {
		return cpj_name + "/" + obj_name;
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static class ImagesSet extends ResourceSet<com.cell.gameedit.object.ImagesSet>
	{
		private static final long serialVersionUID = 1L;
		
		public ImagesSet(
				String cpj_name, 
				String set_name, 
				String res_path) throws Exception {
			super(cpj_name, set_name, res_path);
		}
		
		@Override
		protected com.cell.gameedit.object.ImagesSet loadSetObject(ResourceManager manager) throws Exception {

			return null;
		}
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	
	/** 将编辑器导出的output文件里的场景信息的描述 */
	public static class SceneSet extends ResourceSet<com.cell.gameedit.object.WorldSet>
	{
		private static final long serialVersionUID = 1L;
		
		SceneSet(
				String cpj_name, 
				String set_name, 
				String res_path) throws Exception {
			super(cpj_name, set_name, res_path);
		}
		
		@Override
		protected com.cell.gameedit.object.WorldSet loadSetObject(ResourceManager manager) throws Exception {
			return getSetResource(manager).WorldTable.get(set_name);
		}
		
	}

	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static class SpriteSet extends ResourceSet<com.cell.gameedit.object.SpriteSet>
	{
		private static final long serialVersionUID = 1L;

		SpriteSet(
				String cpj_name, 
				String set_name, 
				String res_path) throws Exception {
			super(cpj_name, set_name, res_path);
		}
		
		@Override
		protected com.cell.gameedit.object.SpriteSet loadSetObject(ResourceManager manager) throws Exception {
			return getSetResource(manager).SprTable.get(set_name);
		}
		
	}
	
	
}

