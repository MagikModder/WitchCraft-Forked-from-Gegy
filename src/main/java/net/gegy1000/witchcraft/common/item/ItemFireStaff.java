package net.gegy1000.witchcraft.common.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.gegy1000.witchcraft.common.entity.EntityFireRain;
import net.gegy1000.witchcraft.common.entity.EntityFlame;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFireStaff extends ItemSword
{
    public ItemFireStaff()
    {
        super(ToolMaterial.WOOD);
        this.setMaxDamage(1000);
    }
    
    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean holding) 
    {
        if(holding && entity instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) entity;
            
            living.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1, 5));
            //            
            //            if(entity.ticksExisted % 16 == 0)
            //            {
            //                int x = (int) Math.floor(entity.posX);
            //                int y = (int) entity.posY - 1;
            //                int z = (int) Math.floor(entity.posZ);
            //                
            //                Block stage1 = Blocks.packed_ice;
            //                Block stage2 = Blocks.ice;
            //                Block stage3 = Blocks.water;
            //                
            //                Block standingOn = world.getBlock(x, y, z);
            //                
            //                if(standingOn == stage1 || standingOn == stage2)
            //                {
            //                    replaceIfBlockIs(world, stage2, stage3, x, y, z);
            //                    replaceIfBlockIs(world, stage2, stage3, x + 1, y, z);
            //                    replaceIfBlockIs(world, stage2, stage3, x - 1, y, z);
            //                    replaceIfBlockIs(world, stage2, stage3, x, y, z + 1);
            //                    replaceIfBlockIs(world, stage2, stage3, x, y, z - 1);
            //                    
            //                    replaceIfBlockIs(world, stage1, stage2, x, y, z);
            //                    replaceIfBlockIs(world, stage1, stage2, x + 1, y, z);
            //                    replaceIfBlockIs(world, stage1, stage2, x - 1, y, z);
            //                    replaceIfBlockIs(world, stage1, stage2, x, y, z + 1);
            //                    replaceIfBlockIs(world, stage1, stage2, x, y, z - 1);
            //                }
            //            }
        }
    }
    
    private void replaceIfBlockIs(World world, Block replacing, Block replaceWith, int x, int y, int z)
    {
        if(world.getBlock(x, y, z) == replacing)
        {
            world.setBlock(x, y, z, replaceWith);
        }
    }
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase source)
    {
        boolean hit = super.hitEntity(stack, target, source);
        
        if(hit && canAttack(source))
        {
            target.setFire(10);
        }
        
        return hit;
    }
    
    private boolean canAttack(EntityLivingBase source)
    {
        return !source.isWet();
    }
    
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time)
    {
        stack.damageItem(1, player);
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean b)
    {
        super.addInformation(stack, player, info, b);
        
        info.add(EnumChatFormatting.GRAY + translate("switch"));
        info.add("");
        info.add(EnumChatFormatting.DARK_RED + translate("mode") + " " + EnumChatFormatting.RED + getModeName(getMode(stack)));
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        int duration = this.getMaxItemUseDuration(stack) - count;
        
        World world = player.worldObj;
        
        if(canAttack(player))
        {
            int mode = getMode(stack);
            
            if(mode == 0)
            {
                if (duration >= 10 && duration <= 40)
                {
                    if(duration % 2 == 0)
                    {
                        world.spawnEntityInWorld(new EntityFlame(world, player));
                    }
                }
            }
            else if(mode == 1)
            {
                if (duration >= 20 && duration <= 30)
                {
                    createRing(player, duration, world);
                }
            }
            else if(mode == 2)
            {
                if(duration >= 20 && duration <= 50)
                {
                    if(!world.isRemote)
                    {
                        int centerX = (int) Math.floor(player.posX);
                        int centerZ = (int) Math.floor(player.posZ);
                        
                        int size = 10;
                        
                        Random rand = new Random();
                        
                        for (int x = centerX - size; x < centerX + size; x++)
                        {
                            for (int z = centerZ - size; z < centerZ + size; z++)
                            {
                                if(rand.nextInt(10) == 0)
                                {
                                    EntityFireRain fireRain = new EntityFireRain(world, player);
                                    
                                    fireRain.setPosition(x + rand.nextFloat(), player.posY + 20 + rand.nextFloat() * 10, z + rand.nextFloat());
                                    
                                    world.spawnEntityInWorld(fireRain);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void createRing(EntityPlayer player, int duration, World world)
    {
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        Vec3 playerPos = Vec3.createVectorHelper(posX, posY, posZ);
        
        if(duration % 2 == 0)
        {
            world.playSoundAtEntity(player, "mob.ghast.fireball", 1.0f, 1.0f);
        }
        
        for (int angle = 0; angle < 360; angle++)
        {
            float f3 = MathHelper.cos(-angle * 0.017453292F - (float) Math.PI);
            float f4 = MathHelper.sin(-angle * 0.017453292F - (float) Math.PI);
            float f5 = -MathHelper.cos(0.017453292F);
            float f6 = MathHelper.sin(0.017453292F);
            
            float f7 = f4 * f5;
            float f8 = f3 * f5;
            
            int dist = (duration - 15);
            Vec3 firePos = playerPos.addVector(f7 * dist, f6 * dist, f8 * dist);
            
            int x = (int) firePos.xCoord;
            int z = (int) firePos.zCoord;
            
            int y = world.getHeightValue(x, z); //TODO closest top block
            
            Random rand = new Random();
            
            int fireX = x;
            int fireY = y;
            int fireZ = z;
            
            if (world.getBlock(x, y, z) != Blocks.air)
            {
                if (!world.isAirBlock(x, y - 1, z))
                {
                    fireY++;
                }
                else if (!world.isAirBlock(x, y + 1, z))
                {
                    fireY--;
                }
                else if (!world.isAirBlock(x - 1, y, z))
                {
                    fireX++;
                }
                else if (!world.isAirBlock(x, y, z - 1))
                {
                    fireZ++;
                }
                else if (!world.isAirBlock(x + 1, y, z))
                {
                    fireX--;
                }
                else if (!world.isAirBlock(x, y, z + 1))
                {
                    fireZ--;
                }
            }
            
            world.spawnParticle("flame", x + rand.nextFloat() - 0.5F / 2, y + 1.2F, z + rand.nextFloat() - 0.5F / 2, 0.0D, 0.0D, 0.0D);
            
            if(world.isAirBlock(fireX, fireY, fireZ))
            {
                if(duration == 30 || rand.nextInt(60) == 5)
                {
                    world.setBlock(fireX, fireY, fireZ, Blocks.fire);
                }
            }
            
            for (Entity entity : getNearbyEntities(world, x, y, z, 2.0F))
            {
                if(entity != player)
                {
                    entity.setFire(20);
                    entity.attackEntityFrom(DamageSource.inFire, 2.0F);
                }
            }
        }
    }
    
    //    private int getClosestTopBlock(World world, int x, int y, int z)
    //    {
    //        int closestTopBlock = -1;
    //        
    //        if(!(world.isAirBlock(x, y, z) && !world.isAirBlock(x, y - 1, z)))
    //        {
    //            while(world.isAirBlock(x, y, z) && y < 256)
    //            {
    //                y++;
    //            }
    //            
    //            closestTopBlock = y;
    //        }
    //        else
    //        {
    //            closestTopBlock = y;
    //        }
    //        
    //        if(closestTopBlock == -1)
    //        {
    //            return world.getHeightValue(x, z);
    //        }
    //        else
    //        {
    //            return closestTopBlock;
    //        }
    //    }
    
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }
    
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
    
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        return EnumChatFormatting.GOLD + super.getItemStackDisplayName(stack);
    }
    
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!player.isSneaking())
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        else
        {
            toggleMode(stack, player);
        }
        
        return stack;
    }
    
    private void toggleMode(ItemStack stack, EntityPlayer player)
    {
        NBTTagCompound tagCompound = getTagCompound(stack);
        
        int newMode = tagCompound.getInteger("Mode") + 1;
        
        if(newMode > 2) //TODO More than 3 modes? API?
        {
            newMode = 0;
        }
        
        if(player.worldObj.isRemote)
        {
            String message = translate("switchmode_start") + " " + colourText(getItemStackDisplayName(stack), EnumChatFormatting.GOLD) + " " + translate("switchmode_middle") + " " + colourText(getModeName(newMode), EnumChatFormatting.RED) + " " + translate("switchmode_end") + ".";
            
            player.addChatMessage(new ChatComponentText(message));
        }
        
        tagCompound.setInteger("Mode", newMode);
    }
    
    private String translate(String text)
    {
        return StatCollector.translateToLocal("stats." + text + ".name");
    }
    
    private String getModeName(int mode)
    {
        return translate(getUnlocalizedName().replaceAll("item.", "") + "_mode_" + mode);
    }
    
    private int getMode(ItemStack stack)
    {
        return getTagCompound(stack).getInteger("Mode");
    }
    
    private NBTTagCompound getTagCompound(ItemStack stack)
    {
        NBTTagCompound tagCompound = stack.getTagCompound();
        
        if(tagCompound == null)
        {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        
        return tagCompound;
    }
    
    private List<Entity> getNearbyEntities(World world, double x, double y, double z, float radius)
    {
        return world.selectEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius), IEntitySelector.selectAnything); /** Meow **/
    }
    
    private String colourText(String text, EnumChatFormatting colour)
    {
        return colour + text + EnumChatFormatting.WHITE;
    }
}
