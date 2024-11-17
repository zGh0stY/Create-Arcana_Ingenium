package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.CreateArcana;
import com.ghosty.ingenium.blocks.energy.manaBattery.ManaBatteryBlockEntity;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ArcanaBlockEntityTypes {
    public static final BlockEntityEntry<SpeedUpperBlockEntity> SPEED_UPPER = CreateArcana.REGISTRATE
            .blockEntity("speed_upper", SpeedUpperBlockEntity::new)
            .instance(() -> SplitShaftInstance::new)
            .validBlocks(ArcanaBlocks.SPEED_UPPER)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<ManaBatteryBlockEntity> MANA_BATTERY = CreateArcana.REGISTRATE
            .blockEntity("mana_battery", ManaBatteryBlockEntity::new)
            .validBlocks(ArcanaBlocks.MANA_BATTERY)
            .register();

    public static void register() {}
}
