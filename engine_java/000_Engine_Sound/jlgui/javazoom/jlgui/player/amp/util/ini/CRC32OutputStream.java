/*
 * CRC32OutputStream.
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
package javazoom.jlgui.player.amp.util.ini;

import java.io.OutputStream;
import java.util.zip.CRC32;

/**
 * @author Jeremy Cloud
 * @version 1.0.0
 */
public class CRC32OutputStream extends OutputStream
{
    private CRC32 crc;

    public CRC32OutputStream()
    {
        crc = new CRC32();
    }

    public void write(int new_byte)
    {
        crc.update(new_byte);
    }

    public long getValue()
    {
        return crc.getValue();
    }
}
