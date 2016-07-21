package com.tadahtech.enchants.enchant.types;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 */
public abstract class DamagedEnchant extends CustomEnchant {

    public DamagedEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int cooldown, int maxLevel) {
        super(name, rarity, enchantType, cooldown, maxLevel);
    }

    public abstract void onDamage(Player player, Player damager, EntityDamageByEntityEvent event, int level);

    @Override
    public void onUse(Player player) {

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

    @Override
    public void onEquip(Player player, int level) {

    }

    @Override
    public void onUnequip(Player player, int level) {

    }

}
