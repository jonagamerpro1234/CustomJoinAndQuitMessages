package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class Logger {
	
	private CustomJoinAndQuitMessages plugin;
	private EventsUtils eventsUtils = new EventsUtils(plugin);
	
    public Logger(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void Log(Level level, String msg) {
        if (msg == null) {
            return;
        }
        switch (level) {
            case ERROR:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&cERROR&e]&7" + " " + msg);
                break;
            case WARNING:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&dWARNING&e]&7" + " " + msg);
                break;
            case INFO:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&9INFO&e]&7" + " " + msg);
                break;
            case OUTLINE:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&bOUTLINE&e]&7" + " " + msg);
                break;
            case SUCCESS:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&aSUCCESS&e]&7" + " " + msg);
                break;
            case DEBUG:
                Utils.sendColorMessage(eventsUtils.getConsoleSender(), "&e[&dDEBUG&e]&7" + " " + msg);
                break;
        }

    }
	
    public static void error(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), ERRORPrefix() + " " + msg);
    }

    public static void warning(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), WARNINGPrefix() + " " + msg);
    }
    
    public static void info(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), INFOPrefix() + " " + msg);
    }
    
    public static void outLine(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), OUTLINEPrefix() + " " + msg);
    }
    
    public static void success(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), SUCCESSPrefix() + " " + msg);
    }
    
    public static void debug(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), DEBUGPrefix() + " " + msg);
    }
    
    public static void defaultMessage(String msg) {
    	Utils.sendColorConsoleMessage(EventsUtils.getStaticConsoleSender(), Utils.getPrefix() + msg);
    }
	
    private static String ERRORPrefix() {
    	return Utils.color("&e[&cERROR&e]&7");
    }
    private static String WARNINGPrefix() {
    	return Utils.color("&e[&dWARNING&e]&7");
    }
    private static String INFOPrefix() {
    	return Utils.color("&e[&9INFO&e]&7");
    }
    private static String OUTLINEPrefix() {
    	return Utils.color("&e[&bOUTLINE&e]&7");
    }
    private static String SUCCESSPrefix() {
    	return Utils.color("&e[&aSUCCESS&e]&7");
    }
    private static String DEBUGPrefix() {
    	return Utils.color("&e[&dDEBUG&e]&7");
    }
	
    public enum Level {
        ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEBUG
    }
	
}
