package com.ghosty.ingenium.events;

import com.ghosty.ingenium.blocks.multiblock.MultiBlockControllerEntity;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MultiBlockBreakHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        Level level = (Level) event.getLevel();
        BlockEntity blockEntity = MultiBlockManager.findControllerForBlock(level, pos);

        if (blockEntity instanceof MultiBlockControllerEntity controllerEntity) {
            if (controllerEntity.isPartOfStructure(pos)) {
                controllerEntity.setFormed(false);
                controllerEntity.emptyStructure();

                event.getPlayer().sendSystemMessage(Component.literal("Multi-block structure is invalid!"));
            }
        }
    }
}
