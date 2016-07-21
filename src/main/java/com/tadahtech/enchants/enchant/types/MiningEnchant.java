package com.tadahtech.enchants.enchant.types;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 */
public abstract class MiningEnchant extends CustomEnchant {


    public MiningEnchant(String name, Rarity rarity, CustomEnchantType enchantType, int cooldown, int maxLevel) {
        super(name, rarity, enchantType, cooldown, maxLevel);
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

    public boolean isAllowed(Material material) {
        return Enchants.getInstance().getMainConfig().isAllowed(material);
    }

}
