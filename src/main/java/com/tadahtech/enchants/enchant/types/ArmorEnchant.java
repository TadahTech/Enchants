package com.tadahtech.enchants.enchant.types;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 */
public abstract class ArmorEnchant extends CustomEnchant {

    public ArmorEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int maxLevel) {
        super(name, rarity, enchantType, -1, maxLevel);
    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {

    }

    @Override
    public void onSwitchedTo(Player player, int level) {

    }

    @Override
    public void onSwitchedFrom(Player player, int level) {

    }

}
