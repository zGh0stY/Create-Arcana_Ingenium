package com.ghosty.ingenium.blocks.energy.arcanaBattery;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.blocks.energy.IArcanaStorage;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlock;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;
import com.ghosty.ingenium.network.NetworkHandler;
import com.ghosty.ingenium.network.ParticleTrailPacket;
import com.ghosty.ingenium.utils.BlockDetectionUtil;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.EnergyStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ArcanaBatteryBlockEntity extends SmartBlockEntity implements IArcanaStorage, IArcanaSource, IArcanaConsumer, IHaveGoggleInformation {
    private EnergyStorage manaStorage = new EnergyStorage(10000);
    private EnergyStorage pranaStorage = new EnergyStorage(10000);
    private EnergyStorage auraStorage = new EnergyStorage(10000);

    // Change constructor to match BlockEntityType.BlockEntitySupplier signature
    public ArcanaBatteryBlockEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void lazyTick() {
        super.lazyTick();

        if (!level.isClientSide()) {
            notifyUpdate();
        }
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        readArcanaEnergy(compound);
        super.read(compound, clientPacket);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        writeArcanaEnergy(compound);
        super.write(compound, clientPacket);
    }

    @Override
    public void initiateArcanaRequest(IArcanaSource coil) {
        int amount = coil.requestArcana(this, 10000 - getStoredArcana(ArcanaType.PRANA), ArcanaType.PRANA);
        if (amount > 0) {
            if (coil instanceof BlockEntity coilBE) {
                ParticleTrailPacket particlepacket = new ParticleTrailPacket(coilBE.getBlockPos(), this.getBlockPos(), ArcanaType.PRANA);
                NetworkHandler.sendToAllNear(particlepacket, this.level, this.worldPosition, 64);
            }
        }
        else {
            if (coil instanceof BlockEntity coilBE) {
                ParticleTrailPacket particlepacket = new ParticleTrailPacket(coilBE.getBlockPos(), this.getBlockPos(), 0, 20, 0.02f);
                NetworkHandler.sendToAllNear(particlepacket, this.level, this.worldPosition, 64);
            }
        }
    }

    @Override
    public int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<BlockPos> visited) {
        if (requester instanceof ArcanaBatteryBlockEntity)
            return 0;

        int maxAmount = requester.addStoredArcana(amount, type, true);
        int extracted = removeStoredArcana(maxAmount, type, false);

        return requester.addStoredArcana(extracted, type, false);
    }

    @Override
    public int getPriority() {
        return 1;
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