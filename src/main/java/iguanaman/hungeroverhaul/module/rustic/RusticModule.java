package iguanaman.hungeroverhaul.module.rustic;

import iguanaman.hungeroverhaul.module.growth.PlantGrowthModule;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import rustic.common.blocks.ModBlocks;
import static iguanaman.hungeroverhaul.HungerOverhaul.log;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RusticModule {

    public static Random random = new Random();


    private static Block crop_tomato;
    private static Block crop_chili;
    private static Block crop_grape;
    private static Block crop_berry;



    public static void postInit() {

        random.setSeed(2 ^ 16 + 2 ^ 8 + (4 * 3 * 271));

        crop_tomato = ModBlocks.TOMATO_CROP;       //temp / humid
        crop_chili = ModBlocks.CHILI_CROP;         //temp / humid / warm
        crop_grape = ModBlocks.GRAPE_STEM;         //humid / warm (need lots of sunlight!)
        crop_berry = ModBlocks.WILDBERRY_BUSH;     //temp / humid


        Map<Class<? extends Block>, PlantGrowthModification> plantMap = generatePlantMap();
        PlantGrowthModule.registerPlantGrowthModifiersClasses(plantMap);
        log.info(String.format("Registered %d Rustic-plants in the growth module", plantMap.entrySet().size()));
    }


    /**
     * Simply generates the plantmap and returns the instance
     * @return the plantMap with a plant -> growthMod mapping
     */
    private static Map<Class<? extends Block>, PlantGrowthModification> generatePlantMap() {

        Map<Class<? extends Block>, PlantGrowthModification> plantMap = new HashMap<>();

        plantMap.put(crop_tomato.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        plantMap.put(crop_chili.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)

        );

        plantMap.put(crop_grape.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        plantMap.put(crop_berry.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
        );

        return plantMap;

    }


}
