package com.ravvenlord.blossom.config;

public class SimpleBlossomConfig implements BlossomConfig {

    private String prefix;
    private String joinMessage;
    private String leaveMessage;
    private String openingBracket, closingBracket;

    public SimpleBlossomConfig(String prefix, String joinMessage, String leaveMessage, String openingBracket, String closingBracket) {
        this.prefix = prefix;
        this.joinMessage = joinMessage;
        this.leaveMessage = leaveMessage;
        this.openingBracket = openingBracket;
        this.closingBracket = closingBracket;
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

    @Override
    public String getOpeningPlayerBracket() {
        return this.openingBracket;
    }

    @Override
    public String getClosingPlayerBracket() {
        return this.closingBracket;
    }


}
