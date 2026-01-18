package com.ghosty.ingenium.blocks.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IArcanaCoilNetworkBlock {
    default void onPlaceNetwork(Level pLevel, BlockPos pPos) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);

        if (entity instanceof IArcanaCoilNetworkEntity networkEntity) {
            networkEntity.updateCoilsBlockPlaced();
        }
    }

    default void onRemoveNetwork(Level pLevel, BlockPos pPos) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);

        if (entity instanceof IArcanaCoilNetworkEntity networkEntity) {
            networkEntity.updateCoilsBlockRemoved();
        }
    }
}
