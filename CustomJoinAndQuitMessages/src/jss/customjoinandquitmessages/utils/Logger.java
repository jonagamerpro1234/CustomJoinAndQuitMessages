package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class Logger {
	
	private CustomJoinAndQuitMessages plugin;
	private EventsUtils EventsUtils = new EventsUtils(plugin);
	
    public Logger(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void Log(Level level, String msg) {
        if (msg == null) {
            return;
        }
        switch (level) {
            case ERROR:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&cERROR&e]&7" + " " + msg);
                break;
            case WARNING:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&dWARNING&e]&7" + " " + msg);
                break;
            case INFO:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&9INFO&e]&7" + " " + msg);
                break;
            case OUTLINE:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&bOUTLINE&e]&7" + " " + msg);
                break;
            case SUCCESS:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&aSUCCESS&e]&7" + " " + msg);
                break;
            case DEBUG:
                Utils.sendColorMessage(EventsUtils.getConsoleSender(), "&e[&dDEBUG&e]&7" + " " + msg);
                break;
        }

    }
	
    public enum Level {
        ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEBUG
    }
	
}
