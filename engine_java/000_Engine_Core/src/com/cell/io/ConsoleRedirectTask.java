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
    private PipedInputStream pis = new PipedInputStream();

    private PipedOutputStream pos;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(pis));

    public ConsoleRedirectTask() throws IOException {
    	super.setDaemon(true);
        pos = new PipedOutputStream(pis);
    }

    public void run() {
        String line = null;
		System.setOut(new PrintStream(pos, true));
		System.setErr(new PrintStream(pos, true));
        while (true) {
            try {
                line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                	appendLine(line);
                }
            } catch (Exception ioe) {
                break;
            }
        }
    }

	/**
	 * 这里处理截获的控制台输出
	 */
	public void appendLine(String line) throws Exception {
	}

}
