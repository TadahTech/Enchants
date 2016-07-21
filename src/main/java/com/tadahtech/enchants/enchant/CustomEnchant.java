package com.tadahtech.enchants.enchant;

import com.tadahtech.enchants.Enchants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public abstract class CustomEnchant {

    private static final Map<String, CustomEnchant> enchants = Maps.newHashMap();
    private static final Map<Rarity, List<CustomEnchant>> enchantsByRarity = Maps.newHashMap();

    private String name;
    private Rarity rarity;
    private CustomEnchantType enchantType;
    private int cooldown;
    private Map<UUID, Long> cooldowns;
    private int maxLevel;

    public CustomEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int cooldown, int maxLevel) {
        this.name = name;
        this.rarity = rarity;
        this.enchantType = enchantType;
        this.cooldown = cooldown;
        this.maxLevel = maxLevel;
        this.cooldowns = Maps.newHashMap();
        List<CustomEnchant> byRare = enchantsByRarity.getOrDefault(rarity, Lists.newLinkedList());
        byRare.add(this);
        enchantsByRarity.put(rarity, byRare);
        enchants.put(name.toLowerCase(), this);
        Enchants.getInstance().getLogger().info("Created " + name + " enchant. Rarity: " + rarity.name());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Enchants.getInstance().getMainConfig().getDescription(getEnchantType());
    }

    public Rarity getRarity() {
        return rarity;
    }

    public CustomEnchantType getEnchantType() {
        return enchantType;
    }

    public boolean canUse(Player player) {
        if(this.cooldown <= 0) {
            return true;
        }
        Long in = this.cooldowns.get(player.getUniqueId());
        if(in == null) {
            return true;
        }
        long remaining = (System.currentTimeMillis() - in) - cooldown;
        if(remaining <= 0) {
            return true;
        }
        return false;
    }

    public void use(Player player) {
        if(this.cooldown <= 0) {
            return;
        }
        this.cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        onUse(player);
    }

    public abstract void onUse(Player player);

    public abstract void onBlockBreak(Player player, Block block, int level);

    public abstract void onPlayerHit(Player player, Player hit, int level);

    public abstract void onSwitchedTo(Player player, int level);

    public abstract void onSwitchedFrom(Player player, int level);

    public abstract void onEquip(Player player, int level);

    public abstract void onUnequip(Player player, int level);

    public abstract boolean canEnchant(ItemStack itemStack);

    public static CustomEnchant getCustomEnchant(String name) {
        return enchants.get(name.toLowerCase());
    }

    public static List<CustomEnchant> getEnchantsByRarity(Rarity rarity) {
        return enchantsByRarity.get(rarity);
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
