/*
 * Skin.
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
package javazoom.jlgui.player.amp.skin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javazoom.jlgui.player.amp.PlayerActionEvent;
import javazoom.jlgui.player.amp.equalizer.ui.SplinePanel;
import javazoom.jlgui.player.amp.util.Config;
import javazoom.jlgui.player.amp.visual.ui.SpectrumTimeAnalyzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class allows to load all skin (2.0 compliant) features. 
 */
public class Skin
{
    private static Log log = LogFactory.getLog(Skin.class);
    public static final String TITLETEXT = "jlGui 3.0 ";
    private Config config = null;
    private String skinVersion = "1"; // 1, 2, for different Volume.bmp
    private String path = null;
    private boolean dspEnabled = true;
    /*-- Window Parameters --*/
    private int WinWidth, WinHeight;
    private String theMain = "main.bmp";
    private Image imMain = null;
    /*-- Text Members --*/
    private int fontWidth = 5;
    private int fontHeight = 6;
    private String theText = "text.bmp";
    private Image imText;
    private String fontIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\"@a  " + "0123456789  :()-'!_+ /[]^&%.=$#" + "   ?*";
    private ActiveFont acFont = null;
    private ActiveJLabel acTitleLabel = null;
    private ActiveJLabel acSampleRateLabel = null;
    private ActiveJLabel acBitRateLabel = null;
    private String sampleRateClearText = "  ";
    private int[] sampleRateLocation = { 156, 43 };
    private String bitsRateClearText = "   ";
    private int[] bitsRateLocation = { 110, 43 };
    private int[] titleLocation = { 111, 27 };
    /*-- Numbers Members --*/
    private int numberWidth = 9;
    private int numberHeight = 13;
    private String theNumbers = "numbers.bmp";
    private String theNumEx = "nums_ex.bmp";
    private Image imNumbers;
    private String numberIndex = "0123456789 ";
    private int[] minuteHLocation = { 48, 26 };
    private int[] minuteLLocation = { 60, 26 };
    private int[] secondHLocation = { 78, 26 };
    private int[] secondLLocation = { 90, 26 };
    private ActiveJNumberLabel acMinuteH = null;
    private ActiveJNumberLabel acMinuteL = null;
    private ActiveJNumberLabel acSecondH = null;
    private ActiveJNumberLabel acSecondL = null;
    /*-- Buttons Panel members --*/
    private String theButtons = "cbuttons.bmp";
    private Image imButtons;
    private ActiveJButton acPrevious, acPlay, acPause, acStop, acNext, acEject;
    private Image imPrevious, imPlay, imPause, imStop, imNext, imEject;
    private Image[] releasedImage = { imPrevious, imPlay, imPause, imStop, imNext, imEject };
    private Image[] pressedImage = { imPrevious, imPlay, imPause, imStop, imNext, imEject };
    private int[] releasedPanel = { 0, 0, 23, 18, 23, 0, 23, 18, 46, 0, 23, 18, 69, 0, 23, 18, 92, 0, 22, 18, 114, 0, 22, 16 };
    private int[] pressedPanel = { 0, 18, 23, 18, 23, 18, 23, 18, 46, 18, 23, 18, 69, 18, 23, 18, 92, 18, 22, 18, 114, 16, 22, 16 };
    private int[] panelLocation = { 16, 88, 39, 88, 62, 88, 85, 88, 108, 88, 136, 89 };
    /*-- EqualizerUI/Playlist/Shuffle/Repeat  --*/
    private String theEPSRButtons = "shufrep.bmp";
    private Image imEPSRButtons;
    private ActiveJToggleButton acEqualizer, acPlaylist, acShuffle, acRepeat;
    private Image[] releasedEPSRImage = { null, null, null, null };
    private Image[] pressedEPSRImage = { null, null, null, null };
    private int[] releasedEPSRPanel = { 0, 61, 23, 12, 23, 61, 23, 12, 28, 0, 47, 15, 0, 0, 28, 15 };
    private int[] pressedEPSRPanel = { 0, 73, 23, 12, 23, 73, 23, 12, 28, 30, 47, 15, 0, 30, 28, 15 };
    private int[] panelEPSRLocation = { 219, 58, 242, 58, 164, 89, 212, 89 };
    /*-- Volume Panel members --*/
    public static final int VOLUMEMAX = 100;
    private String theVolume = "volume.bmp";
    private Image imVolume;
    private ActiveJSlider acVolume = null;;
    private Image[] volumeImage = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
    private String fakeIndex = "abcdefghijklmnopqrstuvwxyz01";
    private int[] volumeBarLocation = { 107, 57 };
    private Image[] releasedVolumeImage = { null };
    private Image[] pressedVolumeImage = { null };
    private int[] releasedVolumePanel0 = { 15, 422, 14, 11 };
    private int[] pressedVolumePanel0 = { 0, 422, 14, 11 };
    private int[] releasedVolumePanel1 = { 75, 376, 14, 11 };
    private int[] pressedVolumePanel1 = { 90, 376, 14, 11 };
    /*-- Balance Panel members --*/
    public static final int BALANCEMAX = 5;
    private String theBalance = "balance.bmp";
    private ActiveJSlider acBalance = null;
    private Image imBalance;
    private Image[] balanceImage = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
    private Image[] releasedBalanceImage = { null };
    private Image[] pressedBalanceImage = { null };
    private int[] releasedBalancePanel0 = { 15, 422, 14, 11 };
    private int[] pressedBalancePanel0 = { 0, 422, 14, 11 };
    private int[] releasedBalancePanel1 = { 75, 376, 14, 11 };
    private int[] pressedBalancePanel1 = { 90, 376, 14, 11 };
    private int[] balanceBarLocation = { 177, 57 };
    /*-- Title members --*/
    private String theTitleBar = "titlebar.bmp";
    private Image imTitleBar;
    private ActiveJBar acTitleBar = null;
    private Image imTitleB;
    private Image[] releasedTitleIm = { imTitleB };
    private Image[] pressedTitleIm = { imTitleB };
    private int[] releasedTitlePanel = { 27, 0, 264 - 20, 14 }; // -20 for the two button add by me
    private int[] pressedTitlePanel = { 27, 15, 264 - 20, 14 };// -20 for the two button add by me
    private int[] titleBarLocation = { 0, 0 };
    /*-- Exit member --*/
    private ActiveJButton acExit = null;
    private int[] releasedExitPanel = { 18, 0, 9, 9 };
    private int[] pressedExitPanel = { 18, 9, 9, 9 };
    private Image[] releasedExitIm = { null };
    private Image[] pressedExitIm = { null };
    private int[] exitLocation = { 264, 3 };
    /*-- Minimize member --*/
    private ActiveJButton acMinimize = null;
    private int[] releasedMinimizePanel = { 9, 0, 9, 9 };
    private int[] pressedMinimizePanel = { 9, 9, 9, 9 };
    private Image[] releasedMinimizeIm = { null };
    private Image[] pressedMinimizeIm = { null };
    private int[] minimizeLocation = { 244, 3 };
    /*-- Mono/Stereo Members --*/
    private String theMode = "monoster.bmp";
    private Image imMode;
    private int[] activeModePanel = { 0, 0, 28, 12, 29, 0, 27, 12 };
    private int[] passiveModePanel = { 0, 12, 28, 12, 29, 12, 27, 12 };
    private Image[] activeModeImage = { null, null };
    private Image[] passiveModeImage = { null, null };
    private int[] monoLocation = { 212, 41 };
    private int[] stereoLocation = { 239, 41 };
    private ActiveJIcon acMonoIcon = null;
    private ActiveJIcon acStereoIcon = null;
    /*-- PosBar members --*/
    public static final int POSBARMAX = 1000;
    private String thePosBar = "posbar.bmp";
    private Image imPosBar;
    private ActiveJSlider acPosBar = null;
    private Image[] releasedPosIm = { null };
    private Image[] pressedPosIm = { null };
    private int[] releasedPosPanel = { 248, 0, 28, 10 };
    private int[] pressedPosPanel = { 278, 0, 28, 10 };
    private int[] posBarLocation = { 16, 72 };
    /*-- Play/Pause Icons --*/
    private String theIcons = "playpaus.bmp";
    private Image imIcons;
    private Image[] iconsImage = { null, null, null, null, null };
    private int[] iconsPanel = { 0, 0, 9, 9, 9, 0, 9, 9, 18, 0, 9, 9, 36, 0, 3, 9, 27, 0, 2, 9 };
    private int[] iconsLocation = { 26, 28, 24, 28 };
    private ActiveJIcon acPlayIcon = null;
    private ActiveJIcon acTimeIcon = null;
    /*-- Readme --*/
    private String theReadme = "readme.txt";
    private String readme = null;
    /*-- DSP and viscolor --*/
    private String theViscolor = "viscolor.txt";
    private String viscolor = null;
    private int[] visualLocation = { 24, 44 };
    private int[] visualSize = { 76, 15 };
    private SpectrumTimeAnalyzer analyzer = null;
    /*-- EqualizerUI --*/
    private Image imFullEqualizer = null;
    private Image imEqualizer = null;
    private Image imSliders = null;
    private ActiveJSlider[] acSlider = { null, null, null, null, null, null, null, null, null, null, null };
    private Image[] sliderImage = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
    private int[][] sliderBarLocation = { { 21, 38 }, { 78, 38 }, { 96, 38 }, { 114, 38 }, { 132, 38 }, { 150, 38 }, { 168, 38 }, { 186, 38 }, { 204, 38 }, { 222, 38 }, { 240, 38 } };
    private Image[] releasedSliderImage = { null };
    private Image[] pressedSliderImage = { null };
    private int[][] sliderLocation = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
    private Image[] releasedPresetsImage = { null };
    private Image[] pressedPresetsImage = { null };
    private int[] panelPresetsLocation = { 217, 18 };
    private ActiveJButton acPresets = null;
    private ActiveJToggleButton acOnOff, acAuto;
    private Image[] releasedOAImage = { null, null };
    private Image[] pressedOAImage = { null, null };
    private int[] panelOALocation = { 15, 18, 39, 18 };
    private SplinePanel spline = null;
    private int[] panelSplineLocation = { 88, 17, 113, 19 };
    private Image splineImage = null;
    private Image splineBarImage = null;
    private ResourceBundle bundle = null;
    /*-- Playlist --*/
    private PlaylistUIDelegate playlist = null;
    private Image imPlaylist = null;
    private String plEdit = null;
    private ActiveJSlider acPlSlider = null;
    private int[] plSliderLocation = { 255, 20 };
    private ActiveJButton acPlUp, acPlDown;
    private ActiveJButton acPlAdd, acPlRemove, acPlSelect, acPlMisc, acPlList;
    private int[] plAddLocation = { 14, 86 };
    private int[] plRemoveLocation = { 14 + 30, 86 };
    private int[] plSelectLocation = { 14 + 60, 86 };
    private int[] plMiscLocation = { 14 + 89, 86 };
    private int[] plListLocation = { 14 + 214, 86 };
    private ActiveJPopup acPlAddPopup, acPlRemovePopup, acPlSelectPopup, acPlMiscPopup, acPlListPopup;
    private int[] plAddPopupArea = { 14, 50, 22, 18 * 3 };
    private int[] plRemovePopupArea = { 14 + 29, 32, 22, 18 * 4 };
    private int[] plSelectPopupArea = { 14 + 58, 50, 22, 18 * 3 };
    private int[] plMiscPopupArea = { 14 + 87, 50, 22, 18 * 3 };
    private int[] plListPopupArea = { 14 + 217, 50, 22, 18 * 3 };

    public Skin()
    {
        super();
        String i18n = "javazoom/jlgui/player/amp/skin/skin";
        bundle = ResourceBundle.getBundle(i18n);
    }

    /**
     * Return I18N value of a given key.
     * @param key
     * @return
     */
    public String getResource(String key)
    {
        String value = null;
        try
        {
            value = bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            log.debug(e);
        }
        return value;
    }

    /**
     * Return skin path.
     * @return
     */
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean isDspEnabled()
    {
        return dspEnabled;
    }

    public void setDspEnabled(boolean dspEnabled)
    {
        this.dspEnabled = dspEnabled;
    }

    /**
     * Loads a new skin from local file system.
     * @param skinName
     */
    public void loadSkin(String skinName)
    {
        SkinLoader skl = new SkinLoader(skinName);
        try
        {
            loadSkin(skl);
            path = skinName;
        }
        catch (Exception e)
        {
            log.info("Can't load skin : ", e);
            InputStream sis = this.getClass().getClassLoader().getResourceAsStream("javazoom/jlgui/player/amp/metrix.wsz");
            log.info("Load default skin for JAR");
            loadSkin(sis);
        }
    }

    /**
     * Loads a new skin from any input stream.
     * @param skinStream
     */
    public void loadSkin(InputStream skinStream)
    {
        SkinLoader skl = new SkinLoader(skinStream);
        try
        {
            loadSkin(skl);
        }
        catch (Exception e)
        {
            log.info("Can't load skin : ", e);
            InputStream sis = this.getClass().getClassLoader().getResourceAsStream("javazoom/jlgui/player/amp/metrix.wsz");
            log.info("Load default skin for JAR");
            loadSkin(sis);
        }
    }

    /**
     * Loads a skin from a SkinLoader.
     * @param skl
     * @throws Exception
     */
    public void loadSkin(SkinLoader skl) throws Exception
    {
        skl.loadImages();
        imMain = skl.getImage(theMain);
        imButtons = skl.getImage(theButtons);
        imTitleBar = skl.getImage(theTitleBar);
        imText = skl.getImage(theText);
        imMode = skl.getImage(theMode);
        imNumbers = skl.getImage(theNumbers);
        // add by John Yang
        if (imNumbers == null)
        {
            log.debug("Try load nums_ex.bmp !");
            imNumbers = skl.getImage(theNumEx);
        }
        imVolume = skl.getImage(theVolume);
        imBalance = skl.getImage(theBalance);
        imIcons = skl.getImage(theIcons);
        imPosBar = skl.getImage(thePosBar);
        imEPSRButtons = skl.getImage(theEPSRButtons);
        viscolor = (String) skl.getContent(theViscolor);
        String readmeStr = theReadme;
        readme = (String) skl.getContent(readmeStr);
        if (readme == null)
        {
            readmeStr = readmeStr.toUpperCase();
            readme = (String) skl.getContent(readmeStr);
        }
        if (readme == null)
        {
            readmeStr = readmeStr.substring(0, 1) + theReadme.substring(1, theReadme.length());
            readme = (String) skl.getContent(readmeStr);
        }
        // Computes volume slider height :
        int vh = (imVolume.getHeight(null) - 422);
        if (vh > 0)
        {
            releasedVolumePanel0[3] = vh;
            pressedVolumePanel0[3] = vh;
            releasedVolumePanel1[3] = vh;
            pressedVolumePanel1[3] = vh;
        }
        // Computes balance slider height :
        if (imBalance == null) imBalance = imVolume;
        int bh = (imBalance.getHeight(null) - 422);
        if (bh > 0)
        {
            releasedBalancePanel0[3] = bh;
            pressedBalancePanel0[3] = bh;
            releasedBalancePanel1[3] = bh;
            pressedBalancePanel1[3] = bh;
        }
        // Compute posbar height.
        int ph = imPosBar.getHeight(null);
        if (ph > 0)
        {
            releasedPosPanel[3] = ph;
            pressedPosPanel[3] = ph;
        }
        WinHeight = imMain.getHeight(null); // 116
        WinWidth = imMain.getWidth(null); // 275
        /*-- Text --*/
        acFont = new ActiveFont(imText, fontIndex, fontWidth, fontHeight);
        acTitleLabel = new ActiveJLabel();
        acTitleLabel.setAcFont(acFont);
        acTitleLabel.setCropRectangle(new Rectangle(0, 0, 155, 6));
        acTitleLabel.setConstraints(new AbsoluteConstraints(titleLocation[0], titleLocation[1], 155, 6));
        acTitleLabel.setAcText(TITLETEXT.toUpperCase());
        acSampleRateLabel = new ActiveJLabel();
        acSampleRateLabel.setAcFont(acFont);
        acSampleRateLabel.setConstraints(new AbsoluteConstraints(sampleRateLocation[0], sampleRateLocation[1]));
        acSampleRateLabel.setAcText(sampleRateClearText);
        acBitRateLabel = new ActiveJLabel();
        acBitRateLabel.setAcFont(acFont);
        acBitRateLabel.setConstraints(new AbsoluteConstraints(bitsRateLocation[0], bitsRateLocation[1]));
        acBitRateLabel.setAcText(bitsRateClearText);
        /*-- Buttons --*/
        readPanel(releasedImage, releasedPanel, pressedImage, pressedPanel, imButtons);
        setButtonsPanel();
        /*-- Volume/Balance --*/
        if (skinVersion.equals("1"))
        {
            readPanel(releasedVolumeImage, releasedVolumePanel0, pressedVolumeImage, pressedVolumePanel0, imVolume);
            readPanel(releasedBalanceImage, releasedBalancePanel0, pressedBalanceImage, pressedBalancePanel0, imBalance);
        }
        else
        {
            readPanel(releasedVolumeImage, releasedVolumePanel1, pressedVolumeImage, pressedVolumePanel1, imVolume);
            readPanel(releasedBalanceImage, releasedBalancePanel1, pressedBalanceImage, pressedBalancePanel1, imBalance);
        }
        setVolumeBalancePanel(vh, bh);
        /*-- Title Bar --*/
        readPanel(releasedTitleIm, releasedTitlePanel, pressedTitleIm, pressedTitlePanel, imTitleBar);
        setTitleBarPanel();
        /*-- Exit --*/
        readPanel(releasedExitIm, releasedExitPanel, pressedExitIm, pressedExitPanel, imTitleBar);
        setExitPanel();
        /*-- Minimize --*/
        readPanel(releasedMinimizeIm, releasedMinimizePanel, pressedMinimizeIm, pressedMinimizePanel, imTitleBar);
        setMinimizePanel();
        /*-- Mode --*/
        readPanel(activeModeImage, activeModePanel, passiveModeImage, passiveModePanel, imMode);
        setMonoStereoPanel();
        /*-- Numbers --*/
        ImageIcon[] numbers = new ImageIcon[numberIndex.length()];
        for (int h = 0; h < numberIndex.length(); h++)
        {
            numbers[h] = new ImageIcon((new Taftb(numberIndex, imNumbers, numberWidth, numberHeight, 0, "" + numberIndex.charAt(h))).getBanner());
        }
        acMinuteH = new ActiveJNumberLabel();
        acMinuteH.setNumbers(numbers);
        acMinuteH.setConstraints(new AbsoluteConstraints(minuteHLocation[0], minuteHLocation[1]));
        acMinuteH.setAcText(" ");
        acMinuteL = new ActiveJNumberLabel();
        acMinuteL.setNumbers(numbers);
        acMinuteL.setConstraints(new AbsoluteConstraints(minuteLLocation[0], minuteLLocation[1]));
        acMinuteL.setAcText(" ");
        acSecondH = new ActiveJNumberLabel();
        acSecondH.setNumbers(numbers);
        acSecondH.setConstraints(new AbsoluteConstraints(secondHLocation[0], secondHLocation[1]));
        acSecondH.setAcText(" ");
        acSecondL = new ActiveJNumberLabel();
        acSecondL.setNumbers(numbers);
        acSecondL.setConstraints(new AbsoluteConstraints(secondLLocation[0], secondLLocation[1]));
        acSecondL.setAcText(" ");
        /*--  Icons --*/
        readPanel(iconsImage, iconsPanel, null, null, imIcons);
        acPlayIcon = new ActiveJIcon();
        ImageIcon[] playIcons = { new ImageIcon(iconsImage[0]), new ImageIcon(iconsImage[1]), new ImageIcon(iconsImage[2]) };
        acPlayIcon.setIcons(playIcons);
        acPlayIcon.setConstraints(new AbsoluteConstraints(iconsLocation[0], iconsLocation[1]));
        acPlayIcon.setIcon(2);
        acTimeIcon = new ActiveJIcon();
        ImageIcon[] timeIcons = { new ImageIcon(iconsImage[3]), new ImageIcon(iconsImage[4]) };
        acTimeIcon.setIcons(timeIcons);
        acTimeIcon.setConstraints(new AbsoluteConstraints(iconsLocation[2], iconsLocation[3]));
        /*-- DSP --*/
        setAnalyzerPanel();
        /*-- Pos Bar --*/
        readPanel(releasedPosIm, releasedPosPanel, pressedPosIm, pressedPosPanel, imPosBar);
        setPosBarPanel();
        /*-- EqualizerUI/Playlist/Shuffle/Repeat  --*/
        readPanel(releasedEPSRImage, releasedEPSRPanel, pressedEPSRImage, pressedEPSRPanel, imEPSRButtons);
        setEPSRButtonsPanel();
        /*-- EqualizerUI --*/
        imFullEqualizer = skl.getImage("eqmain.bmp");
        imEqualizer = new BufferedImage(WinWidth, WinHeight, BufferedImage.TYPE_INT_RGB);
        imEqualizer.getGraphics().drawImage(imFullEqualizer, 0, 0, null);
        imSliders = new BufferedImage(208, 128, BufferedImage.TYPE_INT_RGB);
        imSliders.getGraphics().drawImage(imFullEqualizer, 0, 0, 208, 128, 13, 164, 13 + 208, 164 + 128, null);
        setSliderPanel();
        setOnOffAutoPanel();
        setPresetsPanel();
        setSplinePanel();
        /*-- Playlist --*/
        imPlaylist = skl.getImage("pledit.bmp");
        plEdit = (String) skl.getContent("pledit.txt");
        setPlaylistPanel();
    }

    /**
     * Instantiate Buttons Panel with ActiveComponent.
     */
    private void setButtonsPanel()
    {
        int l = 0;
        acPrevious = new ActiveJButton();
        acPrevious.setIcon(new ImageIcon(releasedImage[0]));
        acPrevious.setPressedIcon(new ImageIcon(pressedImage[0]));
        acPrevious.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[0].getWidth(null), releasedImage[0].getHeight(null)));
        acPrevious.setToolTipText(getResource("button.previous"));
        acPrevious.setActionCommand(PlayerActionEvent.ACPREVIOUS);
        acPlay = new ActiveJButton();
        acPlay.setIcon(new ImageIcon(releasedImage[1]));
        acPlay.setPressedIcon(new ImageIcon(pressedImage[1]));
        acPlay.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[1].getWidth(null), releasedImage[1].getHeight(null)));
        acPlay.setToolTipText(getResource("button.play"));
        acPlay.setActionCommand(PlayerActionEvent.ACPLAY);
        acPause = new ActiveJButton();
        acPause.setIcon(new ImageIcon(releasedImage[2]));
        acPause.setPressedIcon(new ImageIcon(pressedImage[2]));
        acPause.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[2].getWidth(null), releasedImage[2].getHeight(null)));
        acPause.setToolTipText(getResource("button.pause"));
        acPause.setActionCommand(PlayerActionEvent.ACPAUSE);
        acStop = new ActiveJButton();
        acStop.setIcon(new ImageIcon(releasedImage[3]));
        acStop.setPressedIcon(new ImageIcon(pressedImage[3]));
        acStop.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[3].getWidth(null), releasedImage[3].getHeight(null)));
        acStop.setToolTipText(getResource("button.stop"));
        acStop.setActionCommand(PlayerActionEvent.ACSTOP);
        acNext = new ActiveJButton();
        acNext.setIcon(new ImageIcon(releasedImage[4]));
        acNext.setPressedIcon(new ImageIcon(pressedImage[4]));
        acNext.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[4].getWidth(null), releasedImage[4].getHeight(null)));
        acNext.setToolTipText(getResource("button.next"));
        acNext.setActionCommand(PlayerActionEvent.ACNEXT);
        acEject = new ActiveJButton();
        acEject.setIcon(new ImageIcon(releasedImage[5]));
        acEject.setPressedIcon(new ImageIcon(pressedImage[5]));
        acEject.setConstraints(new AbsoluteConstraints(panelLocation[l++], panelLocation[l++], releasedImage[5].getWidth(null), releasedImage[5].getHeight(null)));
        acEject.setToolTipText(getResource("button.eject"));
        acEject.setActionCommand(PlayerActionEvent.ACEJECT);
    }

    /**
     * Instantiate EPSR Buttons Panel with ActiveComponent.
     * imEqualizer, imPlaylist, imShuffle, imRepeat
     */
    private void setEPSRButtonsPanel()
    {
        int l = 0;
        acEqualizer = new ActiveJToggleButton();
        acEqualizer.setIcon(new ImageIcon(releasedEPSRImage[0]));
        acEqualizer.setSelectedIcon(new ImageIcon(pressedEPSRImage[0]));
        acEqualizer.setPressedIcon(new ImageIcon(pressedEPSRImage[0]));
        acEqualizer.setConstraints(new AbsoluteConstraints(panelEPSRLocation[l++], panelEPSRLocation[l++], releasedEPSRImage[0].getWidth(null), releasedEPSRImage[0].getHeight(null)));
        acEqualizer.setToolTipText(getResource("toggle.equalizer"));
        acEqualizer.setActionCommand(PlayerActionEvent.ACEQUALIZER);
        acEqualizer.setSelected(config.isEqualizerEnabled());
        acPlaylist = new ActiveJToggleButton();
        acPlaylist.setIcon(new ImageIcon(releasedEPSRImage[1]));
        acPlaylist.setSelectedIcon(new ImageIcon(pressedEPSRImage[1]));
        acPlaylist.setPressedIcon(new ImageIcon(pressedEPSRImage[1]));
        acPlaylist.setConstraints(new AbsoluteConstraints(panelEPSRLocation[l++], panelEPSRLocation[l++], releasedEPSRImage[1].getWidth(null), releasedEPSRImage[1].getHeight(null)));
        acPlaylist.setToolTipText(getResource("toggle.playlist"));
        acPlaylist.setActionCommand(PlayerActionEvent.ACPLAYLIST);
        acPlaylist.setSelected(config.isPlaylistEnabled());
        acShuffle = new ActiveJToggleButton();
        acShuffle.setIcon(new ImageIcon(releasedEPSRImage[2]));
        acShuffle.setSelectedIcon(new ImageIcon(pressedEPSRImage[2]));
        acShuffle.setPressedIcon(new ImageIcon(pressedEPSRImage[2]));
        acShuffle.setConstraints(new AbsoluteConstraints(panelEPSRLocation[l++], panelEPSRLocation[l++], releasedEPSRImage[2].getWidth(null), releasedEPSRImage[2].getHeight(null)));
        acShuffle.setToolTipText(getResource("toggle.shuffle"));
        acShuffle.setActionCommand(PlayerActionEvent.ACSHUFFLE);
        acShuffle.setSelected(config.isShuffleEnabled());
        acRepeat = new ActiveJToggleButton();
        acRepeat.setIcon(new ImageIcon(releasedEPSRImage[3]));
        acRepeat.setSelectedIcon(new ImageIcon(pressedEPSRImage[3]));
        acRepeat.setPressedIcon(new ImageIcon(pressedEPSRImage[3]));
        acRepeat.setConstraints(new AbsoluteConstraints(panelEPSRLocation[l++], panelEPSRLocation[l++], releasedEPSRImage[3].getWidth(null), releasedEPSRImage[3].getHeight(null)));
        acRepeat.setToolTipText(getResource("toggle.repeat"));
        acRepeat.setActionCommand(PlayerActionEvent.ACREPEAT);
        acRepeat.setSelected(config.isRepeatEnabled());
    }

    /**
     * Instantiate Volume/Balance Panel with ActiveComponent.
     * @param vheight
     * @param bheight
     */
    private void setVolumeBalancePanel(int vheight, int bheight)
    {
        // Volume.
        acVolume = new ActiveJSlider();
        acVolume.setMinimum(0);
        acVolume.setMaximum(VOLUMEMAX);
        int volumeValue = config.getVolume();
        if (volumeValue < 0) volumeValue = (int) VOLUMEMAX / 2;
        acVolume.setValue(volumeValue);
        acVolume.setToolTipText(getResource("slider.volume"));
        int l = 0;
        for (int k = 0; k < volumeImage.length; k++)
        {
            //volumeImage[k] = (new Taftb(fakeIndex, imVolume, 68, 13, 2, "" + fakeIndex.charAt(k))).getBanner();
            volumeImage[k] = (new Taftb(fakeIndex, imVolume, imVolume.getWidth(null), 13, 2, "" + fakeIndex.charAt(k))).getBanner();
        }
        if (volumeImage[0].getHeight(null) > releasedVolumeImage[0].getHeight(null))
        {
            acVolume.setConstraints(new AbsoluteConstraints(volumeBarLocation[l++], volumeBarLocation[l++], volumeImage[0].getWidth(null), volumeImage[0].getHeight(null)));
        }
        else
        {
            acVolume.setConstraints(new AbsoluteConstraints(volumeBarLocation[l++], volumeBarLocation[l++], volumeImage[0].getWidth(null), releasedVolumeImage[0].getHeight(null)));
        }
        ActiveSliderUI sUI = new ActiveSliderUI(acVolume);
        sUI.setThumbImage(releasedVolumeImage[0]);
        sUI.setThumbPressedImage(pressedVolumeImage[0]);
        sUI.setBackgroundImages(volumeImage);
        if (vheight < 0) vheight = 0;
        sUI.forceThumbHeight(vheight);
        sUI.setThumbXOffset(0);
        sUI.setThumbYOffset(1);
        acVolume.setUI(sUI);
        // Balance
        acBalance = new ActiveJSlider();
        acBalance.setMinimum(-BALANCEMAX);
        acBalance.setMaximum(BALANCEMAX);
        acBalance.setValue(0);
        acBalance.setToolTipText(getResource("slider.balance"));
        Image cropBalance = new BufferedImage(38, 418, BufferedImage.TYPE_INT_RGB);
        Graphics g = cropBalance.getGraphics();
        g.drawImage(imBalance, 0, 0, 38, 418, 9, 0, 9 + 38, 0 + 418, null);
        for (int k = 0; k < balanceImage.length; k++)
        {
            balanceImage[k] = (new Taftb(fakeIndex, cropBalance, 38, 13, 2, "" + fakeIndex.charAt(k))).getBanner();
        }
        l = 0;
        if (balanceImage[0].getHeight(null) > releasedBalanceImage[0].getHeight(null))
        {
            acBalance.setConstraints(new AbsoluteConstraints(balanceBarLocation[l++], balanceBarLocation[l++], balanceImage[0].getWidth(null), balanceImage[0].getHeight(null)));
        }
        else
        {
            acBalance.setConstraints(new AbsoluteConstraints(balanceBarLocation[l++], balanceBarLocation[l++], balanceImage[0].getWidth(null), releasedBalanceImage[0].getHeight(null)));
        }
        sUI = new ActiveSliderUI(acBalance);
        sUI.setThumbImage(releasedBalanceImage[0]);
        sUI.setThumbPressedImage(pressedBalanceImage[0]);
        sUI.setBackgroundImages(balanceImage);
        if (bheight < 0) bheight = 0;
        sUI.forceThumbHeight(bheight);
        sUI.setThumbXOffset(1);
        sUI.setThumbYOffset(1);
        acBalance.setUI(sUI);
    }

    /**
     * Instantiate Title Panel with ActiveComponent.
     */
    protected void setTitleBarPanel()
    {
        int l = 0;
        acTitleBar = new ActiveJBar();
        ImageBorder border = new ImageBorder();
        border.setImage(releasedTitleIm[0]);
        acTitleBar.setBorder(border);
        acTitleBar.setConstraints(new AbsoluteConstraints(titleBarLocation[l++], titleBarLocation[l++], releasedTitleIm[0].getWidth(null), releasedTitleIm[0].getHeight(null)));
    }

    /**
     * Instantiate Exit Panel with ActiveComponent.
     */
    protected void setExitPanel()
    {
        int l = 0;
        acExit = new ActiveJButton();
        acExit.setIcon(new ImageIcon(releasedExitIm[0]));
        acExit.setPressedIcon(new ImageIcon(pressedExitIm[0]));
        acExit.setConstraints(new AbsoluteConstraints(exitLocation[l++], exitLocation[l++], releasedExitIm[0].getWidth(null), releasedExitIm[0].getHeight(null)));
        acExit.setToolTipText(getResource("button.exit"));
        acExit.setActionCommand(PlayerActionEvent.ACEXIT);
    }

    /**
     * Instantiate Minimize Panel with ActiveComponent.
     */
    protected void setMinimizePanel()
    {
        int l = 0;
        acMinimize = new ActiveJButton();
        acMinimize.setIcon(new ImageIcon(releasedMinimizeIm[0]));
        acMinimize.setPressedIcon(new ImageIcon(pressedMinimizeIm[0]));
        acMinimize.setConstraints(new AbsoluteConstraints(minimizeLocation[l++], minimizeLocation[l++], releasedMinimizeIm[0].getWidth(null), releasedMinimizeIm[0].getHeight(null)));
        acMinimize.setToolTipText(getResource("button.minimize"));
        acMinimize.setActionCommand(PlayerActionEvent.ACMINIMIZE);
    }

    /**
     * Instantiate Mono/Stereo panel. 
     */
    private void setMonoStereoPanel()
    {
        acMonoIcon = new ActiveJIcon();
        ImageIcon[] mono = { new ImageIcon(passiveModeImage[1]), new ImageIcon(activeModeImage[1]) };
        acMonoIcon.setIcons(mono);
        acMonoIcon.setIcon(0);
        acMonoIcon.setConstraints(new AbsoluteConstraints(monoLocation[0], monoLocation[1], passiveModeImage[1].getWidth(null), passiveModeImage[1].getHeight(null)));
        acStereoIcon = new ActiveJIcon();
        ImageIcon[] stereo = { new ImageIcon(passiveModeImage[0]), new ImageIcon(activeModeImage[0]) };
        acStereoIcon.setIcons(stereo);
        acStereoIcon.setIcon(0);
        acStereoIcon.setConstraints(new AbsoluteConstraints(stereoLocation[0], stereoLocation[1], passiveModeImage[0].getWidth(null), passiveModeImage[0].getHeight(null)));
    }

    /**
     * Initialize Spectrum/Time analyzer.
     */
    private void setAnalyzerPanel()
    {
        String javaVersion = System.getProperty("java.version");
        if ((javaVersion != null) && ((javaVersion.startsWith("1.3"))) || (javaVersion.startsWith("1.4")))
        {
            log.info("DSP disabled for JRE " + javaVersion);
        }
        else if (!dspEnabled)
        {
            log.info("DSP disabled");
        }
        else
        {
            if (analyzer == null) analyzer = new SpectrumTimeAnalyzer();
            String visualMode = config.getVisualMode();
            if ((visualMode != null) && (visualMode.length() > 0))
            {
                if (visualMode.equalsIgnoreCase("off")) analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_OFF);
                else if (visualMode.equalsIgnoreCase("oscillo")) analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE);
                else analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
            }
            else analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
            analyzer.setSpectrumAnalyserBandCount(19);
            analyzer.setVisColor(viscolor);
            analyzer.setLocation(visualLocation[0], visualLocation[1]);
            analyzer.setSize(visualSize[0], visualSize[1]);
            analyzer.setSpectrumAnalyserDecay(0.05f);
            int fps = SpectrumTimeAnalyzer.DEFAULT_FPS;
            analyzer.setFps(fps);
            analyzer.setPeakDelay((int) (fps * SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO));
            analyzer.setConstraints(new AbsoluteConstraints(visualLocation[0], visualLocation[1], visualSize[0], visualSize[1]));
            analyzer.setToolTipText(getResource("panel.analyzer"));
        }
    }

    /**
     * Instantiate PosBar Panel with ActiveComponent.
     */
    protected void setPosBarPanel()
    {
        int l = 0;
        Image posBackground = new BufferedImage(248, 10, BufferedImage.TYPE_INT_RGB);
        posBackground.getGraphics().drawImage(imPosBar, 0, 0, 248, 10, 0, 0, 248, 10, null);
        acPosBar = new ActiveJSlider();
        acPosBar.setMinimum(0);
        acPosBar.setMaximum(POSBARMAX);
        acPosBar.setValue(0);
        acPosBar.setOrientation(JSlider.HORIZONTAL);
        acPosBar.setConstraints(new AbsoluteConstraints(posBarLocation[l++], posBarLocation[l++], 248, releasedPosIm[0].getHeight(null)));
        ActiveSliderUI sUI = new ActiveSliderUI(acPosBar);
        Image[] back = { posBackground };
        sUI.setBackgroundImages(back);
        sUI.setThumbXOffset(0);
        sUI.setThumbYOffset(0);
        sUI.setThumbImage(releasedPosIm[0]);
        sUI.setThumbPressedImage(pressedPosIm[0]);
        acPosBar.setUI(sUI);
        acPosBar.setToolTipText(getResource("slider.seek"));
    }

    /**
     * Set sliders for equalizer.
     */
    private void setSliderPanel()
    {
        releasedSliderImage[0] = new BufferedImage(12, 11, BufferedImage.TYPE_INT_RGB);
        Graphics g = releasedSliderImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, 12, 11, 0, 164, 0 + 12, 164 + 11, null);
        pressedSliderImage[0] = new BufferedImage(10, 11, BufferedImage.TYPE_INT_RGB);
        g = pressedSliderImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, 11, 11, 0, 176, 0 + 11, 176 + 11, null);
        for (int k = 0; k < sliderImage.length / 2; k++)
        {
            sliderImage[k] = new BufferedImage(13, 63, BufferedImage.TYPE_INT_RGB);
            g = sliderImage[k].getGraphics();
            g.drawImage(imSliders, 0, 0, 13, 63, k * 15, 0, k * 15 + 13, 0 + 63, null);
        }
        for (int k = 0; k < sliderImage.length / 2; k++)
        {
            sliderImage[k + (sliderImage.length / 2)] = new BufferedImage(13, 63, BufferedImage.TYPE_INT_RGB);
            g = sliderImage[k + (sliderImage.length / 2)].getGraphics();
            g.drawImage(imSliders, 0, 0, 13, 63, k * 15, 65, k * 15 + 13, 65 + 63, null);
        }
        // Setup sliders
        for (int i = 0; i < acSlider.length; i++)
        {
            sliderLocation[i][0] = sliderBarLocation[i][0] + 1;
            sliderLocation[i][1] = sliderBarLocation[i][1] + 1;// + deltaSlider * gainEqValue[i] / maxEqGain;
            acSlider[i] = new ActiveJSlider();
            acSlider[i].setMinimum(0);
            acSlider[i].setMaximum(100);
            acSlider[i].setValue(50);
            acSlider[i].setOrientation(JSlider.VERTICAL);
            ActiveSliderUI sUI = new ActiveSliderUI(acSlider[i]);
            sUI.setThumbImage(releasedSliderImage[0]);
            sUI.setThumbPressedImage(pressedSliderImage[0]);
            sUI.setBackgroundImages(sliderImage);
            sUI.setThumbXOffset(1);
            sUI.setThumbYOffset(-1);
            acSlider[i].setUI(sUI);
            acSlider[i].setConstraints(new AbsoluteConstraints(sliderLocation[i][0], sliderLocation[i][1], releasedSliderImage[0].getWidth(null), sliderImage[0].getHeight(null)));
        }
        acSlider[0].setEnabled(false);
    }

    /**
     * Set On/Off and Auto checkbox.
     */
    public void setOnOffAutoPanel()
    {
        // On/Off
        int w = 24, h = 12;
        releasedOAImage[0] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = releasedOAImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 10, 119, 10 + w, 119 + h, null);
        pressedOAImage[0] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g = pressedOAImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 69, 119, 69 + w, 119 + h, null);
        acOnOff = new ActiveJToggleButton();
        acOnOff.setIcon(new ImageIcon(releasedOAImage[0]));
        acOnOff.setSelectedIcon(new ImageIcon(pressedOAImage[0]));
        acOnOff.setPressedIcon(new ImageIcon(pressedOAImage[0]));
        acOnOff.setSelected(config.isEqualizerOn());
        acOnOff.setConstraints(new AbsoluteConstraints(panelOALocation[0], panelOALocation[1], releasedOAImage[0].getWidth(null), releasedOAImage[0].getHeight(null)));
        acOnOff.setToolTipText(getResource("equalizer.toggle.onoff"));
        acOnOff.setActionCommand(PlayerActionEvent.ACEQONOFF);
        // Auto
        w = 34;
        h = 12;
        releasedOAImage[1] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g = releasedOAImage[1].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 34, 119, 34 + w, 119 + h, null);
        pressedOAImage[1] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g = pressedOAImage[1].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 93, 119, 93 + w, 119 + h, null);
        acAuto = new ActiveJToggleButton();
        acAuto.setIcon(new ImageIcon(releasedOAImage[1]));
        acAuto.setPressedIcon(new ImageIcon(pressedOAImage[1]));
        acAuto.setSelectedIcon(new ImageIcon(pressedOAImage[1]));
        acAuto.setConstraints(new AbsoluteConstraints(panelOALocation[2], panelOALocation[3], releasedOAImage[1].getWidth(null), releasedOAImage[1].getHeight(null)));
        acAuto.setToolTipText(getResource("equalizer.toggle.auto"));
        acAuto.setActionCommand(PlayerActionEvent.ACEQAUTO);
    }

    /**
     * Set presets button.
     */
    public void setPresetsPanel()
    {
        int w = 44, h = 12;
        releasedPresetsImage[0] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = releasedPresetsImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 224, 164, 224 + w, 164 + h, null);
        pressedPresetsImage[0] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g = pressedPresetsImage[0].getGraphics();
        g.drawImage(imFullEqualizer, 0, 0, w, h, 224, 176, 224 + w, 176 + h, null);
        acPresets = new ActiveJButton();
        acPresets.setIcon(new ImageIcon(releasedPresetsImage[0]));
        acPresets.setPressedIcon(new ImageIcon(pressedPresetsImage[0]));
        acPresets.setConstraints(new AbsoluteConstraints(panelPresetsLocation[0], panelPresetsLocation[1], releasedPresetsImage[0].getWidth(null), releasedPresetsImage[0].getHeight(null)));
        acPresets.setToolTipText(getResource("equalizer.button.presets"));
        acPresets.setActionCommand(PlayerActionEvent.ACEQPRESETS);
    }

    /**
     * Instantiate equalizer spline panel.
     */
    public void setSplinePanel()
    {
        int w = panelSplineLocation[2];
        int h = panelSplineLocation[3];
        splineImage = null;
        splineBarImage = null;
        spline = null;
        if (imFullEqualizer.getHeight(null) > 294)
        {
            splineImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            splineBarImage = new BufferedImage(w, 1, BufferedImage.TYPE_INT_RGB);
            splineImage.getGraphics().drawImage(imFullEqualizer, 0, 0, w, h, 0, 294, 0 + w, 294 + h, null);
            splineBarImage.getGraphics().drawImage(imFullEqualizer, 0, 0, w, 1, 0, 294 + h + 1, 0 + w, 294 + h + 1 + 1, null);
            spline = new SplinePanel();
            spline.setBackgroundImage(splineImage);
            spline.setBarImage(splineBarImage);
            int[] pixels = new int[1 * h];
            PixelGrabber pg = new PixelGrabber(imFullEqualizer, 115, 294, 1, h, pixels, 0, 1);
            try
            {
                pg.grabPixels();
            }
            catch (InterruptedException e)
            {
                log.debug(e);
            }
            Color[] colors = new Color[h];
            for (int i = 0; i < h; i++)
            {
                int c = pixels[i];
                int red = (c & 0x00ff0000) >> 16;
                int green = (c & 0x0000ff00) >> 8;
                int blue = c & 0x000000ff;
                colors[i] = new Color(red, green, blue);
            }
            spline.setGradient(colors);
            spline.setConstraints(new AbsoluteConstraints(panelSplineLocation[0], panelSplineLocation[1], panelSplineLocation[2], panelSplineLocation[3]));
        }
    }

    /**
     * Instantiate playlist panel.
     */
    public void setPlaylistPanel()
    {
        playlist = new PlaylistUIDelegate();
        Image titleCenter = new BufferedImage(100, 20, BufferedImage.TYPE_INT_RGB);
        titleCenter.getGraphics().drawImage(imPlaylist, 0, 0, 100, 20, 26, 0, 126, 20, null);
        playlist.setTitleCenterImage(titleCenter);
        Image titleLeft = new BufferedImage(25, 20, BufferedImage.TYPE_INT_RGB);
        titleLeft.getGraphics().drawImage(imPlaylist, 0, 0, 25, 20, 0, 0, 25, 20, null);
        playlist.setTitleLeftImage(titleLeft);
        Image titleStretch = new BufferedImage(25, 20, BufferedImage.TYPE_INT_RGB);
        titleStretch.getGraphics().drawImage(imPlaylist, 0, 0, 25, 20, 127, 0, 152, 20, null);
        playlist.setTitleStretchImage(titleStretch);
        Image titleRight = new BufferedImage(25, 20, BufferedImage.TYPE_INT_RGB);
        titleRight.getGraphics().drawImage(imPlaylist, 0, 0, 25, 20, 153, 0, 178, 20, null);
        playlist.setTitleRightImage(titleRight);
        Image btmLeft = new BufferedImage(125, 38, BufferedImage.TYPE_INT_RGB);
        btmLeft.getGraphics().drawImage(imPlaylist, 0, 0, 125, 38, 0, 72, 125, 110, null);
        playlist.setBottomLeftImage(btmLeft);
        Image btmRight = new BufferedImage(150, 38, BufferedImage.TYPE_INT_RGB);
        btmRight.getGraphics().drawImage(imPlaylist, 0, 0, 150, 38, 126, 72, 276, 110, null);
        playlist.setBottomRightImage(btmRight);
        Image bodyLeft = new BufferedImage(12, 28, BufferedImage.TYPE_INT_RGB);
        bodyLeft.getGraphics().drawImage(imPlaylist, 0, 0, 12, 28, 0, 42, 12, 70, null);
        playlist.setLeftImage(bodyLeft);
        Image bodyRight = new BufferedImage(20, 28, BufferedImage.TYPE_INT_RGB);
        bodyRight.getGraphics().drawImage(imPlaylist, 0, 0, 20, 28, 31, 42, 51, 70, null);
        playlist.setRightImage(bodyRight);
        // Parse color
        plEdit = plEdit.toLowerCase();
        ByteArrayInputStream in = new ByteArrayInputStream(plEdit.getBytes());
        BufferedReader lin = new BufferedReader(new InputStreamReader(in));
        try
        {
            for (;;)
            {
                String line = lin.readLine();
                if (line == null) break;
                if ((line.toLowerCase()).startsWith("normalbg")) playlist.setBackgroundColor(parsePlEditColor(line));
                else if ((line.toLowerCase()).startsWith("normal")) playlist.setNormalColor(parsePlEditColor(line));
                else if ((line.toLowerCase()).startsWith("current")) playlist.setCurrentColor(parsePlEditColor(line));
                else if ((line.toLowerCase()).startsWith("selectedbg")) playlist.setSelectedBackgroundColor(parsePlEditColor(line));
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        finally
        {
            try
            {
                if (in != null) in.close();
            }
            catch (IOException e)
            {
            }
        }
        // Playlist slider.
        acPlSlider = new ActiveJSlider();
        acPlSlider.setOrientation(JSlider.VERTICAL);
        acPlSlider.setMinimum(0);
        acPlSlider.setMaximum(100);
        acPlSlider.setValue(100);
        ActiveSliderUI sUI = new ActiveSliderUI(acPlSlider);
        Image scrollBarReleased = new BufferedImage(8, 18, BufferedImage.TYPE_INT_RGB);
        scrollBarReleased.getGraphics().drawImage(imPlaylist, 0, 0, 8, 18, 52, 53, 52 + 8, 53 + 18, null);
        sUI.setThumbImage(scrollBarReleased);
        Image scrollBarClicked = new BufferedImage(8, 18, BufferedImage.TYPE_INT_RGB);
        scrollBarClicked.getGraphics().drawImage(imPlaylist, 0, 0, 8, 18, 61, 53, 61 + 8, 53 + 18, null);
        sUI.setThumbPressedImage(scrollBarClicked);
        Image sliderBackground = new BufferedImage(20, 58, BufferedImage.TYPE_INT_RGB);        
        sliderBackground.getGraphics().drawImage(bodyRight, 0, 0, null);
        sliderBackground.getGraphics().drawImage(bodyRight, 0, 28, null);
        sliderBackground.getGraphics().drawImage(bodyRight, 0, 30, null);
        Image[] background = { sliderBackground };
        sUI.setBackgroundImages(background);
        sUI.setThumbXOffset(5);
        acPlSlider.setUI(sUI);
        acPlSlider.setConstraints(new AbsoluteConstraints(plSliderLocation[0], plSliderLocation[1], 20, 58));
        // Up/Down scroll buttons
        acPlUp = new ActiveJButton();
        Image upScrollButton = new BufferedImage(8, 4, BufferedImage.TYPE_INT_RGB);
        upScrollButton.getGraphics().drawImage(imPlaylist, 0, 0, 8, 4, 261, 75, 269, 79, null);
        acPlUp.setIcon(new ImageIcon(upScrollButton));
        acPlUp.setPressedIcon(new ImageIcon(upScrollButton));
        acPlUp.setConstraints(new AbsoluteConstraints(WinWidth - 15, WinHeight - 35, 8, 4));
        acPlUp.setActionCommand(PlayerActionEvent.ACPLUP);
        acPlDown = new ActiveJButton();
        Image downScrollButton = new BufferedImage(8, 4, BufferedImage.TYPE_INT_RGB);
        downScrollButton.getGraphics().drawImage(imPlaylist, 0, 0, 8, 4, 261, 80, 269, 84, null);
        acPlDown.setIcon(new ImageIcon(downScrollButton));
        acPlDown.setPressedIcon(new ImageIcon(downScrollButton));
        acPlDown.setConstraints(new AbsoluteConstraints(WinWidth - 15, WinHeight - 30, 8, 4));
        acPlDown.setActionCommand(PlayerActionEvent.ACPLDOWN);
        // Playlist AddFile/AddDir/AddURL buttons
        int w = 22;
        int h = 18;
        Image addButtonImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        addButtonImage.getGraphics().drawImage(imPlaylist, 0, 0, w, h, 14, 80, 14 + w, 80 + h, null);
        acPlAdd = new ActiveJButton();
        acPlAdd.setIcon(new ImageIcon(addButtonImage));
        acPlAdd.setPressedIcon(new ImageIcon(addButtonImage));
        acPlAdd.setActionCommand(PlayerActionEvent.ACPLADDPOPUP);
        acPlAdd.setConstraints(new AbsoluteConstraints(plAddLocation[0], plAddLocation[1], w, h));
        ActiveJButton acPlAddFile = createPLButton(0, 149);
        acPlAddFile.setActionCommand(PlayerActionEvent.ACPLADDFILE);
        ActiveJButton acPlAddDir = createPLButton(0, 130);
        acPlAddDir.setActionCommand(PlayerActionEvent.ACPLADDDIR);
        ActiveJButton acPlAddURL = createPLButton(0, 111);
        acPlAddURL.setActionCommand(PlayerActionEvent.ACPLADDURL);
        acPlAddPopup = new ActiveJPopup();
        ActiveJButton[] addbuttons = { acPlAddURL, acPlAddDir, acPlAddFile };
        acPlAddPopup.setItems(addbuttons);
        acPlAddPopup.setConstraints(new AbsoluteConstraints(plAddPopupArea[0], plAddPopupArea[1], plAddPopupArea[2], plAddPopupArea[3]));
        // Playlist RemoveMisc/RemoveSelection/Crop/RemoveAll buttons
        Image removeButtonImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        removeButtonImage.getGraphics().drawImage(imPlaylist, 0, 0, w, h, 14 + 30, 80, 14 + 30 + w, 80 + h, null);
        acPlRemove = new ActiveJButton();
        acPlRemove.setIcon(new ImageIcon(removeButtonImage));
        acPlRemove.setPressedIcon(new ImageIcon(removeButtonImage));
        acPlRemove.setActionCommand(PlayerActionEvent.ACPLREMOVEPOPUP);
        acPlRemove.setConstraints(new AbsoluteConstraints(plRemoveLocation[0], plRemoveLocation[1], w, h));
        ActiveJButton acPlRemoveMisc = createPLButton(54, 168);
        acPlRemoveMisc.setActionCommand(PlayerActionEvent.ACPLREMOVEMISC);
        ActiveJButton acPlRemoveSel = createPLButton(54, 149);
        acPlRemoveSel.setActionCommand(PlayerActionEvent.ACPLREMOVESEL);
        ActiveJButton acPlRemoveCrop = createPLButton(54, 130);
        acPlRemoveCrop.setActionCommand(PlayerActionEvent.ACPLREMOVECROP);
        ActiveJButton acPlRemoveAll = createPLButton(54, 111);
        acPlRemoveAll.setActionCommand(PlayerActionEvent.ACPLREMOVEALL);
        acPlRemovePopup = new ActiveJPopup();
        ActiveJButton[] rembuttons = { acPlRemoveMisc, acPlRemoveAll, acPlRemoveCrop, acPlRemoveSel };
        acPlRemovePopup.setItems(rembuttons);
        acPlRemovePopup.setConstraints(new AbsoluteConstraints(plRemovePopupArea[0], plRemovePopupArea[1], plRemovePopupArea[2], plRemovePopupArea[3]));
        // Playlist SelAll/SelZero/SelInv buttons
        Image selButtonImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        selButtonImage.getGraphics().drawImage(imPlaylist, 0, 0, w, h, 14 + 60, 80, 14 + 60 + w, 80 + h, null);
        acPlSelect = new ActiveJButton();
        acPlSelect.setIcon(new ImageIcon(selButtonImage));
        acPlSelect.setPressedIcon(new ImageIcon(selButtonImage));
        acPlSelect.setActionCommand(PlayerActionEvent.ACPLSELPOPUP);
        acPlSelect.setConstraints(new AbsoluteConstraints(plSelectLocation[0], plSelectLocation[1], w, h));
        ActiveJButton acPlSelectAll = createPLButton(104, 149);
        acPlSelectAll.setActionCommand(PlayerActionEvent.ACPLSELALL);
        ActiveJButton acPlSelectZero = createPLButton(104, 130);
        acPlSelectZero.setActionCommand(PlayerActionEvent.ACPLSELZERO);
        ActiveJButton acPlSelectInv = createPLButton(104, 111);
        acPlSelectInv.setActionCommand(PlayerActionEvent.ACPLSELINV);
        acPlSelectPopup = new ActiveJPopup();
        ActiveJButton[] selbuttons = { acPlSelectInv, acPlSelectZero, acPlSelectAll };
        acPlSelectPopup.setItems(selbuttons);
        acPlSelectPopup.setConstraints(new AbsoluteConstraints(plSelectPopupArea[0], plSelectPopupArea[1], plSelectPopupArea[2], plSelectPopupArea[3]));
        // Playlist MiscOpts/MiscFile/MiscSort buttons
        Image miscButtonImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        miscButtonImage.getGraphics().drawImage(imPlaylist, 0, 0, w, h, 14 + 89, 80, 14 + 89 + w, 80 + h, null);
        acPlMisc = new ActiveJButton();
        acPlMisc.setIcon(new ImageIcon(miscButtonImage));
        acPlMisc.setPressedIcon(new ImageIcon(miscButtonImage));
        acPlMisc.setActionCommand(PlayerActionEvent.ACPLMISCPOPUP);
        acPlMisc.setConstraints(new AbsoluteConstraints(plMiscLocation[0], plMiscLocation[1], w, h));
        ActiveJButton acPlMiscOpts = createPLButton(154, 149);
        acPlMiscOpts.setActionCommand(PlayerActionEvent.ACPLMISCOPTS);
        ActiveJButton acPlMiscFile = createPLButton(154, 130);
        acPlMiscFile.setActionCommand(PlayerActionEvent.ACPLMISCFILE);
        ActiveJButton acPlMiscSort = createPLButton(154, 111);
        acPlMiscSort.setActionCommand(PlayerActionEvent.ACPLMISCSORT);
        acPlMiscPopup = new ActiveJPopup();
        ActiveJButton[] miscbuttons = { acPlMiscSort, acPlMiscFile, acPlMiscOpts };
        acPlMiscPopup.setItems(miscbuttons);
        acPlMiscPopup.setConstraints(new AbsoluteConstraints(plMiscPopupArea[0], plMiscPopupArea[1], plMiscPopupArea[2], plMiscPopupArea[3]));
        // Playlist ListLoad/ListSave/ListNew buttons
        Image listButtonImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        listButtonImage.getGraphics().drawImage(imPlaylist, 0, 0, w, h, 14 + 215, 80, 14 + 215 + w, 80 + h, null);
        acPlList = new ActiveJButton();
        acPlList.setIcon(new ImageIcon(listButtonImage));
        acPlList.setPressedIcon(new ImageIcon(listButtonImage));
        acPlList.setActionCommand(PlayerActionEvent.ACPLLISTPOPUP);
        acPlList.setConstraints(new AbsoluteConstraints(plListLocation[0], plListLocation[1], w, h));
        ActiveJButton acPlListLoad = createPLButton(204, 149);
        acPlListLoad.setActionCommand(PlayerActionEvent.ACPLLISTLOAD);
        ActiveJButton acPlListSave = createPLButton(204, 130);
        acPlListSave.setActionCommand(PlayerActionEvent.ACPLLISTSAVE);
        ActiveJButton acPlListNew = createPLButton(204, 111);
        acPlListNew.setActionCommand(PlayerActionEvent.ACPLLISTNEW);
        acPlListPopup = new ActiveJPopup();
        ActiveJButton[] listbuttons = { acPlListNew, acPlListSave, acPlListLoad };
        acPlListPopup.setItems(listbuttons);
        acPlListPopup.setConstraints(new AbsoluteConstraints(plListPopupArea[0], plListPopupArea[1], plListPopupArea[2], plListPopupArea[3]));
    }

    /**
     * Create Playlist buttons.
     * @param sx
     * @param sy
     * @return
     */
    private ActiveJButton createPLButton(int sx, int sy)
    {
        Image normal = new BufferedImage(22, 18, BufferedImage.TYPE_INT_RGB);
        Image clicked = new BufferedImage(22, 18, BufferedImage.TYPE_INT_RGB);
        Graphics g = normal.getGraphics();
        g.drawImage(imPlaylist, 0, 0, 22, 18, sx, sy, sx + 22, sy + 18, null);
        sx += 23;
        g = clicked.getGraphics();
        g.drawImage(imPlaylist, 0, 0, 22, 18, sx, sy, sx + 22, sy + 18, null);
        ActiveJButton comp = new ActiveJButton();
        comp.setIcon(new ImageIcon(normal));
        comp.setPressedIcon(new ImageIcon(clicked));
        comp.setRolloverIcon(new ImageIcon(clicked));
        comp.setRolloverEnabled(true);
        return comp;
    }

    /**
     * Parse playlist colors.
     * @param line
     * @return
     * @throws Exception
     */
    private Color parsePlEditColor(String line) throws Exception
    {
        int pos = line.indexOf("#");
        if (pos == -1)
        {
            pos = line.indexOf("=");
            if (pos == -1) throw new Exception("Can not parse color!");
        }
        line = line.substring(pos + 1);
        int r = Integer.parseInt(line.substring(0, 2), 16);
        int g = Integer.parseInt(line.substring(2, 4), 16);
        int b = Integer.parseInt(line.substring(4), 16);
        return new Color(r, g, b);
    }

    /**
     * Crop Panel Features from image file.
     * @param releasedImage
     * @param releasedPanel
     * @param pressedImage
     * @param pressedPanel
     * @param imPanel
     */
    public void readPanel(Image[] releasedImage, int[] releasedPanel, Image[] pressedImage, int[] pressedPanel, Image imPanel)
    {
        int xul, yul, xld, yld;
        int j = 0;
        if (releasedImage != null)
        {
            for (int i = 0; i < releasedImage.length; i++)
            {
                releasedImage[i] = new BufferedImage(releasedPanel[j + 2], releasedPanel[j + 3], BufferedImage.TYPE_INT_RGB);
                xul = releasedPanel[j];
                yul = releasedPanel[j + 1];
                xld = releasedPanel[j] + releasedPanel[j + 2];
                yld = releasedPanel[j + 1] + releasedPanel[j + 3];
                (releasedImage[i].getGraphics()).drawImage(imPanel, 0, 0, releasedPanel[j + 2], releasedPanel[j + 3], xul, yul, xld, yld, null);
                j = j + 4;
            }
        }
        j = 0;
        if (pressedImage != null)
        {
            for (int i = 0; i < pressedImage.length; i++)
            {
                pressedImage[i] = new BufferedImage(pressedPanel[j + 2], pressedPanel[j + 3], BufferedImage.TYPE_INT_RGB);
                xul = pressedPanel[j];
                yul = pressedPanel[j + 1];
                xld = pressedPanel[j] + pressedPanel[j + 2];
                yld = pressedPanel[j + 1] + pressedPanel[j + 3];
                (pressedImage[i].getGraphics()).drawImage(imPanel, 0, 0, pressedPanel[j + 2], pressedPanel[j + 3], xul, yul, xld, yld, null);
                j = j + 4;
            }
        }
    }

    public ActiveJButton getAcEject()
    {
        return acEject;
    }

    public ActiveJButton getAcNext()
    {
        return acNext;
    }

    public ActiveJButton getAcPause()
    {
        return acPause;
    }

    public ActiveJButton getAcPlay()
    {
        return acPlay;
    }

    public ActiveJButton getAcPrevious()
    {
        return acPrevious;
    }

    public ActiveJButton getAcStop()
    {
        return acStop;
    }

    public ActiveJButton getAcExit()
    {
        return acExit;
    }

    public ActiveJButton getAcMinimize()
    {
        return acMinimize;
    }

    public ActiveJBar getAcTitleBar()
    {
        return acTitleBar;
    }

    public ActiveJLabel getAcTitleLabel()
    {
        return acTitleLabel;
    }

    public ActiveJLabel getAcSampleRateLabel()
    {
        return acSampleRateLabel;
    }

    public ActiveJLabel getAcBitRateLabel()
    {
        return acBitRateLabel;
    }

    public String getSkinVersion()
    {
        return skinVersion;
    }

    public void setSkinVersion(String skinVersion)
    {
        this.skinVersion = skinVersion;
    }

    public ActiveJToggleButton getAcEqualizer()
    {
        return acEqualizer;
    }

    public ActiveJToggleButton getAcPlaylist()
    {
        return acPlaylist;
    }

    public ActiveJToggleButton getAcRepeat()
    {
        return acRepeat;
    }

    public ActiveJToggleButton getAcShuffle()
    {
        return acShuffle;
    }

    public ActiveJSlider getAcVolume()
    {
        return acVolume;
    }

    public ActiveJSlider getAcBalance()
    {
        return acBalance;
    }

    public ActiveJIcon getAcMonoIcon()
    {
        return acMonoIcon;
    }

    public ActiveJIcon getAcStereoIcon()
    {
        return acStereoIcon;
    }

    public ActiveJSlider getAcPosBar()
    {
        return acPosBar;
    }

    public ActiveJIcon getAcPlayIcon()
    {
        return acPlayIcon;
    }

    public ActiveJIcon getAcTimeIcon()
    {
        return acTimeIcon;
    }

    public ActiveJNumberLabel getAcMinuteH()
    {
        return acMinuteH;
    }

    public ActiveJNumberLabel getAcMinuteL()
    {
        return acMinuteL;
    }

    public ActiveJNumberLabel getAcSecondH()
    {
        return acSecondH;
    }

    public ActiveJNumberLabel getAcSecondL()
    {
        return acSecondL;
    }

    public SpectrumTimeAnalyzer getAcAnalyzer()
    {
        return analyzer;
    }

    public ActiveJButton getAcEqPresets()
    {
        return acPresets;
    }

    public ActiveJToggleButton getAcEqOnOff()
    {
        return acOnOff;
    }

    public ActiveJToggleButton getAcEqAuto()
    {
        return acAuto;
    }

    public ActiveJSlider[] getAcEqSliders()
    {
        return acSlider;
    }

    public ActiveJSlider getAcPlSlider()
    {
        return acPlSlider;
    }

    public ActiveJButton getAcPlUp()
    {
        return acPlUp;
    }

    public ActiveJButton getAcPlDown()
    {
        return acPlDown;
    }

    public ActiveJButton getAcPlAdd()
    {
        return acPlAdd;
    }

    public ActiveJPopup getAcPlAddPopup()
    {
        return acPlAddPopup;
    }

    public ActiveJButton getAcPlRemove()
    {
        return acPlRemove;
    }

    public ActiveJPopup getAcPlRemovePopup()
    {
        return acPlRemovePopup;
    }

    public ActiveJButton getAcPlSelect()
    {
        return acPlSelect;
    }

    public ActiveJPopup getAcPlSelectPopup()
    {
        return acPlSelectPopup;
    }

    public ActiveJButton getAcPlMisc()
    {
        return acPlMisc;
    }

    public ActiveJPopup getAcPlMiscPopup()
    {
        return acPlMiscPopup;
    }

    public ActiveJButton getAcPlList()
    {
        return acPlList;
    }

    public ActiveJPopup getAcPlListPopup()
    {
        return acPlListPopup;
    }

    public SplinePanel getSpline()
    {
        return spline;
    }

    public PlaylistUIDelegate getPlaylistPanel()
    {
        return playlist;
    }

    /**
     * Return readme content from skin.
     * @return
     */
    public String getReadme()
    {
        return readme;
    }

    public int getMainWidth()
    {
        return WinWidth;
    }

    public int getMainHeight()
    {
        return WinHeight;
    }

    public Image getMainImage()
    {
        return imMain;
    }

    public Image getEqualizerImage()
    {
        return imEqualizer;
    }

    /**
     * Return visual colors from skin.
     * @return
     */
    public String getVisColors()
    {
        return viscolor;
    }

    public Config getConfig()
    {
        return config;
    }

    public void setConfig(Config config)
    {
        this.config = config;
    }
}
