package jss.customjoinandquitmessages.config;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger;
import jss.customjoinandquitmessages.utils.Settings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class Lang {

    public String No_Permission;
    public String No_Permission_Label;
    public String Help_cmd;
    public List<String> help_1;
    public String reload;
    public String Error_Cmd;
    public String Error_Console;
    public String Error_Sound;
    public String error_null_group;
    private CustomJoinAndQuitMessages plugin;
    private FileConfiguration locale = null;
    private File localeFile = null;
    private final Locale localeObject;
    private final String localeName;
    private final int index;

    public Lang(final CustomJoinAndQuitMessages plugin, final String localeName, final int index) {
        this.plugin = plugin;
        this.index = index;
        this.localeName = localeName;
        getlang(localeName);
        loadLocale();
        localeObject = new Locale(localeName.substring(0, 2), localeName.substring(3, 5));
    }

    public FileConfiguration getlang(final String localeName) {
        if (this.locale == null) {
            reloadlang(localeName);
        }
        return locale;
    }

    public void reloadlang(final String localeName) {
        final File localeDir = new File(plugin.getDataFolder() + File.separator + "lang");

        if (!localeDir.exists()) {
            localeDir.mkdir();
        }

        if (localeFile == null) {
            localeFile = new File(localeDir.getPath(), localeName + ".yml");
        }

        if (localeFile.exists()) {
            locale = YamlConfiguration.loadConfiguration(localeFile);
        } else {
            if (plugin.getResource("lang/" + localeName + ".yml") != null) {
                plugin.saveResource("lang/" + localeName + ".yml", true);
                localeFile = new File(plugin.getDataFolder() + File.separator + "lang", localeName + ".yml");
                locale = YamlConfiguration.loadConfiguration(localeFile);
            } else {
                Logger.error("Could not find lang file! " + localeName);
            }
        }
    }

    private void loadLocale() {
        String main = "CustomJoinAndQuitMessage";
        Settings.messages_prefix = locale.getString(main + ".Prefix");
        Error_Cmd = locale.getString(main + ".Error-Cmd");
        Error_Console = locale.getString(main + ".Error-Console");
        Error_Sound = locale.getString(main + ".Sound-Error");
        reload = locale.getString(main + ".Reload");
        Help_cmd = locale.getString(main + ".Help-Cmd");
        No_Permission = locale.getString(main + ".No-Permission");
        No_Permission_Label = locale.getString(main + ".No-Permission-Label");
        help_1 = locale.getStringList(main + ".Help-Msg");
        error_null_group = locale.getString(main + ".Groups.NotFoundGroup");
    }

    public String getLocaleName() {
        return this.localeName;
    }

    public String getLanguageName() {
        if (localeObject == null) {
            return "unknown";
        }
        return localeObject.getDisplayLanguage(localeObject);
    }

    public String getCountryName() {
        if (localeObject == null) {
            return "unknown";
        }
        return localeObject.getDisplayCountry(localeObject);
    }

    public int getIndex() {
        return index;
    }
}
