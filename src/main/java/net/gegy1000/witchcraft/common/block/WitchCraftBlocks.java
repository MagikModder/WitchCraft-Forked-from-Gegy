package net.gegy1000.witchcraft.common.block;

import net.gegy1000.witchcraft.WitchCraft;
import net.gegy1000.witchcraft.common.tab.WitchCraftTabs;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class WitchCraftBlocks
{
    private static final String modid = WitchCraft.modid;
    
    public static void init()
    {
        
    }
    
    private static void registerBlocks(Block block, String name)
    {
        String unlocalizedName = name.toLowerCase().replaceAll(" ", "_").replaceAll("'", "");
        
        block.setBlockName(unlocalizedName);
        block.setBlockTextureName(modid + ":" + unlocalizedName);
        block.setCreativeTab(WitchCraftTabs.witchcraft);
        
        GameRegistry.registerBlock(block, unlocalizedName);
    }
}
