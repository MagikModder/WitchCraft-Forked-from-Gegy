package net.gegy1000.witchcraft.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderInvisible extends Render
{
    public RenderInvisible()
    {
        shadowSize = 0.0F;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
    
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
    {
    }
}