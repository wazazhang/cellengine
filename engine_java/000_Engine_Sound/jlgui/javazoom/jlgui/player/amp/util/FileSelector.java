/*
 * FileSelector.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net 
 *  
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package javazoom.jlgui.player.amp.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javazoom.jlgui.player.amp.Loader;

/**
 * This class is used to select a file or directory for loading or saving.
 */
public class FileSelector
{
    public static final int OPEN = 1;
    public static final int SAVE = 2;
    public static final int SAVE_AS = 3;
    public static final int DIRECTORY = 4;
    private File[] files = null;
    private File directory = null;
    private static FileSelector instance = null;

    public File[] getFiles()
    {
        return files;
    }

    public File getDirectory()
    {
        return directory;
    }

    public static final FileSelector getInstance()
    {
        if (instance == null) instance = new FileSelector();
        return instance;
    }

    /**
     * Opens a dialog box so that the user can search for a file
     * with the given extension and returns the filename selected.
     *
     * @param extensions the extension of the filename to be selected,
     *                   or "" if any filename can be used
     * @param directory  the folder to be put in the starting directory
     * @param mode       the action that will be performed on the file, used to tell what
     *                   files are valid
     * @return the selected file
     */
    public static File[] selectFile(Loader loader, int mode, boolean multiple, String extensions, String description, File directory)
    {
        return selectFile(loader, mode, multiple, null, extensions, description, null, directory);
    }

    /**
     * Opens a dialog box so that the user can search for a file
     * with the given extension and returns the filename selected.
     *
     * @param extensions  the extension of the filename to be selected,
     *                    or "" if any filename can be used
     * @param titlePrefix the string to be put in the title, followed by : SaveAs
     * @param mode        the action that will be performed on the file, used to tell what
     *                    files are valid
     * @param defaultFile the default file
     * @param directory   the string to be put in the starting directory
     * @return the selected filename
     */
    public static File[] selectFile(Loader loader, int mode, boolean multiple, File defaultFile, String extensions, String description, String titlePrefix, File directory)
    {
        JFrame mainWindow = null;
        if (loader instanceof JFrame)
        {
            mainWindow = (JFrame) loader;
        }
        JFileChooser filePanel = new JFileChooser();
        StringBuffer windowTitle = new StringBuffer();
        if (titlePrefix != null && titlePrefix.length() > 0) windowTitle.append(titlePrefix).append(": ");
        switch (mode)
        {
            case OPEN:
                windowTitle.append("Open");
                break;
            case SAVE:
                windowTitle.append("Save");
                break;
            case SAVE_AS:
                windowTitle.append("Save As");
                break;
            case DIRECTORY:
                windowTitle.append("Choose Directory");
                break;
        }
        filePanel.setDialogTitle(windowTitle.toString());
        FileNameFilter filter = new FileNameFilter(extensions, description);
        filePanel.setFileFilter(filter);
        if (defaultFile != null) filePanel.setSelectedFile(defaultFile);
        if (directory != null) filePanel.setCurrentDirectory(directory);
        filePanel.setMultiSelectionEnabled(multiple);
        int retVal = -1;
        switch (mode)
        {
            case OPEN:
                filePanel.setDialogType(JFileChooser.OPEN_DIALOG);
                retVal = filePanel.showOpenDialog(mainWindow);
                break;
            case SAVE:
                filePanel.setDialogType(JFileChooser.SAVE_DIALOG);
                retVal = filePanel.showSaveDialog(mainWindow);
                break;
            case SAVE_AS:
                filePanel.setDialogType(JFileChooser.SAVE_DIALOG);
                retVal = filePanel.showSaveDialog(mainWindow);
                break;
            case DIRECTORY:
                filePanel.setDialogType(JFileChooser.SAVE_DIALOG);
                filePanel.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                retVal = filePanel.showDialog(mainWindow, "Select");
                break;
        }
        if (retVal == JFileChooser.APPROVE_OPTION)
        {
            if (multiple) getInstance().files = filePanel.getSelectedFiles();
            else
            {
                getInstance().files = new File[1];
                getInstance().files[0] = filePanel.getSelectedFile();
            }
            getInstance().directory = filePanel.getCurrentDirectory();
        }
        else
        {
            getInstance().files = null;
        }
        return getInstance().files;
    }
}
