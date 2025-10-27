package jss.customjoinandquitmessage.utils.update.core;

import java.util.List;

public class UpdateInfo {

    private final String version_number;
    private final String version_type;
    private final String changelog;
    private final List<FileInfo> files;

    public UpdateInfo(String version_number, String version_type, String changelog, List<FileInfo> files) {
        this.version_number = version_number;
        this.version_type = version_type;
        this.changelog = changelog;
        this.files = files;
    }

    public String getVersionNumber() { return version_number; }

    public String getVersionType() { return version_type; }

    public String getChangelog() { return changelog; }

    public String getDownloadUrl() {
        if (files != null && !files.isEmpty()) return files.get(0).url;
        return "No available";
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public static class FileInfo {
        public String url;
    }

    @Override
    public String toString() {
        return version_number + " (" + version_type + ")";
    }
}
