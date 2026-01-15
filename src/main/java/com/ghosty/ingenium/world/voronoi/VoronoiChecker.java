package com.ghosty.ingenium.world.voronoi;

/**
 * Simple Voronoi checker - just determines if a chunk is a border
 */
public class VoronoiChecker {
    private static final int GRID_SIZE = 32;
    private long worldSeed;
    private boolean initialized = false;

    public VoronoiChecker() {
        // Empty constructor - needs to be initialized with seed
    }

    public void initialize(long seed) {
        this.worldSeed = seed;
        this.initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isBorderChunk(int chunkX, int chunkZ) {
        if (!initialized) return false;

        long centerCell = getCellId(chunkX, chunkZ);

        return centerCell != getCellId(chunkX + 1, chunkZ) ||
                centerCell != getCellId(chunkX - 1, chunkZ) ||
                centerCell != getCellId(chunkX, chunkZ + 1) ||
                centerCell != getCellId(chunkX, chunkZ - 1);
    }

    private long getCellId(int chunkX, int chunkZ) {
        int gridX = Math.floorDiv(chunkX, GRID_SIZE);
        int gridZ = Math.floorDiv(chunkZ, GRID_SIZE);

        long bestId = 0;
        double bestDistSq = Double.MAX_VALUE;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int gx = gridX + dx;
                int gz = gridZ + dz;

                long h = hash(gx, gz);
                double rx = (h & 0xFFFF) / 65535.0;
                double rz = ((h >> 16) & 0xFFFF) / 65535.0;
                double sx = gx * GRID_SIZE + rx * GRID_SIZE;
                double sz = gz * GRID_SIZE + rz * GRID_SIZE;

                double distSq = (chunkX - sx) * (chunkX - sx) + (chunkZ - sz) * (chunkZ - sz);
                if (distSq < bestDistSq) {
                    bestDistSq = distSq;
                    bestId = h;
                }
            }
        }

        return bestId;
    }

    private long hash(long x, long z) {
        long h = x * 374761393L + z * 668265263L + worldSeed;
        h = (h ^ (h >> 13)) * 1274126177L;
        return h ^ (h >> 16);
    }
}