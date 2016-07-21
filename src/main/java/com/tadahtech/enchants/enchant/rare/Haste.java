package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.HeldItemEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 */
public class Haste extends HeldItemEnchant {

    private final PotionEffect EFFECT_1 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0);
    private final PotionEffect EFFECT_2 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
    private final PotionEffect EFFECT_3 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2);
    
    public Haste() {
        super("Haste", Rarity.RARE, CustomEnchantType.HASTE, -1, 3);
    }

    @Override
    public void onUse(Player player) {
        
    }

    @Override
    public void onSwitchedTo(Player player, int level) {
        switch (level) {
            case 1:
                player.addPotionEffect(EFFECT_1);
                break;
            case 2:
                player.addPotionEffect(EFFECT_2);
                break;
            case 3:
                player.addPotionEffect(EFFECT_3);
                break;
        }
        player.playSound(player.getLocation(), Sound.DRINK, 1.0F, 1.0F);
    }

    @Override
    public void onSwitchedFrom(Player player, int level) {
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isMaterial(itemStack);
    }
}
