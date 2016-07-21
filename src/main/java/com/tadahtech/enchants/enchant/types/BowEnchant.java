package com.tadahtech.enchants.enchant.types;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 */
public abstract class BowEnchant extends CustomEnchant {

    public BowEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int cooldown, int maxLevel) {
        super(name, rarity, enchantType, cooldown, maxLevel);
    }

    public abstract void onShoot(Player player, EntityShootBowEvent event, int level);

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

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getType() == Material.BOW;
    }
}
