package com.tadahtech.enchants.enchant.uncommon;

import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.enchant.types.MiningEnchant;
import com.tadahtech.enchants.listener.ExplosiveBreakEvent;
import com.tadahtech.enchants.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 *
 */
public class Explosive extends MiningEnchant {
    
    public Explosive() {
        super("Explosive", Rarity.UNCOMMON, CustomEnchantType.EXPLOSIVE, 20, 5);
    }

    @Override
    public void onUse(Player player) {
        
    }

    @Override
    public void onBlockBreak(Player player, Block block, int level) {
        int radius = 2;
        int chance = 5;
        switch (level) {
            case 2:
                chance = 10;
                break;
            case 3:
                chance = 12;
                radius = 3;
                break;
            case 4:
                chance = 15;
                radius = 3;
                break;
            case 5:
                chance = 20;
                radius = 4;
                break;
        }
        int next = new Random().nextInt(100) + 1;
        if(chance > next) {
            return;
        }
        Location location = block.getLocation().clone();

        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
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
                        return;
                    }

                    loc.getBlock().breakNaturally();
                }
            }
        }
        player.playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
        use(player);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return ItemUtil.isTool(itemStack);
    }
}
