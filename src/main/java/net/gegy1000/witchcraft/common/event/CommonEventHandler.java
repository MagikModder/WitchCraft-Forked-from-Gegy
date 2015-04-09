package net.gegy1000.witchcraft.common.event;

import net.gegy1000.witchcraft.common.item.ItemIceStaff;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler
{
    @SubscribeEvent
    public void onUpdate(LivingUpdateEvent event)
    {
//        EntityLivingBase entity = event.entityLiving;
//        ItemStack heldItem = entity.getHeldItem();
//        
//        if(heldItem != null && heldItem.getItem() instanceof ItemIceStaff)
//        {
//            World world = entity.worldObj;
//            
//            int x = (int) Math.floor(entity.posX + (entity.posX - entity.prevPosX) * 2);
//            int y = (int) entity.posY - 1;
//            int z = (int) Math.floor(entity.posZ + (entity.posZ - entity.prevPosZ) * 2);
//            
//            Block stage2 = Blocks.ice;
//            Block stage1 = Blocks.water;
//            
//            Block standingOn = world.getBlock(x, y, z);
//            
//            if((standingOn == stage1 || standingOn == stage2) && world.isAirBlock(x, y + 1, z))
//            {
//                replaceIfBlockIs(world, stage1, stage2, x, y, z);
//                replaceIfBlockIs(world, stage1, stage2, x + 1, y, z);
//                replaceIfBlockIs(world, stage1, stage2, x - 1, y, z);
//                replaceIfBlockIs(world, stage1, stage2, x, y, z + 1);
//                replaceIfBlockIs(world, stage1, stage2, x, y, z - 1);
//            }
//        }
    }
    
    private void replaceIfBlockIs(World world, Block replacing, Block replaceWith, int x, int y, int z)
    {
        if(world.getBlock(x, y, z) == replacing)
        {
            world.setBlock(x, y, z, replaceWith);
        }
    }
}
