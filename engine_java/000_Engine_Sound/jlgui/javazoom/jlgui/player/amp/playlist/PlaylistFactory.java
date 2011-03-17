/*
 * PlaylistFactory.
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

import java.lang.reflect.Constructor;
import javazoom.jlgui.player.amp.util.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * PlaylistFactory.
 */
public class PlaylistFactory
{
    private static PlaylistFactory _instance = null;
    private Playlist _playlistInstance = null;
    private Config _config = null;
    private static Log log = LogFactory.getLog(PlaylistFactory.class);

    /**
     * Constructor.
     */
    private PlaylistFactory()
    {
        _config = Config.getInstance();
    }

    /**
     * Returns instance of PlaylistFactory.
     */
    public synchronized static PlaylistFactory getInstance()
    {
        if (_instance == null)
        {
            _instance = new PlaylistFactory();
        }
        return _instance;
    }

    /**
     * Returns Playlist instantied from full qualified class name.
     */
    public Playlist getPlaylist()
    {
        if (_playlistInstance == null)
        {
            String classname = _config.getPlaylistClassName();
            boolean interfaceFound = false;
            try
            {
                Class aClass = Class.forName(classname);
                Class superClass = aClass;
                // Looking for Playlist interface implementation.
                while (superClass != null)
                {
                    Class[] interfaces = superClass.getInterfaces();
                    for (int i = 0; i < interfaces.length; i++)
                    {
                        if ((interfaces[i].getName()).equals("javazoom.jlgui.player.amp.playlist.Playlist"))
                        {
                            interfaceFound = true;
                            break;
                        }
                    }
                    if (interfaceFound == true) break;
                    superClass = superClass.getSuperclass();
                }
                if (interfaceFound == false)
                {
                    log.error("Error : Playlist implementation not found in " + classname + " hierarchy");
                }
                else
                {
                    Class[] argsClass = new Class[] {};
                    Constructor c = aClass.getConstructor(argsClass);
                    _playlistInstance = (Playlist) (c.newInstance(null));
                    log.info(classname + " loaded");
                }
            }
            catch (Exception e)
            {
                log.error("Error : " + classname + " : " + e.getMessage());
            }
        }
        return _playlistInstance;
    }
}