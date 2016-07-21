package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 *
 */
public class Zeus extends WeaponEnchant {

    public Zeus() {
        super("Zeus", Rarity.LEGENDARY, CustomEnchantType.ZEUS, 60, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = level == 1 ? 3 : level == 2 ? 5 : 8;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        hit.getWorld().strikeLightning(hit.getLocation());
        use(player);
    }
}
