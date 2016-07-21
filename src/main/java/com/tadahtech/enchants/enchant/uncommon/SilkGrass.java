package com.tadahtech.enchants.enchant.uncommon;

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
public class SilkGrass extends MiningEnchant {

    public SilkGrass() {
        super("SilkGrass", Rarity.UNCOMMON, CustomEnchantType.SILK_GRASS, 20, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        if(block.getType() != Material.DIRT) {
            return;
        }
        int chance = 30 * level;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        player.getInventory().addItem(new ItemStack(Material.GRASS));
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 1.0f, 1.0f);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isSpade(itemStack);
    }
}
