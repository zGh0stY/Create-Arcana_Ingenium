package com.ghosty.ingenium.blocks.energy;

import com.ghosty.ingenium.api.energy.ArcanaType;
import net.minecraft.core.BlockPos;

import java.util.HashSet;

public interface IArcanaSource {
    int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<BlockPos> visited);

    default int requestArcana(IArcanaStorage requester, int amount, ArcanaType type) {
        return requestArcana(requester, amount, type, new HashSet<>());
    }

    int getPriority();
}
