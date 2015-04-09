package net.gegy1000.witchcraft.common.tab;

import net.gegy1000.witchcraft.common.item.WitchCraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabWitchCraft extends CreativeTabs
{
    public CreativeTabWitchCraft()
    {
        super("witchcraft");
    }

    @Override
    public Item getTabIconItem()
    {
        return WitchCraftItems.fireStaff;
    }
}
