package com.ravvenlord.blossom.config;

import org.bukkit.ChatColor;

public interface BlossomConfig {

    String getRawPrefix();

    default String getPrefix() {
        return this.getRawPrefix() + ChatColor.RESET + " ";
    }

    String getJoinMessage(String fullName);

    String getLeaveMessage(String fullName);

    String getOpeningPlayerBracket();

    String getClosingPlayerBracket();
}
