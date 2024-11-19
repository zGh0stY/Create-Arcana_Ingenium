package com.ghosty.ingenium.blocks.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockStructure {
    Block[][][] structure;
    Block controllerBlock;

    public MultiBlockStructure(Block controllerBlock, Block[][][] structure) {
        this.controllerBlock = controllerBlock;
        this.structure = structure;
    }

    /*
    // @Return returns the way the structure is facing
     */
    public int isValid(Level level, BlockPos controllerPos) {
        Vector3i controllerMatrixPos = findController(structure, controllerBlock);
        BlockPos origin = controllerPos.offset(-controllerMatrixPos.x, -controllerMatrixPos.y, -controllerMatrixPos.z);
        Block[][][] currentStructure = structure;

        boolean iterationValid;
        int dir;

        for (dir = 0; dir < 4; dir++) {
            iterationValid = true;
            //printStructure(currentStructure);
            for (int y = 0; y < currentStructure.length; y++) {
                for (int z = 0; z < currentStructure[y].length; z++) {
                    for (int x = 0; x < currentStructure[y][z].length; x++) {
                        BlockPos checkPos = origin.offset(x, y, z); // Offset for correct positioning
                        Block expectedBlock = currentStructure[y][z][x];
                        BlockState actualState = level.getBlockState(checkPos);

                        if (expectedBlock != null && actualState.getBlock() != expectedBlock) {
                            System.out.println("Invalid block at " + checkPos + ", expected " + expectedBlock);
                            if (dir == 3)
                                return -1;
                            else {
                                iterationValid = false;
                                break;
                            }
                        }
                    }
                    // Stop redundant operations
                    if (!iterationValid)
                        break;
                }
                // Stop redundant operations
                if (!iterationValid)
                    break;
            }
            // Stop if last iteration was valid
            if (iterationValid)
                break;

            currentStructure = rotate90Degrees(currentStructure);
        }
        return dir;
    }

    public List<BlockPos> getStructureBlocks(Level level, BlockPos controllerPos, int dir) {
        Vector3i controllerMatrixPos = findController(structure, controllerBlock);
        List<BlockPos> blocks = new ArrayList<>();
        Block[][][] currentStructure = this.structure;

        for (int i = 0; i < dir; i++) {
            currentStructure = rotate90Degrees(structure);
        }

        BlockPos origin = controllerPos.offset(-controllerMatrixPos.x, -controllerMatrixPos.y, -controllerMatrixPos.z);
        for (int y = 0; y < currentStructure.length; y++) {
            for (int z = 0; z < currentStructure[y].length; z++) {
                for (int x = 0; x < currentStructure[y][z].length; x++) {
                    BlockPos checkPos = origin.offset(x, y, z); // Offset for correct positioning
                    blocks.add(checkPos);
                }
            }
        }

        return blocks;
    }

    private Vector3i findController(Block[][][] Structure, Block controller) {
        for (int y = 0; y < structure.length; y++) {
            for (int z = 0; z < structure[y].length; z++) {
                for (int x = 0; x < structure[y][z].length; x++) {
                    Block block = structure[y][z][x];
                    if (block != null && block.equals(controller)) {
                        Vector3i pos = new Vector3i(x, y, z);
                        return pos;
                    }
                }
            }
        }

        return null;
    }

    private Block[][][] rotate90Degrees(Block[][][] structure) {
        int layers = structure.length; // Y-axis (height)
        int rows = structure[0].length; // Z-axis (depth)
        int cols = structure[0][0].length; // X-axis (width)

        // Create a new array for the rotated structure
        Block[][][] rotated = new Block[layers][cols][rows];

        // Transpose and reverse rows for each layer
        for (int y = 0; y < layers; y++) {
            for (int z = 0; z < rows; z++) {
                for (int x = 0; x < cols; x++) {
                    // Transpose: Swap x and z
                    rotated[y][x][rows - 1 - z] = structure[y][z][x]; // Reverse the row (rows - 1 - z)
                }
            }
        }

        return rotated;
    }

    public static void printStructure(Block[][][] structure) {
        for (int y = 0; y < structure.length; y++) {
            System.out.println("Layer " + y + ":");
            for (int z = 0; z < structure[y].length; z++) {
                for (int x = 0; x < structure[y][z].length; x++) {
                    // Replace null blocks with a placeholder
                    Block block = structure[y][z][x];
                    System.out.print((block != null ? block.getName() : "null") + " ");
                }
                System.out.println(); // New line after each row
            }
            System.out.println(); // Extra line between layers
        }
    }
}
