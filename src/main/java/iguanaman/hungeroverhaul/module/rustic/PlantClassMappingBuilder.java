package iguanaman.hungeroverhaul.module.rustic;

import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;

/**
 * This class helps with building a Class -> PlantMod map of IGrowables
 */
public class PlantClassMappingBuilder {

    private final List<PlantClassMappingBuilderEntry> entries = new ArrayList<>();


    /**
     * Returns a new instance of {@link PlantClassMappingBuilderEntry} with the provided block's class set
     * @param block the block of which the class will be used
     * @return the entry
     */
    public static PlantClassMappingBuilderEntry entry(Block block) {
        return new PlantClassMappingBuilderEntry(block.getClass());
    }

    /**
     * Adds the entry to the builder
     * @param entry an entry
     * @return the instance of this builder for chaining purposes
     */
    public PlantClassMappingBuilder add(PlantClassMappingBuilderEntry entry) {
        entries.add(entry);
        return this;
    }


    /**
     * Creates a map which consists of a class -> mod mapping
     * @return a newly instanciated map
     */
    public Map<Class<? extends Block>, PlantGrowthModification> getClassBlockMap() {
        HashMap<Class<? extends Block>, PlantGrowthModification> ret = new HashMap<>();

        for (PlantClassMappingBuilderEntry entry : entries) {
            ret.put(entry.clazz,entry.getFusedModification());
        }


        return ret;
    }


    public static class PlantClassMappingBuilderEntry {

        private final Class<? extends Block> clazz;
        private final List<PlantGrowthModification> mods = new ArrayList<>();

        private boolean hasGrowthProbMul = false;
        private float growthProbMul = 0.0f;

        private PlantGrowthModification fusedModification = null;


        private PlantClassMappingBuilderEntry(Class<? extends Block> clazz) {
            this.clazz = clazz;
        }


        /**
         * Adds a new modifier to this entry
         * @param modification the modifier
         * @return the instance of this builder for chaining purposes
         */
        public PlantClassMappingBuilderEntry modifier(PlantGrowthModification modification) {
            mods.add(modification);
            return this;
        }


        /**
         * Sets the groth multiplier which will be set for the resulting PlantGrowthModification of {@link PlantClassMappingBuilderEntry#getFusedModification()}
         * @param mul the multiplier
         * @return the instance of this entry for chaining purposes
         */
        public PlantClassMappingBuilderEntry growthMul(float mul) {
            hasGrowthProbMul = true;
            growthProbMul = mul;

            return this;
        }


        /**
         * Biome-wrapper for {@link PlantClassMappingBuilderEntry#type(net.minecraftforge.common.BiomeDictionary.Type)}
         * @see PlantClassMappingBuilderEntry#type(BiomeDictionary.Type)
         * @return the instance of this entry for chaining purposes
         */
        public PlantClassMappingBuilderEntry biome(Biome biome) {
            Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);

            biomeTypes.forEach(this::type);

            return this;
        }


        /**
         * Biome-wrapper for {@link PlantClassMappingBuilderEntry#type(BiomeDictionary.Type, float)}
         * @return the instance of this entry for chaining purposes
         */
        public PlantClassMappingBuilderEntry biome(Biome biome, float mul) {
            Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);

            biomeTypes.forEach( t -> type(t, mul));

            return this;
        }


        /**
         * Overloaded wrapper for {@link PlantClassMappingBuilderEntry#type(BiomeDictionary.Type, float)} with {@code mul} being defaulted to 1.0f
         * @return the instance of this entry for chaining purposes
         */
        public PlantClassMappingBuilderEntry type(BiomeDictionary.Type type) {
            type(type,1.0f);

            return  this;
        }


        /**
         * Adds a new PlantGrowthModification to this entry with a type -> 1.0f mapping
         * @param type the type
         * @param mul the modifier
         * @return the instance of this entry for chaining purposes
         */
        public PlantClassMappingBuilderEntry type(BiomeDictionary.Type type, float mul) {
            modifier( new PlantGrowthModification().setModifierForType(type, mul));

            return this;
        }




        /**
         * Fuses all the modifiers into one and applies the growthProbMultiplier in case it is set
         *
         * @return this entry, for chaining purposes
         */
        private void fuse() {

            if (fusedModification == null) {
                fusedModification = new PlantGrowthModification();

                for (PlantGrowthModification mod : mods) {
                    fusedModification = fusedModification.merge(mod);

                }

                if (hasGrowthProbMul) {
                    fusedModification.setGrowthTickProbability(growthProbMul);
                }
            }
        }


        /**
         * Fuses the modifiers in case the fused mod isn't already present
         * @return the fused modification
         */
        private PlantGrowthModification getFusedModification() {
            fuse();
            return fusedModification;
        }


    }
}
