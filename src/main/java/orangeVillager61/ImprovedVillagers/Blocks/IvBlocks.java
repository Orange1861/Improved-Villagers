package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class IvBlocks {

	public static Block villager_nose;
	public static Block light_blue_stairs;
	
	public static void Init(){
		villager_nose = new Villager_Nose("villager_nose", Material.CLAY, 1.0F, 0.25F);
		light_blue_stairs = new LightBlueStairs("light_blue_stairs");
	}
	public static void createBlocks()
	{
		registerBlock(villager_nose);
		registerBlock(light_blue_stairs);
	}
	
	protected static void registerBlock(Block block){
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
}
