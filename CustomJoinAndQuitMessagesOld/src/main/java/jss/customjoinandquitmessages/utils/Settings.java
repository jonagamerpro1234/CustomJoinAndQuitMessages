package jss.customjoinandquitmessages.utils;

import java.util.List;

public class Settings {

    public static String messages_prefix;
    public static boolean hook_luckperms_autoUpdate_group;
    //Others
    public static String defaultLanguage;
    public static boolean is_Group_Display;
    public static boolean update;
    public static String c_type;
    public static boolean settings_debug;

    //Join section

    public static boolean join;
    public static String join_message;

    public static boolean firstjoin;
    public static String join_message_first;

    public static boolean join_title;
    public static String join_message_title_title;
    public static String join_message_title_subtitle;
    public static int join_title_fadeIn;
    public static int join_title_stay;
    public static int join_title_fadeOut;

    public static boolean join_actionbar;
    public static String join_message_actionbar_text;

    public static boolean join_sound;
    public static String join_sound_name;
    public static float join_sound_pitch;
    public static int join_sound_vol;

    //Quit section
    public static boolean quit;
    public static String quit_message;

    //Welcome section
    public static boolean welcome;
    public static List<String> list_welcome;

    //Hooks section
    public static boolean hook_discordSrv;
    public static String hook_discordSrv_channelId;

    public static boolean hook_essentials;
    public static boolean hook_essentials_hidePlayerVanish;

    public static boolean hook_essentialsDiscord;
    public static boolean hook_essentialsDiscord_use_default_channel;
    public static String hook_essentialsDiscord_channelId;
    public static boolean hook_luckperms;
    public static long hook_luckperms_autoUpdate_group_tick;
}
