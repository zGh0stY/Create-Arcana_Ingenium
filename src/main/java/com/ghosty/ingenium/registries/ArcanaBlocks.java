package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.CreateArcana;
import com.ghosty.ingenium.blocks.kinetics.speedUpper.SpeedUpperBlock;
import com.ghosty.ingenium.blocks.energy.manaBattery.ManaBatteryBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.tags.BlockTags;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ArcanaBlocks {
    private static final CreateRegistrate REGISTRATE = CreateArcana.registrate();

    // Or if you're using regular Registrate:
    // private static final Registrate REGISTRATE = CreateArcana.registrate();

    static {
        // Set creative tab if you have one
        // REGISTRATE.setCreativeTab(AllCreativeTabs.ARCANA_TAB);
    }

    // Register Speed Upper block
    public static final BlockEntry<SpeedUpperBlock> SPEED_UPPER = REGISTRATE
            .block("speed_upper", SpeedUpperBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .properties(p -> p
                    .mapColor(MapColor.METAL)
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .lang("Speed Upper")
            .simpleItem() // Automatically creates and registers item
            .register();

    // Register Mana Battery block
    public static final BlockEntry<ManaBatteryBlock> MANA_BATTERY = REGISTRATE
            .block("mana_battery", ManaBatteryBlock::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(p -> p
                    .mapColor(MapColor.STONE)
                    .strength(2.0F, 5.0F)
                    .requiresCorrectToolForDrops())
            .transform(pickaxeOnly())
            .lang("Mana Battery")
            .simpleItem()
            .register();

    public static void register() {

    }
}