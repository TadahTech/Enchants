package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.DamagedEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Hothead extends DamagedEnchant {

    public Hothead() {
        super("Hothead", Rarity.LEGENDARY, CustomEnchantType.HOTHEAD, 30, 3);
    }

    @Override
    public void onDamage(Player player, Player damager, EntityDamageByEntityEvent event, int level) {
        int chance = 5 * level;
        int next = new Random().nextInt(100) + 1;
        if (chance > next) {
            return;
        }
        damager.setFireTicks(80 * (level == 1 ? 3 : level == 2 ? 5 : 8));
        player.playSound(player.getLocation(), Sound.BLAZE_HIT, 1.0F, 1.0F);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isArmor(itemStack);
    }
}
