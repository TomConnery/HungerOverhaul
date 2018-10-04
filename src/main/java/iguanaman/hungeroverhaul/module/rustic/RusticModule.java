package iguanaman.hungeroverhaul.module.rustic;

import iguanaman.hungeroverhaul.common.config.Config;
import iguanaman.hungeroverhaul.module.growth.PlantGrowthModule;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import net.minecraftforge.common.BiomeDictionary;
import rustic.common.blocks.ModBlocks;
import rustic.common.blocks.crops.Herbs;

import java.util.HashMap;
import java.util.Map;

import static iguanaman.hungeroverhaul.HungerOverhaul.log;

public class RusticModule {
    //------------------------------
    //config values
    //------------------------------

    //global/categorical adjustments
    public final static float CONFIG_GLOBAL_GROWTH_MUL = 1.0f;
    public final static float CONFIG_CROP_GROWTH_MUL = 2.0f;
    public final static float CONFIG_HERB_GROWTH_MUL = 2.0f;

    //crop-specific adjustments
    public final static float CONFIG_TOMATO_GROWTH_MUL = 7;
    public final static float CONFIG_GRAPE_STEM_GROWTH_MUL = 12;
    public final static float CONFIG_GRAPE_LEAVES_GROWTH_MUL = 10;
    public final static float CONFIG_CHILI_GROWTH_MUL = 7;
    public final static float CONFIG_BERRY_GROWTH_MUL = 7;

    //herb-specific adjustments
    public final static float CONFIG_COHOSH_GROWTH_MUL = 1.0f;
    public final static float CONFIG_GINGSENG_GROWTH_MUL = 1.0f;
    public final static float CONFIG_ALOE_VERA_GROWTH_MUL = 1.0f;
    public final static float CONFIG_BLOOD_ORCHID_GROWTH_MUL = 1.0f;
    public final static float CONFIG_CHAMOMILE_GROWTH_MUL = 1.0f;
    public final static float CONFIG_CLOUDSBLUFF_GROWTH_MUL = 1.0f;
    public final static float CONFIG_CORE_ROOT_GROWTH_MUL = 1.0f;
    public final static float CONFIG_DEATHSTALK_MUSHROOM_GROWTH_MUL = 1.0f;
    public final static float CONFIG_HORSETAIL_GROWTH_MUL = 1.0f;
    public final static float CONFIG_MARSH_MALLOW_GROWTH_MUL = 1.0f;
    public final static float CONFIG_MOONCAP_MUSHROOM_GROWTH_MUL = 1.0f;
    public final static float CONFIG_WIND_THISTLE_GROWTH_MUL = 1.0f;


    //block refs
    private static Block crop_tomato;
    private static Block crop_chili;
    private static Block crop_grape_stem;
    private static Block crop_berry;
    private static Block crop_grape_leaves;

    private static Block herb_cohosh;
    private static Block herb_gingseng;
    private static Block herb_aloe_vera;
    private static Block herb_blood_orchid;
    private static Block herb_chamomile;
    private static Block herb_cloudsbluff;
    private static Block herb_core_root;
    private static Block herb_deathstalk_mushroom;
    private static Block herb_horsetail;
    private static Block herb_marsh_mallow;
    private static Block herb_mooncap_mushroom;
    private static Block herb_wind_thistle;

    //shortcut vals
    private final static float baseCropGrowthMul = Config.rusticGlobalGrowthMul * Config.rusticCropGrowthMul;
    private final static float baseHerbGrowthMul = Config.rusticGlobalGrowthMul * Config.rusticGlobalHerbGrowthModifier;


    public static void postInit() {

        //crops
        crop_tomato = ModBlocks.TOMATO_CROP;       //temp / humid
        crop_chili = ModBlocks.CHILI_CROP;         //temp / humid / warm
        crop_grape_stem = ModBlocks.GRAPE_STEM;    //humid / warm (need lots of sunlight!)
        crop_berry = ModBlocks.WILDBERRY_BUSH;     //temp / humid
        crop_grape_leaves = ModBlocks.GRAPE_LEAVES;

        //herbs
        herb_cohosh = Herbs.COHOSH;
        herb_gingseng = Herbs.GINSENG_CROP;
        herb_aloe_vera = Herbs.ALOE_VERA;
        herb_blood_orchid = Herbs.BLOOD_ORCHID;
        herb_chamomile = Herbs.CHAMOMILE;
        herb_cloudsbluff = Herbs.CLOUDSBLUFF_CROP;
        herb_core_root = Herbs.CORE_ROOT_CROP;
        herb_deathstalk_mushroom = Herbs.DEATHSTALK;
        herb_horsetail = Herbs.HORSETAIL;
        herb_marsh_mallow = Herbs.MARSH_MALLOW_CROP;
        herb_mooncap_mushroom = Herbs.MOONCAP;
        herb_wind_thistle = Herbs.WIND_THISTLE;


        Map<Class<? extends Block>, PlantGrowthModification> plantMap = generatePlantMap();
        PlantGrowthModule.registerPlantGrowthModifiersClasses(plantMap);
        log.info(String.format("Registered %d Rustic-plants in the growth module", plantMap.entrySet().size()));
    }


    /**
     * Simply generates the plantmap and returns the instance
     *
     * @return the plantMap with a plant -> growthMod mapping
     */
    private static Map<Class<? extends Block>, PlantGrowthModification> generatePlantMap() {

        Map<Class<? extends Block>, PlantGrowthModification> plantMap = new HashMap<>();


        //plants
        plantMap.put(crop_tomato.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                        .merge(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                        .setGrowthTickProbability(baseCropGrowthMul * Config.rusticTomatoGrowthMul)
        );

        plantMap.put(crop_chili.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                        .merge(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                        .setGrowthTickProbability(baseCropGrowthMul * Config.rusticChiliGrowthMul)
        );

        plantMap.put(crop_grape_stem.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                        .merge(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                        .setGrowthTickProbability(baseCropGrowthMul * Config.rusticGrapeStemGrowthMul)
        );

        plantMap.put(crop_berry.getClass(),
                PlantGrowthModification.HUMID_GROWTH_MODIFIER
                        .merge(PlantGrowthModification.TROPICAL_GROWTH_MODIFIER)
                        .setGrowthTickProbability(baseCropGrowthMul * Config.rusticBerryGrowthMul)
        );

        plantMap.put(crop_grape_leaves.getClass(),
                PlantGrowthModification.WARM_SAPLING_MODIFIER
                        .merge(PlantGrowthModification.WARM_SAPLING_MODIFIER
                                .setGrowthTickProbability(baseCropGrowthMul * Config.rusticGrapeLeavesGrowthMul)
                        )
        );

        // for questions regarding the biomes, see https://github.com/the-realest-stu/Rustic/wiki/Herbs
        PlantClassMappingBuilder builder = new PlantClassMappingBuilder();

        builder.add(
                PlantClassMappingBuilder.entry(herb_cohosh)
                        .type(BiomeDictionary.Type.FOREST)
                        .growthMul(baseHerbGrowthMul * Config.rusticCohoshGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_gingseng)
                        .type(BiomeDictionary.Type.PLAINS)
                        .type(BiomeDictionary.Type.FOREST)
                        .growthMul(baseHerbGrowthMul * Config.rusticGingsengGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_aloe_vera)
                        .type(BiomeDictionary.Type.SANDY)
                        .type(BiomeDictionary.Type.SAVANNA)
                        .type(BiomeDictionary.Type.MESA)
                        .growthMul(baseHerbGrowthMul * Config.rusticAloeVeraGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_blood_orchid)
                        .type(BiomeDictionary.Type.JUNGLE)
                        .growthMul(baseHerbGrowthMul * Config.rusticBloodOrchidGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_chamomile)
                        .type(BiomeDictionary.Type.PLAINS)
                        .type(BiomeDictionary.Type.FOREST)
                        .type(BiomeDictionary.Type.SWAMP)
                        .growthMul(baseHerbGrowthMul * Config.rusticChamomileGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_cloudsbluff)
                        .type(BiomeDictionary.Type.MOUNTAIN)
                        .growthMul(baseHerbGrowthMul * Config.rusticCloudsbluffGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_core_root)
                        //todo: add darkness requirement
                        .growthMul(baseHerbGrowthMul * Config.rusticCoreRootGrowthMul)

        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_deathstalk_mushroom)
                        .type(BiomeDictionary.Type.NETHER)
                        .growthMul(baseHerbGrowthMul * Config.rusticDeathstalkMushroomGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_horsetail)
                        .type(BiomeDictionary.Type.JUNGLE)
                        .type(BiomeDictionary.Type.SWAMP)
                        .type(BiomeDictionary.Type.PLAINS)
                        .type(BiomeDictionary.Type.FOREST)
                        .growthMul(baseHerbGrowthMul * Config.rusticHorsetailGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_marsh_mallow)
                        .type(BiomeDictionary.Type.JUNGLE)
                        .type(BiomeDictionary.Type.SWAMP)
                        .growthMul(baseHerbGrowthMul * Config.rusticMarshMallowGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_mooncap_mushroom)
                        .type(BiomeDictionary.Type.JUNGLE)
                        //todo: add darkness requirement
                        .growthMul(baseHerbGrowthMul * Config.rusticMooncapMushroomGrowthMul)
        );

        builder.add(
                PlantClassMappingBuilder.entry(herb_wind_thistle)
                        .type(BiomeDictionary.Type.MOUNTAIN)
                        .type(BiomeDictionary.Type.PLAINS)
                        .growthMul(baseHerbGrowthMul * Config.rusticWindThistleGrowthMul)
        );

        plantMap.putAll(builder.getClassBlockMap());

        return plantMap;

    }


}
