package com.ghosty.ingenium.blocks.energy.arcanaCoil;

import com.ghosty.ingenium.blocks.energy.IArcanaCoilNetworkBlock;
import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.registries.AllBlockEntityTypes;
import com.ghosty.ingenium.utils.BlockDetectionUtil;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ArcanaCoilBlock extends Block implements IBE<ArcanaCoilBlockEntity>, IArcanaCoilNetworkBlock {
    public final static int RANGE = 5;

    public ArcanaCoilBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        IArcanaCoilNetworkBlock.super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if (pLevel.isClientSide())
            return;

        if (pLevel.getBlockEntity(pPos) instanceof ArcanaCoilBlockEntity coilbe) {
            List<BlockPos> positions = BlockDetectionUtil.getBlocksInSphericalRadius(pPos, RANGE);
            List<BlockPos> consumers = new ArrayList<>();
            List<BlockPos> sources = new ArrayList<>();
            for (BlockPos pos : positions) {
                BlockEntity be = pLevel.getBlockEntity(pos);

                if (pos.equals(pPos))
                    continue;
                if (be instanceof IArcanaConsumer consumer) {
                    consumers.add(pos);
                }
                if (be instanceof IArcanaSource source) {
                    sources.add(pos);
                }
            }

            sources.sort((pos1, pos2) -> {
                    BlockEntity be1 = pLevel.getBlockEntity(pos1);
                    BlockEntity be2 = pLevel.getBlockEntity(pos2);

                    if (be1 instanceof IArcanaSource source1 && be2 instanceof IArcanaSource source2) {
                        return Integer.compare(source2.getPriority(), source1.getPriority());
                    }
                    return 0;
                }
            );

            coilbe.setConsumers(consumers);
            coilbe.setSources(sources);
        }
    }

    @Override
    public Class<ArcanaCoilBlockEntity> getBlockEntityClass() {
        return ArcanaCoilBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ArcanaCoilBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.ARCANA_COIL.get();
    }
}
