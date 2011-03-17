/*
 * Config.
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
package javazoom.jlgui.player.amp.util;

import java.io.File;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javazoom.jlgui.player.amp.util.ini.Configuration;

/**
 * This class provides all parameters for jlGui coming from a file.
 */
public class Config
{
    public static String[] protocols = { "http:", "file:", "ftp:", "https:", "ftps:", "jar:" };
    public static String TAGINFO_POLICY_FILE = "file";
    public static String TAGINFO_POLICY_ALL = "all";
    public static String TAGINFO_POLICY_NONE = "none";
    private static String CONFIG_FILE_NAME = "jlgui.ini";
    private Configuration _config = null;
    // configuration keys
    private static final String LAST_URL = "last_url",
    LAST_DIR = "last_dir",
    ORIGINE_X = "origine_x",
    ORIGINE_Y = "origine_y",
    LAST_SKIN = "last_skin",
    LAST_SKIN_DIR = "last_skin_dir",
    EXTENSIONS = "allowed_extensions",
    PLAYLIST_IMPL = "playlist_impl",
    TAGINFO_MPEG_IMPL = "taginfo_mpeg_impl",
    TAGINFO_OGGVORBIS_IMPL = "taginfo_oggvorbis_impl",
    TAGINFO_APE_IMPL = "taginfo_ape_impl",
    TAGINFO_FLAC_IMPL = "taginfo_flac_impl",
    LAST_PLAYLIST = "last_playlist",
    PROXY_SERVER = "proxy_server",
    PROXY_PORT = "proxy_port",
    PROXY_LOGIN = "proxy_login",
    PROXY_PASSWORD = "proxy_password",
    PLAYLIST_ENABLED = "playlist_enabled",
    SHUFFLE_ENABLED = "shuffle_enabled",
    REPEAT_ENABLED = "repeat_enabled",
    EQUALIZER_ENABLED = "equalizer_enabled",
    EQUALIZER_ON = "equalizer_on",
    EQUALIZER_AUTO = "equalizer_auto",
    LAST_EQUALIZER = "last_equalizer",
    SCREEN_LIMIT = "screen_limit",
    TAGINFO_POLICY = "taginfo_policy",
    VOLUME_VALUE = "volume_value",
    AUDIO_DEVICE = "audio_device",
    VISUAL_MODE = "visual_mode";
    
    private static Config _instance = null;
    private String _audioDevice = "";
    private String _visualMode = "";
    private String _extensions = "m3u,pls,wsz,snd,aifc,aif,wav,au,mp1,mp2,mp3,ogg,spx,flac,ape,mac";
    private String _lastUrl = "";
    private String _lastDir = "";
    private String _lastSkinDir = "";
    private String _lastEqualizer = "";
    private String _defaultSkin = "";
    private String _playlist = "javazoom.jlgui.player.amp.playlist.BasePlaylist";
    private String _taginfoMpeg = "javazoom.jlgui.player.amp.tag.MpegInfo";
    private String _taginfoOggVorbis = "javazoom.jlgui.player.amp.tag.OggVorbisInfo";
    private String _taginfoAPE = "javazoom.jlgui.player.amp.tag.APEInfo";
    private String _taginfoFlac = "javazoom.jlgui.player.amp.tag.FlacInfo";
    private String _playlistFilename = "";
    private int _x = 0;
    private int _y = 0;
    private String _proxyServer = "";
    private String _proxyLogin = "";
    private String _proxyPassword = "";
    private int _proxyPort = -1;
    private int _volume = -1;
    private boolean _playlistEnabled = false;
    private boolean _shuffleEnabled = false;
    private boolean _repeatEnabled = false;
    private boolean _equalizerEnabled = false;
    private boolean _equalizerOn = false;
    private boolean _equalizerAuto = false;
    private boolean _screenLimit = false;
    private String _taginfoPolicy = TAGINFO_POLICY_FILE;
    
    private JFrame topParent = null;    
    private ImageIcon iconParent = null;

    private Config()
    {
    }

    /**
     * Returns Config instance.
     */
    public synchronized static Config getInstance()
    {
        if (_instance == null)
        {
            _instance = new Config();
        }
        return _instance;
    }
    
    public void setTopParent(JFrame frame)
    {
        topParent = frame;
    }
    
    public JFrame getTopParent()
    {
        if (topParent == null)
        {
            topParent = new JFrame();
        }
        return topParent;
    }
    
    public void setIconParent(ImageIcon icon)
    {
        iconParent = icon;
    }
    
    public ImageIcon getIconParent()
    {
        return iconParent;
    }

    /**
     * Returns JavaSound audio device.
     * @return String
     */
    public String getAudioDevice()
    {
        return _audioDevice;
    }

    /**
     * Set JavaSound audio device.
     * @param dev String
     */
    public void setAudioDevice(String dev)
    {
        _audioDevice = dev;
    }

    /**
     * Return visual mode.
     * @return
     */
    public String getVisualMode()
    {
        return _visualMode;
    }
    
    /**
     * Set visual mode.
     * @param mode
     */
    public void setVisualMode(String mode)
    {
        _visualMode = mode;
    }
    
    /**
     * Returns playlist filename.
     */
    public String getPlaylistFilename()
    {
        return _playlistFilename;
    }

    /**
     * Sets playlist filename.
     */
    public void setPlaylistFilename(String pl)
    {
        _playlistFilename = pl;
    }

    /**
     * Returns last equalizer values.
     */
    public int[] getLastEqualizer()
    {
        int[] vals = null;
        if ((_lastEqualizer != null) && (!_lastEqualizer.equals("")))
        {
            vals = new int[11];
            int i = 0;
            StringTokenizer st = new StringTokenizer(_lastEqualizer, ",");
            while (st.hasMoreTokens())
            {
                String v = st.nextToken();
                vals[i++] = Integer.parseInt(v);
            }
        }
        return vals;
    }

    /**
     * Sets last equalizer values.
     */
    public void setLastEqualizer(int[] vals)
    {
        if (vals != null)
        {
            String dump = "";
            for (int i = 0; i < vals.length; i++)
            {
                dump = dump + vals[i] + ",";
            }
            _lastEqualizer = dump.substring(0, (dump.length() - 1));
        }
    }

    /**
     * Return screen limit flag.
     *
     * @return is screen limit flag
     */
    public boolean isScreenLimit()
    {
        return _screenLimit;
    }

    /**
     * Set screen limit flag.
     *
     * @param b
     */
    public void setScreenLimit(boolean b)
    {
        _screenLimit = b;
    }

    /**
     * Returns last URL.
     */
    public String getLastURL()
    {
        return _lastUrl;
    }

    /**
     * Sets last URL.
     */
    public void setLastURL(String url)
    {
        _lastUrl = url;
    }

    /**
     * Returns last Directory.
     */
    public String getLastDir()
    {
        if ((_lastDir != null) && (!_lastDir.endsWith(File.separator)))
        {
            _lastDir = _lastDir + File.separator;
        }
        return _lastDir;
    }

    /**
     * Sets last Directory.
     */
    public void setLastDir(String dir)
    {
        _lastDir = dir;
        if ((_lastDir != null) && (!_lastDir.endsWith(File.separator)))
        {
            _lastDir = _lastDir + File.separator;
        }
    }

    /**
     * Returns last skin directory.
     */
    public String getLastSkinDir()
    {
        if ((_lastSkinDir != null) && (!_lastSkinDir.endsWith(File.separator)))
        {
            _lastSkinDir = _lastSkinDir + File.separator;
        }
        return _lastSkinDir;
    }

    /**
     * Sets last skin directory.
     */
    public void setLastSkinDir(String dir)
    {
        _lastSkinDir = dir;
        if ((_lastSkinDir != null) && (!_lastSkinDir.endsWith(File.separator)))
        {
            _lastSkinDir = _lastSkinDir + File.separator;
        }
    }

    /**
     * Returns audio extensions.
     */
    public String getExtensions()
    {
        return _extensions;
    }

    /**
     * Returns proxy server.
     */
    public String getProxyServer()
    {
        return _proxyServer;
    }

    /**
     * Returns proxy port.
     */
    public int getProxyPort()
    {
        return _proxyPort;
    }

    /**
     * Returns volume value.
     */
    public int getVolume()
    {
        return _volume;
    }

    /**
     * Returns volume value.
     */
    public void setVolume(int vol)
    {
        _volume = vol;
    }

    /**
     * Returns X location.
     */
    public int getXLocation()
    {
        return _x;
    }

    /**
     * Returns Y location.
     */
    public int getYLocation()
    {
        return _y;
    }

    /**
     * Sets X,Y location.
     */
    public void setLocation(int x, int y)
    {
        _x = x;
        _y = y;
    }

    /**
     * Sets Proxy info.
     */
    public void setProxy(String url, int port, String login, String password)
    {
        _proxyServer = url;
        _proxyPort = port;
        _proxyLogin = login;
        _proxyPassword = password;
    }

    /**
     * Enables Proxy.
     */
    public boolean enableProxy()
    {
        if ((_proxyServer != null) && (!_proxyServer.equals("")))
        {
            System.getProperties().put("proxySet", "true");
            System.getProperties().put("proxyHost", _proxyServer);
            System.getProperties().put("proxyPort", "" + _proxyPort);
            return true;
        }
        else return false;
    }

    /**
     * Returns PlaylistUI state.
     */
    public boolean isPlaylistEnabled()
    {
        return _playlistEnabled;
    }

    /**
     * Sets PlaylistUI state.
     */
    public void setPlaylistEnabled(boolean ena)
    {
        _playlistEnabled = ena;
    }

    /**
     * Returns ShuffleUI state.
     */
    public boolean isShuffleEnabled()
    {
        return _shuffleEnabled;
    }

    /**
     * Sets ShuffleUI state.
     */
    public void setShuffleEnabled(boolean ena)
    {
        _shuffleEnabled = ena;
    }

    /**
     * Returns RepeatUI state.
     */
    public boolean isRepeatEnabled()
    {
        return _repeatEnabled;
    }

    /**
     * Sets RepeatUI state.
     */
    public void setRepeatEnabled(boolean ena)
    {
        _repeatEnabled = ena;
    }

    /**
     * Returns EqualizerUI state.
     */
    public boolean isEqualizerEnabled()
    {
        return _equalizerEnabled;
    }

    /**
     * Sets EqualizerUI state.
     */
    public void setEqualizerEnabled(boolean ena)
    {
        _equalizerEnabled = ena;
    }

    /**
     * Returns default skin.
     */
    public String getDefaultSkin()
    {
        return _defaultSkin;
    }

    /**
     * Sets default skin.
     */
    public void setDefaultSkin(String skin)
    {
        _defaultSkin = skin;
    }

    /**
     * Returns playlist classname implementation.
     */
    public String getPlaylistClassName()
    {
        return _playlist;
    }

    /**
     * Set playlist classname implementation.
     */
    public void setPlaylistClassName(String s)
    {
        _playlist = s;
    }

    /**
     * Returns Mpeg TagInfo classname implementation.
     */
    public String getMpegTagInfoClassName()
    {
        return _taginfoMpeg;
    }

    /**
     * Returns Ogg Vorbis TagInfo classname implementation.
     */
    public String getOggVorbisTagInfoClassName()
    {
        return _taginfoOggVorbis;
    }

    /**
     * Returns APE TagInfo classname implementation.
     */
    public String getAPETagInfoClassName()
    {
        return _taginfoAPE;
    }

    /**
     * Returns Ogg Vorbis TagInfo classname implementation.
     */
    public String getFlacTagInfoClassName()
    {
        return _taginfoFlac;
    }

    /**
     * Loads configuration for the specified file.
     */
    public void load(String configfile)
    {
        CONFIG_FILE_NAME = configfile;
        load();
    }

    /**
     * Loads configuration.
     */
    public void load()
    {
        _config = new Configuration(CONFIG_FILE_NAME);
        // Creates config entries if needed.
        if (_config.get(AUDIO_DEVICE) == null) _config.add(AUDIO_DEVICE, _audioDevice);
        if (_config.get(VISUAL_MODE) == null) _config.add(VISUAL_MODE, _visualMode);
        if (_config.get(LAST_URL) == null) _config.add(LAST_URL, _lastUrl);
        if (_config.get(LAST_EQUALIZER) == null) _config.add(LAST_EQUALIZER, _lastEqualizer);
        if (_config.get(LAST_DIR) == null) _config.add(LAST_DIR, _lastDir);
        if (_config.get(LAST_SKIN_DIR) == null) _config.add(LAST_SKIN_DIR, _lastSkinDir);
        if (_config.get(TAGINFO_POLICY) == null) _config.add(TAGINFO_POLICY, _taginfoPolicy);
        if (_config.getInt(ORIGINE_X) == -1) _config.add(ORIGINE_X, _x);
        if (_config.getInt(ORIGINE_Y) == -1) _config.add(ORIGINE_Y, _y);
        if (_config.get(LAST_SKIN) == null) _config.add(LAST_SKIN, _defaultSkin);
        if (_config.get(LAST_PLAYLIST) == null) _config.add(LAST_PLAYLIST, _playlistFilename);
        if (_config.get(PLAYLIST_IMPL) == null) _config.add(PLAYLIST_IMPL, _playlist);
        if (_config.get(TAGINFO_MPEG_IMPL) == null) _config.add(TAGINFO_MPEG_IMPL, _taginfoMpeg);
        if (_config.get(TAGINFO_OGGVORBIS_IMPL) == null) _config.add(TAGINFO_OGGVORBIS_IMPL, _taginfoOggVorbis);
        if (_config.get(TAGINFO_APE_IMPL) == null) _config.add(TAGINFO_APE_IMPL, _taginfoAPE);
        if (_config.get(TAGINFO_FLAC_IMPL) == null) _config.add(TAGINFO_FLAC_IMPL, _taginfoFlac);
        if (_config.get(EXTENSIONS) == null) _config.add(EXTENSIONS, _extensions);
        if (_config.get(PROXY_SERVER) == null) _config.add(PROXY_SERVER, _proxyServer);
        if (_config.getInt(PROXY_PORT) == -1) _config.add(PROXY_PORT, _proxyPort);
        if (_config.getInt(VOLUME_VALUE) == -1) _config.add(VOLUME_VALUE, _volume);
        if (_config.get(PROXY_LOGIN) == null) _config.add(PROXY_LOGIN, _proxyLogin);
        if (_config.get(PROXY_PASSWORD) == null) _config.add(PROXY_PASSWORD, _proxyPassword);
        if (!_config.getBoolean(PLAYLIST_ENABLED)) _config.add(PLAYLIST_ENABLED, _playlistEnabled);
        if (!_config.getBoolean(SHUFFLE_ENABLED)) _config.add(SHUFFLE_ENABLED, _shuffleEnabled);
        if (!_config.getBoolean(REPEAT_ENABLED)) _config.add(REPEAT_ENABLED, _repeatEnabled);
        if (!_config.getBoolean(EQUALIZER_ENABLED)) _config.add(EQUALIZER_ENABLED, _equalizerEnabled);
        if (!_config.getBoolean(EQUALIZER_ON)) _config.add(EQUALIZER_ON, _equalizerOn);
        if (!_config.getBoolean(EQUALIZER_AUTO)) _config.add(EQUALIZER_AUTO, _equalizerAuto);
        if (!_config.getBoolean(SCREEN_LIMIT)) _config.add(SCREEN_LIMIT, _screenLimit);
        // Reads config entries
        _audioDevice = _config.get(AUDIO_DEVICE, _audioDevice);
        _visualMode = _config.get(VISUAL_MODE, _visualMode);
        _lastUrl = _config.get(LAST_URL, _lastUrl);
        _lastEqualizer = _config.get(LAST_EQUALIZER, _lastEqualizer);
        _lastDir = _config.get(LAST_DIR, _lastDir);
        _lastSkinDir = _config.get(LAST_SKIN_DIR, _lastSkinDir);
        _x = _config.getInt(ORIGINE_X, _x);
        _y = _config.getInt(ORIGINE_Y, _y);
        _defaultSkin = _config.get(LAST_SKIN, _defaultSkin);
        _playlistFilename = _config.get(LAST_PLAYLIST, _playlistFilename);
        _taginfoPolicy = _config.get(TAGINFO_POLICY, _taginfoPolicy);
        _extensions = _config.get(EXTENSIONS, _extensions);
        _playlist = _config.get(PLAYLIST_IMPL, _playlist);
        _taginfoMpeg = _config.get(TAGINFO_MPEG_IMPL, _taginfoMpeg);
        _taginfoOggVorbis = _config.get(TAGINFO_OGGVORBIS_IMPL, _taginfoOggVorbis);
        _taginfoAPE = _config.get(TAGINFO_APE_IMPL, _taginfoAPE);
        _taginfoFlac = _config.get(TAGINFO_FLAC_IMPL, _taginfoFlac);
        _proxyServer = _config.get(PROXY_SERVER, _proxyServer);
        _proxyPort = _config.getInt(PROXY_PORT, _proxyPort);
        _volume = _config.getInt(VOLUME_VALUE, _volume);
        _proxyLogin = _config.get(PROXY_LOGIN, _proxyLogin);
        _proxyPassword = _config.get(PROXY_PASSWORD, _proxyPassword);
        _playlistEnabled = _config.getBoolean(PLAYLIST_ENABLED, _playlistEnabled);
        _shuffleEnabled = _config.getBoolean(SHUFFLE_ENABLED, _shuffleEnabled);
        _repeatEnabled = _config.getBoolean(REPEAT_ENABLED, _repeatEnabled);
        _equalizerEnabled = _config.getBoolean(EQUALIZER_ENABLED, _equalizerEnabled);
        _equalizerOn = _config.getBoolean(EQUALIZER_ON, _equalizerOn);
        _equalizerAuto = _config.getBoolean(EQUALIZER_AUTO, _equalizerAuto);
        _screenLimit = _config.getBoolean(SCREEN_LIMIT, _screenLimit);
    }

    /**
     * Saves configuration.
     */
    public void save()
    {
        if (_config != null)
        {
            _config.add(ORIGINE_X, _x);
            _config.add(ORIGINE_Y, _y);
            if (_lastDir != null) _config.add(LAST_DIR, _lastDir);
            if (_lastSkinDir != null) _config.add(LAST_SKIN_DIR, _lastSkinDir);
            if (_audioDevice != null) _config.add(AUDIO_DEVICE, _audioDevice);
            if (_visualMode != null) _config.add(VISUAL_MODE, _visualMode);
            if (_lastUrl != null) _config.add(LAST_URL, _lastUrl);
            if (_lastEqualizer != null) _config.add(LAST_EQUALIZER, _lastEqualizer);
            if (_playlistFilename != null) _config.add(LAST_PLAYLIST, _playlistFilename);
            if (_playlist != null) _config.add(PLAYLIST_IMPL, _playlist);
            if (_defaultSkin != null) _config.add(LAST_SKIN, _defaultSkin);
            if (_taginfoPolicy != null) _config.add(TAGINFO_POLICY, _taginfoPolicy);
            if (_volume != -1) _config.add(VOLUME_VALUE, _volume);
            _config.add(PLAYLIST_ENABLED, _playlistEnabled);
            _config.add(SHUFFLE_ENABLED, _shuffleEnabled);
            _config.add(REPEAT_ENABLED, _repeatEnabled);
            _config.add(EQUALIZER_ENABLED, _equalizerEnabled);
            _config.add(EQUALIZER_ON, _equalizerOn);
            _config.add(EQUALIZER_AUTO, _equalizerAuto);
            _config.add(SCREEN_LIMIT, _screenLimit);
            _config.save();
        }
    }

    /**
     * @return equalizer auto flag
     */
    public boolean isEqualizerAuto()
    {
        return _equalizerAuto;
    }

    /**
     * @return equalizer on flag
     */
    public boolean isEqualizerOn()
    {
        return _equalizerOn;
    }

    /**
     * @param b
     */
    public void setEqualizerAuto(boolean b)
    {
        _equalizerAuto = b;
    }

    /**
     * @param b
     */
    public void setEqualizerOn(boolean b)
    {
        _equalizerOn = b;
    }

    public static boolean startWithProtocol(String input)
    {
        boolean ret = false;
        if (input != null)
        {
            input = input.toLowerCase();
            for (int i = 0; i < protocols.length; i++)
            {
                if (input.startsWith(protocols[i]))
                {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * @return tag info policy
     */
    public String getTaginfoPolicy()
    {
        return _taginfoPolicy;
    }

    /**
     * @param string
     */
    public void setTaginfoPolicy(String string)
    {
        _taginfoPolicy = string;
    }
}
