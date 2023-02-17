package jss.customjoinandquitmessages.config.utils;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;


public class FileLister {

    private final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();

    public List<String> list() throws IOException {
        List<String> result = new ArrayList<>();

        /*Path langDir = plugin.getDataFolder().toPath().resolve("lang");

        Logger.debug("Path: " + langDir);

        if(Files.exists(langDir)) {
            Pattern langFilePattern = Pattern.compile("^[a-z]{2}(_[A-Z]{2})?\\.yml$");

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(langDir)) {

                for (Path entry : stream) {
                    String fileName = entry.getFileName().toString();
                    if (langFilePattern.matcher(fileName).matches()) {
                        result.add(fileName.replace(".yml", ""));
                    }
                }

                if (!result.isEmpty()) {
                    return result;
                }
            }

            Path jarFile;

            try {
                Method method = JavaPlugin.class.getDeclaredMethod("getFile");
                method.setAccessible(true);

                jarFile = (Path) method.invoke(this.plugin);
            } catch (Exception ex) {
                throw new IOException(ex);
            }

            try (JarFile jar = new JarFile(jarFile.toFile())) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String path = entry.getName();

                    if (!path.startsWith("lang")) {
                        continue;
                    }

                    if (entry.getName().endsWith(".yml")) {
                        String name = entry.getName().replace(".yml", "").replace("lang/", "");
                        if (langFilePattern.matcher(name).matches()) {
                            result.add(name);
                        }
                    }

                }
            }
        }*/


        File langDir = new File(plugin.getDataFolder(), "lang");

        Logger.debug("Path: " + langDir);


        if (langDir.exists()) {
            FilenameFilter ymlFilter = (dir, name) -> {
                String lowercaseName = name.toLowerCase();
                if (plugin.getConfig().getBoolean("Config.Debug")) {
                    Logger.debug("&eLoad Lang File: &9" + name);
                }
                if (lowercaseName.endsWith(".yml") && name.length() == 9 && name.substring(2, 3).contains("_")) {
                    return true;
                } else {
                    if (lowercaseName.endsWith(".yml") && !lowercaseName.equals("messages.yml")) {
                        Logger.warning("&eFile: " + name + "is not in the correct format for a lang file - &bskipping...");
                    }
                    return false;
                }
            };
            for (String fileName : Objects.requireNonNull(langDir.list(ymlFilter))) {
                result.add(fileName.replace(".yml", ""));
            }
            if (!result.isEmpty())
                return result;
        }
        File jarfile;
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);

            jarfile = (File) method.invoke(this.plugin);
        } catch (Exception e) {
            throw new IOException(e);
        }
        JarFile jar = new JarFile(jarfile);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {

            JarEntry entry = entries.nextElement();
            String path = entry.getName();

            if (!path.startsWith("lang")) {
                continue;
            }

            if (entry.getName().endsWith(".yml")) {
                String name = entry.getName().replace(".yml", "").replace("lang/", "");
                if (name.length() == 5 && name.substring(2, 3).contains("_")) {
                    result.add(name);
                }
            }

        }
        jar.close();


        Logger.debug("List of available languages");
        for (String s : result){
            Logger.debug(" * " + s);
        }
        Logger.debug("============================");


        return result;
    }


}
