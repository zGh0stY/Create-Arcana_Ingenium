package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.blocks.energy.arcanaBattery.ArcanaBatteryBlockEntity;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;
import com.ghosty.ingenium.blocks.energy.leylineExtractor.LeylineExtractorBlockEntity;
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

    public static final BlockEntityEntry<ArcanaBatteryBlockEntity> ARCANA_BATTERY = REGISTRATE
            .blockEntity("mana_battery", ArcanaBatteryBlockEntity::new)
            .validBlocks(AllBlocks.ARCANA_BATTERY)
            .register();

    public static final BlockEntityEntry<LeylineExtractorBlockEntity> LEYLINE_EXTRACTOR = REGISTRATE
            .blockEntity("leyline_extractor", LeylineExtractorBlockEntity::new)
            .validBlocks(AllBlocks.LEYLINE_EXTRACTOR)
            .register();

    public static final BlockEntityEntry<ArcanaCoilBlockEntity> ARCANA_COIL = REGISTRATE
            .blockEntity("arcana_coil", ArcanaCoilBlockEntity::new)
            .validBlocks(AllBlocks.ARCANA_COIL)
            .register();

    public static final BlockEntityEntry<MultiBlockControllerEntity> MULTIBLOCK_CONTROLLER = REGISTRATE
            .blockEntity("multiblock_controller", MultiBlockControllerEntity::new)
            .validBlocks(AllBlocks.MULTIBLOCK_CONTROLLER)
            .register();

    public static void register() {

    }
}