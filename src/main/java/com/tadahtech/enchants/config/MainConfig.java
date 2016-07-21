package com.tadahtech.enchants.config;

import com.tadahtech.enchants.Enchants;
import com.tadahtech.enchants.enchant.CustomEnchantType;
import com.tadahtech.enchants.enchant.Rarity;
import com.tadahtech.enchants.npc.NPC;
import com.tadahtech.enchants.util.Colors;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class MainConfig {

    private final Map<Rarity, String> colors = Maps.newHashMap();
    private final Map<Rarity, Integer> totalChances = Maps.newHashMap();
    private final Map<CustomEnchantType, Integer> enchantChances = Maps.newHashMap();
    private final Map<CustomEnchantType, String> enchantDescriptions = Maps.newHashMap();
    private final Map<Rarity, Integer> costs = Maps.newHashMap();
    private final Map<Rarity, List<String>> npcLore = Maps.newHashMap();
    private final List<Material> allowedBlocks = Lists.newArrayList();

    private List<Location> locations;
    private boolean rightClickBook;
    private String nameFormat;
    private String loreFormat;
    private String npcName;

    public MainConfig() {
        if(!new File(Enchants.getInstance().getDataFolder(), "config.yml").exists()) {
            Enchants.getInstance().saveDefaultConfig();
        }
        load();
    }

    public void load() {
        FileConfiguration config = Enchants.getInstance().getConfig();
        this.rightClickBook = config.getBoolean("click-top-open");

        for(String s : config.getString("explosive-allowed-blocks").split(", ")) {
            this.allowedBlocks.add(Material.getMaterial(s.toUpperCase()));
        }

        ConfigurationSection npcLores = config.getConfigurationSection("npc-lores");
        for(String s : npcLores.getKeys(false)) {
            this.npcLore.put(Rarity.valueOf(s.toUpperCase()), npcLores.getStringList(s));
        }

        ConfigurationSection descriptions = config.getConfigurationSection("enchant-descriptions");
        for(String s : descriptions.getKeys(false)) {
            this.enchantDescriptions.put(CustomEnchantType.valueOf(s.toUpperCase()), descriptions.getString(s));
        }

        ConfigurationSection itemSection = config.getConfigurationSection("enchantment-item");
        this.nameFormat = itemSection.getString("name");
        this.loreFormat = itemSection.getString("description");

        ConfigurationSection colorSection = config.getConfigurationSection("colors");

        for(Rarity rarity : Rarity.values()) {
            this.colors.put(rarity, colorSection.getString(rarity.name()));
        }

        this.locations = config.getStringList("npc.locations").stream().map(this::parseString).collect(Collectors.toList());
        this.npcName = ChatColor.translateAlternateColorCodes('&', config.getString("npc.name"));

        ConfigurationSection npcCats = config.getConfigurationSection("npc.menu.categories");

        for(String s : npcCats.getKeys(false)) {
            Rarity rarity = Rarity.valueOf(s);
            ConfigurationSection section = npcCats.getConfigurationSection(s);
            ConfigurationSection chances = section.getConfigurationSection("chances");
            int total = 0;
            int cost = section.getInt("cost");
            this.costs.put(rarity, cost);
            for(String c : chances.getKeys(false)) {
                CustomEnchantType type = CustomEnchantType.valueOf(c);
                int chance = chances.getInt(c);
                total += chance;
                this.enchantChances.put(type, chance);
            }
            this.totalChances.put(rarity, total);
        }
    }

    public void save(List<NPC> np) {
        List<String> strings = np.stream().map(npc -> parseLocation(npc.getBukkitEntity().getLocation())).collect(Collectors.toList());
        FileConfiguration config = Enchants.getInstance().getConfig();
        config.set("npc.locations", strings);
        Enchants.getInstance().saveConfig();
    }

    public String getColor(Rarity rarity) {
        return this.colors.getOrDefault(rarity, Colors.GRAY);
    }

    public Map<Rarity, Integer> getTotalChances() {
        return totalChances;
    }

    public Map<CustomEnchantType, Integer> getEnchantChances() {
        return enchantChances;
    }

    public int getCost(Rarity rarity) {
        return this.costs.getOrDefault(rarity, 1000);
    }

    public boolean doRightClickBook() {
        return rightClickBook;
    }

    public String getLoreFormat() {
        return loreFormat;
    }

    public String getNameFormat() {
        return nameFormat;
    }

    /**
     * Parses the location into a string. Usable in configurations, and the lot.
     * @param l - The location to parse.
     * @return - A string representing the key components of the location. To recreate it.
     */
    public String parseLocation(Location l){
        return l.getWorld().getName() + ":" + l.getX() + ":"
          + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
    }

    /**
     * Parse the string into a location. Used after retrieving from configurations.
     * @param s - The serialized location string.
     * @return - The location.
     */
    public Location parseString(String s){
        String[] parts = s.split(":");
        String worldName = parts[0];
        String x = parts[1];
        String y = parts[2];
        String z = parts[3];
        String yaw = parts[4];
        String pitch = parts[5];
        return new Location(Enchants.getInstance().getServer().getWorld(worldName), Double.parseDouble(x), Double.parseDouble(y),
          Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getNPCName() {
        return npcName;
    }

    public String getDescription(CustomEnchantType enchantType) {
        return enchantDescriptions.get(enchantType);
    }

    public List<String> getNPCLore(Rarity rarity) {
        return npcLore.get(rarity);
    }

    public boolean isAllowed(Material material) {
        return this.allowedBlocks.contains(material);
    }
}
