package com.ghosty.ingenium.events;

import com.ghosty.ingenium.CreateArcana;
import com.ghosty.ingenium.world.voronoi.VoronoiChecker;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

        CreateArcana.logger().info("Voronoi checker initialized with world seed: {}", seed);
    }

    public static VoronoiChecker getChecker() {
        return checker;
    }
}
