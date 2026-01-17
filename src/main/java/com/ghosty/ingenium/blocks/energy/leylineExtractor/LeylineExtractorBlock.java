package com.ghosty.ingenium.blocks.energy.leylineExtractor;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.api.energy.ArcanaType;
import com.ghosty.ingenium.events.LeylineHandler;
import com.ghosty.ingenium.registries.AllBlockEntityTypes;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeylineExtractorBlock extends KineticBlock implements IBE<LeylineExtractorBlockEntity>, ICogWheel {

    public LeylineExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);

        if (!worldIn.isClientSide) {
            int chunkX = (int) Math.floor((double) pos.getX() / 16);
            int chunkZ = (int) Math.floor((double) pos.getZ() / 16);

            boolean isOnLeyline = LeylineHandler.getChecker().isBorderChunk(chunkX, chunkZ);
            if (isOnLeyline) {
                float biomeTemp = worldIn.getBiome(pos).get().getBaseTemperature();
                ArcanaType arcanaType = LeylineHandler.arcanaTypeFromTemperature(biomeTemp);

                ArcanaIngenium.logger().info("Leyline Extractor placed on leyline!");

                BlockEntity blockEntity = worldIn.getBlockEntity(pos);
                if (blockEntity != null)
                    if (blockEntity instanceof LeylineExtractorBlockEntity extractorBE)
                        extractorBE.setArcanaType(arcanaType);
            }
            else {
                ArcanaIngenium.logger().info("Leyline Extractor placed off leyline.");
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext
                && ((EntityCollisionContext) context).getEntity() instanceof Player)
            return AllShapes.CASING_14PX.get(Direction.DOWN);

        return AllShapes.MECHANICAL_PROCESSOR_SHAPE;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }

    @Override
    public Class<LeylineExtractorBlockEntity> getBlockEntityClass() {
        return LeylineExtractorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LeylineExtractorBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.LEYLINE_EXTRACTOR.get();
    }
}
