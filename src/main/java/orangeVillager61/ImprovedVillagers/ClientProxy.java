package orangeVillager61.ImprovedVillagers;

import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.client.gui.GuiHandler;
import orangeVillager61.ImprovedVillagers.client.render.entities.IvVillagerRender;
import orangeVillager61.ImprovedVillagers.client.render.entities.RenderFactory;

public class ClientProxy extends CommonProxy{

	 @Override
	    public void preInit(FMLPreInitializationEvent e) {
	        super.preInit(e);
	        if (Config.enableIvTexture){
	        	RenderingRegistry.registerEntityRenderingHandler(IvVillager.class, new RenderFactory());
	        }
	    }

	    @Override
	    public void init(FMLInitializationEvent e) {
	        super.init(e);
	        NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MOD_ID, new GuiHandler());
	        
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent e) {
	        super.postInit(e);
	    }
}
