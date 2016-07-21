package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 *
 */
public class Freeze extends WeaponEnchant {

    public Freeze() {
        super("Freeze", Rarity.RARE, CustomEnchantType.FREEZE, 20, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = level == 1 ? 2 : level == 2 ? 5 : 8;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        float speed = hit.getWalkSpeed();
        hit.setWalkSpeed(0);
        hit.playSound(hit.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
        player.playSound(player.getLocation(), Sound.FIRE, 1.0F, 1.0F);
        new BukkitRunnable() {
            @Override
            public void run() {
                hit.setWalkSpeed(speed);
            }
        }.runTaskLater(Enchants.getInstance(), 20 * level);
        use(player);
    }
}
