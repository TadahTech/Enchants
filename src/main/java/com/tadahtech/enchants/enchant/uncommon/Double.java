package com.tadahtech.enchants.enchant.uncommon;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.BowEnchant;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.Random;

/**
 *
 */
public class Double extends BowEnchant {

    public Double() {
        super("Double", Rarity.UNCOMMON, CustomEnchantType.DOUBLE, 5, 3);
    }

    @Override
    public void onShoot(Player player, EntityShootBowEvent event, int level) {
        int chance = 3;
        if(level == 2) {
            chance = 5;
        } else if (level == 3) {
            chance = 8;
        }

        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }

        Arrow arrow = player.getWorld().spawn(player.getEyeLocation(), Arrow.class);
        arrow.setVelocity(event.getProjectile().getVelocity());
        arrow.setShooter(player);
        use(player);

    }
}
