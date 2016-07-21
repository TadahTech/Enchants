package com.tadahtech.enchants.enchant.legendary;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.MiningEnchant;
import com.tadahtech.enchants.listener.ExplosiveBreakEvent;
import com.tadahtech.enchants.util.ItemUtil;
import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Fireball extends MiningEnchant {

    public Fireball() {
        super("Fireball", Rarity.LEGENDARY, CustomEnchantType.FIREBALL, 60, 3);
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        int chance = 5 * level;
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        Location location = block.getLocation().clone();
        List<FireBlockData> blocks = Lists.newArrayList();

        for(int x = location.getBlockX() - level; x <= location.getBlockX() + level; x++) {
            for(int y = location.getBlockY() - level; y <= location.getBlockY() + level; y++) {
                for(int z = location.getBlockZ() - level; z <= location.getBlockZ() + level; z++) {
                    Location loc = new Location(location.getWorld(), x, y, z);
                    if(loc.equals(location)) {
                        continue;
                    }
                    if(loc.getBlock().getType() == Material.AIR) {
                        continue;
                    }
                    if(!isAllowed(loc.getBlock().getType())) {
                        continue;
                    }
                    //Makes sure that nothing breaks that should
                    ExplosiveBreakEvent event = new ExplosiveBreakEvent(loc.getBlock(), player);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if(event.isCancelled()) {
                        continue;
                    }
                    blocks.add(new FireBlockData(loc));
                }
            }
        }

        Collections.shuffle(blocks);

        new BukkitRunnable() {

            private int total = blocks.size();
            private int index = 0;
            private float note = 0.5f;

            @Override
            public void run() {
                FireBlockData data = blocks.get(index);
                int curr = ++data.ticks;
                note = note + 0.3f;

                if(curr < 10) {
                    for(int i = 0; i < 3; i++) {
                        player.playEffect(data.location, Effect.MOBSPAWNER_FLAMES, Effect.MOBSPAWNER_FLAMES.getData());
                    }
                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, note, 1.0f);
                    return;
                }
                data.block.breakNaturally();
                player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
                if((index + 1) == total) {
                    cancel();
                    blocks.clear();
                    return;
                }
                index++;
            }
        }.runTaskTimer(Enchants.getInstance(), 0L, 1L);

        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isTool(itemStack);
    }

    private class FireBlockData {

        private int ticks;
        private Location location;
        private Block block;

        private FireBlockData(Location location) {
            this.ticks = 0;
            this.location = location;
            this.block = location.getBlock();
        }


    }
}
