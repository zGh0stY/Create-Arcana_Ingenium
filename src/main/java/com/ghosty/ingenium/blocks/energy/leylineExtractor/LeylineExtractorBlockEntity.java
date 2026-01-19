package com.ghosty.ingenium.blocks.energy.leylineExtractor;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.blocks.energy.IArcanaStorage;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.simibubi.create.content.kinetics.gauge.GaugeBlockEntity;
import com.simibubi.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.EnergyStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LeylineExtractorBlockEntity extends KineticBlockEntity implements IArcanaSource, IArcanaStorage, IHaveGoggleInformation {
    private final int MAX_EXTRACT = 50;
    private final int EXTRACT_RATE = 5;

    private EnergyStorage manaStorage = new EnergyStorage(10000);
    private EnergyStorage pranaStorage = new EnergyStorage(10000);
    private EnergyStorage auraStorage = new EnergyStorage(10000);
    ArcanaType arcanaType;
    int tickCounter = 0;

    public LeylineExtractorBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public void setArcanaType(ArcanaType arcanaType) {
        this.arcanaType = arcanaType;
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        readArcanaEnergy(compound);
        arcanaType = ArcanaType.valueOf(compound.getString("ArcanaType"));
        super.read(compound, clientPacket);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        writeArcanaEnergy(compound);
        if (arcanaType != null)
            compound.putString("ArcanaType", arcanaType.name());
        super.write(compound, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();

        if (level != null) {
            if (!level.isClientSide() && isSpeedRequirementFulfilled() && arcanaType != null) {
                tickCounter++;
                if (tickCounter == EXTRACT_RATE) {
                    tickCounter = 0;
                    int amount = addStoredArcana(MAX_EXTRACT, arcanaType, false);
                }
            }
        }
    }

    @Override
    public void lazyTick() {
        super.lazyTick();

        if (!level.isClientSide()) {
            notifyUpdate();
        }
    }

    @Override
    public int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<BlockPos> visited) {
        int maxAmount = requester.addStoredArcana(amount, type, true);
        int extracted = removeStoredArcana(maxAmount, type, false);

        return requester.addStoredArcana(extracted, type, false);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("Mana Stored: " + getStoredArcana(ArcanaType.MANA)).forGoggles(tooltip);
        CreateLang.translate("Prana Stored: " + getStoredArcana(ArcanaType.PRANA)).forGoggles(tooltip);
        CreateLang.translate("Aura Stored: " + getStoredArcana(ArcanaType.AURA)).forGoggles(tooltip);

        return true;
    }

    @Override
    public EnergyStorage getManaStorage() {
        return manaStorage;
    }

    @Override
    public EnergyStorage getPranaStorage() {
        return pranaStorage;
    }

    @Override
    public EnergyStorage getAuraStorage() {
        return auraStorage;
    }

    @Override
    public void resetStoredArcana() {
        manaStorage = new EnergyStorage(10000);
        pranaStorage = new EnergyStorage(10000);
        auraStorage = new EnergyStorage(10000);
    }
}
