package orangeVillager61.ImprovedVillagers.Items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Blocks.Villager_Nose;

public class IvItems {

	public Random r = new Random();
	
	public static Item raw_villager;
	public static Item cooked_villager;
	public static Item thieving_nose;
	public static Item notification_marker;
	//public static Item villager_life_extender;
	
	public static void Init(){
		raw_villager = new BasicFood("raw_villager", 5, 0.5F, true, new PotionEffect(MobEffects.HUNGER, 80, 0));
		cooked_villager = new BasicFood("cooked_villager", 9, 1.1F, true);
		thieving_nose = new ThievingNose("thieving_nose", 16);
		notification_marker = new BasicItem("notification_marker", 1);
	}
	
	public static void createItems()
	{
		registerItem(raw_villager, "raw_villager");
		registerItem(cooked_villager, "cooked_villager");
		registerItem(thieving_nose, "thieving_nose");
		registerItem(notification_marker, "notification_marker");
		//registerItem(villager_life_extender, "villager_life_extender");
	}
	
	protected static void registerItem(Item item, String name){
		item.setRegistryName(Reference.MOD_ID + ":" + name);
		GameRegistry.register(item);
	}
}
