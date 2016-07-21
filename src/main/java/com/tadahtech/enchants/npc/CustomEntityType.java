package com.tadahtech.enchants.npc;

import com.tadahtech.enchants.Enchants;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 */
public enum CustomEntityType {

    NPC("Villager", 120, NPC.class);

    private static final MetadataValue FIXED = new FixedMetadataValue(Enchants.getInstance(), "");

    CustomEntityType(String name, int id, Class<? extends EntityInsentient> clazz){
        addToMaps(clazz, name, id);
    }

    /**
     * Spawn a customentity at a location.
     * @param location - The location to spawn at.
     * @return - The entity spawned.
     */
    public EntityLiving spawn(Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        EntityLiving entity;
        switch (this) {
            case NPC:
                entity = new NPC(world);
                break;
            default:
                entity = new NPC(world);
                break;
        }
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(entity);
        entity.getBukkitEntity().setMetadata("nodamage", FIXED);
        entity.getBukkitEntity().setCustomNameVisible(true);
        entity.getBukkitEntity().setCustomName(Enchants.getInstance().getMainConfig().getNPCName());
        return entity;
    }

    /**
     * Add the custom mob to the maps.
     */
    private static void addToMaps(Class clazz, String name, int id) {
        ((Map) getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map) getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, id);
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void register() {
        Logger logger = Enchants.getInstance().getLogger();
        logger.info("Registered " + CustomEntityType.NPC.name() );
    }
}
