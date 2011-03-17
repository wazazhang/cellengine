package com.g3d.jogl.test;
import java.io.*;
import java.nio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import javax.media.opengl.*;

public class Imaging 
{

	// ////////////// Variables /////////////////////////

	// Databuffer that holds the loaded image.
	byte[] imgRGBA = null; // This is for the old JOGL version.
	ByteBuffer imgRGBABuf; // For JOGL version higher 1.1.
	// Image size retrieved durung loading,
	// re-used when image is drawn.
	int imgHeight;
	int imgWidth;

	// To copy the content of the current frame.
	int frameWidth;
	int frameHeight;

	// /////////////// Functions /////////////////////////

	public Imaging(String filename)
	{
		// Load image and get height and width for raster.
		//
		java.awt.Image img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		MediaTracker tracker = new MediaTracker(new Canvas());
		tracker.addImage(img, 0);
		try {
			// Starts loading all images tracked by
			// this media tracker (wait max para ms).
			tracker.waitForAll(1000);
		} catch (InterruptedException ie) {
			System.out.println("MediaTracker Exception");
		}

		imgHeight = img.getHeight(null);
		imgWidth = img.getWidth(null);
		frameWidth = imgWidth;
		frameHeight = imgHeight;
		// System.out.println( "Image, width=" + imgWidth +
		// ", height=" + imgHeight ); //ddd

		// Create a raster with correct size,
		// and a colorModel and finally a bufImg.
		//
		WritableRaster raster = Raster.createInterleavedRaster(
				DataBuffer.TYPE_BYTE, imgWidth, imgHeight, 4, null);
		ComponentColorModel colorModel = new ComponentColorModel(ColorSpace
				.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
				true, false, ComponentColorModel.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);
		BufferedImage bufImg = new BufferedImage(colorModel, // color model
				raster, false, // isRasterPremultiplied
				null); // properties

		// Filter img into bufImg and perform
		// Coordinate Transformations on the way.
		//
		Graphics2D g = bufImg.createGraphics();
		AffineTransform gt = new AffineTransform();
		gt.translate(0, imgHeight);
		gt.scale(1, -1d);
		g.transform(gt);
		g.drawImage(img, null, null);
		// Retrieve underlying byte array (imgBuf)
		// from bufImg.
		DataBufferByte imgBuf = (DataBufferByte) raster.getDataBuffer();
		imgRGBA = imgBuf.getData();
		// System.out.println( "Image length=" + imgRGBA.length );//ddd
		if (imgRGBA == null) {
			System.out.println("ERROR: Could not load image.");
			return;
		}
		// Put image into the ByteBuffer for the new JOGL version.
		imgRGBABuf = ByteBuffer.allocateDirect(imgRGBA.length);
		imgRGBABuf.put(imgRGBA);
		// System.out.println( "imgRGBABuf=" + imgRGBABuf.capacity()+
		// " / "+ imgRGBABuf.limit() ); //ddd
		g.dispose();
	}

	public void draw(GL gl, int x, int y) {
		// Load image, if necessary.
		if (imgRGBA != null) {

//			gl.glPushAttrib(GL.GL_DEPTH_BUFFER_BIT);
//			gl.glPushAttrib(GL.GL_COLOR_BUFFER_BIT);
			{

				gl.glDisable(GL.GL_DEPTH_TEST);

				// enable alpha mask (import from gif sets alpha bits)
				gl.glEnable(GL.GL_BLEND);
				gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

//				// Draw a rectangle under part of image
//				// to prove alpha works.
//				gl.glColor4f(.5f, 0.1f, 0.2f, .5f);
//				gl.glRecti(0, 0, 100, 330);
//				gl.glColor3f(0.0f, 0.0f, 0.0f);

				// Draw image as bytes.
				// gl.glRasterPos2i( 150, 100 );
				gl.glWindowPos2i(x, y);
				gl.glPixelZoom(1.0f, 1.0f); // x-factor, y-factor
				gl.glDrawPixels(imgWidth, imgHeight, gl.GL_RGBA,
						gl.GL_UNSIGNED_BYTE, imgRGBABuf.rewind());
			}
//			gl.glPopAttrib();
//			gl.glPopAttrib();
		}

	}

}
