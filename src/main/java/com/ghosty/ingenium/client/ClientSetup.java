package com.ghosty.ingenium.client;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.items.LeylineDetector;
import com.ghosty.ingenium.registries.AllItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ArcanaIngenium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerItemProperties();
        });
    }

    private static void registerItemProperties() {
        ItemProperties.register(
                AllItems.LEYLINE_DETECTOR.get(),
                ArcanaIngenium.rl("leyline_proximity"),
                (stack, world, entity, seed) -> {
                    if (entity == null) return 0.0f;

                    return LeylineDetector.calculateProximityToLeyline(
                            entity.position().x,
                            entity.position().z,
                            8
                    );
                }
        );
    }
}