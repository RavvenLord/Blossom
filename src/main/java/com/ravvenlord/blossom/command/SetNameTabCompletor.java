package com.ravvenlord.blossom.command;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class SetNameTabCompletor implements TabCompleter {

    private static final List<String> EMPTY_LIST = new CopyOnWriteArrayList<>();

    private Server server;

    public SetNameTabCompletor(Server server) {
        this.server = server;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return server.getOnlinePlayers().stream().map(Player::getName).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
            case 2:
            case 3:
                return args[args.length - 1].isEmpty() ? Collections.singletonList("_") : EMPTY_LIST;
            case 4:
                return Arrays.stream(ChatColor.values()).map(Enum::name).filter(s -> s.startsWith(args[3].toUpperCase())).collect(Collectors.toList());
            default:
                return EMPTY_LIST;
        }
    }
}
