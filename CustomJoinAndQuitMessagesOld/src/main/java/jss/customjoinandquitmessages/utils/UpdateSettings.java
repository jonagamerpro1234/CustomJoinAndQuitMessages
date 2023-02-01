package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class UpdateSettings {

    public static String[] URL_PlUGIN = {"https://www.spigotmc.org/resources/custom-join-and-quit-message-1-7-x-1-19-x.57006/","https://github.com/jonagamerpro1234/CustomJoinAndQuitMessages/releases"};
    public static String[] API_UPDATE = {"https://api.spigotmc.org/legacy/update.php?resource=57006","https://api.github.com/repos/jonagamerpro1234/CustomJoinAndQuitMessages/releases/latest"};
    private static final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();
    public static String VERSION = plugin.version;

}
