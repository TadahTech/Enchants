package com.tadahtech.enchants.book;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.EnchantLevel;
import com.tadahtech.enchants.item.ItemBuilder;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CustomEnchantmentBook {

    private static final Cache<ItemStack, CustomEnchantmentBook> books = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    private CustomEnchant enchant;
    private ItemStack itemStack;
    private int level;

    public CustomEnchantmentBook(CustomEnchant enchant, int level) {
        this.enchant = enchant;
        this.level = level;
    }

    public static CustomEnchantmentBook getFromItemStack(ItemStack itemStack) {
        if (books.getIfPresent(itemStack) != null) {
            return books.getIfPresent(itemStack);
        }

        if (!itemStack.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (!meta.hasDisplayName()) {
            return null;
        }

        String name = ChatColor.stripColor(meta.getDisplayName());

        if (!name.contains(" ")) {
            return null;
        }

        String[] str = name.split(" ");

        if (str.length < 2) {
            return null;
        }

        String first = str[0];

        CustomEnchant enchant = CustomEnchant.getCustomEnchant(first);

        if (enchant == null) {
            return null;
        }

        int level = EnchantLevel.getNumber(str[1]);

        CustomEnchantmentBook book = new CustomEnchantmentBook(enchant, level);
        book.itemStack = itemStack;
        books.put(itemStack, book);
        return book;
    }

    public CustomEnchant getEnchant() {
        return enchant;
    }

    public ItemStack toItemStack() {
        if (itemStack == null) {
            ItemBuilder builder = new ItemBuilder(Material.ENCHANTED_BOOK);
            String nameFormat = Enchants.getInstance().getMainConfig().getNameFormat();
            String loreFormat = Enchants.getInstance().getMainConfig().getLoreFormat();

            String name = nameFormat.replace("{ENCHANT}", enchant.getName()).replace("{LEVEL}", EnchantLevel.getRoman(level));
            name = ChatColor.translateAlternateColorCodes('&', name);
            builder.setTitle(name);

            boolean multiLineDesc = enchant.getDescription().split("\\|").length > 0;

            List<String> lore = Lists.newArrayList();
            if (loreFormat.split("\\|").length > 0) {
                for (String s : loreFormat.split("\\|")) {
                    String line = s;
                    line = line.replace("{ENCHANT_DESCRIPTION}", enchant.getDescription());
                    line = ChatColor.translateAlternateColorCodes('&', line);
                    if(multiLineDesc && s.contains("{ENCHANT_DESCRIPTION}"))
                    {
                        for(String l : enchant.getDescription().split("\\|"))
                        {
                            lore.add(ChatColor.translateAlternateColorCodes('&', l));
                        }
                    }
                    lore.add(line);
                }
            } else {
                String line = loreFormat;
                line = line.replace("{ENCHANT_DESCRIPTION}", enchant.getDescription());
                line = ChatColor.translateAlternateColorCodes('&', line);
                if(multiLineDesc && line.contains("{ENCHANT_DESCRIPTION}"))
                {
                    for(String l : enchant.getDescription().split("\\|"))
                    {
                        lore.add(ChatColor.translateAlternateColorCodes('&', l));
                    }
                }
                lore.add(line);
            }
            builder.addLoreLines(lore);
            this.itemStack = builder.build();
            books.put(itemStack, this);
        }
        return itemStack;
    }

    public int getLevel() {
        return level;
    }
}
