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
public class Spikes extends DamagedEnchant {

    public Spikes() {
        super("Spikes", Rarity.LEGENDARY, CustomEnchantType.SPIKES, 60, 3);
    }

    @Override
    public void onDamage(Player player, Player damager, EntityDamageByEntityEvent event, int level) {
        int chance = 1 + level;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        event.setCancelled(true);
        damager.damage(event.getFinalDamage() * 1.5);
        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
        damager.playSound(damager.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isArmor(itemStack);
    }
}
