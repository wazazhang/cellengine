/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.java2d.pipe;

import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import sun.dc.path.PathConsumer;
import sun.dc.path.PathException;
import sun.dc.pr.Rasterizer;
import sun.dc.pr.PathStroker;
import sun.dc.pr.PathDasher;
import sun.dc.pr.PRException;

/**
 * This class provides utility routines to create, cache, initialize
 * and release Ductus Rasterizer objects.
 */
public final class DuctusRenderer {
    public static final float PenUnits = 0.01f;
    public static final int MinPenUnits = 100;
    public static final int MinPenUnitsAA = 20;
    public static final float MinPenSizeAA = PenUnits * MinPenUnitsAA;

    static final int RasterizerCaps[] = {
	Rasterizer.BUTT, Rasterizer.ROUND, Rasterizer.SQUARE
    };

    static final int RasterizerCorners[] = {
	Rasterizer.MITER, Rasterizer.ROUND, Rasterizer.BEVEL
    };

    private static Rasterizer theRasterizer;

    public synchronized static Rasterizer getRasterizer() {
	Rasterizer r = theRasterizer;
	if (r == null) {
	    r = new Rasterizer();
	} else {
	    theRasterizer = null;
	}
	return r;
    }

    public synchronized static void dropRasterizer(Rasterizer r) {
	r.reset();
	theRasterizer = r;
    }

    private static byte[] theTile;

    public synchronized static byte[] getAlphaTile() {	
	byte[] t = theTile;
	if (t == null) {
	    int dim = Rasterizer.TILE_SIZE;
	    t = new byte[dim * dim];
	} else {
	    theTile = null;
	}
	return t;
    }

    public synchronized static void dropAlphaTile(byte[] t) {
	theTile = t;
    }

    /*
     * writeAlpha is not threadsafe (it uses a global table without
     * locking), so we must use this static synchronized accessor
     * method to serialize accesses to it.
     */
    public synchronized static void getAlpha(Rasterizer r, byte[] alpha,
					     int xstride, int ystride,
					     int offset)
	throws PRException
    {
	try {
	    r.writeAlpha(alpha, xstride, ystride, offset);
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
    }

    /*
     * Returns a new PathConsumer that will take a path trajectory and
     * feed the stroked outline for that trajectory to the supplied
     * PathConsumer.
     */
    public static PathConsumer createStroker(PathConsumer consumer,
					     BasicStroke bs, boolean thin,
					     AffineTransform transform)
    {
	PathStroker stroker = new PathStroker(consumer);
	consumer = stroker;

	float matrix[] = null;
	if (!thin) {
	    stroker.setPenDiameter(bs.getLineWidth());
	    if (transform != null) {
		matrix = getTransformMatrix(transform);
	    }
	    stroker.setPenT4(matrix);
	    stroker.setPenFitting(PenUnits, MinPenUnits);
	}
	stroker.setCaps(RasterizerCaps[bs.getEndCap()]);
	stroker.setCorners(RasterizerCorners[bs.getLineJoin()],
			   bs.getMiterLimit());
	float[] dashes = bs.getDashArray();
	if (dashes != null) {
	    PathDasher dasher = new PathDasher(stroker);
	    dasher.setDash(dashes, bs.getDashPhase());
	    if (transform != null && matrix == null) {
		matrix = getTransformMatrix(transform);
	    }
	    dasher.setDashT4(matrix);
	    consumer = dasher;
	}

	return consumer;
    }

    static float[] getTransformMatrix(AffineTransform transform) {
	float matrix[] = new float[4];
	double dmatrix[] = new double[6];
	transform.getMatrix(dmatrix);
	for (int i = 0; i < 4; i++) {
	    matrix[i] = (float) dmatrix[i];
	}
	return matrix;
    }

    /*
     * Disposes a PathConsumer chain created by createStroker.
     * This method will dispose all PathConsumers chained
     * together starting from the stroker argument until it
     * reaches the terminus object, or null.
     */
    public static void disposeStroker(PathConsumer stroker,
				      PathConsumer terminus)
    {
	while (stroker != null && stroker != terminus) {
	    PathConsumer next = stroker.getConsumer();
	    stroker.dispose();
	    stroker = next;
	}
    }

    final static float UPPER_BND = Float.MAX_VALUE / 2.0f;
    final static float LOWER_BND = -UPPER_BND;

    /*
     * Feed a path from a PathIterator to a Ductus PathConsumer.
     */
    public static void feedConsumer(PathIterator pi, PathConsumer consumer,
				    boolean normalize, float norm)
	throws PathException
    {
	consumer.beginPath();
	boolean pathClosed = false;
        boolean skip = false;
        boolean subpathStarted = false;
	float mx = 0.0f;
	float my = 0.0f;
	float point[]  = new float[6];
	float rnd = (0.5f - norm);
	float ax = 0.0f;
	float ay = 0.0f;

	while (!pi.isDone()) {
	    int type = pi.currentSegment(point);
	    if (pathClosed == true) {
		pathClosed = false;
		if (type != PathIterator.SEG_MOVETO) {
		    // Force current point back to last moveto point
		    consumer.beginSubpath(mx, my);
                    subpathStarted = true;
		}
	    }
	    if (normalize) {
		int index;
		switch (type) {
		case PathIterator.SEG_CUBICTO:
		    index = 4;
		    break;
		case PathIterator.SEG_QUADTO:
		    index = 2;
		    break;
		case PathIterator.SEG_MOVETO:
		case PathIterator.SEG_LINETO:
		    index = 0;
		    break;
		case PathIterator.SEG_CLOSE:
		default:
		    index = -1;
		    break;
		}
		if (index >= 0) {
		    float ox = point[index];
		    float oy = point[index+1];
		    float newax = (float) Math.floor(ox + rnd) + norm;
		    float neway = (float) Math.floor(oy + rnd) + norm;
		    point[index] = newax;
		    point[index+1] = neway;
		    newax -= ox;
		    neway -= oy;
		    switch (type) {
		    case PathIterator.SEG_CUBICTO:
			point[0] += ax;
			point[1] += ay;
			point[2] += newax;
			point[3] += neway;
			break;
		    case PathIterator.SEG_QUADTO:
			point[0] += (newax + ax) / 2;
			point[1] += (neway + ay) / 2;
			break;
		    case PathIterator.SEG_MOVETO:
		    case PathIterator.SEG_LINETO:
		    case PathIterator.SEG_CLOSE:
			break;
		    }
		    ax = newax;
		    ay = neway;
		}
	    }
	    switch (type) {
	    case PathIterator.SEG_MOVETO:

                /* Checking SEG_MOVETO coordinates if they are out of the 
                 * [LOWER_BND, UPPER_BND] range. This check also handles NaN 
                 * and Infinity values. Skipping next path segment in case of 
                 * invalid data.
                 */
                if (point[0] < UPPER_BND && point[0] > LOWER_BND && 
                    point[1] < UPPER_BND && point[1] > LOWER_BND) 
                {
                    mx = point[0];
                    my = point[1];
		    consumer.beginSubpath(mx, my);
                    subpathStarted = true;
                    skip = false;
                } else {
                    skip = true;
                }
		break;
	    case PathIterator.SEG_LINETO:
                /* Checking SEG_LINETO coordinates if they are out of the 
                 * [LOWER_BND, UPPER_BND] range. This check also handles NaN 
                 * and Infinity values. Ignoring current path segment in case  
                 * of invalid data. If segment is skipped its endpoint 
                 * (if valid) is used to begin new subpath. 
                 */
                if (point[0] < UPPER_BND && point[0] > LOWER_BND &&
                    point[1] < UPPER_BND && point[1] > LOWER_BND) 
                {
                    if (skip) {
                        consumer.beginSubpath(point[0], point[1]);
                        subpathStarted = true;
                        skip = false;
                    } else {
                        consumer.appendLine(point[0], point[1]);
                    }
                } 
		break;
	    case PathIterator.SEG_QUADTO:
		// Quadratic curves take two points
                
                /* Checking SEG_QUADTO coordinates if they are out of the 
                 * [LOWER_BND, UPPER_BND] range. This check also handles NaN 
                 * and Infinity values. Ignoring current path segment in case  
                 * of invalid endpoints's data. Equivalent to the SEG_LINETO
                 * if endpoint coordinates are valid but there are invalid data
                 * amoung other coordinates
                 */
                if (point[2] < UPPER_BND && point[2] > LOWER_BND &&
                    point[3] < UPPER_BND && point[3] > LOWER_BND) 
                {
                    if (skip) {
                        consumer.beginSubpath(point[2], point[3]);
                        subpathStarted = true;
                        skip = false;
                    } else {
                        if (point[0] < UPPER_BND && point[0] > LOWER_BND &&
                            point[1] < UPPER_BND && point[1] > LOWER_BND) 
                        {
                            consumer.appendQuadratic(point[0], point[1],
                                                     point[2], point[3]);
                        } else {
                            consumer.appendLine(point[2], point[3]);
                        }
                    }
                } 
		break;
	    case PathIterator.SEG_CUBICTO:
		// Cubic curves take three points
                
                /* Checking SEG_CUBICTO coordinates if they are out of the 
                 * [LOWER_BND, UPPER_BND] range. This check also handles NaN 
                 * and Infinity values. Ignoring current path segment in case  
                 * of invalid endpoints's data. Equivalent to the SEG_LINETO
                 * if endpoint coordinates are valid but there are invalid data
                 * amoung other coordinates
                 */
                if (point[4] < UPPER_BND && point[4] > LOWER_BND &&
                    point[5] < UPPER_BND && point[5] > LOWER_BND) 
                {
                    if (skip) {
                        consumer.beginSubpath(point[4], point[5]);
                        subpathStarted = true;
                        skip = false;
                    } else {
                        if (point[0] < UPPER_BND && point[0] > LOWER_BND && 
                            point[1] < UPPER_BND && point[1] > LOWER_BND &&
                            point[2] < UPPER_BND && point[2] > LOWER_BND &&
                            point[3] < UPPER_BND && point[3] > LOWER_BND) 
                        {
                            consumer.appendCubic(point[0], point[1],
                                                 point[2], point[3],
                                                 point[4], point[5]);
                        } else {
                            consumer.appendLine(point[4], point[5]);
                        }
                    }
                }
		break;
	    case PathIterator.SEG_CLOSE:
                if (subpathStarted) {
                    consumer.closedSubpath();
                    subpathStarted = false;
		    pathClosed = true;
                }
		break;
	    }
	    pi.next();
	}

	consumer.endPath();
    }

    /*
     * Returns a new Rasterizer that is ready to rasterize the given Shape.
     */
    public static Rasterizer createShapeRasterizer(PathIterator pi,
						   AffineTransform transform,
						   BasicStroke stroke,
						   boolean thin,
						   boolean normalize,
						   float norm)
    {
	Rasterizer r = getRasterizer();

        if (stroke != null) {
	    float matrix[] = null;
            r.setUsage(Rasterizer.STROKE);
	    if (thin) {
		r.setPenDiameter(MinPenSizeAA);
	    } else {
		r.setPenDiameter(stroke.getLineWidth());
		if (transform != null) {
		    matrix = getTransformMatrix(transform);
		    r.setPenT4(matrix);
		}
		r.setPenFitting(PenUnits, MinPenUnitsAA);
	    }
	    r.setCaps(RasterizerCaps[stroke.getEndCap()]);
	    r.setCorners(RasterizerCorners[stroke.getLineJoin()],
			 stroke.getMiterLimit());
	    float[] dashes = stroke.getDashArray();
	    if (dashes != null) {
		r.setDash(dashes, stroke.getDashPhase());
		if (transform != null && matrix == null) {
		    matrix = getTransformMatrix(transform);
		}
		r.setDashT4(matrix);
	    }
        } else {
            r.setUsage(pi.getWindingRule() == PathIterator.WIND_EVEN_ODD
                       ? Rasterizer.EOFILL
                       : Rasterizer.NZFILL);
        }

	r.beginPath();
	{
            boolean pathClosed = false;
            boolean skip = false;
            boolean subpathStarted = false;
            float mx = 0.0f;
            float my = 0.0f;
            float point[]  = new float[6];
	    float rnd = (0.5f - norm);
	    float ax = 0.0f;
	    float ay = 0.0f;

	    while (!pi.isDone()) {
		int type = pi.currentSegment(point);
                if (pathClosed == true) {
                    pathClosed = false;
                    if (type != PathIterator.SEG_MOVETO) {
                        // Force current point back to last moveto point
                        r.beginSubpath(mx, my);
                        subpathStarted = true;
                    }
                }
		if (normalize) {
		    int index;
		    switch (type) {
		    case PathIterator.SEG_CUBICTO:
			index = 4;
			break;
		    case PathIterator.SEG_QUADTO:
			index = 2;
			break;
		    case PathIterator.SEG_MOVETO:
		    case PathIterator.SEG_LINETO:
			index = 0;
			break;
		    case PathIterator.SEG_CLOSE:
		    default:
			index = -1;
			break;
		    }
		    if (index >= 0) {
			float ox = point[index];
			float oy = point[index+1];
			float newax = (float) Math.floor(ox + rnd) + norm;
			float neway = (float) Math.floor(oy + rnd) + norm;
			point[index] = newax;
			point[index+1] = neway;
			newax -= ox;
			neway -= oy;
			switch (type) {
			case PathIterator.SEG_CUBICTO:
			    point[0] += ax;
			    point[1] += ay;
			    point[2] += newax;
			    point[3] += neway;
			    break;
			case PathIterator.SEG_QUADTO:
			    point[0] += (newax + ax) / 2;
			    point[1] += (neway + ay) / 2;
			    break;
			case PathIterator.SEG_MOVETO:
			case PathIterator.SEG_LINETO:
			case PathIterator.SEG_CLOSE:
			    break;
			}
			ax = newax;
			ay = neway;
		    }
		}
		switch (type) {
		case PathIterator.SEG_MOVETO:

                   /* Checking SEG_MOVETO coordinates if they are out of the 
                    * [LOWER_BND, UPPER_BND] range. This check also handles NaN 
                    * and Infinity values. Skipping next path segment in case 
                    * of invalid data.
                    */
 
                    if (point[0] < UPPER_BND &&  point[0] > LOWER_BND && 
                        point[1] < UPPER_BND &&  point[1] > LOWER_BND) 
                    {
                        mx = point[0];
                        my = point[1];
		        r.beginSubpath(mx, my);
                        subpathStarted = true;
                        skip = false;
                    } else {
                        skip = true;
                    }
		    break;

		case PathIterator.SEG_LINETO:
                    /* Checking SEG_LINETO coordinates if they are out of the 
                     * [LOWER_BND, UPPER_BND] range. This check also handles 
                     * NaN and Infinity values. Ignoring current path segment 
                     * in case of invalid data. If segment is skipped its 
                     * endpoint (if valid) is used to begin new subpath. 
                     */
                    if (point[0] < UPPER_BND && point[0] > LOWER_BND &&
                        point[1] < UPPER_BND && point[1] > LOWER_BND) 
                    {
                        if (skip) {
                            r.beginSubpath(point[0], point[1]);
                            subpathStarted = true;
                            skip = false;
                        } else {
                            r.appendLine(point[0], point[1]);
                        }
                    }
		    break;

		case PathIterator.SEG_QUADTO:
                    // Quadratic curves take two points
                    
                    /* Checking SEG_QUADTO coordinates if they are out of the 
                     * [LOWER_BND, UPPER_BND] range. This check also handles 
                     * NaN and Infinity values. Ignoring current path segment  
                     * in case of invalid endpoints's data. Equivalent to the 
                     * SEG_LINETO if endpoint coordinates are valid but there 
                     * are invalid data amoung other coordinates
                     */
                    if (point[2] < UPPER_BND && point[2] > LOWER_BND &&
                        point[3] < UPPER_BND && point[3] > LOWER_BND) 
                    {
                        if (skip) {
                            r.beginSubpath(point[2], point[3]);
                            subpathStarted = true;
                            skip = false;
                        } else {
                            if (point[0] < UPPER_BND && point[0] > LOWER_BND &&
                                point[1] < UPPER_BND && point[1] > LOWER_BND) 
                            {
                                r.appendQuadratic(point[0], point[1],
                                                  point[2], point[3]);
                            } else {
                                r.appendLine(point[2], point[3]);
                            }
                        }
                    }
		    break;
		case PathIterator.SEG_CUBICTO:
                    // Cubic curves take three points
                    
                    /* Checking SEG_CUBICTO coordinates if they are out of the 
                     * [LOWER_BND, UPPER_BND] range. This check also handles 
                     * NaN and Infinity values. Ignoring  current path segment 
                     * in case of invalid endpoints's data. Equivalent to the 
                     * SEG_LINETO if endpoint coordinates are valid but there 
                     * are invalid data amoung other coordinates
                     */

                    if (point[4] < UPPER_BND && point[4] > LOWER_BND &&
                        point[5] < UPPER_BND && point[5] > LOWER_BND) 
                    {
                        if (skip) {
                            r.beginSubpath(point[4], point[5]);
                            subpathStarted = true;
                            skip = false;
                        } else {
                            if (point[0] < UPPER_BND && point[0] > LOWER_BND && 
                                point[1] < UPPER_BND && point[1] > LOWER_BND &&
                                point[2] < UPPER_BND && point[2] > LOWER_BND &&
                                point[3] < UPPER_BND && point[3] > LOWER_BND) 
                            {
                                r.appendCubic(point[0], point[1],
                                              point[2], point[3],
                                              point[4], point[5]);
                            } else {
                                r.appendLine(point[4], point[5]);
                            }
                        }
                    }
		    break;
		case PathIterator.SEG_CLOSE:
                    if (subpathStarted) {
		        r.closedSubpath();
                        subpathStarted = false;
                        pathClosed = true;
                    }
		    break;
		}
		pi.next();
	    }
	}

        try {
            r.endPath();
        } catch (PRException e) {
            /*
             * This exeption is thrown from the native part of the Ductus
             * (only in case of a debug build) to indicate that some
             * segments of the path have very large coordinates.
             * See 4485298 for more info.
             */
            System.err.println("DuctusRenderer.createShapeRasterizer: " +  e);
        }

        return r;
    }

    public static Rasterizer createPgramRasterizer(double x, double y,
                                                   double dx1, double dy1,
                                                   double dx2, double dy2,
                                                   double lw1, double lw2)
    {
        // REMIND: Deal with large coordinates!
        double ldx1, ldy1, ldx2, ldy2;
        boolean innerpgram = (lw1 > 0 && lw2 > 0);

        if (innerpgram) {
            ldx1 = dx1 * lw1;
            ldy1 = dy1 * lw1;
            ldx2 = dx2 * lw2;
            ldy2 = dy2 * lw2;
            x -= (ldx1 + ldx2) / 2.0;
            y -= (ldy1 + ldy2) / 2.0;
            dx1 += ldx1;
            dy1 += ldy1;
            dx2 += ldx2;
            dy2 += ldy2;
            if (lw1 > 1 && lw2 > 1) {
                // Inner parallelogram was entirely consumed by stroke...
                innerpgram = false;
            }
        } else {
            ldx1 = ldy1 = ldx2 = ldy2 = 0;
        }

	Rasterizer r = getRasterizer();

        r.setUsage(Rasterizer.EOFILL);

	r.beginPath();
        r.beginSubpath((float) x, (float) y);
        r.appendLine((float) (x+dx1), (float) (y+dy1));
        r.appendLine((float) (x+dx1+dx2), (float) (y+dy1+dy2));
        r.appendLine((float) (x+dx2), (float) (y+dy2));
        r.closedSubpath();
        if (innerpgram) {
            x += ldx1 + ldx2;
            y += ldy1 + ldy2;
            dx1 -= 2.0 * ldx1;
            dy1 -= 2.0 * ldy1;
            dx2 -= 2.0 * ldx2;
            dy2 -= 2.0 * ldy2;
            r.beginSubpath((float) x, (float) y);
            r.appendLine((float) (x+dx1), (float) (y+dy1));
            r.appendLine((float) (x+dx1+dx2), (float) (y+dy1+dy2));
            r.appendLine((float) (x+dx2), (float) (y+dy2));
            r.closedSubpath();
        }

        try {
            r.endPath();
        } catch (PRException e) {
            /*
             * This exeption is thrown from the native part of the Ductus
             * (only in case of a debug build) to indicate that some
             * segments of the path have very large coordinates.
             * See 4485298 for more info.
             */
            System.err.println("DuctusRenderer.createShapeRasterizer: " +  e);
        }

        return r;
    }
}
