package jss.customjoinandquitmessages.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;

public class Logger {
	
	private CustomJoinAndQuitMessages plugin;
	private EventUtils eventUtils = new EventUtils(plugin);
	
    public Logger(CustomJoinAndQuitMessages plugin) {
		this.plugin = plugin;
	}

	public void Log(Level level, String msg) {
        if (msg == null) {
            return;
        }
        switch (level) {
            case ERROR:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&cERROR&e]&7" + " " + msg);
                break;
            case WARNING:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&dWARNING&e]&7" + " " + msg);
                break;
            case INFO:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&9INFO&e]&7" + " " + msg);
                break;
            case OUTLINE:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&bOUTLINE&e]&7" + " " + msg);
                break;
            case SUCCESS:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&aSUCCESS&e]&7" + " " + msg);
                break;
            case DEBUG:
                Utils.sendColorMessage(eventUtils.getConsoleSender(), "&e[&dDEBUG&e]&7" + " " + msg);
                break;
        }

    }
	
    public enum Level {
        ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEBUG
    }
	
}
