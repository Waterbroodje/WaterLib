package me.waterbroodje.waterlib.utilities.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.OfflinePlayer;

public class MessageBuilder {
    private final String message;

    public MessageBuilder(String message) {
        this.message = message;
    }

    public MessageBuilder replace(String placeholder, String replacement) {
        return new MessageBuilder(message.replace(placeholder, replacement));
    }

    public MessageBuilder color() {
        return new MessageBuilder(Color.colorize(message));
    }

    public MessageBuilder append(String message) {
        return new MessageBuilder(this.message + message);
    }

    public MessageBuilder appendPlaceholder(OfflinePlayer player) {
        return new MessageBuilder(this.message + PlaceholderAPI.setPlaceholders(player, message));
    }

    public MessageBuilder addClickEvent(ClickEvent.Action action, String value) {
        return new MessageBuilder(this.message + new ClickEvent(action, value).toString());
    }

    public MessageBuilder addHoverEvent(HoverEvent.Action action, String value) {
        return new MessageBuilder(this.message + new HoverEvent(action, new Text(value)).toString());
    }

    public MessageBuilder addHoverEvent(HoverEvent.Action action, Content content) {
        return new MessageBuilder(this.message + new HoverEvent(action, content).toString());
    }

    public String build() {
        return message;
    }
}
