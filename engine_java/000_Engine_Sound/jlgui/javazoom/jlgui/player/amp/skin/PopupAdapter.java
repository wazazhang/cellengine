/*
 * PopupAdapter.
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
package javazoom.jlgui.player.amp.skin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

public class PopupAdapter extends MouseAdapter
{
    private JPopupMenu popup = null;
    
    public PopupAdapter(JPopupMenu popup)
    {
        super();
        this.popup=popup;
    }

    public void mousePressed(MouseEvent e)
    {
        checkPopup(e);
    }

    public void mouseClicked(MouseEvent e)
    {
        checkPopup(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        checkPopup(e);
    }

    private void checkPopup(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
