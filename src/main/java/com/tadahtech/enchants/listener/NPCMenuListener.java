package com.tadahtech.enchants.listener;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.book.CustomEnchantmentBook;
import com.tadahtech.enchants.config.lang.Argument;
import com.tadahtech.enchants.config.lang.MessageKey;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.util.ExpManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Random;

/**
 *
 */
public class NPCMenuListener implements Listener {

    private final Enchants plugin;

    public NPCMenuListener() {
        this.plugin = Enchants.getInstance();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(!plugin.getNpcMenu().getInventory().getName().equalsIgnoreCase(event.getInventory().getName())) {
            return;
        }

        int slot = event.getRawSlot();

        event.setCancelled(true);
        event.setResult(Result.DENY);

        player.closeInventory();

        switch (slot) {
            case 10:
                click(player, Rarity.COMMON);
                break;
            case 12:
                click(player, Rarity.UNCOMMON);
                break;
            case 14:
                click(player, Rarity.RARE);
                break;
            case 16:
                click(player, Rarity.LEGENDARY);
                break;

        }
    }

    private void click(Player player, Rarity rarity) {
        int totalXP = ExpManager.getTotalExperience(player);
        int cost = plugin.getMainConfig().getCost(rarity);

        if(totalXP < cost) {
            plugin.getLang().sendMessage(player, MessageKey.NOT_ENOUGH_EXP, null);
            return;
        }

        CustomEnchant enchant = plugin.getEnchantmentCalculator().getNewEnchant(rarity);

        int level = 1;
        int max = enchant.getMaxLevel();

        Random random = new Random();
        int next = random.nextInt(100) + 1;
        if(next <= 15)
        {
            level = 2;
        }
        if(next <= 12)
        {
            level = 3;
        }
        if(next <= 9)
        {
            level = 3;
        }
        if(next <= 6)
        {
            level = 4;
        }
        if(next <= 3)
        {
            level = 5;
        }
        if(level > max)
        {
            level = max;
        }

        CustomEnchantmentBook book = new CustomEnchantmentBook(enchant, level);
        ItemStack bookItem = book.toItemStack();

        ExpManager.setTotalExperience(player, totalXP - cost);

        plugin.getLang().sendMessage(player, MessageKey.SUCCESSFULLY_PURCHASED, Collections.singletonMap(Argument.ENCHANT, enchant.getName()));

        player.getInventory().addItem(bookItem);

    }



}
