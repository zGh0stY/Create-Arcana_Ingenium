package com.ghosty.ingenium;

import com.ghosty.ingenium.data.multiblock.MultiBlockStructureLoader;
import com.ghosty.ingenium.data.multiblock.MultiBlockStructures;
import com.ghosty.ingenium.events.MultiBlockBreakHandler;
import com.ghosty.ingenium.events.LeylineHandler;
import com.ghosty.ingenium.registries.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArcanaIngenium.MODID)
public class ArcanaIngenium
{
    public static final String MODID = "ingenium";
    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArcanaIngenium(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        REGISTRATE.registerEventListeners(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(MultiBlockBreakHandler.class);
        MinecraftForge.EVENT_BUS.register(LeylineHandler.class);

        AllBlocks.register();
        AllItems.register();
        AllBlockEntityTypes.register();
        AllCreativeTabs.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        MultiBlockStructureLoader.loadStructures(Minecraft.getInstance().getResourceManager());
        MultiBlockStructures.initialize();
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static Logger logger() {
        return LOGGER;
    }
}
