package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.CreateArcana;
import com.ghosty.ingenium.blocks.energy.manaBattery.ManaBatteryBlockEntity;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ArcanaBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = CreateArcana.registrate();

    public static final BlockEntityEntry<SpeedUpperBlockEntity> SPEED_UPPER = REGISTRATE
            .blockEntity("speed_upper", SpeedUpperBlockEntity::new)
            .validBlocks(ArcanaBlocks.SPEED_UPPER)
            .register();

    public static final BlockEntityEntry<ManaBatteryBlockEntity> MANA_BATTERY = REGISTRATE
            .blockEntity("mana_battery", ManaBatteryBlockEntity::new)
            .validBlocks(ArcanaBlocks.MANA_BATTERY)
            .register();

    public static void register() {

    }
}