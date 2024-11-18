package com.ghosty.ingenium.blocks.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MultiBlockManager {
    private static final Map<ChunkPos, List<MultiBlockControllerEntity>> trackedControllers = new HashMap<>();

    // Register a controller in the map based on its chunk position
    public static void registerController(MultiBlockControllerEntity controller) {
        ChunkPos chunkPos = new ChunkPos(controller.getBlockPos());
        trackedControllers.computeIfAbsent(chunkPos, k -> new ArrayList<>()).add(controller);
    }

    // Unregister a controller from the map
    public static void unregisterController(MultiBlockControllerEntity controller) {
        ChunkPos chunkPos = new ChunkPos(controller.getBlockPos());
        List<MultiBlockControllerEntity> controllersInChunk = trackedControllers.get(chunkPos);

        if (controllersInChunk != null) {
            controllersInChunk.remove(controller);
            if (controllersInChunk.isEmpty()) {
                trackedControllers.remove(chunkPos);
            }
        }
    }

    // Find the controller for a given block position
    public static MultiBlockControllerEntity findControllerForBlock(Level level, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        List<MultiBlockControllerEntity> controllersInChunk = trackedControllers.get(chunkPos);

        if (controllersInChunk != null) {
            for (MultiBlockControllerEntity controller : controllersInChunk) {
                if (controller.isPartOfStructure(pos)) {
                    return controller;
                }
            }
        }

        return null;
    }
}
