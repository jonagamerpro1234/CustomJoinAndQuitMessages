package jss.customjoinandquitmessage.files;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

@SuppressWarnings("ALL")
public class LangFile {

    private final CustomJoinAndQuitMessage plugin;
    private FileConfiguration config;
    private File file;

    private final Locale localeObj;
    private final String localeName;
    private int index;

    public LangFile(CustomJoinAndQuitMessage plugin, @NotNull String localeName, int index){
        this.plugin = plugin;
        this.index = index;
        this.localeName = localeName;
        getConfig(localeName);
        loadMessages();
        localeObj = new Locale(localeName.substring(0,2),localeName.substring(3,5));
    }

    public FileConfiguration getConfig(final String name) {
        if(config == null){
            reload(name);
        }
        return config;
    }

    public void reload(final String name){
        File dir = new File(plugin.getDataFolder(),"langs");

        if(!dir.exists()){
            if(dir.mkdirs()) {
                plugin.getLogger().info("Carpeta 'langs' creada correctamente");
            } else {
                plugin.getLogger().warning("No se pudo crear la carpeta 'langs'");
            }
        }

        if(file == null){
            file = new File(dir.getPath(), name + ".yml");
        }

        if(file.exists()){
            config = YamlConfiguration.loadConfiguration(file);
        }else{

            if(plugin.getResource("langs/" + name + ".yml") != null){
                plugin.saveResource("langs/" + name + ".yml", true);
                file = new File(plugin.getDataFolder() + File.separator + "langs", name + ".yml");
                config = YamlConfiguration.loadConfiguration(file);
            }
        }
    }

    private void loadMessages(){
        Settings.lang_prefix = config.getString("Prefix");
        Settings.lang_nopermission = config.getString("NoPermission");
        Settings.lang_usageMainCommand = config.getString("UsageMainCommand");
        Settings.lang_allowConsoleCommand = config.getString("allowConsoleCommand");
        Settings.lang_unknownArguments = config.getString("UnknownArguments");
        Settings.lang_reloadCommand = config.getString("ReloadCommand");
        Settings.lang_usageDisplayCommand = config.getString("UsageDisplayCommand");
        Settings.lang_unknownSound = config.getString("UnknownSound");
        Settings.lang_disableCommand = config.getString("DisableCommand");
        Settings.lang_updateAlert_console = config.getStringList("UpdateAlert.Console");
        Settings.lang_updateAlert_player = config.getStringList("UpdateAlert.Player");
        Settings.lang_helpCommand = config.getStringList("HelpCommand");
    }

}
