package net.gegy1000.witchcraft.common.entity;

import net.gegy1000.witchcraft.WitchCraft;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class WitchCraftEntities
{
    public static void init()
    {
        registerEntity(EntityFlame.class, "flame", 20, 10, true);
        registerEntity(EntityFireRain.class, "fire_rain", 20, 10, true);
    }
    
    private static void registerEntityWithEgg(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int primary, int secondary)
    {
        name = WitchCraft.modid + name;
        
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, name, id, primary, secondary);
        EntityRegistry.registerModEntity(entityClass, name, id, WitchCraft.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }
    
    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates)
    {
        name = WitchCraft.modid + name;
        
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, name, id);
        EntityRegistry.registerModEntity(entityClass, name, id, WitchCraft.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }
}
