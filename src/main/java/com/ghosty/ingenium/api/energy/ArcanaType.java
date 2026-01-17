package com.ghosty.ingenium.api.energy;

public enum ArcanaType {
    MANA(0x4287f5), PRANA(0x37f037), AURA(0xe61e2e);

    private final int color;

    ArcanaType(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
