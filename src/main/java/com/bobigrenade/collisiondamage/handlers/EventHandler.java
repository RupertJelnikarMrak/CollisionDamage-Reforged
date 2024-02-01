package com.bobigrenade.collisiondamage.handlers;

import com.bobigrenade.collisiondamage.networking.ModMessages;
import com.bobigrenade.collisiondamage.networking.packet.CollisionC2SPacket;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class EventHandler {
    
    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {
        if (event.phase == Phase.START || event.player == null || event.side == LogicalSide.SERVER) return;

        Player player = event.player;
        
        double motionX = player.getDeltaMovement().x;
        double motionZ = player.getDeltaMovement().z;
        double curMotionCombined = ((double) ((int)(Math.sqrt((motionX * motionZ) + (motionZ * motionZ)) * 20 * 100))) / 100;

        double prevMotionCombined = ((double) player.getPersistentData().getDouble("prevMotionCombined"));
        player.getPersistentData().putDouble("prevMotionCombined", curMotionCombined);

        if (player.isFallFlying()) return;

        double accel = prevMotionCombined-curMotionCombined;
        if (accel > 5 && (player.horizontalCollision || player.minorHorizontalCollision)) {
            ModMessages.sendToServer(new CollisionC2SPacket(accel));
        }
    }
}
