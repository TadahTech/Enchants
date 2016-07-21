package com.tadahtech.enchants.enchant;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class EnchantmentCalculator {

    private final Map<Rarity, Integer> totalChances = Maps.newHashMap();
    private final Map<CustomEnchantType, Integer> enchantChances = Maps.newHashMap();

    public EnchantmentCalculator(Map<Rarity, Integer> totalChances, Map<CustomEnchantType, Integer> enchantChances) {
        this.totalChances.putAll(totalChances);
        this.enchantChances.putAll(enchantChances);
    }

    public int getTotalChance(Rarity rarity) {
        return totalChances.get(rarity);
    }

    public CustomEnchant getNewEnchant(Rarity rarity) {
        double totalChance = getTotalChance(rarity);

        double next = Math.random();
        List<CustomEnchant> enchants = CustomEnchant.getEnchantsByRarity(rarity);
        Collections.shuffle(enchants);

        for(CustomEnchant enchant : enchants) {
            int chance = this.enchantChances.get(enchant.getEnchantType());
            double percent = chance / totalChance;
            if(next <= percent) {
                return enchant;
            } else {
                next -= percent;
            }
        }

        return null;
    }

}
