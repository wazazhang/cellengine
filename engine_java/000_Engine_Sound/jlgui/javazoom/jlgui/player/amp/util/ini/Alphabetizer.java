/*
 * Alphabetizer.
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
 * This class alphabetizes strings.
 *
 * @author Matt "Spiked Bat" Segur
 */
public class Alphabetizer
{
    public static boolean lessThan(String str1, String str2)
    {
        return compare(str1, str2) < 0;
    }

    public static boolean greaterThan(String str1, String str2)
    {
        return compare(str1, str2) > 0;
    }

    public static boolean equalTo(String str1, String str2)
    {
        return compare(str1, str2) == 0;
    }

    /**
     * Performs a case-insensitive comparison of the two strings.
     */
    public static int compare(String s1, String s2)
    {
        if (s1 == null && s2 == null) return 0;
        else if (s1 == null) return -1;
        else if (s2 == null) return +1;
        int len1 = s1.length();
        int len2 = s2.length();
        int len = Math.min(len1, len2);
        for (int i = 0; i < len; i++)
        {
            int comparison = compare(s1.charAt(i), s2.charAt(i));
            if (comparison != 0) return comparison;
        }
        if (len1 < len2) return -1;
        else if (len1 > len2) return +1;
        else return 0;
    }

    /**
     * Performs a case-insensitive comparison of the two characters.
     */
    public static int compare(char c1, char c2)
    {
        if (65 <= c1 && c1 <= 91) c1 += 32;
        if (65 <= c2 && c2 <= 91) c2 += 32;
        if (c1 < c2) return -1;
        else if (c1 > c2) return +1;
        else return 0;
    }
}