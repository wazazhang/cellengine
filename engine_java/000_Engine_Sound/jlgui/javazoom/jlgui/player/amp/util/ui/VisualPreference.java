/*
 * VisualPreference.
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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.player.amp.visual.ui.SpectrumTimeAnalyzer;

public class VisualPreference extends PreferenceItem implements ActionListener, ChangeListener
{
    private JPanel modePane = null;
    private JPanel spectrumPane = null;
    private JPanel oscilloPane = null;
    private JRadioButton spectrumMode = null;
    private JRadioButton oscilloMode = null;
    private JRadioButton offMode = null;
    private JCheckBox peaksBox = null;
    private JSlider analyzerFalloff = null;
    private JSlider peaksFalloff = null;
    private static VisualPreference instance = null;

    private VisualPreference()
    {
    }

    public static VisualPreference getInstance()
    {
        if (instance == null)
        {
            instance = new VisualPreference();
        }
        return instance;
    }

    public void loadUI()
    {
        if (loaded == false)
        {
            bundle = ResourceBundle.getBundle("javazoom/jlgui/player/amp/util/ui/visual");
            setBorder(new TitledBorder(getResource("title")));
            modePane = new JPanel();
            modePane.setBorder(new TitledBorder(getResource("mode.title")));
            modePane.setLayout(new FlowLayout());
            spectrumMode = new JRadioButton(getResource("mode.spectrum"));
            spectrumMode.addActionListener(this);
            oscilloMode = new JRadioButton(getResource("mode.oscilloscope"));
            oscilloMode.addActionListener(this);
            offMode = new JRadioButton(getResource("mode.off"));
            offMode.addActionListener(this);
            SpectrumTimeAnalyzer analyzer = null;
            if (player != null)
            {
                analyzer = player.getSkin().getAcAnalyzer();
                int displayMode = SpectrumTimeAnalyzer.DISPLAY_MODE_OFF;
                if (analyzer != null)
                {
                    displayMode = analyzer.getDisplayMode();
                }
                if (displayMode == SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER)
                {
                    spectrumMode.setSelected(true);
                }
                else if (displayMode == SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE)
                {
                    oscilloMode.setSelected(true);
                }
                else if (displayMode == SpectrumTimeAnalyzer.DISPLAY_MODE_OFF)
                {
                    offMode.setSelected(true);
                }
            }
            ButtonGroup modeGroup = new ButtonGroup();
            modeGroup.add(spectrumMode);
            modeGroup.add(oscilloMode);
            modeGroup.add(offMode);
            modePane.add(spectrumMode);
            modePane.add(oscilloMode);
            modePane.add(offMode);
            spectrumPane = new JPanel();
            spectrumPane.setLayout(new BoxLayout(spectrumPane, BoxLayout.Y_AXIS));
            peaksBox = new JCheckBox(getResource("spectrum.peaks"));
            peaksBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            peaksBox.addActionListener(this);
            if ((analyzer != null) && (analyzer.isPeaksEnabled())) peaksBox.setSelected(true);
            else peaksBox.setSelected(false);
            spectrumPane.add(peaksBox);
            // Analyzer falloff.
            JLabel analyzerFalloffLabel = new JLabel(getResource("spectrum.analyzer.falloff"));
            analyzerFalloffLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            spectrumPane.add(analyzerFalloffLabel);
            int minDecay = (int) (SpectrumTimeAnalyzer.MIN_SPECTRUM_ANALYSER_DECAY * 100);
            int maxDecay = (int) (SpectrumTimeAnalyzer.MAX_SPECTRUM_ANALYSER_DECAY * 100);
            int decay = (maxDecay + minDecay) / 2;
            if (analyzer != null)
            {
                decay = (int) (analyzer.getSpectrumAnalyserDecay() * 100);
            }
            analyzerFalloff = new JSlider(JSlider.HORIZONTAL, minDecay, maxDecay, decay);
            analyzerFalloff.setMajorTickSpacing(1);
            analyzerFalloff.setPaintTicks(true);
            analyzerFalloff.setMaximumSize(new Dimension(150, analyzerFalloff.getPreferredSize().height));
            analyzerFalloff.setAlignmentX(Component.LEFT_ALIGNMENT);
            analyzerFalloff.setSnapToTicks(true);
            analyzerFalloff.addChangeListener(this);
            spectrumPane.add(analyzerFalloff);
            // Peaks falloff.
            JLabel peaksFalloffLabel = new JLabel(getResource("spectrum.peaks.falloff"));
            peaksFalloffLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            spectrumPane.add(peaksFalloffLabel);
            int peakDelay = SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY;
            int fps = SpectrumTimeAnalyzer.DEFAULT_FPS;
            if (analyzer != null)
            {
                fps = analyzer.getFps();
                peakDelay = analyzer.getPeakDelay();
            }
            peaksFalloff = new JSlider(JSlider.HORIZONTAL, 0, 4, computeSliderValue(peakDelay, fps));
            peaksFalloff.setMajorTickSpacing(1);
            peaksFalloff.setPaintTicks(true);
            peaksFalloff.setSnapToTicks(true);
            Hashtable labelTable = new Hashtable();
            labelTable.put(new Integer(0), new JLabel("Slow"));
            labelTable.put(new Integer(4), new JLabel("Fast"));
            peaksFalloff.setLabelTable(labelTable);
            peaksFalloff.setPaintLabels(true);
            peaksFalloff.setMaximumSize(new Dimension(150, peaksFalloff.getPreferredSize().height));
            peaksFalloff.setAlignmentX(Component.LEFT_ALIGNMENT);
            peaksFalloff.addChangeListener(this);
            spectrumPane.add(peaksFalloff);
            // Spectrum pane
            spectrumPane.setBorder(new TitledBorder(getResource("spectrum.title")));
            if (getResource("oscilloscope.title") != null)
            {
                oscilloPane = new JPanel();
                oscilloPane.setBorder(new TitledBorder(getResource("oscilloscope.title")));
            }
            GridBagLayout layout = new GridBagLayout();
            setLayout(layout);
            GridBagConstraints cnts = new GridBagConstraints();
            cnts.fill = GridBagConstraints.BOTH;
            cnts.gridwidth = 2;
            cnts.weightx = 2.0;
            cnts.weighty = 0.25;
            cnts.gridx = 0;
            cnts.gridy = 0;
            add(modePane, cnts);
            cnts.gridwidth = 1;
            cnts.weightx = 1.0;
            cnts.weighty = 1.0;
            cnts.gridx = 0;
            cnts.gridy = 1;
            add(spectrumPane, cnts);
            cnts.weightx = 1.0;
            cnts.weighty = 1.0;
            cnts.gridx = 1;
            cnts.gridy = 1;
            if (oscilloPane != null) add(oscilloPane, cnts);
            if (analyzer == null)
            {
                disablePane(modePane);
                disablePane(spectrumPane);
                disablePane(oscilloPane);
            }
            loaded = true;
        }
    }

    private void disablePane(JPanel pane)
    {
        if (pane != null)
        {
            Component[] cpns = pane.getComponents();
            if (cpns != null)
            {
                for (int i = 0; i < cpns.length; i++)
                {
                    cpns[i].setEnabled(false);
                }
            }
        }
    }

    public void actionPerformed(ActionEvent ev)
    {
        if (player != null)
        {
            SpectrumTimeAnalyzer analyzer = player.getSkin().getAcAnalyzer();
            if (analyzer != null)
            {
                if (ev.getSource().equals(spectrumMode))
                {
                    analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
                    analyzer.startDSP(null);
                }
                else if (ev.getSource().equals(oscilloMode))
                {
                    analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE);
                    analyzer.startDSP(null);
                }
                else if (ev.getSource().equals(offMode))
                {
                    analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_OFF);
                    analyzer.closeDSP();
                    analyzer.repaint();
                }
                else if (ev.getSource().equals(peaksBox))
                {
                    if (peaksBox.isSelected()) analyzer.setPeaksEnabled(true);
                    else analyzer.setPeaksEnabled(false);
                }
            }
        }
    }

    public void stateChanged(ChangeEvent ce)
    {
        if (player != null)
        {
            SpectrumTimeAnalyzer analyzer = player.getSkin().getAcAnalyzer();
            if (analyzer != null)
            {
                if (ce.getSource() == analyzerFalloff)
                {
                    if (!analyzerFalloff.getValueIsAdjusting())
                    {
                        analyzer.setSpectrumAnalyserDecay(analyzerFalloff.getValue() * 1.0f / 100.0f);
                    }
                }
                else if (ce.getSource() == peaksFalloff)
                {
                    if (!peaksFalloff.getValueIsAdjusting())
                    {
                        analyzer.setPeakDelay(computeDelay(peaksFalloff.getValue(), analyzer.getFps()));
                    }
                }
            }
        }
    }

    private int computeDelay(int slidervalue, int fps)
    {
        float p = SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO;
        float n = SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO_RANGE;
        int delay = Math.round(((-n * slidervalue * 1.0f / 2.0f) + p + n) * fps);
        return delay;
    }

    private int computeSliderValue(int delay, int fps)
    {
        float p = SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO;
        float n = SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO_RANGE;
        int value = (int) Math.round((((p - (delay * 1.0 / fps * 1.0f)) * 2 / n) + 2));
        return value;
    }
}
