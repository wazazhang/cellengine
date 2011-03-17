/*
 * TagSearch.
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
package javazoom.jlgui.player.amp.tag.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.player.amp.PlayerUI;
import javazoom.jlgui.player.amp.playlist.Playlist;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;
import javazoom.jlgui.player.amp.tag.TagInfo;

/**
 * This class allows to search and play for a particular track in the current playlist. 
 */
public class TagSearch extends JFrame
{
    private static String sep = System.getProperty("file.separator");
    private JTextField searchField;
    private JList list;
    private DefaultListModel m;
    private PlayerUI player;
    private Vector _playlist, restrictedPlaylist;
    private String lastSearch = null;
    private JScrollPane scroll;
    private ResourceBundle bundle;
    private JRadioButton all, artist, album, title;

    public TagSearch(PlayerUI ui)
    {
        super();
        player = ui;
        _playlist = null;
        restrictedPlaylist = null;
        bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/tag/ui/tag");
        initComponents();
    }

    public void display()
    {
        if (list.getModel().getSize() != 0)
        {
            setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(player.getParent(), bundle.getString("emptyPlaylistMsg"), bundle.getString("emptyPlaylistTitle"), JOptionPane.OK_OPTION);
        }
    }

    /**
     * Initialises the User Interface.
     */
    private void initComponents()
    {
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(bundle.getString("title"));
        this.setLocation(player.getX() + player.getWidth(), player.getY());
        JPanel main = new JPanel(new BorderLayout(0, 1));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setMinimumSize(new java.awt.Dimension(0, 0));
        main.setPreferredSize(new java.awt.Dimension(300, 400));
        JPanel searchPane = new JPanel(new GridLayout(4, 1, 10, 2));
        JLabel searchLabel = new JLabel(bundle.getString("searchLabel"));
        searchField = new JTextField();
        searchField.addKeyListener(new KeyboardListener());
        searchPane.add(searchLabel);
        searchPane.add(searchField);
        all = new JRadioButton(bundle.getString("radioAll"), true);
        artist = new JRadioButton(bundle.getString("radioArtist"), false);
        album = new JRadioButton(bundle.getString("radioAlbum"), false);
        title = new JRadioButton(bundle.getString("radioTitle"), false);
        all.addChangeListener(new RadioListener());
        ButtonGroup filters = new ButtonGroup();
        filters.add(all);
        filters.add(artist);
        filters.add(album);
        filters.add(title);
        JPanel topButtons = new JPanel(new GridLayout(1, 2));
        JPanel bottomButtons = new JPanel(new GridLayout(1, 2));
        topButtons.add(all);
        topButtons.add(artist);
        bottomButtons.add(album);
        bottomButtons.add(title);
        searchPane.add(topButtons);
        searchPane.add(bottomButtons);
        list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initList();
        list.addMouseListener(new ClickListener());
        list.addKeyListener(new KeyboardListener());
        scroll = new JScrollPane(list);
        main.add(searchPane, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);
        add(main);
        pack();
    }

    /**
     * Initialises the list so that it displays the details of all songs in the playlist.
     */
    private void initList()
    {
        Playlist playlist = player.getPlaylist();
        int c = player.getPlaylist().getPlaylistSize();
        _playlist = new Vector();
        for (int i = 0; i < c; i++)
        {
            _playlist.addElement(playlist.getItemAt(i));
        }
        restrictedPlaylist = _playlist;
        m = new DefaultListModel();
        for (int i = 0; i < _playlist.size(); i++)
        {
            PlaylistItem plItem = (PlaylistItem) _playlist.get(i);
            if (plItem.isFile()) m.addElement(getDisplayString(plItem));
        }
        list.setModel(m);
    }

    public String getDisplayString(PlaylistItem pi)
    {
        TagInfo song = pi.getTagInfo();
        String element;
        String location = pi.getLocation();
        location = location.substring(location.lastIndexOf(sep) + 1, location.lastIndexOf("."));
        if (song == null)
        {
            element = location;
        }
        else
        {
            if (song.getArtist() == null || song.getArtist().equals(""))
            {
                element = location;
            }
            else
            {
                element = song.getArtist().trim();
                if (song.getTitle() == null || song.getTitle().equals(""))
                {
                    element += " - " + location;
                }
                else
                {
                    element += " - " + song.getTitle().trim();
                }
            }
        }
        return element;
    }

    /**
     * Searches the playlist for a song containing the words in the given search string.
     * It searches on the title, artist, album and filename of each song in the playlist.
     *
     * @param searchString The string to search for in all songs in the playlist
     **/
    private void searchList(String searchString)
    {
        String[] s = searchString.split(" ");
        String lastS = "";
        if (s.length > 0) lastS = s[s.length - 1];
        if (lastS.equals(""))
        {
            list.setModel(m);
            restrictedPlaylist = _playlist;
        }
        else
        {
            DefaultListModel newModel = new DefaultListModel();
            if (lastSearch != null)
            {
                if (searchString.length() <= 1 || !searchString.substring(searchString.length() - 2).equals(lastSearch))
                {
                    list.setModel(m);
                    restrictedPlaylist = _playlist;
                }
            }
            Vector pI = restrictedPlaylist;
            restrictedPlaylist = new Vector();
            for (int a = 0; a < s.length; a++)
            {
                String currentS = s[a];
                int size = list.getModel().getSize();
                boolean[] remove = new boolean[size];
                for (int i = 0; i < size; i++)
                {
                    final int TITLE_SEARCH = 0;
                    final int ARTIST_SEARCH = 1;
                    final int ALBUM_SEARCH = 2;
                    final int FILENAME_SEARCH = 3;
                    TagInfo pli = ((PlaylistItem) pI.get(i)).getTagInfo();
                    remove[i] = false;
                    boolean found = false;
                    int searchType;
                    if (artist.isSelected())
                    {
                        searchType = ARTIST_SEARCH;
                    }
                    else if (album.isSelected())
                    {
                        searchType = ALBUM_SEARCH;
                    }
                    else if (title.isSelected())
                    {
                        searchType = TITLE_SEARCH;
                    }
                    else
                    {
                        searchType = -1;
                    }
                    for (int j = 0; j <= FILENAME_SEARCH; j++)
                    {
                        String listString = "";
                        if (pli == null)
                        {
                            if (searchType != -1)
                            {
                                break;
                            }
                            j = FILENAME_SEARCH;
                        }
                        else if (searchType != -1)
                        {
                            j = searchType;
                        }
                        switch (j)
                        {
                            case (TITLE_SEARCH):
                                if (pli.getTitle() != null) listString = pli.getTitle().toLowerCase();
                                break;
                            case (ARTIST_SEARCH):
                                if (pli.getArtist() != null) listString = pli.getArtist().toLowerCase();
                                break;
                            case (ALBUM_SEARCH):
                                if (pli.getAlbum() != null) listString = pli.getAlbum().toLowerCase();
                                break;
                            case (FILENAME_SEARCH):
                                String location = ((PlaylistItem) pI.get(i)).getLocation().toLowerCase();
                                listString = location.substring(location.lastIndexOf(sep) + 1, location.lastIndexOf("."));
                                break;
                        }
                        currentS = currentS.toLowerCase();
                        if (found = search(currentS, listString))
                        {
                            break;
                        }
                        if (searchType != -1)
                        {
                            break;
                        }
                    }
                    //if(found)foundAt[a] = i;
                    if (found && a == 0)
                    {
                        //todo new
                        newModel.addElement(getDisplayString((PlaylistItem) pI.get(i)));
                        restrictedPlaylist.add(pI.get(i));
                    }
                    if (!found && a != 0)
                    {
                        remove[i] = true;
                    }
                }
                //remove all unmatching items
                for (int x = size - 1; x >= 0; x--)
                {
                    if (remove[x])
                    {
                        newModel.remove(x);
                        restrictedPlaylist.remove(x);
                    }
                }
                pI = restrictedPlaylist;
                list.setModel(newModel);
            }
            list.setModel(newModel);
            lastSearch = searchField.getText();
        }
        if (list.getModel().getSize() > 0) list.setSelectedIndex(0);
    }

    /**
     * Searches to see if a particular string exists within another string
     *
     * @param pattern The string to search for
     * @param text The string in which to search for the pattern string
     * @return True if the pattern string exists in the text string
     */
    private boolean search(String pattern, String text)
    {
        int pStart = 0;
        int tStart = 0;
        char[] pChar = pattern.toCharArray();
        char[] tChar = text.toCharArray();
        while (pStart < pChar.length && tStart < tChar.length)
        {
            if (pChar[pStart] == tChar[tStart])
            {
                pStart++;
                tStart++;
            }
            else
            {
                pStart = 0;
                if (pChar[pStart] != tChar[tStart])
                {
                    tStart++;
                }
            }
        }
        return pStart == pChar.length;
    }

    /**
     * Calls the relavent methods in the player class to play a song.
     */
    private void playSong()
    {
        Playlist playlist = player.getPlaylist();
        player.pressStop();
        player.setCurrentSong((PlaylistItem) restrictedPlaylist.get(list.getSelectedIndex()));
        playlist.setCursor(playlist.getIndex((PlaylistItem) restrictedPlaylist.get(list.getSelectedIndex())));
        player.pressStart();
        dispose();
    }
    /**
     * Class to handle keyboard presses.
     */
    class KeyboardListener implements KeyListener
    {
        public void keyReleased(KeyEvent e)
        {
            if (e.getSource().equals(searchField))
            {
                if (e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP)
                {
                    searchList(searchField.getText()); // Search for current search string
                }
            }
        }

        public void keyTyped(KeyEvent e)
        {
            if (list.getSelectedIndex() != -1)
            {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    playSong();
                }
            }
        }

        public void keyPressed(KeyEvent e)
        {
            int index = list.getSelectedIndex();
            if (e.getKeyCode() == KeyEvent.VK_DOWN && index < list.getModel().getSize() - 1)
            {
                //list.setSelectedIndex(index+1);
                JScrollBar vBar = scroll.getVerticalScrollBar();
                vBar.setValue(vBar.getValue() + vBar.getUnitIncrement() * 5);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP && index >= 0)
            {
                JScrollBar vBar = scroll.getVerticalScrollBar();
                vBar.setValue(vBar.getValue() - vBar.getUnitIncrement() * 5);
                //list.setSelectedIndex(index-1);
            }
        }
    }
    /**
     * Class to play a song if one is double-clicked on on the search list.
     */
    class ClickListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2 && list.getSelectedIndex() != -1)
            {
                playSong();
            }
        }
    }
    class RadioListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            searchList(searchField.getText());
        }
    }
}
