package com.ravvenlord.blossom;

import com.google.gson.GsonBuilder;
import com.ravvenlord.blossom.command.SetNameCommand;
import com.ravvenlord.blossom.command.SetNameTabCompletor;
import com.ravvenlord.blossom.config.BlossomConfig;
import com.ravvenlord.blossom.config.SimpleBlossomConfigBuilder;
import com.ravvenlord.blossom.data.JsonPlayerDataDao;
import com.ravvenlord.blossom.data.MapPlayerDataContainer;
import com.ravvenlord.blossom.data.PlayerDataContainer;
import com.ravvenlord.blossom.data.PlayerDataDao;
import com.ravvenlord.blossom.listener.PlayerConnectionListener;
import com.ravvenlord.blossom.scoreboard.BlossomScoreboardManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Blossom extends JavaPlugin {

    private PlayerDataDao playerDataDao;
    private PlayerDataContainer playerDataContainer;
    private BlossomScoreboardManager scoreboardManager;
    private BlossomConfig config;

    @Override
    public void onEnable() {
        Path playerDataFolder = getDataFolder().toPath().resolve(Path.of("playerData"));
        if (Files.notExists(playerDataFolder)) {
            getLogger().info("Creating new player data directory");
            try {
                Files.createDirectories(playerDataFolder);
            } catch (IOException e) {
                throw new RuntimeException("Could not create data folder", e);
            }
        }

        this.saveDefaultConfig();
        this.config = new SimpleBlossomConfigBuilder().build(this.getConfig());

        this.playerDataDao = new JsonPlayerDataDao(playerDataFolder, new GsonBuilder().setPrettyPrinting().create());
        this.playerDataContainer = new MapPlayerDataContainer();
        this.scoreboardManager = new BlossomScoreboardManager(getServer().getScoreboardManager().getMainScoreboard()
                , uuid -> Optional.ofNullable(getServer().getPlayer(uuid)).map(HumanEntity::getName).orElse(null)
                , this.config);

        getLogger().info("Loading currently online players");
        getServer().getOnlinePlayers().forEach(player -> {
            this.playerDataDao.getPlayerData(player.getUniqueId())
                    .ifPresent(d -> {
                        this.playerDataContainer.pushPlayerData(player.getUniqueId(), d);
                        this.scoreboardManager.updateTeam(player.getUniqueId(), d);
                    });

            this.scoreboardManager.preparePlayer(player);
        });

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this.playerDataDao, this.playerDataContainer, this.scoreboardManager, config), this);

        PluginCommand setNameCommand = getCommand("setname");
        setNameCommand.setExecutor(new SetNameCommand(this.config, getServer()::getPlayerExact, this.playerDataContainer, this.scoreboardManager));
        setNameCommand.setTabCompleter(new SetNameTabCompletor(getServer()));

        getLogger().info("Enabled blossom");
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().stream()
                .map(Player::getUniqueId).forEach(u -> this.playerDataContainer.getPlayerData(u).ifPresent(p -> {
            try {
                this.scoreboardManager.remove(u);
                this.playerDataDao.storePlayerData(u, p);
            } catch (IOException e) {
                getLogger().info("Could not store player data of player " + u.toString());
                e.printStackTrace();
            }
        }));
    }
}
