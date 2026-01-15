package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.ArcanaIngenium;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AllCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcanaIngenium.MODID);

    public static final RegistryObject<CreativeModeTab> ARCANA_TAB = CREATIVE_MODE_TABS.register("arcana_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(AllBlocks.SPEED_UPPER.get()))
                    .title(Component.translatable("itemGroup.arcana_tab"))
                    .displayItems((parameters, output) -> {
                        // Add all blocks
                        output.accept(AllBlocks.SPEED_UPPER.get());
                        output.accept(AllBlocks.MANA_BATTERY.get());
                        output.accept(AllBlocks.MULTIBLOCK_CONTROLLER.get());

                        output.accept(AllItems.LEYLINE_DETECTOR.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}