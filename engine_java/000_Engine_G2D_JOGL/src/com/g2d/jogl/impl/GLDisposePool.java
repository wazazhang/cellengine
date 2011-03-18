package com.g2d.jogl.impl;

import java.util.concurrent.ThreadPoolExecutor;

import javax.media.opengl.GL;

import com.cell.util.concurrent.ThreadPool;

public class GLDisposePool 
{
	private static ThreadPool pool = new ThreadPool("gl-dispose", 0, 1, 1);
	static void glDeleteTextures(final GL gl, final int[] gl_texture) {
		pool.executeTask(new Runnable() {
			@Override
			public void run() {
				gl.glDeleteTextures(1, gl_texture, 0);
			}
		});
	}
}
