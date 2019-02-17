package com.ravvenlord.blossom.scoreboard;

import com.ravvenlord.blossom.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class BlossomScoreboardManager {

    private Scoreboard scoreboard;
    private Function<UUID, String> nameLookup;

    public BlossomScoreboardManager(Scoreboard scoreboard, Function<UUID, String> nameLookup) {
        this.scoreboard = scoreboard;
        this.nameLookup = nameLookup;
    }

    public void updateTeam(UUID playerUUID, PlayerData playerData) {
        String teamName = playerUUID.toString().replaceAll("-", "").substring(0, 15);
        String playerName = nameLookup.apply(playerUUID);

        Team playerTeam = Optional.ofNullable(this.scoreboard.getTeam(teamName))
                .orElseGet(() -> this.scoreboard.registerNewTeam(teamName));

        playerTeam.setPrefix(playerData.getFirstName() + " [");
        playerTeam.setSuffix("] " + playerData.getLastName());
        playerTeam.setColor(playerData.getNameColor());
        playerTeam.setDisplayName(playerName);
        playerTeam.addEntry(playerName);
    }

    public void remove(UUID uuid) {
        String teamName = uuid.toString().replaceAll("-", "").substring(0, 15);
        Optional.ofNullable(this.scoreboard.getTeam(teamName)).ifPresent(Team::unregister);
    }

    public void preparePlayer(Player player) {
        player.setScoreboard(this.scoreboard);
    }
}
