package com.ghosty.ingenium.registries;

import com.ghosty.ingenium.ArcanaIngenium;
import com.ghosty.ingenium.items.LeylineDetector;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

public class AllItems {
    private static final CreateRegistrate REGISTRATE = ArcanaIngenium.registrate();

    public static final ItemEntry<LeylineDetector> LEYLINE_DETECTOR = REGISTRATE
            .item("leyline_detector", LeylineDetector::new)
            .properties(p -> p.stacksTo(1))
            .register();

    public static void register() {

    }
}
