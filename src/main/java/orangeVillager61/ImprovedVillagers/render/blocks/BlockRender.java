package orangeVillager61.ImprovedVillagers.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;

public class BlockRender {
	
public static String modid = Reference.MOD_ID;
	
	@SideOnly(Side.CLIENT)
	public static void preInit() {
		
	}
	
	public static void registerBlockRenderer() {
		
	    reg(IvBlocks.villager_nose);
	    reg(IvBlocks.light_blue_stairs);
	    //reg(IvBlocks.clay1_door);
	}

	public static void reg(Block block) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static void reg(Block block, int meta, String file) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(file, "inventory"));
	}
}