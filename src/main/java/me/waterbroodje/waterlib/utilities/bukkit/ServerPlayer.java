package me.waterbroodje.waterlib.utilities.bukkit;

import me.waterbroodje.waterlib.utilities.chat.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ServerPlayer {
    private final Player player;

    public ServerPlayer(Player player) {
        this.player = player;
    }

    public ServerPlayer(String name) {
        this.player = Bukkit.getPlayer(name);
    }

    public ServerPlayer(UUID uuid) {
        this.player = Bukkit.getPlayer(uuid);
    }

    public void sendActionBar(String message) {
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(message));
    }

    public void sendMessage(MessageBuilder message) {
        player.sendMessage(message.build());
    }

    public Player getPlayer() {
        return player;
    }
}