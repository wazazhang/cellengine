import java.io.File;
import java.util.Arrays;

import com.cell.CUtil;
import com.cell.io.CFile;

/**
 * @author sony
 * 将一个目录里所有以指定前缀的文件重命名为另外一种后缀。
 */
public class FileChecker 
{
	public static void main(String[] args)
	{
		String usage = "FileChecker [file header hex] [input folder] [rename suffix]";
		
		if (args.length >= 3) 
		{
			File root = new File(args[1]);
			byte[] prefixBin = CUtil.hex2bin(args[0]);
			renamekFiles(root, prefixBin, args[2]);
		} else {
			System.out.println(usage);
		}
		
		
	}
	
	static private void renamekFiles(File root, byte[] prefixBin, String suffix)
	{
		if (root.isDirectory()) {
			for (File sub : root.listFiles()) {
				renamekFiles(sub, prefixBin, suffix);
			}
		} else if (root.isFile()) {
			try {
				byte[] srcD = CFile.readData(root, 0, prefixBin.length);
				if (CUtil.arrayEquals(srcD, 0, prefixBin, 0, prefixBin.length)) {
					System.out.println("rename : " + root.getPath() + " -> " + root.getPath() + suffix) ;
					CFile.copy(root, new File(root.getPath() + suffix));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
