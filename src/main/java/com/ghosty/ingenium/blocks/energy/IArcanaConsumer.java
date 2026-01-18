package com.ghosty.ingenium.blocks.energy;

public interface IArcanaConsumer extends IArcanaCoilNetworkEntity {
    void initiateArcanaRequest(IArcanaSource coil);
}
