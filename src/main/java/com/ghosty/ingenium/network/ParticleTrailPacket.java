package com.ghosty.ingenium.network;

import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.client.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticleTrailPacket {
    private final BlockPos from;
    private final BlockPos to;
    private final int color;
    private final int count;
    private final float speed;

    // For custom color
    public ParticleTrailPacket(BlockPos from, BlockPos to, int color, int count, float speed) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.count = count;
        this.speed = speed;
    }

    // For ArcanaType
    public ParticleTrailPacket(BlockPos from, BlockPos to, ArcanaType type) {
        this.from = from;
        this.to = to;
        this.color = type.getColor();
        this.count = 20;
        this.speed = 0.02f;
    }

    public ParticleTrailPacket(FriendlyByteBuf buf) {
        this.from = buf.readBlockPos();
        this.to = buf.readBlockPos();
        this.color = buf.readInt();
        this.count = buf.readInt();
        this.speed = buf.readFloat();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(from);
        buf.writeBlockPos(to);
        buf.writeInt(color);
        buf.writeInt(count);
        buf.writeFloat(speed);
    }

    public static ParticleTrailPacket decode(FriendlyByteBuf buf) {
        BlockPos from = buf.readBlockPos();
        BlockPos to = buf.readBlockPos();
        int color = buf.readInt();
        int count = buf.readInt();
        float speed = buf.readFloat();
        return new ParticleTrailPacket(from, to, color, count, speed);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // SAFE: This runs on the appropriate thread (client thread)
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                if (mc.level != null) {
                    ParticleUtil.spawnParticleTrail(mc.level, from, to, color, count, speed);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
