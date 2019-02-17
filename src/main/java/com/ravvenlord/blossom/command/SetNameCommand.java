package com.ravvenlord.blossom.command;

import com.ravvenlord.blossom.config.BlossomConfig;
import com.ravvenlord.blossom.data.PlayerDataContainer;
import com.ravvenlord.blossom.scoreboard.BlossomScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class SetNameCommand implements CommandExecutor {

    private BlossomConfig config;
    private Function<String, Player> playerLookup;
    private Predicate<String> isSet;
    private PlayerDataContainer playerDataContainer;
    private BlossomScoreboardManager scoreboardManager;

    public SetNameCommand(BlossomConfig config, Function<String, Player> playerLookup, PlayerDataContainer playerDataContainer, BlossomScoreboardManager scoreboardManager) {
        this.config = config;
        this.playerLookup = playerLookup;
        this.playerDataContainer = playerDataContainer;
        this.scoreboardManager = scoreboardManager;
        this.isSet = s -> !s.equals("_");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blossom.set")) {
            sender.sendMessage(config.getRawPrefix() + ChatColor.RED + "You do not have the permission to edit a player name");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(config.getRawPrefix() + ChatColor.RED + "Please use /setname <playerName> <firstname|_> [secondName|_] [chatColor|_]");
            return true;
        }

        get(0, args).map(this.playerLookup).ifPresentOrElse(target -> {
            playerDataContainer.getPlayerData(target.getUniqueId()).ifPresentOrElse(d -> {

                get(1, args).filter(isSet).ifPresent(d::setFirstName);
                get(2, args).filter(isSet).ifPresent(d::setLastName);
                get(3, args).filter(isSet).map(ChatColor::valueOf).ifPresent(d::setNameColor);

                target.setDisplayName(d.getFullColouredName());
                this.scoreboardManager.updateTeam(target.getUniqueId(), d);
                sender.sendMessage(config.getPrefix() + String.format("The player " + ChatColor.GOLD + "%s"
                        + ChatColor.RESET + " has the name %s", target.getName(), d.getFullColouredName()));

            }, () -> sender.sendMessage(config.getPrefix() + String.format("The player " + ChatColor.GOLD + "%s"
                    + ChatColor.RESET + " has no player data. Relogging may help", args[0])));

        }, () -> sender.sendMessage(config.getPrefix() + String.format("The player " + ChatColor.GOLD + "%s"
                + ChatColor.RESET + " is not online", args[0])));

        return true;
    }

    private <T> Optional<T> get(int index, T[] array) {
        return index >= array.length || index < 0 ? Optional.empty() : Optional.ofNullable(array[index]);
    }
}
