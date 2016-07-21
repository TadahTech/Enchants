package com.tadahtech.enchants.util;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 *
 */
public class ItemUtil {

    private static final List<Material> weapons = Lists.newArrayList(Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD,
      Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE, Material.BOW);

    private static final List<Material> armor = Lists.newArrayList(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
      Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
      Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
      Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
      Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

    private static final List<Material> tools = Lists.newArrayList(Material.STONE_PICKAXE, Material.WOOD_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE,
      Material.STONE_SPADE, Material.WOOD_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE,
      Material.STONE_AXE, Material.WOOD_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE);

    private static final List<Material> materials = Lists.newArrayList(Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE,
      Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE,
      Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE,
      Material.DIAMOND_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.WOOD_HOE,
      Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD);

    public static boolean isWeapon(ItemStack itemStack) {
        return weapons.contains(itemStack.getType());
    }

    public static boolean isArmor(ItemStack itemStack) {
        return armor.contains(itemStack.getType());
    }

    public static boolean isTool(ItemStack itemStack) {
        return tools.contains(itemStack.getType());
    }

    public static boolean isMaterial(ItemStack itemStack) {
        return materials.contains(itemStack.getType());
    }


    public static boolean isSpade(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.STONE_SPADE
          || material == Material.WOOD_SPADE
          || material == Material.GOLD_SPADE
          || material == Material.IRON_SPADE
          || material == Material.DIAMOND_SPADE;
    }
    
    public static boolean isPickaxe(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.STONE_PICKAXE
          || material == Material.WOOD_PICKAXE
          || material == Material.GOLD_PICKAXE
          || material == Material.IRON_PICKAXE
          || material == Material.DIAMOND_PICKAXE;
    }

    public static boolean isAxe(ItemStack itemStack) {
        Material material = itemStack.getType();
        return material == Material.STONE_AXE
          || material == Material.WOOD_AXE
          || material == Material.GOLD_AXE
          || material == Material.IRON_AXE
          || material == Material.DIAMOND_AXE;
    }

    public static boolean isBoots(ItemStack itemStack) {
        return itemStack.getType() == Material.LEATHER_BOOTS
          || itemStack.getType() == Material.CHAINMAIL_BOOTS
          || itemStack.getType() == Material.IRON_BOOTS
          || itemStack.getType() == Material.GOLD_BOOTS
          || itemStack.getType() == Material.DIAMOND_BOOTS;
    }

    public static boolean isHelmet(ItemStack itemStack) {
        return itemStack.getType() == Material.LEATHER_HELMET
          || itemStack.getType() == Material.CHAINMAIL_HELMET
          || itemStack.getType() == Material.IRON_HELMET
          || itemStack.getType() == Material.GOLD_HELMET
          || itemStack.getType() == Material.DIAMOND_HELMET;
    }

    public static String getName(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                return itemStack.getItemMeta().getDisplayName();
            }
        }

        Material material = itemStack.getType();

        String itemName = material.name();

        StringBuilder builder = new StringBuilder();

        String[] str = itemName.split("_");
        if (str.length == 0) {
            builder.append(itemName.substring(0, 1).toUpperCase()).append(itemName.substring(1, itemName.length()).toLowerCase());
        } else {
            for (int i = 0; i < str.length; i++) {
                String s = str[i];
                builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length()).toLowerCase());
                if ((i + 1) < str.length) {
                    builder.append(" ");
                }
            }
        }

        return builder.toString();
    }

}
