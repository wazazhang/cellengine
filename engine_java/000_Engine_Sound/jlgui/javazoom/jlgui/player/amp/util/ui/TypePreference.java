/*
 * TypePreference.
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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TypePreference extends PreferenceItem implements ActionListener, ListSelectionListener
{
    private DefaultListModel listModel = null;
    private JList types = null;
    private JPanel listPane = null;
    private JPanel extensionPane = null;
    private static TypePreference instance = null;

    private TypePreference()
    {
        listModel = new DefaultListModel();
    }

    public static TypePreference getInstance()
    {
        if (instance == null)
        {
            instance = new TypePreference();
        }
        return instance;
    }

    public void loadUI()
    {
        if (loaded == false)
        {
            bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/type");
            setBorder(new TitledBorder(getResource("title")));
            loadTypes();
            types = new JList(listModel);
            types.setBorder(new EmptyBorder(1, 2, 1, 1));
            types.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            types.setLayoutOrientation(JList.VERTICAL);
            types.setVisibleRowCount(12);
            types.addListSelectionListener(this);
            JScrollPane listScroller = new JScrollPane(types);
            listScroller.setPreferredSize(new Dimension(80, 240));
            listPane = new JPanel();
            listPane.add(listScroller);
            extensionPane = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            setLayout(layout);
            GridBagConstraints cnts = new GridBagConstraints();
            cnts.fill = GridBagConstraints.BOTH;
            cnts.gridwidth = 1;
            cnts.weightx = 0.30;
            cnts.weighty = 1.0;
            cnts.gridx = 0;
            cnts.gridy = 0;
            add(listPane, cnts);
            cnts.gridwidth = 1;
            cnts.weightx = 0.70;
            cnts.weighty = 1.0;
            cnts.gridx = 1;
            cnts.gridy = 0;
            add(extensionPane, cnts);
            loaded = true;
        }
    }

    public void actionPerformed(ActionEvent ev)
    {
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting() == false)
        {
            if (types.getSelectedIndex() == -1)
            {
            }
            else
            {
            }
        }
    }

    private void loadTypes()
    {
        String extensions = player.getConfig().getExtensions();
        StringTokenizer st = new StringTokenizer(extensions, ",");
        while (st.hasMoreTokens())
        {
            String type = st.nextToken();
            listModel.addElement(type);
        }
    }
}
