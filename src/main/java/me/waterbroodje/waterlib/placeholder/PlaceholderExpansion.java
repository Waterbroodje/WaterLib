package me.waterbroodje.waterlib.placeholder;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {
    private final List<Placeholder> placeholders = new ArrayList<>();
    private final String identifier;
    private final String author;
    private final String version;

    public PlaceholderExpansion(String identifier, String author, String version, Set<Placeholder> placeholderSet) {
        this.identifier = identifier;
        this.author = author;
        this.version = version;

        placeholders.addAll(placeholderSet);
    }

    Map<String, CachedPlaceholder> placeholderCache = new HashMap<>();

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params) {
        CachedPlaceholder cachedPlaceholder = placeholderCache.computeIfAbsent(params, s -> {
            for(Placeholder placeholder : placeholders) {
                Matcher matcher = placeholder.getPattern().matcher(params);
                if(!matcher.matches()) continue;
                return new CachedPlaceholder(matcher, placeholder);
            }
            return null;
        });

        if(cachedPlaceholder == null) return null;

        try {
            return cachedPlaceholder.getPlaceholder().parse(cachedPlaceholder.getMatcher(), p);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return identifier;
    }

    @Override
    public @NotNull String getAuthor() {
        return author;
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }
}
