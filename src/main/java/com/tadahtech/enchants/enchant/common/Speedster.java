package com.tadahtech.enchants.enchant.common;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.ArmorEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 */
public class Speedster extends ArmorEnchant {

    private final PotionEffect SPEED_1 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0);
    private final PotionEffect SPEED_2 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);

    public Speedster() {
        super("Speedster", Rarity.COMMON, CustomEnchantType.SPPEDSTER, 2);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {
        if(level == 1) {
            player.addPotionEffect(SPEED_1);
            return;
        }
        player.addPotionEffect(SPEED_2);
    }

    @Override
    public void onUnequip(Player player, int level) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isBoots(itemStack);
    }
}
