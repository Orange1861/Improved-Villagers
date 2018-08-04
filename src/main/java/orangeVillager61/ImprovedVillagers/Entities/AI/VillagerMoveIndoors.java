package orangeVillager61.ImprovedVillagers.Entities.AI;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;

public class VillagerMoveIndoors extends EntityAIBase
{
    private final IvVillager entity;
    private VillageDoorInfo doorInfo;
    private int insidePosX = -1;
    private int insidePosZ = -1;

    public VillagerMoveIndoors(IvVillager entityIn)
    {
        this.entity = entityIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        BlockPos blockpos = new BlockPos(this.entity);
        if ((!this.entity.world.isDaytime() || this.entity.world.isRaining() && !this.entity.world.getBiome(blockpos).canRain()) && this.entity.world.provider.hasSkyLight())
        {
            if (this.entity.getRNG().nextInt(5) != 0)
            {
                return false;
            }
            else if (this.insidePosX != -1 && this.entity.getDistanceSq((double)this.insidePosX, this.entity.posY, (double)this.insidePosZ) < 4.0D)
            {
                return false;
            }
            else
            {
                Village village = this.entity.world.getVillageCollection().getNearestVillage(blockpos, 14);

                if (village == null)
                {
                    return false;
                }
                else
                {
                    this.doorInfo = village.getDoorInfo(blockpos);
                    return this.doorInfo != null;
                }
            }
        }
        else
        {
            EntityZombie zombie = (EntityZombie) entity.world.findNearestEntityWithinAABB(EntityZombie.class, this.entity.getEntityBoundingBox().expand(12.0D, 4.0D, 12.0D), this.entity);
            if (!(zombie == null))
            {
            	if (this.entity.getRNG().nextInt(5) != 0)
                {
                    return false;
                }
                else if (this.insidePosX != -1 && this.entity.getDistanceSq((double)this.insidePosX, this.entity.posY, (double)this.insidePosZ) < 4.0D)
                {
                    return false;
                }
                else
                {
                    Village village = this.entity.world.getVillageCollection().getNearestVillage(blockpos, 14);

                    if (village == null)
                    {
                        return false;
                    }
                    else
                    {
                        this.doorInfo = village.getDoorInfo(blockpos);
                        return this.doorInfo != null;
                    }
                }
            }
            else {
                return false;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.insidePosX = -1;
        BlockPos blockpos = this.doorInfo.getInsideBlockPos();
        int i = blockpos.getX();
        int j = blockpos.getY();
        int k = blockpos.getZ();

        if (this.entity.getDistanceSq(blockpos) > 256.0D)
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 14, 3, new Vec3d((double)i + 0.5D, (double)j, (double)k + 0.5D));

            if (vec3d != null)
            {
                this.entity.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 0.7D);
            }
        }
        else
        {
            this.entity.getNavigator().tryMoveToXYZ((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.7D);
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
        this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
        this.doorInfo = null;
    }
}
