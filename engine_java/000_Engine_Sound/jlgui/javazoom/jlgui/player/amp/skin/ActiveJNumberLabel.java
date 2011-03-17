/*
 * ActiveJNumberLabel.
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

public class ActiveJNumberLabel extends ActiveJLabel
{
    private ImageIcon[] numbers = null;

    public ActiveJNumberLabel()
    {
        super();
    }

    public ImageIcon[] getNumbers()
    {
        return numbers;
    }

    public void setNumbers(ImageIcon[] numbers)
    {
        this.numbers = numbers;
    }

    public void setAcText(String numberStr)
    {
        int number = 10;
        try
        {
            number = Integer.parseInt(numberStr);
        }
        catch (NumberFormatException e)
        {
        }
        if ((number >= 0) && (number < numbers.length))
        {
            setIcon(numbers[number]);
        }
    }
}
