package com.sun.javafx.newt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class tracking configuration and order of physical screens in the
 * current graphcis environment.
 * 
 * @author Dmitri Trembovetski
 */
public abstract class ScreensInfo {
    private final ArrayList screensData = new ArrayList(0);
    private Data primaryData;
    private ArrayList newScreensData = new ArrayList(0);
    private Data newPrimaryData;
    private final ArrayList listeners = new ArrayList(0);

    /**
     * releases all listeners
     */
    public void dispose() {
        screensData.clear();
        primaryData = null;
        listeners.clear();
    }

    /**
     * Returns the data associated with the primary physical screen
     * @return data for primary screen
     */
    public final Data getPrimaryScreenData() {
        synchronized (screensData) {
            ensureInited();
            return primaryData;
        }
    }

    /**
     *
     * @return current number of physical screens in the system
     */
    public final int getScreensNum() {
        synchronized (screensData) {
            ensureInited();
            return screensData.size();
        }
    }

    public final void 
        addScreensConfigChangeListener(ScreenSetChangeListener l)
    {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void 
        removeScreensConfigChangeListener(ScreenSetChangeListener l)
    {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    /**
     *
     * @param index of the screen to retrieve the data for
     * @return data associated with the screen with given index
     */
    public final Data getScreenData(int index) {
        synchronized (screensData) {
            ensureInited();
            if (index < 0 && index >= screensData.size()) {
                index = 0;
            }
            return (Data) screensData.get(index);
        }
    }

    private void ensureInited() {
        if (screensData.size() == 0) {
            initScreens();
        }
    }

    /**
     * This method is called from native code
     */
    protected void addScreen(int index, long hMon, boolean isPrimary,
                             int x, int y, int width, int height,
                             int wx, int wy, int ww, int wh,
                             float dpiX, float dpiY)
    {
        Data data = new Data(index, hMon, isPrimary,
                             x, y, width, height,
                             wx, wy, ww, wh, dpiX, dpiY);
        if (data.isPrimary) {
            newPrimaryData = data;
        }
        newScreensData.add(data);
    }

    /**
     * called from both java and native code to [re]initialize the screens data
     * It also notifies the listeners of screen configuration changes
     */
    protected final void initScreens() {
        newPrimaryData = null;
        newScreensData.clear();
        nInitData();
        if (newPrimaryData == null) {
            newPrimaryData = (Data) newScreensData.get(0);
        }
        ArrayList oldData = screensData;
        synchronized (screensData) {
            screensData.clear();
            screensData.addAll(newScreensData);
            newScreensData.clear();
            primaryData = newPrimaryData;
            newPrimaryData = null;

            // make sure primary is always first
            screensData.remove(primaryData);
            screensData.add(0, primaryData);
        }
        synchronized (listeners) {
            if (listeners.size() > 0) {
                List oldDataSet = Collections.unmodifiableList(oldData);
                List newDataSet = Collections.unmodifiableList(screensData);
                for (int i = 0; i < listeners.size(); i++) {
                    ((ScreenSetChangeListener)listeners.get(i)).
                        screenSetChanged(newDataSet, oldDataSet);
                }
            }
        }
    }

    /**
     * subclasses are to override this method to call addScreen for the
     * with each currently attached physical screen's data
     */
    protected abstract void nInitData();

    /**
     * Encapsulates data for the characteristics of a physical screen
     */
    public static class Data {
        
        public final int index;
        /**
         * unique platform-dependent native screen handle, could be used for
         * figuring out if screens were added/removed/rearranged
         */
        public final long nativeHandle;
        
        // physical dimensions
        public final int x;
        public final int y;
        public final int w;
        public final int h;
        
        // work area
        public final int wx;
        public final int wy;
        public final int ww;
        public final int wh;
        public final boolean isPrimary;
        
        public final float dpiX;
        public final float dpiY;

        public Data(int index, long nativeHandle, boolean isPrimary,
                    int x, int y, int width, int height,
                    int wx, int wy, int ww, int wh, float dpiX, float dpiY)
        {
            this.index = index;
            this.nativeHandle = nativeHandle;
            this.isPrimary = isPrimary;
            this.x = x;
            this.y = y;
            this.w = width;
            this.h = height;
            this.wx = wx;
            this.wy = wy;
            this.ww = ww;
            this.wh = wh;
            this.dpiX = dpiX;
            this.dpiY = dpiY;
        }

        public String toString() {
            return "ScreenData[index=" + index + " isPrimary=" + isPrimary +
                " nativeHandle=" + Long.toHexString(nativeHandle) +
                " dpiX=" + dpiX + " dpiY=" + dpiY +
                " full: (" + x + ", " + y + ", " + w + ", " + h + ")" +
                " work: (" + wx + ", " + wy + ", " + ww + ", " + wh + ")]";
        }
    }
}
