package com.ghosty.ingenium.blocks.energy.arcanaCoil;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.blocks.energy.IArcanaCoilNetworkEntity;
import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.blocks.energy.IArcanaStorage;
import com.ghosty.ingenium.network.NetworkHandler;
import com.ghosty.ingenium.network.ParticleTrailPacket;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ArcanaCoilBlockEntity extends SmartBlockEntity implements IArcanaSource {
    final int SPEED = 5;
    final int MAX_OUTPUT = 20;
    List<IArcanaConsumer> consumers = new ArrayList<>();
    List<IArcanaSource> sources = new ArrayList<>();
    int tickCounter = 0;

    List<ArcanaCoilBlockEntity> networkCoils = new ArrayList<>();

    public ArcanaCoilBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide())
            return;

        tickCounter++;
        if (tickCounter == SPEED) {
            tickCounter = 0;

            if (!consumers.isEmpty()) {
                IArcanaConsumer consumer = consumers.remove(0);
                consumer.initiateArcanaRequest(this);
                consumers.add(consumer);
            }
        }
    }

    @Override
    public int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<IArcanaSource> visited) {
        for (IArcanaSource source : sources) {
            if (visited.contains(source))
                continue;

            visited.add(source);
            int provided = source.requestArcana(requester, Math.min(amount, MAX_OUTPUT), type, visited);
            if (provided > 0) {
                if (source instanceof BlockEntity sourceBE) {
                    ParticleTrailPacket particlepacket = new ParticleTrailPacket(sourceBE.getBlockPos(), this.getBlockPos(), type);
                    NetworkHandler.sendToAllNear(particlepacket, this.level, this.worldPosition, 64);
                }

                return provided;
            }
        }

        return 0;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public void setConsumers(List<IArcanaConsumer> consumers) {
        this.consumers = consumers;
    }

    public void setSources(List<IArcanaSource> sources) {
        this.sources = sources;
    }

    public List<IArcanaConsumer> getConsumers() {
        return consumers;
    }

    public List<IArcanaSource> getSources() {
        return sources;
    }

    public void removeFromNetwork(IArcanaCoilNetworkEntity networkMember) {
        if (networkMember instanceof IArcanaConsumer consumer) {
            this.consumers.remove(consumer);
        }
        if (networkMember instanceof IArcanaSource source) {
            this.sources.remove(source);
        }
    }

    public void addToNetwork(IArcanaCoilNetworkEntity networkMember) {
        if (networkMember instanceof IArcanaConsumer consumer) {
            this.consumers.add(consumer);
        }
        if (networkMember instanceof IArcanaSource source) {
            this.sources.add(source);
        }
    }

    @Override
    public List<ArcanaCoilBlockEntity> getCoils() {
        return networkCoils;
    }
}
