package orangeVillager61.ImprovedVillagers.Entities.AI;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public abstract class VillagerAIDoorInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected BlockPos doorPosition = BlockPos.ORIGIN;
    /** The wooden door block */
    protected BlockFenceGate doorBlock;
    /** If is true then the Entity has stopped Door Interaction and compoleted the task. */
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    float entityPositionZ;

    public VillagerAIDoorInteract(EntityLiving entityIn)
    {
        this.theEntity = entityIn;

        if (!(entityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */ 
    public boolean shouldExecute()
    {
        if (!this.theEntity.isCollidedHorizontally)
        {
            return false;
        }
        else
        {
            PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
            Path path = pathnavigateground.getPath();

            if (path != null && !path.isFinished() && pathnavigateground.getEnterDoors())
            {
                for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                {
                    PathPoint pathpoint = path.getPathPointFromIndex(i);
                    this.doorPosition = new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord);

                    if (this.theEntity.getDistanceSq((double)this.doorPosition.getX(), this.theEntity.posY, (double)this.doorPosition.getZ()) <= 2.25D)
                    {
                        this.doorBlock = this.getBlockDoor(this.doorPosition);

                        if (this.doorBlock != null)
                        {
                            return true;
                        }
                    }
                }

                this.doorPosition = (new BlockPos(this.theEntity)).up();
                this.doorBlock = this.getBlockDoor(this.doorPosition);
                return this.doorBlock != null;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.hasStoppedDoorInteraction;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)((double)((float)this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
        this.entityPositionZ = (float)((double)((float)this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        float f = (float)((double)((float)this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
        float f1 = (float)((double)((float)this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
        float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

        if (f2 < 0.0F)
        {
            this.hasStoppedDoorInteraction = true;
        }
    }

    private BlockFenceGate getBlockDoor(BlockPos pos)
    {
        IBlockState iblockstate = this.theEntity.world.getBlockState(pos);
        Block block1 = iblockstate.getBlock();
        return block1 instanceof BlockFenceGate && iblockstate.getMaterial() == Material.WOOD ? (BlockFenceGate)block1 : null;
    }
}