package jss.customjoinandquitmessage.files.utils;

import jss.customjoinandquitmessage.files.LangFile;

import java.io.IOException;
import java.util.HashMap;

public class PreConfigLoader {

    public void loadConfigs(){
        Settings.config_Lang = "en_US";
    }

    public boolean loadLangs(){
        HashMap<String, LangFile> availableLangs = new HashMap<>();
        FileList fileList = new FileList();
        int index = 1;

        try {
            for(String code : fileList.list()){
                availableLangs.put(code, new LangFile(code, index++));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!availableLangs.containsKey(Settings.config_Lang)){
            Settings.config_Lang = "en_US";
            availableLangs.put(Settings.config_Lang, new LangFile(Settings.config_Lang,0));
        }
        return true;
    }

}
