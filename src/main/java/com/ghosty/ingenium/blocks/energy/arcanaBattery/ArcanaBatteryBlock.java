package com.ghosty.ingenium.blocks.energy.arcanaBattery;

import com.ghosty.ingenium.blocks.energy.IArcanaCoilNetworkBlock;
import com.ghosty.ingenium.registries.AllBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ArcanaBatteryBlock extends Block implements IBE<ArcanaBatteryBlockEntity>, IArcanaCoilNetworkBlock {
    public ArcanaBatteryBlock(Properties pProperties) {
        super(pProperties);
    }

    /*
    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        ManaBatteryBlockEntity blockEntity = getBlockEntity(worldIn, pos);
        if (blockEntity == null)
            return;

        blockEntity.getCapability(ArcanaCapabilities.MANA).ifPresent(manaStorage -> {
            manaStorage.receiveEnergy(20, false);
        });
    }
    */

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        IArcanaCoilNetworkBlock.super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    public Class<ArcanaBatteryBlockEntity> getBlockEntityClass() {
        return ArcanaBatteryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ArcanaBatteryBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.ARCANA_BATTERY.get();
    }
}
