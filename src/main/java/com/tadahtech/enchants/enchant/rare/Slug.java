package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 *
 */
public class Slug extends WeaponEnchant {

    private final PotionEffect EFFECT_1 = new PotionEffect(PotionEffectType.SLOW, 3 * 20, 0);
    private final PotionEffect EFFECT_2 = new PotionEffect(PotionEffectType.SLOW, 5 * 20, 0);
    private final PotionEffect EFFECT_3 = new PotionEffect(PotionEffectType.SLOW, 7 * 20, 0);
    private final PotionEffect EFFECT_4 = new PotionEffect(PotionEffectType.SLOW, 10 * 20, 0);

    public Slug() {
        super("Slug", Rarity.RARE, CustomEnchantType.SLUG, 30, 4);
    }

    @Override
    public void onUse(Player player) {
        
    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = 5;
        PotionEffect effect = EFFECT_1;
        switch (level) {
            case 2:
                chance = 7;
                effect = EFFECT_2;
                break;
            case 3:
                chance = 10;
                effect = EFFECT_3;
                break;
            case 4:
                chance = 15;
                effect = EFFECT_4;
                break;
        }
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        hit.addPotionEffect(effect);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isWeapon(itemStack);
    }
}
