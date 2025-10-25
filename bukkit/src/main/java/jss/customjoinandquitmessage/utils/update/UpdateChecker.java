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

    private final JavaPlugin plugin;
    private final String projectId;
    private final UpdateSettings settings;
    private final Gson gson = new Gson();

    public UpdateChecker(JavaPlugin plugin, String projectId, UpdateSettings settings) {
        this.plugin = plugin;
        this.projectId = projectId;
        this.settings = settings;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                UpdateInfo latest = fetchLatestVersion();
                if (latest == null) return;

                String currentVersion = plugin.getDescription().getVersion();
                int compare = compareVersions(currentVersion, latest.getVersionNumber());

                if (settings.showInConsole()) {
                    Logger.info("§7Última versión disponible: §a" + latest.getVersionNumber() +
                            " (" + latest.getVersionType() + ")");
                    Logger.info("§7Descarga: §b" + latest.getDownloadUrl());
                }

                if (compare < 0) {
                    Logger.warning("§cTu versión (" + currentVersion + ") está desactualizada.");
                    Logger.warning("§7Changelog: §f" + summarizeChangelog(latest.getChangelog()));
                } else if (compare == 0) {
                    Logger.info("§aEstás usando la versión más reciente (" + currentVersion + ")");
                } else {
                    Logger.info("§eEstás usando una versión de desarrollo (" + currentVersion + ")");
                }

            } catch (Exception e) {
                Logger.warning("[UpdateChecker] Error al comprobar actualizaciones: " + e.getMessage());
            }
        });
    }

    private @Nullable UpdateInfo fetchLatestVersion() {
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + projectId + "/version");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", plugin.getName() + " UpdateChecker");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Type listType = new TypeToken<List<UpdateInfo>>() {}.getType();
            List<UpdateInfo> versions = gson.fromJson(reader, listType);
            reader.close();

            if (versions == null || versions.isEmpty()) {
                Logger.warning("[UpdateChecker] No se encontraron versiones en Modrinth.");
                return null;
            }

            return versions.get(0); // last version
        } catch (Exception e) {
            Logger.warning("[UpdateChecker] Error al obtener datos: " + e.getMessage());
            return null;
        }
    }

    private int compareVersions(@NotNull String current, @NotNull String latest) {
        String[] c = current.replace("v", "").split("\\.");
        String[] l = latest.replace("v", "").split("\\.");
        int length = Math.max(c.length, l.length);

        for (int i = 0; i < length; i++) {
            int cv = (i < c.length) ? parsePart(c[i]) : 0;
            int lv = (i < l.length) ? parsePart(l[i]) : 0;
            if (cv < lv) return -1;
            if (cv > lv) return 1;
        }
        return 0;
    }

    private int parsePart(String s) {
        try {
            return Integer.parseInt(s.replaceAll("\\D", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private @NotNull String summarizeChangelog(String changelog) {
        return (changelog == null || changelog.isEmpty())
                ? "No changelog available"
                : (changelog.length() > 150 ? changelog.substring(0, 150) + "..." : changelog);
    }
}
