package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlock;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;
import com.ghosty.ingenium.utils.BlockDetectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IArcanaCoilNetworkEntity {
    List<ArcanaCoilBlockEntity> getCoils();
    default void updateCoilsBlockRemoved() {
        for (ArcanaCoilBlockEntity coil : getCoils()) {
            coil.removeFromNetwork(this);
        }
    }

    default void updateCoilsBlockPlaced() {
        List<BlockPos> blocks =  BlockDetectionUtil.getBlocksInSphericalRadius(getBlockPos(), ArcanaCoilBlock.RANGE);
        for (BlockPos pos : blocks) {
            if (getLevel().getBlockEntity(pos) instanceof ArcanaCoilBlockEntity coil) {
                coil.addToNetwork(this);
                getCoils().add(coil);
            }
        }
    }

    Level getLevel();
    BlockPos getBlockPos();
}
