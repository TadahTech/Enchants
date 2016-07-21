package com.tadahtech.enchants.menu;

import com.tadahtech.enchants.enchant.CustomEnchant;
import com.tadahtech.enchants.item.ItemBuilder;
import com.tadahtech.enchants.util.Colors;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class Menu {

    public static final String INV_NAME = "&8Skyblock ● Enchants";

    public static final int CONFIRM = 50;
    public static final int CANCEL = 48;

    public static final ItemStack SWORD = new ItemBuilder(Material.DIAMOND_SWORD)
      .setTitle(Colors.WHITE + "Left Side")
      .setLore(Colors.GRAY + "Insert the item you'd like", Colors.GRAY + "to enchant here")
      .setGlow(true)
      .build();

    public static final ItemStack BOOK = new ItemBuilder(Material.ENCHANTED_BOOK)
      .setTitle(Colors.WHITE + "Right Side")
      .setLore(Colors.GRAY + "Insert your enchantment", Colors.GRAY + "book here")
      .setGlow(true)
      .build();

    public static final ItemStack EMERALD = new ItemBuilder(Material.EMERALD_BLOCK)
      .setTitle(Colors.GREEN + Colors.BOLD + "Confirm")
      .setLore(Colors.GRAY + "Confirm and enchant the item!")
      .build();

    public static final ItemStack REDSTONE = new ItemBuilder(Material.REDSTONE_BLOCK)
      .setTitle(Colors.RED + Colors.BOLD + "Cancel")
      .setLore(Colors.GRAY + "Cancel operation and close the menu.")
      .build();

    private static final ItemStack PANE = new ItemBuilder(Material.STAINED_GLASS_PANE).setTitle(" ").setData(DyeColor.BLACK.getWoolData()).build();

    private static final ItemStack LEFT_PAPER = new ItemBuilder(Material.PAPER)
      .setTitle(Colors.WHITE + "Left Side")
      .setLore(Colors.GRAY + "Insert the item you'd like", Colors.GRAY + "to enchant here")
      .build();

    private static final ItemStack RIGHT_PAPER = new ItemBuilder(Material.PAPER)
      .setTitle(Colors.WHITE + "Right Side")
      .setLore(Colors.GRAY + "Insert your enchantment", Colors.GRAY + "book here")
      .build();

    private static final Map<UUID, Menu> players = Maps.newHashMap();

    private static final List<Integer> deniedSlots = Lists.newArrayList();
    private static final List<Integer> BOOK_SLOTS = Lists.newArrayList(23, 24, 32, 33);

    static {
        for (int i = 1; i < 8; i++) {
            deniedSlots.add(i);
        }

        for (int i = 4; i < 49; i += 9) {
            deniedSlots.add(i);
        }

        for (int i = 45; i < 54; i++) {
            deniedSlots.add(i);
        }

        deniedSlots.addAll(Lists.newArrayList(0, 8, 18, 26, 27, 35));
        deniedSlots.remove(deniedSlots.indexOf(48));
        deniedSlots.remove(deniedSlots.indexOf(50));
    }

    private Player player;
    private ItemStack itemStack;
    private CustomEnchant enchant;
    private ItemStack bookItem;
    private int enchantLevel;

    public Menu(Player player) {
        this.player = player;

        openInventory();

        players.put(player.getUniqueId(), this);

    }

    public Menu(Player player, ItemStack bookItem, CustomEnchant enchant) {
        this.bookItem = bookItem;
        this.enchant = enchant;
        this.player = player;

        openInventory();

        players.put(player.getUniqueId(), this);
    }

    private void openInventory() {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&', INV_NAME));

        for (int i = 1; i < 8; i++) {
            inventory.setItem(i, PANE);
        }

        for (int i = 4; i < 49; i += 9) {
            inventory.setItem(i, PANE);
        }

        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, PANE);
        }

        inventory.setItem(0, SWORD);
        inventory.setItem(8, BOOK);

        inventory.setItem(18, LEFT_PAPER);
        inventory.setItem(26, RIGHT_PAPER);
        inventory.setItem(27, LEFT_PAPER);
        inventory.setItem(35, RIGHT_PAPER);

        inventory.setItem(48, REDSTONE);
        inventory.setItem(50, EMERALD);

        if(this.bookItem != null) {
            for(int i : BOOK_SLOTS) {
                inventory.setItem(i, this.bookItem);
            }
        }

        player.openInventory(inventory);

    }

    public static Menu get(Player player) {
        return players.get(player.getUniqueId());
    }

    public static List<Integer> getDeniedSlots() {
        return deniedSlots;
    }

    public CustomEnchant getEnchant() {
        return enchant;
    }

    public void setEnchant(CustomEnchant enchant) {
        this.enchant = enchant;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setBookItem(ItemStack bookItem) {
        this.bookItem = bookItem;
    }

    public ItemStack getBookItem() {
        return bookItem;
    }

    public void cleanup() {
        players.remove(player.getUniqueId());
        this.player = null;
        this.itemStack = null;
        this.bookItem = null;
        this.enchant = null;
    }

    public int getEnchantLevel() {
        return enchantLevel;
    }

    public void setEnchantLevel(int enchantLevel) {
        this.enchantLevel = enchantLevel;
    }
}
