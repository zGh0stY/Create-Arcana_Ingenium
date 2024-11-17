package com.ghosty.ingenium.registries;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.energy.IEnergyStorage;

public class ArcanaCapabilities {
    // Declare capabilities
    public static final Capability<IEnergyStorage> MANA = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> PRANA = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> AURA = CapabilityManager.get(new CapabilityToken<>() {});
}
