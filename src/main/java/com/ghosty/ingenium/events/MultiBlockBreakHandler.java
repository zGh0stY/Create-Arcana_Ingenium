package com.ghosty.ingenium.events;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockController;
import com.ghosty.ingenium.blocks.multiblock.MultiBlockControllerEntity;
import com.ghosty.ingenium.data.multiblock.MultiBlockManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MultiBlockBreakHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        Level level = (Level) event.getLevel();

        MultiBlockManager manager = MultiBlockManager.get(level);

        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof MultiBlockController controller) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MultiBlockControllerEntity controllerEntity) {
                controllerEntity.setFormed(false);
                controllerEntity.emptyStructure();
            }

            event.getPlayer().sendSystemMessage(Component.literal("Multi-block structure is invalid!"));
        }
        else if (manager.isStructureBlock(pos)) {
            BlockPos controllerPos = manager.getControllerForBlock(pos);
            BlockEntity entity = level.getBlockEntity(controllerPos);

            if (entity instanceof MultiBlockControllerEntity controller) {
                controller.setFormed(false);
                controller.emptyStructure();
            }

            event.getPlayer().sendSystemMessage(Component.literal("Multi-block structure is invalid!"));
        }
    }
}
