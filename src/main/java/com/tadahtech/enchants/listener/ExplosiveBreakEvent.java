package com.tadahtech.enchants.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 */
public class ExplosiveBreakEvent extends BlockBreakEvent {

    private static final HandlerList handlers = new HandlerList();

    public ExplosiveBreakEvent(Block theBlock, Player player) {
        super(theBlock, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
