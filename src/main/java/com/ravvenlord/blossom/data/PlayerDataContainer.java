package com.ravvenlord.blossom.data;

import java.util.Optional;
import java.util.UUID;

public interface PlayerDataContainer {

    Optional<PlayerData> getPlayerData(UUID uuid);

    void pushPlayerData(UUID uuid, PlayerData playerData);

    Optional<PlayerData> removeData(UUID uuid);
}
