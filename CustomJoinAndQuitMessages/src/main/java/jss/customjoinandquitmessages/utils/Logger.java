package jss.customjoinandquitmessages.utils;

public class Logger {

	public void Log(Level level, String msg) {
        if (msg == null) {
            return;
        }
        switch (level) {
            case ERROR:
                error(msg);
                break;
            case WARNING:
                warning(msg);
                break;
            case INFO:
                info(msg);
                break;
            case OUTLINE:
                outLine(msg);
                break;
            case SUCCESS:
                success(msg);
                break;
            case DEBUG:
                debug(msg);
                break;
            default:
            	defaultMessage(msg);
            	break;
        }

    }
	
    public static void error(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(),  Utils.getPrefix() + "&8-> &e[&cERROR&e] &7" + msg);
    }

    public static void warning(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&8-> &e[&dWARNING&e] &7" + msg);
    }
    
    public static void info(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&8-> &e[&9INFO&e] &7" + msg);
    }
    
    public static void outLine(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&8-> &e[&bOUTLINE&e] &7" + msg);
    }
    
    public static void success(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&8-> &e[&aSUCCESS&e] &7" + msg);
    }
    
    public static void debug(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + "&8-> &e[&dDEBUG&e] &7" + msg);
    }
    
    public static void defaultMessage(String msg) {
    	Utils.sendColorConsoleMessage(EventUtils.getStaticConsoleSender(), Utils.getPrefix() + msg);
    }
		
    public enum Level {
        ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEBUG
    }
	
}
