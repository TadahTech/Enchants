package com.tadahtech.enchants.enchant.common;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 *
 */
public class Strike extends WeaponEnchant {

    public Strike() {
        super("Strike", Rarity.COMMON, CustomEnchantType.STRIKE, 10, 5);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = 5;
        int added = 1;
        switch (level) {
            case 2:
                chance = 8;
                break;
            case 3:
                chance = 10;
                break;
            case 4:
                chance = 10;
                added = 2;
                break;
            case 12:
                chance = 12;
                added = 2;
                break;
        }

        int next = new Random().nextInt(100) + 1;

        if(chance > next) {
            return;
        }

        player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.1f, 1.0f);

        for(int i = 0; i < 10; i++) {
            player.playEffect(hit.getLocation().clone().add(Math.random(), Math.random(), Math.random()), Effect.MAGIC_CRIT, Effect.MAGIC_CRIT.getData());
        }

        hit.damage(added);
        use(player);
    }

}
