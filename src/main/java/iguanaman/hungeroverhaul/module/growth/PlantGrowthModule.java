package iguanaman.hungeroverhaul.module.growth;

import iguanaman.hungeroverhaul.common.RandomHelper;
import iguanaman.hungeroverhaul.common.config.Config;
import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;


public class PlantGrowthModule
{
    private static HashMap<Class<? extends Block>, PlantGrowthModification> plantGrowthModificationsByBlockClass = new HashMap<>();

    private static HashMap<Block, PlantGrowthModification> plantGrowthModificationsByBlock = new HashMap<>();
    
    public static void registerPlantGrowthModifier(Class<? extends Block> blockClass, PlantGrowthModification growthModification)
    {
        plantGrowthModificationsByBlockClass.put(blockClass, growthModification);
    }

    public static void registerPlantGrowthModifier(Block block, PlantGrowthModification growthModification)
    {
        plantGrowthModificationsByBlock.put(block, growthModification);
    }

    public static PlantGrowthModification getPlantGrowthModification(Block block)
    {
        if (block == null)
        {
            return null;
        }

        PlantGrowthModification growthModification = plantGrowthModificationsByBlock.get(block);

        if (growthModification != null)
        {
            return growthModification;
        }
        else
        {
            return getPlantGrowthModification(block.getClass());
        }
    }

    public static PlantGrowthModification getPlantGrowthModification(Class<? extends Block> blockClass)
    {
        // get exact matches first
        PlantGrowthModification growthModifier = plantGrowthModificationsByBlockClass.get(blockClass);
        // as a backup, check instanceof
        if (growthModifier == null)
        {
            for (Map.Entry<Class<? extends Block>, PlantGrowthModification> entry : plantGrowthModificationsByBlockClass.entrySet())
            {
                if (entry.getKey().isAssignableFrom(blockClass))
                {
                    return entry.getValue();
                }

                if (entry.getKey() == blockClass) {
                    return entry.getValue();
                }
            }
        }
        return growthModifier;
    }

    /**
     * Map-wrapper for {@link #registerPlantGrowthModifier(Block, PlantGrowthModification)}
     * @param mods the modifiers to register
     */
    public static void registerPlantGrowthModifiers(Map<Block, PlantGrowthModification> mods) {
        for (Map.Entry<Block, PlantGrowthModification> entry : mods.entrySet()) {
            registerPlantGrowthModifier(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Map-wrapper for {@link #registerPlantGrowthModifier(Class, PlantGrowthModification)}
     * @param mods the modifiers to register
     */
    public static void registerPlantGrowthModifiersClasses(Map<Class<? extends Block>, PlantGrowthModification> mods) {
        for (Map.Entry<Class<? extends Block>, PlantGrowthModification> entry : mods.entrySet()) {
            registerPlantGrowthModifier(entry.getKey(), entry.getValue());
        }
    }

    @SubscribeEvent
    public void allowGrowthTick(Pre event)
    {

        Class<? extends Block> blockClass = event.getState().getBlock().getClass();

        PlantGrowthModification growthModification = getPlantGrowthModification(blockClass);

        if (growthModification == null)
        {
            return;
        }

        // sunlight
        float sunlightModifier = !growthModification.needsSunlight || (growthModification.needsSunlight && event.getWorld().isDaytime() && event.getWorld().canSeeSky(event.getPos())) ? 1 : Config.noSunlightRegrowthMultiplier;
        if (sunlightModifier == 0)
        {
            event.setResult(Result.DENY);
            return;
        }

        // biome
        float biomeModifier = growthModification.genericWrongTypeMultiplier;
        if (!growthModification.biomeGrowthModifiers.isEmpty())
        {
            biomeModifier = Config.wrongBiomeRegrowthMultiplier;
            Biome biome = event.getWorld().getBiome(event.getPos());

            for (BiomeDictionary.Type type : BiomeDictionary.getTypes(biome))
            {
                if (growthModification.biomeGrowthModifiers.containsKey(type))
                {
                    biomeModifier = growthModification.getModifierForType(type);
                    break;
                }
            }
        }
        if (biomeModifier == 0)
        {
            event.setResult(Result.DENY);
            return;
        }

        // random
        if (RandomHelper.nextFloat(event.getWorld().rand, growthModification.growthTickProbability * biomeModifier * sunlightModifier) >= 1)
        {
            event.setResult(Result.DENY);
            return;
        }

        // still go through with the default conditionals
        event.setResult(Result.DEFAULT);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlantGrowthModule.class.getSimpleName() + "[", "]")
                .toString();
    }
}
