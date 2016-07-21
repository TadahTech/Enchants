package com.tadahtech.enchants.enchant.rare;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.ArmorEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 */
public class ItemKeeper extends ArmorEnchant {

    public ItemKeeper() {
        super("ItemKeeper", Rarity.RARE, CustomEnchantType.ITEM_KEEPER, 4);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {

    }

    @Override
    public void onUnequip(Player player, int level) {

    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isBoots(itemStack);
    }
}
