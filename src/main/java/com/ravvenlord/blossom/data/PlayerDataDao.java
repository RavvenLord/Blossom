package com.ravvenlord.blossom.data;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface PlayerDataDao {

    Optional<PlayerData> getPlayerData(UUID uuid);

    void storePlayerData(UUID uuid, PlayerData playerData) throws IOException;
}
