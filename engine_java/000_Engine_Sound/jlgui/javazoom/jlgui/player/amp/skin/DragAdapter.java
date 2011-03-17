/*
 * DragAdapter.
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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DragAdapter extends MouseAdapter implements MouseMotionListener
{
    private int mousePrevX = 0;
    private int mousePrevY = 0;
    private Component component = null;

    public DragAdapter(Component component)
    {
        super();
        this.component = component;
    }

    public void mousePressed(MouseEvent me)
    {
        super.mousePressed(me);
        mousePrevX = me.getX();
        mousePrevY = me.getY();
    }

    public void mouseDragged(MouseEvent me)
    {
        int mX = me.getX();
        int mY = me.getY();
        int cX = component.getX();
        int cY = component.getY();
        int moveX = mX - mousePrevX; // Negative if move left
        int moveY = mY - mousePrevY; // Negative if move down
        if (moveX == 0 && moveY == 0) return;
        mousePrevX = mX - moveX;
        mousePrevY = mY - moveY;
        int newX = cX + moveX;
        int newY = cY + moveY;
        component.setLocation(newX, newY);
    }

    public void mouseMoved(MouseEvent e)
    {
    }
}
