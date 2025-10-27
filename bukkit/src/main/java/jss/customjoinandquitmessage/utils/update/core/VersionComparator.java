package jss.customjoinandquitmessage.utils.update.core;

import jss.customjoinandquitmessage.utils.logger.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for comparing semantic versions in the format x.x.x-xx
 * Supports custom type suffixes like a (alpha), b (beta), r (release).
 */
public class VersionComparator {

    /**
     * Compares two version strings of the format x.x.x-xx
     *
     * @param current Current version (e.g. "1.8.0-b4")
     * @param latest  Latest version (e.g. "1.8.1-r1")
     * @return -1 if current < latest, 0 if equal, 1 if current > latest
     */
    public static int compare(String current, String latest) {
        if (current == null || latest == null) {
            Logger.warning("[VersionComparator] One of the versions is null: current=" + current + ", latest=" + latest);
            return 0;
        }

        String[] currentParts = current.split("-");
        String[] latestParts = latest.split("-");

        String[] currentNums = currentParts[0].split("\\.");
        String[] latestNums = latestParts[0].split("\\.");

        // Compare major.minor.patch
        for (int i = 0; i < 3; i++) {
            int c = parsePart(currentNums, i);
            int l = parsePart(latestNums, i);
            if (c < l) return -1;
            if (c > l) return 1;
        }

        // Compare version type (a < b < r)
        if (currentParts.length > 1 && latestParts.length > 1) {
            char cType = currentParts[1].charAt(0);
            char lType = latestParts[1].charAt(0);
            int typeCompare = typePriority(cType) - typePriority(lType);
            if (typeCompare != 0) return typeCompare;

            // Compare iteration number (e.g. b4 vs b2)
            int cIter = parseIntSafe(currentParts[1].substring(1));
            int lIter = parseIntSafe(latestParts[1].substring(1));
            return Integer.compare(cIter, lIter);
        }

        return 0;
    }

    @Contract(pure = true)
    private static int parsePart(String @NotNull [] parts, int index) {
        if (index >= parts.length) return 0;
        return parseIntSafe(parts[index]);
    }

    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static int typePriority(char type) {
        if (type == 'a') return 1; // alpha
        if (type == 'b') return 2; // beta
        if (type == 'r') return 3; // release
        return 0;
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
}
