package iguanaman.hungeroverhaul.module.rustic;

import iguanaman.hungeroverhaul.common.config.Config;
import iguanaman.hungeroverhaul.module.growth.PlantGrowthModule;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import rustic.common.blocks.ModBlocks;

import java.util.HashMap;
import java.util.Map;

import static iguanaman.hungeroverhaul.HungerOverhaul.log;

public class RusticModule {

    //config values
    public final static double CONFIG_GLOBAL_GROWTH_MUL = 1.0;
    public final static double CONFIG_CROP_GROWTH_MUL = 2.0;
    public final static double CONFIG_TOMATO_GROWTH_MUL = 7;
    public final static double CONFIG_GRAPE_STEM_GROWTH_MUL = 12;
    public final static double CONFIG_GRAPE_LEAVES_GROWTH_MUL = 10;
    public final static double CONFIG_CHILI_GROWTH_MUL = 7;
    public final static double CONFIG_BERRY_GROWTH_MUL = 7;

    //block refs
    private static Block crop_tomato;
    private static Block crop_chili;
    private static Block crop_grape_stem;
    private static Block crop_berry;
    private static Block crop_grape_leaves;

    //shortcut vals
    private final static float baseCropGrowthMul = (float) (Config.rusticGlobalGrowthMul * Config.rusticCropGrowthMul);



    public static void postInit() {

        crop_tomato = ModBlocks.TOMATO_CROP;       //temp / humid
        crop_chili = ModBlocks.CHILI_CROP;         //temp / humid / warm
        crop_grape_stem = ModBlocks.GRAPE_STEM;    //humid / warm (need lots of sunlight!)
        crop_berry = ModBlocks.WILDBERRY_BUSH;     //temp / humid
        crop_grape_leaves = ModBlocks.GRAPE_LEAVES;



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
                .setGrowthTickProbability((float) (baseCropGrowthMul * Config.rusticTomatoGrowthMul))
        );

        plantMap.put(crop_chili.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                .setGrowthTickProbability((float) (baseCropGrowthMul * Config.rusticChiliGrowthMul))
        );

        plantMap.put(crop_grape_stem.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                .setGrowthTickProbability((float) (baseCropGrowthMul * Config.rusticGrapeStemGrowthMul))
        );

        plantMap.put(crop_berry.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                .setGrowthTickProbability((float) (baseCropGrowthMul * Config.rusticBerryGrowthMul))
        );

        plantMap.put(crop_grape_leaves.getClass(),
                PlantGrowthModification.WARM_SAPLING_MODIFIER
                .withTypeModifiersOf(PlantGrowthModification.WARM_SAPLING_MODIFIER
                .setGrowthTickProbability((float) (baseCropGrowthMul * Config.rusticGrapeLeavesGrowthMul))
                )
        );

        return plantMap;

    }


}
