package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.CreateArcana;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArcanaCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateArcana.MODID);

    public static final RegistryObject<CreativeModeTab> ARCANA_TAB = CREATIVE_MODE_TABS.register("arcana_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ArcanaBlocks.SPEED_UPPER.get()))
                    .title(Component.translatable("itemGroup.arcana_tab"))
                    .displayItems((parameters, output) -> {
                        // Add all blocks
                        output.accept(ArcanaBlocks.SPEED_UPPER.get());
                        output.accept(ArcanaBlocks.MANA_BATTERY.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}