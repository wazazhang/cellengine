/*********************************************************************************
 * BuildProcess
 * 类所用可用的方法
 *********************************************************************************
 *
 * 得到当前工作目录
 * public File getDir()
 * 
 * 执行操作系统指令
 * public Object exec(String cmd) 
 * 
 * 执行操作系统指令，并等待超时
 * public Object exec(String cmd, int timeout)
 * 
 * 删除文件或整个目录
 * public BuildProcess deleteIfExists(String child_file) 
 * 
 * 工作目录中。获得该目录下所有子文件信息
 * public LinkedHashMap<String, byte[]> getEntrys(String root, String regex, String entry_prefix)
 * 
 * 工作目录中。获得该目录下所有子文件信息
 * public BuildProcess getEntrys(String root, String regex, String entry_prefix, Map<String, byte[]> packs)
 * 
 * 将所有entry打包成zip格式
 * public File pakEntrys(Map<String, byte[]> entrys, String out) 
 * 
 * 工作目录中。将该目录下符合的文件都打包，自定义子文件的前缀
 * public File pakFiles(String root, String regex, String out, String prefix)
 * 
 * 工作目录中。导出一份缩略图
 * public BuildProcess saveImageThumb(String src_file, String dst_file, float scale)
 * 
 * 转换图片成MASK信息
 * public BuildProcess convertImageMaskEntrys(Map<String, byte[]> packs)
 * 
 * 工作目录中。获得该目录下所有子文件信息，并转换成ImageMask
 * public BuildProcess getImageMaskEntrys(String root, String regex, String entry_prefix, Map<String, byte[]> packs)
 * 
 * 工作目录中。将该目录下符合的文件都打包，自定义子文件的前缀
 * public File pakImageMaskFiles(String root, String regex, String out, String prefix)
 * 
 */

/*********************************************************************************
 * interface implements
 * public boolean checkOutputExists(BuildProcess p, File dir, File cpj_file_name)
 * 检查以前是否已经存在导出资源
 *********************************************************************************/
function checkOutputExists(p, dir, cpj_file_name)
{
//	var output_properties = java.io.File(dir, "output\\" + cpj_file_name.getName().replace(".cpj", ".properties"));
//	if (!output_properties.exists()) {
//		return false;
//	}
	var output_pak = java.io.File(dir, "..\\" + dir.getName()+".pak");
	if (!output_pak.exists()) {
		return false;
	}
	return true;
}

/*********************************************************************************
 * interface implements
 * public void 	output(BuildProcess p, File dir, File cpj_file_name)
 * 导出资源
 *********************************************************************************/
function output(p, dir, cpj_file_name)
{

	convertImage	(p, dir, cpj_file_name);
	
//	createDirOutput	(p, dir, cpj_file_name);
	
	createPakOutput	(p, dir, cpj_file_name);
	
	clean(p);
}

//-----------------------------------------------------------------------------------------
/**
 * 压缩图片
 */
function convertImage(p, dir, cpj_file_name)
{
	convert = java.io.File(
			dir.getParentFile().getParentFile().getParentFile(),
			"\\sign\\ImageMagick\\convertimg.exe").getCanonicalPath();
	
//	println(convert);
	
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.saveImageThumb("jpg.jpg", "thumb.jpg", 0.1);
		
		p.exec(convert + " thumb.jpg -strip -quality 50 thumb.jpg", 60000);
		
		// 场景只需要压缩JPG地砖
		var jpgs = java.io.File(dir, "output\\set\\jpg").listFiles(); 
		for (i in jpgs) 
		{
			var file = jpgs[i];
			if (file.getName().endsWith(".jpg")) {
				p.exec(convert + " " + file + " -strip -quality 95 " + file, 60000);
			}
		}
	}
	// 主角
	else if (cpj_file_name.getName().endsWith("actor.cpj")) 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			
			if (file.getName().endsWith(".png"))
			{
				if (dir.getName().startsWith("actor_000")) 
				{
					// actor_00* 开头的单位，PNG 8 像素
					//p.exec(convert + " " + file + " -strip -quality 90 PNG8:" + file, 60000);
				} 
				else 
				{
					// 其他 PNG 24 半透
					p.exec(convert + " " + file + " -strip " + file, 60000);
				}
			}
		}
	}
	// 道具全部 PNG 8 像素
	else if (cpj_file_name.getName().endsWith("item.cpj")) 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			if (file.getName().endsWith(".png")) {
				p.exec(convert + " " + file + " -strip -quality 95 PNG8:" + file, 60000);
			}
		}
	}
	// 其他 PNG 24 半透
	else 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			if (file.getName().endsWith(".png")) {
				p.exec(convert + " " + file + " -strip " + file, 60000);
			}
		}
	}
}

/**输出老版本资源*/
function createDirOutput(p, dir, cpj_file_name)
{
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.pakFiles			("output\\set\\jpg", ".jpg", "output\\jpg.zip", "");
		
		p.pakImageMaskFiles	("output\\set\\png", ".png", "output\\png.zip", "");
	}
}

/**输出新版本资源*/
function createPakOutput(p, dir, cpj_file_name)
{
	var entrys = p.getEntrys("output", ".properties", "");
	
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.getEntrys(".", "thumb.jpg", "", entrys);
		p.getEntrys("output\\set\\jpg", ".jpg", "jpg/", entrys);
		p.getImageMaskEntrys("output\\set\\png", ".png", "png/", entrys);
		
		com.cell.io.CFile.copy(java.io.File(dir, "output\\scene.properties"), java.io.File(dir, "scene.properties"));
	}
	else 
	{
		if (dir.getName().startsWith("actor_00")) {
			p.getEntrys(".", "icon_\\w+.png", "", entrys);
		}
		{
			// 输出整图，导致创建图片过慢。
			//p.getEntrys("output", ".png", "", entrys);
		}{
			// 输出为碎图
			var files = java.io.File(dir, "output").listFiles(); 
			for (i in files) {
				var file = files[i];
				if (file.isDirectory()) {
					p.getEntrys("output\\"+file.getName(), ".png", file.getName()+"/", entrys);
				}
			}
		}
	}
	
	p.pakEntrys(entrys, "..\\"+dir.getName()+".pak");
}

/**清理临时文件*/
function clean(p)
{
	
	p.deleteIfExists("output");
	
	p.deleteIfExists("output\\set");
	p.deleteIfExists("output\\jpg.png");
	p.deleteIfExists("output\\png.png");
	p.deleteIfExists("output\\scene_graph.conf");
	p.deleteIfExists("output\\scene_jpg.conf");
	p.deleteIfExists("output\\scene_png.conf");

	p.deleteIfExists("_script");
	p.deleteIfExists("scene_jpg_thumb.conf");
	p.deleteIfExists("png.jpg");
	p.deleteIfExists("jpg.jpg");
	
}