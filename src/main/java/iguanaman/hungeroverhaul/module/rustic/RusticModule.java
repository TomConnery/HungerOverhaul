package iguanaman.hungeroverhaul.module.rustic;

import iguanaman.hungeroverhaul.module.growth.PlantGrowthModule;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import rustic.common.blocks.ModBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RusticModule {

    public static Random random = new Random();


    private static Block crop_tomato;
    private static Block crop_chili;
    private static Block crop_grape;
    private static Block crop_berry;



    public static void init() {

        random.setSeed(2 ^ 16 + 2 ^ 8 + (4 * 3 * 271));

        crop_tomato = ModBlocks.TOMATO_CROP;       //temp / humid
        crop_chili = ModBlocks.CHILI_CROP;         //temp / humid / warm
        crop_grape = ModBlocks.GRAPE_STEM;         //humid / warm (need lots of sunlight!)
        crop_berry = ModBlocks.WILDBERRY_BUSH;     //temp / humid



        PlantGrowthModule.registerPlantGrowthModifiers(generatePlantMap());


    }


    /**
     * Simply generates the plantmap and returns the instance
     * @return the plantMap with a plant -> growthMod mapping
     */
    private static Map<Block, PlantGrowthModification> generatePlantMap() {

        Map<Block, PlantGrowthModification> plantMap = new HashMap<>();

        plantMap.put(crop_tomato,
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        plantMap.put(crop_chili,
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)

        );

        plantMap.put(crop_grape,
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        plantMap.put(crop_berry,
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        return plantMap;

    }


}
