package net.gegy1000.witchcraft.common.item;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemIceStaff extends ItemSword
{
    public ItemIceStaff()
    {
        super(ToolMaterial.WOOD);
        this.setMaxDamage(1000);
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
            target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 9999, 10));
            target.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 9999, 10));
        }
        
        return hit;
    }
    
    private boolean canAttack(EntityLivingBase source)
    {
        return !source.isBurning();
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
        info.add(EnumChatFormatting.AQUA + translate("mode") + " " + EnumChatFormatting.WHITE + getModeName(getMode(stack)));
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
                if (duration >= 10 && duration <= 30)
                {
                    if(duration % 2 == 0)
                    {
                    }
                }
            }
            else if(mode == 1)
            {
                if (duration >= 20 && duration <= 30)
                {
                }
            }
        }
    }
    
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
        return EnumChatFormatting.AQUA + super.getItemStackDisplayName(stack);
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
