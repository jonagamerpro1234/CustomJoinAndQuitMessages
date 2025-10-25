package jss.customjoinandquitmessage.utils.logger;


import jss.customjoinandquitmessage.utils.Utils;

public enum LoggerLevel {
    //Dev Test
    /*INFO(Settings.logger_prefix_info != null ? Settings.logger_prefix_info : "&e[&9INFO&e]&7"),
    WARNING(Settings.logger_prefix_warning != null ? Settings.logger_prefix_warning : "&e[&cWARNING&e]&7"),
    SUCCESS(Settings.logger_prefix_success != null ? Settings.logger_prefix_success : "&e[&aSUCCESS&e]&7"),
    ERROR(Settings.logger_prefix_error != null ? Settings.logger_prefix_error : "&e[&cERROR&e]&7"),
    DEBUG(Settings.logger_prefix_debug != null ? Settings.logger_prefix_debug : "&e[&dDEBUG&e]&7"),
    OUTLINE(Settings.logger_prefix_outline != null ? Settings.logger_prefix_outline : "&e[&bOUTLINE&e]&7"),
    CHAT(Settings.logger_prefix_chat != null ? Settings.logger_prefix_chat : "&e[&aCHAT&e]&7");*/

    INFO(Utils.getPrefix(true) + " -> &e[&9INFO&e]&7"),
    WARNING(Utils.getPrefix(true) + "-> &e[&cWARNING&e]&7"),
    SUCCESS(Utils.getPrefix(true) + "-> &e[&aSUCCESS&e]&7"),
    ERROR(Utils.getPrefix(true) + "-> &e[&cERROR&e]&7"),
    DEBUG(Utils.getPrefix(true) + "-> &e[&dDEBUG&e]&7"),
    OUTLINE(Utils.getPrefix(true) + "-> &e[&bOUTLINE&e]&7"),
    CHAT(Utils.getPrefix(true) + "-> &e[&aCHAT&e]&7");

    private final String prefix;

    LoggerLevel(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
