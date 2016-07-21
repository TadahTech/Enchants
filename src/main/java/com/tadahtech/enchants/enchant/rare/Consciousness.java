package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.ArmorEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 */
public class Consciousness extends ArmorEnchant {

    public Consciousness() {
        super("Consciousness", Rarity.RARE, CustomEnchantType.CONSCIOUSNESS, 1);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {

    }

    @Override
    public void onUnequip(Player player, int level) {

    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isHelmet(itemStack);
    }
}
