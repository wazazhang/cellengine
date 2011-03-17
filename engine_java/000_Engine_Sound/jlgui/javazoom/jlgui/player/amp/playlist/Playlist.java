/*
 * Playlist.
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

import java.util.Collection;

/**
 * Playlist.
 * This interface defines method that a playlist should implement.<br>
 * A playlist provides a collection of item to play and a cursor to know
 * which item is playing.
 */
public interface Playlist
{
    // Next methods will be called by the Playlist UI.
    /**
     * Loads playlist.
     */
    public boolean load(String filename);

    /**
     * Saves playlist.
     */
    public boolean save(String filename);

    /**
     * Adds item at a given position in the playlist.
     */
    public void addItemAt(PlaylistItem pli, int pos);

    /**
     * Searchs and removes item from the playlist.
     */
    public void removeItem(PlaylistItem pli);

    /**
     * Removes item at a given position from the playlist.
     */
    public void removeItemAt(int pos);

    /**
     * Removes all items in the playlist.
     */
    public void removeAllItems();

    /**
     * Append item at the end of the playlist.
     */
    public void appendItem(PlaylistItem pli);

    /**
     * Sorts items of the playlist.
     */
    public void sortItems(int sortmode);

    /**
     * Returns item at a given position from the playlist.
     */
    public PlaylistItem getItemAt(int pos);

    /**
     * Returns a collection of playlist items.
     */
    public Collection getAllItems();

    /**
     * Returns then number of items in the playlist.
     */
    public int getPlaylistSize();

    // Next methods will be used by the Player
    /**
     * Randomly re-arranges the playlist.
     */
    public void shuffle();

    /**
     * Returns item matching to the cursor.
     */
    public PlaylistItem getCursor();

    /**
     * Moves the cursor at the begining of the Playlist.
     */
    public void begin();

    /**
     * Returns item matching to the cursor.
     */
    public int getSelectedIndex();

    /**
     * Returns index of playlist item.
     */
    public int getIndex(PlaylistItem pli);

    /**
     * Computes cursor position (next).
     */
    public void nextCursor();

    /**
     * Computes cursor position (previous).
     */
    public void previousCursor();

    /**
     * Set the modification flag for the playlist
     */
    boolean setModified(boolean set);

    /**
     * Checks the modification flag
     */
    public boolean isModified();

    void setCursor(int index);
}
