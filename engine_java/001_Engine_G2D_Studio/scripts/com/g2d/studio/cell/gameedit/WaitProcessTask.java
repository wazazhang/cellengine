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
		try {
			int exitcode = p.exitValue();
//			System.out.println("exit code = " + exitcode);
		} catch (Exception err) {
			System.err.println("强制结束进程 ");
			p.destroy();
		}
	}
}
