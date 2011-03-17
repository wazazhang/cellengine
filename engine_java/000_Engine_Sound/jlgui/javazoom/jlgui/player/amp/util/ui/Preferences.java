/*
 * Preferences.
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javazoom.jlgui.player.amp.PlayerUI;
import javazoom.jlgui.player.amp.util.Config;

public class Preferences extends JFrame implements TreeSelectionListener, ActionListener
{
    private static Preferences instance = null;
    private JTree tree = null;
    private ResourceBundle bundle = null;
    private DefaultMutableTreeNode options = null;
    private DefaultMutableTreeNode filetypes = null;
    private DefaultMutableTreeNode device = null;
    private DefaultMutableTreeNode proxy = null;
    private DefaultMutableTreeNode plugins = null;
    private DefaultMutableTreeNode visual = null;
    private DefaultMutableTreeNode visuals = null;
    private DefaultMutableTreeNode output = null;
    //private DefaultMutableTreeNode drm = null;
    private DefaultMutableTreeNode skins = null;
    private DefaultMutableTreeNode browser = null;
    private JScrollPane treePane = null;
    private JScrollPane workPane = null;
    private JButton close = null;
    private PlayerUI player = null;

    public Preferences(PlayerUI player)
    {
        super();
        this.player = player;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon = Config.getInstance().getIconParent();
        if (icon != null) setIconImage(icon.getImage());
    }

    public static synchronized Preferences getInstance(PlayerUI player)
    {
        if (instance == null)
        {
            instance = new Preferences(player);
            instance.loadUI();
        }
        return instance;
    }

    private void loadUI()
    {
        bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/preferences");
        setTitle(getResource("title"));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        // Options
        if (getResource("tree.options") != null)
        {
            options = new DefaultMutableTreeNode(getResource("tree.options"));
            if (getResource("tree.options.device") != null)
            {
                device = new DefaultMutableTreeNode();
                device.setUserObject(new NodeItem(getResource("tree.options.device"), getResource("tree.options.device.impl")));
                options.add(device);
            }
            if (getResource("tree.options.visual") != null)
            {
                visual = new DefaultMutableTreeNode();
                visual.setUserObject(new NodeItem(getResource("tree.options.visual"), getResource("tree.options.visual.impl")));
                options.add(visual);
            }
            if (getResource("tree.options.filetypes") != null)
            {
                filetypes = new DefaultMutableTreeNode();
                filetypes.setUserObject(new NodeItem(getResource("tree.options.filetypes"), getResource("tree.options.filetypes.impl")));
                options.add(filetypes);
            }
            if (getResource("tree.options.system") != null)
            {
                proxy = new DefaultMutableTreeNode();
                proxy.setUserObject(new NodeItem(getResource("tree.options.system"), getResource("tree.options.system.impl")));
                options.add(proxy);
            }
            root.add(options);
        }
        // Plugins
        if (getResource("tree.plugins") != null)
        {
            plugins = new DefaultMutableTreeNode(getResource("tree.plugins"));
            if (getResource("tree.plugins.visualization") != null)
            {
                visuals = new DefaultMutableTreeNode();
                visuals.setUserObject(new NodeItem(getResource("tree.plugins.visualization"), getResource("tree.plugins.visualization.impl")));
                plugins.add(visuals);
            }
            if (getResource("tree.plugins.output") != null)
            {
                output = new DefaultMutableTreeNode();
                output.setUserObject(new NodeItem(getResource("tree.plugins.output"), getResource("tree.plugins.output.impl")));
                plugins.add(output);
            }
            /*if (getResource("tree.plugins.drm") != null)
             {
             drm = new DefaultMutableTreeNode();
             drm.setUserObject(new NodeItem(getResource("tree.plugins.drm"), getResource("tree.plugins.drm.impl")));
             plugins.add(drm);
             }*/
            root.add(plugins);
        }
        // Skins
        if (getResource("tree.skins") != null)
        {
            skins = new DefaultMutableTreeNode(getResource("tree.skins"));
            if (getResource("tree.skins.browser") != null)
            {
                browser = new DefaultMutableTreeNode();
                browser.setUserObject(new NodeItem(getResource("tree.skins.browser"), getResource("tree.skins.browser.impl")));
                skins.add(browser);
            }
            root.add(skins);
        }
        tree = new JTree(root);
        tree.setRootVisible(false);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        tree.setCellRenderer(renderer);
        tree.addTreeSelectionListener(this);
        int i = 0;
        while (i < tree.getRowCount())
        {
            tree.expandRow(i++);
        }
        tree.setBorder(new EmptyBorder(1, 4, 1, 2));
        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);
        GridBagConstraints cnts = new GridBagConstraints();
        cnts.fill = GridBagConstraints.BOTH;
        cnts.weightx = 0.3;
        cnts.weighty = 1.0;
        cnts.gridx = 0;
        cnts.gridy = 0;
        treePane = new JScrollPane(tree);
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BorderLayout());
        leftPane.add(treePane, BorderLayout.CENTER);
        if (getResource("button.close") != null)
        {
            close = new JButton(getResource("button.close"));
            close.addActionListener(this);
            leftPane.add(close, BorderLayout.SOUTH);
        }
        getContentPane().add(leftPane, cnts);
        cnts.weightx = 1.0;
        cnts.gridx = 1;
        cnts.gridy = 0;
        workPane = new JScrollPane(new JPanel());
        getContentPane().add(workPane, cnts);
    }

    public void valueChanged(TreeSelectionEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) return;
        if (node.isLeaf())
        {
            Object nodeItem = node.getUserObject();
            if ((nodeItem != null) && (nodeItem instanceof NodeItem))
            {
                PreferenceItem pane = getPreferenceItem(((NodeItem) nodeItem).getImpl());
                if (pane != null)
                {
                    pane.setPlayer(player);
                    pane.loadUI();
                    pane.setParentFrame(this);
                    workPane.setViewportView(pane);
                }
            }
        }
    }

    public void selectSkinBrowserPane()
    {
        TreeNode[] path = browser.getPath();
        tree.setSelectionPath(new TreePath(path));
    }

    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getSource() == close)
        {
            if (player != null)
            {
                Config config = player.getConfig();
                config.save();
            }
            dispose();
        }
    }

    /**
     * Return I18N value of a given key.
     * @param key
     * @return
     */
    public String getResource(String key)
    {
        String value = null;
        if (key != null)
        {
            try
            {
                value = bundle.getString(key);
            }
            catch (MissingResourceException e)
            {
            }
        }
        return value;
    }

    public PreferenceItem getPreferenceItem(String impl)
    {
        PreferenceItem item = null;
        if (impl != null)
        {
            try
            {
                Class aClass = Class.forName(impl);
                Method method = aClass.getMethod("getInstance", null);
                item = (PreferenceItem) method.invoke(null, null);
            }
            catch (Exception e)
            {
                // TODO
            }
        }
        return item;
    }
}
