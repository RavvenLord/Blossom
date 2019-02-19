package com.ravvenlord.blossom.listener;

import com.ravvenlord.blossom.config.BlossomConfig;
import com.ravvenlord.blossom.data.BasicPlayerData;
import com.ravvenlord.blossom.data.PlayerDataContainer;
import com.ravvenlord.blossom.data.PlayerDataDao;
import com.ravvenlord.blossom.scoreboard.BlossomScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    private PlayerDataDao provider;
    private PlayerDataContainer container;
    private BlossomScoreboardManager scoreboardManager;
    private BlossomConfig config;

    public PlayerConnectionListener(PlayerDataDao provider, PlayerDataContainer container, BlossomScoreboardManager scoreboardManager, BlossomConfig config) {
        this.provider = provider;
        this.container = container;
        this.scoreboardManager = scoreboardManager;
        this.config = config;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        this.provider.getPlayerData(uuid).ifPresentOrElse(d -> {
            event.setJoinMessage(this.config.getJoinMessage(d.getFullColouredName()));

            this.container.pushPlayerData(uuid, d);
            this.scoreboardManager.updateTeam(uuid, d);
            this.scoreboardManager.sendScoreboard(event.getPlayer());

            event.getPlayer().setDisplayName(d.getFullColouredName());
            event.setJoinMessage(this.config.getJoinMessage(d.getFullColouredName()));
        }, () -> this.container.pushPlayerData(uuid, new BasicPlayerData("", "", ChatColor.WHITE)));
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        this.container.removeData(event.getPlayer().getUniqueId()).ifPresent(d -> {
            event.setQuitMessage(this.config.getLeaveMessage(d.getFullColouredName()));

            try {
                this.provider.storePlayerData(event.getPlayer().getUniqueId(), d);
            } catch (IOException e) {
                throw new RuntimeException("Could not store the player data of the player " + event.getPlayer().getName(), e);
            }
        });

        this.scoreboardManager.remove(event.getPlayer().getUniqueId());
    }

}
