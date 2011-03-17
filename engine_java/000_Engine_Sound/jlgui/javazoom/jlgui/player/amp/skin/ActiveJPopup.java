/*
 * ActiveJPopup.
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

import java.awt.GridLayout;
import javax.swing.JPanel;

public class ActiveJPopup extends JPanel
{
    private AbsoluteConstraints constraints = null;
    private ActiveJButton[] items = null;
    
    public ActiveJPopup()
    {
        super();
        setBorder(null);
        setDoubleBuffered(true);
    }

    public void setConstraints(AbsoluteConstraints cnts)
    {
        constraints = cnts;
    }

    public AbsoluteConstraints getConstraints()
    {
        return constraints;
    }

    public ActiveJButton[] getItems()
    {
        return items;
    }

    public void setItems(ActiveJButton[] items)
    {
        this.items = items;
        if (items != null)
        {
            setLayout(new GridLayout(items.length, 1, 0, 0));
            for (int i=0;i<items.length;i++)
            {
                add(items[i]);    
            }
            
        }
    }
    
    
}
