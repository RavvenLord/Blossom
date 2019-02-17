package com.ravvenlord.blossom.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class JsonPlayerDataDao implements PlayerDataDao {

    private Path playerDirectory;
    private Gson gson;

    public JsonPlayerDataDao(Path playerDirectory, Gson gson) {
        this.playerDirectory = playerDirectory;
        this.gson = gson;
    }

    @Override
    public Optional<PlayerData> getPlayerData(UUID uuid) {
        Path playerFilePath = this.playerDirectory.resolve(uuid.toString() + ".json");
        try (Reader reader = Files.newBufferedReader(playerFilePath)) {
            return Optional.of(gson.fromJson(reader, BasicPlayerData.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void storePlayerData(UUID uuid, PlayerData playerData) throws IOException {
        Path playerFilePath = this.playerDirectory.resolve(uuid.toString() + ".json");
        try (Writer writer = Files.newBufferedWriter(playerFilePath)) {
            gson.toJson(playerData, BasicPlayerData.class, writer);
        }
    }
}
