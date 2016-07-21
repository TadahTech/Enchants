package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.WeaponEnchant;
import com.tadahtech.enchants.util.Colors;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Heartstealer extends WeaponEnchant {

    public Heartstealer() {
        super("Heartstealer", Rarity.RARE, CustomEnchantType.HEART_STEALER, 180, 1);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onPlayerHit(Player player, Player hit, int level) {
        if(3 > new Random().nextInt(100) + 1) {
            return;
        }
        double pHealth = player.getHealth();
        double dHealth = hit.getHealth();
        if(dHealth < pHealth) {
            return;
        }
        player.setHealth(dHealth);
        hit.setHealth(pHealth);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        hit.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        player.sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + "Swapped health!");
        hit.sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + "Swapped health!");
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isWeapon(itemStack);
    }
}
