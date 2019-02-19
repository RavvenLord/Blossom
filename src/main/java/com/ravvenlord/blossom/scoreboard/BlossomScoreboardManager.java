package com.ravvenlord.blossom.scoreboard;

import com.ravvenlord.blossom.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * The {@link BlossomScoreboardManager} interfaces defines a scoreboard manager that can update the player scorebpard
 */
public interface BlossomScoreboardManager {
    /**
     * Updates the team of the player to meet the required player data
     *
     * @param playerUUID the uuid of the player
     * @param playerData the {@link PlayerData} instance with the values to update against
     */
    void updateTeam(UUID playerUUID, PlayerData playerData);

    /**
     * Remove removes the given uuid from the scoreboard manager
     *
     * @param uuid the uuid to remove
     */
    void remove(UUID uuid);

    /**
     * Prepares the player by assigning the managed scoreboard to the player
     * @param player the player to send the scoreboard to
     */
    void sendScoreboard(Player player);
}
