package net.gegy1000.witchcraft;

import net.gegy1000.witchcraft.common.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WitchCraft.modid, name = WitchCraft.name, version = WitchCraft.version)
public class WitchCraft
{
    //Ice staff freeze mobs that are hit freezes water that you walk on
    //Wind/Air staff lets you boost forward (key press). Also blow mobs and blocks away & Ice breath
    //Key press to change mode
    
    public static final String modid = "witchcraft";
    public static final String name = "WitchCraft";
    public static final String version = "0.0.0";
    
    @Instance(WitchCraft.modid)
    public static WitchCraft instance;
    
    @SidedProxy(clientSide = "net.gegy1000.witchcraft.client.proxy.ClientProxy", serverSide = "net.gegy1000.witchcraft.common.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit();
    }
    
    @EventHandler
    public void init(FMLPostInitializationEvent event)
    {
        proxy.init();
    }
}
