package com.tadahtech.enchants.listener;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.book.CustomEnchantmentBook;
import com.tadahtech.enchants.config.lang.Argument;
import com.tadahtech.enchants.config.lang.Lang;
import com.tadahtech.enchants.config.lang.MessageKey;
import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.item.EnchantedItem;
import com.tadahtech.enchants.menu.Menu;
import com.tadahtech.enchants.util.ItemUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Listener for the Menu system
 */
public class MenuListener implements Listener {

    private final List<Integer> BOOK_SLOTS = Lists.newArrayList(23, 24, 32, 33);
    private final List<Integer> ITEM_SLOTS = Lists.newArrayList(20, 21, 29, 30);

    private Lang lang;

    public MenuListener() {
        this.lang = Enchants.getInstance().getLang();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.get(player);

        if (menu == null) {
            return;
        }

        int rawSlot = event.getRawSlot();

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        boolean book = clickedItem.getType() == Material.ENCHANTED_BOOK;
        CustomEnchantmentBook enchantmentBook = CustomEnchantmentBook.getFromItemStack(clickedItem);

        CustomEnchant currentEnchant = menu.getEnchant();
        ItemStack currentItem = menu.getItemStack();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        if (Menu.getDeniedSlots().contains(rawSlot)) {
            return;
        }

        Inventory top = player.getOpenInventory().getTopInventory();
        Inventory clicked = event.getClickedInventory();
        InventoryType inventoryType = clicked.getType();

        if (inventoryType == InventoryType.PLAYER) {
            if (book) {
                if (enchantmentBook == null) {
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                    return;
                }

                if (currentEnchant == null) {
                    for (int i : BOOK_SLOTS) {
                        top.setItem(i, clickedItem);
                    }
                } else {
                    ItemStack back = null;
                    for (int i : BOOK_SLOTS) {
                        if (back == null) {
                            back = top.getItem(i);
                        }
                        top.setItem(i, null);
                    }
                    if(back != null) {
                        player.getInventory().addItem(back);
                    }

                }

                menu.setBookItem(clickedItem);

                menu.setEnchantLevel(enchantmentBook.getLevel());

                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);

                menu.setEnchant(enchantmentBook.getEnchant());

                for (int i : BOOK_SLOTS) {
                    top.setItem(i, clickedItem);
                }

                player.getInventory().setItem(event.getSlot(), null);
                player.updateInventory();
                return;
            }

            if (currentItem == null) {
                for (int i : ITEM_SLOTS) {
                    top.setItem(i, clickedItem);
                }
            } else {
                ItemStack back = null;
                for (int i : ITEM_SLOTS) {
                    if (back == null) {
                        back = top.getItem(i);
                    }
                    top.setItem(i, null);
                }
                if(back != null) {
                    player.getInventory().addItem(back);
                }
            }

            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);

            menu.setItemStack(clickedItem);

            for (int i : ITEM_SLOTS) {
                top.setItem(i, clickedItem);
            }

            player.getInventory().setItem(event.getSlot(), null);
            player.updateInventory();
            return;
        }

        if (BOOK_SLOTS.contains(rawSlot)) {
            if (currentEnchant == null) {
                return;
            }

            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);

            ItemStack back = null;
            for (int i : BOOK_SLOTS) {
                if (back == null) {
                    back = top.getItem(i);
                }
                top.setItem(i, null);
            }
            if(back != null) {
                player.getInventory().addItem(back);
            }
            menu.setBookItem(null);
            menu.setEnchantLevel(0);
            return;
        }

        if (ITEM_SLOTS.contains(rawSlot)) {
            if (currentItem == null) {
                return;
            }

            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);

            ItemStack back = null;
            for (int i : ITEM_SLOTS) {
                if (back == null) {
                    back = top.getItem(i);
                }
                top.setItem(i, null);
            }
            menu.setItemStack(null);
            if(back != null) {
                player.getInventory().addItem(back);
            }
            return;
        }

        if(rawSlot == Menu.CANCEL) {
            player.closeInventory();
            lang.sendMessage(player, MessageKey.CANCELLED, null);
            return;
        }

        if (rawSlot == Menu.CONFIRM) {
            if (currentItem == null) {
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                lang.sendMessage(player, MessageKey.FAILED_ITEM, null);
                return;
            }

            if (currentEnchant == null || menu.getEnchantLevel() <= 0) {
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                lang.sendMessage(player, MessageKey.FAILED_BOOK, null);
                return;
            }

            Map<Argument, String> map = Maps.newHashMap();
            map.put(Argument.ENCHANT, currentEnchant.getName());
            map.put(Argument.ITEM, ItemUtil.getName(currentItem));

            if(!currentEnchant.canEnchant(currentItem)) {
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                lang.sendMessage(player, MessageKey.ENCHANT_CANNOT_APPLY, map);
                return;
            }

            EnchantedItem enchantedItem = EnchantedItem.getFromItemStack(currentItem);

            if (enchantedItem == null) {
                enchantedItem = new EnchantedItem(currentItem, currentEnchant, menu.getEnchantLevel());
            } else {
                if (enchantedItem.has(currentEnchant)) {
                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                    lang.sendMessage(player, MessageKey.ALREDY_ON, ImmutableMap.of(Argument.ENCHANT, currentEnchant.getName(), Argument.ITEM, ItemUtil.getName(currentItem)));
                    return;
                }
                enchantedItem.add(currentEnchant, menu.getEnchantLevel());
            }

            player.getInventory().addItem(enchantedItem.toItemStack());

            menu.cleanup();

            player.closeInventory();

            lang.sendMessage(player, MessageKey.SUCCESS, map);

            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

        }
    }

    private boolean contains(Player player, ItemStack back) {
        for(ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack.isSimilar(back)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = Menu.get(player);

        if (menu == null) {
            return;
        }

        if(menu.getItemStack() != null) {
            player.getInventory().addItem(menu.getItemStack());
        }

        if(menu.getBookItem() != null) {
            player.getInventory().addItem(menu.getBookItem());
        }

        menu.cleanup();
    }

}
