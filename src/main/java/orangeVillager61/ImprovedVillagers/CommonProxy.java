package orangeVillager61.ImprovedVillagers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;
import orangeVillager61.ImprovedVillagers.Entities.IVillagerStorage;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.Entities.VillagerStorage;
import orangeVillager61.ImprovedVillagers.Entities.VillagerStorages;
import orangeVillager61.ImprovedVillagers.Items.IvItems;
import orangeVillager61.ImprovedVillagers.mobDrops.VillagerDrops;

public class CommonProxy {

	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		ResourceLocation resourceLocation1 = new ResourceLocation("iv", "villager");
		MinecraftForge.EVENT_BUS.register(new ChangeVilMateAI());
		MinecraftForge.EVENT_BUS.register(new OverrideVillagers());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		if (Config.enableVillages == 0){
			MinecraftForge.TERRAIN_GEN_BUS.register(new ChangeVillageGeneration());
		}
		IvItems.Init();
		IvBlocks.Init();
		IvItems.createItems();
		IvBlocks.createBlocks();
		CapabilityManager.INSTANCE.register(IVillagerStorage.class, new VillagerStorages(), VillagerStorage.class);
		EntityRegistry.registerModEntity(resourceLocation1, IvVillager.class, "IvVillager", 0, Iv.instance, 32, 1, true);
		
	}
	@EventHandler
    public void init(FMLInitializationEvent e) {
		IvRecipes.addRecipes();
     	MinecraftForge.EVENT_BUS.register(new VillagerDrops());
	}
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {

    }
}
