package me.waterbroodje.waterlib.utilities;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Color {
    private final String message;

    public Color(String message) {
        this.message = message;
    }

    public String translate() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String translateCustomChar(Character colorChar) {
        return ChatColor.translateAlternateColorCodes(colorChar, message);
    }

    public String translateHexColorCodes(String startTag, String endTag) {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }
}
