package jss.customjoinandquitmessage.utils.update.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to handle changelog processing and formatting.
 * Can summarize by characters or by number of lines, and provides
 * methods suitable for showing in inventories (ItemStack lore).
 */
public class ChangelogUtils {

    /**
     * Represents a changelog entry with full text and split lines.
     */
    public static class ChangelogEntry {
        private final String fullText;
        private final List<String> lines;

        public ChangelogEntry(@NotNull String fullText) {
            this.fullText = fullText;
            this.lines = new ArrayList<>(Arrays.asList(fullText.split("\n")));
        }

        /** Returns the full changelog text */
        public String getFullText() {
            return fullText;
        }

        /** Returns the changelog split into lines */
        public List<String> getLines() {
            return lines;
        }
    }

    /**
     * Summarizes a changelog by characters (default max 150).
     *
     * @param changelog the full changelog text
     * @return summarized text
     */
    public static @NotNull String summarize(@Nullable String changelog) {
        return summarize(changelog, 150);
    }

    /**
     * Summarizes a changelog by a specified character limit.
     *
     * @param changelog the full changelog text
     * @param maxChars maximum number of characters to show
     * @return summarized text
     */
    public static @NotNull String summarize(@Nullable String changelog, int maxChars) {
        if (changelog == null || changelog.isEmpty()) return "No changelog available";
        return changelog.length() > maxChars
                ? changelog.substring(0, maxChars) + "..."
                : changelog;
    }

    /**
     * Summarizes a changelog by number of lines.
     *
     * @param changelog the full changelog text
     * @param maxLines maximum number of lines to show
     * @return summarized text
     */
    public static @NotNull String summarizeByLines(@Nullable String changelog, int maxLines) {
        if (changelog == null || changelog.isEmpty()) return "No changelog available";

        String[] lines = changelog.split("\n");
        if (lines.length <= maxLines) return changelog;

        return String.join("\n", Arrays.copyOf(lines, maxLines)) + "\n...";
    }

    /**
     * Converts a changelog into a list of lines suitable for ItemStack lore.
     *
     * @param changelog the full changelog text
     * @param maxLines maximum number of lines to show in the lore
     * @return list of strings for lore
     */
    public static @NotNull List<String> toLore(@Nullable String changelog, int maxLines) {
        if (changelog == null || changelog.isEmpty()) {
            return new ArrayList<>(Collections.singletonList("No changelog available"));
        }

        ChangelogEntry entry = new ChangelogEntry(changelog);
        List<String> lore = new ArrayList<>();

        int limit = Math.min(maxLines, entry.getLines().size());
        for (int i = 0; i < limit; i++) {
            lore.add(entry.getLines().get(i));
        }

        if (entry.getLines().size() > maxLines) {
            lore.add("..."); // indicate there are more lines
        }

        return lore;
    }
}
