package com.ghosty.ingenium.blocks.energy.arcanaCoil;

import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.blocks.energy.IArcanaConsumer;
import com.ghosty.ingenium.blocks.energy.IArcanaSource;
import com.ghosty.ingenium.blocks.energy.IArcanaStorage;
import com.ghosty.ingenium.network.NetworkHandler;
import com.ghosty.ingenium.network.ParticleTrailPacket;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ArcanaCoilBlockEntity extends SmartBlockEntity implements IArcanaSource {
    final int SPEED = 5;
    final int MAX_OUTPUT = 20;
    List<BlockPos> consumers = new ArrayList<>();
    List<BlockPos> sources = new ArrayList<>();
    int tickCounter = 0;

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
                BlockPos consumerPos = consumers.remove(0);
                if (level.getBlockEntity(consumerPos) instanceof IArcanaConsumer consumer) {
                    consumer.initiateArcanaRequest(this);
                    consumers.add(consumerPos);
                    return;
                }
                notifyUpdate();
            }
        }
    }

    @Override
    public int requestArcana(IArcanaStorage requester, int amount, ArcanaType type, HashSet<BlockPos> visited) {
        List<BlockPos> toRemove = new ArrayList<>();

        for (BlockPos sourcePos : sources) {
            if (level.getBlockEntity(sourcePos) instanceof IArcanaSource source) {
                if (visited.contains(sourcePos))
                    continue;

                if (requester instanceof IArcanaSource && source == requester)
                    continue;

                visited.add(sourcePos);
                int provided = source.requestArcana(requester, Math.min(amount, MAX_OUTPUT), type, visited);
                if (provided > 0) {
                    if (source instanceof BlockEntity sourceBE) {
                        ParticleTrailPacket particlepacket = new ParticleTrailPacket(sourceBE.getBlockPos(), this.getBlockPos(), type);
                        NetworkHandler.sendToAllNear(particlepacket, this.level, this.worldPosition, 64);
                    }

                    return provided;
                }
            }
            else {
                toRemove.add(sourcePos);
            }
        }

        if (!toRemove.isEmpty()) {
            sources.removeAll(toRemove);
            notifyUpdate();
        }

        return 0;
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        consumers.clear();
        if (tag.contains("CoilConsumers", Tag.TAG_LIST)) {
            ListTag consumersList = tag.getList("CoilConsumers", Tag.TAG_COMPOUND);
            for (Tag item : consumersList) {
                if (item instanceof CompoundTag posTag) {
                    BlockPos pos = NbtUtils.readBlockPos(posTag);
                    consumers.add(pos);
                }
            }
        }

        sources.clear();
        if (tag.contains("CoilSources", Tag.TAG_LIST)) {
            ListTag sourcesList = tag.getList("CoilSources", Tag.TAG_COMPOUND);
            for (Tag item : sourcesList) {
                if (item instanceof CompoundTag posTag) {
                    BlockPos pos = NbtUtils.readBlockPos(posTag);
                    sources.add(pos);
                }
            }
        }

        super.read(tag, clientPacket);
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        ListTag consumersList = new ListTag();
        for (BlockPos consumerPos : consumers) {
            consumersList.add(NbtUtils.writeBlockPos(consumerPos));
        }
        tag.put("CoilConsumers", consumersList);

        ListTag sourcesList = new ListTag();
        for (BlockPos sourcePos : sources) {
            sourcesList.add(NbtUtils.writeBlockPos(sourcePos));
        }
        tag.put("CoilSources", sourcesList);

        super.write(tag, clientPacket);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public void addToNetwork(BlockPos pos) {
        BlockEntity networkMember = level.getBlockEntity(pos);
        if (networkMember instanceof IArcanaConsumer consumer) {
            this.consumers.add(pos);
        }
        if (networkMember instanceof IArcanaSource source) {
            this.sources.add(pos);
        }
        notifyUpdate();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public void setConsumers(List<BlockPos> consumers) {
        this.consumers = consumers;
    }

    public void setSources(List<BlockPos> sources) {
        this.sources = sources;
    }

    public List<BlockPos> getConsumers() {
        return consumers;
    }

    public List<BlockPos> getSources() {
        return sources;
    }
}
