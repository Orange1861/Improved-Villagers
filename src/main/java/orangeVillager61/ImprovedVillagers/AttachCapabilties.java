package orangeVillager61.ImprovedVillagers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.Entities.HarvestTimeProvider;

public class AttachCapabilties {
	
	public static final ResourceLocation HARVEST_TIME_CAP = new ResourceLocation(Reference.MOD_ID, "Harvest_Time");
			
	@SubscribeEvent
	public void AttachCapToEntity (AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityAnimal)
		{
			event.addCapability(HARVEST_TIME_CAP, new HarvestTimeProvider());
		}
	}
}
