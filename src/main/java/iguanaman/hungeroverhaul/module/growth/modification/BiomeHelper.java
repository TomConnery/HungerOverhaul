package iguanaman.hungeroverhaul.module.growth.modification;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for providing methods which help with biomes, temperatures
 * and climate in general (go green or go home!)
 */
public class BiomeHelper {

    /**
     * Returns all registered biomes which have a temperature between from and to
     * @param from (inclusive)
     * @param to (inclusive)
     * @return a list of biomes which match the given range
     */
    public static List<Biome> biomesWithTemperatureFromTo(float from, float to) {

        List<Biome> ret = new ArrayList<>();

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getDefaultTemperature() <= to && biome.getDefaultTemperature() >=from) {
                ret.add(biome);
            }
        }

        return ret;
    }


    /**
     * Returns all bioms which are neither hot nor cold and thus per definition temperate
     * @see net.minecraftforge.common.BiomeDictionary
     * @return a list of all temperate biomes
     */
    public static List<Biome> temperateBiomes() {
        List<Biome> ret = new ArrayList<>();

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT)) {
                ret.add(biome);
            }
        }

        return ret;
    }

}
