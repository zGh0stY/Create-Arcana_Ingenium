package com.ghosty.ingenium.data.multiblock;

public enum MultiBlockStructureType {
    TEST_STRUCTURE;

    private MultiBlockStructure structure;

    public MultiBlockStructure get() {
        return this.structure;
    }

    public void set(MultiBlockStructure structure) {
        this.structure = structure;
    }
}
