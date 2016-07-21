package com.tadahtech.enchants.npc;

import net.minecraft.server.v1_8_R3.*;

/**
 *
 */
public class NPC extends EntityVillager {

    public NPC(World world) {
        super(world);
        NMS.clearGoals(targetSelector, goalSelector);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
    }


    /**
     * Overriding the move method so that the Dealer does not move.
     * @param x - The X movement.
     * @param y - The Y movement.
     * @param z - The Z movement.
     */
    @Override
    public void g(double x, double y, double z){
    }

    /**
     * Overriding the collide method so that the Dealer cannot be pushed around. It doesn't fall to peer pressure.
     * It is not a scrub. It stands on its own to feet. Working hard, day and night. It has a family.
     * It worked hard to get this far. It doesn't let other people push it around. It stands firm. It was born on a warm day,
     * in the heart of the valley. One day, its mother and father abandoned it. They left to go to the casino. Wait a minute...
     * That's them over there! MOM! DAD! WHERE HAVE YOU BEEN?!?!
     * @param other The entity colliding
     */
    @Override
    public void collide(Entity other){

    }
}
