/*
 * Taftb.
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

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import javax.swing.JComponent;

/**
 * Taftb is used to build gif image from graphical fonts.
 */
public class Taftb extends JComponent
{
    public Image theFonts;
    private int imageW;
    private int fontWidth;
    private int fontHeight;
    private int Yspacing;
    protected Image theBanner;
    protected int pixels[];
    private PixelGrabber pg;
    private String theText;

    /**
     * Text banner building according to the alphabet index, font size and Y spacing.
     */
    public Taftb(String alphaIndex, Image fontFile, int fontW, int fontH, int Yspc, String theTxt/*, Color BgValue*/)
    {
        fontWidth = fontW;
        fontHeight = fontH;
        Yspacing = Yspc;
        theText = theTxt;
        theFonts = fontFile;
        imageW = theFonts.getWidth(this);
        /*-- We create the TextBanner by grabbing font letters in the image fonts --*/
        pixels = new int[theText.length() * fontW * fontH];
        int SpacePosition = 0;
        int offsetSp = 0;
        /*-- We search the space position in the Alphabet index --*/
        while ((offsetSp < alphaIndex.length()) && (alphaIndex.charAt(offsetSp) != ' '))
        {
            offsetSp++;
        }
        if (offsetSp < alphaIndex.length()) SpacePosition = offsetSp;
        for (int offsetT = 0; offsetT < theText.length(); offsetT++)
        {
            int xPos = 0;
            int yPos = 0;
            int reste = 0;
            int entie = 0;
            int offsetA = 0;
            int FontPerLine = (int) Math.rint((imageW / fontW));
            /*-- We search the letter's position in the Alphabet index --*/
            while ((offsetA < alphaIndex.length()) && (theText.charAt(offsetT) != alphaIndex.charAt(offsetA)))
            {
                offsetA++;
            }
            /*-- We deduce its image's position (Int forced) --*/
            if (offsetA < alphaIndex.length())
            {
                reste = offsetA % FontPerLine;
                entie = (offsetA - reste);
                xPos = reste * fontW;
                yPos = ((entie / FontPerLine) * fontH) + ((entie / FontPerLine) * Yspacing);
            }
            else
            /*-- If the letter is not indexed the space (if available) is selected --*/
            {
                reste = SpacePosition % FontPerLine;
                entie = (SpacePosition - reste);
                xPos = reste * fontW;
                yPos = ((entie / FontPerLine) * fontH) + ((entie / FontPerLine) * Yspacing);
            }
            /*-- We grab the letter in the font image and put it in a pixel array --*/
            pg = new PixelGrabber(theFonts, xPos, yPos, fontW, fontH, pixels, offsetT * fontW, theText.length() * fontW);
            try
            {
                pg.grabPixels();
            }
            catch (InterruptedException e)
            {
            }
        }
        /*-- We create the final Image Banner throught an Image --*/
        theBanner = createImage(new MemoryImageSource(theText.length() * fontW, fontH, pixels, 0, theText.length() * fontW));
    }

    /**
     * Returns final banner as an image.
     */
    public Image getBanner()
    {
        return theBanner;
    }

    /**
     * Returns final banner as cropped image.
     */
    public Image getBanner(int x, int y, int sx, int sy)
    {
        Image cropBanner = null;
        CropImageFilter cif = new CropImageFilter(x, y, sx, sy);
        cropBanner = createImage(new FilteredImageSource(theBanner.getSource(), cif));
        return cropBanner;
    }

    /**
     * Returns final banner as a pixels array.
     */
    public int[] getPixels()
    {
        return pixels;
    }

    /**
     * Returns banner's length.
     */
    public int getPixelsW()
    {
        return theText.length() * fontWidth;
    }

    /**
     * Returns banner's height.
     */
    public int getPixelsH()
    {
        return fontHeight;
    }
}
