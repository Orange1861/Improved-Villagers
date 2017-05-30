package orangeVillager61.ImprovedVillagers.client.render.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

@SideOnly(Side.CLIENT)
public class IvVillagerRender extends RenderLiving<IvVillager>
{
    private static final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/villager.png");
    private static final ResourceLocation FARMER_VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/farmer.png");
    private static final ResourceLocation LIBRARIAN_VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/librarian.png");
    private static final ResourceLocation PRIEST_VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/priest.png");
    private static final ResourceLocation SMITH_VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/smith.png");
    private static final ResourceLocation BUTCHER_VILLAGER_TEXTURES = new ResourceLocation("iv:textures/entity/villager/butcher.png");
    private static final ResourceLocation VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/villager_young.png");
    private static final ResourceLocation FARMER_VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/farmer_young.png");
    private static final ResourceLocation LIBRARIAN_VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/librarian_young.png");
    private static final ResourceLocation PRIEST_VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/priest_young.png");
    private static final ResourceLocation SMITH_VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/smith_young.png");
    private static final ResourceLocation BUTCHER_VILLAGER_YOUNG_TEXTURES = new ResourceLocation("iv:textures/entity/villager/butcher_young.png");
    private static final ResourceLocation VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/villager_old.png");
    private static final ResourceLocation FARMER_VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/farmer_old.png");
    private static final ResourceLocation LIBRARIAN_VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/librarian_old.png");
    private static final ResourceLocation PRIEST_VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/priest_old.png");
    private static final ResourceLocation SMITH_VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/smith_old.png");
    private static final ResourceLocation BUTCHER_VILLAGER_OLD_TEXTURES = new ResourceLocation("iv:textures/entity/villager/butcher_old.png");

    public IvVillagerRender(RenderManager renderManager)
    {
        super(renderManager, new ModelVillager(0.0F), 0.5F);
        this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
    }

    public ModelVillager getMainModel()
    {
        return (ModelVillager)super.getMainModel();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(IvVillager entity)
    {
	    if (entity.isChild() || entity.getAdultAge() == "Young Adult"){
    		if (entity.getProfessionForge() == entity.PROFESSION_FARMER || entity.getProfession() == 0)
	    	{
	    		return FARMER_VILLAGER_YOUNG_TEXTURES;
	    	}
	    	else if (entity.getProfession() == 1)
			{
				return LIBRARIAN_VILLAGER_YOUNG_TEXTURES;
			}
		    else if (entity.getProfession() == 2)
			{
				return PRIEST_VILLAGER_YOUNG_TEXTURES;
			}
		    else if (entity.getProfession() == 3)
			{
				return SMITH_VILLAGER_YOUNG_TEXTURES;
			}
		    else if (entity.getProfession() == 4)
			{
				return BUTCHER_VILLAGER_YOUNG_TEXTURES;
			}
		    else if (entity.getProfessionForge() == entity.PROFESSION_NITWIT)
			{
				return VILLAGER_YOUNG_TEXTURES;
			}
		    else
		    {
		    	return VILLAGER_YOUNG_TEXTURES;
		    }
    	}
    	else if (entity.getAdultAge() == "Middle Aged"){
	    	if (entity.getProfessionForge() == entity.PROFESSION_FARMER || entity.getProfession() == 0)
	        	{
	        		return FARMER_VILLAGER_TEXTURES;
	        	}
	        else if (entity.getProfession() == 1)
	    	{
	    		return LIBRARIAN_VILLAGER_TEXTURES;
	    	}
	        else if (entity.getProfession() == 2)
	    	{
	    		return PRIEST_VILLAGER_TEXTURES;
	    	}
	        else if (entity.getProfession() == 3)
	    	{
	    		return SMITH_VILLAGER_TEXTURES;
	    	}
	        else if (entity.getProfession() == 4)
	    	{
	    		return BUTCHER_VILLAGER_TEXTURES;
	    	}
		    else if (entity.getProfessionForge() == entity.PROFESSION_NITWIT)
	    	{
	    		return VILLAGER_TEXTURES;
	    	}
	        else
	        {
	        	return VILLAGER_TEXTURES;
	        }
    	}
    	else if (entity.getAdultAge() == "Elder"){
    		if (entity.getProfessionForge() == entity.PROFESSION_FARMER || entity.getProfession() == 0)
        	{
        		return FARMER_VILLAGER_OLD_TEXTURES;
        	}
    		else if (entity.getProfession() == 1)
        	{
        		return LIBRARIAN_VILLAGER_OLD_TEXTURES;
        	}
            else if (entity.getProfession() == 2)
        	{
        		return PRIEST_VILLAGER_OLD_TEXTURES;
        	}
            else if (entity.getProfession() == 3)
        	{
        		return SMITH_VILLAGER_OLD_TEXTURES;
        	}
            else if (entity.getProfession() == 4)
        	{
        		return BUTCHER_VILLAGER_OLD_TEXTURES;
        	}
		    else if (entity.getProfessionForge() == entity.PROFESSION_NITWIT)
        	{
        		return VILLAGER_OLD_TEXTURES;
        	}
            else
            {
            	return VILLAGER_OLD_TEXTURES;
            }
        	}
    	else{
    		//System.out.println(entity.getAdultAge());
    		return null;
    		}	
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(IvVillager entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;
        if (entitylivingbaseIn.getGrowingAge() < 0)
        {
            f = (float)((double)f * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }

}
