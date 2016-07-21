package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 *
 */
public class Cannibal extends WeaponEnchant {

    public Cannibal() {
        super("Cannibal", Rarity.LEGENDARY, CustomEnchantType.CANNIBAL, 10, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = 5 * level;
        int points = 1 + level;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        player.setSaturation(20.0f);
        player.setFoodLevel(player.getFoodLevel() + points);
        player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.0F, 1.0F);
        use(player);
    }
}
