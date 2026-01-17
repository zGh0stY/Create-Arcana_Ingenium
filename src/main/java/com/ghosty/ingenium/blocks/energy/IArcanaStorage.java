package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.api.energy.ArcanaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public interface IArcanaStorage {
    // Remove the shared storage fields - each implementing class will provide their own

    // Add these abstract methods that implementing classes MUST provide
    EnergyStorage getManaStorage();
    EnergyStorage getPranaStorage();
    EnergyStorage getAuraStorage();

    default void readArcanaEnergy(CompoundTag compound) {
        resetStoredArcana();

        getManaStorage().receiveEnergy(compound.getInt("ManaStorage"), false);
        getPranaStorage().receiveEnergy(compound.getInt("PranaStorage"), false);
        getAuraStorage().receiveEnergy(compound.getInt("AuraStorage"), false);
    }

    default void writeArcanaEnergy(CompoundTag compound) {
        compound.putInt("ManaStorage", getManaStorage().getEnergyStored());
        compound.putInt("PranaStorage", getPranaStorage().getEnergyStored());
        compound.putInt("AuraStorage", getAuraStorage().getEnergyStored());
    }

    default int getStoredArcana(ArcanaType type) {
        switch (type) {
            case MANA -> {
                return getManaStorage().getEnergyStored();
            }
            case PRANA -> {
                return getPranaStorage().getEnergyStored();
            }
            case AURA -> {
                return getAuraStorage().getEnergyStored();
            }
            default -> {
                return 0;
            }
        }
    }

    default int addStoredArcana(int amount, ArcanaType type, boolean simulate) {
        switch (type) {
            case MANA -> {
                return getManaStorage().receiveEnergy(amount, simulate);
            }
            case PRANA -> {
                return getPranaStorage().receiveEnergy(amount, simulate);
            }
            case AURA -> {
                return getAuraStorage().receiveEnergy(amount, simulate);
            }
            default -> {
                return 0;
            }
        }
    }

    default int removeStoredArcana(int amount, ArcanaType type, boolean simulate) {
        switch(type) {
            case MANA -> {
                return getManaStorage().extractEnergy(amount, simulate);
            }
            case PRANA -> {
                return getPranaStorage().extractEnergy(amount, simulate);
            }
            case AURA -> {
                return getAuraStorage().extractEnergy(amount, simulate);
            }
            default -> {
                return 0;
            }
        }
    }

    void resetStoredArcana();
}