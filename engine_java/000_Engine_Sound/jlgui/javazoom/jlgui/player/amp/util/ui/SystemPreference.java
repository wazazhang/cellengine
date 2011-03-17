/*
 * SystemPreference.
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
package javazoom.jlgui.player.amp.util.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SystemPreference extends PreferenceItem
{
    private JTextArea info = null;
    private boolean loaded = false;
    private static SystemPreference instance = null;

    private SystemPreference()
    {
    }

    public static SystemPreference getInstance()
    {
        if (instance == null)
        {
            instance = new SystemPreference();
        }
        return instance;
    }

    public void loadUI()
    {
        if (loaded == false)
        {
            bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/system");
            setBorder(new TitledBorder(getResource("title")));
            setLayout(new BorderLayout());
            info = new JTextArea(16,35);
            info.setFont(new Font("Dialog", Font.PLAIN, 11));
            info.setEditable(false);
            info.setCursor(null);
            info.setBorder(new EmptyBorder(1,2,1,1));
            Properties props = System.getProperties();
            Iterator it = props.keySet().iterator();
            TreeMap map = new TreeMap();
            while (it.hasNext())
            {
                String key = (String) it.next();
                String value = props.getProperty(key);
                map.put(key, value);
            }
            it = map.keySet().iterator();
            while (it.hasNext())
            {
                String key = (String) it.next();
                String value = (String) map.get(key);
                info.append(key + "=" + value + "\r\n");                  
            }
            JScrollPane infoScroller = new JScrollPane(info);
            infoScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            infoScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            add(infoScroller, BorderLayout.CENTER);
            info.setCaretPosition(0);
            loaded = true;            
        }
    }
}
