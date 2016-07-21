package com.tadahtech.enchants.enchant.uncommon;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.ArmorEnchant;
import com.tadahtech.enchants.util.ItemUtil;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class Regrowth extends ArmorEnchant {

    private final Map<UUID, BukkitTask> tasks = Maps.newHashMap();

    public Regrowth() {
        super("Regrowth", Rarity.UNCOMMON, CustomEnchantType.REGROWTH, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {
        this.tasks.put(player.getUniqueId(), new BukkitRunnable() {
            @Override
            public void run() {
                double health = 0.5 * level;
                if((player.getHealth() + health) > 20) {
                    return;
                }
                player.setHealth(player.getHealth() + health);
            }
        }.runTaskTimer(Enchants.getInstance(), 20 * 10, 20 * 10));
        use(player);
    }

    @Override
    public void onUnequip(Player player, int level) {
        BukkitTask bukkitTask = this.tasks.remove(player.getUniqueId());
        if(bukkitTask != null) {
            bukkitTask.cancel();
        }
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isHelmet(itemStack);
    }
}
