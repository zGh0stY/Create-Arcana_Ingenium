package com.ghosty.ingenium.blocks.energy.arcanaCoil;

import com.ghosty.ingenium.blocks.energy.IArcanaCoilNetworkBlock;
import com.ghosty.ingenium.blocks.energy.IArcanaCoilNetworkEntity;
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

        if (pLevel.isClientSide())
            return;

        if (pLevel.getBlockEntity(pPos) instanceof ArcanaCoilBlockEntity coilbe) {
            List<BlockPos> positions = BlockDetectionUtil.getBlocksInSphericalRadius(pPos, RANGE);
            List<IArcanaConsumer> consumers = new ArrayList<>();
            List<IArcanaSource> sources = new ArrayList<>();
            for (BlockPos pos : positions) {
                BlockEntity be = pLevel.getBlockEntity(pos);

                if (pos.equals(pPos))
                    continue;
                if (be instanceof IArcanaConsumer consumer) {
                    consumers.add(consumer);
                }
                if (be instanceof IArcanaSource source) {
                    sources.add(source);
                }
            }

            sources.sort((source1, source2) ->
                    Integer.compare(source2.getPriority(), source1.getPriority())
            );

            coilbe.setConsumers(consumers);
            coilbe.setSources(sources);
        }

        IArcanaCoilNetworkBlock.super.onPlaceNetwork(pLevel, pPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        IArcanaCoilNetworkBlock.super.onRemoveNetwork(pLevel, pPos);

        if (pLevel.getBlockEntity(pPos) instanceof ArcanaCoilBlockEntity coilbe) {
            for (IArcanaCoilNetworkEntity entity : coilbe.getConsumers())
                entity.getCoils().remove(coilbe);
            for (IArcanaCoilNetworkEntity entity : coilbe.getSources())
                entity.getCoils().remove(coilbe);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
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
