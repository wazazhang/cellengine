/*
 * SortedStrings.
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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * An object that represents an array of alpabetized Strings.  Implemented
 * with an String array that grows as appropriate.
 */
public class SortedStrings extends Alphabetizer implements Cloneable
{
    public static final int DEFAULT_SIZE = 32;
    private String[] strings;
    private int string_count;
    private double growth_rate = 2.0;

    /**
     * Constructor creates a new SortedStrings object of default
     * size.
     */
    public SortedStrings()
    {
        clear();
    }

    /**
     * Constructor creates a new SortedStrings object of size passed.
     */
    public SortedStrings(int initial_size)
    {
        clear(initial_size);
    }

    /**
     * Contructor creates a new SortedStrings object using a DataInput
     * object.  The first int in the DataInput object is assumed to be
     * the size wanted for the SortedStrings object.
     */
    public SortedStrings(DataInput in) throws IOException
    {
        int count = string_count = in.readInt();
        String[] arr = strings = new String[count];
        for (int i = 0; i < count; i++)
            arr[i] = in.readUTF();
    }

    /**
     * Contructor creates a new SortedStrings object, initializing it
     * with the String[] passed.
     */
    public SortedStrings(String[] array)
    {
        this(array.length);
        int new_size = array.length;
        for (int i = 0; i < new_size; i++)
            add(array[i]);
    }

    /**
     * Clones the SortedStrings object.
     */
    public Object clone()
    {
        try
        {
            SortedStrings clone = (SortedStrings) super.clone();
            clone.strings = (String[]) strings.clone();
            return clone;
        }
        catch (CloneNotSupportedException e)
        {
            return null;
        }
    }

    /**
     * Writes a the SortedStrings object to the DataOutput object.
     */
    public void emit(DataOutput out) throws IOException
    {
        int count = string_count;
        String[] arr = strings;
        out.writeInt(count);
        for (int i = 0; i < count; i++)
            out.writeUTF(arr[i]);
    }

    /**
     * Merge two sorted lists of integers.  The time complexity of
     * the merge is O(n).
     */
    public SortedStrings merge(SortedStrings that)
    {
        int count1 = this.string_count;
        int count2 = that.string_count;
        String[] ints1 = this.strings;
        String[] ints2 = that.strings;
        String num1, num2;
        int i1 = 0, i2 = 0;
        SortedStrings res = new SortedStrings(count1 + count2);
        while (i1 < count1 && i2 < count2)
        {
            num1 = ints1[i1];
            num2 = ints2[i2];
            if (compare(num1, num2) < 0)
            {
                res.add(num1);
                i1++;
            }
            else if (compare(num2, num1) < 0)
            {
                res.add(num2);
                i2++;
            }
            else
            {
                res.add(num1);
                i1++;
                i2++;
            }
        }
        if (i1 < count1)
        {
            for (; i1 < count1; i1++)
                res.add(ints1[i1]);
        }
        else for (; i2 < count2; i2++)
            res.add(ints2[i2]);
        return res;
    }

    /**
     * Returns a SortedStrings object that has the Strings
     * from this object that are not in the one passed.
     */
    public SortedStrings diff(SortedStrings that)
    {
        int count1 = this.string_count;
        int count2 = that.string_count;
        String[] ints1 = this.strings;
        String[] ints2 = that.strings;
        String num1, num2;
        int i1 = 0, i2 = 0;
        SortedStrings res = new SortedStrings(count1);
        while (i1 < count1 && i2 < count2)
        {
            num1 = ints1[i1];
            num2 = ints2[i2];
            if (compare(num1, num2) < 0)
            {
                res.add(num1);
                i1++;
            }
            else if (compare(num2, num1) < 0) i2++;
            else
            {
                i1++;
                i2++;
            }
        }
        if (i1 < count1)
        {
            for (; i1 < count1; i1++)
                res.add(ints1[i1]);
        }
        return res;
    }

    /**
     * Clears the Strings from the object and creates a new one
     * of the default size.
     */
    public void clear()
    {
        clear(DEFAULT_SIZE);
    }

    /**
     * Clears the Strings from the object and creates a new one
     * of the size passed.
     */
    public void clear(int initial_size)
    {
        strings = new String[initial_size];
        string_count = 0;
    }

    /**
     * Adds the String passed to the array in its proper place -- sorted.
     */
    public void add(String num)
    {
        if (string_count == 0 || greaterThan(num, strings[string_count - 1]))
        {
            if (string_count == strings.length) strings = (String[]) Array.grow(strings, growth_rate);
            strings[string_count] = num;
            string_count++;
        }
        else insert(search(num), num);
    }

    /**
     * Inserts the String passed to the array at the index passed.
     */
    private void insert(int index, String num)
    {
        if (strings[index] == num) return;
        else
        {
            if (string_count == strings.length) strings = (String[]) Array.grow(strings, growth_rate);
            System.arraycopy(strings, index, strings, index + 1, string_count - index);
            strings[index] = num;
            string_count++;
        }
    }

    /**
     * Removes the String passed from the array.
     */
    public void remove(String num)
    {
        int index = search(num);
        if (index < string_count && equalTo(strings[index], num)) removeIndex(index);
    }

    /**
     * Removes the String from the beginning of the array to the
     * index passed.
     */
    public void removeIndex(int index)
    {
        if (index < string_count)
        {
            System.arraycopy(strings, index + 1, strings, index, string_count - index - 1);
            string_count--;
        }
    }

    /**
     * Returns true flag if the String passed is in the array.
     */
    public boolean contains(String num)
    {
        int index = search(num);
        return index < string_count && equalTo(strings[search(num)], num);
    }

    /**
     * Returns the number of Strings in the array.
     */
    public int stringCount()
    {
        return string_count;
    }

    /**
     * Returns String index of the int passed.
     */
    public int indexOf(String num)
    {
        int index = search(num);
        return index < string_count && equalTo(strings[index], num) ? index : -1;
    }

    /**
     * Returns the String value at the index passed.
     */
    public String stringAt(int index)
    {
        return strings[index];
    }

    /**
     * Returns the index where the String value passed is located
     * or where it should be sorted to if it is not present.
     */
    protected int search(String num)
    {
        String[] strings = this.strings;
        int lb = 0, ub = string_count, index;
        String index_key;
        while (true)
        {
            if (lb >= ub - 1)
            {
                if (lb < string_count && !greaterThan(num, strings[lb])) return lb;
                else return lb + 1;
            }
            index = (lb + ub) / 2;
            index_key = strings[index];
            if (greaterThan(num, index_key)) lb = index + 1;
            else if (lessThan(num, index_key)) ub = index;
            else return index;
        }
    }

    /**
     * Returns an String[] that contains the value in the SortedStrings
     * object.
     */
    public String[] toStringArray()
    {
        String[] array = new String[string_count];
        System.arraycopy(strings, 0, array, 0, string_count);
        return array;
    }

    /**
     * Returns a sorted String[] from the String[] passed.
     */
    public static String[] sort(String[] input)
    {
        SortedStrings new_strings = new SortedStrings(input);
        return new_strings.toStringArray();
    }
}
