package jss.customjoinandquitmessages.utils;

import java.util.List;

public class Settings {

    //Message section
    public static String messages_prefix;

    //Others
    public static String settings_defaultLanguage;
    public static boolean compress_logs;
    public static boolean compress_logs_on_shutdown;
    public static boolean delete_compressed_logs;
    public static int delete_compressed_logs_after_days;
    public static boolean is_Group_Display;
    public static boolean settings_update;
    public static String chatformat_type;
    public static boolean settings_debug;

    //Join section and more
    public static boolean join;
    public static String join_message;
    public static boolean firstjoin;
    public static String join_message_first;

    //Title and Subtitle join
    public static boolean join_title;
    public static String join_message_title_title;
    public static String join_message_title_subtitle;
    public static int join_title_fadeIn;
    public static int join_title_stay;
    public static int join_title_fadeOut;

    //Actionbar Join
    public static boolean join_actionbar;
    public static String join_message_actionbar_text;

    //Sound Join
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

    //DiscordSRV Hook
    public static boolean hook_discordSrv;
    public static String hook_discordSrv_channelId;

    //Essentials Hook
    public static boolean hook_essentials;
    public static boolean hook_essentials_hidePlayerVanish;

    //EssentialsDiscord Hook
    public static boolean hook_essentialsDiscord;
    public static boolean hook_essentialsDiscord_use_default_channel;
    public static String hook_essentialsDiscord_channelId;

    //LuckPerms Hook
    public static boolean hook_luckperms;
    public static long hook_luckperms_autoUpdate_group_tick;
    public static boolean hook_luckperms_autoUpdate_group;
}
