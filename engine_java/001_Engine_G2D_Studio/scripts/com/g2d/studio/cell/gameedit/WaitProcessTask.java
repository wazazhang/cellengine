package com.g2d.studio.cell.gameedit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cell.CIO;

public class WaitProcessTask extends Thread
{
	final Process p;
	final int timeout;
	
	public WaitProcessTask(Process p, int timeout) {
		this.p = p;
		this.timeout = timeout;
	}
	
	@Override
	public void run() 
	{
		synchronized (this) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		try {
//			Thread.sleep(1000);
//			InputStream is = p.getInputStream();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
//			try {
//				while (is.available() > 0) {
//					byte[] data = new byte[1024];
//					int read_bytes = is.read(data);
//					if (read_bytes <= 0) {
//						break;
//					} else {
//						baos.write(data, 0, read_bytes);
//					}
//				}
//			} catch (Exception e) {
//			}
//			System.out.println(new String(baos.toByteArray()));
//		}catch(Exception err) {}

		
		try {
			int exitcode = p.exitValue();
//			System.out.println("exit code = " + exitcode);
		} catch (Exception err) {
			System.err.println("强制结束进程 ");
			p.destroy();
		}
	}
}
