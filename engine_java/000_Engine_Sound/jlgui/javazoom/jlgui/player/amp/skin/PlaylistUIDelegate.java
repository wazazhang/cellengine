/*
 * PlaylistUIDelegate.
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;
import javazoom.jlgui.player.amp.playlist.ui.PlaylistUI;

public class PlaylistUIDelegate
{
    private AbsoluteConstraints constraints = null;
    private Image titleLeftImage = null;
    private Image titleRightImage = null;
    private Image titleCenterImage = null;
    private Image titleStretchImage = null;
    private Image leftImage = null;
    private Image rightImage = null;
    private Image bottomLeftImage = null;
    private Image bottomRightImage = null;
    private Image bottomStretchImage = null;
    private Color backgroundColor = null;
    private Color selectedBackgroundColor = null;
    private Color normalColor = null;
    private Color currentColor = null;
    private Font font = null;
    private int listarea[] = { 12, 24 - 4, 256, 78 };
    private PlaylistUI parent = null;

    public PlaylistUIDelegate()
    {
        super();
        currentColor = new Color(102, 204, 255);
        normalColor = new Color(0xb2, 0xe4, 0xf6);
        selectedBackgroundColor = Color.black;
        backgroundColor = Color.black;
        font = new Font("Dialog", Font.PLAIN, 10);
    }

    public void setParent(PlaylistUI playlist)
    {
        parent = playlist;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public Color getSelectedBackgroundColor()
    {
        return selectedBackgroundColor;
    }

    public Color getCurrentColor()
    {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor)
    {
        this.currentColor = currentColor;
    }

    public Color getNormalColor()
    {
        return normalColor;
    }

    public void setNormalColor(Color normalColor)
    {
        this.normalColor = normalColor;
    }

    public void setSelectedBackgroundColor(Color selectedColor)
    {
        this.selectedBackgroundColor = selectedColor;
    }

    public Image getBottomLeftImage()
    {
        return bottomLeftImage;
    }

    public void setBottomLeftImage(Image bottomLeftImage)
    {
        this.bottomLeftImage = bottomLeftImage;
    }

    public Image getBottomRightImage()
    {
        return bottomRightImage;
    }

    public void setBottomRightImage(Image bottomRightImage)
    {
        this.bottomRightImage = bottomRightImage;
    }

    public Image getBottomStretchImage()
    {
        return bottomStretchImage;
    }

    public void setBottomStretchImage(Image bottomStretchImage)
    {
        this.bottomStretchImage = bottomStretchImage;
    }

    public Image getLeftImage()
    {
        return leftImage;
    }

    public void setLeftImage(Image leftImage)
    {
        this.leftImage = leftImage;
    }

    public Image getRightImage()
    {
        return rightImage;
    }

    public void setRightImage(Image rightImage)
    {
        this.rightImage = rightImage;
    }

    public Image getTitleCenterImage()
    {
        return titleCenterImage;
    }

    public void setTitleCenterImage(Image titleCenterImage)
    {
        this.titleCenterImage = titleCenterImage;
    }

    public Image getTitleLeftImage()
    {
        return titleLeftImage;
    }

    public void setTitleLeftImage(Image titleLeftImage)
    {
        this.titleLeftImage = titleLeftImage;
    }

    public Image getTitleRightImage()
    {
        return titleRightImage;
    }

    public void setTitleRightImage(Image titleRightImage)
    {
        this.titleRightImage = titleRightImage;
    }

    public Image getTitleStretchImage()
    {
        return titleStretchImage;
    }

    public void setTitleStretchImage(Image titleStretchImage)
    {
        this.titleStretchImage = titleStretchImage;
    }

    public void setConstraints(AbsoluteConstraints cnts)
    {
        constraints = cnts;
    }

    public AbsoluteConstraints getConstraints()
    {
        return constraints;
    }

    public int getLines()
    {
        return ((listarea[3] - listarea[1]) / 12);
    }

    public boolean isInSelectArea(int x, int y)
    {
        return (x >= listarea[0] && x <= listarea[2] && y >= listarea[1] && y <= listarea[3]);
    }

    public boolean isIndexArea(int y, int n)
    {
        return (y >= listarea[1] + 12 - 10 + n * 12 && y < listarea[1] + 12 - 10 + n * 12 + 14);
    }

    public void paintBackground(Graphics g)
    {
        g.drawImage(titleLeftImage, 0, 0, null);
        g.drawImage(titleStretchImage, 25, 0, null);
        g.drawImage(titleStretchImage, 50, 0, null);
        g.drawImage(titleStretchImage, 62, 0, null);
        g.drawImage(titleCenterImage, 87, 0, null);
        g.drawImage(titleStretchImage, 187, 0, null);
        g.drawImage(titleStretchImage, 200, 0, null);
        g.drawImage(titleStretchImage, 225, 0, null);
        g.drawImage(titleRightImage, 250, 0, null);
        g.drawImage(leftImage, 0, 20, null);
        g.drawImage(leftImage, 0, 48, null);
        g.drawImage(leftImage, 0, 50, null);
        //g.drawImage(rightImage, parent.getWidth() - 20, 20, null);
        //g.drawImage(rightImage, parent.getWidth() - 20, 48, null);
        //g.drawImage(rightImage, parent.getWidth() - 20, 50, null);
        g.drawImage(bottomLeftImage, 0, parent.getHeight() - 38, null);
        g.drawImage(bottomRightImage, 125, parent.getHeight() - 38, null);
    }

    public void paintList(Graphics g)
    {
        g.setColor(backgroundColor);
        g.fillRect(listarea[0], listarea[1], listarea[2] - listarea[0], listarea[3] - listarea[1]);
        if (font != null) g.setFont(font);
        if (parent.getPlaylist() != null)
        {
            int currentSelection = parent.getPlaylist().getSelectedIndex();
            g.setColor(normalColor);
            int n = parent.getPlaylist().getPlaylistSize();
            for (int i = 0; i < n; i++)
            {
                if (i < parent.getTopIndex()) continue;
                int k = i - parent.getTopIndex();
                if (listarea[1] + 12 + k * 12 > listarea[3]) break;
                PlaylistItem pli = parent.getPlaylist().getItemAt(i);
                String name = pli.getFormattedName();
                if (pli.isSelected())
                {
                    g.setColor(selectedBackgroundColor);
                    g.fillRect(listarea[0] + 4, listarea[1] + 12 - 10 + k * 12, listarea[2] - listarea[0] - 4, 14);
                }
                if (i == currentSelection) g.setColor(currentColor);
                else g.setColor(normalColor);
                if (i + 1 >= 10) g.drawString((i + 1) + ".  " + name, listarea[0] + 12, listarea[1] + 12 + k * 12);
                else g.drawString("0" + (i + 1) + ".  " + name, listarea[0] + 12, listarea[1] + 12 + k * 12);
                if (i == currentSelection) g.setColor(normalColor);
            }
            //g.drawImage(rightImage, parent.getWidth() - 20, 20, null);
            //g.drawImage(rightImage, parent.getWidth() - 20, 48, null);
            //g.drawImage(rightImage, parent.getWidth() - 20, 50, null);
        }
    }
}
