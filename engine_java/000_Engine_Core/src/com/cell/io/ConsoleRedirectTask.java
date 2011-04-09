package com.cell.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.cell.CObject;

public class ConsoleRedirectTask extends Thread
{
	private OutputStream fos;
	
    private PipedInputStream pis = new PipedInputStream();

    private PipedOutputStream pos;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(pis));

    public ConsoleRedirectTask(OutputStream out) throws IOException {
        pos = new PipedOutputStream(pis);
		fos = out;
		System.setOut(new PrintStream(pos, true));
		System.setErr(new PrintStream(pos, true));
    }

    public void run() {
        String line = null;
        while (true) {
            try {
                line = reader.readLine()+"\r\n";
            } catch (IOException ioe) {
                break;
            }
            if (line == null) {
                break;
            } else {
            	//这里处理截获的控制台输出
            	try {
					fos.write(line.getBytes(CObject.getEncoding()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    }

	

}
