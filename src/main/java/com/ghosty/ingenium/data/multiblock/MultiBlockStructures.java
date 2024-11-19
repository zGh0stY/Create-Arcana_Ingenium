package com.ghosty.ingenium.data.multiblock;

import com.ghosty.ingenium.registries.ArcanaBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MultiBlockStructures {
    // TODO: Use JSON files to configure structures for better mod integration
    // Declare your structure
    public static MultiBlockStructure TEST_STRUCTURE;

    // Initialize structure after block registration
    public static void initialize() {
        TEST_STRUCTURE = MultiBlockStructureLoader.getStructure("test_structure");
    }
}
