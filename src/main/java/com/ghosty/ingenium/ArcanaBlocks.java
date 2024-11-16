package com.ghosty.ingenium;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.ghosty.ingenium.kinetics.speedUpper.SpeedUpperBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

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

    public static void register() {}
}
