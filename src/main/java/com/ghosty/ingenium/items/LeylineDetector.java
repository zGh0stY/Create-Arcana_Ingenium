package com.ghosty.ingenium.items;

import com.ghosty.ingenium.events.LeylineHandler;
import net.minecraft.world.item.Item;

public class LeylineDetector extends Item {

    public LeylineDetector(Properties properties) {
        super(properties);
    }

    public static float calculateProximityToLeyline(double x, double z, int searchRadius) {
        int chunkX = (int) Math.floor(x / 16);
        int chunkZ = (int) Math.floor(z / 16);

        // First, check if we're directly on a border
        if (LeylineHandler.getChecker().isBorderChunk(chunkX, chunkZ)) {
            return 1.0f; // Max intensity - standing right on border
        }

        // Search for the nearest border chunk
        int closestDistance = Integer.MAX_VALUE;

        // Search in increasing radius until we find a border
        for (int radius = 1; radius <= searchRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    // Only check perimeter of this radius (more efficient)
                    if (Math.abs(dx) != radius && Math.abs(dz) != radius) {
                        continue;
                    }

                    int checkX = chunkX + dx;
                    int checkZ = chunkZ + dz;

                    if (LeylineHandler.getChecker().isBorderChunk(checkX, checkZ)) {
                        // Manhattan distance (faster) or Chebyshev distance
                        int distance = Math.max(Math.abs(dx), Math.abs(dz)); // Chebyshev
                        if (distance < closestDistance) {
                            closestDistance = distance;
                        }
                    }
                }
            }

            // If we found something at this radius, calculate proximity
            if (closestDistance != Integer.MAX_VALUE) {
                // Convert distance to proximity (closer = higher value)
                // Using inverse square for smoother falloff
                float normalizedDistance = (float) closestDistance / searchRadius;
                return Math.max(0.0f, 1.0f - (normalizedDistance * normalizedDistance));
            }
        }

        // No border found within search radius
        return 0.0f;
    }
}