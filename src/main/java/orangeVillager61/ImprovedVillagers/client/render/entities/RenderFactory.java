package orangeVillager61.ImprovedVillagers.client.render.entities;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactory implements IRenderFactory{

	@Override
	public Render createRenderFor(RenderManager manager) {
		return new IvVillagerRender(manager);
	}

}
