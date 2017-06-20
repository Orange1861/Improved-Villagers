package orangeVillager61.ImprovedVillagers;

import java.util.Random;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;


public class RevertVillagers {
	Random r = new Random();
	
	@SubscribeEvent
	public void entityJoinedWorldEventHandler(EntityJoinWorldEvent event)
	{
		if (event.getEntity().getClass() == IvVillager.class && Config.revertVillagers && event.getWorld().isRemote == false)
		{
			event.getEntity().setDead();
			doOverwriteVillager(event, (IvVillager) event.getEntity());
		}
	}

	private void doOverwriteVillager(EntityJoinWorldEvent event, IvVillager entity) 
	{
		if (entity.getProfession() >= 0 && entity.getProfession() <= 5)
		{
			EntityVillager entityVillager = new EntityVillager(entity.getEntityWorld(), entity.getProfession());
			entityVillager.setGrowingAge(entity.getGrowingAge());
			entityVillager.onInitialSpawn(event.getWorld().getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
			entityVillager.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
			entityVillager.setCustomNameTag(entity.getName());
			event.getWorld().setEntityState(entityVillager, (byte)12); 
			event.getWorld().spawnEntityInWorld(entityVillager); 
			
		}
	}
}
