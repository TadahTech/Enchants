package com.tadahtech.enchants.menu;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.config.MainConfig;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.item.ItemBuilder;
import com.tadahtech.enchants.util.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class NPCMenu {

    public static final String NAME = "&8Skyblock ● Random Enchants";

    private static final ItemStack PANE = new ItemBuilder(Material.STAINED_GLASS_PANE)
      .setTitle(" ")
      .setData(DyeColor.BLACK.getWoolData())
      .build();

    private ItemStack COMMON;
    private ItemStack UNCOMMON;
    private ItemStack RARE;
    private ItemStack LEGENDARY;

    private Inventory inventory;

    public NPCMenu() {
        MainConfig mainConfig = Enchants.getInstance().getMainConfig();
        String common = ChatColor.translateAlternateColorCodes('&', mainConfig.getColor(Rarity.COMMON));
        String uncommon = ChatColor.translateAlternateColorCodes('&', mainConfig.getColor(Rarity.UNCOMMON));
        String rare = ChatColor.translateAlternateColorCodes('&', mainConfig.getColor(Rarity.RARE));
        String legendary = ChatColor.translateAlternateColorCodes('&', mainConfig.getColor(Rarity.LEGENDARY));

        List<String> commonLore = mainConfig.getNPCLore(Rarity.COMMON).stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        List<String> uncommonLore = mainConfig.getNPCLore(Rarity.UNCOMMON).stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        List<String> rareLore = mainConfig.getNPCLore(Rarity.RARE).stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        List<String> legendLore = mainConfig.getNPCLore(Rarity.LEGENDARY).stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());

        COMMON = new ItemBuilder(Material.STAINED_GLASS_PANE)
          .setTitle(common + Colors.BOLD + "Common")
          .setData((byte) 13)
          .addLoreLines(commonLore)
          .build();

        UNCOMMON = new ItemBuilder(Material.STAINED_GLASS_PANE)
          .setTitle(uncommon + Colors.BOLD + "Uncommon")
          .setData((byte) 3)
          .addLoreLines(uncommonLore)
          .build();

        RARE = new ItemBuilder(Material.STAINED_GLASS_PANE)
          .setTitle(rare + Colors.BOLD + "Rare")
          .setData((byte) 10)
          .addLoreLines(rareLore)
          .build();

        LEGENDARY = new ItemBuilder(Material.STAINED_GLASS_PANE)
          .setTitle(legendary + Colors.BOLD + "Legendary")
          .setData((byte) 1)
          .addLoreLines(legendLore)
          .build();

        this.inventory = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', NAME));
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, PANE);
        }
        inventory.setItem(10, COMMON);
        inventory.setItem(12, UNCOMMON);
        inventory.setItem(14, RARE);
        inventory.setItem(16, LEGENDARY);
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
