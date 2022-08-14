package jss.customjoinandquitmessages.manager;

import jss.customjoinandquitmessages.CustomJoinAndQuitMessages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerManager {

    private final CustomJoinAndQuitMessages plugin = CustomJoinAndQuitMessages.get();
    private final FileConfiguration config = plugin.getPlayerFile().getConfig();

    public String getGroup(@NotNull Player player) {
        if (existsPlayer(player.getName())) return config.getString("Players." + player.getName() + ".Group");
        return null;
    }

    public void setGroup(@NotNull Player player, String group) {
        if (existsPlayer(player.getName())) config.set("Players." + player.getName() + ".Group", group);
    }

    public void createPlayer(@NotNull Player player, String group) {
        if (!existsPlayer(player.getName())) {
            config.set("Players." + player.getName() + ".Group", group);
            this.save();
        }
    }

    private void save() {
        plugin.getPlayerFile().saveConfig();
    }

    public boolean existsPlayer(String path) {
        return config.contains("Players." + path);
    }
}
