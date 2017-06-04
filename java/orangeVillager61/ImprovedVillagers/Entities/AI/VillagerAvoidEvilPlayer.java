package orangeVillager61.ImprovedVillagers.Entities.AI;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class VillagerAvoidEvilPlayer<T extends Entity> extends EntityAIBase
{
    private final Predicate<Entity> canBeSeenSelector;
    /** The entity we are attached to */
    protected EntityCreature theEntity;
    private final double farSpeed;
    private final double nearSpeed;
    protected EntityPlayer closestLivingEntity;
    protected Village villageObj;
    private final float avoidDistance;
    /** The PathEntity of our entity */
    private Path entityPathEntity;
    /** The PathNavigate of our entity */
    private final PathNavigate entityPathNavigate;
    /** Class of entity this behavior seeks to avoid */
    private final Class<EntityPlayer> classToAvoid;
    private final Predicate <? super T > avoidTargetSelector;

    public VillagerAvoidEvilPlayer(EntityCreature theEntityIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, Village villageobj)
    {
        this(theEntityIn, EntityPlayer.class, Predicates.<T>alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn, villageobj);
    }

    public VillagerAvoidEvilPlayer(EntityCreature theEntityIn, Class<EntityPlayer> classToAvoidIn, Predicate <? super T > avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, Village villageobj)
    {
        this.canBeSeenSelector = new Predicate<Entity>()
        {
            public boolean apply(Entity p_apply_1_)
            {
                return p_apply_1_.isEntityAlive() && VillagerAvoidEvilPlayer.this.theEntity.getEntitySenses().canSee(p_apply_1_) && !VillagerAvoidEvilPlayer.this.theEntity.isOnSameTeam(p_apply_1_);
            }
        };
        this.theEntity = theEntityIn;
        this.classToAvoid = classToAvoidIn;
        this.avoidTargetSelector = avoidTargetSelectorIn;
        this.avoidDistance = avoidDistanceIn;
        this.farSpeed = farSpeedIn;
        this.villageObj = villageobj;
        this.nearSpeed = nearSpeedIn;
        this.entityPathNavigate = theEntityIn.getNavigator();
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        List<EntityPlayer> list = this.theEntity.world.<EntityPlayer>getEntitiesWithinAABB(this.classToAvoid, this.theEntity.getEntityBoundingBox().expand((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), Predicates.and(new Predicate[] {EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector, this.avoidTargetSelector})); 
    	if (list.isEmpty())
        {
            return false;
        }
        else
        {
        	if (this.theEntity.world.isRemote == false){
            System.out.println(this.villageObj.getPlayerReputation(this.classToAvoid.getName()));
        	if (this.villageObj.getPlayerReputation(this.classToAvoid.getName()) < -8){ 
            this.closestLivingEntity = list.get(0);
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3d(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
            if (vec3d == null)
            {
                return false;
            }
            else if (this.closestLivingEntity.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
            {
                return false;
            }
            else
            {
                this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
                return this.entityPathEntity != null;
            }
        }
        	else{
        		return false;
        	}
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
        return !this.entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D)
        {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        }
        else
        {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}