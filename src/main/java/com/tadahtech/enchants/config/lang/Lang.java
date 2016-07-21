package com.tadahtech.enchants.config.lang;

import com.tadahtech.enchants.Enchants;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;

/**
 *
 */
public class Lang {

    private String prefix;
    private final Map<MessageKey, String> messages = Maps.newHashMap();

    public Lang() {
        load();
    }

    public void load() {
        File file = new File(Enchants.getInstance().getDataFolder(), "language.yml");

        if (!file.exists()) {
            Enchants.getInstance().saveResource("language.yml", true);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        this.prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));

        ConfigurationSection section = config.getConfigurationSection("enchants");

        for (String s : section.getKeys(false)) {
            MessageKey messageKey = MessageKey.valueOf(s.toUpperCase());
            String message = section.getString(s);
            messages.put(messageKey, message);
        }
    }

    public void sendMessage(Player player, MessageKey key, Map<Argument, String> args) {
        String message = messages.get(key);

        if (message == null) {
            player.sendMessage(ChatColor.RED + "No message found for key: " + key.name());
            return;
        }

        if(args == null) {
            player.sendMessage(this.prefix + " " + ChatColor.translateAlternateColorCodes('&', message));
            return;
        }

        Map<String, Object> map = Maps.newHashMap();

        for (Map.Entry<Argument, String> entry : args.entrySet()) {
            map.put(entry.getKey().getInText(), entry.getValue());
        }

        if(message.split("\\|").length > 0) {
            for(String s : message.split("\\|")) {
                player.sendMessage(this.prefix + " " + ChatColor.translateAlternateColorCodes('&', replaceTokens(s, map)));
            }
            return;
        }

        player.sendMessage(this.prefix + " " + ChatColor.translateAlternateColorCodes('&', replaceTokens(message, map)));

    }

    protected static String replaceTokens(String src, Map<String, Object> replacements) {
        if (src.indexOf('{') == -1 || src.indexOf('}') == -1) {
            return src;
        }

        StringBuilder builder = new StringBuilder(src);

        int openIx = -1, closeIx;
        for (int i = 0; i < builder.length(); i++) {
            // Find open bracket or continue early
            if (openIx == -1) {
                if (builder.charAt(i) == '{') {
                    openIx = i;
                }
                continue;
            }

            // Keep going until we find a close bracket
            if (builder.charAt(i) != '}') {
                continue;
            }

            closeIx = i;

            String label = builder.substring(openIx + 1, closeIx);
            Object object = replacements.get(label);
            // Replace token if we have a replacement
            if (object != null) {
                String replacement = replacements.get(label).toString();
                builder.replace(openIx, closeIx + 1, replacement);

                // Adjust our search index accordingly
                int lengthDiff = replacement.length() - (closeIx + 1 - openIx);
                i += lengthDiff;
            }

            openIx = -1;
        }

        return builder.toString();
    }

    public String getPrefix() {
        return prefix;
    }

}
