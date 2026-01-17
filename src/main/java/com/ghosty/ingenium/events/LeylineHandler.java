package com.ghosty.ingenium.events;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.registries.AllCapabilities;
import com.ghosty.ingenium.world.voronoi.VoronoiChecker;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class LeylineHandler {
    private static VoronoiChecker checker;

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        long seed = serverLevel.getSeed();

        checker = new VoronoiChecker();
        checker.initialize(seed);

        ArcanaIngenium.logger().info("Voronoi checker initialized with world seed: {}", seed);
    }

    public static VoronoiChecker getChecker() {
        return checker;
    }

    public static @NotNull ArcanaType arcanaTypeFromTemperature(float temperature) {
        if (temperature <= 0.3)
            return ArcanaType.MANA;
        if (temperature < 2.0 && temperature > 0.3)
            return ArcanaType.PRANA;
        return ArcanaType.AURA;
    }
}
