package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.blocks.energy.arcanaCoil.ArcanaCoilBlockEntity;

public interface IArcanaConsumer {
    void initiateArcanaRequest(IArcanaSource coil);
}
