package net.gegy1000.witchcraft.common.entity;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFlame extends EntityThrowable
{
    public EntityFlame(World world)
    {
        super(world);
    }
    
    public EntityFlame(World world, EntityLivingBase entity)
    {
        super(world, entity);
    }
    
    public EntityFlame(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }
    
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
    protected float func_70182_d()
    {
        return 2.0F;
    }
    
    public void onUpdate()
    {
        super.onUpdate();
        
        if (this.isEntityAlive())
        {
            if (worldObj.isRemote)
            {
                for (int i = 0; i < 5; ++i)
                {
                    float f = (rand.nextFloat() / 5);
                    
                    if(rand.nextInt(2) == 1)
                    {
                        f = -f;
                    }
                    
                    float f2 = f;
                    
                    if(rand.nextInt(2) == 1)
                    {
                        f2 = (rand.nextFloat() / 5);
                    }
                    
                    worldObj.spawnParticle("flame", posX + f, posY + 0.15F + f, posZ + f2, 0, 0, 0);
                }
            }
            
            if (ticksExisted > 10)
            {
                this.setDead();
            }
        }
    }
    
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            mop.entityHit.setFire(20);
            
            if (getThrower() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) getThrower();
                mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 10.0F);
            }
        }
        
        setFire(worldObj, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
        
        this.setDead();
    }
    
    public void setFire(World world, int x, int y, int z, int sideHit)
    {
        if (sideHit == 0)
        {
            --y;
        }
        else if (sideHit == 1)
        {
            ++y;
        }
        else if (sideHit == 2)
        {
            --z;
        }
        else if (sideHit == 3)
        {
            ++z;
        }
        else if (sideHit == 4)
        {
            --x;
        }
        else if (sideHit == 5)
        {
            ++x;
        }
        
        if (world.isAirBlock(x, y, z))
        {
            world.setBlock(x, y, z, Blocks.fire);
        }
    }
}