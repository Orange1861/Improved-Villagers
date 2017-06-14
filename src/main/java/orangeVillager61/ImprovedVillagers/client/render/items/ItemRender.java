package orangeVillager61.ImprovedVillagers.client.render.items;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;
import orangeVillager61.ImprovedVillagers.Items.IvItems;

public class ItemRender {
	
public static String modid = Reference.MOD_ID;

	public static void registerItemRenderer() {
		reg(IvItems.thieving_nose);
		reg(IvItems.raw_villager);
		reg(IvItems.cooked_villager);
		reg(IvItems.notification_marker);
	}

	public static void reg(Item item) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
