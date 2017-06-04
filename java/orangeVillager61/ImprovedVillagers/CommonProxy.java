package orangeVillager61.ImprovedVillagers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
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

	public static final ResourceLocation VILLAGE_BUTCHER_LT = LootTableList.register(new ResourceLocation("iv","village_butcher"));

	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		ResourceLocation resourceLocation1 = new ResourceLocation("iv", "villager");
		MinecraftForge.EVENT_BUS.register(new ChangeVilMateAI());
		MinecraftForge.EVENT_BUS.register(new OverrideVillagers());
		EntityRegistry.registerModEntity(resourceLocation1, IvVillager.class, "IvVillager", 0, Iv.instance, 32, 1, true);
		//MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		if (Config.enableVillages){
			MinecraftForge.TERRAIN_GEN_BUS.register(new ChangeVillageGeneration());
		}
		IvItems.Init();
		IvBlocks.Init();
		IvItems.createItems();
		IvBlocks.createBlocks();
		
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
