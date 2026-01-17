package com.ghosty.ingenium.blocks.energy.arcanaCoil;

import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.registries.AllBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ArcanaCoilBlock extends Block implements IBE<ArcanaCoilBlockEntity> {
    final int RANGE = 5;

    public ArcanaCoilBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if (pLevel.isClientSide())
            return;

        if (pLevel.getBlockEntity(pPos) instanceof ArcanaCoilBlockEntity coilbe) {
            List<BlockPos> positions = getBlocksInSphericalRadius(pPos, RANGE);
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
    }

    private List<BlockPos> getBlocksInSphericalRadius(BlockPos center, int radius) {
        List<BlockPos> positions = new ArrayList<>();
        int radiusSq = radius * radius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx*dx + dy*dy + dz*dz <= radiusSq) {
                        positions.add(center.offset(dx, dy, dz));
                    }
                }
            }
        }
        return positions;
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
