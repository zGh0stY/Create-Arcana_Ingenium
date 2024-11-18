package com.ghosty.ingenium.registries;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.ghosty.ingenium.CreateArcana;
import com.ghosty.ingenium.blocks.energy.manaBattery.ManaBatteryBlock;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlock;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockController;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public class ArcanaBlocks {
    static {
        CreateArcana.REGISTRATE.setCreativeTab(ArcanaCreativeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<SpeedUpperBlock> SPEED_UPPER = CreateArcana.REGISTRATE.block("speed_upper", SpeedUpperBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<ManaBatteryBlock> MANA_BATTERY = CreateArcana.REGISTRATE.block("mana_battery", ManaBatteryBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .simpleItem()
            .register();

    public static final BlockEntry<MultiBlockController> MULTIBLOCK_CONTROLLER = CreateArcana.REGISTRATE.block("multiblock_controller", MultiBlockController::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .simpleItem()
            .register();

    public static void register() {}
}
