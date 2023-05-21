package jss.customjoinandquitmessages.utils.logger;

import jss.customjoinandquitmessages.utils.Settings;
import org.bukkit.scheduler.BukkitRunnable;

public class LoggerTask extends BukkitRunnable {

    public void run() {
        if(Settings.compress_logs){
            LoggerFileUtils.compressLogs();
        }

        if(Settings.delete_compressed_logs){
            LoggerFileUtils.deleteOldCompressedLogs();
        }
    }


}
