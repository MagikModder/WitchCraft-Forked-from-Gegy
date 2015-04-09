package net.gegy1000.witchcraft.client.proxy;

import net.gegy1000.witchcraft.client.render.RenderInvisible;
import net.gegy1000.witchcraft.common.entity.EntityFireRain;
import net.gegy1000.witchcraft.common.entity.EntityFlame;
import net.gegy1000.witchcraft.common.proxy.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    public void preInit()
    {
        super.preInit();
        
        RenderingRegistry.registerEntityRenderingHandler(EntityFlame.class, new RenderInvisible());
        RenderingRegistry.registerEntityRenderingHandler(EntityFireRain.class, new RenderInvisible());
    }
    
    public void init()
    {
        super.init();
    }
}
