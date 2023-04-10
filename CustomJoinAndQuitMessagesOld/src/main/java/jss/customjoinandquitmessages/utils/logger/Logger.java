package jss.customjoinandquitmessages.utils.logger;

import jss.customjoinandquitmessages.utils.EventUtils;
import jss.customjoinandquitmessages.utils.Settings;
import jss.customjoinandquitmessages.utils.Util;

public class Logger {

    public static void error(String msg) {
        try{
            Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&cERROR&e] &7" + msg);
        }catch (Exception e){
            FileLogger.logError(msg, e);
        }
    }

    public static void warning(String msg) {
        Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&dWARNING&e] &7" + msg);
        FileLogger.logWarning(msg);
    }

    public static void info(String msg) {
        Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&9INFO&e] &7" + msg);
        FileLogger.logInfo(msg);
    }

    public static void outLine(String msg) {
        Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&bOUTLINE&e] &7" + msg);
        FileLogger.logOutLine(msg);
    }

    public static void success(String msg) {
        Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&aSUCCESS&e] &7" + msg);
        FileLogger.logSuccess(msg);
    }

    public static void debug(String msg) {
        if(Settings.settings_debug) {
            Util.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Util.getPrefix(true) + "&8: &e[&dDEBUG&e] &7" + msg);
            FileLogger.logDebug(msg);
        }
    }

}
