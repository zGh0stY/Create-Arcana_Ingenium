package com.ghosty.ingenium.registries;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class AllCapabilities {
    // Declare capabilities
    public static final Capability<IEnergyStorage> MANA = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> PRANA = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IEnergyStorage> AURA = CapabilityManager.get(new CapabilityToken<>() {});

    public static Capability<IEnergyStorage> ArcanaToCapability(String arcanaType) {
        return switch (arcanaType) {
            case "MANA" -> MANA;
            case "PRANA" -> PRANA;
            case "AURA" -> AURA;
            default -> null;
        };
    }

    public static @NotNull String ArcanaToString(Capability<IEnergyStorage> arcanaType) {
        if (arcanaType == MANA)
            return "MANA";
        if (arcanaType == PRANA)
            return "PRANA";
        if (arcanaType == AURA)
            return "AURA";
        return "UNKNOWN";
    }
}
