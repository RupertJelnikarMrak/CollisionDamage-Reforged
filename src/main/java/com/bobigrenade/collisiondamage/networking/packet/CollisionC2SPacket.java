package com.bobigrenade.collisiondamage.networking.packet;

import java.util.function.Supplier;

import com.bobigrenade.collisiondamage.Config;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CollisionC2SPacket {
    private double accel;

    public double getAccel() {
        return accel;
    }

    public CollisionC2SPacket() {
        this.accel = 0;
    }

    public CollisionC2SPacket(double accel) {
        this.accel = accel;
    }

    public CollisionC2SPacket(FriendlyByteBuf buf) {
        this.accel = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(accel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (this.accel > Config.accelerationThreshold) {
                ServerPlayer player = context.getSender();

                float damageValue = (((float)Math.round((this.accel - Config.accelerationThreshold)*4*Config.damageMultiplier))/4);
                player.hurt(Config.damageTypeWall ? player.damageSources().flyIntoWall() : player.damageSources().fall(), damageValue);
            }
        });
        return true;
    }
}
