package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.MiningEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Treefeller extends MiningEnchant {

    public Treefeller() {
        super("Treefeller", Rarity.LEGENDARY, CustomEnchantType.TREEFELLER, 120, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        int chance = 10 * level;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isAxe(itemStack);
    }
}
