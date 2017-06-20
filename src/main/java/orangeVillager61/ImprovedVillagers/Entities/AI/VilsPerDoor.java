package orangeVillager61.ImprovedVillagers.Entities.AI;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Config;

public class VilsPerDoor extends EntityAIBase
{
	public final double VilPerDoor = Config.VilPerDoor;
    private final EntityVillager villagerObj;
    private EntityVillager mate;
    int mateAgain;
    protected Random rand = new Random();
    private World world;
    private int matingTimeout;
    Village villageObj;

    public VilsPerDoor(EntityVillager villagerIn)
    {
        this.villagerObj = villagerIn;
        this.world = villagerIn.worldObj;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villagerObj.getGrowingAge() != 0)
        {
            return false;
        }
        else if (this.villagerObj.getRNG().nextInt(500) != 0)
        {
            return false;
        }
        else
        {
            this.villageObj = this.world.getVillageCollection().getNearestVillage(new BlockPos(this.villagerObj), 0);

            if (this.villageObj == null)
            {
                return false;
            }
            else if (this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getIsWillingToMate(true))
            {
                Entity entity = this.world.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), this.villagerObj);

                if (entity == null)
                {
                    return false;
                }
                else
                {
                    this.mate = (EntityVillager)entity;
                    return this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true);
                }
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.matingTimeout = 300;
        this.villagerObj.setMating(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.matingTimeout >= 0 && this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate(false);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        --this.matingTimeout;
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25D)
        {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
        }
        else if (this.matingTimeout == 0 && this.mate.isMating())
        {
            this.giveBirth();
        }

        if (this.villagerObj.getRNG().nextInt(35) == 0)
        {
            this.world.setEntityState(this.villagerObj, (byte)12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager()
    {
        if (!this.villageObj.isMatingSeason())
        {
            return false;
        }
        else
        {
            int i = (int)((double)((float)this.villageObj.getNumVillageDoors()) * VilPerDoor);
            return this.villageObj.getNumVillagers() < i;
        }
    }

    private void giveBirth()
    {
        EntityVillager entityvillager = this.villagerObj.createChild(this.mate);
        EntityVillager entityvillager2 = this.villagerObj.createChild(this.mate);
        this.mate.setGrowingAge(6000);
        this.villagerObj.setGrowingAge(6000);
        if (Config.MateAgain){
        	mateAgain = rand.nextInt(3) + 1;
        }
        else{
        	mateAgain = 0;
        }
        if (mateAgain == 1) {
        this.mate.setIsWillingToMate(true);
        this.villagerObj.setIsWillingToMate(true);
        }
        else {
        this.mate.setIsWillingToMate(false);
        this.villagerObj.setIsWillingToMate(false);	
        }
        if (rand.nextInt(100) + 1 <= Config.twins && Config.twins > 0){
        entityvillager2.setGrowingAge(-24000);
        entityvillager2.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
        this.world.spawnEntityInWorld(entityvillager2);
        this.world.setEntityState(entityvillager2, (byte)12);
        }
        entityvillager.setGrowingAge(-24000);
        entityvillager.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
        this.world.spawnEntityInWorld(entityvillager);
        this.world.setEntityState(entityvillager, (byte)12);
    }


}
