import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.security.MD5;

/**
 * @author sony
 * 将一个目录里所有以指定前缀的文件重命名为另外一种后缀。
 */
public class FileChecker 
{
	public static void main(String[] args) throws IOException
	{
		String usage = 
			"将一个目录里所有以指定前缀二进制数据的文件重命名为另外一种后缀。\n" +
			"FileChecker " +
			"[file header hex] " +
			"[input folder] " +
			"[output folder] " +
			"[rename suffix] <use md5 path output true(default) or false)>";
		
		if (args.length >= 4) 
		{
			File out  = new File(args[2]);
			File root = new File(args[1]);
			boolean isMD5Path = true;
			try {
				isMD5Path = Boolean.parseBoolean(args[4]);
			}catch (Exception e) {}
			byte[] prefixBin = CUtil.hex2bin(args[0]);
			renamekFiles(
					root.getCanonicalPath(), 
					out.getCanonicalPath(),
					root,
					prefixBin, 
					args[3],
					isMD5Path);
		} else {
			System.out.println(usage);
		}
		
		
	}
	
	static private void renamekFiles(
			String rootPath, 
			String outPath, 
			File curFile, 
			byte[] prefixBin, 
			String suffix,
			boolean isMD5Path) throws IOException
	{
		if (curFile.isDirectory()) {
			for (File sub : curFile.listFiles()) {
				renamekFiles(rootPath, outPath, sub, prefixBin, suffix, isMD5Path);
			}
		} else if (curFile.isFile()) {
			try {
				byte[] srcD = CFile.readData(curFile, 0, prefixBin.length);
				if (CUtil.arrayEquals(srcD, 0, prefixBin, 0, prefixBin.length)) {
					String outSubPath = curFile.getCanonicalPath().substring(
							rootPath.length());
					if (isMD5Path) {
						outSubPath = MD5.getMD5(outSubPath);
					}
					File outfile = new File(outPath, outSubPath + suffix);
					System.out.println("rename : " + 
							curFile.getPath() + " -> " + 
							outfile.getPath()) ;
					CFile.copy(curFile, new File(outfile.getPath()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
