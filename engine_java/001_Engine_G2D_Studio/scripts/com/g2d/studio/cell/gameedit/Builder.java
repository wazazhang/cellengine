package com.g2d.studio.cell.gameedit;

import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.File;

public abstract class Builder 
{
	/**
	 * 打开编辑器编辑
	 * @param cpj_file
	 * @return
	 */
	public abstract void openCellGameEdit(File cpj_file);
	
	/**
	 * 导出精灵文件
	 * @param cpj_file_name
	 * @param ignore_on_exist 只有在资源不存在时，才执行。一般用在新加的资源。
	 * @return
	 */
	public abstract void buildSprite(File cpj_file_name, boolean ignore_on_exist);
	
	/**
	 * 导出场景文件
	 * @param cpj_file_name
	 * @param ignore_on_exist 只有在资源不存在时，才执行。一般用在新加的资源。
	 * @return
	 */
	public abstract void buildScene(File cpj_file_name, boolean ignore_on_exist);
	
	/**
	 * 创建资源实体
	 * @param cpj_file
	 * @return
	 */
	public abstract StudioResource createResource(com.g2d.studio.io.File cpj_file);
	
	/**
	 * 检查此目录下是否包含资源文件
	 * @param file
	 * @param res_type
	 * @return
	 */
	public abstract com.g2d.studio.io.File getCPJFile(com.g2d.studio.io.File file, CPJResourceType res_type);
	
	
//	----------------------------------------------------------------------------------------------------------
	
	private static Builder instance;
	
	public static Builder setBuilder(String builder_class_name, String g2d_root) {
		try {
			Class<?> type = Class.forName(builder_class_name);
			instance = (Builder)type.getConstructor(g2d_root.getClass()).newInstance(g2d_root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static Builder setBuilder(Builder builder) {
		instance = builder;
		return instance;
	}
	
	public static Builder getInstance() {
		return instance;
	}

//	----------------------------------------------------------------------------------------------------------
	
	

}
