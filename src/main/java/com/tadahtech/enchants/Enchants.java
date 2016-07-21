package com.tadahtech.enchants;

import com.tadahtech.enchants.armorEquip.ArmorEquipListener;
import com.tadahtech.enchants.command.EnchantCommand;
import com.tadahtech.enchants.config.MainConfig;
import com.tadahtech.enchants.config.lang.Lang;
import com.tadahtech.enchants.enchant.EnchantmentCalculator;
import com.tadahtech.enchants.enchant.GlowEnchant;
import com.tadahtech.enchants.enchant.legendary.*;
import com.tadahtech.enchants.enchant.rare.*;
import com.tadahtech.enchants.enchant.uncommon.*;
import com.tadahtech.enchants.enchant.uncommon.Double;
import com.tadahtech.enchants.listener.EnchantListener;
import com.tadahtech.enchants.listener.MenuListener;
import com.tadahtech.enchants.listener.NPCListener;
import com.tadahtech.enchants.listener.NPCMenuListener;
import com.tadahtech.enchants.menu.NPCMenu;
import com.tadahtech.enchants.npc.CustomEntityType;
import com.tadahtech.enchants.npc.NPC;
import com.google.common.collect.Lists;
import com.tadahtech.enchants.enchant.common.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Enchants extends JavaPlugin {

    private static Enchants instance;

    private Lang lang;
    private EnchantmentCalculator enchantmentCalculator;
    private NPCMenu npcMenu;
    private MainConfig mainConfig;
    private List<NPC> entities;

    public static Enchants getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("Registering Config's");
        this.lang = new Lang();
        this.mainConfig = new MainConfig();
        this.npcMenu = new NPCMenu();
        getLogger().info("Registered Config's");
        getLogger().info("Setting up enchantment calculator...");
        this.enchantmentCalculator = new EnchantmentCalculator(this.mainConfig.getTotalChances(), this.mainConfig.getEnchantChances());
        getLogger().info("Set up enchantment calculator.");
        getLogger().info("Registering Listeners...");
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(Collections.emptyList()), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new NPCMenuListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new NPCListener(), this);
        getLogger().info("Registered Listeners.");
        getLogger().info("Registering Command...");
        getCommand("ce").setExecutor(new EnchantCommand());
        getLogger().info("Registering GlowEnchant");
        GlowEnchant.register();
        getLogger().info("Registered GlowEnchant");
        //register enchants
        getLogger().info("Registering Enchantments");
        new Speedster();
        new Jumper();
        new NightVision();
        new Drugged();
        new Strike();
        new Smelter();
        new Sapling();
        new Myopia();
        new Brainwashed();
        new Double();
        new Blocker();
        new Lifesteal();
        new Regrowth();
        new Enlighten();
        new Explosive();
        new SilkGrass();
        new Haste();
        new Slug();
        new Freeze();
        new Heartstealer();
        new Skelly();
        new Return();
        new ItemKeeper();
        new Consciousness();
        new Cannibal();
        new Hothead();
        new Zeus();
        new Juggernaut();
        new Spikes();
        new Fireball();
        getLogger().info("Registered Enchantments");
        getLogger().info("finished.");
        getLogger().info("Setting up NPC's");
        CustomEntityType.register();
        this.entities = Lists.newArrayList();
        for(Location location : mainConfig.getLocations()) {
            NPC npc = (NPC) CustomEntityType.NPC.spawn(location);
            this.entities.add(npc);
        }
        getLogger().info("Set up NPC's");
    }

    @Override
    public void onDisable(){
        mainConfig.save(entities);
        for (NPC d : entities){
            d.getWorld().removeEntity(d);
            d.die();
        }
        this.entities.clear();
    }


    public Lang getLang() {
        return this.lang;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public EnchantmentCalculator getEnchantmentCalculator() {
        return enchantmentCalculator;
    }

    public NPCMenu getNpcMenu() {
        return npcMenu;
    }

    public void addEntity(NPC npc) {
        this.entities.add(npc);
    }

    public void remove(Entity rightClicked) {
        NPC npc = (NPC) ((CraftEntity) rightClicked).getHandle();
        this.entities.remove(npc);
        npc.getWorld().removeEntity(npc);
        npc.die();
    }
}
