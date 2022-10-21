package jss.customjoinandquitmessage.files.utils;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileList {

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    public List<String> list() throws IOException {

        List<String> result = new ArrayList<>();

        File dir = new File(plugin.getDataFolder(), "lang");

        if(dir.exists()){

            FilenameFilter filter = (dir1, name) -> {
                String toLowerCaseName = name.toLowerCase();
                if(toLowerCaseName.endsWith(".yml") && name.length() == 9 && name.substring(2, 3).contains("_")){
                    return true;
                }else{
                    if (toLowerCaseName.endsWith(".yml") && !toLowerCaseName.equals("messages.yml")) {
                        System.out.println("File: " + name + "is not in the correct format for a lang file - skipping...");
                    }
                    return false;
                }
            };

            for (String key : Objects.requireNonNull(dir.list(filter))) {
                key = StringUtils.replace(key, ".yml", "");
                result.add(key);
            }

            if(!result.isEmpty()) return result;
        }

        File jar;

        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);

            jar = (File) method.invoke(this.plugin);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        JarFile jarFile = new JarFile(jar);

        Enumeration<JarEntry> entryEnumeration = jarFile.entries();

        while (entryEnumeration.hasMoreElements()){

            JarEntry entry =  entryEnumeration.nextElement();

            String path = entry.getName();

            if (!path.startsWith("langs")) continue;

            if(path.endsWith(".yml")){
                path = StringUtils.replace(path, ".yml", "");
                if(path.length() == 5 && path.substring(2,3).contains("_")){
                    result.add(path);
                }
            }
        }
        jarFile.close();
        return  result;
    }

}
