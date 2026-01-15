package com.ghosty.ingenium.data.multiblock;

import java.util.HashMap;
import java.util.Map;

public enum MultiBlockStructures {
    TEST_STRUCTURE;
    public static Map<MultiBlockStructures, MultiBlockStructure> structures;

    // Initialize structure after block registration
    public static void initialize() {
        structures = new HashMap<>();
        structures.put(TEST_STRUCTURE, MultiBlockStructureLoader.getStructure("test_structure"));
    }

    public MultiBlockStructure get() {
        return structures.get(this);
    }
}
