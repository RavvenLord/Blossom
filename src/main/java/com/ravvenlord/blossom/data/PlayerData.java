package com.ravvenlord.blossom.data;

import org.bukkit.ChatColor;

public interface PlayerData {

    String getFirstName();

    void setFirstName(String name);

    String getLastName();

    void setLastName(String name);

    ChatColor getNameColor();

    void setNameColor(ChatColor color);

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    default String getFullColouredName() {
        return getNameColor() + getFullName() + ChatColor.RESET;
    }
}
