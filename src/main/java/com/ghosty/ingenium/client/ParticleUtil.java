package com.ghosty.ingenium.client;

import com.ghosty.ingenium.api.energy.ArcanaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;

public class ParticleUtil {
    // Private constructor - static utility class
    private ParticleUtil() {
    }

    /**
     * Spawns a particle trail between two positions
     *
     * @param level Client level
     * @param from  Starting position
     * @param to    Ending position
     * @param color RGB color (0xRRGGBB)
     * @param count Number of particles
     * @param speed Movement speed (0 for static)
     */
    public static void spawnParticleTrail(Level level, BlockPos from, BlockPos to,
                                          int color, int count, float speed) {
        if (level == null) return;

        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double dz = to.getZ() - from.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (distance < 0.1) return;

        // Normalize direction
        double stepX = dx / distance;
        double stepY = dy / distance;
        double stepZ = dz / distance;

        // Convert color
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        DustParticleOptions particle = new DustParticleOptions(
                new Vector3f(r, g, b), 1.0f
        );

        // Spawn particles along the line
        for (int i = 0; i <= count; i++) {
            double progress = (double) i / count;
            double x = from.getX() + 0.5 + dx * progress;
            double y = from.getY() + 0.5 + dy * progress;
            double z = from.getZ() + 0.5 + dz * progress;

            // Add slight random offset for more natural look
            double offset = 0.1;
            x += (Math.random() - 0.5) * offset;
            y += (Math.random() - 0.5) * offset;
            z += (Math.random() - 0.5) * offset;

            level.addParticle(particle, x, y, z,
                    stepX * speed, stepY * speed, stepZ * speed);
        }
    }

    /**
     * Arcana-specific particle trail
     */
    public static void spawnArcanaTrail(Level level, BlockPos from, BlockPos to,
                                        ArcanaType type) {
        spawnParticleTrail(level, from, to, type.getColor(), 20, 0.02f);
    }
}
