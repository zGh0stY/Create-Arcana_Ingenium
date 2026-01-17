package com.ghosty.ingenium.network;

import com.ghosty.ingenium.ArcanaIngenium;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ArcanaIngenium.rl("main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void registerPackets() {
        // Register all packets
        CHANNEL.registerMessage(packetId++, ParticleTrailPacket.class,
                ParticleTrailPacket::encode,
                ParticleTrailPacket::decode,
                ParticleTrailPacket::handle);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }

    public static void sendToPlayer(Object packet, net.minecraft.server.level.ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToAllNear(Object packet, Level level, BlockPos pos, double radius) {
        CHANNEL.send(PacketDistributor.NEAR.with(() ->
                new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(),
                        radius, level.dimension())
        ), packet);
    }
}
