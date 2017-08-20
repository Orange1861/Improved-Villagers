package orangeVillager61.ImprovedVillagers.Items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Blocks.LightBlueStairs;
import orangeVillager61.ImprovedVillagers.Blocks.Villager_Nose;

@Mod.EventBusSubscriber

public class IvItems {

	public Random r = new Random();
	
	@GameRegistry.ObjectHolder("iv:raw_villager")
	public static BasicFood raw_villager = null;
	
	@GameRegistry.ObjectHolder("iv:cooked_villager")
	public static BasicFood cooked_villager = null;
	
	@GameRegistry.ObjectHolder("iv:thieving_nose")
	public static ThievingNose thieving_nose = null;
	
	@GameRegistry.ObjectHolder("iv:notification_marker")
	public static BasicItem notification_marker = null;
	
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ThievingNose());
        event.getRegistry().register(new BasicItem("notification_marker", 1, 120));
        event.getRegistry().register(new BasicFood("cooked_villager", 9, 1.1F, true));
        event.getRegistry().register(new BasicFood("raw_villager", 5, 0.5F, true, new PotionEffect(MobEffects.HUNGER, 80, 0)));
    }
    
    public static void initModels() {
    	raw_villager.initModel();
    	cooked_villager.initModel();
    	thieving_nose.initModel();
    	notification_marker.initModel();
    }
}
