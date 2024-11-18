package com.ghosty.ingenium.blocks.multiblock;

import com.ghosty.ingenium.registries.ArcanaBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockControllerEntity extends BlockEntity {
    //TODO: Use NBT tags to save structure data
    private boolean isFormed = false;
    private List<BlockPos> structureBlocks;

    public MultiBlockControllerEntity(BlockEntityType<?> pType, BlockPos pos, BlockState state) {
        super(pType, pos, state);
        structureBlocks = new ArrayList<>();
    }

    public void setFormed(boolean formed) {
        this.isFormed = formed;

        if (formed)
            MultiBlockManager.registerController(this);
        else
            MultiBlockManager.unregisterController(this);
    }

    public boolean isFormed() {
        return isFormed;
    }

    public void addToStructure(BlockPos pos) {
        if (!structureBlocks.contains(pos)) {
            structureBlocks.add(pos);
        }
    }

    public void emptyStructure() {
        structureBlocks = new ArrayList<>();
    }

    public void removeFromStructure(BlockPos pos) {
        structureBlocks.remove(pos);
    }

    public boolean isPartOfStructure(BlockPos pos) {
        return structureBlocks.contains(pos);
    }
}
