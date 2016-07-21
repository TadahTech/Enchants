package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.BowEnchant;
import com.tadahtech.enchants.util.Colors;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.Random;

/**
 *
 */
public class Skelly extends BowEnchant {

    public Skelly() {
        super("Skelly", Rarity.RARE, CustomEnchantType.SKELLY, 60, 5);
    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int next = new Random().nextInt(100) + 1;
        if(level > next) {
            return;
        }
        Skeleton skeleton = hit.getWorld().spawn(hit.getLocation().clone().add(Math.random(), Math.random(), Math.random()), Skeleton.class);
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName(Colors.GRAY + player.getName() + " Archer Summon");
        player.playSound(player.getLocation(), Sound.SKELETON_HURT, 1.0F, 1.0F);
        use(player);
    }

    @Override
    public void onShoot(Player player, EntityShootBowEvent event, int level) {

    }
}
