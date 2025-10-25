package jss.customjoinandquitmessage.utils.update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jss.customjoinandquitmessage.utils.logger.Logger;
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

public class UpdateChecker {

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
    public UpdateChecker(JavaPlugin plugin, String projectId, UpdateSettings settings) {
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
                String versionType = extractVersionType(currentVersion);
                Logger.debug("[UpdateChecker] Current plugin version type: " + versionType);
                Logger.debug("[UpdateChecker] Current plugin version: " + currentVersion);

                // Compare current plugin version with latest
                int compare = compareVersions(currentVersion, latest.getVersionNumber());

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
     * Compares two versions with format x.x.x-xx
     * - First compares major.minor.patch numerically
     * - Then version type (a < b < r)
     * - Then iteration number
     * @param current current plugin version
     * @param latest latest version from Modrinth
     * @return -1 if current < latest, 0 if equal, 1 if current > latest
     */
    private int compareVersions(String current, String latest) {
        if (current == null || latest == null) {
            Logger.warning("[UpdateChecker] compareVersions received null values. current=" + current + ", latest=" + latest);
            return 0;
        }

        Logger.debug("[UpdateChecker] Comparing versions: " + current + " vs " + latest);

        // Split main numbers and suffix (-xx)
        String[] currentParts = current.split("-");
        String[] latestParts = latest.split("-");

        String[] currentNums = currentParts[0].split("\\.");
        String[] latestNums = latestParts[0].split("\\.");

        // Compare major.minor.patch numerically
        for (int i = 0; i < 3; i++) {
            int c = (i < currentNums.length) ? parsePart(currentNums[i]) : 0;
            int l = (i < latestNums.length) ? parsePart(latestNums[i]) : 0;
            Logger.debug("[UpdateChecker] Comparing part " + i + ": " + c + " vs " + l);
            if (c < l) return -1;
            if (c > l) return 1;
        }

        // Compare version type if major.minor.patch are equal
        if (currentParts.length > 1 && latestParts.length > 1) {
            char cType = currentParts[1].charAt(0);
            char lType = latestParts[1].charAt(0);
            int typeCompare = typePriority(cType) - typePriority(lType);
            Logger.debug("[UpdateChecker] Comparing type: " + cType + " vs " + lType + " -> " + typeCompare);
            if (typeCompare != 0) return typeCompare;
        }

        // Compare iteration number if a type is equal
        if (currentParts.length > 1 && latestParts.length > 1) {
            int cIter = parsePart(currentParts[1].substring(1));
            int lIter = parsePart(latestParts[1].substring(1));
            Logger.debug("[UpdateChecker] Comparing iterations: " + cIter + " vs " + lIter);
            if (cIter < lIter) return -1;
            if (cIter > lIter) return 1;
        }

        // Versions are exactly equal
        return 0;
    }

    /**
     * Assigns numeric priority to a version type for comparison
     * a -> 1, b -> 2, r -> 3
     * @param type character representing version type
     * @return priority number
     */
    private int typePriority(char type) {
        switch (type) {
            case 'a': return 1;
            case 'b': return 2;
            case 'r': return 3;
            default: return 0; // unknown type
        }
    }

    /**
     * Parses a string into an integer safely
     * @param part numeric string
     * @return integer value or 0 if parsing fails
     */
    private int parsePart(String part) {
        try {
            int val = Integer.parseInt(part);
            Logger.debug("[UpdateChecker] parsePart: converted '" + part + "' -> " + val);
            return val;
        } catch (NumberFormatException e) {
            Logger.debug("[UpdateChecker] parsePart: could not convert '" + part + "', returning 0");
            return 0;
        }
    }

    /**
     * Gets the version type from a string with format x.x.x-xx
     * Examples:
     * "1.8.0-a2" -> "alpha"
     * "1.8.0-b4" -> "beta"
     * "1.8.0-r1" -> "release"
     */
    public static @NotNull String extractVersionType(String version) {
        if (version == null || !version.contains("-") || version.length() <= version.indexOf("-") + 1) {
            return "unknown";
        }

        char typeChar = version.charAt(version.indexOf("-") + 1);
        switch (typeChar) {
            case 'a': return "alpha";
            case 'b': return "beta";
            case 'r': return "release";
            default: return "unknown";
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
