package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlock;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;
import com.ghosty.ingenium.utils.BlockDetectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface IArcanaCoilNetworkBlock {
    default void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        ArcanaIngenium.logger().info("Placed!");
        if (worldIn != null && !worldIn.isClientSide) {
            List<BlockPos> blocks = BlockDetectionUtil.getBlocksInSphericalRadius(pos, ArcanaCoilBlock.RANGE);
            for (BlockPos checkPos : blocks) {
                BlockEntity be = worldIn.getBlockEntity(checkPos);
                if (be instanceof ArcanaCoilBlockEntity coilBE) {
                    coilBE.addToNetwork(pos);
                }
            }
        }
    }
}
