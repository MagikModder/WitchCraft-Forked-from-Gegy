package net.gegy1000.witchcraft.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.gegy1000.witchcraft.common.block.WitchCraftBlocks;
import net.gegy1000.witchcraft.common.entity.WitchCraftEntities;
import net.gegy1000.witchcraft.common.event.CommonEventHandler;
import net.gegy1000.witchcraft.common.item.WitchCraftItems;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void preInit()
    {
        WitchCraftItems.init();
        WitchCraftBlocks.init();
        WitchCraftEntities.init();
        
        CommonEventHandler eventHandler = new CommonEventHandler();
        
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
    
    public void init()
    {
        
    }
}
