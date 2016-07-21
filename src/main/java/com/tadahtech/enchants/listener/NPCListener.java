package com.tadahtech.enchants.listener;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.util.Colors;
import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;
import java.util.UUID;

/**
 *
 */
public class NPCListener implements Listener {

    private static final List<UUID> deleting = Lists.newArrayList();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(event.getEntity().hasMetadata("nodamage")) {
            event.setCancelled(true);
            if(deleting.contains(event.getDamager().getUniqueId())) {
                Enchants.getInstance().remove(event.getEntity());
                event.getEntity().remove();
                event.getDamager().sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + " Deleted NPC.");
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        if(event.getRightClicked().hasMetadata("nodamage")) {
            event.setCancelled(true);
            if(deleting.contains(event.getPlayer().getUniqueId())) {
                Enchants.getInstance().remove(event.getRightClicked());
                event.getRightClicked().remove();
                event.getPlayer().sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + " Deleted NPC.");
                return;
            }
            Enchants.getInstance().getNpcMenu().open(event.getPlayer());
            return;
        }
        LivingEntity livingEntity = (LivingEntity) event.getRightClicked();
        if(livingEntity.isCustomNameVisible() && livingEntity.getCustomName().equalsIgnoreCase(Enchants.getInstance().getMainConfig().getNPCName())) {
            Enchants.getInstance().getNpcMenu().open(event.getPlayer());
        }
    }

    public static void toggle(Player player) {
        if(deleting.remove(player.getUniqueId())) {
            player.sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + " Stopped deleting NPC's.");
        } else {
            deleting.add(player.getUniqueId());
            player.sendMessage(Enchants.getInstance().getLang().getPrefix() + Colors.GRAY + " Started deleting NPC's.");
        }
    }
}
