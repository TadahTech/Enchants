package com.tadahtech.enchants.listener;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.armorEquip.ArmorEquipEvent;
import com.tadahtech.enchants.book.CustomEnchantmentBook;
import com.tadahtech.enchants.config.MainConfig;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.types.BowEnchant;
import com.tadahtech.enchants.enchant.types.DamagedEnchant;
import com.tadahtech.enchants.enchant.types.HeldItemEnchant;
import com.tadahtech.enchants.item.EnchantedItem;
import com.tadahtech.enchants.menu.Menu;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class EnchantListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInHand();

        if (itemStack == null || itemStack.getType() != Material.ENCHANTED_BOOK) {
            return;
        }

        MainConfig config = Enchants.getInstance().getMainConfig();

        if (!config.doRightClickBook()) {
            return;
        }

        CustomEnchantmentBook book = CustomEnchantmentBook.getFromItemStack(itemStack);

        if (book == null) {
            return;
        }

        new Menu(player, itemStack, book.getEnchant()).setEnchantLevel(book.getLevel());
        player.getInventory().remove(itemStack);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int chance = 0;
        for(ItemStack itemStack : player.getInventory().getArmorContents()) {
            EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(itemStack);
            if(enchantedItem == null) {
                continue;
            }
            if(!enchantedItem.getEnchants().contains(CustomEnchant.getCustomEnchant("ItemKeeper"))) {
                continue;
            }
            int level = enchantedItem.getLevel(CustomEnchant.getCustomEnchant("ItemKeeper"));
            chance += 25 * level;
        }
        if(chance <= 0) {
            return;
        }

        if(chance >= 100) {
            return;
        }

        int size = player.getInventory().getContents().length;
        if(size <= 0) {
            return;
        }

        List<ItemStack> toKeep = Lists.newArrayList();

        int remain = size / (chance / 25);
        for(int i = 0; i < remain; i++) {
            toKeep.add(player.getInventory().getContents()[i]);
        }
        for(int i = remain; i < player.getInventory().getContents().length; i++) {
            player.getLocation().getWorld().dropItem(player.getEyeLocation(), player.getInventory().getContents()[i]);
        }
        player.getInventory().clear();
        player.getInventory().setContents(toKeep.toArray(new ItemStack[toKeep.size()]));
        event.setKeepInventory(true);

    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player hit = (Player) event.getEntity();

        ItemStack[] armor = hit.getInventory().getArmorContents();
        if(armor.length != 0) {
            for(ItemStack itemStack : armor) {
                EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(itemStack);
                if(enchantedItem == null) {
                    continue;
                }
                enchantedItem.getEnchants().forEach(customEnchant -> {
                    if(!customEnchant.canUse(hit)) {
                        return;
                    }
                    if(customEnchant instanceof DamagedEnchant) {
                        ((DamagedEnchant) customEnchant).onDamage(hit, player, event, enchantedItem.getLevel(customEnchant));
                        return;
                    }
                    customEnchant.onPlayerHit(player, hit, enchantedItem.getLevel(customEnchant));
                });
            }
        }

        ItemStack used = player.getInventory().getItemInHand();

        EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(used);

        if (enchantedItem == null) {
            return;
        }

        enchantedItem.getEnchants().forEach(customEnchant -> {
            if (!customEnchant.canUse(player)) {
                return;
            }
            customEnchant.onPlayerHit(player, hit, enchantedItem.getLevel(customEnchant));
        });
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player hit = (Player) event.getEntity();

        if (!(event.getDamager() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getDamager();

        ProjectileSource arrowShooter = arrow.getShooter();

        if (!(arrowShooter instanceof Player)) {
            return;
        }

        Player player = (Player) arrowShooter;

        Map<CustomEnchant, Integer> enchants = (Map<CustomEnchant, Integer>) arrow.getMetadata("customArrow").get(0).value();

        if (enchants == null) {
            return;
        }

        enchants.forEach((customEnchant, integer) -> {
            if (!customEnchant.canUse(player)) {
                return;
            }
            customEnchant.onPlayerHit(player, hit, integer);
        });

        arrow.remove();
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(event.getBow());

        if (enchantedItem == null) {
            return;
        }

        if (!(event.getProjectile() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getProjectile();

        Map<CustomEnchant, Integer> map = Maps.newHashMap();
        enchantedItem.getEnchants().forEach(customEnchant -> {
            if (!customEnchant.canUse((Player) event.getEntity())) {
                return;
            }
            if(customEnchant instanceof BowEnchant) {
                ((BowEnchant) customEnchant).onShoot((Player) event.getEntity(), event, enchantedItem.getLevel(customEnchant));
            }
            map.put(customEnchant, enchantedItem.getLevel(customEnchant));
        });

        arrow.setMetadata("customArrow", new FixedMetadataValue(Enchants.getInstance(), map));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(event instanceof ExplosiveBreakEvent) {
            return;
        }

        if(event.isCancelled()) {
            return;
        }

        ItemStack used = player.getInventory().getItemInHand();

        EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(used);

        if (enchantedItem == null) {
            return;
        }

        enchantedItem.getEnchants().forEach(customEnchant -> {
            if (!customEnchant.canUse(player)) {
                return;
            }
            customEnchant.onBlockBreak(player, event.getBlock(), enchantedItem.getLevel(customEnchant));
        });

    }

    @EventHandler
    public void onSwitchedTo(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack to = player.getInventory().getItem(event.getNewSlot());
        ItemStack from = player.getInventory().getItem(event.getPreviousSlot());

        if (from != null) {
            EnchantedItem item = EnchantedItem.getFromItemStack(from);
            if (item != null) {
                item.getEnchants().stream().filter(enchant -> enchant instanceof HeldItemEnchant).forEach(customEnchant -> customEnchant.onSwitchedFrom(player, item.getLevel(customEnchant)));
            }
        }

        if (to != null) {
            EnchantedItem item = EnchantedItem.getFromItemStack(to);
            if (item != null) {
                item.getEnchants().stream().filter(enchant -> enchant instanceof HeldItemEnchant).forEach(customEnchant -> customEnchant.onSwitchedTo(player, item.getLevel(customEnchant)));
            }
        }
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();

        ItemStack old = event.getOldArmorPiece();
        ItemStack newItem = event.getNewArmorPiece();

        if (old != null) {
            EnchantedItem item = EnchantedItem.getFromItemStack(old);
            if (item != null) {
                item.getEnchants().forEach(customEnchant -> customEnchant.onUnequip(player, item.getLevel(customEnchant)));
            }
        }

        if (newItem != null) {
            EnchantedItem item = EnchantedItem.getFromItemStack(newItem);
            if (item != null) {
                item.getEnchants().forEach(customEnchant -> customEnchant.onEquip(player, item.getLevel(customEnchant)));
            }
        }
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null) {
            return;
        }

        EnchantedItem item = EnchantedItem.getFromItemStack(event.getCurrentItem());

        if(item == null) {
            return;
        }

        item.getEnchants().stream().filter(enchant -> enchant instanceof HeldItemEnchant).forEach(customEnchant -> customEnchant.onSwitchedTo(player, item.getLevel(customEnchant)));
    }

}
