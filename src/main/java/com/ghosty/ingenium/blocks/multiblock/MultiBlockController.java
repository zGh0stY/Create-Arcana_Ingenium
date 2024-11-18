package com.ghosty.ingenium.blocks.multiblock;

import com.ghosty.ingenium.registries.ArcanaBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MultiBlockController extends Block implements IBE<MultiBlockControllerEntity> {
    public MultiBlockController(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) { // Ensure this is only done on the server side
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MultiBlockControllerEntity controller) {
                // Call the validation method
                int valid = MultiBlockStructures.TEST_STRUCTURE.isValid(level, pos);

                if (valid >= 0) {
                    player.sendSystemMessage(Component.literal("Multi-block structure is valid!"));
                    controller.setFormed(true);

                    List<BlockPos> blocks = MultiBlockStructures.TEST_STRUCTURE.getStructureBlocks(level, pos, valid);
                    for (BlockPos block : blocks) {
                        controller.addToStructure(block);
                    }
                } else {
                    player.sendSystemMessage(Component.literal("Multi-block structure is invalid!"));
                    controller.setFormed(false);
                    controller.emptyStructure();
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
        if (!level.isClientSide) {
            // Handle structure disassembly
            //MultiBlockStructureValidator.disassembleStructure(level, pos);
        }
    }

    @Override
    public Class<MultiBlockControllerEntity> getBlockEntityClass() {
        return MultiBlockControllerEntity.class;
    }

    @Override
    public BlockEntityType<? extends MultiBlockControllerEntity> getBlockEntityType() {
        return ArcanaBlockEntityTypes.MULTIBLOCK_CONTROLLER.get();
    }
}
