package com.tadahtech.enchants.enchant.uncommon;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Lifesteal extends WeaponEnchant {

    public Lifesteal() {
        super("Lifesteal", Rarity.UNCOMMON, CustomEnchantType.LIFESTEAL, 30, 2);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = level == 1 ? 4 : 6;
        int next = new Random().nextInt(100) + 1;
        if (chance > next) {
            return;
        }
        int health = 2 * level;

        if((player.getHealth() + health) > 20) {
            return;
        }

        player.setHealth(player.getHealth() + health);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
        for (int i = 0; i < 10; i++) {
            player.playEffect(player.getEyeLocation().clone().add(0, 0.1, 0), Effect.HEART, Effect.HEART.getData());
        }
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isWeapon(itemStack);
    }
}
