package orangeVillager61.ImprovedVillagers;

import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Entities.AI.VilsPerDoor;

public class ChangeVilMateAI {

	@SubscribeEvent
	public void entityVillagerAIOverride(LivingSpawnEvent event) {
	     if (event.getEntity() != null){
		    if (event.getEntity() instanceof EntityVillager) {
		          EntityVillager villager = (EntityVillager) event.getEntity();
		          villager.tasks.addTask(5, new VilsPerDoor(villager));
		          villager.tasks.addTask(3, new EntityAITempt(villager, 0.9D, Items.EMERALD, false));
		          villager.tasks.addTask(1, new EntityAIPanic(villager, 0.55D));
		     }
		}
	}
}