package jss.customjoinandquitmessage.libs.luckperms.tasks;

import jss.customjoinandquitmessage.CustomJoinAndQuitMessage;
import jss.customjoinandquitmessage.files.utils.Settings;
import jss.customjoinandquitmessage.libs.luckperms.LuckPermsHook;
import jss.customjoinandquitmessage.storage.PlayerData;
import jss.customjoinandquitmessage.storage.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GroupUpdateTask extends BukkitRunnable {

    private final CustomJoinAndQuitMessage plugin;

    public GroupUpdateTask(CustomJoinAndQuitMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String playerName = player.getName();
            String group = "default";

            if (Settings.luckperms_enabled && LuckPermsHook.get().isLuckPerms()) {

                String luckPermsGroup = LuckPermsHook.get().getGroupName(playerName);

                if (luckPermsGroup != null) {
                    group = luckPermsGroup;
                }
            }

            PlayerDataManager playerDataManager = plugin.getPlayerDataManager();
            PlayerData playerData =  playerDataManager.load(playerName);

            if(playerData == null) {
                playerData = new PlayerData(group);
            } else {

                if(group.equals(playerData.getGroup())) {
                    continue;
                }

                playerData.setGroup(group);
            }

            playerDataManager.savePlayer(
                    playerName,
                    playerData,
                    PlayerDataManager.DataType.BOTH
            );
        }
    }

    public void start(int delay, int ticks, boolean isEnabled) {
        if (isEnabled) {
            this.runTaskTimer(plugin, delay, ticks);
        }
    }
}