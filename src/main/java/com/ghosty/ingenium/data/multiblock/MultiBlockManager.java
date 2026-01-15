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
    private final Map<ChunkPos, List<BlockPos>> trackedControllers = new HashMap<>();

    public MultiBlockManager() {

    }

    public MultiBlockManager(CompoundTag tag) {
        load(tag);
    }

    // Register a controller in the map based on its chunk position
    public void registerController(BlockPos controller) {
        ChunkPos chunkPos = new ChunkPos(controller);
        if (trackedControllers.containsKey(chunkPos))
            if (trackedControllers.get(chunkPos).contains(controller))
                return;
        trackedControllers.computeIfAbsent(chunkPos, k -> new ArrayList<>()).add(controller);
        System.out.println("Registering Controller!");
        System.out.println(trackedControllers);
        setDirty();
    }

    // Unregister a controller from the map
    public void unregisterController(BlockPos controller) {
        ChunkPos chunkPos = new ChunkPos(controller);
        List<BlockPos> controllersInChunk = trackedControllers.get(chunkPos);

        if (controllersInChunk != null) {
            controllersInChunk.remove(controller);
            if (controllersInChunk.isEmpty()) {
                trackedControllers.remove(chunkPos);
            }
        }

        System.out.println("Unregistering Controller!");
        System.out.println(trackedControllers);

        setDirty();
    }

    // Find the controller for a given block position
    public MultiBlockControllerEntity findControllerForBlock(Level level, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        List<BlockPos> controllersInChunk = trackedControllers.get(chunkPos);

        if (controllersInChunk != null) {
            for (BlockPos controller : controllersInChunk) {
                MultiBlockControllerEntity entity = (MultiBlockControllerEntity) level.getBlockEntity(controller);

                if (entity != null && entity.isPartOfStructure(pos)) {
                    return entity;
                }
            }
        }

        return null;
    }

    // Load the data from NBT
    public void load(CompoundTag tag) {
        trackedControllers.clear();
        ListTag chunkList = tag.getList("TrackedControllers", Tag.TAG_COMPOUND);

        for (int i = 0; i < chunkList.size(); i++) {
            CompoundTag chunkTag = chunkList.getCompound(i);
            ChunkPos chunkPos = new ChunkPos(chunkTag.getInt("ChunkX"), chunkTag.getInt("ChunkZ"));

            List<BlockPos> controllers = new ArrayList<>();
            ListTag controllerList = chunkTag.getList("Controllers", Tag.TAG_COMPOUND);
            for (int j = 0; j < controllerList.size(); j++) {
                CompoundTag posTag = controllerList.getCompound(j);
                BlockPos pos = new BlockPos(posTag.getInt("X"), posTag.getInt("Y"), posTag.getInt("Z"));
                controllers.add(pos);
            }

            trackedControllers.put(chunkPos, controllers);
        }

        System.out.println("Loading MultiblockManager Controllers!");
        System.out.println(trackedControllers);
    }

    // Save the data to NBT
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag chunkList = new ListTag();

        for (Map.Entry<ChunkPos, List<BlockPos>> entry : trackedControllers.entrySet()) {
            CompoundTag chunkTag = new CompoundTag();
            chunkTag.putInt("ChunkX", entry.getKey().x);
            chunkTag.putInt("ChunkZ", entry.getKey().z);

            ListTag controllerList = new ListTag();
            for (BlockPos pos : entry.getValue()) {
                CompoundTag posTag = new CompoundTag();
                posTag.putInt("X", pos.getX());
                posTag.putInt("Y", pos.getY());
                posTag.putInt("Z", pos.getZ());
                controllerList.add(posTag);
            }

            chunkTag.put("Controllers", controllerList);
            chunkList.add(chunkTag);
        }

        tag.put("TrackedControllers", chunkList);
        return tag;
    }

    // Get the manager instance for a level
    public static MultiBlockManager get(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("MultiBlockManager can only be used on the server side!");
        }

        return serverLevel.getDataStorage().computeIfAbsent(
                MultiBlockManager::new,
                MultiBlockManager::new,
                DATA_NAME
        );
    }
}
