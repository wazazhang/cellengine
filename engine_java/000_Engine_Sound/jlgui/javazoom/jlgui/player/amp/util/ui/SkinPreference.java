/*
 * SkinPreference.
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
import java.io.File;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javazoom.jlgui.player.amp.util.FileNameFilter;
import javazoom.jlgui.player.amp.util.FileSelector;

public class SkinPreference extends PreferenceItem implements ActionListener, ListSelectionListener
{
    public static final String DEFAULTSKIN = "<Default Skin>";
    public static final String SKINEXTENSION = "wsz";
    private DefaultListModel listModel = null;
    private JList skins = null;
    private JTextArea info = null;
    private JPanel listPane = null;
    private JPanel infoPane = null;
    private JPanel browsePane = null;
    private JButton selectSkinDir = null;
    private static SkinPreference instance = null;

    private SkinPreference()
    {
        listModel = new DefaultListModel();
    }

    public static SkinPreference getInstance()
    {
        if (instance == null)
        {
            instance = new SkinPreference();
        }
        return instance;
    }

    public void loadUI()
    {
        if (loaded == false)
        {
            bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/skin");
            setBorder(new TitledBorder(getResource("title")));
            File dir = null;
            if (player != null)
            {
                dir = new File(player.getConfig().getLastSkinDir());
            }
            loadSkins(dir);
            skins = new JList(listModel);
            skins.setBorder(new EmptyBorder(1, 2, 1, 1));
            skins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            skins.setLayoutOrientation(JList.VERTICAL);
            skins.setVisibleRowCount(12);
            skins.addListSelectionListener(this);
            JScrollPane listScroller = new JScrollPane(skins);
            listScroller.setPreferredSize(new Dimension(300, 140));
            listPane = new JPanel();
            listPane.add(listScroller);
            infoPane = new JPanel();
            info = new JTextArea(4, 35);
            info.setEditable(false);
            info.setCursor(null);
            JScrollPane infoScroller = new JScrollPane(info);
            infoScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            infoScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            infoPane.add(infoScroller);
            browsePane = new JPanel();
            selectSkinDir = new JButton(getResource("browser.directory.button"));
            selectSkinDir.addActionListener(this);
            browsePane.add(selectSkinDir);
            GridBagLayout layout = new GridBagLayout();
            setLayout(layout);
            GridBagConstraints cnts = new GridBagConstraints();
            cnts.fill = GridBagConstraints.BOTH;
            cnts.gridwidth = 1;
            cnts.weightx = 1.0;
            cnts.weighty = 0.60;
            cnts.gridx = 0;
            cnts.gridy = 0;
            add(listPane, cnts);
            cnts.gridwidth = 1;
            cnts.weightx = 1.0;
            cnts.weighty = 0.30;
            cnts.gridx = 0;
            cnts.gridy = 1;
            add(infoPane, cnts);
            cnts.weightx = 1.0;
            cnts.weighty = 0.10;
            cnts.gridx = 0;
            cnts.gridy = 2;
            add(browsePane, cnts);
            loaded = true;
        }
    }

    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getActionCommand().equalsIgnoreCase(getResource("browser.directory.button")))
        {
            File[] file = FileSelector.selectFile(player.getLoader(), FileSelector.DIRECTORY, false, "", "Directories", new File(player.getConfig().getLastSkinDir()));
            if ((file != null) && (file[0].isDirectory()))
            {
                player.getConfig().setLastSkinDir(file[0].getAbsolutePath());
                loadSkins(file[0]);
            }
        }
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting() == false)
        {
            if (skins.getSelectedIndex() == -1)
            {
            }
            else
            {
                String name = (String) listModel.get(skins.getSelectedIndex());
                String filename = player.getConfig().getLastSkinDir() + name + "." + SKINEXTENSION;
                player.getSkin().setPath(filename);
                player.loadSkin();
                player.getConfig().setDefaultSkin(filename);
                String readme = player.getSkin().getReadme();
                if (readme == null) readme = "";
                info.setText(readme);
                info.setCaretPosition(0);
            }
        }
    }

    private void loadSkins(File dir)
    {
        listModel.clear();
        listModel.addElement(DEFAULTSKIN);
        if ((dir != null) && (dir.exists()))
        {
            File[] files = dir.listFiles(new FileNameFilter(SKINEXTENSION, "Skins", false));
            if ((files != null) && (files.length > 0))
            {
                for (int i = 0; i < files.length; i++)
                {
                    String filename = files[i].getName();
                    listModel.addElement(filename.substring(0, filename.length() - 4));
                }
            }
        }
    }

}
