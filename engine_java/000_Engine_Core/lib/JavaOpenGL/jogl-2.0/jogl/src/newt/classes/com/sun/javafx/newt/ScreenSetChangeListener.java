package com.sun.javafx.newt;

import java.util.List;

/**
 * Interface for tracking changes in the configuration of the physical screens
 * in the system.
 *
 * @author Dmitri Trembovetski
 */
public interface ScreenSetChangeListener {
    /**
     * Notifies that the set of physical screens has changed. The change may
     * be a physical addition or removal of the screens, or working/visual
     * are change.
     * 
     * @param newConfig a List<ScreenInfo.Data> list of old Data's
     * @param newConfig a List<ScreenInfo.Data> list of new Data's
     */
    void screenSetChanged(List newConfig, List oldConfig);
}
