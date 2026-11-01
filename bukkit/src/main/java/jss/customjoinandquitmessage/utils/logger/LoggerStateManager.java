
package jss.customjoinandquitmessage.utils.logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

public class LoggerStateManager {

    private static final Path DEV_DIR = Paths.get("plugins/CustomJoinAndQuitMessages/dev");
    private static final Path STATE_FILE = DEV_DIR.resolve("logger-state.dat");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LoggerState loadState() throws IOException {
        if (!Files.exists(STATE_FILE)) {
            return new LoggerState(true, LocalDateTime.now());
        }

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(STATE_FILE)) {
            properties.load(input);
        }

        String shutdownValue = properties.getProperty("shutdownCompleted");
        String lastUpdatedValue = properties.getProperty("lastUpdated");

        if (shutdownValue == null || lastUpdatedValue == null) {
            throw new IOException("Logger state file is missing required properties.");
        }

        try {
            boolean shutdownCompleted = Boolean.parseBoolean(shutdownValue);
            LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedValue, DATE_FORMATTER);

            return new LoggerState(shutdownCompleted, lastUpdated);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid date in logger state file.", e);
        }
    }

    public void saveState(@NotNull LoggerState state) throws IOException {
        Files.createDirectories(DEV_DIR);

        Properties properties = new Properties();
        properties.setProperty("shutdownCompleted", Boolean.toString(state.isShutdownCompleted()));
        properties.setProperty("lastUpdated", state.getLastUpdated().format(DATE_FORMATTER));

        try (OutputStream output = Files.newOutputStream(STATE_FILE)) {
            properties.store(output, "Logger State");
        }
    }
}
