/*
 * PreferenceItem.
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
package javazoom.jlgui.player.amp.util.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javazoom.jlgui.player.amp.PlayerUI;

public abstract class PreferenceItem extends JPanel
{
    protected PlayerUI player = null;
    protected ResourceBundle bundle = null;
    protected boolean loaded = false;
    protected JFrame parent = null; 

    /**
     * Return I18N value of a given key.
     * @param key
     * @return
     */
    public String getResource(String key)
    {
        String value = null;
        if (key != null)
        {
            try
            {
                value = bundle.getString(key);
            }
            catch (MissingResourceException e)
            {
            }
        }
        return value;
    }

    
    public void setPlayer(PlayerUI player)
    {
        this.player = player;
    }

    public JFrame getParentFrame()
    {
        return parent;
    }


    public void setParentFrame(JFrame parent)
    {
        this.parent = parent;
    }


    public abstract void loadUI();
}
