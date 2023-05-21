package jss.customjoinandquitmessages.update;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import jss.customjoinandquitmessages.utils.logger.Logger;
import jss.customjoinandquitmessages.utils.Util;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private final CustomJoinAndQuitMessages plugin;

    public UpdateChecker(CustomJoinAndQuitMessages plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    public void sendGithubUpdate(){
        getVersion("github" ,version -> {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.success("&a" + plugin.name + " is up to date!");
            } else {
                Logger.outLine("&5<||" + Util.setLine());
                Logger.warning("&5<||&b" + plugin.name + " is outdated!");
                Logger.warning("&5<||&bNewest version: &a" + version);
                Logger.warning("&5<||&bYour version: &d" + UpdateSettings.VERSION);
                Logger.warning("&5<||&bUpdate Here on Github: &e" + UpdateSettings.URL_PlUGIN[1]);
                Logger.outLine("&5<||" + Util.setLine());
            }
        });
    }

    public void sendSpigotUpdate(){
        getVersion("spigot" ,version -> {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.success("&a" + plugin.name + " is up to date!");
            } else {
                Logger.outLine("&5<||" + Util.setLine());
                Logger.warning("&5<||&b" + plugin.name + " is outdated!");
                Logger.warning("&5<||&bNewest version: &a" + version);
                Logger.warning("&5<||&bYour version: &d" + UpdateSettings.VERSION);
                Logger.warning("&5<||&bUpdate Here on Spigot: &e" + UpdateSettings.URL_PlUGIN[0]);
                Logger.outLine("&5<||" + Util.setLine());
            }
        });
    }

    private void getVersion(@NotNull String source, Consumer<String> consumer) {
        String url;
        switch (source) {
            case "spigot":
                url = UpdateSettings.API_UPDATE[0];
                break;
            case "github":
                url = UpdateSettings.API_UPDATE[1];
                break;
            default:
                Logger.error("Invalid source for update check: " + source);
                return;
        }

        String finalUrl = url;
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(finalUrl).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {

                    if(source.equals("github")){
                        String latestRelease = scanner.next();
                        Gson gson = new Gson();
                        JsonObject json= gson.fromJson(latestRelease, JsonObject.class);
                        String versionName = json.get("tag_name").getAsString();

                        if(versionName.contains("Beta")){
                            Logger.info("The latest release is a beta version: " + versionName);
                            Logger.info("You can download the beta Github: " + UpdateSettings.URL_PlUGIN[1]);
                        }else{
                            consumer.accept(scanner.next());
                        }

                    }else{
                        consumer.accept(scanner.next());
                    }
                }
            } catch (IOException e) {
                Logger.error("Could not check for updates: &c" + e.getMessage());
            }
        });
    }

}
