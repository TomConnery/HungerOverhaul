package iguanaman.hungeroverhaul.module.growth;

import iguanaman.hungeroverhaul.module.growth.modification.PlantGrowthModification;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantGrowthModuleTest {

    @BeforeAll
    static void startMinecraft() {

    }

    @Test
    void registerPlantGrowthModifier() {

        //registering block classes results in a unique class -> mod association, meaning
        //we can use pointer-equality checks


        Block a = new Block(Material.AIR);

        TestBlockClass b = new TestBlockClass(Material.AIR);

        //using anonymous classes to simulate custom classes. the methods should be able to
        Block c = new Block(Material.AIR) {

        };

        //dummy mod
        PlantGrowthModification modA = new PlantGrowthModification();
        PlantGrowthModification modB = new PlantGrowthModification();
        PlantGrowthModification modC = new PlantGrowthModification();


        Map<Class<? extends Block>, PlantGrowthModification> classMap = new HashMap<>();

        classMap.put(a.getClass(),modA);
        classMap.put(b.getClass(),modB);
        classMap.put(c.getClass(),modC);

        PlantGrowthModule.registerPlantGrowthModifiersClasses(classMap);

        PlantGrowthModification mod = PlantGrowthModule.getPlantGrowthModification(a.getClass());
        assertEquals(modA,mod);

        mod = PlantGrowthModule.getPlantGrowthModification(b.getClass());
        assertEquals(modB,mod);

        mod = PlantGrowthModule.getPlantGrowthModification(c.getClass());
        assertEquals(modC, mod);

    }

    private class TestBlockClass extends Block{
        public TestBlockClass(Material materialIn) {
            super(null);
        }
    }
}