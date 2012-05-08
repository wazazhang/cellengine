package com.g2d.studio.io.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.util.Pair;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.io.File;
import com.g2d.studio.io.IO;

public class FileIO implements IO
{
	private static ArrayList<Pair<String, String>> REDIRECT_MAP = new ArrayList<Pair<String,String>>();
	
	private static void addRedirect(String src, String dst) {
		REDIRECT_MAP.add(new Pair<String, String>(src, dst));
	}
	
	public FileIO(String[] args) {
		for (int i = 1; i < args.length; i++) {
			if (args[i].startsWith("redirect=")) {
				File rfile = createFile(args[i].substring("redirect=".length()));
				String ftext = rfile.readUTF();
				for (String line : CUtil.splitString(ftext, "\n")) {
					String[] sd = CUtil.splitString(line, "->", true);
					if (sd.length > 1) {
						FileIO.addRedirect(sd[0], sd[1]);
						System.out.println("FileImpl - redirect map : " + sd[0]);
						System.out.println("                          " + sd[1]);
					}
				}
			}
		}
	}
	
    public File createFile(String pathname) {
        return new FileImpl(new java.io.File(pathname));
    }

    public File createFile(String parent, String child) {
    	return new FileImpl(new java.io.File(parent, child));
    }

    public File createFile(File parent, String child) {
    	return new FileImpl(new java.io.File(((FileImpl)parent).file, child));
    }
    
    private static class FileImpl implements File
    {
    	final java.io.File file;
    	
    	private FileImpl(java.io.File file) {
    		try {
    			file = file.getCanonicalFile();
    			String path = file.getPath();
//    			System.out.println("create File : " + path);
    			for (Pair<String, String> pair : REDIRECT_MAP) {
    				if (path.startsWith(pair.getKey())) {
    					path = pair.getValue() + path.substring(pair.getValue().length());
    					file = new java.io.File(path);
    					System.out.println("FileImpl - redirect File : " + path);
    					break;
    				}
    			}
    		} catch (Throwable e) {
    			e.printStackTrace();
    		}
			this.file = file;
		}
    	
    	@Override
    	public InputStream getInputStream() {
    		try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
    	}

    	@Override
    	public OutputStream getOutputStream() {
    		try {
				return new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
    	}
    	
    	@Override
    	public byte[] readBytes() {
    		return CIO.readStream(getInputStream());
    	}
    	@Override
    	public void writeBytes(byte[] data) {
    		if (file.getParentFile().exists()) {
    			file.getParentFile().mkdirs();
    		}
    		CFile.writeData(file, data);
    	}
    	
    	@Override
    	public String readUTF() {
    		return CIO.stringDecode(readBytes(), CIO.ENCODING);
    	}
    	@Override
    	public void writeUTF(String data) {
    		writeBytes(CIO.stringEncode(data, CIO.ENCODING));
    	}
    	
		@Override
		public boolean exists() {
			return file.exists();
		}

		@Override
		public String getName() {
			return file.getName();
		}

		@Override
		public String getParent() {
			return file.getParent();
		}

		@Override
		public File getParentFile() {
			return new FileImpl(file.getParentFile());
		}
		
		@Override
		public File getChildFile(String name) {
			return new FileImpl(new java.io.File(file, name));
		}
		
		@Override
		public String getPath() {
			return file.getPath();
		}

		@Override
		public File[] listFiles() {
			java.io.File[] rfs = file.listFiles();
			ArrayList<File> files = new ArrayList<File>(rfs.length);
			for (java.io.File sub : file.listFiles()) {
				files.add(new FileImpl(sub));
			}
			return files.toArray(new File[files.size()]);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof FileImpl) {
				if (((FileImpl) obj).file.equals(this.file)) {
//					System.out.println("equal : " + this.file);
					return true;
				}
			} 
			return false;
		}
		
		@Override
		public int hashCode() {
			return file.hashCode();
		}
		
		@Override
		public void delete() {
			file.delete();
		}
		
		@Override
		public String toString() {
			return file.toString();
		}
    }
}
