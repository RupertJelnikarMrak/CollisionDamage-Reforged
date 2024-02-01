package com.bobigrenade.collisiondamage;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CollisionDamage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Double> ACCELERATION_THRESHOLD = BUILDER
        .comment("How large the player's deceleration must be before they will begin taking damage. (Measured in meters per second)")
        .define("AccelerationThreshold", 17.0);

    private static final ForgeConfigSpec.ConfigValue<Double> DAMAGE_MULTIPLIER = BUILDER
        .comment("Multiplies the damage taken when over the threshold. Default 1.0x is 1 damage per 1m/s/s over threshold.")
        .define("DamageMultiplier", 1.0);

    private static final ForgeConfigSpec.ConfigValue<Boolean> DAMAGE_TYPE_WALL = BUILDER
        .comment("Use damage type FLY_INTO_WALL? If false, will instead use FALL. (Set this to false if you want stuff like feather-falling to affect collision damage as well)")
        .define("DamageTypeWall", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double accelerationThreshold;
    public static double damageMultiplier;
    public static boolean damageTypeWall;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        accelerationThreshold = ACCELERATION_THRESHOLD.get();
        damageMultiplier = DAMAGE_MULTIPLIER.get();
        damageTypeWall = DAMAGE_TYPE_WALL.get();
    }
}
