/*
 * Array.
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

/**
 * This class represents an array of objects.
 *
 * @author Jeremy Cloud
 * @version 1.0.0
 */
public class Array
{
    public static Object[] copy(Object[] sors, Object[] dest)
    {
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    public static String[] doubleArray(String[] sors)
    {
        System.out.print("** doubling string array...  ");
        int new_size = (sors.length <= 8 ? 16 : sors.length << 1);
        String[] dest = new String[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        System.out.println("done **.");
        return dest;
    }

    public static int[] doubleArray(int[] sors)
    {
        int new_size = (sors.length < 8 ? 16 : sors.length << 1);
        int[] dest = new int[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    public static int[] grow(int[] sors, double growth_rate)
    {
        int new_size = Math.max((int) (sors.length * growth_rate), sors.length + 1);
        int[] dest = new int[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    public static boolean[] grow(boolean[] sors, double growth_rate)
    {
        int new_size = Math.max((int) (sors.length * growth_rate), sors.length + 1);
        boolean[] dest = new boolean[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    public static Object[] grow(Object[] sors, double growth_rate)
    {
        int new_size = Math.max((int) (sors.length * growth_rate), sors.length + 1);
        Object[] dest = new Object[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    public static String[] grow(String[] sors, double growth_rate)
    {
        int new_size = Math.max((int) (sors.length * growth_rate), sors.length + 1);
        String[] dest = new String[new_size];
        System.arraycopy(sors, 0, dest, 0, sors.length);
        return dest;
    }

    /**
     * @param start - inclusive
     * @param end   - exclusive
     */
    public static void shiftUp(Object[] array, int start, int end)
    {
        int count = end - start;
        if (count > 0) System.arraycopy(array, start, array, start + 1, count);
    }

    /**
     * @param start - inclusive
     * @param end   - exclusive
     */
    public static void shiftDown(Object[] array, int start, int end)
    {
        int count = end - start;
        if (count > 0) System.arraycopy(array, start, array, start - 1, count);
    }

    public static void shift(Object[] array, int start, int amount)
    {
        int count = array.length - start - (amount > 0 ? amount : 0);
        System.arraycopy(array, start, array, start + amount, count);
    }
}
