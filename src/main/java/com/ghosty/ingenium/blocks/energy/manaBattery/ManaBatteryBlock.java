package com.ghosty.ingenium.blocks.energy.manaBattery;

import com.ghosty.ingenium.registries.ArcanaBlockEntityTypes;
import com.ghosty.ingenium.registries.ArcanaCapabilities;
import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class ManaBatteryBlock extends Block implements IBE<ManaBatteryBlockEntity> {
    public ManaBatteryBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = getBlockEntity(level, pos);

            if (blockEntity instanceof ManaBatteryBlockEntity energyBlockEntity) {
                energyBlockEntity.getCapability(ArcanaCapabilities.MANA).ifPresent(manaStorage -> {
                    manaStorage.receiveEnergy(150, false);

                    int currentMana = manaStorage.getEnergyStored();

                    player.sendSystemMessage(Component.literal("Current Mana: " + currentMana));
                });
            }
        }

        return InteractionResult.SUCCESS;
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
    public Class<ManaBatteryBlockEntity> getBlockEntityClass() {
        return ManaBatteryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ManaBatteryBlockEntity> getBlockEntityType() {
        return ArcanaBlockEntityTypes.MANA_BATTERY.get();
    }
}
