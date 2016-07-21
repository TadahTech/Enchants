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
public class NightVision extends ArmorEnchant {

    private final PotionEffect EFFECT = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0);

    public NightVision() {
        super("NightVision", Rarity.COMMON, CustomEnchantType.NIGHT_VISION, 1);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {
        player.addPotionEffect(EFFECT);
    }

    @Override
    public void onUnequip(Player player, int level) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isHelmet(itemStack);
    }
}
