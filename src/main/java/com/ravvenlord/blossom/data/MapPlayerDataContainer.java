package com.ravvenlord.blossom.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MapPlayerDataContainer implements PlayerDataContainer {

    private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    @Override
    public Optional<PlayerData> getPlayerData(UUID uuid) {
        return Optional.ofNullable(playerDataMap.get(uuid));
    }

    @Override
    public void pushPlayerData(UUID uuid, PlayerData playerData) {
        this.playerDataMap.put(uuid, playerData);
    }

    @Override
    public Optional<PlayerData> removeData(UUID uuid) {
        return Optional.ofNullable(this.playerDataMap.remove(uuid));
    }
}
