package net.gegy1000.witchcraft.common.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.gegy1000.witchcraft.WitchCraft;
import net.gegy1000.witchcraft.common.tab.WitchCraftTabs;
import net.minecraft.item.Item;

public class WitchCraftItems
{
    private static final String modid = WitchCraft.modid;

    public static Item fireStaff;
    public static Item iceStaff;
    
    public static void init()
    {
        fireStaff = new ItemFireStaff();
        iceStaff = new ItemIceStaff();
        
        registerItem(fireStaff, "Fire Staff");
        registerItem(iceStaff, "Ice Staff");
    }
    
    private static void registerItem(Item item, String name)
    {
        String unlocalizedName = name.toLowerCase().replaceAll(" ", "_").replaceAll("'", "");
        
        item.setUnlocalizedName(unlocalizedName);
        item.setTextureName(modid + ":" + unlocalizedName);
        item.setCreativeTab(WitchCraftTabs.witchcraft);
        
        GameRegistry.registerItem(item, unlocalizedName);
    }
}
