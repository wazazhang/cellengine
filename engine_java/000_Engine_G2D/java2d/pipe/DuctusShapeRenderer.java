/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.java2d.pipe;

import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.PathIterator;
import sun.awt.SunHints;
import sun.java2d.SunGraphics2D;
import sun.dc.pr.Rasterizer;
import sun.dc.pr.PRException;

/**
 * This class is used to convert raw geometry into 8-bit alpha tiles
 * using the Ductus Rasterizer for application by the next stage of
 * the pipeline.
 * This class sets up the Rasterizer and computes the alpha tiles
 * and then passes them on to a CompositePipe object for painting.
 */
public class DuctusShapeRenderer
    implements ShapeDrawPipe, ParallelogramPipe
{
    CompositePipe outpipe;

    public DuctusShapeRenderer(CompositePipe pipe) {
	outpipe = pipe;
    }

    public void draw(SunGraphics2D sg, Shape s) {
	BasicStroke bs;

	if (sg.stroke instanceof BasicStroke) {
	    bs = (BasicStroke) sg.stroke;
	} else {
	    s = sg.stroke.createStrokedShape(s);
	    bs = null;
	}

	renderPath(sg, s, bs);
    }

    public void fill(SunGraphics2D sg, Shape s) {
	renderPath(sg, s, null);
    }

    private static Rectangle2D computeBBox(double x, double y,
                                           double dx1, double dy1,
                                           double dx2, double dy2)
    {
        double lox, loy, hix, hiy;
        lox = hix = x;
        loy = hiy = y;
        if (dx1 < 0) { lox += dx1; } else { hix += dx1; }
        if (dy1 < 0) { loy += dy1; } else { hiy += dy1; }
        if (dx2 < 0) { lox += dx2; } else { hix += dx2; }
        if (dy2 < 0) { loy += dy2; } else { hiy += dy2; }
        return new Rectangle2D.Double(lox, loy, hix-lox, hiy-loy);
    }

    public void fillParallelogram(SunGraphics2D sg,
                                  double x, double y,
                                  double dx1, double dy1,
                                  double dx2, double dy2)
    {
        Rasterizer r =
            DuctusRenderer.createPgramRasterizer(x, y, dx1, dy1, dx2, dy2,
                                                 0, 0);
        renderTiles(sg, computeBBox(x, y, dx1, dy1, dx2, dy2), r);
    }

    public void drawParallelogram(SunGraphics2D sg,
                                  double x, double y,
                                  double dx1, double dy1,
                                  double dx2, double dy2,
                                  double lw1, double lw2)
    {
        Rasterizer r =
            DuctusRenderer.createPgramRasterizer(x, y, dx1, dy1, dx2, dy2,
                                                 lw1, lw2);
        // Note that bbox is of the original shape, not the wide path.
        // This is appropriate for handing to Paint methods...
        renderTiles(sg, computeBBox(x, y, dx1, dy1, dx2, dy2), r);
    }

    public void renderPath(SunGraphics2D sg, Shape s, BasicStroke bs) {
	PathIterator pi = s.getPathIterator(sg.transform);
	boolean adjust = (bs != null &&
			  sg.strokeHint != SunHints.INTVAL_STROKE_PURE);
	boolean thin = (sg.strokeState <= sg.STROKE_THINDASHED);
	Rasterizer r = DuctusRenderer.createShapeRasterizer(pi, sg.transform,
                                                            bs, thin,
                                                            adjust, 0.5f);
        renderTiles(sg, s, r);
    }

    public void renderTiles(SunGraphics2D sg, Shape s, Rasterizer r) {
	Object context = null;
	byte alpha[] = null;

	try {
	    int abox[] = new int[4];
	    r.getAlphaBox(abox);
	    Rectangle devR = new Rectangle(abox[0], abox[1],
					   abox[2] - abox[0],
					   abox[3] - abox[1]);
	    sg.getCompClip().clipBoxToBounds(abox);
	    if (abox[0] >= abox[2] || abox[1] >= abox[3]) {
		return;
	    }
	    r.setOutputArea(abox[0], abox[1],
			    abox[2] - abox[0], abox[3] - abox[1]);
	    context = outpipe.startSequence(sg, s, devR, abox);

	    int tsize = Rasterizer.TILE_SIZE;
	    alpha = DuctusRenderer.getAlphaTile();

	    byte[] atile;

	    for (int y = abox[1]; y < abox[3]; y += tsize) {
		for (int x = abox[0]; x < abox[2]; x += tsize) {
		    int w = Math.min(tsize, abox[2] - x);
		    int h = Math.min(tsize, abox[3] - y);

		    int state = r.getTileState();
		    if (state == Rasterizer.TILE_IS_ALL_0 ||
			outpipe.needTile(context, x, y, w, h) == false)
		    {
			r.nextTile();
			outpipe.skipTile(context, x, y);
			continue;
		    }
		    if (state == Rasterizer.TILE_IS_GENERAL) {
			atile = alpha;
			DuctusRenderer.getAlpha(r, alpha, 1, tsize, 0);
		    } else {
			atile = null;
			r.nextTile();
		    }

		    outpipe.renderPathTile(context, atile, 0, tsize,
					   x, y, w, h);
		}
	    }
	} catch (PRException e) {
	    e.printStackTrace();
	} finally {
	    DuctusRenderer.dropRasterizer(r);
	    if (context != null) {
		outpipe.endSequence(context);
	    }
	    if (alpha != null) {
		DuctusRenderer.dropAlphaTile(alpha);
	    }
	}
    }
}
