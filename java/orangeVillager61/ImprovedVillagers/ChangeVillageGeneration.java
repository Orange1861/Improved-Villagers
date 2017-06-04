package orangeVillager61.ImprovedVillagers;

import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.generation.IvMapGenVillage;

public class ChangeVillageGeneration {

	@SubscribeEvent
	public void changeVillageGen (InitMapGenEvent event) {
		if (event.getType() != null && event.getType() == EventType.VILLAGE){
			IvMapGenVillage newGen = new IvMapGenVillage();
			event.setNewGen(newGen);
		}
	}	
	
}