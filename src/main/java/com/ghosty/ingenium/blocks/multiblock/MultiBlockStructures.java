package com.ghosty.ingenium.blocks.multiblock;

import com.ghosty.ingenium.registries.ArcanaBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class MultiBlockStructures {
    // TODO: Use JSON files to configure structures for better mod integration
    // Declare your structure
    public static MultiBlockStructure TEST_STRUCTURE;

    // Initialize structure after block registration
    public static void initialize() {
        TEST_STRUCTURE = new MultiBlockStructure(
                ArcanaBlocks.MULTIBLOCK_CONTROLLER.get(),
                new Block[][][]{
                        // Layer 1
                        {
                                {Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN},
                                {Blocks.OBSIDIAN, ArcanaBlocks.MULTIBLOCK_CONTROLLER.get(), Blocks.OBSIDIAN},
                                {Blocks.OBSIDIAN, Blocks.OBSIDIAN, Blocks.OBSIDIAN}
                        },
                        // Layer 2
                        {
                                {Blocks.AIR, Blocks.AIR, Blocks.AIR},
                                {Blocks.OBSIDIAN, Blocks.AIR, Blocks.OBSIDIAN},
                                {Blocks.AIR, Blocks.AIR, Blocks.AIR}
                        }
                }
        );
    }
}
