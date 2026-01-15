package com.ghosty.ingenium.blocks.energy.manaBattery;

import com.ghosty.ingenium.registries.AllCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ArcanaBatteryBlockEntity extends BlockEntity {
    private final EnergyStorage manaStorage = new EnergyStorage(1000);
    private final EnergyStorage pranaStorage = new EnergyStorage(1000);
    private final EnergyStorage auraStorage = new EnergyStorage(1000);
    private final LazyOptional<IEnergyStorage> manaOpt = LazyOptional.of(() -> manaStorage);
    private final LazyOptional<IEnergyStorage> pranaOpt = LazyOptional.of(() -> pranaStorage);
    private final LazyOptional<IEnergyStorage> auraOpt = LazyOptional.of(() -> auraStorage);

    // Change constructor to match BlockEntityType.BlockEntitySupplier signature
    public ArcanaBatteryBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == AllCapabilities.MANA)
            return manaOpt.cast();
        if (cap == AllCapabilities.PRANA)
            return pranaOpt.cast();
        if (cap == AllCapabilities.AURA)
            return auraOpt.cast();
        return super.getCapability(cap, side);
    }
}