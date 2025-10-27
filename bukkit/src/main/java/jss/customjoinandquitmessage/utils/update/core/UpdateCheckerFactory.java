package jss.customjoinandquitmessage.utils.update.core;

import jss.customjoinandquitmessage.utils.update.github.GithubUpdateChecker;
import jss.customjoinandquitmessage.utils.update.modrinth.ModrinthUpdateChecker;
import jss.customjoinandquitmessage.utils.update.spigot.SpigotUpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Factory class to create the correct type of UpdateChecker (Modrinth, GitHub, Spigot, etc.)
 */
public class UpdateCheckerFactory {

    public enum Source {
        MODRINTH,
        GITHUB,
        SPIGOT
    }

    /**
     * Creates a new UpdateChecker instance based on the given source.
     *
     * @param plugin   the main JavaPlugin instance
     * @param settings Update settings (console visibility, etc.)
     * @param source   the update source (MODRINTH, GITHUB, SPIGOT)
     * @param id1      first identifier (Modrinth projectId, GitHub owner, or Spigot resource ID as string)
     * @param id2      second identifier (GitHub repo name, can be null for Modrinth or Spigot)
     * @return an UpdateChecker implementation
     */
    @Contract("_, _, _, _, _ -> new")
    public static @NotNull UpdateChecker create(JavaPlugin plugin, UpdateSettings settings, @NotNull Source source, String id1, String id2) {
        switch (source) {
            case MODRINTH:
                return new ModrinthUpdateChecker(plugin, id1, settings);
            case GITHUB:
                return new GithubUpdateChecker(plugin, settings, id1, id2);
            case SPIGOT:
                try {
                    int resourceId = Integer.parseInt(id1);
                    return new SpigotUpdateChecker(plugin, resourceId, settings);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("SPIGOT source requires numeric resource ID, got: " + id1, e);
                }
            default:
                throw new IllegalArgumentException("Unknown update source: " + source);
        }
    }
}
