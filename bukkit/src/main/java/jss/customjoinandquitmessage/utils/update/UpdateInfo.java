package jss.customjoinandquitmessage.utils.update;

public class UpdateInfo {

    private final String versionNumber;
    private final String versionType;
    private final String changelog;
    private final String downloadUrl;

    public UpdateInfo(String versionNumber, String versionType, String changelog, String downloadUrl) {
        this.versionNumber = versionNumber;
        this.versionType = versionType;
        this.changelog = changelog;
        this.downloadUrl = downloadUrl;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public String getVersionType() {
        return versionType;
    }

    public String getChangelog() {
        return changelog;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Override
    public String toString() {
        return versionNumber + " (" + versionType + ")";
    }
}
