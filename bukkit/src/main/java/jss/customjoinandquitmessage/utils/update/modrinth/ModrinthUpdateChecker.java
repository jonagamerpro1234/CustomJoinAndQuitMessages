package jss.customjoinandquitmessage.utils.update.modrinth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jss.customjoinandquitmessage.utils.logger.Logger;
import jss.customjoinandquitmessage.utils.update.core.UpdateChecker;
import jss.customjoinandquitmessage.utils.update.core.UpdateInfo;
import jss.customjoinandquitmessage.utils.update.core.UpdateSettings;
import jss.customjoinandquitmessage.utils.update.core.VersionComparator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ModrinthUpdateChecker extends UpdateChecker {

    // The plugin instance
    private final JavaPlugin plugin;

    // Modrinth project ID to check for updates
    private final String projectId;

    // Settings for update notifications
    private final UpdateSettings settings;

    // Gson instance for JSON parsing
    private final Gson gson = new Gson();

    /**
     * Constructor
     * @param plugin the JavaPlugin instance
     * @param projectId the Modrinth project ID
     * @param settings configuration for showing updates in console
     */
    public ModrinthUpdateChecker(JavaPlugin plugin, String projectId, UpdateSettings settings) {
        super(plugin, settings);
        this.plugin = plugin;
        this.projectId = projectId;
        this.settings = settings;
        Logger.debug("[UpdateChecker] Initialized for project: " + projectId);
    }

    /**
     * Main method to check for updates asynchronously
     */
    public void checkForUpdates() {
        Logger.debug("[UpdateChecker] Starting update check...");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                // Fetch the latest version from Modrinth API
                UpdateInfo latest = fetchLatestVersion();
                if (latest == null) {
                    Logger.debug("[UpdateChecker] fetchLatestVersion returned null, cancelling check.");
                    return;
                }

                Logger.debug("[UpdateChecker] Latest version fetched: " + latest.getVersionNumber() +
                        " (" + latest.getVersionType() + ")");

                // Get the current plugin version and its type
                String currentVersion = plugin.getDescription().getVersion();
                String versionType = VersionComparator.extractVersionType(currentVersion);
                Logger.debug("[UpdateChecker] Current plugin version type: " + versionType);
                Logger.debug("[UpdateChecker] Current plugin version: " + currentVersion);

                // Compare current plugin version with latest
                int compare = VersionComparator.compare(currentVersion, latest.getVersionNumber());

                // Show information in console if configured
                if (settings.showInConsole()) {
                    Logger.info("&7Latest available version: &a" + latest.getVersionNumber() +
                            " (" + latest.getVersionType() + ")");
                    Logger.info("&7Download: &b" + "https://modrinth.com/plugin/customjoinandquitmessages/version/" + latest.getVersionNumber());
                }

                // Show version status to user
                if (compare < 0) {
                    Logger.warning("&cYour version (" + currentVersion + ") is outdated.");
                    Logger.warning("&7Changelog: &f" + summarizeChangelog(latest.getChangelog()));
                } else if (compare == 0) {
                    Logger.info("&aYou are using the latest version " + currentVersion + " (" + versionType + ")");
                } else {
                    Logger.info("&eYou are using a development version " + currentVersion + " (" + versionType + ")");
                }

            } catch (Exception e) {
                Logger.warning("[UpdateChecker] Error while checking updates: " + e.getMessage());
            }
        });
    }

    /**
     * Fetches the latest version information from Modrinth API
     * @return UpdateInfo object of the latest version or null if failed
     */
    private @Nullable UpdateInfo fetchLatestVersion() {
        Logger.debug("[UpdateChecker] fetchLatestVersion: starting request to Modrinth...");
        try {
            //Create HTTP connection
            URL url = new URL("https://api.modrinth.com/v2/project/" + projectId + "/version");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", plugin.getName() + " UpdateChecker");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            // Parse JSON response into a list of UpdateInfo
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Type listType = new TypeToken<List<UpdateInfo>>() {}.getType();
            List<UpdateInfo> versions = gson.fromJson(reader, listType);
            reader.close();

            if (versions == null || versions.isEmpty()) {
                Logger.warning("[UpdateChecker] No versions found on Modrinth.");
                return null;
            }

            // Debug: print first 3 versions to verify data
            for (int i = 0; i < Math.min(3, versions.size()); i++) {
                UpdateInfo v = versions.get(i);
                Logger.debug("[UpdateChecker] Version " + i + ": number=" + v.getVersionNumber() +
                        ", type=" + v.getVersionType() +
                        ", files=" + (v.getFiles() != null ? v.getFiles().size() : "null"));
            }

            // Return the first version (latest)
            return versions.get(0);

        } catch (Exception e) {
            Logger.warning("[UpdateChecker] Error fetching data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Summarizes a changelog for display (max 150 chars)
     * @param changelog full changelog text
     * @return summarized text
     */
    private @NotNull String summarizeChangelog(String changelog) {
        Logger.debug("[UpdateChecker] Summarizing changelog...");
        return (changelog == null || changelog.isEmpty())
                ? "No changelog available"
                : (changelog.length() > 150 ? changelog.substring(0, 150) + "..." : changelog);
    }
}
