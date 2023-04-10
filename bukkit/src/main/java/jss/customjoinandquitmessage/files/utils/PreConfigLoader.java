package jss.customjoinandquitmessage.files.utils;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.LangFile;

import java.io.IOException;
import java.util.HashMap;

public class PreConfigLoader {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    @SuppressWarnings("unused")
    public void loadConfigs(){
        Settings.config_Lang = "en_US";
    }

    public boolean loadLangs(){
        HashMap<String, LangFile> availableLangs = new HashMap<>();
        FileList fileList = new FileList();
        int index = 1;

        try {
            for(String code : fileList.list()){
                availableLangs.put(code, new LangFile(plugin, code, index++));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!availableLangs.containsKey(Settings.config_Lang)){
            Settings.config_Lang = "en_US";
            availableLangs.put(Settings.config_Lang, new LangFile(plugin, Settings.config_Lang,0));
        }
        return true;
    }

}
