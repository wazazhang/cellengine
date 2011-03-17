/*
 * ActiveJLabel.
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

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ActiveJLabel extends JLabel
{
    private AbsoluteConstraints constraints = null;
    private ActiveFont acFont = null;
    private Rectangle cropRectangle = null;
    private String acText = null;

    public ActiveJLabel()
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

    public ActiveFont getAcFont()
    {
        return acFont;
    }

    public void setAcFont(ActiveFont acFont)
    {
        this.acFont = acFont;
    }

    public Rectangle getCropRectangle()
    {
        return cropRectangle;
    }

    public void setCropRectangle(Rectangle cropRectangle)
    {
        this.cropRectangle = cropRectangle;
    }

    public String getAcText()
    {
        return acText;
    }

    public void setAcText(String txt)
    {
        acText = txt;
        
        acText = acText.replace('È','E');
        acText = acText.replace('É','E');
        acText = acText.replace('Ê','E');
        acText = acText.replace('À','A');
        acText = acText.replace('Ä','A');
        acText = acText.replace('Ç','C');
        acText = acText.replace('Ù','U');
        acText = acText.replace('Ü','U');
        acText = acText.replace('Ï','I');        
        if (acFont != null)
        {
            Taftb parser = new Taftb(acFont.getIndex(), acFont.getImage(), acFont.getWidth(), acFont.getHeight(), 0, acText);
            if (cropRectangle != null)
            {
                setIcon(new ImageIcon(parser.getBanner(cropRectangle.x, cropRectangle.y, cropRectangle.width, cropRectangle.height)));
            }
            else
            {
                setIcon(new ImageIcon(parser.getBanner()));
            }
        }
    }
}
