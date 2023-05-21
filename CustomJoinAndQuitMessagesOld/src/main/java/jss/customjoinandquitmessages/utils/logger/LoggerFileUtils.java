package jss.customjoinandquitmessages.utils.logger;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

public class LoggerFileUtils {

    private static final String LOGS_DIR = "plugins/CustomJoinAndQuitMessages/logs/";
    private static final String LOGS_COMPRESSED_DIR = "logs/";
    private static final int MAX_LOG_AGE_IN_DAYS = 30;

    public static void compressLogs() {
        File logsDir = new File(LOGS_DIR);
        if (!logsDir.exists()) {
            logsDir.mkdir();
        }

        File compressedLogsDir = new File(LOGS_COMPRESSED_DIR);
        if (!compressedLogsDir.exists()) {
            compressedLogsDir.mkdir();
        }

        File[] logFiles = logsDir.listFiles((dir, name) -> name.endsWith(".log"));
        if (logFiles != null) {
            for (File logFile : logFiles) {
                if (isFileOld(logFile)) {
                    try (FileInputStream fis = new FileInputStream(logFile);
                         FileOutputStream fos = new FileOutputStream(compressedLogsDir + "/" + logFile.getName() + ".zip");
                         GZIPOutputStream gzos = new GZIPOutputStream(fos)) {

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            gzos.write(buffer, 0, len);
                        }

                        fis.close();
                        gzos.finish();
                        gzos.close();
                        logFile.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void deleteOldCompressedLogs() {
        File compressedLogsDir = new File(LOGS_COMPRESSED_DIR);
        if (!compressedLogsDir.exists()) {
            compressedLogsDir.mkdir();
        }

        File[] compressedLogFiles = compressedLogsDir.listFiles((dir, name) -> name.endsWith(".zip"));
        if (compressedLogFiles != null) {
            for (File compressedLogFile : compressedLogFiles) {
                if (isFileOld(compressedLogFile)) {
                    compressedLogFile.delete();
                }
            }
        }
    }

    private static boolean isFileOld(@NotNull File file) {
        long diffInMillis = System.currentTimeMillis() - file.lastModified();
        long daysDiff = diffInMillis / (24 * 60 * 60 * 1000);
        return daysDiff > MAX_LOG_AGE_IN_DAYS;
    }

    public static @NotNull String getLogFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime dateTime = LocalDateTime.now();
        return LOGS_DIR + formatter.format(dateTime) + ".log";
    }

}
