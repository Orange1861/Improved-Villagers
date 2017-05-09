package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class IvBlocks {

	public static Block villager_nose;
	//public static ClayDoor clay1_door;
	public static Block light_blue_stairs;
	
	public static void Init(){
		villager_nose = new Villager_Nose("villager_nose", Material.CLAY, 1.0F, 0.25F);
		//clay1_door = new ClayDoor("clay1_door", Material.ROCK, 10.0F, 7.25F);
		light_blue_stairs = new LightBlueStairs("light_blue_stairs");
	}
	public static void createBlocks()
	{
		registerBlock(villager_nose);
		registerBlock(light_blue_stairs);
		//registerBlock(clay1_door);
	}
	
	protected static void registerBlock(Block block){
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
}
