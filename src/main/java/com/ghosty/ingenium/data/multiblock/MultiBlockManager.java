package com.ghosty.ingenium.data.multiblock;

import com.ghosty.ingenium.blocks.multiblock.MultiBlockControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class MultiBlockManager extends SavedData {
    private static final String DATA_NAME = "multiblock_manager";

    // Three data structures for fast lookups:
    private final Map<BlockPos, BlockPos> blockToController = new HashMap<>(); // Fast O(1) block lookup
    private final Map<BlockPos, Set<BlockPos>> controllerToBlocks = new HashMap<>(); // Controller's blocks
    private final Map<ChunkPos, Set<BlockPos>> chunkToControllers = new HashMap<>(); // For chunk-based ops

    public MultiBlockManager() {}

    public MultiBlockManager(CompoundTag tag) {
        load(tag);
    }

    // Register a complete structure
    public void registerStructure(BlockPos controllerPos, Set<BlockPos> structureBlocks) {
        // Remove old structure if exists
        unregisterStructure(controllerPos);

        // Register new structure
        controllerToBlocks.put(controllerPos, new HashSet<>(structureBlocks));

        for (BlockPos blockPos : structureBlocks) {
            blockToController.put(blockPos, controllerPos);

            // Also track by chunk for potential chunk-based operations
            ChunkPos chunkPos = new ChunkPos(blockPos);
            chunkToControllers.computeIfAbsent(chunkPos, k -> new HashSet<>()).add(controllerPos);
        }

        setDirty();
    }

    // Unregister a structure
    public void unregisterStructure(BlockPos controllerPos) {
        Set<BlockPos> blocks = controllerToBlocks.remove(controllerPos);
        if (blocks != null) {
            for (BlockPos blockPos : blocks) {
                blockToController.remove(blockPos);

                // Clean up chunk mapping
                ChunkPos chunkPos = new ChunkPos(blockPos);
                Set<BlockPos> controllersInChunk = chunkToControllers.get(chunkPos);
                if (controllersInChunk != null) {
                    controllersInChunk.remove(controllerPos);
                    if (controllersInChunk.isEmpty()) {
                        chunkToControllers.remove(chunkPos);
                    }
                }
            }
        }
        setDirty();
    }

    // FAST O(1) lookup: Is this block part of any structure?
    public boolean isStructureBlock(BlockPos blockPos) {
        return blockToController.containsKey(blockPos);
    }

    // FAST O(1) lookup: Get controller for a block
    public BlockPos getControllerForBlock(BlockPos blockPos) {
        return blockToController.get(blockPos);
    }

    // Get all blocks for a controller
    public Set<BlockPos> getBlocksForController(BlockPos controllerPos) {
        Set<BlockPos> blocks = controllerToBlocks.get(controllerPos);
        return blocks != null ? Collections.unmodifiableSet(blocks) : Collections.emptySet();
    }

    // Load from NBT
    public void load(CompoundTag tag) {
        blockToController.clear();
        controllerToBlocks.clear();
        chunkToControllers.clear();

        // Load controller->blocks mapping
        ListTag controllerList = tag.getList("Controllers", Tag.TAG_COMPOUND);
        for (int i = 0; i < controllerList.size(); i++) {
            CompoundTag controllerTag = controllerList.getCompound(i);
            BlockPos controllerPos = BlockPos.of(controllerTag.getLong("ControllerPos"));

            Set<BlockPos> blocks = new HashSet<>();
            ListTag blocksList = controllerTag.getList("Blocks", Tag.TAG_COMPOUND);
            for (int j = 0; j < blocksList.size(); j++) {
                BlockPos blockPos = BlockPos.of(blocksList.getCompound(j).getLong("Pos"));
                blocks.add(blockPos);
                blockToController.put(blockPos, controllerPos);

                // Build chunk mapping
                ChunkPos chunkPos = new ChunkPos(blockPos);
                chunkToControllers.computeIfAbsent(chunkPos, k -> new HashSet<>()).add(controllerPos);
            }

            controllerToBlocks.put(controllerPos, blocks);
        }
    }

    // Save to NBT
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag controllerList = new ListTag();

        for (Map.Entry<BlockPos, Set<BlockPos>> entry : controllerToBlocks.entrySet()) {
            CompoundTag controllerTag = new CompoundTag();
            controllerTag.putLong("ControllerPos", entry.getKey().asLong());

            ListTag blocksList = new ListTag();
            for (BlockPos blockPos : entry.getValue()) {
                CompoundTag blockTag = new CompoundTag();
                blockTag.putLong("Pos", blockPos.asLong());
                blocksList.add(blockTag);
            }

            controllerTag.put("Blocks", blocksList);
            controllerList.add(controllerTag);
        }

        tag.put("Controllers", controllerList);
        return tag;
    }

    public static MultiBlockManager get(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("MultiBlockManager can only be used on the server side!");
        }
        return serverLevel.getDataStorage().computeIfAbsent(
                tag -> {
                    MultiBlockManager manager = new MultiBlockManager();
                    manager.load(tag);
                    return manager;
                },
                MultiBlockManager::new,
                DATA_NAME
        );
    }
}