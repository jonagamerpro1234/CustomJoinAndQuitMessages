package jss.customjoinandquitmessage.utils.update.core;

import jss.customjoinandquitmessage.utils.update.github.GithubUpdateChecker;
import jss.customjoinandquitmessage.utils.update.modrinth.ModrinthUpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Factory class to create the correct type of UpdateChecker (Modrinth, GitHub, etc.)
 */
public class UpdateCheckerFactory {

    public enum Source {
        MODRINTH,
        GITHUB
    }

    /**
     * Creates a new UpdateChecker instance based on the given source.
     *
     * @param plugin   the main JavaPlugin instance
     * @param settings Update settings (console visibility, etc.)
     * @param source   the update source (MODRINTH, GITHUB)
     * @param id1      first identifier (Modrinth projectId or GitHub owner)
     * @param id2      second identifier (GitHub repo name, can be null for Modrinth)
     * @return an UpdateChecker implementation
     */
    @Contract("_, _, _, _, _ -> new")
    public static @NotNull UpdateChecker create(JavaPlugin plugin, UpdateSettings settings, Source source, String id1, String id2) {
        if (source == Source.MODRINTH) {
            return new ModrinthUpdateChecker(plugin, id1, settings);
        } else if (source == Source.GITHUB) {
            return new GithubUpdateChecker(plugin, settings, id1, id2);
        } else {
            throw new IllegalArgumentException("Unknown update source: " + source);
        }
    }
}
