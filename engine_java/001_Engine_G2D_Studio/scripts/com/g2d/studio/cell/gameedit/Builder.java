package com.g2d.studio.cell.gameedit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.script.js.JSManager;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;

public abstract class Builder 
{
//	----------------------------------------------------------------------------------------------------------
	
	private static Builder instance;
	
	public static Builder setBuilder(Builder builder) {
		instance = builder;
		return instance;
	}
	
	public static Builder getInstance() {
		return instance;
	}

//	----------------------------------------------------------------------------------------------------------
	final protected java.io.File g2d_project_root;

	protected JSManager external_script_manager = new JSManager();
	
	public Builder(String g2d_project_root) throws IOException
	{
		this.g2d_project_root = new java.io.File(g2d_project_root).getCanonicalFile();
	}

	protected java.io.File getLocalFile(String cpj_file) {
		try {
			return new java.io.File(cpj_file).getCanonicalFile();
		} catch (Exception err) {
			err.printStackTrace();
			return new java.io.File(cpj_file);
		}
	}

	/**
	 * 打开编辑器编辑
	 * @param cpj_file
	 * @return
	 */
	public Process openCellGameEdit(String cpj_file) 
	{
		return CellGameEditWrap.openCellGameEdit(
				"CellGameEdit.exe", 
				getLocalFile(cpj_file)
				);
	}
	
	/**
	 * 执行导出脚本
	 * @param cpj_file
	 * @return
	 */
	public Process execCellGameOutput(String cpj_file, String[] args)
	{
		return CellGameEditWrap.openCellGameEdit(
				"CellGameOutput.exe", 
				getLocalFile(cpj_file),
				args
				);
	}
	
	public JSBuildOutputScript createOutputScript(File scfile)
	{
		try {
			if (scfile.exists()) {
				JSBuildOutputScript script = external_script_manager.getInterface(
						scfile.getCanonicalPath(), 
						JSBuildOutputScript.class);
				return script;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSBuildCustomScript createCustomScript(File scfile)
	{
		try {
			if (scfile.exists()) {
				JSBuildCustomScript script = external_script_manager.getInterface(
						scfile.getCanonicalPath(), 
						JSBuildCustomScript.class);
				return script;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出精灵文件
	 * @param cpj_file_name
	 * @param ignore_on_exist 只有在资源不存在时，才执行。一般用在新加的资源。
	 * @return
	 */
	public void buildSprite(String cpj_file, boolean ignore_on_exist)
	{
		try {
			File cpj_file_name = getLocalFile(cpj_file);
			BuilderTask pb = preBuild(cpj_file_name);
			if (ignore_on_exist && pb.checkOutputExists()) {
				System.out.println("ignore : " + cpj_file);
				return;
			}
			pb.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出场景文件
	 * @param cpj_file_name
	 * @param ignore_on_exist 只有在资源不存在时，才执行。一般用在新加的资源。
	 * @return
	 */
	public void buildScene(String cpj_file, boolean ignore_on_exist)
	{
		try {
			File cpj_file_name = getLocalFile(cpj_file);
			BuilderTask pb = preBuild(cpj_file_name);
			if (ignore_on_exist && pb.checkOutputExists()) {
				System.out.println("ignore : " + cpj_file);
				return;
			}
			pb.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 创建编译脚本
	 * @param cpj_file_name
	 * @return
	 */
	abstract public BuilderTask preBuild(File cpj_file_name) throws Exception;
	
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
	public abstract com.g2d.studio.io.File findCPJFile(
			com.g2d.studio.io.File file, 
			CPJResourceType res_type);
	
	/**
	 * 执行自定义操作指令
	 * @param sc_file_path 需要执行的js脚本
	 * @param workDir 工作目录
	 * @param g2dRoot g2d工程目录
	 */
	public void runScript(
			String sc_file_path,
			java.io.File workDir,
			java.io.File g2dRoot)
	{
		try {
			java.io.File sc_file = new java.io.File(sc_file_path).getCanonicalFile();
			if (sc_file.exists()) {
				JSBuildCustomScript script = external_script_manager.getInterface(
						sc_file.getCanonicalPath(), 
						JSBuildCustomScript.class);
				if (script != null) {
					java.io.File dir = new java.io.File(
							System.getProperty("user.dir")).getCanonicalFile();
					BuildProcess bp = new BuildProcess(dir, g2dRoot);
					script.build(bp, dir);
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}


	public class BuilderTask 
	{
		JSBuildOutputScript script;
		BuildProcess bp;
		final java.io.File dir;
		final java.io.File cpjFile;
		final java.io.File g2dRoot;
		int timeoutMS = 60000;
		
		public BuilderTask(
				JSBuildOutputScript script, 
				java.io.File cpjFile, 
				int timeoutMS) throws Exception
		{
			this.script = script;
			this.cpjFile = cpjFile;
			this.g2dRoot = g2d_project_root;
			this.timeoutMS = timeoutMS;
			this.dir = cpjFile.getParentFile().getCanonicalFile();
			this.bp = new BuildProcess(dir, g2dRoot);
		}
			
		public boolean checkOutputExists() {
			if (bp != null) {
				if (script != null) {
					try {
						return script.checkOutputExists(bp, dir, cpjFile);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				} else {
					return new File(cpjFile.getParentFile(), "output").exists();
				}
			}
			return false;
		}

		public void build()
		{
			try {
				System.out.println("build " + cpjFile.getName() + " : " + cpjFile.getPath());
				String[] output_sc = this.selectOuputScript();
				Process process = execCellGameOutput(
						cpjFile.getCanonicalPath(), 
						output_sc
						);
				WaitProcessTask task = new WaitProcessTask(process, 
						timeoutMS);
				task.start();
				try {
					process.waitFor();
					Thread.yield();
				} finally {
					synchronized (task) {
						task.notifyAll();
					}
				}
				this.runCustomOutput();
				System.out.println("done !");
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
//				this.saveBuildBat();
			}
		}
			
		private String[] selectOuputScript() throws IOException
		{
			if (bp != null) {
				try {
					if (script != null) {
						ArrayList<String> output_files = new ArrayList<String>();
						script.selectOuputScript(bp, dir, cpjFile, output_files);
						if (!output_files.isEmpty()) {
							String[] ret = new String[output_files.size()];
							int i = 0;
							for (String file : output_files) {
								java.io.File sc_file = new java.io.File(g2dRoot, file);
								ret[i] = sc_file.getCanonicalPath();
								i++;
							}
							return ret;
						}
					} 
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
			return new String[]{"output.xml"};
		}


		private void runCustomOutput() 
		{
			if (bp != null) {
				if (script != null) {
					try {
						script.output(bp, dir, cpjFile);
					} catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		
//		private void saveBuildBat() {
//			if (bp != null) {
//				if (script != null) {
//					try {
//						script.saveBuildBat(bp, dir, cpjFile);
//					} catch (Throwable ex) {
//						ex.printStackTrace();
//					}
//				}
//			}
//		}
			
			
		
		
		
	}
	
}
