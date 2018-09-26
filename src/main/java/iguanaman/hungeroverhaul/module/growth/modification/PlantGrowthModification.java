package iguanaman.hungeroverhaul.module.growth.modification;

import java.util.HashMap;
import java.util.Map;

import iguanaman.hungeroverhaul.common.config.Config;
import net.minecraftforge.common.BiomeDictionary.Type;

public class PlantGrowthModification {

    public boolean needsSunlight = true;

    public final HashMap<Type, Float> biomeGrowthModifiers = new HashMap<Type, Float>();

    public float growthTickProbability = 0;

    public float genericWrongTypeMultiplier = Config.wrongBiomeRegrowthMultiplier;


    public PlantGrowthModification setNeedsSunlight(boolean needsSunlight) {
        this.needsSunlight = needsSunlight;
        return this;
    }

    public float getModifierForType(Type biomeType) {
        return this.biomeGrowthModifiers.get(biomeType);
    }

    public PlantGrowthModification setModifierForType(Type biomeType, float growthModifier) {
        this.biomeGrowthModifiers.put(biomeType, growthModifier);
        return this;
    }

    public PlantGrowthModification setModifiersForTypes(Map<Type, Float> biomeGrowthModifiers) {
        this.biomeGrowthModifiers.putAll(biomeGrowthModifiers);
        return this;
    }

    public PlantGrowthModification setGrowthTickProbability(float growthTickProbability) {
        this.growthTickProbability = growthTickProbability;
        return this;
    }

    public PlantGrowthModification setGenericWrongTypeMultiplier(float genericWrongTypeMultiplier) {
        this.genericWrongTypeMultiplier = genericWrongTypeMultiplier;
        return this;
    }

    /**
     * Allows for the merging of the biome-list with the one of the other modifier
     *
     * @param other the other modifier
     * @return a new instance with merged biome-list
     */
    public PlantGrowthModification withTypeModifiersOf(PlantGrowthModification other) {
        try {
            PlantGrowthModification ret = (PlantGrowthModification) this.clone();
            ret.setModifiersForTypes(other.biomeGrowthModifiers);

        } catch (CloneNotSupportedException ignored) {}
        return null;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {

        super.clone();

        return new PlantGrowthModification()
                .setGrowthTickProbability(growthTickProbability)
                .setModifiersForTypes(biomeGrowthModifiers)
                .setNeedsSunlight(needsSunlight)
                .setGenericWrongTypeMultiplier(genericWrongTypeMultiplier);
    }





    //---------------------------------
    // default-modifiers for default usage
    //---------------------------------

    //climate-based

    public static final PlantGrowthModification ARID_GROWTH_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(true)
            .setGrowthTickProbability(Config.cropRegrowthMultiplier)
            .setModifierForType(Type.SANDY, 1);

    public static final PlantGrowthModification TROPICAL_GROWTH_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(true)
            .setGrowthTickProbability(Config.cropRegrowthMultiplier)
            .setModifierForType(Type.JUNGLE, 1)
            .setModifierForType(Type.SWAMP, 1);

    public static final PlantGrowthModification HUMID_GROWTH_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(true)
            .setGrowthTickProbability(Config.cropRegrowthMultiplier)
            .setModifierForType(Type.FOREST, 1)
            .setModifierForType(Type.PLAINS, 1);

    public static final PlantGrowthModification COLD_GROWTH_MODIFIER = null;    //TODO add biomes

    public static final PlantGrowthModification POLAR_GROWTH_MODIFIER = null;   //TODO add biomes



    //saplings

    //generic saplings
    public static final PlantGrowthModification GENERIC_SAPLING_MODIFIER = new PlantGrowthModification()
            .setGrowthTickProbability(Config.saplingRegrowthMultiplier);

    //temperate saplings
    public static final PlantGrowthModification TEMPERATE_SAPLING_MODIFIER = new PlantGrowthModification()
            .setGrowthTickProbability(Config.saplingRegrowthMultiplier)
            .setModifierForType(Type.FOREST, 1)
            .setModifierForType(Type.PLAINS, 1);


    //warm saplings
    public static final PlantGrowthModification WARM_SAPLING_MODIFIER = new PlantGrowthModification()
            .setGrowthTickProbability(Config.saplingRegrowthMultiplier)
            .setModifierForType(Type.JUNGLE, 1)
            .setModifierForType(Type.SWAMP, 1);


    //fruits

    //generic fruits
    public static final PlantGrowthModification GENERIC_FRUIT_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(false)
            .setGrowthTickProbability(Config.treeCropRegrowthMultiplier);


    //temperate fruits
    public static final PlantGrowthModification TEMPERATE_FRUIT_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(false)
            .setGrowthTickProbability(Config.treeCropRegrowthMultiplier)
            .setModifierForType(Type.FOREST, 1)
            .setModifierForType(Type.PLAINS, 1);


    //warm fruits
    public static final PlantGrowthModification WARM_FRUIT_MODIFIER = new PlantGrowthModification()
            .setNeedsSunlight(false)
            .setGrowthTickProbability(Config.treeCropRegrowthMultiplier)
            .setModifierForType(Type.JUNGLE, 1)
            .setModifierForType(Type.SWAMP, 1);
}
