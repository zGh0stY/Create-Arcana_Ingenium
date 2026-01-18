package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.api.energy.ArcanaType;

import java.util.HashSet;

public interface IArcanaSource extends IArcanaCoilNetworkEntity {
    int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<IArcanaSource> visited);

    default int requestArcana(IArcanaStorage requester, int amount, ArcanaType type) {
        return requestArcana(requester, amount, type, new HashSet<>());
    }

    int getPriority();
}
