package com.ghosty.ingenium.blocks.energy.manaBattery;

import com.ghosty.ingenium.registries.ArcanaBlockEntityTypes;
import com.ghosty.ingenium.registries.ArcanaCapabilities;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class ManaBatteryBlockEntity extends BlockEntity {
    private final EnergyStorage manaStorage = new EnergyStorage(1000); // Mana capacity

    private final LazyOptional<IEnergyStorage> manaOpt = LazyOptional.of(() -> manaStorage);

    public ManaBatteryBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ArcanaCapabilities.MANA)
            return manaOpt.cast();
        return super.getCapability(cap, side);
    }
}
