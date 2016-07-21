package com.tadahtech.enchants.enchant.uncommon;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import com.tadahtech.enchants.item.EnchantedItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 *
 */
public class Myopia extends WeaponEnchant {

    private final PotionEffect EFFECT_1 = new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 1);
    private final PotionEffect EFFECT_2 = new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 1);
    private final PotionEffect EFFECT_3 = new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 1);

    public Myopia() {
        super("Myopia", Rarity.UNCOMMON, CustomEnchantType.MYOPIA, 40, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = 5;
        PotionEffect effect = EFFECT_1;
        if(level == 2) {
            chance = 8;
            effect = EFFECT_2;
        } else if(level == 3) {
            chance = 10;
            effect = EFFECT_3;
        }
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        for (ItemStack itemStack : hit.getInventory().getArmorContents()) {
            EnchantedItem item = EnchantedItem.getFromItemStack(itemStack);
            if(item == null) {
                continue;
            }
            if (item.getEnchants().contains(CustomEnchant.getCustomEnchant("Consciousness"))) {
                player.playSound(player.getLocation(), Sound.HORSE_ANGRY, 1.0F, 1.0F);
                hit.playSound(hit.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                return;
            }
        }
        hit.addPotionEffect(effect);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
        use(player);

    }

}
