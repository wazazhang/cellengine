package com.sun.javafx.newt.windows;

import com.sun.javafx.newt.ScreensInfo;

/**
 *
 * @author tdv
 */
public class WindowsScreensInfo extends ScreensInfo {

    static ScreensInfo theInstance;
    static ScreensInfo getInstance() {
        if (theInstance == null) {
            theInstance = new WindowsScreensInfo();
        }
        return theInstance;
    }

    private WindowsScreensInfo() {
        initScreens();
    }

    protected native void nInitData();
}

