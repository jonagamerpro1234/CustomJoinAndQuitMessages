package jss.customjoinandquitmessage.utils.logger;

import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.utils.EventUtils;
import jss.customjoinandquitmessage.utils.MessageUtils;
import jss.customjoinandquitmessage.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("all")
public class Logger {

    private static final File logFile = new File("plugins/CustomJoinAndQuitMessages/logs.txt");

    private static void log(LoggerLevel level, String msg) {
        if (level == LoggerLevel.DEBUG && !Settings.config_Debug) return;
        // Variable para el mensaje formateado
        String formattedMsg;

        // Si es DEBUG, incluir la clase y la línea
        if (level == LoggerLevel.DEBUG) {
            // Obtener la clase y la línea
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StackTraceElement element = stackTrace[3]; // Tomamos el tercer elemento que es el que llamó a log

            String className = element.getClassName();
            int lineNumber = element.getLineNumber();

            // Formatear el mensaje para DEBUG incluyendo la clase y la línea
            formattedMsg = Settings.enable_colors
                    ? level.getPrefix() + " [" + className + ":" + lineNumber + "] " + msg
                    : level.getPrefix() + " [" + className + ":" + lineNumber + "] " + msg;
        } else {
            // Para otros niveles, solo el mensaje con el prefijo
            formattedMsg = Settings.enable_colors
                    ? level.getPrefix() + " " + msg
                    : level.getPrefix() + " " + msg;
        }

        // Enviar mensaje a la consola
        MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), formattedMsg);

        // Guardar en archivo de log
        writeToFile(level, formattedMsg);
    }

    private static void writeToFile(LoggerLevel level, String msg) {
        try {
            if (!logFile.exists()) logFile.createNewFile();
            FileWriter writer = new FileWriter(logFile, true);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timeStamp + "] [" + level.name() + "] " + msg + "\n");
            writer.close();
        } catch (IOException e) {
            MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), "&cError writing log file: " + e.getMessage());
        }
    }

    // Métodos de logging con niveles predefinidos
    public static void warning(String msg) { log(LoggerLevel.WARNING, msg); }
    public static void success(String msg) { log(LoggerLevel.SUCCESS, msg); }
    public static void error(String msg) { log(LoggerLevel.ERROR, msg); }
    public static void debug(String msg) { log(LoggerLevel.DEBUG, msg); }
    public static void info(String msg) { log(LoggerLevel.INFO, msg); }
    public static void outline(String msg) { log(LoggerLevel.OUTLINE, msg); }
    public static void chat(String msg) { log(LoggerLevel.CHAT, msg); }

    // Método para logs con excepciones detalladas
    public static void error(@NotNull Exception e, String msg) {
        log(LoggerLevel.ERROR, msg + " | Exception: " + e.getMessage());
        e.printStackTrace();
    }

    // Mensaje sin prefijo específico
    public static void defaultMessage(String msg) {
        String formattedMsg = Settings.enable_colors
                ? Utils.getPrefix(true) + msg
                : msg;

        MessageUtils.sendColorMessage(EventUtils.getConsoleSender(), formattedMsg);
    }
}
