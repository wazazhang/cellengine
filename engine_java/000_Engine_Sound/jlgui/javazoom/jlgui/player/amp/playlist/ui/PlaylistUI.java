/*
 * PlaylistUI.
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
package javazoom.jlgui.player.amp.playlist.ui;

import java.awt.Graphics;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.player.amp.PlayerActionEvent;
import javazoom.jlgui.player.amp.PlayerUI;
import javazoom.jlgui.player.amp.playlist.Playlist;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;
import javazoom.jlgui.player.amp.skin.AbsoluteLayout;
import javazoom.jlgui.player.amp.skin.ActiveJButton;
import javazoom.jlgui.player.amp.skin.DropTargetAdapter;
import javazoom.jlgui.player.amp.skin.Skin;
import javazoom.jlgui.player.amp.skin.UrlDialog;
import javazoom.jlgui.player.amp.tag.TagInfo;
import javazoom.jlgui.player.amp.tag.TagInfoFactory;
import javazoom.jlgui.player.amp.tag.ui.TagInfoDialog;
import javazoom.jlgui.player.amp.util.Config;
import javazoom.jlgui.player.amp.util.FileSelector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PlaylistUI extends JPanel implements ActionListener, ChangeListener
{
    private static Log log = LogFactory.getLog(PlaylistUI.class);
    public static int MAXDEPTH = 4;
    private Config config = null;
    private Skin ui = null;
    private Playlist playlist = null;
    private PlayerUI player = null;
    private int topIndex = 0;
    private int currentSelection = -1;
    private Vector exts = null;
    private boolean isSearching = false;
    private JPopupMenu fipopup = null;

    public PlaylistUI()
    {
        super();
        setDoubleBuffered(true);
        setLayout(new AbsoluteLayout());
        config = Config.getInstance();
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                handleMouseClick(e);
            }
        });
        // DnD support.
        DropTargetAdapter dnd = new DropTargetAdapter()
        {
            public void processDrop(Object data)
            {
                processDnD(data);
            }
        };
        DropTarget dt = new DropTarget(this, DnDConstants.ACTION_COPY, dnd, true);
    }

    public void setPlayer(PlayerUI mp)
    {
        player = mp;
    }

    public void setSkin(Skin skin)
    {
        ui = skin;
    }

    public Skin getSkin()
    {
        return ui;
    }

    public Playlist getPlaylist()
    {
        return playlist;
    }

    public void setPlaylist(Playlist playlist)
    {
        this.playlist = playlist;
    }

    public int getTopIndex()
    {
        return topIndex;
    }

    public void loadUI()
    {
        removeAll();
        ui.getPlaylistPanel().setParent(this);
        add(ui.getAcPlSlider(), ui.getAcPlSlider().getConstraints());
        ui.getAcPlSlider().setValue(100);
        ui.getAcPlSlider().removeChangeListener(this);
        ui.getAcPlSlider().addChangeListener(this);
        add(ui.getAcPlUp(), ui.getAcPlUp().getConstraints());
        ui.getAcPlUp().removeActionListener(this);
        ui.getAcPlUp().addActionListener(this);
        add(ui.getAcPlDown(), ui.getAcPlDown().getConstraints());
        ui.getAcPlDown().removeActionListener(this);
        ui.getAcPlDown().addActionListener(this);
        // Add menu
        add(ui.getAcPlAdd(), ui.getAcPlAdd().getConstraints());
        ui.getAcPlAdd().removeActionListener(this);
        ui.getAcPlAdd().addActionListener(this);
        add(ui.getAcPlAddPopup(), ui.getAcPlAddPopup().getConstraints());
        ui.getAcPlAddPopup().setVisible(false);
        ActiveJButton[] items = ui.getAcPlAddPopup().getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].addActionListener(this);
        }
        // Remove menu
        add(ui.getAcPlRemove(), ui.getAcPlRemove().getConstraints());
        ui.getAcPlRemove().removeActionListener(this);
        ui.getAcPlRemove().addActionListener(this);
        add(ui.getAcPlRemovePopup(), ui.getAcPlRemovePopup().getConstraints());
        ui.getAcPlRemovePopup().setVisible(false);
        items = ui.getAcPlRemovePopup().getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].removeActionListener(this);
            items[i].addActionListener(this);
        }
        // Select menu
        add(ui.getAcPlSelect(), ui.getAcPlSelect().getConstraints());
        ui.getAcPlSelect().removeActionListener(this);
        ui.getAcPlSelect().addActionListener(this);
        add(ui.getAcPlSelectPopup(), ui.getAcPlSelectPopup().getConstraints());
        ui.getAcPlSelectPopup().setVisible(false);
        items = ui.getAcPlSelectPopup().getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].removeActionListener(this);
            items[i].addActionListener(this);
        }
        // Misc menu
        add(ui.getAcPlMisc(), ui.getAcPlMisc().getConstraints());
        ui.getAcPlMisc().removeActionListener(this);
        ui.getAcPlMisc().addActionListener(this);
        add(ui.getAcPlMiscPopup(), ui.getAcPlMiscPopup().getConstraints());
        ui.getAcPlMiscPopup().setVisible(false);
        items = ui.getAcPlMiscPopup().getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].removeActionListener(this);
            items[i].addActionListener(this);
        }
        // List menu
        add(ui.getAcPlList(), ui.getAcPlList().getConstraints());
        ui.getAcPlList().removeActionListener(this);
        ui.getAcPlList().addActionListener(this);
        add(ui.getAcPlListPopup(), ui.getAcPlListPopup().getConstraints());
        ui.getAcPlListPopup().setVisible(false);
        items = ui.getAcPlListPopup().getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].removeActionListener(this);
            items[i].addActionListener(this);
        }
        // Popup menu
        fipopup = new JPopupMenu();
        JMenuItem mi = new JMenuItem(ui.getResource("playlist.popup.info"));
        mi.setActionCommand(PlayerActionEvent.ACPLINFO);
        mi.removeActionListener(this);
        mi.addActionListener(this);
        fipopup.add(mi);
        fipopup.addSeparator();
        mi = new JMenuItem(ui.getResource("playlist.popup.play"));
        mi.setActionCommand(PlayerActionEvent.ACPLPLAY);
        mi.removeActionListener(this);
        mi.addActionListener(this);
        fipopup.add(mi);
        fipopup.addSeparator();
        mi = new JMenuItem(ui.getResource("playlist.popup.remove"));
        mi.setActionCommand(PlayerActionEvent.ACPLREMOVE);
        mi.removeActionListener(this);
        mi.addActionListener(this);
        fipopup.add(mi);
        validate();
        repaint();
    }

    /**
     * Initialize playlist.
     */
    public void initPlayList()
    {
        topIndex = 0;
        nextCursor();
    }

    /**
     * Repaint the file list area and scroll it if necessary
     */
    public void nextCursor()
    {
        currentSelection = playlist.getSelectedIndex();
        int n = playlist.getPlaylistSize();
        int nlines = ui.getPlaylistPanel().getLines();
        while (currentSelection - topIndex > nlines - 1)
            topIndex += 2;
        if (topIndex >= n) topIndex = n - 1;
        while (currentSelection < topIndex)
            topIndex -= 2;
        if (topIndex < 0) topIndex = 0;
        resetScrollBar();
        repaint();
    }

    /**
     * Get the item index according to the mouse y position
     * @param y
     * @return
     */
    protected int getIndex(int y)
    {
        int n0 = playlist.getPlaylistSize();
        if (n0 == 0) return -1;
        for (int n = 0; n < 100; n++)
        {
            if (ui.getPlaylistPanel().isIndexArea(y, n))
            {
                if (topIndex + n > n0 - 1) return -1;
                return topIndex + n;
            }
        }
        return -1;
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e)
    {
        Object src = e.getSource();
        //log.debug("State (EDT=" + SwingUtilities.isEventDispatchThread() + ")");
        if (src == ui.getAcPlSlider())
        {
            int n = playlist.getPlaylistSize();
            float dx = (100 - ui.getAcPlSlider().getValue()) / 100.0f;
            int index = (int) (dx * (n - 1));
            if (index != topIndex)
            {
                topIndex = index;
                paintList();
            }
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        final ActionEvent evt = e;
        new Thread("PlaylistUIActionEvent")
        {
            public void run()
            {
                processActionEvent(evt);
            }
        }.start();
    }

    /**
     * Process action event.
     * @param e
     */
    public void processActionEvent(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        log.debug("Action=" + cmd + " (EDT=" + SwingUtilities.isEventDispatchThread() + ")");
        int n = playlist.getPlaylistSize();
        if (cmd.equals(PlayerActionEvent.ACPLUP))
        {
            topIndex--;
            if (topIndex < 0) topIndex = 0;
            resetScrollBar();
            paintList();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLDOWN))
        {
            topIndex++;
            if (topIndex > n - 1) topIndex = n - 1;
            resetScrollBar();
            paintList();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLADDPOPUP))
        {
            ui.getAcPlAdd().setVisible(false);
            ui.getAcPlAddPopup().setVisible(true);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVEPOPUP))
        {
            ui.getAcPlRemove().setVisible(false);
            ui.getAcPlRemovePopup().setVisible(true);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLSELPOPUP))
        {
            ui.getAcPlSelect().setVisible(false);
            ui.getAcPlSelectPopup().setVisible(true);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLMISCPOPUP))
        {
            ui.getAcPlMisc().setVisible(false);
            ui.getAcPlMiscPopup().setVisible(true);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLLISTPOPUP))
        {
            ui.getAcPlList().setVisible(false);
            ui.getAcPlListPopup().setVisible(true);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLINFO))
        {
            popupFileInfo();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLPLAY))
        {
            int n0 = playlist.getPlaylistSize();
            PlaylistItem pli = null;
            for (int i = n0 - 1; i >= 0; i--)
            {
                pli = playlist.getItemAt(i);
                if (pli.isSelected()) break;
            }
            // Play.
            if ((pli != null) && (pli.getTagInfo() != null))
            {
                player.pressStop();
                player.setCurrentSong(pli);
                playlist.setCursor(playlist.getIndex(pli));
                player.pressStart();
            }
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVE))
        {
            delSelectedItems();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLADDFILE))
        {
            ui.getAcPlAddPopup().setVisible(false);
            ui.getAcPlAdd().setVisible(true);
            File[] file = FileSelector.selectFile(player.getLoader(), FileSelector.OPEN, true, config.getExtensions(), ui.getResource("playlist.popup.add.file"), new File(config.getLastDir()));
            if (FileSelector.getInstance().getDirectory() != null) config.setLastDir(FileSelector.getInstance().getDirectory().getPath());
            addFiles(file);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLADDURL))
        {
            ui.getAcPlAddPopup().setVisible(false);
            ui.getAcPlAdd().setVisible(true);
            UrlDialog UD = new UrlDialog(config.getTopParent(), ui.getResource("playlist.popup.add.url"), player.getLoader().getLocation().x, player.getLoader().getLocation().y + player.getHeight(), null);
            UD.show();
            if (UD.getFile() != null)
            {
                PlaylistItem pli = new PlaylistItem(UD.getFile(), UD.getURL(), -1, false);
                playlist.appendItem(pli);
                resetScrollBar();
                repaint();
            }
        }
        else if (cmd.equals(PlayerActionEvent.ACPLADDDIR))
        {
            ui.getAcPlAddPopup().setVisible(false);
            ui.getAcPlAdd().setVisible(true);
            File[] file = FileSelector.selectFile(player.getLoader(), FileSelector.DIRECTORY, false, "", ui.getResource("playlist.popup.add.dir"), new File(config.getLastDir()));
            if (FileSelector.getInstance().getDirectory() != null) config.setLastDir(FileSelector.getInstance().getDirectory().getPath());
            if (file == null || !file[0].isDirectory()) return;
            // TODO - add message box for wrong filename
            addDir(file[0]);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVEALL))
        {
            ui.getAcPlRemovePopup().setVisible(false);
            ui.getAcPlRemove().setVisible(true);
            delAllItems();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVESEL))
        {
            ui.getAcPlRemovePopup().setVisible(false);
            ui.getAcPlRemove().setVisible(true);
            delSelectedItems();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVEMISC))
        {
            ui.getAcPlRemovePopup().setVisible(false);
            ui.getAcPlRemove().setVisible(true);
            // TODO
        }
        else if (cmd.equals(PlayerActionEvent.ACPLREMOVECROP))
        {
            ui.getAcPlRemovePopup().setVisible(false);
            ui.getAcPlRemove().setVisible(true);
            // TODO
        }
        else if (cmd.equals(PlayerActionEvent.ACPLSELALL))
        {
            ui.getAcPlSelectPopup().setVisible(false);
            ui.getAcPlSelect().setVisible(true);
            selFunctions(1);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLSELINV))
        {
            ui.getAcPlSelectPopup().setVisible(false);
            ui.getAcPlSelect().setVisible(true);
            selFunctions(-1);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLSELZERO))
        {
            ui.getAcPlSelectPopup().setVisible(false);
            ui.getAcPlSelect().setVisible(true);
            selFunctions(0);
        }
        else if (cmd.equals(PlayerActionEvent.ACPLMISCOPTS))
        {
            ui.getAcPlMiscPopup().setVisible(false);
            ui.getAcPlMisc().setVisible(true);
            // TODO
        }
        else if (cmd.equals(PlayerActionEvent.ACPLMISCFILE))
        {
            ui.getAcPlMiscPopup().setVisible(false);
            ui.getAcPlMisc().setVisible(true);
            popupFileInfo();
        }
        else if (cmd.equals(PlayerActionEvent.ACPLMISCSORT))
        {
            ui.getAcPlMiscPopup().setVisible(false);
            ui.getAcPlMisc().setVisible(true);
            // TODO
        }
        else if (cmd.equals(PlayerActionEvent.ACPLLISTLOAD))
        {
            ui.getAcPlListPopup().setVisible(false);
            ui.getAcPlList().setVisible(true);
            File[] file = FileSelector.selectFile(player.getLoader(), FileSelector.OPEN, true, config.getExtensions(), ui.getResource("playlist.popup.list.load"), new File(config.getLastDir()));
            if (FileSelector.getInstance().getDirectory() != null) config.setLastDir(FileSelector.getInstance().getDirectory().getPath());
            if ((file != null) && (file[0] != null))
            {
                String fsFile = file[0].getName();
                if ((fsFile.toLowerCase().endsWith(ui.getResource("playlist.extension.m3u"))) || (fsFile.toLowerCase().endsWith(ui.getResource("playlist.extension.pls"))))
                {
                    if (player.loadPlaylist(config.getLastDir() + fsFile))
                    {
                        config.setPlaylistFilename(config.getLastDir() + fsFile);
                        playlist.begin();
                        playlist.setCursor(-1);
                        // TODO
                        topIndex = 0;
                    }
                    resetScrollBar();
                    repaint();
                }
            }
        }
        else if (cmd.equals(PlayerActionEvent.ACPLLISTSAVE))
        {
            ui.getAcPlListPopup().setVisible(false);
            ui.getAcPlList().setVisible(true);
            // TODO
        }
        else if (cmd.equals(PlayerActionEvent.ACPLLISTNEW))
        {
            ui.getAcPlListPopup().setVisible(false);
            ui.getAcPlList().setVisible(true);
            // TODO
        }
    }

    /**
     * Display file info.
     */
    public void popupFileInfo()
    {
        int n0 = playlist.getPlaylistSize();
        PlaylistItem pli = null;
        for (int i = n0 - 1; i >= 0; i--)
        {
            pli = playlist.getItemAt(i);
            if (pli.isSelected()) break;
        }
        // Display Tag Info.
        if (pli != null)
        {
            TagInfo taginfo = pli.getTagInfo();
            TagInfoFactory factory = TagInfoFactory.getInstance();
            TagInfoDialog dialog = factory.getTagInfoDialog(taginfo);
            dialog.setLocation(player.getLoader().getLocation().x, player.getLoader().getLocation().y + player.getHeight());
            dialog.show();            
        }
    }

    /**
     * Selection operation in pledit window
     * @param mode -1 : inverse selected items, 0 : select none, 1 : select all 
     */
    private void selFunctions(int mode)
    {
        int n0 = playlist.getPlaylistSize();
        if (n0 == 0) return;
        for (int i = 0; i < n0; i++)
        {
            PlaylistItem pli = playlist.getItemAt(i);
            if (pli == null) break;
            if (mode == -1)
            { // inverse selection
                pli.setSelected(!pli.isSelected());
            }
            else if (mode == 0)
            { // select none
                pli.setSelected(false);
            }
            else if (mode == 1)
            { // select all
                pli.setSelected(true);
            }
        }
        repaint();
    }

    /**
     * Remove all items in playlist.
     */
    private void delAllItems()
    {
        int n0 = playlist.getPlaylistSize();
        if (n0 == 0) return;
        playlist.removeAllItems();
        topIndex = 0;
        ui.getAcPlSlider().setValue(100);
        repaint();
    }

    /**
     * Remove selected items in playlist.
     */
    private void delSelectedItems()
    {
        int n0 = playlist.getPlaylistSize();
        boolean brepaint = false;
        for (int i = n0 - 1; i >= 0; i--)
        {
            if (playlist.getItemAt(i).isSelected())
            {
                playlist.removeItemAt(i);
                brepaint = true;
            }
        }
        if (brepaint)
        {
            int n = playlist.getPlaylistSize();
            if (topIndex >= n) topIndex = n - 1;
            if (topIndex < 0) topIndex = 0;
            resetScrollBar();
            repaint();
        }
    }

    /**
     * Add file(s) to playlist.
     * @param file
     */
    public void addFiles(File[] file)
    {
        if (file != null)
        {
            for (int i = 0; i < file.length; i++)
            {
                String fsFile = file[i].getName();
                if ((!fsFile.toLowerCase().endsWith(ui.getResource("skin.extension"))) && (!fsFile.toLowerCase().endsWith(ui.getResource("playlist.extension.m3u"))) && (!fsFile.toLowerCase().endsWith(ui.getResource("playlist.extension.pls"))))
                {
                    PlaylistItem pli = new PlaylistItem(fsFile, file[i].getAbsolutePath(), -1, true);
                    playlist.appendItem(pli);
                    resetScrollBar();
                    repaint();
                }
            }
        }
    }

    /**
     * Handle mouse clicks on playlist.
     * @param evt
     */
    protected void handleMouseClick(MouseEvent evt)
    {
        int x = evt.getX();
        int y = evt.getY();
        ui.getAcPlAddPopup().setVisible(false);
        ui.getAcPlAdd().setVisible(true);
        ui.getAcPlRemovePopup().setVisible(false);
        ui.getAcPlRemove().setVisible(true);
        ui.getAcPlSelectPopup().setVisible(false);
        ui.getAcPlSelect().setVisible(true);
        ui.getAcPlMiscPopup().setVisible(false);
        ui.getAcPlMisc().setVisible(true);
        ui.getAcPlListPopup().setVisible(false);
        ui.getAcPlList().setVisible(true);
        // Check select action
        if (ui.getPlaylistPanel().isInSelectArea(x, y))
        {
            int index = getIndex(y);
            if (index != -1)
            {
                // PopUp
                if (javax.swing.SwingUtilities.isRightMouseButton(evt))
                {
                    if (fipopup != null) fipopup.show(this, x, y);
                }
                else
                {
                    PlaylistItem pli = playlist.getItemAt(index);
                    if (pli != null)
                    {
                        pli.setSelected(!pli.isSelected());
                        if ((evt.getClickCount() == 2) && (evt.getModifiers() == MouseEvent.BUTTON1_MASK))
                        {
                            player.pressStop();
                            player.setCurrentSong(pli);
                            playlist.setCursor(index);
                            player.pressStart();
                        }
                    }
                }
                repaint();
            }
        }
    }

    /**
     * Process Drag&Drop
     * @param data
     */
    public void processDnD(Object data)
    {
        log.debug("Playlist DnD");
        // Looking for files to drop.
        if (data instanceof List)
        {
            List al = (List) data;
            if ((al != null) && (al.size() > 0))
            {
                ArrayList fileList = new ArrayList();
                ArrayList folderList = new ArrayList();
                ListIterator li = al.listIterator();
                while (li.hasNext())
                {
                    File f = (File) li.next();
                    if ((f.exists()) && (f.canRead()))
                    {
                        if (f.isFile()) fileList.add(f);
                        else if (f.isDirectory()) folderList.add(f);
                    }
                }
                addFiles(fileList);
                addDirs(folderList);
            }
        }
        else if (data instanceof String)
        {
            String files = (String) data;
            if ((files.length() > 0))
            {
                ArrayList fileList = new ArrayList();
                ArrayList folderList = new ArrayList();
                StringTokenizer st = new StringTokenizer(files, System.getProperty("line.separator"));
                // Transfer files dropped.
                while (st.hasMoreTokens())
                {
                    String path = st.nextToken();
                    if (path.startsWith("file://"))
                    {
                        path = path.substring(7, path.length());
                        if (path.endsWith("\r")) path = path.substring(0, (path.length() - 1));
                    }
                    File f = new File(path);
                    if ((f.exists()) && (f.canRead()))
                    {
                        if (f.isFile()) fileList.add(f);
                        else if (f.isDirectory()) folderList.add(f);
                    }
                }
                addFiles(fileList);
                addDirs(folderList);
            }
        }
        else
        {
            log.info("Unknown dropped objects");
        }
    }

    /**
     * Add files to playlistUI.
     * @param fileList
     */
    public void addFiles(List fileList)
    {
        if (fileList.size() > 0)
        {
            File[] file = (File[]) fileList.toArray(new File[fileList.size()]);
            addFiles(file);
        }
    }

    /**
     * Add directories to playlistUI.
     * @param folderList
     */
    public void addDirs(List folderList)
    {
        if (folderList.size() > 0)
        {
            ListIterator it = folderList.listIterator();
            while (it.hasNext())
            {
                addDir((File) it.next());
            }
        }
    }

    /**
     * Compute slider value.
     */
    private void resetScrollBar()
    {
        int n = playlist.getPlaylistSize();
        float dx = (n < 1) ? 0 : ((float) topIndex / (n - 1)) * (100);
        ui.getAcPlSlider().setValue(100 - (int) dx);
    }

    public void paintList()
    {
        if (!isVisible()) return;
        else repaint();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g)
    {
        ui.getPlaylistPanel().paintBackground(g);
        ui.getPlaylistPanel().paintList(g);
    }

    /**
     * Add all files under this directory to play list.
     * @param fsFile
     */
    private void addDir(File fsFile)
    {
        // Put all music file extension in a Vector
        String ext = config.getExtensions();
        StringTokenizer st = new StringTokenizer(ext, ", ");
        if (exts == null)
        {
            exts = new Vector();
            while (st.hasMoreTokens())
            {
                exts.add("." + st.nextElement());
            }
        }
        // recursive
        Thread addThread = new AddThread(fsFile);
        addThread.start();
        // Refresh thread
        Thread refresh = new Thread("Refresh")
        {
            public void run()
            {
                while (isSearching)
                {
                    resetScrollBar();
                    repaint();
                    try
                    {
                        Thread.sleep(4000);
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }
        };
        refresh.start();
    }
    class AddThread extends Thread
    {
        private File fsFile;

        public AddThread(File fsFile)
        {
            super("Add");
            this.fsFile = fsFile;
        }

        public void run()
        {
            isSearching = true;
            addMusicRecursive(fsFile, 0);
            isSearching = false;
            resetScrollBar();
            repaint();
        }
    }

    private void addMusicRecursive(File rootDir, int depth)
    {
        // We do not want waste time
        if (rootDir == null || depth > MAXDEPTH) return;
        String[] list = rootDir.list();
        if (list == null) return;
        for (int i = 0; i < list.length; i++)
        {
            File ff = new File(rootDir, list[i]);
            if (ff.isDirectory()) addMusicRecursive(ff, depth + 1);
            else
            {
                if (isMusicFile(list[i]))
                {
                    PlaylistItem pli = new PlaylistItem(list[i], rootDir + File.separator + list[i], -1, true);
                    playlist.appendItem(pli);
                }
            }
        }
    }

    private boolean isMusicFile(String ff)
    {
        int sz = exts.size();
        for (int i = 0; i < sz; i++)
        {
            String ext = exts.elementAt(i).toString().toLowerCase();
            // TODO : Improve
            if (ext.equalsIgnoreCase(".wsz") || ext.equalsIgnoreCase(".m3u") || ext.equalsIgnoreCase(".pls")) continue;
            if (ff.toLowerCase().endsWith(exts.elementAt(i).toString().toLowerCase())) return true;
        }
        return false;
    }
}
