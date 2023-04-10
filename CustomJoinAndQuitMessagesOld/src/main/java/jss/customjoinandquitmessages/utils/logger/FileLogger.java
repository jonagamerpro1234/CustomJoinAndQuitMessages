package jss.customjoinandquitmessages.utils.logger;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private static final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LOG_DIRECTORY = "plugins/CustomJoinAndQuitMessages/logs";

    private static void log(String message, Throwable exception) {
        String logFileName = LOG_DIRECTORY + "/" + getLogFileName();
        String logMessage = "[" + LocalDateTime.now().format(DATE_TIME_FORMATTER) + "] " + message + System.lineSeparator();
        String stackTrace = "";

        if (exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            stackTrace = sw.toString();
        }

        File logFile = new File(logFileName);
        boolean fileExists = logFile.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            if (!fileExists) {
                //noinspection ResultOfMethodCallIgnored
                logFile.createNewFile();
                createHeadFile(writer);
            }

            writer.write(logMessage.replaceAll("&[0-9a-fA-Fklmnor]", ""));
            writer.write(stackTrace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logError(String message, Exception e) {
        log("[ERROR] " + message, e);
    }

    public static void logWarning(String message) {
        log("[WARNING] " + message, null);
    }

    public static void logInfo(String message) {
        log("[INFO] " + message, null);
    }

    public static void logOutLine(String message) {
        log("[OUTLINE] " + message, null);
    }

    public static void logSuccess(String message) {
        log("[SUCCESS] " + message, null);
    }

    public static void logDebug(String message) {
        log("[DEBUG] " + message, null);
    }

    private static void createHeadFile(@NotNull BufferedWriter writer) throws IOException {
        writer.write("Plugin version: " + plugin.version + System.lineSeparator());
        writer.write("Java version: " + System.getProperty("java.version") + System.lineSeparator());
        writer.write("Server software: " + Bukkit.getServer().getName() + " " + Bukkit.getServer().getVersion() + System.lineSeparator());
        writer.write("------------------------------" + System.lineSeparator());
        writer.write(System.lineSeparator());
    }

    private static @NotNull String getLogFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter) + ".log";
    }

}
