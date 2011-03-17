/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.java2d.opengl;

import sun.java2d.pipe.hw.AccelGraphicsConfig;

/**
 * This interface collects the methods that are provided by both
 * GLXGraphicsConfig and WGLGraphicsConfig, making it easier to invoke these
 * methods directly from OGLSurfaceData.
 */
interface OGLGraphicsConfig extends AccelGraphicsConfig {
    OGLContext getContext();
    long getNativeConfigInfo();
    boolean isCapPresent(int cap);
}
