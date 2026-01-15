package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.blocks.energy.manaBattery.ArcanaBatteryBlockEntity;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlockEntity;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockControllerEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = ArcanaIngenium.registrate();

    public static final BlockEntityEntry<SpeedUpperBlockEntity> SPEED_UPPER = REGISTRATE
            .blockEntity("speed_upper", SpeedUpperBlockEntity::new)
            .validBlocks(AllBlocks.SPEED_UPPER)
            .register();

    public static final BlockEntityEntry<ArcanaBatteryBlockEntity> MANA_BATTERY = REGISTRATE
            .blockEntity("mana_battery", ArcanaBatteryBlockEntity::new)
            .validBlocks(AllBlocks.MANA_BATTERY)
            .register();

    public static final BlockEntityEntry<MultiBlockControllerEntity> MULTIBLOCK_CONTROLLER = REGISTRATE
            .blockEntity("multiblock_controller", MultiBlockControllerEntity::new)
            .validBlocks(AllBlocks.MULTIBLOCK_CONTROLLER)
            .register();

    public static void register() {

    }
}