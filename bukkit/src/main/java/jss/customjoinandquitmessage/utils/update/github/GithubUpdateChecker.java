package jss.customjoinandquitmessage.utils.update.github;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jss.customjoinandquitmessage.utils.logger.Logger;
import jss.customjoinandquitmessage.utils.update.core.UpdateChecker;
import jss.customjoinandquitmessage.utils.update.core.UpdateSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubUpdateChecker extends UpdateChecker {

    private final String owner;
    private final String repo;

    public GithubUpdateChecker(JavaPlugin plugin, UpdateSettings settings, String owner, String repo) {
        super(plugin, settings);
        this.owner = owner;
        this.repo = repo;
    }

    @Override
    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", plugin.getName() + " UpdateChecker");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                String latestVersion = json.get("tag_name").getAsString();
                String changelog = json.get("body").getAsString();
                String pageUrl = json.get("html_url").getAsString();

                String currentVersion = plugin.getDescription().getVersion();

                Logger.debug("[GitHubChecker] Latest: " + latestVersion + " | Current: " + currentVersion);

                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    Logger.warning("&cA new version is available: &a" + latestVersion);
                    Logger.info("&7Changelog: &f" + changelog);
                    Logger.info("&7Download: &b" + pageUrl);
                } else {
                    Logger.info("&aYou are using the latest version: " + currentVersion);
                }

            } catch (Exception e) {
                logError(e);
            }
        });
    }
}
