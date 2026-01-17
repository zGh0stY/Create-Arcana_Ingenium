package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.api.energy.ArcanaType;

public interface IArcanaSource {
    int requestArcana(IArcanaStorage requester, int amount, ArcanaType type);

    int getPriority();
}
