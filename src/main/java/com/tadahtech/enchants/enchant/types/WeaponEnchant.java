package com.tadahtech.enchants.enchant.types;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 */
public abstract class WeaponEnchant extends CustomEnchant {

    public WeaponEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int cooldown, int maxLevel) {
        super(name, rarity, enchantType, cooldown, maxLevel);
    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {

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
       return ItemUtil.isWeapon(itemStack);
    }

}
