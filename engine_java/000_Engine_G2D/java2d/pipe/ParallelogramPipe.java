/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.java2d.pipe;

import sun.java2d.SunGraphics2D;

/**
 * This interface defines the set of calls that pipeline objects
 * can use to pass on responsibility for drawing arbitrary
 * parallelogram shapes.
 * Six floating point numbers are provided and the parallelogram
 * is defined as the quadrilateral with the following vertices:
 * <pre>
 *     origin: (x, y)
 *          => (x+dx1, y+dy1)
 *          => (x+dx1+dx2, y+dy1+dy2)
 *          => (x+dx2, y+dy2)
 *          => origin
 * </pre>
 */
public interface ParallelogramPipe {
    public void fillParallelogram(SunGraphics2D sg,
                                  double x, double y,
                                  double dx1, double dy1,
                                  double dx2, double dy2);

    /**
     * Draw a Parallelogram with the indicated line widths
     * assuming a standard BasicStroke with MITER joins.
     * lw1 specifies the width of the stroke along the dx1,dy1
     * vector and lw2 specifies the width of the stroke along
     * the dx2,dy2 vector.
     * This is equivalent to outsetting the indicated
     * parallelogram by lw/2 pixels, then insetting the
     * same parallelogram by lw/2 pixels and filling the
     * difference between the outer and inner parallelograms.
     */
    public void drawParallelogram(SunGraphics2D sg,
                                  double x, double y,
                                  double dx1, double dy1,
                                  double dx2, double dy2,
                                  double lw1, double lw2);
}
