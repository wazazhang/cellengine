/*
 * DevicePreference.
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;

public class DevicePreference extends PreferenceItem implements ActionListener
{
    private BasicPlayer bplayer = null;
    private static DevicePreference instance = null;

    private DevicePreference()
    {
    }

    public static DevicePreference getInstance()
    {
        if (instance == null)
        {
            instance = new DevicePreference();
        }
        return instance;
    }

    public void loadUI()
    {
        removeAll();
        bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/device");
        setBorder(new TitledBorder(getResource("title")));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        BasicController controller = null;
        if (player != null) controller = player.getController();
        if ((controller != null) && (controller instanceof BasicPlayer))
        {
            bplayer = (BasicPlayer) controller;
            List devices = bplayer.getMixers();
            String mixer = bplayer.getMixerName();
            ButtonGroup group = new ButtonGroup();
            Iterator it = devices.iterator();
            while (it.hasNext())
            {
                String name = (String) it.next();
                JRadioButton radio = new JRadioButton(name);
                if (name.equals(mixer))
                {
                    radio.setSelected(true);
                }
                else
                {
                    radio.setSelected(false);
                }
                group.add(radio);
                radio.addActionListener(this);
                radio.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(radio);
            }
            JPanel lineInfo = new JPanel();
            lineInfo.setLayout(new BoxLayout(lineInfo, BoxLayout.Y_AXIS));
            lineInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
            lineInfo.setBorder(new EmptyBorder(4, 6, 0, 0));
            if (getResource("line.buffer.size") != null)
            {
                Object[] args = { new Integer(bplayer.getLineCurrentBufferSize()) };
                String str = MessageFormat.format(getResource("line.buffer.size"), args);
                JLabel lineBufferSizeLabel = new JLabel(str);
                lineInfo.add(lineBufferSizeLabel);
            }
            if (getResource("help") != null)
            {
                lineInfo.add(Box.createRigidArea(new Dimension(0, 30)));
                JLabel helpLabel = new JLabel(getResource("help"));
                lineInfo.add(helpLabel);
            }
            add(lineInfo);
        }
    }

    public void actionPerformed(ActionEvent ev)
    {
        if (bplayer != null) bplayer.setMixerName(ev.getActionCommand());
    }
}
