package com.ravvenlord.blossom.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class SimpleBlossomConfigBuilder {

    public BlossomConfig build(FileConfiguration configuration) {
        return new SimpleBlossomConfig(
                translate(configuration.getString("prefix")),
                translate(configuration.getString("join-message", "%s joined the game")),
                translate(configuration.getString("leave-message", "%s has left the game")),
                configuration.getString("opening-bracket", " ["),
                configuration.getString("closing-bracket", "] "));
    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
