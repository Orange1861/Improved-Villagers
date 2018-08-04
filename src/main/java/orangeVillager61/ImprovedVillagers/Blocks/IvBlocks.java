package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public final class IvBlocks {

	@GameRegistry.ObjectHolder("iv:light_blue_stairs")
	public static final LightBlueStairs light_blue_stairs = null;
	
	@GameRegistry.ObjectHolder("iv:villager_nose")
	public static final Villager_Nose villager_nose = null;
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new LightBlueStairs());
        event.getRegistry().register(new Villager_Nose());
    }
	
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(light_blue_stairs).setRegistryName(light_blue_stairs.getRegistryName()));
        event.getRegistry().register(new ItemBlock(villager_nose).setRegistryName(villager_nose.getRegistryName()));
    }
    
    public static void initModels() {
    	light_blue_stairs.initModel();
    	villager_nose.initModel();
    }
}
