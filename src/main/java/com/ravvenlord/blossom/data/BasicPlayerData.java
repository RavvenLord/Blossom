package com.ravvenlord.blossom.data;

import org.bukkit.ChatColor;

public class BasicPlayerData implements PlayerData {

    private String firstName;
    private String lastName;
    private ChatColor nameColor;

    public BasicPlayerData(String firstName, String lastName, ChatColor nameColor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nameColor = nameColor;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public ChatColor getNameColor() {
        return this.nameColor;
    }

    @Override
    public void setFirstName(String name) {
        this.firstName = name;
    }

    @Override
    public void setLastName(String name) {
        this.lastName = name;
    }

    @Override
    public void setNameColor(ChatColor color) {
        this.nameColor = color;
    }
}
