package orangeVillager61.ImprovedVillagers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Entities.VillagerProvider;

public class CapabilityHandler

{

 public static final ResourceLocation MANA_CAP = new ResourceLocation(Reference.MOD_ID, "villager");



 @SubscribeEvent

 public void attachCapability(AttachCapabilitiesEvent.Entity event)

 {

 if (!(event.getEntity() instanceof IvVillager)) return;



 event.addCapability(MANA_CAP, new VillagerProvider());

 }

}