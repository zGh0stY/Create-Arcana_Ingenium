package com.ghosty.create_arcana;

import com.ghosty.create_arcana.kinetics.speedUpper.SpeedUpperBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.gearbox.GearboxRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftInstance;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ArcanaBlockEntityTypes {
    public static final BlockEntityEntry<SpeedUpperBlockEntity> SPEED_UPPER = CreateArcana.REGISTRATE
            .blockEntity("speed_upper", SpeedUpperBlockEntity::new)
            .instance(() -> SplitShaftInstance::new)
            .validBlocks(ArcanaBlocks.SPEED_UPPER)
            .renderer(() -> SplitShaftRenderer::new)
            .register();

    public static void register() {}
}
