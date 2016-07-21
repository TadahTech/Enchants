package com.tadahtech.enchants.enchant.rare;

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
public class Return extends DamagedEnchant {

    public Return() {
        super("Return", Rarity.RARE, CustomEnchantType.RETURN, 10, 3);
    }

    @Override
    public void onDamage(Player player, Player damager, EntityDamageByEntityEvent event, int level) {
        int chance = level * 2;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        event.setCancelled(true);
        damager.damage(event.getFinalDamage());
        damager.playSound(damager.getLocation(), Sound.ANVIL_BREAK, 1.0F, 1.0F);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isArmor(itemStack);
    }
}
