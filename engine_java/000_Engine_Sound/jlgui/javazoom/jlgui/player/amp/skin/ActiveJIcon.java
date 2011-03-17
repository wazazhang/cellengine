/*
 * ActiveJIcon.
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ActiveJIcon extends JLabel
{
    private AbsoluteConstraints constraints = null;
    private ImageIcon[] icons = null;

    public ActiveJIcon()
    {
        super();
        this.setBorder(null);
        this.setDoubleBuffered(true);
    }

    public void setIcons(ImageIcon[] icons)
    {
        this.icons = icons;
    }

    public void setIcon(int id)
    {
        if ((id >= 0) && (id < icons.length))
        {
            setIcon(icons[id]);
        }
    }

    public void setConstraints(AbsoluteConstraints cnts)
    {
        constraints = cnts;
    }

    public AbsoluteConstraints getConstraints()
    {
        return constraints;
    }
}
