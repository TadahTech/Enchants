package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.HeldItemEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 */
public class Juggernaut extends HeldItemEnchant {
    
    private final PotionEffect EFFECT_1_SLOW = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0);
    private final PotionEffect EFFECT_1_STRENGTH = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0);
    private final PotionEffect EFFECT_2_SLOW = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1);
    private final PotionEffect EFFECT_2_STRENGTH = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
    
    public Juggernaut() {
        super("Juggernaut", Rarity.LEGENDARY, CustomEnchantType.JUGGERNAUT, -1, 2);
    }

    @Override
    public void onUse(Player player) {
        
    }

    @Override
    public void onSwitchedTo(Player player, int level) {
        switch (level) {
            case 1:
                player.addPotionEffect(EFFECT_1_SLOW);
                player.addPotionEffect(EFFECT_1_STRENGTH);
                break;
            case 2:
                player.addPotionEffect(EFFECT_2_SLOW);
                player.addPotionEffect(EFFECT_2_STRENGTH);
                break;
        }
    }

    @Override
    public void onSwitchedFrom(Player player, int level) {
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isWeapon(itemStack);
    }
}
