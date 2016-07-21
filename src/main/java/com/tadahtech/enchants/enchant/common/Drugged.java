package com.tadahtech.enchants.enchant.common;

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
public class Drugged extends ArmorEnchant {

    private final int TICK_RATE = 80;

    private final Map<UUID, BukkitTask> tasks = Maps.newHashMap();

    public Drugged() {
        super("Drugged", Rarity.COMMON, CustomEnchantType.DRUGGED, 2);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onEquip(Player player, int level) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                player.setSaturation(20);
                player.setFoodLevel(player.getFoodLevel() + 1);
            }
        }.runTaskTimer(Enchants.getInstance(), TICK_RATE / (1 + level), TICK_RATE / (1 + level));
        this.tasks.put(player.getUniqueId(), task);
    }

    @Override
    public void onUnequip(Player player, int level) {
        BukkitTask task = this.tasks.remove(player.getUniqueId());
        if(task != null) {
            task.cancel();
        }
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isHelmet(itemStack);
    }
}
