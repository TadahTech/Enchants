package com.tadahtech.enchants.enchant.common;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.MiningEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Sapling extends MiningEnchant {

    public Sapling() {
        super("Sapling", Rarity.COMMON, CustomEnchantType.SAPLING, 60, 2);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        int chance = 20;
        if(level == 2) {
            chance = 30;
        }
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        player.getInventory().addItem(new ItemStack(Material.SAPLING));
        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0F);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isAxe(itemStack);
    }
}
