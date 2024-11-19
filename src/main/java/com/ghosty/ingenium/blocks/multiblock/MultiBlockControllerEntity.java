package com.ghosty.ingenium.blocks.multiblock;

import com.ghosty.ingenium.registries.ArcanaBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockControllerEntity extends BlockEntity {
    private boolean isFormed = false;
    private List<BlockPos> structureBlocks;

    public MultiBlockControllerEntity(BlockEntityType<?> pType, BlockPos pos, BlockState state) {
        super(pType, pos, state);
        structureBlocks = new ArrayList<>();
    }

    public void setFormed(boolean formed) {
        this.isFormed = formed;
        setChanged();

        MultiBlockManager manager = MultiBlockManager.get(this.getLevel());
        if (formed)
            manager.registerController(this.getBlockPos());
        else
            manager.unregisterController(this.getBlockPos());
    }

    public boolean isFormed() {
        return isFormed;
    }

    public void addToStructure(BlockPos pos) {
        if (!structureBlocks.contains(pos)) {
            structureBlocks.add(pos);
            setChanged();
        }
    }

    public void emptyStructure() {
        structureBlocks = new ArrayList<>();
        setChanged();
    }

    public void removeFromStructure(BlockPos pos) {
        structureBlocks.remove(pos);
        setChanged();
    }

    public boolean isPartOfStructure(BlockPos pos) {
        return structureBlocks.contains(pos);
    }

    // Save to NBT
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        // Save the `isFormed` flag
        tag.putBoolean("IsFormed", isFormed);

        // Save the structureBlocks list
        ListTag blockList = new ListTag();
        for (BlockPos pos : structureBlocks) {
            CompoundTag blockPosTag = new CompoundTag();
            blockPosTag.putInt("X", pos.getX());
            blockPosTag.putInt("Y", pos.getY());
            blockPosTag.putInt("Z", pos.getZ());
            blockList.add(blockPosTag);
        }
        tag.put("StructureBlocks", blockList);
    }

    // Load from NBT
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        System.out.println("Loading ControllerEntity Data!");
        System.out.println(tag);

        // Load the `isFormed` flag
        isFormed = tag.getBoolean("IsFormed");

        // Load the structureBlocks list
        structureBlocks = new ArrayList<>();
        ListTag blockList = tag.getList("StructureBlocks", Tag.TAG_COMPOUND); // Each entry is a CompoundTag
        for (int i = 0; i < blockList.size(); i++) {
            CompoundTag blockPosTag = blockList.getCompound(i);
            int x = blockPosTag.getInt("X");
            int y = blockPosTag.getInt("Y");
            int z = blockPosTag.getInt("Z");
            structureBlocks.add(new BlockPos(x, y, z));
        }
    }
}
