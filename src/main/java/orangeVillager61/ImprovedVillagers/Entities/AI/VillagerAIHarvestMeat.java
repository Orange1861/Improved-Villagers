package orangeVillager61.ImprovedVillagers.Entities.AI;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Config;
import orangeVillager61.ImprovedVillagers.Entities.HarvestTimeProvider;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class VillagerAIHarvestMeat extends EntityAIBase
{
    private final IvVillager villagerObj;
    private EntityAnimal animal;
    int mateAgain;
    protected Random rand = new Random();
    private World world;
    String animal_type;
    int delayCounter;

    public VillagerAIHarvestMeat(IvVillager villagerIn)
    {
        this.villagerObj = villagerIn;
        this.world = villagerIn.world;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	System.out.println("Should Execute started.");
        if (this.villagerObj.getGrowingAge() != 0)
        {
    		System.out.println("Isn't adult");
            return false;
        }
        else if (this.villagerObj.wantsMoreFood() == false)
        {
    		System.out.println("Doesn't want food");
        	return false;
        }
        else if (this.villagerObj.getProfession() != 4)
        {
    		System.out.println("Is not a butcher");
        	return false;
        }
        else
        {
            EntityAnimal entity = (EntityAnimal) this.world.findNearestEntityWithinAABB(EntityAnimal.class, this.villagerObj.getEntityBoundingBox().expand(100.0D, 7.0D, 100.0D), this.villagerObj);

            if (entity == null)
            {
        		System.out.println("Entity is null");
                return false;
            }
            if (this.villagerObj.world.getWorldTime() - entity.getCapability(HarvestTimeProvider.CAPABILITY_HARVEST_ANIMAL_TIME, null).get_time() < 24000 && entity.getCapability(HarvestTimeProvider.CAPABILITY_HARVEST_ANIMAL_TIME, null).get_time() != 0)
			{
        		System.out.println("Entity has been harvested");
				return false;
			}
            if (entity.getGrowingAge() < 0)
            {
        		System.out.println("Entity is not adult");
            	return false;
            }
            if (entity instanceof EntityCow)
            {
            	this.animal_type = "Cow";
            	return true;
            }
            else if (entity instanceof EntityPig)
            {
            	this.animal_type = "Pig";
            	return true;
            }
            else if (entity instanceof EntitySheep)
            {
            	this.animal_type = "Sheep";
            	return true;
            }
            else if (entity instanceof EntityChicken)
            {
            	this.animal_type = "Chicken";
            	return true;
            }
            else
            {
        		System.out.println("Entity is not harvestable");
            	return false;
            }            
        }
    }
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        System.out.println("Butcher task approved.");
        this.delayCounter = 0;
    }
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	if (!this.animal.isEntityAlive())
        {
            return false;
        }
    	else if (this.villagerObj.getNavigator().noPath())
    	{
    		return false;
    	}
        else
        {
            System.out.println("Butcher task allowed to continue.");
        	return true;
        }
    }
    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.animal = null;
        this.villagerObj.getNavigator().clearPathEntity();
        this.delayCounter = 5;
    }
    /**
     * Updates the task
     */
    public void updateTask()
    {
        System.out.println("Butcher to animal.");
        if (this.villagerObj.getDistanceSqToEntity(this.animal) > 2.25D)
        {
            if (--this.delayCounter <= 0)
            {
                this.delayCounter = 10;
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.animal, 0.53D);
            }
        }
        else
        {
        	this.getMeat();
        }
    }

    private void getMeat()
    {
    	if (this.animal_type == "Cow")
    	{
    		this.animal.dropItem(Items.COOKED_BEEF, 1);
    	}
    	else if (this.animal_type == "Pig")
    	{
    		this.animal.dropItem(Items.COOKED_PORKCHOP, 1);
    	}
    	else if (this.animal_type == "Sheep")
    	{
    		this.animal.dropItem(Items.COOKED_MUTTON, 1);
    	}
    	else if (this.animal_type == "Chicken")
    	{
    		this.animal.dropItem(Items.COOKED_CHICKEN, 1);
    	}
        System.out.println("Butcher got the meat.");
    	this.villagerObj.setWorkTicks(200);
    	this.animal.getCapability(HarvestTimeProvider.CAPABILITY_HARVEST_ANIMAL_TIME, null).setTime(this.animal.world.getWorldTime());
    }


}