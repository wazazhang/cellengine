/*
 * BasePlaylist.
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
package javazoom.jlgui.player.amp.playlist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javazoom.jlgui.player.amp.util.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BasePlaylist implementation.
 * This class implements Playlist interface using a Vector.
 * It support .m3u and .pls playlist format.
 */
public class BasePlaylist implements Playlist
{
    protected Vector _playlist = null;
    protected int _cursorPos = -1;
    protected boolean isModified;
    protected String M3UHome = null;
    protected String PLSHome = null;
    private static Log log = LogFactory.getLog(BasePlaylist.class);

    /**
     * Constructor.
     */
    public BasePlaylist()
    {
        _playlist = new Vector();
    }

    public boolean isModified()
    {
        return isModified;
    }

    /**
     * Loads playlist as M3U format.
     */
    public boolean load(String filename)
    {
        setModified(true);
        boolean loaded = false;
        if ((filename != null) && (filename.toLowerCase().endsWith(".m3u")))
        {
            loaded = loadM3U(filename);
        }
        else if ((filename != null) && (filename.toLowerCase().endsWith(".pls")))
        {
            loaded = loadPLS(filename);
        }
        return loaded;
    }

    /**
     * Load playlist from M3U format.
     *
     * @param filename
     * @return
     */
    protected boolean loadM3U(String filename)
    {
        Config config = Config.getInstance();
        _playlist = new Vector();
        boolean loaded = false;
        BufferedReader br = null;
        try
        {
            // Playlist from URL ? (http:, ftp:, file: ....)
            if (Config.startWithProtocol(filename))
            {
                br = new BufferedReader(new InputStreamReader((new URL(filename)).openStream()));
            }
            else
            {
                br = new BufferedReader(new FileReader(filename));
            }
            String line = null;
            String songName = null;
            String songFile = null;
            String songLength = null;
            while ((line = br.readLine()) != null)
            {
                if (line.trim().length() == 0) continue;
                if (line.startsWith("#"))
                {
                    if (line.toUpperCase().startsWith("#EXTINF"))
                    {
                        int indA = line.indexOf(",", 0);
                        if (indA != -1)
                        {
                            songName = line.substring(indA + 1, line.length());
                        }
                        int indB = line.indexOf(":", 0);
                        if (indB != -1)
                        {
                            if (indB < indA) songLength = (line.substring(indB + 1, indA)).trim();
                        }
                    }
                }
                else
                {
                    songFile = line;
                    if (songName == null) songName = songFile;
                    if (songLength == null) songLength = "-1";
                    PlaylistItem pli = null;
                    if (Config.startWithProtocol(songFile))
                    {
                        // URL.
                        pli = new PlaylistItem(songName, songFile, Long.parseLong(songLength), false);
                    }
                    else
                    {
                        // File.
                        File f = new File(songFile);
                        if (f.exists())
                        {
                            pli = new PlaylistItem(songName, songFile, Long.parseLong(songLength), true);
                        }
                        else
                        {
                            // Try relative path.
                            f = new File(config.getLastDir() + songFile);
                            if (f.exists())
                            {
                                pli = new PlaylistItem(songName, config.getLastDir() + songFile, Long.parseLong(songLength), true);
                            }
                            else
                            {
                                // Try optional M3U home.
                                if (M3UHome != null)
                                {
                                    if (Config.startWithProtocol(M3UHome))
                                    {
                                        pli = new PlaylistItem(songName, M3UHome + songFile, Long.parseLong(songLength), false);
                                    }
                                    else
                                    {
                                        pli = new PlaylistItem(songName, M3UHome + songFile, Long.parseLong(songLength), true);
                                    }
                                }
                            }
                        }
                    }
                    if (pli != null) this.appendItem(pli);
                    songFile = null;
                    songName = null;
                    songLength = null;
                }
            }
            loaded = true;
        }
        catch (Exception e)
        {
            log.debug("Can't load .m3u playlist", e);
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (Exception ioe)
            {
                log.info("Can't close .m3u playlist", ioe);
            }
        }
        return loaded;
    }

    /**
     * Load playlist in PLS format.
     *
     * @param filename
     * @return
     */
    protected boolean loadPLS(String filename)
    {
        Config config = Config.getInstance();
        _playlist = new Vector();
        boolean loaded = false;
        BufferedReader br = null;
        try
        {
            // Playlist from URL ? (http:, ftp:, file: ....)
            if (Config.startWithProtocol(filename))
            {
                br = new BufferedReader(new InputStreamReader((new URL(filename)).openStream()));
            }
            else
            {
                br = new BufferedReader(new FileReader(filename));
            }
            String line = null;
            String songName = null;
            String songFile = null;
            String songLength = null;
            while ((line = br.readLine()) != null)
            {
                if (line.trim().length() == 0) continue;
                if ((line.toLowerCase().startsWith("file")))
                {
                    StringTokenizer st = new StringTokenizer(line, "=");
                    st.nextToken();
                    songFile = st.nextToken().trim();
                }
                else if ((line.toLowerCase().startsWith("title")))
                {
                    StringTokenizer st = new StringTokenizer(line, "=");
                    st.nextToken();
                    songName = st.nextToken().trim();
                }
                else if ((line.toLowerCase().startsWith("length")))
                {
                    StringTokenizer st = new StringTokenizer(line, "=");
                    st.nextToken();
                    songLength = st.nextToken().trim();
                }
                // New entry ?
                if (songFile != null)
                {
                    PlaylistItem pli = null;
                    if (songName == null) songName = songFile;
                    if (songLength == null) songLength = "-1";
                    if (Config.startWithProtocol(songFile))
                    {
                        // URL.
                        pli = new PlaylistItem(songName, songFile, Long.parseLong(songLength), false);
                    }
                    else
                    {
                        // File.
                        File f = new File(songFile);
                        if (f.exists())
                        {
                            pli = new PlaylistItem(songName, songFile, Long.parseLong(songLength), true);
                        }
                        else
                        {
                            // Try relative path.
                            f = new File(config.getLastDir() + songFile);
                            if (f.exists())
                            {
                                pli = new PlaylistItem(songName, config.getLastDir() + songFile, Long.parseLong(songLength), true);
                            }
                            else
                            {
                                // Try optional PLS home.
                                if (PLSHome != null)
                                {
                                    if (Config.startWithProtocol(PLSHome))
                                    {
                                        pli = new PlaylistItem(songName, PLSHome + songFile, Long.parseLong(songLength), false);
                                    }
                                    else
                                    {
                                        pli = new PlaylistItem(songName, PLSHome + songFile, Long.parseLong(songLength), true);
                                    }
                                }
                            }
                        }
                    }
                    if (pli != null) this.appendItem(pli);
                    songName = null;
                    songFile = null;
                    songLength = null;
                }
            }
            loaded = true;
        }
        catch (Exception e)
        {
            log.debug("Can't load .pls playlist", e);
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (Exception ioe)
            {
                log.info("Can't close .pls playlist", ioe);
            }
        }
        return loaded;
    }

    /**
     * Saves playlist in M3U format.
     */
    public boolean save(String filename)
    {
        // Implemented by C.K
        if (_playlist != null)
        {
            BufferedWriter bw = null;
            try
            {
                bw = new BufferedWriter(new FileWriter(filename));
                bw.write("#EXTM3U");
                bw.newLine();
                Iterator it = _playlist.iterator();
                while (it.hasNext())
                {
                    PlaylistItem pli = (PlaylistItem) it.next();
                    bw.write("#EXTINF:" + pli.getM3UExtInf());
                    bw.newLine();
                    bw.write(pli.getLocation());
                    bw.newLine();
                }
                return true;
            }
            catch (IOException e)
            {
                log.info("Can't save playlist", e);
            }
            finally
            {
                try
                {
                    if (bw != null)
                    {
                        bw.close();
                    }
                }
                catch (IOException ioe)
                {
                    log.info("Can't close playlist", ioe);
                }
            }
        }
        return false;
    }

    /**
     * Adds item at a given position in the playlist.
     */
    public void addItemAt(PlaylistItem pli, int pos)
    {
        _playlist.insertElementAt(pli, pos);
        setModified(true);
    }

    /**
     * Searchs and removes item from the playlist.
     */
    public void removeItem(PlaylistItem pli)
    {
        _playlist.remove(pli);
        setModified(true);
    }

    /**
     * Removes item at a given position from the playlist.
     */
    public void removeItemAt(int pos)
    {
        _playlist.removeElementAt(pos);
        setModified(true);
    }

    /**
     * Removes all items from the playlist.
     */
    public void removeAllItems()
    {
        _playlist.removeAllElements();
        _cursorPos = -1;
        setModified(true);
    }

    /**
     * Append item at the end of the playlist.
     */
    public void appendItem(PlaylistItem pli)
    {
        _playlist.addElement(pli);
        setModified(true);
    }

    /**
     * Sorts items of the playlist.
     */
    public void sortItems(int sortmode)
    {
        // TODO
    }

    /**
     * Shuffles items in the playlist randomly
     */
    public void shuffle()
    {
        int size = _playlist.size();
        if (size < 2) { return; }
        Vector v = _playlist;
        _playlist = new Vector(size);
        while ((size = v.size()) > 0)
        {
            _playlist.addElement(v.remove((int) (Math.random() * size)));
        }
        begin();
    }

    /**
     * Moves the cursor at the top of the playlist.
     */
    public void begin()
    {
        _cursorPos = -1;
        if (getPlaylistSize() > 0)
        {
            _cursorPos = 0;
        }
        setModified(true);
    }

    /**
     * Returns item at a given position from the playlist.
     */
    public PlaylistItem getItemAt(int pos)
    {
        PlaylistItem pli = null;
        pli = (PlaylistItem) _playlist.elementAt(pos);
        return pli;
    }

    /**
     * Returns a collection of playlist items.
     */
    public Collection getAllItems()
    {
        // TODO
        return null;
    }

    /**
     * Returns then number of items in the playlist.
     */
    public int getPlaylistSize()
    {
        return _playlist.size();
    }

    // Next methods will be used by the Player
    /**
     * Returns item matching to the cursor.
     */
    public PlaylistItem getCursor()
    {
        if ((_cursorPos < 0) || (_cursorPos >= _playlist.size())) { return null; }
        return getItemAt(_cursorPos);
    }

    /**
     * Computes cursor position (next).
     */
    public void nextCursor()
    {
        _cursorPos++;
    }

    /**
     * Computes cursor position (previous).
     */
    public void previousCursor()
    {
        _cursorPos--;
        if (_cursorPos < 0)
        {
            _cursorPos = 0;
        }
    }

    public boolean setModified(boolean set)
    {
        isModified = set;
        return isModified;
    }

    public void setCursor(int index)
    {
        _cursorPos = index;
    }

    /**
     * Returns selected index.
     */
    public int getSelectedIndex()
    {
        return _cursorPos;
    }

    /**
     * Returns index of playlist item.
     */
    public int getIndex(PlaylistItem pli)
    {
        int pos = -1;
        for (int i = 0; i < _playlist.size(); i++)
        {
            pos = i;
            PlaylistItem p = (PlaylistItem) _playlist.elementAt(i);
            if (p.equals(pli)) break;
        }
        return pos;
    }

    /**
     * Get M3U home for relative playlist.
     *
     * @return
     */
    public String getM3UHome()
    {
        return M3UHome;
    }

    /**
     * Set optional M3U home for relative playlist.
     *
     * @param string
     */
    public void setM3UHome(String string)
    {
        M3UHome = string;
    }

    /**
     * Get PLS home for relative playlist.
     *
     * @return
     */
    public String getPLSHome()
    {
        return PLSHome;
    }

    /**
     * Set optional PLS home for relative playlist.
     *
     * @param string
     */
    public void setPLSHome(String string)
    {
        PLSHome = string;
    }
}