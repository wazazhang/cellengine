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

function build(p, dir)
{
	
	var convert = java.io.File(
			dir.getParentFile(),
			"\\sign\\ImageMagick\\convertimg.exe").getCanonicalPath();
	
//	--------------------------------------------------------------------------------------
	
	println("优化所有图标");
	
	var icons = java.io.File(dir, "icons").listFiles(); 
	
	for (i in icons)
	{
		var file = icons[i];
		
		if (file.getName().endsWith(".png"))
		{
			var cmd = convert + " " + file + " -strip -quality 95 PNG8:" + file;
			p.exec(cmd, 60000);
			println(cmd);
		}
	}
	
//	--------------------------------------------------------------------------------------
	
	
	
	
	
//	--------------------------------------------------------------------------------------
	
	
}
