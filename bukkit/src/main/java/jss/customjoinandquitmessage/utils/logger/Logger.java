package jss.customjoinandquitmessage.utils.logger;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.EventUtils;
import jss.customjoinandquitmessage.utils.MessageUtils;
import jss.customjoinandquitmessage.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {

    private static final File logFile = new File("plugins/CustomJoinAndQuitMessages/logs.txt");
    private static LoggerStateManager stateManager;
    private static LoggerState state;

    public static void initialize(LoggerStateManager stateManager, LoggerState state) {
        Logger.stateManager = stateManager;
        Logger.state = state;
    }

    private static void log(LoggerLevel level, String msg) {
        if (level == LoggerLevel.DEBUG && !Settings.config_Debug) return;

        String formattedMsg;
        String fileMsg = msg;

        if (level == LoggerLevel.DEBUG) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StackTraceElement element = stackTrace[3];

            String className = element.getClassName();
            int lineNumber = element.getLineNumber();

            String location = "[" + className + ":" + lineNumber + "] ";

            formattedMsg = level.getPrefix() + " " + location + msg;
            fileMsg = location + msg;
        } else {
            formattedMsg = level.getPrefix() + " " + msg;
        }

        MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), formattedMsg);
        writeToFile(level, fileMsg);
    }

    public static boolean saveLogFile() {
        Path source = logFile.toPath();
        Path directory = Paths.get("plugins/CustomJoinAndQuitMessages/dev/logs");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Path destination = directory.resolve(date + ".log");

        try {
            if (!Files.exists(source) || Files.size(source) == 0) return true;

            Files.createDirectories(directory);
            byte[] logData = Files.readAllBytes(source);

            Files.write(destination, logData, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.write(source, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            severe(e, "Failed to save log file");
            return false;
        }
    }

    public static boolean recoverLogFile() {
        if (!Files.exists(logFile.toPath())) return true;

        try {
            if (Files.size(logFile.toPath()) == 0) return true;

            info("Recovering pending log records...");
            return saveLogFile();
        } catch (IOException e) {
            severe(e, "Failed to recover pending log records");
            return false;
        }
    }

    public static boolean rotareLogFile(LoggerStateManager stateManager, @NotNull LoggerState state) {
        LocalDate lastLogDate = state.getLastUpdated().toLocalDate();
        LocalDate latestLogDate = LocalDate.now();

        if (lastLogDate.equals(latestLogDate)) return true;
        if (!saveLogFile()) return false;

        state.update(false);
        try {
            stateManager.saveState(state);
            return true;
        } catch (IOException e) {
            severe(e, "Failed to update logger state after rotation");
            return false;
        }
    }

    private static void writeToFile(@NotNull LoggerLevel level, String msg) {
        if (stateManager != null && state != null) {
            if (!rotareLogFile(stateManager, state)) {

            }
        }

        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timeStamp + "] [" + level.name() + "] " + msg + "\n");
        } catch (IOException e) {
            MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), "&cError writing log file: " + e.getMessage());
        }
    }

    // Métodos de logging con niveles predefinidos
    public static void warning(String msg) { log(LoggerLevel.WARNING, msg); }
    public static void success(String msg) { log(LoggerLevel.SUCCESS, msg); }
    public static void error(String msg) { log(LoggerLevel.ERROR, msg); }
    public static void severe(String msg) { log(LoggerLevel.SEVERE, msg); }
    public static void debug(String msg) { log(LoggerLevel.DEBUG, msg); }
    public static void info(String msg) { log(LoggerLevel.INFO, msg); }
    public static void outline(String msg) { log(LoggerLevel.OUTLINE, msg); }
    public static void chat(String msg) { log(LoggerLevel.CHAT, msg); }

    // Método para logs con excepciones detalladas
    public static void error(@NotNull Exception e, String msg) {
        log(LoggerLevel.ERROR, msg + " | Exception: " + e.getMessage());
    }

    public static void severe(@NotNull Exception e, String msg) {
        log(LoggerLevel.SEVERE, msg + " | Exception: " + e.getMessage());
    }

    // Mensaje sin prefijo específico
    public static void defaultMessage(String msg) {
        String formattedMsg = Settings.enable_colors ? Utils.getPrefix(true) + msg: msg;
        MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), formattedMsg);
    }
}
