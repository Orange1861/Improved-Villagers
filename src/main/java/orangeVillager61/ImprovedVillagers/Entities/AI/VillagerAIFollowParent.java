package orangeVillager61.ImprovedVillagers.Entities.AI;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class VillagerAIFollowParent extends EntityAIBase
{
    private final IvVillager entityObj;
    private IvVillager parent;
    private int delayCounter;
    double moveSpeed;

    public VillagerAIFollowParent(IvVillager entityObjIn, double speed)
    {
        this.entityObj = entityObjIn;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        BlockPos blockpos = new BlockPos(this.entityObj);
        if ((!this.entityObj.worldObj.isDaytime() || this.entityObj.worldObj.isRaining() && !this.entityObj.worldObj.getBiome(blockpos).canRain()) && !this.entityObj.worldObj.provider.getHasNoSky())
        {
            	List<IvVillager> list = this.entityObj.worldObj.<IvVillager>getEntitiesWithinAABB(this.entityObj.getClass(), this.entityObj.getEntityBoundingBox().expand(128.0D, 6.0D, 128.0D));
                IvVillager entityanimal = null;
                double d0 = Double.MAX_VALUE;

                for (IvVillager entityanimal1 : list)
                {
                    if (entityanimal1.getGrowingAge() >= 0)
                    {
                        double d1 = this.entityObj.getDistanceSqToEntity(entityanimal1);

                        if (d1 <= d0)
                        {
                            d0 = d1;
                            if (entityanimal1.getUniqueID().equals(this.entityObj.getMotherId()) || entityanimal1.getUniqueID().equals(this.entityObj.getFatherId()))
                            {
                            	entityanimal = entityanimal1;
                            }
                        }
                    }
                }

                if (entityanimal == null)
                {
                    return false;
                }
                else if (d0 < 9.0D)
                {
                    return false;
                }
                else
                {
                    this.parent = entityanimal;
                    return true;
                }
            }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (this.entityObj.getGrowingAge() >= 0)
        {
            return false;
        }
        else if (!this.entityObj.isEntityAlive())
        {
            return false;
        }
        else
        {
            double d0 = this.entityObj.getDistanceSqToEntity(this.parent);
            return d0 >= 8.0D && d0 <= 1000.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.delayCounter = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.parent = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (--this.delayCounter <= 0)
        {
            this.delayCounter = 10;
            this.entityObj.getNavigator().tryMoveToEntityLiving(this.parent, this.moveSpeed);
        }
    }
}