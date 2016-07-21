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
public class Brainwashed extends WeaponEnchant {

    private final PotionEffect EFFECT_1 = new PotionEffect(PotionEffectType.CONFUSION, 7 * 20, 0);
    private final PotionEffect EFFECT_2 = new PotionEffect(PotionEffectType.CONFUSION, 9 * 20, 0);

    public Brainwashed() {
        super("Brainwashed", Rarity.UNCOMMON, CustomEnchantType.BRAINWASHED, 40, 2);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        int chance = 4 * level;
        int next = new Random().nextInt(100) + 1;
        if (chance > next) {
            return;
        }
        for (ItemStack itemStack : hit.getInventory().getArmorContents()) {
            EnchantedItem item = EnchantedItem.getFromItemStack(itemStack);
            if(item == null) {
                continue;
            }
            if (item.getEnchants().contains(CustomEnchant.getCustomEnchant("Enlighten"))) {
                player.playSound(player.getLocation(), Sound.HORSE_ANGRY, 1.0F, 1.0F);
                hit.playSound(hit.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                return;
            }
        }

        hit.addPotionEffect(level == 1 ? EFFECT_1 : EFFECT_2);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
        use(player);
    }
}
