/*
 * TagInfoFactory.
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
package javazoom.jlgui.player.amp.tag;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.player.amp.tag.ui.APEDialog;
import javazoom.jlgui.player.amp.tag.ui.EmptyDialog;
import javazoom.jlgui.player.amp.tag.ui.FlacDialog;
import javazoom.jlgui.player.amp.tag.ui.MpegDialog;
import javazoom.jlgui.player.amp.tag.ui.OggVorbisDialog;
import javazoom.jlgui.player.amp.tag.ui.TagInfoDialog;
import javazoom.jlgui.player.amp.util.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is a factory for TagInfo and TagInfoDialog.
 * It allows to any plug custom TagIngfo parser matching to TagInfo
 * interface.
 */
public class TagInfoFactory
{
    private static Log log = LogFactory.getLog(TagInfoFactory.class);
    private static TagInfoFactory instance = null;
    private Class MpegTagInfoClass = null;
    private Class VorbisTagInfoClass = null;
    private Class APETagInfoClass = null;
    private Class FlacTagInfoClass = null;
    private Config conf = null;

    private TagInfoFactory()
    {
        super();
        conf = Config.getInstance();
        String classname = conf.getMpegTagInfoClassName();
        MpegTagInfoClass = getTagInfoImpl(classname);
        if (MpegTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            MpegTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.MpegInfo");
        }
        classname = conf.getOggVorbisTagInfoClassName();
        VorbisTagInfoClass = getTagInfoImpl(classname);
        if (VorbisTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            VorbisTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.OggVorbisInfo");
        }
        classname = conf.getAPETagInfoClassName();
        APETagInfoClass = getTagInfoImpl(classname);
        if (APETagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            APETagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.APEInfo");
        }
        classname = conf.getFlacTagInfoClassName();
        FlacTagInfoClass = getTagInfoImpl(classname);
        if (FlacTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            FlacTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.FlacInfo");
        }
    }

    public static synchronized TagInfoFactory getInstance()
    {
        if (instance == null)
        {
            instance = new TagInfoFactory();
        }
        return instance;
    }

    /**
     * Return tag info from a given URL.
     *
     * @param location
     * @return TagInfo structure for given URL
     */
    public TagInfo getTagInfo(URL location)
    {
        TagInfo taginfo;
        try
        {
            taginfo = getTagInfoImplInstance(MpegTagInfoClass);
            taginfo.load(location);
        }
        catch (IOException ex)
        {
            log.debug(ex);
            taginfo = null;
        }
        catch (UnsupportedAudioFileException ex)
        {
            // Not Mpeg Format
            taginfo = null;
        }
        if (taginfo == null)
        {
            // Check Ogg Vorbis format.
            try
            {
                taginfo = getTagInfoImplInstance(VorbisTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Ogg Vorbis Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check APE format.
            try
            {
                taginfo = getTagInfoImplInstance(APETagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not APE Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check Flac format.
            try
            {
                taginfo = getTagInfoImplInstance(FlacTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Flac Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        return taginfo;
    }

    /**
     * Return tag info from a given String.
     *
     * @param location
     * @return TagInfo structure for given location
     */
    public TagInfo getTagInfo(String location)
    {
        if (Config.startWithProtocol(location))
        {
            try
            {
                return getTagInfo(new URL(location));
            }
            catch (MalformedURLException e)
            {
                return null;
            }
        }
        else
        {
            return getTagInfo(new File(location));
        }
    }

    /**
     * Get TagInfo for given file.
     *
     * @param location
     * @return TagInfo structure for given location
     */
    public TagInfo getTagInfo(File location)
    {
        TagInfo taginfo;
        // Check Mpeg format.
        try
        {
            taginfo = getTagInfoImplInstance(MpegTagInfoClass);
            taginfo.load(location);
        }
        catch (IOException ex)
        {
            log.debug(ex);
            taginfo = null;
        }
        catch (UnsupportedAudioFileException ex)
        {
            // Not Mpeg Format
            taginfo = null;
        }
        if (taginfo == null)
        {
            // Check Ogg Vorbis format.
            try
            {
                //taginfo = new OggVorbisInfo(location);
                taginfo = getTagInfoImplInstance(VorbisTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Ogg Vorbis Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check APE format.
            try
            {
                taginfo = getTagInfoImplInstance(APETagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not APE Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check Flac format.
            try
            {
                taginfo = getTagInfoImplInstance(FlacTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Flac Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        return taginfo;
    }

    /**
     * Return dialog (graphical) to display tag info.
     *
     * @param taginfo
     * @return TagInfoDialog for given TagInfo
     */
    public TagInfoDialog getTagInfoDialog(TagInfo taginfo)
    {
        TagInfoDialog dialog;
        if (taginfo != null)
        {
            if (taginfo instanceof OggVorbisInfo)
            {
                dialog = new OggVorbisDialog(conf.getTopParent(), "OggVorbis info", (OggVorbisInfo) taginfo);
            }
            else if (taginfo instanceof MpegInfo)
            {
                dialog = new MpegDialog(conf.getTopParent(), "Mpeg info", (MpegInfo) taginfo);
            }
            else if (taginfo instanceof APEInfo)
            {
                dialog = new APEDialog(conf.getTopParent(), "Ape info", (APEInfo) taginfo);
            }
            else if (taginfo instanceof FlacInfo)
            {
                dialog = new FlacDialog(conf.getTopParent(), "Flac info", (FlacInfo) taginfo);
            }
            else
            {
                dialog = new EmptyDialog(conf.getTopParent(), "No info", taginfo);
            }
        }
        else
        {
            dialog = new EmptyDialog(conf.getTopParent(), "No info", null);
        }
        return dialog;
    }

    /**
     * Load and check class implementation from classname.
     *
     * @param classname
     * @return TagInfo implementation for given class name
     */
    public Class getTagInfoImpl(String classname)
    {
        Class aClass = null;
        boolean interfaceFound = false;
        if (classname != null)
        {
            try
            {
                aClass = Class.forName(classname);
                Class superClass = aClass;
                // Looking for TagInfo interface implementation.
                while (superClass != null)
                {
                    Class[] interfaces = superClass.getInterfaces();
                    for (int i = 0; i < interfaces.length; i++)
                    {
                        if ((interfaces[i].getName()).equals("javazoom.jlgui.player.amp.tag.TagInfo"))
                        {
                            interfaceFound = true;
                            break;
                        }
                    }
                    if (interfaceFound) break;
                    superClass = superClass.getSuperclass();
                }
                if (interfaceFound) log.info(classname + " loaded");
                else log.info(classname + " not loaded");
            }
            catch (ClassNotFoundException e)
            {
                log.error("Error : " + classname + " : " + e.getMessage());
            }
        }
        return aClass;
    }

    /**
     * Return new instance of given class.
     *
     * @param aClass
     * @return TagInfo for given class
     */
    public TagInfo getTagInfoImplInstance(Class aClass)
    {
        TagInfo instance = null;
        if (aClass != null)
        {
            try
            {
                Class[] argsClass = new Class[] {};
                Constructor c = aClass.getConstructor(argsClass);
                instance = (TagInfo) (c.newInstance(null));
            }
            catch (Exception e)
            {
                log.error("Cannot Instanciate : " + aClass.getName() + " : " + e.getMessage());
            }
        }
        return instance;
    }
}
