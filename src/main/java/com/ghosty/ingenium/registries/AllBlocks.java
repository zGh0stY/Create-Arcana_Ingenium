package com.ghosty.ingenium.registries;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlock;
import com.ghosty.ingenium.blocks.energy.leylineExtractor.LeylineExtractorBlock;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlock;
import com.ghosty.ingenium.blocks.energy.arcanaBattery.ArcanaBatteryBlock;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockController;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.infrastructure.config.CStress;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;

public class AllBlocks {
    private static final CreateRegistrate REGISTRATE = ArcanaIngenium.registrate();

    // Register Speed Upper block
    public static final BlockEntry<SpeedUpperBlock> SPEED_UPPER = REGISTRATE
            .block("speed_upper", SpeedUpperBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .onRegister(block -> BlockStressValues.IMPACTS.register(block, () -> 0.0))
            .register();

    // Register Mana Battery block
    public static final BlockEntry<ArcanaBatteryBlock> ARCANA_BATTERY = REGISTRATE
            .block("arcana_battery", ArcanaBatteryBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .simpleItem()
            .register();

    public static final BlockEntry<LeylineExtractorBlock> LEYLINE_EXTRACTOR = REGISTRATE
            .block("leyline_extractor", LeylineExtractorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .onRegister(block -> BlockStressValues.IMPACTS.register(block, () -> 8.0))
            .register();

    public static final BlockEntry<ArcanaCoilBlock> ARCANA_COIL = REGISTRATE
            .block("arcana_coil", ArcanaCoilBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .simpleItem()
            .register();

    public static final BlockEntry<MultiBlockController> MULTIBLOCK_CONTROLLER = REGISTRATE
            .block("multiblock_controller", MultiBlockController::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .simpleItem()
            .register();

    public static void register() {

    }
}
