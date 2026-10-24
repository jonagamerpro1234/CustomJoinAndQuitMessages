package jss.customjoinandquitmessage.utils.update.spigot;

import com.google.gson.Gson;
import jss.customjoinandquitmessage.utils.logger.Logger;
import jss.customjoinandquitmessage.utils.update.core.UpdateChecker;
import jss.customjoinandquitmessage.utils.update.core.UpdateInfo;
import jss.customjoinandquitmessage.utils.update.core.UpdateSettings;
import jss.customjoinandquitmessage.utils.update.modrinth.ModrinthUpdateChecker;
import jss.customjoinandquitmessage.utils.update.util.VersionComparator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

/**
 * Update a checker for Spigot resources, using the core UpdateInfo class.
 */
public class SpigotUpdateChecker extends UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;
    private final UpdateSettings settings;
    private final Gson gson = new Gson();

    public SpigotUpdateChecker(JavaPlugin plugin, int resourceId, UpdateSettings settings) {
        super(plugin, settings);
        this.plugin = plugin;
        this.resourceId = resourceId;
        this.settings = settings;
        Logger.debug("[SpigotUpdateChecker] Initialized for resource: " + resourceId);
    }

    @Override
    public void checkForUpdates() {
        Logger.debug("[SpigotUpdateChecker] Starting update check...");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                UpdateInfo latest = fetchLatestVersion();
                if (latest == null) {
                    Logger.debug("[SpigotUpdateChecker] fetchLatestVersion returned null, cancelling check.");
                    return;
                }

                String currentVersion = plugin.getDescription().getVersion();
                String versionType = VersionComparator.extractVersionType(currentVersion);

                Logger.debug("[SpigotUpdateChecker] Current version: " + currentVersion + " (" + versionType + ")");
                Logger.debug("[SpigotUpdateChecker] Latest version: " + latest.getVersionNumber() + " (" + latest.getVersionType() + ")");

                int compare = VersionComparator.compare(currentVersion, latest.getVersionNumber());

                if (settings.showInConsole()) {
                    Logger.info("&7Latest available version: &a" + latest.getVersionNumber() + " (" + latest.getVersionType() + ")");
                    Logger.info("&7Download: &b" + latest.getDownloadUrl());
                }

                ModrinthUpdateChecker.getStatus(latest, currentVersion, versionType, compare);

            } catch (Exception e) {
                Logger.warning("[SpigotUpdateChecker] Error checking updates: " + e.getMessage());
            }
        });
    }

    private @Nullable UpdateInfo fetchLatestVersion() {
        Logger.debug("[SpigotUpdateChecker] fetchLatestVersion: starting request to Spigot API...");
        try {
            URL url = new URL("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + resourceId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", plugin.getName() + " UpdateChecker");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            SpigotResponse response = gson.fromJson(reader, SpigotResponse.class);
            reader.close();

            if (response == null || response.getCurrentVersion() == null) {
                Logger.warning("[SpigotUpdateChecker] No version found for resource " + resourceId);
                return null;
            }

            // Build UpdateInfo using FileInfo list
            UpdateInfo.FileInfo fileInfo = new UpdateInfo.FileInfo();
            fileInfo.url = response.getFile() != null ? response.getFile() : "No available";

            return new UpdateInfo(
                    response.getCurrentVersion(),
                    VersionComparator.extractVersionType(response.getCurrentVersion()),
                    response.getChangelog(),
                    Collections.singletonList(fileInfo)
            );

        } catch (Exception e) {
            Logger.warning("[SpigotUpdateChecker] Error fetching data: " + e.getMessage());
            return null;
        }
    }

    private static class SpigotResponse {
        private String current_version;
        private String changelog;
        private String file;

        public String getCurrentVersion() { return current_version; }
        public String getChangelog() { return changelog; }
        public String getFile() { return file; }
    }
}
