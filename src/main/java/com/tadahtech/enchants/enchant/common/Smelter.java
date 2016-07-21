package com.tadahtech.enchants.enchant.common;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.MiningEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import com.google.common.collect.Maps;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

/**
 *
 */
public class Smelter extends MiningEnchant {

    private static final Map<Material, Material> smelts = Maps.newHashMap();

    static {
        smelts.put(Material.IRON_ORE, Material.IRON_INGOT);
        smelts.put(Material.GOLD_ORE, Material.GOLD_INGOT);
    }

    public Smelter() {
        super("Smelter", Rarity.COMMON, CustomEnchantType.SMELTER, 15, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        if(!smelts.containsKey(block.getType())) {
            return;
        }

        int chance = 20 * level;
        int next = new Random().nextInt(100) + 1;

        if(chance > next) {
            return;
        }

        Material give = smelts.get(block.getType());

        player.getInventory().addItem(new ItemStack(give));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
        double d = 0.0;
        for(int i = 0; i < 10; i++) {
            d += 0.1;
            player.playEffect(block.getLocation().clone().add(0, d, 0), Effect.HAPPY_VILLAGER, Effect.HAPPY_VILLAGER.getData());
        }
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isPickaxe(itemStack);
    }
}
