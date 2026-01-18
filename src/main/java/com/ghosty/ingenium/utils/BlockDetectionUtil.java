package com.ghosty.ingenium.utils;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BlockDetectionUtil {
    static public List<BlockPos> getBlocksInSphericalRadius(BlockPos center, int radius) {
        List<BlockPos> positions = new ArrayList<>();
        int radiusSq = radius * radius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }

                    if (dx*dx + dy*dy + dz*dz <= radiusSq) {
                        positions.add(center.offset(dx, dy, dz));
                    }
                }
            }
        }
        return positions;
    }
}
