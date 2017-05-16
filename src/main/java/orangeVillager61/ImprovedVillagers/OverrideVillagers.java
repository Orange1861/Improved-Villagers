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
	
	public String[] male_list = {"Bob", "Joseph", "Aaron", "Philp", "Adam", "Paul", "Donald", "Ryan", 
			"Mark", "Brian", "Robert", "Willam", "Harold", "Anthony", "Julius", 
			"Mathew", "Tyler", "Noah", "Patrick", "Caden", "Michael", "Jeffery",
			"James", "John", "Thomas", "Otto", "Bill", "Sheldon", "Leonard", 
			"Howard", "Carter", "Theodore", "Herbert"};
	public String[] female_list = {"Karen", "Lessie", "Kayla", "Brianna", "Isabella", "Elizabeth",
			  "Kira", "Jadzia", "Abigail", "Chloe", "Olivia", "Sophia", "Emily", 
			  "Charlotte", "Amelia", "Maria", "Daria", "Sarah", "Theodora",
			  "Tia", "Jennifer", "Anglica", "Denna", "Tasha", "Catherine", "Lily",
			  "Amy", "Penny", "Julina", "Audrey", "Avery"};

	@SubscribeEvent
	public void entityJoinedWorldEventHandler(EntityJoinWorldEvent event)
	{
		if (event.getEntity().getClass() == EntityVillager.class && Config.overwriteOriginalVillagers && event.getWorld().isRemote == false)
		{
			event.getEntity().setDead();
			doOverwriteVillager(event, (EntityVillager) event.getEntity());
		}
	}

	private void doOverwriteVillager(EntityJoinWorldEvent event, EntityVillager entity) 
	{
		if (entity.getProfession() >= 0 && entity.getProfession() <= 5)
		{
	       String Name = null;
	       int Gender;
	       Gender = r.nextInt(2) + 1;
	        		if (Gender == 1){
	        			Name = male_list[r.nextInt(male_list.length)];
	        		}
	        		else if (Gender == 2){
	        			Name = female_list[r.nextInt(female_list.length)]; 
	        		}
	        		else{
	        			Name = "None";
	        			System.out.println("Something went wrong with gender, please report.");
	        			System.out.println("No Name");
	        		}
			IvVillager entityVillager = new IvVillager(entity.getWorld(), entity.getProfession(), Gender, Name);
			entityVillager.setGrowingAge(entity.getGrowingAge());
			entityVillager.onInitialSpawn(event.getWorld().getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
			entityVillager.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
			event.getWorld().setEntityState(entityVillager, (byte)12); 
			event.getWorld().spawnEntity(entityVillager); 
			
		}
	}
}
