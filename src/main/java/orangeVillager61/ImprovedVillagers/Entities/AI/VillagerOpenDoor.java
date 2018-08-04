package orangeVillager61.ImprovedVillagers.Entities.AI;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;

public class VillagerOpenDoor extends EntityAIDoorInteract
{
    /** If the entity close the door */
    boolean closeDoor;
    /** The temporisation before the entity close the door (in ticks, always 20 = 1 second) */
    int closeDoorTemporisation;

    public VillagerOpenDoor(EntityLiving entitylivingIn, boolean shouldClose)
    {
        super(entitylivingIn);
        this.entity = entitylivingIn;
        this.closeDoor = shouldClose;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
    	if (this.closeDoor == false)
    	{
    		EntityZombie zombie = (EntityZombie) entity.world.findNearestEntityWithinAABB(EntityZombie.class, this.entity.getEntityBoundingBox().expand(10.0D, 4.0D, 10.0D), this.entity);
    		if (zombie == null)
    		{
    	        return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
    		}
    	}
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.closeDoorTemporisation = 20;
        this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        if (this.closeDoor)
        {
            this.doorBlock.toggleDoor(this.entity.world, this.doorPosition, false);
        }
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        --this.closeDoorTemporisation;
        super.updateTask();
    }
}
