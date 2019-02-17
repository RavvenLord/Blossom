package com.ravvenlord.blossom.config;

public class SimpleBlossomConfig implements BlossomConfig {

    private String prefix;
    private String joinMessage;
    private String leaveMessage;

    public SimpleBlossomConfig(String prefix, String joinMessage, String leaveMessage) {
        this.prefix = prefix;
        this.joinMessage = joinMessage;
        this.leaveMessage = leaveMessage;
    }

    @Override
    public String getRawPrefix() {
        return this.prefix;
    }

    @Override
    public String getJoinMessage(String fullName) {
        return String.format(this.joinMessage, fullName);
    }

    @Override
    public String getLeaveMessage(String fullName) {
        return String.format(this.leaveMessage, fullName);
    }
}
