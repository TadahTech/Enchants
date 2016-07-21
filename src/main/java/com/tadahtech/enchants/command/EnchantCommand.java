package com.tadahtech.enchants.command;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.book.CustomEnchantmentBook;
import com.tadahtech.enchants.config.lang.Argument;
import com.tadahtech.enchants.config.lang.Lang;
import com.tadahtech.enchants.config.lang.MessageKey;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.listener.NPCListener;
import com.tadahtech.enchants.menu.Menu;
import com.tadahtech.enchants.npc.CustomEntityType;
import com.tadahtech.enchants.npc.NPC;
import com.tadahtech.enchants.util.Colors;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class EnchantCommand implements CommandExecutor {

    private static final String LINE = Colors.DARK_AQUA + Colors.BOLD + "===============================";

    private static final String BASE = "arcadianenchant.";

    private static final String GIVE_RANDOM = BASE + "giverandom";
    private static final String GIVE = BASE + "give";
    private static final String SETNPC = BASE + "setnpc";
    private static final String RELOAD = BASE + "reload";

    private final Lang lang;

    public EnchantCommand() {
        this.lang = Enchants.getInstance().getLang();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            new Menu((Player) commandSender);
            return true;
        }
        Player player = (Player) commandSender;
        if (args.length == 1) {
            String arg = args[0];
            if(arg.equalsIgnoreCase("opennpc")) {
                Enchants.getInstance().getNpcMenu().open(player);
                return true;
            }
            switch (arg.toLowerCase()) {
                case "setnpc":
                    if (!player.hasPermission(SETNPC)) {
                        lang.sendMessage(player, MessageKey.NO_PERMISSION, null);
                        return true;
                    }
                    NPC npc = (NPC) CustomEntityType.NPC.spawn(player.getLocation());
                    Enchants.getInstance().addEntity(npc);
                    lang.sendMessage(player, MessageKey.NPC, null);
                    return true;

                case "reload":
                    if (!player.hasPermission(RELOAD)) {
                        lang.sendMessage(player, MessageKey.NO_PERMISSION, null);
                        return true;
                    }
                    Enchants.getInstance().reloadConfig();
                    lang.sendMessage(player, MessageKey.RELOAD, null);
                    return true;

                case "list":
                    player.sendMessage(LINE);
                    for (Rarity rarity : Rarity.values()) {
                        List<CustomEnchant> enchantTypes = CustomEnchant.getEnchantsByRarity(rarity);
                        lang.sendMessage(player, MessageKey.LIST_ALL_CATEGORY, Collections.singletonMap(Argument.CATEGORY, Enchants.getInstance().getMainConfig().getColor(rarity) + rarity.name()));
                        for (CustomEnchant enchant : enchantTypes) {
                            Map<Argument, String> map = Maps.newHashMap();
                            map.put(Argument.ENCHANT, enchant.getName());
                            map.put(Argument.MAX_LEVELS, String.valueOf(enchant.getMaxLevel()));

                            lang.sendMessage(player, MessageKey.LIST_ALL_ENCHANT, map);
                        }
                    }
                    player.sendMessage(LINE);
                    return true;

                case "deletenpc":
                    if (!player.hasPermission(SETNPC)) {
                        lang.sendMessage(player, MessageKey.NO_PERMISSION, null);
                        return true;
                    }
                    NPCListener.toggle(player);
                    return true;
            }
            player.sendMessage(lang.getPrefix() + Colors.RED + " Unknown command");
            return true;
        }
        if (args.length == 2) {
            String arg = args[0];
            String second = args[1];

            if (arg.equalsIgnoreCase("list")) {
                try {
                    Rarity rarity = Rarity.valueOf(second.toUpperCase());
                    List<CustomEnchant> enchantTypes = CustomEnchant.getEnchantsByRarity(rarity);

                    player.sendMessage(LINE);

                    lang.sendMessage(player, MessageKey.LIST_ALL_CATEGORY, Collections.singletonMap(Argument.CATEGORY, Enchants.getInstance().getMainConfig().getColor(rarity) + rarity.name()));

                    for (CustomEnchant enchant : enchantTypes) {
                        Map<Argument, String> map = Maps.newHashMap();
                        map.put(Argument.ENCHANT, enchant.getName());
                        map.put(Argument.MAX_LEVELS, String.valueOf(enchant.getMaxLevel()));

                        lang.sendMessage(player, MessageKey.LIST_ALL_ENCHANT, map);
                    }

                    player.sendMessage(LINE);
                } catch (Exception e) {
                    lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                    return true;
                }
            }
            return true;
        }

        if (args.length == 3) {
            String arg = args[0];

            if (!arg.equalsIgnoreCase("give")) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            if(!player.hasPermission(GIVE_RANDOM)) {
                lang.sendMessage(player, MessageKey.NO_PERMISSION, null);
                return true;
            }

            String second = args[1];
            String third = args[2];
            Rarity rarity;

            try {
                rarity = Rarity.valueOf(third.toUpperCase());
            } catch (Exception e) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            Player target = Bukkit.getPlayerExact(second);

            if (target == null) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            CustomEnchant enchant = Enchants.getInstance().getEnchantmentCalculator().getNewEnchant(rarity);
            CustomEnchantmentBook book = new CustomEnchantmentBook(enchant, 1);

            ItemStack itemStack = book.toItemStack();
            target.getInventory().addItem(itemStack);

            Map<Argument, String> map = Maps.newHashMap();
            map.put(Argument.PLAYER, target.getName());
            map.put(Argument.CATEGORY, rarity.name());
            lang.sendMessage(player, MessageKey.GIVE_RANDOM, map);
            return true;
        }

        if (args.length == 4) {
            String arg = args[0];

            if (!arg.equalsIgnoreCase("give")) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            if(!player.hasPermission(GIVE)) {
                lang.sendMessage(player, MessageKey.NO_PERMISSION, null);
                return true;
            }

            String second = args[1];
            String third = args[2];
            String fourth = args[3];

            Player target = Bukkit.getPlayerExact(second);
            if (target == null) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            CustomEnchant enchant = CustomEnchant.getCustomEnchant(third);
            if (enchant == null) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            int level = 0;
            try {
                level = Integer.parseInt(fourth);
                if(level > enchant.getMaxLevel()) {
                    lang.sendMessage(player, MessageKey.TOO_HIGH_LEVEL, null);
                    return true;
                }
            } catch (Exception e) {
                lang.sendMessage(player, MessageKey.INVALID_ARGS, null);
                return true;
            }

            CustomEnchantmentBook book = new CustomEnchantmentBook(enchant, level);

            ItemStack itemStack = book.toItemStack();
            target.getInventory().addItem(itemStack);

            Map<Argument, String> map = Maps.newHashMap();
            map.put(Argument.PLAYER, target.getName());
            map.put(Argument.ENCHANT, enchant.getName());
            map.put(Argument.LEVEL, String.valueOf(level));
            lang.sendMessage(player, MessageKey.GIVE, map);
            return true;
        }

        return true;
    }
}
