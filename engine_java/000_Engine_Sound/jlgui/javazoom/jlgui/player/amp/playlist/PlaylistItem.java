/*
 * PlaylistItem.
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
 *
 */
package javazoom.jlgui.player.amp.playlist;

import javazoom.jlgui.player.amp.tag.TagInfo;
import javazoom.jlgui.player.amp.tag.TagInfoFactory;
import javazoom.jlgui.player.amp.util.Config;
import javazoom.jlgui.player.amp.util.FileUtil;

/**
 * This class implements item for playlist.
 */
public class PlaylistItem
{
    protected String _name = null;
    protected String _displayName = null;
    protected String _location = null;
    protected boolean _isFile = true;
    protected long _seconds = -1;
    protected boolean _isSelected = false; // add by JOHN YANG
    protected TagInfo _taginfo = null;

    protected PlaylistItem()
    {
    }

    /**
     * Contructor for playlist item.
     *
     * @param name     Song name to be displayed
     * @param location File or URL
     * @param seconds  Time length
     * @param isFile   true for File instance
     */
    public PlaylistItem(String name, String location, long seconds, boolean isFile)
    {
        _name = name;
        _seconds = seconds;
        _isFile = isFile;
        Config config = Config.getInstance();
        if (config.getTaginfoPolicy().equals(Config.TAGINFO_POLICY_ALL))
        {
            // Read tag info for any File or URL. It could take time.
            setLocation(location, true);
        }
        else if (config.getTaginfoPolicy().equals(Config.TAGINFO_POLICY_FILE))
        {
            // Read tag info for any File only not for URL.
            if (_isFile) setLocation(location, true);
            else setLocation(location, false);
        }
        else
        {
            // Do not read tag info.
            setLocation(location, false);
        }
    }

    /**
     * Returns item name such as (hh:mm:ss) Title - Artist if available.
     *
     * @return
     */
    public String getFormattedName()
    {
        if (_displayName == null)
        {
            if (_seconds > 0)
            {
                String length = getFormattedLength();
                return "(" + length + ") " + _name;
            }
            else return _name;
        }
        // Name extracted from TagInfo or stream title.
        else return _displayName;
    }

    public String getName()
    {
        return _name;
    }

    public String getLocation()
    {
        return _location;
    }

    /**
     * Returns true if item to play is coming for a file.
     *
     * @return
     */
    public boolean isFile()
    {
        return _isFile;
    }

    /**
     * Set File flag for playslit item.
     *
     * @param b
     */
    public void setFile(boolean b)
    {
        _isFile = b;
    }

    /**
     * Returns playtime in seconds. If tag info is available then its playtime will be returned.
     *
     * @return playtime
     */
    public long getLength()
    {
        if ((_taginfo != null) && (_taginfo.getPlayTime() > 0)) return _taginfo.getPlayTime();
        else return _seconds;
    }

    public int getBitrate()
    {
        if (_taginfo != null) return _taginfo.getBitRate();
        else return -1;
    }

    public int getSamplerate()
    {
        if (_taginfo != null) return _taginfo.getSamplingRate();
        else return -1;
    }

    public int getChannels()
    {
        if (_taginfo != null) return _taginfo.getChannels();
        else return -1;
    }

    public void setSelected(boolean mode)
    {
        _isSelected = mode;
    }

    public boolean isSelected()
    {
        return _isSelected;
    }

    /**
     * Reads file comments/tags.
     *
     * @param l
     */
    public void setLocation(String l)
    {
        setLocation(l, false);
    }

    /**
     * Reads (or not) file comments/tags.
     *
     * @param l        input location
     * @param readInfo
     */
    public void setLocation(String l, boolean readInfo)
    {
        _location = l;
        if (readInfo == true)
        {
            // Read Audio Format and read tags/comments.
            if ((_location != null) && (!_location.equals("")))
            {
                TagInfoFactory factory = TagInfoFactory.getInstance();
                _taginfo = factory.getTagInfo(l);
            }
        }
        _displayName = getFormattedDisplayName();
    }

    /**
     * Returns item lenght such as hh:mm:ss
     *
     * @return formatted String.
     */
    public String getFormattedLength()
    {
        long time = getLength();
        String length = "";
        if (time > -1)
        {
            int minutes = (int) Math.floor(time / 60);
            int hours = (int) Math.floor(minutes / 60);
            minutes = minutes - hours * 60;
            int seconds = (int) (time - minutes * 60 - hours * 3600);
            // Hours.
            if (hours > 0)
            {
                length = length + FileUtil.rightPadString(hours + "", '0', 2) + ":";
            }
            length = length + FileUtil.rightPadString(minutes + "", '0', 2) + ":" + FileUtil.rightPadString(seconds + "", '0', 2);
        }
        else length = "" + time;
        return length;
    }

    /**
     * Returns item name such as (hh:mm:ss) Title - Artist
     *
     * @return formatted String.
     */
    public String getFormattedDisplayName()
    {
        if (_taginfo == null) return null;
        else
        {
            String length = getFormattedLength();
            if ((_taginfo.getTitle() != null) && (!_taginfo.getTitle().equals("")) && (_taginfo.getArtist() != null) && (!_taginfo.getArtist().equals("")))
            {
                if (getLength() > 0) return ("(" + length + ") " + _taginfo.getTitle() + " - " + _taginfo.getArtist());
                else return (_taginfo.getTitle() + " - " + _taginfo.getArtist());
            }
            else if ((_taginfo.getTitle() != null) && (!_taginfo.getTitle().equals("")))
            {
                if (getLength() > 0) return ("(" + length + ") " + _taginfo.getTitle());
                else return (_taginfo.getTitle());
            }
            else
            {
                if (getLength() > 0) return ("(" + length + ") " + _name);
                else return (_name);
            }
        }
    }

    public void setFormattedDisplayName(String fname)
    {
        _displayName = fname;
    }

    /**
     * Return item name such as hh:mm:ss,Title,Artist
     *
     * @return formatted String.
     */
    public String getM3UExtInf()
    {
        if (_taginfo == null)
        {
            return (_seconds + "," + _name);
        }
        else
        {
            if ((_taginfo.getTitle() != null) && (_taginfo.getArtist() != null))
            {
                return (getLength() + "," + _taginfo.getTitle() + " - " + _taginfo.getArtist());
            }
            else if (_taginfo.getTitle() != null)
            {
                return (getLength() + "," + _taginfo.getTitle());
            }
            else
            {
                return (_seconds + "," + _name);
            }
        }
    }

    /**
     * Return TagInfo.
     *
     * @return
     */
    public TagInfo getTagInfo()
    {
        if (_taginfo == null)
        {
            // Inspect location
            setLocation(_location, true);
        }
        return _taginfo;
    }
}
