
package jss.customjoinandquitmessage.files.utils;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.utils.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

//Nota no olvidar: pasar traducción de doc a ingles :V

/**
 * Gestiona la búsqueda de archivos de idiomas disponibles para el plugin.
 * <p>
 * Busca primero los archivos de idioma existentes en el directorio externo
 * {@code langs}. Si no encuentra ninguno válido, inspecciona el JAR del plugin
 * para obtener los idiomas incluidos como recursos predeterminados.
 *
 * @since 1.6.0
 */
public class FileList {

    private static final String LANGS_DIRECTORY = "langs";
    private static final String FILE_EXTENSION = ".yml";
    private static final Pattern LANGUAGE_FILE = Pattern.compile("[a-z]{2}_[a-z]{2}\\.yml");

    private final CustomJoinAndQuitMessage plugin = CustomJoinAndQuitMessage.get();

    /**
     * Obtiene los identificadores de los idiomas disponibles.
     * <p>
     * Prioriza los archivos externos. Si no encuentra archivos de idioma
     * válidos en el directorio externo, busca los idiomas incluidos en el JAR.
     *
     * @return lista de identificadores de idiomas sin la extensión {@code .yml}
     * @throws IOException si ocurre un error al abrir o leer el archivo JAR
     */
    public @NotNull List<String> list() throws IOException {
        List<String> externalLanguages = listExternalLanguages();

        if (!externalLanguages.isEmpty()) {
            return externalLanguages;
        }

        return listJarLanguages();
    }

    /**
     * Busca los archivos de idioma en el directorio externo del plugin.
     *
     * @return lista de identificadores de idiomas encontrados; estará vacía
     *         si el directorio no existe, no es un directorio o no contiene
     *         archivos válidos
     */
    private @NotNull List<String> listExternalLanguages() {
        File directory = new File(
                plugin.getDataFolder(),
                LANGS_DIRECTORY
        );

        if (!directory.isDirectory()) {
            return new ArrayList<>();
        }

        String[] files = filterFiles(directory);
        List<String> result = new ArrayList<>();

        if (files == null) {
            return result;
        }

        for (String fileName : files) {
            result.add(removeExtension(fileName));
        }

        return result;
    }

    /**
     * Filtra los archivos del directorio según el formato de los nombres
     * de archivo de idioma admitidos.
     * <p>
     * Los archivos YAML que no cumplen el formato esperado se omiten y,
     * salvo {@code messages.yml}, generan una advertencia en el registro.
     *
     * @param directory directorio cuyos archivos se van a filtrar
     * @return nombres de los archivos que cumplen el formato esperado,
     *         o {@code null} si no se pudieron listar los archivos
     */
    private @Nullable String[] filterFiles(@NotNull File directory) {
        FilenameFilter filter = (dir, name) -> {
            String normalizedName = name.toLowerCase(Locale.ROOT);

            if (LANGUAGE_FILE.matcher(normalizedName).matches()) {
                return true;
            }

            if (normalizedName.endsWith(FILE_EXTENSION) && !normalizedName.equals("messages.yml")) {
                Logger.warning("File: " + name + " is not in the correct format for a language file - skipping...");
            }

            return false;
        };

        return directory.list(filter);
    }

    /**
     * Busca los archivos de idioma incluidos dentro del JAR del plugin.
     * <p>
     * Solo considera archivos que se encuentren directamente dentro del
     * directorio {@code langs} del JAR y que cumplan el formato esperado.
     *
     * @return lista de identificadores de idiomas encontrados, sin la
     *         extensión {@code .yml}
     * @throws IOException si ocurre un error al abrir o leer el JAR
     * @throws IllegalStateException si no se puede obtener el archivo JAR
     */
    private @NotNull List<String> listJarLanguages() throws IOException {
        List<String> result = new ArrayList<>();

        try (JarFile jarFile = new JarFile(getPluginFile())) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String path = entry.getName();

                if (entry.isDirectory() || !path.startsWith(LANGS_DIRECTORY + "/")) {
                    continue;
                }

                String fileName = path.substring((LANGS_DIRECTORY + "/").length());

                if (LANGUAGE_FILE.matcher(fileName.toLowerCase(Locale.ROOT)).matches()) {
                    result.add(removeExtension(fileName));
                }
            }
        }

        return result;
    }

    /**
     * Elimina la extensión {@code .yml} del final de un nombre de archivo.
     *
     * @param fileName nombre del archivo que se va a procesar
     * @return nombre sin la extensión {@code .yml}, si estaba presente;
     *         de lo contrario, devuelve el nombre original
     */
    private @NotNull String removeExtension(@NotNull String fileName) {
        if (fileName.endsWith(FILE_EXTENSION)) {
            return fileName.substring(0, fileName.length() - FILE_EXTENSION.length()
            );
        }

        return fileName;
    }

    /**
     * Obtiene mediante Reflection el archivo JAR desde el que se carga
     * la instancia del plugin.
     *
     * @return archivo JAR del plugin
     * @throws IllegalStateException si no se puede acceder al método
     *         interno {@code getFile()} o si su invocación falla
     */
    private @NotNull File getPluginFile() {
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);

            return (File) method.invoke(plugin);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not obtain the plugin JAR file", e);
        }
    }
}
