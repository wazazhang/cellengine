package com.sun.javafx.newt.macosx;

import com.sun.javafx.newt.ScreensInfo;

/**
 *
 * @author tdv
 */
public class MacScreensInfo extends ScreensInfo {

    static ScreensInfo theInstance;
    static ScreensInfo getInstance() {
        if (theInstance == null) {
            theInstance = new MacScreensInfo();
        }
        return theInstance;
    }

    private MacScreensInfo() {
        initScreens();
    }

    protected native void nInitData();
}

