package orangeVillager61.ImprovedVillagers.Entities.AI;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Config;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class IvVillagersBeingEvil extends EntityAIBase
{
	public final double VilPerDoor = Config.VilPerDoor;
    private final IvVillager villagerObj;
    private IvVillager victim;
    protected Random rand = new Random();
    private World world;
    Village villageObj;

    public IvVillagersBeingEvil(IvVillager villagerIn)
    {
        this.villagerObj = villagerIn;
        this.world = villagerIn.world;
        this.setMutexBits(3);
    }

	@Override
	public boolean shouldExecute() {
        if (this.villagerObj.getRNG().nextInt(5) != 0)
        {
            return false;
        }
        IvVillager entity = this.world.findNearestEntityWithinAABB(IvVillager.class, this.villagerObj.getEntityBoundingBox().expand(10.0D, 3.0D, 10.0D), this.villagerObj);
        if (entity == null)
        {
            return false;
        }
        if (entity.getRobbedTime() > 0)
        {
        	return false;
        }
        else
        {
        	System.out.println("Gonna Rob");
            this.victim = (IvVillager)entity;
            return true;
        }
	}
    public void startExecuting()
    {
        this.villagerObj.setBeingEvil(true);
    }
    
    public void resetTask()
    {
        this.villageObj = null;
        this.victim = null;        
        this.villagerObj.setBeingEvil(false);
    }
    
    public boolean continueExecuting()
    {
        return this.victim.getRobbedTime() == 0;
    }
    public void updateTask()
    {
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.victim, 10.0F, 30.0F);
        if (this.villagerObj.getDistanceSqToEntity(this.victim) > 1.25D)
        {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.victim, 0.8D);
        }
        else 
        {
        	System.out.println("Robbed");
            this.victim.getRobbed();
            this.villagerObj.setBeingEvil(false);
        }
    }
}