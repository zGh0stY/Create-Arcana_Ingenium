package com.ghosty.create_arcana;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

import com.ghosty.create_arcana.kinetics.speedUpper.SpeedUpperBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.material.MapColor;

public class ArcanaBlocks {
    static {
        CreateArcana.REGISTRATE.setCreativeTab(ArcanaCreativeTabs.BASE_CREATIVE_TAB);
    }
    public static final BlockEntry<SpeedUpperBlock> SPEED_UPPER = CreateArcana.REGISTRATE.block("speed_upper", SpeedUpperBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL).forceSolidOn())
            .transform(BlockStressDefaults.setNoImpact())
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static void register() {}
}
