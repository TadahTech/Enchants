package com.tadahtech.enchants.enchant.uncommon;

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
public class Blocker extends DamagedEnchant {

    public Blocker() {
        super("Blocker", Rarity.UNCOMMON, CustomEnchantType.BLOCKER, 10, 5);
    }

    @Override
    public void onDamage(Player player, Player damager, EntityDamageByEntityEvent event, int level) {
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
        event.setDamage(event.getFinalDamage() - added);
        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
        use(player);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isArmor(itemStack);
    }
}
