package com.tadahtech.enchants.item;

import com.tadahtech.enchants.enchant.GlowEnchant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemBuilder {

    private int amount;
    private short data;
    private final HashMap<Enchantment, Integer> enchants = Maps.newHashMap();
    private final List<String> lore = Lists.newArrayList();
    private Material mat;
    private String title;
    private boolean glow;

    public ItemBuilder(Material mat) {
        this(mat, 1, (short) 0);
    }

    public ItemBuilder(Material mat, int amount, short data) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder setGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.lore.clear();
        this.lore.addAll(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLoreLines(List<String> list) {
        this.lore.addAll(list);
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.mat, this.amount, this.data);
        ItemMeta meta = item.getItemMeta();

        if (this.title != null) {
            meta.setDisplayName(this.title);
        }
        if (!this.lore.isEmpty()) {
            meta.setLore(this.lore);
        }
        item.setItemMeta(meta);

        if (glow) {
            item.addEnchantment(GlowEnchant.getGlowEnchant(), 1);
        }

        item.addUnsafeEnchantments(this.enchants);
        return item;
    }

    public Material getType() {
        return this.mat;
    }

    public ItemBuilder setData(short newData) {
        this.data = newData;
        return this;
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

}