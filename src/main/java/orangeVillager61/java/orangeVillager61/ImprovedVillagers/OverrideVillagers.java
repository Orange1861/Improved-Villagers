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


public class OverrideVillagers {
	Random r = new Random();
	
	@SubscribeEvent
	public void entityJoinedWorldEventHandler(EntityJoinWorldEvent event)
	{
		if (event.getEntity().getClass() == EntityVillager.class && Config.overwriteOriginalVillagers && event.getWorld().isRemote == false && !Config.revertVillagers)
		{
			event.getEntity().setDead();
			doOverwriteVillager(event, (EntityVillager) event.getEntity());
		}
	}

	private void doOverwriteVillager(EntityJoinWorldEvent event, EntityVillager entity) 
	{
		if (entity.getProfession() >= 0 && entity.getProfession() <= 5)
		{
			int Gender;
		    Gender = r.nextInt(2) + 1;
	        String Name = IvVillager.random_name(Gender);
			IvVillager entityVillager = new IvVillager(entity.getWorld(), entity.getProfession(), Gender, Name);
			entityVillager.setGrowingAge(entity.getGrowingAge());
			entityVillager.onInitialSpawn(event.getWorld().getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
			entityVillager.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
			event.getWorld().setEntityState(entityVillager, (byte)12); 
			event.getWorld().spawnEntity(entityVillager); 
			
		}
	}
}
