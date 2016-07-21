package com.tadahtech.enchants.item;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.EnchantLevel;
import com.tadahtech.enchants.enchant.GlowEnchant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 */
public class EnchantedItem {

    private static final Cache<ItemStack, EnchantedItem> items = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    private ItemStack itemStack;

    private List<CustomEnchant> enchants;
    private Map<CustomEnchant, Integer> enchantLevels;

    public EnchantedItem(List<CustomEnchant> enchants, Map<CustomEnchant, Integer> enchantLevels) {
        this.enchantLevels = enchantLevels;
        this.enchants = enchants;
    }

    public EnchantedItem(ItemStack itemStack, CustomEnchant enchant, int level) {
        this.enchants = Lists.newArrayList();
        this.enchants.add(enchant);
        this.enchantLevels = Maps.newHashMap();
        this.enchantLevels.put(enchant, level);
        this.itemStack = itemStack;
        items.put(itemStack, this);
    }

    public static EnchantedItem getFromItemStack(ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }

        if(items.getIfPresent(itemStack) != null) {
            return items.getIfPresent(itemStack);
        }

        if (!itemStack.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (!meta.hasLore()) {
            return null;
        }

        List<String> lore = Lists.newArrayList(meta.getLore());

        List<CustomEnchant> enchants = Lists.newArrayList();
        Map<CustomEnchant, Integer> levels = Maps.newHashMap();

        for (String s : lore) {
            String clone = ChatColor.stripColor(s);

            //CustomEnchant format: ENCHANTMENT LEVEL
            String[] str = clone.split(" ");

            if (str.length == 0) {
                continue;
            }

            String first = str[0];

            CustomEnchant enchant = CustomEnchant.getCustomEnchant(first);

            if(enchant == null) {
                continue;
            }

            int level = EnchantLevel.getNumber(str[1]);
            enchants.add(enchant);
            levels.put(enchant, level);
        }

        if(enchants.isEmpty() || levels.isEmpty()) {
            return null;
        }

        EnchantedItem enchant = new EnchantedItem(enchants, levels);
        enchant.itemStack = itemStack;

        items.put(itemStack, enchant);

        return enchant;
    }

    public boolean has(CustomEnchant enchant) {
        return this.enchants.contains(enchant);
    }

    public List<CustomEnchant> getEnchants() {
        return enchants;
    }

    public Integer getLevel(CustomEnchant enchant) {
        return enchantLevels.get(enchant);
    }

    public void add(CustomEnchant currentEnchant, int i) {
        this.enchants.add(currentEnchant);
        this.enchantLevels.put(currentEnchant, i);
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = this.itemStack;

        if(!itemStack.containsEnchantment(GlowEnchant.getGlowEnchant())) {
            itemStack.addEnchantment(GlowEnchant.getGlowEnchant(), 1);
        }

        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = meta.getLore();

        if(lore == null) {
            lore = Lists.newArrayList();
            lore.addAll(this.enchants.stream().map(enchant -> getFormattingColor(enchant) + enchant.getName() + " " + EnchantLevel.getRoman(this.enchantLevels.get(enchant))).collect(Collectors.toList()));
        } else {
            List<CustomEnchant> alreadyOn = Lists.newArrayList();
            for (String line : lore) {
                String clone = ChatColor.stripColor(line);
                String[] str = clone.split(" ");

                if (str.length == 0) {
                    continue;
                }

                String first = str[0];

                CustomEnchant enchant = CustomEnchant.getCustomEnchant(first);

                if (enchant == null) {
                    continue;
                }

                alreadyOn.add(enchant);

            }

            for(CustomEnchant enchant : this.enchants) {
                if(alreadyOn.contains(enchant)) {
                    continue;
                }
                lore.add(getFormattingColor(enchant) + enchant.getName() + " " + EnchantLevel.getRoman(this.enchantLevels.get(enchant)));
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        items.put(itemStack, this);
        this.itemStack = itemStack;

        return itemStack;
    }

    public String getFormattingColor(CustomEnchant enchant) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', Enchants.getInstance().getMainConfig().getColor(enchant.getRarity()));
    }
}
