package com.ghosty.ingenium.data.multiblock;

import com.google.gson.Gson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultiBlockStructureLoader {
    private static final Gson GSON = new Gson();
    private static final Map<String, MultiBlockStructure> STRUCTURES = new HashMap<>();

    public static void loadStructures(ResourceManager resourceManager) {
        // Clear existing structures
        STRUCTURES.clear();
        System.out.println("Loading Multiblock Structures!");

        // Load all JSON files from the multiblocks directory
        resourceManager.listResources("multiblock", path -> path.getPath().endsWith(".json")).forEach((resourceLocation, resource) -> {
            try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                MultiBlockStructureData data = GSON.fromJson(reader, MultiBlockStructureData.class);

                // Convert JSON data to a MultiBlockStructure
                String structureName = resourceLocation.getPath().replace("multiblock/", "").replace(".json", "");
                Block controllerBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(data.getController()));
                Block[][][] layers = parseLayers(data.getLayers());
                System.out.println("Loading new structure:");
                System.out.println(structureName);
                System.out.println(controllerBlock);
                System.out.println(Arrays.deepToString(layers));

                STRUCTURES.put(structureName, new MultiBlockStructure(controllerBlock, layers));
            } catch (Exception e) {
                System.err.println("Failed to load multiblock structure: " + resourceLocation);
                e.printStackTrace();
            }
        });
    }

    private static Block[][][] parseLayers(String[][][] layers) {
        Block[][][] result = new Block[layers.length][][];
        for (int i = 0; i < layers.length; i++) {
            result[i] = new Block[layers[i].length][];
            for (int j = 0; j < layers[i].length; j++) {
                result[i][j] = new Block[layers[i][j].length];
                for (int k = 0; k < layers[i][j].length; k++) {
                    result[i][j][k] = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(layers[i][j][k]));
                }
            }
        }
        return result;
    }

    public static MultiBlockStructure getStructure(String name) {
        return STRUCTURES.get(name);
    }
}

