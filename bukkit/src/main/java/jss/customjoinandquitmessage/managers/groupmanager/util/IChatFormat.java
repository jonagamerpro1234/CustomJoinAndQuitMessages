package jss.customjoinandquitmessage.managers.groupmanager.util;

import java.util.List;

public interface IChatFormat {

    boolean isMainEnabled();
    String getMainFormat();
    int MainDelay();
    boolean isWelcome();
    List<String> getWelcome();
    boolean isTitle();
    String getTitle();
    String getSubTitle();
    int getFadeIn();
    int getStay();
    int getFadeOut();
    boolean isActionbar();
    String getActionbar();
    boolean isSound();
    String getNameSound();
    boolean isSendToAllSound();
    int getSoundVolume();

    int getSoundPitch();
}
