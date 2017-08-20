package orangeVillager61.ImprovedVillagers.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Reference;

@Mod.EventBusSubscriber
public class RegisterBlockModels {
	
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        IvBlocks.initModels();
    }
}
