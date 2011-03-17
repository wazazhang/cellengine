/*
 * ActiveSliderUI.
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class ActiveSliderUI extends BasicSliderUI
{
    private Image thumbImage = null;
    private Image thumbPressedImage = null;
    private Image[] backgroundImages = null;
    private JSlider parentSlider = null;
    private Dimension thumbDim = null;
    private int newThumbHeight = -1;
    private int thumbXOffset = 0;
    private int thumbYOffset = 0;
    private boolean hideThumb = false;

    public ActiveSliderUI(JSlider slider)
    {
        super(slider);
        parentSlider = slider;
    }

    public void setThumbImage(Image img)
    {
        thumbImage = img;
        thumbDim = new Dimension(thumbImage.getWidth(null), thumbImage.getHeight(null));
    }

    public void setThumbPressedImage(Image img)
    {
        thumbPressedImage = img;
    }

    protected Dimension getThumbSize()
    {
        return thumbDim;
    }

    public void forceThumbHeight(int h)
    {
        newThumbHeight = h;
    }

    public void setThumbXOffset(int x)
    {
        thumbXOffset = x;
    }

    public void setThumbYOffset(int y)
    {
        thumbYOffset = y;
    }
    
    public void setHideThumb(boolean hide)
    {
        hideThumb = hide;
    }

    public void setBackgroundImages(Image[] img)
    {
        backgroundImages = img;
    }

    public void paintFocus(Graphics g)
    {
    }

    public void paintThumb(Graphics g)
    {
        if (hideThumb == true) return;
        Image img = thumbImage; 
        if (img != null)
        {
            if (thumbPressedImage != null)
            {
                if (parentSlider.getValueIsAdjusting())
                {
                    img = thumbPressedImage;                  
                }
            }
            if (newThumbHeight >= 0)
            {
                if (parentSlider.getOrientation() == JSlider.HORIZONTAL)
                {
                    g.drawImage(img, thumbRect.x + thumbXOffset, thumbYOffset, img.getWidth(null), newThumbHeight, null);
                }
                else
                {
                    g.drawImage(img, thumbXOffset, thumbRect.y + thumbYOffset, img.getWidth(null), newThumbHeight, null);
                }
            }
            else
            {
                if (parentSlider.getOrientation() == JSlider.HORIZONTAL)
                {
                    g.drawImage(img, thumbRect.x + thumbXOffset, thumbYOffset, img.getWidth(null), img.getHeight(null), null);
                }
                else
                {
                    g.drawImage(img, thumbXOffset, thumbRect.y + thumbYOffset, img.getWidth(null), img.getHeight(null), null);
                }
            }
        }
    }

    public void paintTrack(Graphics g)
    {
        if (backgroundImages != null)
        {
            int id = (int) Math.round(((double) Math.abs(parentSlider.getValue()) / (double) parentSlider.getMaximum()) * (backgroundImages.length - 1));
            g.drawImage(backgroundImages[id], 0, 0, backgroundImages[id].getWidth(null), backgroundImages[id].getHeight(null), null);
        }
    }

    public void setThumbLocation(int x, int y)
    {
        super.setThumbLocation(x, y);
        parentSlider.repaint();
    }    
}
